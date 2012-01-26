package com.gmail.andreasmartinmoerch.danandchat.commands;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.gmail.andreasmartinmoerch.danandchat.DanAndChat;
import com.gmail.andreasmartinmoerch.danandchat.channel.Channel;

public class AShortCut {
	private DanAndChat plugin;
	
	public AShortCut(DanAndChat instance){
		this.plugin = instance;
	}
	
	public void shoot(Player player, String message){
		String newMessage = "";
		String[] args = message.split(" ");
		for (int i = 1; i + 1 <= args.length; i++){
			newMessage += args[i] + " ";
		}
		
		if(args[0].toLowerCase().contains("ch:")){
			Channel c = searchChannel(args[0].substring(4));
			if (c != null){
				c.sendMessage(newMessage, player);
			}
		} else 
		if (args[0].toLowerCase().contains("pl:")){
			Player p = searchPlayer(args[0].substring(4));
			if (p != null){
				p.sendMessage(ChatColor.GREEN + "From " + player.getName() + ": "
						+ ChatColor.WHITE + newMessage);
				player.sendMessage(ChatColor.YELLOW + "To " + p.getName() + ": "
						+ ChatColor.WHITE + newMessage);
			}
		} else {
			Channel c = searchChannel(args[0].substring(1));
			if (c != null){
				c.sendMessage(newMessage, player);
			} else {
				Player p = searchPlayer(args[0].substring(1));
				if (p != null){
					p.sendMessage(ChatColor.GREEN + "From " + player.getName() + ": "
							+ ChatColor.WHITE + newMessage);
					player.sendMessage(ChatColor.YELLOW + "To " + p.getName() + ": "
							+ ChatColor.WHITE + newMessage);
				}
			}
		}
		
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
	
	public Channel searchChannel(String search){
		for (Channel c: this.plugin.getChannels().channels){
			if (c.getShortCut().equalsIgnoreCase(search)){
				return c;
			}
			if (c.getName().equalsIgnoreCase(search)){
				return c;
			}
			if (c.getName().toLowerCase().startsWith(search.toLowerCase()));
		}
		return null;
	}
}
