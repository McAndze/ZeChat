package com.gmail.andreasmartinmoerch.danandchat.channel;

import com.gmail.andreasmartinmoerch.danandchat.Settings;

public class LocalChannel extends Channel{
	
	private int range;
	
	public LocalChannel(String name){
		super(name);
	}

	@Override
	public void loadFromConfig() {
		super.loadFromConfig();
		
		// Range
		this.setRange(Settings.channelsConfig.getInt(this.getName() + ".range", 30));
	}

	public int getRange() {
		return range;
	}

	public void setRange(int range) {
		this.range = range;
	}

}
