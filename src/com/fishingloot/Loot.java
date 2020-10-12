package com.fishingloot;

import java.util.ArrayList;

public class Loot {
    /*
        @param material ItemStack material
        @param name Display name of the itemstack
        @param catchMsg Message sent to the player on fish catch
        @param lore Lore of the itemstack
        @param commands Commands that run when the itemstack is caught
        @param enchantments A list of possible enchantments that the itemstack can have
        @param durability Determines if an item can have a durability.
        @param consumed Determines if the itemstack is consumed on pickup
        @param minEnch Minimum amount of enchantments that the itemstack can have
        @param maxEnch Maximum amount of enchantments that the itemstack can have
        @param minAmount Minimum amount of items the itemstack will have
        @param maxAmount Maximum amount of items the itemstack will have
        @param minXp Minimum amount of experience that will drop when the itemstack is caught
        @param maxXp Maximum amount of experience that will drop when the itemstack is caught
        @param weight The integer value that the drop rate will cap at
        @param chance The integer value of the drop rate
        @param enchChance The chance of the itemstack being enchanted
     */
    public String material;
    private String name;
    private String catchMsg;
    private ArrayList<String> lore = new ArrayList<>();
    private ArrayList<String> commands = new ArrayList<>();
    private ArrayList<String> enchantments = new ArrayList<>();
    private boolean consumed = false;
    private boolean durability = false;
    private int minEnch, maxEnch;
    private int minAmount, maxAmount;
    private int minDura, maxDura;
    private int minXp, maxXp;
    private int weight, chance;
    private double enchChance;

    public Loot(String material, int weight, int chance){
        this.material = material;
        this.weight = weight;
        this.chance = chance;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCatchMsg() {
        return catchMsg;
    }

    public void setCatchMsg(String catchMsg) {
        this.catchMsg = catchMsg;
    }

    public boolean hasCatchMessage(){
        if(getCatchMsg() != null) return true;
        return false;
    }

    public ArrayList<String> getLore() {
        return lore;
    }

    public void addLore(String s){
        this.lore.add(s);
    }

    public void setLore(ArrayList<String> lore) {
        this.lore = lore;
    }

    public ArrayList<String> getCommands() {
        return commands;
    }

    public void addCommand(String s){
        this.commands.add(s);
    }

    public void setCommands(ArrayList<String> commands) {
        this.commands = commands;
    }

    public ArrayList<String> getEnchantments() {
        return enchantments;
    }

    public void addEnchantment(String s){
        this.enchantments.add(s);
    }

    public void setEnchantments(ArrayList<String> enchantments) {
        this.enchantments = enchantments;
    }

    public boolean hasDura(){
        return durability;
    }

    public void setDura(boolean dura){
        this.durability = dura;
    }

    public void setDura(int min, int max){
        this.minDura = min;
        this.maxDura = max;
    }

    public int getMinDura() {
        return minDura;
    }

    public int getMaxDura() {
        return maxDura;
    }

    public void setDura(int amount){
        this.minDura = amount;
        this.maxDura = amount;
    }

    public boolean isConsumed() {
        return consumed;
    }

    public void setConsumed(boolean consumed) {
        this.consumed = consumed;
    }

    public int getMinEnch() {
        return minEnch;
    }

    public int getMaxEnch() {
        return maxEnch;
    }

    public void setEnch(int min, int max){
        this.minEnch = min;
        this.maxEnch = max;
    }

    public void setEnch(int ench){
        this.minEnch = ench;
        this.maxEnch = ench;
    }

    public int getMinAmount() {
        return minAmount;
    }

    public int getMaxAmount() {
        return maxAmount;
    }

    public void setAmount(int min, int max){
        this.minAmount = min;
        this.maxAmount = max;
    }

    public void setAmount(int amount){
        this.minAmount = amount;
        this.maxAmount = amount;
    }

    public int getMinXp() {
        return minXp;
    }

    public int getMaxXp() {
        return maxXp;
    }

    public void setXp(int min, int max){
        this.minXp = min;
        this.maxXp = max;
    }

    public void setXp(int xp){
        this.minXp = xp;
        this.maxXp = xp;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getChance() {
        return chance;
    }

    public void setChance(int chance) {
        this.chance = chance;
    }

    public double getEnchChance() {
        return enchChance;
    }

    public void setEnchChance(double enchChance) {
        this.enchChance = enchChance;
    }

    public boolean noRandom(String type){
        switch(type){
            case "ench":
                if(getMinEnch() == getMaxEnch()){
                    return true;
                }
            break;
            case "amount":
                if(getMinAmount() == getMaxAmount()){
                    return true;
                }
            break;
            case "xp":
                if(getMinXp() == getMaxXp()){
                    return true;
                }
            break;
            case "dura":
                if(getMinDura() == getMaxDura()){
                    return true;
                }
            break;
        }
        return false;
    }

}
