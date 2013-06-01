package com.landsofnavia.naviachat.channel.filter.withargs;

import org.bukkit.entity.Player;

import com.landsofnavia.naviachat.channel.Channel;

public class FilterWithArgs_inChannels extends FilterWithArgs {

	@Override
	public boolean filterPlayer(Player player) {
		for (String s: this.getValues()){
			Channel c = this.getChannel().plugin.getChannels().getChannelWithName(s);
			if (c == null){
				this.getChannel().plugin.getDanandLogger().logMsg("Channel not found: " + s, "WARNING");
				return false;
			}
			if (c.playerIsInChannel(player)){
				return true;
			}
		}
		return false;
	}
	
}
