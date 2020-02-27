// 
// Decompiled by Procyon v0.5.36
// 

package com.praya.myitems.builder.specialpower;

import org.bukkit.entity.Player;
import com.praya.myitems.manager.game.LoreStatsManager;
import com.praya.myitems.manager.game.GameManager;
import org.bukkit.plugin.Plugin;
import java.util.Iterator;
import org.bukkit.potion.PotionEffectType;
import com.praya.agarthalib.utility.CombatUtil;
import core.praya.agarthalib.enums.branch.SoundEnum;
import core.praya.agarthalib.enums.branch.ParticleEnum;
import core.praya.agarthalib.bridge.unity.Bridge;
import java.util.Set;
import java.util.Collection;
import org.bukkit.util.Vector;
import org.bukkit.scheduler.BukkitRunnable;
import com.praya.agarthalib.utility.PlayerUtil;
import java.util.HashSet;
import org.bukkit.Location;
import com.praya.myitems.config.plugin.MainConfig;
import org.bukkit.plugin.java.JavaPlugin;
import com.praya.myitems.MyItems;
import org.bukkit.entity.LivingEntity;
import api.praya.myitems.builder.power.PowerSpecialEnum;
import com.praya.myitems.builder.abs.SpecialPower;

public class SpecialPowerNeroBeam extends SpecialPower
{
    private static final PowerSpecialEnum special;
    
    static {
        special = PowerSpecialEnum.NERO_BEAM;
    }
    
    public SpecialPowerNeroBeam() {
        super(SpecialPowerNeroBeam.special);
    }
    
    public final int getDuration() {
        return SpecialPowerNeroBeam.special.getDuration();
    }
    
    public final int getLimit() {
        return 15;
    }
    
    public final double getBaseRange() {
        return 1.5;
    }
    
    public final double getScaleRange() {
        return 0.05;
    }
    
    public final double getStartRadius() {
        return 0.2;
    }
    
    public final double getScaleRadius() {
        return 0.05;
    }
    
    @Override
    public final void cast(final LivingEntity caster) {
        final MyItems plugin = (MyItems)JavaPlugin.getProvidingPlugin((Class)MyItems.class);
        final GameManager gameManager = plugin.getGameManager();
        final LoreStatsManager statsManager = gameManager.getStatsManager();
        final MainConfig mainConfig = MainConfig.getInstance();
        final Location loc = caster.getEyeLocation();
        final Location leftLoc = new Location(loc.getWorld(), 0.0, 0.0, 0.0, loc.getYaw() - 90.0f, loc.getPitch());
        final Location upLoc = new Location(loc.getWorld(), 0.0, 0.0, 0.0, loc.getYaw(), loc.getPitch() - 90.0f);
        final Vector left = leftLoc.getDirection();
        final Vector up = upLoc.getDirection();
        final Vector vector = loc.getDirection();
        final int duration = this.getDuration();
        final double weaponDamage = statsManager.getLoreStatsWeapon(caster).getDamage();
        final double skillDamage = SpecialPowerNeroBeam.special.getBaseAdditionalDamage() + SpecialPowerNeroBeam.special.getBasePercentDamage() * weaponDamage / 100.0;
        final Set<LivingEntity> listEntity = new HashSet<LivingEntity>();
        final Collection<Player> players = (Collection<Player>)PlayerUtil.getNearbyPlayers(loc, mainConfig.getEffectRange());
        new BukkitRunnable() {
            final int limit = SpecialPowerNeroBeam.this.getLimit();
            double range = SpecialPowerNeroBeam.this.getBaseRange();
            double startRadius = SpecialPowerNeroBeam.this.getStartRadius();
            double radius = 0.2;
            int t = 0;
            double degree;
            
            public void run() {
                if (this.t >= this.limit) {
                    this.cancel();
                    return;
                }
                final Location partLoc = new Location(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
                this.degree = 3.141592653589793 / (2.0 * (this.radius / this.startRadius));
                for (double math = 0.0; math <= 6.283185307179586; math += this.degree) {
                    final double calcHorizontal = Math.sin(math) * this.radius;
                    final double calVertical = Math.cos(math) * this.radius;
                    partLoc.add(left.getX() * calcHorizontal, left.getY() * calcHorizontal, left.getZ() * calcHorizontal);
                    partLoc.add(up.getX() * calVertical, up.getY() * calVertical, up.getZ() * calVertical);
                    Bridge.getBridgeParticle().playParticle(players, ParticleEnum.SPELL_WITCH, partLoc, 1, 0.0, 0.0, 0.0, 0.0);
                    partLoc.subtract(left.getX() * calcHorizontal, left.getY() * calcHorizontal, left.getZ() * calcHorizontal);
                    partLoc.subtract(up.getX() * calVertical, up.getY() * calVertical, up.getZ() * calVertical);
                }
                Bridge.getBridgeSound().playSound(players, loc, SoundEnum.ENTITY_WITHER_SHOOT, 5.0f, 1.0f);
                for (final LivingEntity unit : CombatUtil.getNearbyUnits(loc, this.range)) {
                    if (!unit.equals(caster) && !listEntity.contains(unit)) {
                        listEntity.add(unit);
                        CombatUtil.applyPotion(unit, PotionEffectType.SLOW, duration, 10);
                        CombatUtil.skillDamage(caster, unit, skillDamage);
                    }
                }
                this.radius += SpecialPowerNeroBeam.this.getScaleRadius();
                this.range += SpecialPowerNeroBeam.this.getScaleRange();
                loc.add(vector);
                ++this.t;
            }
        }.runTaskTimer((Plugin)plugin, 0L, 1L);
    }
}
