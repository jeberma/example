package com.involver.demo;

import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.broker.BrokerService;
import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.impl.DefaultCamelContext;

import com.example.camel.RetardTransformer;

public class Main {
	
	public static void main(String... args) throws Exception {
		
		CamelContext context = new DefaultCamelContext();
		context.addRoutes(new RouteBuilder(){
			public void configure() throws Exception {
				
				from("timer://rssjob?fixedRate=true&period=1000")
				.to("http://www.cardplayer.com/cptv.rss")
				.bean(RetardTransformer.class, "transform")
				.to("log:com.example.camel?maxChars=50");
				
				
			}
		});
		context.start();
	}

}
