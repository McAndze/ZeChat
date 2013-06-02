package com.landsofnavia.naviachat.commands;

import java.util.ArrayList;

import org.bukkit.command.Command;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.file.FileConfiguration;

import com.landsofnavia.naviachat.NaviaChat;

public class CommandManager {
	private NaviaChat plugin;
	FileConfiguration conf;
	public CommandManager(NaviaChat plugin){
		this.plugin = plugin;
		this.conf = plugin.getConfig();
	}
	
	public void initialize(){
		conf = this.plugin.getConfig();
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
		if(conf.getBoolean("commands." +  Commands.PREFIX.toString().toLowerCase(), true)){
			prefixCommand();
		}
		ignoreCommand();
	}
	/**
	 * I know the setAliases don't work this way. They will be changed soon.
	 */
	
	public void chCommand(){			
		Command cmd = plugin.getCommand(Commands.CH.toString());
		cmd.setAliases(conf.getStringList("commands" +"."+ Commands.CH.toString().toLowerCase() +"."+ "aliases:"));
		((PluginCommand)cmd).setExecutor(new ChangeCommand(plugin));
	}
	
	public void channelCommand(){
		Command cmd = plugin.getCommand(Commands.CHANNEL.toString());
		cmd.setAliases(conf.getStringList("commands" +"."+ Commands.CHANNEL.toString().toLowerCase() +"."+ "aliases:"));
		((PluginCommand)cmd).setExecutor(new ChannelCommand(plugin));
	}
	
	public void leaveChannelCommand(){		
		Command cmd = plugin.getCommand(Commands.LEAVECHANNEL.toString());
		cmd.setAliases(conf.getStringList("commands" +"."+ Commands.LEAVECHANNEL.toString().toLowerCase() +"."+ "aliases:"));
		((PluginCommand)cmd).setExecutor(new LeavechannelCommand(plugin));
	}
	
	public void meCommand(){		
		Command cmd = plugin.getCommand(Commands.ME.toString());
		cmd.setAliases(conf.getStringList("commands" +"."+ Commands.ME.toString().toLowerCase() +"."+ "aliases:"));
		((PluginCommand)cmd).setExecutor(new MeCommand(plugin));
	}
	
	public void tCommand(){		
		Command cmd = plugin.getCommand(Commands.T.toString());
		cmd.setAliases(conf.getStringList("commands" +"."+ Commands.T.toString().toLowerCase() +"."+ "aliases:"));
		((PluginCommand)cmd).setExecutor(new TCommand(plugin));
	}
	
	public void prefixCommand(){
		Command cmd = plugin.getCommand(Commands.PREFIX.toString());
		cmd.setAliases(conf.getStringList("commands" +"."+ Commands.PREFIX.toString().toLowerCase() +"."+ "aliases:"));
		((PluginCommand)cmd).setExecutor(new PrefixCommand(plugin));
	}
	
	public void ignoreCommand(){
		Command cmd = plugin.getCommand("ignore");
		cmd.setAliases(conf.getStringList("commands" +"."+ "ignore" +"."+ "aliases:"));
		((PluginCommand)cmd).setExecutor(new IgnoreCommand(plugin));
	}
}
