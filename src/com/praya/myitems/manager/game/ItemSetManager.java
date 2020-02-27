// 
// Decompiled by Procyon v0.5.36
// 

package com.praya.myitems.manager.game;

import org.bukkit.inventory.InventoryView;
import java.util.ListIterator;
import org.bukkit.inventory.PlayerInventory;
import java.util.Set;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.GameMode;
import com.praya.agarthalib.utility.TextUtil;
import java.util.Collections;
import org.bukkit.entity.Player;
import java.util.HashSet;
import org.bukkit.inventory.Inventory;
import api.praya.myitems.builder.ability.AbilityItemWeapon;
import api.praya.myitems.builder.item.ItemSetBonusEffectAbilityWeapon;
import api.praya.myitems.builder.item.ItemSetBonusEffect;
import api.praya.myitems.builder.item.ItemSetBonusEffectStats;
import api.praya.myitems.builder.item.ItemSetBonus;
import api.praya.myitems.builder.ability.AbilityWeapon;
import api.praya.myitems.builder.item.ItemSetBonusEffectEntity;
import core.praya.agarthalib.bridge.unity.Bridge;
import core.praya.agarthalib.enums.main.Slot;
import java.util.HashMap;
import org.bukkit.entity.LivingEntity;
import java.util.List;
import com.praya.agarthalib.utility.EquipmentUtil;
import org.bukkit.inventory.ItemStack;
import org.bukkit.ChatColor;
import com.praya.myitems.config.plugin.MainConfig;
import java.util.Iterator;
import api.praya.myitems.builder.item.ItemSetComponent;
import java.util.ArrayList;
import api.praya.myitems.builder.item.ItemSet;
import java.util.Collection;
import com.praya.myitems.MyItems;
import com.praya.myitems.config.game.ItemSetConfig;
import com.praya.myitems.builder.handler.HandlerManager;

public class ItemSetManager extends HandlerManager
{
    private final ItemSetConfig itemSetConfig;
    
    protected ItemSetManager(final MyItems plugin) {
        super(plugin);
        this.itemSetConfig = new ItemSetConfig(plugin);
    }
    
    public final ItemSetConfig getItemSetConfig() {
        return this.itemSetConfig;
    }
    
    public final Collection<String> getItemSetIDs() {
        return this.getItemSetConfig().getItemSetIDs();
    }
    
    public final Collection<ItemSet> getAllItemSet() {
        return this.getItemSetConfig().getAllItemSet();
    }
    
    public final ItemSet getItemSet(final String id) {
        return this.getItemSetConfig().getItemSet(id);
    }
    
    public final boolean isExists(final String id) {
        return this.getItemSet(id) != null;
    }
    
    public final Collection<String> getItemComponentIDs() {
        final Collection<String> itemComponentIDs = new ArrayList<String>();
        for (final ItemSet itemSet : this.getAllItemSet()) {
            for (final ItemSetComponent itemSetComponent : itemSet.getAllItemSetComponent()) {
                final String itemComponentID = itemSetComponent.getID();
                itemComponentIDs.add(itemComponentID);
            }
        }
        return itemComponentIDs;
    }
    
    public final ItemSetComponent getItemComponentByKeyLore(final String keyLore) {
        if (keyLore != null) {
            for (final ItemSet itemSet : this.getAllItemSet()) {
                for (final ItemSetComponent key : itemSet.getAllItemSetComponent()) {
                    if (key.getKeyLore().equalsIgnoreCase(keyLore)) {
                        return key;
                    }
                }
            }
        }
        return null;
    }
    
    public final ItemSetComponent getItemComponentByLore(final String lore) {
        if (lore != null) {
            final String keySetComponentSelf = MainConfig.KEY_SET_COMPONENT_SELF;
            if (lore.contains(keySetComponentSelf)) {
                final String[] partsComponent = lore.split(keySetComponentSelf);
                if (partsComponent.length > 1) {
                    final String componentKeyLore = ChatColor.stripColor(partsComponent[1]);
                    final ItemSetComponent itemSetComponent = this.getItemComponentByKeyLore(componentKeyLore);
                    return itemSetComponent;
                }
            }
        }
        return null;
    }
    
    public final ItemSetComponent getItemComponent(final ItemStack item) {
        if (item != null) {
            final List<String> lores = (List<String>)EquipmentUtil.getLores(item);
            for (final String lore : lores) {
                final ItemSetComponent itemSetComponent = this.getItemComponentByLore(lore);
                if (itemSetComponent != null) {
                    return itemSetComponent;
                }
            }
        }
        return null;
    }
    
    public final ItemSet getItemSetByComponentID(final String componentID) {
        if (componentID != null) {
            for (final ItemSet key : this.getAllItemSet()) {
                for (final ItemSetComponent itemSetComponent : key.getAllItemSetComponent()) {
                    if (itemSetComponent.getID().equalsIgnoreCase(componentID)) {
                        return key;
                    }
                }
            }
        }
        return null;
    }
    
    public final ItemSet getItemSetByKeyLore(final String keyLore) {
        if (keyLore != null) {
            for (final ItemSet key : this.getAllItemSet()) {
                for (final ItemSetComponent itemSetComponent : key.getAllItemSetComponent()) {
                    if (itemSetComponent.getKeyLore().equalsIgnoreCase(keyLore)) {
                        return key;
                    }
                }
            }
        }
        return null;
    }
    
    public final ItemSet getItemSetByLore(final String lore) {
        if (lore != null) {
            final String keySetComponentSelf = MainConfig.KEY_SET_COMPONENT_SELF;
            if (lore.contains(keySetComponentSelf)) {
                final String[] partsComponent = lore.split(keySetComponentSelf);
                if (partsComponent.length > 1) {
                    final String componentKeyLore = ChatColor.stripColor(partsComponent[1]);
                    final ItemSet itemSet = this.getItemSetByKeyLore(componentKeyLore);
                    return itemSet;
                }
            }
        }
        return null;
    }
    
    public final ItemSet getItemSet(final ItemStack item) {
        if (item != null) {
            final List<String> lores = (List<String>)EquipmentUtil.getLores(item);
            for (final String lore : lores) {
                final ItemSet itemSet = this.getItemSetByLore(lore);
                if (itemSet != null) {
                    return itemSet;
                }
            }
        }
        return null;
    }
    
    public final boolean isItemSet(final ItemStack item) {
        return this.getItemSet(item) != null;
    }
    
    public final HashMap<Slot, ItemSetComponent> getMapItemComponent(final LivingEntity entity) {
        return this.getMapItemComponent(entity, true);
    }
    
    public final HashMap<Slot, ItemSetComponent> getMapItemComponent(final LivingEntity entity, final boolean checkSlot) {
        final HashMap<Slot, ItemSetComponent> mapItemSetComponent = new HashMap<Slot, ItemSetComponent>();
        if (entity != null) {
            Slot[] values;
            for (int length = (values = Slot.values()).length, i = 0; i < length; ++i) {
                final Slot slot = values[i];
                final ItemStack item = Bridge.getBridgeEquipment().getEquipment(entity, slot);
                if (item != null) {
                    final ItemSetComponent itemSetComponent = this.getItemComponent(item);
                    if (itemSetComponent != null && itemSetComponent.isMatchSlot(slot)) {
                        mapItemSetComponent.put(slot, itemSetComponent);
                    }
                }
            }
        }
        return mapItemSetComponent;
    }
    
    public final HashMap<Slot, ItemSet> getMapItemSet(final LivingEntity entity) {
        return this.getMapItemSet(entity, true);
    }
    
    public final HashMap<Slot, ItemSet> getMapItemSet(final LivingEntity entity, final boolean checkSlot) {
        final HashMap<Slot, ItemSet> mapItemSet = new HashMap<Slot, ItemSet>();
        if (entity != null) {
            final HashMap<Slot, ItemSetComponent> mapItemSetComponent = this.getMapItemComponent(entity, checkSlot);
            for (final Slot slot : mapItemSetComponent.keySet()) {
                final ItemSetComponent itemSetComponent = mapItemSetComponent.get(slot);
                final ItemSet itemSet = itemSetComponent.getItemSet();
                if (itemSet != null) {
                    mapItemSet.put(slot, itemSet);
                }
            }
        }
        return mapItemSet;
    }
    
    public final HashMap<ItemSet, Integer> getMapItemSetTotal(final LivingEntity entity) {
        return this.getMapItemSetTotal(entity, true);
    }
    
    public final HashMap<ItemSet, Integer> getMapItemSetTotal(final LivingEntity entity, final boolean checkSlot) {
        final HashMap<ItemSet, Integer> mapItemSetTotal = new HashMap<ItemSet, Integer>();
        if (entity != null) {
            final HashMap<Slot, ItemSet> mapItemSet = this.getMapItemSet(entity, checkSlot);
            for (final Slot slot : mapItemSet.keySet()) {
                final ItemSet itemSet = mapItemSet.get(slot);
                if (mapItemSetTotal.containsKey(itemSet)) {
                    final int total = mapItemSetTotal.get(itemSet) + 1;
                    mapItemSetTotal.put(itemSet, total);
                }
                else {
                    mapItemSetTotal.put(itemSet, 1);
                }
            }
        }
        return mapItemSetTotal;
    }
    
    public final ItemSetBonusEffectEntity getItemSetBonusEffectEntity(final LivingEntity entity) {
        return this.getItemSetBonusEffectEntity(entity, true);
    }
    
    public final ItemSetBonusEffectEntity getItemSetBonusEffectEntity(final LivingEntity entity, final boolean checkSlot) {
        return this.getItemSetBonusEffectEntity(entity, checkSlot, true);
    }
    
    public final ItemSetBonusEffectEntity getItemSetBonusEffectEntity(final LivingEntity entity, final boolean checkSlot, final boolean checkChance) {
        final GameManager gameManager = this.plugin.getGameManager();
        final AbilityWeaponManager abilityWeaponManager = gameManager.getAbilityWeaponManager();
        final HashMap<AbilityWeapon, Integer> mapAbilityWeapon = new HashMap<AbilityWeapon, Integer>();
        double additionalDamage = 0.0;
        double percentDamage = 0.0;
        double penetration = 0.0;
        double pvpDamage = 0.0;
        double pveDamage = 0.0;
        double additionalDefense = 0.0;
        double percentDefense = 0.0;
        double health = 0.0;
        double healthRegen = 0.0;
        double staminaMax = 0.0;
        double staminaRegen = 0.0;
        double attackAoERadius = 0.0;
        double attackAoEDamage = 0.0;
        double pvpDefense = 0.0;
        double pveDefense = 0.0;
        double criticalChance = 0.0;
        double criticalDamage = 0.0;
        double blockAmount = 0.0;
        double blockRate = 0.0;
        double hitRate = 0.0;
        double dodgeRate = 0.0;
        if (entity != null) {
            final HashMap<ItemSet, Integer> mapItemSetTotal = this.getMapItemSetTotal(entity, checkSlot);
            for (final ItemSet itemSet : mapItemSetTotal.keySet()) {
                final int total = mapItemSetTotal.get(itemSet);
                for (final ItemSetBonus itemSetBonus : itemSet.getAllItemSetBonus()) {
                    final int amountID = itemSetBonus.getAmountID();
                    if (total >= amountID) {
                        final ItemSetBonusEffect itemSetBonusEffect = itemSetBonus.getEffect();
                        final ItemSetBonusEffectStats itemSetBonusEffectStats = itemSetBonusEffect.getEffectStats();
                        final ItemSetBonusEffectAbilityWeapon itemSetBonusEffectAbilityWeapon = itemSetBonusEffect.getEffectAbilityWeapon();
                        final Collection<AbilityItemWeapon> listAbilityItemWeapon = itemSetBonusEffectAbilityWeapon.getAllAbilityItemWeapon();
                        final HashMap<AbilityWeapon, Integer> mapAbilityWeaponBonus = abilityWeaponManager.getMapAbilityWeapon(listAbilityItemWeapon, checkChance);
                        additionalDamage += itemSetBonusEffectStats.getAdditionalDamage();
                        percentDamage += itemSetBonusEffectStats.getPercentDamage();
                        penetration += itemSetBonusEffectStats.getPenetration();
                        pvpDamage += itemSetBonusEffectStats.getPvPDamage();
                        pveDamage += itemSetBonusEffectStats.getPvEDamage();
                        additionalDefense += itemSetBonusEffectStats.getAdditionalDefense();
                        percentDefense += itemSetBonusEffectStats.getPercentDefense();
                        health += itemSetBonusEffectStats.getHealth();
                        healthRegen += itemSetBonusEffectStats.getHealthRegen();
                        staminaMax += itemSetBonusEffectStats.getStaminaMax();
                        staminaRegen += itemSetBonusEffectStats.getStaminaRegen();
                        attackAoERadius += itemSetBonusEffectStats.getAttackAoERadius();
                        attackAoEDamage += itemSetBonusEffectStats.getAttackAoEDamage();
                        pvpDefense += itemSetBonusEffectStats.getPvPDefense();
                        pveDefense += itemSetBonusEffectStats.getPvEDefense();
                        criticalChance += itemSetBonusEffectStats.getCriticalChance();
                        criticalDamage += itemSetBonusEffectStats.getCriticalDamage();
                        blockAmount += itemSetBonusEffectStats.getBlockAmount();
                        blockRate += itemSetBonusEffectStats.getBlockRate();
                        hitRate += itemSetBonusEffectStats.getHitRate();
                        dodgeRate += itemSetBonusEffectStats.getDodgeRate();
                        for (final AbilityWeapon abilityWeapon : mapAbilityWeaponBonus.keySet()) {
                            final int grade = mapAbilityWeaponBonus.get(abilityWeapon);
                            if (mapAbilityWeapon.containsKey(abilityWeapon)) {
                                final int totalGrade = mapAbilityWeapon.get(abilityWeapon) + grade;
                                mapAbilityWeapon.put(abilityWeapon, totalGrade);
                            }
                            else {
                                mapAbilityWeapon.put(abilityWeapon, grade);
                            }
                        }
                    }
                }
            }
        }
        final ItemSetBonusEffectStats itemSetBonusEffectStats2 = new ItemSetBonusEffectStats(additionalDamage, percentDamage, penetration, pvpDamage, pveDamage, additionalDefense, percentDefense, health, healthRegen, staminaMax, staminaRegen, attackAoERadius, attackAoEDamage, pvpDefense, pveDefense, criticalChance, criticalDamage, blockAmount, blockRate, hitRate, dodgeRate);
        final ItemSetBonusEffectEntity itemSetBonusEffectEntity = new ItemSetBonusEffectEntity(itemSetBonusEffectStats2, mapAbilityWeapon);
        return itemSetBonusEffectEntity;
    }
    
    public final void updateItemSet(final LivingEntity entity) {
        this.updateItemSet(entity, true);
    }
    
    public final void updateItemSet(final LivingEntity entity, final boolean checkPlayerInventory) {
        this.updateItemSet(entity, checkPlayerInventory, null);
    }
    
    public final void updateItemSet(final LivingEntity entity, final boolean checkPlayerInventory, final Inventory inventory) {
        final MainConfig mainConfig = MainConfig.getInstance();
        if (entity != null) {
            final String divider = "\n";
            final String keyLine = MainConfig.KEY_SET_LINE;
            final String keySetComponentSelf = MainConfig.KEY_SET_COMPONENT_SELF;
            final String keySetComponentOther = MainConfig.KEY_SET_COMPONENT_OTHER;
            final String loreBonusActive = mainConfig.getSetLoreBonusActive();
            final String loreBonusInactive = mainConfig.getSetLoreBonusInactive();
            final String loreComponentActive = mainConfig.getSetLoreComponentActive();
            final String loreComponentInactive = mainConfig.getSetLoreComponentInactive();
            final HashMap<Slot, ItemSetComponent> mapItemSetComponent = this.getMapItemComponent(entity);
            final HashMap<ItemSet, Integer> mapItemSetTotal = new HashMap<ItemSet, Integer>();
            final Collection<ItemSetComponent> allItemSetComponent = mapItemSetComponent.values();
            final Set<ItemStack> contents = new HashSet<ItemStack>();
            Slot[] values;
            for (int length = (values = Slot.values()).length, i = 0; i < length; ++i) {
                final Slot slot = values[i];
                final ItemStack item = Bridge.getBridgeEquipment().getEquipment(entity, slot);
                if (item != null) {
                    contents.add(item);
                    if (mapItemSetComponent.containsKey(slot)) {
                        final ItemSetComponent itemSetComponent = mapItemSetComponent.get(slot);
                        final ItemSet itemSet = itemSetComponent.getItemSet();
                        if (itemSet != null) {
                            if (mapItemSetTotal.containsKey(itemSet)) {
                                final int total = mapItemSetTotal.get(itemSet) + 1;
                                mapItemSetTotal.put(itemSet, total);
                            }
                            else {
                                mapItemSetTotal.put(itemSet, 1);
                            }
                        }
                    }
                }
            }
            if (entity instanceof Player) {
                if (checkPlayerInventory) {
                    final Player player = (Player)entity;
                    final PlayerInventory playerInventory = player.getInventory();
                    final ItemStack itemCursor = player.getItemOnCursor();
                    if (itemCursor != null) {
                        contents.add(itemCursor);
                    }
                    ItemStack[] contents2;
                    for (int length2 = (contents2 = playerInventory.getContents()).length, j = 0; j < length2; ++j) {
                        final ItemStack content = contents2[j];
                        if (content != null) {
                            contents.add(content);
                        }
                    }
                }
                if (inventory != null) {
                    ItemStack[] contents3;
                    for (int length3 = (contents3 = inventory.getContents()).length, k = 0; k < length3; ++k) {
                        final ItemStack content2 = contents3[k];
                        if (content2 != null) {
                            contents.add(content2);
                        }
                    }
                }
            }
            for (final ItemStack item2 : contents) {
                final ItemSetComponent itemSetComponent2 = this.getItemComponent(item2);
                if (itemSetComponent2 != null) {
                    final ItemSet itemSet2 = itemSetComponent2.getItemSet();
                    if (itemSet2 == null) {
                        continue;
                    }
                    final String name = itemSet2.getName();
                    final int total2 = mapItemSetTotal.containsKey(itemSet2) ? mapItemSetTotal.get(itemSet2) : 0;
                    final int maxComponent = itemSet2.getTotalComponent();
                    final List<String> lores = (List<String>)EquipmentUtil.getLores(item2);
                    final List<String> loresBonus = new ArrayList<String>();
                    final List<String> loresComponent = new ArrayList<String>();
                    final List<Integer> bonusAmountIDs = new ArrayList<Integer>(itemSet2.getBonusAmountIDs());
                    final Iterator<String> iteratorLores = lores.iterator();
                    final HashMap<String, String> mapPlaceholder = new HashMap<String, String>();
                    List<String> loresSet = mainConfig.getSetFormat();
                    Collections.sort(bonusAmountIDs);
                    while (iteratorLores.hasNext()) {
                        final String lore = iteratorLores.next();
                        if (lore.contains(keyLine)) {
                            iteratorLores.remove();
                        }
                    }
                    for (final ItemSetComponent partComponent : itemSet2.getAllItemSetComponent()) {
                        final String partComponentID = partComponent.getID();
                        final String keyLore = partComponent.getKeyLore();
                        String formatComponent = mainConfig.getSetFormatComponent();
                        String replacementKeyLore;
                        if (allItemSetComponent.contains(partComponent)) {
                            formatComponent = String.valueOf(loreComponentActive) + formatComponent;
                            if (partComponent.equals(itemSetComponent2)) {
                                replacementKeyLore = String.valueOf(keySetComponentSelf) + loreComponentActive + keyLore + keySetComponentSelf + loreComponentActive;
                            }
                            else {
                                replacementKeyLore = String.valueOf(keySetComponentOther) + loreComponentActive + keyLore + keySetComponentOther + loreComponentActive;
                            }
                        }
                        else {
                            formatComponent = String.valueOf(loreComponentInactive) + formatComponent;
                            if (partComponent.equals(itemSetComponent2)) {
                                replacementKeyLore = String.valueOf(keySetComponentSelf) + loreComponentInactive + keyLore + keySetComponentSelf + loreComponentInactive;
                            }
                            else {
                                replacementKeyLore = String.valueOf(keySetComponentOther) + loreComponentInactive + keyLore + keySetComponentOther + loreComponentInactive;
                            }
                        }
                        mapPlaceholder.clear();
                        mapPlaceholder.put("item_set_component_id", partComponentID);
                        mapPlaceholder.put("item_set_component_keylore", replacementKeyLore);
                        formatComponent = TextUtil.placeholder((HashMap)mapPlaceholder, formatComponent, "<", ">");
                        loresComponent.add(formatComponent);
                    }
                    for (final int bonusAmountID : bonusAmountIDs) {
                        final ItemSetBonus partBonus = itemSet2.getItemSetBonus(bonusAmountID);
                        final List<String> listDescription = partBonus.getDescription();
                        for (final String description : listDescription) {
                            String formatBonus = mainConfig.getSetFormatBonus();
                            if (total2 >= bonusAmountID) {
                                formatBonus = String.valueOf(loreBonusActive) + formatBonus;
                            }
                            else {
                                formatBonus = String.valueOf(loreBonusInactive) + formatBonus;
                            }
                            mapPlaceholder.clear();
                            mapPlaceholder.put("item_set_bonus_amount", String.valueOf(bonusAmountID));
                            mapPlaceholder.put("item_set_bonus_description", String.valueOf(description));
                            formatBonus = TextUtil.placeholder((HashMap)mapPlaceholder, formatBonus, "<", ">");
                            loresBonus.add(formatBonus);
                        }
                    }
                    final String lineBonus = TextUtil.convertListToString((List)loresBonus, "\n");
                    final String lineComponent = TextUtil.convertListToString((List)loresComponent, "\n");
                    mapPlaceholder.clear();
                    mapPlaceholder.put("item_set_name", name);
                    mapPlaceholder.put("item_set_total", String.valueOf(total2));
                    mapPlaceholder.put("item_set_max", String.valueOf(maxComponent));
                    mapPlaceholder.put("list_item_set_component", lineComponent);
                    mapPlaceholder.put("list_item_set_bonus", lineBonus);
                    loresSet = (List<String>)TextUtil.placeholder((HashMap)mapPlaceholder, (List)loresSet, "<", ">");
                    loresSet = (List<String>)TextUtil.expandList((List)loresSet, "\n");
                    final ListIterator<String> iteratorLoresSet = loresSet.listIterator();
                    while (iteratorLoresSet.hasNext()) {
                        final String lore2 = String.valueOf(keyLine) + iteratorLoresSet.next();
                        iteratorLoresSet.set(lore2);
                    }
                    lores.addAll(loresSet);
                    EquipmentUtil.setLores(item2, (List)lores);
                }
            }
            if (entity instanceof Player) {
                final Player player = (Player)entity;
                final GameMode gameMode = player.getGameMode();
                final InventoryView inventoryView = player.getOpenInventory();
                final InventoryType inventoryType = inventoryView.getType();
                if (!gameMode.equals((Object)GameMode.CREATIVE) || !inventoryType.equals((Object)InventoryType.CREATIVE)) {
                    player.updateInventory();
                }
            }
        }
    }
}
