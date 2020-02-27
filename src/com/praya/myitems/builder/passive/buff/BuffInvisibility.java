// 
// Decompiled by Procyon v0.5.36
// 

package com.praya.myitems.builder.passive.buff;

import com.praya.agarthalib.utility.MathUtil;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import com.praya.myitems.manager.game.PassiveEffectManager;
import com.praya.myitems.manager.game.GameManager;
import com.praya.agarthalib.utility.PotionUtil;
import org.bukkit.OfflinePlayer;
import com.praya.myitems.config.plugin.MainConfig;
import org.bukkit.plugin.java.JavaPlugin;
import com.praya.myitems.MyItems;
import org.bukkit.entity.Player;
import api.praya.myitems.builder.passive.PassiveEffectEnum;
import com.praya.myitems.builder.abs.PassiveEffect;

public class BuffInvisibility extends PassiveEffect
{
    private static final PassiveEffectEnum buff;
    
    static {
        buff = PassiveEffectEnum.INVISIBILITY;
    }
    
    public BuffInvisibility() {
        super(BuffInvisibility.buff, 1);
    }
    
    public BuffInvisibility(final int grade) {
        super(BuffInvisibility.buff, grade);
    }
    
    @Override
    public final void cast(final Player player) {
        final MyItems plugin = (MyItems)JavaPlugin.getPlugin((Class)MyItems.class);
        final GameManager gameManager = plugin.getGameManager();
        final PassiveEffectManager passiveEffectManager = gameManager.getPassiveEffectManager();
        final MainConfig mainConfig = MainConfig.getInstance();
        if (!passiveEffectManager.isPassiveEffectCooldown(BuffInvisibility.buff, (OfflinePlayer)player)) {
            final PotionEffectType potionType = this.getPotion();
            final int duration = this.getDuration();
            final long cooldown = this.getCooldown();
            final boolean isEnableParticle = mainConfig.isMiscEnableParticlePotion();
            final PotionEffect potion = PotionUtil.createPotion(potionType, duration, this.grade, true, isEnableParticle);
            player.addPotionEffect(potion);
            passiveEffectManager.setPassiveEffectCooldown(BuffInvisibility.buff, (OfflinePlayer)player, cooldown);
        }
    }
    
    private final int getDuration() {
        final MainConfig mainConfig = MainConfig.getInstance();
        return (int)(mainConfig.getPassivePeriodEffect() * this.grade / 2.5 + 20.0);
    }
    
    private final long getCooldown() {
        final MainConfig mainConfig = MainConfig.getInstance();
        return (long)(MathUtil.convertTickToMilis(mainConfig.getPassivePeriodEffect()) * BuffInvisibility.buff.getMaxGrade() / 2.5);
    }
}
