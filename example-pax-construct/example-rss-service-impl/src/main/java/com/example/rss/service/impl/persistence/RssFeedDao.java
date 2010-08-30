package com.example.rss.service.impl.persistence;

import org.springframework.stereotype.Repository;

import com.example.hibernate.common.HibernateGenericDao;
import com.example.rss.service.impl.domain.RssFeed;

@Repository
public class RssFeedDao extends HibernateGenericDao<RssFeed, Long> {
	
	public RssFeedDao() {
		super(RssFeed.class);
	}
	
	public RssFeed findByUuid(String uuid) {
		return (RssFeed) getSession()
			.createQuery("from " + getDomainClass().getName() + "where uuid=:uuid")
			.setParameter("uuid", uuid)
			.uniqueResult();
	}

}
