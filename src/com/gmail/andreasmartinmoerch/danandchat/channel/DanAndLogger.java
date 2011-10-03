package com.gmail.andreasmartinmoerch.danandchat.channel;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;
import java.util.logging.Logger;

import com.gmail.andreasmartinmoerch.danandchat.DanAndChat;
import com.gmail.andreasmartinmoerch.danandchat.utils.MessageGetter;
import com.gmail.andreasmartinmoerch.danandchat.utils.Messages;


public class DanAndLogger{
	public static Logger log = Logger.getLogger("Minecraft");
	private File thislog;
	private DanAndChat plugin;
	private Channel channel = null;
	/**
	 * Is a Vector, as I might add some timed writings.
	 */
	private Vector<String> toWrite;
	
	public DanAndLogger(DanAndChat plugin, String thislog, String file){
		this.plugin = plugin;
		this.toWrite = new Vector<String>();
		File dirs;
		if (file == null){
			dirs = new File("plugins" + File.separator + "DanAndChat" + File.separator + "logs");
		} else {
			dirs = new File("plugins" + File.separator + "DanAndChat" + File.separator + "logs" + File.separator + file);
		}
		dirs.mkdirs();
		File fThisLog = new File(dirs, thislog);
		this.thislog = fThisLog;
	}
	
	public DanAndLogger(DanAndChat plugin, Channel channel){
		this.plugin = plugin;
		this.channel = channel;
		File dirs;
		dirs = new File("plugins" + File.separator + "DanAndChat" + File.separator + "logs" + File.separator + "channels");
		dirs.mkdirs();
		File thislog = new File(dirs, this.channel.getName() + ".yml");
		this.thislog = thislog;
	}
	
	public void initialize(){
		if (!this.thislog.exists()){
			try {
				this.thislog.createNewFile();
			} catch (IOException e){
				log.warning("[DanAndChat] Could not make file " + this.thislog.getName() + "!");
				e.printStackTrace();
			}
			
		}
		this.toWrite = new Vector<String>();
	}
	
	public void logMsg(Messages message, String type, String... args){
		String sMessage = this.plugin.getMessageGetter().getMessageWithArgs(message, args);
		this.logMsg(sMessage, type);
	}
	
	public void logMsg(String message, String type){	
		String toLog;
		if (this.channel == null){
			if (type != null && !type.isEmpty()){
				toLog = "[DanAndChat] [" + type + "] " + ": " + message;
			} else {
				toLog = "[DanAndChat]: " + message;
			}
			
		} else {
			if (type != null && !type.isEmpty()){
				toLog = "[DanAndChat] [" +channel.getName()+ "]: " + message;
			} else {
				toLog = "[DanAndChat] [" + type + "][" +channel.getName()+ "]: " + message;
			}
			
		}
		
		if (MessageGetter.debug == false){
			if (type != null && !type.equalsIgnoreCase("debug") ){
				log.info(toLog);
			}
		} else {
			log.info(toLog);
		}
		
		toWrite.add(toLog);
	}
	
	public void logMsg(Messages message, String type){
		String sMessage = this.plugin.getMessageGetter().getMessage(message);
		this.logMsg(sMessage, type);
	}
	
	
	public void writeToFile(){
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(this.thislog, true));
			String sToWrite = "";
			for (String s: toWrite){
				sToWrite += s + "\r\n";
			}
			writer.write(sToWrite);
			toWrite.clear();
			writer.close();
		} catch (IOException e){
			e.printStackTrace();
			log.warning("[DanAndChat] Could not write to file: " + this.thislog.getName());
		} 
	}
}
