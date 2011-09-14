package com.gmail.andreasmartinmoerch.danandchat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.gmail.andreasmartinmoerch.danandchat.channel.Channel;

public class ChannelManager
{
  public static HashMap<Player, Channel> playerFocused;
  public static List<Channel> channels;
  private static HashMap<Player, Boolean> playerState;

  public static boolean playerChangeChannel(String channel, Player player)
  {
    Channel c = null;
    for (Channel ch: channels){
    	if (ch.getShortCut().equalsIgnoreCase(channel)){
    		c = ch;
    		break;
    	}
    }
    if (c == null) {
      player.sendMessage(ChatColor.RED + "Channel does not exist: " + channel);
      return true;
    }
    if (!c.playerIsInChannel(player)){
    	    c.addPlayer(player);
    }
    if (ChannelManager.playerFocused.containsKey(player)){
    	playerFocused.remove(player);
    }
    playerFocused.put(player, c);	

    return true;
  }

  public static boolean playerChangeChannel(Channel channel, Player player) {
    if (!channelExists(channel)) {
      player.sendMessage(ChatColor.RED + "Channel does not exist: " + channel.getName());
      return true;
    }

    if (!channel.playerIsInChannel(player)) {
      channel.addPlayer(player);
    }
    setFocusedChannel(channel, player);
    return true;
  }

  public static void initialize() {
    playerFocused = new HashMap<Player, Channel>();
    channels = new ArrayList<Channel>();
    playerState = new HashMap<Player, Boolean>();

    loadChannels();
  }

  public static void playerLeaveChannel(Channel c, Player p)
  {
    c.removePlayer(p);
    if (playerFocused.get(p) == c){
    	playerFocused.remove(p);
    }
    p.sendMessage(ChatColor.RED + "You are muted, until you join another channel.");
  }

  public static Channel getChannelWithShortcut(String name) {
    for (Channel c : channels) {
      if (c.getShortCut().equals(name)) {
        return c;
      }
    }
    return null;
  }

  public static void loadChannels() {
    channels = Settings.getChannels();

    DanAndChat.log.info("[DanAndChat] Loaded " + channels.size() + " channels.");
  }

  public static void setPlayerState(Player player, boolean ic) {
    if (playerState.containsKey(player)) {
      playerState.remove(player);
    }
    playerState.put(player, Boolean.valueOf(ic));
  }

  public static boolean playerIsIc(Player player) {
    if (playerState.containsKey(player)) {
      return ((Boolean)playerState.get(player)).booleanValue();
    }
    return false;
  }

  public static boolean playerIsInChannel(Player player, Channel channel)
  {
    return channel.playerIsInChannel(player);
  }

  public static Channel getFocusedChannel(Player player)
  {
    boolean yes = false;
    for (Channel c : channels) {
      if (c.playerIsInChannel(player)) {
        yes = true;
        break;
      }
    }
    if (yes) {
      return (Channel)playerFocused.get(player);
    }
    return null;
  }

  public static void setFocusedChannel(Channel channel, Player player)
  {
    if (playerFocused.containsKey(player)) {
      playerFocused.remove(player);
    }
    playerFocused.put(player, channel);
  }

  public static void playerAddChannel(Channel c, Player p)
  {
    if (!c.playerIsInChannel(p))
      c.addPlayer(p);
  }

  public static boolean channelExists(Channel c)
  {
    return channels.contains(c);
  }
}