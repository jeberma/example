package com.example;

import org.osgi.framework.Bundle;
import org.springframework.osgi.test.AbstractConfigurableBundleCreatorTests;

public class SpringDmSampleTest extends AbstractConfigurableBundleCreatorTests {
	
	public void testIntegration() {
		boolean started = false;
		for(Bundle each : bundleContext.getBundles()) {
			if("com.example.springdm".equals(each.getSymbolicName())
					&& each.getState() == Bundle.ACTIVE) {
						started = true;
						break;
			}
		}
		assertTrue("bundle is not installed or active", started);
	}
	
	@Override
	protected String[] getTestBundlesNames() {
		return new String[]{"com.example, example-springdm, 0.0.1-SNAPSHOT"};
	}
}