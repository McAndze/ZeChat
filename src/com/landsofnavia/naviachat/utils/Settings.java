package com.landsofnavia.naviachat.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.World;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.file.FileConfiguration;

import com.landsofnavia.naviachat.NaviaChat;
import com.landsofnavia.naviachat.channel.Channel;
import com.landsofnavia.naviachat.commands.Commands;
public class Settings {
	private NaviaChat plugin;
	
	public Settings(NaviaChat plugin){
		this.plugin = plugin;
	}
		
	public void initialize(){
		
	}
	
	public MessageGetter getNewMessageGetter(){
		String prefix = null;
		MessageGetter msgGetter;
		if ((prefix = this.config.getString("plugin.message-set", null)) == null){
			msgGetter = new MessageGetter(this.plugin, this.messageConfig);
		} else { msgGetter = new MessageGetter(this.plugin, this.messageConfig, prefix); }
		MessageGetter.writeDefaultMessagesToConfig(this.messageConfig, false);
		MessageGetter.debug = this.config.getBoolean("plugin.debug", false);
		return msgGetter;
	}
	
	public void initializeConf(){
		
	}
	
	public List<Channel> getChannels(){
		List<Channel> channels = new ArrayList<Channel>();
		ConfigurationNode glNode = this.channelsConfig.getNode("channels");
		
		for (String s: glNode.getKeys()){
			Channel c = new Channel(s, this.plugin);
			c.initialize();
			channels.add(c);
		}
		
		refresh(channelsConfig);
		return channels;
	}
	
	public void refresh(Configuration c){
		c.save();
		c.load();
	}
}
