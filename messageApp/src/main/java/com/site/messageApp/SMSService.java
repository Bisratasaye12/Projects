package com.site.messageApp;

public class SMSService implements MessageService {
	public void sendMessage(String message) {
		System.out.println("Sending an SMS with a message: " + message);
	}
}
