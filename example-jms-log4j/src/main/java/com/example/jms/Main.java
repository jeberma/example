package com.example.jms;

import javax.jms.Connection;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQObjectMessage;
import org.apache.log4j.spi.LoggingEvent;

public class Main implements MessageListener {
	
	public Main() throws Exception {
		ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("tcp://localhost:61616");
		Connection conn = factory.createConnection();
		Session sess = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
		conn.start();
		javax.jms.MessageConsumer consumer = sess.createConsumer(sess.createTopic("jms/logTopic"));
		consumer.setMessageListener(this);
		// log a message
		//Logger log = Logger.getLogger(getClass());
		//log.info("Test log");
		// clean up
		//Thread.sleep(1000);
		//consumer.close();
		//sess.close();
		//conn.close();
		
	}
	
	public static void main(String... args) throws Exception {
		new Main();
	}

	public void onMessage(Message msg) {
		System.out.println("ON MESSAGE");
		try {
			LoggingEvent event = (LoggingEvent)((ActiveMQObjectMessage)msg).getObject();
			System.out.println("Received log [" + event.getLevel() + "]: "+ event.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
}
