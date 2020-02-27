// 
// Decompiled by Procyon v0.5.36
// 

package com.praya.myitems.config.game;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import java.io.File;
import com.praya.myitems.manager.plugin.DataManager;
import com.praya.myitems.manager.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import com.praya.agarthalib.utility.FileUtil;
import java.util.Iterator;
import java.util.Collection;
import com.praya.myitems.MyItems;
import api.praya.myitems.builder.ability.AbilityWeaponProperties;
import java.util.HashMap;
import com.praya.myitems.builder.handler.HandlerConfig;

public class AbilityWeaponConfig extends HandlerConfig
{
    private final HashMap<String, AbilityWeaponProperties> mapAbilityWeaponProperties;
    
    public AbilityWeaponConfig(final MyItems plugin) {
        super(plugin);
        this.mapAbilityWeaponProperties = new HashMap<String, AbilityWeaponProperties>();
    }
    
    public final Collection<String> getAbilityWeaponPropertiesIDs() {
        return this.mapAbilityWeaponProperties.keySet();
    }
    
    public final Collection<AbilityWeaponProperties> getAllAbilityWeaponProperties() {
        return this.mapAbilityWeaponProperties.values();
    }
    
    public final AbilityWeaponProperties getAbilityWeaponProperties(final String ability) {
        if (ability != null) {
            for (final String key : this.getAbilityWeaponPropertiesIDs()) {
                if (key.equalsIgnoreCase(ability)) {
                    return this.mapAbilityWeaponProperties.get(key);
                }
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
        this.mapAbilityWeaponProperties.clear();
    }
    
    private final void loadConfig() {
        final PluginManager pluginManager = this.plugin.getPluginManager();
        final DataManager dataManager = pluginManager.getDataManager();
        final String path = dataManager.getPath("Path_File_Ability_Weapon");
        final File file = FileUtil.getFile((JavaPlugin)this.plugin, path);
        if (!file.exists()) {
            FileUtil.saveResource((JavaPlugin)this.plugin, path);
        }
        for (int t = 0; t < 2; ++t) {
            FileConfiguration config;
            if (t == 0) {
                config = FileUtil.getFileConfigurationResource((JavaPlugin)this.plugin, path);
            }
            else {
                config = FileUtil.getFileConfiguration(file);
            }
            for (final String key : config.getKeys(false)) {
                final ConfigurationSection mainDataSection = config.getConfigurationSection(key);
                int maxGrade = 1;
                int baseDurationEffect = 1;
                int scaleDurationEffect = 1;
                double scaleBaseBonusDamage = 0.0;
                double scaleBasePercentDamage = 0.0;
                double scaleCastBonusDamage = 0.0;
                double scaleCastPercentDamage = 0.0;
                for (final String keySection : mainDataSection.getKeys(false)) {
                    if (keySection.equalsIgnoreCase("Max_Grade")) {
                        maxGrade = mainDataSection.getInt(keySection);
                    }
                    else if (keySection.equalsIgnoreCase("Base_Duration_Effect")) {
                        baseDurationEffect = mainDataSection.getInt(keySection);
                    }
                    else if (keySection.equalsIgnoreCase("Scale_Duration_Effect")) {
                        scaleDurationEffect = mainDataSection.getInt(keySection);
                    }
                    else if (keySection.equalsIgnoreCase("Scale_Base_Bonus_Damage")) {
                        scaleBaseBonusDamage = mainDataSection.getDouble(keySection);
                    }
                    else if (keySection.equalsIgnoreCase("Scale_Base_Percent_Damage")) {
                        scaleBasePercentDamage = mainDataSection.getDouble(keySection);
                    }
                    else if (keySection.equalsIgnoreCase("Scale_Cast_Bonus_Damage")) {
                        scaleCastBonusDamage = mainDataSection.getDouble(keySection);
                    }
                    else {
                        if (!keySection.equalsIgnoreCase("Scale_Cast_Percent_Damage")) {
                            continue;
                        }
                        scaleCastPercentDamage = mainDataSection.getDouble(keySection);
                    }
                }
                maxGrade = Math.max(1, maxGrade);
                baseDurationEffect = Math.max(0, baseDurationEffect);
                scaleDurationEffect = Math.max(0, scaleDurationEffect);
                final AbilityWeaponProperties abilityWeaponProperties = new AbilityWeaponProperties(maxGrade, baseDurationEffect, scaleDurationEffect, scaleBaseBonusDamage, scaleBasePercentDamage, scaleCastBonusDamage, scaleCastPercentDamage);
                this.mapAbilityWeaponProperties.put(key, abilityWeaponProperties);
            }
        }
    }
    
    private final void moveOldFile() {
        final PluginManager pluginManager = this.plugin.getPluginManager();
        final DataManager dataManager = pluginManager.getDataManager();
        final String pathSource = "Configuration/ability.yml";
        final String pathTarget = dataManager.getPath("Path_File_Ability_Weapon");
        final File fileSource = FileUtil.getFile((JavaPlugin)this.plugin, "Configuration/ability.yml");
        final File fileTarget = FileUtil.getFile((JavaPlugin)this.plugin, pathTarget);
        if (fileSource.exists()) {
            FileUtil.moveFileSilent(fileSource, fileTarget);
        }
    }
}
