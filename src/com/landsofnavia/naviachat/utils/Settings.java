package com.landsofnavia.naviachat.utils;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.ConfigurationSection;

import com.landsofnavia.naviachat.NaviaChat;
import com.landsofnavia.naviachat.channel.Channel;
import com.landsofnavia.naviachat.channel.NaviaLogger;
public class Settings {
	private NaviaChat plugin;
	
	public Settings(NaviaChat plugin){
		this.plugin = plugin;
	}
		
	public void initialize(){
		plugin.saveDefaultConfig();
	}
	
	public MessageGetter getNewMessageGetter(){
//		String prefix = null;
		MessageGetter msgGetter = new MessageGetter(plugin);
		msgGetter.writeDefaultMessagesToConfig(false);
		MessageGetter.debug = this.plugin.getConfig().getBoolean("plugin.debug", false);
		return msgGetter;
	}
	
	public List<Channel> getChannels(){
		List<Channel> channels = new ArrayList<Channel>();
		ConfigurationSection  node = plugin.getConfig().getConfigurationSection("channels");
		
		// debug
		NaviaLogger.log.info(node.getKeys(false).toString());
		
		for (String s: node.getKeys(false)){
			Channel c = new Channel(s, this.plugin);
			c.initialize();
			channels.add(c);
		}
		return channels;
	}
}
