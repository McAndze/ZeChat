package com.gmail.andreasmartinmoerch.danandchat.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.gmail.andreasmartinmoerch.danandchat.ChannelManager;
import com.gmail.andreasmartinmoerch.danandchat.channel.Channel;
import com.gmail.andreasmartinmoerch.danandchat.plugins.PermissionChecker;

public class MeCommand implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		String commandName = cmd.getName();
		
		if (!(sender instanceof Player)){
			return true;
		}
		Player player = (Player)sender;
		
		if (!PermissionChecker.playerCanMe(player)){
			player.sendMessage(ChatColor.RED + "You can not use that command.");
			return true;
		}
		String action = "";
		for (String s: args){
			action += s + " ";
		}
		boolean inAChannel = false;
		for (Channel c: ChannelManager.channels){
			if (c.playerIsInChannel(player)){
				inAChannel = true;
				break;
			}
		}
		if (!inAChannel){
			player.sendMessage(ChatColor.RED + "You are not in a channel.");
			return true;
		}
		
//  		if (ChannelManager.getFocusedChannel(player).isLocal()){
//		    	MessageHandler.sendIndependentLocalMessage(
//					player.getLocation(), 
//					MessageHandler.getIcEmote(player, action), 
//					ChannelManager.getFocusedChannel(player).getRange());
//		} else {
//			NaviaChat.server.broadcastMessage(ChatColor.AQUA + "* " + player.getDisplayName() + " " + action);
//		}
		return true;
	}
	
}
