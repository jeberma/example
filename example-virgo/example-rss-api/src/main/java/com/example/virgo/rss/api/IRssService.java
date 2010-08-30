package com.example.virgo.rss.api;

import java.net.URL;

public interface IRssService {

	RssFeedType createRssFeed(URL feedUrl);
	
	void deleteRssFeed(String uuid);
	
	RssFeedType getRssFeed(String uuid);
	
	GetAllRssFeedsResponse getAllRssFeeds(GetAllRssFeedsRequest request);
}
