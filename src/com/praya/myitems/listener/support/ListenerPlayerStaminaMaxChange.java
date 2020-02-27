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
import api.praya.combatstamina.builder.event.PlayerStaminaMaxChangeEvent;
import com.praya.myitems.MyItems;
import org.bukkit.event.Listener;
import com.praya.myitems.builder.handler.HandlerEvent;

public class ListenerPlayerStaminaMaxChange extends HandlerEvent implements Listener
{
    public ListenerPlayerStaminaMaxChange(final MyItems plugin) {
        super(plugin);
    }
    
    @EventHandler(priority = EventPriority.NORMAL)
    public void eventPlayerStaminaMaxChange(final PlayerStaminaMaxChangeEvent event) {
        final GameManager gameManager = this.plugin.getGameManager();
        final LoreStatsManager statsManager = gameManager.getStatsManager();
        final SocketManager socketManager = gameManager.getSocketManager();
        if (!event.isCancelled()) {
            final Player player = event.getPlayer();
            final PlayerStaminaMaxChangeEvent.StaminaMaxModifierEnum staminaMaxType = PlayerStaminaMaxChangeEvent.StaminaMaxModifierEnum.BONUS;
            final LoreStatsArmor statsBuild = statsManager.getLoreStatsArmor((LivingEntity)player);
            final SocketGemsProperties socketBuild = socketManager.getSocketProperties((LivingEntity)player);
            final double staminaMaxStats = statsBuild.getStaminaMax();
            final double staminaMaxSocket = socketBuild.getStaminaMax();
            final double staminaMaxBase = event.getOriginalMaxStamina(staminaMaxType);
            final double staminaMaxResult = staminaMaxStats + staminaMaxSocket + staminaMaxBase;
            event.setMaxStamina(staminaMaxType, staminaMaxResult);
        }
    }
}
