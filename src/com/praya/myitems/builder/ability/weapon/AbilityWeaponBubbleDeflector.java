// 
// Decompiled by Procyon v0.5.36
// 

package com.praya.myitems.builder.ability.weapon;

import org.bukkit.entity.Player;
import org.bukkit.projectiles.ProjectileSource;
import java.util.Iterator;
import com.praya.agarthalib.utility.CombatUtil;
import org.bukkit.util.Vector;
import org.bukkit.potion.PotionEffect;
import java.util.Set;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;
import core.praya.agarthalib.enums.branch.SoundEnum;
import java.util.Collection;
import core.praya.agarthalib.enums.branch.ParticleEnum;
import core.praya.agarthalib.bridge.unity.Bridge;
import com.praya.agarthalib.utility.PlayerUtil;
import java.util.HashSet;
import com.praya.agarthalib.utility.PotionUtil;
import org.bukkit.potion.PotionEffectType;
import com.praya.agarthalib.utility.ProjectileUtil;
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

public class AbilityWeaponBubbleDeflector extends AbilityWeapon implements AbilityWeaponAttributeBaseDamage, AbilityWeaponAttributeCastDamage, AbilityWeaponAttributeEffect
{
    private static final String ABILITY_ID = "Bubble_Deflector";
    
    private AbilityWeaponBubbleDeflector(final MyItems plugin, final String id) {
        super((Plugin)plugin, id);
    }
    
    public static final AbilityWeaponBubbleDeflector getInstance() {
        return AbilityBubbleDeflectorHelper.instance;
    }
    
    @Override
    public String getKeyLore() {
        final MainConfig mainConfig = MainConfig.getInstance();
        return mainConfig.getAbilityWeaponIdentifierBubbleDeflector();
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
        final AbilityWeaponProperties abilityWeaponProperties = abilityWeaponManager.getAbilityWeaponProperties("Bubble_Deflector");
        return abilityWeaponProperties.getMaxGrade();
    }
    
    @Override
    public double getBaseBonusDamage(final int grade) {
        final MyItems plugin = (MyItems)JavaPlugin.getPlugin((Class)MyItems.class);
        final GameManager gameManager = plugin.getGameManager();
        final AbilityWeaponManager abilityWeaponManager = gameManager.getAbilityWeaponManager();
        final AbilityWeaponProperties abilityWeaponProperties = abilityWeaponManager.getAbilityWeaponProperties("Bubble_Deflector");
        final double baseBonusDamage = grade * abilityWeaponProperties.getScaleBaseBonusDamage();
        return baseBonusDamage;
    }
    
    @Override
    public double getBasePercentDamage(final int grade) {
        final MyItems plugin = (MyItems)JavaPlugin.getPlugin((Class)MyItems.class);
        final GameManager gameManager = plugin.getGameManager();
        final AbilityWeaponManager abilityWeaponManager = gameManager.getAbilityWeaponManager();
        final AbilityWeaponProperties abilityWeaponProperties = abilityWeaponManager.getAbilityWeaponProperties("Bubble_Deflector");
        final double basePercentDamage = grade * abilityWeaponProperties.getScaleBasePercentDamage();
        return basePercentDamage;
    }
    
    @Override
    public double getCastBonusDamage(final int grade) {
        final MyItems plugin = (MyItems)JavaPlugin.getPlugin((Class)MyItems.class);
        final GameManager gameManager = plugin.getGameManager();
        final AbilityWeaponManager abilityWeaponManager = gameManager.getAbilityWeaponManager();
        final AbilityWeaponProperties abilityWeaponProperties = abilityWeaponManager.getAbilityWeaponProperties("Bubble_Deflector");
        final double castBonusDamage = grade * abilityWeaponProperties.getScaleCastBonusDamage();
        return castBonusDamage;
    }
    
    @Override
    public double getCastPercentDamage(final int grade) {
        final MyItems plugin = (MyItems)JavaPlugin.getPlugin((Class)MyItems.class);
        final GameManager gameManager = plugin.getGameManager();
        final AbilityWeaponManager abilityWeaponManager = gameManager.getAbilityWeaponManager();
        final AbilityWeaponProperties abilityWeaponProperties = abilityWeaponManager.getAbilityWeaponProperties("Bubble_Deflector");
        final double castPercentDamage = grade * abilityWeaponProperties.getScaleCastPercentDamage();
        return castPercentDamage;
    }
    
    @Override
    public int getEffectDuration(final int grade) {
        final MyItems plugin = (MyItems)JavaPlugin.getPlugin((Class)MyItems.class);
        final GameManager gameManager = plugin.getGameManager();
        final AbilityWeaponManager abilityWeaponManager = gameManager.getAbilityWeaponManager();
        final AbilityWeaponProperties abilityWeaponProperties = abilityWeaponManager.getAbilityWeaponProperties("Bubble_Deflector");
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
            final Location location = ProjectileUtil.isProjectile(caster) ? ProjectileUtil.getDirectLocation(caster) : attacker.getEyeLocation();
            final Vector vector = location.getDirection();
            final int limit = 10 + grade;
            final int duration = this.getEffectDuration(grade);
            final double range = 1.5;
            final double bubbleDamage = this.getCastBonusDamage(grade) + damage * (this.getCastPercentDamage(grade) / 100.0);
            final PotionEffect potionEffect = PotionUtil.createPotion(PotionEffectType.SLOW, duration, 4);
            final Set<LivingEntity> units = new HashSet<LivingEntity>();
            final Collection<Player> players = (Collection<Player>)PlayerUtil.getNearbyPlayers(location, mainConfig.getEffectRange());
            victims.addPotionEffect(potionEffect);
            Bridge.getBridgeParticle().playParticle((Collection)players, ParticleEnum.WATER_SPLASH, location, 40, 0.25, 0.25, 0.25, 0.0);
            Bridge.getBridgeParticle().playParticle((Collection)players, ParticleEnum.WATER_WAKE, location, 40, 0.25, 0.25, 0.25, 0.0);
            Bridge.getBridgeSound().playSound((Collection)players, location, SoundEnum.ENTITY_GUARDIAN_FLOP, 5.0f, 1.0f);
            new BukkitRunnable() {
                int time = 0;
                
                public void run() {
                    if (this.time >= limit) {
                        this.cancel();
                        return;
                    }
                    for (final LivingEntity unit : CombatUtil.getNearbyUnits(location, 1.5)) {
                        if (!unit.equals(attacker) && !unit.equals(victims) && !units.contains(unit)) {
                            CombatUtil.areaDamage(attacker, unit, bubbleDamage);
                            unit.addPotionEffect(potionEffect);
                            units.add(unit);
                        }
                    }
                    if (this.time % 2 == 0) {
                        Bridge.getBridgeSound().playSound(players, location, SoundEnum.BLOCK_WATER_AMBIENT, 2.0f, 1.0f);
                    }
                    Bridge.getBridgeParticle().playParticle(players, ParticleEnum.WATER_DROP, location, 25, 0.2, 0.2, 0.2, 0.0);
                    Bridge.getBridgeParticle().playParticle(players, ParticleEnum.WATER_BUBBLE, location, 25, 0.2, 0.2, 0.2, 0.0);
                    location.add(vector);
                    ++this.time;
                }
            }.runTaskTimer((Plugin)plugin, 0L, 1L);
        }
    }
    
    private static class AbilityBubbleDeflectorHelper
    {
        private static final AbilityWeaponBubbleDeflector instance;
        
        static {
            final MyItems plugin = (MyItems)JavaPlugin.getPlugin((Class)MyItems.class);
            instance = new AbilityWeaponBubbleDeflector(plugin, "Bubble_Deflector");
        }
    }
}
