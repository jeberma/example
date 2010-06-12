package com.example.camel;

import java.util.Collection;
import java.util.Arrays;

public class RssRecipientList {
	
	public Collection<String> routeTo() {
		return Arrays.asList(
				"http://rss.cnn.com/rss/cnn_topstories.rss",
				"http://rss.cnn.com/rss/cnn_topstories.rss",
				"http://rss.cnn.com/rss/cnn_us.rss",
				"http://rss.cnn.com/rss/si_topstories.rss"
			);
	}

}
