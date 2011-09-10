package com.gmail.andreasmartinmoerch.danandchat.chmarkup;

import org.bukkit.entity.Player;

import com.gmail.andreasmartinmoerch.danandchat.channel.Channel;
import com.gmail.andreasmartinmoerch.danandchat.plugins.PermissionChecker;

public class ChInterpreter {
	public static String interpretString(String format, final Channel channel, final Player player, final String message){
		if (format.contains("&" + ChKey.CHANNEL.toString() + ".")){
			format = channelKey(format, channel);
		}
		if (format.contains("&" + ChKey.COLOR.toString() + ".")){
			format = colorKey(format);
		}
		if (format.contains("&" + ChKey.PLAYER.toString() + ".")){
			format = playerKey(format, player);
		}
		
		format = format.replaceAll("&&", "&");
		format = format.replaceAll("&MESSAGE", message);
		
		return format;
	}
	
	public static String channelKey(String format, final Channel channel){
		format = format.replaceAll("&" + ChKey.CHANNEL.toString() + "." + ChannelArgs.COLOR.toString(), channel.getColor().getStrPresentation());
		format = format.replaceAll("&" + ChKey.CHANNEL.toString() + "." + ChannelArgs.NAME.toString(), channel.getName());
		format = format.replaceAll("&" + ChKey.CHANNEL.toString() + "." + ChannelArgs.SHORTCUT.toString(), channel.getShortCut());
		format = format.replaceAll("&" + ChKey.CHANNEL.toString() + "." + ChannelArgs.SHORTNAME.toString(), channel.getShortCut());
		return format;
	}
	
	public static String colorKey(String format){
		format = format.replaceAll("&" + ChKey.COLOR.toString() + "." + ChatColor.AQUA.toString(), ChatColor.AQUA.getStrPresentation());
		format = format.replaceAll("&" + ChKey.COLOR.toString() + "." + ChatColor.BLACK.toString(), ChatColor.BLACK.getStrPresentation());
		format = format.replaceAll("&" + ChKey.COLOR.toString() + "." + ChatColor.BLUE.toString(), ChatColor.BLUE.getStrPresentation());
		format = format.replaceAll("&" + ChKey.COLOR.toString() + "." + ChatColor.DARK_AQUA.toString(), ChatColor.DARK_AQUA.getStrPresentation());
		format = format.replaceAll("&" + ChKey.COLOR.toString() + "." + ChatColor.DARK_BLUE.toString(), ChatColor.DARK_BLUE.getStrPresentation());
		format = format.replaceAll("&" + ChKey.COLOR.toString() + "." + ChatColor.DARK_GRAY.toString(), ChatColor.DARK_GRAY.getStrPresentation());
		format = format.replaceAll("&" + ChKey.COLOR.toString() + "." + ChatColor.DARK_GREEN.toString(), ChatColor.DARK_GREEN.getStrPresentation());
		format = format.replaceAll("&" + ChKey.COLOR.toString() + "." + ChatColor.DARK_PURPLE.toString(), ChatColor.DARK_PURPLE.getStrPresentation());
		format = format.replaceAll("&" + ChKey.COLOR.toString() + "." + ChatColor.DARK_RED.toString(), ChatColor.DARK_RED.getStrPresentation());
		format = format.replaceAll("&" + ChKey.COLOR.toString() + "." + ChatColor.GOLD.toString(), ChatColor.GOLD.getStrPresentation());
		format = format.replaceAll("&" + ChKey.COLOR.toString() + "." + ChatColor.GRAY.toString(), ChatColor.GRAY.getStrPresentation());
		format = format.replaceAll("&" + ChKey.COLOR.toString() + "." + ChatColor.GREEN.toString(), ChatColor.GREEN.getStrPresentation());
		format = format.replaceAll("&" + ChKey.COLOR.toString() + "." + ChatColor.LIGHT_PURPLE.toString(), ChatColor.LIGHT_PURPLE.getStrPresentation());
		format = format.replaceAll("&" + ChKey.COLOR.toString() + "." + ChatColor.RED.toString(), ChatColor.RED.getStrPresentation());
		format = format.replaceAll("&" + ChKey.COLOR.toString() + "." + ChatColor.WHITE.toString(), ChatColor.WHITE.getStrPresentation());
		format = format.replaceAll("&" + ChKey.COLOR.toString() + "." + ChatColor.YELLOW.toString(), ChatColor.YELLOW.getStrPresentation());
		return format;
	}
	
	public static String playerKey(String format, Player player){
		format = format.replaceAll("&" + ChKey.PLAYER.toString() + "." + PlayerArgs.GROUP.toString(), PermissionChecker.getGroup(player));
		format = format.replaceAll("&" + ChKey.PLAYER.toString() + "." + PlayerArgs.NAME.toString(), player.getName());
//		format = format.replaceAll("&" + ChKey.PLAYER.toString() + "." + PlayerArgs.PREFIX.toString() + "\\\\}", PermissionChecker.getPrefix(player));
		return format;
	}
}
