package com.gmail.andreasmartinmoerch.danandchat.channel;

import java.util.List;

import org.bukkit.entity.Player;

import com.gmail.andreasmartinmoerch.danandchat.DanAndChat;
import com.gmail.andreasmartinmoerch.danandchat.Settings;

public class Channels {
	private DanAndChat plugin;
	
	public List<Channel> channels;
	
	public Channels(DanAndChat plugin){
		this.plugin = plugin;
	    channels = Settings.getChannels();

	    DanAndChat.log.info("[DanAndChat] Loaded " + channels.size() + " channels.");
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
