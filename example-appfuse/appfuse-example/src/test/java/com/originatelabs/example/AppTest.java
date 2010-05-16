package com.originatelabs.example;

import junit.framework.TestCase;

public class AppTest extends TestCase {
    public void testGetHello() throws Exception {
        assertEquals("Hello", App.getHello());
    }
}
