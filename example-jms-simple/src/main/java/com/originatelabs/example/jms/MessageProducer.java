package com.originatelabs.example.jms;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

public class MessageProducer {
	
	private JmsTemplate jmsTemplate;
	
	private String destinationName;
	
	public void setDestinationName(String value) {
		destinationName = value;
	}
	
	public void setJmsTemplate(JmsTemplate value) {
		jmsTemplate = value;
	}
	
	public void send() {
		jmsTemplate.send(destinationName, new MessageCreator(){
			public Message createMessage(Session session) throws JMSException {
				return session.createTextMessage("HELLO!");
			}
		});
	}

}
