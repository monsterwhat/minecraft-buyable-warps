package com.playdeca;

import com.playdeca.listeners.EventListener;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import java.io.File;
import java.io.IOException;
import java.util.Objects;


public class BuyableWarps extends JavaPlugin {

    public File pluginConfig;
    private static BuyableWarps instance;
    private Economy economy;

    public BuyableWarps() {
    }

    @Override
    public void onEnable(){
        instance = this;
        Bukkit.getLogger().info("Buyable Warps has been enabled!");

        //Check if the server has a registered economy; disable plugin if not
        if(!(setupEconomy())){
            this.getServer().getPluginManager().disablePlugin(this);
            Bukkit.getLogger().warning("Buyable Warps has been disabled!");
        }

        getPluginConfig();
        createDirectory();
        createConfig();

        Objects.requireNonNull(this.getCommand("buywarp")).setExecutor(new Commands()); //defines command "buywarp"

        this.getServer().getPluginManager().registerEvents(new EventListener(),this); //defines the login listener
    }

    @Override
    public void onDisable(){

    }

    private boolean setupEconomy() { //grabs what type of economy plugin is being used
        RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(Economy.class);
        if (economyProvider == null) {
            return false;
        }
        else{
            economy = economyProvider.getProvider();
            return true;
        }

    }

    public Economy getEconomy(){
        return economy;
    }

    private void createDirectory(){ //created a directory labelled "player_warps" to the server plugin files

        //Possibly redundant
        if (!getDataFolder().exists()) {
            if(getDataFolder().mkdirs()){
                Bukkit.getLogger().info("Created plugin directory");
            }
        }

        File directory = new File(getDataFolder(), "Location_YMLs");

        if(!directory.exists()){
            if(directory.mkdir()){
                Bukkit.getLogger().info("Created player_warps directory");
            }
        }
    }

    public static BuyableWarps getInstance() {
        return instance;
    }

    private void getPluginConfig(){
        this.pluginConfig = new File(BuyableWarps.getInstance().getDataFolder(), "Buyable_Warps_Config.yml");
    }

    private void createConfig(){
        File configFile = new File(BuyableWarps.getInstance().getDataFolder(), "Buyable_Warps_Config.yml");

        if (!configFile.exists()){
            FileConfiguration fileConfig = YamlConfiguration.loadConfiguration(configFile);

            fileConfig.createSection("Main");
            Objects.requireNonNull(fileConfig.getConfigurationSection("Main")).set("Warp_Cost", 10000);

            try{
                fileConfig.save(configFile);
            } catch (IOException e){
                Bukkit.getLogger().info("Could not save config file");
                Bukkit.getLogger().info(e.getMessage());
            }

        }
    }
}
