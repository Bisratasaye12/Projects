package com.site.messageApp;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MessagingApp {
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("message-config.xml");
		MessageService messageService = (SMSService) context.getBean("sms-service", MessageService.class);
		messageService.sendMessage("The message to be sent");
	}
}