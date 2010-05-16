package com.example.jms;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.Session;
import javax.naming.InitialContext;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.net.JMSAppender;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@Component
public class Log4jConfigurer implements InitializingBean {

	public void afterPropertiesSet() throws Exception {
		System.out.println("AFTER PROPERTIES SET");
		//Logger.getRootLogger().setLevel(Level.INFO);
		//addConsoleAppender();
		//addJmsAppender();
		//ping();
	}
	
	private void addConsoleAppender() {
		ConsoleAppender appender = new ConsoleAppender();
		appender.setTarget(ConsoleAppender.SYSTEM_OUT);
		PatternLayout layout = new PatternLayout();
		layout.setConversionPattern("%d %-5p %c - %m%n");
		appender.setLayout(layout);
		appender.activateOptions();
		Logger.getRootLogger().addAppender(appender);
	}
	
	private void addJmsAppender() {
		JMSAppender appender = new JMSAppender();
		appender.setTopicConnectionFactoryBindingName("jms/ConnectionFactory");
		appender.setTopicBindingName("jms/logTopic");
		appender.setProviderURL("tcp://localhost:61616");
		//appender.setInitialContextFactoryName("org.mortbay.naming.InitialContextFactory");
		appender.activateOptions();
		Logger.getRootLogger().addAppender(appender);
	}
	
	private void ping() {
		try {
			InitialContext ctx = new InitialContext();
			ConnectionFactory cf = (ConnectionFactory)ctx.lookup("jms/ConnectionFactory");
			Connection conn = cf.createConnection();
			Session session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
			javax.jms.MessageProducer producer = session.createProducer((Destination)ctx.lookup("jms/logTopic"));
			producer.send(session.createTextMessage("FOO"));
		}catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
}
