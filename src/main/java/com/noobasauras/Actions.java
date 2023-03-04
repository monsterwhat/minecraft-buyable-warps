package com.noobasauras;

import com.noobasauras.location.LocationUtil;
import org.bukkit.ChatColor;
import org.bukkit.EntityEffect;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;

public class Actions {

    static void listWarps(Player player){
        String listWarps;
        listWarps = LocationUtil.listLocationWarp(player).toString();
        player.sendMessage(ChatColor.translateAlternateColorCodes('£', "£b"+ listWarps));
    }

    static void goToWarp(Player player, String warpName){

        if (warpName == null){
            player.sendMessage(ChatColor.translateAlternateColorCodes('£', "£bYou need to give a warp name!"));
            return;
        }

        player.teleport(LocationUtil.teleportLocationWarp(player, warpName), PlayerTeleportEvent.TeleportCause.PLUGIN);
        player.sendMessage("Shazam!");
        player.playEffect(EntityEffect.TELEPORT_ENDER);
        player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1, 1);
    }

    static void buyAWarp(Player player, String warpName){

        if (warpName == null){
            player.sendMessage(ChatColor.translateAlternateColorCodes('£', "£bYou need to give your warps a name!"));
            return;
        }

        FileConfiguration pluginConfig = YamlConfiguration.loadConfiguration(BuyableWarps.getInstance().pluginConfig);
        int costOfAWarp = pluginConfig.getConfigurationSection("Main").getInt("Warp Cost");

        Location location = player.getLocation();
        double playerBalance = BuyableWarps.getInstance().getEconomy().getBalance(player);


        if(playerBalance < costOfAWarp) { //checks that the player has more than 1000 currency

            player.sendMessage(ChatColor.translateAlternateColorCodes('£', "£4You do not have enough money for this warp!"));
            return;
        }

        BuyableWarps.getInstance().getEconomy().withdrawPlayer(player, costOfAWarp);
        player.sendMessage(ChatColor.translateAlternateColorCodes('£',"£bYou have been charged " + BuyableWarps.getInstance().getEconomy().format(costOfAWarp) +
                " for the warp at: " + location.getX() + ", " + location.getY() + ", " + location.getZ()));

        LocationUtil.setLocationWarp(player, location, warpName);
        player.playSound(player.getLocation(), Sound.BLOCK_BEACON_ACTIVATE, 1, 1);
    }

    static void displayHelp(Player player){
        player.sendMessage("-----------------------------------");

        player.sendMessage(ChatColor.translateAlternateColorCodes('£',"£b/bw help : £dShows a list of commands!"));

        player.sendMessage(ChatColor.translateAlternateColorCodes('£',"£b/bw buy <warp name>: £dBuys a warp to the location you are currently standing at!"));

        player.sendMessage(ChatColor.translateAlternateColorCodes('£',"£b/bw list : £dlists all of your currently saved warps"));

        player.sendMessage(ChatColor.translateAlternateColorCodes('£', "£b/bw warp <warp name> : £dteleports you to the co-ordinates of the named warp"));

        player.sendMessage(ChatColor.translateAlternateColorCodes('£', "£b/bw delete <warp name> : £dDeletes the warp specified"));

        player.sendMessage("------------------------------------");
    }

    static void deleteWarp(Player player, String warpName){

        if (warpName == null){
            player.sendMessage(ChatColor.translateAlternateColorCodes('£', "£bYou need to choose a warp to delete!"));
            return;
        }

        LocationUtil.deleteLocationWarp(player, warpName);
        player.sendMessage(ChatColor.translateAlternateColorCodes('£', "£bYou have deleted the warp:" + warpName));
        player.playSound(player.getLocation(), Sound.BLOCK_BEACON_DEACTIVATE, 1, 1);
    }
}
