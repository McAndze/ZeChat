package com.gmail.andreasmartinmoerch.danandchat.plugins;

import com.gmail.andreasmartinmoerch.danandchat.DanAndChat;

public class PermissionChecker {
	/**
	 * Permission nodes
	 */
	
	public static final String all = ".*";

	
	public static final String prefix = "danandchat";
		public static final String channel = ".channel";
			public static final String ban = ".ban";
			public static final String unban = ".unban";
			public static final String list = ".list";
				public static final String in = ".in";
				public static final String available = ".available";
		public static final String leaveChannel = ".leavechannel";
		public static final String canTalk = ".cantalk";
		public static final String tell = ".tell";
		public static final String me = ".me";
		public static final String changeChannel = ".changechannel";
		public static final String defaultChannel = ".defaultchannel";
		public static final String focusedChannel = ".focusedchannel";
	
	private DanAndChat plugin;
	//TODO: Write one function that covers all of these functions instead. (Move permission nodes to the CommandHandler, maybe)	
		
	public PermissionChecker(DanAndChat plugin){
		this.plugin = plugin;
	}
}
