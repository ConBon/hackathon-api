package com.crc;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.crc.beans.Product;
import com.crc.beans.Review;
import com.crc.heroku.ConnectionManager;
import com.google.gson.Gson;

/**
 * Resource (exposed at "/products" path)
 */

@Path("/products")
@Singleton
public class Products {

	private static ConnectionManager connectionManager = ConnectionManager.getInstance();
	private Map<Integer, Product> products;
	private Map<Integer, ArrayList<Review>> productReviews;
	private Gson gson = new Gson();
	private boolean productsFetched = false;
    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
	@GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getProducts() throws UnsupportedEncodingException {  
		if(!productsFetched) {
	     	products = new ConcurrentHashMap<Integer, Product>();
	        try {
				Connection connection = connectionManager.getConnection();
				Statement stmt = connection.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT * FROM products;");
				while (rs.next()) {
					Integer prodId = rs.getInt(1);
					String[] sizesArray = (String[]) rs.getArray(5).getArray();
					String [] coloursArray = (String[]) rs.getArray(8).getArray();
					
					Product product = new Product.ProductBuilder()
							.productId(prodId)
							.brandName(rs.getString(2))
							.productName(rs.getString(3))
							.price(rs.getFloat(4))
							.sizes(Arrays.asList(sizesArray))
							.rating(rs.getInt(6))
							.imageUrl(URLEncoder.encode(rs.getString(7), "UTF-8"))
							.colours(Arrays.asList(coloursArray)).build();
					products.put(prodId, product);
				}
				productsFetched = true;
				return gson.toJson(products);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			return gson.toJson(products);
		}
		
		return "Oops, try again!";
    }
    
    @GET
    @Path("/{productId}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getProduct(@PathParam("productId") String productIdStr) throws UnsupportedEncodingException {
    	if(productIdStr != null) {
    		Integer productId;
    		try{
    			productId = Integer.parseInt(productIdStr);
    			
	    		if(products == null) {
	    			products = new ConcurrentHashMap<Integer, Product>();
	    		}
	    		if(products.containsKey(productId)) {
	    			return gson.toJson(products.get(productId));
	    		} else {
	    			try {
	    				Connection connection = connectionManager.getConnection();
	    				Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
	    				ResultSet rs = stmt.executeQuery("SELECT * FROM products WHERE id='"+productId+"';");
	    				if(rs.next()) {
	    					//reset cursor to first row after check
	    					rs.beforeFirst();
		    				while (rs.next()) {
		    					Integer prodId = rs.getInt(1);
		    					String[] sizesArray = (String[]) rs.getArray(5).getArray();
		    					String [] coloursArray = (String[]) rs.getArray(8).getArray();
		    					
		    					Product product = new Product.ProductBuilder()
		    							.productId(prodId)
		    							.brandName(rs.getString(2))
		    							.productName(rs.getString(3))
		    							.price(rs.getFloat(4))
		    							.sizes(Arrays.asList(sizesArray))
		    							.rating(rs.getInt(6))
		    							.imageUrl(URLEncoder.encode(rs.getString(7), "UTF-8"))
		    							.colours(Arrays.asList(coloursArray)).build();
		    					products.put(prodId, product);
		    					
		    					return gson.toJson(product);
		    				}
	    				} else {
	    					return "No results";
	    				}
	    			} catch (SQLException e) {
	    				e.printStackTrace();
	    			}
	    		}
    		} catch(NumberFormatException e) {
    			e.printStackTrace(); 
    			return "Not a valid product id!";
    		}
    	} else {
    		return this.getProducts();
    	} 	
    	return "";
    }
    
    @GET
    @Path("/{productId}/reviews")
    @Produces(MediaType.APPLICATION_JSON)
    public String getProductReviews(@PathParam("productId") String productIdStr) throws UnsupportedEncodingException {
    	if(productIdStr != null) {
    		Integer productId;
    		try{
    			productId = Integer.parseInt(productIdStr);
    			
	    		if(productReviews == null) {
	    			productReviews = new ConcurrentHashMap<Integer, ArrayList<Review>>();
	    		}
	    		if(productReviews.containsKey(productId)) {
	    			return gson.toJson(productReviews.get(productId));
	    		} else {
	    			try {
	    				Connection connection = connectionManager.getConnection();
	    				Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
	    				ResultSet rs = stmt.executeQuery("SELECT reviews.reviewId, reviews.title, reviews.reviewtext, reviews.nickname,"
	    								+ "reviews.positivevotes, reviews.negativeVotes FROM products INNER JOIN reviews ON products.id=reviews.productId where id='"+productId+"';");
	    				if(rs.next()) {
	    					//reset cursor to first row after check
	    					rs.beforeFirst();
	    					Integer reviewId = 0;
	    					ArrayList<Review> reviews = new ArrayList<>();
		    				while (rs.next()) {
		    					reviewId = rs.getInt(1);
		    					
		    					Review review = new Review.ReviewBuilder()
		    							.title(rs.getString(2))
		    							.reviewText(rs.getString(3))
		    							.nickname(rs.getString(4))
		    							.positiveVotes(rs.getInt(5))
		    							.negativeVotes(rs.getInt(6)).build();
		    					
		    					reviews.add(review);
		    				}
		    				productReviews.put(reviewId, reviews);
		    				return gson.toJson(reviews);
	    				} else {
	    					return "No results";
	    				}
	    			} catch (SQLException e) {
	    				e.printStackTrace();
	    			}
	    		}
    		} catch(NumberFormatException e) {
    			e.printStackTrace(); 
    			return "Not a valid product id!";
    		}
    	} else {
    		return this.getProducts();
    	} 	
    	return "Oops, try again!";
    }
}
