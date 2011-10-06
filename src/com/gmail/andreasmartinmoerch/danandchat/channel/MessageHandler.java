package com.gmail.andreasmartinmoerch.danandchat.channel;

import java.util.ArrayList;
import java.util.logging.Logger;

import org.bukkit.entity.Player;

import com.gmail.andreasmartinmoerch.danandchat.DanAndChat;

/**
 * Holds methods for encoding messages, for specific channels.
 * 
 * @author Andreas
 * 
 */
public class MessageHandler {
	private final DanAndChat plugin;

	public MessageHandler(DanAndChat plugin) {
		this.plugin = plugin;
	}

	private static final int LINEBREAK = 53;

	public ArrayList<String> formatMessage(String format, Channel c, Player sender,
			String originalMessage) {
		return breakMessage(this.plugin.getDanAndParser().fullParse(format, true, c, sender, originalMessage));
//		return breakMessage((new DanAndParser(this.plugin)).interpretString(
//				c.getFormatting(), c, sender, originalMessage));
	}

	// TODO: Fix this. It's BUGGED AS HELL
	public static ArrayList<String> breakMessage(String message) {
		ArrayList<String> lines = new ArrayList<String>();
		if (message.length() < LINEBREAK){
			lines.add(message);
			return lines;
		}
		
		String[] words = message.split(" ");
		
		
		Logger debug = Logger.getLogger("Minecraft");
		
		String s = "";
		for (String str: words){
			debug.info(s);
			for (String debugstring: lines){
				if (debugstring != null){
					debug.info("Debog: " + debugstring);
				}
			}
			String backup = new String(s);
			s += str + " ";
			if (s.length() > LINEBREAK){
				s = backup;
				lines.add(new String(s));
				s = "";
			}
		}
		
		return lines;
		// if (message.length() < LINEBREAK){
		// lines.add(message);
		// return lines;
		// }
		//
		// String[] split = message.split(" ");
		//
		// String curLine = "";
		// int linebreakmultiplierhigh = 1;
		// int linebreaklowrange = -1;
		// for (int i = 0; i < split.length; i++){
		// String s = split[i];
		// int tl = s.length() + curLine.length();
		// if (tl < (LINEBREAK * linebreakmultiplierhigh) && tl >
		// linebreaklowrange){
		// curLine += s + " ";
		// if (linebreakmultiplierhigh == 2){
		// DanAndChat.server.getPlayer("Mcandze").sendMessage(s +
		// " <-- Split (s). " + curLine + " <-- curLine");
		// }
		// } else {
		// lines.add(curLine);
		// curLine = "";
		// linebreakmultiplierhigh++;
		// linebreaklowrange = linebreaklowrange + LINEBREAK;
		// DanAndChat.server.getPlayer("Mcandze").sendMessage("Hi!");
		// }
		// }

		return lines;

	}
}
