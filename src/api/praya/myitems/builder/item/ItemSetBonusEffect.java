// 
// Decompiled by Procyon v0.5.36
// 

package api.praya.myitems.builder.item;

public class ItemSetBonusEffect
{
    private final ItemSetBonusEffectStats effectStats;
    private final ItemSetBonusEffectAbilityWeapon effectAbilityWeapon;
    
    public ItemSetBonusEffect(final ItemSetBonusEffectStats effectStats, final ItemSetBonusEffectAbilityWeapon effectAbilityWeapon) {
        this.effectStats = effectStats;
        this.effectAbilityWeapon = effectAbilityWeapon;
    }
    
    public final ItemSetBonusEffectStats getEffectStats() {
        return this.effectStats;
    }
    
    public final ItemSetBonusEffectAbilityWeapon getEffectAbilityWeapon() {
        return this.effectAbilityWeapon;
    }
}
