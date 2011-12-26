/**
 * 
 */
package com.gmail.andreasmartinmoerch.danandchat;

import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.event.server.ServerListener;

/**
 * @author Huliheaden
 *
 */
public class DanAndChatServerListener extends ServerListener {

	private DanAndChat plugin;
	
	public DanAndChatServerListener(DanAndChat plugin){
		this.plugin = plugin;
	}
	
	@Override
	public void onPluginEnable(PluginEnableEvent event) {
		String name = event.getPlugin().getDescription().getName();
		
		if (name.equals("PermissionsBukkit")){
			this.plugin.getExtensionManager().loadPermissions(event);
		}
		
		if (name.equals("ColorMe")){
			this.plugin.getExtensionManager().loadColorMe(event);
		}
		
		/*if (name.equals("")){
			this.plugin.getExtensionManager()
		}*/
	}
	
	

}
