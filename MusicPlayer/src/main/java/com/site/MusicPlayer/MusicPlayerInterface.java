package com.site.MusicPlayer;

public interface MusicPlayerInterface {
	void play();

	void stop();
	
	void setOutputDevice(OutputDeviceInterface outputDevice);
}