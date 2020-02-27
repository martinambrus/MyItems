// 
// Decompiled by Procyon v0.5.36
// 

package api.praya.myitems.builder.element;

public class ElementBoost
{
    private final double scaleBaseAdditionalDamage;
    private final double scaleBasePercentDamage;
    
    public ElementBoost(final double scaleBaseAdditionalDamage, final double scaleBasePercentDamage) {
        this.scaleBaseAdditionalDamage = scaleBaseAdditionalDamage;
        this.scaleBasePercentDamage = scaleBasePercentDamage;
    }
    
    public final double getScaleBaseAdditionalDamage() {
        return this.scaleBaseAdditionalDamage;
    }
    
    public final double getScaleBasePercentDamage() {
        return this.scaleBasePercentDamage;
    }
}
