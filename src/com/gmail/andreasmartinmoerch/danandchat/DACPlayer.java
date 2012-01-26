package com.gmail.andreasmartinmoerch.danandchat;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.bukkit.entity.Player;

public class DACPlayer {
	public static Set<DACPlayer> players = new TreeSet<DACPlayer>();
	public static DACPlayer getDACPlayer(Player player){
		for (DACPlayer dPlayer: players){
			if (dPlayer.getPlayer().equals(player)){
				return dPlayer;
			}
		}
		// Hopefully this will never be used.
		return null;
	}
	
	private Player player;
	private boolean chatCancelled;
	private List<String> toSend;
	
	public DACPlayer(Player player){
		this.player = player;
		this.chatCancelled = false;
		this.toSend = new ArrayList<String>();
	}
	
	public void sendMessage(String s){
		if (isChatCancelled()){
			toSend.add(s);
		} else {
			getPlayer().sendMessage(s);
		}
	}
	
	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public boolean isChatCancelled() {
		return chatCancelled;
	}

	public void enableChat(){
		this.chatCancelled = false;
		if (getToSend() == null || getToSend().isEmpty()){
			return;
		}
		getPlayer().sendMessage("§6[DanAndChat]:§f held messages:");
		for (String s: getToSend()){
			getPlayer().sendMessage(s);
		}
	}
	
	public void disableChat(){
		this.chatCancelled = true;
	}

	public List<String> getToSend() {
		return toSend;
	}

	public void setToSend(List<String> toSend) {
		this.toSend = toSend;
	}
	
	
}
