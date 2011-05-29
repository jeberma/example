package com.example.jbpm5.spring;

import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.io.ResourceFactory;
import org.drools.runtime.StatefulKnowledgeSession;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;

public class StatefulKnowledgeSessionFactoryBean 
	implements FactoryBean<StatefulKnowledgeSession>, DisposableBean {
	
	private StatefulKnowledgeSession ksession;
	
	public StatefulKnowledgeSession getObject() throws Exception {
		KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
		kbuilder.add(ResourceFactory.newClassPathResource("hello.bpmn"), ResourceType.BPMN2);
		kbuilder.add(ResourceFactory.newClassPathResource("spring.bpmn"), ResourceType.BPMN2);  	 
		ksession = kbuilder.newKnowledgeBase().newStatefulKnowledgeSession();
		return ksession;
	}

	public Class<?> getObjectType() {
		return StatefulKnowledgeSession.class;
	}

	public boolean isSingleton() {
		return true;
	}
	
	public void destroy() throws Exception {
		if(ksession != null) {
			ksession.dispose();
		}
	}
}
