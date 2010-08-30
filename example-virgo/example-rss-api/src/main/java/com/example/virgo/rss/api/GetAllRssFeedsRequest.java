package com.example.virgo.rss.api;

public class GetAllRssFeedsRequest {
	
	private int page;
	
	private int perPage;
	
	private boolean includeChannelItems;

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getPerPage() {
		return perPage;
	}

	public void setPerPage(int perPage) {
		this.perPage = perPage;
	}

	public boolean isIncludeChannelItems() {
		return includeChannelItems;
	}

	public void setIncludeChannelItems(boolean includeChannelItems) {
		this.includeChannelItems = includeChannelItems;
	}
}

