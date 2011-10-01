package com.gmail.andreasmartinmoerch.danandchat.channel;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

import com.gmail.andreasmartinmoerch.danandchat.DanAndChat;

public class Channels {
	private DanAndChat plugin;
	public List<Player> init = new ArrayList<Player>();
	public List<Channel> channels;
	
	public Channels(DanAndChat plugin){
		this.plugin = plugin;

	}
	
	public void initialize(){
		channels = this.plugin.getSettings().getChannels();
	    this.plugin.log.info("[DanAndChat] Loaded " + channels.size() + " channels.");
	    
		for (Player p: plugin.getServer().getOnlinePlayers()){
			initializePlayer(p);
		}
	}
	
	/**
	 * Initalizes a Player's channels.
	 * @param p Player to initialize.
	 */
	public void initializePlayer(Player p){
		List<Channel> channels = this.plugin.getChannels().channels;
 		for (Channel c: channels){
 			if (c.isAutoJoin()){
 				c.addPlayer(p);
 			}
 			if(c.isAutoFocus() && !c.getBanned().contains(p.getName())){
 				c.addFocus(p);
 			}
 		}
 		if (!this.plugin.getChannels().playerHasFocusedChannel(p)){
 			Channel c = channels.get(0);
 			if (!c.getBanned().contains(p.getName()))
 			{
 	 			c.addFocus(p);
 			}
 		}
 		init.add(p);
	}
	
	public boolean playerHasFocusedChannel(Player p){
		for(Channel c: channels){
			if (c.getFocused().contains(p)){
				return true;
			}
		}
		return false;
	}
	
	public Channel getFocusedChannel(Player p){
		if (!playerHasFocusedChannel(p)){
			return null;
		}
		Channel ch = null;
		boolean found = false;
		for (Channel c: channels){
			if (c.getFocused().contains(p)){
				if (found){
					c.getFocused().remove(p);
				} else {
					ch = c;
					found = true;
				}
			}
		}
		return ch;
	}
	
	public Channel getChannelWithShortcut(String shortcut){
		for (Channel c: channels){
			if (c.getShortCut().equalsIgnoreCase(shortcut)){
				return c;
			}
		}
		return null;
	}
}
