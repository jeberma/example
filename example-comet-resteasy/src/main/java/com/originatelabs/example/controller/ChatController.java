package com.originatelabs.example.controller;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

@Path("/chat")
@Controller
public class ChatController {
	
	@GET
	@Produces(MediaType.TEXT_HTML)
	public ModelAndView index() {
		return new ModelAndView("chat/index");
	}

}
