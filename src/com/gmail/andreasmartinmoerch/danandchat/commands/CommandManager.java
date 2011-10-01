package com.gmail.andreasmartinmoerch.danandchat.commands;

import java.util.ArrayList;

import org.bukkit.command.Command;
import org.bukkit.command.PluginCommand;
import org.bukkit.util.config.Configuration;

import com.gmail.andreasmartinmoerch.danandchat.DanAndChat;
import com.gmail.andreasmartinmoerch.danandchat.commands.ChangeCommand;
import com.gmail.andreasmartinmoerch.danandchat.commands.ChannelCommand;
import com.gmail.andreasmartinmoerch.danandchat.commands.Commands;
import com.gmail.andreasmartinmoerch.danandchat.commands.LeavechannelCommand;
import com.gmail.andreasmartinmoerch.danandchat.commands.MeCommand;
import com.gmail.andreasmartinmoerch.danandchat.commands.TCommand;

public class CommandManager {
	private DanAndChat plugin;
	private Configuration conf;
	
	public CommandManager(DanAndChat plugin){
		this.plugin = plugin;
	}
	
	public void initialize(){
		conf = this.plugin.getSettings().config;
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
	}
	/**
	 * I know the setAliases don't work this way. They will be changed soon.
	 */
	
	public void chCommand(){			
		Configuration conf = this.plugin.getSettings().config;
		Command cmd = plugin.getCommand(Commands.CH.toString());
		cmd.setAliases(conf.getStringList("commands" +"."+ Commands.CH.toString().toLowerCase() +"."+ "aliases:", new ArrayList<String>()));
		((PluginCommand)cmd).setExecutor(new ChangeCommand(plugin));
	}
	
	public void channelCommand(){
		Configuration conf = this.plugin.getSettings().config;
		Command cmd = plugin.getCommand(Commands.CHANNEL.toString());
		cmd.setAliases(conf.getStringList("commands" +"."+ Commands.CHANNEL.toString().toLowerCase() +"."+ "aliases:", new ArrayList<String>()));
		((PluginCommand)cmd).setExecutor(new ChannelCommand(plugin));
	}
	
	public void leaveChannelCommand(){
		Configuration conf = this.plugin.getSettings().config;
		Command cmd = plugin.getCommand(Commands.LEAVECHANNEL.toString());
		cmd.setAliases(conf.getStringList("commands" +"."+ Commands.LEAVECHANNEL.toString().toLowerCase() +"."+ "aliases:", new ArrayList<String>()));
		((PluginCommand)cmd).setExecutor(new LeavechannelCommand(plugin));
	}
	
	public void meCommand(){
		Configuration conf = this.plugin.getSettings().config;
		Command cmd = plugin.getCommand(Commands.ME.toString());
		cmd.setAliases(conf.getStringList("commands" +"."+ Commands.ME.toString().toLowerCase() +"."+ "aliases:", new ArrayList<String>()));
		((PluginCommand)cmd).setExecutor(new MeCommand(plugin));
	}
	
	public void tCommand(){
		Configuration conf = this.plugin.getSettings().config;
		Command cmd = plugin.getCommand(Commands.T.toString());
		cmd.setAliases(conf.getStringList("commands" +"."+ Commands.T.toString().toLowerCase() +"."+ "aliases:", new ArrayList<String>()));
		((PluginCommand)cmd).setExecutor(new TCommand(plugin));
	}
	
	public void prefixCommand(){
		Configuration conf = this.plugin.getSettings().config;
		Command cmd = plugin.getCommand(Commands.PREFIX.toString());
		cmd.setAliases(conf.getStringList("commands" +"."+ Commands.PREFIX.toString().toLowerCase() +"."+ "aliases:", new ArrayList<String>()));
		((PluginCommand)cmd).setExecutor(new PrefixCommand(plugin));
	}
}
