package com.gmail.andreasmartinmoerch.danandchat;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerQuitEvent;

import com.gmail.andreasmartinmoerch.danandchat.DanAndChat;
import com.gmail.andreasmartinmoerch.danandchat.channel.Channel;
import com.gmail.andreasmartinmoerch.danandchat.plugins.PermissionChecker;
import com.gmail.andreasmartinmoerch.danandchat.utils.Messages;

/**
 * The Player Listener.
 * 
 * @author Andreas
 * 
 */
public class DanAndChatPlayerListener extends PlayerListener {
	DanAndChat plugin;

	public DanAndChatPlayerListener(DanAndChat instance) {
		plugin = instance;
	}

	@Override
	public void onPlayerChat(PlayerChatEvent event) {
		if (event.isCancelled()) {
			return;
		}
		onChat(event);

	}

	public void onChat(PlayerChatEvent event) {
		Player player = event.getPlayer();
		String message;
		event.setCancelled(true);
		Channel c = this.plugin.getChannels().getFocusedChannel(player);

		if (!player.hasPermission(PermissionChecker.prefix + PermissionChecker.canTalk)) {
			String returnMessage = this.plugin.getMessageGetter().getMessage(Messages.NO_PERMISSION_TO_TALK);
			player.sendMessage(returnMessage);
			return;
		}
		message = event.getMessage();

		// TODO: This is old. Do something about it.
		// if (event.getMessage().startsWith(".")){
		// message = event.getMessage().substring(1);
		// ChannelManager.setPlayerState(event.getPlayer(), false);
		// } else {
		// message = event.getMessage();
		// ChannelManager.setPlayerState(event.getPlayer(), true);
		// }

		if (c != null) {
			c.sendMessage(message, event.getPlayer());
		} else {
			String returnMessage = this.plugin.getMessageGetter().getMessage(Messages.IN_INVALID_CHANNEL);
			player.sendMessage(returnMessage);
		}
	}

	@Override
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		if (!plugin.getChannels().init.contains(player)) {
			plugin.getChannels().initializePlayer(player);
		}
	}

	@Override
	public void onPlayerQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();

		for (Channel c : this.plugin.getChannels().channels) {
			c.removePlayer(player);
			c.getFocused().remove(player);
		}
		plugin.getChannels().init.remove(player);
	}

}
