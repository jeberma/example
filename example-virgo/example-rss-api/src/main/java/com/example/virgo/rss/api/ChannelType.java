package com.example.virgo.rss.api;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ChannelType {
	
	private String title;
	
	private String link;
	
	private String description;
	
	private String language;
	
	private Calendar pubDate;
	
	private String docs;
	
	private String generator;
	
	private String managingEditor;
	
	private String webMaster;
	
	private List<ItemType> items;
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public Calendar getPubDate() {
		return pubDate;
	}

	public void setPubDate(Calendar pubDate) {
		this.pubDate = pubDate;
	}

	public String getDocs() {
		return docs;
	}

	public void setDocs(String docs) {
		this.docs = docs;
	}

	public String getGenerator() {
		return generator;
	}

	public void setGenerator(String generator) {
		this.generator = generator;
	}

	public String getManagingEditor() {
		return managingEditor;
	}

	public void setManagingEditor(String managingEditor) {
		this.managingEditor = managingEditor;
	}

	public String getWebMaster() {
		return webMaster;
	}

	public void setWebMaster(String webMaster) {
		this.webMaster = webMaster;
	}
	
	public List<ItemType> getItems() {
		if(items == null) {
			items = new ArrayList<ItemType>();
		}
		return items;
	}
	
	public void setItems(List<ItemType> value) {
		items = value;
	}
	
}
