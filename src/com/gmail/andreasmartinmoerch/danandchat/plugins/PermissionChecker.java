package com.gmail.andreasmartinmoerch.danandchat.plugins;

import org.bukkit.entity.Player;

public class PermissionChecker {
	/**
	 * Permission nodes
	 */
	
	public static final String all = ".*";
	
	public static final String plugin = "naviachat";
		public static final String changeChannel = ".channel";
			public static final String ban = ".ban";
			public static final String unban = ".unban";
			public static final String list = ".list";
		public static final String leaveChannel = ".leavechannel";
		public static final String canTalk = ".cantalk";
		public static final String tell = ".tell";
		public static final String me = ".me";
		
	//TODO: Write one function that covers all of these functions instead. (Move permission nodes to the CommandHandler, maybe)	
	
	public static boolean playerCanChangeChannel(Player player){
		if (!player.hasPermission(plugin + all)){
			return player.hasPermission(plugin + changeChannel);
		} else {
			return true;
		}
		
	}
	
	public static boolean playerCanTalk(Player player){
		if (!player.hasPermission(plugin + all)){
			return player.hasPermission(plugin + canTalk);
		} else {
			return true;
		}
	}
	
	public static boolean playerCanBan(Player player){
		if (player.isOp()){
			return true;
		}
		if (!player.hasPermission(plugin + all)){
			return player.hasPermission(plugin + ban);
		} else {
			return true;
		}
	}
	
	public static boolean playerCanUnban(Player player){
		if (player.isOp()){
			return true;
		}
		if (!player.hasPermission(plugin + all)){
			return player.hasPermission(plugin + unban);
		} else {
			return true;
		}
	}
	
	public static boolean playerCanList(Player player){
		if (!player.hasPermission(plugin + all)){
			return player.hasPermission(plugin + list);
		} else {
			return true;
		}
	}
	
	public static boolean playerCanTell(Player player){
		if (!player.hasPermission(plugin + all)){
			return player.hasPermission(plugin + tell);
		} else {
			return true;
		}
	}
	
	public static boolean playerCanMe(Player player){
		if (!player.hasPermission(plugin + all)){
			return player.hasPermission(plugin + me);
		} else {
			return true;
		}
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
