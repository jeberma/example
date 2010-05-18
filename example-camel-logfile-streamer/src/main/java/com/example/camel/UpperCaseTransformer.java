package com.example.camel;

public class UpperCaseTransformer {
	
	public String transform(String body) {
		if(body == null) {
			return null;
		}
		return body.toUpperCase();
	}

}
