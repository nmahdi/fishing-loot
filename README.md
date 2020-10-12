# Fishing Loot
Fishing Loot is a plugin that lets server owners customize fishing rewards.

# Features
The plugin currently supports:
- Item Type
- Display Name
- Lore/Tooltip
- Catch Message
- Commands (Ran on item pickup)
- Enchanted Items (Random or fixed amount, random levels)
- Durability (Random or fixed amount)
- Amount (Random or fixed amount)
- Experience (Random or fixed amount)
- Consumed (Item is consumed on pickup)

# Config
Config.yml Exmaple:
# M: Item Type
# W: Weight
# N: Display Name
# L: Tooltip/Lore
# CM: Catch Message
# C: Commands (Ran on item pickup)
# E: Enchants (Separate by spaces)
# EA: Enchants Amount(Exact# or Min# Max#)
# EC: Enchant chance (In %)
# D: Durability (True/False) (Exact# or Min# Max#) (How much durability is removed from a tool)
# A: Amount(Exact# or Min# Max#)
# XP: Experience(# or Min Max)
# CN: Consumed (True/False) (Can only be used for named items)
# FILE: Separate File
# Example: 'M:diamond_pickaxe,W:100,N:&dDiamond Pickaxe,L:&7First Line/nSecond Line,
#           CM:&7You caught a fish!, C:say %player% caught a Diamond Pickaxe!/ngive %player% gold_ingot,
#           E:efficiency unbreaking fortune,EA:1 2,EC:30.0,D:true 1 1000,A:1,XP:5 10,CN:false'
# Example for file: 'FILE:cod.yml'
#
# Enchant levels are currently randomized
#
# Placeholders: %player%
# Command and Lore new line '/n'
# Keyword for enchant list 'all'
drops:
  - 'M:enchanted_book,W:200,E:all,EA:1 4'
  - 'M:cod,W:1250'
  - 'M:salmon,W:950'
  - 'M:tropical_fish,W:650'
  - 'M:pufferfish,W:528'
  - 'M:lily_pad,W:400'
  - 'M:glass_bottle,W:400'
  - 'M:coal,W:800,A:1 12'
  - 'M:coal,W:500,A:13 24'
  - 'M:coal,W:250,A:25 32'
  - 'M:iron_ingot,W:600,A:1 8'
  - 'M:iron_ingot,W:200,A:9 12'
  - 'M:gold_ingot,W:400,A:1 4'
  - 'M:gold_ingot,W:200,A:5 6'
  - 'M:diamond,W:400,A:1 6'
  - 'M:emerald,W:200,A:1 4'
  - 'M:leather,W:1250'
  - 'M:bow,W:50,E:infinity power punch flame unbreaking,D:false'
  - 'M:fishing_rod,W:100,E:lure mending unbreaking,D:false'
  - 'M:ink_sac,W:30'
  - 'M:bone,W:80'
  - 'M:string,W:100'
  - 'M:experience_bottle,W:200,A:1 4'
  - 'M:paper,W:50'
# 'all' Keyword enchant list
enchants:
  - 'protection'
  - 'fire_protection'
  - 'feather_falling'
  - 'blast_protection'
  - 'projectile_protection'
  - 'respiration'
  - 'aqua_affinity'
  - 'thorns'
  - 'depth_strider'
  - 'frost_walker'
  - 'shaprness'
  - 'smite'
  - 'bane_of_arthropods'
  - 'knockback'
  - 'fire_aspect'
  - 'looting'
  - 'efficiency'
  - 'silk_touch'
  - 'unbreaking'
  - 'fortune'
  - 'power'
  - 'punch'
  - 'flame'
  - 'infinity'
  - 'lure'
  - 'loyalty'
  - 'impaling'
  - 'riptide'
  - 'channeling'
  - 'multishot'
  - 'quick_charge'
  - 'piercing'
  - 'mending'  

Separate YML Example:
material: 'diamond_pickaxe'
weight: 300
name: '&c&lUltimate Pickaxe'
lore:
    - '&cCan Mine Anything!'
commands:
    - 'bc %player% caught an &cUltimate Pickaxe!'
enchants:
    - 'efficiency'
    - 'fortune'
    - 'unbreaking'
enchant-chance: 100    
enchant-amount: 3
catch-msg: '&aYou caught an &cUltimate Pickaxe!'
durability: 'true 1 100'
consumed: false
amount: '1'
experience: '1 10'

# Commands
'/fishingloot loot' Opens up the loot table (GUI). Permission: fishingloot.loot
'/fishingloot enchants' Sends you a chat message with the 'all' keyword enchants. Warning: SPAMS YOUR CHAT. Permission: fishingloot.enchants

# Planned Features
Planned Features:
- Region & Permission based rewards
- Entities with custom drops support
- Fishing leaderboard
- In-game reward creator
- Fishing events
- Time based rewards
- Support for enchant levels

# Notes
This is a project I made for fun. Do not expect any updates. 
Support will be very limited, add Mahdi#2374 on discord for support.
