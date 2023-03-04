package com.noobasauras;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Commands implements CommandExecutor {


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        try {
            if (!(sender instanceof Player player)) { //Checks to see if the issuer of the command is in fact a player
                sender.sendMessage("You have to be a player to execute this command!");
                return true;
            }
            if (args.length == 0) {
                Actions.displayHelp(player);
                return true;
            }
            if (args[0].equalsIgnoreCase("list")) { //command done
                Actions.listWarps(player);
                return true;
            } else if (args[0].equalsIgnoreCase("help")) { //command done
                Actions.displayHelp(player);
                return true;
            } else if (args[0].equalsIgnoreCase("delete")) { //command done
                if (args.length < 2) {
                    player.sendMessage("You need to specify a warp to delete!");
                    return true;
                }
                Actions.deleteWarp(player, args[1]);
                return true;
            } else if (args[0].equalsIgnoreCase("buy")) { //command done
                if (args.length < 2) {
                    player.sendMessage("You need to name your warp!");
                    return true;
                }
                Actions.buyAWarp(player, args[1]);
                return true;
            } else if (args[0].equalsIgnoreCase("warp")) { //command done
                if (args.length < 2) {
                    player.sendMessage("You need to specify a warp!");
                    return true;
                }
                Actions.goToWarp(player, args[1]);
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}