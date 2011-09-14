package com.gmail.andreasmartinmoerch.danandchat;

import java.util.logging.Logger;

import org.blockface.bukkitstats.CallHome;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.Event.Priority;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.gmail.andreasmartinmoerch.danandchat.commands.ChCommand;
import com.gmail.andreasmartinmoerch.danandchat.commands.ChannelCommand;
import com.gmail.andreasmartinmoerch.danandchat.commands.Commands;
import com.gmail.andreasmartinmoerch.danandchat.commands.LeavechannelCommand;
import com.gmail.andreasmartinmoerch.danandchat.commands.MeCommand;
import com.gmail.andreasmartinmoerch.danandchat.commands.TCommand;
import com.gmail.andreasmartinmoerch.danandchat.plugins.ExtensionManager;
import com.gmail.andreasmartinmoerch.danandchat.plugins.PermissionChecker;

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
 * The above license applies to all classes, unless stated otherwise in the individual class.
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
	public PermissionChecker perms;
	
	/**
	 * Default method
	 */
	public void onEnable(){
		server = getServer();
		
		PluginDescriptionFile pdfFile = getDescription();
		log.info(sfPlugin + " Version: " + pdfFile.getVersion() + " by Mcandze, is enabled.");	
		
		
		PluginManager pm = getServer().getPluginManager();
		
		pm.registerEvent(Event.Type.PLAYER_CHAT, playerListener, Priority.High, this);
		pm.registerEvent(Event.Type.PLAYER_JOIN, playerListener, Priority.Monitor, this);
		pm.registerEvent(Event.Type.PLAYER_QUIT, playerListener, Priority.Monitor, this);
		
		// Set commands!
		this.getCommand(Commands.CH.toString()).setExecutor(new ChCommand(this));
		this.getCommand(Commands.CHANNEL.toString()).setExecutor(new ChannelCommand(this));
		this.getCommand(Commands.LEAVECHANNEL.toString()).setExecutor(new LeavechannelCommand(this));
		this.getCommand(Commands.ME.toString()).setExecutor(new MeCommand(this));
		this.getCommand(Commands.T.toString()).setExecutor(new TCommand(this));
		
		initializeStuff();
		
		CallHome.load(this);
	}
	

	/**
	 * Default method
	 */
	public void onDisable(){
		Settings.config = null;
		Settings.channelsConfig = null;
		ChannelManager.channels = null;
		ExtensionManager.permissions = null;
		this.perms = null;
	}
	
	public void initializeStuff(){
		Settings.initialize();
		ChannelManager.initialize();
		ExtensionManager.loadNaviaChar();
		perms = new PermissionChecker(this);
		ExtensionManager.loadPermissions();
	}
	


}
