package com.crc;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Root resource
 * @author conalmclaughlin
 * 
 */
@Path("/")
public class Index {

	@GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getRoot() {
		return "CRC Hackathon RESTful API demo.\n"
				+"@GET /products\n"
				+"@GET /products/{productId}\n"
				+"@GET /products/{productId}/reviews\n"
				+"@GET /reviews\n"
				+"@GET /categories\n"
				+"@GET /categories/{categoryId}/products";
	}
}
