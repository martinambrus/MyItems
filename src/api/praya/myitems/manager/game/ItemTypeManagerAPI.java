// 
// Decompiled by Procyon v0.5.36
// 

package api.praya.myitems.manager.game;

import com.praya.myitems.manager.game.GameManager;
import com.praya.myitems.manager.game.ItemTypeManager;
import api.praya.myitems.builder.item.ItemType;
import java.util.Collection;
import com.praya.myitems.MyItems;
import com.praya.myitems.builder.handler.HandlerManager;

public class ItemTypeManagerAPI extends HandlerManager
{
    protected ItemTypeManagerAPI(final MyItems plugin) {
        super(plugin);
    }
    
    public final Collection<String> getItemTypeIDs() {
        return this.getItemTypeManager().getItemTypeIDs();
    }
    
    public final Collection<ItemType> getItemTypes() {
        return this.getItemTypeManager().getItemTypes();
    }
    
    public final ItemType getItemType(final String id) {
        return this.getItemTypeManager().getItemType(id);
    }
    
    public final boolean isItemTypeExists(final String id) {
        return this.getItemTypeManager().isItemTypeExists(id);
    }
    
    private final ItemTypeManager getItemTypeManager() {
        final GameManager gameManager = this.plugin.getGameManager();
        final ItemTypeManager itemTypeManager = gameManager.getItemTypeManager();
        return itemTypeManager;
    }
}
