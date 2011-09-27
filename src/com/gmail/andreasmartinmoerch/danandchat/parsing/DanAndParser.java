package com.gmail.andreasmartinmoerch.danandchat.parsing;

import me.samkio.RPGWorld.RPGWorldPlugin;
import me.samkio.RPGWorld.ranks.RankManager;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.gmail.andreasmartinmoerch.danandchat.DanAndChat;
import com.gmail.andreasmartinmoerch.danandchat.channel.Channel;
public class DanAndParser {
	
	private Channel channel = null;
	private Player player = null;
	private String message = null;
	
	private DanAndChat plugin;
	public DanAndParser(DanAndChat plugin){
		this.plugin = plugin;
	}
	public String interpretString(String format, final Channel channel, final Player player, final String message){
		if (format.contains("&" + ParsingVariables.CHANNEL.toString() + ".")){
			format = parseChannelInfo(format, channel);
		}
		if (format.contains("&" + ParsingVariables.COLOUR.toString() + ".")){
			format = colorKey(format);
		}
		if (format.contains("&" + ParsingVariables.PLAYER.toString() + ".")){
			format = parsePlayerInfo(format, player);
		}
		
		format = format.replaceAll("&&", "&");
		format = format.replaceAll("&MESSAGE", message);
		
		return format;
	}
	
	public String parseMessage(String toParse, String message){
		return toParse.replaceAll("&MESSAGE", message);
	}
	
	/**
	 * A collection of all the possible parsings. 
	 * The idea is that it should do no parsing on it's own.
	 * @param format The String that contains all the
	 * @param args Max. 1 Channel, Max. 1 Player, Max 1. String(Message)
	 * @param parseMessage If it should parse the message contained in args.
	 * @return the parsed string.
	 */
	public String fullParse(String format, boolean parseMessage, Object... args){
		
		if (format == null){
			throw new NullPointerException();
		}
		
		for (Object o: args){
			if (channel != null && player != null){
				break;
			}
			if (o instanceof Player){
				this.player = (Player)o;
			} else if (o instanceof Channel){
				this.channel = (Channel)o;
			} else if (o instanceof String){
				this.message = (String)o;
			}
		}
		
		if (parseMessage && this.message != null){
			format = parseMessage(format, message);
		}
		
		if (this.channel != null && format.contains("&" + ParsingVariables.CHANNEL.toString() + ".")){
			format = parseChannelInfo(format, channel);
		}
		if (this.player != null && format.contains("&" + ParsingVariables.PLAYER.toString() + ".")){
			format = parsePlayerInfo(format, player);
		}
		
		format = parseAmpersands(format);
		
		if (!parseMessage && this.message != null){
			format = parseMessage(format, message);
		}
		
		return format;
	}
	
	public String parseAmpersands(String toParse){
		return toParse.replaceAll("&&", "&");
	}
	
	/**
	 * A function replace all "&" with "§", if the following character is a digit or is lowercase.
	 * @param toParse The string to parse.
	 * @return Parsed String.
	 */
	public String parseColours(String toParse){
		if (!toParse.contains("&")){
			return toParse;
		}
		
		char[] chars = toParse.toCharArray();
		for (int i = 0; i < chars.length; i++){
			if (chars[i] == '&'){
				if (Character.isLowerCase(chars[i + 1]) || Character.isDigit(chars[i + 1])){
					chars[i] = '§';
				}
			}
		}
		String toReturn = "";
		for (char c: chars){
			toReturn += c;
		}
		
		return toReturn;
	}
	
	/**
	 * @deprecated Use {@link #parseChannelInfo(String,Channel)} instead
	 */
	public String channelKey(String format, final Channel channel){
		return parseChannelInfo(format, channel);
	}
	public String parseChannelInfo(String format, final Channel channel){
		// TODO: Add checks, so we don't have to call more than necessary.
		if (format.contains("&" + ParsingVariables.CHANNEL.toString() + "." + ChannelParsingVariables.NAME.toString())){
			format = format.replaceAll("&" + ParsingVariables.CHANNEL.toString() + "." + ChannelParsingVariables.NAME.toString(), channel.getName());
		}
		if (format.contains("&" + ParsingVariables.CHANNEL.toString() + "." + ChannelParsingVariables.SHORTCUT.toString())){
			format = format.replaceAll("&" + ParsingVariables.CHANNEL.toString() + "." + ChannelParsingVariables.SHORTCUT.toString(), channel.getShortCut());
		}
		if (format.contains("&" + ParsingVariables.CHANNEL.toString() + "." + ChannelParsingVariables.SHORTNAME.toString())){
			format = format.replaceAll("&" + ParsingVariables.CHANNEL.toString() + "." + ChannelParsingVariables.SHORTNAME.toString(), channel.getShortCut());
		}
		
		return format;
	}
	
	/**
	 * @deprecated 
	 * @see #parseColours(String)
	 */
	public String colorKey(String format){
		// TODO: Add checks, so we don't have to call more than necessary.
		format = format.replaceAll("&" + ParsingVariables.COLOUR.toString() + "." + ColourParsingVariables.AQUA.toString(), ColourParsingVariables.AQUA.getStrPresentation());
		format = format.replaceAll("&" + ParsingVariables.COLOUR.toString() + "." + ColourParsingVariables.BLACK.toString(), ColourParsingVariables.BLACK.getStrPresentation());
		format = format.replaceAll("&" + ParsingVariables.COLOUR.toString() + "." + ColourParsingVariables.BLUE.toString(), ColourParsingVariables.BLUE.getStrPresentation());
		format = format.replaceAll("&" + ParsingVariables.COLOUR.toString() + "." + ColourParsingVariables.DARK_AQUA.toString(), ColourParsingVariables.DARK_AQUA.getStrPresentation());
		format = format.replaceAll("&" + ParsingVariables.COLOUR.toString() + "." + ColourParsingVariables.DARK_BLUE.toString(), ColourParsingVariables.DARK_BLUE.getStrPresentation());
		format = format.replaceAll("&" + ParsingVariables.COLOUR.toString() + "." + ColourParsingVariables.DARK_GRAY.toString(), ColourParsingVariables.DARK_GRAY.getStrPresentation());
		format = format.replaceAll("&" + ParsingVariables.COLOUR.toString() + "." + ColourParsingVariables.DARK_GREEN.toString(), ColourParsingVariables.DARK_GREEN.getStrPresentation());
		format = format.replaceAll("&" + ParsingVariables.COLOUR.toString() + "." + ColourParsingVariables.DARK_PURPLE.toString(), ColourParsingVariables.DARK_PURPLE.getStrPresentation());
		format = format.replaceAll("&" + ParsingVariables.COLOUR.toString() + "." + ColourParsingVariables.DARK_RED.toString(), ColourParsingVariables.DARK_RED.getStrPresentation());
		format = format.replaceAll("&" + ParsingVariables.COLOUR.toString() + "." + ColourParsingVariables.GOLD.toString(), ColourParsingVariables.GOLD.getStrPresentation());
		format = format.replaceAll("&" + ParsingVariables.COLOUR.toString() + "." + ColourParsingVariables.GRAY.toString(), ColourParsingVariables.GRAY.getStrPresentation());
		format = format.replaceAll("&" + ParsingVariables.COLOUR.toString() + "." + ColourParsingVariables.GREEN.toString(), ColourParsingVariables.GREEN.getStrPresentation());
		format = format.replaceAll("&" + ParsingVariables.COLOUR.toString() + "." + ColourParsingVariables.LIGHT_PURPLE.toString(), ColourParsingVariables.LIGHT_PURPLE.getStrPresentation());
		format = format.replaceAll("&" + ParsingVariables.COLOUR.toString() + "." + ColourParsingVariables.RED.toString(), ColourParsingVariables.RED.getStrPresentation());
		format = format.replaceAll("&" + ParsingVariables.COLOUR.toString() + "." + ColourParsingVariables.WHITE.toString(), ColourParsingVariables.WHITE.getStrPresentation());
		format = format.replaceAll("&" + ParsingVariables.COLOUR.toString() + "." + ColourParsingVariables.YELLOW.toString(), ColourParsingVariables.YELLOW.getStrPresentation());
		return format;
	}
	
	/**
	 * @deprecated Use {@link #parsePlayerInfo(String,Player)} instead
	 */
	public String playerKey(String format, Player player){
		return parsePlayerInfo(format, player);
	}
	public String parsePlayerInfo(String format, Player player){
		// TODO: Add checks, so we don't have to call more than necessary.
		if (this.plugin.getExtensionManager().usesPermissionsBukkit() && format.contains("&" + ParsingVariables.PLAYER.toString() + "." + PlayerParsingVariables.GROUP.toString())){
			String group = this.plugin.getExtensionManager().permissionsBukkit.getGroup(player.getName()).getName();
			if (group == null){
				group = "";
			}
			format = format.replaceAll("&" + ParsingVariables.PLAYER.toString() + "." + PlayerParsingVariables.GROUP.toString(), group);
		} else {
			format = format.replaceAll("&" + ParsingVariables.PLAYER.toString() + "." + PlayerParsingVariables.GROUP.toString(), "");
		}
		
		format = format.replaceAll("&" + ParsingVariables.PLAYER.toString() + "." + PlayerParsingVariables.NAME.toString(), player.getName());
		
		if (this.plugin.getExtensionManager().isUsingColorMe()){
			format = format.replaceAll("&" + ParsingVariables.PLAYER.toString() + "." + PlayerParsingVariables.COLOUR.toString(), this.plugin.getExtensionManager().color.findColor(this.plugin.getExtensionManager().color.getColor(player.getName())));
		} else {
			format = format.replaceAll("&" + ParsingVariables.PLAYER.toString() + "." + PlayerParsingVariables.COLOUR.toString(), "");
		}
		if(this.plugin.getExtensionManager().usesRPGWorld()){
			if (RPGWorldPlugin.useRanksAsPrefix()){
				format = format.replaceAll("&" + ParsingVariables.PLAYER.toString() + "." + PlayerParsingVariables.RPGPREFIX.toString(), RankManager.getPlayerRank(player.getName()));
			} else {
				format = format.replaceAll("&" + ParsingVariables.PLAYER.toString() + "." + PlayerParsingVariables.RPGPREFIX.toString(), "");
			}
		} else {
			format = format.replaceAll("&" + ParsingVariables.PLAYER.toString() + "." + PlayerParsingVariables.RPGPREFIX.toString(), "");
		}
		
		if (format.contains("&" + ParsingVariables.PLAYER.toString() + "." + PlayerParsingVariables.HEALTH.toString())){
			format = format.replaceAll("&" + ParsingVariables.PLAYER.toString() + "." + PlayerParsingVariables.HEALTH, healthToString(player)); 
		}
		
		format = format.replaceAll("&" + ParsingVariables.PLAYER.toString() + "." + PlayerParsingVariables.DISPLAYNAME.toString(), player.getDisplayName());
		if (format.contains("&" + ParsingVariables.PLAYER.toString() + "." + PlayerParsingVariables.PREFIX.toString())){
			format = format.replaceAll("&" + ParsingVariables.PLAYER.toString() + "." + PlayerParsingVariables.PREFIX.toString(), this.plugin.getPrefixer().getPrefix(player));
		}
		
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
