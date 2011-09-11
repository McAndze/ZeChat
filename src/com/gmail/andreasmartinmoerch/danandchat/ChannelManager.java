package com.gmail.andreasmartinmoerch.danandchat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.gmail.andreasmartinmoerch.danandchat.channel.Channel;
public class ChannelManager {
	
	// TODO: This solution just sucks. Do something about it. Basically this whole class sucks. It's like a rotten scaffold. Just barely keeps you from falling to the ground.
	private static HashMap<Player, Channel> playerFocused;
	
	public static List<Channel> channels;
	
	private static HashMap<Player, Boolean> playerState;

	
	
	public static boolean playerChangeChannel(String channel, Player player){
		Channel c;
		if (!channelExists((c = getChannelWithShortcut(channel)))){
			player.sendMessage(ChatColor.RED + "Channel does not exist: " + channel);
			return true;
		}
		setFocusedChannel(c, player);
		playerAddChannel(c, player);
		return true;
	}
	
	public static boolean playerChangeChannel(Channel channel, Player player){
		if (!channelExists(channel)){
			player.sendMessage(ChatColor.RED + "Channel does not exist: " + channel.getName());
			return false;
		}
		
		if (!channel.playerIsInChannel(player)){
			channel.addPlayer(player);
		}
		setFocusedChannel(channel, player);
		return true;
	}
	
	public static void initialize(){
		playerFocused = new HashMap<Player, Channel>();
		channels = new ArrayList<Channel>();
		playerState = new HashMap<Player, Boolean>();
		
		loadChannels();
		for (Player p: DanAndChat.server.getOnlinePlayers()){
			initializePlayerChannels(p);
		}
	}
	
	public static void playerLeaveChannel(Channel c, Player p){
		c.removePlayer(p);
		for (Channel ch: channels){
			if (ch.playerIsInChannel(p)){
				playerChangeChannel(ch, p);
				return;
			}
		}
		p.sendMessage(ChatColor.RED + "Could not find any channels. You are muted, until you join another channel.");
	}
	
	public static Channel getChannelWithShortcut(String name){
		for (Channel c: channels){
			if (c.getShortCut().equals(name)){
				return c;
			}
		}
		return null;
	}
	
	public static void initializePlayerChannels(Player player){
//		StringTokenizer strTok = new StringTokenizer(ExtensionManager
//				.permissions
//				.getGroupPermissionString(ExtensionManager.permissions.getGroup(player.getName()), "default-channels"),
//				",");
//		while (strTok.hasMoreTokens()){
//			playerChangeChannel(getChannelWithShortcut(strTok.nextToken()), player);
//		}
		
		playerChangeChannel(channels.get(0), player);
	}
	
	public static Channel getFirstFocusedChannel(Player player){
		Channel c;
		
//		if ((c = getChannelWithShortcut(ExtensionManager
//		.permissions
//		.getGroupPermissionString(ExtensionManager.permissions.getGroup(player.getName()), "focused-on-login"))) != null){
//			return c;
//		}
		return null;
	}
	
	public static void loadChannels(){
		channels = Settings.getChannels();

		
		DanAndChat.log.info(DanAndChat.sfPlugin + " Loaded " + channels.size() + " channels.");
	}
	
	public static void setPlayerState(Player player, boolean ic){
		if (playerState.containsKey(player)){
			playerState.remove(player);
		}
		playerState.put(player, ic);
	}
	
	public static boolean playerIsIc(Player player){
		if (playerState.containsKey(player)){
			return playerState.get(player);
		}
		return false;
		
	}
	
	/**
	 * Checks if <player> is in channel.
	 * @param player
	 * @param channel
	 * @return
	 */
	public static boolean playerIsInChannel(Player player, Channel channel){
		return channel.playerIsInChannel(player);
	}
	
	
	/**
	 * Gets this player's focused channel.
	 * @param player
	 * @return
	 */
	public static Channel getFocusedChannel(Player player){
		boolean yes = false;
		for (Channel c: channels){
			if (c.playerIsInChannel(player)){
				yes = true;
				break;
			}
		}
		if (yes){
			return playerFocused.get(player);
		} else {
			return null;
		}
		
	}
	
	public static void setFocusedChannel(Channel channel, Player player){
		if (playerFocused.containsKey(player)){
			playerFocused.remove(player);
		}
		playerFocused.put(player, channel);
		
	}
	
	public static void playerAddChannel(Channel c, Player p){
		if (!c.playerIsInChannel(p)){
			c.addPlayer(p);
		}
	}
	
	public static boolean channelExists(Channel c){
		if (channels.contains(c)){
			return true;
		}
		return false;
	}
}
