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
	NO_PERMISSION_CHANNEL_RELOAD ("&4No permission."),
	NO_PERMISSION_CHANNEL_MUTE ("&4No permission."),
	NO_PERMISSION_CHANNEL_UNMUTE ("&4No permission."),
	NO_PERMISSION_CHANNEL_BAN ("&4No permission."),
	NO_PERMISSION_CHANNEL_LIST_AVAILABLE ("&4No permission."),
	NO_PERMISSION_CHANNEL_LIST_IN ("&4No permission."),
	NO_PERMISSION_LEAVECHANNEL ("&4You don't have permission to do that."),
	YOU_ARE_NOT_IN_CHANNEL ("&4You are not in that channel!"),
	COULD_NOT_FIND_CHANNEL_WITH_SHORTCUT ("&4Could not find channel with shortcut: &2%ARG0."),
	COULD_NOT_REJOIN_FOCUSED_CHANNEL ("&4Could not re-join previously focused channel."),
	UNKNOWN_ARG ("&4Unknown arg: &2%ARG0"),
	YOU_HAVE_BEEN_MUTED ("&4You have been muted from the channel: &2%ARG0"),
	YOU_HAVE_BEEN_UNMUTED ("&6You have been &2unmuted &6from the channel: &2%ARG0"),
	YOU_HAVE_BEEN_BANNED ("&4You have been banned from the channel: &2%ARG0"),
	YOU_HAVE_BEEN_UNBANNED ("&6You have been &2unbanned &6from the channel: &2%ARG0"),
	COULD_NOT_FIND_CHANNEL ("Could not find channel: %ARG0"),
	CHANGED_CHANNEL_TO ("&6Changed channel to: &2%ARG0"),
	CHANGED_FOCUS_TO ("&6Changed focus to: &2%ARG0"),
	LEFT_CHANNEL ("&4Left channel: &2%ARG0"),
	UNFOCUS_CHANNEL ("&4Unfocused channel: &2%ARG0"),
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
