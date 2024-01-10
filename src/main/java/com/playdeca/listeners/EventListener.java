package com.playdeca.listeners;

import com.playdeca.location.LocationUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public class EventListener implements Listener {

    @EventHandler
    public void playerLogin(PlayerLoginEvent event){

        Player player = event.getPlayer();
        LocationUtil.createFile(player);
    }
}
