package com.landsofnavia.naviachat.utils;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.ConfigurationSection;

import com.landsofnavia.naviachat.NaviaChat;
import com.landsofnavia.naviachat.channel.Channel;
public class Settings {
	private NaviaChat plugin;
	
	public Settings(NaviaChat plugin){
		this.plugin = plugin;
	}
		
	public void initialize(){
		
	}
	
	public MessageGetter getNewMessageGetter(){
		String prefix = null;
		MessageGetter msgGetter = new MessageGetter(plugin);
		msgGetter.writeDefaultMessagesToConfig(false);
		MessageGetter.debug = this.plugin.getConfig().getBoolean("plugin.debug", false);
		return msgGetter;
	}
	
	public void initializeConf(){
		
	}
	
	public List<Channel> getChannels(){
		List<Channel> channels = new ArrayList<Channel>();
		ConfigurationSection  node = plugin.getConfig().getConfigurationSection("channels");
//		List<String> node = plugin.getConfig().getStringList("channels");
		
		for (String s: node.getKeys(false)){
			Channel c = new Channel(s, this.plugin);
			c.initialize();
			channels.add(c);
		}
		return channels;
	}
}
