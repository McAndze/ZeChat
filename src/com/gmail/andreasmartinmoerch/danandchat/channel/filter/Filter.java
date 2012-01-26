package com.gmail.andreasmartinmoerch.danandchat.channel.filter;

import java.util.List;

import org.bukkit.entity.Player;

import com.gmail.andreasmartinmoerch.danandchat.channel.Channel;

public interface Filter {
	public abstract Channel getChannel();
	public abstract void setChannel(Channel channel);
	public abstract boolean filterPlayer(Player player);
	public abstract void setValues(List<String> values);
}
