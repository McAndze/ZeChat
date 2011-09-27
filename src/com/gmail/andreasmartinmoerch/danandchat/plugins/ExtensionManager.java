package com.gmail.andreasmartinmoerch.danandchat.plugins;

import org.bukkit.plugin.Plugin;

import com.gmail.andreasmartinmoerch.danandchat.DanAndChat;
import com.platymuus.bukkit.permissions.PermissionsPlugin;
import com.sparkedia.valrix.ColorMe.ColorMe;

public class ExtensionManager {
	boolean usesrpg = false;
	public ColorMe color;
	public boolean isUsingNaviaChar = false;
	private final DanAndChat plugin;
	public PermissionsPlugin permissionsBukkit;

	public ExtensionManager(DanAndChat plugin) {
		this.plugin = plugin;
	}

	public void initialize() {
		loadColorMe();
		initRPGWorld();
		loadPermissions();
	}

	public boolean usesPermissionsBukkit() {
		return permissionsBukkit != null;
	}

	public void loadPermissions() {
		Plugin plugin;
		if ((plugin = this.plugin.getServer().getPluginManager()
				.getPlugin("PermissionsBukkit")) != null) {
			this.permissionsBukkit = (PermissionsPlugin) plugin;
		}
	}

	public void loadColorMe() {
		Plugin plugin;
		if ((plugin = this.plugin.getServer().getPluginManager()
				.getPlugin("ColorMe")) != null) {
			color = (ColorMe) plugin;
		}
	}

	public boolean isUsingColorMe() {
		return color != null;
	}

	public void loadNaviaChar() {
		if (this.plugin.getServer().getPluginManager().getPlugin("NaviaChar") != null) {
			isUsingNaviaChar = true;
		}
	}

	public boolean usesRPGWorld() {
		return usesrpg;
	}

	public void initRPGWorld() {
		// Plugin testPlugin = null;
		// if ((testPlugin =
		// this.plugin.getServer().getPluginManager().getPlugin("RPGWorld")) !=
		// null){
		// RPGWorldPlugin rpg = (RPGWorldPlugin)testPlugin;
		// this.plugin.log.info("[DanAndChat] Found RPGWorld! Hooking in!");
		// rpg.chatPlugins.hookPlugin(new DanAndChatRPG(plugin));
		// }
	}
}
