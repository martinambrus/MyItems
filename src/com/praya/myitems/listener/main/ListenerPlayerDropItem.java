// 
// Decompiled by Procyon v0.5.36
// 

package com.praya.myitems.listener.main;

import org.bukkit.event.EventPriority;
import com.praya.myitems.utility.main.TriggerSupportUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.entity.Item;
import com.praya.myitems.manager.game.GameManager;
import org.bukkit.plugin.Plugin;
import api.praya.myitems.builder.passive.PassiveEffectEnum;
import org.bukkit.entity.LivingEntity;
import java.util.Collection;
import com.praya.myitems.manager.game.PassiveEffectManager;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import com.praya.myitems.manager.game.ItemSetManager;
import org.bukkit.scheduler.BukkitRunnable;
import com.praya.agarthalib.utility.EquipmentUtil;
import core.praya.agarthalib.enums.main.Slot;
import core.praya.agarthalib.bridge.unity.Bridge;
import com.praya.myitems.config.plugin.MainConfig;
import org.bukkit.event.player.PlayerDropItemEvent;
import com.praya.myitems.MyItems;
import org.bukkit.event.Listener;
import com.praya.myitems.builder.handler.HandlerEvent;

public class ListenerPlayerDropItem extends HandlerEvent implements Listener
{
    public ListenerPlayerDropItem(final MyItems plugin) {
        super(plugin);
    }
    
    @EventHandler
    public void playerDropItemEvent(final PlayerDropItemEvent event) {
        final GameManager gameManager = this.plugin.getGameManager();
        final PassiveEffectManager passiveEffectManager = gameManager.getPassiveEffectManager();
        final ItemSetManager itemSetManager = gameManager.getItemSetManager();
        final MainConfig mainConfig = MainConfig.getInstance();
        final Player player = event.getPlayer();
        if (!EquipmentUtil.isSolid(Bridge.getBridgeEquipment().getEquipment(player, Slot.MAINHAND))) {
            final Item drop = event.getItemDrop();
            final ItemStack item = drop.getItemStack();
            final boolean enableGradeCalculation = mainConfig.isPassiveEnableGradeCalculation();
            final Collection<PassiveEffectEnum> passiveEffects = passiveEffectManager.getPassiveEffects(item);
            new BukkitRunnable() {
                public void run() {
                    if (itemSetManager.isItemSet(item)) {
                        itemSetManager.updateItemSet((LivingEntity)player);
                    }
                    passiveEffectManager.reloadPassiveEffect(player, passiveEffects, enableGradeCalculation);
                }
            }.runTaskLater((Plugin)this.plugin, 1L);
        }
    }
    
    @EventHandler(priority = EventPriority.MONITOR)
    public void triggerSupport(final PlayerDropItemEvent event) {
        if (!event.isCancelled()) {
            final Player player = event.getPlayer();
            new BukkitRunnable() {
                public void run() {
                    TriggerSupportUtil.updateSupport(player);
                }
            }.runTaskLater((Plugin)this.plugin, 2L);
        }
    }
}
