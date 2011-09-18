package com.gmail.andreasmartinmoerch.danandchat.plugins;

import org.bukkit.plugin.Plugin;

import com.gmail.andreasmartinmoerch.danandchat.DanAndChat;
import com.sparkedia.valrix.ColorMe.ColorMe;

public class ExtensionManager {
	public ColorMe color;
	public boolean isUsingNaviaChar = false;
	private DanAndChat plugin;
	
	public ExtensionManager(DanAndChat plugin){
		this.plugin = plugin;
	}
	
	public void loadColorMe(){
		Plugin plugin;
		if ((plugin = DanAndChat.server.getPluginManager().getPlugin("ColorMe")) != null){
			color = (ColorMe)plugin;
		}
	}
	
	public boolean isUsingColorMe(){
		return color != null;
	}
	
	public void loadNaviaChar(){
		if (DanAndChat.server.getPluginManager().getPlugin("NaviaChar") != null){
			isUsingNaviaChar = true;
		}
	}
}
