package com.playdeca;

import com.playdeca.location.LocationUtil;
import com.playdeca.models.warp;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.EntityEffect;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;
import java.util.List;
import java.util.Objects;

public class Actions {

    static void listWarps(Player player){
        //listWarps = LocationUtil.listLocationWarp(player).toString();
        List<warp> listWarpsNew = LocationUtil.listLocationWarpNew(player);
        if(listWarpsNew.isEmpty()){
            Component message = Component.text("You have no warps!");
            player.sendMessage(message);
            return;
        }
        Component message = Component.text("Your warps are: ");
        player.sendMessage(message);
        for (com.playdeca.models.warp warp : listWarpsNew) {
            Component warpMessage = Component.text("Warp: " + warp.getWarpName()).hoverEvent(Component.text("Click me to teleport to this warp!")).clickEvent(net.kyori.adventure.text.event.ClickEvent.runCommand("/bw warp " + warp.getWarpName()));
            player.sendMessage(warpMessage);
        }
    }

    static void goToWarp(Player player, String warpName){

        if (warpName == null || warpName.isEmpty()){
            Component message = Component.text("You need to give a warp name!");
            player.sendMessage(message);
            return;
        }

        player.teleport(LocationUtil.teleportLocationWarp(player, warpName), PlayerTeleportEvent.TeleportCause.PLUGIN);
        player.sendMessage("Shazam!");
        player.playEffect(EntityEffect.TELEPORT_ENDER);
        player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1, 1);
    }

    static void buyAWarp(Player player, String warpName){

        if (warpName == null || warpName.isEmpty()){ //checks that the warpName argument is not null or empty
            Component message = Component.text("You need to give your warp a name!");
            player.sendMessage(message);
            return;
        }

        FileConfiguration pluginConfig = YamlConfiguration.loadConfiguration(BuyableWarps.getInstance().pluginConfig);
        int costOfAWarp = Objects.requireNonNull(pluginConfig.getConfigurationSection("Main")).getInt("Warp Cost");
        double playerBalance = BuyableWarps.getInstance().getEconomy().getBalance(player);
        if(playerBalance < costOfAWarp) { //checks that the player has more than 1000 currency
            Component message = Component.text("You do not have enough money for this warp!");
            player.sendMessage(message);
            return;
        }

        Location location = player.getLocation();

        BuyableWarps.getInstance().getEconomy().withdrawPlayer(player, costOfAWarp);
        Component message = Component.text("You have been charged " + BuyableWarps.getInstance().getEconomy().format(costOfAWarp) +
                " for the warp at: " + location.getX() + ", " + location.getY() + ", " + location.getZ());
        player.sendMessage(message);

        LocationUtil.setLocationWarp(player, location, warpName);
        player.playSound(player.getLocation(), Sound.BLOCK_BEACON_ACTIVATE, 1, 1);
    }

    static void displayHelp(Player player){
        player.sendMessage("-----------------------------------");
        Component message = Component.text("==--== Buyable Warps Help ==--==").color(NamedTextColor.YELLOW);
        player.sendMessage(message);
        Component message2 = Component.text("/bw help : Shows a list of commands!").color(NamedTextColor.YELLOW).hoverEvent(Component.text("Click me!")).clickEvent(net.kyori.adventure.text.event.ClickEvent.runCommand("/bw help"));
        player.sendMessage(message2);
        Component message3 = Component.text("/bw buy <warp name> : Buys a warp to the location you are currently standing at!").color(NamedTextColor.YELLOW).clickEvent(net.kyori.adventure.text.event.ClickEvent.suggestCommand("/bw buy <warp name>"));
        player.sendMessage(message3);
        Component message4 = Component.text("/bw list : lists all of your currently saved warps").color(NamedTextColor.YELLOW).clickEvent(net.kyori.adventure.text.event.ClickEvent.runCommand("/bw list"));
        player.sendMessage(message4);
        Component message5 = Component.text("/bw warp <warp name> : teleports you to the co-ordinates of the named warp").color(NamedTextColor.YELLOW).clickEvent(net.kyori.adventure.text.event.ClickEvent.suggestCommand("/bw warp <warp name>"));
        player.sendMessage(message5);
        Component message6 = Component.text("/bw delete <warp name> : Deletes the warp specified").color(NamedTextColor.YELLOW).clickEvent(net.kyori.adventure.text.event.ClickEvent.suggestCommand("/bw delete <warp name>"));
        player.sendMessage(message6);
        player.sendMessage("------------------------------------");

    }

    static void deleteWarp(Player player, String warpName){

        if (warpName == null || warpName.isEmpty()){
            Component message = Component.text("You need to choose a warp to delete!");
            player.sendMessage(message);
            return;
        }

        LocationUtil.deleteLocationWarp(player, warpName);
        Component message = Component.text("You have deleted the warp: " + warpName);
        player.sendMessage(message);
        player.playSound(player.getLocation(), Sound.BLOCK_BEACON_DEACTIVATE, 1, 1);
    }
}
