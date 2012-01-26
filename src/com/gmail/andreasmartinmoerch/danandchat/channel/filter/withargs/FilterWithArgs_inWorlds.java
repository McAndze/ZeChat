package com.gmail.andreasmartinmoerch.danandchat.channel.filter.withargs;

import org.bukkit.entity.Player;

public class FilterWithArgs_inWorlds extends FilterWithArgs {

	@Override
	public boolean filterPlayer(Player player) {
		boolean passAll = true;
		for (String s: this.getValues()){
			if (!passAll){
				break;
			}
			if (!player.getWorld().getName().equalsIgnoreCase(s)){
				passAll = false;
			}
		}
		return passAll;
	}
	
}
