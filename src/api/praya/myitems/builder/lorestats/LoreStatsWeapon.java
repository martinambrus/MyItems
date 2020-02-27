// 
// Decompiled by Procyon v0.5.36
// 

package api.praya.myitems.builder.lorestats;

public class LoreStatsWeapon
{
    private final double damage;
    private final double penetration;
    private final double pvpDamage;
    private final double pveDamage;
    private final double attackAoERadius;
    private final double attackAoEDamage;
    private final double criticalChance;
    private final double criticalDamage;
    private final double hitRate;
    
    public LoreStatsWeapon(final double damage, final double penetration, final double pvpDamage, final double pveDamage, final double attackAoERadius, final double attackAoEDamage, final double criticalChance, final double criticalDamage, final double hitRate) {
        this.damage = damage;
        this.penetration = penetration;
        this.pvpDamage = pvpDamage;
        this.pveDamage = pveDamage;
        this.attackAoERadius = attackAoERadius;
        this.attackAoEDamage = attackAoEDamage;
        this.criticalChance = criticalChance;
        this.criticalDamage = criticalDamage;
        this.hitRate = hitRate;
    }
    
    public final double getDamage() {
        return this.damage;
    }
    
    public final double getPenetration() {
        return this.penetration;
    }
    
    public final double getPvPDamage() {
        return this.pvpDamage;
    }
    
    public final double getPvEDamage() {
        return this.pveDamage;
    }
    
    public final double getAttackAoERadius() {
        return this.attackAoERadius;
    }
    
    public final double getAttackAoEDamage() {
        return this.attackAoEDamage;
    }
    
    public final double getCriticalChance() {
        return this.criticalChance;
    }
    
    public final double getCriticalDamage() {
        return this.criticalDamage;
    }
    
    public final double getHitRate() {
        return this.hitRate;
    }
}
