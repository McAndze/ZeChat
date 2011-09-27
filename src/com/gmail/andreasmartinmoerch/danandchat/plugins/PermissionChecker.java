package com.gmail.andreasmartinmoerch.danandchat.plugins;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

import com.gmail.andreasmartinmoerch.danandchat.DanAndChat;
import com.gmail.andreasmartinmoerch.danandchat.channel.Channel;

public class PermissionChecker {
	/**
	 * Permission nodes
	 */
	
	public List<Player> init = new ArrayList<Player>();
	
	public static final String all = ".*";

	
	public static final String prefix = "danandchat";
		public static final String channel = ".channel";
			public static final String ban = ".ban";
			public static final String unban = ".unban";
			public static final String list = ".list";
				public static final String in = ".in";
				public static final String available = ".available";
		public static final String leaveChannel = ".leavechannel";
		public static final String canTalk = ".cantalk";
		public static final String tell = ".tell";
		public static final String me = ".me";
		public static final String changeChannel = ".changechannel";
		public static final String defaultChannel = ".defaultchannel";
		public static final String focusedChannel = ".focusedchannel";
	
	private DanAndChat plugin;
	//TODO: Write one function that covers all of these functions instead. (Move permission nodes to the CommandHandler, maybe)	
		
	public PermissionChecker(DanAndChat plugin){
		this.plugin = plugin;
		for (Player p: plugin.getServer().getOnlinePlayers()){
			initializePlayer(p);
		}
	}
	
	/**
	 * Initalizes a Player's channels.
	 * @param p Player to initialize.
	 */
	public void initializePlayer(Player p){
		List<Channel> channels = this.plugin.getChannels().channels;
 		for (Channel c: channels){
 			if (c.isAutoJoin()){
 				c.addPlayer(p);
 			}
 			if(c.isAutoFocus() && !c.getBanned().contains(p.getName())){
 				c.addPlayer(p);
 				c.getFocused().add(p);
 			}
 		}
 		if (!this.plugin.getChannels().playerHasFocusedChannel(p)){
 			Channel c = channels.get(0);
 			if (!c.getBanned().contains(p.getName()))
 			{
 	 			c.addPlayer(p);
 	 			c.getFocused().add(p);
 			}
 		}
 		init.add(p);
	}
}
