package com.gmail.andreasmartinmorch.danandchat.commands;

import com.gmail.andreasmartinmorch.danandchat.DanAndChat;
import com.gmail.andreasmartinmorch.danandchat.prefixer.Prefixer;

import java.util.logging.Logger;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PrefixCommand
  implements CommandExecutor
{
  private DanAndChat plugin;

  public PrefixCommand(DanAndChat plugin)
  {
    this.plugin = plugin;
  }

  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
  {
    if (((sender instanceof Player)) && 
      (!((Player)sender).hasPermission("danandchat.prefix"))) {
      return true;
    }

    String subcmd = args[0];
    if (subcmd == null) {
      sender.sendMessage("subcmd is null");
      return false;
    }
    Logger.getLogger("Minecraft").info(subcmd);
    if ((subcmd.equalsIgnoreCase("prefixgrp")) || (subcmd.equalsIgnoreCase("prgrp")) || (subcmd.equalsIgnoreCase("prefixgroup"))) {
      Logger.getLogger("Minecraft").info("2");
      String group = args[1];
      String prefix = args[2];
      if ((group == null) || (prefix == null)) {
        sender.sendMessage("group or prefix is null");
        return false;
      }
      Logger.getLogger("Minecraft").info("3");
      sender.sendMessage(this.plugin.prefixer.setGroupPrefix(group, prefix));
      Logger.getLogger("Minecraft").info("4");
    } else if (subcmd.equalsIgnoreCase("prefix")) {
      String player = args[1];
      String prefix = args[2];
      if ((player == null) || (prefix == null)) {
        sender.sendMessage("player or prefix is null");
        return false;
      }

      sender.sendMessage(this.plugin.prefixer.setPrefix(player, prefix));
    }
    return true;
  }
}