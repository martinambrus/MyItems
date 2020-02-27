// 
// Decompiled by Procyon v0.5.36
// 

package com.praya.myitems.config.game;

import org.bukkit.inventory.ItemStack;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import java.io.File;
import com.praya.myitems.manager.plugin.DataManager;
import com.praya.myitems.manager.plugin.PluginManager;
import api.praya.myitems.builder.socket.SocketGemsProperties;
import core.praya.agarthalib.bridge.unity.Bridge;
import java.util.List;
import com.praya.agarthalib.utility.EquipmentUtil;
import core.praya.agarthalib.enums.main.RomanNumber;
import api.praya.myitems.builder.socket.SocketGems;
import core.praya.agarthalib.enums.branch.MaterialEnum;
import com.praya.agarthalib.utility.MathUtil;
import com.praya.agarthalib.utility.EnchantmentUtil;
import com.praya.agarthalib.utility.MaterialUtil;
import com.praya.agarthalib.utility.TextUtil;
import core.praya.agarthalib.enums.main.SlotType;
import org.bukkit.enchantments.Enchantment;
import java.util.ArrayList;
import org.bukkit.plugin.java.JavaPlugin;
import com.praya.agarthalib.utility.FileUtil;
import com.praya.myitems.config.plugin.MainConfig;
import java.util.Iterator;
import java.util.Collection;
import com.praya.myitems.MyItems;
import api.praya.myitems.builder.socket.SocketGemsTree;
import java.util.HashMap;
import com.praya.myitems.builder.handler.HandlerConfig;

public class SocketConfig extends HandlerConfig
{
    private final HashMap<String, SocketGemsTree> mapSocketTree;
    
    public SocketConfig(final MyItems plugin) {
        super(plugin);
        this.mapSocketTree = new HashMap<String, SocketGemsTree>();
    }
    
    public final Collection<String> getSocketIDs() {
        return this.mapSocketTree.keySet();
    }
    
    public final Collection<SocketGemsTree> getSocketTreeBuilds() {
        return this.mapSocketTree.values();
    }
    
    public final SocketGemsTree getSocketTree(final String id) {
        for (final String key : this.getSocketIDs()) {
            if (key.equalsIgnoreCase(id)) {
                return this.mapSocketTree.get(key);
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
        this.mapSocketTree.clear();
    }
    
    private final void loadConfig() {
        final PluginManager pluginManager = this.plugin.getPluginManager();
        final DataManager dataManager = pluginManager.getDataManager();
        final MainConfig mainConfig = MainConfig.getInstance();
        final String path = dataManager.getPath("Path_File_Socket");
        final File file = FileUtil.getFile((JavaPlugin)this.plugin, path);
        if (!file.exists()) {
            FileUtil.saveResource((JavaPlugin)this.plugin, path);
        }
        final FileConfiguration config = FileUtil.getFileConfiguration(file);
        for (final String key : config.getKeys(false)) {
            final ConfigurationSection section = config.getConfigurationSection(key);
            final List<String> lores = new ArrayList<String>();
            final List<String> flags = new ArrayList<String>();
            final HashMap<Enchantment, Integer> enchantments = new HashMap<Enchantment, Integer>();
            String keyLore = null;
            String display = null;
            Material material = null;
            SlotType typeItem = SlotType.UNIVERSAL;
            boolean shiny = false;
            boolean unbreakable = false;
            short data = 0;
            int maxGrade = 1;
            double baseSuccessRate = 100.0;
            double scaleSuccessRate = 0.0;
            double baseAdditionalDamage = 0.0;
            double scaleAdditionalDamage = 0.0;
            double basePercentDamage = 0.0;
            double scalePercentDamage = 0.0;
            double basePenetration = 0.0;
            double scalePenetration = 0.0;
            double basePvPDamage = 0.0;
            double scalePvPDamage = 0.0;
            double basePvEDamage = 0.0;
            double scalePvEDamage = 0.0;
            double baseAdditionalDefense = 0.0;
            double scaleAdditionalDefense = 0.0;
            double basePercentDefense = 0.0;
            double scalePercentDefense = 0.0;
            double baseMaxHealth = 0.0;
            double scaleMaxHealth = 0.0;
            double baseHealthRegen = 0.0;
            double scaleHealthRegen = 0.0;
            double baseStaminaMax = 0.0;
            double scaleStaminaMax = 0.0;
            double baseStaminaRegen = 0.0;
            double scaleStaminaRegen = 0.0;
            double baseAttackAoERadius = 0.0;
            double scaleAttackAoERadius = 0.0;
            double baseAttackAoEDamage = 0.0;
            double scaleAttackAoEDamage = 0.0;
            double basePvPDefense = 0.0;
            double scalePvPDefense = 0.0;
            double basePvEDefense = 0.0;
            double scalePvEDefense = 0.0;
            double baseCriticalChance = 0.0;
            double scaleCriticalChance = 0.0;
            double baseCriticalDamage = 0.0;
            double scaleCriticalDamage = 0.0;
            double baseBlockAmount = 0.0;
            double scaleBlockAmount = 0.0;
            double baseBlockRate = 0.0;
            double scaleBlockRate = 0.0;
            double baseHitRate = 0.0;
            double scaleHitRate = 0.0;
            double baseDodgeRate = 0.0;
            double scaleDodgeRate = 0.0;
            for (final String keySection : section.getKeys(false)) {
                if (keySection.equalsIgnoreCase("KeyLore")) {
                    keyLore = TextUtil.colorful(section.getString(keySection));
                }
                else if (keySection.equalsIgnoreCase("Display_Name") || keySection.equalsIgnoreCase("Display") || keySection.equalsIgnoreCase("Name")) {
                    display = section.getString(keySection);
                }
                else if (keySection.equalsIgnoreCase("Material")) {
                    material = MaterialUtil.getMaterial(section.getString(keySection));
                }
                else if (keySection.equalsIgnoreCase("Data")) {
                    data = (short)section.getInt(keySection);
                }
                else if (keySection.equalsIgnoreCase("Shiny")) {
                    shiny = section.getBoolean(keySection);
                }
                else if (keySection.equalsIgnoreCase("Unbreakable")) {
                    unbreakable = section.getBoolean(keySection);
                }
                else if (keySection.equalsIgnoreCase("Max_Grade")) {
                    maxGrade = section.getInt(keySection);
                }
                else if (keySection.equalsIgnoreCase("Type_Item")) {
                    final String textTypeItem = section.getString(keySection);
                    typeItem = SlotType.getSlotType(textTypeItem);
                }
                else if (keySection.equalsIgnoreCase("Base_Success_Rate")) {
                    baseSuccessRate = section.getDouble(keySection);
                }
                else if (keySection.equalsIgnoreCase("Scale_Success_Rate")) {
                    scaleSuccessRate = section.getDouble(keySection);
                }
                else if (keySection.equalsIgnoreCase("Lores") || keySection.equalsIgnoreCase("Lore")) {
                    lores.addAll(section.getStringList(keySection));
                }
                else if (keySection.equalsIgnoreCase("Flags") || keySection.equalsIgnoreCase("ItemFlags")) {
                    flags.addAll(section.getStringList(keySection));
                }
                else if (keySection.equalsIgnoreCase("Enchantments") || keySection.equalsIgnoreCase("Enchantment")) {
                    for (final String lineEnchant : section.getStringList(keySection)) {
                        final String[] parts = lineEnchant.replaceAll(" ", "").split(":");
                        int grade = 1;
                        if (parts.length > 0) {
                            final String enchantmentKey = parts[0].toUpperCase();
                            final Enchantment enchantment = EnchantmentUtil.getEnchantment(enchantmentKey);
                            if (enchantment == null) {
                                continue;
                            }
                            if (parts.length > 1) {
                                final String textGrade = parts[1];
                                if (MathUtil.isNumber(textGrade)) {
                                    grade = MathUtil.parseInteger(textGrade);
                                    grade = MathUtil.limitInteger(grade, 1, grade);
                                }
                            }
                            enchantments.put(enchantment, grade);
                        }
                    }
                }
                else if (keySection.equalsIgnoreCase("Bonus_Base_Additional_Damage") || keySection.equalsIgnoreCase("Bonus_Additional_Damage")) {
                    baseAdditionalDamage = section.getDouble(keySection);
                }
                else if (keySection.equalsIgnoreCase("Bonus_Scale_Additional_Damage")) {
                    scaleAdditionalDamage = section.getDouble(keySection);
                }
                else if (keySection.equalsIgnoreCase("Bonus_Base_Percent_Damage") || keySection.equalsIgnoreCase("Bonus_Percent_Damage")) {
                    basePercentDamage = section.getDouble(keySection);
                }
                else if (keySection.equalsIgnoreCase("Bonus_Scale_Percent_Damage")) {
                    scalePercentDamage = section.getDouble(keySection);
                }
                else if (keySection.equalsIgnoreCase("Bonus_Base_Penetration") || keySection.equalsIgnoreCase("Bonus_Penetration")) {
                    basePenetration = section.getDouble(keySection);
                }
                else if (keySection.equalsIgnoreCase("Bonus_Scale_Penetration")) {
                    scalePenetration = section.getDouble(keySection);
                }
                else if (keySection.equalsIgnoreCase("Bonus_Base_PvP_Damage") || keySection.equalsIgnoreCase("Bonus_PvP_Damage")) {
                    basePvPDamage = section.getDouble(keySection);
                }
                else if (keySection.equalsIgnoreCase("Bonus_Scale_PvP_Damage")) {
                    scalePvPDamage = section.getDouble(keySection);
                }
                else if (keySection.equalsIgnoreCase("Bonus_Base_PvE_Damage") || keySection.equalsIgnoreCase("Bonus_PvE_Damage")) {
                    basePvEDamage = section.getDouble(keySection);
                }
                else if (keySection.equalsIgnoreCase("Bonus_Scale_PvE_Damage")) {
                    scalePvEDamage = section.getDouble(keySection);
                }
                else if (keySection.equalsIgnoreCase("Bonus_Base_Additional_Defense") || keySection.equalsIgnoreCase("Bonus_Additional_Defense")) {
                    baseAdditionalDefense = section.getDouble(keySection);
                }
                else if (keySection.equalsIgnoreCase("Bonus_Scale_Additional_Defense")) {
                    scaleAdditionalDefense = section.getDouble(keySection);
                }
                else if (keySection.equalsIgnoreCase("Bonus_Base_Percent_Defense") || keySection.equalsIgnoreCase("Bonus_Percent_Defense")) {
                    basePercentDefense = section.getDouble(keySection);
                }
                else if (keySection.equalsIgnoreCase("Bonus_Scale_Percent_Defense")) {
                    scalePercentDefense = section.getDouble(keySection);
                }
                else if (keySection.equalsIgnoreCase("Bonus_Base_Max_Health") || keySection.equalsIgnoreCase("Bonus_Max_Health")) {
                    baseMaxHealth = section.getDouble(keySection);
                }
                else if (keySection.equalsIgnoreCase("Bonus_Scale_Max_Health")) {
                    scaleMaxHealth = section.getDouble(keySection);
                }
                else if (keySection.equalsIgnoreCase("Bonus_Base_Health_Regen") || keySection.equalsIgnoreCase("Bonus_Health_Regen")) {
                    baseHealthRegen = section.getDouble(keySection);
                }
                else if (keySection.equalsIgnoreCase("Bonus_Scale_Health_Regen")) {
                    scaleHealthRegen = section.getDouble(keySection);
                }
                else if (keySection.equalsIgnoreCase("Bonus_Base_Stamina_Max") || keySection.equalsIgnoreCase("Bonus_Stamina_Max")) {
                    baseStaminaMax = section.getDouble(keySection);
                }
                else if (keySection.equalsIgnoreCase("Bonus_Scale_Stamina_Max")) {
                    scaleStaminaMax = section.getDouble(keySection);
                }
                else if (keySection.equalsIgnoreCase("Bonus_Base_Stamina_Regen") || keySection.equalsIgnoreCase("Bonus_Stamina_Regen")) {
                    baseStaminaRegen = section.getDouble(keySection);
                }
                else if (keySection.equalsIgnoreCase("Bonus_Scale_Stamina_Regen")) {
                    scaleStaminaRegen = section.getDouble(keySection);
                }
                else if (keySection.equalsIgnoreCase("Bonus_Base_Attack_AoE_Radius") || keySection.equalsIgnoreCase("Bonus_Attack_AoE_Radius")) {
                    baseAttackAoERadius = section.getDouble(keySection);
                }
                else if (keySection.equalsIgnoreCase("Bonus_Scale_Attack_AoE_Radius")) {
                    scaleAttackAoERadius = section.getDouble(keySection);
                }
                else if (keySection.equalsIgnoreCase("Bonus_Base_Attack_AoE_Damage") || keySection.equalsIgnoreCase("Bonus_Attack_AoE_Damage")) {
                    baseAttackAoEDamage = section.getDouble(keySection);
                }
                else if (keySection.equalsIgnoreCase("Bonus_Scale_Attack_AoE_Damage")) {
                    scaleAttackAoEDamage = section.getDouble(keySection);
                }
                else if (keySection.equalsIgnoreCase("Bonus_Base_PvP_Defense") || keySection.equalsIgnoreCase("Bonus_PvP_Defense")) {
                    basePvPDefense = section.getDouble(keySection);
                }
                else if (keySection.equalsIgnoreCase("Bonus_Scale_PvP_Defense")) {
                    scalePvPDefense = section.getDouble(keySection);
                }
                else if (keySection.equalsIgnoreCase("Bonus_Base_PvE_Defense") || keySection.equalsIgnoreCase("Bonus_PvE_Defense")) {
                    basePvEDefense = section.getDouble(keySection);
                }
                else if (keySection.equalsIgnoreCase("Bonus_Scale_PvE_Defense")) {
                    scalePvEDefense = section.getDouble(keySection);
                }
                else if (keySection.equalsIgnoreCase("Bonus_Base_Critical_Chance") || keySection.equalsIgnoreCase("Bonus_Critical_Chance")) {
                    baseCriticalChance = section.getDouble(keySection);
                }
                else if (keySection.equalsIgnoreCase("Bonus_Scale_Critical_Chance")) {
                    scaleCriticalChance = section.getDouble(keySection);
                }
                else if (keySection.equalsIgnoreCase("Bonus_Base_Critical_Damage") || keySection.equalsIgnoreCase("Bonus_Critical_Damage")) {
                    baseCriticalDamage = section.getDouble(keySection);
                }
                else if (keySection.equalsIgnoreCase("Bonus_Scale_Critical_Damage")) {
                    scaleCriticalDamage = section.getDouble(keySection);
                }
                else if (keySection.equalsIgnoreCase("Bonus_Base_Block_Amount") || keySection.equalsIgnoreCase("Bonus_Block_Amount")) {
                    baseBlockAmount = section.getDouble(keySection);
                }
                else if (keySection.equalsIgnoreCase("Bonus_Scale_Block_Amount")) {
                    scaleBlockAmount = section.getDouble(keySection);
                }
                else if (keySection.equalsIgnoreCase("Bonus_Base_Block_Rate") || keySection.equalsIgnoreCase("Bonus_Block_Rate")) {
                    baseBlockRate = section.getDouble(keySection);
                }
                else if (keySection.equalsIgnoreCase("Bonus_Scale_Block_Rate")) {
                    scaleBlockRate = section.getDouble(keySection);
                }
                else if (keySection.equalsIgnoreCase("Bonus_Base_Hit_Rate") || keySection.equalsIgnoreCase("Bonus_Hit_Rate")) {
                    baseHitRate = section.getDouble(keySection);
                }
                else if (keySection.equalsIgnoreCase("Bonus_Scale_Hit_Rate")) {
                    scaleHitRate = section.getDouble(keySection);
                }
                else if (keySection.equalsIgnoreCase("Bonus_Base_Dodge_Rate") || keySection.equalsIgnoreCase("Bonus_Dodge_Rate")) {
                    baseDodgeRate = section.getDouble(keySection);
                }
                else if (keySection.equalsIgnoreCase("Bonus_Scale_Dodge_Rate")) {
                    scaleDodgeRate = section.getDouble(keySection);
                }
                else {
                    if (!keySection.equalsIgnoreCase("Effect") && !keySection.equalsIgnoreCase("Effects")) {
                        continue;
                    }
                    final ConfigurationSection effectSection = section.getConfigurationSection(keySection);
                    for (final String effect : effectSection.getKeys(false)) {
                        if (effect.equalsIgnoreCase("Bonus_Base_Additional_Damage") || effect.equalsIgnoreCase("Bonus_Additional_Damage")) {
                            baseAdditionalDamage = effectSection.getDouble(effect);
                        }
                        else if (effect.equalsIgnoreCase("Bonus_Scale_Additional_Damage")) {
                            scaleAdditionalDamage = effectSection.getDouble(effect);
                        }
                        else if (effect.equalsIgnoreCase("Bonus_Base_Percent_Damage") || effect.equalsIgnoreCase("Bonus_Percent_Damage")) {
                            basePercentDamage = effectSection.getDouble(effect);
                        }
                        else if (effect.equalsIgnoreCase("Bonus_Scale_Percent_Damage")) {
                            scalePercentDamage = effectSection.getDouble(effect);
                        }
                        else if (effect.equalsIgnoreCase("Bonus_Base_Penetration") || effect.equalsIgnoreCase("Bonus_Penetration")) {
                            basePenetration = effectSection.getDouble(effect);
                        }
                        else if (effect.equalsIgnoreCase("Bonus_Scale_Penetration")) {
                            scalePenetration = effectSection.getDouble(effect);
                        }
                        else if (effect.equalsIgnoreCase("Bonus_Base_PvP_Damage") || effect.equalsIgnoreCase("Bonus_PvP_Damage")) {
                            basePvPDamage = effectSection.getDouble(effect);
                        }
                        else if (effect.equalsIgnoreCase("Bonus_Scale_PvP_Damage")) {
                            scalePvPDamage = effectSection.getDouble(effect);
                        }
                        else if (effect.equalsIgnoreCase("Bonus_Base_PvE_Damage") || effect.equalsIgnoreCase("Bonus_PvE_Damage")) {
                            basePvEDamage = effectSection.getDouble(effect);
                        }
                        else if (effect.equalsIgnoreCase("Bonus_Scale_PvE_Damage")) {
                            scalePvEDamage = effectSection.getDouble(effect);
                        }
                        else if (effect.equalsIgnoreCase("Bonus_Base_Additional_Defense") || effect.equalsIgnoreCase("Bonus_Additional_Defense")) {
                            baseAdditionalDefense = effectSection.getDouble(effect);
                        }
                        else if (effect.equalsIgnoreCase("Bonus_Scale_Additional_Defense")) {
                            scaleAdditionalDefense = effectSection.getDouble(effect);
                        }
                        else if (effect.equalsIgnoreCase("Bonus_Base_Percent_Defense") || effect.equalsIgnoreCase("Bonus_Percent_Defense")) {
                            basePercentDefense = effectSection.getDouble(effect);
                        }
                        else if (effect.equalsIgnoreCase("Bonus_Scale_Percent_Defense")) {
                            scalePercentDefense = effectSection.getDouble(effect);
                        }
                        else if (effect.equalsIgnoreCase("Bonus_Base_Max_Health") || effect.equalsIgnoreCase("Bonus_Max_Health")) {
                            baseMaxHealth = effectSection.getDouble(effect);
                        }
                        else if (effect.equalsIgnoreCase("Bonus_Scale_Max_Health")) {
                            scaleMaxHealth = effectSection.getDouble(effect);
                        }
                        else if (effect.equalsIgnoreCase("Bonus_Base_Health_Regen") || effect.equalsIgnoreCase("Bonus_Health_Regen")) {
                            baseHealthRegen = effectSection.getDouble(effect);
                        }
                        else if (effect.equalsIgnoreCase("Bonus_Scale_Health_Regen")) {
                            scaleHealthRegen = effectSection.getDouble(effect);
                        }
                        else if (effect.equalsIgnoreCase("Bonus_Base_Stamina_Max") || effect.equalsIgnoreCase("Bonus_Stamina_Max")) {
                            baseStaminaMax = effectSection.getDouble(effect);
                        }
                        else if (effect.equalsIgnoreCase("Bonus_Scale_Stamina_Max")) {
                            scaleStaminaMax = effectSection.getDouble(effect);
                        }
                        else if (effect.equalsIgnoreCase("Bonus_Base_Stamina_Regen") || effect.equalsIgnoreCase("Bonus_Stamina_Regen")) {
                            baseStaminaRegen = effectSection.getDouble(effect);
                        }
                        else if (effect.equalsIgnoreCase("Bonus_Scale_Stamina_Regen")) {
                            scaleStaminaRegen = effectSection.getDouble(effect);
                        }
                        else if (effect.equalsIgnoreCase("Bonus_Base_Attack_AoE_Radius") || effect.equalsIgnoreCase("Bonus_Attack_AoE_Radius")) {
                            baseAttackAoERadius = effectSection.getDouble(effect);
                        }
                        else if (effect.equalsIgnoreCase("Bonus_Scale_Attack_AoE_Radius")) {
                            scaleAttackAoERadius = effectSection.getDouble(effect);
                        }
                        else if (effect.equalsIgnoreCase("Bonus_Base_Attack_AoE_Damage") || effect.equalsIgnoreCase("Bonus_Attack_AoE_Damage")) {
                            baseAttackAoEDamage = effectSection.getDouble(effect);
                        }
                        else if (effect.equalsIgnoreCase("Bonus_Scale_Attack_AoE_Damage")) {
                            scaleAttackAoEDamage = effectSection.getDouble(effect);
                        }
                        else if (effect.equalsIgnoreCase("Bonus_Base_PvP_Defense") || effect.equalsIgnoreCase("Bonus_PvP_Defense")) {
                            basePvPDefense = effectSection.getDouble(effect);
                        }
                        else if (effect.equalsIgnoreCase("Bonus_Scale_PvP_Defense")) {
                            scalePvPDefense = effectSection.getDouble(effect);
                        }
                        else if (effect.equalsIgnoreCase("Bonus_Base_PvE_Defense") || effect.equalsIgnoreCase("Bonus_PvE_Defense")) {
                            basePvEDefense = effectSection.getDouble(effect);
                        }
                        else if (effect.equalsIgnoreCase("Bonus_Scale_PvE_Defense")) {
                            scalePvEDefense = effectSection.getDouble(effect);
                        }
                        else if (effect.equalsIgnoreCase("Bonus_Base_Critical_Chance") || effect.equalsIgnoreCase("Bonus_Critical_Chance")) {
                            baseCriticalChance = effectSection.getDouble(effect);
                        }
                        else if (effect.equalsIgnoreCase("Bonus_Scale_Critical_Chance")) {
                            scaleCriticalChance = effectSection.getDouble(effect);
                        }
                        else if (effect.equalsIgnoreCase("Bonus_Base_Critical_Damage") || effect.equalsIgnoreCase("Bonus_Critical_Damage")) {
                            baseCriticalDamage = effectSection.getDouble(effect);
                        }
                        else if (effect.equalsIgnoreCase("Bonus_Scale_Critical_Damage")) {
                            scaleCriticalDamage = effectSection.getDouble(effect);
                        }
                        else if (effect.equalsIgnoreCase("Bonus_Base_Block_Amount") || effect.equalsIgnoreCase("Bonus_Block_Amount")) {
                            baseBlockAmount = effectSection.getDouble(effect);
                        }
                        else if (effect.equalsIgnoreCase("Bonus_Scale_Block_Amount")) {
                            scaleBlockAmount = effectSection.getDouble(effect);
                        }
                        else if (effect.equalsIgnoreCase("Bonus_Base_Block_Rate") || effect.equalsIgnoreCase("Bonus_Block_Rate")) {
                            baseBlockRate = effectSection.getDouble(effect);
                        }
                        else if (effect.equalsIgnoreCase("Bonus_Scale_Block_Rate")) {
                            scaleBlockRate = effectSection.getDouble(effect);
                        }
                        else if (effect.equalsIgnoreCase("Bonus_Base_Hit_Rate") || effect.equalsIgnoreCase("Bonus_Hit_Rate")) {
                            baseHitRate = effectSection.getDouble(effect);
                        }
                        else if (effect.equalsIgnoreCase("Bonus_Scale_Hit_Rate")) {
                            scaleHitRate = effectSection.getDouble(effect);
                        }
                        else if (effect.equalsIgnoreCase("Bonus_Base_Dodge_Rate") || effect.equalsIgnoreCase("Bonus_Dodge_Rate")) {
                            baseDodgeRate = effectSection.getDouble(effect);
                        }
                        else {
                            if (!effect.equalsIgnoreCase("Bonus_Scale_Dodge_Rate")) {
                                continue;
                            }
                            scaleDodgeRate = effectSection.getDouble(effect);
                        }
                    }
                }
            }
            if (material != null && keyLore != null) {
                final MaterialEnum materialEnum = MaterialEnum.getMaterialEnum(material, (int)data);
                if (materialEnum == null) {
                    continue;
                }
                final HashMap<Integer, SocketGems> mapSocket = new HashMap<Integer, SocketGems>();
                final HashMap<String, String> map = new HashMap<String, String>();
                String typeItemName = null;
                switch (typeItem) {
                    case WEAPON: {
                        typeItemName = mainConfig.getSocketTypeItemWeapon();
                        break;
                    }
                    case ARMOR: {
                        typeItemName = mainConfig.getSocketTypeItemArmor();
                        break;
                    }
                    case UNIVERSAL: {
                        typeItemName = mainConfig.getSocketTypeItemUniversal();
                        break;
                    }
                    default: {
                        typeItemName = "Unknown";
                        break;
                    }
                }
                for (int grade2 = 1; grade2 <= maxGrade; ++grade2) {
                    final double successRate = MathUtil.limitDouble(baseSuccessRate + (grade2 - 1) * scaleSuccessRate, 0.0, 100.0);
                    final double failureRate = 100.0 - successRate;
                    final double additionalDamage = baseAdditionalDamage + (grade2 - 1) * scaleAdditionalDamage;
                    final double percentDamage = basePercentDamage + (grade2 - 1) * scalePercentDamage;
                    final double penetration = basePenetration + (grade2 - 1) * scalePenetration;
                    final double pvpDamage = basePvPDamage + (grade2 - 1) * scalePvPDamage;
                    final double pveDamage = basePvEDamage + (grade2 - 1) * scalePvEDamage;
                    final double additionalDefense = baseAdditionalDefense + (grade2 - 1) * scaleAdditionalDefense;
                    final double percentDefense = basePercentDefense + (grade2 - 1) * scalePercentDefense;
                    final double maxHealth = baseMaxHealth + (grade2 - 1) * scaleMaxHealth;
                    final double healthRegen = baseHealthRegen + (grade2 - 1) * scaleHealthRegen;
                    final double staminaMax = baseStaminaMax + (grade2 - 1) * scaleStaminaMax;
                    final double staminaRegen = baseStaminaRegen + (grade2 - 1) * scaleStaminaRegen;
                    final double attackAoERadius = baseAttackAoERadius + (grade2 - 1) * scaleAttackAoERadius;
                    final double attackAoEDamage = baseAttackAoEDamage + (grade2 - 1) * scaleAttackAoEDamage;
                    final double pvpDefense = basePvPDefense + (grade2 - 1) * scalePvPDefense;
                    final double pveDefense = basePvEDefense + (grade2 - 1) * scalePvEDefense;
                    final double criticalChance = baseCriticalChance + (grade2 - 1) * scaleCriticalChance;
                    final double criticalDamage = baseCriticalDamage + (grade2 - 1) * scaleCriticalDamage;
                    final double blockAmount = baseBlockAmount + (grade2 - 1) * scaleBlockAmount;
                    final double blockRate = baseBlockRate + (grade2 - 1) * scaleBlockRate;
                    final double hitRate = baseHitRate + (grade2 - 1) * scaleHitRate;
                    final double dodgeRate = baseDodgeRate + (grade2 - 1) * scaleDodgeRate;
                    map.clear();
                    map.put("gems", key);
                    map.put("grade", RomanNumber.getRomanNumber(grade2));
                    map.put("type_item", typeItemName);
                    map.put("success_rate", String.valueOf(MathUtil.roundNumber(successRate)));
                    map.put("failure_rate", String.valueOf(MathUtil.roundNumber(failureRate)));
                    map.put("additional_damage", String.valueOf(MathUtil.roundNumber(additionalDamage)));
                    map.put("percent_damage", String.valueOf(MathUtil.roundNumber(percentDamage)));
                    map.put("penetration", String.valueOf(MathUtil.roundNumber(penetration)));
                    map.put("pvp_damage", String.valueOf(MathUtil.roundNumber(pvpDamage)));
                    map.put("pve_damage", String.valueOf(MathUtil.roundNumber(pveDamage)));
                    map.put("additional_defense", String.valueOf(MathUtil.roundNumber(additionalDefense)));
                    map.put("percent_defense", String.valueOf(MathUtil.roundNumber(percentDefense)));
                    map.put("max_health", String.valueOf(MathUtil.roundNumber(maxHealth)));
                    map.put("health_max", String.valueOf(MathUtil.roundNumber(maxHealth)));
                    map.put("health_regen", String.valueOf(MathUtil.roundNumber(healthRegen)));
                    map.put("max_stamina", String.valueOf(MathUtil.roundNumber(staminaMax)));
                    map.put("stamina_max", String.valueOf(MathUtil.roundNumber(staminaMax)));
                    map.put("stamina_regen", String.valueOf(MathUtil.roundNumber(staminaRegen)));
                    map.put("attack_aoe_radius", String.valueOf(MathUtil.roundNumber(attackAoERadius)));
                    map.put("attack_aoe_damage", String.valueOf(MathUtil.roundNumber(attackAoEDamage)));
                    map.put("pvp_defense", String.valueOf(MathUtil.roundNumber(pvpDefense)));
                    map.put("pve_defense", String.valueOf(MathUtil.roundNumber(pveDefense)));
                    map.put("critical_chance", String.valueOf(MathUtil.roundNumber(criticalChance)));
                    map.put("critical_damage", String.valueOf(MathUtil.roundNumber(criticalDamage)));
                    map.put("block_amount", String.valueOf(MathUtil.roundNumber(blockAmount)));
                    map.put("block_rate", String.valueOf(MathUtil.roundNumber(blockRate)));
                    map.put("hit_rate", String.valueOf(MathUtil.roundNumber(hitRate)));
                    map.put("dodge_rate", String.valueOf(MathUtil.roundNumber(dodgeRate)));
                    final String itemDisplay = TextUtil.placeholder((HashMap)map, display);
                    final String itemKeyLore = TextUtil.placeholder((HashMap)map, keyLore);
                    final ItemStack item = EquipmentUtil.createItem(materialEnum, itemDisplay, 1);
                    final List<String> itemLores = (List<String>)TextUtil.placeholder((HashMap)map, (List)lores);
                    if (shiny) {
                        EquipmentUtil.shiny(item);
                    }
                    if (unbreakable) {
                        Bridge.getBridgeTagsNBT().setUnbreakable(item, true);
                    }
                    if (!itemLores.isEmpty()) {
                        EquipmentUtil.setLores(item, (List)itemLores);
                    }
                    if (!flags.isEmpty()) {
                        for (final String flag : flags) {
                            EquipmentUtil.addFlag(item, new String[] { flag });
                        }
                    }
                    if (!enchantments.isEmpty()) {
                        for (final Enchantment enchantment2 : enchantments.keySet()) {
                            final int enchantmentGrade = enchantments.get(enchantment2);
                            EquipmentUtil.addEnchantment(item, enchantment2, enchantmentGrade);
                        }
                    }
                    EquipmentUtil.hookPlaceholderAPI(item);
                    EquipmentUtil.colorful(item);
                    if (EquipmentUtil.isSolid(item)) {
                        final SocketGemsProperties socketProperties = new SocketGemsProperties(additionalDamage, percentDamage, penetration, pvpDamage, pveDamage, additionalDefense, percentDefense, maxHealth, healthRegen, staminaMax, staminaRegen, attackAoERadius, attackAoEDamage, pvpDefense, pveDefense, criticalChance, criticalDamage, blockAmount, blockRate, hitRate, dodgeRate);
                        final SocketGems build = new SocketGems(item, itemKeyLore, key, grade2, successRate, typeItem, socketProperties);
                        mapSocket.put(grade2, build);
                    }
                }
                final SocketGemsTree socketTree = new SocketGemsTree(key, maxGrade, typeItem, mapSocket);
                this.mapSocketTree.put(key, socketTree);
            }
        }
    }
    
    private final void moveOldFile() {
        final PluginManager pluginManager = this.plugin.getPluginManager();
        final DataManager dataManager = pluginManager.getDataManager();
        final String pathSource = "socket.yml";
        final String pathTarget = dataManager.getPath("Path_File_Socket");
        final File fileSource = FileUtil.getFile((JavaPlugin)this.plugin, "socket.yml");
        final File fileTarget = FileUtil.getFile((JavaPlugin)this.plugin, pathTarget);
        if (fileSource.exists()) {
            FileUtil.moveFileSilent(fileSource, fileTarget);
        }
    }
}
