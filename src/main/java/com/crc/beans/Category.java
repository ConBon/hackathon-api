package com.crc.beans;

public class Category {
	private final Integer catId;
	private final String catName;
	
	public Category(final int bCatId, final String bCatName) {
		this.catId = bCatId;
		this.catName = bCatName;
	}
	
	public static class CategoryBuilder {
		private Integer bCatId;
		private String bCatName;
		
		public CategoryBuilder() {}
		
		public CategoryBuilder catId(Integer catId) {
			this.bCatId = catId;
			return this;
		}
		
		public CategoryBuilder catName(String catName) {
			this.bCatName = catName;
			return this;
		}
		
		public Category build() {
			return new Category(bCatId, bCatName);
		}
	}
}
