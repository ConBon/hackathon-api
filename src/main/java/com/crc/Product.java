package com.crc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.crc.heroku.ConnectionProvider;

/**
 * Root resource (exposed at "product" path)
 */
@Path("/products")
public class Product {

	private static ConnectionProvider connectionProvider = ConnectionProvider.getInstance();
    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getProduct(@PathParam("productId") String productIdStr) {
        int productId;
        if(productIdStr != null && productIdStr != "") {	
	        try {
	        	productId = Integer.parseInt(productIdStr);
	        } catch(NumberFormatException e) {}
        }
    	
        
        try {
			Connection connection = connectionProvider.getConnection();
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM products;");
			/*String results = "";*/
			while (rs.next()) {
				return rs.getString(1) + ":"+rs.getString(2);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	return "";
    }
    
    @POST
    public void postProduct(String x) {
    	
    }
}
