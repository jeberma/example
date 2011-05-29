package com.example.jbpm5.hello;

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
		//ksession.startProcess("com.example.jbpm5.hello.HelloProcess");
		ksession.startProcess("com.example.jbpm5.hello.SpringProcess");
	}

}
