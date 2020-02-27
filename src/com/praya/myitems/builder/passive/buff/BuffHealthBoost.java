// 
// Decompiled by Procyon v0.5.36
// 

package com.praya.myitems.builder.passive.buff;

import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.plugin.java.JavaPlugin;
import com.praya.myitems.MyItems;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import com.praya.agarthalib.utility.PotionUtil;
import com.praya.myitems.config.plugin.MainConfig;
import org.bukkit.entity.Player;
import api.praya.myitems.builder.passive.PassiveEffectEnum;
import com.praya.myitems.builder.abs.PassiveEffect;

public class BuffHealthBoost extends PassiveEffect
{
    private static final PassiveEffectEnum buff;
    
    static {
        buff = PassiveEffectEnum.HEALTH_BOOST;
    }
    
    public BuffHealthBoost() {
        super(BuffHealthBoost.buff, 1);
    }
    
    public BuffHealthBoost(final int grade) {
        super(BuffHealthBoost.buff, grade);
    }
    
    @Override
    public final void cast(final Player player) {
        final MainConfig mainConfig = MainConfig.getInstance();
        final PotionEffectType potionType = this.getPotion();
        final boolean isEnableParticle = mainConfig.isMiscEnableParticlePotion();
        final PotionEffect potion = PotionUtil.createPotion(potionType, 96000, this.grade, true, isEnableParticle);
        player.addPotionEffect(potion);
    }
    
    public final void reset(final Player player) {
        final MyItems plugin = (MyItems)JavaPlugin.getPlugin((Class)MyItems.class);
        final PotionEffectType potion = this.getPotion();
        final double health = player.getHealth();
        player.removePotionEffect(potion);
        new BukkitRunnable() {
            public void run() {
                if (player.isOnline()) {
                    final double maxHealth = player.getMaxHealth();
                    player.setHealth((health > maxHealth) ? maxHealth : health);
                }
            }
        }.runTaskLater((Plugin)plugin, 1L);
    }
}
