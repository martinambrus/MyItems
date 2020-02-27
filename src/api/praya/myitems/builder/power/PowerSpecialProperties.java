// 
// Decompiled by Procyon v0.5.36
// 

package api.praya.myitems.builder.power;

public class PowerSpecialProperties
{
    private final int durationEffect;
    private final double baseAdditionalDamage;
    private final double basePercentDamage;
    
    public PowerSpecialProperties(final int durationEffect, final double baseAdditionalDamage, final double basePercentDamage) {
        this.durationEffect = durationEffect;
        this.baseAdditionalDamage = baseAdditionalDamage;
        this.basePercentDamage = basePercentDamage;
    }
    
    public final int getDurationEffect() {
        return this.durationEffect;
    }
    
    public final double getBaseAdditionalDamage() {
        return this.baseAdditionalDamage;
    }
    
    public final double getBasePercentDamage() {
        return this.basePercentDamage;
    }
}
