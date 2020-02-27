// 
// Decompiled by Procyon v0.5.36
// 

package api.praya.myitems.builder.ability;

import com.praya.agarthalib.utility.MathUtil;

public class AbilityWeaponProperties
{
    private final int maxGrade;
    private final int baseDurationEffect;
    private final int scaleDurationEffect;
    private final double scaleBaseBonusDamage;
    private final double scaleBasePercentDamage;
    private final double scaleCastBonusDamage;
    private final double scaleCastPercentDamage;
    
    public AbilityWeaponProperties(final int maxGrade, final int baseDurationEffect, final int scaleDurationEffect, final double scaleBaseBonusDamage, final double scaleBasePercentDamage, final double scaleCastBonusDamage, final double scaleCastPercentDamage) {
        this.maxGrade = maxGrade;
        this.baseDurationEffect = baseDurationEffect;
        this.scaleDurationEffect = scaleDurationEffect;
        this.scaleBaseBonusDamage = scaleBaseBonusDamage;
        this.scaleBasePercentDamage = scaleBasePercentDamage;
        this.scaleCastBonusDamage = scaleCastBonusDamage;
        this.scaleCastPercentDamage = scaleCastPercentDamage;
    }
    
    public final int getMaxGrade() {
        return this.maxGrade;
    }
    
    public final int getBaseDurationEffect() {
        return this.baseDurationEffect;
    }
    
    public final int getScaleDurationEffect() {
        return this.scaleDurationEffect;
    }
    
    public final double getScaleBaseBonusDamage() {
        return this.scaleBaseBonusDamage;
    }
    
    public final double getScaleBasePercentDamage() {
        return this.scaleBasePercentDamage;
    }
    
    public final double getScaleCastBonusDamage() {
        return this.scaleCastBonusDamage;
    }
    
    public final double getScaleCastPercentDamage() {
        return this.scaleCastPercentDamage;
    }
    
    public final int getTotalDuration(final int grade) {
        final int finalGrade = MathUtil.limitInteger(grade, 0, this.getMaxGrade());
        final int baseDuration = this.getBaseDurationEffect();
        final int scaleDuration = this.getScaleDurationEffect();
        final int totalDuration = baseDuration + finalGrade * scaleDuration;
        return totalDuration;
    }
    
    public final double getTotalBaseDamage(final int grade, final double damage) {
        final int finalGrade = MathUtil.limitInteger(grade, 0, this.getMaxGrade());
        final double scaleBonusDamage = this.getScaleBaseBonusDamage();
        final double scalePercentDamage = this.getScaleBasePercentDamage();
        final double totalDamage = finalGrade * scaleBonusDamage + damage * (finalGrade * scalePercentDamage / 100.0);
        return totalDamage;
    }
    
    public final double getTotalCastDamage(final int grade, final double damage) {
        final int finalGrade = MathUtil.limitInteger(grade, 0, this.getMaxGrade());
        final double scaleBonusDamage = this.getScaleCastBonusDamage();
        final double scalePercentDamage = this.getScaleCastPercentDamage();
        final double totalDamage = finalGrade * scaleBonusDamage + damage * (finalGrade * scalePercentDamage / 100.0);
        return totalDamage;
    }
}
