/**
 * 
 */
package com.gmail.andreasmartinmoerch.danandchat.utils;

/**
 * @author Huliheaden Contains nicks for different messages.
 */
public enum Messages {
	NO_COMPATIBLE_GROUP_MANAGER ("No compatible group manager");
	
	private final String fallback;
	
	private Messages(final String fallback){
		this.fallback = fallback;
	}
	
	/**
	 * Gets the fallback, if it can't find a custom message.
	 * @return fallback for chosen message.
	 */
	public String getFallback(){ return fallback; }
}
