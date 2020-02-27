// 
// Decompiled by Procyon v0.5.36
// 

package com.praya.myitems.manager.player;

import api.praya.myitems.builder.lorestats.LoreStatsArmor;
import api.praya.myitems.builder.item.ItemStatsArmor;
import api.praya.myitems.builder.element.ElementBoostStats;
import api.praya.myitems.builder.socket.SocketGemsProperties;
import api.praya.myitems.builder.lorestats.LoreStatsWeapon;
import java.util.HashMap;
import org.bukkit.inventory.ItemStack;
import com.praya.myitems.manager.game.ElementManager;
import com.praya.myitems.manager.game.SocketManager;
import com.praya.myitems.manager.game.LoreStatsManager;
import com.praya.myitems.manager.game.GameManager;
import api.praya.myitems.builder.lorestats.LoreStatsOption;
import org.bukkit.entity.LivingEntity;
import core.praya.agarthalib.enums.main.SlotType;
import api.praya.myitems.builder.lorestats.LoreStatsEnum;
import core.praya.agarthalib.enums.main.Slot;
import core.praya.agarthalib.bridge.unity.Bridge;
import com.praya.myitems.config.plugin.MainConfig;
import api.praya.myitems.builder.item.ItemStatsWeapon;
import org.bukkit.entity.Player;
import com.praya.myitems.MyItems;
import com.praya.myitems.builder.handler.HandlerManager;

public class PlayerItemStatsManager extends HandlerManager
{
    protected PlayerItemStatsManager(final MyItems plugin) {
        super(plugin);
    }
    
    public final ItemStatsWeapon getItemStatsWeapon(final Player player) {
        final GameManager gameManager = this.plugin.getGameManager();
        final LoreStatsManager statsManager = gameManager.getStatsManager();
        final SocketManager socketManager = gameManager.getSocketManager();
        final ElementManager elementManager = gameManager.getElementManager();
        final MainConfig mainConfig = MainConfig.getInstance();
        final ItemStack itemEquipmentMainHand = Bridge.getBridgeEquipment().getEquipment(player, Slot.MAINHAND);
        final ItemStack itemEquipmentOffHand = Bridge.getBridgeEquipment().getEquipment(player, Slot.OFFHAND);
        final boolean enableItemUniversal = mainConfig.isStatsEnableItemUniversal();
        final boolean enableOffHand = mainConfig.isAbilityWeaponEnableOffHand();
        final boolean hasStatsCriticalChance = statsManager.hasLoreStats(itemEquipmentMainHand, LoreStatsEnum.CRITICAL_CHANCE) || (enableOffHand && statsManager.hasLoreStats(itemEquipmentOffHand, LoreStatsEnum.CRITICAL_CHANCE));
        final boolean hasStatsCriticalDamage = statsManager.hasLoreStats(itemEquipmentMainHand, LoreStatsEnum.CRITICAL_DAMAGE) || (enableOffHand && statsManager.hasLoreStats(itemEquipmentOffHand, LoreStatsEnum.CRITICAL_DAMAGE));
        final HashMap<String, Double> mapElementWeapon = elementManager.getMapElement((LivingEntity)player, SlotType.WEAPON, false);
        final LoreStatsWeapon statsWeapon = statsManager.getLoreStatsWeapon((LivingEntity)player, false, false);
        final SocketGemsProperties socket = socketManager.getSocketProperties((LivingEntity)player, false);
        final ElementBoostStats elementWeapon = elementManager.getElementBoostStats(mapElementWeapon);
        final double scaleOffHandValue = mainConfig.getStatsScaleOffHandValue();
        double statsDamage = 0.0;
        double statsDamageDiff = 0.0;
        Slot[] values;
        for (int length = (values = Slot.values()).length, i = 0; i < length; ++i) {
            final Slot slot = values[i];
            if (slot.getType().equals((Object)SlotType.WEAPON) || enableItemUniversal) {
                final ItemStack item = Bridge.getBridgeEquipment().getEquipment(player, slot);
                if (item != null) {
                    final double scaleValue = slot.equals((Object)Slot.OFFHAND) ? (enableOffHand ? scaleOffHandValue : 0.0) : 1.0;
                    final double statsItemDamageMin = statsManager.getLoreValue(item, LoreStatsEnum.DAMAGE, LoreStatsOption.MIN) * scaleValue;
                    final double statsItemDamageMax = statsManager.getLoreValue(item, LoreStatsEnum.DAMAGE, LoreStatsOption.MAX) * scaleValue;
                    final double statsItemDamageDiff = statsItemDamageMax - statsItemDamageMin;
                    statsDamage += statsItemDamageMin;
                    statsDamageDiff += statsItemDamageDiff;
                }
            }
        }
        final double socketDamage = socket.getAdditionalDamage() + statsDamage * socket.getPercentDamage() / 100.0;
        final double elementDamage = elementWeapon.getBaseAdditionalDamage() + statsDamage * elementWeapon.getBasePercentDamage() / 100.0;
        final double attributeDamage = statsDamage + socketDamage + elementDamage;
        final double baseDamage = 0.0;
        final double basePenetration = 0.0;
        final double basePvPDamage = 100.0;
        final double basePvEDamage = 100.0;
        final double baseCriticalChance = 5.0;
        final double baseCriticalDamage = 1.2;
        final double baseAttackAoERadius = 0.0;
        final double baseAttackAoEDamage = 0.0;
        final double baseHitRate = 100.0;
        final double totalDamageMin = 0.0 + attributeDamage;
        final double totalDamageMax = totalDamageMin + statsDamageDiff;
        final double totalPenetration = 0.0 + statsWeapon.getPenetration() + socket.getPenetration();
        final double totalPvPDamage = 100.0 + statsWeapon.getPvPDamage() + socket.getPvPDamage();
        final double totalPvEDamage = 100.0 + statsWeapon.getPvEDamage() + socket.getPvEDamage();
        final double totalCriticalChance = hasStatsCriticalChance ? (statsWeapon.getCriticalChance() + socket.getCriticalChance()) : 5.0;
        final double totalCriticalDamage = hasStatsCriticalDamage ? (1.0 + statsWeapon.getCriticalDamage() + socket.getCriticalDamage() / 100.0) : 1.2;
        final double totalAttackAoERadius = 0.0 + statsWeapon.getAttackAoERadius() + socket.getAttackAoERadius();
        final double totalAttackAoEDamage = 0.0 + statsWeapon.getAttackAoEDamage() + socket.getAttackAoEDamage();
        final double totalHitRate = 100.0 + statsWeapon.getHitRate() + socket.getHitRate();
        return new ItemStatsWeapon(totalDamageMin, totalDamageMax, totalPenetration, totalPvPDamage, totalPvEDamage, totalAttackAoERadius, totalAttackAoEDamage, totalCriticalChance, totalCriticalDamage, totalHitRate, socketDamage, elementDamage);
    }
    
    public final ItemStatsArmor getItemStatsArmor(final Player player) {
        final GameManager gameManager = this.plugin.getGameManager();
        final LoreStatsManager statsManager = gameManager.getStatsManager();
        final SocketManager socketManager = gameManager.getSocketManager();
        final LoreStatsArmor statsArmor = statsManager.getLoreStatsArmor((LivingEntity)player, false);
        final SocketGemsProperties socket = socketManager.getSocketProperties((LivingEntity)player, false);
        final double statsDefense = statsArmor.getDefense();
        final double socketDefense = socket.getAdditionalDefense() + statsDefense * socket.getPercentDefense() / 100.0;
        final double attributeDefense = statsDefense + socketDefense;
        final double baseDefense = 0.0;
        final double basePvPDefense = 100.0;
        final double basePvEDefense = 100.0;
        final double baseHealth = 0.0;
        final double baseHealthRegen = 0.0;
        final double baseStaminaMax = 0.0;
        final double baseStaminaRegen = 0.0;
        final double baseBlockAmount = 25.0;
        final double baseBlockRate = 0.0;
        final double baseDodgeRate = 0.0;
        final double totalDefense = 0.0 + attributeDefense;
        final double totalPvPDefense = 100.0 + statsArmor.getPvPDefense() + socket.getPvPDefense();
        final double totalPvEDefense = 100.0 + statsArmor.getPvEDefense() + socket.getPvEDefense();
        final double totalHealth = 0.0 + statsArmor.getHealth() + socket.getHealth();
        final double totalHealthRegen = 0.0 + statsArmor.getHealthRegen() + socket.getHealthRegen();
        final double totalStaminaMax = 0.0 + statsArmor.getStaminaMax() + socket.getStaminaMax();
        final double totalStaminaRegen = 0.0 + statsArmor.getStaminaRegen() + socket.getStaminaRegen();
        final double totalBlockAmount = 25.0 + statsArmor.getBlockAmount() + socket.getBlockAmount();
        final double totalBlockRate = 0.0 + statsArmor.getBlockRate() + socket.getBlockRate();
        final double totalDodgeRate = 0.0 + statsArmor.getDodgeRate() + socket.getDodgeRate();
        return new ItemStatsArmor(totalDefense, totalPvPDefense, totalPvEDefense, totalHealth, totalHealthRegen, totalStaminaMax, totalStaminaRegen, totalBlockAmount, totalBlockRate, totalDodgeRate, socketDefense);
    }
}
