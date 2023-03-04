package com.noobasauras;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;




public class Commands implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(!(sender instanceof Player)) { //Checks to see if the issuer of the command is in fact a player
            sender.sendMessage("You have to be a player to execute this command!");
            return true;
        }

        Player player = (Player)sender;

        if(args.length == 0){
            Actions.displayHelp(player);
            return true;
        }

        if (args[0].equalsIgnoreCase("list")){ //command done
            Actions.listWarps(player);
            return true;
        }
        else if (args[0].equalsIgnoreCase("help")){ //command done
            Actions.displayHelp(player);
            return true;
        }
        else if (args[0].equalsIgnoreCase("delete")){ //command done
            Actions.deleteWarp(player, args[1]);
            return true;
        }
        else if (args[0].equalsIgnoreCase("buy")){ //command done
            Actions.buyAWarp(player, args[1]);
            return true;
        }
        else if (args[0].equalsIgnoreCase("warp")){ //command done
            Actions.goToWarp(player, args[1]);
            return true;
        }
        else {
            return false;
        }
    }
}