// 
// Decompiled by Procyon v0.5.36
// 

package api.praya.myitems.builder.potion;

public class PotionProperties
{
    private final int grade;
    private final double scaleChance;
    private final double scaleDuration;
    
    public PotionProperties(final int grade, final double scaleChance, final double scaleDuration) {
        this.grade = grade;
        this.scaleChance = scaleChance;
        this.scaleDuration = scaleDuration;
    }
    
    public final int getGrade() {
        return this.grade;
    }
    
    public final double getScaleChance() {
        return this.scaleChance;
    }
    
    public final double getScaleDuration() {
        return this.scaleDuration;
    }
}
