// 
// Decompiled by Procyon v0.5.36
// 

package com.praya.myitems.config.plugin;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import java.io.File;
import com.praya.myitems.manager.plugin.DataManager;
import com.praya.myitems.manager.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import com.praya.agarthalib.utility.FileUtil;
import java.util.Map;
import java.util.Iterator;
import java.util.Collection;
import com.praya.myitems.MyItems;
import java.util.HashMap;
import com.praya.myitems.builder.handler.HandlerConfig;

public class PlaceholderConfig extends HandlerConfig
{
    private final HashMap<String, String> mapPlaceholder;
    
    public PlaceholderConfig(final MyItems plugin) {
        super(plugin);
        this.mapPlaceholder = new HashMap<String, String>();
        this.setup();
    }
    
    public final Collection<String> getPlaceholderIDs() {
        return this.mapPlaceholder.keySet();
    }
    
    public final Collection<String> getPlaceholders() {
        return this.mapPlaceholder.values();
    }
    
    public final String getPlaceholder(final String id) {
        for (final String key : this.getPlaceholderIDs()) {
            if (key.equalsIgnoreCase(id)) {
                return this.mapPlaceholder.get(key);
            }
        }
        return null;
    }
    
    public final HashMap<String, String> getPlaceholderCopy() {
        return new HashMap<String, String>(this.mapPlaceholder);
    }
    
    public final void setup() {
        this.moveOldFile();
        this.reset();
        this.loadConfig();
    }
    
    private final void reset() {
        this.mapPlaceholder.clear();
    }
    
    private final void loadConfig() {
        final PluginManager pluginManager = this.plugin.getPluginManager();
        final DataManager dataManager = pluginManager.getDataManager();
        final String path = dataManager.getPath("Path_File_Placeholder");
        final File file = FileUtil.getFile((JavaPlugin)this.plugin, path);
        if (!file.exists()) {
            FileUtil.saveResource((JavaPlugin)this.plugin, path);
        }
        for (int t = 0; t < 2; ++t) {
            final FileConfiguration config = (t == 0) ? FileUtil.getFileConfigurationResource((JavaPlugin)this.plugin, path) : FileUtil.getFileConfiguration(file);
            for (final String key : config.getKeys(false)) {
                if (key.equalsIgnoreCase("Placeholder")) {
                    final ConfigurationSection placeholderSection = config.getConfigurationSection(key);
                    for (final String placeholder : placeholderSection.getKeys(false)) {
                        this.mapPlaceholder.put(placeholder, placeholderSection.getString(placeholder));
                    }
                }
            }
        }
    }
    
    private final void moveOldFile() {
        final PluginManager pluginManager = this.plugin.getPluginManager();
        final DataManager dataManager = pluginManager.getDataManager();
        final String pathSource = "placeholder.yml";
        final String pathTarget = dataManager.getPath("Path_File_Placeholder");
        final File fileSource = FileUtil.getFile((JavaPlugin)this.plugin, "placeholder.yml");
        final File fileTarget = FileUtil.getFile((JavaPlugin)this.plugin, pathTarget);
        if (fileSource.exists()) {
            FileUtil.moveFileSilent(fileSource, fileTarget);
        }
    }
}
