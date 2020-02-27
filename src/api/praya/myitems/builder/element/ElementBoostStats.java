// 
// Decompiled by Procyon v0.5.36
// 

package api.praya.myitems.builder.element;

public class ElementBoostStats
{
    private final double baseAdditionalDamage;
    private final double basePercentDamage;
    
    public ElementBoostStats(final double baseAdditionalDamage, final double basePercentDamage) {
        this.baseAdditionalDamage = baseAdditionalDamage;
        this.basePercentDamage = basePercentDamage;
    }
    
    public final double getBaseAdditionalDamage() {
        return this.baseAdditionalDamage;
    }
    
    public final double getBasePercentDamage() {
        return this.basePercentDamage;
    }
}
