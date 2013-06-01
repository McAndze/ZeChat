package com.landsofnavia.naviachat.channel.filter.withargs;

import org.bukkit.entity.Player;


public class FilterWithArgs_hasName extends FilterWithArgs {

	@Override
	public boolean filterPlayer(Player player) {
		for (String s: this.getValues()){
			if (player.getName().equalsIgnoreCase(s)){
				return true;
			}
		}
		return false;
	}

}
