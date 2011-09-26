package com.gmail.andreasmartinmorch.danandchat.channel;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;
import java.util.logging.Logger;

/**
 * The ChannelLogger class is containing functions for logging messages from a
 * channel to a file, while also outputting it on the screen. Every Channel has
 * a ChannelLogger.
 * 
 * @author andreas
 * 
 */
public class ChannelLogger {
	public static Logger log = Logger.getLogger("Minecraft");
	private final File chLog;
	private final Channel channel;
	private final Vector<String> toWrite;

	/**
	 * Creates and initializes the ChannelLogger.
	 * 
	 * @param channel
	 *            The owner of the ChannelLogger.
	 */
	public ChannelLogger(Channel channel) {
		this.channel = channel;
		this.chLog = new File("plugins" + File.separator + "DanAndChat"
				+ File.separator + "logs", channel.getName() + ".log");
		if (!chLog.exists()) {
			try {
				chLog.createNewFile();
			} catch (IOException e) {
				log.warning("[DanAndChat] Could not make file "
						+ chLog.getName() + "!");
			}

		}
		this.toWrite = new Vector<String>();
	}

	public void logMsg(String s, String type) {
		String toLog = "[DanAndChat] [" + type + "] " + channel.getName()
				+ ": " + s;
		log.info(toLog);
		toWrite.add(toLog);
	}

	public void write() {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(chLog,
					true));
			String sToWrite = "";
			for (String s : toWrite) {
				sToWrite += s + "\r\n";
			}
			writer.write(sToWrite);
			toWrite.clear();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
			log.warning("[DanAndChat] Could not write to file: "
					+ chLog.getName());
		}
	}
}
