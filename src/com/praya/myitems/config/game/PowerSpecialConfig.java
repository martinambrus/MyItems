// 
// Decompiled by Procyon v0.5.36
// 

package com.praya.myitems.config.game;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import java.io.File;
import com.praya.myitems.manager.plugin.DataManager;
import com.praya.myitems.manager.plugin.PluginManager;
import com.praya.agarthalib.utility.MathUtil;
import org.bukkit.plugin.java.JavaPlugin;
import com.praya.agarthalib.utility.FileUtil;
import java.util.Iterator;
import java.util.Collection;
import com.praya.myitems.MyItems;
import api.praya.myitems.builder.power.PowerSpecialProperties;
import api.praya.myitems.builder.power.PowerSpecialEnum;
import java.util.HashMap;
import com.praya.myitems.builder.handler.HandlerConfig;

public class PowerSpecialConfig extends HandlerConfig
{
    private final HashMap<PowerSpecialEnum, PowerSpecialProperties> mapPowerSpecial;
    
    public PowerSpecialConfig(final MyItems plugin) {
        super(plugin);
        this.mapPowerSpecial = new HashMap<PowerSpecialEnum, PowerSpecialProperties>();
    }
    
    public final Collection<PowerSpecialEnum> getPowerSpecialIDs() {
        return this.mapPowerSpecial.keySet();
    }
    
    public final Collection<PowerSpecialProperties> getPowerSpecialPropertyBuilds() {
        return this.mapPowerSpecial.values();
    }
    
    public final PowerSpecialProperties getPowerSpecialProperties(final PowerSpecialEnum id) {
        for (final PowerSpecialEnum key : this.getPowerSpecialIDs()) {
            if (key.equals(id)) {
                return this.mapPowerSpecial.get(key);
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
        this.mapPowerSpecial.clear();
    }
    
    private final void loadConfig() {
        final PluginManager pluginManager = this.plugin.getPluginManager();
        final DataManager dataManager = pluginManager.getDataManager();
        final String path = dataManager.getPath("Path_File_Power_Special");
        final File file = FileUtil.getFile((JavaPlugin)this.plugin, path);
        if (!file.exists()) {
            FileUtil.saveResource((JavaPlugin)this.plugin, path);
        }
        for (int t = 0; t < 2; ++t) {
            final FileConfiguration config = (t == 0) ? FileUtil.getFileConfigurationResource((JavaPlugin)this.plugin, path) : FileUtil.getFileConfiguration(file);
            for (final String key : config.getKeys(false)) {
                final PowerSpecialEnum special = PowerSpecialEnum.get(key);
                if (special != null) {
                    final ConfigurationSection section = config.getConfigurationSection(key);
                    int durationEffect = 1;
                    double baseAdditionalDamage = 0.0;
                    double basePercentDamage = 100.0;
                    for (final String keySection : section.getKeys(false)) {
                        if (keySection.equalsIgnoreCase("Duration_Effect")) {
                            durationEffect = section.getInt(keySection);
                        }
                        else if (keySection.equalsIgnoreCase("Base_Additional_Damage")) {
                            baseAdditionalDamage = section.getDouble(keySection);
                        }
                        else {
                            if (!keySection.equalsIgnoreCase("Base_Percent_Damage")) {
                                continue;
                            }
                            basePercentDamage = section.getDouble(keySection);
                        }
                    }
                    durationEffect = MathUtil.limitInteger(durationEffect, 1, durationEffect);
                    baseAdditionalDamage = MathUtil.limitDouble(baseAdditionalDamage, 0.0, baseAdditionalDamage);
                    basePercentDamage = MathUtil.limitDouble(basePercentDamage, 0.0, basePercentDamage);
                    final PowerSpecialProperties powerSpecialProperties = new PowerSpecialProperties(durationEffect, baseAdditionalDamage, basePercentDamage);
                    this.mapPowerSpecial.put(special, powerSpecialProperties);
                }
            }
        }
    }
    
    private final void moveOldFile() {
        final PluginManager pluginManager = this.plugin.getPluginManager();
        final DataManager dataManager = pluginManager.getDataManager();
        final String pathSource = "specialpower.yml";
        final String pathTarget = dataManager.getPath("Path_File_Power_Special");
        final File fileSource = FileUtil.getFile((JavaPlugin)this.plugin, "specialpower.yml");
        final File fileTarget = FileUtil.getFile((JavaPlugin)this.plugin, pathTarget);
        if (fileSource.exists()) {
            FileUtil.moveFileSilent(fileSource, fileTarget);
        }
    }
}
