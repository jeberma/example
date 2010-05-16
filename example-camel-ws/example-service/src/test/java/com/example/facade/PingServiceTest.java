
package com.example.facade;

import static org.junit.Assert.*;

import javax.xml.ws.Endpoint;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import org.example.api.ping.IPingService;

public class PingServiceTest {
	    
	private static final String ADDRESS = "http://localhost:9000/ws";
		
	private PingService service;
			    
	private IPingService client;
				    
	private Endpoint endpoint;
					    
	@Before
	public void setUp() {
		service = new PingService();
		endpoint = Endpoint.publish(ADDRESS, service);

		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		factory.setServiceClass(IPingService.class);
		factory.setAddress(ADDRESS);
		client = (IPingService)factory.create(); 
	}   
						    
	@After
	public void tearDown() {
		endpoint.stop();
	}   
							
	@Test
	public void ping() {
		assertEquals("hello", client.ping("hello"));
	}   
}
