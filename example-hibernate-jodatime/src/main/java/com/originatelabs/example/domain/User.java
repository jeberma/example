package com.originatelabs.example.domain;

import java.util.Calendar;
import java.util.TimeZone;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.Columns;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

@Entity
public class User {

	private Long id;
	
	private DateTime ts = new DateTime(DateTimeZone.UTC);
	
	private DateTime ts2 = new DateTime(DateTimeZone.UTC);
	
	private Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}
	
	public void setId(Long value) {
		id = value;
	}
	
	@Columns(columns={@Column(name="ts"),@Column(name="tszone")})
	@Type(type="org.joda.time.contrib.hibernate.PersistentDateTimeTZ")
	public DateTime getTs() {
		return ts;
	}
	public void setTs(DateTime value) {
		ts = value;
	}
	
	@Column
	@Type(type="org.joda.time.contrib.hibernate.PersistentDateTime")
	public DateTime getTs2() {
		return ts2;
	}
	public void setTs2(DateTime value) {
		ts2 = value;
	}
	
	@Column
	@Type(type="com.originatelabs.example.domain.HibernateUTC$CalendarType")
	public Calendar getCal() {
		return cal;
	}
	public void setCal(Calendar value) {
		cal = value;
	}
}
