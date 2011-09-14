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
		for (Player p: plugin.server.getOnlinePlayers()){
			initializePlayer(p);
		}
	}
	
	public void initializePlayer(Player p){
		// REWRITING: 
		List<Channel> channels = ChannelManager.channels;
 		for (Channel c: channels){
 			if (c.isAutoJoin()){
 				c.addPlayer(p);
 			}
 			if(c.isAutoFocus() && !c.getBanned().contains(p.getName())){
 				c.addPlayer(p);
 				ChannelManager.playerFocused.put(p, c);
 			}
 		}
 		if (!ChannelManager.playerFocused.containsKey(p)){
 			Channel c = channels.get(0);
 			if (!c.getBanned().contains(p.getName()))
 			{
 	 			c.addPlayer(p);
 	 			ChannelManager.playerFocused.put(p, c);
 			}
 		}
 		init.add(p);
		
//		boolean success = false;
//		List<Channel> channels = ChannelManager.channels;
//		for (Channel c:  channels){
//			if (p.hasPermission(prefix + changeChannel + "." + c.getShortCut())){
//				c.addPlayer(p);
//			}
//			if (p.hasPermission(prefix + focusedChannel + "." + c.getShortCut())){
//				if (!c.playerIsInChannel(p)){
//					c.addPlayer(p);
//				}
//				ChannelManager.playerFocused.put(p, c);
//			}
//		}
//		if (ChannelManager.playerFocused.containsKey(p)){
//			success = true;
//		}
//		if (!success){
//			p.sendMessage(ChatColor.RED + "Chat initialization failed. Please speak to the server admin about this issue.  You may not be able to talk, or have to join a channel manually. Try \"/channel list available\" to see what channels you can join.");
//			this.init.remove(p);
//			return;
//		}
//		this.init.add(p);
//	    ArrayList<String> perms = new ArrayList<String>();
//	    perms.add("danandchat.defaultchannel.g");
//	    String focusedPerm = "danandchat.focusedchannel.g";
//	    for (PermissionAttachmentInfo perm : p.getEffectivePermissions())
//	    {
//	      String s;
//	      if ((s = perm.getPermission()).startsWith("danandchat.defaultchannel")) {
//	        perms.add(s);
//	      }
//	      if (perm.getPermission().startsWith("danandchat.focusedchannel")) {
//	        focusedPerm = perm.getPermission();
//	      }
//	    }
//	    for (String s : perms) {
//	      StringTokenizer strTok = new StringTokenizer(s, ".");
//	      for (int i = 0; strTok.hasMoreElements() || i == 2; i++){
//	    	  strTok.nextToken();
//	      }
//	      for (Channel c : ChannelManager.channels) {
//	    	 if (strTok.hasMoreTokens()){
//		        if (strTok.nextToken().equalsIgnoreCase(c.getShortCut())) {
//		          c.addPlayer(p);
//		        }
//	    	 }
//	      p.sendMessage(focusedPerm);
//		  StringTokenizer strTok2 = new StringTokenizer(focusedPerm, ".");
//		  for (int i = 0; strTok2.hasMoreElements() || i == 2; i++){
//			  p.sendMessage(strTok.nextToken());
//		  }
//	      if (strTok2.hasMoreTokens()){
//		      if (c.getShortCut().equalsIgnoreCase(strTok2.nextToken()))
//		          ChannelManager.playerFocused.put(p, c);
//	      }
//	      }
//	    }
	}
	
	//TODO: Still trying to think of a better way of doing this.
	public boolean playerHasPermission(Player player, String perm){
		
		for (Permission p: plugin.getDescription().getPermissions()){
			if (p.getName().equalsIgnoreCase(perm)){
				if (player.hasPermission(p)){
					return true;
				} else {
					break;
				}
			}
		}
		
		return false;
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
