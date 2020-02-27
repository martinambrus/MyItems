// 
// Decompiled by Procyon v0.5.36
// 

package com.praya.myitems.config.plugin;

import java.util.Iterator;
import org.bukkit.configuration.file.FileConfiguration;
import com.praya.myitems.manager.plugin.DataManager;
import com.praya.myitems.manager.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import com.praya.agarthalib.utility.FileUtil;
import java.util.ArrayList;
import com.praya.myitems.MyItems;
import java.util.Collection;
import core.praya.agarthalib.enums.main.Dependency;
import java.util.HashMap;
import com.praya.myitems.builder.handler.HandlerConfig;

public class DependencyConfig extends HandlerConfig
{
    private final HashMap<Dependency, Collection<String>> mapDependency;
    
    public DependencyConfig(final MyItems plugin) {
        super(plugin);
        this.mapDependency = new HashMap<Dependency, Collection<String>>();
    }
    
    public final Collection<Dependency> getDependencyKeys() {
        return this.mapDependency.keySet();
    }
    
    public final Collection<String> getDependency(final Dependency type) {
        return this.mapDependency.containsKey(type) ? this.mapDependency.get(type) : new ArrayList<String>();
    }
    
    public final boolean hasDependency(final Dependency type) {
        return this.mapDependency.containsKey(type);
    }
    
    public final boolean hasAnyDependency() {
        return !this.mapDependency.isEmpty();
    }
    
    public final void setup() {
        this.reset();
        this.loadConfig();
    }
    
    private final void reset() {
        this.mapDependency.clear();
    }
    
    private final void loadConfig() {
        final PluginManager pluginManager = this.plugin.getPluginManager();
        final DataManager dataManager = pluginManager.getDataManager();
        final String path = dataManager.getPath("Path_File_Dependency");
        final FileConfiguration config = FileUtil.getFileConfigurationResource((JavaPlugin)this.plugin, path);
        for (final String key : config.getKeys(false)) {
            if (key.equalsIgnoreCase("Soft_Dependency") || key.equalsIgnoreCase("Soft_Dependencies")) {
                if (config.isString(key)) {
                    final Collection<String> dependencies = new ArrayList<String>();
                    dependencies.add(config.getString(key));
                    this.mapDependency.put(Dependency.SOFT_DEPENDENCY, dependencies);
                }
                else {
                    if (!config.isList(key)) {
                        continue;
                    }
                    this.mapDependency.put(Dependency.SOFT_DEPENDENCY, config.getStringList(key));
                }
            }
            else {
                if (!key.equalsIgnoreCase("Hard_Dependency") && !key.equalsIgnoreCase("Hard_Dependencies")) {
                    continue;
                }
                if (config.isString(key)) {
                    final Collection<String> dependencies = new ArrayList<String>();
                    dependencies.add(config.getString(key));
                    this.mapDependency.put(Dependency.HARD_DEPENDENCY, dependencies);
                }
                else {
                    if (!config.isList(key)) {
                        continue;
                    }
                    this.mapDependency.put(Dependency.HARD_DEPENDENCY, config.getStringList(key));
                }
            }
        }
    }
}
