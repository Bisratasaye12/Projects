package com.site.MusicPlayer;

public class Mp3Player implements MusicPlayerInterface {
	private OutputDeviceInterface outputDevice;
	@Override
	public void play() {
		System.out.println("MP3 player playing");
	}

	@Override
	public void stop() {
		System.out.println("MP3 player stopped");
	}
	
	@Override
	public void setOutputDevice(OutputDeviceInterface
	outputDevice) {

	this.outputDevice = outputDevice;
	}
}