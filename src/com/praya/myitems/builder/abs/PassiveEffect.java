// 
// Decompiled by Procyon v0.5.36
// 

package com.praya.myitems.builder.abs;

import com.praya.myitems.builder.passive.debuff.DebuffWither;
import com.praya.myitems.builder.passive.debuff.DebuffWeak;
import com.praya.myitems.builder.passive.debuff.DebuffUnluck;
import com.praya.myitems.builder.passive.debuff.DebuffGlow;
import com.praya.myitems.builder.passive.debuff.DebuffToxic;
import com.praya.myitems.builder.passive.debuff.DebuffStarve;
import com.praya.myitems.builder.passive.debuff.DebuffSlow;
import com.praya.myitems.builder.passive.debuff.DebuffFatigue;
import com.praya.myitems.builder.passive.debuff.DebuffConfuse;
import com.praya.myitems.builder.passive.debuff.DebuffBlind;
import com.praya.myitems.builder.passive.buff.BuffWaterBreathing;
import com.praya.myitems.builder.passive.buff.BuffVision;
import com.praya.myitems.builder.passive.buff.BuffStrength;
import com.praya.myitems.builder.passive.buff.BuffSpeed;
import com.praya.myitems.builder.passive.buff.BuffSaturation;
import com.praya.myitems.builder.passive.buff.BuffRegeneration;
import com.praya.myitems.builder.passive.buff.BuffProtection;
import com.praya.myitems.builder.passive.buff.BuffLuck;
import com.praya.myitems.builder.passive.buff.BuffJump;
import com.praya.myitems.builder.passive.buff.BuffInvisibility;
import com.praya.myitems.builder.passive.buff.BuffHealthBoost;
import com.praya.myitems.builder.passive.buff.BuffHaste;
import com.praya.myitems.builder.passive.buff.BuffFireResistance;
import com.praya.myitems.builder.passive.buff.BuffAbsorb;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.entity.Player;
import api.praya.myitems.builder.passive.PassiveEffectEnum;

public abstract class PassiveEffect
{
    protected final PassiveEffectEnum buffEnum;
    protected final int grade;
    
    public PassiveEffect(final PassiveEffectEnum buffEnum, final int grade) {
        this.buffEnum = buffEnum;
        this.grade = grade;
    }
    
    public abstract void cast(final Player p0);
    
    public final PassiveEffectEnum getPassiveEffectEnum() {
        return this.buffEnum;
    }
    
    public final int getGrade() {
        return this.grade;
    }
    
    public final PotionEffectType getPotion() {
        return this.buffEnum.getPotion();
    }
    
    public static final PassiveEffect getPassiveEffect(final PassiveEffectEnum buffEnum, final int grade) {
        switch (buffEnum) {
            case ABSORB: {
                return new BuffAbsorb(grade);
            }
            case FIRE_RESISTANCE: {
                return new BuffFireResistance(grade);
            }
            case HASTE: {
                return new BuffHaste(grade);
            }
            case HEALTH_BOOST: {
                return new BuffHealthBoost(grade);
            }
            case INVISIBILITY: {
                return new BuffInvisibility(grade);
            }
            case JUMP: {
                return new BuffJump(grade);
            }
            case LUCK: {
                return new BuffLuck(grade);
            }
            case PROTECTION: {
                return new BuffProtection(grade);
            }
            case REGENERATION: {
                return new BuffRegeneration(grade);
            }
            case SATURATION: {
                return new BuffSaturation(grade);
            }
            case SPEED: {
                return new BuffSpeed(grade);
            }
            case STRENGTH: {
                return new BuffStrength(grade);
            }
            case VISION: {
                return new BuffVision(grade);
            }
            case WATER_BREATHING: {
                return new BuffWaterBreathing(grade);
            }
            case BLIND: {
                return new DebuffBlind(grade);
            }
            case CONFUSE: {
                return new DebuffConfuse(grade);
            }
            case FATIGUE: {
                return new DebuffFatigue(grade);
            }
            case SLOW: {
                return new DebuffSlow(grade);
            }
            case STARVE: {
                return new DebuffStarve(grade);
            }
            case TOXIC: {
                return new DebuffToxic(grade);
            }
            case GLOW: {
                return new DebuffGlow(grade);
            }
            case UNLUCK: {
                return new DebuffUnluck(grade);
            }
            case WEAK: {
                return new DebuffWeak(grade);
            }
            case WITHER: {
                return new DebuffWither(grade);
            }
            default: {
                return null;
            }
        }
    }
}
