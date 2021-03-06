package com.crc.beans;

import java.util.List;

public class Product {
	private final int productId;
	private final String brandName;
	private final String productName;
	private final float price;
	private final List<String> sizes;
	private final int rating;
	private final String imageUrl;
	private final List<String> colours;
	
	public Product(
			final int bProductId,
			final String bBrandName,
			final String bProductName,
			final float bPrice,
			final List<String> bSizes,
			final int bRating,
			final String bImageUrl,
			final List<String> bColours) {
		this.productId = bProductId;
		this.brandName = bBrandName;
		this.productName = bProductName;
		this.price = bPrice;
		this.sizes = bSizes;
		this.rating = bRating;
		this.imageUrl = bImageUrl;
		this.colours = bColours;
	}

	public static class ProductBuilder {
		private int bProductId;
		private String bBrandName;
		private String bProductName;
		private float bPrice;
		private List<String> bSizes;
		private int bRating;
		private String bImageUrl;
		private List<String> bColours;
		
		public ProductBuilder() {}
		
		public ProductBuilder productId(int productId) {
			this.bProductId = productId;
			return this;
		}
		
		public ProductBuilder brandName(String brandName) {
			this.bBrandName = brandName;
			return this;
		}
		
		public ProductBuilder productName(String productName) {
			this.bProductName = productName;
			return this;
		}
		
		public ProductBuilder price(float price) {
			this.bPrice = price;
			return this;
		}
		public ProductBuilder sizes(List<String> sizes) {
			this.bSizes = sizes;
			return this;
		}
		public ProductBuilder rating(int rating) {
			this.bRating = rating;
			return this;
		}
		public ProductBuilder imageUrl(String imageUrl) {
			this.bImageUrl = imageUrl;
			return this;
		}
		public ProductBuilder colours(List<String> colours) {
			this.bColours = colours;
			return this;
		}
		
		public Product build() {
			return new Product(bProductId, bBrandName, bProductName, bPrice, 
					bSizes, bRating, bImageUrl, bColours);
		}
	}

	public int getProductId() {
		return productId;
	}

	public String getBrandName() {
		return brandName;
	}

	public String getProductName() {
		return productName;
	}

	public float getPrice() {
		return price;
	}

	public List<String> getSizes() {
		return sizes;
	}

	public int getRating() {
		return rating;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public List<String> getColours() {
		return colours;
	}
}