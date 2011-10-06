package com.gmail.andreasmartinmoerch.danandchat.channel;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.util.config.ConfigurationNode;

import com.gmail.andreasmartinmoerch.danandchat.DanAndChat;
import com.gmail.andreasmartinmoerch.danandchat.parsing.ColourParsingVariables;
import com.gmail.andreasmartinmoerch.danandchat.utils.Messages;

/**
 * This class represents a Channel.
 * 
 * @author Andreas
 * 
 */
public class Channel {
	
	private List<ChannelTag> tags;

	private final DanAndChat plugin;
	private List<World> worlds;
	private List<String> banned;
	private List<String> muted;
	private List<String> focused;
	private String name;
	private String shortName;
	private String shortCut;
	private ColourParsingVariables colour;
	private List<String> allowedGroups;
	private List<String> allowedPlayers;
	private boolean ic;
	private boolean hidden;
	private boolean bPrivate;
	private ConfigurationNode chNode;;
	private int localRange;
	private String formatting = "[&CHANNEL.COLOUR&CHANNEL.NAME]&COLOUR.WHITE<&PLAYER.NAME>: &MESSAGE";
	private String meFormatting = "&6* &PLAYER.DISPLAYNAME &f&MESSAGE";
	private boolean usesMe;
	private boolean autoJoin;
	private boolean autoFocus;
	private DanAndLogger chLogger;

	private List<String> players;

//	public Channel(String name, DanAndChat plugin) {
//		this.name = name;
//		this.plugin = plugin;
//
//		this.worlds = new ArrayList<World>();
//		this.banned = new ArrayList<String>();
//		this.allowedGroups = new ArrayList<String>();
//		this.allowedPlayers = new ArrayList<String>();
//		this.chNode = plugin.getSettings().channelsConfig.getNode("channels"
//				+ "." + this.getName());
//		this.players = new ArrayList<Player>();
//		this.focused = new ArrayList<Player>();
//	}
	
	public Channel(String name, DanAndChat plugin){
		this.name = name;
		this.plugin = plugin;
		this.tags = new ArrayList<ChannelTag>();

		this.worlds = new ArrayList<World>();
		this.banned = new ArrayList<String>();
		this.allowedGroups = new ArrayList<String>();
		this.allowedPlayers = new ArrayList<String>();
		this.chNode = plugin.getSettings().channelsConfig.getNode("channels"
				+ "." + this.getName());
		this.players = new ArrayList<String>();
		this.focused = new ArrayList<String>();
	}
	
	public void initialize(){
		this.chLogger = new DanAndLogger(this.plugin, this);
		this.chLogger.initialize();
		// Worlds
		List<World> worlds = new ArrayList<World>();
		List<String> defWorlds = new ArrayList<String>();
		defWorlds.add(this.plugin.getServer().getWorlds().get(0).getName());

		for (String s : chNode.getStringList("worlds", defWorlds)) {
			World world = this.plugin.getServer().getWorld(s);
			if (world != null) {
				worlds.add(world);
			} else {
				this.plugin.getDanandLogger().logMsg("Found invalid world specified: "
						+ s + " - In channel: " + this.getName()
						+ ". It may not work in this world.", "WARNING");
			}
		}
		if (worlds.isEmpty()) {
			worlds.add(this.plugin.getServer().getWorlds().get(0));
		}
		this.setWorlds(worlds);

		// Banned players
		List<String> banned = new ArrayList<String>();
		for (String s : chNode.getStringList("banned-players", banned)) {
			banned.add(s);
		}
		this.setBanned(banned);

		// Muted players
		List<String> muted = new ArrayList<String>();

		for (String s : chNode.getStringList("muted-players", muted)) {
			muted.add(s);
		}
		this.setMuted(muted);

		// ShortName
		String shortName;
		shortName = chNode.getString("short-name");
		if (shortName == null) {
			this.setShortName(this.getName());
		} else {
			this.setShortName(shortName);
		}

		// Shortcut
		String shortcut;
		shortcut = chNode.getString("shortcut");
		if (shortcut == null || shortcut.isEmpty()) {
			this.setShortCut(this.getName());
		} else {
			this.setShortCut(shortcut);
		}

		// Colour
		try {
			this.setColor(ColourParsingVariables.valueOf(chNode.getString(".colour")
					.toUpperCase()));
		} catch (Exception e) {
			try {
				this.setColor(ColourParsingVariables.valueOf(chNode.getString(".color")
						.toUpperCase()));
			} catch (Exception ex) {

			}
			this.setColor(ColourParsingVariables.WHITE);
		}
		
		// TODO: Add support for group managers.
		// // Allowed groups
		// List<String> groups = new ArrayList<String>();
		// for (String s: Settings.channelsConfig.getKeys("channels" + "." +
		// this.getName() + ".allowed-groups")){
		// groups.add(s);
		// }
		// this.setAllowedGroups(groups);
		//
		// // Allowed players
		// List<String> players = new ArrayList<String>();
		// for (String s: Settings.channelsConfig.getKeys("channels" + "." +
		// this.getName() + ".exempted-players")){
		// players.add(s);
		// }
		// this.setAllowedPlayers(players);

		// In Character
		this.setIc(chNode.getBoolean("in-character-focused", false));

		// Uses /me?
		this.setUsesMe(chNode.getBoolean("uses-me", true));

		// Hidden
		this.setHidden(chNode.getBoolean("hidden", false));
		if (this.hidden){
			this.tags.add(ChannelTag.HIDDEN);
		}

		// Range. -1 = Global
		this.setLocalRange(chNode.getInt("range", -1));
		String newformat;
		if ((newformat = chNode.getString("formatting")) != null
				&& !(newformat.isEmpty())) {
			this.setFormatting(newformat);
		}

		// Autojoin?
		this.setAutoJoin(chNode.getBoolean("auto-join", true));

		// Autofocus?
		this.setAutoFocus(chNode.getBoolean("auto-focus", false));
	}
	
	public void removeFocus(Player player){
		this.focused.remove(player.getName());
		if (this.plugin.getSettings().config.getBoolean("messages" + "." + "notify-on-unfocus", true) == true){
			player.sendMessage(this.plugin.getMessageGetter()
					.getMessageWithArgs(Messages.UNFOCUS_CHANNEL, this.getName()));
		}
	}
	
	public void addFocus(Player p){
		this.focused.add(p.getName());
		if (this.plugin.getSettings().config.getBoolean("messages" + "." + "notify-on-focus", true) == true){
			p.sendMessage(this.plugin.getMessageGetter()
					.getMessageWithArgs(Messages.CHANGED_FOCUS_TO, this.getName()));
		}
	}
	
	public List<String> getFocused() {
		return focused;
	}

	public void setFocused(List<String> focused) {
		this.focused = focused;
	}

	/**
	 * @deprecated
	 * @see #initialize()
	 */
	public void loadFromConfig() {
		this.initialize();
	}

	public DanAndLogger getChLogger() {
		return chLogger;
	}

	public void setChLogger(DanAndLogger chLogger) {
		this.chLogger = chLogger;
	}

	public String getFormatting() {
		return formatting;
	}

	public void setFormatting(String formatting) {
		this.formatting = formatting;
	}

	public int getLocalRange() {
		return localRange;
	}

	public void setLocalRange(int localRange) {
		this.localRange = localRange;
	}

	public boolean isbPrivate() {
		return bPrivate;
	}

	public void setbPrivate(boolean bPrivate) {
		this.bPrivate = bPrivate;
	}

	public ConfigurationNode getChNode() {
		return chNode;
	}

	public void setChNode(ConfigurationNode chNode) {
		this.chNode = chNode;
	}

	public void sendMe(Player sender, String emote) {
		if (!this.usesMe) {
			return;
		}

		if (this.playerIsInChannel(sender)) {
			this.chLogger.logMsg("* " + sender.getName() + emote, "EMOTE");
			if (this.getLocalRange() == -1) {
				for (Player p : this.plugin.getServer().getOnlinePlayers()) {
					if (!(this.getBanned().contains(p.getName()))
							&& this.playerIsInChannel(p)
							&& this.getWorlds().contains(sender.getWorld())) {
						p.sendMessage(ChatColor.GOLD + "* "
								+ sender.getDisplayName() + " " + emote);
					}
				}
			} else {
				Location loc = sender.getLocation();
				for (Player p : this.plugin.getServer().getOnlinePlayers()) {
					if (!(this.getBanned().contains(p.getName()))
							&& this.playerIsInChannel(p)
							&& this.getWorlds().contains(sender.getWorld())
							&& isInDistance(p, loc)) {
						p.sendMessage(ChatColor.GOLD + "* "
								+ sender.getDisplayName() + " " + emote);
					}
				}
			}

		}
	}

	public void sendMessage(String message, Player sender) {
		boolean ic = false;
		if (this.playerIsInChannel(sender)) {
			this.chLogger.logMsg(sender.getName() + ": " + message, "MSG");
			ArrayList<String> newMessage = this.plugin.getMessageHandler()
					.formatMessage(this.formatting, this, sender, message);
			if (this.getLocalRange() == -1) {
				for (Player p : this.plugin.getServer().getOnlinePlayers()) {
					if (!(this.getBanned().contains(p.getName()))
							&& !(this.getMuted().contains(p.getName()))
							&& this.playerIsInChannel(p)
							&& this.getWorlds().contains(sender.getWorld())) {
						for (String s : newMessage) {
							p.sendMessage(s);
						}
					}
				}
			} else {
				Location loc = sender.getLocation();
				for (Player p : this.plugin.getServer().getOnlinePlayers()) {
					if (!(this.getBanned().contains(p.getName()))
							&& !(this.getMuted().contains(p.getName()))
							&& this.playerIsInChannel(p)
							&& this.getWorlds().contains(sender.getWorld())
							&& isInDistance(p, loc)) {
						for (String s : newMessage) {
							p.sendMessage(s);
						}
					}
				}
			}

		}

	}

	public boolean isInWorld(Player p) {
		for (World w : worlds) {
			if (p.getWorld().equals(w)) {
				return true;
			}
		}
		return false;
	}

	public static ArrayList<String> messages = new ArrayList<String>();

	public void banPlayer(String s) {
		this.banned.add(s);
		this.chNode.setProperty("banned-players", banned);
		this.plugin.getSettings().channelsConfig.save();
		this.plugin.getSettings().channelsConfig.load();
	}

	public void unbanPlayer(String s) {
		this.banned.remove(s);
		this.chNode.setProperty("banned-players", banned);
		this.plugin.getSettings().channelsConfig.save();
		this.plugin.getSettings().channelsConfig.load();
	}

	public void mutePlayer(String s) {
		this.muted.add(s);
		this.chNode.setProperty("muted-players", muted);
		this.plugin.getSettings().channelsConfig.save();
		this.plugin.getSettings().channelsConfig.load();
	}

	public void unmutePlayer(String s) {
		this.muted.remove(s);
		this.chNode.setProperty("muted-players", muted);
		this.plugin.getSettings().channelsConfig.save();
		this.plugin.getSettings().channelsConfig.load();
	}

	public static void noOneIsNear(Player p) {
		Random test = new Random();
		if (messages.isEmpty()) {
			p.sendMessage(ColourParsingVariables.GREEN + "No one can hear you.");
			return;
		}
		p.sendMessage(ColourParsingVariables.GREEN
				+ messages.get(test.nextInt(messages.size())));
	}

	public boolean isInDistance(Player receiver, Location sender) {
		double xP = Math.pow(sender.getX() - receiver.getLocation().getX(), 2);
		double yP = Math.pow(sender.getY() - receiver.getLocation().getY(), 2);
		double zP = Math.pow(sender.getZ() - receiver.getLocation().getZ(), 2);
		if (Math.sqrt(xP + yP + zP) <= this.getLocalRange()) {
			return true;
		}
		return false;
	}

	public boolean isAutoJoin() {
		return autoJoin;
	}

	public void setAutoJoin(boolean autoJoin) {
		this.autoJoin = autoJoin;
	}

	public boolean isAutoFocus() {
		return autoFocus;
	}

	public void setAutoFocus(boolean autoFocus) {
		this.autoFocus = autoFocus;
	}

	public List<String> getMuted() {
		return muted;
	}

	public void setMuted(List<String> muted) {
		this.muted = muted;
	}

	public List<String> getAllowedGroups() {
		return allowedGroups;
	}

	public void setAllowedGroups(List<String> allowedGroups) {
		this.allowedGroups = allowedGroups;
	}

	public List<String> getAllowedPlayers() {
		return allowedPlayers;
	}

	public void setAllowedPlayers(List<String> allowedPlayers) {
		this.allowedPlayers = allowedPlayers;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public List<World> getWorlds() {
		return worlds;
	}

	public void setWorlds(List<World> worlds) {
		this.worlds = worlds;
	}

	public List<String> getBanned() {
		return banned;
	}

	public void setBanned(List<String> banned) {
		this.banned = banned;
	}

	public boolean playerIsInChannel(Player p) {
		return players.contains(p.getName());
	}

	public void removePlayer(Player p) {
		this.players.remove(p.getName());
		this.removeFocus(p);
		if (this.plugin.getSettings().config.getBoolean("messages" + "." + "notify-on-leave", true) == true){
			p.sendMessage(this.plugin.getMessageGetter()
					.getMessageWithArgs(Messages.LEFT_CHANNEL, this.getName()));
		}
	}

	public void addPlayer(Player p) {
		this.players.add(p.getName());
		if (this.plugin.getSettings().config.getBoolean("messages" + "." + "notify-on-join", true) == true){
			p.sendMessage(this.plugin.getMessageGetter().
					getMessageWithArgs(Messages.CHANGED_CHANNEL_TO, this.getName()));
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean usesMe() {
		return usesMe;
	}

	public void setUsesMe(boolean usesMe) {
		this.usesMe = usesMe;
	}

	public void setPlayers(List<String> players) {
		this.players = players;
	}

	public String getShortCut() {
		return shortCut;
	}

	public void setShortCut(String shortCut) {
		this.shortCut = shortCut;
	}

	public ColourParsingVariables getColor() {
		return colour;
	}

	public void setColor(ColourParsingVariables color) {
		this.colour = color;
	}

	public boolean isIc() {
		return ic;
	}

	public void setIc(boolean ic) {
		this.ic = ic;
	}

	public boolean isHidden() {
		return hidden;
	}

	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}

	// public void initialize(){
	// this.name = properties.getString("name-of-channel");
	// this.sCut = properties.getString("shortcut-name");
	// this.range = properties.getInt("range-in-blocks");
	// this.color = properties.getString("color-of-chat");
	// this.ic = properties.getBoolean("is-in-character-focused");
	// this.banned = new ArrayList<Player>();
	// if (properties.keyExists("banned-players")){
	// String unFormatted = properties.getString("banned-players");
	// StringTokenizer strTok = new StringTokenizer(unFormatted, ",");
	// while (strTok.hasMoreTokens()){
	// banned.add(NaviaChat.server.getPlayer(strTok.nextToken()));
	// }
	// }
	// this.worlds = new ArrayList<World>();
	// if (properties.getString("worlds").contains(",")){
	// StringTokenizer strTok = new
	// StringTokenizer(properties.getString("worlds"), ",");
	// while (strTok.hasMoreTokens()){
	// World world = NaviaChat.server.getWorld(strTok.nextToken());
	// worlds.add(world);
	// }
	// } else {
	// worlds.add(NaviaChat
	// .server
	// .getWorld("world"));
	// }
	// }
	//
	// public void banPlayer(Player player){
	// banned.add(player);
	// if (!properties.keyExists("banned-players")){
	// properties.setString("banned-players", player.getName());
	// } else {
	// String tmpr = properties.getString("banned-players");
	// properties.setString("banned-players", tmpr + "," + player.getName());
	// }
	// }
	//
	// public boolean unbanPlayer(Player player){
	// if (banned.contains(player)){
	// banned.remove(player);
	//
	// return true;
	// }
	// return false;
	// }
	//
	// public boolean isPlayerBanned(Player player){
	// if (banned.contains(player)){
	// return true;
	// }
	// return false;
	// }
	//
	// public boolean isHidden(){
	// return hidden;
	// }
	//
	// public boolean isIc(){
	// return ic;
	// }
	//
	// public String getShortCut(){
	// return this.sCut;
	// }
	//
	// public boolean isLocal(){
	// return range != 0;
	// }
	//
	// public void sendMe(String action, Player sender){
	// Logger log = Logger.getLogger("Minecraft");
	// if (this.isLocal()){
	// if (this.isIc() && ChannelManager.playerIsIc(sender)){
	// String emote = MessageHandler.getIcEmote(sender, action);
	// log.info("[NaviaChat] [" + this.name + "] [EMOTE] " + emote);
	// for (Player p: NaviaChat.server.getOnlinePlayers()){
	// if (isInDistance(p, sender.getLocation()) && !isPlayerBanned(p)){
	// p.sendMessage(emote);
	// }
	// }
	// } else {
	//
	// }
	// } else {
	//
	// }
	// }
	//
	// public void sendMessage(String message, Player sender, boolean ic){
	// Logger log = Logger.getLogger("Minecraft");
	// if (this.isLocal()){
	// String newMessage=MessageHandler.getLocalMessage(sender, message, this,
	// ic);
	// log.info("[NaviaChat] " + newMessage);
	// int anyone = 0;
	// for (Player p: NaviaChat.server.getOnlinePlayers()){
	// if (isInDistance(p, sender.getLocation()) &&
	// ChannelManager.playerIsInChannel(p, this) && !isPlayerBanned(p) &&
	// isInWorld(p)){
	// p.sendMessage(newMessage);
	// anyone++;
	// }
	// }
	// if (anyone == 1){
	// noOneIsNear(sender);
	// }
	// } else {
	// if (ChannelManager.playerIsInChannel(sender, this)){
	// String newMessage=MessageHandler.encodeGlobalMessage(sender, message,
	// this);
	// log.info("[NaviaChat]" + newMessage);
	// for (Player p: NaviaChat.server.getOnlinePlayers()){
	// if (!isPlayerBanned(p) && ChannelManager.playerIsInChannel(p, this) &&
	// isInWorld(p)){
	// p.sendMessage(newMessage);
	// }
	// }
	// }
	//
	// }
	// }
	//
	// public boolean isInWorld(Player p){
	// for (World w: worlds){
	// if (p.getWorld().equals(w)){
	// return true;
	// }
	// }
	// return false;
	// }
	//
	// public static ArrayList<String> messages = new ArrayList<String>();
	//
	// public static void noOneIsNear(Player p){
	// Random test = new Random();
	// if (messages.isEmpty()){
	// p.sendMessage(ChatColor.GREEN + "No one can hear you.");
	// return;
	// }
	// p.sendMessage(ChatColor.GREEN +
	// messages.get(test.nextInt(messages.size())));
	// }
	//
	// public boolean isInDistance(Player receiver, Location sender){
	// double xP =
	// Math.pow(sender.getX() - receiver.getLocation().getX(), 2);
	// double yP =
	// Math.pow(sender.getY() - receiver.getLocation().getY(), 2);
	// double zP =
	// Math.pow(sender.getZ() - receiver.getLocation().getZ(), 2);
	// if (Math.sqrt(xP + yP + zP) <= range){
	// return true;
	// }
	// return false;
	// }
	//
	// /**
	// * @return the range
	// */
	// public int getRange() {
	// return range;
	// }
	//
	// /**
	// * @param range the range to set
	// */
	// public void setRange(int range) {
	// this.range = range;
	// }
	//
	// /**
	// * @return the name
	// */
	// public String getName() {
	// return name;
	// }
	//
	// /**
	// * @param name the name to set
	// */
	// public void setName(String name) {
	// this.name = name;
	// }
	//
	// /**
	// * @return the sCut
	// */
	// public String getsCut() {
	// return sCut;
	// }
	//
	// /**
	// * @param sCut the sCut to set
	// */
	// public void setsCut(String sCut) {
	// this.sCut = sCut;
	// }
	//
	// /**
	// * @return the color
	// */
	// public String getColor() {
	// return color;
	// }
	//
	// /**
	// * @param color the color to set
	// */
	// public void setColor(String color) {
	// this.color = color;
	// }
	//
	// /**
	// * @param ic the ic to set
	// */
	// public void setIc(boolean ic) {
	// this.ic = ic;
	// }
	// /**
	// * @return the world
	// */
	// public ArrayList<World> getWorlds() {
	// return worlds;
	// }
	//
	// /**
	// * @param world the world to set
	// */
	// public void setWorlds(ArrayList<World> worlds) {
	// this.worlds = worlds;
	// }
}
