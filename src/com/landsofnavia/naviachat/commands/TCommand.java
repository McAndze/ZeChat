package com.landsofnavia.naviachat.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.landsofnavia.naviachat.NaviaChat;
import com.landsofnavia.naviachat.plugins.PermissionChecker;

public class TCommand implements CommandExecutor {
	private final NaviaChat plugin;

	public TCommand(NaviaChat plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		String commandName = cmd.getName();

		if (!(sender instanceof Player)) {
			return true;
		}
		Player player = (Player) sender;

		if (!player.hasPermission(PermissionChecker.prefix
				+ PermissionChecker.tell)) {
			player.sendMessage(ChatColor.RED + "You can not use that command.");
			return true;
		}
		if (args.length < 1) {
			return false;
		}

		Player p;
		if ((p = searchPlayer(args[0])) == null) {
			player.sendMessage(ChatColor.RED + "Player does not exist.");
			return true;
		}

		if (!p.isOnline()) {
			player.sendMessage(ChatColor.RED + "That player isn't online.");
			return true;
		}
		int len = args.length;
		String message = "";
		for (int i = 1; i < len; i++) {
			if (i == 1){
				message = message + args[i];
			} else {
				message = message + " " + args[i];
			}
			
		}
		p.sendMessage(ChatColor.GREEN + "From " + player.getName() + ": "
				+ ChatColor.WHITE + message);
		player.sendMessage(ChatColor.YELLOW + "To " + p.getName() + ": "
				+ ChatColor.WHITE + message);
		return true;
	}
	
	public Player searchPlayer(String name){
		for (Player p: this.plugin.getServer().getOnlinePlayers()){
			if (p.getName().equalsIgnoreCase(name)){
				return p;
			}
			if (p.getName().toLowerCase().startsWith(name.toLowerCase())){
				return p;
			}
		}
		return null;
	}

}
