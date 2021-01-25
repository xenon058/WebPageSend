package com.webpagesend;

public class TweetRecord {
	private String tweetUrl;

	public TweetRecord(String tweetUrl) {
		this.tweetUrl = tweetUrl;
	}

	public String getTweetUrl() {
		return tweetUrl;
	}

	public void setTweetUrl(String tweetUrl) {
		this.tweetUrl = tweetUrl;
	}

	@Override
	public String toString() {
		return "tweetUrl：" + this.tweetUrl;
	}
}
