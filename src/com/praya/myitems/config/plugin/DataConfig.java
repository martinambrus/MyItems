// 
// Decompiled by Procyon v0.5.36
// 

package com.praya.myitems.config.plugin;

import org.bukkit.configuration.file.FileConfiguration;
import java.util.List;
import java.util.ArrayList;
import org.bukkit.plugin.java.JavaPlugin;
import com.praya.agarthalib.utility.FileUtil;
import java.util.Iterator;
import java.util.Collection;
import com.praya.myitems.MyItems;
import core.praya.agarthalib.builder.main.DataBuild;
import java.util.HashMap;
import com.praya.myitems.builder.handler.HandlerConfig;

public class DataConfig extends HandlerConfig
{
    private final HashMap<String, DataBuild> mapData;
    
    public DataConfig(final MyItems plugin) {
        super(plugin);
        this.mapData = new HashMap<String, DataBuild>();
        this.setup();
    }
    
    public final Collection<String> getDataIDs() {
        return this.mapData.keySet();
    }
    
    public final Collection<DataBuild> getDataBuilds() {
        return this.mapData.values();
    }
    
    public final DataBuild getData(final String id) {
        if (id != null) {
            for (final String key : this.getDataIDs()) {
                if (key.equalsIgnoreCase(id)) {
                    return this.mapData.get(key);
                }
            }
        }
        return null;
    }
    
    public final void setup() {
        this.reset();
        this.loadConfig();
    }
    
    private final void reset() {
        this.mapData.clear();
    }
    
    private final void loadConfig() {
        final FileConfiguration config = FileUtil.getFileConfigurationResource((JavaPlugin)this.plugin, "Resources/data.yml");
        for (final String path : config.getKeys(true)) {
            final String key = path.replace(".", "_");
            if (config.isString(path)) {
                final String text = config.getString(path);
                final List<String> list = new ArrayList<String>();
                list.add(text);
                final DataBuild dataBuild = new DataBuild(key, (List)list);
                this.mapData.put(key, dataBuild);
            }
            else {
                if (!config.isList(path)) {
                    continue;
                }
                final List<String> list2 = (List<String>)config.getStringList(path);
                final DataBuild dataBuild2 = new DataBuild(key, (List)list2);
                this.mapData.put(key, dataBuild2);
            }
        }
    }
}
