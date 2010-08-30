package com.example.test;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

@ContextConfiguration(locations="/spring.xml")
public class AbstractContextTest extends AbstractJUnit4SpringContextTests {
	
	static {
		NamingContext.activate();
	}

}
