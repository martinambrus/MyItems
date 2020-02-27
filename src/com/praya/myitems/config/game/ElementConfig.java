// 
// Decompiled by Procyon v0.5.36
// 

package com.praya.myitems.config.game;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import java.io.File;
import com.praya.myitems.manager.plugin.DataManager;
import com.praya.myitems.manager.plugin.PluginManager;
import api.praya.myitems.builder.element.ElementPotion;
import api.praya.myitems.builder.element.ElementBoost;
import com.praya.agarthalib.utility.MathUtil;
import com.praya.agarthalib.utility.PotionUtil;
import com.praya.agarthalib.utility.TextUtil;
import api.praya.myitems.builder.potion.PotionProperties;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.plugin.java.JavaPlugin;
import com.praya.agarthalib.utility.FileUtil;
import java.util.Iterator;
import java.util.Collection;
import com.praya.myitems.MyItems;
import api.praya.myitems.builder.element.Element;
import java.util.HashMap;
import com.praya.myitems.builder.handler.HandlerConfig;

public class ElementConfig extends HandlerConfig
{
    private final HashMap<String, Element> mapElement;
    
    public ElementConfig(final MyItems plugin) {
        super(plugin);
        this.mapElement = new HashMap<String, Element>();
    }
    
    public final Collection<String> getElements() {
        return this.mapElement.keySet();
    }
    
    public final Collection<Element> getElementBuilds() {
        return this.mapElement.values();
    }
    
    public final Element getElementBuild(final String element) {
        for (final String key : this.getElements()) {
            if (key.equalsIgnoreCase(element)) {
                return this.mapElement.get(key);
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
        this.mapElement.clear();
    }
    
    private final void loadConfig() {
        final PluginManager pluginManager = this.plugin.getPluginManager();
        final DataManager dataManager = pluginManager.getDataManager();
        final String path = dataManager.getPath("Path_File_Element");
        final File file = FileUtil.getFile((JavaPlugin)this.plugin, path);
        if (!file.exists()) {
            FileUtil.saveResource((JavaPlugin)this.plugin, path);
        }
        final FileConfiguration config = FileUtil.getFileConfiguration(file);
        for (final String key : config.getKeys(false)) {
            final ConfigurationSection section = config.getConfigurationSection(key);
            final HashMap<String, Double> resistance = new HashMap<String, Double>();
            final HashMap<PotionEffectType, PotionProperties> potionAttacker = new HashMap<PotionEffectType, PotionProperties>();
            final HashMap<PotionEffectType, PotionProperties> potionVictims = new HashMap<PotionEffectType, PotionProperties>();
            String keyLore = null;
            double scaleAdditionalDamage = 0.0;
            double scalePercentDamage = 0.0;
            for (final String keySection : section.getKeys(false)) {
                if (keySection.equalsIgnoreCase("Keylore")) {
                    keyLore = TextUtil.colorful(section.getString(keySection));
                }
                else if (keySection.equalsIgnoreCase("Scale_Bonus_Additional_Damage") || keySection.equalsIgnoreCase("Scale_Base_Additional_Damage")) {
                    scaleAdditionalDamage = section.getDouble(keySection);
                }
                else if (keySection.equalsIgnoreCase("Scale_Bonus_Percent_Damage") || keySection.equalsIgnoreCase("Scale_Base_Percent_Damage")) {
                    scalePercentDamage = section.getDouble(keySection);
                }
                else if (keySection.equalsIgnoreCase("Resistance")) {
                    final ConfigurationSection resistanceSection = section.getConfigurationSection(keySection);
                    for (final String keyResistance : resistanceSection.getKeys(false)) {
                        resistance.put(keyResistance, resistanceSection.getDouble(keyResistance));
                    }
                }
                else {
                    if (!keySection.equalsIgnoreCase("Potion_To_Attacker") && !keySection.equalsIgnoreCase("Potion_To_Attackers") && !keySection.equalsIgnoreCase("Potion_To_Victim") && !keySection.equalsIgnoreCase("Potion_To_Victims")) {
                        continue;
                    }
                    final ConfigurationSection potionSection = section.getConfigurationSection(keySection);
                    for (final String keyPotion : potionSection.getKeys(false)) {
                        final PotionEffectType potion = PotionUtil.getPotionEffectType(keyPotion);
                        if (potion != null) {
                            final ConfigurationSection attributePotionSection = potionSection.getConfigurationSection(keyPotion);
                            int potionGrade = 1;
                            double potionScaleChance = 0.0;
                            double potionScaleDuration = 1.0;
                            for (final String keyAttribute : attributePotionSection.getKeys(false)) {
                                if (keyAttribute.equalsIgnoreCase("Grade")) {
                                    potionGrade = attributePotionSection.getInt(keyAttribute);
                                }
                                else if (keyAttribute.equalsIgnoreCase("Scale_Chance")) {
                                    potionScaleChance = attributePotionSection.getDouble(keyAttribute);
                                }
                                else {
                                    if (!keyAttribute.equalsIgnoreCase("Scale_Duration")) {
                                        continue;
                                    }
                                    potionScaleDuration = attributePotionSection.getDouble(keyAttribute);
                                }
                            }
                            potionGrade = MathUtil.limitInteger(potionGrade, 1, 10);
                            potionScaleChance = MathUtil.limitDouble(potionScaleChance, 0.01, 100.0);
                            potionScaleDuration = MathUtil.limitDouble(potionScaleDuration, 0.1, 100.0);
                            final PotionProperties potionAttributes = new PotionProperties(potionGrade, potionScaleChance, potionScaleDuration);
                            if (keySection.equalsIgnoreCase("Potion_To_Attacker") || keySection.equalsIgnoreCase("Potion_To_Attackers")) {
                                potionAttacker.put(potion, potionAttributes);
                            }
                            else {
                                if (!keySection.equalsIgnoreCase("Potion_To_Victim") && !keySection.equalsIgnoreCase("Potion_To_Victims")) {
                                    continue;
                                }
                                potionVictims.put(potion, potionAttributes);
                            }
                        }
                    }
                }
            }
            if (keyLore != null) {
                final ElementBoost boostBuild = new ElementBoost(scaleAdditionalDamage, scalePercentDamage);
                final ElementPotion potionBuild = new ElementPotion(potionAttacker, potionVictims);
                final Element build = new Element(keyLore, boostBuild, potionBuild, resistance);
                this.mapElement.put(key, build);
            }
        }
    }
    
    private final void moveOldFile() {
        final PluginManager pluginManager = this.plugin.getPluginManager();
        final DataManager dataManager = pluginManager.getDataManager();
        final String pathSource = "element.yml";
        final String pathTarget = dataManager.getPath("Path_File_Element");
        final File fileSource = FileUtil.getFile((JavaPlugin)this.plugin, "element.yml");
        final File fileTarget = FileUtil.getFile((JavaPlugin)this.plugin, pathTarget);
        if (fileSource.exists()) {
            FileUtil.moveFileSilent(fileSource, fileTarget);
        }
    }
}
