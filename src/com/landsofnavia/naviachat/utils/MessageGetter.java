package com.landsofnavia.naviachat.utils;

import com.landsofnavia.naviachat.NaviaChat;
import com.landsofnavia.naviachat.parsing.NaviaParser;

/**
 * 
 * @author Huliheaden
 * 
 */
public class MessageGetter {
	/**
	 * Fallback prefix.
	 */
	public static final String DEFAULT_PREFIX = "default";
	private final NaviaChat plugin;	
	public static boolean debug = false;
	
	private MessageFile file;
	
	public MessageGetter(NaviaChat plugin) {
		this.plugin = plugin;
		this.file = new MessageFile("plugins/NaviaChat/messages/messages.nc");
		try {
			this.file.createNewFile();
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	
	public void writeDefaultMessagesToConfig(boolean overwrite){
		this.file.restoreDefaults();
	}
	
	public String getMessageWithArgs(Message message, String... args){
		String customMessage = getMessage(message);
		int i = 0;
		for (String s: args){
			customMessage = customMessage.replaceAll("%ARG" + String.valueOf(i), s);
		}
		
		return customMessage;
	}
	
	/**
	 * Not done.
	 * @param message
	 * @return
	 */
	public String getMessage(Message message){
		String customMessage = this.file.getMessage(message);
		
		if (debug){
			customMessage += "(" + message.toString() + ")";
		}
		
		NaviaParser nap = new NaviaParser(this.plugin);
		customMessage = nap.parseColours(customMessage);
		customMessage = nap.parseAmpersands(customMessage);
		
		return customMessage;
	}
}
