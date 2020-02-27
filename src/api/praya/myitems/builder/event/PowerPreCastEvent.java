// 
// Decompiled by Procyon v0.5.36
// 

package api.praya.myitems.builder.event;

import org.bukkit.inventory.ItemStack;
import api.praya.myitems.builder.power.PowerClickEnum;
import api.praya.myitems.builder.power.PowerEnum;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;

public class PowerPreCastEvent extends Event implements Cancellable
{
    private static final HandlerList handlers;
    private boolean cancel;
    private final Player player;
    private final PowerEnum power;
    private final PowerClickEnum click;
    private final ItemStack item;
    private final String lore;
    
    static {
        handlers = new HandlerList();
    }
    
    public PowerPreCastEvent(final Player player, final PowerEnum power, final PowerClickEnum click, final ItemStack item, final String lore) {
        this.cancel = false;
        this.player = player;
        this.power = power;
        this.click = click;
        this.item = item;
        this.lore = lore;
    }
    
    public final Player getPlayer() {
        return this.player;
    }
    
    public final PowerEnum getPower() {
        return this.power;
    }
    
    public final PowerClickEnum getClick() {
        return this.click;
    }
    
    public final ItemStack getItem() {
        return this.item;
    }
    
    public final String getLore() {
        return this.lore;
    }
    
    public HandlerList getHandlers() {
        return PowerPreCastEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return PowerPreCastEvent.handlers;
    }
    
    public boolean isCancelled() {
        return this.cancel;
    }
    
    public void setCancelled(final boolean cancel) {
        this.cancel = cancel;
    }
}
