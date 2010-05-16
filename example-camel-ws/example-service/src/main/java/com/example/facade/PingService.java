package com.example.facade;

import javax.jws.WebService;

import org.springframework.stereotype.Service;

@Service
public class PingService implements org.example.api.ping.IPingService {
	
	public String ping(String request) {
		return request;
	}
}
