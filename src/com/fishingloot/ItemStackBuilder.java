package com.fishingloot;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class ItemStackBuilder {

    private ItemStack itemStack;
    private ItemMeta meta;
    private ArrayList<String> lore = new ArrayList<>();

    public ItemStackBuilder(Material material){
        itemStack = new ItemStack(material, 1);
        meta = itemStack.getItemMeta();
    }

    public void setAmount(int amount){
        itemStack.setAmount(amount);
    }

    public ItemStackBuilder setName(String name){
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
        return this;
    }

    public ItemStackBuilder addLore(String s){
        lore.add(ChatColor.translateAlternateColorCodes('&', s));
        return this;
    }

    public ItemStackBuilder addLore(ArrayList<String> lore){
        for(String s : lore){
            this.lore.add(ChatColor.translateAlternateColorCodes('&', s));
        }
        return this;
    }

    public ItemStackBuilder spaceLore(){
        lore.add(" ");
        return this;
    }

    public void setDurability(int durability){
        if(meta instanceof Damageable){
            ((Damageable)meta).setDamage(durability);
        }
    }

    public int getMaxDurability(){
        return itemStack.getType().getMaxDurability();
    }

    public ItemStackBuilder addFlag(ItemFlag itemFlag){
        meta.addItemFlags(itemFlag);
        return this;
    }

    public ItemStack build(){
        meta.setLore(lore);
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    public ItemStack get(){
        return itemStack;
    }

    public ItemMeta getMeta(){
        return meta;
    }

}
