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

import com.crc.beans.Category;
import com.crc.beans.Product;
import com.crc.heroku.ConnectionManager;
import com.google.gson.Gson;

@Path("/categories")
@Singleton
public class Categories {

	private static ConnectionManager connectionManager = ConnectionManager.getInstance();
	private Map<String, Category> categories;
	private Map<String, ArrayList<Product>> categoryProducts;
	private Gson gson = new Gson();
	private boolean categoriesFetched = false;
    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
	@GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getCategories() throws UnsupportedEncodingException {  
		if(!categoriesFetched) {
			categories = new ConcurrentHashMap<String, Category>();
	        try {
				Connection connection = connectionManager.getConnection();
				Statement stmt = connection.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT * FROM categories;");
				while (rs.next()) {
					Integer catId = rs.getInt(1);
					String catName = rs.getString(2);
					
					Category category = new Category.CategoryBuilder()
							.catId(catId)
							.catName(catName).build();
					categories.put(catName, category);
				}
				categoriesFetched = true;
				return gson.toJson(categories);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			return gson.toJson(categories);
		}
		
		return "Oops, try again!";
    }
    
    @GET
    @Path("/{categoryId}/products")
    @Produces(MediaType.APPLICATION_JSON)
    public String getProductReviews(@PathParam("categoryId") String categoryIdStr) throws UnsupportedEncodingException {
    	if(categoryIdStr != null) {
    		Integer categoryId;
    		try{
    			categoryId = Integer.parseInt(categoryIdStr);
    			
	    		if(categoryProducts == null) {
	    			categoryProducts = new ConcurrentHashMap<String, ArrayList<Product>>();
	    		}
	    		if(categoryProducts.containsKey(categoryId)) {
	    			return gson.toJson(categoryProducts.get(categoryId));
	    		} else {
	    			try {
	    				Connection connection = connectionManager.getConnection();
	    				Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
	    				ResultSet rs = stmt.executeQuery("SELECT c.catname, p.id, p.brandname, p.productname, p.price, p.sizes, p.rating, p.imageurl, "
	    						+"p.colours, p.catId FROM products p, categories c where p.catid = c.categoryid and p.catid = '"+categoryId+"';");
	    				if(rs.next()) {
	    					//reset cursor to first row after check
	    					rs.beforeFirst();
	    					String catName ="";
	    					ArrayList<Product> products = new ArrayList<>();
		    				while (rs.next()) {
		    					catName = rs.getString(1);
		    					Integer prodId = rs.getInt(2);
		    					String[] sizesArray = (String[]) rs.getArray(6).getArray();
		    					String [] coloursArray = (String[]) rs.getArray(9).getArray();
		    					
		    					Product product = new Product.ProductBuilder()
		    							.productId(prodId)
		    							.brandName(rs.getString(3))
		    							.productName(rs.getString(4))
		    							.price(rs.getFloat(5))
		    							.sizes(Arrays.asList(sizesArray))
		    							.rating(rs.getInt(7))
		    							.imageUrl(URLEncoder.encode(rs.getString(8), "UTF-8"))
		    							.colours(Arrays.asList(coloursArray)).build();
		    					products.add(product);
		    				}
		    				categoryProducts.put(catName, products);
		    				return gson.toJson(products);
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
    		return this.getCategories();
    	} 	
    	return "Oops, try again!";
    }
}