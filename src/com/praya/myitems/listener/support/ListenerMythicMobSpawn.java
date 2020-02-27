// 
// Decompiled by Procyon v0.5.36
// 

package com.praya.myitems.listener.support;

import org.bukkit.event.EventHandler;
import api.praya.myitems.builder.item.ItemSet;
import api.praya.myitems.builder.item.ItemGenerator;
import org.bukkit.inventory.ItemStack;
import java.util.Iterator;
import org.bukkit.entity.LivingEntity;
import java.util.List;
import org.bukkit.entity.Entity;
import io.lumine.xikage.mythicmobs.mobs.MythicMob;
import com.praya.myitems.manager.game.ItemSetManager;
import com.praya.myitems.manager.game.ItemGeneratorManager;
import com.praya.myitems.manager.game.ItemManager;
import com.praya.myitems.manager.game.GameManager;
import core.praya.agarthalib.enums.main.SlotType;
import core.praya.agarthalib.bridge.unity.Bridge;
import com.praya.agarthalib.utility.MathUtil;
import core.praya.agarthalib.enums.main.Slot;
import com.praya.agarthalib.utility.EntityUtil;
import io.lumine.xikage.mythicmobs.api.bukkit.events.MythicMobSpawnEvent;
import com.praya.myitems.MyItems;
import org.bukkit.event.Listener;
import com.praya.myitems.builder.handler.HandlerEvent;

public class ListenerMythicMobSpawn extends HandlerEvent implements Listener
{
    public ListenerMythicMobSpawn(final MyItems plugin) {
        super(plugin);
    }
    
    @EventHandler
    public void eventMythicMobSpawn(final MythicMobSpawnEvent event) {
        final GameManager gameManager = this.plugin.getGameManager();
        final ItemManager itemManager = gameManager.getItemManager();
        final ItemGeneratorManager itemGeneratorManager = gameManager.getItemGeneratorManager();
        final ItemSetManager itemSetManager = gameManager.getItemSetManager();
        final MythicMob mythicMob = event.getMobType();
        final Entity entity = event.getEntity();
        final List<String> equipment = (List<String>)mythicMob.getEquipment();
        if (EntityUtil.isLivingEntity(entity)) {
            final LivingEntity livingEntity = EntityUtil.parseLivingEntity(entity);
            for (final String lineEquipment : equipment) {
                final String[] args = lineEquipment.split(" ");
                final String textSlot = args[0];
                final Slot slot = Slot.get(textSlot);
                if (slot != null && args.length > 1) {
                    final String key = args[1];
                    if ((!key.equalsIgnoreCase("MyItems") && !key.equalsIgnoreCase("MyItem")) || args.length <= 2) {
                        continue;
                    }
                    final String category = args[2];
                    if (category.equalsIgnoreCase("Custom") || category.equalsIgnoreCase("Items") || category.equalsIgnoreCase("Item")) {
                        if (args.length <= 3) {
                            continue;
                        }
                        final String name = args[3];
                        final ItemStack item = itemManager.getItem(name);
                        if (item == null) {
                            continue;
                        }
                        double chance3;
                        if (args.length > 4) {
                            final String textChance = args[4];
                            if (textChance.contains("~")) {
                                final String[] componentsChance = textChance.split("~");
                                final String textChance2 = componentsChance[0];
                                final String textChance3 = componentsChance[1];
                                if (!MathUtil.isNumber(textChance2)) {
                                    continue;
                                }
                                if (!MathUtil.isNumber(textChance3)) {
                                    continue;
                                }
                                final double chance1 = MathUtil.parseDouble(textChance2);
                                final double chance2 = MathUtil.parseDouble(textChance3);
                                chance3 = MathUtil.valueBetween(chance1, chance2);
                            }
                            else {
                                if (!MathUtil.isNumber(textChance)) {
                                    continue;
                                }
                                chance3 = MathUtil.roundNumber(MathUtil.parseDouble(textChance));
                            }
                        }
                        else {
                            chance3 = 1.0;
                        }
                        if (!MathUtil.chanceOf(chance3, 1.0)) {
                            continue;
                        }
                        Bridge.getBridgeEquipment().setEquipment(livingEntity, item, slot);
                    }
                    else if (category.equalsIgnoreCase("Generator") || category.equalsIgnoreCase("Generate") || category.equalsIgnoreCase("Gen")) {
                        if (args.length <= 3) {
                            continue;
                        }
                        final String textGenerator = args[3];
                        final ItemGenerator itemGenerator = itemGeneratorManager.getItemGenerator(textGenerator);
                        if (itemGenerator == null) {
                            continue;
                        }
                        final SlotType slotType = slot.getType();
                        final Slot limit = slotType.equals((Object)SlotType.ARMOR) ? slot : null;
                        final ItemStack item2 = itemGenerator.generateItem(limit);
                        if (item2 == null) {
                            continue;
                        }
                        double chance6;
                        if (args.length > 4) {
                            final String textChance4 = args[4];
                            if (textChance4.contains("~")) {
                                final String[] componentsChance2 = textChance4.split("~");
                                final String textChance5 = componentsChance2[0];
                                final String textChance6 = componentsChance2[1];
                                if (!MathUtil.isNumber(textChance5)) {
                                    continue;
                                }
                                if (!MathUtil.isNumber(textChance6)) {
                                    continue;
                                }
                                final double chance4 = MathUtil.parseDouble(textChance5);
                                final double chance5 = MathUtil.parseDouble(textChance6);
                                chance6 = MathUtil.valueBetween(chance4, chance5);
                            }
                            else {
                                if (!MathUtil.isNumber(textChance4)) {
                                    continue;
                                }
                                chance6 = MathUtil.roundNumber(MathUtil.parseDouble(textChance4));
                            }
                        }
                        else {
                            chance6 = 1.0;
                        }
                        if (!MathUtil.chanceOf(chance6, 1.0)) {
                            continue;
                        }
                        Bridge.getBridgeEquipment().setEquipment(livingEntity, item2, slot);
                    }
                    else {
                        if ((!category.equalsIgnoreCase("Set") && !category.equalsIgnoreCase("SetItem") && !category.equalsIgnoreCase("ItemSet")) || args.length <= 3) {
                            continue;
                        }
                        final String componentID = args[3];
                        final ItemSet itemSet = itemSetManager.getItemSetByComponentID(componentID);
                        if (itemSet == null) {
                            continue;
                        }
                        final ItemStack item3 = itemSet.generateItem(componentID);
                        if (item3 == null) {
                            continue;
                        }
                        double chance9;
                        if (args.length > 4) {
                            final String textChance7 = args[4];
                            if (textChance7.contains("~")) {
                                final String[] componentsChance3 = textChance7.split("~");
                                final String textChance8 = componentsChance3[0];
                                final String textChance9 = componentsChance3[1];
                                if (!MathUtil.isNumber(textChance8)) {
                                    continue;
                                }
                                if (!MathUtil.isNumber(textChance9)) {
                                    continue;
                                }
                                final double chance7 = MathUtil.parseDouble(textChance8);
                                final double chance8 = MathUtil.parseDouble(textChance9);
                                chance9 = MathUtil.valueBetween(chance7, chance8);
                            }
                            else {
                                if (!MathUtil.isNumber(textChance7)) {
                                    continue;
                                }
                                chance9 = MathUtil.roundNumber(MathUtil.parseDouble(textChance7));
                            }
                        }
                        else {
                            chance9 = 1.0;
                        }
                        if (!MathUtil.chanceOf(chance9, 1.0)) {
                            continue;
                        }
                        Bridge.getBridgeEquipment().setEquipment(livingEntity, item3, slot);
                    }
                }
            }
        }
    }
}
