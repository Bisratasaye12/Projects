package com.site.messageApp;

public class EmailService implements MessageService {
	public void sendMessage(String message) {
		System.out.println("Sending an email with a message: " + message);
	}
}
