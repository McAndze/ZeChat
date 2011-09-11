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

public class LeavechannelCommand implements CommandExecutor{
	private DanAndChat plugin;
	
	public LeavechannelCommand(DanAndChat plugin){
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
		// Start validation
		if (!this.plugin.perms.playerHasPermission(player, PermissionChecker.prefix + PermissionChecker.leaveChannel)){
			player.sendMessage(ChatColor.RED + "You don't have permission to do that.");
			return false;
		}
		
		if (args.length == 0){
			return false;
		}
		// End validation.
		Channel c;
		if ((c=ChannelManager.getChannelWithShortcut(args[0])) == null){
			player.sendMessage(ChatColor.RED + "Channel does not exist.");
			return true;
		}
		
		if (!ChannelManager.playerIsInChannel(player, c)){
			player.sendMessage(ChatColor.RED + "You are not in that channel!");
		}
		
		c.removePlayer(player);
		return true;
	}
	
}
