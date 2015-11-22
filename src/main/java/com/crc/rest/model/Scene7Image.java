package com.crc.rest.model;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class Scene7Image {
	private final String asset_id;
	private final String colour;
	private final String image;
	private final String product_id;
	private final String sku_id;
	private final int variant;
	public final String imageURL;

	public static final String IMAGE_PATH = "http://media.chainreactioncycles.com/is/image/ChainReactionCycles/";

	public Scene7Image(final String asset_id, final String colour, final String image, final String product_id,
			final String sku_id, final int variant) {
		this.asset_id = asset_id;
		this.colour = colour;
		this.image = image;
		this.product_id = product_id;
		this.sku_id = sku_id;
		this.variant = variant;
		this.imageURL = IMAGE_PATH + image;
	}

	public static class ImageBuilder {
		private String asset_id;
		private String colour;
		private String image;
		private String product_id;
		private String sku_id;
		private int variant;
		private String imageURL;

		public String getImageURL() {
			return imageURL;
		}

		public ImageBuilder asset_id(String asset_id) {
			this.asset_id = asset_id;
			return this;
		}

		public ImageBuilder colour(String colour) {
			this.colour = colour;
			return this;
		}

		public ImageBuilder image(String image) {
			this.image = image;
			return this;
		}

		public ImageBuilder product_id(String product_id) {
			this.product_id = product_id;
			return this;
		}

		public ImageBuilder sku_id(String sku_id) {
			this.sku_id = sku_id;
			return this;
		}

		public ImageBuilder variant(int variant) {
			this.variant = variant;
			return this;
		}

		public ImageBuilder imageURL(String image) throws UnsupportedEncodingException {
			this.imageURL = URLEncoder.encode((IMAGE_PATH + image), "UTF-8");
			return this;
		}

		public Scene7Image build() {
			return new Scene7Image(asset_id, colour, image, product_id, sku_id, variant);
		}
	}

	public String getAsset_id() {
		return asset_id;
	}

	public String getColour() {
		return colour;
	}

	public String getImage() {
		return image;
	}

	public String getProduct_id() {
		return product_id;
	}

	public String getSku_id() {
		return sku_id;
	}

	public int getVariant() {
		return variant;
	}

	public String getImageURL() {
		return imageURL;
	}

}
