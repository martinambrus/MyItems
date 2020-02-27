// 
// Decompiled by Procyon v0.5.36
// 

package com.praya.myitems.menu;

import java.util.Iterator;
import api.praya.myitems.builder.element.ElementBoostStats;
import api.praya.myitems.builder.item.ItemSetBonusEffectStats;
import api.praya.myitems.builder.item.ItemSetBonusEffectEntity;
import api.praya.myitems.builder.item.ItemStatsArmor;
import api.praya.myitems.builder.item.ItemStatsWeapon;
import org.bukkit.inventory.ItemStack;
import com.praya.myitems.manager.plugin.LanguageManager;
import com.praya.myitems.manager.player.PlayerItemStatsManager;
import com.praya.myitems.manager.game.ItemSetManager;
import com.praya.myitems.manager.game.ElementManager;
import com.praya.myitems.manager.game.AbilityWeaponManager;
import com.praya.myitems.manager.player.PlayerManager;
import com.praya.myitems.manager.game.GameManager;
import com.praya.myitems.manager.plugin.PluginManager;
import core.praya.agarthalib.enums.branch.FlagEnum;
import com.praya.agarthalib.utility.EquipmentUtil;
import core.praya.agarthalib.enums.branch.MaterialEnum;
import java.util.List;
import com.praya.agarthalib.utility.TextUtil;
import core.praya.agarthalib.enums.main.RomanNumber;
import api.praya.myitems.builder.ability.AbilityWeapon;
import com.praya.agarthalib.utility.MathUtil;
import java.util.ArrayList;
import core.praya.agarthalib.enums.main.SlotType;
import core.praya.agarthalib.enums.main.Slot;
import core.praya.agarthalib.bridge.unity.Bridge;
import java.util.HashMap;
import core.praya.agarthalib.builder.menu.MenuSlot;
import org.bukkit.entity.LivingEntity;
import core.praya.agarthalib.builder.menu.MenuGUI;
import core.praya.agarthalib.builder.menu.MenuSlotAction;
import core.praya.agarthalib.builder.menu.Menu;
import org.bukkit.entity.Player;
import com.praya.myitems.MyItems;
import core.praya.agarthalib.builder.menu.MenuExecutor;
import com.praya.myitems.builder.handler.HandlerMenu;

public class MenuStats extends HandlerMenu implements MenuExecutor
{
    public MenuStats(final MyItems plugin) {
        super(plugin);
    }
    
    public void onClick(final Player player, final Menu menu, final MenuSlotAction.ActionType actionType, final String... args) {
    }
    
    public final void updateStatsMenu(final MenuGUI menuGUI, final Player player) {
        final PluginManager pluginManager = this.plugin.getPluginManager();
        final GameManager gameManager = this.plugin.getGameManager();
        final PlayerManager playerManager = this.plugin.getPlayerManager();
        final AbilityWeaponManager abilityWeaponManager = gameManager.getAbilityWeaponManager();
        final ElementManager elementManager = gameManager.getElementManager();
        final ItemSetManager itemSetManager = gameManager.getItemSetManager();
        final PlayerItemStatsManager playerItemStatsManager = playerManager.getPlayerItemStatsManager();
        final LanguageManager lang = pluginManager.getLanguageManager();
        final String divider = "\n";
        final String headerSlotHelmet = lang.getText((LivingEntity)player, "Menu_Item_Header_Stats_Slot_Helmet");
        final String headerSlotChestplate = lang.getText((LivingEntity)player, "Menu_Item_Header_Stats_Slot_Chestplate");
        final String headerSlotLeggings = lang.getText((LivingEntity)player, "Menu_Item_Header_Stats_Slot_Leggings");
        final String headerSlotBoots = lang.getText((LivingEntity)player, "Menu_Item_Header_Stats_Slot_Boots");
        final String headerSlotMainHand = lang.getText((LivingEntity)player, "Menu_Item_Header_Stats_Slot_MainHand");
        final String headerSlotOffHand = lang.getText((LivingEntity)player, "Menu_Item_Header_Stats_Slot_OffHand");
        final String headerInformation = lang.getText((LivingEntity)player, "Menu_Item_Header_Stats_Information");
        final String headerAttack = lang.getText((LivingEntity)player, "Menu_Item_Header_Stats_Attack");
        final String headerDefense = lang.getText((LivingEntity)player, "Menu_Item_Header_Stats_Defense");
        final String headerAbility = lang.getText((LivingEntity)player, "Menu_Item_Header_Stats_Ability");
        final String headerElement = lang.getText((LivingEntity)player, "Menu_Item_Header_Stats_Element");
        final MenuGUI.SlotCell cellEquipmentHelmet = MenuGUI.SlotCell.C2;
        final MenuGUI.SlotCell cellEquipmentChestplate = MenuGUI.SlotCell.C3;
        final MenuGUI.SlotCell cellEquipmentLeggings = MenuGUI.SlotCell.C4;
        final MenuGUI.SlotCell cellEquipmentBoots = MenuGUI.SlotCell.C5;
        final MenuGUI.SlotCell cellEquipmentMainHand = MenuGUI.SlotCell.E2;
        final MenuGUI.SlotCell cellEquipmentOffHand = MenuGUI.SlotCell.E3;
        final MenuGUI.SlotCell cellInformation = MenuGUI.SlotCell.E5;
        final MenuGUI.SlotCell cellAttack = MenuGUI.SlotCell.G2;
        final MenuGUI.SlotCell cellDefense = MenuGUI.SlotCell.G3;
        final MenuGUI.SlotCell cellAbility = MenuGUI.SlotCell.G4;
        final MenuGUI.SlotCell cellElement = MenuGUI.SlotCell.G5;
        final MenuSlot menuSlotEquipmentHelmet = new MenuSlot(cellEquipmentHelmet.getIndex());
        final MenuSlot menuSlotEquipmentChestplate = new MenuSlot(cellEquipmentChestplate.getIndex());
        final MenuSlot menuSlotEquipmentLeggings = new MenuSlot(cellEquipmentLeggings.getIndex());
        final MenuSlot menuSlotEquipmentBoots = new MenuSlot(cellEquipmentBoots.getIndex());
        final MenuSlot menuSlotEquipmentMainHand = new MenuSlot(cellEquipmentMainHand.getIndex());
        final MenuSlot menuSlotEquipmentOffHand = new MenuSlot(cellEquipmentOffHand.getIndex());
        final MenuSlot menuSlotInformation = new MenuSlot(cellInformation.getIndex());
        final MenuSlot menuSlotAttack = new MenuSlot(cellAttack.getIndex());
        final MenuSlot menuSlotDefense = new MenuSlot(cellDefense.getIndex());
        final MenuSlot menuSlotAbility = new MenuSlot(cellAbility.getIndex());
        final MenuSlot menuSlotElement = new MenuSlot(cellElement.getIndex());
        final HashMap<String, String> map = new HashMap<String, String>();
        final HashMap<String, String> mapData = new HashMap<String, String>();
        final ItemStack itemEquipmentHelmet = Bridge.getBridgeEquipment().getEquipment(player, Slot.HELMET);
        final ItemStack itemEquipmentChestplate = Bridge.getBridgeEquipment().getEquipment(player, Slot.CHESTPLATE);
        final ItemStack itemEquipmentLeggings = Bridge.getBridgeEquipment().getEquipment(player, Slot.LEGGINGS);
        final ItemStack itemEquipmentBoots = Bridge.getBridgeEquipment().getEquipment(player, Slot.BOOTS);
        final ItemStack itemEquipmentMainHand = Bridge.getBridgeEquipment().getEquipment(player, Slot.MAINHAND);
        final ItemStack itemEquipmentOffHand = Bridge.getBridgeEquipment().getEquipment(player, Slot.OFFHAND);
        final ItemStatsWeapon itemStatsWeapon = playerItemStatsManager.getItemStatsWeapon(player);
        final ItemStatsArmor itemStatsArmor = playerItemStatsManager.getItemStatsArmor(player);
        final HashMap<AbilityWeapon, Integer> mapAbilityWeapon = abilityWeaponManager.getMapAbilityWeapon((LivingEntity)player);
        final HashMap<String, Double> mapElementWeapon = elementManager.getMapElement((LivingEntity)player, SlotType.WEAPON, false);
        final HashMap<String, Double> mapElementArmor = elementManager.getMapElement((LivingEntity)player, SlotType.ARMOR, false);
        final ItemSetBonusEffectEntity itemSetBonusEffectEntity = itemSetManager.getItemSetBonusEffectEntity((LivingEntity)player, true, false);
        final ItemSetBonusEffectStats itemSetBonusEffectStats = itemSetBonusEffectEntity.getEffectStats();
        final ElementBoostStats elementWeapon = elementManager.getElementBoostStats(mapElementWeapon);
        final double elementAdditionalDamage = elementWeapon.getBaseAdditionalDamage();
        final double elementPercentDamage = elementWeapon.getBasePercentDamage();
        final double abilityBaseBonusDamage = abilityWeaponManager.getTotalBaseBonusDamage(mapAbilityWeapon);
        final double abilityBasePercentDamage = abilityWeaponManager.getTotalBasePercentDamage(mapAbilityWeapon);
        final double abilityCastBonusDamage = abilityWeaponManager.getTotalCastBonusDamage(mapAbilityWeapon);
        final double abilityCastPercentDamage = abilityWeaponManager.getTotalCastPercentDamage(mapAbilityWeapon);
        final double totalDamageMin = (itemStatsWeapon.getTotalDamageMin() + itemSetBonusEffectStats.getAdditionalDamage()) * ((100.0 + itemSetBonusEffectStats.getPercentDamage()) / 100.0);
        final double totalDamageMax = (itemStatsWeapon.getTotalDamageMax() + itemSetBonusEffectStats.getAdditionalDamage()) * ((100.0 + itemSetBonusEffectStats.getPercentDamage()) / 100.0);
        final double totalPenetration = itemStatsWeapon.getTotalPenetration() + itemSetBonusEffectStats.getPenetration();
        final double totalPvPDamage = itemStatsWeapon.getTotalPvPDamage() + itemSetBonusEffectStats.getPvPDamage();
        final double totalPvEDamage = itemStatsWeapon.getTotalPvEDamage() + itemSetBonusEffectStats.getPvEDamage();
        final double totalCriticalChance = itemStatsWeapon.getTotalCriticalChance() + itemSetBonusEffectStats.getCriticalChance();
        final double totalCriticalDamage = itemStatsWeapon.getTotalCriticalDamage() + itemSetBonusEffectStats.getCriticalDamage();
        final double totalAttackAoERadius = itemStatsWeapon.getTotalAttackAoERadius() + itemSetBonusEffectStats.getAttackAoERadius();
        final double totalAttackAoEDamage = itemStatsWeapon.getTotalAttackAoEDamage() + itemSetBonusEffectStats.getAttackAoEDamage();
        final double totalHitRate = itemStatsWeapon.getTotalHitRate() + itemSetBonusEffectStats.getHitRate();
        final double totalDefense = (itemStatsArmor.getTotalDefense() + itemSetBonusEffectStats.getAdditionalDefense()) * ((100.0 + itemSetBonusEffectStats.getPercentDefense()) / 100.0);
        final double totalPvPDefense = itemStatsArmor.getTotalPvPDefense() + itemSetBonusEffectStats.getPvPDefense();
        final double totalPvEDefense = itemStatsArmor.getTotalPvEDefense() + itemSetBonusEffectStats.getPvEDefense();
        final double totalHealth = itemStatsArmor.getTotalHealth() + itemSetBonusEffectStats.getHealth();
        final double totalHealthRegen = itemStatsArmor.getTotalHealthRegen() + itemSetBonusEffectStats.getHealthRegen();
        final double totalStaminaMax = itemStatsArmor.getTotalStaminaMax() + itemSetBonusEffectStats.getStaminaMax();
        final double totalStaminaRegen = itemStatsArmor.getTotalStaminaRegen() + itemSetBonusEffectStats.getStaminaRegen();
        final double totalBlockAmount = itemStatsArmor.getTotalBlockAmount() + itemSetBonusEffectStats.getBlockAmount();
        final double totalBlockRate = itemStatsArmor.getTotalBlockRate() + itemSetBonusEffectStats.getBlockRate();
        final double totalDodgeRate = itemStatsArmor.getTotalDodgeRate() + itemSetBonusEffectStats.getDodgeRate();
        final List<String> loreAbilityDataWeapon = new ArrayList<String>();
        final List<String> loreElementDataWeapon = new ArrayList<String>();
        final List<String> loreElementDataArmor = new ArrayList<String>();
        String loreDamage = String.valueOf(MathUtil.roundNumber(totalDamageMin));
        List<String> loreInformation = lang.getListText((LivingEntity)player, "Menu_Item_Lores_Stats_Information");
        List<String> loreAttack = lang.getListText((LivingEntity)player, "Menu_Item_Lores_Stats_Attack");
        List<String> loreDefense = lang.getListText((LivingEntity)player, "Menu_Item_Lores_Stats_Defense");
        List<String> loreAbility = lang.getListText((LivingEntity)player, "Menu_Item_Lores_Stats_Ability");
        List<String> loreElement = lang.getListText((LivingEntity)player, "Menu_Item_Lores_Stats_Element");
        for (final AbilityWeapon abilityWeaponItemSet : itemSetBonusEffectEntity.getAllAbilityWeapon()) {
            final int grade = itemSetBonusEffectEntity.getGradeAbilityWeapon(abilityWeaponItemSet);
            final int maxGrade = abilityWeaponItemSet.getMaxGrade();
            if (mapAbilityWeapon.containsKey(abilityWeaponItemSet)) {
                final int totalGrade = mapAbilityWeapon.get(abilityWeaponItemSet) + grade;
                mapAbilityWeapon.put(abilityWeaponItemSet, Math.min(maxGrade, totalGrade));
            }
            else {
                mapAbilityWeapon.put(abilityWeaponItemSet, Math.min(maxGrade, grade));
            }
        }
        for (final AbilityWeapon abilityWeapon : mapAbilityWeapon.keySet()) {
            final String abilityType = abilityWeapon.getID();
            final int abilityGrade = mapAbilityWeapon.get(abilityWeapon);
            String formatAbilityData = lang.getText((LivingEntity)player, "Menu_Item_Format_Stats_Ability_Data");
            mapData.clear();
            mapData.put("ability_type", abilityType);
            mapData.put("ability_grade", RomanNumber.getRomanNumber(abilityGrade));
            formatAbilityData = TextUtil.placeholder((HashMap)mapData, formatAbilityData);
            loreAbilityDataWeapon.add(formatAbilityData);
        }
        for (final String keyElement : mapElementWeapon.keySet()) {
            final double elementValue = mapElementWeapon.get(keyElement);
            String formatElementData = lang.getText((LivingEntity)player, "Menu_Item_Format_Stats_Element_Data");
            mapData.clear();
            mapData.put("element_type", keyElement);
            mapData.put("element_value", String.valueOf(elementValue));
            formatElementData = TextUtil.placeholder((HashMap)mapData, formatElementData);
            loreElementDataWeapon.add(formatElementData);
        }
        for (final String keyElement : mapElementArmor.keySet()) {
            final double elementValue = mapElementArmor.get(keyElement);
            String formatElementData = lang.getText((LivingEntity)player, "Menu_Item_Format_Stats_Element_Data");
            mapData.clear();
            mapData.put("element_type", keyElement);
            mapData.put("element_value", String.valueOf(elementValue));
            formatElementData = TextUtil.placeholder((HashMap)mapData, formatElementData);
            loreElementDataArmor.add(formatElementData);
        }
        if (totalDamageMin != totalDamageMax) {
            loreDamage = lang.getText((LivingEntity)player, "Menu_Item_Format_Stats_Attack_Damage_Range");
            mapData.clear();
            mapData.put("stats_damage_min", String.valueOf(MathUtil.roundNumber(totalDamageMin)));
            mapData.put("stats_damage_max", String.valueOf(MathUtil.roundNumber(totalDamageMax)));
            loreDamage = TextUtil.placeholder((HashMap)mapData, loreDamage);
        }
        final String lineAbilityDataWeapon = TextUtil.convertListToString((List)loreAbilityDataWeapon, "\n");
        final String lineElementDataWeapon = TextUtil.convertListToString((List)loreElementDataWeapon, "\n");
        final String lineElementDataArmor = TextUtil.convertListToString((List)loreElementDataArmor, "\n");
        final String lineAbilityEmpty = lang.getText((LivingEntity)player, "Menu_Item_Format_Stats_Ability_Empty");
        final String lineElementEmpty = lang.getText((LivingEntity)player, "Menu_Item_Format_Stats_Element_Empty");
        map.put("stats_damage", loreDamage);
        map.put("stats_penetration", String.valueOf(MathUtil.roundNumber(totalPenetration)));
        map.put("stats_pvp_damage", String.valueOf(MathUtil.roundNumber(totalPvPDamage)));
        map.put("stats_pve_damage", String.valueOf(MathUtil.roundNumber(totalPvEDamage)));
        map.put("stats_critical_chance", String.valueOf(MathUtil.roundNumber(totalCriticalChance)));
        map.put("stats_critical_damage", String.valueOf(MathUtil.roundNumber(totalCriticalDamage)));
        map.put("stats_aoe_radius", String.valueOf(MathUtil.roundNumber(totalAttackAoERadius)));
        map.put("stats_aoe_damage", String.valueOf(MathUtil.roundNumber(totalAttackAoEDamage)));
        map.put("stats_hit_rate", String.valueOf(MathUtil.roundNumber(totalHitRate)));
        map.put("stats_defense", String.valueOf(MathUtil.roundNumber(totalDefense)));
        map.put("stats_pvp_defense", String.valueOf(MathUtil.roundNumber(totalPvPDefense)));
        map.put("stats_pve_defense", String.valueOf(MathUtil.roundNumber(totalPvEDefense)));
        map.put("stats_health", String.valueOf(MathUtil.roundNumber(totalHealth)));
        map.put("stats_health_regen", String.valueOf(MathUtil.roundNumber(totalHealthRegen)));
        map.put("stats_stamina_max", String.valueOf(MathUtil.roundNumber(totalStaminaMax)));
        map.put("stats_stamina_regen", String.valueOf(MathUtil.roundNumber(totalStaminaRegen)));
        map.put("stats_block_amount", String.valueOf(MathUtil.roundNumber(totalBlockAmount)));
        map.put("stats_block_rate", String.valueOf(MathUtil.roundNumber(totalBlockRate)));
        map.put("stats_dodge_rate", String.valueOf(MathUtil.roundNumber(totalDodgeRate)));
        map.put("ability_base_additional_damage", String.valueOf(MathUtil.roundNumber(abilityBaseBonusDamage)));
        map.put("ability_base_percent_damage", String.valueOf(MathUtil.roundNumber(abilityBasePercentDamage)));
        map.put("ability_cast_additional_damage", String.valueOf(MathUtil.roundNumber(abilityCastBonusDamage)));
        map.put("ability_cast_percent_damage", String.valueOf(MathUtil.roundNumber(abilityCastPercentDamage)));
        map.put("element_additional_damage", String.valueOf(MathUtil.roundNumber(elementAdditionalDamage)));
        map.put("element_percent_damage", String.valueOf(MathUtil.roundNumber(elementPercentDamage)));
        map.put("ability_data", mapAbilityWeapon.isEmpty() ? lineAbilityEmpty : lineAbilityDataWeapon);
        map.put("ability_data_weapon", mapAbilityWeapon.isEmpty() ? lineAbilityEmpty : lineAbilityDataWeapon);
        map.put("element_data_weapon", mapElementWeapon.isEmpty() ? lineElementEmpty : lineElementDataWeapon);
        map.put("element_data_armor", mapElementArmor.isEmpty() ? lineElementEmpty : lineElementDataArmor);
        loreInformation = (List<String>)TextUtil.placeholder((HashMap)map, (List)loreInformation);
        loreAttack = (List<String>)TextUtil.placeholder((HashMap)map, (List)loreAttack);
        loreDefense = (List<String>)TextUtil.placeholder((HashMap)map, (List)loreDefense);
        loreAbility = (List<String>)TextUtil.placeholder((HashMap)map, (List)loreAbility);
        loreElement = (List<String>)TextUtil.placeholder((HashMap)map, (List)loreElement);
        loreInformation = (List<String>)TextUtil.expandList((List)loreInformation, "\n");
        loreAttack = (List<String>)TextUtil.expandList((List)loreAttack, "\n");
        loreDefense = (List<String>)TextUtil.expandList((List)loreDefense, "\n");
        loreAbility = (List<String>)TextUtil.expandList((List)loreAbility, "\n");
        loreElement = (List<String>)TextUtil.expandList((List)loreElement, "\n");
        final ItemStack itemSlotHelmet = EquipmentUtil.createItem(MaterialEnum.RED_STAINED_GLASS_PANE, headerSlotHelmet, 1);
        final ItemStack itemSlotChestplate = EquipmentUtil.createItem(MaterialEnum.RED_STAINED_GLASS_PANE, headerSlotChestplate, 1);
        final ItemStack itemSlotLeggings = EquipmentUtil.createItem(MaterialEnum.RED_STAINED_GLASS_PANE, headerSlotLeggings, 1);
        final ItemStack itemSlotBoots = EquipmentUtil.createItem(MaterialEnum.RED_STAINED_GLASS_PANE, headerSlotBoots, 1);
        final ItemStack itemSlotMainHand = EquipmentUtil.createItem(MaterialEnum.RED_STAINED_GLASS_PANE, headerSlotMainHand, 1);
        final ItemStack itemSlotOffHand = EquipmentUtil.createItem(MaterialEnum.RED_STAINED_GLASS_PANE, headerSlotOffHand, 1);
        final ItemStack itemInformation = EquipmentUtil.createItem(MaterialEnum.SIGN, headerInformation, 1, (List)loreInformation);
        final ItemStack itemAttack = EquipmentUtil.createItem(MaterialEnum.IRON_SWORD, headerAttack, 1, (List)loreAttack);
        final ItemStack itemDefense = EquipmentUtil.createItem(MaterialEnum.IRON_CHESTPLATE, headerDefense, 1, (List)loreDefense);
        final ItemStack itemAbility = EquipmentUtil.createItem(MaterialEnum.BLAZE_POWDER, headerAbility, 1, (List)loreAbility);
        final ItemStack itemElement = EquipmentUtil.createItem(MaterialEnum.MAGMA_CREAM, headerElement, 1, (List)loreElement);
        EquipmentUtil.addFlag(itemAttack, new FlagEnum[] { FlagEnum.HIDE_ATTRIBUTES });
        EquipmentUtil.addFlag(itemDefense, new FlagEnum[] { FlagEnum.HIDE_ATTRIBUTES });
        menuSlotEquipmentHelmet.setItem(EquipmentUtil.isSolid(itemEquipmentHelmet) ? itemEquipmentHelmet : itemSlotHelmet);
        menuSlotEquipmentChestplate.setItem(EquipmentUtil.isSolid(itemEquipmentChestplate) ? itemEquipmentChestplate : itemSlotChestplate);
        menuSlotEquipmentLeggings.setItem(EquipmentUtil.isSolid(itemEquipmentLeggings) ? itemEquipmentLeggings : itemSlotLeggings);
        menuSlotEquipmentBoots.setItem(EquipmentUtil.isSolid(itemEquipmentBoots) ? itemEquipmentBoots : itemSlotBoots);
        menuSlotEquipmentMainHand.setItem(EquipmentUtil.isSolid(itemEquipmentMainHand) ? itemEquipmentMainHand : itemSlotMainHand);
        menuSlotEquipmentOffHand.setItem(EquipmentUtil.isSolid(itemEquipmentOffHand) ? itemEquipmentOffHand : itemSlotOffHand);
        menuSlotInformation.setItem(itemInformation);
        menuSlotAttack.setItem(itemAttack);
        menuSlotDefense.setItem(itemDefense);
        menuSlotAbility.setItem(itemAbility);
        menuSlotElement.setItem(itemElement);
        menuGUI.setMenuSlot(menuSlotEquipmentHelmet);
        menuGUI.setMenuSlot(menuSlotEquipmentChestplate);
        menuGUI.setMenuSlot(menuSlotEquipmentLeggings);
        menuGUI.setMenuSlot(menuSlotEquipmentBoots);
        menuGUI.setMenuSlot(menuSlotEquipmentMainHand);
        menuGUI.setMenuSlot(menuSlotEquipmentOffHand);
        menuGUI.setMenuSlot(menuSlotInformation);
        menuGUI.setMenuSlot(menuSlotAttack);
        menuGUI.setMenuSlot(menuSlotDefense);
        menuGUI.setMenuSlot(menuSlotAbility);
        menuGUI.setMenuSlot(menuSlotElement);
        player.updateInventory();
    }
}
