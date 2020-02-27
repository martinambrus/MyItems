// 
// Decompiled by Procyon v0.5.36
// 

package com.praya.myitems.config.plugin;

import com.praya.agarthalib.utility.EquipmentUtil;
import core.praya.agarthalib.enums.branch.MaterialEnum;
import java.util.Collection;
import org.bukkit.Material;
import java.util.ArrayList;
import com.praya.agarthalib.utility.DataUtil;
import org.bukkit.configuration.ConfigurationSection;
import java.util.Iterator;
import org.bukkit.ChatColor;
import java.io.File;
import com.praya.myitems.manager.plugin.DataManager;
import com.praya.myitems.manager.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import com.praya.agarthalib.utility.FileUtil;
import java.util.List;
import org.bukkit.inventory.ItemStack;
import org.bukkit.configuration.file.YamlConfiguration;
import com.praya.myitems.MyItems;
import com.praya.agarthalib.utility.TextUtil;
import org.bukkit.configuration.file.FileConfiguration;
import com.praya.myitems.builder.handler.HandlerConfig;

public class MainConfig extends HandlerConfig
{
    public static final String KEY_STATS;
    public static final String KEY_STATS_VALUE;
    public static final String KEY_PASSIVE_EFFECT;
    public static final String KEY_ABILITY_WEAPON;
    public static final String KEY_ABILITY_ARMOR;
    public static final String KEY_ABILITY_PERCENT;
    public static final String KEY_CLICK;
    public static final String KEY_COMMAND;
    public static final String KEY_SHOOT;
    public static final String KEY_SPECIAL;
    public static final String KEY_EXP_CURRENT;
    public static final String KEY_EXP_UP;
    public static final String KEY_COOLDOWN;
    public static final String KEY_ELEMENT;
    public static final String KEY_ELEMENT_VALUE;
    public static final String KEY_SOCKET_SLOT;
    public static final String KEY_SOCKET_LORE_GEMS;
    public static final String KEY_REQ_BOUND;
    public static final String KEY_REQ_LEVEL;
    public static final String KEY_REQ_PERMISSION;
    public static final String KEY_REQ_CLASS;
    public static final String KEY_SET_LINE;
    public static final String KEY_SET_COMPONENT_SELF;
    public static final String KEY_SET_COMPONENT_OTHER;
    private final FileConfiguration config;
    
    static {
        KEY_STATS = TextUtil.colorful("&0&1&r");
        KEY_STATS_VALUE = TextUtil.colorful("&0&2&r");
        KEY_PASSIVE_EFFECT = TextUtil.colorful("&0&3&r");
        KEY_ABILITY_WEAPON = TextUtil.colorful("&0&4&r");
        KEY_ABILITY_ARMOR = TextUtil.colorful("&2&5&r");
        KEY_ABILITY_PERCENT = TextUtil.colorful("&0&5&r");
        KEY_CLICK = TextUtil.colorful("&0&6&r");
        KEY_COMMAND = TextUtil.colorful("&0&7&r");
        KEY_SHOOT = TextUtil.colorful("&0&8&r");
        KEY_SPECIAL = TextUtil.colorful("&0&9&r");
        KEY_EXP_CURRENT = TextUtil.colorful("&1&0&r");
        KEY_EXP_UP = TextUtil.colorful("&1&1&r");
        KEY_COOLDOWN = TextUtil.colorful("&1&2&r");
        KEY_ELEMENT = TextUtil.colorful("&1&3&r");
        KEY_ELEMENT_VALUE = TextUtil.colorful("&1&4&r");
        KEY_SOCKET_SLOT = TextUtil.colorful("&1&5&r");
        KEY_SOCKET_LORE_GEMS = TextUtil.colorful("&1&6&r");
        KEY_REQ_BOUND = TextUtil.colorful("&2&1&r");
        KEY_REQ_LEVEL = TextUtil.colorful("&2&2&r");
        KEY_REQ_PERMISSION = TextUtil.colorful("&2&3&r");
        KEY_REQ_CLASS = TextUtil.colorful("&2&4&r");
        KEY_SET_LINE = TextUtil.colorful("&3&0&r");
        KEY_SET_COMPONENT_SELF = TextUtil.colorful("&3&1&r");
        KEY_SET_COMPONENT_OTHER = TextUtil.colorful("&3&2&r");
    }
    
    private MainConfig(final MyItems plugin) {
        super(plugin);
        this.config = (FileConfiguration)new YamlConfiguration();
    }
    
    public static final MainConfig getInstance() {
        return MainConfigHelper.instance;
    }
    
    public final String getGeneralVersion() {
        return this.config.getString("Configuration.General.Version");
    }
    
    public final String getGeneralLocale() {
        return this.config.getString("Configuration.General.Locale");
    }
    
    public final boolean isMetricsMessage() {
        return this.config.getBoolean("Configuration.Metrics.Message");
    }
    
    public final boolean isHookMessage() {
        return this.config.getBoolean("Configuration.Hook.Message");
    }
    
    public final String getUtilityTooltip() {
        return this.config.getString("Configuration.Utility.Tooltip");
    }
    
    public final String getUtilityCurrency() {
        return this.config.getString("Configuration.Utility.Currency");
    }
    
    public final double getEffectRange() {
        return this.config.getDouble("Configuration.Effect.Range");
    }
    
    public final int getListContent() {
        return this.config.getInt("Configuration.List.Content");
    }
    
    public final boolean isModifierEnableVanillaDamage() {
        return this.config.getBoolean("Configuration.Modifier.Enable_Vanilla_Damage");
    }
    
    public final boolean isModifierEnableVanillaFormulaArmor() {
        return this.config.getBoolean("Configuration.Modifier.Enable_Vanilla_Formula_Armor");
    }
    
    public final boolean isModifierEnableCustomModifier() {
        return this.config.getBoolean("Configuration.Modifier.Enable_Custom_Modifier");
    }
    
    public final boolean isModifierEnableFlatDamage() {
        return this.config.getBoolean("Configuration.Modifier.Enable_Flat_Damage");
    }
    
    public final boolean isModifierEnableBalancingSystem() {
        return this.config.getBoolean("Configuration.Modifier.Enable_Balancing_System");
    }
    
    public final boolean isModifierEnableBalancingMob() {
        return this.config.getBoolean("Configuration.Modifier.Enable_Balancing_Mob");
    }
    
    public final boolean isModifierEnableCustomCritical() {
        return this.config.getBoolean("Configuration.Modifier.Enable_Custom_Critical");
    }
    
    public final boolean isModifierEnableCustomMobDamage() {
        return this.config.getBoolean("Configuration.Modifier.Enable_Custom_Mob_Damage");
    }
    
    public final boolean isModifierDefenseAffectEntityExplosion() {
        return this.config.getBoolean("Configuration.Modifier.Defense_Affect_Entity_Explosion");
    }
    
    public final boolean isModifierDefenseAffectBlockExplosion() {
        return this.config.getBoolean("Configuration.Modifier.Defense_Affect_Block_Explosion");
    }
    
    public final boolean isModifierDefenseAffectCustom() {
        return this.config.getBoolean("Configuration.Modifier.Defense_Affect_Custom");
    }
    
    public final boolean isModifierDefenseAffectContact() {
        return this.config.getBoolean("Configuration.Modifier.Defense_Affect_Contact");
    }
    
    public final boolean isModifierDefenseAffectFall() {
        return this.config.getBoolean("Configuration.Modifier.Defense_Affect_Fall");
    }
    
    public final boolean isModifierDefenseAffectFallingBlock() {
        return this.config.getBoolean("Configuration.Modifier.Defense_Affect_Falling_Block");
    }
    
    public final boolean isModifierDefenseAffectThorn() {
        return this.config.getBoolean("Configuration.Modifier.Defense_Affect_Thorn");
    }
    
    public final String getModifierCriticalAttackType() {
        return this.config.getString("Configuration.Modifier.Critical_Attack_Type");
    }
    
    public final double getModifierModusValue() {
        return this.config.getDouble("Configuration.Modifier.Modus_Value");
    }
    
    public final double getModifierScaleDamageVanilla() {
        return this.config.getDouble("Configuration.Modifier.Scale_Damage_Vanilla");
    }
    
    public final double getModifierScaleDamageCustom() {
        return this.config.getDouble("Configuration.Modifier.Scale_Damage_Custom");
    }
    
    public final double getModifierScaleDamageOverall() {
        return this.config.getDouble("Configuration.Modifier.Scale_Damage_Overall");
    }
    
    public final double getModifierScaleDefenseOverall() {
        return this.config.getDouble("Configuration.Modifier.Scale_Defense_Overall");
    }
    
    public final double getModifierScaleDefensePhysic() {
        return this.config.getDouble("Configuration.Modifier.Scale_Defense_Physic");
    }
    
    public final double getModifierScaleAbsorbEffect() {
        return this.config.getDouble("Configuration.Modifier.Scale_Absorb_Effect");
    }
    
    public final double getModifierScaleMobDamageReceive() {
        return this.config.getDouble("Configuration.Modifier.Scale_Mob_Damage_Receive");
    }
    
    public final double getModifierScaleExpOffHand() {
        return this.config.getDouble("Configuration.Modifier.Scale_Exp_OffHand");
    }
    
    public final double getModifierScaleExpArmor() {
        return this.config.getDouble("Configuration.Modifier.Scale_Exp_Armor");
    }
    
    public final double getDropExpPlayer() {
        return this.config.getDouble("Configuration.Drop.Exp_Player");
    }
    
    public final double getDropExpMobs() {
        return this.config.getDouble("Configuration.Drop.Exp_Mobs");
    }
    
    public final String getSupportTypeLevel() {
        return this.config.getString("Configuration.Support.Type_Level");
    }
    
    public final String getSupportTypeClass() {
        return this.config.getString("Configuration.Support.Type_Class");
    }
    
    public final boolean isStatsEnableItemUniversal() {
        return this.config.getBoolean("Configuration.Stats.Enable_Item_Universal");
    }
    
    public final boolean isStatsEnableItemBroken() {
        return this.config.getBoolean("Configuration.Stats.Enable_Item_Broken");
    }
    
    public final boolean isStatsEnableItemBrokenMessage() {
        return this.config.getBoolean("Configuration.Stats.Enable_Item_Broken_Message");
    }
    
    public final boolean isStatsEnableMaxHealth() {
        return this.config.getBoolean("Configuration.Stats.Enable_Max_Health");
    }
    
    public final String getStatsFormatValue() {
        return this.config.getString("Configuration.Stats.Format_Value");
    }
    
    public final String getStatsFormatExp() {
        return this.config.getString("Configuration.Stats.Format_Exp");
    }
    
    public final String getStatsColor() {
        return this.config.getString("Configuration.Stats.Color");
    }
    
    public final String getStatsColorValue() {
        return this.config.getString("Configuration.Stats.Color_Value");
    }
    
    public final String getStatsColorExpCurrent() {
        return this.config.getString("Configuration.Stats.Color_Exp_Current");
    }
    
    public final String getStatsColorExpUp() {
        return this.config.getString("Configuration.Stats.Color_Exp_Up");
    }
    
    public final String getStatsLorePositiveValue() {
        return this.config.getString("Configuration.Stats.Lore_Positive_Value");
    }
    
    public final String getStatsLoreNegativeValue() {
        return this.config.getString("Configuration.Stats.Lore_Negative_Value");
    }
    
    public final String getStatsLoreRangeSymbol() {
        return this.config.getString("Configuration.Stats.Lore_Range_Symbol");
    }
    
    public final String getStatsLoreDividerSymbol() {
        return this.config.getString("Configuration.Stats.Lore_Divider_Symbol");
    }
    
    public final String getStatsLoreMultiplierSymbol() {
        return this.config.getString("Configuration.Stats.Lore_Multiplier_Symbol");
    }
    
    public final double getStatsScaleOffHandValue() {
        return this.config.getDouble("Configuration.Stats.Scale_OffHand_Value");
    }
    
    public final double getStatsScaleUpValue() {
        return this.config.getDouble("Configuration.Stats.Scale_Up_Value");
    }
    
    public final double getStatsScaleArmorShield() {
        return this.config.getDouble("Configuration.Stats.Scale_Armor_Shield");
    }
    
    public final int getStatsMaxLevelValue() {
        return this.config.getInt("Configuration.Stats.Max_Level_Value");
    }
    
    public final String getStatsIdentifierDamage() {
        return this.config.getString("Configuration.Stats.Identifier_Damage");
    }
    
    public final String getStatsIdentifierPenetration() {
        return this.config.getString("Configuration.Stats.Identifier_Penetration");
    }
    
    public final String getStatsIdentifierPvPDamage() {
        return this.config.getString("Configuration.Stats.Identifier_PvP_Damage");
    }
    
    public final String getStatsIdentifierPvEDamage() {
        return this.config.getString("Configuration.Stats.Identifier_PvE_Damage");
    }
    
    public final String getStatsIdentifierDefense() {
        return this.config.getString("Configuration.Stats.Identifier_Defense");
    }
    
    public final String getStatsIdentifierPvPDefense() {
        return this.config.getString("Configuration.Stats.Identifier_PvP_Defense");
    }
    
    public final String getStatsIdentifierPvEDefense() {
        return this.config.getString("Configuration.Stats.Identifier_PvE_Defense");
    }
    
    public final String getStatsIdentifierHealth() {
        return this.config.getString("Configuration.Stats.Identifier_Health");
    }
    
    public final String getStatsIdentifierHealthRegen() {
        return this.config.getString("Configuration.Stats.Identifier_Health_Regen");
    }
    
    public final String getStatsIdentifierStaminaMax() {
        return this.config.getString("Configuration.Stats.Identifier_Stamina_Max");
    }
    
    public final String getStatsIdentifierStaminaRegen() {
        return this.config.getString("Configuration.Stats.Identifier_Stamina_Regen");
    }
    
    public final String getStatsIdentifierAttackAoERadius() {
        return this.config.getString("Configuration.Stats.Identifier_Attack_AoE_Radius");
    }
    
    public final String getStatsIdentifierAttackAoEDamage() {
        return this.config.getString("Configuration.Stats.Identifier_Attack_AoE_Damage");
    }
    
    public final String getStatsIdentifierCriticalChance() {
        return this.config.getString("Configuration.Stats.Identifier_Critical_Chance");
    }
    
    public final String getStatsIdentifierCriticalDamage() {
        return this.config.getString("Configuration.Stats.Identifier_Critical_Damage");
    }
    
    public final String getStatsIdentifierBlockAmount() {
        return this.config.getString("Configuration.Stats.Identifier_Block_Amount");
    }
    
    public final String getStatsIdentifierBlockRate() {
        return this.config.getString("Configuration.Stats.Identifier_Block_Rate");
    }
    
    public final String getStatsIdentifierHitRate() {
        return this.config.getString("Configuration.Stats.Identifier_Hit_Rate");
    }
    
    public final String getStatsIdentifierDodgeRate() {
        return this.config.getString("Configuration.Stats.Identifier_Dodge_Rate");
    }
    
    public final String getStatsIdentifierFishingChance() {
        return this.config.getString("Configuration.Stats.Identifier_Fishing_Chance");
    }
    
    public final String getStatsIdentifierFishingPower() {
        return this.config.getString("Configuration.Stats.Identifier_Fishing_Power");
    }
    
    public final String getStatsIdentifierFishingSpeed() {
        return this.config.getString("Configuration.Stats.Identifier_Fishing_Speed");
    }
    
    public final String getStatsIdentifierLuresMaxTension() {
        return this.config.getString("Configuration.Stats.Identifier_Lures_Max_Tension");
    }
    
    public final String getStatsIdentifierLuresEndurance() {
        return this.config.getString("Configuration.Stats.Identifier_Lures_Endurance");
    }
    
    public final String getStatsIdentifierDurability() {
        return this.config.getString("Configuration.Stats.Identifier_Durability");
    }
    
    public final String getStatsIdentifierLevel() {
        return this.config.getString("Configuration.Stats.Identifier_Level");
    }
    
    public final String getAbilityFormat() {
        return this.config.getString("Configuration.Ability.Format");
    }
    
    public final String getAbilityColor() {
        return this.config.getString("Configuration.Ability.Color");
    }
    
    public final String getAbilityColorPercent() {
        return this.config.getString("Configuration.Ability.Color_Percent");
    }
    
    public final boolean isAbilityWeaponEnableOffHand() {
        return this.config.getBoolean("Configuration.Ability.Weapon.Enable_OffHand");
    }
    
    public final String getAbilityWeaponIdentifierAirShock() {
        return this.config.getString("Configuration.Ability.Weapon.Identifier_Air_Shock");
    }
    
    public final String getAbilityWeaponIdentifierBadLuck() {
        return this.config.getString("Configuration.Ability.Weapon.Identifier_Bad_Luck");
    }
    
    public final String getAbilityWeaponIdentifierBlind() {
        return this.config.getString("Configuration.Ability.Weapon.Identifier_Blind");
    }
    
    public final String getAbilityWeaponIdentifierBubbleDeflector() {
        return this.config.getString("Configuration.Ability.Weapon.Identifier_Bubble_Deflector");
    }
    
    public final String getAbilityWeaponIdentifierCannibalism() {
        return this.config.getString("Configuration.Ability.Weapon.Identifier_Cannibalism");
    }
    
    public final String getAbilityWeaponIdentifierConfuse() {
        return this.config.getString("Configuration.Ability.Weapon.Identifier_Confuse");
    }
    
    public final String getAbilityWeaponIdentifierCurse() {
        return this.config.getString("Configuration.Ability.Weapon.Identifier_Curse");
    }
    
    public final String getAbilityWeaponIdentifierDarkFlame() {
        return this.config.getString("Configuration.Ability.Weapon.Identifier_Dark_Flame");
    }
    
    public final String getAbilityWeaponIdentifierDarkImpact() {
        return this.config.getString("Configuration.Ability.Weapon.Identifier_Dark_Impact");
    }
    
    public final String getAbilityWeaponIdentifierFlame() {
        return this.config.getString("Configuration.Ability.Weapon.Identifier_Flame");
    }
    
    public final String getAbilityWeaponIdentifierFlameWheel() {
        return this.config.getString("Configuration.Ability.Weapon.Identifier_Flame_Wheel");
    }
    
    public final String getAbilityWeaponIdentifierFreeze() {
        return this.config.getString("Configuration.Ability.Weapon.Identifier_Freeze");
    }
    
    public final String getAbilityWeaponIdentifierHarm() {
        return this.config.getString("Configuration.Ability.Weapon.Identifier_Harm");
    }
    
    public final String getAbilityWeaponIdentifierHungry() {
        return this.config.getString("Configuration.Ability.Weapon.Identifier_Hungry");
    }
    
    public final String getAbilityWeaponIdentifierLevitation() {
        return this.config.getString("Configuration.Ability.Weapon.Identifier_Levitation");
    }
    
    public final String getAbilityWeaponIdentifierLightning() {
        return this.config.getString("Configuration.Ability.Weapon.Identifier_Lightning");
    }
    
    public final String getAbilityWeaponIdentifierPoison() {
        return this.config.getString("Configuration.Ability.Weapon.Identifier_Poison");
    }
    
    public final String getAbilityWeaponIdentifierRoots() {
        return this.config.getString("Configuration.Ability.Weapon.Identifier_Roots");
    }
    
    public final String getAbilityWeaponIdentifierSlowness() {
        return this.config.getString("Configuration.Ability.Weapon.Identifier_Slowness");
    }
    
    public final String getAbilityWeaponIdentifierTired() {
        return this.config.getString("Configuration.Ability.Weapon.Identifier_Tired");
    }
    
    public final String getAbilityWeaponIdentifierVampirism() {
        return this.config.getString("Configuration.Ability.Weapon.Identifier_Vampirism");
    }
    
    public final String getAbilityWeaponIdentifierVenomBlast() {
        return this.config.getString("Configuration.Ability.Weapon.Identifier_Venom_Blast");
    }
    
    public final String getAbilityWeaponIdentifierVenomSpread() {
        return this.config.getString("Configuration.Ability.Weapon.Identifier_Venom_Spread");
    }
    
    public final String getAbilityWeaponIdentifierWeakness() {
        return this.config.getString("Configuration.Ability.Weapon.Identifier_Weakness");
    }
    
    public final String getAbilityWeaponIdentifierWither() {
        return this.config.getString("Configuration.Ability.Weapon.Identifier_Wither");
    }
    
    public final boolean isPassiveEnableGradeCalculation() {
        return this.config.getBoolean("Configuration.Passive.Enable_Grade_Calculation");
    }
    
    public final boolean isPassiveEnableHand() {
        return this.config.getBoolean("Configuration.Passive.Enable_Hand");
    }
    
    public final int getPassivePeriodEffect() {
        return this.config.getInt("Configuration.Passive.Period_Effect");
    }
    
    public final String getPassiveBuffFormat() {
        return this.config.getString("Configuration.Passive.Buff.Format");
    }
    
    public final String getPassiveBuffColor() {
        return this.config.getString("Configuration.Passive.Buff.Color");
    }
    
    public final String getPassiveBuffIdentifierStrength() {
        return this.config.getString("Configuration.Passive.Buff.Identifier_Strength");
    }
    
    public final String getPassiveBuffIdentifierProtection() {
        return this.config.getString("Configuration.Passive.Buff.Identifier_Protection");
    }
    
    public final String getPassiveBuffIdentifierVision() {
        return this.config.getString("Configuration.Passive.Buff.Identifier_Vision");
    }
    
    public final String getPassiveBuffIdentifierJump() {
        return this.config.getString("Configuration.Passive.Buff.Identifier_Jump");
    }
    
    public final String getPassiveBuffIdentifierAbsorb() {
        return this.config.getString("Configuration.Passive.Buff.Identifier_Absorb");
    }
    
    public final String getPassiveBuffIdentifierFireResistance() {
        return this.config.getString("Configuration.Passive.Buff.Identifier_Fire_Resistance");
    }
    
    public final String getPassiveBuffIdentifierInvisibility() {
        return this.config.getString("Configuration.Passive.Buff.Identifier_Invisibility");
    }
    
    public final String getPassiveBuffIdentifierLuck() {
        return this.config.getString("Configuration.Passive.Buff.Identifier_Luck");
    }
    
    public final String getPassiveBuffIdentifierHealthBoost() {
        return this.config.getString("Configuration.Passive.Buff.Identifier_Health_Boost");
    }
    
    public final String getPassiveBuffIdentifierRegeneration() {
        return this.config.getString("Configuration.Passive.Buff.Identifier_Regeneration");
    }
    
    public final String getPassiveBuffIdentifierSaturation() {
        return this.config.getString("Configuration.Passive.Buff.Identifier_Saturation");
    }
    
    public final String getPassiveBuffIdentifierSpeed() {
        return this.config.getString("Configuration.Passive.Buff.Identifier_Speed");
    }
    
    public final String getPassiveBuffIdentifierWaterBreathing() {
        return this.config.getString("Configuration.Passive.Buff.Identifier_Water_Breathing");
    }
    
    public final String getPassiveBuffIdentifierHaste() {
        return this.config.getString("Configuration.Passive.Buff.Identifier_Haste");
    }
    
    public final String getPassiveDebuffFormat() {
        return this.config.getString("Configuration.Passive.Debuff.Format");
    }
    
    public final String getPassiveDebuffColor() {
        return this.config.getString("Configuration.Passive.Debuff.Color");
    }
    
    public final String getPassiveDebuffIdentifierWeak() {
        return this.config.getString("Configuration.Passive.Debuff.Identifier_Weak");
    }
    
    public final String getPassiveDebuffIdentifierSlow() {
        return this.config.getString("Configuration.Passive.Debuff.Identifier_Slow");
    }
    
    public final String getPassiveDebuffIdentifierBlind() {
        return this.config.getString("Configuration.Passive.Debuff.Identifier_Blind");
    }
    
    public final String getPassiveDebuffIdentifierConfuse() {
        return this.config.getString("Configuration.Passive.Debuff.Identifier_Confuse");
    }
    
    public final String getPassiveDebuffIdentifierStarve() {
        return this.config.getString("Configuration.Passive.Debuff.Identifier_Starve");
    }
    
    public final String getPassiveDebuffIdentifierToxic() {
        return this.config.getString("Configuration.Passive.Debuff.Identifier_Toxic");
    }
    
    public final String getPassiveDebuffIdentifierGlow() {
        return this.config.getString("Configuration.Passive.Debuff.Identifier_Glow");
    }
    
    public final String getPassiveDebuffIdentifierFatigue() {
        return this.config.getString("Configuration.Passive.Debuff.Identifier_Fatigue");
    }
    
    public final String getPassiveDebuffIdentifierWither() {
        return this.config.getString("Configuration.Passive.Debuff.Identifier_Wither");
    }
    
    public final String getPassiveDebuffIdentifierUnluck() {
        return this.config.getString("Configuration.Passive.Debuff.Identifier_Unluck");
    }
    
    public final boolean isPowerEnableMessageCooldown() {
        return this.config.getBoolean("Configuration.Power.Enable_Message_Cooldown");
    }
    
    public final String getPowerFormat() {
        return this.config.getString("Configuration.Power.Format");
    }
    
    public final String getPowerColorClick() {
        return this.config.getString("Configuration.Power.Color_Click");
    }
    
    public final String getPowerColorType() {
        return this.config.getString("Configuration.Power.Color_Type");
    }
    
    public final String getPowerColorCooldown() {
        return this.config.getString("Configuration.Power.Color_Cooldown");
    }
    
    public final String getPowerClickLeft() {
        return this.config.getString("Configuration.Power.Click_Left");
    }
    
    public final String getPowerClickRight() {
        return this.config.getString("Configuration.Power.Click_Right");
    }
    
    public final String getPowerClickShiftLeft() {
        return this.config.getString("Configuration.Power.Click_Shift_Left");
    }
    
    public final String getPowerClickShiftRight() {
        return this.config.getString("Configuration.Power.Click_Shift_Right");
    }
    
    public final String getPowerProjectileIdentifierArrow() {
        return this.config.getString("Configuration.Power.Projectile.Identifier_Arrow");
    }
    
    public final String getPowerProjectileIdentifierSnowBall() {
        return this.config.getString("Configuration.Power.Projectile.Identifier_Snow_Ball");
    }
    
    public final String getPowerProjectileIdentifierEgg() {
        return this.config.getString("Configuration.Power.Projectile.Identifier_Egg");
    }
    
    public final String getPowerProjectileIdentifierFlameArrow() {
        return this.config.getString("Configuration.Power.Projectile.Identifier_Flame_Arrow");
    }
    
    public final String getPowerProjectileIdentifierFlameBall() {
        return this.config.getString("Configuration.Power.Projectile.Identifier_Flame_Ball");
    }
    
    public final String getPowerProjectileIdentifierFlameEgg() {
        return this.config.getString("Configuration.Power.Projectile.Identifier_Flame_Egg");
    }
    
    public final String getPowerProjectileIdentifierSmallFireball() {
        return this.config.getString("Configuration.Power.Projectile.Identifier_Small_Fireball");
    }
    
    public final String getPowerProjectileIdentifierLargeFireball() {
        return this.config.getString("Configuration.Power.Projectile.Identifier_Large_Fireball");
    }
    
    public final String getPowerProjectileIdentifierWitherSkull() {
        return this.config.getString("Configuration.Power.Projectile.Identifier_Wither_Skull");
    }
    
    public final String getPowerSpecialIdentifierBlink() {
        return this.config.getString("Configuration.Power.Special.Identifier_Blink");
    }
    
    public final String getPowerSpecialIdentifierFissure() {
        return this.config.getString("Configuration.Power.Special.Identifier_Fissure");
    }
    
    public final String getPowerSpecialIdentifierIceSpikes() {
        return this.config.getString("Configuration.Power.Special.Identifier_Ice_Spikes");
    }
    
    public final String getPowerSpecialIdentifierAmaterasu() {
        return this.config.getString("Configuration.Power.Special.Identifier_Amaterasu");
    }
    
    public final String getPowerSpecialIdentifierNeroBeam() {
        return this.config.getString("Configuration.Power.Special.Identifier_Nero_Beam");
    }
    
    public final String getElementFormat() {
        return this.config.getString("Configuration.Element.Format");
    }
    
    public final String getElementColor() {
        return this.config.getString("Configuration.Element.Color");
    }
    
    public final String getElementColorValue() {
        return this.config.getString("Configuration.Element.Color_Value");
    }
    
    public final String getSocketFormatSlot() {
        return this.config.getString("Configuration.Socket.Format_Slot");
    }
    
    public final String getSocketFormatSlotEmpty() {
        return this.config.getString("Configuration.Socket.Format_Slot_Empty");
    }
    
    public final String getSocketFormatSlotLocked() {
        return this.config.getString("Configuration.Socket.Format_Slot_Locked");
    }
    
    public final String getSocketTypeItemWeapon() {
        return this.config.getString("Configuration.Socket.Type_Item_Weapon");
    }
    
    public final String getSocketTypeItemArmor() {
        return this.config.getString("Configuration.Socket.Type_Item_Armor");
    }
    
    public final String getSocketTypeItemUniversal() {
        return this.config.getString("Configuration.Socket.Type_Item_Universal");
    }
    
    public final double getSocketCostSocket() {
        return this.config.getInt("Configuration.Socket.Cost_Socket");
    }
    
    public final double getSocketCostUnlock() {
        return this.config.getInt("Configuration.Socket.Cost_Unlock");
    }
    
    public final double getSocketCostDesocket() {
        return this.config.getInt("Configuration.Socket.Cost_Desocket");
    }
    
    public final ItemStack getSocketItemRodUnlock() {
        final ItemStack socketItemRodUnlock = this.config.getItemStack("Configuration.Socket.Item.Rod_Unlock");
        return (socketItemRodUnlock != null) ? socketItemRodUnlock.clone() : null;
    }
    
    public final ItemStack getSocketItemRodRemove() {
        final ItemStack socketItemRodRemove = this.config.getItemStack("Configuration.Socket.Item.Rod_Remove");
        return (socketItemRodRemove != null) ? socketItemRodRemove.clone() : null;
    }
    
    public final String getSocketColorSlot() {
        return this.config.getString("Configuration.Socket.Color_Slot");
    }
    
    public final String getRequirementFormatLevel() {
        return this.config.getString("Configuration.Requirement.Format_Level");
    }
    
    public final String getRequirementFormatPermission() {
        return this.config.getString("Configuration.Requirement.Format_Permission");
    }
    
    public final String getRequirementFormatClass() {
        return this.config.getString("Configuration.Requirement.Format_Class");
    }
    
    public final String getRequirementFormatSoulUnbound() {
        return this.config.getString("Configuration.Requirement.Format_Soul_Unbound");
    }
    
    public final String getRequirementFormatSoulBound() {
        return this.config.getString("Configuration.Requirement.Format_Soul_Bound");
    }
    
    public final String getRequirementColorSoulBound() {
        return this.config.getString("Configuration.Requirement.Color_Soul_Bound");
    }
    
    public final String getRequirementColorLevel() {
        return this.config.getString("Configuration.Requirement.Color_Level");
    }
    
    public final String getRequirementColorPermission() {
        return this.config.getString("Configuration.Requirement.Color_Permission");
    }
    
    public final String getRequirementColorClass() {
        return this.config.getString("Configuration.Requirement.Color_Class");
    }
    
    public final List<String> getSetFormat() {
        return (List<String>)this.config.getStringList("Configuration.Set.Format");
    }
    
    public final String getSetFormatComponent() {
        return this.config.getString("Configuration.Set.Format_Component");
    }
    
    public final String getSetFormatBonus() {
        return this.config.getString("Configuration.Set.Format_Bonus");
    }
    
    public final String getSetLoreComponentActive() {
        return this.config.getString("Configuration.Set.Lore_Component_Active");
    }
    
    public final String getSetLoreComponentInactive() {
        return this.config.getString("Configuration.Set.Lore_Component_Inactive");
    }
    
    public final String getSetLoreBonusActive() {
        return this.config.getString("Configuration.Set.Lore_Bonus_Active");
    }
    
    public final String getSetLoreBonusInactive() {
        return this.config.getString("Configuration.Set.Lore_Bonus_Inactive");
    }
    
    public final boolean isMiscEnableParticlePotion() {
        return this.config.getBoolean("Configuration.Misc.Enable_Particle_Potion");
    }
    
    public final void setup() {
        final PluginManager pluginManager = this.plugin.getPluginManager();
        final DataManager dataManager = pluginManager.getDataManager();
        final String pathFile = dataManager.getPath("Path_File_Config");
        this.moveOldFile();
        final File file = FileUtil.getFile((JavaPlugin)this.plugin, pathFile);
        if (!file.exists()) {
            FileUtil.saveResource((JavaPlugin)this.plugin, pathFile);
        }
        final FileConfiguration configurationResource = FileUtil.getFileConfigurationResource((JavaPlugin)this.plugin, pathFile);
        final FileConfiguration configurationFile = FileUtil.getFileConfiguration(file);
        this.loadConfig(this.config, configurationResource);
        this.loadConfig(this.config, configurationFile);
        this.loadOldConfig(this.config, configurationFile);
        this.loadConfigColor(this.config);
    }
    
    private final void loadConfig(final FileConfiguration config, final FileConfiguration source) {
        for (final String key : source.getKeys(false)) {
            if (key.equalsIgnoreCase("Configuration") || key.equalsIgnoreCase("Config")) {
                final ConfigurationSection dataSection = source.getConfigurationSection(key);
                for (final String data : dataSection.getKeys(false)) {
                    if (data.equalsIgnoreCase("General")) {
                        final ConfigurationSection generalDataSection = dataSection.getConfigurationSection(data);
                        for (final String generalData : generalDataSection.getKeys(false)) {
                            if (generalData.equalsIgnoreCase("Version")) {
                                final String path = "Configuration.General.Version";
                                final String generalVersion = generalDataSection.getString(generalData);
                                config.set("Configuration.General.Version", (Object)generalVersion);
                            }
                            else {
                                if (!generalData.equalsIgnoreCase("Locale")) {
                                    continue;
                                }
                                final String path = "Configuration.General.Locale";
                                final String generalLocale = generalDataSection.getString(generalData);
                                config.set("Configuration.General.Locale", (Object)generalLocale);
                            }
                        }
                    }
                    else if (data.equalsIgnoreCase("Metrics")) {
                        final ConfigurationSection metricsDataSection = dataSection.getConfigurationSection(data);
                        for (final String metricsData : metricsDataSection.getKeys(false)) {
                            if (metricsData.equalsIgnoreCase("Message")) {
                                final String path = "Configuration.Metrics.Message";
                                final boolean metricsMessage = metricsDataSection.getBoolean(metricsData);
                                config.set("Configuration.Metrics.Message", (Object)metricsMessage);
                            }
                        }
                    }
                    else if (data.equalsIgnoreCase("Hook")) {
                        final ConfigurationSection hookDataSection = dataSection.getConfigurationSection(data);
                        for (final String hookData : hookDataSection.getKeys(false)) {
                            if (hookData.equalsIgnoreCase("Message")) {
                                final String path = "Configuration.Hook.Message";
                                final boolean hookMessage = hookDataSection.getBoolean(hookData);
                                config.set("Configuration.Hook.Message", (Object)hookMessage);
                            }
                        }
                    }
                    else if (data.equalsIgnoreCase("Utility")) {
                        final ConfigurationSection utilityDataSection = dataSection.getConfigurationSection(data);
                        for (final String utilityData : utilityDataSection.getKeys(false)) {
                            if (utilityData.equalsIgnoreCase("Tooltip")) {
                                final String path = "Configuration.Utility.Tooltip";
                                final String utilityTooltip = utilityDataSection.getString(utilityData);
                                config.set("Configuration.Utility.Tooltip", (Object)utilityTooltip);
                            }
                            else {
                                if (!utilityData.equalsIgnoreCase("Currency")) {
                                    continue;
                                }
                                final String path = "Configuration.Utility.Currency";
                                final String utilityCurrency = utilityDataSection.getString(utilityData);
                                config.set("Configuration.Utility.Currency", (Object)utilityCurrency);
                            }
                        }
                    }
                    else if (data.equalsIgnoreCase("Effect")) {
                        final ConfigurationSection effectDataSection = dataSection.getConfigurationSection(data);
                        for (final String effectData : effectDataSection.getKeys(false)) {
                            if (effectData.equalsIgnoreCase("Range")) {
                                final String path = "Configuration.Effect.Range";
                                final double effectRange = effectDataSection.getDouble(effectData);
                                config.set("Configuration.Effect.Range", (Object)effectRange);
                            }
                        }
                    }
                    else if (data.equalsIgnoreCase("List")) {
                        final ConfigurationSection listDataSection = dataSection.getConfigurationSection(data);
                        for (final String listData : listDataSection.getKeys(false)) {
                            if (listData.equalsIgnoreCase("Content")) {
                                final String path = "Configuration.List.Content";
                                final int listContent = listDataSection.getInt(listData);
                                config.set("Configuration.List.Content", (Object)listContent);
                            }
                        }
                    }
                    else if (data.equalsIgnoreCase("Modifier")) {
                        final ConfigurationSection modifierDataSection = dataSection.getConfigurationSection(data);
                        for (final String modifierData : modifierDataSection.getKeys(false)) {
                            if (modifierData.equalsIgnoreCase("Enable_Vanilla_Damage")) {
                                final String path = "Configuration.Modifier.Enable_Vanilla_Damage";
                                final boolean modifierEnableVanillaDamage = modifierDataSection.getBoolean(modifierData);
                                config.set("Configuration.Modifier.Enable_Vanilla_Damage", (Object)modifierEnableVanillaDamage);
                            }
                            else if (modifierData.equalsIgnoreCase("Enable_Vanilla_Formula_Armor")) {
                                final String path = "Configuration.Modifier.Enable_Vanilla_Formula_Armor";
                                final boolean modifierEnableVanillaFormulaArmor = modifierDataSection.getBoolean(modifierData);
                                config.set("Configuration.Modifier.Enable_Vanilla_Formula_Armor", (Object)modifierEnableVanillaFormulaArmor);
                            }
                            else if (modifierData.equalsIgnoreCase("Enable_Custom_Modifier")) {
                                final String path = "Configuration.Modifier.Enable_Custom_Modifier";
                                final boolean modifierEnableCustomModifier = modifierDataSection.getBoolean(modifierData);
                                config.set("Configuration.Modifier.Enable_Custom_Modifier", (Object)modifierEnableCustomModifier);
                            }
                            else if (modifierData.equalsIgnoreCase("Enable_Flat_Damage")) {
                                final String path = "Configuration.Modifier.Enable_Flat_Damage";
                                final boolean modifierEnableFlatDamage = modifierDataSection.getBoolean(modifierData);
                                config.set("Configuration.Modifier.Enable_Flat_Damage", (Object)modifierEnableFlatDamage);
                            }
                            else if (modifierData.equalsIgnoreCase("Enable_Balancing_System")) {
                                final String path = "Configuration.Modifier.Enable_Balancing_System";
                                final boolean modifierEnableBalancingSystem = modifierDataSection.getBoolean(modifierData);
                                config.set("Configuration.Modifier.Enable_Balancing_System", (Object)modifierEnableBalancingSystem);
                            }
                            else if (modifierData.equalsIgnoreCase("Enable_Balancing_Mob")) {
                                final String path = "Configuration.Modifier.Enable_Balancing_Mob";
                                final boolean modifierEnableBalancingMob = modifierDataSection.getBoolean(modifierData);
                                config.set("Configuration.Modifier.Enable_Balancing_Mob", (Object)modifierEnableBalancingMob);
                            }
                            else if (modifierData.equalsIgnoreCase("Enable_Custom_Critical")) {
                                final String path = "Configuration.Modifier.Enable_Custom_Critical";
                                final boolean modifierEnableCustomCritical = modifierDataSection.getBoolean(modifierData);
                                config.set("Configuration.Modifier.Enable_Custom_Critical", (Object)modifierEnableCustomCritical);
                            }
                            else if (modifierData.equalsIgnoreCase("Enable_Custom_Mob_Damage")) {
                                final String path = "Configuration.Modifier.Enable_Custom_Mob_Damage";
                                final boolean modifierEnableCustomMobDamage = modifierDataSection.getBoolean(modifierData);
                                config.set("Configuration.Modifier.Enable_Custom_Mob_Damage", (Object)modifierEnableCustomMobDamage);
                            }
                            else if (modifierData.equalsIgnoreCase("Defense_Affect_Entity_Explosion")) {
                                final String path = "Configuration.Modifier.Defense_Affect_Entity_Explosion";
                                final boolean modifierDefenseAffectEntityExplosion = modifierDataSection.getBoolean(modifierData);
                                config.set("Configuration.Modifier.Defense_Affect_Entity_Explosion", (Object)modifierDefenseAffectEntityExplosion);
                            }
                            else if (modifierData.equalsIgnoreCase("Defense_Affect_Block_Explosion")) {
                                final String path = "Configuration.Modifier.Defense_Affect_Block_Explosion";
                                final boolean modifierDefenseAffectBlockExplosion = modifierDataSection.getBoolean(modifierData);
                                config.set("Configuration.Modifier.Defense_Affect_Block_Explosion", (Object)modifierDefenseAffectBlockExplosion);
                            }
                            else if (modifierData.equalsIgnoreCase("Defense_Affect_Custom")) {
                                final String path = "Configuration.Modifier.Defense_Affect_Custom";
                                final boolean modifierDefenseAffectCustom = modifierDataSection.getBoolean(modifierData);
                                config.set("Configuration.Modifier.Defense_Affect_Custom", (Object)modifierDefenseAffectCustom);
                            }
                            else if (modifierData.equalsIgnoreCase("Defense_Affect_Contact")) {
                                final String path = "Configuration.Modifier.Defense_Affect_Contact";
                                final boolean modifierDefenseAffectContact = modifierDataSection.getBoolean(modifierData);
                                config.set("Configuration.Modifier.Defense_Affect_Contact", (Object)modifierDefenseAffectContact);
                            }
                            else if (modifierData.equalsIgnoreCase("Defense_Affect_Fall")) {
                                final String path = "Configuration.Modifier.Defense_Affect_Fall";
                                final boolean modifierDefenseAffectFall = modifierDataSection.getBoolean(modifierData);
                                config.set("Configuration.Modifier.Defense_Affect_Fall", (Object)modifierDefenseAffectFall);
                            }
                            else if (modifierData.equalsIgnoreCase("Defense_Affect_Falling_Block")) {
                                final String path = "Configuration.Modifier.Defense_Affect_Falling_Block";
                                final boolean modifierDefenseAffectFallingBlock = modifierDataSection.getBoolean(modifierData);
                                config.set("Configuration.Modifier.Defense_Affect_Falling_Block", (Object)modifierDefenseAffectFallingBlock);
                            }
                            else if (modifierData.equalsIgnoreCase("Defense_Affect_Thorn")) {
                                final String path = "Configuration.Modifier.Defense_Affect_Thorn";
                                final boolean modifierDefenseAffectThorn = modifierDataSection.getBoolean(modifierData);
                                config.set("Configuration.Modifier.Defense_Affect_Thorn", (Object)modifierDefenseAffectThorn);
                            }
                            else if (modifierData.equalsIgnoreCase("Critical_Attack_Type")) {
                                final String path = "Configuration.Modifier.Critical_Attack_Type";
                                final String modifierCriticalAttackType = modifierDataSection.getString(modifierData);
                                config.set("Configuration.Modifier.Critical_Attack_Type", (Object)modifierCriticalAttackType);
                            }
                            else if (modifierData.equalsIgnoreCase("Modus_Value")) {
                                final String path = "Configuration.Modifier.Modus_Value";
                                final double modifierModusValue = modifierDataSection.getDouble(modifierData);
                                config.set("Configuration.Modifier.Modus_Value", (Object)modifierModusValue);
                            }
                            else if (modifierData.equalsIgnoreCase("Scale_Damage_Vanilla")) {
                                final String path = "Configuration.Modifier.Scale_Damage_Vanilla";
                                final double modifierScaleDamageVanilla = modifierDataSection.getDouble(modifierData);
                                config.set("Configuration.Modifier.Scale_Damage_Vanilla", (Object)modifierScaleDamageVanilla);
                            }
                            else if (modifierData.equalsIgnoreCase("Scale_Damage_Custom")) {
                                final String path = "Configuration.Modifier.Scale_Damage_Custom";
                                final double modifierScaleDamageCustom = modifierDataSection.getDouble(modifierData);
                                config.set("Configuration.Modifier.Scale_Damage_Custom", (Object)modifierScaleDamageCustom);
                            }
                            else if (modifierData.equalsIgnoreCase("Scale_Damage_Overall")) {
                                final String path = "Configuration.Modifier.Scale_Damage_Overall";
                                final double modifierScaleDamageOverall = modifierDataSection.getDouble(modifierData);
                                config.set("Configuration.Modifier.Scale_Damage_Overall", (Object)modifierScaleDamageOverall);
                            }
                            else if (modifierData.equalsIgnoreCase("Scale_Defense_Overall")) {
                                final String path = "Configuration.Modifier.Scale_Defense_Overall";
                                final double modifierScaleDefenseOverall = modifierDataSection.getDouble(modifierData);
                                config.set("Configuration.Modifier.Scale_Defense_Overall", (Object)modifierScaleDefenseOverall);
                            }
                            else if (modifierData.equalsIgnoreCase("Scale_Defense_Physic")) {
                                final String path = "Configuration.Modifier.Scale_Defense_Physic";
                                final double modifierScaleDefensePhysic = modifierDataSection.getDouble(modifierData);
                                config.set("Configuration.Modifier.Scale_Defense_Physic", (Object)modifierScaleDefensePhysic);
                            }
                            else if (modifierData.equalsIgnoreCase("Scale_Absorb_Effect")) {
                                final String path = "Configuration.Modifier.Scale_Absorb_Effect";
                                final double modifierScaleAbsorbEffect = modifierDataSection.getDouble(modifierData);
                                config.set("Configuration.Modifier.Scale_Absorb_Effect", (Object)modifierScaleAbsorbEffect);
                            }
                            else if (modifierData.equalsIgnoreCase("Scale_Mob_Damage_Receive")) {
                                final String path = "Configuration.Modifier.Scale_Mob_Damage_Receive";
                                final double modifierScaleMobDamageReceive = modifierDataSection.getDouble(modifierData);
                                config.set("Configuration.Modifier.Scale_Mob_Damage_Receive", (Object)modifierScaleMobDamageReceive);
                            }
                            else if (modifierData.equalsIgnoreCase("Scale_Exp_OffHand")) {
                                final String path = "Configuration.Modifier.Scale_Exp_OffHand";
                                final double modifierScaleExpOffHand = modifierDataSection.getDouble(modifierData);
                                config.set("Configuration.Modifier.Scale_Exp_OffHand", (Object)modifierScaleExpOffHand);
                            }
                            else {
                                if (!modifierData.equalsIgnoreCase("Scale_Exp_Armor")) {
                                    continue;
                                }
                                final String path = "Configuration.Modifier.Scale_Exp_Armor";
                                final double modifierScaleExpArmor = modifierDataSection.getDouble(modifierData);
                                config.set("Configuration.Modifier.Scale_Exp_Armor", (Object)modifierScaleExpArmor);
                            }
                        }
                    }
                    else if (data.equalsIgnoreCase("Drop")) {
                        final ConfigurationSection dropDataSection = dataSection.getConfigurationSection(data);
                        for (final String dropData : dropDataSection.getKeys(false)) {
                            if (dropData.equalsIgnoreCase("Exp_Player")) {
                                final String path = "Configuration.Drop.Exp_Player";
                                final double dropExpPlayer = dropDataSection.getDouble(dropData);
                                config.set("Configuration.Drop.Exp_Player", (Object)dropExpPlayer);
                            }
                            else {
                                if (!dropData.equalsIgnoreCase("Exp_Mobs")) {
                                    continue;
                                }
                                final String path = "Configuration.Drop.Exp_Mobs";
                                final double dropExpMobs = dropDataSection.getDouble(dropData);
                                config.set("Configuration.Drop.Exp_Mobs", (Object)dropExpMobs);
                            }
                        }
                    }
                    else if (data.equalsIgnoreCase("Support")) {
                        final ConfigurationSection supportDataSection = dataSection.getConfigurationSection(data);
                        for (final String supportData : supportDataSection.getKeys(false)) {
                            if (supportData.equalsIgnoreCase("Type_Level")) {
                                final String path = "Configuration.Support.Type_Level";
                                final String supportTypeLevel = supportDataSection.getString(supportData);
                                config.set("Configuration.Support.Type_Level", (Object)supportTypeLevel);
                            }
                            else {
                                if (!supportData.equalsIgnoreCase("Type_Class")) {
                                    continue;
                                }
                                final String path = "Configuration.Support.Type_Class";
                                final String supportTypeClass = supportDataSection.getString(supportData);
                                config.set("Configuration.Support.Type_Class", (Object)supportTypeClass);
                            }
                        }
                    }
                    else if (data.equalsIgnoreCase("Stats")) {
                        final ConfigurationSection statsDataSection = dataSection.getConfigurationSection(data);
                        for (final String statsData : statsDataSection.getKeys(false)) {
                            if (statsData.equalsIgnoreCase("Enable_Item_Universal")) {
                                final String path = "Configuration.Stats.Enable_Item_Universal";
                                final boolean statsEnableItemUniversal = statsDataSection.getBoolean(statsData);
                                config.set("Configuration.Stats.Enable_Item_Universal", (Object)statsEnableItemUniversal);
                            }
                            else if (statsData.equalsIgnoreCase("Enable_Item_Broken")) {
                                final String path = "Configuration.Stats.Enable_Item_Broken";
                                final boolean statsEnableItemBroken = statsDataSection.getBoolean(statsData);
                                config.set("Configuration.Stats.Enable_Item_Broken", (Object)statsEnableItemBroken);
                            }
                            else if (statsData.equalsIgnoreCase("Enable_Item_Broken_Message")) {
                                final String path = "Configuration.Stats.Enable_Item_Broken_Message";
                                final boolean statsEnableItemBrokenMessage = statsDataSection.getBoolean(statsData);
                                config.set("Configuration.Stats.Enable_Item_Broken_Message", (Object)statsEnableItemBrokenMessage);
                            }
                            else if (statsData.equalsIgnoreCase("Enable_Max_Health")) {
                                final String path = "Configuration.Stats.Enable_Max_Health";
                                final boolean statsEnableMaxHealth = statsDataSection.getBoolean(statsData);
                                config.set("Configuration.Stats.Enable_Max_Health", (Object)statsEnableMaxHealth);
                            }
                            else if (statsData.equalsIgnoreCase("Format_Value")) {
                                final String path = "Configuration.Stats.Format_Value";
                                final String statsFormatValue = statsDataSection.getString(statsData);
                                config.set("Configuration.Stats.Format_Value", (Object)TextUtil.colorful(statsFormatValue));
                            }
                            else if (statsData.equalsIgnoreCase("Format_Exp")) {
                                final String path = "Configuration.Stats.Format_Exp";
                                final String statsFormatExp = statsDataSection.getString(statsData);
                                config.set("Configuration.Stats.Format_Exp", (Object)TextUtil.colorful(statsFormatExp));
                            }
                            else if (statsData.equalsIgnoreCase("Lore_Positive_Value")) {
                                final String path = "Configuration.Stats.Lore_Positive_Value";
                                final String statsLorePositiveValue = statsDataSection.getString(statsData);
                                config.set("Configuration.Stats.Lore_Positive_Value", (Object)TextUtil.colorful(statsLorePositiveValue));
                            }
                            else if (statsData.equalsIgnoreCase("Lore_Negative_Value")) {
                                final String path = "Configuration.Stats.Lore_Negative_Value";
                                final String statsLoreNegativeValue = statsDataSection.getString(statsData);
                                config.set("Configuration.Stats.Lore_Negative_Value", (Object)TextUtil.colorful(statsLoreNegativeValue));
                            }
                            else if (statsData.equalsIgnoreCase("Lore_Range_Symbol")) {
                                final String path = "Configuration.Stats.Lore_Range_Symbol";
                                final String statsLoreRangeSymbol = statsDataSection.getString(statsData);
                                config.set("Configuration.Stats.Lore_Range_Symbol", (Object)TextUtil.colorful(statsLoreRangeSymbol));
                            }
                            else if (statsData.equalsIgnoreCase("Lore_Divider_Symbol")) {
                                final String path = "Configuration.Stats.Lore_Divider_Symbol";
                                final String statsLoreDividerSymbol = statsDataSection.getString(statsData);
                                config.set("Configuration.Stats.Lore_Divider_Symbol", (Object)TextUtil.colorful(statsLoreDividerSymbol));
                            }
                            else if (statsData.equalsIgnoreCase("Lore_Multiplier_Symbol")) {
                                final String path = "Configuration.Stats.Lore_Multiplier_Symbol";
                                final String statsLoreMultiplierSymbol = statsDataSection.getString(statsData);
                                config.set("Configuration.Stats.Lore_Multiplier_Symbol", (Object)TextUtil.colorful(statsLoreMultiplierSymbol));
                            }
                            else if (statsData.equalsIgnoreCase("Scale_OffHand_Value")) {
                                final String path = "Configuration.Stats.Scale_OffHand_Value";
                                final double statsScaleOffHandValue = statsDataSection.getDouble(statsData);
                                config.set("Configuration.Stats.Scale_OffHand_Value", (Object)statsScaleOffHandValue);
                            }
                            else if (statsData.equalsIgnoreCase("Scale_Up_Value")) {
                                final String path = "Configuration.Stats.Scale_Up_Value";
                                final double statsScaleUpValue = statsDataSection.getDouble(statsData);
                                config.set("Configuration.Stats.Scale_Up_Value", (Object)statsScaleUpValue);
                            }
                            else if (statsData.equalsIgnoreCase("Scale_Armor_Shield")) {
                                final String path = "Configuration.Stats.Scale_Armor_Shield";
                                final double statsScaleArmorShield = statsDataSection.getDouble(statsData);
                                config.set("Configuration.Stats.Scale_Armor_Shield", (Object)statsScaleArmorShield);
                            }
                            else if (statsData.equalsIgnoreCase("Max_Level_Value")) {
                                final String path = "Configuration.Stats.Max_Level_Value";
                                final int statsMaxLevelValue = statsDataSection.getInt(statsData);
                                config.set("Configuration.Stats.Max_Level_Value", (Object)statsMaxLevelValue);
                            }
                            else if (statsData.equalsIgnoreCase("Identifier_Damage")) {
                                final String path = "Configuration.Stats.Identifier_Damage";
                                final String statsIdentifierDamage = statsDataSection.getString(statsData);
                                config.set("Configuration.Stats.Identifier_Damage", (Object)TextUtil.colorful(statsIdentifierDamage));
                            }
                            else if (statsData.equalsIgnoreCase("Identifier_Penetration")) {
                                final String path = "Configuration.Stats.Identifier_Penetration";
                                final String statsIdentifierPenetration = statsDataSection.getString(statsData);
                                config.set("Configuration.Stats.Identifier_Penetration", (Object)TextUtil.colorful(statsIdentifierPenetration));
                            }
                            else if (statsData.equalsIgnoreCase("Identifier_PvP_Damage")) {
                                final String path = "Configuration.Stats.Identifier_PvP_Damage";
                                final String statsIdentifierPvPDamage = statsDataSection.getString(statsData);
                                config.set("Configuration.Stats.Identifier_PvP_Damage", (Object)TextUtil.colorful(statsIdentifierPvPDamage));
                            }
                            else if (statsData.equalsIgnoreCase("Identifier_PvE_Damage")) {
                                final String path = "Configuration.Stats.Identifier_PvE_Damage";
                                final String statsIdentifierPvEDamage = statsDataSection.getString(statsData);
                                config.set("Configuration.Stats.Identifier_PvE_Damage", (Object)TextUtil.colorful(statsIdentifierPvEDamage));
                            }
                            else if (statsData.equalsIgnoreCase("Identifier_Defense")) {
                                final String path = "Configuration.Stats.Identifier_Defense";
                                final String statsIdentifierDefense = statsDataSection.getString(statsData);
                                config.set("Configuration.Stats.Identifier_Defense", (Object)TextUtil.colorful(statsIdentifierDefense));
                            }
                            else if (statsData.equalsIgnoreCase("Identifier_PvP_Defense")) {
                                final String path = "Configuration.Stats.Identifier_PvP_Defense";
                                final String statsIdentifierPvPDefense = statsDataSection.getString(statsData);
                                config.set("Configuration.Stats.Identifier_PvP_Defense", (Object)TextUtil.colorful(statsIdentifierPvPDefense));
                            }
                            else if (statsData.equalsIgnoreCase("Identifier_PvE_Defense")) {
                                final String path = "Configuration.Stats.Identifier_PvE_Defense";
                                final String statsIdentifierPvEDefense = statsDataSection.getString(statsData);
                                config.set("Configuration.Stats.Identifier_PvE_Defense", (Object)TextUtil.colorful(statsIdentifierPvEDefense));
                            }
                            else if (statsData.equalsIgnoreCase("Identifier_Health")) {
                                final String path = "Configuration.Stats.Identifier_Health";
                                final String statsIdentifierHealth = statsDataSection.getString(statsData);
                                config.set("Configuration.Stats.Identifier_Health", (Object)TextUtil.colorful(statsIdentifierHealth));
                            }
                            else if (statsData.equalsIgnoreCase("Identifier_Health_Regen")) {
                                final String path = "Configuration.Stats.Identifier_Health_Regen";
                                final String statsIdentifierHealthRegen = statsDataSection.getString(statsData);
                                config.set("Configuration.Stats.Identifier_Health_Regen", (Object)TextUtil.colorful(statsIdentifierHealthRegen));
                            }
                            else if (statsData.equalsIgnoreCase("Identifier_Stamina_Max")) {
                                final String path = "Configuration.Stats.Identifier_Stamina_Max";
                                final String statsIdentifierStaminaMax = statsDataSection.getString(statsData);
                                config.set("Configuration.Stats.Identifier_Stamina_Max", (Object)TextUtil.colorful(statsIdentifierStaminaMax));
                            }
                            else if (statsData.equalsIgnoreCase("Identifier_Stamina_Regen")) {
                                final String path = "Configuration.Stats.Identifier_Stamina_Regen";
                                final String statsIdentifierStaminaRegen = statsDataSection.getString(statsData);
                                config.set("Configuration.Stats.Identifier_Stamina_Regen", (Object)TextUtil.colorful(statsIdentifierStaminaRegen));
                            }
                            else if (statsData.equalsIgnoreCase("Identifier_Attack_AoE_Radius")) {
                                final String path = "Configuration.Stats.Identifier_Attack_AoE_Radius";
                                final String statsIdentifierAttackAoERadius = statsDataSection.getString(statsData);
                                config.set("Configuration.Stats.Identifier_Attack_AoE_Radius", (Object)TextUtil.colorful(statsIdentifierAttackAoERadius));
                            }
                            else if (statsData.equalsIgnoreCase("Identifier_Attack_AoE_Damage")) {
                                final String path = "Configuration.Stats.Identifier_Attack_AoE_Damage";
                                final String statsIdentifierAttackAoEDamage = statsDataSection.getString(statsData);
                                config.set("Configuration.Stats.Identifier_Attack_AoE_Damage", (Object)TextUtil.colorful(statsIdentifierAttackAoEDamage));
                            }
                            else if (statsData.equalsIgnoreCase("Identifier_Critical_Chance")) {
                                final String path = "Configuration.Stats.Identifier_Critical_Chance";
                                final String statsIdentifierCriticalChance = statsDataSection.getString(statsData);
                                config.set("Configuration.Stats.Identifier_Critical_Chance", (Object)TextUtil.colorful(statsIdentifierCriticalChance));
                            }
                            else if (statsData.equalsIgnoreCase("Identifier_Critical_Damage")) {
                                final String path = "Configuration.Stats.Identifier_Critical_Damage";
                                final String statsIdentifierCriticalDamage = statsDataSection.getString(statsData);
                                config.set("Configuration.Stats.Identifier_Critical_Damage", (Object)TextUtil.colorful(statsIdentifierCriticalDamage));
                            }
                            else if (statsData.equalsIgnoreCase("Identifier_Block_Amount")) {
                                final String path = "Configuration.Stats.Identifier_Block_Amount";
                                final String statsIdentifierBlockAmount = statsDataSection.getString(statsData);
                                config.set("Configuration.Stats.Identifier_Block_Amount", (Object)TextUtil.colorful(statsIdentifierBlockAmount));
                            }
                            else if (statsData.equalsIgnoreCase("Identifier_Block_Rate")) {
                                final String path = "Configuration.Stats.Identifier_Block_Rate";
                                final String statsIdentifierBlockRate = statsDataSection.getString(statsData);
                                config.set("Configuration.Stats.Identifier_Block_Rate", (Object)TextUtil.colorful(statsIdentifierBlockRate));
                            }
                            else if (statsData.equalsIgnoreCase("Identifier_Hit_Rate")) {
                                final String path = "Configuration.Stats.Identifier_Hit_Rate";
                                final String statsIdentifierHitRate = statsDataSection.getString(statsData);
                                config.set("Configuration.Stats.Identifier_Hit_Rate", (Object)TextUtil.colorful(statsIdentifierHitRate));
                            }
                            else if (statsData.equalsIgnoreCase("Identifier_Dodge_Rate")) {
                                final String path = "Configuration.Stats.Identifier_Dodge_Rate";
                                final String statsIdentifierDodgeRate = statsDataSection.getString(statsData);
                                config.set("Configuration.Stats.Identifier_Dodge_Rate", (Object)TextUtil.colorful(statsIdentifierDodgeRate));
                            }
                            else if (statsData.equalsIgnoreCase("Identifier_Fishing_Chance")) {
                                final String path = "Configuration.Stats.Identifier_Fishing_Chance";
                                final String statsIdentifierFishingChance = statsDataSection.getString(statsData);
                                config.set("Configuration.Stats.Identifier_Fishing_Chance", (Object)TextUtil.colorful(statsIdentifierFishingChance));
                            }
                            else if (statsData.equalsIgnoreCase("Identifier_Fishing_Power")) {
                                final String path = "Configuration.Stats.Identifier_Fishing_Power";
                                final String statsIdentifierFishingPower = statsDataSection.getString(statsData);
                                config.set("Configuration.Stats.Identifier_Fishing_Power", (Object)TextUtil.colorful(statsIdentifierFishingPower));
                            }
                            else if (statsData.equalsIgnoreCase("Identifier_Fishing_Speed")) {
                                final String path = "Configuration.Stats.Identifier_Fishing_Speed";
                                final String statsIdentifierFishingSpeed = statsDataSection.getString(statsData);
                                config.set("Configuration.Stats.Identifier_Fishing_Speed", (Object)TextUtil.colorful(statsIdentifierFishingSpeed));
                            }
                            else if (statsData.equalsIgnoreCase("Identifier_Lures_Max_Tension")) {
                                final String path = "Configuration.Stats.Identifier_Lures_Max_Tension";
                                final String statsIdentifierLuresMaxTension = statsDataSection.getString(statsData);
                                config.set("Configuration.Stats.Identifier_Lures_Max_Tension", (Object)TextUtil.colorful(statsIdentifierLuresMaxTension));
                            }
                            else if (statsData.equalsIgnoreCase("Identifier_Lures_Endurance")) {
                                final String path = "Configuration.Stats.Identifier_Lures_Endurance";
                                final String statsIdentifierLuresEndurance = statsDataSection.getString(statsData);
                                config.set("Configuration.Stats.Identifier_Lures_Endurance", (Object)TextUtil.colorful(statsIdentifierLuresEndurance));
                            }
                            else if (statsData.equalsIgnoreCase("Identifier_Durability")) {
                                final String path = "Configuration.Stats.Identifier_Durability";
                                final String statsIdentifierDurability = statsDataSection.getString(statsData);
                                config.set("Configuration.Stats.Identifier_Durability", (Object)TextUtil.colorful(statsIdentifierDurability));
                            }
                            else {
                                if (!statsData.equalsIgnoreCase("Identifier_Level")) {
                                    continue;
                                }
                                final String path = "Configuration.Stats.Identifier_Level";
                                final String statsIdentifierLevel = statsDataSection.getString(statsData);
                                config.set("Configuration.Stats.Identifier_Level", (Object)TextUtil.colorful(statsIdentifierLevel));
                            }
                        }
                    }
                    else if (data.equalsIgnoreCase("Ability")) {
                        final ConfigurationSection abilityDataSection = dataSection.getConfigurationSection(data);
                        for (final String abilityData : abilityDataSection.getKeys(false)) {
                            if (abilityData.equalsIgnoreCase("Format")) {
                                final String path = "Configuration.Ability.Format";
                                final String abilityFormat = abilityDataSection.getString(abilityData);
                                config.set("Configuration.Ability.Format", (Object)TextUtil.colorful(abilityFormat));
                            }
                            else {
                                if (!abilityData.equalsIgnoreCase("Weapon")) {
                                    continue;
                                }
                                final ConfigurationSection abilityWeaponDataSection = abilityDataSection.getConfigurationSection(abilityData);
                                for (final String abilityWeaponData : abilityWeaponDataSection.getKeys(false)) {
                                    if (abilityWeaponData.equalsIgnoreCase("Enable_OffHand")) {
                                        final String path2 = "Configuration.Ability.Weapon.Enable_OffHand";
                                        final boolean abilityWeaponEnableOffHand = abilityWeaponDataSection.getBoolean(abilityWeaponData);
                                        config.set("Configuration.Ability.Weapon.Enable_OffHand", (Object)abilityWeaponEnableOffHand);
                                    }
                                    else if (abilityWeaponData.equalsIgnoreCase("Identifier_Air_Shock")) {
                                        final String path2 = "Configuration.Ability.Weapon.Identifier_Air_Shock";
                                        final String abilityWeaponIdentifierAirShock = abilityWeaponDataSection.getString(abilityWeaponData);
                                        config.set("Configuration.Ability.Weapon.Identifier_Air_Shock", (Object)abilityWeaponIdentifierAirShock);
                                    }
                                    else if (abilityWeaponData.equalsIgnoreCase("Identifier_Bad_Luck")) {
                                        final String path2 = "Configuration.Ability.Weapon.Identifier_Bad_Luck";
                                        final String abilityWeaponIdentifierBadLuck = abilityWeaponDataSection.getString(abilityWeaponData);
                                        config.set("Configuration.Ability.Weapon.Identifier_Bad_Luck", (Object)abilityWeaponIdentifierBadLuck);
                                    }
                                    else if (abilityWeaponData.equalsIgnoreCase("Identifier_Blind")) {
                                        final String path2 = "Configuration.Ability.Weapon.Identifier_Blind";
                                        final String abilityWeaponIdentifierBlind = abilityWeaponDataSection.getString(abilityWeaponData);
                                        config.set("Configuration.Ability.Weapon.Identifier_Blind", (Object)abilityWeaponIdentifierBlind);
                                    }
                                    else if (abilityWeaponData.equalsIgnoreCase("Identifier_Bubble_Deflector")) {
                                        final String path2 = "Configuration.Ability.Weapon.Identifier_Bubble_Deflector";
                                        final String abilityWeaponIdentifierBubbleDeflector = abilityWeaponDataSection.getString(abilityWeaponData);
                                        config.set("Configuration.Ability.Weapon.Identifier_Bubble_Deflector", (Object)abilityWeaponIdentifierBubbleDeflector);
                                    }
                                    else if (abilityWeaponData.equalsIgnoreCase("Identifier_Cannibalism")) {
                                        final String path2 = "Configuration.Ability.Weapon.Identifier_Cannibalism";
                                        final String abilityWeaponIdentifierCannibalism = abilityWeaponDataSection.getString(abilityWeaponData);
                                        config.set("Configuration.Ability.Weapon.Identifier_Cannibalism", (Object)abilityWeaponIdentifierCannibalism);
                                    }
                                    else if (abilityWeaponData.equalsIgnoreCase("Identifier_Confuse") || abilityWeaponData.equalsIgnoreCase("Identifier_Nausea")) {
                                        final String path2 = "Configuration.Ability.Weapon.Identifier_Confuse";
                                        final String abilityWeaponIdentifierConfuse = abilityWeaponDataSection.getString(abilityWeaponData);
                                        config.set("Configuration.Ability.Weapon.Identifier_Confuse", (Object)abilityWeaponIdentifierConfuse);
                                    }
                                    else if (abilityWeaponData.equalsIgnoreCase("Identifier_Curse")) {
                                        final String path2 = "Configuration.Ability.Weapon.Identifier_Curse";
                                        final String abilityWeaponIdentifierCurse = abilityWeaponDataSection.getString(abilityWeaponData);
                                        config.set("Configuration.Ability.Weapon.Identifier_Curse", (Object)abilityWeaponIdentifierCurse);
                                    }
                                    else if (abilityWeaponData.equalsIgnoreCase("Identifier_Dark_Flame")) {
                                        final String path2 = "Configuration.Ability.Weapon.Identifier_Dark_Flame";
                                        final String abilityWeaponIdentifierDarkFlame = abilityWeaponDataSection.getString(abilityWeaponData);
                                        config.set("Configuration.Ability.Weapon.Identifier_Dark_Flame", (Object)abilityWeaponIdentifierDarkFlame);
                                    }
                                    else if (abilityWeaponData.equalsIgnoreCase("Identifier_Dark_Impact")) {
                                        final String path2 = "Configuration.Ability.Weapon.Identifier_Dark_Impact";
                                        final String abilityWeaponIdentifierDarkImpact = abilityWeaponDataSection.getString(abilityWeaponData);
                                        config.set("Configuration.Ability.Weapon.Identifier_Dark_Impact", (Object)abilityWeaponIdentifierDarkImpact);
                                    }
                                    else if (abilityWeaponData.equalsIgnoreCase("Identifier_Flame")) {
                                        final String path2 = "Configuration.Ability.Weapon.Identifier_Flame";
                                        final String abilityWeaponIdentifierFlame = abilityWeaponDataSection.getString(abilityWeaponData);
                                        config.set("Configuration.Ability.Weapon.Identifier_Flame", (Object)abilityWeaponIdentifierFlame);
                                    }
                                    else if (abilityWeaponData.equalsIgnoreCase("Identifier_Flame_Wheel")) {
                                        final String path2 = "Configuration.Ability.Weapon.Identifier_Flame_Wheel";
                                        final String abilityWeaponIdentifierFlameWheel = abilityWeaponDataSection.getString(abilityWeaponData);
                                        config.set("Configuration.Ability.Weapon.Identifier_Flame_Wheel", (Object)abilityWeaponIdentifierFlameWheel);
                                    }
                                    else if (abilityWeaponData.equalsIgnoreCase("Identifier_Freeze")) {
                                        final String path2 = "Configuration.Ability.Weapon.Identifier_Freeze";
                                        final String abilityWeaponIdentifierFreeze = abilityWeaponDataSection.getString(abilityWeaponData);
                                        config.set("Configuration.Ability.Weapon.Identifier_Freeze", (Object)abilityWeaponIdentifierFreeze);
                                    }
                                    else if (abilityWeaponData.equalsIgnoreCase("Identifier_Harm")) {
                                        final String path2 = "Configuration.Ability.Weapon.Identifier_Harm";
                                        final String abilityWeaponIdentifierHarm = abilityWeaponDataSection.getString(abilityWeaponData);
                                        config.set("Configuration.Ability.Weapon.Identifier_Harm", (Object)abilityWeaponIdentifierHarm);
                                    }
                                    else if (abilityWeaponData.equalsIgnoreCase("Identifier_Hungry")) {
                                        final String path2 = "Configuration.Ability.Weapon.Identifier_Hungry";
                                        final String abilityWeaponIdentifierHungry = abilityWeaponDataSection.getString(abilityWeaponData);
                                        config.set("Configuration.Ability.Weapon.Identifier_Hungry", (Object)abilityWeaponIdentifierHungry);
                                    }
                                    else if (abilityWeaponData.equalsIgnoreCase("Identifier_Levitation")) {
                                        final String path2 = "Configuration.Ability.Weapon.Identifier_Levitation";
                                        final String abilityWeaponIdentifierLevitation = abilityWeaponDataSection.getString(abilityWeaponData);
                                        config.set("Configuration.Ability.Weapon.Identifier_Levitation", (Object)abilityWeaponIdentifierLevitation);
                                    }
                                    else if (abilityWeaponData.equalsIgnoreCase("Identifier_Lightning")) {
                                        final String path2 = "Configuration.Ability.Weapon.Identifier_Lightning";
                                        final String abilityWeaponIdentifierLightning = abilityWeaponDataSection.getString(abilityWeaponData);
                                        config.set("Configuration.Ability.Weapon.Identifier_Lightning", (Object)abilityWeaponIdentifierLightning);
                                    }
                                    else if (abilityWeaponData.equalsIgnoreCase("Identifier_Poison")) {
                                        final String path2 = "Configuration.Ability.Weapon.Identifier_Poison";
                                        final String abilityWeaponIdentifierPoison = abilityWeaponDataSection.getString(abilityWeaponData);
                                        config.set("Configuration.Ability.Weapon.Identifier_Poison", (Object)abilityWeaponIdentifierPoison);
                                    }
                                    else if (abilityWeaponData.equalsIgnoreCase("Identifier_Roots")) {
                                        final String path2 = "Configuration.Ability.Weapon.Identifier_Roots";
                                        final String abilityWeaponIdentifierRoots = abilityWeaponDataSection.getString(abilityWeaponData);
                                        config.set("Configuration.Ability.Weapon.Identifier_Roots", (Object)abilityWeaponIdentifierRoots);
                                    }
                                    else if (abilityWeaponData.equalsIgnoreCase("Identifier_Slowness")) {
                                        final String path2 = "Configuration.Ability.Weapon.Identifier_Slowness";
                                        final String abilityWeaponIdentifierSlowness = abilityWeaponDataSection.getString(abilityWeaponData);
                                        config.set("Configuration.Ability.Weapon.Identifier_Slowness", (Object)abilityWeaponIdentifierSlowness);
                                    }
                                    else if (abilityWeaponData.equalsIgnoreCase("Identifier_Tired") || abilityWeaponData.equalsIgnoreCase("Identifier_Fatigue")) {
                                        final String path2 = "Configuration.Ability.Weapon.Identifier_Tired";
                                        final String abilityWeaponIdentifierFatigue = abilityWeaponDataSection.getString(abilityWeaponData);
                                        config.set("Configuration.Ability.Weapon.Identifier_Tired", (Object)abilityWeaponIdentifierFatigue);
                                    }
                                    else if (abilityWeaponData.equalsIgnoreCase("Identifier_Vampirism")) {
                                        final String path2 = "Configuration.Ability.Weapon.Identifier_Vampirism";
                                        final String abilityWeaponIdentifierVampirism = abilityWeaponDataSection.getString(abilityWeaponData);
                                        config.set("Configuration.Ability.Weapon.Identifier_Vampirism", (Object)abilityWeaponIdentifierVampirism);
                                    }
                                    else if (abilityWeaponData.equalsIgnoreCase("Identifier_Venom_Blast")) {
                                        final String path2 = "Configuration.Ability.Weapon.Identifier_Venom_Blast";
                                        final String abilityWeaponIdentifierVenomBlast = abilityWeaponDataSection.getString(abilityWeaponData);
                                        config.set("Configuration.Ability.Weapon.Identifier_Venom_Blast", (Object)abilityWeaponIdentifierVenomBlast);
                                    }
                                    else if (abilityWeaponData.equalsIgnoreCase("Identifier_Venom_Spread")) {
                                        final String path2 = "Configuration.Ability.Weapon.Identifier_Venom_Spread";
                                        final String abilityWeaponIdentifierVenomSpread = abilityWeaponDataSection.getString(abilityWeaponData);
                                        config.set("Configuration.Ability.Weapon.Identifier_Venom_Spread", (Object)abilityWeaponIdentifierVenomSpread);
                                    }
                                    else if (abilityWeaponData.equalsIgnoreCase("Identifier_Weakness")) {
                                        final String path2 = "Configuration.Ability.Weapon.Identifier_Weakness";
                                        final String abilityWeaponIdentifierWeakness = abilityWeaponDataSection.getString(abilityWeaponData);
                                        config.set("Configuration.Ability.Weapon.Identifier_Weakness", (Object)abilityWeaponIdentifierWeakness);
                                    }
                                    else {
                                        if (!abilityWeaponData.equalsIgnoreCase("Identifier_Wither")) {
                                            continue;
                                        }
                                        final String path2 = "Configuration.Ability.Weapon.Identifier_Wither";
                                        final String abilityWeaponIdentifierWither = abilityWeaponDataSection.getString(abilityWeaponData);
                                        config.set("Configuration.Ability.Weapon.Identifier_Wither", (Object)abilityWeaponIdentifierWither);
                                    }
                                }
                            }
                        }
                    }
                    else if (data.equalsIgnoreCase("Passive")) {
                        final ConfigurationSection passiveDataSection = dataSection.getConfigurationSection(data);
                        for (final String passiveData : passiveDataSection.getKeys(false)) {
                            if (passiveData.equalsIgnoreCase("Enable_Grade_Calculation")) {
                                final String path = "Configuration.Passive.Enable_Grade_Calculation";
                                final boolean passiveEnableGradeCalculation = passiveDataSection.getBoolean(passiveData);
                                config.set("Configuration.Passive.Enable_Grade_Calculation", (Object)passiveEnableGradeCalculation);
                            }
                            else if (passiveData.equalsIgnoreCase("Enable_Hand")) {
                                final String path = "Configuration.Passive.Enable_Hand";
                                final boolean passiveEnableHand = passiveDataSection.getBoolean(passiveData);
                                config.set("Configuration.Passive.Enable_Hand", (Object)passiveEnableHand);
                            }
                            else if (passiveData.equalsIgnoreCase("Period_Effect")) {
                                final String path = "Configuration.Passive.Period_Effect";
                                final int passivePeriodEffect = passiveDataSection.getInt(passiveData);
                                config.set("Configuration.Passive.Period_Effect", (Object)passivePeriodEffect);
                            }
                            else if (passiveData.equalsIgnoreCase("Buff")) {
                                final ConfigurationSection passiveBuffDataSection = passiveDataSection.getConfigurationSection(passiveData);
                                for (final String passiveBuffData : passiveBuffDataSection.getKeys(false)) {
                                    if (passiveBuffData.equalsIgnoreCase("Format")) {
                                        final String path2 = "Configuration.Passive.Buff.Format";
                                        final String passiveBuffFormat = passiveBuffDataSection.getString(passiveBuffData);
                                        config.set("Configuration.Passive.Buff.Format", (Object)TextUtil.colorful(passiveBuffFormat));
                                    }
                                    else if (passiveBuffData.equalsIgnoreCase("Identifier_Strength")) {
                                        final String path2 = "Configuration.Passive.Buff.Identifier_Strength";
                                        final String passiveBuffIdentifierStrength = passiveBuffDataSection.getString(passiveBuffData);
                                        config.set("Configuration.Passive.Buff.Identifier_Strength", (Object)TextUtil.colorful(passiveBuffIdentifierStrength));
                                    }
                                    else if (passiveBuffData.equalsIgnoreCase("Identifier_Protection")) {
                                        final String path2 = "Configuration.Passive.Buff.Identifier_Protection";
                                        final String passiveBuffIdentifierProtection = passiveBuffDataSection.getString(passiveBuffData);
                                        config.set("Configuration.Passive.Buff.Identifier_Protection", (Object)TextUtil.colorful(passiveBuffIdentifierProtection));
                                    }
                                    else if (passiveBuffData.equalsIgnoreCase("Identifier_Vision")) {
                                        final String path2 = "Configuration.Passive.Buff.Identifier_Vision";
                                        final String passiveBuffIdentifierVision = passiveBuffDataSection.getString(passiveBuffData);
                                        config.set("Configuration.Passive.Buff.Identifier_Vision", (Object)TextUtil.colorful(passiveBuffIdentifierVision));
                                    }
                                    else if (passiveBuffData.equalsIgnoreCase("Identifier_Jump")) {
                                        final String path2 = "Configuration.Passive.Buff.Identifier_Jump";
                                        final String passiveBuffIdentifierJump = passiveBuffDataSection.getString(passiveBuffData);
                                        config.set("Configuration.Passive.Buff.Identifier_Jump", (Object)TextUtil.colorful(passiveBuffIdentifierJump));
                                    }
                                    else if (passiveBuffData.equalsIgnoreCase("Identifier_Absorb")) {
                                        final String path2 = "Configuration.Passive.Buff.Identifier_Absorb";
                                        final String passiveBuffIdentifierAbsorb = passiveBuffDataSection.getString(passiveBuffData);
                                        config.set("Configuration.Passive.Buff.Identifier_Absorb", (Object)TextUtil.colorful(passiveBuffIdentifierAbsorb));
                                    }
                                    else if (passiveBuffData.equalsIgnoreCase("Identifier_Fire_Resistance")) {
                                        final String path2 = "Configuration.Passive.Buff.Identifier_Fire_Resistance";
                                        final String passiveBuffIdentifierFireResistance = passiveBuffDataSection.getString(passiveBuffData);
                                        config.set("Configuration.Passive.Buff.Identifier_Fire_Resistance", (Object)TextUtil.colorful(passiveBuffIdentifierFireResistance));
                                    }
                                    else if (passiveBuffData.equalsIgnoreCase("Identifier_Invisibility")) {
                                        final String path2 = "Configuration.Passive.Buff.Identifier_Invisibility";
                                        final String passiveBuffIdentifierInvisibility = passiveBuffDataSection.getString(passiveBuffData);
                                        config.set("Configuration.Passive.Buff.Identifier_Invisibility", (Object)TextUtil.colorful(passiveBuffIdentifierInvisibility));
                                    }
                                    else if (passiveBuffData.equalsIgnoreCase("Identifier_Luck")) {
                                        final String path2 = "Configuration.Passive.Buff.Identifier_Luck";
                                        final String passiveBuffIdentifierLuck = passiveBuffDataSection.getString(passiveBuffData);
                                        config.set("Configuration.Passive.Buff.Identifier_Luck", (Object)TextUtil.colorful(passiveBuffIdentifierLuck));
                                    }
                                    else if (passiveBuffData.equalsIgnoreCase("Identifier_Health_Boost")) {
                                        final String path2 = "Configuration.Passive.Buff.Identifier_Health_Boost";
                                        final String passiveBuffIdentifierHealthBoost = passiveBuffDataSection.getString(passiveBuffData);
                                        config.set("Configuration.Passive.Buff.Identifier_Health_Boost", (Object)TextUtil.colorful(passiveBuffIdentifierHealthBoost));
                                    }
                                    else if (passiveBuffData.equalsIgnoreCase("Identifier_Regeneration")) {
                                        final String path2 = "Configuration.Passive.Buff.Identifier_Regeneration";
                                        final String passiveBuffIdentifierRegeneration = passiveBuffDataSection.getString(passiveBuffData);
                                        config.set("Configuration.Passive.Buff.Identifier_Regeneration", (Object)TextUtil.colorful(passiveBuffIdentifierRegeneration));
                                    }
                                    else if (passiveBuffData.equalsIgnoreCase("Identifier_Saturation")) {
                                        final String path2 = "Configuration.Passive.Buff.Identifier_Saturation";
                                        final String passiveBuffIdentifierSaturation = passiveBuffDataSection.getString(passiveBuffData);
                                        config.set("Configuration.Passive.Buff.Identifier_Saturation", (Object)TextUtil.colorful(passiveBuffIdentifierSaturation));
                                    }
                                    else if (passiveBuffData.equalsIgnoreCase("Identifier_Speed")) {
                                        final String path2 = "Configuration.Passive.Buff.Identifier_Speed";
                                        final String passiveBuffIdentifierSpeed = passiveBuffDataSection.getString(passiveBuffData);
                                        config.set("Configuration.Passive.Buff.Identifier_Speed", (Object)TextUtil.colorful(passiveBuffIdentifierSpeed));
                                    }
                                    else if (passiveBuffData.equalsIgnoreCase("Identifier_Water_Breathing")) {
                                        final String path2 = "Configuration.Passive.Buff.Identifier_Water_Breathing";
                                        final String passiveBuffIdentifierWaterBreathing = passiveBuffDataSection.getString(passiveBuffData);
                                        config.set("Configuration.Passive.Buff.Identifier_Water_Breathing", (Object)TextUtil.colorful(passiveBuffIdentifierWaterBreathing));
                                    }
                                    else {
                                        if (!passiveBuffData.equalsIgnoreCase("Identifier_Haste")) {
                                            continue;
                                        }
                                        final String path2 = "Configuration.Passive.Buff.Identifier_Haste";
                                        final String passiveBuffIdentifierHaste = passiveBuffDataSection.getString(passiveBuffData);
                                        config.set("Configuration.Passive.Buff.Identifier_Haste", (Object)TextUtil.colorful(passiveBuffIdentifierHaste));
                                    }
                                }
                            }
                            else {
                                if (!passiveData.equalsIgnoreCase("Debuff")) {
                                    continue;
                                }
                                final ConfigurationSection passiveDebuffDataSection = passiveDataSection.getConfigurationSection(passiveData);
                                for (final String passiveDebuffData : passiveDebuffDataSection.getKeys(false)) {
                                    if (passiveDebuffData.equalsIgnoreCase("Format")) {
                                        final String path2 = "Configuration.Passive.Debuff.Format";
                                        final String passiveDebuffFormat = passiveDebuffDataSection.getString(passiveDebuffData);
                                        config.set("Configuration.Passive.Debuff.Format", (Object)TextUtil.colorful(passiveDebuffFormat));
                                    }
                                    else if (passiveDebuffData.equalsIgnoreCase("Identifier_Weak")) {
                                        final String path2 = "Configuration.Passive.Debuff.Identifier_Weak";
                                        final String passiveDebuffIdentifierWeak = passiveDebuffDataSection.getString(passiveDebuffData);
                                        config.set("Configuration.Passive.Debuff.Identifier_Weak", (Object)TextUtil.colorful(passiveDebuffIdentifierWeak));
                                    }
                                    else if (passiveDebuffData.equalsIgnoreCase("Identifier_Slow")) {
                                        final String path2 = "Configuration.Passive.Debuff.Identifier_Slow";
                                        final String passiveDebuffIdentifierSlow = passiveDebuffDataSection.getString(passiveDebuffData);
                                        config.set("Configuration.Passive.Debuff.Identifier_Slow", (Object)TextUtil.colorful(passiveDebuffIdentifierSlow));
                                    }
                                    else if (passiveDebuffData.equalsIgnoreCase("Identifier_Blind")) {
                                        final String path2 = "Configuration.Passive.Debuff.Identifier_Blind";
                                        final String passiveDebuffIdentifierBlind = passiveDebuffDataSection.getString(passiveDebuffData);
                                        config.set("Configuration.Passive.Debuff.Identifier_Blind", (Object)TextUtil.colorful(passiveDebuffIdentifierBlind));
                                    }
                                    else if (passiveDebuffData.equalsIgnoreCase("Identifier_Confuse")) {
                                        final String path2 = "Configuration.Passive.Debuff.Identifier_Confuse";
                                        final String passiveDebuffIdentifierConfuse = passiveDebuffDataSection.getString(passiveDebuffData);
                                        config.set("Configuration.Passive.Debuff.Identifier_Confuse", (Object)TextUtil.colorful(passiveDebuffIdentifierConfuse));
                                    }
                                    else if (passiveDebuffData.equalsIgnoreCase("Identifier_Starve")) {
                                        final String path2 = "Configuration.Passive.Debuff.Identifier_Starve";
                                        final String passiveDebuffIdentifierStarve = passiveDebuffDataSection.getString(passiveDebuffData);
                                        config.set("Configuration.Passive.Debuff.Identifier_Starve", (Object)TextUtil.colorful(passiveDebuffIdentifierStarve));
                                    }
                                    else if (passiveDebuffData.equalsIgnoreCase("Identifier_Toxic")) {
                                        final String path2 = "Configuration.Passive.Debuff.Identifier_Toxic";
                                        final String passiveDebuffIdentifierToxic = passiveDebuffDataSection.getString(passiveDebuffData);
                                        config.set("Configuration.Passive.Debuff.Identifier_Toxic", (Object)TextUtil.colorful(passiveDebuffIdentifierToxic));
                                    }
                                    else if (passiveDebuffData.equalsIgnoreCase("Identifier_Glow")) {
                                        final String path2 = "Configuration.Passive.Debuff.Identifier_Glow";
                                        final String passiveDebuffIdentifierGlow = passiveDebuffDataSection.getString(passiveDebuffData);
                                        config.set("Configuration.Passive.Debuff.Identifier_Glow", (Object)TextUtil.colorful(passiveDebuffIdentifierGlow));
                                    }
                                    else if (passiveDebuffData.equalsIgnoreCase("Identifier_Fatigue")) {
                                        final String path2 = "Configuration.Passive.Debuff.Identifier_Fatigue";
                                        final String passiveDebuffIdentifierFatigue = passiveDebuffDataSection.getString(passiveDebuffData);
                                        config.set("Configuration.Passive.Debuff.Identifier_Fatigue", (Object)TextUtil.colorful(passiveDebuffIdentifierFatigue));
                                    }
                                    else if (passiveDebuffData.equalsIgnoreCase("Identifier_Wither")) {
                                        final String path2 = "Configuration.Passive.Debuff.Identifier_Wither";
                                        final String passiveDebuffIdentifierWither = passiveDebuffDataSection.getString(passiveDebuffData);
                                        config.set("Configuration.Passive.Debuff.Identifier_Wither", (Object)TextUtil.colorful(passiveDebuffIdentifierWither));
                                    }
                                    else {
                                        if (!passiveDebuffData.equalsIgnoreCase("Identifier_Unluck")) {
                                            continue;
                                        }
                                        final String path2 = "Configuration.Passive.Debuff.Identifier_Unluck";
                                        final String passiveDebuffIdentifierUnluck = passiveDebuffDataSection.getString(passiveDebuffData);
                                        config.set("Configuration.Passive.Debuff.Identifier_Unluck", (Object)TextUtil.colorful(passiveDebuffIdentifierUnluck));
                                    }
                                }
                            }
                        }
                    }
                    else if (data.equalsIgnoreCase("Power")) {
                        final ConfigurationSection powerDataSection = dataSection.getConfigurationSection(data);
                        for (final String powerData : powerDataSection.getKeys(false)) {
                            if (powerData.equalsIgnoreCase("Enable_Message_Cooldown")) {
                                final String path = "Configuration.Power.Enable_Message_Cooldown";
                                final boolean powerEnableMessageCooldown = powerDataSection.getBoolean(powerData);
                                config.set("Configuration.Power.Enable_Message_Cooldown", (Object)powerEnableMessageCooldown);
                            }
                            else if (powerData.equalsIgnoreCase("Format")) {
                                final String path = "Configuration.Power.Format";
                                final String powerFormat = powerDataSection.getString(powerData);
                                config.set("Configuration.Power.Format", (Object)TextUtil.colorful(powerFormat));
                            }
                            else if (powerData.equalsIgnoreCase("Click_Left")) {
                                final String path = "Configuration.Power.Click_Left";
                                final String powerClickLeft = powerDataSection.getString(powerData);
                                config.set("Configuration.Power.Click_Left", (Object)TextUtil.colorful(powerClickLeft));
                            }
                            else if (powerData.equalsIgnoreCase("Click_Right")) {
                                final String path = "Configuration.Power.Click_Right";
                                final String powerClickRight = powerDataSection.getString(powerData);
                                config.set("Configuration.Power.Click_Right", (Object)TextUtil.colorful(powerClickRight));
                            }
                            else if (powerData.equalsIgnoreCase("Click_Shift_Left")) {
                                final String path = "Configuration.Power.Click_Shift_Left";
                                final String powerClickShiftLeft = powerDataSection.getString(powerData);
                                config.set("Configuration.Power.Click_Shift_Left", (Object)TextUtil.colorful(powerClickShiftLeft));
                            }
                            else if (powerData.equalsIgnoreCase("Click_Shift_Right")) {
                                final String path = "Configuration.Power.Click_Shift_Right";
                                final String powerClickShiftRight = powerDataSection.getString(powerData);
                                config.set("Configuration.Power.Click_Shift_Right", (Object)TextUtil.colorful(powerClickShiftRight));
                            }
                            else if (powerData.equalsIgnoreCase("Projectile")) {
                                final ConfigurationSection powerProjectileDataSection = powerDataSection.getConfigurationSection(powerData);
                                for (final String powerProjectileData : powerProjectileDataSection.getKeys(false)) {
                                    if (powerProjectileData.equalsIgnoreCase("Identifier_Arrow")) {
                                        final String path2 = "Configuration.Power.Projectile.Identifier_Arrow";
                                        final String powerProjectileIdentifierArrow = powerProjectileDataSection.getString(powerProjectileData);
                                        config.set("Configuration.Power.Projectile.Identifier_Arrow", (Object)TextUtil.colorful(powerProjectileIdentifierArrow));
                                    }
                                    else if (powerProjectileData.equalsIgnoreCase("Identifier_Snow_Ball")) {
                                        final String path2 = "Configuration.Power.Projectile.Identifier_Snow_Ball";
                                        final String powerProjectileIdentifierSnowBall = powerProjectileDataSection.getString(powerProjectileData);
                                        config.set("Configuration.Power.Projectile.Identifier_Snow_Ball", (Object)TextUtil.colorful(powerProjectileIdentifierSnowBall));
                                    }
                                    else if (powerProjectileData.equalsIgnoreCase("Identifier_Egg")) {
                                        final String path2 = "Configuration.Power.Projectile.Identifier_Egg";
                                        final String powerProjectileIdentifierEgg = powerProjectileDataSection.getString(powerProjectileData);
                                        config.set("Configuration.Power.Projectile.Identifier_Egg", (Object)TextUtil.colorful(powerProjectileIdentifierEgg));
                                    }
                                    else if (powerProjectileData.equalsIgnoreCase("Identifier_Flame_Arrow")) {
                                        final String path2 = "Configuration.Power.Projectile.Identifier_Flame_Arrow";
                                        final String powerProjectileIdentifierFlameArrow = powerProjectileDataSection.getString(powerProjectileData);
                                        config.set("Configuration.Power.Projectile.Identifier_Flame_Arrow", (Object)TextUtil.colorful(powerProjectileIdentifierFlameArrow));
                                    }
                                    else if (powerProjectileData.equalsIgnoreCase("Identifier_Flame_Ball")) {
                                        final String path2 = "Configuration.Power.Projectile.Identifier_Flame_Ball";
                                        final String powerProjectileIdentifierFlameBall = powerProjectileDataSection.getString(powerProjectileData);
                                        config.set("Configuration.Power.Projectile.Identifier_Flame_Ball", (Object)TextUtil.colorful(powerProjectileIdentifierFlameBall));
                                    }
                                    else if (powerProjectileData.equalsIgnoreCase("Identifier_Flame_Egg")) {
                                        final String path2 = "Configuration.Power.Projectile.Identifier_Flame_Egg";
                                        final String powerProjectileIdentifierFlameEgg = powerProjectileDataSection.getString(powerProjectileData);
                                        config.set("Configuration.Power.Projectile.Identifier_Flame_Egg", (Object)TextUtil.colorful(powerProjectileIdentifierFlameEgg));
                                    }
                                    else if (powerProjectileData.equalsIgnoreCase("Identifier_Small_Fireball")) {
                                        final String path2 = "Configuration.Power.Projectile.Identifier_Small_Fireball";
                                        final String powerProjectileIdentifierSmallFireball = powerProjectileDataSection.getString(powerProjectileData);
                                        config.set("Configuration.Power.Projectile.Identifier_Small_Fireball", (Object)TextUtil.colorful(powerProjectileIdentifierSmallFireball));
                                    }
                                    else if (powerProjectileData.equalsIgnoreCase("Identifier_Large_Fireball")) {
                                        final String path2 = "Configuration.Power.Projectile.Identifier_Large_Fireball";
                                        final String powerProjectileIdentifierLargeFireball = powerProjectileDataSection.getString(powerProjectileData);
                                        config.set("Configuration.Power.Projectile.Identifier_Large_Fireball", (Object)TextUtil.colorful(powerProjectileIdentifierLargeFireball));
                                    }
                                    else {
                                        if (!powerProjectileData.equalsIgnoreCase("Identifier_Wither_Skull")) {
                                            continue;
                                        }
                                        final String path2 = "Configuration.Power.Projectile.Identifier_Wither_Skull";
                                        final String powerProjectileIdentifierWitherSkull = powerProjectileDataSection.getString(powerProjectileData);
                                        config.set("Configuration.Power.Projectile.Identifier_Wither_Skull", (Object)TextUtil.colorful(powerProjectileIdentifierWitherSkull));
                                    }
                                }
                            }
                            else {
                                if (!powerData.equalsIgnoreCase("Special")) {
                                    continue;
                                }
                                final ConfigurationSection powerSpecialDataSection = powerDataSection.getConfigurationSection(powerData);
                                for (final String powerSpecialData : powerSpecialDataSection.getKeys(false)) {
                                    if (powerSpecialData.equalsIgnoreCase("Identifier_Blink")) {
                                        final String path2 = "Configuration.Power.Special.Identifier_Blink";
                                        final String powerSpecialIdentifierBlink = powerSpecialDataSection.getString(powerSpecialData);
                                        config.set("Configuration.Power.Special.Identifier_Blink", (Object)TextUtil.colorful(powerSpecialIdentifierBlink));
                                    }
                                    else if (powerSpecialData.equalsIgnoreCase("Identifier_Fissure")) {
                                        final String path2 = "Configuration.Power.Special.Identifier_Fissure";
                                        final String powerSpecialIdentifierFissure = powerSpecialDataSection.getString(powerSpecialData);
                                        config.set("Configuration.Power.Special.Identifier_Fissure", (Object)TextUtil.colorful(powerSpecialIdentifierFissure));
                                    }
                                    else if (powerSpecialData.equalsIgnoreCase("Identifier_Ice_Spikes")) {
                                        final String path2 = "Configuration.Power.Special.Identifier_Ice_Spikes";
                                        final String powerSpecialIdentifierIceSpikes = powerSpecialDataSection.getString(powerSpecialData);
                                        config.set("Configuration.Power.Special.Identifier_Ice_Spikes", (Object)TextUtil.colorful(powerSpecialIdentifierIceSpikes));
                                    }
                                    else if (powerSpecialData.equalsIgnoreCase("Identifier_Amaterasu")) {
                                        final String path2 = "Configuration.Power.Special.Identifier_Amaterasu";
                                        final String powerSpecialIdentifierAmaterasu = powerSpecialDataSection.getString(powerSpecialData);
                                        config.set("Configuration.Power.Special.Identifier_Amaterasu", (Object)TextUtil.colorful(powerSpecialIdentifierAmaterasu));
                                    }
                                    else {
                                        if (!powerSpecialData.equalsIgnoreCase("Identifier_Nero_Beam")) {
                                            continue;
                                        }
                                        final String path2 = "Configuration.Power.Special.Identifier_Nero_Beam";
                                        final String powerSpecialIdentifierNeroBeam = powerSpecialDataSection.getString(powerSpecialData);
                                        config.set("Configuration.Power.Special.Identifier_Nero_Beam", (Object)TextUtil.colorful(powerSpecialIdentifierNeroBeam));
                                    }
                                }
                            }
                        }
                    }
                    else if (data.equalsIgnoreCase("Element")) {
                        final ConfigurationSection elementDataSection = dataSection.getConfigurationSection(data);
                        for (final String elementData : elementDataSection.getKeys(false)) {
                            if (elementData.equalsIgnoreCase("Format")) {
                                final String path = "Configuration.Element.Format";
                                final String elementFormat = elementDataSection.getString(elementData);
                                config.set("Configuration.Element.Format", (Object)TextUtil.colorful(elementFormat));
                            }
                        }
                    }
                    else if (data.equalsIgnoreCase("Socket")) {
                        final ConfigurationSection socketDataSection = dataSection.getConfigurationSection(data);
                        for (final String socketData : socketDataSection.getKeys(false)) {
                            if (socketData.equalsIgnoreCase("Format_Slot")) {
                                final String path = "Configuration.Socket.Format_Slot";
                                final String socketFormatSlot = socketDataSection.getString(socketData);
                                config.set("Configuration.Socket.Format_Slot", (Object)TextUtil.colorful(socketFormatSlot));
                            }
                            else if (socketData.equalsIgnoreCase("Format_Slot_Empty")) {
                                final String path = "Configuration.Socket.Format_Slot_Empty";
                                final String socketFormatSlotEmpty = socketDataSection.getString(socketData);
                                config.set("Configuration.Socket.Format_Slot_Empty", (Object)TextUtil.colorful(socketFormatSlotEmpty));
                            }
                            else if (socketData.equalsIgnoreCase("Format_Slot_Locked")) {
                                final String path = "Configuration.Socket.Format_Slot_Locked";
                                final String socketFormatSlotLocked = socketDataSection.getString(socketData);
                                config.set("Configuration.Socket.Format_Slot_Locked", (Object)TextUtil.colorful(socketFormatSlotLocked));
                            }
                            else if (socketData.equalsIgnoreCase("Type_Item_Weapon")) {
                                final String path = "Configuration.Socket.Type_Item_Weapon";
                                final String socketTypeItemWeapon = socketDataSection.getString(socketData);
                                config.set("Configuration.Socket.Type_Item_Weapon", (Object)TextUtil.colorful(socketTypeItemWeapon));
                            }
                            else if (socketData.equalsIgnoreCase("Type_Item_Armor")) {
                                final String path = "Configuration.Socket.Type_Item_Armor";
                                final String socketTypeItemArmor = socketDataSection.getString(socketData);
                                config.set("Configuration.Socket.Type_Item_Armor", (Object)TextUtil.colorful(socketTypeItemArmor));
                            }
                            else if (socketData.equalsIgnoreCase("Type_Item_Universal")) {
                                final String path = "Configuration.Socket.Type_Item_Universal";
                                final String socketTypeItemUniversal = socketDataSection.getString(socketData);
                                config.set("Configuration.Socket.Type_Item_Universal", (Object)TextUtil.colorful(socketTypeItemUniversal));
                            }
                            else if (socketData.equalsIgnoreCase("Cost_Socket")) {
                                final String path = "Configuration.Socket.Cost_Socket";
                                final double socketCostSocket = socketDataSection.getDouble(socketData);
                                config.set("Configuration.Socket.Cost_Socket", (Object)Math.max(0.0, socketCostSocket));
                            }
                            else if (socketData.equalsIgnoreCase("Cost_Unlock")) {
                                final String path = "Configuration.Socket.Cost_Unlock";
                                final double socketCostUnlock = socketDataSection.getDouble(socketData);
                                config.set("Configuration.Socket.Cost_Unlock", (Object)Math.max(0.0, socketCostUnlock));
                            }
                            else if (socketData.equalsIgnoreCase("Cost_Desocket")) {
                                final String path = "Configuration.Socket.Cost_Desocket";
                                final double socketCostDesocket = socketDataSection.getDouble(socketData);
                                config.set("Configuration.Socket.Cost_Desocket", (Object)Math.max(0.0, socketCostDesocket));
                            }
                            else {
                                if (!socketData.equalsIgnoreCase("Item")) {
                                    continue;
                                }
                                final ConfigurationSection socketItemDataSection = socketDataSection.getConfigurationSection(socketData);
                                for (final String socketItemData : socketItemDataSection.getKeys(false)) {
                                    if (socketItemData.equalsIgnoreCase("Rod_Unlock")) {
                                        final String path2 = "Configuration.Socket.Item.Rod_Unlock";
                                        final ConfigurationSection itemSection = socketItemDataSection.getConfigurationSection(socketItemData);
                                        final ItemStack socketItemRodUnlock = this.loadItemStack(itemSection);
                                        config.set("Configuration.Socket.Item.Rod_Unlock", (Object)socketItemRodUnlock);
                                    }
                                    else {
                                        if (!socketItemData.equalsIgnoreCase("Rod_Remove")) {
                                            continue;
                                        }
                                        final String path2 = "Configuration.Socket.Item.Rod_Remove";
                                        final ConfigurationSection itemSection = socketItemDataSection.getConfigurationSection(socketItemData);
                                        final ItemStack socketItemRodRemove = this.loadItemStack(itemSection);
                                        config.set("Configuration.Socket.Item.Rod_Remove", (Object)socketItemRodRemove);
                                    }
                                }
                            }
                        }
                    }
                    else if (data.equalsIgnoreCase("Requirement")) {
                        final ConfigurationSection requirementDataSection = dataSection.getConfigurationSection(data);
                        for (final String requirementData : requirementDataSection.getKeys(false)) {
                            if (requirementData.equalsIgnoreCase("Format_Level")) {
                                final String path = "Configuration.Requirement.Format_Level";
                                final String requirementFormatLevel = requirementDataSection.getString(requirementData);
                                config.set("Configuration.Requirement.Format_Level", (Object)TextUtil.colorful(requirementFormatLevel));
                            }
                            else if (requirementData.equalsIgnoreCase("Format_Permission")) {
                                final String path = "Configuration.Requirement.Format_Permission";
                                final String requirementFormatPermission = requirementDataSection.getString(requirementData);
                                config.set("Configuration.Requirement.Format_Permission", (Object)TextUtil.colorful(requirementFormatPermission));
                            }
                            else if (requirementData.equalsIgnoreCase("Format_Class")) {
                                final String path = "Configuration.Requirement.Format_Class";
                                final String requirementFormatClass = requirementDataSection.getString(requirementData);
                                config.set("Configuration.Requirement.Format_Class", (Object)TextUtil.colorful(requirementFormatClass));
                            }
                            else if (requirementData.equalsIgnoreCase("Format_Soul_Unbound")) {
                                final String path = "Configuration.Requirement.Format_Soul_Unbound";
                                final String requirementFormatSoulUnbound = requirementDataSection.getString(requirementData);
                                config.set("Configuration.Requirement.Format_Soul_Unbound", (Object)TextUtil.colorful(requirementFormatSoulUnbound));
                            }
                            else {
                                if (!requirementData.equalsIgnoreCase("Format_Soul_Bound")) {
                                    continue;
                                }
                                final String path = "Configuration.Requirement.Format_Soul_Bound";
                                final String requirementFormatSoulBound = requirementDataSection.getString(requirementData);
                                config.set("Configuration.Requirement.Format_Soul_Bound", (Object)TextUtil.colorful(requirementFormatSoulBound));
                            }
                        }
                    }
                    else if (data.equalsIgnoreCase("Set")) {
                        final ConfigurationSection setDataSection = dataSection.getConfigurationSection(data);
                        for (final String setData : setDataSection.getKeys(false)) {
                            if (setData.equalsIgnoreCase("Format")) {
                                final String path = "Configuration.Set.Format";
                                final List<String> setFormat = (List<String>)setDataSection.getStringList(setData);
                                config.set("Configuration.Set.Format", (Object)setFormat);
                            }
                            else if (setData.equalsIgnoreCase("Format_Component")) {
                                final String path = "Configuration.Set.Format_Component";
                                final String setFormatComponent = setDataSection.getString(setData);
                                config.set("Configuration.Set.Format_Component", (Object)ChatColor.stripColor(TextUtil.colorful(setFormatComponent)));
                            }
                            else if (setData.equalsIgnoreCase("Format_Bonus")) {
                                final String path = "Configuration.Set.Format_Bonus";
                                final String setFormatBonus = setDataSection.getString(setData);
                                config.set("Configuration.Set.Format_Bonus", (Object)ChatColor.stripColor(TextUtil.colorful(setFormatBonus)));
                            }
                            else if (setData.equalsIgnoreCase("Lore_Component_Active")) {
                                final String path = "Configuration.Set.Lore_Component_Active";
                                final String setLoreComponentActive = setDataSection.getString(setData);
                                config.set("Configuration.Set.Lore_Component_Active", (Object)TextUtil.colorful(setLoreComponentActive));
                            }
                            else if (setData.equalsIgnoreCase("Lore_Component_Inactive")) {
                                final String path = "Configuration.Set.Lore_Component_Inactive";
                                final String setLoreComponentInactive = setDataSection.getString(setData);
                                config.set("Configuration.Set.Lore_Component_Inactive", (Object)TextUtil.colorful(setLoreComponentInactive));
                            }
                            else if (setData.equalsIgnoreCase("Lore_Bonus_Active")) {
                                final String path = "Configuration.Set.Lore_Bonus_Active";
                                final String setLoreBonusActive = setDataSection.getString(setData);
                                config.set("Configuration.Set.Lore_Bonus_Active", (Object)TextUtil.colorful(setLoreBonusActive));
                            }
                            else {
                                if (!setData.equalsIgnoreCase("Lore_Bonus_Inactive")) {
                                    continue;
                                }
                                final String path = "Configuration.Set.Lore_Bonus_Inactive";
                                final String setLoreBonusInactive = setDataSection.getString(setData);
                                config.set("Configuration.Set.Lore_Bonus_Inactive", (Object)TextUtil.colorful(setLoreBonusInactive));
                            }
                        }
                    }
                    else {
                        if (!data.equalsIgnoreCase("Misc")) {
                            continue;
                        }
                        final ConfigurationSection miscDataSection = dataSection.getConfigurationSection(data);
                        for (final String miscData : miscDataSection.getKeys(false)) {
                            if (miscData.equalsIgnoreCase("Enable_Particle_Potion")) {
                                final String path = "Configuration.Misc.Enable_Particle_Potion";
                                final boolean miscEnableParticlePotion = miscDataSection.getBoolean(miscData);
                                config.set("Configuration.Misc.Enable_Particle_Potion", (Object)miscEnableParticlePotion);
                            }
                        }
                    }
                }
            }
        }
    }
    
    private final void loadOldConfig(final FileConfiguration config, final FileConfiguration source) {
        for (final String data : source.getKeys(false)) {
            if (data.equalsIgnoreCase("Metrics_Message")) {
                final String path = "Configuration.Metrics.Message";
                final boolean metricsMessage = source.getBoolean(data);
                config.set("Configuration.Metrics.Message", (Object)metricsMessage);
            }
            else if (data.equalsIgnoreCase("Hook_Message")) {
                final String path = "Configuration.Hook.Message";
                final boolean hookMessage = source.getBoolean(data);
                config.set("Configuration.Hook.Message", (Object)hookMessage);
            }
            else if (data.equalsIgnoreCase("Code_Tooltip")) {
                final String path = "Configuration.Utility.Tooltip";
                final String utilityTooltip = source.getString(data);
                config.set("Configuration.Utility.Tooltip", (Object)utilityTooltip);
            }
            else if (data.equalsIgnoreCase("Hook_Message")) {
                final String path = "Configuration.Utility.Currency";
                final String utilityCurrency = source.getString(data);
                config.set("Configuration.Utility.Currency", (Object)utilityCurrency);
            }
            else if (data.equalsIgnoreCase("Effect_Range")) {
                final String path = "Configuration.Effect.Range";
                final double effectRange = source.getDouble(data);
                config.set("Configuration.Effect.Range", (Object)effectRange);
            }
            else if (data.equalsIgnoreCase("List_Content")) {
                final String path = "Configuration.List.Content";
                final int listContent = source.getInt(data);
                config.set("Configuration.List.Content", (Object)listContent);
            }
            else if (data.equalsIgnoreCase("Enable_Vanilla_Modifier")) {
                final String path = "Configuration.Modifier.Enable_Vanilla_Damage";
                final boolean modifierEnableVanillaDamage = source.getBoolean(data);
                config.set("Configuration.Modifier.Enable_Vanilla_Damage", (Object)modifierEnableVanillaDamage);
            }
            else if (data.equalsIgnoreCase("Enable_Vanilla_Formula_Armor")) {
                final String path = "Configuration.Modifier.Enable_Vanilla_Formula_Armor";
                final boolean modifierEnableVanilaFormulaArmor = source.getBoolean(data);
                config.set("Configuration.Modifier.Enable_Vanilla_Formula_Armor", (Object)modifierEnableVanilaFormulaArmor);
            }
            else if (data.equalsIgnoreCase("Enable_CustomLore_Modifier")) {
                final String path = "Configuration.Modifier.Enable_Custom_Modifier";
                final boolean modifierEnableCustomModifier = source.getBoolean(data);
                config.set("Configuration.Modifier.Enable_Custom_Modifier", (Object)modifierEnableCustomModifier);
            }
            else if (data.equalsIgnoreCase("Enable_Flat_Damage")) {
                final String path = "Configuration.Modifier.Enable_Flat_Damage";
                final boolean modifierEnableFlatDamage = source.getBoolean(data);
                config.set("Configuration.Modifier.Enable_Flat_Damage", (Object)modifierEnableFlatDamage);
            }
            else if (data.equalsIgnoreCase("Enable_Balancing_System")) {
                final String path = "Configuration.Modifier.Enable_Balancing_System";
                final boolean modifierEnableBalancingSystem = source.getBoolean(data);
                config.set("Configuration.Modifier.Enable_Balancing_System", (Object)modifierEnableBalancingSystem);
            }
            else if (data.equalsIgnoreCase("Enable_Balancing_MobDamage")) {
                final String path = "Configuration.Modifier.Enable_Balancing_Mob";
                final boolean modifierEnableBalancingMob = source.getBoolean(data);
                config.set("Configuration.Modifier.Enable_Balancing_Mob", (Object)modifierEnableBalancingMob);
            }
            else if (data.equalsIgnoreCase("Custom_Critical")) {
                final String path = "Configuration.Modifier.Enable_Custom_Critical";
                final boolean modifierEnableCustomCritical = source.getBoolean(data);
                config.set("Configuration.Modifier.Enable_Custom_Critical", (Object)modifierEnableCustomCritical);
            }
            else if (data.equalsIgnoreCase("Custom_Mob_Modifier")) {
                final String path = "Configuration.Modifier.Enable_Custom_Mob_Damage";
                final boolean modifierEnableCustomMobDamage = source.getBoolean(data);
                config.set("Configuration.Modifier.Enable_Custom_Mob_Damage", (Object)modifierEnableCustomMobDamage);
            }
            else if (data.equalsIgnoreCase("Defense_Affect_Entity_Explosion")) {
                final String path = "Configuration.Modifier.Defense_Affect_Entity_Explosion";
                final boolean modifierDefenseAffectEntityExplosion = source.getBoolean(data);
                config.set("Configuration.Modifier.Defense_Affect_Entity_Explosion", (Object)modifierDefenseAffectEntityExplosion);
            }
            else if (data.equalsIgnoreCase("Defense_Affect_Block_Explosion")) {
                final String path = "Configuration.Modifier.Defense_Affect_Block_Explosion";
                final boolean modifierDefenseAffectBlockExplosion = source.getBoolean(data);
                config.set("Configuration.Modifier.Defense_Affect_Block_Explosion", (Object)modifierDefenseAffectBlockExplosion);
            }
            else if (data.equalsIgnoreCase("Defense_Affect_Custom")) {
                final String path = "Configuration.Modifier.Defense_Affect_Custom";
                final boolean modifierDefenseAffectCustom = source.getBoolean(data);
                config.set("Configuration.Modifier.Defense_Affect_Custom", (Object)modifierDefenseAffectCustom);
            }
            else if (data.equalsIgnoreCase("Defense_Affect_Contact")) {
                final String path = "Configuration.Modifier.Defense_Affect_Contact";
                final boolean modifierDefenseAffectContact = source.getBoolean(data);
                config.set("Configuration.Modifier.Defense_Affect_Contact", (Object)modifierDefenseAffectContact);
            }
            else if (data.equalsIgnoreCase("Defense_Affect_Fall")) {
                final String path = "Configuration.Modifier.Defense_Affect_Fall";
                final boolean modifierDefenseAffectFall = source.getBoolean(data);
                config.set("Configuration.Modifier.Defense_Affect_Fall", (Object)modifierDefenseAffectFall);
            }
            else if (data.equalsIgnoreCase("Defense_Affect_Falling_Block")) {
                final String path = "Configuration.Modifier.Defense_Affect_Falling_Block";
                final boolean modifierDefenseAffectFallingBlock = source.getBoolean(data);
                config.set("Configuration.Modifier.Defense_Affect_Falling_Block", (Object)modifierDefenseAffectFallingBlock);
            }
            else if (data.equalsIgnoreCase("Defense_Affect_Thorns")) {
                final String path = "Configuration.Modifier.Defense_Affect_Thorn";
                final boolean modifierDefenseAffectThorn = source.getBoolean(data);
                config.set("Configuration.Modifier.Defense_Affect_Thorn", (Object)modifierDefenseAffectThorn);
            }
            else if (data.equalsIgnoreCase("Critical_Attack")) {
                final String path = "Configuration.Modifier.Critical_Attack_Type";
                final String modifierCriticalAttackType = source.getString(data);
                config.set("Configuration.Modifier.Critical_Attack_Type", (Object)modifierCriticalAttackType);
            }
            else if (data.equalsIgnoreCase("Modus_Value")) {
                final String path = "Configuration.Modifier.Modus_Value";
                final double modifierModusValue = source.getDouble(data);
                config.set("Configuration.Modifier.Modus_Value", (Object)modifierModusValue);
            }
            else if (data.equalsIgnoreCase("Scale_Damage_Vanilla")) {
                final String path = "Configuration.Modifier.Scale_Damage_Vanilla";
                final double modifierScaleDamageVanilla = source.getDouble(data);
                config.set("Configuration.Modifier.Scale_Damage_Vanilla", (Object)modifierScaleDamageVanilla);
            }
            else if (data.equalsIgnoreCase("Scale_Damage_Custom")) {
                final String path = "Configuration.Modifier.Scale_Damage_Custom";
                final double modifierScaleDamageCustom = source.getDouble(data);
                config.set("Configuration.Modifier.Scale_Damage_Custom", (Object)modifierScaleDamageCustom);
            }
            else if (data.equalsIgnoreCase("Scale_Damage_Overall")) {
                final String path = "Configuration.Modifier.Scale_Damage_Overall";
                final double modifierScaleDamageOverall = source.getDouble(data);
                config.set("Configuration.Modifier.Scale_Damage_Overall", (Object)modifierScaleDamageOverall);
            }
            else if (data.equalsIgnoreCase("Scale_Defense_Overall")) {
                final String path = "Configuration.Modifier.Scale_Defense_Overall";
                final double modifierScaleDefenseOverall = source.getDouble(data);
                config.set("Configuration.Modifier.Scale_Defense_Overall", (Object)modifierScaleDefenseOverall);
            }
            else if (data.equalsIgnoreCase("Scale_Defense_Physic")) {
                final String path = "Configuration.Modifier.Scale_Defense_Physic";
                final double modifierScaleDefensePhysic = source.getDouble(data);
                config.set("Configuration.Modifier.Scale_Defense_Physic", (Object)modifierScaleDefensePhysic);
            }
            else if (data.equalsIgnoreCase("Scale_Absorb_Effect")) {
                final String path = "Configuration.Modifier.Scale_Absorb_Effect";
                final double modifierScaleAbsorbEffect = source.getDouble(data);
                config.set("Configuration.Modifier.Scale_Absorb_Effect", (Object)modifierScaleAbsorbEffect);
            }
            else if (data.equalsIgnoreCase("Scale_Mob_Damage_Receive")) {
                final String path = "Configuration.Modifier.Scale_Mob_Damage_Receive";
                final double modifierScaleMobDamageReceive = source.getDouble(data);
                config.set("Configuration.Modifier.Scale_Mob_Damage_Receive", (Object)modifierScaleMobDamageReceive);
            }
            else if (data.equalsIgnoreCase("Scale_Exp_OffHand")) {
                final String path = "Configuration.Modifier.Scale_Exp_OffHand";
                final double modifierScaleExpOffHand = source.getDouble(data);
                config.set("Configuration.Modifier.Scale_Exp_OffHand", (Object)modifierScaleExpOffHand);
            }
            else if (data.equalsIgnoreCase("Scale_Exp_Armor")) {
                final String path = "Configuration.Modifier.Scale_Exp_Armor";
                final double modifierScaleExpArmor = source.getDouble(data);
                config.set("Configuration.Modifier.Scale_Exp_Armor", (Object)modifierScaleExpArmor);
            }
            else if (data.equalsIgnoreCase("Exp_Player")) {
                final String path = "Configuration.Drop.Exp_Player";
                final double dropExpPlayer = source.getDouble(data);
                config.set("Configuration.Drop.Exp_Player", (Object)dropExpPlayer);
            }
            else if (data.equalsIgnoreCase("Exp_Mobs")) {
                final String path = "Configuration.Drop.Exp_Mobs";
                final double dropExpMobs = source.getDouble(data);
                config.set("Configuration.Drop.Exp_Mobs", (Object)dropExpMobs);
            }
            else if (data.equalsIgnoreCase("Support_Level_Type")) {
                final String path = "Configuration.Support.Type_Level";
                final String supportTypeLevel = source.getString(data);
                config.set("Configuration.Support.Type_Level", (Object)supportTypeLevel);
            }
            else if (data.equalsIgnoreCase("Support_Class_Type")) {
                final String path = "Configuration.Support.Type_Class";
                final String supportTypeClass = source.getString(data);
                config.set("Configuration.Support.Type_Class", (Object)supportTypeClass);
            }
            else if (data.equalsIgnoreCase("Enable_Item_Broken")) {
                final String path = "Configuration.Stats.Enable_Item_Broken";
                final boolean statsEnableItemBroken = source.getBoolean(data);
                config.set("Configuration.Stats.Enable_Item_Broken", (Object)statsEnableItemBroken);
            }
            else if (data.equalsIgnoreCase("Enable_Stats_Max_Health")) {
                final String path = "Configuration.Stats.Enable_Max_Health";
                final boolean statsEnableMaxHealth = source.getBoolean(data);
                config.set("Configuration.Stats.Enable_Max_Health", (Object)statsEnableMaxHealth);
            }
            else if (data.equalsIgnoreCase("Stats_Format")) {
                final String path = "Configuration.Stats.Format_Value";
                final String statsFormatValue = source.getString(data);
                config.set("Configuration.Stats.Format_Value", (Object)TextUtil.colorful(statsFormatValue));
            }
            else if (data.equalsIgnoreCase("Stats_Exp_Format")) {
                final String path = "Configuration.Stats.Format_Exp";
                final String statsFormatExp = source.getString(data);
                config.set("Configuration.Stats.Format_Exp", (Object)TextUtil.colorful(statsFormatExp));
            }
            else if (data.equalsIgnoreCase("Positive_Value")) {
                final String path = "Configuration.Stats.Lore_Positive_Value";
                final String statsLorePositiveValue = source.getString(data);
                config.set("Configuration.Stats.Lore_Positive_Value", (Object)TextUtil.colorful(statsLorePositiveValue));
            }
            else if (data.equalsIgnoreCase("Negative_Value")) {
                final String path = "Configuration.Stats.Lore_Negative_Value";
                final String statsLoreNegativeValue = source.getString(data);
                config.set("Configuration.Stats.Lore_Negative_Value", (Object)TextUtil.colorful(statsLoreNegativeValue));
            }
            else if (data.equalsIgnoreCase("Range_Symbol")) {
                final String path = "Configuration.Stats.Lore_Range_Symbol";
                final String statsLoreRangeSymbol = source.getString(data);
                config.set("Configuration.Stats.Lore_Range_Symbol", (Object)TextUtil.colorful(statsLoreRangeSymbol));
            }
            else if (data.equalsIgnoreCase("Divider_Symbol")) {
                final String path = "Configuration.Stats.Lore_Divider_Symbol";
                final String statsLoreDividerSymbol = source.getString(data);
                config.set("Configuration.Stats.Lore_Divider_Symbol", (Object)TextUtil.colorful(statsLoreDividerSymbol));
            }
            else if (data.equalsIgnoreCase("Multiplier_Symbol")) {
                final String path = "Configuration.Stats.Lore_Multiplier_Symbol";
                final String statsLoreMultiplierSymbol = source.getString(data);
                config.set("Configuration.Stats.Lore_Multiplier_Symbol", (Object)TextUtil.colorful(statsLoreMultiplierSymbol));
            }
            else if (data.equalsIgnoreCase("Scale_OffHand_Value")) {
                final String path = "Configuration.Stats.Scale_OffHand_Value";
                final double statsScaleOffHandValue = source.getDouble(data);
                config.set("Configuration.Stats.Scale_OffHand_Value", (Object)statsScaleOffHandValue);
            }
            else if (data.equalsIgnoreCase("Scale_Absorb_Effect")) {
                final String path = "Configuration.Stats.Scale_Up_Value";
                final double statsScaleUpValue = source.getDouble(data);
                config.set("Configuration.Stats.Scale_Up_Value", (Object)statsScaleUpValue);
            }
            else if (data.equalsIgnoreCase("Max_Level_Value")) {
                final String path = "Configuration.Stats.Max_Level_Value";
                final double statsMaxLevelValue = source.getInt(data);
                config.set("Configuration.Stats.Max_Level_Value", (Object)statsMaxLevelValue);
            }
            else if (data.equalsIgnoreCase("Damage")) {
                final String path = "Configuration.Stats.Identifier_Damage";
                final String statsIdentifierDamage = source.getString(data);
                config.set("Configuration.Stats.Identifier_Damage", (Object)TextUtil.colorful(statsIdentifierDamage));
            }
            else if (data.equalsIgnoreCase("Penetration")) {
                final String path = "Configuration.Stats.Identifier_Penetration";
                final String statsIdentifierPenetration = source.getString(data);
                config.set("Configuration.Stats.Identifier_Penetration", (Object)TextUtil.colorful(statsIdentifierPenetration));
            }
            else if (data.equalsIgnoreCase("PvP_Damage")) {
                final String path = "Configuration.Stats.Identifier_PvP_Damage";
                final String statsIdentifierPvPDamage = source.getString(data);
                config.set("Configuration.Stats.Identifier_PvP_Damage", (Object)TextUtil.colorful(statsIdentifierPvPDamage));
            }
            else if (data.equalsIgnoreCase("PvE_Damage")) {
                final String path = "Configuration.Stats.Identifier_PvE_Damage";
                final String statsIdentifierPvEDamage = source.getString(data);
                config.set("Configuration.Stats.Identifier_PvE_Damage", (Object)TextUtil.colorful(statsIdentifierPvEDamage));
            }
            else if (data.equalsIgnoreCase("Defense")) {
                final String path = "Configuration.Stats.Identifier_Defense";
                final String statsIdentifierDefense = source.getString(data);
                config.set("Configuration.Stats.Identifier_Defense", (Object)TextUtil.colorful(statsIdentifierDefense));
            }
            else if (data.equalsIgnoreCase("PvP_Defense")) {
                final String path = "Configuration.Stats.Identifier_PvP_Defense";
                final String statsIdentifierPvPDefense = source.getString(data);
                config.set("Configuration.Stats.Identifier_PvP_Defense", (Object)TextUtil.colorful(statsIdentifierPvPDefense));
            }
            else if (data.equalsIgnoreCase("PvE_Defense")) {
                final String path = "Configuration.Stats.Identifier_PvE_Defense";
                final String statsIdentifierPvEDefense = source.getString(data);
                config.set("Configuration.Stats.Identifier_PvE_Defense", (Object)TextUtil.colorful(statsIdentifierPvEDefense));
            }
            else if (data.equalsIgnoreCase("Health")) {
                final String path = "Configuration.Stats.Identifier_Health";
                final String statsIdentifierHealth = source.getString(data);
                config.set("Configuration.Stats.Identifier_Health", (Object)TextUtil.colorful(statsIdentifierHealth));
            }
            else if (data.equalsIgnoreCase("Health_Regen")) {
                final String path = "Configuration.Stats.Identifier_Health_Regen";
                final String statsIdentifierHealthRegen = source.getString(data);
                config.set("Configuration.Stats.Identifier_Health_Regen", (Object)TextUtil.colorful(statsIdentifierHealthRegen));
            }
            else if (data.equalsIgnoreCase("Stamina_Max")) {
                final String path = "Configuration.Stats.Identifier_Stamina_Max";
                final String statsIdentifierStaminaMax = source.getString(data);
                config.set("Configuration.Stats.Identifier_Stamina_Max", (Object)TextUtil.colorful(statsIdentifierStaminaMax));
            }
            else if (data.equalsIgnoreCase("Stamina_Regen")) {
                final String path = "Configuration.Stats.Identifier_Stamina_Regen";
                final String statsIdentifierStaminaRegen = source.getString(data);
                config.set("Configuration.Stats.Identifier_Stamina_Regen", (Object)TextUtil.colorful(statsIdentifierStaminaRegen));
            }
            else if (data.equalsIgnoreCase("Attack_AoE_Radius")) {
                final String path = "Configuration.Stats.Identifier_Attack_AoE_Radius";
                final String statsIdentifierAttackAoERadius = source.getString(data);
                config.set("Configuration.Stats.Identifier_Attack_AoE_Radius", (Object)TextUtil.colorful(statsIdentifierAttackAoERadius));
            }
            else if (data.equalsIgnoreCase("Attack_AoE_Damage")) {
                final String path = "Configuration.Stats.Identifier_Attack_AoE_Damage";
                final String statsIdentifierAttackAoEDamage = source.getString(data);
                config.set("Configuration.Stats.Identifier_Attack_AoE_Damage", (Object)TextUtil.colorful(statsIdentifierAttackAoEDamage));
            }
            else if (data.equalsIgnoreCase("Critical_Chance")) {
                final String path = "Configuration.Stats.Identifier_Critical_Chance";
                final String statsIdentifierCriticalChance = source.getString(data);
                config.set("Configuration.Stats.Identifier_Critical_Chance", (Object)TextUtil.colorful(statsIdentifierCriticalChance));
            }
            else if (data.equalsIgnoreCase("Critical_Damage")) {
                final String path = "Configuration.Stats.Identifier_Critical_Damage";
                final String tatsIdentifierCriticalDamages = source.getString(data);
                config.set("Configuration.Stats.Identifier_Critical_Damage", (Object)TextUtil.colorful(tatsIdentifierCriticalDamages));
            }
            else if (data.equalsIgnoreCase("Block_Amount")) {
                final String path = "Configuration.Stats.Identifier_Block_Amount";
                final String statsIdentifierBlockAmount = source.getString(data);
                config.set("Configuration.Stats.Identifier_Block_Amount", (Object)TextUtil.colorful(statsIdentifierBlockAmount));
            }
            else if (data.equalsIgnoreCase("Block_Rate")) {
                final String path = "Configuration.Stats.Identifier_Block_Rate";
                final String statsIdentifierBlockRate = source.getString(data);
                config.set("Configuration.Stats.Identifier_Block_Rate", (Object)TextUtil.colorful(statsIdentifierBlockRate));
            }
            else if (data.equalsIgnoreCase("Hit_Rate")) {
                final String path = "Configuration.Stats.Identifier_Hit_Rate";
                final String statsIdentifierHitRate = source.getString(data);
                config.set("Configuration.Stats.Identifier_Hit_Rate", (Object)TextUtil.colorful(statsIdentifierHitRate));
            }
            else if (data.equalsIgnoreCase("Dodge_Rate")) {
                final String path = "Configuration.Stats.Identifier_Dodge_Rate";
                final String statsIdentifierDodgeRate = source.getString(data);
                config.set("Configuration.Stats.Identifier_Dodge_Rate", (Object)TextUtil.colorful(statsIdentifierDodgeRate));
            }
            else if (data.equalsIgnoreCase("Durability")) {
                final String path = "Configuration.Stats.Identifier_Durability";
                final String statsIdentifierDurability = source.getString(data);
                config.set("Configuration.Stats.Identifier_Durability", (Object)TextUtil.colorful(statsIdentifierDurability));
            }
            else if (data.equalsIgnoreCase("Level")) {
                final String path = "Configuration.Stats.Identifier_Level";
                final String statsIdentifierLevel = source.getString(data);
                config.set("Configuration.Stats.Identifier_Level", (Object)TextUtil.colorful(statsIdentifierLevel));
            }
            else if (data.equalsIgnoreCase("Ability_Format")) {
                final String path = "Configuration.Ability.Format";
                final String abilityFormat = source.getString(data);
                config.set("Configuration.Ability.Format", (Object)TextUtil.colorful(abilityFormat));
            }
            else if (data.equalsIgnoreCase("Enable_OffHand_Ability")) {
                final String path = "Configuration.Ability.Weapon.Enable_OffHand";
                final boolean statsAbilityWeaponEnableOffHand = source.getBoolean(data);
                config.set("Configuration.Ability.Weapon.Enable_OffHand", (Object)statsAbilityWeaponEnableOffHand);
            }
            else if (data.equalsIgnoreCase("Flame")) {
                final String path = "Configuration.Ability.Weapon.Identifier_Flame";
                final String abilityWeaponIdentifierFlame = source.getString(data);
                config.set("Configuration.Ability.Weapon.Identifier_Flame", (Object)TextUtil.colorful(abilityWeaponIdentifierFlame));
            }
            else if (data.equalsIgnoreCase("Poison")) {
                final String path = "Configuration.Ability.Weapon.Identifier_Poison";
                final String abilityWeaponIdentifierPoison = source.getString(data);
                config.set("Configuration.Ability.Weapon.Identifier_Poison", (Object)TextUtil.colorful(abilityWeaponIdentifierPoison));
            }
            else if (data.equalsIgnoreCase("Wither")) {
                final String path = "Configuration.Ability.Weapon.Identifier_Wither";
                final String abilityWeaponIdentifierWither = source.getString(data);
                config.set("Configuration.Ability.Weapon.Identifier_Wither", (Object)TextUtil.colorful(abilityWeaponIdentifierWither));
            }
            else if (data.equalsIgnoreCase("Lightning")) {
                final String path = "Configuration.Ability.Weapon.Identifier_Lightning";
                final String abilityWeaponIdentifierLightning = source.getString(data);
                config.set("Configuration.Ability.Weapon.Identifier_Lightning", (Object)TextUtil.colorful(abilityWeaponIdentifierLightning));
            }
            else if (data.equalsIgnoreCase("Cannibalism")) {
                final String path = "Configuration.Ability.Weapon.Identifier_Cannibalism";
                final String abilityWeaponIdentifierCannibalism = source.getString(data);
                config.set("Configuration.Ability.Weapon.Identifier_Cannibalism", (Object)TextUtil.colorful(abilityWeaponIdentifierCannibalism));
            }
            else if (data.equalsIgnoreCase("Vampirism")) {
                final String path = "Configuration.Ability.Weapon.Identifier_Vampirism";
                final String abilityWeaponIdentifierVampirism = source.getString(data);
                config.set("Configuration.Ability.Weapon.Identifier_Vampirism", (Object)TextUtil.colorful(abilityWeaponIdentifierVampirism));
            }
            else if (data.equalsIgnoreCase("Freeze")) {
                final String path = "Configuration.Ability.Weapon.Identifier_Freeze";
                final String abilityWeaponIdentifierFreeze = source.getString(data);
                config.set("Configuration.Ability.Weapon.Identifier_Freeze", (Object)TextUtil.colorful(abilityWeaponIdentifierFreeze));
            }
            else if (data.equalsIgnoreCase("Roots")) {
                final String path = "Configuration.Ability.Weapon.Identifier_Roots";
                final String abilityWeaponIdentifierRoots = source.getString(data);
                config.set("Configuration.Ability.Weapon.Identifier_Roots", (Object)TextUtil.colorful(abilityWeaponIdentifierRoots));
            }
            else if (data.equalsIgnoreCase("Curse")) {
                final String path = "Configuration.Ability.Weapon.Identifier_Curse";
                final String abilityWeaponIdentifierCurse = source.getString(data);
                config.set("Configuration.Ability.Weapon.Identifier_Curse", (Object)TextUtil.colorful(abilityWeaponIdentifierCurse));
            }
            else if (data.equalsIgnoreCase("Slowness")) {
                final String path = "Configuration.Ability.Weapon.Identifier_Slowness";
                final String abilityWeaponIdentifierSlowness = source.getString(data);
                config.set("Configuration.Ability.Weapon.Identifier_Slowness", (Object)TextUtil.colorful(abilityWeaponIdentifierSlowness));
            }
            else if (data.equalsIgnoreCase("Fatigue")) {
                final String path = "Configuration.Ability.Weapon.Identifier_Fatigue";
                final String abilityWeaponIdentifierFatigue = source.getString(data);
                config.set("Configuration.Ability.Weapon.Identifier_Fatigue", (Object)TextUtil.colorful(abilityWeaponIdentifierFatigue));
            }
            else if (data.equalsIgnoreCase("Nausea")) {
                final String path = "Configuration.Ability.Weapon.Identifier_Nausea";
                final String abilityWeaponIdentifierNausea = source.getString(data);
                config.set("Configuration.Ability.Weapon.Identifier_Nausea", (Object)TextUtil.colorful(abilityWeaponIdentifierNausea));
            }
            else if (data.equalsIgnoreCase("Weakness")) {
                final String path = "Configuration.Ability.Weapon.Identifier_Weakness";
                final String abilityWeaponIdentifierWeakness = source.getString(data);
                config.set("Configuration.Ability.Weapon.Identifier_Weakness", (Object)TextUtil.colorful(abilityWeaponIdentifierWeakness));
            }
            else if (data.equalsIgnoreCase("Blind")) {
                final String path = "Configuration.Ability.Weapon.Identifier_Blind";
                final String abilityWeaponIdentifierBlind = source.getString(data);
                config.set("Configuration.Ability.Weapon.Identifier_Blind", (Object)TextUtil.colorful(abilityWeaponIdentifierBlind));
            }
            else if (data.equalsIgnoreCase("Hungry")) {
                final String path = "Configuration.Ability.Weapon.Identifier_Hungry";
                final String abilityWeaponIdentifierHungry = source.getString(data);
                config.set("Configuration.Ability.Weapon.Identifier_Hungry", (Object)TextUtil.colorful(abilityWeaponIdentifierHungry));
            }
            else if (data.equalsIgnoreCase("Levitation")) {
                final String path = "Configuration.Ability.Weapon.Identifier_Levitation";
                final String abilityWeaponIdentifierLevitation = source.getString(data);
                config.set("Configuration.Ability.Weapon.Identifier_Levitation", (Object)TextUtil.colorful(abilityWeaponIdentifierLevitation));
            }
            else if (data.equalsIgnoreCase("Bad_Luck")) {
                final String path = "Configuration.Ability.Weapon.Identifier_Bad_Luck";
                final String abilityWeaponIdentifierBadLuck = source.getString(data);
                config.set("Configuration.Ability.Weapon.Identifier_Bad_Luck", (Object)TextUtil.colorful(abilityWeaponIdentifierBadLuck));
            }
            else if (data.equalsIgnoreCase("Harm")) {
                final String path = "Configuration.Ability.Weapon.Identifier_Harm";
                final String abilityWeaponIdentifierHarm = source.getString(data);
                config.set("Configuration.Ability.Weapon.Identifier_Harm", (Object)TextUtil.colorful(abilityWeaponIdentifierHarm));
            }
            else if (data.equalsIgnoreCase("Flame_Wheel")) {
                final String path = "Configuration.Ability.Weapon.Identifier_Flame_Wheel";
                final String abilityWeaponIdentifierFlameWheel = source.getString(data);
                config.set("Configuration.Ability.Weapon.Identifier_Flame_Wheel", (Object)TextUtil.colorful(abilityWeaponIdentifierFlameWheel));
            }
            else if (data.equalsIgnoreCase("Air_Shock")) {
                final String path = "Configuration.Ability.Weapon.Identifier_Air_Shock";
                final String abilityWeaponIdentifierAirShock = source.getString(data);
                config.set("Configuration.Ability.Weapon.Identifier_Air_Shock", (Object)TextUtil.colorful(abilityWeaponIdentifierAirShock));
            }
            else if (data.equalsIgnoreCase("Dark_Flame")) {
                final String path = "Configuration.Ability.Weapon.Identifier_Dark_Flame";
                final String abilityWeaponIdentifierDarkFlame = source.getString(data);
                config.set("Configuration.Ability.Weapon.Identifier_Dark_Flame", (Object)TextUtil.colorful(abilityWeaponIdentifierDarkFlame));
            }
            else if (data.equalsIgnoreCase("Dark_Impact")) {
                final String path = "Configuration.Ability.Weapon.Identifier_Dark_Impact";
                final String abilityWeaponIdentifierDarkImpact = source.getString(data);
                config.set("Configuration.Ability.Weapon.Identifier_Dark_Impact", (Object)TextUtil.colorful(abilityWeaponIdentifierDarkImpact));
            }
            else if (data.equalsIgnoreCase("Venom_Spread")) {
                final String path = "Configuration.Ability.Weapon.Identifier_Venom_Spread";
                final String abilityWeaponIdentifierVenomSpread = source.getString(data);
                config.set("Configuration.Ability.Weapon.Identifier_Venom_Spread", (Object)TextUtil.colorful(abilityWeaponIdentifierVenomSpread));
            }
            else if (data.equalsIgnoreCase("Venom_Blast")) {
                final String path = "Configuration.Ability.Weapon.Identifier_Venom_Blast";
                final String abilityWeaponIdentifierVenomBlast = source.getString(data);
                config.set("Configuration.Ability.Weapon.Identifier_Venom_Blast", (Object)TextUtil.colorful(abilityWeaponIdentifierVenomBlast));
            }
            else if (data.equalsIgnoreCase("Bubble_Deflector")) {
                final String path = "Configuration.Ability.Weapon.Identifier_Bubble_Deflector";
                final String abilityWeaponIdentifierBubbleDeflector = source.getString(data);
                config.set("Configuration.Ability.Weapon.Identifier_Bubble_Deflector", (Object)TextUtil.colorful(abilityWeaponIdentifierBubbleDeflector));
            }
            else if (data.equalsIgnoreCase("Enable_Buffs_Calculation")) {
                final String path = "Configuration.Passive.Enable_Grade_Calculation";
                final boolean passiveEnableGradeCalculation = source.getBoolean(data);
                config.set("Configuration.Passive.Enable_Grade_Calculation", (Object)passiveEnableGradeCalculation);
            }
            else if (data.equalsIgnoreCase("Enable_Buffs_Hand")) {
                final String path = "Configuration.Passive.Enable_Hand";
                final boolean passiveEnableHand = source.getBoolean(data);
                config.set("Configuration.Passive.Enable_Hand", (Object)passiveEnableHand);
            }
            else if (data.equalsIgnoreCase("Buffs_Format")) {
                final String path = "Configuration.Passive.Buff.Format";
                final String passiveBuffFormat = source.getString(data);
                config.set("Configuration.Passive.Buff.Format", (Object)TextUtil.colorful(passiveBuffFormat));
            }
            else if (data.equalsIgnoreCase("Strength")) {
                final String path = "Configuration.Passive.Buff.Identifier_Strength";
                final String passiveBuffIdentifierStrength = source.getString(data);
                config.set("Configuration.Passive.Buff.Identifier_Strength", (Object)TextUtil.colorful(passiveBuffIdentifierStrength));
            }
            else if (data.equalsIgnoreCase("Protection")) {
                final String path = "Configuration.Passive.Buff.Identifier_Protection";
                final String passiveBuffIdentifierProtection = source.getString(data);
                config.set("Configuration.Passive.Buff.Identifier_Protection", (Object)TextUtil.colorful(passiveBuffIdentifierProtection));
            }
            else if (data.equalsIgnoreCase("Vision")) {
                final String path = "Configuration.Passive.Buff.Identifier_Vision";
                final String passiveBuffIdentifierVision = source.getString(data);
                config.set("Configuration.Passive.Buff.Identifier_Vision", (Object)TextUtil.colorful(passiveBuffIdentifierVision));
            }
            else if (data.equalsIgnoreCase("Jump")) {
                final String path = "Configuration.Passive.Buff.Identifier_Jump";
                final String passiveBuffIdentifierJump = source.getString(data);
                config.set("Configuration.Passive.Buff.Identifier_Jump", (Object)TextUtil.colorful(passiveBuffIdentifierJump));
            }
            else if (data.equalsIgnoreCase("Absorb")) {
                final String path = "Configuration.Passive.Buff.Identifier_Absorb";
                final String passiveBuffIdentifierAbsorb = source.getString(data);
                config.set("Configuration.Passive.Buff.Identifier_Absorb", (Object)TextUtil.colorful(passiveBuffIdentifierAbsorb));
            }
            else if (data.equalsIgnoreCase("Fire_Resist")) {
                final String path = "Configuration.Passive.Buff.Identifier_Fire_Resistance";
                final String passiveBuffIdentifierFireResistance = source.getString(data);
                config.set("Configuration.Passive.Buff.Identifier_Fire_Resistance", (Object)TextUtil.colorful(passiveBuffIdentifierFireResistance));
            }
            else if (data.equalsIgnoreCase("Invisibility")) {
                final String path = "Configuration.Passive.Buff.Identifier_Invisibility";
                final String passiveBuffIdentifierInvisibility = source.getString(data);
                config.set("Configuration.Passive.Buff.Identifier_Invisibility", (Object)TextUtil.colorful(passiveBuffIdentifierInvisibility));
            }
            else if (data.equalsIgnoreCase("Luck")) {
                final String path = "Configuration.Passive.Buff.Identifier_Luck";
                final String passiveBuffIdentifierLuck = source.getString(data);
                config.set("Configuration.Passive.Buff.Identifier_Luck", (Object)TextUtil.colorful(passiveBuffIdentifierLuck));
            }
            else if (data.equalsIgnoreCase("Health_Boost")) {
                final String path = "Configuration.Passive.Buff.Identifier_Health_Boost";
                final String passiveBuffIdentifierHealthBoost = source.getString(data);
                config.set("Configuration.Passive.Buff.Identifier_Health_Boost", (Object)TextUtil.colorful(passiveBuffIdentifierHealthBoost));
            }
            else if (data.equalsIgnoreCase("Regeneration")) {
                final String path = "Configuration.Passive.Buff.Identifier_Regeneration";
                final String passiveBuffIdentifierRegeneration = source.getString(data);
                config.set("Configuration.Passive.Buff.Identifier_Regeneration", (Object)TextUtil.colorful(passiveBuffIdentifierRegeneration));
            }
            else if (data.equalsIgnoreCase("Saturation")) {
                final String path = "Configuration.Passive.Buff.Identifier_Saturation";
                final String passiveBuffIdentifierSaturation = source.getString(data);
                config.set("Configuration.Passive.Buff.Identifier_Saturation", (Object)TextUtil.colorful(passiveBuffIdentifierSaturation));
            }
            else if (data.equalsIgnoreCase("Speed")) {
                final String path = "Configuration.Passive.Buff.Identifier_Speed";
                final String passiveBuffIdentifierSpeed = source.getString(data);
                config.set("Configuration.Passive.Buff.Identifier_Speed", (Object)TextUtil.colorful(passiveBuffIdentifierSpeed));
            }
            else if (data.equalsIgnoreCase("Water_Breathing")) {
                final String path = "Configuration.Passive.Buff.Identifier_Water_Breathing";
                final String passiveBuffIdentifierWaterBreathing = source.getString(data);
                config.set("Configuration.Passive.Buff.Identifier_Water_Breathing", (Object)TextUtil.colorful(passiveBuffIdentifierWaterBreathing));
            }
            else if (data.equalsIgnoreCase("Haste")) {
                final String path = "Configuration.Passive.Buff.Identifier_Haste";
                final String passiveBuffIdentifierHaste = source.getString(data);
                config.set("Configuration.Passive.Buff.Identifier_Haste", (Object)TextUtil.colorful(passiveBuffIdentifierHaste));
            }
            else if (data.equalsIgnoreCase("Debuffs_Format")) {
                final String path = "Configuration.Passive.Debuff.Format";
                final String passiveDebuffFormat = source.getString(data);
                config.set("Configuration.Passive.Debuff.Format", (Object)TextUtil.colorful(passiveDebuffFormat));
            }
            else if (data.equalsIgnoreCase("Debuff_Weak")) {
                final String path = "Configuration.Passive.Debuff.Identifier_Weak";
                final String passiveDebuffIdentifierWeak = source.getString(data);
                config.set("Configuration.Passive.Debuff.Identifier_Weak", (Object)TextUtil.colorful(passiveDebuffIdentifierWeak));
            }
            else if (data.equalsIgnoreCase("Debuff_Slow")) {
                final String path = "Configuration.Passive.Debuff.Identifier_Slow";
                final String passiveDebuffIdentifierSlow = source.getString(data);
                config.set("Configuration.Passive.Debuff.Identifier_Slow", (Object)TextUtil.colorful(passiveDebuffIdentifierSlow));
            }
            else if (data.equalsIgnoreCase("Debuff_Blind")) {
                final String path = "Configuration.Passive.Debuff.Identifier_Blind";
                final String passiveDebuffIdentifierBlind = source.getString(data);
                config.set("Configuration.Passive.Debuff.Identifier_Blind", (Object)TextUtil.colorful(passiveDebuffIdentifierBlind));
            }
            else if (data.equalsIgnoreCase("Debuff_Confuse")) {
                final String path = "Configuration.Passive.Debuff.Identifier_Confuse";
                final String passiveDebuffIdentifierConfuse = source.getString(data);
                config.set("Configuration.Passive.Debuff.Identifier_Confuse", (Object)TextUtil.colorful(passiveDebuffIdentifierConfuse));
            }
            else if (data.equalsIgnoreCase("Debuff_Starve")) {
                final String path = "Configuration.Passive.Debuff.Identifier_Starve";
                final String passiveDebuffIdentifierStarve = source.getString(data);
                config.set("Configuration.Passive.Debuff.Identifier_Starve", (Object)TextUtil.colorful(passiveDebuffIdentifierStarve));
            }
            else if (data.equalsIgnoreCase("Debuff_Toxic")) {
                final String path = "Configuration.Passive.Debuff.Identifier_Toxic";
                final String passiveDebuffIdentifierToxic = source.getString(data);
                config.set("Configuration.Passive.Debuff.Identifier_Toxic", (Object)TextUtil.colorful(passiveDebuffIdentifierToxic));
            }
            else if (data.equalsIgnoreCase("Debuff_Fatigue")) {
                final String path = "Configuration.Passive.Debuff.Identifier_Fatigue";
                final String passiveDebuffIdentifierFatigue = source.getString(data);
                config.set("Configuration.Passive.Debuff.Identifier_Fatigue", (Object)TextUtil.colorful(passiveDebuffIdentifierFatigue));
            }
            else if (data.equalsIgnoreCase("Debuff_Wither")) {
                final String path = "Configuration.Passive.Debuff.Identifier_Wither";
                final String passiveDebuffIdentifierWither = source.getString(data);
                config.set("Configuration.Passive.Debuff.Identifier_Wither", (Object)TextUtil.colorful(passiveDebuffIdentifierWither));
            }
            else if (data.equalsIgnoreCase("Debuff_Unluck")) {
                final String path = "Configuration.Passive.Debuff.Identifier_Unluck";
                final String passiveDebuffIdentifierUnluck = source.getString(data);
                config.set("Configuration.Passive.Debuff.Identifier_Unluck", (Object)TextUtil.colorful(passiveDebuffIdentifierUnluck));
            }
            else if (data.equalsIgnoreCase("Enable_Power_Message")) {
                final String path = "Configuration.Power.Enable_Message_Cooldown";
                final boolean powerEnableMessageCooldown = source.getBoolean(data);
                config.set("Configuration.Power.Enable_Message_Cooldown", (Object)powerEnableMessageCooldown);
            }
            else if (data.equalsIgnoreCase("Power_Format")) {
                final String path = "Configuration.Power.Format";
                final String powerFormat = source.getString(data);
                config.set("Configuration.Power.Format", (Object)TextUtil.colorful(powerFormat));
            }
            else if (data.equalsIgnoreCase("Left_Click")) {
                final String path = "Configuration.Power.Click_Left";
                final String powerClickLeft = source.getString(data);
                config.set("Configuration.Power.Click_Left", (Object)TextUtil.colorful(powerClickLeft));
            }
            else if (data.equalsIgnoreCase("Right_Click")) {
                final String path = "Configuration.Power.Click_Right";
                final String powerClickRight = source.getString(data);
                config.set("Configuration.Power.Click_Right", (Object)TextUtil.colorful(powerClickRight));
            }
            else if (data.equalsIgnoreCase("Shift_Left_Click")) {
                final String path = "Configuration.Power.Click_Shift_Left";
                final String powerClickShiftLeft = source.getString(data);
                config.set("Configuration.Power.Click_Shift_Left", (Object)TextUtil.colorful(powerClickShiftLeft));
            }
            else if (data.equalsIgnoreCase("Shift_Right_Click")) {
                final String path = "Configuration.Power.Click_Shift_Right";
                final String powerClickShiftRight = source.getString(data);
                config.set("Configuration.Power.Click_Shift_Right", (Object)TextUtil.colorful(powerClickShiftRight));
            }
            else if (data.equalsIgnoreCase("Projectile_Arrow")) {
                final String path = "Configuration.Power.Projectile.Identifier_Arrow";
                final String powerProjectileIdentifierArrow = source.getString(data);
                config.set("Configuration.Power.Projectile.Identifier_Arrow", (Object)TextUtil.colorful(powerProjectileIdentifierArrow));
            }
            else if (data.equalsIgnoreCase("Projectile_SnowBall")) {
                final String path = "Configuration.Power.Projectile.Identifier_Snow_Ball";
                final String powerProjectileIdentifierSnowBall = source.getString(data);
                config.set("Configuration.Power.Projectile.Identifier_Snow_Ball", (Object)TextUtil.colorful(powerProjectileIdentifierSnowBall));
            }
            else if (data.equalsIgnoreCase("Projectile_Egg")) {
                final String path = "Configuration.Power.Projectile.Identifier_Egg";
                final String powerProjectileIdentifierEgg = source.getString(data);
                config.set("Configuration.Power.Projectile.Identifier_Egg", (Object)TextUtil.colorful(powerProjectileIdentifierEgg));
            }
            else if (data.equalsIgnoreCase("Projectile_Flame_Arrow")) {
                final String path = "Configuration.Power.Projectile.Identifier_Flame_Arrow";
                final String powerProjectileIdentifierFlameArrow = source.getString(data);
                config.set("Configuration.Power.Projectile.Identifier_Flame_Arrow", (Object)TextUtil.colorful(powerProjectileIdentifierFlameArrow));
            }
            else if (data.equalsIgnoreCase("Projectile_Flame_Ball")) {
                final String path = "Configuration.Power.Projectile.Identifier_Flame_Ball";
                final String powerProjectileIdentifierFlameBall = source.getString(data);
                config.set("Configuration.Power.Projectile.Identifier_Flame_Ball", (Object)TextUtil.colorful(powerProjectileIdentifierFlameBall));
            }
            else if (data.equalsIgnoreCase("Projectile_Flame_Egg")) {
                final String path = "Configuration.Power.Projectile.Identifier_Flame_Egg";
                final String powerProjectileIdentifierFlameEgg = source.getString(data);
                config.set("Configuration.Power.Projectile.Identifier_Flame_Egg", (Object)TextUtil.colorful(powerProjectileIdentifierFlameEgg));
            }
            else if (data.equalsIgnoreCase("Projectile_Small_Fireball")) {
                final String path = "Configuration.Power.Projectile.Identifier_Small_Fireball";
                final String powerProjectileIdentifierSmallFireball = source.getString(data);
                config.set("Configuration.Power.Projectile.Identifier_Small_Fireball", (Object)TextUtil.colorful(powerProjectileIdentifierSmallFireball));
            }
            else if (data.equalsIgnoreCase("Projectile_Large_Fireball")) {
                final String path = "Configuration.Power.Projectile.Identifier_Large_Fireball";
                final String powerProjectileIdentifierLargeFireball = source.getString(data);
                config.set("Configuration.Power.Projectile.Identifier_Large_Fireball", (Object)TextUtil.colorful(powerProjectileIdentifierLargeFireball));
            }
            else if (data.equalsIgnoreCase("Projectile_WitherSkull")) {
                final String path = "Configuration.Power.Projectile.Identifier_Wither_Skull";
                final String powerProjectileIdentifierWitherSkull = source.getString(data);
                config.set("Configuration.Power.Projectile.Identifier_Wither_Skull", (Object)TextUtil.colorful(powerProjectileIdentifierWitherSkull));
            }
            else if (data.equalsIgnoreCase("Special_Blink")) {
                final String path = "Configuration.Power.Special.Identifier_Blink";
                final String powerSpecialIdentifierBlink = source.getString(data);
                config.set("Configuration.Power.Special.Identifier_Blink", (Object)TextUtil.colorful(powerSpecialIdentifierBlink));
            }
            else if (data.equalsIgnoreCase("Special_Fissure")) {
                final String path = "Configuration.Power.Special.Identifier_Fissure";
                final String powerSpecialIdentifierFissure = source.getString(data);
                config.set("Configuration.Power.Special.Identifier_Fissure", (Object)TextUtil.colorful(powerSpecialIdentifierFissure));
            }
            else if (data.equalsIgnoreCase("Special_Ice_Spikes")) {
                final String path = "Configuration.Power.Special.Identifier_Ice_Spikes";
                final String powerSpecialIdentifierIceSpikes = source.getString(data);
                config.set("Configuration.Power.Special.Identifier_Ice_Spikes", (Object)TextUtil.colorful(powerSpecialIdentifierIceSpikes));
            }
            else if (data.equalsIgnoreCase("Special_Amaterasu")) {
                final String path = "Configuration.Power.Special.Identifier_Amaterasu";
                final String powerSpecialIdentifierAmaterasu = source.getString(data);
                config.set("Configuration.Power.Special.Identifier_Amaterasu", (Object)TextUtil.colorful(powerSpecialIdentifierAmaterasu));
            }
            else if (data.equalsIgnoreCase("Special_Nero_Beam")) {
                final String path = "Configuration.Power.Special.Identifier_Nero_Beam";
                final String powerSpecialIdentifierNeroBeam = source.getString(data);
                config.set("Configuration.Power.Special.Identifier_Nero_Beam", (Object)TextUtil.colorful(powerSpecialIdentifierNeroBeam));
            }
            else if (data.equalsIgnoreCase("Element_Format")) {
                final String path = "Configuration.Element.Format";
                final String elementFormat = source.getString(data);
                config.set("Configuration.Element.Format", (Object)TextUtil.colorful(elementFormat));
            }
            else if (data.equalsIgnoreCase("Format_Socket")) {
                final String path = "Configuration.Socket.Format_Slot";
                final String socketFormatSlot = source.getString(data);
                config.set("Configuration.Socket.Format_Slot", (Object)TextUtil.colorful(socketFormatSlot));
            }
            else if (data.equalsIgnoreCase("Socket_Empty")) {
                final String path = "Configuration.Socket.Format_Slot_Empty";
                final String socketFormatSlotEmpty = source.getString(data);
                config.set("Configuration.Socket.Format_Slot_Empty", (Object)TextUtil.colorful(socketFormatSlotEmpty));
            }
            else if (data.equalsIgnoreCase("Format_Requirement_Level")) {
                final String path = "Configuration.Requirement.Format_Level";
                final String requirementFormatLevel = source.getString(data);
                config.set("Configuration.Requirement.Format_Level", (Object)TextUtil.colorful(requirementFormatLevel));
            }
            else if (data.equalsIgnoreCase("Format_Requirement_Permission")) {
                final String path = "Configuration.Requirement.Format_Permission";
                final String requirementFormatPermission = source.getString(data);
                config.set("Configuration.Requirement.Format_Permission", (Object)TextUtil.colorful(requirementFormatPermission));
            }
            else if (data.equalsIgnoreCase("Format_Requirement_Class")) {
                final String path = "Configuration.Requirement.Format_Class";
                final String requirementFormatClass = source.getString(data);
                config.set("Configuration.Requirement.Format_Class", (Object)TextUtil.colorful(requirementFormatClass));
            }
            else if (data.equalsIgnoreCase("Format_Requirement_Soulbound_Unbound")) {
                final String path = "Configuration.Requirement.Format_Soul_Unbound";
                final String requirementFormatSoulUnbound = source.getString(data);
                config.set("Configuration.Requirement.Format_Soul_Unbound", (Object)TextUtil.colorful(requirementFormatSoulUnbound));
            }
            else if (data.equalsIgnoreCase("Format_Requirement_Soulbound_Bound")) {
                final String path = "Configuration.Requirement.Format_Soul_Bound";
                final String requirementFormatSoulBound = source.getString(data);
                config.set("Configuration.Requirement.Format_Soul_Bound", (Object)TextUtil.colorful(requirementFormatSoulBound));
            }
            else {
                if (!data.equalsIgnoreCase("Enable_Particle_Potion")) {
                    continue;
                }
                final String path = "Configuration.Misc.Enable_Particle_Potion";
                final boolean miscEnableParticlePotion = source.getBoolean(data);
                config.set("Configuration.Misc.Enable_Particle_Potion", (Object)miscEnableParticlePotion);
            }
        }
    }
    
    private final void loadConfigColor(final FileConfiguration config) {
        final String pathStatsColor = "Configuration.Stats.Color";
        final String pathStatsColorValue = "Configuration.Stats.Color_Value";
        final String pathStatsColorExpCurrent = "Configuration.Stats.Color_Exp_Current";
        final String pathStatsColorExpUp = "Configuration.Stats.Color_Exp_Up";
        final String pathPassiveBuffColor = "Configuration.Passive.Buff.Color";
        final String pathPassiveDebuffColor = "Configuration.Passive.Debuff.Color";
        final String pathPowerColorClick = "Configuration.Power.Color_Click";
        final String pathPowerColorType = "Configuration.Power.Color_Type";
        final String pathPowerColorCooldown = "Configuration.Power.Color_Cooldown";
        final String pathAbilityColor = "Configuration.Ability.Color";
        final String pathAbilityColorPercent = "Configuration.Ability.Color_Percent";
        final String pathElementColor = "Configuration.Element.Color";
        final String pathElementColorValue = "Configuration.Element.Color_Value";
        final String pathSocketColorSlot = "Configuration.Socket.Color_Slot";
        final String pathRequirementSoulBound = "Configuration.Requirement.Color_Soul_Bound";
        final String pathRequirementLevel = "Configuration.Requirement.Color_Level";
        final String pathRequirementPermission = "Configuration.Requirement.Color_Permission";
        final String pathRequirementClass = "Configuration.Requirement.Color_Class";
        final String keyStatsColor = DataUtil.keyGen(this.getStatsFormatValue(), "<stats>");
        final String keyStatsColorValue = DataUtil.keyGen(this.getStatsFormatValue(), "<value>");
        final String keyStatsColorExpCurrent = DataUtil.keyGen(this.getStatsFormatExp(), "<exp>");
        final String keyStatsColorExpUp = DataUtil.keyGen(this.getStatsFormatExp(), "<up>");
        final String keyPassiveBuffColor = DataUtil.keyGen(this.getPassiveBuffFormat(), "<buff>");
        final String keyPassiveDebuffColor = DataUtil.keyGen(this.getPassiveDebuffFormat(), "<debuff>");
        final String keyPowerColorClick = DataUtil.keyGen(this.getPowerFormat(), "<click>");
        final String keyPowerColorType = DataUtil.keyGen(this.getPowerFormat(), "<type>");
        final String keyPowerColorCooldown = DataUtil.keyGen(this.getPowerFormat(), "<cooldown>");
        final String keyAbilityColor = DataUtil.keyGen(this.getAbilityFormat(), "<ability>");
        final String keyAbilityColorPercent = DataUtil.keyGen(this.getAbilityFormat(), "<chance>");
        final String keyElementColor = DataUtil.keyGen(this.getElementFormat(), "<element>");
        final String keyElementColorValue = DataUtil.keyGen(this.getElementFormat(), "<value>");
        final String keySocketColorSlot = DataUtil.keyGen(this.getSocketFormatSlot(), "<slot>");
        final String keyRequirementSoulBound = DataUtil.keyGen(this.getRequirementFormatSoulBound(), "<player>");
        final String keyRequirementLevel = DataUtil.keyGen(this.getRequirementFormatLevel(), "<level>");
        final String keyRequirementPermission = DataUtil.keyGen(this.getRequirementFormatPermission(), "<permission>");
        final String keyRequirementClass = DataUtil.keyGen(this.getRequirementFormatClass(), "<class>");
        config.set("Configuration.Stats.Color", (Object)keyStatsColor);
        config.set("Configuration.Stats.Color_Value", (Object)keyStatsColorValue);
        config.set("Configuration.Stats.Color_Exp_Current", (Object)keyStatsColorExpCurrent);
        config.set("Configuration.Stats.Color_Exp_Up", (Object)keyStatsColorExpUp);
        config.set("Configuration.Passive.Buff.Color", (Object)keyPassiveBuffColor);
        config.set("Configuration.Passive.Debuff.Color", (Object)keyPassiveDebuffColor);
        config.set("Configuration.Power.Color_Click", (Object)keyPowerColorClick);
        config.set("Configuration.Power.Color_Type", (Object)keyPowerColorType);
        config.set("Configuration.Power.Color_Cooldown", (Object)keyPowerColorCooldown);
        config.set("Configuration.Ability.Color", (Object)keyAbilityColor);
        config.set("Configuration.Ability.Color_Percent", (Object)keyAbilityColorPercent);
        config.set("Configuration.Element.Color", (Object)keyElementColor);
        config.set("Configuration.Element.Color_Value", (Object)keyElementColorValue);
        config.set("Configuration.Socket.Color_Slot", (Object)keySocketColorSlot);
        config.set("Configuration.Requirement.Color_Soul_Bound", (Object)keyRequirementSoulBound);
        config.set("Configuration.Requirement.Color_Level", (Object)keyRequirementLevel);
        config.set("Configuration.Requirement.Color_Permission", (Object)keyRequirementPermission);
        config.set("Configuration.Requirement.Color_Class", (Object)keyRequirementClass);
    }
    
    private final ItemStack loadItemStack(final ConfigurationSection dataSection) {
        final List<String> itemLores = new ArrayList<String>();
        String itemName = null;
        Material itemMaterial = Material.STONE;
        short itemData = 0;
        boolean itemShiny = false;
        for (final String data : dataSection.getKeys(false)) {
            if (data.equalsIgnoreCase("Display_Name")) {
                itemName = dataSection.getString(data);
            }
            else if (data.equalsIgnoreCase("Data")) {
                itemData = (short)dataSection.getInt(data);
            }
            else if (data.equalsIgnoreCase("Material")) {
                final String dataMaterialText = dataSection.getString(data);
                final Material dataMaterial = Material.getMaterial(dataMaterialText);
                itemMaterial = ((dataMaterial != null) ? dataMaterial : itemMaterial);
            }
            else if (data.equalsIgnoreCase("Lores")) {
                final List<String> dataLores = (List<String>)dataSection.getStringList(data);
                itemLores.addAll(dataLores);
            }
            else {
                if (!data.equalsIgnoreCase("Shiny")) {
                    continue;
                }
                itemShiny = dataSection.getBoolean(data);
            }
        }
        if (itemMaterial != null) {
            final MaterialEnum materialEnum = MaterialEnum.getMaterialEnum(itemMaterial, (int)itemData);
            if (materialEnum != null) {
                final ItemStack item = EquipmentUtil.createItem(materialEnum, itemName, 1, (List)itemLores);
                if (itemShiny) {
                    EquipmentUtil.shiny(item);
                }
                EquipmentUtil.colorful(item);
                return item;
            }
        }
        return null;
    }
    
    private final void moveOldFile() {
        final PluginManager pluginManager = this.plugin.getPluginManager();
        final DataManager dataManager = pluginManager.getDataManager();
        final String pathSource = "config.yml";
        final String pathTarget = dataManager.getPath("Path_File_Config");
        final File fileSourcce = FileUtil.getFile((JavaPlugin)this.plugin, "config.yml");
        final File fileTarget = FileUtil.getFile((JavaPlugin)this.plugin, pathTarget);
        if (fileSourcce.exists()) {
            FileUtil.moveFileSilent(fileSourcce, fileTarget);
        }
    }
    
    private static class MainConfigHelper
    {
        private static MainConfig instance;
        
        static {
            final MyItems plugin = (MyItems)JavaPlugin.getPlugin((Class)MyItems.class);
            (MainConfigHelper.instance = new MainConfig(plugin)).setup();
        }
    }
}
