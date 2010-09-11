package com.example.osgi.jta.widget.service;

import javax.transaction.TransactionManager;

public class WidgetService {
	
	private TransactionManager transactionManager;
	
	public void setTransactionManager(TransactionManager value) {
		System.out.println("TRANSACTION MANAGER SET");
		transactionManager = value;
	}
}