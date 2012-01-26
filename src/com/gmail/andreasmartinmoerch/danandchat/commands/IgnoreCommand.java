package com.gmail.andreasmartinmoerch.danandchat.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.gmail.andreasmartinmoerch.danandchat.DanAndChat;

public class IgnoreCommand implements CommandExecutor {
	
	private DanAndChat plugin;
	
	public IgnoreCommand(DanAndChat plugin){
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		if (!(sender instanceof Player)){
			return true;
		}
		Player player = (Player)sender;
		
		if (args.length == 1){
			return ignore(player, args);
		} else {
			return false;
		}
	}
	
	public synchronized boolean ignore(Player player, String[] args){
		List<String> strings = this.plugin.getSettings().prefixConfig.getStringList("database.players."+player.getName().toLowerCase()+".ignore", new ArrayList<String>());
		
		boolean unIgnore = false;
		for (String s: strings){
			if (s.equalsIgnoreCase(args[0])){
				strings.remove(s);
				unIgnore = true;
			}
		}
		if (!unIgnore){
			strings.add(args[0]);
		}
		this.plugin.getSettings().prefixConfig.setProperty("database.players."+player.getName().toLowerCase()+".ignore", strings);
		return true;
	}

}
