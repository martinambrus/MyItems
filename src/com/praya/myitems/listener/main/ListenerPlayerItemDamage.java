// 
// Decompiled by Procyon v0.5.36
// 

package com.praya.myitems.listener.main;

import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;
import core.praya.agarthalib.bridge.unity.BridgeTagsNBT;
import com.praya.myitems.manager.game.LoreStatsManager;
import com.praya.myitems.manager.game.GameManager;
import api.praya.myitems.builder.lorestats.LoreStatsEnum;
import core.praya.agarthalib.bridge.unity.Bridge;
import org.bukkit.event.player.PlayerItemDamageEvent;
import com.praya.myitems.MyItems;
import org.bukkit.event.Listener;
import com.praya.myitems.builder.handler.HandlerEvent;

public class ListenerPlayerItemDamage extends HandlerEvent implements Listener
{
    public ListenerPlayerItemDamage(final MyItems plugin) {
        super(plugin);
    }
    
    @EventHandler
    public void itemDamageEvent(final PlayerItemDamageEvent event) {
        final GameManager gameManager = this.plugin.getGameManager();
        final LoreStatsManager statsManager = gameManager.getStatsManager();
        if (!event.isCancelled()) {
            final BridgeTagsNBT bridgeTagsNBT = Bridge.getBridgeTagsNBT();
            final ItemStack item = event.getItem();
            if (bridgeTagsNBT.isUnbreakable(item) || statsManager.hasLoreStats(item, LoreStatsEnum.DURABILITY)) {
                event.setCancelled(true);
            }
        }
    }
}
