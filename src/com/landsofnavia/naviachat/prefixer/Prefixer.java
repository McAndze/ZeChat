package com.landsofnavia.naviachat.prefixer;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.landsofnavia.naviachat.NaviaChat;
import com.landsofnavia.naviachat.channel.NaviaLogger;
import com.sun.istack.internal.logging.Logger;

public class Prefixer {
	private NaviaChat plugin;
	
	public Prefixer(NaviaChat plugin){
		this.plugin = plugin;
	}
	
	public String getPrefix(Player player){
//		String prefix = player.getMetadata("chatPrefix").get(0).asString()
		return "";
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
		/*String groupPrefix = "";
		String test = this.plugin.getSettings().prefixConfig.getString("groups." + group.toLowerCase(), null);
		if (test != null){
			groupPrefix = test;
		}
		return groupPrefix;*/
		return "";
	}
	
	
	public String setGroupPrefix(String group, String prefix){
		/*this.plugin.getSettings().prefixConfig.load();
		this.plugin.getSettings().prefixConfig.setProperty("groups." + group.toLowerCase(), prefix);
		this.plugin.getSettings().prefixConfig.save();
		return ChatColor.GREEN + "Set prefix: " + ChatColor.GOLD + prefix + ChatColor.GREEN + " to group: " + ChatColor.GOLD + group + ChatColor.GREEN + ".";*/
		NaviaLogger.log.info("setgroupprefix is not implemented");
		return "";
	}
	
	public String setPrefix(String player, String prefix){
		/*this.plugin.getSettings().prefixConfig.load();
		this.plugin.getSettings().prefixConfig.setProperty("users." + player.toLowerCase(), prefix);
		this.plugin.getSettings().prefixConfig.save();
		return ChatColor.GREEN + "Set prefix: " + ChatColor.GOLD + prefix + ChatColor.GREEN + " to player: " + ChatColor.GOLD + player + ChatColor.GREEN + ".";*/
		NaviaLogger.log.info("setrefix is not implemented");
		return "";
	}
}
