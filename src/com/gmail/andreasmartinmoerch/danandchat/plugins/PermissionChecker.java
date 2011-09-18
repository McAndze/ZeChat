package com.gmail.andreasmartinmoerch.danandchat.plugins;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.StringTokenizer;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;

import com.gmail.andreasmartinmoerch.danandchat.ChannelManager;
import com.gmail.andreasmartinmoerch.danandchat.DanAndChat;
import com.gmail.andreasmartinmoerch.danandchat.DanAndChatPlayerListener;
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
	
	public void initializePlayer(Player p){
		List<Channel> channels = this.plugin.channels.channels;
 		for (Channel c: channels){
 			if (c.isAutoJoin()){
 				c.addPlayer(p);
 			}
 			if(c.isAutoFocus() && !c.getBanned().contains(p.getName())){
 				c.addPlayer(p);
 				c.getFocused().add(p);;
 			}
 		}
 		if (!this.plugin.channels.playerHasFocusedChannel(p)){
 			Channel c = channels.get(0);
 			if (!c.getBanned().contains(p.getName()))
 			{
 	 			c.addPlayer(p);
 	 			c.getFocused();
 			}
 		}
 		init.add(p);
	}
	
	public static String getGroup(Player player){
//		return ExtensionManager.permissions.getGroup(player.getWorld().getName(), player.getName());
		return "";
	}
	
	public static String getPrefix(Player player){
//		if (!ExtensionManager.isUsingPermissions()){
//			return "";
//		}
//		return ExtensionManager.permissions.getGroup(player.getWorld().getName(), player.getName());
		return "";
	}
}
