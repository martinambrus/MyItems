// 
// Decompiled by Procyon v0.5.36
// 

package api.praya.myitems.manager.game;

import com.praya.myitems.manager.game.GameManager;
import com.praya.myitems.manager.game.ItemTierManager;
import api.praya.myitems.builder.item.ItemTier;
import java.util.Collection;
import com.praya.myitems.MyItems;
import com.praya.myitems.builder.handler.HandlerManager;

public class ItemTierManagerAPI extends HandlerManager
{
    protected ItemTierManagerAPI(final MyItems plugin) {
        super(plugin);
    }
    
    public final Collection<String> getItemTierIDs() {
        return this.getItemTierManager().getItemTierIDs();
    }
    
    public final Collection<ItemTier> getItemTiers() {
        return this.getItemTierManager().getItemTiers();
    }
    
    public final ItemTier getItemTier(final String id) {
        return this.getItemTierManager().getItemTier(id);
    }
    
    public final boolean isItemTierExists(final String id) {
        return this.getItemTierManager().isItemTierExists(id);
    }
    
    private final ItemTierManager getItemTierManager() {
        final GameManager gameManager = this.plugin.getGameManager();
        final ItemTierManager itemTierManager = gameManager.getItemTierManager();
        return itemTierManager;
    }
}
