package com.gmail.andreasmartinmoerch.danandchat.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.gmail.andreasmartinmoerch.danandchat.DanAndChat;
import com.gmail.andreasmartinmoerch.danandchat.plugins.PermissionChecker;

public class TCommand implements CommandExecutor{
	private DanAndChat plugin;
	
	public TCommand(DanAndChat plugin){
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
		
		if (!this.plugin.perms.playerHasPermission(player, PermissionChecker.prefix + PermissionChecker.tell)){
			player.sendMessage(ChatColor.RED + "You can not use that command.");
			return true;
		}
		if (args.length < 1){
			return false;
		}
		
		Player p;
		if ((p = DanAndChat.server.getPlayer(args[0])) != null){
			player.sendMessage(ChatColor.RED + "Player does not exist.");
			return true;
		}
		
		if (!(p = DanAndChat.server.getPlayer(args[0])).isOnline()){
			player.sendMessage(ChatColor.RED + "That player isn't online.");
			return true;
		}
		int len = args.length;
		String message = "";
		for (int i = 1; i < len; i++){
			message = message + " " + args[i];
		}
		p.sendMessage(ChatColor.GREEN + "From " + player.getName() + ": " + ChatColor.WHITE + message);
		player.sendMessage(ChatColor.YELLOW + "To " + p.getName() + ": " + message);
		return true;
	}
	
}
