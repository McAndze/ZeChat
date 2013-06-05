package com.landsofnavia.naviachat.utils;

import java.io.File;

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
			new File (this.file.getParent()){{mkdirs();}};
			this.file.createNewFile();
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	
	public void writeDefaultMessagesToConfig(boolean overwrite){
		this.file.restoreDefaults();
		this.file.reload();
	}
	
	public String getMessageWithArgs(Message message, String... args){
		System.out.println(message.toString());
		System.out.println(message.getFallback());
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
		// debug
		System.out.println(message.toString());
		System.out.println(customMessage);
		
		if (debug){
			customMessage += "(" + message.toString() + ")";
		}
		
		NaviaParser nap = new NaviaParser(this.plugin);
		customMessage = nap.parseColours(customMessage);
		customMessage = nap.parseAmpersands(customMessage);
		
		return customMessage;
	}
}
