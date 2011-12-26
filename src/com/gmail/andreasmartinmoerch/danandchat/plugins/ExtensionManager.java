package com.gmail.andreasmartinmoerch.danandchat.plugins;

import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.plugin.Plugin;

import com.gmail.andreasmartinmoerch.danandchat.DanAndChat;
import com.gmail.andreasmartinmoerch.danandchat.utils.Messages;
import com.platymuus.bukkit.permissions.PermissionsPlugin;
import com.sparkedia.valrix.ColorMe.ColorMe;

public class ExtensionManager {
	private DanAndChat plugin;
	public ColorMe color = null;
	public PermissionsPlugin permissionsBukkit = null;

	public ExtensionManager(DanAndChat plugin) {
		this.plugin = plugin;
	}

	public boolean usesPermissionsBukkit() {
		return permissionsBukkit != null;
	}

	public void loadPermissions(PluginEnableEvent event) {
		Plugin plugin;
		if ((plugin = event.getPlugin()) != null) {
			try {
				this.permissionsBukkit = (PermissionsPlugin) plugin;
			} catch (Exception e) {
				this.plugin.getDanandLogger().logMsg(Messages.COULD_NOT_HOOK, event.getPlugin().getDescription().getFullName());
				this.permissionsBukkit = null;
			}
			
		}
	}

	public void loadColorMe(PluginEnableEvent event) {
		Plugin plugin;
		if ((plugin = event.getPlugin()) != null) {
			try {
				this.color = (ColorMe) plugin;
			} catch (Exception e) {
				this.plugin.getDanandLogger().logMsg(Messages.COULD_NOT_HOOK, event.getPlugin().getDescription().getFullName());
				this.color = null;
			}
			
		}
	}

	public boolean isUsingColorMe() {
		return color != null;
	}

	public boolean usesRPGWorld() {
		return false;
	}

	public void initRPGWorld(PluginEnableEvent event) {
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
