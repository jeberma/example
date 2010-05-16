package com.originatelabs.example.gwt.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.originatelabs.example.gwt.client.SampleRemoteService;

public class SampleRemoteServiceImpl extends RemoteServiceServlet 
	implements SampleRemoteService {

	public String doComplimentMe() {
		return "this is a compliment from the server side - you don't look too bad today";
	}
	
	
}
