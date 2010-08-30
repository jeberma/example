package com.example.virgo.rss.api;

public class RssFeedType {
	
	private String uuid;
	
	private ChannelType channel;

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
}
