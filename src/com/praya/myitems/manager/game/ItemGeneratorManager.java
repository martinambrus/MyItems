// 
// Decompiled by Procyon v0.5.36
// 

package com.praya.myitems.manager.game;

import api.praya.myitems.builder.item.ItemGenerator;
import java.util.Collection;
import com.praya.myitems.MyItems;
import com.praya.myitems.config.game.ItemGeneratorConfig;
import com.praya.myitems.builder.handler.HandlerManager;

public class ItemGeneratorManager extends HandlerManager
{
    private final ItemGeneratorConfig itemGeneratorConfig;
    
    protected ItemGeneratorManager(final MyItems plugin) {
        super(plugin);
        this.itemGeneratorConfig = new ItemGeneratorConfig(plugin);
    }
    
    public final ItemGeneratorConfig getItemGeneratorConfig() {
        return this.itemGeneratorConfig;
    }
    
    public final Collection<String> getItemGeneratorIDs() {
        return this.getItemGeneratorConfig().getItemGeneratorIDs();
    }
    
    public final Collection<ItemGenerator> getItemGenerators() {
        return this.getItemGeneratorConfig().getItemGenerators();
    }
    
    public final ItemGenerator getItemGenerator(final String nameid) {
        return this.getItemGeneratorConfig().getItemGenerator(nameid);
    }
    
    public final boolean isItemGeneratorExists(final String nameid) {
        return this.getItemGenerator(nameid) != null;
    }
}
