package com.landsofnavia.naviachat.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.landsofnavia.naviachat.NaviaChat;
import com.landsofnavia.naviachat.channel.Channel;
import com.landsofnavia.naviachat.plugins.PermissionChecker;
import com.landsofnavia.naviachat.utils.Message;

/*
 * This command is for changing channels. 
 */
public class ChangeCommand implements CommandExecutor {
	
	private NaviaChat plugin;
	
	public ChangeCommand(NaviaChat plugin){
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		if (!(sender instanceof Player)){
			return true;
		}
		Player player = (Player)sender;
		
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
			player.sendMessage(this.plugin.getMessageGetter().getMessageWithArgs(Message.COULD_NOT_FIND_CHANNEL_WITH_SHORTCUT, args[0]));
			if (oldFocus != null){
				oldFocus.addFocus(player);
			} else {
				player.sendMessage(this.plugin.getMessageGetter().getMessage(Message.COULD_NOT_REJOIN_FOCUSED_CHANNEL));				
			}
			return true;
		}
		if (!player.hasPermission(PermissionChecker.prefix + PermissionChecker.changeChannel + "." + c.getName().toLowerCase())){
			player.sendMessage(this.plugin.getMessageGetter().getMessage(Message.NO_PERMISSION_CHANGE_CHANNEL));
			return true;
		}
		
		if (!c.playerIsInChannel(player)){
			c.addPlayer(player);
		}
		
		c.addFocus(player);
		return true;
	}
	
	/*
	 * Example of how this works:
	 * Command sent:
	 * \ch g Hi everyone!
	 * This sends the message 'Hi everyone!' to the channel with shortcut 'g'.
	 */
	public boolean shortcut(Player player, String[] args){
		Channel c = null;
		
		for (Channel ch: this.plugin.getChannels().channels){
			if (ch.getShortCut().equalsIgnoreCase(args[0])){
				c = ch;
			}
		}
		
		if (c == null){
			player.sendMessage(this.plugin.getMessageGetter().getMessageWithArgs(Message.COULD_NOT_FIND_CHANNEL_WITH_SHORTCUT, args[0]));
			return true;
		}
		if (!player.hasPermission(PermissionChecker.prefix + PermissionChecker.changeChannel + "." + c.getName().toLowerCase())){
			player.sendMessage(this.plugin.getMessageGetter().getMessage(Message.NO_PERMISSION_CHANGE_CHANNEL));
			return true;
		}
		
		if (!c.playerIsInChannel(player)){
			c.addPlayer(player);
		}
		
		String message  = "";
		for (int i = 1; i + 1 <= args.length; i++){
			message += args[i] + " ";
		}
		
		c.sendMessage(message, player);
		
		return true;
	}

}
