package com.gmail.andreasmartinmorch.danandchat.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.gmail.andreasmartinmorch.danandchat.ChannelManager;
import com.gmail.andreasmartinmorch.danandchat.DanAndChat;
import com.gmail.andreasmartinmorch.danandchat.channel.Channel;
import com.gmail.andreasmartinmorch.danandchat.plugins.PermissionChecker;

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
		if (!player.hasPermission(PermissionChecker.prefix + PermissionChecker.leaveChannel)){
			player.sendMessage(ChatColor.RED + "You don't have permission to do that.");
			return false;
		}
		
		if (args.length == 0){
			return false;
		}
		// End validation.
		Channel c;
		if ((c=plugin.channels.getChannelWithShortcut(args[0])) == null){
			player.sendMessage(ChatColor.RED + "Channel does not exist, with shortcut: " + args[0] + ".");
			return true;
		}
		
		if (!c.playerIsInChannel(player)){
			player.sendMessage(ChatColor.RED + "You are not in that channel!");
		}
		
		c.removePlayer(player);
		c.getFocused().remove(player);
		for (Channel ch: this.plugin.channels.channels){
			if (ch.isAutoFocus() && ch.playerIsInChannel(player)){
				ch.getFocused().add(player);
			}
		}
		return true;
	}
	
}
