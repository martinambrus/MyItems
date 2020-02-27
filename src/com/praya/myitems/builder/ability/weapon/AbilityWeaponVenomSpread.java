// 
// Decompiled by Procyon v0.5.36
// 

package com.praya.myitems.builder.ability.weapon;

import org.bukkit.entity.Player;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.potion.PotionEffect;
import java.util.Iterator;
import com.praya.agarthalib.utility.CombatUtil;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;
import core.praya.agarthalib.enums.branch.SoundEnum;
import java.util.Collection;
import core.praya.agarthalib.enums.branch.ParticleEnum;
import core.praya.agarthalib.bridge.unity.Bridge;
import org.bukkit.potion.PotionEffectType;
import com.praya.agarthalib.utility.PotionUtil;
import com.praya.agarthalib.utility.PlayerUtil;
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

public class AbilityWeaponVenomSpread extends AbilityWeapon implements AbilityWeaponAttributeBaseDamage, AbilityWeaponAttributeCastDamage, AbilityWeaponAttributeEffect
{
    private static final String ABILITY_ID = "Venom_Spread";
    
    private AbilityWeaponVenomSpread(final MyItems plugin, final String id) {
        super((Plugin)plugin, id);
    }
    
    public static final AbilityWeaponVenomSpread getInstance() {
        return AbilityVenomSpreadHelper.instance;
    }
    
    @Override
    public String getKeyLore() {
        final MainConfig mainConfig = MainConfig.getInstance();
        return mainConfig.getAbilityWeaponIdentifierVenomSpread();
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
        final AbilityWeaponProperties abilityWeaponProperties = abilityWeaponManager.getAbilityWeaponProperties("Venom_Spread");
        return abilityWeaponProperties.getMaxGrade();
    }
    
    @Override
    public double getBaseBonusDamage(final int grade) {
        final MyItems plugin = (MyItems)JavaPlugin.getPlugin((Class)MyItems.class);
        final GameManager gameManager = plugin.getGameManager();
        final AbilityWeaponManager abilityWeaponManager = gameManager.getAbilityWeaponManager();
        final AbilityWeaponProperties abilityWeaponProperties = abilityWeaponManager.getAbilityWeaponProperties("Venom_Spread");
        final double baseBonusDamage = grade * abilityWeaponProperties.getScaleBaseBonusDamage();
        return baseBonusDamage;
    }
    
    @Override
    public double getBasePercentDamage(final int grade) {
        final MyItems plugin = (MyItems)JavaPlugin.getPlugin((Class)MyItems.class);
        final GameManager gameManager = plugin.getGameManager();
        final AbilityWeaponManager abilityWeaponManager = gameManager.getAbilityWeaponManager();
        final AbilityWeaponProperties abilityWeaponProperties = abilityWeaponManager.getAbilityWeaponProperties("Venom_Spread");
        final double basePercentDamage = grade * abilityWeaponProperties.getScaleBasePercentDamage();
        return basePercentDamage;
    }
    
    @Override
    public double getCastBonusDamage(final int grade) {
        final MyItems plugin = (MyItems)JavaPlugin.getPlugin((Class)MyItems.class);
        final GameManager gameManager = plugin.getGameManager();
        final AbilityWeaponManager abilityWeaponManager = gameManager.getAbilityWeaponManager();
        final AbilityWeaponProperties abilityWeaponProperties = abilityWeaponManager.getAbilityWeaponProperties("Venom_Spread");
        final double castBonusDamage = grade * abilityWeaponProperties.getScaleCastBonusDamage();
        return castBonusDamage;
    }
    
    @Override
    public double getCastPercentDamage(final int grade) {
        final MyItems plugin = (MyItems)JavaPlugin.getPlugin((Class)MyItems.class);
        final GameManager gameManager = plugin.getGameManager();
        final AbilityWeaponManager abilityWeaponManager = gameManager.getAbilityWeaponManager();
        final AbilityWeaponProperties abilityWeaponProperties = abilityWeaponManager.getAbilityWeaponProperties("Venom_Spread");
        final double castPercentDamage = grade * abilityWeaponProperties.getScaleCastPercentDamage();
        return castPercentDamage;
    }
    
    @Override
    public int getEffectDuration(final int grade) {
        final MyItems plugin = (MyItems)JavaPlugin.getPlugin((Class)MyItems.class);
        final GameManager gameManager = plugin.getGameManager();
        final AbilityWeaponManager abilityWeaponManager = gameManager.getAbilityWeaponManager();
        final AbilityWeaponProperties abilityWeaponProperties = abilityWeaponManager.getAbilityWeaponProperties("Venom_Spread");
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
            final Location location = victims.getLocation();
            final int duration = this.getEffectDuration(grade);
            final int limit = 5;
            final double dividen = 2.0;
            final double spreadDamage = this.getCastBonusDamage(grade) + damage * (this.getCastPercentDamage(grade) / 100.0);
            final Collection<Player> players = (Collection<Player>)PlayerUtil.getNearbyPlayers(location, mainConfig.getEffectRange());
            final PotionEffectType potionType = PotionUtil.getPoisonType(victims);
            final int amplifier = potionType.equals((Object)PotionEffectType.WITHER) ? 3 : 2;
            final PotionEffect potion = PotionUtil.createPotion(potionType, duration, amplifier);
            victims.addPotionEffect(potion);
            Bridge.getBridgeParticle().playParticle((Collection)players, ParticleEnum.SLIME, location, 40, 0.25, 0.5, 0.25, 0.0);
            Bridge.getBridgeSound().playSound((Collection)players, location, SoundEnum.BLOCK_SLIME_HIT, 5.0f, 1.0f);
            new BukkitRunnable() {
                double range = 0.5;
                int time = 0;
                
                public void run() {
                    if (this.time >= 5) {
                        this.cancel();
                        return;
                    }
                    Bridge.getBridgeSound().playSound(players, location, SoundEnum.BLOCK_GRAVEL_BREAK, 5.0f, 1.0f);
                    for (double i = 0.0; i <= 6.283185307179586; i += 3.141592653589793 / (2.0 * (1 + this.time))) {
                        final double x = this.range * Math.sin(i);
                        final double z = this.range * Math.cos(i);
                        location.add(x, 0.0, z);
                        Bridge.getBridgeParticle().playParticle(players, ParticleEnum.VILLAGER_HAPPY, location, 1, 0.0, 0.0, 0.0, 0.0);
                        if (this.time < 3) {
                            Bridge.getBridgeParticle().playParticle(players, ParticleEnum.CLOUD, location, 1, 0.0, 0.0, 0.0, 0.0);
                        }
                        location.subtract(x, 0.0, z);
                    }
                    for (final LivingEntity unit : CombatUtil.getNearbyUnits(location, this.range)) {
                        if (!unit.equals(attacker) && !unit.equals(victims)) {
                            final PotionEffectType potionType = PotionUtil.getPoisonType(victims);
                            final int grade = potionType.equals((Object)PotionEffectType.WITHER) ? 3 : 2;
                            final PotionEffect potion = PotionUtil.createPotion(potionType, grade, duration);
                            unit.addPotionEffect(potion);
                            CombatUtil.areaDamage(attacker, unit, spreadDamage);
                        }
                    }
                    this.range += 0.5;
                    ++this.time;
                }
            }.runTaskTimer((Plugin)plugin, 0L, 1L);
        }
    }
    
    private static class AbilityVenomSpreadHelper
    {
        private static final AbilityWeaponVenomSpread instance;
        
        static {
            final MyItems plugin = (MyItems)JavaPlugin.getPlugin((Class)MyItems.class);
            instance = new AbilityWeaponVenomSpread(plugin, "Venom_Spread");
        }
    }
}
