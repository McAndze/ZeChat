package com.landsofnavia.naviachat;

import java.util.logging.Logger;

import org.bukkit.plugin.java.JavaPlugin;

import com.landsofnavia.naviachat.channel.Channel;
import com.landsofnavia.naviachat.channel.ChannelManager;
import com.landsofnavia.naviachat.channel.MessageHandler;
import com.landsofnavia.naviachat.channel.NaviaLogger;
import com.landsofnavia.naviachat.commands.CommandManager;
import com.landsofnavia.naviachat.parsing.NaviaParser;
import com.landsofnavia.naviachat.plugins.ExtensionManager;
import com.landsofnavia.naviachat.plugins.PermissionChecker;
import com.landsofnavia.naviachat.prefixer.Prefixer;
import com.landsofnavia.naviachat.utils.MessageGetter;
import com.landsofnavia.naviachat.utils.Settings;

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
public class NaviaChat extends JavaPlugin {
	// Finals
	/**
	 * Just a quick way to access the Logger. Probably won't be used much
	 * longer.
	 */
	public final Logger log = Logger.getLogger("Minecraft");
	/**
	 * The DanAndChat PlayerListener
	 */
	public final NaviaPlayerListener playerListener = new NaviaPlayerListener(
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
	private NaviaLogger danandLogger;
	private NaviaParser danAndParser;

	/**
	 * Default method
	 */
	public void onEnable() {
		initializeFields();
		getServer().getPluginManager().registerEvents(playerListener, this);
		/* this.log.info("[DanAndChat] DanAndChat v" + pdfFile.getVersion()
				+ " enabled."); */
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
		this.danandLogger = new NaviaLogger(this, "danand.log", null);
		this.danAndParser = new NaviaParser(this);
		
		this.settings.initialize();
		this.commandManager.initialize();
		this.extensionManager.initialize();
		this.messageGetter = this.settings.getNewMessageGetter();
		this.channels.initialize();
		this.danandLogger.initialize();
		
	}
	
	public NaviaParser getDanAndParser(){
		return this.danAndParser;
	}
	
	public NaviaLogger getDanandLogger() {
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
