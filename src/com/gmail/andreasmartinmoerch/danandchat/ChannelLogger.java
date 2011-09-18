package com.gmail.andreasmartinmoerch.danandchat;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.gmail.andreasmartinmoerch.danandchat.channel.Channel;

public class ChannelLogger {
	public static Logger log = Logger.getLogger("Minecraft");
	private File chLog;
	private Channel channel;
	private List<String> toWrite;
	
	public ChannelLogger(Channel channel){
		this.channel = channel;
		this.chLog = new File("plugins" + File.separator + "DanAndChat" + File.separator + "logs", channel.getName() + ".log");
		if (!chLog.exists()){
			try {
				chLog.createNewFile();
			} catch (IOException e){
				log.warning("[DanAndChat] Could not make file " + chLog.getName() + "!");
			}
			
		}
		this.toWrite = new ArrayList<String>();
	}
	
	public void logMsg(String s, String type){
		String toLog = "[DanAndChat] [" + type + "] " + channel.getName() + ": " + s;
		log.info(toLog);
		toWrite.add(toLog);
	}
	
	public void write(){
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(chLog, true));
			String sToWrite = "";
			for (String s: toWrite){
				sToWrite += s + "\r\n";
			}
			writer.write(sToWrite);
			toWrite.clear();
			writer.close();
		} catch (IOException e){
			e.printStackTrace();
			log.warning("[DanAndChat] Could not write to file: " + chLog.getName());
		} 
	}
}
