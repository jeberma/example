package com.originatelabs.example.comet;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.annotations.Suspend;
import org.jboss.resteasy.spi.AsynchronousResponse;

@Path("/chat")
public class ChatResource {
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public void index(
			final @QueryParam("q") String q,
			final @Suspend(1000) AsynchronousResponse response) {
		
		new Thread(new Runnable(){
			public void run() {
				response.setResponse(
						Response.ok(q != null ? q : "hello")
						.type(MediaType.TEXT_PLAIN)
						.build()
				);
			}
		}).start();
	}
}
