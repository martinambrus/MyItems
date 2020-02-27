// 
// Decompiled by Procyon v0.5.36
// 

package com.praya.myitems.listener.main;

import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;
import org.bukkit.entity.Player;
import com.praya.myitems.manager.game.LoreStatsManager;
import com.praya.myitems.manager.game.GameManager;
import org.bukkit.entity.LivingEntity;
import api.praya.myitems.builder.lorestats.LoreStatsOption;
import api.praya.myitems.builder.lorestats.LoreStatsEnum;
import com.praya.agarthalib.utility.EquipmentUtil;
import core.praya.agarthalib.enums.main.Slot;
import core.praya.agarthalib.bridge.unity.Bridge;
import com.praya.agarthalib.utility.BlockUtil;
import org.bukkit.event.block.BlockBreakEvent;
import com.praya.myitems.MyItems;
import org.bukkit.event.Listener;
import com.praya.myitems.builder.handler.HandlerEvent;

public class ListenerBlockBreak extends HandlerEvent implements Listener
{
    public ListenerBlockBreak(final MyItems plugin) {
        super(plugin);
    }
    
    @EventHandler
    public void eventBlockBreak(final BlockBreakEvent event) {
        final GameManager gameManager = this.plugin.getGameManager();
        final LoreStatsManager statsManager = gameManager.getStatsManager();
        if (!event.isCancelled()) {
            if (BlockUtil.isSet(event.getBlock())) {
                event.setCancelled(true);
                return;
            }
            final Player player = event.getPlayer();
            final ItemStack item = Bridge.getBridgeEquipment().getEquipment(player, Slot.MAINHAND);
            if (EquipmentUtil.hasLore(item)) {
                final int durability = (int)statsManager.getLoreValue(item, LoreStatsEnum.DURABILITY, LoreStatsOption.CURRENT);
                if (!statsManager.durability((LivingEntity)player, item, durability, true)) {
                    event.setCancelled(true);
                    statsManager.sendBrokenCode((LivingEntity)player, Slot.MAINHAND);
                }
            }
        }
    }
}
