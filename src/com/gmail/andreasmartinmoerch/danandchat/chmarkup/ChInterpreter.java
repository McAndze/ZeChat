package com.gmail.andreasmartinmoerch.danandchat.chmarkup;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.print.attribute.standard.Severity;

import me.samkio.RPGWorld.RPGWorldPlugin;
import me.samkio.RPGWorld.ranks.RankManager;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachmentInfo;

import com.gmail.andreasmartinmoerch.danandchat.DanAndChat;
import com.gmail.andreasmartinmoerch.danandchat.channel.Channel;

public class ChInterpreter {
	
	private DanAndChat plugin;
	public ChInterpreter(DanAndChat plugin){
		this.plugin = plugin;
	}
	public String interpretString(String format, final Channel channel, final Player player, final String message){
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
	
	public String channelKey(String format, final Channel channel){
		// TODO: Add checks, so we don't have to call more than necessary.
		
		format = format.replaceAll("&" + ChKey.CHANNEL.toString() + "." + ChannelArgs.NAME.toString(), channel.getName());
		format = format.replaceAll("&" + ChKey.CHANNEL.toString() + "." + ChannelArgs.SHORTCUT.toString(), channel.getShortCut());
		format = format.replaceAll("&" + ChKey.CHANNEL.toString() + "." + ChannelArgs.SHORTNAME.toString(), channel.getShortCut());
		return format;
	}
	
	public String colorKey(String format){
		// TODO: Add checks, so we don't have to call more than necessary.
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
	
	public String playerKey(String format, Player player){
		// TODO: Add checks, so we don't have to call more than necessary.
		if (this.plugin.exManager.usesPermissionsBukkit() && format.contains("&" + ChKey.PLAYER.toString() + "." + PlayerArgs.GROUP.toString())){
			String group = this.plugin.exManager.permissionsBukkit.getGroup(player.getName()).getName();
			if (group == null){
				group = "";
			}
			format = format.replaceAll("&" + ChKey.PLAYER.toString() + "." + PlayerArgs.GROUP.toString(), group);
		} else {
			format = format.replaceAll("&" + ChKey.PLAYER.toString() + "." + PlayerArgs.GROUP.toString(), "");
		}
		
		format = format.replaceAll("&" + ChKey.PLAYER.toString() + "." + PlayerArgs.NAME.toString(), player.getName());
		
		if (this.plugin.exManager.isUsingColorMe()){
			format = format.replaceAll("&" + ChKey.PLAYER.toString() + "." + PlayerArgs.COLOUR.toString(), this.plugin.exManager.color.findColor(this.plugin.exManager.color.getColor(player.getName())));
		} else {
			format = format.replaceAll("&" + ChKey.PLAYER.toString() + "." + PlayerArgs.COLOUR.toString(), "");
		}
		if(this.plugin.exManager.usesRPGWorld()){
			if (RPGWorldPlugin.useRanksAsPrefix()){
				format = format.replaceAll("&" + ChKey.PLAYER.toString() + "." + PlayerArgs.RPGPREFIX, RankManager.getPlayerRank(player.getName()));
			} else {
				format = format.replaceAll("&" + ChKey.PLAYER.toString() + "." + PlayerArgs.RPGPREFIX, "");
			}
		} else {
			format = format.replaceAll("&" + ChKey.PLAYER.toString() + "." + PlayerArgs.RPGPREFIX, "");
		}
		
		format = format.replaceAll("&" + ChKey.PLAYER.toString() + "." + PlayerArgs.HEALTH, healthToString(player)); 
		format = format.replaceAll("&" + ChKey.PLAYER.toString() + "." + PlayerArgs.DISPLAYNAME.toString(), player.getDisplayName());
		format = format.replaceAll("&" + ChKey.PLAYER.toString() + "." + PlayerArgs.PREFIX.toString(), this.plugin.prefixer.getPrefix(player));
		return format;
	}
	
//	private String replace(String format, String sOld, Method method, Object... args){
//	
//	String toReturn = "";
//	if (!format.contains(sOld)){
//		return format;
//	} else {
//		try {
//			// TODO: Find a better way to handle method args.
//			String replace;
//			if (args == null){
//				replace = (String)method.invoke();
//			} else {
//			String replace = (String)method.invoke(args);
//			toReturn = format.replaceAll(sOld, replace);
//		} catch (IllegalArgumentException i){
//			this.plugin.log.severe("[DanAndChat] Illegal arguments passed to " + method.getName() + "! Report to McAndze/Huliheaden.");
//			i.printStackTrace();
//		} catch (IllegalAccessException e) {
//			this.plugin.log.severe("[DanAndChat] Illegal access from " + method.getName() + "! Report to McAndze/Huliheaden.");
//			e.printStackTrace();
//		} catch (InvocationTargetException e) {
//			e.printStackTrace();
//		}
//	}
//	return toReturn;
//}
	
	private String healthToString(Player player){
		int health = 0;
		String s = "";
		
		for (health = 0; health < 20; health++){
			if (health > player.getHealth()){
				s += ChatColor.RED + "|";
			} else {
				s += ChatColor.GREEN + "|";
			}
		}
		s += ChatColor.WHITE;
		return s;
	}
}
