package com.landsofnavia.naviachat.plugins;

import java.util.ArrayList;
import java.util.List;

import com.landsofnavia.naviachat.NaviaChat;

public class ExtensionManager {
	private NaviaChat plugin;
	public List<String> enabledPlugins = new ArrayList<String>();
	
	public static final String TOWNY = "Towny";
	public static final String TOWNYCHAT = "TownyChat";
	public static final String VAULT = "Vault";
	public final List<String> usablePlugins = new ArrayList<String>(){{
		add(TOWNY);
		add(TOWNYCHAT);
		add(VAULT);
	}};
	
	

	public ExtensionManager(NaviaChat plugin) {
		this.plugin = plugin;
	}
	
	public void initialize(){
//		for (Plugin plugin: this.plugin.getServer().getPluginManager().getPlugins()){
//			PluginDescriptionFile p = plugin.getDescription();
//			if (usablePlugins.contains(p.getName())){
//				enabledPlugins.add(p.getName());
//			}
//		}
//		for (String s: this.enabledPlugins){
//			if (s.equalsIgnoreCase(TOWNY)){
//				Plugin test = this.plugin.getServer().getPluginManager().getPlugin(TOWNY);
//				try {
//					towny = (Towny)test;
//				} catch (Exception e){
//					towny = null;
//					enabledPlugins.remove(TOWNY);
//					enabledPlugins.remove(TOWNYCHAT);
//				}
//				continue;
//			}
//			if (s.equalsIgnoreCase(TOWNYCHAT) && enabledPlugins.contains(TOWNYCHAT)){
//				Plugin test = this.plugin.getServer().getPluginManager().getPlugin(TOWNYCHAT);
//				try {
//					townyChat = (Chat)test;
//				} catch (Exception e){
//					towny = null;
//					enabledPlugins.remove(TOWNYCHAT);
//				}
//			}
//			
//			
//			
//		}
	}
}
