package com.example.rss.service.impl.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.rss.service.api.IRssService;
import com.example.rss.service.api.RssFeedType;
import com.example.rss.service.impl.domain.RssFeed;
import com.example.rss.service.impl.persistence.RssFeedDao;

@Service
@Transactional
public class RssService implements IRssService {
	
	private RssFeedDao rssFeedDao;
	
	@Required
	public void setRssFeedDao(RssFeedDao value) {
		rssFeedDao = value;
	}

	public RssFeedType createRssFeed(String feedUrl) {
		RssFeed rssFeed = new RssFeed();
		rssFeed.setUrl(feedUrl);
		rssFeedDao.save(rssFeed);
		return Convert.toRssFeedType(rssFeed);
	}

	public void deleteRssFeed(String uuid) {
		RssFeed feed = rssFeedDao.findByUuid(uuid);
		if(feed != null) {
			rssFeedDao.delete(feed.getId());
		}
	}

	@Transactional(readOnly=true)
	public List<RssFeedType> getAllRssFeeds() {
		List<RssFeed> rssFeeds = rssFeedDao.findAll();
		return Convert.toRssFeedTypes(rssFeeds);
	}

	@Transactional(readOnly=true)
	public RssFeedType getRssFeed(String uuid) {
		RssFeed feed = rssFeedDao.findByUuid(uuid);
		if(feed == null) {
			return null;
		}
		return Convert.toRssFeedType(feed);
	}

}
