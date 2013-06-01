package com.landsofnavia.naviachat.prefixer;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.landsofnavia.naviachat.NaviaChat;
import com.landsofnavia.naviachat.plugins.PermissionChecker;

public class Prefixer {
	private NaviaChat plugin;
	
	public Prefixer(NaviaChat plugin){
		this.plugin = plugin;
	}
	
	public String getPrefix(Player player){
		String prefix;
		
		// Player prefix! 
		prefix = this.plugin.getSettings().prefixConfig.getString("users." + player.getName().toLowerCase(), null);
		
		// Try primary group prefix
		if (prefix == null || prefix.equalsIgnoreCase("null")){
			try {
				prefix = getGroupPrefix(PermissionChecker.permission.getPrimaryGroup(player));
			} catch (UnsupportedOperationException u){
				this.plugin.getDanandLogger().logMsg("[Vault] "+ u.getMessage(), "WARNING");
				prefix = null;
			}
		}
		// Try getting the first group, and the prefix
		if (prefix == null || prefix.equalsIgnoreCase("null")){
			try {
				prefix = getGroupPrefix(PermissionChecker.permission.getPlayerGroups(player.getWorld(), player.getName())[0]);
			} catch (UnsupportedOperationException u){
				prefix = null;
			}
		}
		if (prefix == null || prefix.equalsIgnoreCase("null")){
			return "";
		}
		prefix = prefix.replaceAll("&", "§");
		return prefix;
	}
	
//	public String getGroupsPrefix(String... groups){
//		String groupsPrefix = "";
//		if (this.plugin.getExtensionManager().usesPermissionsBukkit()){
//			if (groups == null || groups.isEmpty()){
//				return "";
//			}
//			for (Group g: groups){
//				if (groupsPrefix.equalsIgnoreCase("")){
//					groupsPrefix = getGroupPrefix(g.getName());
//				} else {
//					groupsPrefix += ChatColor.WHITE + "|" +  getGroupPrefix(g.getName());
//				}
//			}
//				
//			}
//	}
	
	public String getGroupPrefix(String group){
		String groupPrefix = "";
		String test = this.plugin.getSettings().prefixConfig.getString("groups." + group.toLowerCase(), null);
		if (test != null){
			groupPrefix = test;
		}
		return groupPrefix;
	}
	
	public String setGroupPrefix(String group, String prefix){
		this.plugin.getSettings().prefixConfig.load();
		this.plugin.getSettings().prefixConfig.setProperty("groups." + group.toLowerCase(), prefix);
		this.plugin.getSettings().prefixConfig.save();
		return ChatColor.GREEN + "Set prefix: " + ChatColor.GOLD + prefix + ChatColor.GREEN + " to group: " + ChatColor.GOLD + group + ChatColor.GREEN + ".";
	}
	
	public String setPrefix(String player, String prefix){
		this.plugin.getSettings().prefixConfig.load();
		this.plugin.getSettings().prefixConfig.setProperty("users." + player.toLowerCase(), prefix);
		this.plugin.getSettings().prefixConfig.save();
		return ChatColor.GREEN + "Set prefix: " + ChatColor.GOLD + prefix + ChatColor.GREEN + " to player: " + ChatColor.GOLD + player + ChatColor.GREEN + ".";
	}
}
