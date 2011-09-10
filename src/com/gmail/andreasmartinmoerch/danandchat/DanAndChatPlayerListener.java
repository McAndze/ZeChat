package com.gmail.andreasmartinmoerch.danandchat;

import java.util.StringTokenizer;

import org.bukkit.ChatColor;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerListener;

import com.gmail.andreasmartinmoerch.danandchat.channel.Channel;
import com.gmail.andreasmartinmoerch.danandchat.plugins.ExtensionManager;
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
		Channel c = ChannelManager.getFocusedChannel(event.getPlayer());
		String message;
		
		if (!PermissionChecker.playerCanTalk(event.getPlayer())){
			event.setCancelled(true);
			event.getPlayer().sendMessage(ChatColor.RED + "You are not allowed to talk. At all. Or rather; chat.");
			return;
		}
		
		
		// TODO: This is old. Do something about it. Lazy cunt. Talking to you, McAndze.
		if (event.getMessage().startsWith(".")){
			message = event.getMessage().substring(1);
			ChannelManager.setPlayerState(event.getPlayer(), false);
		} else {
			message = event.getMessage();
			ChannelManager.setPlayerState(event.getPlayer(), true);
		}
		
		if (c != null){
			c.sendMessage(message, event.getPlayer());
		} else {
			event.getPlayer().sendMessage(ChatColor.RED + "You're in an invalid channel. Please try joining another.");
		}
		event.setCancelled(true);
		
	}
	
	public void onPlayerJoin(PlayerEvent event){
		ChannelManager.initializePlayerChannels(event.getPlayer());
		
	}
	
}
