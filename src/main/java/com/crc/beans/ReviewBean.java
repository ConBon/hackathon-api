package com.crc.beans;

public class ReviewBean {
	private final String title;
	private final String reviewText;
	private final String nickname;
	private final int positiveVotes;
	private final int negativeVotes;
	private final int productId;
	
	public ReviewBean(
			final String bTitle,
			final String bReviewText,
			final String bNickname,
			final int bPositiveVotes,
			final int bNegativeVotes,
			final int bProductId) {
		this.title = bTitle;
		this.reviewText = bReviewText;
		this.nickname = bNickname;
		this.positiveVotes = bPositiveVotes;
		this.negativeVotes = bNegativeVotes;
		this.productId = bProductId;
	}
	
	public static class ReviewBuilder {
		private String bTitle;
		private String bReviewText;
		private String bNickname;
		private int bPositiveVotes;
		private int bNegativeVotes;
		private int bProductId;
		
		public ReviewBuilder title(String title) {
			this.bTitle = title;
			return this;
		}
		
		public ReviewBuilder reviewText(String reviewText) {
			this.bReviewText = reviewText;
			return this;
		}
		
		public ReviewBuilder nickname(String nickname) {
			this.bNickname = nickname;
			return this;
		}
		
		public ReviewBuilder positiveVotes(int positiveVotes) {
			this.bPositiveVotes = positiveVotes;
			return this;
		}
		
		public ReviewBuilder negativeVotes(int negativeVotes) {
			this.bNegativeVotes = negativeVotes;
			return this;
		}
		
		public ReviewBuilder productId(int productId) {
			this.bProductId = productId;
			return this;
		}
		
		public ReviewBean build() {
			return new ReviewBean (bTitle, bReviewText, bNickname, bPositiveVotes, bNegativeVotes, bProductId);
		}
		
	}
	
}
