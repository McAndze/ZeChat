package com.landsofnavia.naviachat.channel.filter.withargs;

import java.util.Arrays;
import java.util.List;

import org.bukkit.entity.Player;

import com.landsofnavia.naviachat.plugins.PermissionChecker;


public class FilterWithArgs_inGroup extends FilterWithArgs{

	/* @Override
	public boolean filterPlayer(Player player) {
		List<String> groups = Arrays.asList(PermissionChecker.permission.getPlayerGroups(player));
		for (String s: this.getValues()){
			if (groups.contains(s)){
				return true;
			}
		}
		return false;
	}*/
	
	public boolean filterPlayer(Player player){
		return true;
	}
	
}
