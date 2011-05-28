package com.example.jbpm5.test;

import javax.naming.NamingException;

import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.mock.jndi.SimpleNamingContextBuilder;

import com.atomikos.icatch.jta.UserTransactionImp;
import com.atomikos.icatch.jta.UserTransactionManager;

public abstract class MockJndiContext {
	
	private static SimpleNamingContextBuilder builder = new SimpleNamingContextBuilder();
	
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
		DriverManagerDataSource  ds = new DriverManagerDataSource();
		ds.setDriverClassName("com.mysql.jdbc.Driver");
		ds.setUsername("root");
		ds.setPassword("");
		ds.setUrl("jdbc:mysql://localhost:3306/example_jbpm_db?createDatabaseIfNotExist=true&useUnicode=true&characterEncoding=utf-8");
		builder.bind("java:comp/env/jdbc/processInstanceDS", ds);
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
