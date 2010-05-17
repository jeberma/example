package com.example.camel;

import java.util.Arrays;
import java.util.Collection;

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
