package com.example.jbpm5.hello;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.drools.runtime.StatefulKnowledgeSession;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

@ContextConfiguration(locations="/spring.xml")
public class HelloSpringTest extends AbstractJUnit4SpringContextTests{
	
	@Autowired
	private StatefulKnowledgeSession ksession;
	
	@Test
	public void hello() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", UUID.randomUUID().toString());
		ksession.startProcess("com.example.jbpm5.hello.SpringProcess", params);
	}

}
