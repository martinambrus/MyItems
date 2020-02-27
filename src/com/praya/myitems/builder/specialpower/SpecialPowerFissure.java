// 
// Decompiled by Procyon v0.5.36
// 

package com.praya.myitems.builder.specialpower;

import org.bukkit.entity.Player;
import com.praya.myitems.manager.game.LoreStatsManager;
import com.praya.myitems.manager.game.GameManager;
import org.bukkit.plugin.Plugin;
import java.util.Iterator;
import com.praya.agarthalib.utility.CombatUtil;
import core.praya.agarthalib.enums.branch.ParticleEnum;
import org.bukkit.util.Vector;
import java.util.Set;
import org.bukkit.scheduler.BukkitRunnable;
import java.util.Collection;
import core.praya.agarthalib.enums.branch.SoundEnum;
import core.praya.agarthalib.bridge.unity.Bridge;
import com.praya.agarthalib.utility.PlayerUtil;
import java.util.HashSet;
import org.bukkit.Location;
import com.praya.myitems.config.plugin.MainConfig;
import org.bukkit.plugin.java.JavaPlugin;
import com.praya.myitems.MyItems;
import org.bukkit.entity.LivingEntity;
import api.praya.myitems.builder.power.PowerSpecialEnum;
import com.praya.myitems.builder.abs.SpecialPower;

public class SpecialPowerFissure extends SpecialPower
{
    private static final PowerSpecialEnum special;
    
    static {
        special = PowerSpecialEnum.FISSURE;
    }
    
    public SpecialPowerFissure() {
        super(SpecialPowerFissure.special);
    }
    
    public final int getDuration() {
        return SpecialPowerFissure.special.getDuration();
    }
    
    @Override
    public final void cast(final LivingEntity caster) {
        final MyItems plugin = (MyItems)JavaPlugin.getProvidingPlugin((Class)MyItems.class);
        final GameManager gameManager = plugin.getGameManager();
        final LoreStatsManager statsManager = gameManager.getStatsManager();
        final MainConfig mainConfig = MainConfig.getInstance();
        final Location loc = caster.getLocation();
        final Location horizontalLoc = new Location(loc.getWorld(), 0.0, 0.0, 0.0, loc.getYaw(), 0.0f);
        final Vector aim = horizontalLoc.getDirection().normalize();
        final int duration = this.getDuration();
        final double weaponDamage = statsManager.getLoreStatsWeapon(caster).getDamage();
        final double skillDamage = SpecialPowerFissure.special.getBaseAdditionalDamage() + SpecialPowerFissure.special.getBasePercentDamage() * weaponDamage / 100.0;
        final Set<LivingEntity> listEntity = new HashSet<LivingEntity>();
        final Collection<Player> players = (Collection<Player>)PlayerUtil.getNearbyPlayers(loc, mainConfig.getEffectRange());
        loc.add(aim);
        Bridge.getBridgeSound().playSound((Collection)players, loc, SoundEnum.ITEM_FIRECHARGE_USE, 1.0f, 1.0f);
        new BukkitRunnable() {
            final int limit = 12;
            final double range = 2.0;
            int t = 0;
            
            public void run() {
                if (this.t >= 12) {
                    this.cancel();
                    return;
                }
                Bridge.getBridgeParticle().playParticle(players, ParticleEnum.FLAME, loc, 25, 0.15, 0.25, 0.15, 0.019999999552965164);
                Bridge.getBridgeParticle().playParticle(players, ParticleEnum.LAVA, loc, 10, 0.2, 0.15, 0.2, 0.05000000074505806);
                Bridge.getBridgeSound().playSound(players, loc, SoundEnum.BLOCK_FIRE_AMBIENT, 0.8f, 1.0f);
                for (final LivingEntity unit : CombatUtil.getNearbyUnits(loc, 2.0)) {
                    if (!unit.equals(caster) && !listEntity.contains(unit)) {
                        listEntity.add(unit);
                        unit.setFireTicks(duration);
                        CombatUtil.skillDamage(caster, unit, skillDamage);
                    }
                }
                loc.add(aim);
                ++this.t;
            }
        }.runTaskTimer((Plugin)plugin, 0L, 1L);
    }
}
