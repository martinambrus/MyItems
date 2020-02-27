// 
// Decompiled by Procyon v0.5.36
// 

package com.praya.myitems.config.game;

import java.util.List;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import java.io.File;
import com.praya.myitems.manager.game.ItemTierManager;
import com.praya.myitems.manager.game.ItemTypeManager;
import com.praya.myitems.manager.plugin.DataManager;
import com.praya.myitems.manager.game.GameManager;
import com.praya.myitems.manager.plugin.PluginManager;
import com.praya.agarthalib.utility.TextUtil;
import api.praya.myitems.builder.item.ItemGeneratorTier;
import api.praya.myitems.builder.item.ItemTier;
import api.praya.myitems.builder.item.ItemGeneratorType;
import api.praya.myitems.builder.item.ItemType;
import java.util.ArrayList;
import org.bukkit.plugin.java.JavaPlugin;
import com.praya.agarthalib.utility.FileUtil;
import java.util.Iterator;
import java.util.Collection;
import com.praya.myitems.MyItems;
import api.praya.myitems.builder.item.ItemGenerator;
import java.util.HashMap;
import com.praya.myitems.builder.handler.HandlerConfig;

public class ItemGeneratorConfig extends HandlerConfig
{
    private final HashMap<String, ItemGenerator> mapItemGenerator;
    
    public ItemGeneratorConfig(final MyItems plugin) {
        super(plugin);
        this.mapItemGenerator = new HashMap<String, ItemGenerator>();
    }
    
    public final Collection<String> getItemGeneratorIDs() {
        return this.mapItemGenerator.keySet();
    }
    
    public final Collection<ItemGenerator> getItemGenerators() {
        return this.mapItemGenerator.values();
    }
    
    public final ItemGenerator getItemGenerator(final String nameid) {
        for (final String key : this.getItemGeneratorIDs()) {
            if (key.equalsIgnoreCase(nameid)) {
                return this.mapItemGenerator.get(key);
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
        this.mapItemGenerator.clear();
    }
    
    private final void loadConfig() {
        final PluginManager pluginManager = this.plugin.getPluginManager();
        final GameManager gameManager = this.plugin.getGameManager();
        final DataManager dataManager = pluginManager.getDataManager();
        final ItemTypeManager itemTypeManager = gameManager.getItemTypeManager();
        final ItemTierManager itemTierManager = gameManager.getItemTierManager();
        final String pathDefault = dataManager.getPath("Path_File_Item_Generator");
        final String pathFolder = dataManager.getPath("Path_Folder_Item_Generator");
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
                final ConfigurationSection dataSection = config.getConfigurationSection(key);
                final List<String> lores = new ArrayList<String>();
                final List<String> flags = new ArrayList<String>();
                final HashMap<ItemType, ItemGeneratorType> mapType = new HashMap<ItemType, ItemGeneratorType>();
                final HashMap<ItemTier, ItemGeneratorTier> mapTier = new HashMap<ItemTier, ItemGeneratorTier>();
                String displayName = null;
                boolean unbreakable = false;
                for (final String data : dataSection.getKeys(false)) {
                    if (data.equalsIgnoreCase("Display_Name")) {
                        displayName = TextUtil.colorful(dataSection.getString(data));
                    }
                    else if (data.equalsIgnoreCase("Unbreakable")) {
                        unbreakable = dataSection.getBoolean(data);
                    }
                    else if (data.equalsIgnoreCase("Flags") || data.equalsIgnoreCase("ItemFlags")) {
                        flags.addAll(dataSection.getStringList(data));
                    }
                    else if (data.equalsIgnoreCase("Lores")) {
                        lores.addAll(dataSection.getStringList(data));
                    }
                    else if (data.equalsIgnoreCase("Type")) {
                        final ConfigurationSection typeSection = dataSection.getConfigurationSection(data);
                        for (final String type : typeSection.getKeys(false)) {
                            final ItemType itemType = itemTypeManager.getItemType(type);
                            if (itemType != null) {
                                final ConfigurationSection typePropertiesSection = typeSection.getConfigurationSection(type);
                                final List<String> description = new ArrayList<String>();
                                final List<String> names = new ArrayList<String>();
                                int possibility = 1;
                                for (final String typeProperties : typePropertiesSection.getKeys(false)) {
                                    if (typeProperties.equalsIgnoreCase("Possibility")) {
                                        possibility = typePropertiesSection.getInt(typeProperties);
                                    }
                                    else if (typeProperties.equalsIgnoreCase("Description")) {
                                        for (final String desc : typePropertiesSection.getStringList(typeProperties)) {
                                            description.add(desc);
                                        }
                                    }
                                    else {
                                        if (!typeProperties.equalsIgnoreCase("Name") && !typeProperties.equalsIgnoreCase("Names")) {
                                            continue;
                                        }
                                        for (final String name : typePropertiesSection.getStringList(typeProperties)) {
                                            names.add(name);
                                        }
                                    }
                                }
                                final ItemGeneratorType itemTypeProperties = new ItemGeneratorType(possibility, description, names);
                                mapType.put(itemType, itemTypeProperties);
                            }
                        }
                    }
                    else {
                        if (!data.equalsIgnoreCase("Tier")) {
                            continue;
                        }
                        final ConfigurationSection tierSection = dataSection.getConfigurationSection(data);
                        for (final String tier : tierSection.getKeys(false)) {
                            final ItemTier itemTier = itemTierManager.getItemTier(tier);
                            if (itemTier != null) {
                                final ConfigurationSection tierPropertiesSection = tierSection.getConfigurationSection(tier);
                                final List<String> additionalLores = new ArrayList<String>();
                                int possibility2 = 1;
                                for (final String tierProperties : tierPropertiesSection.getKeys(false)) {
                                    if (tierProperties.equalsIgnoreCase("Possibility")) {
                                        possibility2 = tierPropertiesSection.getInt(tierProperties);
                                    }
                                    else {
                                        if (!tierProperties.equalsIgnoreCase("Additional_Lores")) {
                                            continue;
                                        }
                                        for (final String additionalLore : tierPropertiesSection.getStringList(tierProperties)) {
                                            additionalLores.add(additionalLore);
                                        }
                                    }
                                }
                                final ItemGeneratorTier itemTierProperties = new ItemGeneratorTier(possibility2, additionalLores);
                                mapTier.put(itemTier, itemTierProperties);
                            }
                        }
                    }
                }
                if (displayName != null) {
                    final ItemGenerator itemGenerator = new ItemGenerator(key, displayName, unbreakable, lores, flags, mapType, mapTier);
                    this.mapItemGenerator.put(key, itemGenerator);
                }
            }
        }
    }
    
    private final void moveOldFile() {
        final PluginManager pluginManager = this.plugin.getPluginManager();
        final DataManager dataManager = pluginManager.getDataManager();
        final String pathSource_1 = "item_generator.yml";
        final String pathSource_2 = "Configuration/item_generator.yml";
        final String pathTarget = dataManager.getPath("Path_File_Item_Generator");
        final File fileSource_1 = FileUtil.getFile((JavaPlugin)this.plugin, "item_generator.yml");
        final File fileSource_2 = FileUtil.getFile((JavaPlugin)this.plugin, "Configuration/item_generator.yml");
        final File fileTarget = FileUtil.getFile((JavaPlugin)this.plugin, pathTarget);
        if (fileSource_1.exists()) {
            FileUtil.moveFileSilent(fileSource_1, fileTarget);
        }
        else if (fileSource_2.exists()) {
            FileUtil.moveFileSilent(fileSource_2, fileTarget);
        }
    }
}
