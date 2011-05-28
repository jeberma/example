package com.example;

import org.example.api.ping.IPingService;
import org.example.api.ping.PingRequest;
import org.example.api.ping.PingResponse;

public class PingServiceSoap implements IPingService {

	@Override
	public PingResponse ping(PingRequest body) {
		PingResponse response = new PingResponse();
		response.setMsg(body.getMsg());
		return response;
	}
	
	

}
