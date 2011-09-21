package com.gmail.andreasmartinmoerch.danandchat.plugins;

import me.samkio.RPGWorld.RPGWorldPlugin;

import org.bukkit.plugin.Plugin;

import com.gmail.andreasmartinmoerch.danandchat.DanAndChat;
import com.gmail.andreasmartinmoerch.danandchat.DanAndChatRPG;
import com.sparkedia.valrix.ColorMe.ColorMe;

public class ExtensionManager {
	public RPGWorldPlugin rpgplugin = null;
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
	
	public boolean usesRPGWorld(){
		return this.rpgplugin != null;
	}
	
	public void initRPGWorld(){
		Plugin testPlugin;
		if ((testPlugin = this.plugin.getServer().getPluginManager().getPlugin("RPGWorld")) != null){
			rpgplugin = (RPGWorldPlugin)testPlugin;
			this.plugin.log.info("[DanAndChat] Found RPGWorld! Hooking in!");
		}
		if (this.usesRPGWorld()){
			this.rpgplugin.chatPlugins.hookPlugin(new DanAndChatRPG(plugin));
		}
	}}
