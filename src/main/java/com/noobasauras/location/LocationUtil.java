package com.noobasauras.location;

import com.noobasauras.BuyableWarps;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.Set;


public class LocationUtil {

    public LocationUtil() {

    }

    public static void createFile(Player player){
        String UUID = player.getUniqueId().toString();

        File playerWarps = new File(BuyableWarps.getInstance().getDataFolder() + "/Location_YMLs", UUID + ".yml");

        if (!(playerWarps.exists())) {
            FileConfiguration config = getConfig(playerWarps);

            try{
                config.save(playerWarps);
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    public static void setLocationWarp(Player player, Location loc, String warpName){
        File file = getFile(getUUID(player));
        FileConfiguration config = getConfig(file);

        config.createSection(warpName);
        Objects.requireNonNull(config.getConfigurationSection(warpName)).set("x", loc.getX());
        Objects.requireNonNull(config.getConfigurationSection(warpName)).set("y", loc.getY());
        Objects.requireNonNull(config.getConfigurationSection(warpName)).set("z", loc.getZ());
        Objects.requireNonNull(config.getConfigurationSection(warpName)).set("world", Objects.requireNonNull(loc.getWorld()).getName());
        Objects.requireNonNull(config.getConfigurationSection(warpName)).set("pitch", loc.getPitch());
        Objects.requireNonNull(config.getConfigurationSection(warpName)).set("yaw", loc.getYaw());

        try{
            config.save(file);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void deleteLocationWarp(Player player, String warpName) {
        File file = getFile(getUUID(player));
        FileConfiguration config = getConfig(file);
        if(config.contains(warpName)) {
            config.set(warpName, null);
            try{
                config.save(file);
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    public static Location teleportLocationWarp(Player player, String warpName){

        File file = getFile(getUUID(player));
        FileConfiguration config = getConfig(file);

        ConfigurationSection section = config.getConfigurationSection(warpName);
        if (!config.contains(warpName)) {
            // Not found
            player.sendMessage("No warp found, are you sure that is the right name? /bw list");
            return player.getLocation();
        }
        else {
            assert section != null;
            return new Location(
                Bukkit.getServer().getWorld(Objects.requireNonNull(section.getString("world"))),
                section.getDouble("x"),
                section.getDouble("y"),
                section.getDouble("z"),
                (float) section.getDouble("yaw"),
                (float) section.getDouble("pitch")
            );
        }
    }

    public static Set<String> listLocationWarp(Player player){

        File file = getFile(getUUID(player));
        FileConfiguration config = getConfig(file);
        return config.getKeys(false);
    }

    private static String getUUID(Player player){
        return player.getUniqueId().toString();
    }

    private static File getFile(String UUID){
        return new File(BuyableWarps.getInstance().getDataFolder()  + "/Location_YMLs", UUID + ".yml");
    }

    private static FileConfiguration getConfig(File file){
        return YamlConfiguration.loadConfiguration(file);
    }
}
