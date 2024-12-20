package com.site.MusicPlayer;

public class Headphones implements OutputDeviceInterface {
	@Override
	public void outputSound() {
		System.out.println("Headphones used");
	}
}