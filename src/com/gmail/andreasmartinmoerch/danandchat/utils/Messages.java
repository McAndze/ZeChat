/**
 * 
 */
package com.gmail.andreasmartinmoerch.danandchat.utils;

/**
 * @author Huliheaden Contains nicks for different messages.
 */
public enum Messages {
	NO_COMPATIBLE_GROUP_MANAGER ("&4No compatible group manager found."),
	NO_PERMISSION_TO_TALK ("&4You are not allowed to chat. At all. You can chat with me though, but I'm quite shy."),
	IN_INVALID_CHANNEL ("&4You're in an invalid channel. Try your luck with \"/ch g\" or \"/ch l\""),
	NO_PERMISSION_CHANGE_CHANNEL ("&4You haven't got permission to do that."),
	COULD_NOT_FIND_CHANNEL_WITH_SHORTCUT ("&4Could not find channel with shortcut: &2%ARG0."),
	COULD_NOT_REJOIN_FOCUSED_CHANNEL ("&4Could not re-join previously focused channel.")
	;
	
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
