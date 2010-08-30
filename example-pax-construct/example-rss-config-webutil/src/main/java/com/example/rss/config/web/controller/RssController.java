package com.example.rss.config.web.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.rss.service.api.IRssService;
import com.example.rss.service.api.RssFeedType;

@Controller
@RequestMapping("/rss")
public class RssController {

	private IRssService rssService;
	
	@Required
	public void setRssService(IRssService value) {
		rssService = value;
	}
	
	@RequestMapping(value={"","/"}, method=RequestMethod.GET)
	public String index(Map<String, Object> model) {
		List<RssFeedType> feeds = rssService.getAllRssFeeds();
		model.put("feeds", feeds);
		return "rss/index";
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public String create() {
		return "rss/create";
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public String doCreate(@RequestParam("url") String url) {
		rssService.createRssFeed(url);
		return "redirect:/app/rss";
	}
}
