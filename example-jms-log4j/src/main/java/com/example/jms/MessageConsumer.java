package com.example.jms;

import javax.jms.Message;
import javax.jms.TextMessage;

public class MessageConsumer {

	public void handleMessage(Message msg) {
		System.out.println(msg);
	}
	
	public void handleMessage(TextMessage msg) {
		System.out.println(msg);
	}
	
	public void handleMessage(String msg) {
		System.out.println("[Message Recieved] " + msg);
	}
	
	public void handleMessage(byte[] msg) {
		System.out.println(msg);
	}
	
}
