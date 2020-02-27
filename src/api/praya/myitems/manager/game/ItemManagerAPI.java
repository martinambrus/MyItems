// 
// Decompiled by Procyon v0.5.36
// 

package api.praya.myitems.manager.game;

import com.praya.myitems.manager.game.GameManager;
import com.praya.myitems.manager.game.ItemManager;
import org.bukkit.inventory.ItemStack;
import java.util.Collection;
import com.praya.myitems.MyItems;
import com.praya.myitems.builder.handler.HandlerManager;

public class ItemManagerAPI extends HandlerManager
{
    protected ItemManagerAPI(final MyItems plugin) {
        super(plugin);
    }
    
    public final Collection<String> getItemNames() {
        return this.getItemManager().getItemIDs();
    }
    
    public final Collection<ItemStack> getItems() {
        return this.getItemManager().getItems();
    }
    
    public final ItemStack getItem(final String nameid) {
        return this.getItemManager().getItem(nameid);
    }
    
    public final boolean isExist(final String nameid) {
        return this.getItemManager().isExist(nameid);
    }
    
    private final ItemManager getItemManager() {
        final GameManager gameManager = this.plugin.getGameManager();
        final ItemManager itemManager = gameManager.getItemManager();
        return itemManager;
    }
}
