package com.example.rss.config.web.service;

import com.example.feature.config.api.IFeatureConfig;

public class RssFeatureConfig implements IFeatureConfig {

	public String getAdminUri() {
		return "/example-rss-config-webutil/app/rss";
	}

	public String getFeatureName() {
		return "Rss Feed";
	}

}
