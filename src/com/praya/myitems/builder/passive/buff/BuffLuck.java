// 
// Decompiled by Procyon v0.5.36
// 

package com.praya.myitems.builder.passive.buff;

import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import com.praya.agarthalib.utility.PotionUtil;
import com.praya.agarthalib.utility.ServerUtil;
import core.praya.agarthalib.enums.main.VersionNMS;
import com.praya.myitems.config.plugin.MainConfig;
import org.bukkit.entity.Player;
import api.praya.myitems.builder.passive.PassiveEffectEnum;
import com.praya.myitems.builder.abs.PassiveEffect;

public class BuffLuck extends PassiveEffect
{
    private static final PassiveEffectEnum buff;
    
    static {
        buff = PassiveEffectEnum.LUCK;
    }
    
    public BuffLuck() {
        super(BuffLuck.buff, 1);
    }
    
    public BuffLuck(final int grade) {
        super(BuffLuck.buff, grade);
    }
    
    @Override
    public final void cast(final Player player) {
        final MainConfig mainConfig = MainConfig.getInstance();
        if (ServerUtil.isCompatible(VersionNMS.V1_9_R2)) {
            final PotionEffectType potionType = this.getPotion();
            final boolean isEnableParticle = mainConfig.isMiscEnableParticlePotion();
            final PotionEffect potion = PotionUtil.createPotion(potionType, 96000, this.grade, true, isEnableParticle);
            player.addPotionEffect(potion);
        }
    }
}
