package com.crc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.crc.beans.ProductBean;
import com.crc.heroku.ConnectionProvider;
import com.google.gson.Gson;

/**
 * Resource (exposed at "/products" path)
 */
@Path("/products")
public class Product {

	private static ConnectionProvider connectionProvider = ConnectionProvider.getInstance();
	private List<ProductBean> products;
	private Gson gson = new Gson();
    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getProducts() {
    	if(products ==  null) {
	     	products = new ArrayList<ProductBean>();
	        try {
				Connection connection = connectionProvider.getConnection();
				Statement stmt = connection.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT * FROM products;");
				while (rs.next()) {
					
					String[] sizesArray = (String[]) rs.getArray(5).getArray();
					String [] coloursArray = (String[]) rs.getArray(8).getArray();
					
					ProductBean product = new ProductBean.ProductBuilder()
							.brandName(rs.getString(2))
							.productName(rs.getString(3))
							.price(rs.getFloat(4))
							.sizes(Arrays.asList(sizesArray))
							.rating(rs.getInt(6))
							.imageUrl(rs.getString(7))
							.colours(Arrays.asList(coloursArray)).build();
					products.add(product);
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
    	}
    	return gson.toJson(products);
    }
}
