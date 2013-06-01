package com.landsofnavia.naviachat.channel.filter.noargs;

import org.bukkit.entity.Player;


public class FilterWithoutArgs_inChannel extends FilterWithoutArgs {

	@Override
	public boolean filterPlayer(Player player) {
		if (this.getChannel().playerIsInChannel(player)){
			return true;
		}
		return false;
	}
	
}
