// 
// Decompiled by Procyon v0.5.36
// 

package com.praya.myitems.listener.main;

import org.bukkit.event.EventPriority;
import com.praya.myitems.utility.main.TriggerSupportUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.util.Vector;
import org.bukkit.entity.Entity;
import com.praya.myitems.utility.main.CustomEffectUtil;
import api.praya.myitems.builder.passive.PassiveEffectTypeEnum;
import com.praya.agarthalib.utility.PlayerUtil;
import org.bukkit.plugin.Plugin;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.event.player.PlayerJoinEvent;
import com.praya.myitems.MyItems;
import org.bukkit.event.Listener;
import com.praya.myitems.builder.handler.HandlerEvent;

public class ListenerPlayerJoin extends HandlerEvent implements Listener
{
    public ListenerPlayerJoin(final MyItems plugin) {
        super(plugin);
    }
    
    @EventHandler
    public void eventPlayerJoin(final PlayerJoinEvent event) {
        final Player player = event.getPlayer();
        new BukkitRunnable() {
            public void run() {
                if (player.isOnline() && player.getWalkSpeed() < 0.05f) {
                    player.setWalkSpeed(0.2f);
                }
            }
        }.runTaskLater((Plugin)this.plugin, 1L);
        new BukkitRunnable() {
            public void run() {
                if (!PlayerUtil.isOnline(player)) {
                    this.cancel();
                    return;
                }
                if (CustomEffectUtil.isRunCustomEffect((Entity)player, PassiveEffectTypeEnum.ROOTS)) {
                    final Vector vector = player.getVelocity().setY(-1);
                    player.setVelocity(vector);
                }
            }
        }.runTaskTimer((Plugin)this.plugin, 0L, 1L);
    }
    
    @EventHandler(priority = EventPriority.HIGHEST)
    public void triggerSupport(final PlayerJoinEvent event) {
        final Player player = event.getPlayer();
        TriggerSupportUtil.updateSupport(player);
    }
}
