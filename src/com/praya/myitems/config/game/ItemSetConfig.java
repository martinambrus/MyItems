// 
// Decompiled by Procyon v0.5.36
// 

package com.praya.myitems.config.game;

import org.bukkit.Material;
import java.util.Set;
import java.util.List;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import java.io.File;
import com.praya.myitems.manager.plugin.DataManager;
import com.praya.myitems.manager.plugin.PluginManager;
import api.praya.myitems.builder.item.ItemSetComponentItem;
import com.praya.agarthalib.utility.EnchantmentUtil;
import com.praya.agarthalib.utility.MaterialUtil;
import org.bukkit.enchantments.Enchantment;
import core.praya.agarthalib.enums.main.Slot;
import java.util.HashSet;
import api.praya.myitems.builder.item.ItemSetBonusEffect;
import api.praya.myitems.builder.ability.AbilityItemWeapon;
import org.bukkit.ChatColor;
import com.praya.agarthalib.utility.TextUtil;
import api.praya.myitems.builder.item.ItemSetBonusEffectAbilityWeapon;
import api.praya.myitems.builder.item.ItemSetBonusEffectStats;
import java.util.ArrayList;
import com.praya.agarthalib.utility.MathUtil;
import api.praya.myitems.builder.item.ItemSetComponent;
import api.praya.myitems.builder.item.ItemSetBonus;
import org.bukkit.plugin.java.JavaPlugin;
import com.praya.agarthalib.utility.FileUtil;
import java.util.Iterator;
import java.util.Collection;
import com.praya.myitems.MyItems;
import api.praya.myitems.builder.item.ItemSet;
import java.util.HashMap;
import com.praya.myitems.builder.handler.HandlerConfig;

public class ItemSetConfig extends HandlerConfig
{
    private final HashMap<String, ItemSet> mapItemSet;
    
    public ItemSetConfig(final MyItems plugin) {
        super(plugin);
        this.mapItemSet = new HashMap<String, ItemSet>();
    }
    
    public final Collection<String> getItemSetIDs() {
        return this.mapItemSet.keySet();
    }
    
    public final Collection<ItemSet> getAllItemSet() {
        return this.mapItemSet.values();
    }
    
    public final ItemSet getItemSet(final String id) {
        for (final String key : this.getItemSetIDs()) {
            if (key.equalsIgnoreCase(id)) {
                return this.mapItemSet.get(key);
            }
        }
        return null;
    }
    
    public final void setup() {
        this.reset();
        this.loadConfig();
    }
    
    private final void reset() {
        this.mapItemSet.clear();
    }
    
    private final void loadConfig() {
        final PluginManager pluginManager = this.plugin.getPluginManager();
        final DataManager dataManager = pluginManager.getDataManager();
        final String pathDefault = dataManager.getPath("Path_File_Item_Set");
        final String pathFolder = dataManager.getPath("Path_Folder_Item_Set");
        final File fileDefault = FileUtil.getFile((JavaPlugin)this.plugin, pathDefault);
        final File fileFolder = FileUtil.getFile((JavaPlugin)this.plugin, pathFolder);
        if (!fileDefault.exists()) {
            FileUtil.saveResource((JavaPlugin)this.plugin, pathDefault);
        }
        File[] listFiles;
        for (int length = (listFiles = fileFolder.listFiles()).length, i = 0; i < length; ++i) {
            final File file = listFiles[i];
            final FileConfiguration config = FileUtil.getFileConfiguration(file);
            for (final String key : config.getKeys(false)) {
                final ConfigurationSection mainDataSection = config.getConfigurationSection(key);
                final HashMap<Integer, ItemSetBonus> mapBonus = new HashMap<Integer, ItemSetBonus>();
                final HashMap<String, ItemSetComponent> mapComponent = new HashMap<String, ItemSetComponent>();
                String name = key.replace("_", " ");
                for (final String mainData : mainDataSection.getKeys(false)) {
                    if (mainData.equalsIgnoreCase("Name")) {
                        name = mainDataSection.getString(mainData);
                    }
                    else if (mainData.equalsIgnoreCase("Bonus")) {
                        final ConfigurationSection bonusAmountSection = mainDataSection.getConfigurationSection(mainData);
                        for (final String bonusAmount : bonusAmountSection.getKeys(false)) {
                            if (MathUtil.isNumber(bonusAmount)) {
                                final ConfigurationSection bonusDataSection = bonusAmountSection.getConfigurationSection(bonusAmount);
                                final int amountID = MathUtil.parseInteger(bonusAmount);
                                final List<String> description = new ArrayList<String>();
                                ItemSetBonusEffectStats bonusEffectStats = new ItemSetBonusEffectStats();
                                ItemSetBonusEffectAbilityWeapon bonusEffectAbilityWeapon = new ItemSetBonusEffectAbilityWeapon();
                                for (final String bonusData : bonusDataSection.getKeys(false)) {
                                    if (bonusData.equalsIgnoreCase("Description")) {
                                        if (bonusDataSection.isList(bonusData)) {
                                            for (final String descriptionLine : bonusDataSection.getStringList(bonusData)) {
                                                description.add(ChatColor.stripColor(TextUtil.colorful(descriptionLine)));
                                            }
                                        }
                                        else {
                                            if (!bonusDataSection.isString(bonusData)) {
                                                continue;
                                            }
                                            description.add(ChatColor.stripColor(TextUtil.colorful(bonusDataSection.getString(bonusData))));
                                        }
                                    }
                                    else {
                                        if (!bonusData.equalsIgnoreCase("Effect")) {
                                            continue;
                                        }
                                        final ConfigurationSection effectDataSection = bonusDataSection.getConfigurationSection(bonusData);
                                        for (final String effectData : effectDataSection.getKeys(false)) {
                                            if (effectData.equalsIgnoreCase("Stats")) {
                                                final ConfigurationSection effectStatsSection = effectDataSection.getConfigurationSection(effectData);
                                                double additionalDamage = 0.0;
                                                double percentDamage = 0.0;
                                                double penetration = 0.0;
                                                double pvpDamage = 0.0;
                                                double pveDamage = 0.0;
                                                double additionalDefense = 0.0;
                                                double percentDefense = 0.0;
                                                double health = 0.0;
                                                double healthRegen = 0.0;
                                                double staminaMax = 0.0;
                                                double staminaRegen = 0.0;
                                                double attackAoERadius = 0.0;
                                                double attackAoEDamage = 0.0;
                                                double pvpDefense = 0.0;
                                                double pveDefense = 0.0;
                                                double criticalChance = 0.0;
                                                double criticalDamage = 0.0;
                                                double blockAmount = 0.0;
                                                double blockRate = 0.0;
                                                double hitRate = 0.0;
                                                double dodgeRate = 0.0;
                                                for (final String effectStats : effectStatsSection.getKeys(false)) {
                                                    if (effectStats.equalsIgnoreCase("Additional_Damage") || effectStats.equalsIgnoreCase("Damage")) {
                                                        additionalDamage = effectStatsSection.getDouble(effectStats);
                                                    }
                                                    else if (effectStats.equalsIgnoreCase("Percent_Damage")) {
                                                        percentDamage = effectStatsSection.getDouble(effectStats);
                                                    }
                                                    else if (effectStats.equalsIgnoreCase("Penetration")) {
                                                        penetration = effectStatsSection.getDouble(effectStats);
                                                    }
                                                    else if (effectStats.equalsIgnoreCase("PvP_Damage")) {
                                                        pvpDamage = effectStatsSection.getDouble(effectStats);
                                                    }
                                                    else if (effectStats.equalsIgnoreCase("PvE_Damage")) {
                                                        pveDamage = effectStatsSection.getDouble(effectStats);
                                                    }
                                                    else if (effectStats.equalsIgnoreCase("Additional_Defense") || effectStats.equalsIgnoreCase("Defense")) {
                                                        additionalDefense = effectStatsSection.getDouble(effectStats);
                                                    }
                                                    else if (effectStats.equalsIgnoreCase("Percent_Defense")) {
                                                        percentDefense = effectStatsSection.getDouble(effectStats);
                                                    }
                                                    else if (effectStats.equalsIgnoreCase("Health")) {
                                                        health = effectStatsSection.getDouble(effectStats);
                                                    }
                                                    else if (effectStats.equalsIgnoreCase("Health_Regen") || effectStats.equalsIgnoreCase("Regen") || effectStats.equalsIgnoreCase("Regeneration")) {
                                                        healthRegen = effectStatsSection.getDouble(effectStats);
                                                    }
                                                    else if (effectStats.equalsIgnoreCase("Stamina_Max") || effectStats.equalsIgnoreCase("Max_Stamina")) {
                                                        staminaMax = effectStatsSection.getDouble(effectStats);
                                                    }
                                                    else if (effectStats.equalsIgnoreCase("Stamina_Regen") || effectStats.equalsIgnoreCase("Regen_Stamina")) {
                                                        staminaRegen = effectStatsSection.getDouble(effectStats);
                                                    }
                                                    else if (effectStats.equalsIgnoreCase("Attack_AoE_Radius")) {
                                                        attackAoERadius = effectStatsSection.getDouble(effectStats);
                                                    }
                                                    else if (effectStats.equalsIgnoreCase("Attack_AoE_Damage")) {
                                                        attackAoEDamage = effectStatsSection.getDouble(effectStats);
                                                    }
                                                    else if (effectStats.equalsIgnoreCase("PvP_Defense")) {
                                                        pvpDefense = effectStatsSection.getDouble(effectStats);
                                                    }
                                                    else if (effectStats.equalsIgnoreCase("PvE_Defense")) {
                                                        pveDefense = effectStatsSection.getDouble(effectStats);
                                                    }
                                                    else if (effectStats.equalsIgnoreCase("Critical_Chance")) {
                                                        criticalChance = effectStatsSection.getDouble(effectStats);
                                                    }
                                                    else if (effectStats.equalsIgnoreCase("Critical_Damage")) {
                                                        criticalDamage = effectStatsSection.getDouble(effectStats);
                                                    }
                                                    else if (effectStats.equalsIgnoreCase("Block_Amount")) {
                                                        blockAmount = effectStatsSection.getDouble(effectStats);
                                                    }
                                                    else if (effectStats.equalsIgnoreCase("Block_Rate")) {
                                                        blockRate = effectStatsSection.getDouble(effectStats);
                                                    }
                                                    else if (effectStats.equalsIgnoreCase("Hit_Rate")) {
                                                        hitRate = effectStatsSection.getDouble(effectStats);
                                                    }
                                                    else {
                                                        if (!effectStats.equalsIgnoreCase("Dodge_Rate")) {
                                                            continue;
                                                        }
                                                        dodgeRate = effectStatsSection.getDouble(effectStats);
                                                    }
                                                }
                                                bonusEffectStats = new ItemSetBonusEffectStats(additionalDamage, percentDamage, penetration, pvpDamage, pveDamage, additionalDefense, percentDefense, health, healthRegen, staminaMax, staminaRegen, attackAoERadius, attackAoEDamage, pvpDefense, pveDefense, criticalChance, criticalDamage, blockAmount, blockRate, hitRate, dodgeRate);
                                            }
                                            else {
                                                if (!effectData.equalsIgnoreCase("Ability_Weapon")) {
                                                    continue;
                                                }
                                                final ConfigurationSection effectAbilityWeaponSection = effectDataSection.getConfigurationSection(effectData);
                                                final HashMap<String, AbilityItemWeapon> mapAbilityItem = new HashMap<String, AbilityItemWeapon>();
                                                for (final String effectAbilityWeapon : effectAbilityWeaponSection.getKeys(false)) {
                                                    final ConfigurationSection effectAbilityWeaponDataSection = effectAbilityWeaponSection.getConfigurationSection(effectAbilityWeapon);
                                                    double chance = 100.0;
                                                    int grade = 0;
                                                    for (final String effectAbilityWeaponData : effectAbilityWeaponDataSection.getKeys(false)) {
                                                        if (effectAbilityWeaponData.equalsIgnoreCase("Chance")) {
                                                            chance = MathUtil.limitDouble(effectAbilityWeaponDataSection.getDouble(effectAbilityWeaponData), 0.0, 100.0);
                                                        }
                                                        else {
                                                            if (!effectAbilityWeaponData.equalsIgnoreCase("Grade")) {
                                                                continue;
                                                            }
                                                            grade = effectAbilityWeaponDataSection.getInt(effectAbilityWeaponData);
                                                        }
                                                    }
                                                    if (grade > 0) {
                                                        final AbilityItemWeapon abilityItemWeapon = new AbilityItemWeapon(effectAbilityWeapon, grade, chance);
                                                        mapAbilityItem.put(effectAbilityWeapon, abilityItemWeapon);
                                                    }
                                                }
                                                bonusEffectAbilityWeapon = new ItemSetBonusEffectAbilityWeapon(mapAbilityItem);
                                            }
                                        }
                                    }
                                }
                                final ItemSetBonusEffect itemSetBonusEffect = new ItemSetBonusEffect(bonusEffectStats, bonusEffectAbilityWeapon);
                                final ItemSetBonus itemSetBonus = new ItemSetBonus(amountID, description, itemSetBonusEffect);
                                mapBonus.put(amountID, itemSetBonus);
                            }
                        }
                    }
                    else {
                        if (!mainData.equalsIgnoreCase("Component")) {
                            continue;
                        }
                        final ConfigurationSection componentSection = mainDataSection.getConfigurationSection(mainData);
                        for (final String component : componentSection.getKeys(false)) {
                            final ConfigurationSection componentDataSection = componentSection.getConfigurationSection(component);
                            final String componentID = String.valueOf(key) + "_" + component;
                            final List<String> lores = new ArrayList<String>();
                            final List<String> flags = new ArrayList<String>();
                            final Set<Slot> slots = new HashSet<Slot>();
                            final HashMap<Enchantment, Integer> mapEnchantment = new HashMap<Enchantment, Integer>();
                            String keyLore = componentID.replace("_", " ");
                            String displayName = null;
                            Material material = null;
                            boolean shiny = false;
                            boolean unbreakable = false;
                            short data = 0;
                            for (final String componentData : componentDataSection.getKeys(false)) {
                                if (componentData.equalsIgnoreCase("KeyLore")) {
                                    keyLore = ChatColor.stripColor(TextUtil.colorful(componentDataSection.getString(componentData)));
                                }
                                else if (componentData.equalsIgnoreCase("Display_Name") || componentData.equalsIgnoreCase("Display") || componentData.equalsIgnoreCase("Name")) {
                                    displayName = componentDataSection.getString(componentData);
                                }
                                else if (componentData.equalsIgnoreCase("Material")) {
                                    material = MaterialUtil.getMaterial(componentDataSection.getString(componentData));
                                }
                                else if (componentData.equalsIgnoreCase("Data")) {
                                    data = (short)componentDataSection.getInt(componentData);
                                }
                                else if (componentData.equalsIgnoreCase("Shiny")) {
                                    shiny = componentDataSection.getBoolean(componentData);
                                }
                                else if (componentData.equalsIgnoreCase("Unbreakable")) {
                                    unbreakable = componentDataSection.getBoolean(componentData);
                                }
                                else if (componentData.equalsIgnoreCase("Lores") || componentData.equalsIgnoreCase("Lore")) {
                                    lores.addAll(componentDataSection.getStringList(componentData));
                                }
                                else if (componentData.equalsIgnoreCase("Flags") || componentData.equalsIgnoreCase("ItemFlags")) {
                                    flags.addAll(componentDataSection.getStringList(componentData));
                                }
                                else if (componentData.equalsIgnoreCase("Slots") || componentData.equalsIgnoreCase("Slot")) {
                                    if (componentDataSection.isString(componentData)) {
                                        final String slotData = componentDataSection.getString(componentData);
                                        final Slot slot = Slot.get(slotData);
                                        if (slot == null) {
                                            continue;
                                        }
                                        slots.add(slot);
                                    }
                                    else {
                                        if (!componentDataSection.isList(componentData)) {
                                            continue;
                                        }
                                        final List<String> listSlotData = (List<String>)componentDataSection.getStringList(componentData);
                                        for (final String slotData2 : listSlotData) {
                                            final Slot slot2 = Slot.get(slotData2);
                                            if (slot2 != null) {
                                                slots.add(slot2);
                                            }
                                        }
                                    }
                                }
                                else {
                                    if (!componentData.equalsIgnoreCase("Enchantments") && !componentData.equalsIgnoreCase("Enchantment")) {
                                        continue;
                                    }
                                    final ConfigurationSection enchantmentDataSection = componentDataSection.getConfigurationSection(componentData);
                                    for (final String enchantmentData : enchantmentDataSection.getKeys(false)) {
                                        final Enchantment enchantment = EnchantmentUtil.getEnchantment(enchantmentData);
                                        if (enchantment != null) {
                                            final int grade2 = enchantmentDataSection.getInt(enchantmentData);
                                            mapEnchantment.put(enchantment, grade2);
                                        }
                                    }
                                }
                            }
                            if (slots.isEmpty()) {
                                Slot[] values;
                                for (int length2 = (values = Slot.values()).length, j = 0; j < length2; ++j) {
                                    final Slot slot3 = values[j];
                                    slots.add(slot3);
                                }
                            }
                            if (material != null) {
                                final ItemSetComponentItem itemSetComponentItem = new ItemSetComponentItem(displayName, material, data, shiny, unbreakable, lores, flags, mapEnchantment);
                                final ItemSetComponent itemSetComponent = new ItemSetComponent(key, componentID, keyLore, itemSetComponentItem, slots);
                                mapComponent.put(componentID, itemSetComponent);
                            }
                        }
                    }
                }
                final ItemSet itemSet = new ItemSet(key, name, mapBonus, mapComponent);
                this.mapItemSet.put(key, itemSet);
            }
        }
    }
}
