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
	private final Configuration configuration;
	private String prefix;
	public static boolean debug = false;
	
	public static void writeDefaultMessagesToConfig(Configuration config, boolean overwrite){
		for (Messages message: Messages.values()){
			if (config.getString(DEFAULT_PREFIX + "." + message.toString(), null) == null || overwrite){
				config.setProperty(DEFAULT_PREFIX + "." + message.toString(), message.getFallback());
			}
			
		}
		config.save();
		config.load();
	}

	/**
	 * Constructor with pre-defined prefix.
	 * 
	 * @param configuration
	 * @param prefix
	 */
	public MessageGetter(NaviaChat plugin, Configuration configuration, String prefix) {
		this.plugin = plugin;
		this.configuration = configuration;
		this.prefix = prefix;
	}
	

	/**
	 * Uses fallback prefix.
	 * 
	 * @param configuration
	 */
	public MessageGetter(NaviaChat plugin, Configuration configuration) {
		this.plugin = plugin;
		this.configuration = configuration;
		this.prefix = DEFAULT_PREFIX;
	}
	
	/**
	 * 
	 * @return used Configuration.
	 */
	public Configuration getConfiguration() {
		return this.configuration;
	}

	/**
	 * 
	 * @return used prefix.
	 */
	public String getPrefix() {
		return this.prefix;
	}
	
	/**
	 * 
	 * @param prefix
	 */
	public void setPrefix(String prefix){
		this.prefix = prefix;
	}
	
	public String getMessageWithArgs(Messages message, String... args){
		String customMessage = getMessage(message);
		int i = 0;
		for (String s: args){
			customMessage = customMessage.replaceFirst("%ARG" + String.valueOf(i), s);
		}
		
		return customMessage;
	}
	
	/**
	 * Not done.
	 * @param message
	 * @return
	 */
	public String getMessage(Messages message){
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
