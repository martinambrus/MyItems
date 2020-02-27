// 
// Decompiled by Procyon v0.5.36
// 

package api.praya.myitems.builder.item;

import com.praya.agarthalib.utility.MathUtil;

public class ItemStatsWeapon
{
    private final double totalDamageMin;
    private final double totalDamageMax;
    private final double totalPenetration;
    private final double totalPvPDamage;
    private final double totalPvEDamage;
    private final double totalAttackAoERadius;
    private final double totalAttackAoEDamage;
    private final double totalCriticalChance;
    private final double totalCriticalDamage;
    private final double totalHitRate;
    private final double socketDamage;
    private final double elementDamage;
    
    public ItemStatsWeapon(final double totalDamageMin, final double totalDamageMax, final double totalPenetration, final double totalPvPDamage, final double totalPvEDamage, final double totalAttackAoERadius, final double totalAttackAoEDamage, final double totalCriticalChance, final double totalCriticalDamage, final double totalHitRate, final double socketDamage, final double elementDamage) {
        this.totalDamageMin = totalDamageMin;
        this.totalDamageMax = totalDamageMax;
        this.totalPenetration = totalPenetration;
        this.totalPvPDamage = totalPvPDamage;
        this.totalPvEDamage = totalPvEDamage;
        this.totalAttackAoERadius = totalAttackAoERadius;
        this.totalAttackAoEDamage = totalAttackAoEDamage;
        this.totalCriticalChance = MathUtil.limitDouble(totalCriticalChance, 0.0, 100.0);
        this.totalCriticalDamage = totalCriticalDamage;
        this.totalHitRate = totalHitRate;
        this.socketDamage = socketDamage;
        this.elementDamage = elementDamage;
    }
    
    public final double getTotalDamageMin() {
        return this.totalDamageMin;
    }
    
    public final double getTotalDamageMax() {
        return this.totalDamageMax;
    }
    
    public final double getTotalPenetration() {
        return this.totalPenetration;
    }
    
    public final double getTotalPvPDamage() {
        return this.totalPvPDamage;
    }
    
    public final double getTotalPvEDamage() {
        return this.totalPvEDamage;
    }
    
    public final double getTotalAttackAoERadius() {
        return this.totalAttackAoERadius;
    }
    
    public final double getTotalAttackAoEDamage() {
        return this.totalAttackAoEDamage;
    }
    
    public final double getTotalCriticalChance() {
        return this.totalCriticalChance;
    }
    
    public final double getTotalCriticalDamage() {
        return this.totalCriticalDamage;
    }
    
    public final double getTotalHitRate() {
        return this.totalHitRate;
    }
    
    public final double getSocketDamage() {
        return this.socketDamage;
    }
    
    public final double getElementDamage() {
        return this.elementDamage;
    }
}
