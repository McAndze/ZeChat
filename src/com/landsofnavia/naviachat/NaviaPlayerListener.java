package com.landsofnavia.naviachat;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerQuitEvent;

import com.landsofnavia.naviachat.channel.Channel;
import com.landsofnavia.naviachat.commands.AShortCut;
import com.landsofnavia.naviachat.plugins.PermissionChecker;
import com.landsofnavia.naviachat.utils.Messages;

/**
 * The Player Listener.
 * 
 * @author Andreas
 * 
 */
public class NaviaPlayerListener extends PlayerListener {
	NaviaChat plugin;

	public NaviaPlayerListener(NaviaChat instance) {
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
		if (event.getMessage().startsWith("@")){
			new AShortCut(plugin).shoot(player, event.getMessage());
			event.setCancelled(true);
			return;
		}
		Channel c = this.plugin.getChannels().getFocusedChannel(player);

		if (!player.hasPermission(PermissionChecker.prefix + PermissionChecker.canTalk)) {
			String returnMessage = this.plugin.getMessageGetter().getMessage(Messages.NO_PERMISSION_TO_TALK);
			Navia.getDACPlayer(player).sendMessage(returnMessage);
			return;
		}
		message = event.getMessage();

		if (c != null) {
			c.sendMessage(message, event.getPlayer());
		} else {
			String returnMessage = this.plugin.getMessageGetter().getMessage(Messages.IN_INVALID_CHANNEL);
			Navia.getDACPlayer(player).sendMessage(returnMessage);
		}
	}

	@Override
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		Navia dPlayer = new Navia(player);
		Navia.players.add(dPlayer);
		if (!plugin.getChannels().init.contains(player)) {
			plugin.getChannels().initializePlayer(player);
		}
	}

	@Override
	public void onPlayerQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		Navia.players.remove(Navia.getDACPlayer(player));
		for (Channel c : this.plugin.getChannels().channels) {
			c.removePlayer(player);
			c.getFocused().remove(player.getName());
		}
		plugin.getChannels().init.remove(player);
	}

}
