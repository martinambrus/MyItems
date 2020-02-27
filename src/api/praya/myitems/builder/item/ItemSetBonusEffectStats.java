// 
// Decompiled by Procyon v0.5.36
// 

package api.praya.myitems.builder.item;

public class ItemSetBonusEffectStats
{
    private final double additionalDamage;
    private final double percentDamage;
    private final double penetration;
    private final double pvpDamage;
    private final double pveDamage;
    private final double additionalDefense;
    private final double percentDefense;
    private final double health;
    private final double healthRegen;
    private final double staminaMax;
    private final double staminaRegen;
    private final double attackAoERadius;
    private final double attackAoEDamage;
    private final double pvpDefense;
    private final double pveDefense;
    private final double criticalChance;
    private final double criticalDamage;
    private final double blockAmount;
    private final double blockRate;
    private final double hitRate;
    private final double dodgeRate;
    
    public ItemSetBonusEffectStats() {
        this(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
    }
    
    public ItemSetBonusEffectStats(final double additionalDamage, final double percentDamage, final double penetration, final double pvpDamage, final double pveDamage, final double additionalDefense, final double percentDefense, final double health, final double healthRegen, final double staminaMax, final double staminaRegen, final double attackAoERadius, final double attackAoEDamage, final double pvpDefense, final double pveDefense, final double criticalChance, final double criticalDamage, final double blockAmount, final double blockRate, final double hitRate, final double dodgeRate) {
        this.additionalDamage = additionalDamage;
        this.percentDamage = percentDamage;
        this.penetration = penetration;
        this.pvpDamage = pvpDamage;
        this.pveDamage = pveDamage;
        this.additionalDefense = additionalDefense;
        this.percentDefense = percentDefense;
        this.health = health;
        this.healthRegen = healthRegen;
        this.staminaMax = staminaMax;
        this.staminaRegen = staminaRegen;
        this.attackAoERadius = attackAoERadius;
        this.attackAoEDamage = attackAoEDamage;
        this.pvpDefense = pvpDefense;
        this.pveDefense = pveDefense;
        this.criticalChance = criticalChance;
        this.criticalDamage = criticalDamage;
        this.blockAmount = blockAmount;
        this.blockRate = blockRate;
        this.hitRate = hitRate;
        this.dodgeRate = dodgeRate;
    }
    
    public final double getAdditionalDamage() {
        return this.additionalDamage;
    }
    
    public final double getPercentDamage() {
        return this.percentDamage;
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
    
    public final double getAdditionalDefense() {
        return this.additionalDefense;
    }
    
    public final double getPercentDefense() {
        return this.percentDefense;
    }
    
    public final double getHealth() {
        return this.health;
    }
    
    public final double getHealthRegen() {
        return this.healthRegen;
    }
    
    public final double getStaminaMax() {
        return this.staminaMax;
    }
    
    public final double getStaminaRegen() {
        return this.staminaRegen;
    }
    
    public final double getAttackAoERadius() {
        return this.attackAoERadius;
    }
    
    public final double getAttackAoEDamage() {
        return this.attackAoEDamage;
    }
    
    public final double getPvPDefense() {
        return this.pvpDefense;
    }
    
    public final double getPvEDefense() {
        return this.pveDefense;
    }
    
    public final double getCriticalChance() {
        return this.criticalChance;
    }
    
    public final double getCriticalDamage() {
        return this.criticalDamage;
    }
    
    public final double getBlockAmount() {
        return this.blockAmount;
    }
    
    public final double getBlockRate() {
        return this.blockRate;
    }
    
    public final double getHitRate() {
        return this.hitRate;
    }
    
    public final double getDodgeRate() {
        return this.dodgeRate;
    }
}
