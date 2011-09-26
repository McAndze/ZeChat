package com.gmail.andreasmartinmorch.danandchat.commands;

import java.util.ArrayList;
import java.util.logging.Logger;

import org.bukkit.command.Command;
import org.bukkit.command.PluginCommand;
import org.bukkit.util.config.Configuration;

import com.gmail.andreasmartinmorch.danandchat.DanAndChat;
import com.gmail.andreasmartinmorch.danandchat.Settings;

public class CommandManager {
	private DanAndChat plugin;
	private Configuration conf;
	
	public CommandManager(DanAndChat plugin){
		this.plugin = plugin;
	}
	

	  public void initialize() {
	    this.conf = this.plugin.settings.config;

	    if (this.conf.getBoolean("commands." + Commands.CH.toString().toLowerCase() + "." + "enabled", true)) {
	      chCommand();
	    }
	    if (this.conf.getBoolean("commands." + Commands.CHANNEL.toString().toLowerCase() + "." + "enabled", true)) {
	      channelCommand();
	    }
	    if (this.conf.getBoolean("commands." + Commands.LEAVECHANNEL.toString().toLowerCase() + "." + "enabled", true)) {
	      leaveChannelCommand();
	    }
	    if (this.conf.getBoolean("commands." + Commands.ME.toString().toLowerCase() + "." + "enabled", true)) {
	      meCommand();
	    }
	    if (this.conf.getBoolean("commands." + Commands.T.toString().toLowerCase() + "." + "enabled", true)) {
	      tCommand();
	    }
	    if (this.conf.getBoolean("commands." + Commands.PREFIX.toString().toLowerCase(), true))
	      prefixCommand();
	  }
	/**
	 * I know the setAliases don't work this way. They will be changed soon.
	 */
	
	  public void chCommand()
	  {
	    Configuration conf = this.plugin.settings.config;
	    Command cmd = this.plugin.getCommand(Commands.CH.toString());
	    cmd.setAliases(conf.getStringList("commands." + Commands.CH.toString().toLowerCase() + "." + "aliases:", new ArrayList<String>()));
	    ((PluginCommand)cmd).setExecutor(new ChangeCommand(this.plugin));
	  }

	  public void channelCommand() {
	    Configuration conf = this.plugin.settings.config;
	    Command cmd = this.plugin.getCommand(Commands.CHANNEL.toString());
	    cmd.setAliases(conf.getStringList("commands." + Commands.CHANNEL.toString().toLowerCase() + "." + "aliases:", new ArrayList<String>()));
	    ((PluginCommand)cmd).setExecutor(new ChannelCommand(this.plugin));
	  }

	  public void leaveChannelCommand() {
	    Configuration conf = this.plugin.settings.config;
	    Command cmd = this.plugin.getCommand(Commands.LEAVECHANNEL.toString());
	    cmd.setAliases(conf.getStringList("commands." + Commands.LEAVECHANNEL.toString().toLowerCase() + "." + "aliases:", new ArrayList<String>()));
	    ((PluginCommand)cmd).setExecutor(new LeavechannelCommand(this.plugin));
	  }

	  public void meCommand() {
	    Configuration conf = this.plugin.settings.config;
	    Command cmd = this.plugin.getCommand(Commands.ME.toString());
	    cmd.setAliases(conf.getStringList("commands." + Commands.ME.toString().toLowerCase() + "." + "aliases:", new ArrayList<String>()));
	    ((PluginCommand)cmd).setExecutor(new MeCommand(this.plugin));
	  }

	  public void tCommand() {
	    Configuration conf = this.plugin.settings.config;
	    Command cmd = this.plugin.getCommand(Commands.T.toString());
	    cmd.setAliases(conf.getStringList("commands." + Commands.T.toString().toLowerCase() + "." + "aliases:", new ArrayList<String>()));
	    ((PluginCommand)cmd).setExecutor(new TCommand(this.plugin));
	  }

	  public void prefixCommand() {
	    Configuration conf = this.plugin.settings.config;
	    Command cmd = this.plugin.getCommand(Commands.PREFIX.toString());
//	    cmd.setAliases(conf.getStringList("commands." + Commands.PREFIX.toString().toLowerCase() + "." + "aliases:", new ArrayList<String>()));
	    ((PluginCommand)cmd).setExecutor(new PrefixCommand(this.plugin));
	  }
}
