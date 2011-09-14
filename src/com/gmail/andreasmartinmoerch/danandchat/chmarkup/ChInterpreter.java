package com.gmail.andreasmartinmoerch.danandchat.chmarkup;

import org.bukkit.entity.Player;

import com.gmail.andreasmartinmoerch.danandchat.channel.Channel;
import com.gmail.andreasmartinmoerch.danandchat.plugins.ExtensionManager;
import com.gmail.andreasmartinmoerch.danandchat.plugins.PermissionChecker;

public class ChInterpreter {
	public static String interpretString(String format, final Channel channel, final Player player, final String message){
		if (format.contains("&" + ChKey.CHANNEL.toString() + ".")){
			format = channelKey(format, channel);
		}
		if (format.contains("&" + ChKey.COLOUR.toString() + ".")){
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
		format = format.replaceAll("&" + ChKey.CHANNEL.toString() + "." + ChannelArgs.COLOUR.toString(), channel.getColor().getStrPresentation());
		format = format.replaceAll("&" + ChKey.CHANNEL.toString() + "." + ChannelArgs.NAME.toString(), channel.getName());
		format = format.replaceAll("&" + ChKey.CHANNEL.toString() + "." + ChannelArgs.SHORTCUT.toString(), channel.getShortCut());
		format = format.replaceAll("&" + ChKey.CHANNEL.toString() + "." + ChannelArgs.SHORTNAME.toString(), channel.getShortCut());
		return format;
	}
	
	public static String colorKey(String format){
		format = format.replaceAll("&" + ChKey.COLOUR.toString() + "." + ChatColour.AQUA.toString(), ChatColour.AQUA.getStrPresentation());
		format = format.replaceAll("&" + ChKey.COLOUR.toString() + "." + ChatColour.BLACK.toString(), ChatColour.BLACK.getStrPresentation());
		format = format.replaceAll("&" + ChKey.COLOUR.toString() + "." + ChatColour.BLUE.toString(), ChatColour.BLUE.getStrPresentation());
		format = format.replaceAll("&" + ChKey.COLOUR.toString() + "." + ChatColour.DARK_AQUA.toString(), ChatColour.DARK_AQUA.getStrPresentation());
		format = format.replaceAll("&" + ChKey.COLOUR.toString() + "." + ChatColour.DARK_BLUE.toString(), ChatColour.DARK_BLUE.getStrPresentation());
		format = format.replaceAll("&" + ChKey.COLOUR.toString() + "." + ChatColour.DARK_GRAY.toString(), ChatColour.DARK_GRAY.getStrPresentation());
		format = format.replaceAll("&" + ChKey.COLOUR.toString() + "." + ChatColour.DARK_GREEN.toString(), ChatColour.DARK_GREEN.getStrPresentation());
		format = format.replaceAll("&" + ChKey.COLOUR.toString() + "." + ChatColour.DARK_PURPLE.toString(), ChatColour.DARK_PURPLE.getStrPresentation());
		format = format.replaceAll("&" + ChKey.COLOUR.toString() + "." + ChatColour.DARK_RED.toString(), ChatColour.DARK_RED.getStrPresentation());
		format = format.replaceAll("&" + ChKey.COLOUR.toString() + "." + ChatColour.GOLD.toString(), ChatColour.GOLD.getStrPresentation());
		format = format.replaceAll("&" + ChKey.COLOUR.toString() + "." + ChatColour.GRAY.toString(), ChatColour.GRAY.getStrPresentation());
		format = format.replaceAll("&" + ChKey.COLOUR.toString() + "." + ChatColour.GREEN.toString(), ChatColour.GREEN.getStrPresentation());
		format = format.replaceAll("&" + ChKey.COLOUR.toString() + "." + ChatColour.LIGHT_PURPLE.toString(), ChatColour.LIGHT_PURPLE.getStrPresentation());
		format = format.replaceAll("&" + ChKey.COLOUR.toString() + "." + ChatColour.RED.toString(), ChatColour.RED.getStrPresentation());
		format = format.replaceAll("&" + ChKey.COLOUR.toString() + "." + ChatColour.WHITE.toString(), ChatColour.WHITE.getStrPresentation());
		format = format.replaceAll("&" + ChKey.COLOUR.toString() + "." + ChatColour.YELLOW.toString(), ChatColour.YELLOW.getStrPresentation());
		return format;
	}
	
	public static String playerKey(String format, Player player){
		format = format.replaceAll("&" + ChKey.PLAYER.toString() + "." + PlayerArgs.GROUP.toString(), PermissionChecker.getGroup(player));
		format = format.replaceAll("&" + ChKey.PLAYER.toString() + "." + PlayerArgs.NAME.toString(), player.getName());
		if (ExtensionManager.isUsingColorMe()){
			format = format.replaceAll("&" + ChKey.PLAYER.toString() + "." + PlayerArgs.COLOUR.toString(), ExtensionManager.color.getColor(player.getName()));
		}
//		format = format.replaceAll("&" + ChKey.PLAYER.toString() + "." + PlayerArgs.PREFIX.toString() + "\\\\}", PermissionChecker.getPrefix(player));
		return format;
	}
}
