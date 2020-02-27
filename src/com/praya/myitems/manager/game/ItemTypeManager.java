// 
// Decompiled by Procyon v0.5.36
// 

package com.praya.myitems.manager.game;

import api.praya.myitems.builder.item.ItemType;
import java.util.Collection;
import com.praya.myitems.MyItems;
import com.praya.myitems.config.game.ItemTypeConfig;
import com.praya.myitems.builder.handler.HandlerManager;

public class ItemTypeManager extends HandlerManager
{
    private final ItemTypeConfig itemTypeConfig;
    
    protected ItemTypeManager(final MyItems plugin) {
        super(plugin);
        this.itemTypeConfig = new ItemTypeConfig(plugin);
    }
    
    public final ItemTypeConfig getItemTypeConfig() {
        return this.itemTypeConfig;
    }
    
    public final Collection<String> getItemTypeIDs() {
        return this.getItemTypeConfig().getItemTypeIDs();
    }
    
    public final Collection<ItemType> getItemTypes() {
        return this.getItemTypeConfig().getItemTypes();
    }
    
    public final ItemType getItemType(final String id) {
        return this.getItemTypeConfig().getItemType(id);
    }
    
    public final boolean isItemTypeExists(final String id) {
        return this.getItemType(id) != null;
    }
}
