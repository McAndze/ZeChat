package com.gmail.andreasmartinmoerch.danandchat.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.gmail.andreasmartinmoerch.danandchat.ChannelManager;
import com.gmail.andreasmartinmoerch.danandchat.DanAndChat;
import com.gmail.andreasmartinmoerch.danandchat.channel.Channel;
import com.gmail.andreasmartinmoerch.danandchat.plugins.PermissionChecker;

public class MeCommand implements CommandExecutor{
	private DanAndChat plugin;
	
	public MeCommand(DanAndChat plugin){
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		String commandName = cmd.getName();
		
		if (!(sender instanceof Player)){
			return true;
		}
		Player player = (Player)sender;
		
		if (!this.plugin.perms.playerHasPermission(player, PermissionChecker.prefix + PermissionChecker.me)){
			player.sendMessage(ChatColor.RED + "You can not use that command.");
			return true;
		}
		if (args.length == 0){
			player.sendMessage(ChatColor.RED + "You can't emote \"nothing\" unless you don't have a heart. Let  out your feelings. It will feel so much better :)");
			return true;
		}
		String action = "";
		for (String s: args){
			action += s + " ";
		}
		boolean inAChannel = false;
		Channel c = null;
		if ((c = ChannelManager.playerFocused.get(player)) != null){
			inAChannel = true;
			
		} else {
			return true;
		}
		if (!inAChannel){
			player.sendMessage(ChatColor.RED + "You are not in a channel.");
			return true;
		}
		c.sendMe(player, action);
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