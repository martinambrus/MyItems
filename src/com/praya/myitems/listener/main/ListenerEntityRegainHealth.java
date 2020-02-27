// 
// Decompiled by Procyon v0.5.36
// 

package com.praya.myitems.listener.main;

import org.bukkit.event.EventHandler;
import org.bukkit.entity.Entity;
import com.praya.myitems.utility.main.CustomEffectUtil;
import api.praya.myitems.builder.passive.PassiveEffectTypeEnum;
import org.bukkit.entity.LivingEntity;
import core.praya.agarthalib.bridge.unity.Bridge;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import com.praya.myitems.MyItems;
import org.bukkit.event.Listener;
import com.praya.myitems.builder.handler.HandlerEvent;

public class ListenerEntityRegainHealth extends HandlerEvent implements Listener
{
    public ListenerEntityRegainHealth(final MyItems plugin) {
        super(plugin);
    }
    
    @EventHandler
    public void eventEntityRegainHealth(final EntityRegainHealthEvent event) {
        if (!event.isCancelled() && Bridge.getBridgeLivingEntity().isLivingEntity(event.getEntity())) {
            final LivingEntity entity = (LivingEntity)event.getEntity();
            if (CustomEffectUtil.isRunCustomEffect((Entity)entity, PassiveEffectTypeEnum.CURSE)) {
                event.setCancelled(true);
            }
        }
    }
}
