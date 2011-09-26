package com.gmail.andreasmartinmorch.danandchat.plugins;

import org.bukkit.plugin.Plugin;

import com.gmail.andreasmartinmorch.danandchat.DanAndChat;
import com.platymuus.bukkit.permissions.PermissionsPlugin;
import com.sparkedia.valrix.ColorMe.ColorMe;

public class ExtensionManager {
	  boolean usesrpg = false;
	  public ColorMe color;
	  public boolean isUsingNaviaChar = false;
	  private DanAndChat plugin;
	  public PermissionsPlugin permissionsBukkit;
	
	public ExtensionManager(DanAndChat plugin){
		this.plugin = plugin;
	}
	
	public void initialize() {
		    loadColorMe();
		    initRPGWorld();
		    loadPermissions();
	}
	
	 public boolean usesPermissionsBukkit() {
		    return this.permissionsBukkit != null;

	 }

		  public void loadPermissions()
		  {
		    Plugin plugin;
		    if ((plugin = DanAndChat.server.getPluginManager().getPlugin("PermissionsBukkit")) != null)
		      this.permissionsBukkit = ((PermissionsPlugin)plugin);
		  }

		  public void loadColorMe()
		  {
		    Plugin plugin;
		    if ((plugin = DanAndChat.server.getPluginManager().getPlugin("ColorMe")) != null)
		      this.color = ((ColorMe)plugin);
		  }

		  public boolean isUsingColorMe()
		  {
		    return this.color != null;
		  }

		  public void loadNaviaChar() {
		    if (DanAndChat.server.getPluginManager().getPlugin("NaviaChar") != null)
		      this.isUsingNaviaChar = true;
		  }

		  public boolean usesRPGWorld()
		  {
		    return this.usesrpg;
		  }

		  public void initRPGWorld()
		  {
		  }
}
