// 
// Decompiled by Procyon v0.5.36
// 

package com.praya.myitems.listener.main;

import api.praya.myitems.builder.power.PowerEnum;
import java.util.List;
import com.praya.myitems.manager.game.LoreStatsManager;
import com.praya.myitems.manager.game.PowerManager;
import org.bukkit.event.Event;
import com.praya.agarthalib.utility.ServerEventUtil;
import api.praya.myitems.builder.event.PowerPreCastEvent;
import api.praya.myitems.builder.power.PowerClickEnum;
import api.praya.myitems.builder.lorestats.LoreStatsOption;
import api.praya.myitems.builder.lorestats.LoreStatsEnum;
import com.praya.myitems.utility.main.TriggerSupportUtil;
import org.bukkit.plugin.Plugin;
import com.praya.myitems.manager.game.ItemSetManager;
import org.bukkit.scheduler.BukkitRunnable;
import core.praya.agarthalib.enums.main.SlotType;
import org.bukkit.event.block.Action;
import java.util.HashMap;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.EventPriority;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;
import org.bukkit.entity.Player;
import com.praya.myitems.manager.plugin.LanguageManager;
import com.praya.myitems.manager.game.RequirementManager;
import com.praya.myitems.manager.plugin.PluginManager;
import com.praya.myitems.manager.game.GameManager;
import core.praya.agarthalib.enums.branch.SoundEnum;
import org.bukkit.command.CommandSender;
import com.praya.agarthalib.utility.SenderUtil;
import com.praya.agarthalib.utility.TextUtil;
import org.bukkit.entity.LivingEntity;
import com.praya.agarthalib.utility.EquipmentUtil;
import core.praya.agarthalib.enums.main.Slot;
import core.praya.agarthalib.bridge.unity.Bridge;
import org.bukkit.event.player.PlayerInteractEvent;
import com.praya.myitems.MyItems;
import org.bukkit.event.Listener;
import com.praya.myitems.builder.handler.HandlerEvent;

public class ListenerPlayerInteract extends HandlerEvent implements Listener
{
    public ListenerPlayerInteract(final MyItems plugin) {
        super(plugin);
    }
    
    @EventHandler(priority = EventPriority.LOWEST)
    public void checkRequirement(final PlayerInteractEvent event) {
        final GameManager gameManager = this.plugin.getGameManager();
        final PluginManager pluginManager = this.plugin.getPluginManager();
        final RequirementManager requirementManager = gameManager.getRequirementManager();
        final LanguageManager lang = pluginManager.getLanguageManager();
        final Player player = event.getPlayer();
        final ItemStack item = Bridge.getBridgeEquipment().getEquipment(player, Slot.MAINHAND);
        if (EquipmentUtil.loreCheck(item)) {
            if (!requirementManager.isAllowedReqSoulBound(player, item)) {
                final String reqBound = requirementManager.getRequirementSoulBound(item);
                final String message = TextUtil.placeholder(lang.getText((LivingEntity)player, "Requirement_Not_Allowed_Bound"), "Player", reqBound);
                event.setCancelled(true);
                SenderUtil.sendMessage((CommandSender)player, message);
                SenderUtil.playSound((CommandSender)player, SoundEnum.ENTITY_BLAZE_DEATH);
                return;
            }
            if (!requirementManager.isAllowedReqPermission(player, item)) {
                final String reqPermission = requirementManager.getRequirementPermission(item);
                final String message = TextUtil.placeholder(lang.getText((LivingEntity)player, "Requirement_Not_Allowed_Permission"), "Permission", reqPermission);
                event.setCancelled(true);
                SenderUtil.sendMessage((CommandSender)player, message);
                SenderUtil.playSound((CommandSender)player, SoundEnum.ENTITY_BLAZE_DEATH);
                return;
            }
            if (!requirementManager.isAllowedReqLevel(player, item)) {
                final int reqLevel = requirementManager.getRequirementLevel(item);
                final String message = TextUtil.placeholder(lang.getText((LivingEntity)player, "Requirement_Not_Allowed_Level"), "Level", String.valueOf(reqLevel));
                event.setCancelled(true);
                SenderUtil.sendMessage((CommandSender)player, message);
                SenderUtil.playSound((CommandSender)player, SoundEnum.ENTITY_BLAZE_DEATH);
                return;
            }
            if (!requirementManager.isAllowedReqClass(player, item)) {
                final String reqClass = requirementManager.getRequirementClass(item);
                final String message = TextUtil.placeholder(lang.getText((LivingEntity)player, "Requirement_Not_Allowed_Class"), "Class", reqClass);
                event.setCancelled(true);
                SenderUtil.sendMessage((CommandSender)player, message);
                SenderUtil.playSound((CommandSender)player, SoundEnum.ENTITY_BLAZE_DEATH);
            }
        }
    }
    
    @EventHandler(priority = EventPriority.NORMAL)
    public void checkBound(final PlayerInteractEvent event) {
        final GameManager gameManager = this.plugin.getGameManager();
        final PluginManager pluginManager = this.plugin.getPluginManager();
        final RequirementManager requirementManager = gameManager.getRequirementManager();
        final LanguageManager lang = pluginManager.getLanguageManager();
        if (!event.isCancelled()) {
            final Player player = event.getPlayer();
            final ItemStack item = Bridge.getBridgeEquipment().getEquipment(player, Slot.MAINHAND);
            if (EquipmentUtil.loreCheck(item)) {
                final Integer lineUnbound = requirementManager.getLineRequirementSoulUnbound(item);
                if (lineUnbound != null) {
                    final String loreBound = requirementManager.getTextSoulBound((OfflinePlayer)player);
                    final Integer lineOld = requirementManager.getLineRequirementSoulBound(item);
                    final HashMap<String, String> map = new HashMap<String, String>();
                    if (lineOld != null) {
                        EquipmentUtil.removeLore(item, (int)lineOld);
                    }
                    String message = lang.getText((LivingEntity)player, "Item_Bound");
                    map.put("item", EquipmentUtil.getDisplayName(item));
                    map.put("player", player.getName());
                    message = TextUtil.placeholder((HashMap)map, message);
                    requirementManager.setMetadataSoulbound((OfflinePlayer)player, item);
                    EquipmentUtil.setLore(item, (int)lineUnbound, loreBound);
                    SenderUtil.sendMessage((CommandSender)player, message);
                }
            }
        }
    }
    
    @EventHandler(priority = EventPriority.NORMAL)
    public void triggerEquipmentChangeEvent(final PlayerInteractEvent event) {
        final GameManager gameManager = this.plugin.getGameManager();
        final ItemSetManager itemSetManager = gameManager.getItemSetManager();
        final Action action = event.getAction();
        if (action.equals((Object)Action.RIGHT_CLICK_AIR) || action.equals((Object)Action.RIGHT_CLICK_BLOCK)) {
            final Player player = event.getPlayer();
            final ItemStack item = Bridge.getBridgeEquipment().getEquipment(player, Slot.MAINHAND);
            if (itemSetManager.isItemSet(item)) {
                final SlotType slotType = SlotType.getSlotType(item);
                if (slotType.equals((Object)SlotType.ARMOR)) {
                    new BukkitRunnable() {
                        public void run() {
                            itemSetManager.updateItemSet((LivingEntity)player);
                        }
                    }.runTaskLater((Plugin)this.plugin, 0L);
                }
            }
        }
    }
    
    @EventHandler(priority = EventPriority.NORMAL)
    public void triggerSupport(final PlayerInteractEvent event) {
        final Action action = event.getAction();
        if (action.equals((Object)Action.RIGHT_CLICK_AIR) || action.equals((Object)Action.RIGHT_CLICK_BLOCK)) {
            final Player player = event.getPlayer();
            final ItemStack item = Bridge.getBridgeEquipment().getEquipment(player, Slot.MAINHAND);
            final SlotType slotType = SlotType.getSlotType(item);
            if (slotType.equals((Object)SlotType.ARMOR)) {
                new BukkitRunnable() {
                    public void run() {
                        TriggerSupportUtil.updateSupport(player);
                    }
                }.runTaskLater((Plugin)this.plugin, 2L);
            }
        }
    }
    
    @EventHandler
    public void powerCheckEvent(final PlayerInteractEvent event) {
        final GameManager gameManager = this.plugin.getGameManager();
        final PowerManager powerManager = gameManager.getPowerManager();
        final LoreStatsManager statsManager = gameManager.getStatsManager();
        final Player player = event.getPlayer();
        final ItemStack item = Bridge.getBridgeEquipment().getEquipment(player, Slot.MAINHAND);
        if (EquipmentUtil.loreCheck(item)) {
            final int durability = (int)statsManager.getLoreValue(item, LoreStatsEnum.DURABILITY, LoreStatsOption.CURRENT);
            if (statsManager.checkDurability(item, durability)) {
                final Action action = event.getAction();
                PowerClickEnum click;
                if (action.equals((Object)Action.LEFT_CLICK_AIR) || action.equals((Object)Action.LEFT_CLICK_BLOCK)) {
                    if (player.isSneaking()) {
                        click = PowerClickEnum.SHIFT_LEFT;
                    }
                    else {
                        click = PowerClickEnum.LEFT;
                    }
                }
                else {
                    if (!action.equals((Object)Action.RIGHT_CLICK_AIR) && !action.equals((Object)Action.RIGHT_CLICK_BLOCK)) {
                        return;
                    }
                    if (player.isSneaking()) {
                        click = PowerClickEnum.SHIFT_RIGHT;
                    }
                    else {
                        click = PowerClickEnum.RIGHT;
                    }
                }
                final int line = powerManager.getLineClick(item, click);
                if (line != -1) {
                    final List<String> lores = (List<String>)EquipmentUtil.getLores(item);
                    final String lore = lores.get(line - 1);
                    final PowerEnum power = powerManager.getPower(lore);
                    if (power != null) {
                        final PowerPreCastEvent powerPreCastEvent = new PowerPreCastEvent(player, power, click, item, lore);
                        ServerEventUtil.callEvent((Event)powerPreCastEvent);
                    }
                }
            }
        }
    }
}
