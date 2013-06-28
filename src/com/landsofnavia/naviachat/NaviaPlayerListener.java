package com.landsofnavia.naviachat;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import com.landsofnavia.naviachat.channel.Channel;
import com.landsofnavia.naviachat.commands.AShortCut;
import com.landsofnavia.naviachat.plugins.PermissionChecker;
import com.landsofnavia.naviachat.utils.Message;

/**
 * The Player Listener.
 * 
 * @author Andreas
 * 
 */
public class NaviaPlayerListener implements Listener {
	final NaviaChat plugin;

	public NaviaPlayerListener(NaviaChat instance) {
		plugin = instance;
	}

	@EventHandler (priority = EventPriority.HIGHEST)
	public void onChat(final AsyncPlayerChatEvent event) {
		if (event.isCancelled()) {
			return;
		}
		event.setCancelled(true);
		
		this.plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new BukkitRunnable() {
			
			@Override
			public void run() {
				System.out.println("Sending message");
				Player player = event.getPlayer();
				String message;
				event.setCancelled(true);
				if (event.getMessage().startsWith("@")){
					new AShortCut(plugin).shoot(player, event.getMessage());
					return;
				}
				Channel c = plugin.getChannels().getFocusedChannel(player);

				if (!player.hasPermission(PermissionChecker.prefix + PermissionChecker.canTalk + "." + c.getName().toLowerCase())){
					player.sendMessage(plugin.getMessageGetter().getMessage(Message.NO_PERMISSION_CHANGE_CHANNEL));
					return;
				}
				message = event.getMessage();

				if (c != null) {
					c.sendMessage(message, event.getPlayer());
				} else {
					String returnMessage = plugin.getMessageGetter().getMessage(Message.IN_INVALID_CHANNEL);
					player.sendMessage(returnMessage);
				}
			}
		});
	}

	@EventHandler (priority = EventPriority.MONITOR)
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		if (!plugin.getChannels().init.contains(player)) {
			plugin.getChannels().initializePlayer(player);
		}
	}

	@EventHandler (priority = EventPriority.MONITOR)
	public void onPlayerQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		for (Channel c : this.plugin.getChannels().channels) {
			c.removePlayer(player);
			c.getFocused().remove(player.getName());
		}
		plugin.getChannels().init.remove(player);
	}

}
