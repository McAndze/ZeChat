package com.gmail.andreasmartinmoerch.danandchat.plugins;

import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;

import com.gmail.andreasmartinmoerch.danandchat.DanAndChat;

public class PermissionChecker {
	/**
	 * Permission nodes
	 */
	
	public static final String all = ".*";
	
	public static final String prefix = "danandchat";
		public static final String channel = ".channel";
			public static final String ban = ".ban";
			public static final String unban = ".unban";
			public static final String list = ".list";
		public static final String leaveChannel = ".leavechannel";
		public static final String canTalk = ".cantalk";
		public static final String tell = ".tell";
		public static final String me = ".me";
		public static final String changeChannel = ".changechannel";
	
	private DanAndChat plugin;
	//TODO: Write one function that covers all of these functions instead. (Move permission nodes to the CommandHandler, maybe)	
		
	public PermissionChecker(DanAndChat plugin){
		this.plugin = plugin;
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
