package com.landsofnavia.naviachat.channel.filter.withargs;

import java.util.List;

import org.bukkit.entity.Player;

import com.landsofnavia.naviachat.channel.Channel;
import com.landsofnavia.naviachat.channel.filter.Filter;

public class FilterWithArgs implements Filter {
	private List<String> values = null;
	private Channel channel;
	
	@Override
	public void setValues(List<String> values){
		this.values = values;
	}
	
	public List<String> getValues(){
		return values;
	}

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

}
