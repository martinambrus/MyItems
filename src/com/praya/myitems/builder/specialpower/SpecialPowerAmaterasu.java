// 
// Decompiled by Procyon v0.5.36
// 

package com.praya.myitems.builder.specialpower;

import org.bukkit.entity.Player;
import com.praya.myitems.manager.game.LoreStatsManager;
import com.praya.myitems.manager.game.GameManager;
import org.bukkit.plugin.Plugin;
import java.util.Iterator;
import com.praya.agarthalib.utility.MathUtil;
import com.praya.agarthalib.utility.CombatUtil;
import core.praya.agarthalib.enums.branch.ParticleEnum;
import java.util.Set;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;
import java.util.Collection;
import core.praya.agarthalib.enums.branch.SoundEnum;
import core.praya.agarthalib.bridge.unity.Bridge;
import com.praya.agarthalib.utility.PlayerUtil;
import java.util.HashSet;
import org.bukkit.entity.Entity;
import com.praya.agarthalib.utility.LocationUtil;
import com.praya.myitems.config.plugin.MainConfig;
import org.bukkit.plugin.java.JavaPlugin;
import com.praya.myitems.MyItems;
import org.bukkit.entity.LivingEntity;
import api.praya.myitems.builder.power.PowerSpecialEnum;
import com.praya.myitems.builder.abs.SpecialPower;

public class SpecialPowerAmaterasu extends SpecialPower
{
    private static final PowerSpecialEnum special;
    
    static {
        special = PowerSpecialEnum.AMATERASU;
    }
    
    public SpecialPowerAmaterasu() {
        super(SpecialPowerAmaterasu.special);
    }
    
    public final int getDuration() {
        return SpecialPowerAmaterasu.special.getDuration();
    }
    
    public final int getLimit() {
        return this.getDuration() / 2;
    }
    
    public final double getRange() {
        return 3.0;
    }
    
    @Override
    public final void cast(final LivingEntity caster) {
        final MyItems plugin = (MyItems)JavaPlugin.getProvidingPlugin((Class)MyItems.class);
        final GameManager gameManager = plugin.getGameManager();
        final LoreStatsManager statsManager = gameManager.getStatsManager();
        final MainConfig mainConfig = MainConfig.getInstance();
        final Location loc = LocationUtil.getLineLocation((Entity)caster, caster.getEyeLocation(), 0.5, 2.0, 20, 20.0, false);
        final int limit = this.getLimit();
        final double range = this.getRange();
        final double weaponDamage = statsManager.getLoreStatsWeapon(caster).getDamage();
        final double skillDamage = SpecialPowerAmaterasu.special.getBaseAdditionalDamage() + SpecialPowerAmaterasu.special.getBasePercentDamage() * weaponDamage / 100.0;
        final Set<LivingEntity> listEntity = new HashSet<LivingEntity>();
        final Collection<Player> players = (Collection<Player>)PlayerUtil.getNearbyPlayers(loc, mainConfig.getEffectRange());
        Bridge.getBridgeSound().playSound((Collection)players, loc, SoundEnum.ITEM_FIRECHARGE_USE, 5.0f, 1.0f);
        new BukkitRunnable() {
            int t = 0;
            
            public void run() {
                if (this.t >= limit) {
                    this.cancel();
                    return;
                }
                Bridge.getBridgeParticle().playParticle(players, ParticleEnum.SPELL_MOB, loc, 25, 1.5, 0.75, 1.5, 0.0);
                Bridge.getBridgeSound().playSound(players, loc, SoundEnum.BLOCK_FIRE_AMBIENT, 5.0f, 1.0f);
                for (final LivingEntity unit : CombatUtil.getNearbyUnits(loc, range)) {
                    if (!unit.equals(caster) && !listEntity.contains(unit)) {
                        listEntity.add(unit);
                    }
                }
                for (final LivingEntity victim : listEntity) {
                    if (!victim.isDead()) {
                        final Location victimLoc = victim.getLocation().add(0.0, 0.5, 0.0);
                        Bridge.getBridgeParticle().playParticle(players, ParticleEnum.SPELL_MOB, victimLoc, 12, 0.25, 0.5, 0.25, 0.0);
                        Bridge.getBridgeSound().playSound(players, victimLoc, SoundEnum.BLOCK_FIRE_AMBIENT, 0.75f, 1.0f);
                        if (!MathUtil.isDividedBy((double)this.t, 10.0)) {
                            continue;
                        }
                        CombatUtil.areaDamage(caster, victim, skillDamage);
                    }
                }
                ++this.t;
            }
        }.runTaskTimer((Plugin)plugin, 0L, 2L);
    }
}
