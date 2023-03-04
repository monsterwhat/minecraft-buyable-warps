package com.noobasauras.listeners;

import com.noobasauras.location.LocationUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public class EventListener implements Listener {

    @EventHandler
    public void playerLogin(PlayerLoginEvent event){ //checks for a player to log in
        Player player = event.getPlayer();

        // locationFile is checked for creation already
        LocationUtil.createFile(player);
    }
}
