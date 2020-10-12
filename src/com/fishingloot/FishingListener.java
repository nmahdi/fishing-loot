package com.fishingloot;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;

import java.util.concurrent.ThreadLocalRandom;

public class FishingListener implements Listener {

    private Main main;
    private LootManager lootManager;
    private ThreadLocalRandom random = ThreadLocalRandom.current();

    public FishingListener(Main main, LootManager lootManager) {
        this.main = main;
        this.lootManager = lootManager;
    }

    @EventHandler
    public void onFish(PlayerFishEvent e) {
        if (e.getState() != PlayerFishEvent.State.CAUGHT_FISH) return;

        int chance = random.nextInt(lootManager.getTotalWeight());

        for (int i = 0; i < lootManager.getLoot().size(); i++) {
            if (i > 0) {
                if (chance < lootManager.getLoot().get(i).getWeight() && chance >= lootManager.getLoot().get(i - 1).getWeight()) {
                    dropFish(e, i);
                }
            } else {
                if (chance < lootManager.getLoot().get(i).getWeight()) {
                    dropFish(e, i);
                }
            }
        }
    }

    @EventHandler
    public void onFishPickup(EntityPickupItemEvent e) {
        if(e.getEntity() instanceof Player) {
            if (lootManager.isProtected(e.getItem().getUniqueId().toString())) {
                if (!lootManager.getCurrentDropPlayer(e.getItem().getUniqueId().toString()).equalsIgnoreCase(e.getEntity().getName())) {
                    e.setCancelled(true);
                    return;
                } else {
                    lootManager.removeDrop(e.getItem().getUniqueId().toString());
                }
            }
            for (Loot loot : lootManager.getLoot()) {
                if (e.getItem().getItemStack() != null && e.getItem().getItemStack().getType() != Material.AIR && loot.getName() != null) {
                    if (e.getItem().getItemStack().getType() == Material.valueOf(loot.getMaterial())) {
                        if (e.getItem().getItemStack().hasItemMeta() && e.getItem().getItemStack().getItemMeta().hasDisplayName()) {
                            if (e.getItem().getItemStack().getItemMeta().getDisplayName().equals(ChatColor.translateAlternateColorCodes('&', loot.getName()))) {
                                if (loot.hasCatchMessage()) {
                                    e.getEntity().sendMessage(ChatColor.translateAlternateColorCodes('&', loot.getCatchMsg()));
                                }
                                if (loot.isConsumed()) {
                                    e.setCancelled(true);
                                    e.getItem().remove();
                                }
                                if (!loot.getCommands().isEmpty()) {
                                    for (String s : loot.getCommands()) {
                                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), s.replaceAll("%player%", e.getEntity().getName()));
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onInvClick(InventoryClickEvent e){
        if(e.getWhoClicked() instanceof Player){
            if(e.getView().getTitle().startsWith(lootManager.GUI_NAME)){
                e.setCancelled(true);
                if(e.getCurrentItem() != null && e.getCurrentItem().getType() != Material.AIR){
                    if(e.getCurrentItem().isSimilar(lootManager.BACK)){
                        String[] t = (ChatColor.stripColor(e.getView().getTitle()).replace(lootManager.GUI_NAME, "")).split("/");
                        if(Integer.valueOf(t[0]) > 1) {
                            lootManager.openLoottable((Player)e.getWhoClicked(), Integer.valueOf(t[0])-1);
                        }
                        if(e.getCurrentItem().isSimilar(lootManager.NEXT)){
                            String[] tt = (ChatColor.stripColor(e.getView().getTitle()).replace(lootManager.GUI_NAME, "")).split("/");
                            lootManager.openLoottable((Player)e.getWhoClicked(), Integer.valueOf(tt[0])+1);
                        }
                    }
                }
            }
        }
    }

    private void dropFish(PlayerFishEvent e, int i){
        ItemStack stack = lootManager.createItemStack(lootManager.getLoot().get(i));
        ((Item) e.getCaught()).setItemStack(stack);
        lootManager.addDrop(e.getCaught().getUniqueId().toString(), e.getPlayer());
        if(lootManager.getLoot().get(i).noRandom("xp")){
            e.setExpToDrop(lootManager.getLoot().get(i).getMinXp());
        }else{
            e.setExpToDrop(random.nextInt(lootManager.getLoot().get(i).getMinXp(), lootManager.getLoot().get(i).getMaxXp()));
        }
    }

}
