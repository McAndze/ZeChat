package com.landsofnavia.naviachat.channel.filter.noargs;

import java.util.List;

import org.bukkit.entity.Player;

import com.landsofnavia.naviachat.channel.Channel;
import com.landsofnavia.naviachat.channel.filter.Filter;

public class FilterWithoutArgs implements Filter{
	private Channel channel;

	@Override
	public boolean filterPlayer(Player player) {
		return false;
	}

	@Override
	public Channel getChannel() {
		return channel;
	}

	@Override
	public void setChannel(Channel channel) {
		this.channel = channel;
	}

	@Override
	public void setValues(List<String> values) {
		// TODO Auto-generated method stub
	}
}
