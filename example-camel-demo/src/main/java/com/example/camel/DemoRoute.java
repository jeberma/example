package com.example.camel;

import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.broker.BrokerService;
import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.impl.DefaultCamelContext;

public class DemoRoute  {
	
	public static void main(String... args) throws Exception {
		BrokerService broker = new BrokerService();
		broker.addConnector("vm://localhost");
		broker.start();
		
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("vm://localhost");
		
		CamelContext context = new DefaultCamelContext();
		context.addComponent("jms", JmsComponent.jmsComponentAutoAcknowledge(connectionFactory));
		
		context.addRoutes(new RouteBuilder(){
			public void configure() throws Exception {
				
				from("quartz:rssJob?cron=* * * 156")
					.recipientList().method(RssRecipientList.class, "routeTo").parallelProcessing()
					.to("jms:rssQueue");
				
				from("jms:rssQueue?concurrentConsumers=10")
					.unmarshal().rss()
					.to("log:com.example.camel?maxChars=50");
				
			}
		});
		context.start();
	}

}
