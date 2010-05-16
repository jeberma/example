package com.example.jms;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.Session;
import javax.naming.InitialContext;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.spi.LoggingEvent;
import org.apache.log4j.xml.XMLLayout;

public class JmsAppender extends AppenderSkeleton {
	
	private volatile String destinationBindingName;
	
	private volatile String connectionFactoryBindingName;
	
	private volatile Connection connection;
	
	private volatile Session session;
	
	private volatile javax.jms.MessageProducer producer;
	
	public JmsAppender() {
		super();
		setLayout(new XMLLayout());
	}
	
	public void setDestinationBindingName(String value) {
		destinationBindingName = value;
	}
	
	public void setConnectionFactoryBindingName(String value) {
		connectionFactoryBindingName = value;
	}
	
	@Override
	protected void append(LoggingEvent event) {
		if(session == null || producer == null) {
			initialize();
		}
		try {
			producer.send(session.createTextMessage(getLayout().format(event)));
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void initialize() {
		try {
			InitialContext ctx = new InitialContext();
			ConnectionFactory connectionFactory =(ConnectionFactory)ctx.lookup(connectionFactoryBindingName);
			connection = connectionFactory.createConnection();
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			Destination destination = (Destination) ctx.lookup(destinationBindingName);
			producer = session.createProducer(destination);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public synchronized void close() {
		if(closed) {
			return;
		}
		try {
			if(producer != null) {
				producer.close();
			}
			if(session != null) {
				session.close();
			}
			if(connection != null) {
				connection.close();
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			closed = true;
		}
	}

	@Override
	public boolean requiresLayout() {
		return true;
	}
}
