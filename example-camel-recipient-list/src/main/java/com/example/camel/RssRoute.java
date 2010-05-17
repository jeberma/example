package com.example.camel;

import org.apache.camel.builder.RouteBuilder;

public class RssRoute extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		
		from("timer://rssjob?fixedRate=true&period=1000")
		.recipientList().method(RssRecipientList.class, "routeTo").parallelProcessing()
		.to("jms:feedQueue");
		
		from("jms:feedQueue?concurrentConsumers=10")
		.unmarshal().rss()
		.to("log:com.example.camel?maxChars=10");
	}
}
