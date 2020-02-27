// 
// Decompiled by Procyon v0.5.36
// 

package com.praya.myitems.listener.main;

import com.praya.myitems.manager.game.ItemSetManager;
import com.praya.myitems.manager.game.LoreStatsManager;
import com.praya.myitems.manager.game.PassiveEffectManager;
import core.praya.agarthalib.enums.main.Slot;
import core.praya.agarthalib.bridge.unity.Bridge;
import api.praya.myitems.builder.lorestats.LoreStatsOption;
import api.praya.myitems.builder.lorestats.LoreStatsEnum;
import com.praya.agarthalib.utility.PlayerUtil;
import org.bukkit.entity.Entity;
import com.praya.agarthalib.utility.EntityUtil;
import com.praya.myitems.config.plugin.MainConfig;
import java.util.HashMap;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.EventPriority;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;
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
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityShootBowEvent;
import com.praya.myitems.MyItems;
import org.bukkit.event.Listener;
import com.praya.myitems.builder.handler.HandlerEvent;

public class ListenerEntityShootBow extends HandlerEvent implements Listener
{
    public ListenerEntityShootBow(final MyItems plugin) {
        super(plugin);
    }
    
    @EventHandler(priority = EventPriority.LOWEST)
    public void checkRequirement(final EntityShootBowEvent event) {
        final GameManager gameManager = this.plugin.getGameManager();
        final PluginManager pluginManager = this.plugin.getPluginManager();
        final RequirementManager requirementManager = gameManager.getRequirementManager();
        final LanguageManager lang = pluginManager.getLanguageManager();
        final LivingEntity shooter = event.getEntity();
        if (shooter instanceof Player) {
            final Player player = (Player)shooter;
            final ItemStack item = event.getBow();
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
    }
    
    @EventHandler(priority = EventPriority.NORMAL)
    public void checkUnbound(final EntityShootBowEvent event) {
        final GameManager gameManager = this.plugin.getGameManager();
        final PluginManager pluginManager = this.plugin.getPluginManager();
        final RequirementManager requirementManager = gameManager.getRequirementManager();
        final LanguageManager lang = pluginManager.getLanguageManager();
        if (!event.isCancelled()) {
            final LivingEntity shooter = event.getEntity();
            if (shooter instanceof Player) {
                final Player player = (Player)shooter;
                final ItemStack item = event.getBow();
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
    }
    
    @EventHandler(priority = EventPriority.HIGH)
    public void entityShootBowEvent(final EntityShootBowEvent event) {
        final GameManager gameManager = this.plugin.getGameManager();
        final PassiveEffectManager passiveEffectManager = gameManager.getPassiveEffectManager();
        final LoreStatsManager statsManager = gameManager.getStatsManager();
        final ItemSetManager itemSetManager = gameManager.getItemSetManager();
        final LanguageManager lang = this.plugin.getPluginManager().getLanguageManager();
        final MainConfig mainConfig = MainConfig.getInstance();
        if (!event.isCancelled() && EntityUtil.isPlayer((Entity)event.getEntity())) {
            final Player player = PlayerUtil.parse(event.getEntity());
            final ItemStack item = event.getBow();
            if (EquipmentUtil.loreCheck(item) && statsManager.hasLoreStats(item, LoreStatsEnum.DURABILITY)) {
                final int durability = (int)statsManager.getLoreValue(item, LoreStatsEnum.DURABILITY, LoreStatsOption.CURRENT);
                final int nextValue = durability - 1;
                if (nextValue < 1) {
                    final boolean enableItemBroken = mainConfig.isStatsEnableItemBroken();
                    final boolean enableItemBrokenMessage = mainConfig.isStatsEnableItemBrokenMessage();
                    if (enableItemBrokenMessage) {
                        final String message = lang.getText((LivingEntity)player, "Item_Broken_Bow");
                        SenderUtil.playSound((CommandSender)player, SoundEnum.ENTITY_BLAZE_DEATH);
                        SenderUtil.sendMessage((CommandSender)player, message);
                    }
                    if (enableItemBroken) {
                        final boolean enableGradeCalculation = mainConfig.isPassiveEnableGradeCalculation();
                        passiveEffectManager.reloadPassiveEffect(player, item, enableGradeCalculation);
                        if (Bridge.getBridgeEquipment().getEquipment(player, Slot.MAINHAND).equals((Object)item)) {
                            Bridge.getBridgeEquipment().setEquipment(player, (ItemStack)null, Slot.MAINHAND);
                        }
                        else if (Bridge.getBridgeEquipment().getEquipment(player, Slot.OFFHAND).equals((Object)item)) {
                            Bridge.getBridgeEquipment().setEquipment(player, (ItemStack)null, Slot.OFFHAND);
                        }
                        itemSetManager.updateItemSet((LivingEntity)player);
                    }
                }
                if (durability < 1) {
                    event.setCancelled(true);
                }
                else {
                    statsManager.damageDurability(item);
                }
            }
        }
    }
}
