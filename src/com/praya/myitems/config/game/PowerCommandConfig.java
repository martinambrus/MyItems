// 
// Decompiled by Procyon v0.5.36
// 

package com.praya.myitems.config.game;

import java.util.List;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import java.io.File;
import com.praya.myitems.manager.plugin.DataManager;
import com.praya.myitems.manager.plugin.PluginManager;
import com.praya.agarthalib.utility.TextUtil;
import java.util.ArrayList;
import org.bukkit.plugin.java.JavaPlugin;
import com.praya.agarthalib.utility.FileUtil;
import java.util.Iterator;
import java.util.Collection;
import com.praya.myitems.MyItems;
import api.praya.myitems.builder.power.PowerCommandProperties;
import java.util.HashMap;
import com.praya.myitems.builder.handler.HandlerConfig;

public class PowerCommandConfig extends HandlerConfig
{
    private final HashMap<String, PowerCommandProperties> mapPowerCommand;
    
    public PowerCommandConfig(final MyItems plugin) {
        super(plugin);
        this.mapPowerCommand = new HashMap<String, PowerCommandProperties>();
    }
    
    public final Collection<String> getPowerCommandIDs() {
        return this.mapPowerCommand.keySet();
    }
    
    public final Collection<PowerCommandProperties> getPowerCommandPropertyBuilds() {
        return this.mapPowerCommand.values();
    }
    
    public final PowerCommandProperties getPowerCommandProperties(final String id) {
        for (final String key : this.getPowerCommandIDs()) {
            if (key.equalsIgnoreCase(id)) {
                return this.mapPowerCommand.get(key);
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
        this.mapPowerCommand.clear();
    }
    
    public final void loadConfig() {
        final PluginManager pluginManager = this.plugin.getPluginManager();
        final DataManager dataManager = pluginManager.getDataManager();
        final String path = dataManager.getPath("path_File_Power_Command");
        final File file = FileUtil.getFile((JavaPlugin)this.plugin, path);
        if (!file.exists()) {
            FileUtil.saveResource((JavaPlugin)this.plugin, path);
        }
        final FileConfiguration config = FileUtil.getFileConfiguration(file);
        for (final String key : config.getKeys(false)) {
            final ConfigurationSection mainDataSection = config.getConfigurationSection(key);
            final List<String> commandOP = new ArrayList<String>();
            final List<String> commandConsole = new ArrayList<String>();
            String keyLore = null;
            boolean consume = false;
            for (final String mainData : mainDataSection.getKeys(false)) {
                if (mainData.equalsIgnoreCase("KeyLore")) {
                    keyLore = TextUtil.colorful(mainDataSection.getString(mainData));
                }
                else if (mainData.equalsIgnoreCase("Consume")) {
                    consume = mainDataSection.getBoolean(mainData);
                }
                else {
                    if (!mainData.equalsIgnoreCase("command")) {
                        continue;
                    }
                    if (mainDataSection.isList(mainData)) {
                        commandOP.addAll(mainDataSection.getStringList(mainData));
                    }
                    else if (mainDataSection.isString(mainData)) {
                        commandOP.add(mainDataSection.getString(mainData));
                    }
                    else {
                        if (!mainDataSection.isConfigurationSection(mainData)) {
                            continue;
                        }
                        final ConfigurationSection commandDataSection = mainDataSection.getConfigurationSection(mainData);
                        for (final String commandData : commandDataSection.getKeys(false)) {
                            if (commandData.equalsIgnoreCase("OP")) {
                                if (commandDataSection.isList(commandData)) {
                                    commandOP.addAll(commandDataSection.getStringList(commandData));
                                }
                                else {
                                    if (!commandDataSection.isString(commandData)) {
                                        continue;
                                    }
                                    commandOP.add(commandDataSection.getString(commandData));
                                }
                            }
                            else {
                                if (!commandData.equalsIgnoreCase("Console")) {
                                    continue;
                                }
                                if (commandDataSection.isList(commandData)) {
                                    commandConsole.addAll(commandDataSection.getStringList(commandData));
                                }
                                else {
                                    if (!commandDataSection.isString(commandData)) {
                                        continue;
                                    }
                                    commandConsole.add(commandDataSection.getString(commandData));
                                }
                            }
                        }
                    }
                }
            }
            if (keyLore != null & (!commandOP.isEmpty() || !commandConsole.isEmpty())) {
                final PowerCommandProperties powerCommandProperties = new PowerCommandProperties(keyLore, consume, commandOP, commandConsole);
                this.mapPowerCommand.put(key, powerCommandProperties);
            }
        }
    }
    
    private final void moveOldFile() {
        final PluginManager pluginManager = this.plugin.getPluginManager();
        final DataManager dataManager = pluginManager.getDataManager();
        final String pathSource = "command.yml";
        final String pathTarget = dataManager.getPath("path_File_Power_Command");
        final File fileSource = FileUtil.getFile((JavaPlugin)this.plugin, "command.yml");
        final File fileTarget = FileUtil.getFile((JavaPlugin)this.plugin, pathTarget);
        if (fileSource.exists()) {
            FileUtil.moveFileSilent(fileSource, fileTarget);
        }
    }
}
