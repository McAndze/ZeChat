package com.gmail.andreasmartinmoerch.danandchat;

import java.util.ArrayList;
import java.util.Collection;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerQuitEvent;

import com.gmail.andreasmartinmoerch.danandchat.channel.Channel;
import com.gmail.andreasmartinmoerch.danandchat.commands.ChCommand;
import com.gmail.andreasmartinmoerch.danandchat.commands.ChannelCommand;
import com.gmail.andreasmartinmoerch.danandchat.commands.Commands;
import com.gmail.andreasmartinmoerch.danandchat.commands.LeavechannelCommand;
import com.gmail.andreasmartinmoerch.danandchat.commands.MeCommand;
import com.gmail.andreasmartinmoerch.danandchat.commands.TCommand;
import com.gmail.andreasmartinmoerch.danandchat.plugins.PermissionChecker;
/**
 * The Player Listener.
 * @author Andreas
 *
 */
public class DanAndChatPlayerListener extends PlayerListener{
	DanAndChat plugin;
	
	public DanAndChatPlayerListener(DanAndChat instance){
		plugin = instance;
	}



	public void onPlayerChat(PlayerChatEvent event){
		Player player = event.getPlayer();
		String message;
		event.setCancelled(true);
		if (!plugin.perms.init.contains(player)){
			plugin.
			perms.
			initializePlayer(player);
			player.sendMessage("hi");
		}
		Channel c = ChannelManager.playerFocused.get(player);
		
		if (!this.plugin.perms.playerHasPermission(player, PermissionChecker.prefix + PermissionChecker.canTalk)){;
			event.getPlayer().sendMessage(ChatColor.RED + "You are not allowed to talk. At all. Or rather; chat.");
			return;
		}
		message = event.getMessage();
		
		// TODO: This is old. Do something about it.
//		if (event.getMessage().startsWith(".")){
//			message = event.getMessage().substring(1);
//			ChannelManager.setPlayerState(event.getPlayer(), false);
//		} else {
//			message = event.getMessage();
//			ChannelManager.setPlayerState(event.getPlayer(), true);
//		}
		
		if (c != null){
			c.sendMessage(message, event.getPlayer());
		} else {
			event.getPlayer().sendMessage(ChatColor.RED + "You're in an invalid channel. Try your luck with \"/ch g\" or \"/ch l\"");
		}
		
	}
	
	

	@Override
	public void onPlayerJoin(PlayerJoinEvent event) {
	}

	@Override
	public void onPlayerQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		
		for (Channel c: ChannelManager.channels){
			c.removePlayer(player);
		}
		plugin.perms.init.remove(player);
		ChannelManager.playerFocused.remove(player);
	}
	
	
	
}
