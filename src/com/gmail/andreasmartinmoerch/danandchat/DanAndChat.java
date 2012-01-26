package com.gmail.andreasmartinmoerch.danandchat;

import java.util.logging.Logger;

import org.blockface.bukkitstats.CallHome;
import org.bukkit.event.Event;
import org.bukkit.event.Event.Priority;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.gmail.andreasmartinmoerch.danandchat.channel.Channel;
import com.gmail.andreasmartinmoerch.danandchat.channel.ChannelManager;
import com.gmail.andreasmartinmoerch.danandchat.channel.DanAndLogger;
import com.gmail.andreasmartinmoerch.danandchat.channel.MessageHandler;
import com.gmail.andreasmartinmoerch.danandchat.commands.CommandManager;
import com.gmail.andreasmartinmoerch.danandchat.parsing.DanAndParser;
import com.gmail.andreasmartinmoerch.danandchat.plugins.ExtensionManager;
import com.gmail.andreasmartinmoerch.danandchat.plugins.PermissionChecker;
import com.gmail.andreasmartinmoerch.danandchat.prefixer.Prefixer;
import com.gmail.andreasmartinmoerch.danandchat.utils.MessageGetter;
import com.gmail.andreasmartinmoerch.danandchat.utils.Settings;

/*
 * This program is free software. It comes without any warranty, to
 * the extent permitted by applicable law. You can redistribute it
 * and/or modify it under the terms of the Do What The Fuck You Want
 * To Public License, Version 2, as published by Sam Hocevar. See
 * underneath for more details.
 */

/*
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

/*
 * The above license applies to all classes, unless stated otherwise in the individual class.
 */
/**
 * This is the main class of DanAndChat.
 * 
 * @author McAndze
 * 
 */
public class DanAndChat extends JavaPlugin {
	// Finals
	/**
	 * Just a quick way to access the Logger. Probably won't be used much
	 * longer.
	 */
	public final Logger log = Logger.getLogger("Minecraft");
	/**
	 * The DanAndChat PlayerListener
	 */
	public final DanAndChatPlayerListener playerListener = new DanAndChatPlayerListener(
			this);

	// Private variables to get. Not set.
	private ChannelManager channels;
	private Settings settings;
	private PermissionChecker permissionChecker;
	private ExtensionManager extensionManager;
	private CommandManager commandManager;
	private MessageHandler messageHandler;
	private Prefixer prefixer;
	private MessageGetter messageGetter;
	private DanAndLogger danandLogger;
	private DanAndParser danAndParser;

	/**
	 * Default method
	 */
	public void onEnable() {
		initializeFields();
		// Get the default PluginManager
		PluginManager pm = getServer().getPluginManager();

		// Register PLAYER.CHAT event with Priority.High
		pm.registerEvent(Event.Type.PLAYER_CHAT, playerListener, Priority.Monitor, this);
		// Monitor PLAYER.JOIN
		pm.registerEvent(Event.Type.PLAYER_JOIN, playerListener, Priority.Monitor, this);
		// Monitor PLAYER.QUIT
		pm.registerEvent(Event.Type.PLAYER_QUIT, playerListener, Priority.Monitor, this);

		PluginDescriptionFile pdfFile = getDescription();
		
		this.log.info("[DanAndChat] DanAndChat v" + pdfFile.getVersion()
				+ " enabled.");
	}

	/**
	 * Default method
	 */
	public void onDisable() {
		for (Channel c : this.channels.channels) {
			c.getChLogger().writeToFile();
		}
		this.messageHandler = null;
		this.settings = null;
		this.extensionManager = null;
		this.prefixer = null;
		this.channels = null;
		// ChannelManager.channels = null;
		this.permissionChecker = null;
		this.commandManager = null;
	}

	/**
	 * Initializes stuff in the correct order.
	 */
	public void initializeFields() {
		// MessageHandler
		this.messageHandler = new MessageHandler(this);
		
		// Settings
		this.settings = new Settings(this);	
		
		// CommandManager
		this.commandManager = new CommandManager(this);	
		
		// Extensions manager
		this.extensionManager = new ExtensionManager(this);
		
		// The prefixer TODO: Make Prefixer a separate plugin
		this.prefixer = new Prefixer(this);
		this.channels = new ChannelManager(this);
		this.danandLogger = new DanAndLogger(this, "danand.log", null);
		this.danAndParser = new DanAndParser(this);
		
		this.settings.initialize();
		this.commandManager.initialize();
		this.extensionManager.initialize();
		this.messageGetter = this.settings.getNewMessageGetter();
		this.channels.initialize();
		this.danandLogger.initialize();
		
		// Check if Vault has been initialized correctly (just in case) and disable if not
		if (!PermissionChecker.initialize(this)){
			this.danandLogger.logMsg("Something went wrong when initializing Vault permissionmanager.", "SEVERE");
			this.getPluginLoader().disablePlugin(this);
		}
	}
	
	public DanAndParser getDanAndParser(){
		return this.danAndParser;
	}
	
	public DanAndLogger getDanandLogger() {
		return danandLogger;
	}
	
	/**
	 * 
	 * @return Used messageGetter
	 */
	public MessageGetter getMessageGetter() {
		return messageGetter;
	}
	
	/**
	 * 
	 * @param messageGetter sets this.messageGetter
	 */
	public void setMessageGetter(MessageGetter messageGetter) {
		this.messageGetter = messageGetter;
	}

	@SuppressWarnings("javadoc")
	public ChannelManager getChannels() {
		return channels;
	}

	@SuppressWarnings("javadoc")
	public Settings getSettings() {
		return settings;
	}

	@SuppressWarnings("javadoc")
	public PermissionChecker getPermissionChecker() {
		return permissionChecker;
	}

	@SuppressWarnings("javadoc")
	public ExtensionManager getExtensionManager() {
		return extensionManager;
	}

	@SuppressWarnings("javadoc")
	public CommandManager getCommandManager() {
		return commandManager;
	}

	@SuppressWarnings("javadoc")
	public MessageHandler getMessageHandler() {
		return messageHandler;
	}

	@SuppressWarnings("javadoc")
	public Prefixer getPrefixer() {
		return prefixer;
	}
}
