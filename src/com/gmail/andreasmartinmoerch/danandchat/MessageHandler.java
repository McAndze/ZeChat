package com.gmail.andreasmartinmoerch.danandchat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.gmail.andreasmartinmoerch.danandchat.channel.Channel;
import com.gmail.andreasmartinmoerch.danandchat.chmarkup.ChInterpreter;
import com.gmail.andreasmartinmoerch.danandchat.plugins.ExtensionManager;
/**
 * Holds methods for encoding messages, for specific channels.
 * @author Andreas
 *
 */
public class MessageHandler {
	
	private static final int LINEBREAK = 60;
	
	public static ArrayList<String> formatMessage(Channel c, Player sender, String originalMessage){
		
		return breakMessage(ChInterpreter.interpretString(c.getFormatting(), c, sender, originalMessage));
	}
	
	public void noOneIsNear(Player p){
		ArrayList<String> messages = new ArrayList<String>();
		try {
			File file = new File(Settings.mainDirectory + File.separator + "messages.txt");
			if (!file.exists()){
				file.createNewFile();
			}
			BufferedReader br = new BufferedReader(new FileReader(file));
			String sCurLine;
			while ((sCurLine = br.readLine()) != null){
				messages.add(sCurLine);
			}
			br.close();
		} catch (Exception e){
			e.printStackTrace();
		}
		Random test = new Random();
		if (!messages.isEmpty()){
			p.sendMessage(ChatColor.GREEN + messages.get(test.nextInt(messages.size())));
		} else {
			p.sendMessage(ChatColor.GREEN + "No one is in range.");
		}
		
	}
	
//	public static String encodeGlobalMessage(Player player, String message, Channel channel){
//		// ['Color''ChannelName']<'PlayerColor''PlayerName'> 'Message'
//		String prefix;
//		String finalString;
//		String playerColor = "f";
////		prefix = ExtensionManager.permissions.getGroupPrefix(ExtensionManager.permissions.getGroup(player.getName()));
//		// TODO: Playercolor, from Characterizationing plugin.
//		
//		finalString =
//			"§" + channel.getColor()
//			+ "[" + channel.getName() + "]"
//			+ "§" + playerColor + "<" + "§" + prefix + player.getDisplayName() + "§f> "
//			+ message;
//		
//		// Final String
//		
//		return finalString;
//	}
	
	public static boolean playerCanTalk(Player player, DanAndChat instance){
		if (ExtensionManager.permissions.has(player, "naviachat.chat.cantalk")){
			return true;
		} else {
			player.sendMessage(ChatColor.RED + "You are muted by an administrator.");
			return false;
		}
	}
	
	public static String getIcEmote(Player sender, String action){
		String name = "";
		
		if (ExtensionManager.isUsingNaviaChar){
//			if (CharHandler.playerHasACharacter(sender)){
//				name = CharHandler.getCharacterByPlayerName(sender.getName()).getCharacterName().split(" ")[0];
//			} else {
//				name = sender.getDisplayName();
//			}
		} else {
			name = sender.getDisplayName();
		}
		
		String message = ChatColor.AQUA
			+ "* "
			+ name
			+ " "
			+ ChatColor.WHITE
			+ action;
		
		return message;
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
