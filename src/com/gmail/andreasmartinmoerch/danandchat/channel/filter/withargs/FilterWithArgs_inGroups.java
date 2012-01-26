package com.gmail.andreasmartinmoerch.danandchat.channel.filter.withargs;

import java.util.Arrays;
import java.util.List;

import org.bukkit.entity.Player;

import com.gmail.andreasmartinmoerch.danandchat.plugins.PermissionChecker;

public class FilterWithArgs_inGroups extends FilterWithArgs {
	@Override
	public boolean filterPlayer(Player player) {
		List<String> groups = Arrays.asList(PermissionChecker.permission.getPlayerGroups(player));
		boolean passAll = true;
		for (String s: this.getValues()){
			if (!groups.contains(s)){
				passAll = false;
			}
			if (!passAll) break;
		}
		return passAll;
	}
}
