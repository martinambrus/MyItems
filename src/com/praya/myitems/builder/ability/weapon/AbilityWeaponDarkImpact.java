// 
// Decompiled by Procyon v0.5.36
// 

package com.praya.myitems.builder.ability.weapon;

import org.bukkit.entity.Player;
import org.bukkit.projectiles.ProjectileSource;
import core.praya.agarthalib.enums.branch.ParticleEnum;
import java.util.Collection;
import core.praya.agarthalib.enums.branch.SoundEnum;
import core.praya.agarthalib.bridge.unity.Bridge;
import com.praya.agarthalib.utility.CombatUtil;
import org.bukkit.potion.PotionEffectType;
import com.praya.agarthalib.utility.PlayerUtil;
import org.bukkit.Location;
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
import api.praya.myitems.builder.ability.AbilityWeaponAttributeBaseDamage;
import api.praya.myitems.builder.ability.AbilityWeapon;

public class AbilityWeaponDarkImpact extends AbilityWeapon implements AbilityWeaponAttributeBaseDamage
{
    private static final String ABILITY_ID = "Dark_Impact";
    
    private AbilityWeaponDarkImpact(final MyItems plugin, final String id) {
        super((Plugin)plugin, id);
    }
    
    public static final AbilityWeaponDarkImpact getInstance() {
        return AbilityDarkImpactHelper.instance;
    }
    
    @Override
    public String getKeyLore() {
        final MainConfig mainConfig = MainConfig.getInstance();
        return mainConfig.getAbilityWeaponIdentifierDarkImpact();
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
        final AbilityWeaponProperties abilityWeaponProperties = abilityWeaponManager.getAbilityWeaponProperties("Dark_Impact");
        return abilityWeaponProperties.getMaxGrade();
    }
    
    @Override
    public double getBaseBonusDamage(final int grade) {
        final MyItems plugin = (MyItems)JavaPlugin.getPlugin((Class)MyItems.class);
        final GameManager gameManager = plugin.getGameManager();
        final AbilityWeaponManager abilityWeaponManager = gameManager.getAbilityWeaponManager();
        final AbilityWeaponProperties abilityWeaponProperties = abilityWeaponManager.getAbilityWeaponProperties("Dark_Impact");
        final double baseBonusDamage = grade * abilityWeaponProperties.getScaleBaseBonusDamage();
        return baseBonusDamage;
    }
    
    @Override
    public double getBasePercentDamage(final int grade) {
        final MyItems plugin = (MyItems)JavaPlugin.getPlugin((Class)MyItems.class);
        final GameManager gameManager = plugin.getGameManager();
        final AbilityWeaponManager abilityWeaponManager = gameManager.getAbilityWeaponManager();
        final AbilityWeaponProperties abilityWeaponProperties = abilityWeaponManager.getAbilityWeaponProperties("Dark_Impact");
        final double basePercentDamage = grade * abilityWeaponProperties.getScaleBasePercentDamage();
        return basePercentDamage;
    }
    
    @Override
    public void cast(final Entity caster, final Entity target, final int grade, final double damage) {
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
            final Location location = victims.getLocation().add(0.0, 0.6, 0.0);
            final Location backLook = new Location(location.getWorld(), location.getX(), location.getY(), location.getZ(), attacker.getLocation().getYaw(), 0.0f);
            final Collection<Player> players = (Collection<Player>)PlayerUtil.getNearbyPlayers(location, mainConfig.getEffectRange());
            victims.teleport(backLook);
            CombatUtil.applyPotion(victims, PotionEffectType.BLINDNESS, 20, 1);
            Bridge.getBridgeSound().playSound((Collection)players, location, SoundEnum.ITEM_FIRECHARGE_USE, 1.0f, 1.0f);
            final double radius = 1.0;
            for (int i1 = 0; i1 < 180; i1 += 90) {
                final double xBase = Math.cos(i1) * radius;
                final double zBase = Math.sin(i1) * radius;
                for (int i2 = 0; i2 < 360; i2 += 9) {
                    final double x = xBase * Math.cos(i2);
                    final double y = Math.sin(i2) * radius;
                    final double z = zBase * Math.cos(i2);
                    location.add(x, y, z);
                    Bridge.getBridgeParticle().playParticle((Collection)players, ParticleEnum.SPELL_WITCH, location, 1, 0.0, 0.0, 0.0, 0.0);
                    location.subtract(x, y, z);
                }
            }
        }
    }
    
    private static class AbilityDarkImpactHelper
    {
        private static final AbilityWeaponDarkImpact instance;
        
        static {
            final MyItems plugin = (MyItems)JavaPlugin.getPlugin((Class)MyItems.class);
            instance = new AbilityWeaponDarkImpact(plugin, "Dark_Impact");
        }
    }
}
