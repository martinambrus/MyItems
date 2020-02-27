// 
// Decompiled by Procyon v0.5.36
// 

package com.praya.myitems.manager.plugin;

import java.util.ArrayList;
import java.util.List;
import core.praya.agarthalib.builder.main.DataBuild;
import java.util.Collection;
import com.praya.myitems.MyItems;
import com.praya.myitems.config.plugin.DataConfig;
import com.praya.myitems.builder.handler.HandlerManager;

public class DataManager extends HandlerManager
{
    private final DataConfig dataConfig;
    
    protected DataManager(final MyItems plugin) {
        super(plugin);
        this.dataConfig = new DataConfig(plugin);
    }
    
    public final DataConfig getDataConfig() {
        return this.dataConfig;
    }
    
    public final Collection<String> getDataIDs() {
        return this.getDataConfig().getDataIDs();
    }
    
    public final Collection<DataBuild> getDataBuilds() {
        return this.getDataConfig().getDataBuilds();
    }
    
    public final DataBuild getData(final String id) {
        return this.getDataConfig().getData(id);
    }
    
    public final boolean isDataExists(final String id) {
        return this.getData(id) != null;
    }
    
    public final List<String> getListPath(final String id) {
        final DataBuild data = this.getData(id);
        return (data != null) ? data.getListPath() : new ArrayList<String>();
    }
    
    public final String getPath(final String id) {
        final DataBuild data = this.getData(id);
        return (data != null) ? data.getPath() : "";
    }
}
