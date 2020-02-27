// 
// Decompiled by Procyon v0.5.36
// 

package api.praya.myitems.builder.item;

import com.praya.myitems.manager.game.ItemSetManager;
import com.praya.myitems.manager.game.GameManager;
import org.bukkit.plugin.java.JavaPlugin;
import com.praya.myitems.MyItems;
import core.praya.agarthalib.enums.main.Slot;
import java.util.Set;

public class ItemSetComponent
{
    private final String itemSetID;
    private final String id;
    private final String keyLore;
    private final ItemSetComponentItem componentItem;
    private final Set<Slot> slots;
    
    public ItemSetComponent(final String itemSetID, final String id, final String keyLore, final ItemSetComponentItem componentItem, final Set<Slot> slots) {
        this.itemSetID = itemSetID;
        this.id = id;
        this.keyLore = keyLore;
        this.componentItem = componentItem;
        this.slots = slots;
    }
    
    public final String getItemSetID() {
        return this.itemSetID;
    }
    
    public final String getID() {
        return this.id;
    }
    
    public final String getKeyLore() {
        return this.keyLore;
    }
    
    public final ItemSetComponentItem getComponentItem() {
        return this.componentItem;
    }
    
    public final Set<Slot> getSlots() {
        return this.slots;
    }
    
    public final boolean isMatchSlot(final Slot slot) {
        return slot != null && this.getSlots().contains(slot);
    }
    
    public final ItemSet getItemSet() {
        final MyItems plugin = (MyItems)JavaPlugin.getPlugin((Class)MyItems.class);
        final GameManager gameManager = plugin.getGameManager();
        final ItemSetManager itemSetManager = gameManager.getItemSetManager();
        final ItemSet itemSet = itemSetManager.getItemSet(this.getItemSetID());
        return itemSet;
    }
}
