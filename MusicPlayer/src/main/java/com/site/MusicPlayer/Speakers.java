package com.site.MusicPlayer;

public class Speakers implements OutputDeviceInterface {
	@Override
	public void outputSound() {
		System.out.println("Speakers used");
	}

}