package com.crc.rest.controller;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.crc.heroku.ConnectionManager;
import com.crc.rest.model.Scene7Image;
import com.google.gson.Gson;

@Path("/images")
@Singleton
public class Images {
	private static ConnectionManager connectionManager = ConnectionManager.getInstance();
	private List<Scene7Image> scene7Images;
	private Gson gson = new Gson();
	private Map<String, ArrayList<Scene7Image>> images;

	/**
	 * Method handling HTTP GET requests. The returned object will be sent to
	 * the client as "text/plain" media type.
	 *
	 * @return String that will be returned as a text/plain response.
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getImages() {
		if (scene7Images == null) {
			scene7Images = new ArrayList<Scene7Image>();
			try {
				Connection connection = connectionManager.getConnection();
				Statement stmt = connection.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT * FROM SCENE7_IMAGES LIMIT 100;");
				while (rs.next()) {

					Scene7Image img = new Scene7Image.ImageBuilder().asset_id(rs.getString(1))
							.product_id(rs.getString(2)).image(rs.getString(3)).colour(rs.getString(5))
							.sku_id(rs.getString(5)).build();
					scene7Images.add(img);
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return gson.toJson(scene7Images);
	}

	/**
	 * Method handling HTTP GET requests. The returned object will be sent to
	 * the client as "text/plain" media type.
	 *
	 * @return String that will be returned as a text/plain response.
	 */
	@GET
	@Path("/{productId}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getImage(@PathParam("productId") String productIdStr) throws UnsupportedEncodingException {
		if (productIdStr != null) {

			try {

				if (images == null) {
					images = new ConcurrentHashMap<String, ArrayList<Scene7Image>>();
				}
				if (images.containsKey(productIdStr)) {
					return gson.toJson(images.get(productIdStr));
				} else {
					try {
						Connection connection = connectionManager.getConnection();
						Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
								ResultSet.CONCUR_READ_ONLY);
						ResultSet rs = stmt.executeQuery(
								"SELECT * FROM SCENE7_IMAGES WHERE product_id='" + productIdStr + "' LIMIT 20;");
						if (rs.next()) {
							// reset cursor to first row after check
							rs.beforeFirst();
							ArrayList<Scene7Image> imageList = new ArrayList<Scene7Image>();
							while (rs.next()) {

								Scene7Image img = new Scene7Image.ImageBuilder().asset_id(rs.getString(1))
										.product_id(rs.getString(2)).image(rs.getString(3)).colour(rs.getString(5))
										.sku_id(rs.getString(5)).build();

								imageList.add(img);
								images.put(productIdStr, imageList);
							}

							return gson.toJson(images.get(productIdStr));
						} else {
							return "No results";
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			} catch (NumberFormatException e) {
				e.printStackTrace();
				return "Not a valid product id!";
			}
		} else {
			return this.getImages();
		}
		return "";
	}
}
