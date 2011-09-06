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
		
	public static boolean playerCanChangeChannel(Player player){	
		if (!ExtensionManager.isUsingPermissions()){
			return true;
		}
		return ExtensionManager.permissions.has(player, plugin + changeChannel);
	}
	
	public static boolean playerCanTalk(Player player){
		if (!ExtensionManager.isUsingPermissions()){
			return true;
		}
		return ExtensionManager.permissions.has(player, plugin + canTalk);
	}
	
	public static boolean playerCanBan(Player player){
		if (!ExtensionManager.isUsingPermissions()){
			if (player.isOp()){
				return true;
			}
			return false;
		}
		return ExtensionManager.permissions.has(player, plugin + ban);
	}
	
	public static boolean playerCanUnban(Player player){
		if (!ExtensionManager.isUsingPermissions()){
			if (player.isOp()){
				return true;
			}
			return false;
		}
		return ExtensionManager.permissions.has(player, plugin + unban);
	}
	
	public static boolean playerCanList(Player player){
		if (!ExtensionManager.isUsingPermissions()){
			return true;
		}
		return ExtensionManager.permissions.has(player, plugin + list);
	}
	
	public static boolean playerCanTell(Player player){
		if (!ExtensionManager.isUsingPermissions()){
			return true;
		}
		return ExtensionManager.permissions.has(player, plugin + tell);
	}
	
	public static boolean playerCanMe(Player player){
		if (!ExtensionManager.isUsingPermissions()){
			return true;
		}
		return ExtensionManager.permissions.has(player, plugin + me);
	}
	
	public static String getGroup(Player player){
		if (!ExtensionManager.isUsingPermissions()){
			return "";
		}
		return ExtensionManager.permissions.getGroup(player.getWorld().getName(), player.getName());
	}
	
	public static String getPrefix(Player player){
		if (!ExtensionManager.isUsingPermissions()){
			return "";
		}
		return ExtensionManager.permissions.getGroup(player.getWorld().getName(), player.getName());
	}
}
