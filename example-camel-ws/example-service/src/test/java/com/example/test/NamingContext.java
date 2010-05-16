package com.example.test;

import java.io.IOException;
import java.util.Properties;

import javax.naming.NamingException;

import org.springframework.core.io.ClassPathResource;
import org.springframework.mock.jndi.SimpleNamingContextBuilder;

import com.atomikos.icatch.jta.UserTransactionImp;
import com.atomikos.icatch.jta.UserTransactionManager;
import com.atomikos.jdbc.nonxa.NonXADataSourceBean;

/**
 * Binds test jndi configurations.
 */
public class NamingContext {
	
	private static SimpleNamingContextBuilder builder = new SimpleNamingContextBuilder();
	
	private NamingContext() {}
	
	/**
	 * Binds the test jndi configurations if not already bound.
	 */
	public synchronized static void beActivated() {
		if(isActivated()) {
			return;
		}
		bindDataSource();
		bindUserTransaction();
		bindTransactionManager();
		try {
			builder.activate();
		} catch(NamingException e) {
			throw new RuntimeException(e);
		}
	}
	
	private synchronized static boolean isActivated() {
		return SimpleNamingContextBuilder.getCurrentContextBuilder() != null;
	}
	
	private static void bindDataSource() {
		try {
			Properties props = new Properties();
			props.load(new ClassPathResource("jdbc.properties").getInputStream());
			NonXADataSourceBean  ds = new NonXADataSourceBean();
			ds.setDriverClassName(props.getProperty("jdbc.driverClassName"));
			ds.setUser(props.getProperty("jdbc.username"));
			ds.setPassword(props.getProperty("jdbc.password"));
			ds.setUrl(props.getProperty("jdbc.uri"));
			builder.bind("java:comp/env/jdbc/CHANGE-ME-DS", ds);
		} catch(IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	private static void bindUserTransaction() {
		builder.bind("java:comp/UserTransaction", new UserTransactionImp());
	}
	
	private static void bindTransactionManager() {
		UserTransactionManager tm = new UserTransactionManager();
		try {
			tm.init();
			builder.bind("java:comp/TransactionManager",tm);
		} catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
}