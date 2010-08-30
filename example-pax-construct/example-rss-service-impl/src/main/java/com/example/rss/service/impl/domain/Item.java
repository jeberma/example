package com.example.rss.service.impl.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.joda.time.DateTime;

@Entity
@Table(name="item")
public class Item {
	
	private Long id;
	
	private String title;
	
	private String link;
	
	private String description;
	
	private DateTime pubDate;
	
	private String guid;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}
	
	@SuppressWarnings("unused")
	private void setId(Long value) {
		id = value;
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

	@Column(name="guid")
	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}
	
	@Override
	public String toString() {
		return new ToStringBuilder(this)
					.append("id", id)
					.append("title", title)
					.append("link", link)
					.append("description", description)
					.append("pubDate", pubDate)
					.append("guid", guid)
					.toString();
	}
}
