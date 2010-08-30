package com.example.rss.service.impl.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.BatchSize;
import org.joda.time.DateTime;

@Entity
@Table(name="channel")
public class Channel {
	
	private Long id;
	
	private long version;
	
	private String title;
	
	private String link;
	
	private String description;
	
	private String language;
	
	private DateTime pubDate;
	
	private String docs;
	
	private String generator;
	
	private String managingEditor;
	
	private String webMaster;
	
	private List<Item> items = new ArrayList<Item>();

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)	
	public Long getId() {
		return id;
	}
	
	@SuppressWarnings("unused")
	private void setId(Long value) {
		id = value;
	}
	
	@Version
	@Column(name="v", nullable=false)
	public long getVersion() {
		return version;
	}
	
	@SuppressWarnings("unused")
	private void setVersion(long value) {
		version = value;
	}

	@Column(name="title")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name="link")
	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	@Column(name="description")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name="language")
	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	@Transient
	public DateTime getPubDate() {
		return pubDate;
	}

	public void setPubDate(DateTime pubDate) {
		this.pubDate = pubDate;
	}
	
	@Column(name="pub_date_millis")
	@SuppressWarnings("unused")
	private Long getPubDateMillis() {
		if(pubDate != null) {
			return pubDate.getMillis();
		} else {
			return null;
		}
	}
	
	@SuppressWarnings("unused")
	private void setPubDateMillis(Long value) {
		if(value != null) {
			pubDate = new DateTime(value);
		}
	}

	@Column(name="docs")
	public String getDocs() {
		return docs;
	}

	public void setDocs(String docs) {
		this.docs = docs;
	}

	@Column(name="generator")
	public String getGenerator() {
		return generator;
	}

	public void setGenerator(String generator) {
		this.generator = generator;
	}

	@Column(name="managing_editor")
	public String getManagingEditor() {
		return managingEditor;
	}

	public void setManagingEditor(String managingEditor) {
		this.managingEditor = managingEditor;
	}

	@Column(name="web_master")
	public String getWebMaster() {
		return webMaster;
	}

	public void setWebMaster(String webMaster) {
		this.webMaster = webMaster;
	}
	
	public void addItem(Item value) {
		if(value != null) {
			items.add(value);
		}
	}
	
	@Transient
	public List<Item> getItemsUnmodifiable() {
		return Collections.unmodifiableList(items);
	}
	
	@OneToMany(cascade=CascadeType.ALL)
	@BatchSize(size=50)
	@SuppressWarnings("unused")
	private List<Item> getItems() {
		return items;
	}
	
	@SuppressWarnings("unused")
	private void setItems(List<Item> value) {
		items = value;
	}
	
	@Override
	public String toString() {
		return new ToStringBuilder(this)
					.append("id",id)
					.append("version", version)
					.append("title", title)
					.append("link", link)
					.append("description", description)
					.append("language", language)
					.append("pubDate", pubDate)
					.append("docs", docs)
					.append("generator", generator)
					.append("managingEditor", managingEditor)
					.append("webMaster", webMaster)
					.toString();
	}
	
}
