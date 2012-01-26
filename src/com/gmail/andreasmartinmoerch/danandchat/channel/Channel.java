package com.gmail.andreasmartinmoerch.danandchat.channel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.config.ConfigurationNode;

import com.gmail.andreasmartinmoerch.danandchat.DACPlayer;
import com.gmail.andreasmartinmoerch.danandchat.DanAndChat;
import com.gmail.andreasmartinmoerch.danandchat.channel.filter.Filter;
import com.gmail.andreasmartinmoerch.danandchat.channel.filter.FilterManager;
import com.gmail.andreasmartinmoerch.danandchat.channel.filter.noargs.FilterWithoutArgs_inChannel;
import com.gmail.andreasmartinmoerch.danandchat.parsing.ColourParsingVariables;
import com.gmail.andreasmartinmoerch.danandchat.utils.Messages;

/**
 * This class represents a Channel.
 * 
 * @author Andreas
 * 
 */
public class Channel {
	
	// Private variables
		// Main variables
		private String name;
		private String shortcut;
		private ConfigurationNode chNode;
	
		// DanAndChat plugin
		public final DanAndChat plugin;
		// Filters
		private List<List<Filter>> joinIncludeFilters;
		private List<List<Filter>> joinExcludeFilters;
		private List<List<Filter>> sendIncludeFilters;
		private List<List<Filter>> sendExcludeFilters;
		private FilterManager filterManager;
		// Hidden from /channel list:
		private boolean hidden;
		// The "personal" DanAndLogger for messages
		private DanAndLogger danAndLogger;
		// Default formatting variables
		private String formatting = "[&4&CHANNEL.NAME]&f<&PLAYER.NAME>: &MESSAGE";
		private String meFormatting = "&6* &PLAYER.NAME &f&MESSAGE";
		// Player lists
		private List<String> banned;
		private List<String> muted;
		private List<String> focused;
		private List<String> players;
		private List<Player> filtered;
		// Old variables
		private boolean autoJoin;
		private boolean autoFocus;
		private int localRange = -1;

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
		this.banned = new ArrayList<String>();
		this.chNode = plugin.getSettings().channelsConfig.getNode("channels"
				+ "." + this.getName());
		this.players = new ArrayList<String>();
		this.focused = new ArrayList<String>();
		this.filtered = new ArrayList<Player>();
	}
	
	public void initialize(){
		this.danAndLogger = new DanAndLogger(this.plugin, this);
		this.danAndLogger.initialize();

		/*
		 * BANNED PLAYERS START
		 */
		List<String> banned = new ArrayList<String>();
		for (String s : chNode.getStringList("banned-players", banned)) {
			banned.add(s);
		}
		this.setBanned(banned);
		/*
		 * BANNED PLAYERS END
		 */

		// Muted players
		List<String> muted = new ArrayList<String>();

		for (String s : chNode.getStringList("muted-players", muted)) {
			muted.add(s);
		}
		this.setMuted(muted);
		
		this.setHidden(chNode.getBoolean("hidden", false));
		
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
		
		this.setShortCut(chNode.getString("shortcut", this.getName().substring(0, 1)));
		
		setupFilters();
	}
	
	public void setupFilters(){
		this.sendIncludeFilters = FilterManager.makeFilterGroups(chNode.getNode("filters.onWrite").getList("include"));
		if (this.sendIncludeFilters == null){
			this.sendIncludeFilters = new ArrayList<List<Filter>>();
			FilterWithoutArgs_inChannel filter = new FilterWithoutArgs_inChannel();
			List<Filter> filterList = new ArrayList<Filter>();
			filterList.add(filter);
			this.sendIncludeFilters.add(filterList);
		}
		
		this.sendExcludeFilters = FilterManager.makeFilterGroups(chNode.getNode("filters.onWrite").getList("exclude"));
		if (this.sendExcludeFilters == null){
			this.sendExcludeFilters = new ArrayList<List<Filter>>();
		}
	}
	
	public void removeFocus(Player player){
		this.focused.remove(player.getName());
		if (this.plugin.getSettings().config.getBoolean("messages" + "." + "notify-on-unfocus", true) == true){
			DACPlayer.getDACPlayer(player).sendMessage(this.plugin.getMessageGetter()
					.getMessageWithArgs(Messages.UNFOCUS_CHANNEL, this.getName()));
		}
	}
	
	public void addFocus(Player p){
		this.focused.add(p.getName());
		if (this.plugin.getSettings().config.getBoolean("messages" + "." + "notify-on-focus", true) == true){
			DACPlayer.getDACPlayer(p).sendMessage(this.plugin.getMessageGetter()
					.getMessageWithArgs(Messages.CHANGED_FOCUS_TO, this.getName()));
		}
	}

	public void sendMe(Player sender, String emote) {
		sendMessage(emote, sender, this.meFormatting);
	}
	
	public void sendMessage(String message, Player sender, String formatting){
		if (this.playerIsInChannel(sender)) {
			if (this.muted.contains(sender.getName())){
				DACPlayer.getDACPlayer(sender).sendMessage(this.plugin.getMessageGetter().getMessageWithArgs(Messages.YOU_HAVE_BEEN_MUTED, this.getName()));
				return;
			}
			this.danAndLogger.logMsg(sender.getName() + ": " + message, "MSG");
			ArrayList<String> newMessage = this.plugin.getMessageHandler()
					.formatMessage(formatting, this, sender, message);
			
			if (this.localRange == -1) {
				for (Player p : this.plugin.getServer().getOnlinePlayers()) {
					
					if (!(this.getBanned().contains(p.getName()))
							&& this.filtered.contains(p)) {						
						for (String s : newMessage) {
							DACPlayer.getDACPlayer(p).sendMessage(s);
						}
					} else {
						if (!this.filtered.contains(p)){
							// Old debug code.
						}
					}
				}
			} else {
				Location loc = sender.getLocation();
				
				for (Player p : this.plugin.getServer().getOnlinePlayers()) {
					if (!(this.getBanned().contains(p.getName()))
							&& isInDistance(p, loc)) {
						for (String s : newMessage) {
							DACPlayer.getDACPlayer(p).sendMessage(s);
						}
					}
				}
			}

		} else {
			
			// TODO: Add to official messages.
			DACPlayer.getDACPlayer(sender).sendMessage(ChatColor.GREEN + "[DanAndChat] " + ChatColor.WHITE + "You're not in this channel: " + ChatColor.GOLD + this.getName());
		}
	}

	public void sendMessage(String message, Player sender) {
		this.sendMessage(message, sender, this.formatting);
	}

	public static ArrayList<String> messages = new ArrayList<String>();

	public void banPlayer(String s) {
		this.banned.add(s);
		this.chNode.setProperty("banned-players", banned);
		this.plugin.getSettings().channelsConfig.save();
		this.plugin.getSettings().channelsConfig.load();
	}
	
	public void banPlayer(Player player){
		banPlayer(player.getName());
	}

	public void unbanPlayer(String s) {
		this.banned.remove(s);
		this.chNode.setProperty("banned-players", banned);
		this.plugin.getSettings().channelsConfig.save();
		this.plugin.getSettings().channelsConfig.load();
	}
	
	public void unbanPlayer(Player player){
		unbanPlayer(player.getName());
	}

	public void mutePlayer(String s) {
		this.muted.add(s);
		this.chNode.setProperty("muted-players", muted);
		this.plugin.getSettings().channelsConfig.save();
		this.plugin.getSettings().channelsConfig.load();
	}
	
	public void mutePlayer(Player player){
		mutePlayer(player.getName());
	}

	public void unmutePlayer(String s) {
		this.muted.remove(s);
		this.chNode.setProperty("muted-players", muted);
		this.plugin.getSettings().channelsConfig.save();
		this.plugin.getSettings().channelsConfig.load();
	}
	
	public void unmutePlayer(Player player){
		unmutePlayer(player.getName());
	}
	
	public boolean isPlayerMuted(Player player){
		return isPlayerMuted(player.getName());
	}
	
	public boolean isPlayerMuted(String player){
		if (this.muted.contains(player)){
			return true;
		} 
		return false;
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
		if (!receiver.getLocation().getWorld().equals(sender.getWorld())){
			return false;
		}
		double xP = Math.pow(sender.getX() - receiver.getLocation().getX(), 2);
		double yP = Math.pow(sender.getY() - receiver.getLocation().getY(), 2);
		double zP = Math.pow(sender.getZ() - receiver.getLocation().getZ(), 2);
		if (Math.sqrt(xP + yP + zP) <= this.getLocalRange()) {
			return true;
		}
		return false;
	}
	
	public void removePlayer(Player p) {
		this.players.remove(p.getName());
		this.removeFocus(p);
		if (this.plugin.getSettings().config.getBoolean("messages" + "." + "notify-on-leave", true) == true){
			p.sendMessage(this.plugin.getMessageGetter()
					.getMessageWithArgs(Messages.LEFT_CHANNEL, this.getName()));
		}
		this.filtered.remove(p);
	}

	public void addPlayer(Player p) {
		this.players.add(p.getName());
		if (this.plugin.getSettings().config.getBoolean("messages" + "." + "notify-on-join", false) == true){
			p.sendMessage(this.plugin.getMessageGetter().
					getMessageWithArgs(Messages.CHANGED_CHANNEL_TO, this.getName()));
		}
		FilterManager filterManager = new FilterManager(this, this.getSendIncludeFilters(), true);
		List<Player> filtered = filterManager.getFiltered(Arrays.asList(this.plugin.getServer().getOnlinePlayers()));
		FilterManager excludeManager = new FilterManager(this, this.getSendExcludeFilters(), true);
		List<Player> filtered2 = excludeManager.getFiltered(filtered);
		for (Player player: filtered2){
			filtered.remove(player);
		}
		this.filtered = filtered;
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

	public List<String> getBanned() {
		return banned;
	}

	public void setBanned(List<String> banned) {
		this.banned = banned;
	}

	public boolean playerIsInChannel(Player p) {
		return players.contains(p.getName());
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPlayers(List<String> players) {
		this.players = players;
	}


	public boolean isHidden() {
		return hidden;
	}

	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}
	
	public List<String> getFocused() {
		return focused;
	}

	public void setFocused(List<String> focused) {
		this.focused = focused;
	}

	public DanAndLogger getChLogger() {
		return danAndLogger;
	}

	public void setChLogger(DanAndLogger chLogger) {
		this.danAndLogger = chLogger;
	}

	public String getFormatting() {
		return formatting;
	}

	public void setFormatting(String formatting) {
		this.formatting = formatting;
	}

	public ConfigurationNode getChNode() {
		return chNode;
	}

	public void setChNode(ConfigurationNode chNode) {
		this.chNode = chNode;
	}

	public int getLocalRange() {
		return localRange;
	}

	public void setLocalRange(int localRange) {
		this.localRange = localRange;
	}

	public String getShortCut() {
		return shortcut;
	}

	public void setShortCut(String shortcut) {
		this.shortcut = shortcut;
	}

	public List<List<Filter>> getJoinIncludeFilters() {
		return joinIncludeFilters;
	}

	public void setJoinIncludeFilters(List<List<Filter>> joinIncludeFilters) {
		this.joinIncludeFilters = joinIncludeFilters;
	}

	public List<List<Filter>> getJoinExcludeFilters() {
		return joinExcludeFilters;
	}

	public void setJoinExcludeFilters(List<List<Filter>> joinExcludeFilters) {
		this.joinExcludeFilters = joinExcludeFilters;
	}

	public List<List<Filter>> getSendIncludeFilters() {
		return sendIncludeFilters;
	}

	public void setSendIncludeFilters(List<List<Filter>> sendIncludeFilters) {
		this.sendIncludeFilters = sendIncludeFilters;
	}

	public List<List<Filter>> getSendExcludeFilters() {
		return sendExcludeFilters;
	}

	public void setSendExcludeFilters(List<List<Filter>> sendExcludeFilters) {
		this.sendExcludeFilters = sendExcludeFilters;
	}

	public FilterManager getFilterManager() {
		return filterManager;
	}

	public void setFilterManager(FilterManager filterManager) {
		this.filterManager = filterManager;
	}
	
	
	
}
