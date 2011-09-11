package com.gmail.andreasmartinmoerch.danandchat;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerQuitEvent;

import com.gmail.andreasmartinmoerch.danandchat.channel.Channel;
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
		Channel c = ChannelManager.getFocusedChannel(player);
		String message;
		
		event.setCancelled(true);
		
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
			event.getPlayer().sendMessage(ChatColor.RED + "You're in an invalid channel. Please try writing \"/ch g\"");
		}
		
	}
	
	public void onPlayerJoin(PlayerEvent event){
		//TODO: Temporary...
		ChannelManager.channels.get(0).addPlayer(event.getPlayer());
		ChannelManager.setFocusedChannel(ChannelManager.channels.get(0), event.getPlayer());
		
	}

	@Override
	public void onPlayerQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		
		for (Channel c: ChannelManager.channels){
			c.removePlayer(player);
		}
	}
	
	
	
}
