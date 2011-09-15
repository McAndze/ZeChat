package com.gmail.andreasmartinmoerch.danandchat.commands;

import java.util.ArrayList;

import org.bukkit.command.PluginCommand;
import org.bukkit.util.config.Configuration;

import com.gmail.andreasmartinmoerch.danandchat.ChangeCommand;
import com.gmail.andreasmartinmoerch.danandchat.DanAndChat;
import com.gmail.andreasmartinmoerch.danandchat.Settings;

public class CommandManager {
	private DanAndChat plugin;
	
	public CommandManager(DanAndChat plugin){
		this.plugin = plugin;
	}
	
	public void initialize(){
		Configuration conf = Settings.config;
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
