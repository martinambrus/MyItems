// 
// Decompiled by Procyon v0.5.36
// 

package com.praya.myitems.builder.ability.weapon;

import org.bukkit.entity.Player;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.Location;
import core.praya.agarthalib.enums.branch.ParticleEnum;
import com.praya.agarthalib.utility.CombatUtil;
import org.bukkit.scheduler.BukkitRunnable;
import java.util.Collection;
import core.praya.agarthalib.enums.branch.SoundEnum;
import core.praya.agarthalib.bridge.unity.Bridge;
import com.praya.agarthalib.utility.PlayerUtil;
import com.praya.myitems.utility.main.CustomEffectUtil;
import api.praya.myitems.builder.passive.PassiveEffectTypeEnum;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Projectile;
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
import api.praya.myitems.builder.ability.AbilityWeaponAttributeCastDamage;
import api.praya.myitems.builder.ability.AbilityWeaponAttributeBaseDamage;
import api.praya.myitems.builder.ability.AbilityWeapon;

public class AbilityWeaponDarkFlame extends AbilityWeapon implements AbilityWeaponAttributeBaseDamage, AbilityWeaponAttributeCastDamage, AbilityWeaponAttributeEffect
{
    private static final String ABILITY_ID = "Dark_Flame";
    
    private AbilityWeaponDarkFlame(final MyItems plugin, final String id) {
        super((Plugin)plugin, id);
    }
    
    public static final AbilityWeaponDarkFlame getInstance() {
        return AbilityDarkFlameHelper.instance;
    }
    
    @Override
    public String getKeyLore() {
        final MainConfig mainConfig = MainConfig.getInstance();
        return mainConfig.getAbilityWeaponIdentifierDarkFlame();
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
        final AbilityWeaponProperties abilityWeaponProperties = abilityWeaponManager.getAbilityWeaponProperties("Dark_Flame");
        return abilityWeaponProperties.getMaxGrade();
    }
    
    @Override
    public double getBaseBonusDamage(final int grade) {
        final MyItems plugin = (MyItems)JavaPlugin.getPlugin((Class)MyItems.class);
        final GameManager gameManager = plugin.getGameManager();
        final AbilityWeaponManager abilityWeaponManager = gameManager.getAbilityWeaponManager();
        final AbilityWeaponProperties abilityWeaponProperties = abilityWeaponManager.getAbilityWeaponProperties("Dark_Flame");
        final double baseBonusDamage = grade * abilityWeaponProperties.getScaleBaseBonusDamage();
        return baseBonusDamage;
    }
    
    @Override
    public double getBasePercentDamage(final int grade) {
        final MyItems plugin = (MyItems)JavaPlugin.getPlugin((Class)MyItems.class);
        final GameManager gameManager = plugin.getGameManager();
        final AbilityWeaponManager abilityWeaponManager = gameManager.getAbilityWeaponManager();
        final AbilityWeaponProperties abilityWeaponProperties = abilityWeaponManager.getAbilityWeaponProperties("Dark_Flame");
        final double basePercentDamage = grade * abilityWeaponProperties.getScaleBasePercentDamage();
        return basePercentDamage;
    }
    
    @Override
    public double getCastBonusDamage(final int grade) {
        final MyItems plugin = (MyItems)JavaPlugin.getPlugin((Class)MyItems.class);
        final GameManager gameManager = plugin.getGameManager();
        final AbilityWeaponManager abilityWeaponManager = gameManager.getAbilityWeaponManager();
        final AbilityWeaponProperties abilityWeaponProperties = abilityWeaponManager.getAbilityWeaponProperties("Dark_Flame");
        final double castBonusDamage = grade * abilityWeaponProperties.getScaleCastBonusDamage();
        return castBonusDamage;
    }
    
    @Override
    public double getCastPercentDamage(final int grade) {
        final MyItems plugin = (MyItems)JavaPlugin.getPlugin((Class)MyItems.class);
        final GameManager gameManager = plugin.getGameManager();
        final AbilityWeaponManager abilityWeaponManager = gameManager.getAbilityWeaponManager();
        final AbilityWeaponProperties abilityWeaponProperties = abilityWeaponManager.getAbilityWeaponProperties("Dark_Flame");
        final double castPercentDamage = grade * abilityWeaponProperties.getScaleCastPercentDamage();
        return castPercentDamage;
    }
    
    @Override
    public int getEffectDuration(final int grade) {
        final MyItems plugin = (MyItems)JavaPlugin.getPlugin((Class)MyItems.class);
        final GameManager gameManager = plugin.getGameManager();
        final AbilityWeaponManager abilityWeaponManager = gameManager.getAbilityWeaponManager();
        final AbilityWeaponProperties abilityWeaponProperties = abilityWeaponManager.getAbilityWeaponProperties("Dark_Flame");
        return abilityWeaponProperties.getTotalDuration(grade);
    }
    
    @Override
    public void cast(final Entity caster, final Entity target, final int grade, final double damage) {
        final MyItems plugin = (MyItems)JavaPlugin.getPlugin((Class)MyItems.class);
        final MainConfig mainConfig = MainConfig.getInstance();
        LivingEntity attacker;
        if (caster instanceof Projectile) {
            final Projectile projectile = (Projectile)caster;
            final ProjectileSource projectileSource = projectile.getShooter();
            if (projectileSource == null || !(projectileSource instanceof LivingEntity)) {
                return;
            }
            attacker = (LivingEntity)projectileSource;
        }
        else {
            attacker = (LivingEntity)caster;
        }
        if (target instanceof LivingEntity) {
            final LivingEntity victims = (LivingEntity)target;
            if (!CustomEffectUtil.isRunCustomEffect((Entity)victims, PassiveEffectTypeEnum.DARK_FLAME)) {
                final Location location = victims.getLocation();
                final int effectDuration = this.getEffectDuration(grade);
                final int period = 2;
                final int limit = effectDuration / 2;
                final long milis = effectDuration * 50;
                final double flameDamage = this.getCastBonusDamage(grade) + damage * (this.getCastPercentDamage(grade) / 100.0);
                final Collection<Player> players = (Collection<Player>)PlayerUtil.getNearbyPlayers(location, mainConfig.getEffectRange());
                Bridge.getBridgeSound().playSound((Collection)players, location, SoundEnum.ITEM_FIRECHARGE_USE, 5.0f, 1.0f);
                CustomEffectUtil.setCustomEffect((Entity)victims, milis, PassiveEffectTypeEnum.DARK_FLAME);
                new BukkitRunnable() {
                    int time = 0;
                    
                    public void run() {
                        if (this.time >= limit) {
                            this.cancel();
                            return;
                        }
                        if (victims.isDead()) {
                            this.cancel();
                            return;
                        }
                        final Location location = victims.getLocation().add(0.0, 0.5, 0.0);
                        if (this.time % 5 == 0) {
                            CombatUtil.areaDamage(attacker, victims, flameDamage);
                        }
                        Bridge.getBridgeParticle().playParticle(players, ParticleEnum.SPELL_MOB, location, 20, 0.25, 0.5, 0.25, 0.0);
                        Bridge.getBridgeSound().playSound(players, location, SoundEnum.BLOCK_FIRE_AMBIENT, 5.0f, 1.0f);
                        ++this.time;
                    }
                }.runTaskTimer((Plugin)plugin, 2L, 2L);
            }
        }
    }
    
    private static class AbilityDarkFlameHelper
    {
        private static final AbilityWeaponDarkFlame instance;
        
        static {
            final MyItems plugin = (MyItems)JavaPlugin.getPlugin((Class)MyItems.class);
            instance = new AbilityWeaponDarkFlame(plugin, "Dark_Flame");
        }
    }
}
