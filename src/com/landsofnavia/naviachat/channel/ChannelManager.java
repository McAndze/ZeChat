package com.landsofnavia.naviachat.channel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.entity.Player;

import com.landsofnavia.naviachat.NaviaChat;

public class ChannelManager {
	private NaviaChat plugin;
	public List<Player> init = new ArrayList<Player>();
	public List<Channel> channels;
	public HashMap<String, List<String>> ignoring;
	
	public ChannelManager(NaviaChat plugin){
		this.plugin = plugin;
		this.ignoring = new HashMap<String, List<String>>();
	}
	
	public void initialize(){
		channels = this.plugin.getSettings().getChannels();
		NaviaLogger.log.info(String.valueOf(channels.size()));
		this.plugin.getDanandLogger().logMsg("Loaded " + channels.size() + " channels.", null);
	    
		for (Player p: plugin.getServer().getOnlinePlayers()){
			initializePlayer(p);
		}
	}
	
	/**
	 * Initalizes a Player's channels.
	 * @param p Player to initialize.
	 */
	public void initializePlayer(Player p){
		List<Channel> channels = this.channels;
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
			if (c.getFocused().contains(p.getName())){
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
			if (c.getFocused().contains(p.getName())){
				if (found){
					// Mainly for clean up. Haven't tested if it ever was needed.
					c.removeFocus(p);
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
	
	public Channel getChannelWithName(String name){
		for (Channel c: channels){
			if (c.getName().equalsIgnoreCase(name)){
				return c;
			}
		}
		return null;
	}
}
