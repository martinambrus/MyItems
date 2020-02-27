// 
// Decompiled by Procyon v0.5.36
// 

package com.praya.myitems.config.game;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import java.io.File;
import com.praya.myitems.manager.plugin.DataManager;
import com.praya.myitems.manager.plugin.PluginManager;
import api.praya.myitems.builder.lorestats.LoreStatsUniversal;
import api.praya.myitems.builder.lorestats.LoreStatsArmor;
import api.praya.myitems.builder.lorestats.LoreStatsWeapon;
import com.praya.agarthalib.utility.TextUtil;
import api.praya.myitems.builder.lorestats.LoreStatsModifier;
import org.bukkit.plugin.java.JavaPlugin;
import com.praya.agarthalib.utility.FileUtil;
import java.util.Iterator;
import java.util.Collection;
import com.praya.myitems.MyItems;
import api.praya.myitems.builder.item.ItemTier;
import java.util.HashMap;
import com.praya.myitems.builder.handler.HandlerConfig;

public class ItemTierConfig extends HandlerConfig
{
    private final HashMap<String, ItemTier> mapTier;
    
    public ItemTierConfig(final MyItems plugin) {
        super(plugin);
        this.mapTier = new HashMap<String, ItemTier>();
    }
    
    public final Collection<String> getItemTierIDs() {
        return this.mapTier.keySet();
    }
    
    public final Collection<ItemTier> getItemTiers() {
        return this.mapTier.values();
    }
    
    public final ItemTier getItemTier(final String id) {
        for (final String key : this.getItemTierIDs()) {
            if (key.equalsIgnoreCase(id)) {
                return this.mapTier.get(key);
            }
        }
        return null;
    }
    
    public final void setup() {
        this.moveOldFile();
        this.reset();
        this.loadConfig();
    }
    
    private final void reset() {
        this.mapTier.clear();
    }
    
    private final void loadConfig() {
        final PluginManager pluginManager = this.plugin.getPluginManager();
        final DataManager dataManager = pluginManager.getDataManager();
        final String pathDefault = dataManager.getPath("Path_File_Item_Tier");
        final String pathFolder = dataManager.getPath("Path_Folder_Item_Tier");
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
                String name = "";
                String prefix = "";
                LoreStatsModifier statsModifier = new LoreStatsModifier();
                for (final String mainData : mainDataSection.getKeys(false)) {
                    if (mainData.equalsIgnoreCase("Name")) {
                        name = TextUtil.colorful(mainDataSection.getString(mainData));
                    }
                    else if (mainData.equalsIgnoreCase("Prefix")) {
                        prefix = TextUtil.colorful(mainDataSection.getString(mainData));
                    }
                    else {
                        if (!mainData.equalsIgnoreCase("Modifier")) {
                            continue;
                        }
                        final ConfigurationSection modifierDataSection = mainDataSection.getConfigurationSection(mainData);
                        double damage = 1.0;
                        double penetration = 1.0;
                        double pvpDamage = 1.0;
                        double pveDamage = 1.0;
                        double criticalChance = 1.0;
                        double criticalDamage = 1.0;
                        double hitRate = 1.0;
                        double defense = 1.0;
                        double pvpDefense = 1.0;
                        double pveDefense = 1.0;
                        double health = 1.0;
                        double healthRegen = 1.0;
                        double staminaMax = 1.0;
                        double staminaRegen = 1.0;
                        double attackAoERadius = 1.0;
                        double attackAoEDamage = 1.0;
                        double blockAmount = 1.0;
                        double blockRate = 1.0;
                        double dodgeRate = 1.0;
                        double durability = 1.0;
                        double level = 1.0;
                        for (final String modifierData : modifierDataSection.getKeys(false)) {
                            if (modifierData.equalsIgnoreCase("Damage")) {
                                damage = modifierDataSection.getDouble(modifierData);
                            }
                            else if (modifierData.equalsIgnoreCase("Penetration")) {
                                penetration = modifierDataSection.getDouble(modifierData);
                            }
                            else if (modifierData.equalsIgnoreCase("PvP_Damage")) {
                                pvpDamage = modifierDataSection.getDouble(modifierData);
                            }
                            else if (modifierData.equalsIgnoreCase("PvE_Damage")) {
                                pveDamage = modifierDataSection.getDouble(modifierData);
                            }
                            else if (modifierData.equalsIgnoreCase("Critical_Chance")) {
                                criticalChance = modifierDataSection.getDouble(modifierData);
                            }
                            else if (modifierData.equalsIgnoreCase("Critical_Damage")) {
                                criticalDamage = modifierDataSection.getDouble(modifierData);
                            }
                            else if (modifierData.equalsIgnoreCase("Hit_Rate")) {
                                hitRate = modifierDataSection.getDouble(modifierData);
                            }
                            else if (modifierData.equalsIgnoreCase("Defense")) {
                                defense = modifierDataSection.getDouble(modifierData);
                            }
                            else if (modifierData.equalsIgnoreCase("PvP_Defense")) {
                                pvpDefense = modifierDataSection.getDouble(modifierData);
                            }
                            else if (modifierData.equalsIgnoreCase("PvE_Defense")) {
                                pveDefense = modifierDataSection.getDouble(modifierData);
                            }
                            else if (modifierData.equalsIgnoreCase("Health")) {
                                health = modifierDataSection.getDouble(modifierData);
                            }
                            else if (modifierData.equalsIgnoreCase("Health_Regen")) {
                                healthRegen = modifierDataSection.getDouble(modifierData);
                            }
                            else if (modifierData.equalsIgnoreCase("Stamina_Max")) {
                                staminaMax = modifierDataSection.getDouble(modifierData);
                            }
                            else if (modifierData.equalsIgnoreCase("Stamina_Regen")) {
                                staminaRegen = modifierDataSection.getDouble(modifierData);
                            }
                            else if (modifierData.equalsIgnoreCase("Attack_AoE_Radius")) {
                                attackAoERadius = modifierDataSection.getDouble(modifierData);
                            }
                            else if (modifierData.equalsIgnoreCase("Attack_AoE_Damage")) {
                                attackAoEDamage = modifierDataSection.getDouble(modifierData);
                            }
                            else if (modifierData.equalsIgnoreCase("Block_Amount")) {
                                blockAmount = modifierDataSection.getDouble(modifierData);
                            }
                            else if (modifierData.equalsIgnoreCase("Block_Rate")) {
                                blockRate = modifierDataSection.getDouble(modifierData);
                            }
                            else if (modifierData.equalsIgnoreCase("Dodge_Rate")) {
                                dodgeRate = modifierDataSection.getDouble(modifierData);
                            }
                            else if (modifierData.equalsIgnoreCase("Durability")) {
                                durability = modifierDataSection.getDouble(modifierData);
                            }
                            else {
                                if (!modifierData.equalsIgnoreCase("Level")) {
                                    continue;
                                }
                                level = modifierDataSection.getDouble(modifierData);
                            }
                        }
                        final LoreStatsWeapon weaponModifier = new LoreStatsWeapon(damage, penetration, pvpDamage, pveDamage, attackAoERadius, attackAoEDamage, criticalChance, criticalDamage, hitRate);
                        final LoreStatsArmor armorModifier = new LoreStatsArmor(defense, pvpDefense, pveDefense, health, healthRegen, staminaMax, staminaRegen, blockAmount, blockRate, dodgeRate);
                        final LoreStatsUniversal universalModifier = new LoreStatsUniversal(durability, level);
                        statsModifier = new LoreStatsModifier(weaponModifier, armorModifier, universalModifier);
                    }
                }
                final ItemTier itemTier = new ItemTier(key, name, prefix, statsModifier);
                this.mapTier.put(key, itemTier);
            }
        }
    }
    
    private final void moveOldFile() {
        final PluginManager pluginManager = this.plugin.getPluginManager();
        final DataManager dataManager = pluginManager.getDataManager();
        final String pathSource_1 = "item_tier.yml";
        final String pathSource_2 = "Configuration/item_tier.yml";
        final String pathTarget = dataManager.getPath("Path_File_Item_Tier");
        final File fileSource_1 = FileUtil.getFile((JavaPlugin)this.plugin, "item_tier.yml");
        final File fileSource_2 = FileUtil.getFile((JavaPlugin)this.plugin, "Configuration/item_tier.yml");
        final File fileTarget = FileUtil.getFile((JavaPlugin)this.plugin, pathTarget);
        if (fileSource_1.exists()) {
            FileUtil.moveFileSilent(fileSource_1, fileTarget);
        }
        else if (fileSource_2.exists()) {
            FileUtil.moveFileSilent(fileSource_2, fileTarget);
        }
    }
}
