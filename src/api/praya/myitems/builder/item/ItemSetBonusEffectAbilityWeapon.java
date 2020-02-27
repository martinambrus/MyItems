// 
// Decompiled by Procyon v0.5.36
// 

package api.praya.myitems.builder.item;

import java.util.Iterator;
import java.util.Collection;
import api.praya.myitems.builder.ability.AbilityItemWeapon;
import java.util.HashMap;

public class ItemSetBonusEffectAbilityWeapon
{
    private final HashMap<String, AbilityItemWeapon> mapAbilityItemWeapon;
    
    public ItemSetBonusEffectAbilityWeapon() {
        this(new HashMap<String, AbilityItemWeapon>());
    }
    
    public ItemSetBonusEffectAbilityWeapon(final HashMap<String, AbilityItemWeapon> mapAbilityItemWeapon) {
        this.mapAbilityItemWeapon = mapAbilityItemWeapon;
    }
    
    public final Collection<String> getAbilityIDs() {
        return this.mapAbilityItemWeapon.keySet();
    }
    
    public final Collection<AbilityItemWeapon> getAllAbilityItemWeapon() {
        return this.mapAbilityItemWeapon.values();
    }
    
    public final AbilityItemWeapon getAbilityItemWeapon(final String ability) {
        if (ability != null) {
            for (final String key : this.getAbilityIDs()) {
                if (key.equalsIgnoreCase(ability)) {
                    return this.mapAbilityItemWeapon.get(key);
                }
            }
        }
        return null;
    }
    
    public final boolean isAbilityExists(final String ability) {
        return this.getAbilityItemWeapon(ability) != null;
    }
}
