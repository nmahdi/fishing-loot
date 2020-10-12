package com.fishingloot;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FishingPlusCommand implements CommandExecutor {

    private LootManager lootManager;
    private int hLoot = 0, hEnch = 1;
    private String[] help = {"&7/fishingloot loot", "&7/fishingloot enchants"};
    private String noPerm = "&5Insufficient Permissions.";
    private String notPlayer = "&5You have to be a player to execute this command.";

    public FishingPlusCommand(LootManager lootManager){
        this.lootManager = lootManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(args.length > 0) {
            switch(args[0]) {
                case "loot":
                    if (sender instanceof Player) {
                        if(sender.hasPermission("fishingloot.loot")) {
                            lootManager.openLoottable((Player)sender, 1);
                        }else{
                            sender.sendMessage(noPerm);
                        }
                    }else{
                        sender.sendMessage(notPlayer);
                    }
                    return true;
                case "enchants":
                    if(sender.hasPermission("fishingloot.enchants")) {
                        sender.sendMessage(ChatColor.GREEN + "List of 'all' enchants:");
                        for (String s : lootManager.getEnchants()) {
                            sender.sendMessage(ChatColor.GRAY + "- " + s);
                        }
                    }else{
                        sender.sendMessage(noPerm);
                    }
                    return true;
            }
        }else{
            for(String s : help){
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', s));
            }
            return true;
        }
        return true;
    }

}
