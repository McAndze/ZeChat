package com.gmail.andreasmartinmoerch.danandchat.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.gmail.andreasmartinmoerch.danandchat.ChannelManager;
import com.gmail.andreasmartinmoerch.danandchat.plugins.PermissionChecker;

public class ChCommand implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		String commandName = cmd.getName();
		
		if (!(sender instanceof Player)){
			return true;
		}
		Player player = (Player)sender;
		
		if (!PermissionChecker.playerCanChangeChannel(player)){
			player.sendMessage(ChatColor.RED + "You haven't got the permission to do that.");
			return true;
		}
		
		if (args.length != 1){
			return false;
		}
		
		return ChannelManager.playerChangeChannel(args[0], player);
	}
	
}
