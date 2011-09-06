package com.gmail.andreasmartinmoerch.danandchat.plugins;

import org.bukkit.plugin.Plugin;

import com.gmail.andreasmartinmoerch.danandchat.DanAndChat;
import com.nijiko.permissions.PermissionHandler;
import com.nijikokun.bukkit.Permissions.Permissions;

public class ExtensionManager {
	
	public static PermissionHandler permissions = null;
	public static boolean isUsingNaviaChar = false;
	
	public static boolean isUsingPermissions(){
		return permissions != null;
	}
	
	public static void loadNaviaChar(){
		if (DanAndChat.server.getPluginManager().getPlugin("NaviaChar") != null){
			isUsingNaviaChar = true;
		}
	}
	
	public static void loadPermissions(){
		Plugin test = DanAndChat.server.getPluginManager().getPlugin("Permissions");
		if (test != null){
			permissions = ((Permissions)test).Security;
		}
	}
}
