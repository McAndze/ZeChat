package com.landsofnavia.naviachat.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.World;
import org.bukkit.util.config.Configuration;
import org.bukkit.util.config.ConfigurationNode;

import com.landsofnavia.naviachat.NaviaChat;
import com.landsofnavia.naviachat.channel.Channel;
import com.landsofnavia.naviachat.commands.Commands;
public class Settings {
	public static final String mainDirectory = "plugins" + File.separator + "DanAndChat" + File.separator;
	public static final String settingsDirectory = "Settings"  + File.separator;
	
	public Configuration config;
		public final String configDir = "plugins" + File.separator + "DanAndChat";
		public final String configFile = "config.yml";
	public Configuration channelsConfig;
		public final String channelsDir = "plugins" + File.separator + "DanAndChat";
		public final String channelsFile = "channels.yml";
	public Configuration prefixConfig;
		public final String databaseConfigDir = configDir;
		public final String databaseFile = "prefixes.yml";
	public Configuration messageConfig;
		public final String messageConfigDir = configDir + File.separator + "advanced";
		public final String messageFile = "messages.yml";
			
	private NaviaChat plugin;
	
	public Settings(NaviaChat plugin){
		this.plugin = plugin;
	}
		
	public void initialize(){
		// Check if data folder exists, and creates it if not.
		if (!this.plugin.getDataFolder().exists()){
			this.plugin.getDataFolder().mkdirs();
		}
		
		// Checks if 
		File f = new File(messageConfigDir);
		if (!f.exists()){
			f.mkdirs();
		}
		
		File fConfig = new File(configDir, configFile);		
		File fChannelsConfig = new File(channelsDir, channelsFile);
		File fPrefixConfig = new File(databaseConfigDir, databaseFile);
		File fMessageConfig = new File(messageConfigDir, messageFile);
		
		try {
			if (!fConfig.exists()){
				fConfig.createNewFile();
			}
			
			if (!fChannelsConfig.exists()){
				fChannelsConfig.createNewFile();
			}
			
			if (!fPrefixConfig.exists()){
				fPrefixConfig.createNewFile();
			}
			if(!fMessageConfig.exists()){
				fMessageConfig.createNewFile();
			}
		} catch (IOException e){
			e.printStackTrace();
		}
		
		config = new Configuration(fConfig);
		config.load();
		this.plugin.getCommandManager().initialize();
		
		channelsConfig = new Configuration(fChannelsConfig);
		channelsConfig.load();
		
		prefixConfig = new Configuration(fPrefixConfig);
		prefixConfig.load();
		
		messageConfig = new Configuration(fMessageConfig);
		messageConfig.load();
		
		if (!config.getBoolean("plugin" +"."+ "initialized", false)){
			initializeConf();
		}
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
		/*
		 * Set default properties for commands.
		 */
		config.setProperty("commands" +"."+ Commands.CH.toString().toLowerCase() +"."+ "enabled" , true);
		config.setProperty("commands" +"."+ Commands.CHANNEL.toString().toLowerCase() +"."+ "enabled" , true);
		config.setProperty("commands" +"."+ Commands.LEAVECHANNEL.toString().toLowerCase() +"."+ "enabled" , true);
		config.setProperty("commands" +"."+ Commands.ME.toString().toLowerCase() +"."+ "enabled" , true);
		config.setProperty("commands" +"."+ Commands.T.toString().toLowerCase() +"."+ "enabled" , true);
		
		/*
		 * Set properties for messages. (In config)
		 */
		config.setProperty("messages" + "." + "notify-on-join", false);
		config.setProperty("messages" + "." + "notify-on-leave", true);
		config.setProperty("messages" + "." + "notify-on-focus", true);
		config.setProperty("messages" + "." + "notify-on-unfocus", false);
		
		List<String> worlds = new ArrayList<String>();
		for (World w: this.plugin.getServer().getWorlds()){
			worlds.add(w.getName());
		}
		this.channelsConfig.setProperty("channels.Global.shortcut", "g");
		this.channelsConfig.setProperty("channels.Global.range", -1);
		this.channelsConfig.setProperty("channels.Global.auto-join", true);
		this.channelsConfig.setProperty("channels.Global.auto-focus", true);
		this.channelsConfig.setProperty("channels.Global.formatting", "&f[&2g&f]<&PLAYER.PREFIX&PLAYER.NAME&f>: &MESSAGE");
		
		List<String> list1 = new ArrayList<String>();
		list1.add(null);
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		map.put("inChannel", list1);
		List<Map<String, List<String>>> list2 = new ArrayList<Map<String, List<String>>>();
		list2.add(map);
		
		this.channelsConfig.setProperty("channels.Global.filters.onWrite.include", list2);
		
		this.channelsConfig.setProperty("channels.local.shortcut", "l");
		this.channelsConfig.setProperty("channels.local.range", 100);
		this.channelsConfig.setProperty("channels.local.auto-join", true);
		this.channelsConfig.setProperty("channels.local.auto-focus", false);
		this.channelsConfig.setProperty("channels.local.formatting", "&PLAYER.DISPLAYNAME: &MESSAGE");
		this.channelsConfig.setProperty("channels.local.filters.onWrite.include", list2);
		
		this.channelsConfig.save();
		this.channelsConfig.load();
		this.config.setProperty("plugin" + "." + "initialized", true);
		config.save();
		config.load();
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
