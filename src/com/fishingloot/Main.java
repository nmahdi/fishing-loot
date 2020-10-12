package com.fishingloot;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;

public class Main extends JavaPlugin {

    private LootManager lootManager;
    private File lootFile = new File(getDataFolder(), "loot");

    @Override
    public void onEnable() {
        saveDefaultConfig();
        getConfig().options().copyDefaults(true);
        if(!lootFile.exists()) {
            lootFile.mkdirs();
        }
        lootManager = new LootManager(this);
        getServer().getPluginManager().registerEvents(new FishingListener(this, lootManager), this);
        getCommand("fishingloot").setExecutor(new FishingPlusCommand(lootManager));
        new BukkitRunnable() {
            @Override
            public void run() {
                if(!lootManager.getDrops().isEmpty()) {
                    for (String s : lootManager.getDrops().keySet()) {
                        if (System.currentTimeMillis() > lootManager.getCurrentDropTime(s) + 30000) {
                            lootManager.removeDrop(s);
                        }
                    }
                }
            }
        }.runTaskTimerAsynchronously(this, 0, 20);
    }

    @Override
    public void onDisable() {

    }

    public File getLootFile() {
        return lootFile;
    }

}
