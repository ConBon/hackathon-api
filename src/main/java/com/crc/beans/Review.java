package com.crc.beans;

public class Review {
	private final String title;
	private final String reviewText;
	private final String nickname;
	private final int positiveVotes;
	private final int negativeVotes;
	private final int reviewId;
	
	public Review(
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
		this.reviewId = bProductId;
	}
	
	public static class ReviewBuilder {
		private String bTitle;
		private String bReviewText;
		private String bNickname;
		private int bPositiveVotes;
		private int bNegativeVotes;
		private int bReviewId;
		
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
		
		public ReviewBuilder reviewId(int reviewId) {
			this.bReviewId = reviewId;
			return this;
		}
		
		public Review build() {
			return new Review (bTitle, bReviewText, bNickname, bPositiveVotes, bNegativeVotes, bReviewId);
		}
		
	}

	public String getTitle() {
		return title;
	}

	public String getReviewText() {
		return reviewText;
	}

	public String getNickname() {
		return nickname;
	}

	public int getPositiveVotes() {
		return positiveVotes;
	}

	public int getNegativeVotes() {
		return negativeVotes;
	}

	public int getProductId() {
		return reviewId;
	}
	
}
