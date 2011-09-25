package com.gmail.andreasmartinmoerch.danandchat.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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
		
		
		if (args.length < 1){
			return false;
		}
		
		ChannelArgs ca;
		try {
			ca = ChannelArgs.valueOf(args[0].toUpperCase());
		} catch (Exception e){
			return false;
		}
		
		if (!(sender instanceof Player)){
			if (args.length < 2){
				return false;
			}
			switch(ca){
			case WRITE: return writeFile(args);
			case RELOAD: return reload(sender, args);
			default: sender.sendMessage("Unknown arg: " + ca.toString());return false;
			}
		} else {
			Player player = (Player)sender;
			
			switch (ca){
			case BAN: return banPlayer(player, args);
			case UNBAN: return unbanPlayer(player, args);
			case LIST: return listChannels(player, args);
			case WRITE: return writeFile(args);
			case MUTE: return mutePlayer(player, args);
			case UNMUTE: return unmutePlayer(player, args);
			default: return false;
			}
		}		
	}
	
	public boolean reload(CommandSender sender, String[] args){
		if (args.length < 3){
			return false;
		}
		if (sender instanceof Player){
			if (!((Player)sender).hasPermission("danandchat.channel.reload")){
				sender.sendMessage(ChatColor.RED + "No permission.");
				return true;
			}
		}
		if (args[2].toLowerCase().startsWith("prefix")){
			this.plugin.settings.prefixConfig.load();
			sender.sendMessage(ChatColor.GREEN + "Reloaded " + ChatColor.GOLD + "prefixes.yml");
			return true;
		} else if(args[2].toLowerCase().startsWith("channe")){
			this.plugin.settings.prefixConfig.load();
			sender.sendMessage(ChatColor.GREEN + "Reloaded " + ChatColor.GOLD + "channels.yml");
			return true;
			
		} else if(args[2].toLowerCase().startsWith("conf")){
			this.plugin.settings.prefixConfig.load();
			sender.sendMessage(ChatColor.GREEN + "Reloaded " + ChatColor.GOLD + "config.yml");
			return true;
			
		}
		
		return false;
	}
	
	public boolean mutePlayer(Player player, String[] args){
		if (!player.hasPermission(PermissionChecker.prefix + PermissionChecker.channel + ".mute")){
			player.sendMessage(ChatColor.RED + "Unknown command.");
			return true;
		}
		
		if (args.length == 2){
			Player victim = DanAndChat.server.getPlayer(args[1]);
			Channel c = this.plugin.channels.getFocusedChannel(player);
			c.mutePlayer(args[1]);
			if (victim != null && victim.isOnline()){
				victim.sendMessage(ChatColor.RED + "You have been muted from channel: " + c.getName());
			}
			return true;
		} else {
			return false;
		}
	}
	
	public boolean unmutePlayer(Player player, String[] args){
		if(!player.hasPermission(PermissionChecker.prefix + PermissionChecker.channel + ".unmute")){
			player.sendMessage(ChatColor.RED + "Unknown command.");
			return true;
		}
		if (args.length == 2){
			Player victim = DanAndChat.server.getPlayer(args[1]);
			Channel c = this.plugin.channels.getFocusedChannel(victim);
			c.unmutePlayer(args[1]);
			if (victim.isOnline()){
				victim.sendMessage(ChatColor.GREEN + "You have been unmuted from channel: " + c.getName());
			}
			return true;
		} else {
			return false;
		}
	}
	
	// <CHANNEL BAN>
	public boolean banPlayer(Player player, String[] args){
		if (!player.hasPermission(PermissionChecker.prefix + PermissionChecker.channel + PermissionChecker.ban)){
			player.sendMessage(ChatColor.RED + "Unknown command.");
			return true;
		}
		
		if (args.length == 2){
			Player victim = DanAndChat.server.getPlayer(args[1]);
			Channel c = this.plugin.channels.getFocusedChannel(player);
			c.banPlayer(args[1]);
			if (victim != null && victim.isOnline()){
				victim.sendMessage(ChatColor.RED + "You have been banned from channel: "+ c.getName());
			}
			return true;
		} else {
			return false;
		}
	}
	// END <CHANNEL BAN>
	
	// START <CHANNEL UNBAN>
	public boolean unbanPlayer(Player player, String[] args){
		if(!player.hasPermission(PermissionChecker.prefix + PermissionChecker.channel + PermissionChecker.unban)){
			player.sendMessage(ChatColor.RED + "Unknown command.");
			return true;
		}
		if (args.length == 2){
			Player victim = DanAndChat.server.getPlayer(args[1]);
			Channel c = this.plugin.channels.getFocusedChannel(victim);
			c.unbanPlayer(args[1]);
			if (victim.isOnline()){
				victim.sendMessage(ChatColor.GREEN + "You have been unbanned from channel: "+ c.getName());
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
			if (!player.hasPermission(
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

			for (Channel c: this.plugin.channels.channels){
				if (!c.isHidden() && !c.getBanned().contains(player)){
					list += ChatColor.GREEN + c.getName() + " " + c.getShortCut() + ChatColor.RED + " | ";
				}
			}
			player.sendMessage(list);
		} else if (args.length == 2 && args[1].equalsIgnoreCase("in") || args.length == 1){
			if (!player.hasPermission(
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
			
			for (Channel c: this.plugin.channels.channels){
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
	
	public boolean writeFile(String[] args){
		if (args[1].equals("all")){
			for (Channel c: this.plugin.channels.channels){
				c.getChLogger().write();
			}
			return true;
		} else {
			for (Channel c: this.plugin.channels.channels){
				if (args[1].equalsIgnoreCase(c.getName())){
					c.getChLogger().write();
					return true;
				}
			}
		}
		this.plugin.log.warning("Could not find channel: " + args[1] + ".");
		return true;
	}
	
}
