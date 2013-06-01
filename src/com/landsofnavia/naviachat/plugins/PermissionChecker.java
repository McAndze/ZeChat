package com.landsofnavia.naviachat.plugins;

import net.milkbowl.vault.permission.Permission;

import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

import com.landsofnavia.naviachat.NaviaChat;

public class PermissionChecker {
	/**
	 * Permission nodes
	 */
	
	public static Permission permission = null;
	
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
		
	public static boolean initialize(NaviaChat plugin){
		RegisteredServiceProvider<Permission> permissionProvider = plugin.getServer().getServicesManager().getRegistration(net.milkbowl.vault.permission.Permission.class);
		if (permissionProvider != null){
			permission = permissionProvider.getProvider();
			return true;
		} else {
			return false;
		}
	}
		
	public static boolean hasPermission(Player player, String permission){
		if (player.hasPermission(permission)){
			return true;
		}
		String permWithWild = "";
		String[] nodes = permission.split("\\.");
		System.out.println(nodes.length);
		for (int i = 0; i + 1 <= nodes.length; i++){
			if (i + 1 == nodes.length){
				permWithWild += "*";
				if (player.hasPermission(permWithWild)){
					return true;
				}
			} else {
				permWithWild += nodes[i] + ".";
				if (player.hasPermission(permWithWild + "*")){
					return true;
				}
			}
		}
		return false;
	}
}
