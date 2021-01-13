package com.webpagesend;

public class TweetRecord {
	private static final String TYPE = "WebSite";

	private String tweetUrl;

	public TweetRecord(String tweetUrl) {
		this.tweetUrl = tweetUrl;
	}

	public static int getType() {
		int i = 2;
		switch(TYPE) {
		case "WebSite":
			i = 0;
			break;
		case "app":
			i = 1;
			break;
		}

		return i;
	}

	public String getTweetUrl() {
		return tweetUrl;
	}

	public void setTweetUrl(String tweetUrl) {
		this.tweetUrl = tweetUrl;
	}
}
