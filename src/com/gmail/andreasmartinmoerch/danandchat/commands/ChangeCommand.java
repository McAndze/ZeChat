package com.gmail.andreasmartinmoerch.danandchat.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.gmail.andreasmartinmoerch.danandchat.DanAndChat;
import com.gmail.andreasmartinmoerch.danandchat.channel.Channel;
import com.gmail.andreasmartinmoerch.danandchat.plugins.PermissionChecker;

public class ChangeCommand implements CommandExecutor {
	
	private DanAndChat plugin;
	
	public ChangeCommand(DanAndChat plugin){
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		if (!(sender instanceof Player)){
			return true;
		}
		Player player = (Player)sender;
		if (!player.hasPermission(PermissionChecker.prefix + PermissionChecker.changeChannel)){
			player.sendMessage(ChatColor.RED + "You haven't got the permission to do that.");
			return true;
		}
		
		if (args.length != 1){
			return false;
		}
		Channel c = null;
		Channel oldFocus = null;
		for (Channel ch: this.plugin.getChannels().channels){
			if (ch.getShortCut().equalsIgnoreCase(args[0])){
				c = ch;
			}
			if (ch.getFocused().contains(player)){
				oldFocus = ch;
				oldFocus.getFocused().remove(player);
			}
		}
		if (c == null){
			player.sendMessage(ChatColor.RED + "Could not find channel with shortcut: " + args[0] + ".");
			if (oldFocus != null){
				oldFocus.getFocused().add(player);
			} else {
				player.sendMessage(ChatColor.RED + "Could not re-join previously focused channel.");				
			}
			return true;
		}
		c.addPlayer(player);
		c.getFocused().add(player);
		return true;
	}

}
