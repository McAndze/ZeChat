package com.landsofnavia.naviachat.utils;

import org.bukkit.util.config.Configuration;

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
		this.file.createNewFile();
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
		String customMessage = "";
		
		if ((customMessage = this.configuration.getString(this.prefix.toLowerCase() + "." + message.toString(), null)) == null){
			customMessage = message.getFallback();
		}
		
		if (debug){
			customMessage += "(" + message.toString() + ")";
		}
		
		NaviaParser dap = new NaviaParser(this.plugin);
		customMessage = dap.parseColours(customMessage);
		customMessage = dap.parseAmpersands(customMessage);
		
		return customMessage;
	}
}
