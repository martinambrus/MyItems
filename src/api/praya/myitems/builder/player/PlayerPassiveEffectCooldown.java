// 
// Decompiled by Procyon v0.5.36
// 

package api.praya.myitems.builder.player;

import api.praya.myitems.builder.passive.PassiveEffectEnum;
import java.util.HashMap;

public class PlayerPassiveEffectCooldown
{
    private final HashMap<PassiveEffectEnum, Long> mapPassiveEffectExpired;
    
    public PlayerPassiveEffectCooldown() {
        this.mapPassiveEffectExpired = new HashMap<PassiveEffectEnum, Long>();
    }
    
    public final void setPassiveEffectCooldown(final PassiveEffectEnum effect, final long cooldown) {
        final long expired = System.currentTimeMillis() + cooldown;
        this.mapPassiveEffectExpired.put(effect, expired);
    }
    
    public final boolean isPassiveEffectCooldown(final PassiveEffectEnum effect) {
        if (this.mapPassiveEffectExpired.containsKey(effect)) {
            final long expired = this.mapPassiveEffectExpired.get(effect);
            final long now = System.currentTimeMillis();
            return now < expired;
        }
        return false;
    }
    
    public final void removePassiveEffectCooldown(final PassiveEffectEnum effect) {
        this.mapPassiveEffectExpired.remove(effect);
    }
    
    public final long getPassiveEffectExpired(final PassiveEffectEnum effect) {
        return this.mapPassiveEffectExpired.containsKey(effect) ? this.mapPassiveEffectExpired.get(effect) : System.currentTimeMillis();
    }
    
    public final long getPassiveEffectTimeLeft(final PassiveEffectEnum effect) {
        return this.isPassiveEffectCooldown(effect) ? (this.mapPassiveEffectExpired.get(effect) - System.currentTimeMillis()) : 0L;
    }
}
