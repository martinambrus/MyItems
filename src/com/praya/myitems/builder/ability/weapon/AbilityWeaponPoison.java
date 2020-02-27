// 
// Decompiled by Procyon v0.5.36
// 

package com.praya.myitems.builder.ability.weapon;

import org.bukkit.entity.Player;
import org.bukkit.Location;
import com.praya.agarthalib.utility.CombatUtil;
import core.praya.agarthalib.enums.branch.SoundEnum;
import java.util.Collection;
import core.praya.agarthalib.enums.branch.ParticleEnum;
import core.praya.agarthalib.bridge.unity.Bridge;
import com.praya.agarthalib.utility.PlayerUtil;
import org.bukkit.potion.PotionEffectType;
import com.praya.agarthalib.utility.PotionUtil;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Entity;
import api.praya.myitems.builder.ability.AbilityWeaponProperties;
import com.praya.myitems.manager.game.AbilityWeaponManager;
import com.praya.myitems.manager.game.GameManager;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.List;
import com.praya.myitems.config.plugin.MainConfig;
import org.bukkit.plugin.Plugin;
import com.praya.myitems.MyItems;
import api.praya.myitems.builder.ability.AbilityWeaponAttributeEffect;
import api.praya.myitems.builder.ability.AbilityWeaponAttributeBaseDamage;
import api.praya.myitems.builder.ability.AbilityWeapon;

public class AbilityWeaponPoison extends AbilityWeapon implements AbilityWeaponAttributeBaseDamage, AbilityWeaponAttributeEffect
{
    private static final String ABILITY_ID = "Poison";
    
    private AbilityWeaponPoison(final MyItems plugin, final String id) {
        super((Plugin)plugin, id);
    }
    
    public static final AbilityWeaponPoison getInstance() {
        return AbilityPoisonHelper.instance;
    }
    
    @Override
    public String getKeyLore() {
        final MainConfig mainConfig = MainConfig.getInstance();
        return mainConfig.getAbilityWeaponIdentifierPoison();
    }
    
    @Override
    public List<String> getDescription() {
        return null;
    }
    
    @Override
    public int getMaxGrade() {
        final MyItems plugin = (MyItems)JavaPlugin.getPlugin((Class)MyItems.class);
        final GameManager gameManager = plugin.getGameManager();
        final AbilityWeaponManager abilityWeaponManager = gameManager.getAbilityWeaponManager();
        final AbilityWeaponProperties abilityWeaponProperties = abilityWeaponManager.getAbilityWeaponProperties("Poison");
        return abilityWeaponProperties.getMaxGrade();
    }
    
    @Override
    public double getBaseBonusDamage(final int grade) {
        final MyItems plugin = (MyItems)JavaPlugin.getPlugin((Class)MyItems.class);
        final GameManager gameManager = plugin.getGameManager();
        final AbilityWeaponManager abilityWeaponManager = gameManager.getAbilityWeaponManager();
        final AbilityWeaponProperties abilityWeaponProperties = abilityWeaponManager.getAbilityWeaponProperties("Poison");
        final double baseBonusDamage = grade * abilityWeaponProperties.getScaleBaseBonusDamage();
        return baseBonusDamage;
    }
    
    @Override
    public double getBasePercentDamage(final int grade) {
        final MyItems plugin = (MyItems)JavaPlugin.getPlugin((Class)MyItems.class);
        final GameManager gameManager = plugin.getGameManager();
        final AbilityWeaponManager abilityWeaponManager = gameManager.getAbilityWeaponManager();
        final AbilityWeaponProperties abilityWeaponProperties = abilityWeaponManager.getAbilityWeaponProperties("Poison");
        final double basePercentDamage = grade * abilityWeaponProperties.getScaleBasePercentDamage();
        return basePercentDamage;
    }
    
    @Override
    public int getEffectDuration(final int grade) {
        final MyItems plugin = (MyItems)JavaPlugin.getPlugin((Class)MyItems.class);
        final GameManager gameManager = plugin.getGameManager();
        final AbilityWeaponManager abilityWeaponManager = gameManager.getAbilityWeaponManager();
        final AbilityWeaponProperties abilityWeaponProperties = abilityWeaponManager.getAbilityWeaponProperties("Poison");
        final int baseDuration = abilityWeaponProperties.getBaseDurationEffect();
        final int scaleDuration = abilityWeaponProperties.getScaleDurationEffect();
        int duration = abilityWeaponProperties.getTotalDuration(grade);
        for (int amplifier = 0; duration > baseDuration + scaleDuration * (5 + amplifier); duration -= scaleDuration * 2, ++amplifier) {}
        return duration;
    }
    
    private final int getEffectAmplifier(final int grade) {
        final MyItems plugin = (MyItems)JavaPlugin.getPlugin((Class)MyItems.class);
        final GameManager gameManager = plugin.getGameManager();
        final AbilityWeaponManager abilityWeaponManager = gameManager.getAbilityWeaponManager();
        final AbilityWeaponProperties abilityWeaponProperties = abilityWeaponManager.getAbilityWeaponProperties("Poison");
        int baseDuration;
        int scaleDuration;
        int duration;
        int amplifier;
        for (baseDuration = abilityWeaponProperties.getBaseDurationEffect(), scaleDuration = abilityWeaponProperties.getScaleDurationEffect(), duration = abilityWeaponProperties.getTotalDuration(grade), amplifier = 0; duration > baseDuration + scaleDuration * (5 + amplifier); duration -= scaleDuration * 2, ++amplifier) {}
        return amplifier;
    }
    
    @Override
    public void cast(final Entity caster, final Entity target, final int grade, final double damage) {
        final MainConfig mainConfig = MainConfig.getInstance();
        if (target instanceof LivingEntity) {
            final LivingEntity victims = (LivingEntity)target;
            final Location location = victims.getEyeLocation();
            final PotionEffectType potion = PotionUtil.getPoisonType(victims);
            final int duration = this.getEffectDuration(grade);
            final int amplifier = potion.equals((Object)PotionEffectType.WITHER) ? (this.getEffectAmplifier(grade) + 1) : this.getEffectAmplifier(grade);
            final Collection<Player> players = (Collection<Player>)PlayerUtil.getNearbyPlayers(location, mainConfig.getEffectRange());
            Bridge.getBridgeParticle().playParticle((Collection)players, ParticleEnum.SLIME, location, 20, 0.3, 0.3, 0.3, 0.30000001192092896);
            Bridge.getBridgeSound().playSound((Collection)players, location, SoundEnum.ENCHANT_THORNS_HIT, 1.0f, 1.0f);
            CombatUtil.applyPotion(victims, potion, duration, amplifier);
        }
    }
    
    private static class AbilityPoisonHelper
    {
        private static final AbilityWeaponPoison instance;
        
        static {
            final MyItems plugin = (MyItems)JavaPlugin.getPlugin((Class)MyItems.class);
            instance = new AbilityWeaponPoison(plugin, "Poison");
        }
    }
}
