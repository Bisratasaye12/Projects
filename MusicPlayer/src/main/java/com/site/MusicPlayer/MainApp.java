package com.site.MusicPlayer;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class MainApp {
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("beansConfig.xml");
		MusicPlayerInterface cdPlayer = (MusicPlayerInterface) context.getBean("cdPlayer");
		cdPlayer.play();
		cdPlayer.stop();

	}
}