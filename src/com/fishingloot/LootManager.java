package com.fishingloot;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;

import java.io.File;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LootManager {

    private Main main;
    private Logger logger;
    private ArrayList<Loot> loots = new ArrayList<>();
    private ArrayList<String> enchants = new ArrayList<>();
    private int totalWeight;
    private ThreadLocalRandom random = ThreadLocalRandom.current();
    private HashMap<String, String> drops = new HashMap<>();
    public String GUI_NAME = ChatColor.GREEN + "Fishing Loot Table ~ ";
    private ArrayList<ItemStack> invItems = new ArrayList<>();
    private int itemsPerPage = 46;
    public ItemStack BACK = new ItemStackBuilder(Material.ARROW).setName("&7Back").build();
    public ItemStack NEXT = new ItemStackBuilder(Material.ARROW).setName("&7Next").build();
    private NumberFormat formatter = new DecimalFormat("#0.00");

    private String nLine = "/n";

    public LootManager(Main main){
        this.main = main;
        this.logger = main.getLogger();
        loadEnchants();
        loadLoot();
        loadLoottableInventory();
    }

    private void loadEnchants(){
        for(String s : main.getConfig().getStringList("enchants")){
            enchants.add(s);
        }
        log("Enchants loaded");
    }

    private void loadLoot(){
        for(String s : main.getConfig().getStringList("drops")){
            String[] tokens = s.split(",");
            String f = getKeyword(tokens, Keyword.File, false, null);
            boolean file = f != null;
            FileConfiguration cc = null;
            if(file) {
                File fFile = new File(main.getLootFile(), f);
                if (fFile.exists()) {
                    cc = YamlConfiguration.loadConfiguration(fFile);
                }
            }
            String m = getKeyword(tokens, Keyword.Material, file, cc);
            String w = getKeyword(tokens, Keyword.Weight, file, cc);
            String n = getKeyword(tokens, Keyword.Name, file, cc);
            String cm = getKeyword(tokens, Keyword.CatchMessage, file, cc);
            String l = getKeyword(tokens, Keyword.Lore, file, cc);
            String c = getKeyword(tokens, Keyword.Commands, file, cc);
            String ea = getKeyword(tokens, Keyword.EnchantsAmount, file, cc);
            String e = getKeyword(tokens, Keyword.Enchants, file, cc);
            String ec = getKeyword(tokens, Keyword.EnchantChance, file, cc);
            String d = getKeyword(tokens, Keyword.Durability, file, cc);
            String cn = getKeyword(tokens, Keyword.Consumed, file, cc);
            String a = getKeyword(tokens, Keyword.Amount, file, cc);
            String xp = getKeyword(tokens, Keyword.Experience, file, cc);
            if(m != null && w != null) {
                totalWeight += Integer.valueOf(w);
                Loot current = new Loot(m.toUpperCase(), totalWeight, Integer.valueOf(w));
                if (n != null) {
                    current.setName(n);
                }
                if(cm != null){
                    current.setCatchMsg(cm);
                }
                if (l != null) {
                    String[] t = l.split(nLine);
                    for (String lore : t) {
                        current.addLore(lore);
                    }
                }
                if (c != null) {
                    String[] t = c.split(nLine);
                    for (String i : t) {
                        current.addCommand(i);
                    }
                }
                if (ea != null) {
                    String[] t = ea.split(" ");
                    if (t.length > 1) {
                        current.setEnch(Integer.valueOf(t[0]), Integer.valueOf(t[1]));
                    } else {
                        current.setEnch(Integer.valueOf(t[0]));
                    }
                }
                if (e != null) {
                    if (!current.getMaterial().contains("enchanted")) {
                        if (e.startsWith("all")) {
                            for (String t : enchants) {
                                if (t != null) {
                                    current.addEnchantment(t);
                                }
                            }
                        } else {
                            String[] tk = e.split(" ");
                            for (String t : tk) {
                                current.addEnchantment(t);
                            }
                        }
                    }
                    if (ea == null) {
                        current.setEnch(1);
                    }
                }
                if(ec != null){
                    current.setEnchChance(Double.valueOf(ec));
                }
                if (d != null) {
                    String[] t = d.split(" ");
                    if(Boolean.valueOf(t[0])) {
                        current.setDura(true);
                        if (t.length > 1) {
                            current.setDura(Integer.valueOf(t[1]), Integer.valueOf(t[2]));
                        } else {
                            current.setDura(Integer.valueOf(t[1]));
                        }
                    }
                }
                if(cn != null){
                    current.setConsumed(Boolean.valueOf(cn));
                }
                if (a != null) {
                    String[] t = a.split(" ");
                    if (t.length > 1) {
                        current.setAmount(Integer.valueOf(t[0]), Integer.valueOf(t[1]));
                    } else {
                        current.setAmount(Integer.valueOf(t[0]));
                    }
                } else {
                    current.setAmount(1);
                }
                if (xp != null) {
                    String[] t = xp.split(" ");
                    if (t.length > 1) {
                        current.setXp(Integer.valueOf(t[0]), Integer.valueOf(t[1]));
                    } else {
                        current.setXp(Integer.valueOf(t[0]));
                    }
                }
                addLoot(current);
            }
        }
        log("Loot loaded");
    }

    private void loadLoottableInventory(){
        for(int i = 0; i < loots.size(); i++) {
                Loot current = loots.get(i);
                ItemStackBuilder builder = new ItemStackBuilder(Material.valueOf(current.getMaterial()));
                if(current.getName() != null){
                    builder.setName(current.getName());
                }
                if(!current.getLore().isEmpty()){
                    builder.addLore(current.getLore());
                }
                builder.spaceLore();
                if(current.hasCatchMessage()){
                    builder.addLore("&7Catch Message: &f" + ChatColor.translateAlternateColorCodes('&', current.getCatchMsg()));
                    builder.spaceLore();
                }
                builder.addLore("&7Weight: " + current.getWeight());
                builder.addLore("&7Chance: " + formatter.format((Double.valueOf(current.getChance())/(double)totalWeight)*100d) + "%");
                if(!current.getCommands().isEmpty()){
                    builder.spaceLore();
                    builder.addLore("&7Commands:");
                    for(String s : current.getCommands()){
                        builder.addLore("&7- " + s);
                    }
                }
                if(!current.getEnchantments().isEmpty()){
                    builder.spaceLore();
                    builder.addLore("&7Enchants:");
                    if(current.getEnchantments().size() == enchants.size()){
                        builder.addLore("&7- ALL ~ Check config.yml for enchant list or do /fishingloot enchants");
                    }else{
                        for(String s : current.getEnchantments()){
                            builder.addLore("&7- " + s);
                        }
                    }
                    if (current.noRandom("ench")) {
                        builder.addLore("&7Enchant Amount: " + current.getMinEnch());
                    }else{
                        builder.addLore("&7Enchant Amount: " + current.getMinEnch() + "-" + current.getMaxEnch());
                    }
                    builder.addLore("&7Enchant Chance: " + current.getEnchChance());
                }
                if(current.hasDura()){
                    if(current.noRandom("dura")){
                        builder.addLore("&7Durability: " + current.getMinDura());
                    }else{
                        builder.addLore("&7Durability: " + current.getMinDura() + "-" + current.getMaxDura());
                    }
                    builder.spaceLore();
                }
                builder.spaceLore();
                builder.addLore("&7Consumed: " + current.isConsumed());
                builder.spaceLore();
                if(current.noRandom("amount")){
                    builder.addLore("&7Amount: " + current.getMinAmount());
                }else{
                    builder.addLore("&7Amount: " + current.getMinAmount() + "-" + current.getMaxAmount());
                }
                builder.spaceLore();
                if(current.getMinXp() > 0){
                    if(current.noRandom("xp")){
                        builder.addLore("&7Experience: " + current.getMinXp());
                    }else{
                        builder.addLore("&7Experience: " + current.getMinXp() + "-" + current.getMaxXp());
                    }
                }else{
                    builder.addLore("&7Experience: Vanilla");
                }
                invItems.add(builder.build());
        }
    }

    public void openLoottable(Player player, int page){
        double pages;
        double amount = loots.size();
        if(amount < itemsPerPage){
            pages = 1;
        }else{
            pages = amount/itemsPerPage;
        }
        if(page > Math.round(pages)) return;

        Inventory inv = Bukkit.createInventory(null, 54,  GUI_NAME + page + "/" + Math.round(pages));
        for(int i = 0; i < itemsPerPage-1; i++){
            inv.setItem(i, new ItemStackBuilder(Material.BARRIER).setName("&cX").build());
        }
        for(int i = itemsPerPage-1; i < inv.getSize(); i++){
            inv.setItem(i, new ItemStackBuilder(Material.BLACK_STAINED_GLASS_PANE).setName(" ").build());
        }

        if(pages > 1){
            if(page == Math.round(pages)){
                for(int i = 0; i < amount-(itemsPerPage*(page-1)); i++) {
                    inv.setItem(i, invItems.get((itemsPerPage * (page - 1)) + i));
                    inv.setItem(45, BACK);
                }
            }else{
                for(int i = 0; i < itemsPerPage; i++) {
                    inv.setItem(i, invItems.get(i));
                    inv.setItem(53, NEXT);
                    inv.setItem(45, BACK);
                }
            }
        }else{
            for(int i = 0; i < amount; i++) {
                inv.setItem(i, invItems.get(i));
            }
            if(pages > page) {
                inv.setItem(53, NEXT);
            }
        }

        inv.setItem(49, new ItemStackBuilder(Material.PAPER).setName("&aWeight: " + totalWeight).build());

        player.openInventory(inv);
    }

    public ItemStack createItemStack(Loot loot) {
        ItemStackBuilder stack = new ItemStackBuilder(Material.valueOf(loot.getMaterial()));
        if(loot.getName() != null){
            stack.setName(loot.getName());
        }
        if(!loot.getLore().isEmpty()){
            for(String s : loot.getLore()){
                stack.addLore(s);
            }
        }
        if(loot.noRandom("amount")){
            stack.setAmount(loot.getMinAmount());
        }else{
            stack.setAmount(random.nextInt(loot.getMinAmount(), loot.getMaxAmount()+1));
        }
        if(loot.hasDura()){
            if(loot.noRandom("dura")){
                stack.setAmount(loot.getMinDura());
            }else{
                stack.setAmount(random.nextInt(loot.getMinDura(), loot.getMaxDura()+1));
            }
        }
        if(!loot.getEnchantments().isEmpty()) {
            if(random.nextDouble(100)+1d <= loot.getEnchChance()) {
                ArrayList<String> enchants = new ArrayList<>();
                if (loot.noRandom("ench")) {
                    for (int i = 0; i < loot.getMinEnch(); i++) {
                        String e = loot.getEnchantments().get(random.nextInt(loot.getEnchantments().size()));
                        if (!enchants.contains(e)) {
                            enchants.add(e);
                        }
                    }
                } else {
                    for (int i = 0; i < random.nextInt(loot.getMinEnch(), loot.getMaxEnch()); i++) {
                        String e = loot.getEnchantments().get(random.nextInt(loot.getEnchantments().size()));
                        if (!enchants.contains(e)) {
                            enchants.add(e);
                        }
                    }
                }
                if (loot.getMaterial() != null) {
                    if (loot.getMaterial().equalsIgnoreCase("enchanted_book")) {
                        EnchantmentStorageMeta meta = (EnchantmentStorageMeta) stack.getMeta();
                        for (String e : enchants) {
                            meta.addStoredEnchant(Enchantment.getByKey(NamespacedKey.minecraft(e)), random.nextInt(Enchantment.getByKey(NamespacedKey.minecraft(e)).getMaxLevel()) + 1, true);
                        }
                    } else {
                        for (String e : enchants) {
                            stack.getMeta().addEnchant(Enchantment.getByKey(NamespacedKey.minecraft(e)), random.nextInt(Enchantment.getByKey(NamespacedKey.minecraft(e)).getMaxLevel()) + 1, true);
                        }
                    }
                }
            }
        }
        return stack.build();
    }

    public int getTotalWeight() {
        return totalWeight;
    }

    public void addLoot(Loot loot){
        loots.add(loot);
    }

    public void removeLoot(Loot loot){
        loots.remove(loot);
    }

    public ArrayList<Loot> getLoot() {
        return loots;
    }

    public ArrayList<String> getEnchants() {
        return enchants;
    }

    /*
        @param c Can be null
     */
    public String getKeyword(String[] tokens, Keyword keyword, boolean file, FileConfiguration c){
        if(file && c != null){
            if(c.contains(keyword.getFile())){
                if(keyword.isList()){
                    String s = "";
                    for(int i = 0; i < c.getStringList(keyword.getFile()).size(); i++){
                        if(i == c.getStringList(keyword.getFile()).size()-1){
                            s+=c.getStringList(keyword.getFile()).get(i);
                        }else{
                            s+=c.getStringList(keyword.getFile()).get(i)+nLine;
                        }
                    }
                    return s;
                }
                return c.getString(keyword.getFile());
            }
        }else {
            for (String s : tokens) {
                if (s.startsWith(keyword.getConfig())) {
                    return s.replace(keyword.getConfig(), "");
                }
            }
        }
        return null;
    }

    public void addDrop(String uuid, Player player){
        drops.put(uuid, player.getName()+","+System.currentTimeMillis());
    }

    public HashMap<String, String> getDrops(){
        return drops;
    }

    public void removeDrop(String uuid){
        drops.remove(uuid);
    }

    public long getCurrentDropTime(String uuid){
        return Long.valueOf(drops.get(uuid).split(",")[1]);
    }

    public String getCurrentDropPlayer(String uuid){
        return drops.get(uuid).split(",")[0];
    }

    public boolean isProtected(String uuid){
        if(drops.containsKey(uuid)){
            return true;
        }
        return false;
    }

    public enum Keyword{
        Material("material", "M:", false),
        Weight("weight", "W:", false),
        Name("name", "N:", false),
        CatchMessage("catch-msg", "CM:", false),
        Lore("lore", "L:", true),
        Commands("commands", "C:", true),
        Enchants("enchants", "E:", true),
        EnchantsAmount("enchants-amount", "EA:", false),
        EnchantChance("enchant-chance", "EC:", false),
        Durability("durability", "D:", false),
        Consumed("consumed", "CN:", false),
        Amount("amount", "A:", false),
        Experience("experience", "XP:", false),
        File("file", "FILE:", false);
        private String file;
        private String config;
        private boolean list;

        Keyword(String file, String config, boolean list){
            this.file = file;
            this.config = config;
            this.list = list;
        }

        public String getFile() {
            return file;
        }

        public String getConfig() {
            return config;
        }

        public boolean isList(){
            return list;
        }
    }

    private void log(String... string) {
        for(String s : string){
            logger.log(Level.INFO, s);
        }
    }

}
