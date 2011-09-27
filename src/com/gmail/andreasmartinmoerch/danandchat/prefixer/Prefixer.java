package com.gmail.andreasmartinmoerch.danandchat.prefixer;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.gmail.andreasmartinmoerch.danandchat.DanAndChat;
import com.platymuus.bukkit.permissions.Group;

public class Prefixer {
	private DanAndChat plugin;
	
	public Prefixer(DanAndChat plugin){
		this.plugin = plugin;
	}
	
	public String getPrefix(Player player){
		String prefix;
		
		prefix = this.plugin.getSettings().prefixConfig.getString("users." + player.getName().toLowerCase(), null);
		
		if (prefix == null){
			prefix = getGroupsPrefix(player);
		}
		if (prefix == null){
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
	
	public String getGroupsPrefix(Player player){
		String groupsPrefix = "";
		if (this.plugin.getExtensionManager().usesPermissionsBukkit()){
			List<Group> groups = this.plugin.getExtensionManager().permissionsBukkit.getGroups(player.getName());
			if (groups == null || groups.isEmpty()){
				return "";
			}
			for (Group g: groups){
				if (groupsPrefix.equalsIgnoreCase("")){
					groupsPrefix = getGroupPrefix(g.getName());
//					if (groups.size() == 1){
//						player.sendMessage("groups size 1");
//						return groupsPrefix;
//					}
				} else {
					groupsPrefix += ChatColor.WHITE + "|" +  getGroupPrefix(g.getName());
				}
			}
				
			}
		return groupsPrefix;
	}
	
	public String getGroupPrefix(String group){
		String groupPrefix = "";
		if (this.plugin.getExtensionManager().usesPermissionsBukkit()){
			String test = this.plugin.getSettings().prefixConfig.getString("groups." + group.toLowerCase(), null);
			if (test != null){
				groupPrefix = test;
			}
		}
		return groupPrefix;
	}
	
	public String setGroupPrefix(String group, String prefix){
		if (!this.plugin.getExtensionManager().usesPermissionsBukkit()){
			return ChatColor.RED + "No compatible group manager installed to set group prefix.";
		}
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
