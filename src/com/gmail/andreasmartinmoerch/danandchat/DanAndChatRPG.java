package com.gmail.andreasmartinmoerch.danandchat;

import org.bukkit.event.player.PlayerChatEvent;

import com.gmail.andreasmartinmoerch.danandchat.DanAndChat;

import me.samkio.RPGWorld.chat.RPGWorldChatPlugin;

public class DanAndChatRPG extends RPGWorldChatPlugin {
	private DanAndChat plugin;
	
	public DanAndChatRPG(DanAndChat plugin){
		this.plugin = plugin;
	}

	@Override
	public void onPlayerChat(PlayerChatEvent e) {
		this.plugin.playerListener.onChat(e);
	}

}
