// 
// Decompiled by Procyon v0.5.36
// 

package api.praya.myitems.builder.element;

import api.praya.myitems.builder.potion.PotionProperties;
import org.bukkit.potion.PotionEffectType;
import java.util.HashMap;

public class ElementPotion
{
    private final HashMap<PotionEffectType, PotionProperties> potionAttacker;
    private final HashMap<PotionEffectType, PotionProperties> potionVictims;
    
    public ElementPotion(final HashMap<PotionEffectType, PotionProperties> potionAttacker, final HashMap<PotionEffectType, PotionProperties> potionVictims) {
        this.potionAttacker = potionAttacker;
        this.potionVictims = potionVictims;
    }
    
    public final HashMap<PotionEffectType, PotionProperties> getPotionAttacker() {
        return this.potionAttacker;
    }
    
    public final HashMap<PotionEffectType, PotionProperties> getPotionVictims() {
        return this.potionVictims;
    }
}
