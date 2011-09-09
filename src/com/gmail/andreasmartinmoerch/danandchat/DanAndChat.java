package com.gmail.andreasmartinmoerch.danandchat;

import java.util.logging.Logger;

import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.Event.Priority;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.gmail.andreasmartinmoerch.danandchat.command.CommandHandler;
import com.gmail.andreasmartinmoerch.danandchat.plugins.ExtensionManager;

/**
 * This program is free software. It comes without any warranty, to
 * the extent permitted by applicable law. You can redistribute it
 * and/or modify it under the terms of the Do What The Fuck You Want
 * To Public License, Version 2, as published by Sam Hocevar. See
 * underneath for more details.
 */

/**
 * DO WHAT THE FUCK YOU WANT TO PUBLIC LICENSE
 *                   Version 2, December 2004
 *
 * Copyright (C) 2004 Sam Hocevar <sam@hocevar.net>
 *
 * Everyone is permitted to copy and distribute verbatim or modified
 * copies of this license document, and changing it is allowed as long
 * as the name is changed.
 *
 *          DO WHAT THE FUCK YOU WANT TO PUBLIC LICENSE
 * TERMS AND CONDITIONS FOR COPYING, DISTRIBUTION AND MODIFICATION
 *
 * 0. You just DO WHAT THE FUCK YOU WANT TO.
 */

/**
 * The above license applies to all classes, but iProperty.java
 * which is made by Nijikokun. The license is included in the class.
 */
/**
 * 
 * @author McAndze
 *
 */
public class DanAndChat extends JavaPlugin{
	
	public static final Logger log = Logger.getLogger("Minecraft");
	
	public static final String sPlugin = "DanAndChat";
	public static final String sfPlugin = "[" + sPlugin + "]";
	// Player Listener
	private final DanAndChatPlayerListener playerListener = new DanAndChatPlayerListener(this);
	// Handles channels, and the players that are in them.
	
	public Settings settings;
	public static Server server;
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args){
		String commandName = command.getName();
		
		if (!(sender instanceof Player)){
			return true;
		}
		Player player = (Player)sender;
		
		return CommandHandler.doCommand(commandName, player, args);
	}
	
	/**
	 * Default method
	 */
	public void onEnable(){
		server = getServer();
		
		initializeStuff();
		PluginDescriptionFile pdfFile = getDescription();
		log.info(sfPlugin + " Version: " + pdfFile.getVersion() + " by Mcandze, is enabled.");	
		
		for (Player p: getServer().getOnlinePlayers()){
			ChannelManager.initializePlayerChannels(p);
		}
		
		PluginManager pm = getServer().getPluginManager();
		
		pm.registerEvent(Event.Type.PLAYER_CHAT, playerListener, Priority.High, this);
		pm.registerEvent(Event.Type.PLAYER_JOIN, playerListener, Priority.Normal, this);
	}
	

	/**
	 * Default method
	 */
	public void onDisable(){
		
	}
	
	public void initializeStuff(){
		Settings.initialize();
		ChannelManager.initialize();
		ExtensionManager.loadNaviaChar();
		ExtensionManager.loadPermissions();
	}
	


}
