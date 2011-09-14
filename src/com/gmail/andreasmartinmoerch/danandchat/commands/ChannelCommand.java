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

public class ChannelCommand implements CommandExecutor{
	private DanAndChat plugin;
	
	public ChannelCommand(DanAndChat plugin){
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
		
		if (args.length < 1){
			return false;
		}
		
		ChannelArgs ca;
		try {
			ca = ChannelArgs.valueOf(args[0].toUpperCase());
		} catch (Exception e){
			return false;
		}
		
		switch (ca){
		case BAN: return banPlayer(player, args);
		case UNBAN: return unbanPlayer(player, args);
		case LIST: return listChannels(player, args);
		default: return false;
		}
		
		
	}
	
	// <CHANNEL BAN>
	public boolean banPlayer(Player player, String[] args){
		if (!this.plugin.perms.playerHasPermission(player, PermissionChecker.prefix + PermissionChecker.channel + PermissionChecker.ban)){
			player.sendMessage(ChatColor.RED + "Unknown command.");
			return true;
		}
		
		if (args.length == 2){
			Player victim = DanAndChat.server.getPlayer(args[1]);
			Channel c = ChannelManager.getFocusedChannel(player);
//			c.banPlayer(victim);
			if (victim.isOnline()){
				victim.sendMessage(ChatColor.RED + "You have been banned from channel: §" + c.getColor() + c.getName());
			}
			return true;
		} else {
			return false;
		}
	}
	// END <CHANNEL BAN>
	
	// START <CHANNEL UNBAN>
	public boolean unbanPlayer(Player player, String[] args){
		if(!this.plugin.perms.playerHasPermission(player, PermissionChecker.prefix + PermissionChecker.channel + PermissionChecker.unban)){
			player.sendMessage(ChatColor.RED + "Unknown command.");
			return true;
		}
		if (args.length == 2){
			Player victim = DanAndChat.server.getPlayer(args[1]);
			Channel c = ChannelManager.getFocusedChannel(player);
//			c.unbanPlayer(victim);
			if (victim.isOnline()){
				victim.sendMessage(ChatColor.GREEN + "You have been unbanned from channel: §" + c.getColor() + c.getName());
			}
			return true;
		} else {
			return false;
		}
	}
	// END <CHANNEL UNBAN>
	
	// START <CHANNEL LIST>
	public boolean listChannels(Player player, String[] args){
		if ((args.length == 2 && args[1].equalsIgnoreCase("available"))){
			if (!this.plugin.perms.playerHasPermission(player,
					PermissionChecker.prefix 
					+ PermissionChecker.channel 
					+ PermissionChecker.list 
					+ PermissionChecker.available)){
				player.sendMessage(ChatColor.DARK_PURPLE + "You do not have access to that command.");
				return true;
			}
			player.sendMessage(ChatColor.GREEN + "===Channels available to you===");
			player.sendMessage(ChatColor.GREEN + "Syntax: " + ChatColor.GRAY + "<ChannelName> <Channel Shortcut> |");
			String list = ChatColor.YELLOW + "Channels: ";

			for (Channel c: ChannelManager.channels){
				if (!c.isHidden() && !c.getBanned().contains(player)){
					list += ChatColor.GREEN + c.getName() + " " + c.getShortCut() + ChatColor.RED + " | ";
				}
			}
			player.sendMessage(list);
		} else if (args.length == 2 && args[1].equalsIgnoreCase("in") || args.length == 1){
			if (!this.plugin.perms.playerHasPermission(player,
					PermissionChecker.prefix 
					+ PermissionChecker.channel 
					+ PermissionChecker.list 
					+ PermissionChecker.in)){
				player.sendMessage(ChatColor.DARK_PURPLE + "You do not have access to that command.");
				return true;
			}
			player.sendMessage(ChatColor.GREEN + "===Channels you're in===");
			player.sendMessage(ChatColor.GREEN + "Syntax: " + ChatColor.GRAY + "<ChannelName> <Channel Shortcut> |");
			String list = ChatColor.YELLOW + "Channels: ";
			
			for (Channel c: ChannelManager.channels){
				if (c.playerIsInChannel(player)){
					list += ChatColor.GREEN + c.getName() + " " + c.getShortCut() + ChatColor.RED + " | ";
				}
			}
			player.sendMessage(list);
		} else {
			return false;
		}
	
		return true;
	}
	// END <CHANNEL LIST>
	
}
