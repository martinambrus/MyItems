// 
// Decompiled by Procyon v0.5.36
// 

package api.praya.myitems.builder.event;

import org.bukkit.inventory.ItemStack;
import api.praya.myitems.builder.power.PowerClickEnum;
import api.praya.myitems.builder.power.PowerEnum;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

public class PowerCommandCastEvent extends PowerPreCastEvent
{
    private static final HandlerList handlers;
    private final String keyCommand;
    private boolean cancel;
    private double cooldown;
    
    static {
        handlers = new HandlerList();
    }
    
    public PowerCommandCastEvent(final Player player, final PowerEnum power, final PowerClickEnum click, final ItemStack item, final String lore, final String keyCommand, final double cooldown) {
        super(player, power, click, item, lore);
        this.cancel = false;
        this.keyCommand = keyCommand;
        this.cooldown = cooldown;
    }
    
    public final String getKeyCommand() {
        return this.keyCommand;
    }
    
    public final double getCooldown() {
        return this.cooldown;
    }
    
    public final void setCooldown(final double cooldown) {
        this.cooldown = cooldown;
    }
    
    @Override
    public HandlerList getHandlers() {
        return PowerCommandCastEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return PowerCommandCastEvent.handlers;
    }
    
    @Override
    public boolean isCancelled() {
        return this.cancel;
    }
    
    @Override
    public void setCancelled(final boolean cancel) {
        this.cancel = cancel;
    }
}
