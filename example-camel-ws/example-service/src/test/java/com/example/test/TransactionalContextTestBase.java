package com.example.test;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

/**
 * Base test class for running transactional unit tests using the 
 * spring application context.
 */
@ContextConfiguration(locations="/spring.xml")
public abstract class TransactionalContextTestBase 
	extends AbstractTransactionalJUnit4SpringContextTests {
	
	static {
		NamingContext.beActivated();
	}
}