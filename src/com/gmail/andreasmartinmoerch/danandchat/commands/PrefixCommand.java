package com.gmail.andreasmartinmoerch.danandchat.commands;

import java.util.logging.Logger;

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
		String subcmd =  args[0];
		if (subcmd == null){
			sender.sendMessage("subcmd is null");
			return false;
		}
		Logger.getLogger("Minecraft").info(subcmd);
		if (subcmd.equalsIgnoreCase("prefixgrp") || subcmd.equalsIgnoreCase("prgrp") || subcmd.equalsIgnoreCase("prefixgroup")){
			Logger.getLogger("Minecraft").info("2");
			String group = args[1];
			String prefix = args[2];
			if (group == null || prefix == null){
				sender.sendMessage("group or prefix is null");
				return false;
			}
			Logger.getLogger("Minecraft").info("3");
			sender.sendMessage(this.plugin.getPrefixer().setGroupPrefix(group, prefix));
			Logger.getLogger("Minecraft").info("4");
		} else if (subcmd.equalsIgnoreCase("prefix")){
			String player = args[1];
			String prefix = args[2];
			if (player == null || prefix == null){
				sender.sendMessage("player or prefix is null");
				return false;
			}
			
			sender.sendMessage(this.plugin.getPrefixer().setPrefix(player, prefix));
		}
		return true;
	}

}
