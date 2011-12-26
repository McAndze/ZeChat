package com.gmail.andreasmartinmoerch.danandchat.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.gmail.andreasmartinmoerch.danandchat.DanAndChat;
import com.gmail.andreasmartinmoerch.danandchat.channel.Channel;
import com.gmail.andreasmartinmoerch.danandchat.plugins.PermissionChecker;
import com.gmail.andreasmartinmoerch.danandchat.utils.Messages;

public class ChangeCommand implements CommandExecutor {
	
	private DanAndChat plugin;
	
	public ChangeCommand(DanAndChat plugin){
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		if (!(sender instanceof Player)){
			return true;
		}
		Player player = (Player)sender;
		if (!player.hasPermission(PermissionChecker.prefix + PermissionChecker.changeChannel)){
			player.sendMessage(this.plugin.getMessageGetter().getMessage(Messages.NO_PERMISSION_CHANGE_CHANNEL));
			return true;
		}
		
		if (args.length == 1){
			return change(player, args);
		} else if (args.length > 1){
			return shortcut(player, args);
		} else {
			return false;
		}
		
	}
	
	public boolean change(Player player, String[] args){
		Channel c = null;
		Channel oldFocus = null;
		
		
		for (Channel ch: this.plugin.getChannels().channels){
			if (ch.getShortCut().equalsIgnoreCase(args[0])){
				c = ch;
			}
			if (ch.getFocused().contains(player.getName())){
				oldFocus = ch;
				oldFocus.removeFocus(player);
			}
		}
		if (c == null){
			player.sendMessage(this.plugin.getMessageGetter().getMessageWithArgs(Messages.COULD_NOT_FIND_CHANNEL_WITH_SHORTCUT, args[0]));
			if (oldFocus != null){
				oldFocus.addFocus(player);
			} else {
				player.sendMessage(this.plugin.getMessageGetter().getMessage(Messages.COULD_NOT_REJOIN_FOCUSED_CHANNEL));				
			}
			return true;
		}
		if (!c.playerIsInChannel(player)){
			c.addPlayer(player);
		}
		
		c.addFocus(player);
		return true;
	}
	
	public boolean shortcut(Player player, String[] args){
		Channel c = null;
		
		for (Channel ch: this.plugin.getChannels().channels){
			if (ch.getShortCut().equalsIgnoreCase(args[0])){
				c = ch;
			}
		}
		
		if (c == null){
			player.sendMessage(this.plugin.getMessageGetter().getMessageWithArgs(Messages.COULD_NOT_FIND_CHANNEL_WITH_SHORTCUT, args[0]));
			return true;
		}
		
		if (!c.playerIsInChannel(player)){
			c.addPlayer(player);
		}
		
		String message  = "";
		for (int i = 1; i <= args.length; i++){
			message += args[i];
		}
		
		c.sendMessage(message, player);
		
		return true;
	}

}
