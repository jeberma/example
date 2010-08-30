package com.example.rss.service.api;

public class RssFeedType {
	
	private String uuid;
	
	private ChannelType channel;
	
	private String url;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public ChannelType getChannel() {
		return channel;
	}

	public void setChannel(ChannelType channel) {
		this.channel = channel;
	}
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String value) {
		url = value;
	}
	
}
