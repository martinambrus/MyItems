// 
// Decompiled by Procyon v0.5.36
// 

package api.praya.myitems.builder.lorestats;

import java.util.Iterator;
import com.praya.myitems.config.plugin.MainConfig;
import com.praya.agarthalib.utility.PluginUtil;
import java.util.Arrays;
import java.util.List;

public enum LoreStatsEnum
{
    DAMAGE((String)null, Arrays.asList("Damage", "Attack", "Physical Attack", "PhysicalAttack", "Physical_Attack")), 
    PENETRATION((String)null, Arrays.asList("Penetration", "Pentrate", "Pierce")), 
    PVP_DAMAGE((String)null, Arrays.asList("PvP Damage", "PvP_Damage", "PvPDamage")), 
    PVE_DAMAGE((String)null, Arrays.asList("PvE Damage", "PvE_Damage", "PvEDamage")), 
    DEFENSE((String)null, Arrays.asList("Defense", "Armor", "Protection", "Physical Defense", "PhysicalDefense", "Physical_Defense")), 
    PVP_DEFENSE((String)null, Arrays.asList("PvP Defense", "PvP_Defense", "PvPDefense")), 
    PVE_DEFENSE((String)null, Arrays.asList("PvE Defense", "PvE_Defense", "PvEDefense")), 
    HEALTH((String)null, Arrays.asList("Health", "Hearth", "HP", "Life")), 
    HEALTH_REGEN("LifeEssence", Arrays.asList("Health Regen", "Health_Regen", "HealthRegen", "Regen", "Regeneration")), 
    STAMINA_MAX("CombatStamina", Arrays.asList("Stamina Max", "Stamina_Max", "StaminaMax", "Max Stamina", "Max_Stamina", "Max_Stamina")), 
    STAMINA_REGEN("CombatStamina", Arrays.asList("Stamina Regen", "Stamina_Regen", "StaminaRegen", "Regen Stamina", "Regen_Stamina", "Regen_Stamina")), 
    ATTACK_AOE_RADIUS((String)null, Arrays.asList("Attack AoE Radius", "Attack_AoE_Radius", "AoE Radius", "AoE_Radius")), 
    ATTACK_AOE_DAMAGE((String)null, Arrays.asList("Attack AoE Damage", "Attack_AoE_Damage", "AoE Damage", "AoE_Damage")), 
    CRITICAL_CHANCE((String)null, Arrays.asList("Critical Chance", "CriticalChance", "Critical_Chance", "CC", "CritChance")), 
    CRITICAL_DAMAGE((String)null, Arrays.asList("Critical Damage", "CriticalDamage", "Critical_Damage", "CD", "CritDamage")), 
    BLOCK_AMOUNT((String)null, Arrays.asList("Block Amount", "BlockAmount", "Block_Amount")), 
    BLOCK_RATE((String)null, Arrays.asList("Block Rate", "BlockRate", "Block_Rate", "Block")), 
    HIT_RATE((String)null, Arrays.asList("Hit Rate", "HitRate", "Hit_Rate", "Hit", "Accuration", "Accuracy")), 
    DODGE_RATE((String)null, Arrays.asList("Dodge Rate", "DodgeRate", "Dodge_Rate", "Dodge")), 
    FISHING_CHANCE("DreamFish", Arrays.asList("Fishing Chance", "FishingChance", "Fishing_Chance", "Fishing Rate", "FishingRate", "Fishing_Rate")), 
    FISHING_POWER("DreamFish", Arrays.asList("Fishing Power", "FishingPower", "Fishing_Power")), 
    FISHING_SPEED("DreamFish", Arrays.asList("Fishing Speed", "FishingSpeed", "Fishing_Speed")), 
    LURES_MAX_TENSION("DreamFish", Arrays.asList("Lures Tension", "LuresTension", "Lures_Tension", "Lures Max Tension", "LuresMaxTension", "Lures_Max_Tension")), 
    LURES_ENDURANCE("DreamFish", Arrays.asList("Lures Endurance", "LuresEndurance", "Lures_Endurance")), 
    DURABILITY((String)null, Arrays.asList("Durability")), 
    LEVEL((String)null, Arrays.asList("Level"));
    
    private final String dependency;
    private final List<String> aliases;
    
    private LoreStatsEnum(final String dependency, final List<String> aliases) {
        this.dependency = dependency;
        this.aliases = aliases;
    }
    
    public final String getDependency() {
        return this.dependency;
    }
    
    public final List<String> getAliases() {
        return this.aliases;
    }
    
    public final boolean hasDependency() {
        return this.getDependency() != null;
    }
    
    public final boolean isAllowed() {
        final String dependency = this.getDependency();
        return PluginUtil.isPluginInstalled(dependency);
    }
    
    public final String getText() {
        final MainConfig mainConfig = MainConfig.getInstance();
        switch (this) {
            case DAMAGE: {
                return mainConfig.getStatsIdentifierDamage();
            }
            case PENETRATION: {
                return mainConfig.getStatsIdentifierPenetration();
            }
            case PVP_DAMAGE: {
                return mainConfig.getStatsIdentifierPvPDamage();
            }
            case PVE_DAMAGE: {
                return mainConfig.getStatsIdentifierPvEDamage();
            }
            case DEFENSE: {
                return mainConfig.getStatsIdentifierDefense();
            }
            case PVP_DEFENSE: {
                return mainConfig.getStatsIdentifierPvPDefense();
            }
            case PVE_DEFENSE: {
                return mainConfig.getStatsIdentifierPvEDefense();
            }
            case HEALTH: {
                return mainConfig.getStatsIdentifierHealth();
            }
            case HEALTH_REGEN: {
                return mainConfig.getStatsIdentifierHealthRegen();
            }
            case STAMINA_MAX: {
                return mainConfig.getStatsIdentifierStaminaMax();
            }
            case STAMINA_REGEN: {
                return mainConfig.getStatsIdentifierStaminaRegen();
            }
            case ATTACK_AOE_RADIUS: {
                return mainConfig.getStatsIdentifierAttackAoERadius();
            }
            case ATTACK_AOE_DAMAGE: {
                return mainConfig.getStatsIdentifierAttackAoEDamage();
            }
            case CRITICAL_CHANCE: {
                return mainConfig.getStatsIdentifierCriticalChance();
            }
            case CRITICAL_DAMAGE: {
                return mainConfig.getStatsIdentifierCriticalDamage();
            }
            case BLOCK_AMOUNT: {
                return mainConfig.getStatsIdentifierBlockAmount();
            }
            case BLOCK_RATE: {
                return mainConfig.getStatsIdentifierBlockRate();
            }
            case HIT_RATE: {
                return mainConfig.getStatsIdentifierHitRate();
            }
            case DODGE_RATE: {
                return mainConfig.getStatsIdentifierDodgeRate();
            }
            case FISHING_CHANCE: {
                return mainConfig.getStatsIdentifierFishingChance();
            }
            case FISHING_POWER: {
                return mainConfig.getStatsIdentifierFishingPower();
            }
            case FISHING_SPEED: {
                return mainConfig.getStatsIdentifierFishingSpeed();
            }
            case LURES_MAX_TENSION: {
                return mainConfig.getStatsIdentifierLuresMaxTension();
            }
            case LURES_ENDURANCE: {
                return mainConfig.getStatsIdentifierLuresEndurance();
            }
            case DURABILITY: {
                return mainConfig.getStatsIdentifierDurability();
            }
            case LEVEL: {
                return mainConfig.getStatsIdentifierLevel();
            }
            default: {
                return null;
            }
        }
    }
    
    public static final LoreStatsEnum get(final String stats) {
        LoreStatsEnum[] values;
        for (int length = (values = values()).length, i = 0; i < length; ++i) {
            final LoreStatsEnum key = values[i];
            for (final String aliases : key.getAliases()) {
                if (aliases.equalsIgnoreCase(stats)) {
                    return key;
                }
            }
        }
        return null;
    }
}
