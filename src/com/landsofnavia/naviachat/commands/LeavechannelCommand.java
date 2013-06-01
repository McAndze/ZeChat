package com.landsofnavia.naviachat.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.landsofnavia.naviachat.NaviaChat;
import com.landsofnavia.naviachat.channel.Channel;
import com.landsofnavia.naviachat.plugins.PermissionChecker;
import com.landsofnavia.naviachat.utils.Message;

public class LeavechannelCommand implements CommandExecutor{
	private NaviaChat plugin;
	
	public LeavechannelCommand(NaviaChat plugin){
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		String commandName = cmd.getName();
		
		if (!(sender instanceof Player)){
			return true;
		}
		Player player = (Player)sender;
		// Start validation
		if (!player.hasPermission(PermissionChecker.prefix + PermissionChecker.leaveChannel)){
			player.sendMessage(this.plugin.getMessageGetter().getMessage(Message.NO_PERMISSION_LEAVECHANNEL));
			return false;
		}
		
		if (args.length == 0){
			return false;
		}
		// End validation.
		Channel c;
		if ((c=plugin.getChannels().getChannelWithShortcut(args[0])) == null){
			player.sendMessage(this.plugin.getMessageGetter().getMessageWithArgs(Message.COULD_NOT_FIND_CHANNEL_WITH_SHORTCUT, args[0]));
			return true;
		}
		
		if (!c.playerIsInChannel(player)){
			player.sendMessage(this.plugin.getMessageGetter().getMessage(Message.YOU_ARE_NOT_IN_CHANNEL));
			return true;
		}
		
		c.removePlayer(player);
		for (Channel ch: this.plugin.getChannels().channels){
			if (ch.isAutoFocus() && ch.playerIsInChannel(player)){
				ch.addFocus(player);
			}
		}
		return true;
	}
	
}
