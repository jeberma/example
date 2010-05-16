package com.example.jms;

import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.naming.InitialContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jms.core.JmsTemplate;

public class MessageProducer {
	
	private static final Log log = LogFactory.getLog(MessageProducer.class);
	
	private JmsTemplate jmsTemplate;
	
	private String destination;
	
	public void setJmsTemplate(JmsTemplate value) {
		jmsTemplate = value;
	}
	
	public void setDestination(String value) {
		destination = value;
	}
	public void send() {
		//jmsTemplate.convertAndSend(destination, "hello");
		log.info("Hello");
		//ping();
	}
	
	private void ping() {
		try {
			InitialContext ctx = new InitialContext();
			JmsTemplate template = new JmsTemplate();
			template.setConnectionFactory((ConnectionFactory)ctx.lookup("java:comp/env/jms/ConnectionFactory"));
			//template.setConnectionFactory(jmsTemplate.getConnectionFactory());
			//template.setDefaultDestinationName("jms/logTopic");
			template.setDefaultDestination((Destination)ctx.lookup("java:comp/env/jms/logTopic"));
			template.convertAndSend("BAR");
		} catch(Exception e) {
			throw new RuntimeException(e);
		}
	}

}
