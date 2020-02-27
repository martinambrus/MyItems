// 
// Decompiled by Procyon v0.5.36
// 

package com.praya.myitems.listener.main;

import org.bukkit.event.EventPriority;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.Inventory;
import com.praya.myitems.manager.game.GameManager;
import org.bukkit.plugin.Plugin;
import api.praya.myitems.builder.passive.PassiveEffectEnum;
import java.util.Collection;
import org.bukkit.entity.LivingEntity;
import com.praya.myitems.utility.main.TriggerSupportUtil;
import com.praya.myitems.manager.game.PassiveEffectManager;
import com.praya.myitems.manager.game.ItemSetManager;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import com.praya.agarthalib.utility.EquipmentUtil;
import com.praya.myitems.config.plugin.MainConfig;
import org.bukkit.event.player.PlayerItemHeldEvent;
import com.praya.myitems.MyItems;
import org.bukkit.event.Listener;
import com.praya.myitems.builder.handler.HandlerEvent;

public class ListenerHeldItem extends HandlerEvent implements Listener
{
    public ListenerHeldItem(final MyItems plugin) {
        super(plugin);
    }
    
    @EventHandler(priority = EventPriority.MONITOR)
    public void playerItemHeldEvent(final PlayerItemHeldEvent event) {
        final GameManager gameManager = this.plugin.getGameManager();
        final PassiveEffectManager passiveEffectManager = gameManager.getPassiveEffectManager();
        final ItemSetManager itemSetManager = gameManager.getItemSetManager();
        final MainConfig mainConfig = MainConfig.getInstance();
        if (!event.isCancelled()) {
            final Player player = event.getPlayer();
            final int slotPrevious = event.getPreviousSlot();
            final int slotAfter = event.getNewSlot();
            final Inventory inventory = (Inventory)player.getInventory();
            final ItemStack itemBefore = inventory.getItem(slotPrevious);
            final ItemStack itemAfter = inventory.getItem(slotAfter);
            final boolean enableItemUniversal = mainConfig.isStatsEnableItemUniversal();
            final boolean enableGradeCalculation = mainConfig.isPassiveEnableGradeCalculation();
            final boolean isSolidBefore = EquipmentUtil.isSolid(itemBefore);
            final boolean isSolidAfter = EquipmentUtil.isSolid(itemAfter);
            if (isSolidBefore || isSolidAfter) {
                new BukkitRunnable() {
                    public void run() {
                        final boolean isShieldBefore = isSolidBefore && itemBefore.getType().toString().equalsIgnoreCase("SHIELD");
                        final boolean isShieldAfter = isSolidAfter && itemAfter.getType().toString().equalsIgnoreCase("SHIELD");
                        if (enableItemUniversal || isShieldBefore || isShieldAfter) {
                            TriggerSupportUtil.updateSupport(player);
                        }
                        if (itemSetManager.isItemSet(itemBefore) || itemSetManager.isItemSet(itemAfter)) {
                            itemSetManager.updateItemSet((LivingEntity)player);
                        }
                        if (isSolidBefore) {
                            final Collection<PassiveEffectEnum> passiveEffectBefore = passiveEffectManager.getPassiveEffects(itemBefore);
                            passiveEffectManager.reloadPassiveEffect(player, passiveEffectBefore, enableGradeCalculation);
                        }
                        if (isSolidAfter) {
                            final Collection<PassiveEffectEnum> passiveEffectAfter = passiveEffectManager.getPassiveEffects(itemAfter);
                            passiveEffectManager.reloadPassiveEffect(player, passiveEffectAfter, enableGradeCalculation);
                        }
                    }
                }.runTaskLater((Plugin)this.plugin, 0L);
            }
        }
    }
}
