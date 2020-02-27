// 
// Decompiled by Procyon v0.5.36
// 

package api.praya.myitems.builder.passive;

import java.util.Iterator;
import org.bukkit.potion.PotionEffectType;
import com.praya.myitems.config.plugin.MainConfig;
import java.util.Arrays;
import java.util.List;

public enum PassiveEffectEnum
{
    STRENGTH(10, PassiveTypeEnum.BUFF, Arrays.asList("Strength", "Attack", "Damage")), 
    PROTECTION(10, PassiveTypeEnum.BUFF, Arrays.asList("Protection", "Defense")), 
    VISION(10, PassiveTypeEnum.BUFF, Arrays.asList("Vision", "Night Vision", "NightVision", "Night_Vision")), 
    JUMP(10, PassiveTypeEnum.BUFF, Arrays.asList("Jump", "Jump Boost", "JumpBoost", "Jump_Boost")), 
    ABSORB(10, PassiveTypeEnum.BUFF, Arrays.asList("Absorb", "Absorbtion")), 
    FIRE_RESISTANCE(10, PassiveTypeEnum.BUFF, Arrays.asList("Fire Resistance", "FireResistance", "Fire_Resistance", "Fire Resist", "FireResist", "Fire_Resist")), 
    INVISIBILITY(10, PassiveTypeEnum.BUFF, Arrays.asList("Invisibility", "Invisible")), 
    LUCK(10, PassiveTypeEnum.BUFF, Arrays.asList("Luck", "Fortune")), 
    HEALTH_BOOST(10, PassiveTypeEnum.BUFF, Arrays.asList("Health Boost", "HealthBoost", "Health_Boost", "Health")), 
    REGENERATION(10, PassiveTypeEnum.BUFF, Arrays.asList("Regeneration", "Regen")), 
    SATURATION(10, PassiveTypeEnum.BUFF, Arrays.asList("Saturation")), 
    SPEED(10, PassiveTypeEnum.BUFF, Arrays.asList("Speed", "Movement", "Move")), 
    WATER_BREATHING(10, PassiveTypeEnum.BUFF, Arrays.asList("Water Breathing", "WaterBreathing", "Water_Breathing")), 
    HASTE(4, PassiveTypeEnum.BUFF, Arrays.asList("Haste")), 
    WEAK(10, PassiveTypeEnum.DEBUFF, Arrays.asList("Weak", "Weakness")), 
    SLOW(5, PassiveTypeEnum.DEBUFF, Arrays.asList("Slow", "Slowness")), 
    BLIND(10, PassiveTypeEnum.DEBUFF, Arrays.asList("Blind", "Blindness")), 
    CONFUSE(10, PassiveTypeEnum.DEBUFF, Arrays.asList("Confuse", "Confusion")), 
    STARVE(10, PassiveTypeEnum.DEBUFF, Arrays.asList("Hunger", "Hungry", "Starve")), 
    TOXIC(10, PassiveTypeEnum.DEBUFF, Arrays.asList("Poison", "Poisonous", "Toxic")), 
    GLOW(1, PassiveTypeEnum.DEBUFF, Arrays.asList("Glow", "Glowing")), 
    FATIGUE(4, PassiveTypeEnum.DEBUFF, Arrays.asList("Tired", "Fatigue")), 
    WITHER(10, PassiveTypeEnum.DEBUFF, Arrays.asList("Wither", "Dark", "Darkness")), 
    UNLUCK(10, PassiveTypeEnum.DEBUFF, Arrays.asList("Unluck", "Deluck"));
    
    private final int maxGrade;
    private final PassiveTypeEnum type;
    private final List<String> aliases;
    
    private PassiveEffectEnum(final int maxGrade, final PassiveTypeEnum type, final List<String> aliases) {
        this.maxGrade = maxGrade;
        this.type = type;
        this.aliases = aliases;
    }
    
    public final int getMaxGrade() {
        return this.maxGrade;
    }
    
    public final PassiveTypeEnum getType() {
        return this.type;
    }
    
    public final List<String> getAliases() {
        return this.aliases;
    }
    
    public final String getName() {
        return this.getAliases().get(0);
    }
    
    public final String getText() {
        final MainConfig mainConfig = MainConfig.getInstance();
        switch (this) {
            case STRENGTH: {
                return mainConfig.getPassiveBuffIdentifierStrength();
            }
            case PROTECTION: {
                return mainConfig.getPassiveBuffIdentifierProtection();
            }
            case VISION: {
                return mainConfig.getPassiveBuffIdentifierVision();
            }
            case JUMP: {
                return mainConfig.getPassiveBuffIdentifierJump();
            }
            case ABSORB: {
                return mainConfig.getPassiveBuffIdentifierAbsorb();
            }
            case FIRE_RESISTANCE: {
                return mainConfig.getPassiveBuffIdentifierFireResistance();
            }
            case INVISIBILITY: {
                return mainConfig.getPassiveBuffIdentifierInvisibility();
            }
            case LUCK: {
                return mainConfig.getPassiveBuffIdentifierLuck();
            }
            case HEALTH_BOOST: {
                return mainConfig.getPassiveBuffIdentifierHealthBoost();
            }
            case REGENERATION: {
                return mainConfig.getPassiveBuffIdentifierRegeneration();
            }
            case SATURATION: {
                return mainConfig.getPassiveBuffIdentifierSaturation();
            }
            case SPEED: {
                return mainConfig.getPassiveBuffIdentifierSpeed();
            }
            case WATER_BREATHING: {
                return mainConfig.getPassiveBuffIdentifierWaterBreathing();
            }
            case HASTE: {
                return mainConfig.getPassiveBuffIdentifierHaste();
            }
            case BLIND: {
                return mainConfig.getPassiveDebuffIdentifierBlind();
            }
            case CONFUSE: {
                return mainConfig.getPassiveDebuffIdentifierConfuse();
            }
            case FATIGUE: {
                return mainConfig.getPassiveDebuffIdentifierFatigue();
            }
            case SLOW: {
                return mainConfig.getPassiveDebuffIdentifierSlow();
            }
            case STARVE: {
                return mainConfig.getPassiveDebuffIdentifierStarve();
            }
            case TOXIC: {
                return mainConfig.getPassiveDebuffIdentifierToxic();
            }
            case GLOW: {
                return mainConfig.getPassiveDebuffIdentifierGlow();
            }
            case UNLUCK: {
                return mainConfig.getPassiveDebuffIdentifierUnluck();
            }
            case WEAK: {
                return mainConfig.getPassiveDebuffIdentifierWeak();
            }
            case WITHER: {
                return mainConfig.getPassiveDebuffIdentifierWither();
            }
            default: {
                return null;
            }
        }
    }
    
    public final PotionEffectType getPotion() {
        switch (this) {
            case ABSORB: {
                return PotionEffectType.ABSORPTION;
            }
            case FIRE_RESISTANCE: {
                return PotionEffectType.FIRE_RESISTANCE;
            }
            case HASTE: {
                return PotionEffectType.FAST_DIGGING;
            }
            case INVISIBILITY: {
                return PotionEffectType.INVISIBILITY;
            }
            case JUMP: {
                return PotionEffectType.JUMP;
            }
            case LUCK: {
                return PotionEffectType.LUCK;
            }
            case PROTECTION: {
                return PotionEffectType.DAMAGE_RESISTANCE;
            }
            case HEALTH_BOOST: {
                return PotionEffectType.HEALTH_BOOST;
            }
            case REGENERATION: {
                return PotionEffectType.REGENERATION;
            }
            case SATURATION: {
                return PotionEffectType.SATURATION;
            }
            case SPEED: {
                return PotionEffectType.SPEED;
            }
            case STRENGTH: {
                return PotionEffectType.INCREASE_DAMAGE;
            }
            case VISION: {
                return PotionEffectType.NIGHT_VISION;
            }
            case WATER_BREATHING: {
                return PotionEffectType.WATER_BREATHING;
            }
            case BLIND: {
                return PotionEffectType.BLINDNESS;
            }
            case CONFUSE: {
                return PotionEffectType.CONFUSION;
            }
            case FATIGUE: {
                return PotionEffectType.SLOW_DIGGING;
            }
            case SLOW: {
                return PotionEffectType.SLOW;
            }
            case STARVE: {
                return PotionEffectType.HUNGER;
            }
            case TOXIC: {
                return PotionEffectType.POISON;
            }
            case GLOW: {
                return PotionEffectType.GLOWING;
            }
            case UNLUCK: {
                return PotionEffectType.UNLUCK;
            }
            case WEAK: {
                return PotionEffectType.WEAKNESS;
            }
            case WITHER: {
                return PotionEffectType.WITHER;
            }
            default: {
                return null;
            }
        }
    }
    
    public static final PassiveEffectEnum get(final String buff) {
        PassiveEffectEnum[] values;
        for (int length = (values = values()).length, i = 0; i < length; ++i) {
            final PassiveEffectEnum key = values[i];
            for (final String aliases : key.getAliases()) {
                if (aliases.equalsIgnoreCase(buff)) {
                    return key;
                }
            }
        }
        return null;
    }
}
