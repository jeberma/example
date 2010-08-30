package com.example.rss.service.api;

import java.util.List;


public interface IRssService {
	
	RssFeedType createRssFeed(String feedUrl);
	
	void deleteRssFeed(String uuid);
	
	RssFeedType getRssFeed(String uuid);
	
	List<RssFeedType> getAllRssFeeds();

}
