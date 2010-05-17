package com.example.camel;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.builder.xml.Namespaces;

public class RssRoute extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		
		from("timer://rssjob?fixedRate=true&period=1000")
		.recipientList().method(RssRecipientList.class, "routeTo").parallelProcessing()
		.to("jms:feedQueue");
		
		from("jms:feedQueue?concurrentConsumers=10")
		.unmarshal().rss()
		.to("log:com.example.camel?maxChars=10");
	
		// youtube example with namespaces
		/*
		Namespaces ns = new Namespaces("feed", "http://www.w3.org/2005/Atom");
		from("http://gdata.youtube.com/feeds/api/users/975y/favorites")
			.split().xpath("/feed:feed/feed:entry", ns).parallelProcessing()
			.convertBodyTo(String.class)
			.to("log:youtube?maxChars=200");
		*/
	}
}
