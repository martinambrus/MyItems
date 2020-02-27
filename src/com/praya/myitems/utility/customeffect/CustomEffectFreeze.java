// 
// Decompiled by Procyon v0.5.36
// 

package com.praya.myitems.utility.customeffect;

import org.bukkit.Location;
import core.praya.agarthalib.enums.branch.SoundEnum;
import java.util.Collection;
import core.praya.agarthalib.enums.branch.ParticleEnum;
import core.praya.agarthalib.bridge.unity.Bridge;
import com.praya.myitems.config.plugin.MainConfig;
import com.praya.myitems.utility.main.CustomEffectUtil;
import org.bukkit.entity.Player;
import com.praya.agarthalib.utility.PlayerUtil;
import org.bukkit.entity.Entity;
import com.praya.agarthalib.utility.EntityUtil;
import org.bukkit.entity.LivingEntity;
import api.praya.myitems.builder.passive.PassiveEffectTypeEnum;

public class CustomEffectFreeze
{
    protected static final PassiveEffectTypeEnum customEffect;
    
    static {
        customEffect = PassiveEffectTypeEnum.FREEZE;
    }
    
    public static final void cast(final LivingEntity livingEntity) {
        if (EntityUtil.isPlayer((Entity)livingEntity)) {
            effect(PlayerUtil.parse(livingEntity));
        }
        display(livingEntity);
    }
    
    public static final void cast(final Player player) {
        effect(player);
        display((LivingEntity)player);
    }
    
    public static final void effect(final Player player) {
        if (CustomEffectUtil.isRunCustomEffect((Entity)player, CustomEffectFreeze.customEffect)) {
            if (player.getWalkSpeed() >= 0.05f) {
                CustomEffectUtil.setSpeedBase((Entity)player, player.getWalkSpeed());
                player.setWalkSpeed(0.0f);
            }
        }
        else if (CustomEffectUtil.hasSpeedBase((Entity)player)) {
            final float baseSpeed = CustomEffectUtil.getSpeedBase((Entity)player);
            CustomEffectUtil.removeSpeedBase((Entity)player);
            player.setWalkSpeed(baseSpeed);
        }
    }
    
    public static final void display(final LivingEntity livingEntity) {
        final MainConfig mainConfig = MainConfig.getInstance();
        if (CustomEffectUtil.isRunCustomEffect((Entity)livingEntity, CustomEffectFreeze.customEffect)) {
            final Location loc = livingEntity.getLocation();
            final Collection<Player> players = (Collection<Player>)PlayerUtil.getNearbyPlayers(loc, mainConfig.getEffectRange());
            Bridge.getBridgeParticle().playParticle((Collection)players, ParticleEnum.CLOUD, loc, 10, 0.25, 0.25, 0.25, 0.10000000149011612);
            Bridge.getBridgeSound().playSound((Collection)players, loc, SoundEnum.BLOCK_GLASS_BREAK, 1.0f, 1.0f);
        }
    }
}
