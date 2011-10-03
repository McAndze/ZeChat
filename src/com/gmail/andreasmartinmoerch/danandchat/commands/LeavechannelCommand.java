package com.gmail.andreasmartinmoerch.danandchat.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.gmail.andreasmartinmoerch.danandchat.DanAndChat;
import com.gmail.andreasmartinmoerch.danandchat.channel.Channel;
import com.gmail.andreasmartinmoerch.danandchat.plugins.PermissionChecker;
import com.gmail.andreasmartinmoerch.danandchat.utils.Messages;

public class LeavechannelCommand implements CommandExecutor{
	private DanAndChat plugin;
	
	public LeavechannelCommand(DanAndChat plugin){
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
			player.sendMessage(this.plugin.getMessageGetter().getMessage(Messages.NO_PERMISSION_LEAVECHANNEL));
			return false;
		}
		
		if (args.length == 0){
			return false;
		}
		// End validation.
		Channel c;
		if ((c=plugin.getChannels().getChannelWithShortcut(args[0])) == null){
			player.sendMessage(this.plugin.getMessageGetter().getMessageWithArgs(Messages.COULD_NOT_FIND_CHANNEL_WITH_SHORTCUT, args[0]));
			return true;
		}
		
		if (!c.playerIsInChannel(player)){
			player.sendMessage(this.plugin.getMessageGetter().getMessage(Messages.YOU_ARE_NOT_IN_CHANNEL));
			return true;
		}
		
		c.removePlayer(player);
		if(c.playerIsInChannel(player)){
			player.sendMessage("DEBUG1");
		}
		for (Channel ch: this.plugin.getChannels().channels){
			if (ch.isAutoFocus() && ch.playerIsInChannel(player)){
				player.sendMessage("DEBUG");
				ch.addFocus(player);
			}
		}
		return true;
	}
	
}
