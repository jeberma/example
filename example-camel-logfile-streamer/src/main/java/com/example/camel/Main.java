package com.example.camel;

import java.util.Properties;
import java.util.UUID;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Main {
	
	private static final Log log = LogFactory.getLog(Main.class);
	
	public static void main(String... args) throws Exception {
		final Properties props = new Properties();
		props.load(Main.class.getResourceAsStream("/environment.properties"));
		
		CamelContext context = new DefaultCamelContext();
		context.addRoutes(new RouteBuilder(){
			@Override
			public void configure() {
				// tails a log file and puts each line on a log queue
				from("stream:file?fileName=" + props.getProperty("log.file") +"&scanStream=true&scanStreamDelay=1000")
				.to("seda:logQueue");
				
				from("seda:logQueue")
				.split(body().tokenize(" "))
				.to("stream:out");
			}
		});
		context.start();
		
		while(true) {
			log.info("something random " + UUID.randomUUID().toString());
			Thread.sleep(1000);
		}
	}

}
