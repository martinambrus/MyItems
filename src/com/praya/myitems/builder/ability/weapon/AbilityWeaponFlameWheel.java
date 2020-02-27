// 
// Decompiled by Procyon v0.5.36
// 

package com.praya.myitems.builder.ability.weapon;

import org.bukkit.entity.Player;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;
import core.praya.agarthalib.enums.branch.SoundEnum;
import java.util.Collection;
import core.praya.agarthalib.enums.branch.ParticleEnum;
import core.praya.agarthalib.bridge.unity.Bridge;
import com.praya.agarthalib.utility.PlayerUtil;
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

public class AbilityWeaponFlameWheel extends AbilityWeapon implements AbilityWeaponAttributeBaseDamage, AbilityWeaponAttributeEffect
{
    private static final String ABILITY_ID = "Flame_Wheel";
    
    private AbilityWeaponFlameWheel(final MyItems plugin, final String id) {
        super((Plugin)plugin, id);
    }
    
    public static final AbilityWeaponFlameWheel getInstance() {
        return AbilityFlameWheelHelper.instance;
    }
    
    @Override
    public String getKeyLore() {
        final MainConfig mainConfig = MainConfig.getInstance();
        return mainConfig.getAbilityWeaponIdentifierFlameWheel();
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
        final AbilityWeaponProperties abilityWeaponProperties = abilityWeaponManager.getAbilityWeaponProperties("Flame_Wheel");
        return abilityWeaponProperties.getMaxGrade();
    }
    
    @Override
    public double getBaseBonusDamage(final int grade) {
        final MyItems plugin = (MyItems)JavaPlugin.getPlugin((Class)MyItems.class);
        final GameManager gameManager = plugin.getGameManager();
        final AbilityWeaponManager abilityWeaponManager = gameManager.getAbilityWeaponManager();
        final AbilityWeaponProperties abilityWeaponProperties = abilityWeaponManager.getAbilityWeaponProperties("Flame_Wheel");
        final double baseBonusDamage = grade * abilityWeaponProperties.getScaleBaseBonusDamage();
        return baseBonusDamage;
    }
    
    @Override
    public double getBasePercentDamage(final int grade) {
        final MyItems plugin = (MyItems)JavaPlugin.getPlugin((Class)MyItems.class);
        final GameManager gameManager = plugin.getGameManager();
        final AbilityWeaponManager abilityWeaponManager = gameManager.getAbilityWeaponManager();
        final AbilityWeaponProperties abilityWeaponProperties = abilityWeaponManager.getAbilityWeaponProperties("Flame_Wheel");
        final double basePercentDamage = grade * abilityWeaponProperties.getScaleBasePercentDamage();
        return basePercentDamage;
    }
    
    @Override
    public int getEffectDuration(final int grade) {
        final MyItems plugin = (MyItems)JavaPlugin.getPlugin((Class)MyItems.class);
        final GameManager gameManager = plugin.getGameManager();
        final AbilityWeaponManager abilityWeaponManager = gameManager.getAbilityWeaponManager();
        final AbilityWeaponProperties abilityWeaponProperties = abilityWeaponManager.getAbilityWeaponProperties("Flame_Wheel");
        return abilityWeaponProperties.getTotalDuration(grade);
    }
    
    @Override
    public void cast(final Entity caster, final Entity target, final int grade, final double damage) {
        final MyItems plugin = (MyItems)JavaPlugin.getPlugin((Class)MyItems.class);
        final MainConfig mainConfig = MainConfig.getInstance();
        if (target instanceof LivingEntity) {
            final LivingEntity victims = (LivingEntity)target;
            final Location location = victims.getLocation();
            final int duration = this.getEffectDuration(grade);
            final int limit = 12;
            final Collection<Player> players = (Collection<Player>)PlayerUtil.getNearbyPlayers(location, mainConfig.getEffectRange());
            victims.setFireTicks(duration);
            Bridge.getBridgeParticle().playParticle((Collection)players, ParticleEnum.LAVA, location, 5, 0.2, 0.05, 0.2, 0.10000000149011612);
            Bridge.getBridgeSound().playSound((Collection)players, location, SoundEnum.ITEM_FIRECHARGE_USE, 1.0f, 1.0f);
            new BukkitRunnable() {
                double horizontal = 0.0;
                double vertical = 0.25;
                int time = 0;
                double x;
                double y;
                double z;
                
                public void run() {
                    if (this.time >= 12) {
                        this.cancel();
                        return;
                    }
                    for (int i = 0; i < 3; ++i) {
                        this.x = 0.8 * Math.sin(this.horizontal);
                        this.y = this.vertical;
                        this.z = 0.8 * Math.cos(this.horizontal);
                        location.add(this.x, this.y, this.z);
                        Bridge.getBridgeParticle().playParticle(players, ParticleEnum.FLAME, location, 1, 0.0, 0.0, 0.0, 0.0);
                        location.subtract(this.x, this.y, this.z);
                        location.add(-this.x, this.y, -this.z);
                        Bridge.getBridgeParticle().playParticle(players, ParticleEnum.FLAME, location, 1, 0.0, 0.0, 0.0, 0.0);
                        location.subtract(-this.x, this.y, -this.z);
                        this.horizontal += 0.1308996938995747;
                        this.vertical += 0.05;
                    }
                    ++this.time;
                }
            }.runTaskTimer((Plugin)plugin, 0L, 1L);
        }
    }
    
    private static class AbilityFlameWheelHelper
    {
        private static final AbilityWeaponFlameWheel instance;
        
        static {
            final MyItems plugin = (MyItems)JavaPlugin.getPlugin((Class)MyItems.class);
            instance = new AbilityWeaponFlameWheel(plugin, "Flame_Wheel");
        }
    }
}
