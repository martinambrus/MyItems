// 
// Decompiled by Procyon v0.5.36
// 

package com.praya.myitems.listener.support;

import org.bukkit.event.EventPriority;
import org.bukkit.event.EventHandler;
import api.praya.myitems.builder.socket.SocketGemsProperties;
import api.praya.myitems.builder.lorestats.LoreStatsArmor;
import org.bukkit.entity.Player;
import com.praya.myitems.manager.game.SocketManager;
import com.praya.myitems.manager.game.LoreStatsManager;
import com.praya.myitems.manager.game.GameManager;
import org.bukkit.entity.LivingEntity;
import api.praya.lifeessence.builder.event.PlayerHealthRegenChangeEvent;
import com.praya.myitems.MyItems;
import org.bukkit.event.Listener;
import com.praya.myitems.builder.handler.HandlerEvent;

public class ListenerPlayerHealthRegenChange extends HandlerEvent implements Listener
{
    public ListenerPlayerHealthRegenChange(final MyItems plugin) {
        super(plugin);
    }
    
    @EventHandler(priority = EventPriority.NORMAL)
    public void eventPlayerHealthRegenChange(final PlayerHealthRegenChangeEvent event) {
        final GameManager gameManager = this.plugin.getGameManager();
        final LoreStatsManager statsManager = gameManager.getStatsManager();
        final SocketManager socketManager = gameManager.getSocketManager();
        if (!event.isCancelled()) {
            final Player player = event.getPlayer();
            final PlayerHealthRegenChangeEvent.HealthRegenModifierEnum healthRegenType = PlayerHealthRegenChangeEvent.HealthRegenModifierEnum.BONUS;
            final LoreStatsArmor statsBuild = statsManager.getLoreStatsArmor((LivingEntity)player);
            final SocketGemsProperties socketBuild = socketManager.getSocketProperties((LivingEntity)player);
            final double healthRegenStats = statsBuild.getHealthRegen();
            final double healthRegenSocket = socketBuild.getHealthRegen();
            final double healthRegenBase = event.getOriginalHealthRegen(healthRegenType);
            final double healthRegenResult = healthRegenStats + healthRegenSocket + healthRegenBase;
            event.setHealthRegen(healthRegenType, healthRegenResult);
        }
    }
}
