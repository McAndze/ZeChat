package com.gmail.andreasmartinmoerch.danandchat.commands;

import java.util.ArrayList;
import java.util.logging.Logger;

import org.bukkit.command.PluginCommand;
import org.bukkit.util.config.Configuration;

import com.gmail.andreasmartinmoerch.danandchat.ChangeCommand;
import com.gmail.andreasmartinmoerch.danandchat.DanAndChat;
import com.gmail.andreasmartinmoerch.danandchat.Settings;

public class CommandManager {
	private DanAndChat plugin;
	private Configuration conf;
	
	public CommandManager(DanAndChat plugin){
		Logger.getLogger("Minecraft").info("[DanAndChat] Hi!!!");
		this.plugin = plugin;
		initialize();
	}
	
	public void initialize(){
		conf = Settings.config;
		if (!conf.getBoolean("plugin" +"."+ "initialized", false)){
			createConf();
		}
		// Initialize the "ch" command.
		if (conf.getBoolean("commands" +"."+ Commands.CH.toString().toLowerCase() +"."+ "enabled" , true)){
			chCommand();
		}
		if (conf.getBoolean("commands" +"."+ Commands.CHANNEL.toString().toLowerCase() +"."+ "enabled" , true)){
			channelCommand();
		}
		if (conf.getBoolean("commands" +"."+ Commands.LEAVECHANNEL.toString().toLowerCase() +"."+ "enabled" , true)){
			leaveChannelCommand();
		}
		if (conf.getBoolean("commands" +"."+ Commands.ME.toString().toLowerCase() +"."+ "enabled" , true)){
			meCommand();
		}
		if (conf.getBoolean("commands" +"."+ Commands.T.toString().toLowerCase() +"."+ "enabled" , true)){
			tCommand();
		}
	}
	
	public void createConf(){
		conf.setProperty("plugin" + "." + "initialized", true);
		conf.setProperty("commands" +"."+ Commands.CH.toString().toLowerCase() +"."+ "enabled" , true);
		conf.setProperty("commands" +"."+ Commands.CHANNEL.toString().toLowerCase() +"."+ "enabled" , true);
		conf.setProperty("commands" +"."+ Commands.LEAVECHANNEL.toString().toLowerCase() +"."+ "enabled" , true);
		conf.setProperty("commands" +"."+ Commands.ME.toString().toLowerCase() +"."+ "enabled" , true);
		conf.setProperty("commands" +"."+ Commands.T.toString().toLowerCase() +"."+ "enabled" , true);
		conf.save();
		conf.load();
	}
	/**
	 * I know the setAliases don't work this way. They will be changed soon.
	 */
	
	public void chCommand(){
		Configuration conf = Settings.config;
		PluginCommand cmd = plugin.getCommand(Commands.CH.toString());
		cmd.setAliases(conf.getStringList("commands" +"."+ Commands.CH.toString().toLowerCase() +"."+ "aliases:", new ArrayList<String>()));
		cmd.setExecutor(new ChangeCommand(plugin));
	}
	
	public void channelCommand(){
		Configuration conf = Settings.config;
		PluginCommand cmd = plugin.getCommand(Commands.CHANNEL.toString());
		cmd.setAliases(conf.getStringList("commands" +"."+ Commands.CHANNEL.toString().toLowerCase() +"."+ "aliases:", new ArrayList<String>()));
		cmd.setExecutor(new ChannelCommand(plugin));
	}
	
	public void leaveChannelCommand(){
		Configuration conf = Settings.config;
		PluginCommand cmd = plugin.getCommand(Commands.LEAVECHANNEL.toString());
		cmd.setAliases(conf.getStringList("commands" +"."+ Commands.LEAVECHANNEL.toString().toLowerCase() +"."+ "aliases:", new ArrayList<String>()));
		cmd.setExecutor(new LeavechannelCommand(plugin));
	}
	
	public void meCommand(){
		Configuration conf = Settings.config;
		PluginCommand cmd = plugin.getCommand(Commands.ME.toString());
		cmd.setAliases(conf.getStringList("commands" +"."+ Commands.ME.toString().toLowerCase() +"."+ "aliases:", new ArrayList<String>()));
		cmd.setExecutor(new MeCommand(plugin));
	}
	
	public void tCommand(){
		Configuration conf = Settings.config;
		PluginCommand cmd = plugin.getCommand(Commands.T.toString());
		cmd.setAliases(conf.getStringList("commands" +"."+ Commands.T.toString().toLowerCase() +"."+ "aliases:", new ArrayList<String>()));
		cmd.setExecutor(new TCommand(plugin));
	}
}
