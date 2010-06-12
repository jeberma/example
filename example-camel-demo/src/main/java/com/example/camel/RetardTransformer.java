package com.example.camel;

public class RetardTransformer {
	
	public String transform(String body) {
		return body.replace("<rss ", "<rss xmlns:media=\"http://http://search.yahoo.com/mrss/\" ");
	}

}
