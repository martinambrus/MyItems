// 
// Decompiled by Procyon v0.5.36
// 

package api.praya.myitems.builder.lorestats;

public class LoreStatsModifier
{
    private final LoreStatsWeapon weaponModifier;
    private final LoreStatsArmor armorModifier;
    private final LoreStatsUniversal universalModifier;
    
    public LoreStatsModifier() {
        this.weaponModifier = new LoreStatsWeapon(1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0);
        this.armorModifier = new LoreStatsArmor(1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0);
        this.universalModifier = new LoreStatsUniversal(1.0, 1.0);
    }
    
    public LoreStatsModifier(final LoreStatsWeapon weaponModifier, final LoreStatsArmor armorModifier, final LoreStatsUniversal universalModifier) {
        this.weaponModifier = weaponModifier;
        this.armorModifier = armorModifier;
        this.universalModifier = universalModifier;
    }
    
    public final LoreStatsWeapon getWeaponModifier() {
        return this.weaponModifier;
    }
    
    public final LoreStatsArmor getArmorModifier() {
        return this.armorModifier;
    }
    
    public final LoreStatsUniversal getUniversalModifier() {
        return this.universalModifier;
    }
    
    public final double getModifier(final LoreStatsEnum loreStats) {
        switch (loreStats) {
            case BLOCK_AMOUNT: {
                return this.armorModifier.getBlockAmount();
            }
            case BLOCK_RATE: {
                return this.armorModifier.getBlockRate();
            }
            case CRITICAL_CHANCE: {
                return this.weaponModifier.getCriticalChance();
            }
            case CRITICAL_DAMAGE: {
                return this.weaponModifier.getCriticalDamage();
            }
            case DAMAGE: {
                return this.weaponModifier.getDamage();
            }
            case DEFENSE: {
                return this.armorModifier.getDefense();
            }
            case DODGE_RATE: {
                return this.armorModifier.getDodgeRate();
            }
            case DURABILITY: {
                return this.universalModifier.getDurability();
            }
            case HEALTH: {
                return this.armorModifier.getHealth();
            }
            case HIT_RATE: {
                return this.weaponModifier.getHitRate();
            }
            case LEVEL: {
                return this.universalModifier.getLeveL();
            }
            case PENETRATION: {
                return this.weaponModifier.getPenetration();
            }
            case PVE_DAMAGE: {
                return this.weaponModifier.getPvEDamage();
            }
            case PVE_DEFENSE: {
                return this.armorModifier.getPvEDefense();
            }
            case PVP_DAMAGE: {
                return this.weaponModifier.getPvPDamage();
            }
            case PVP_DEFENSE: {
                return this.armorModifier.getPvPDefense();
            }
            default: {
                return 1.0;
            }
        }
    }
}
