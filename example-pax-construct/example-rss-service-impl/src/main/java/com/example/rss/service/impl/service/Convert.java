package com.example.rss.service.impl.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.joda.time.DateTime;

import com.example.rss.service.api.ChannelType;
import com.example.rss.service.api.ItemType;
import com.example.rss.service.api.RssFeedType;
import com.example.rss.service.impl.domain.Channel;
import com.example.rss.service.impl.domain.Item;
import com.example.rss.service.impl.domain.RssFeed;

public abstract class Convert {
	
	public static RssFeedType toRssFeedType(RssFeed input) {
		return toRssFeedType(input, true);
	}
	
	public static RssFeedType toRssFeedType(RssFeed input, boolean convertItems) {
		if(input == null) {
			return null;
		}
		RssFeedType result = new RssFeedType();
		result.setChannel(toChannelType(input.getChannel(), convertItems));
		result.setUuid(input.getUuid());
		result.setUrl(input.getUrl());
		return result;
	}
	
	public static List<RssFeedType> toRssFeedTypes(Collection<RssFeed> input) {
		if(CollectionUtils.isEmpty(input)) {
			return new ArrayList<RssFeedType>();
		}
		List<RssFeedType> result = new ArrayList<RssFeedType>();
		for(RssFeed each : input) {
			if(each != null) {
				result.add(toRssFeedType(each, false));
			}
		}
		return result;
	}
	
	public static ChannelType toChannelType(Channel input, boolean convertItems) {
		if(input == null) {
			return null;
		}
		ChannelType result = new ChannelType();
		result.setDescription(input.getDescription());
		result.setDocs(input.getDocs());
		result.setGenerator(input.getGenerator());
		if(convertItems) {
			result.setItems(toItemTypes(input.getItemsUnmodifiable()));
		}
		result.setLanguage(input.getLanguage());
		result.setLink(input.getLink());
		result.setManagingEditor(input.getManagingEditor());
		result.setPubDate(toCalendar(input.getPubDate()));
		result.setTitle(input.getTitle());
		result.setWebMaster(input.getWebMaster());
		return result;
	}
	
	public static List<ItemType> toItemTypes(List<Item> input) {
		if(CollectionUtils.isEmpty(input)) {
			return new ArrayList<ItemType>();
		}
		List<ItemType> result = new ArrayList<ItemType>();
		for(Item each : input) {
			if(each != null) {
				result.add(toItemType(each));
			}
		}
		return result;
	}
	
	public static ItemType toItemType(Item input) {
		if(input == null) {
			return null;
		}
		ItemType result = new ItemType();
		result.setDescription(input.getDescription());
		result.setGuid(input.getGuid());
		result.setLink(input.getLink());
		result.setPubDate(toCalendar(input.getPubDate()));
		result.setTitle(input.getTitle());
		return result;
	}
	
	public static Calendar toCalendar(DateTime input) {
		if(input == null) {
			return null;
		}
		return input.toGregorianCalendar();
	}

}
