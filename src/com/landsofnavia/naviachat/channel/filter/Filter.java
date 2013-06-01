package com.landsofnavia.naviachat.channel.filter;

import java.util.List;

import org.bukkit.entity.Player;

import com.landsofnavia.naviachat.channel.Channel;

public interface Filter {
	public abstract Channel getChannel();
	public abstract void setChannel(Channel channel);
	public abstract boolean filterPlayer(Player player);
	public abstract void setValues(List<String> values);
}
