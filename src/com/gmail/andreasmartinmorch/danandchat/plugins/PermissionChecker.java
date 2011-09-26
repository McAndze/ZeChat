package com.gmail.andreasmartinmorch.danandchat.plugins;

import com.gmail.andreasmartinmorch.danandchat.DanAndChat;
import com.gmail.andreasmartinmorch.danandchat.channel.Channel;
import com.gmail.andreasmartinmorch.danandchat.channel.Channels;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Server;
import org.bukkit.entity.Player;

public class PermissionChecker
{
  public List<Player> init = new ArrayList();
  public static final String all = ".*";
  public static final String prefix = "danandchat";
  public static final String channel = ".channel";
  public static final String ban = ".ban";
  public static final String unban = ".unban";
  public static final String list = ".list";
  public static final String in = ".in";
  public static final String available = ".available";
  public static final String leaveChannel = ".leavechannel";
  public static final String canTalk = ".cantalk";
  public static final String tell = ".tell";
  public static final String me = ".me";
  public static final String changeChannel = ".changechannel";
  public static final String defaultChannel = ".defaultchannel";
  public static final String focusedChannel = ".focusedchannel";
  private DanAndChat plugin;

  public PermissionChecker(DanAndChat plugin)
  {
    this.plugin = plugin;
    for (Player p : plugin.getServer().getOnlinePlayers())
      initializePlayer(p);
  }

  public void initializePlayer(Player p)
  {
    List<Channel> channels = this.plugin.channels.channels;
    for (Channel c : channels) {
      if (c.isAutoJoin()) {
        c.addPlayer(p);
      }
      if ((c.isAutoFocus()) && (!c.getBanned().contains(p.getName()))) {
        c.addPlayer(p);
        c.getFocused().add(p);
      }
    }
    if (!this.plugin.channels.playerHasFocusedChannel(p)) {
      Channel c = (Channel)channels.get(0);
      if (!c.getBanned().contains(p.getName()))
      {
        c.addPlayer(p);
        c.getFocused().add(p);
      }
    }
    this.init.add(p);
  }
}