package com.example.admin.web.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.feature.config.api.IFeatureConfig;

@RequestMapping("/features")
@Controller
public class FeaturesController {
	
	private static final Logger log = LoggerFactory.getLogger(FeaturesController.class);
	
	private List<IFeatureConfig> featureConfigs;
	
	@Required
	public void setFeatureConfigs(List<IFeatureConfig> value) {
		featureConfigs = value;
	}
	
	@RequestMapping
	public String index(Map<String,Object> model) {
		model.put("configs", featureConfigs);
		return "features/index";
	}

}
