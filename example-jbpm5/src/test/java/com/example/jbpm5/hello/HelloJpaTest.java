package com.example.jbpm5.hello;

import javax.naming.InitialContext;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.io.ResourceFactory;
import org.drools.persistence.jpa.JPAKnowledgeService;
import org.drools.runtime.Environment;
import org.drools.runtime.EnvironmentName;
import org.drools.runtime.StatefulKnowledgeSession;
import org.junit.Test;

import com.example.jbpm5.test.MockJndiContext;

public class HelloJpaTest {
	
	static {
		MockJndiContext.beActivated();
	}
	
	@Test
	public void hello() throws Exception {
		InitialContext ctx = new InitialContext();
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("org.jbpm.persistence.jpa");
		
		Environment env = KnowledgeBaseFactory.newEnvironment();
		env.set(EnvironmentName.ENTITY_MANAGER_FACTORY, emf);
		env.set(EnvironmentName.TRANSACTION_MANAGER, ctx.lookup("java:comp/TransactionManager"));
		
		KnowledgeBase kbase = readKnowledgeBase();
		
		StatefulKnowledgeSession ksession = 
			JPAKnowledgeService.newStatefulKnowledgeSession(kbase, null, env);
		
		ksession.startProcess("com.example.jbpm5.hello.HelloProcess");
		ksession.dispose();
	}
	
	private KnowledgeBase readKnowledgeBase() throws Exception {
		KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
		kbuilder.add(ResourceFactory.newClassPathResource("hello.bpmn"), ResourceType.BPMN2);
		return kbuilder.newKnowledgeBase();
	}

}
