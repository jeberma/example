package com.example.test;

import java.io.File;
import java.net.URISyntaxException;

import javax.naming.NamingException;

import org.apache.jackrabbit.core.TransientRepository;
import org.springframework.mock.jndi.SimpleNamingContextBuilder;

/**
 * Binds test jndi configurations
 */
public class NamingContext {

	private static SimpleNamingContextBuilder builder = new SimpleNamingContextBuilder();
	
	private static TransientRepository repository;
	
	public synchronized static void activate() {
		if(isActivated()) {
			return;
		}
		bindRepository();
		try {
			builder.activate();
		} catch(NamingException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static TransientRepository getRepository() {
		return repository;
	}
	
	private synchronized static boolean isActivated() {
		return SimpleNamingContextBuilder.getCurrentContextBuilder() != null;
	}
	
	private static void bindRepository() {
		try {
			repository = new TransientRepository(
					new File(NamingContext.class.getResource("/repository.xml").toURI()),
					new File("target")
			);
			builder.bind("java:comp/env/jcr/repository", repository);
		} catch(URISyntaxException e) {
			throw new RuntimeException(e);
		}
	}
}
