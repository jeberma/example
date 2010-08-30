package com.example.rss.service.impl.domain;

import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.joda.time.DateTime;

@Entity
@Table(name="rss_feed")
public class RssFeed {
	
	private Long id;
	
	private long version;
	
	private String uuid = UUID.randomUUID().toString();
	
	private String url;
	
	private Channel channel;
	
	private DateTime createdDateTime = new DateTime();
	
	private DateTime updatedDateTime;
	
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

	@Column(nullable=false, unique=true)
	public String getUuid() {
		return uuid;
	}

	@SuppressWarnings("unused")
	private void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	@Column
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String value) {
		url = value;
	}
	
	@OneToOne(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	public Channel getChannel() {
		return channel;
	}

	public void setChannel(Channel channel) {
		this.channel = channel;
	}
	
	@Transient
	public DateTime getCreatedDateTime() {
		return createdDateTime;
	}
	
	public void setCreatedDateTime(DateTime value) {
		createdDateTime = value;
	}
	
	@Column(name="created_time_millis", nullable=false)
	@SuppressWarnings("unused")
	private long getCreatedTimeMillis() {
		return createdDateTime.getMillis();
	}
	
	@SuppressWarnings("unused")
	private void setCreatedTimeMillis(long value) {
		createdDateTime = new DateTime(value);
	}
	
	@Transient
	public DateTime getUpdatedDateTime() {
		return updatedDateTime;
	}
	
	public void setUpdatedDateTime(DateTime value) {
		updatedDateTime = value;
	}
	
	@Column(name="updated_time_millis")
	@SuppressWarnings("unused")
	private Long getUpdatedTimeMillis() {
		if(updatedDateTime != null) {
			return updatedDateTime.getMillis();
		} else {
			return null;
		}
	}
	
	@SuppressWarnings("unused")
	private void setUpdatedTimeMillis(Long value) {
		if(value != null) {
			updatedDateTime = new DateTime(value);
		}
	}
	
	@Override
	public String toString() {
		return new ToStringBuilder(this)
					.append("id", id)
					.append("uuid", uuid)
					.append("channel", channel)
					.append("createdDateTime", createdDateTime)
					.append("updatedDateTime", updatedDateTime)
					.toString();
	}
	
}
