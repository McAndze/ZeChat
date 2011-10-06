package com.gmail.andreasmartinmoerch.danandchat.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.gmail.andreasmartinmoerch.danandchat.DanAndChat;

public class PrefixCommand implements CommandExecutor {
	private DanAndChat plugin;
	public PrefixCommand(DanAndChat plugin){
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		if (sender instanceof Player){
			if (!((Player)sender).hasPermission("danandchat.prefix")){
				return true;
			}
		}
		if (args == null || args.length < 3){
			sender.sendMessage("told ya");
			return false;
		}
		String subcmd =  args[0];
		if (subcmd == null){
			sender.sendMessage("subcmd is null");
			return false;
		}
		if (subcmd.equalsIgnoreCase("prefixgrp") || subcmd.equalsIgnoreCase("prgrp") || subcmd.equalsIgnoreCase("prefixgroup")){
			String group;
			String prefix;
			try {
				group = args[1];
				prefix = args[2];
			} catch (ArrayIndexOutOfBoundsException e){
				return false;
			}
			
			if (group == null || prefix == null){
				this.plugin.getDanandLogger().logMsg("group or prefix is null", "DEBUG");
				return false;
			}
			sender.sendMessage(this.plugin.getPrefixer().setGroupPrefix(group, prefix));
		} else if (subcmd.equalsIgnoreCase("prefix")){
			String player;
			String prefix;
			try {
				player = args[1];
				prefix = args[2];
			} catch (ArrayIndexOutOfBoundsException e){
				return false;
			}
			if (player == null || prefix == null){
				this.plugin.getDanandLogger().logMsg("player or prefix is null", "DEBUG");
				return false;
			}
			
			sender.sendMessage(this.plugin.getPrefixer().setPrefix(player, prefix));
		}
		return true;
	}

}
