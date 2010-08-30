package com.example.virgo.rss.api;

import java.util.ArrayList;
import java.util.List;

public class GetAllRssFeedsResponse {
	
	private int currentPage;
	
	private int totalPages;
	
	private long totalEntries;
	
	private List<RssFeedType> rssFeeds = new ArrayList<RssFeedType>();

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public long getTotalEntries() {
		return totalEntries;
	}

	public void setTotalEntries(long totalEntries) {
		this.totalEntries = totalEntries;
	}

	public List<RssFeedType> getRssFeeds() {
		return rssFeeds;
	}

	public void setRssFeeds(List<RssFeedType> rssFeeds) {
		this.rssFeeds = rssFeeds;
	}
}
