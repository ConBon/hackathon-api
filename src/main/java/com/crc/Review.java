package com.crc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.crc.beans.ReviewBean;
import com.crc.heroku.ConnectionProvider;
import com.google.gson.Gson;

@Path("/reviews")
public class Review {
	private static ConnectionProvider connectionProvider = ConnectionProvider.getInstance();
	private List<ReviewBean> reviews;
	private Gson gson = new Gson();
	
	/**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getReviews() {
    	if(reviews == null) {
    		reviews = new ArrayList<ReviewBean>();
	        try {
				Connection connection = connectionProvider.getConnection();
				Statement stmt = connection.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT * FROM reviews;");
				while (rs.next()) {
					
					ReviewBean review = new ReviewBean.ReviewBuilder()
							.title(rs.getString(2))
							.reviewText(rs.getString(3))
							.nickname(rs.getString(4))
							.positiveVotes(rs.getInt(5))
							.negativeVotes(rs.getInt(6))
							.productId(rs.getInt(7)).build();
					reviews.add(review);
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
    	}
    	return gson.toJson(reviews);
    }
}
