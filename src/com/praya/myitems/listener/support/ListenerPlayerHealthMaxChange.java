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
import com.praya.myitems.config.plugin.MainConfig;
import api.praya.agarthalib.builder.event.PlayerHealthMaxChangeEvent;
import com.praya.myitems.MyItems;
import org.bukkit.event.Listener;
import com.praya.myitems.builder.handler.HandlerEvent;

public class ListenerPlayerHealthMaxChange extends HandlerEvent implements Listener
{
    public ListenerPlayerHealthMaxChange(final MyItems plugin) {
        super(plugin);
    }
    
    @EventHandler(priority = EventPriority.NORMAL)
    public void eventPlayerHealthMaxChange(final PlayerHealthMaxChangeEvent event) {
        final GameManager gameManager = this.plugin.getGameManager();
        final LoreStatsManager statsManager = gameManager.getStatsManager();
        final SocketManager socketManager = gameManager.getSocketManager();
        final MainConfig mainConfig = MainConfig.getInstance();
        final boolean enableMaxHealth = mainConfig.isStatsEnableMaxHealth();
        if (!event.isCancelled() && enableMaxHealth) {
            final Player player = event.getPlayer();
            final LoreStatsArmor statsBuild = statsManager.getLoreStatsArmor((LivingEntity)player);
            final SocketGemsProperties socketBuild = socketManager.getSocketProperties((LivingEntity)player);
            final double healthStats = statsBuild.getHealth();
            final double healthSocket = socketBuild.getHealth();
            final double healthBase = event.getMaxHealth();
            final double healthResult = healthStats + healthSocket + healthBase;
            event.setMaxHealth(healthResult);
        }
    }
}
