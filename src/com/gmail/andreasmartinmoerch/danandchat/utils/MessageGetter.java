package com.gmail.andreasmartinmoerch.danandchat.utils;

import org.bukkit.util.config.Configuration;

/**
 * 
 * @author Huliheaden
 * 
 */
public class MessageGetter {
	/**
	 * Fallback prefix.
	 */
	public static final String DEFAULT_PREFIX = "messages";

	private final Configuration configuration;
	private final String prefix;

	/**
	 * Constructor with pre-defined prefix.
	 * 
	 * @param configuration
	 * @param prefix
	 */
	public MessageGetter(Configuration configuration, String prefix) {
		this.configuration = configuration;
		this.prefix = prefix;
	}

	/**
	 * Uses fallback prefix.
	 * 
	 * @param configuration
	 */
	public MessageGetter(Configuration configuration) {
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
}
