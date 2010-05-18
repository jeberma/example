package com.example.camel;

import java.util.UUID;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.properties.PropertiesComponent;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Main {
	
	private static final Log log = LogFactory.getLog(Main.class);
	
	public static void main(String... args) throws Exception {
		CamelContext context = new DefaultCamelContext();
		
		PropertiesComponent pc = new PropertiesComponent();
		pc.setLocation("classpath:environment.properties");
		
		context.addComponent("properties", pc);
		
		context.addRoutes(new RouteBuilder(){
			@Override
			public void configure() {
				// tails a log file and puts each line on a log queue
				from("stream:file?fileName={{log.file}}&scanStream=true&scanStreamDelay=1000")
				.to("seda:logQueue");
				
				from("seda:logQueue")
				.transform().method(UpperCaseTransformer.class, "transform")
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
