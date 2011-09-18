package com.gmail.andreasmartinmoerch.danandchat.channel;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.gmail.andreasmartinmoerch.danandchat.DanAndChat;
import com.gmail.andreasmartinmoerch.danandchat.Settings;
import com.gmail.andreasmartinmoerch.danandchat.chmarkup.ChInterpreter;
import com.gmail.andreasmartinmoerch.danandchat.plugins.ExtensionManager;
/**
 * Holds methods for encoding messages, for specific channels.
 * @author Andreas
 *
 */
public class MessageHandler {
	private DanAndChat plugin;
	public MessageHandler(DanAndChat plugin){
		this.plugin = plugin;
	}
	
	private static final int LINEBREAK = 60;
	
	public ArrayList<String> formatMessage(Channel c, Player sender, String originalMessage){
		
		return breakMessage((new ChInterpreter(this.plugin)).interpretString(c.getFormatting(), c, sender, originalMessage));
	}
	
	// TODO: Fix this. It's BUGGED AS HELL
	public static ArrayList<String> breakMessage(String message){
		ArrayList<String> lines = new ArrayList<String>();
		lines.add(message);
//		if (message.length() < LINEBREAK){
//			lines.add(message);
//			return lines;
//		}
//			
//		String[] split = message.split(" ");
//		
//		String curLine = "";
//		int linebreakmultiplierhigh = 1;
//		int linebreaklowrange = -1;
//		for (int i = 0; i < split.length; i++){
//			String s = split[i];
//			int tl = s.length() + curLine.length();
//			if (tl < (LINEBREAK * linebreakmultiplierhigh) && tl > linebreaklowrange){
//				curLine += s + " ";
//				if (linebreakmultiplierhigh == 2){
//					DanAndChat.server.getPlayer("Mcandze").sendMessage(s + " <-- Split (s). " + curLine + " <-- curLine");
//				}
//			} else {
//				lines.add(curLine);
//				curLine = "";
//				linebreakmultiplierhigh++;
//				linebreaklowrange = linebreaklowrange + LINEBREAK;
//				DanAndChat.server.getPlayer("Mcandze").sendMessage("Hi!");
//			}
//		}
		
		return lines;
		
	}
}