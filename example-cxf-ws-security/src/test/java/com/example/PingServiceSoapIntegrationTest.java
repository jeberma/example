package com.example;

import javax.xml.namespace.QName;

import org.example.api.ping.IPingService;
import org.example.api.ping.PingRequest;
import org.example.api.ping.PingResponse;
import org.example.api.ping.PingService;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

@ContextConfiguration(locations="/cxf-client.xml")
public class PingServiceSoapIntegrationTest extends AbstractJUnit4SpringContextTests {
	
	private static final QName SERVICE_NAME = new QName("http://api.example.org/ping", "PingService");
	
	private static final QName PORT_NAME = new QName("http://api.example.org/ping", "PingPort");
	
	@Test
	public void ping() throws Exception{
		
		IPingService pingService = new PingService(getClass().getResource("/ping.wsdl"), SERVICE_NAME)
										.getPort(PORT_NAME, IPingService.class);
		
		PingRequest request = new PingRequest();
		request.setMsg("hello");
		
		PingResponse response = pingService.ping(request);
		System.out.println(response.getMsg());
	}

}
