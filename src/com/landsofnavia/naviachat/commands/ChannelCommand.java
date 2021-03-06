package com.landsofnavia.naviachat.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.landsofnavia.naviachat.NaviaChat;
import com.landsofnavia.naviachat.channel.Channel;
import com.landsofnavia.naviachat.plugins.PermissionChecker;
import com.landsofnavia.naviachat.utils.Message;

public class ChannelCommand implements CommandExecutor {
	public enum ChannelArgs {
		BAN, UNBAN, LIST, WRITE, RELOAD, MUTE, UNMUTE, RELOADCHANNEL;
	}
	private final NaviaChat plugin;

	public ChannelCommand(NaviaChat plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {

		if (args.length < 1) {
			return false;
		}

		ChannelArgs ca;
		try {
			ca = ChannelArgs.valueOf(args[0].toUpperCase());
		} catch (Exception e) {
			return false;
		}

		if (!(sender instanceof Player)) {
			if (args.length < 2) {
				return false;
			}
			switch (ca) {
			case WRITE:
				return writeFile(args);
			case RELOAD:
				return reload(sender, args);
			default:
				sender.sendMessage(this.plugin.getMessageGetter().getMessageWithArgs(Message.UNKNOWN_ARG, ca.toString()));
				return false;
			}
		} else {
			Player player = (Player) sender;

			switch (ca) {
			case BAN:
				return banPlayer(player, args);
			case UNBAN:
				return unbanPlayer(player, args);
			case LIST:
				return listChannels(player, args);
			case WRITE:
				return writeFile(args);
			case MUTE:
				return mutePlayer(player, args);
			case UNMUTE:
				return unmutePlayer(player, args);
			default:
				return false;
			}
		}
	}

	public boolean reload(CommandSender sender, String[] args) {
		/*if (args.length < 3) {
			return false;
		}
		if (sender instanceof Player) {
			if (!((Player) sender).hasPermission("danandchat.channel.reload")) {
				sender.sendMessage(this.plugin.getMessageGetter().getMessage(Message.NO_PERMISSION_CHANNEL_RELOAD));
				return true;
			}
		}
		if (args[2].toLowerCase().startsWith("prefix")) {
			this.plugin.getSettings().prefixConfig.load();
			sender.sendMessage(ChatColor.GREEN + "Reloaded " + ChatColor.GOLD
					+ "databases.yml");
			return true;
		} else if (args[2].toLowerCase().startsWith("channe")) {
			this.plugin.getSettings().prefixConfig.load();
			sender.sendMessage(ChatColor.GREEN + "Reloaded " + ChatColor.GOLD
					+ "channels.yml");
			return true;

		} else if (args[2].toLowerCase().startsWith("conf")) {
			this.plugin.getSettings().prefixConfig.load();
			sender.sendMessage(ChatColor.GREEN + "Reloaded " + ChatColor.GOLD
					+ "config.yml");
			return true;

		}

		return false;*/
		
		sender.sendMessage("not supported");
		return true;
	}

	public boolean mutePlayer(Player player, String[] args) {
		if (!player.hasPermission(PermissionChecker.prefix
				+ PermissionChecker.channel + ".mute")) {
			player.sendMessage(this.plugin.getMessageGetter().getMessage(Message.NO_PERMISSION_CHANNEL_MUTE));
			return true;
		}

		if (args.length == 2) {
			Player victim = this.plugin.getServer().getPlayer(args[1]);
			Channel c = this.plugin.getChannels().getFocusedChannel(player);
			c.mutePlayer(args[1]);
			if (victim != null && victim.isOnline()) {
				victim.sendMessage(this.plugin.getMessageGetter().getMessageWithArgs(Message.YOU_HAVE_BEEN_MUTED, c.getName()));
			}
			return true;
		} else {
			return false;
		}
	}

	public boolean unmutePlayer(Player player, String[] args) {
		if (!player.hasPermission(PermissionChecker.prefix
				+ PermissionChecker.channel + ".unmute")) {
			player.sendMessage(this.plugin.getMessageGetter().getMessage(Message.NO_PERMISSION_CHANNEL_UNMUTE));
			return true;
		}
		if (args.length == 2) {
			Player victim = this.plugin.getServer().getPlayer(args[1]);
			Channel c = this.plugin.getChannels().getFocusedChannel(victim);
			c.unmutePlayer(args[1]);
			if (victim.isOnline()) {
				victim.sendMessage(this.plugin.getMessageGetter().getMessageWithArgs(Message.YOU_HAVE_BEEN_UNMUTED, c.getName()));
			}
			return true;
		} else {
			return false;
		}
	}

	// <CHANNEL BAN>
	public boolean banPlayer(Player player, String[] args) {
		if (!player.hasPermission(PermissionChecker.prefix
				+ PermissionChecker.channel + PermissionChecker.ban)) {
			player.sendMessage(this.plugin.getMessageGetter().getMessage(Message.NO_PERMISSION_CHANNEL_BAN));
			return true;
		}

		if (args.length == 2) {
			Player victim = this.plugin.getServer().getPlayer(args[1]);
			Channel c = this.plugin.getChannels().getFocusedChannel(player);
			c.banPlayer(args[1]);
			if (victim != null && victim.isOnline()) {
				victim.sendMessage(this.plugin.getMessageGetter().getMessageWithArgs(Message.YOU_HAVE_BEEN_BANNED, c.getName()));
			}
			return true;
		} else {
			return false;
		}
	}

	// END <CHANNEL BAN>

	// START <CHANNEL UNBAN>
	public boolean unbanPlayer(Player player, String[] args) {
		if (!player.hasPermission(PermissionChecker.prefix
				+ PermissionChecker.channel + PermissionChecker.unban)) {
			player.sendMessage(ChatColor.RED + "Unknown command.");
			return true;
		}
		if (args.length == 2) {
			Player victim = this.plugin.getServer().getPlayer(args[1]);
			Channel c = this.plugin.getChannels().getFocusedChannel(victim);
			c.unbanPlayer(args[1]);
			if (victim.isOnline()) {
				victim.sendMessage(this.plugin.getMessageGetter().getMessageWithArgs(Message.YOU_HAVE_BEEN_UNBANNED, c.getName()));
			}
			return true;
		} else {
			return false;
		}
	}

	// END <CHANNEL UNBAN>

	// START <CHANNEL LIST>
	public boolean listChannels(Player player, String[] args) {
		if ((args.length == 2 && args[1].equalsIgnoreCase("available"))) {
			if (!player.hasPermission(PermissionChecker.prefix
					+ PermissionChecker.channel + PermissionChecker.list
					+ PermissionChecker.available)) {
				player.sendMessage(this.plugin.getMessageGetter().getMessage(Message.NO_PERMISSION_CHANNEL_LIST_AVAILABLE));
				return true;
			}
			player.sendMessage(ChatColor.GREEN
					+ "===Channels available to you===");
			player.sendMessage(ChatColor.GREEN + "Syntax: " + ChatColor.GRAY
					+ "<ChannelName> <Channel Shortcut> |");
			String list = ChatColor.YELLOW + "Channels: ";

			for (Channel c : this.plugin.getChannels().channels) {
				if (!c.isHidden() && !c.getBanned().contains(player)) {
					list += ChatColor.GREEN + c.getName() + " "
							+ c.getShortCut() + ChatColor.RED + " | ";
				}
			}
			player.sendMessage(list);
		} else if (args.length == 2 && args[1].equalsIgnoreCase("in")
				|| args.length == 1) {
			if (!player.hasPermission(PermissionChecker.prefix
					+ PermissionChecker.channel + PermissionChecker.list
					+ PermissionChecker.in)) {
				player.sendMessage(this.plugin.getMessageGetter().getMessage(Message.NO_PERMISSION_CHANNEL_LIST_IN));
				return true;
			}
			player.sendMessage(ChatColor.GREEN + "===Channels you're in===");
			player.sendMessage(ChatColor.GREEN + "Syntax: " + ChatColor.GRAY
					+ "<ChannelName> <Channel Shortcut> |");
			String list = ChatColor.YELLOW + "Channels: ";

			for (Channel c : this.plugin.getChannels().channels) {
				if (c.playerIsInChannel(player)) {
					list += ChatColor.GREEN + c.getName() + " "
							+ c.getShortCut() + ChatColor.RED + " | ";
				}
			}
			player.sendMessage(list);
		} else {
			return false;
		}

		return true;
	}

	// END <CHANNEL LIST>

	public boolean writeFile(String[] args) {
		if (args[1].equals("all")) {
			for (Channel c : this.plugin.getChannels().channels) {
				c.getChLogger().writeToFile();
			}
			return true;
		} else {
			for (Channel c : this.plugin.getChannels().channels) {
				if (args[1].equalsIgnoreCase(c.getName())) {
					c.getChLogger().writeToFile();
					return true;
				}
			}
		}
		this.plugin.getDanandLogger().logMsg(Message.COULD_NOT_FIND_CHANNEL, "WARNING", args[1]);
		return true;
	}

}
