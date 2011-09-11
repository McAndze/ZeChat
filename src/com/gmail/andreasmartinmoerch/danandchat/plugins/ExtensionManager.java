package com.gmail.andreasmartinmoerch.danandchat.plugins;

import org.bukkit.plugin.Plugin;

import com.gmail.andreasmartinmoerch.danandchat.DanAndChat;
import com.nijiko.permissions.PermissionHandler;
import com.nijikokun.bukkit.Permissions.Permissions;
import com.sparkedia.valrix.ColorMe.ColorMe;

public class ExtensionManager {
	public static ColorMe color;
	public static PermissionHandler permissions = null;
	public static boolean isUsingNaviaChar = false;
	
	public static boolean isUsingPermissions(){
		return permissions != null;
	}
	
	public static void loadColorMe(){
		Plugin plugin;
		if ((plugin = DanAndChat.server.getPluginManager().getPlugin("ColorMe")) != null){
			color = (ColorMe)color;
		}
	}
	
	public static boolean isUsingColorMe(){
		return color != null;
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
