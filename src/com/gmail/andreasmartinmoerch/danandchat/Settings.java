package com.gmail.andreasmartinmoerch.danandchat;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.util.config.Configuration;
import org.bukkit.util.config.ConfigurationNode;

import com.gmail.andreasmartinmoerch.danandchat.channel.Channel;
public class Settings {
	public static final String mainDirectory = "plugins" + File.separator + "DanAndChat" + File.separator;
	public static final String settingsDirectory = "Settings"  + File.separator;
	
	public static Configuration config;
		public static final String configDir = "plugins" + File.separator + "DanAndChat";
		public static final String configFile = "config.yml";
	public static Configuration channelsConfig;
		public static final String channelsDir = "plugins" + File.separator + "DanAndChat";
		public static final String channelsFile = "channels.yml";
	
	public static void initialize(){
		config = new Configuration(new File(configDir, configFile));
		config.load();
		
		channelsConfig = new Configuration(new File(channelsDir, channelsFile));
		channelsConfig.load();
	}
	
	public static List<Channel> getChannels(){
		List<Channel> channels = new ArrayList<Channel>();
		ConfigurationNode glNode;
		if ((glNode = channelsConfig.getNode("channels")) == null){
			channelsConfig.setProperty("channels", new HashMap<String, Object>());
		}
		
		for (String s: glNode.getKeys()){
			Channel c = new Channel(s);
			c.loadFromConfig();
			channels.add(c);
		}
		
//		for (String s: channelsConfig.getKeys("local-channels")){
//			LocalChannel l = new LocalChannel(s);
//			l.loadFromConfig();
//			channels.add(l);
//		}
		
		refresh(channelsConfig);
		return channels;
	}
	
	public static void refresh(Configuration c){
		c.save();
		c.load();
	}
}
