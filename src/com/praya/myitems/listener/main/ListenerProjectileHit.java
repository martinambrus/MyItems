// 
// Decompiled by Procyon v0.5.36
// 

package com.praya.myitems.listener.main;

import org.bukkit.event.EventHandler;
import org.bukkit.entity.Entity;
import com.praya.agarthalib.utility.ProjectileUtil;
import org.bukkit.event.entity.ProjectileHitEvent;
import com.praya.myitems.MyItems;
import org.bukkit.event.Listener;
import com.praya.myitems.builder.handler.HandlerEvent;

public class ListenerProjectileHit extends HandlerEvent implements Listener
{
    public ListenerProjectileHit(final MyItems plugin) {
        super(plugin);
    }
    
    @EventHandler
    public void onProjectileHit(final ProjectileHitEvent event) {
        final Entity projectile = (Entity)event.getEntity();
        if (ProjectileUtil.isDisappear(projectile) && !projectile.isDead()) {
            projectile.remove();
        }
    }
}
