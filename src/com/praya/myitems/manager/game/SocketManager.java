// 
// Decompiled by Procyon v0.5.36
// 

package com.praya.myitems.manager.game;

import core.praya.agarthalib.bridge.unity.Bridge;
import core.praya.agarthalib.enums.main.Slot;
import org.bukkit.entity.LivingEntity;
import java.util.ArrayList;
import java.util.List;
import com.praya.agarthalib.utility.EquipmentUtil;
import api.praya.myitems.builder.socket.SocketEnum;
import api.praya.myitems.builder.socket.SocketGemsProperties;
import org.bukkit.inventory.ItemStack;
import api.praya.myitems.builder.socket.SocketGems;
import java.util.Iterator;
import com.praya.agarthalib.utility.TextUtil;
import java.util.HashMap;
import com.praya.myitems.config.plugin.MainConfig;
import api.praya.myitems.builder.socket.SocketGemsTree;
import java.util.Collection;
import com.praya.myitems.MyItems;
import com.praya.myitems.config.game.SocketConfig;
import com.praya.myitems.builder.handler.HandlerManager;

public class SocketManager extends HandlerManager
{
    private final SocketConfig socketConfig;
    
    protected SocketManager(final MyItems plugin) {
        super(plugin);
        this.socketConfig = new SocketConfig(plugin);
    }
    
    public final SocketConfig getSocketConfig() {
        return this.socketConfig;
    }
    
    public final Collection<String> getSocketIDs() {
        return this.getSocketConfig().getSocketIDs();
    }
    
    public final Collection<SocketGemsTree> getSocketTreeBuilds() {
        return this.getSocketConfig().getSocketTreeBuilds();
    }
    
    public final SocketGemsTree getSocketTree(final String id) {
        return this.getSocketConfig().getSocketTree(id);
    }
    
    public final boolean isExist(final String name) {
        return this.getSocketTree(name) != null;
    }
    
    public final String getTextSocketSlotEmpty() {
        final MainConfig mainConfig = MainConfig.getInstance();
        final HashMap<String, String> map = new HashMap<String, String>();
        String format = mainConfig.getSocketFormatSlot();
        map.put("slot", this.getKeySocketEmpty());
        format = TextUtil.placeholder((HashMap)map, format, "<", ">");
        return format;
    }
    
    public final String getTextSocketSlotLocked() {
        final MainConfig mainConfig = MainConfig.getInstance();
        final HashMap<String, String> map = new HashMap<String, String>();
        String format = mainConfig.getSocketFormatSlot();
        map.put("slot", this.getKeySocketLocked());
        format = TextUtil.placeholder((HashMap)map, format, "<", ">");
        return format;
    }
    
    public final String getRawName(final String name) {
        for (final String key : this.getSocketIDs()) {
            if (key.equalsIgnoreCase(name)) {
                return key;
            }
        }
        return null;
    }
    
    public final SocketGems getSocketBuild(final String socket, final int grade) {
        for (final String key : this.getSocketIDs()) {
            if (key.equalsIgnoreCase(socket)) {
                return this.getSocketTree(key).getSocketBuild(grade);
            }
        }
        return null;
    }
    
    public final ItemStack getItem(final String socket, final int grade) {
        final SocketGems build = this.getSocketBuild(socket, grade);
        return (build != null) ? build.getItem() : null;
    }
    
    public final String getSocketKeyLore(final String socket, final int grade) {
        final SocketGems build = this.getSocketBuild(socket, grade);
        return (build != null) ? build.getKeyLore() : null;
    }
    
    public final String getSocketKeyLore(final SocketGems socketBuild) {
        return (socketBuild != null) ? socketBuild.getKeyLore() : null;
    }
    
    public final SocketGemsProperties getSocketProperties(final String socket, final int grade) {
        final SocketGems build = this.getSocketBuild(socket, grade);
        return (build != null) ? build.getSocketProperties() : null;
    }
    
    public final double getSocketValue(final String socket, final int grade, final SocketEnum typeValue) {
        return this.getSocketValue(this.getSocketProperties(socket, grade), typeValue);
    }
    
    public final double getSocketValue(final SocketGemsProperties socketProperties, final SocketEnum typeValue) {
        switch (typeValue) {
            case ADDITIONAL_DAMAGE: {
                return (socketProperties != null) ? socketProperties.getAdditionalDamage() : 0.0;
            }
            case PERCENT_DAMAGE: {
                return (socketProperties != null) ? socketProperties.getPercentDamage() : 0.0;
            }
            case PENETRATION: {
                return (socketProperties != null) ? socketProperties.getPenetration() : 0.0;
            }
            case PVP_DAMAGE: {
                return (socketProperties != null) ? socketProperties.getPvPDamage() : 0.0;
            }
            case PVE_DAMAGE: {
                return (socketProperties != null) ? socketProperties.getPvEDamage() : 0.0;
            }
            case ADDITIONAL_DEFENSE: {
                return (socketProperties != null) ? socketProperties.getAdditionalDefense() : 0.0;
            }
            case PERCENT_DEFENSE: {
                return (socketProperties != null) ? socketProperties.getPercentDefense() : 0.0;
            }
            case HEALTH: {
                return (socketProperties != null) ? socketProperties.getHealth() : 0.0;
            }
            case PVP_DEFENSE: {
                return (socketProperties != null) ? socketProperties.getPvPDefense() : 0.0;
            }
            case PVE_DEFENSE: {
                return (socketProperties != null) ? socketProperties.getPvEDefense() : 0.0;
            }
            case CRITICAL_CHANCE: {
                return (socketProperties != null) ? socketProperties.getCriticalChance() : 0.0;
            }
            case CRITICAL_DAMAGE: {
                return (socketProperties != null) ? socketProperties.getCriticalDamage() : 0.0;
            }
            case BLOCK_AMOUNT: {
                return (socketProperties != null) ? socketProperties.getBlockAmount() : 0.0;
            }
            case BLOCK_RATE: {
                return (socketProperties != null) ? socketProperties.getBlockRate() : 0.0;
            }
            case HIT_RATE: {
                return (socketProperties != null) ? socketProperties.getHitRate() : 0.0;
            }
            case DODGE_RATE: {
                return (socketProperties != null) ? socketProperties.getDodgeRate() : 0.0;
            }
            default: {
                return 0.0;
            }
        }
    }
    
    public final boolean isSocketItem(final ItemStack item) {
        return this.getSocketName(item) != null;
    }
    
    public final String getSocketName(final ItemStack item) {
        final SocketGems socket = this.getSocketBuild(item);
        return (socket != null) ? socket.getGems() : null;
    }
    
    public final SocketGems getSocketBuild(final ItemStack item) {
        final SocketManager socketManager = this.plugin.getGameManager().getSocketManager();
        for (final String key : socketManager.getSocketIDs()) {
            for (final SocketGems socket : socketManager.getSocketTree(key).getMapSocket().values()) {
                if (EquipmentUtil.isSimilar(item, socket.getItem())) {
                    return socket;
                }
            }
        }
        return null;
    }
    
    public final List<Integer> getLineLoresSocketEmpty(final ItemStack item) {
        final List<Integer> list = new ArrayList<Integer>();
        if (EquipmentUtil.loreCheck(item)) {
            final List<String> lores = (List<String>)EquipmentUtil.getLores(item);
            for (int index = 0; index < lores.size(); ++index) {
                final String lore = lores.get(index);
                if (this.isSocketEmptyLore(lore)) {
                    list.add(index + 1);
                }
            }
        }
        return list;
    }
    
    public final int getFirstLineSocketEmpty(final ItemStack item) {
        final List<Integer> list = this.getLineLoresSocketEmpty(item);
        return (list.size() > 0) ? list.get(0) : -1;
    }
    
    public final boolean containsSocketEmpty(final ItemStack item) {
        return this.getLineLoresSocketEmpty(item).size() > 0;
    }
    
    public final List<Integer> getLineLoresSocketLocked(final ItemStack item) {
        final List<Integer> list = new ArrayList<Integer>();
        if (EquipmentUtil.loreCheck(item)) {
            final List<String> lores = (List<String>)EquipmentUtil.getLores(item);
            for (int index = 0; index < lores.size(); ++index) {
                final String lore = lores.get(index);
                if (this.isSocketLockedLore(lore)) {
                    list.add(index + 1);
                }
            }
        }
        return list;
    }
    
    public final int getFirstLineSocketLocked(final ItemStack item) {
        final List<Integer> list = this.getLineLoresSocketLocked(item);
        return (list.size() > 0) ? list.get(0) : -1;
    }
    
    public final boolean containsSocketLocked(final ItemStack item) {
        return this.getLineLoresSocketLocked(item).size() > 0;
    }
    
    public final String getSocket(final String lore) {
        final SocketManager socketManager = this.plugin.getGameManager().getSocketManager();
        final String[] part = lore.split(MainConfig.KEY_SOCKET_LORE_GEMS);
        if (part.length > 1) {
            final String keyLore = part[1];
            for (final String key : socketManager.getSocketIDs()) {
                for (final SocketGems socket : socketManager.getSocketTree(key).getMapSocket().values()) {
                    if (keyLore.equalsIgnoreCase(socket.getKeyLore())) {
                        return key;
                    }
                }
            }
        }
        return null;
    }
    
    public final SocketGems getSocketBuild(final String lore) {
        final SocketManager socketManager = this.plugin.getGameManager().getSocketManager();
        final String[] part = lore.split(MainConfig.KEY_SOCKET_LORE_GEMS);
        if (part.length > 1) {
            final String keyLore = part[1];
            for (final String key : socketManager.getSocketIDs()) {
                for (final SocketGems socket : socketManager.getSocketTree(key).getMapSocket().values()) {
                    if (keyLore.equalsIgnoreCase(socket.getKeyLore())) {
                        return socket;
                    }
                }
            }
        }
        return null;
    }
    
    public final List<String> getLoresSocket(final ItemStack item) {
        final List<String> list = new ArrayList<String>();
        if (EquipmentUtil.loreCheck(item)) {
            final List<String> lores = (List<String>)EquipmentUtil.getLores(item);
            for (final String lore : lores) {
                if (this.isSocketGemsLore(lore)) {
                    list.add(lore);
                }
            }
        }
        return list;
    }
    
    public final List<Integer> getLineLoresSocket(final ItemStack item) {
        final List<Integer> list = new ArrayList<Integer>();
        if (EquipmentUtil.loreCheck(item)) {
            final List<String> lores = (List<String>)EquipmentUtil.getLores(item);
            for (int index = 0; index < lores.size(); ++index) {
                final String lore = lores.get(index);
                if (this.isSocketGemsLore(lore)) {
                    list.add(index + 1);
                }
            }
        }
        return list;
    }
    
    public final boolean containsSocketGems(final ItemStack item) {
        return this.getLineLoresSocket(item).size() > 0;
    }
    
    public final boolean isSocketGemsLore(final String lore) {
        return lore.contains(MainConfig.KEY_SOCKET_LORE_GEMS);
    }
    
    public final boolean isSocketEmptyLore(final String lore) {
        final String key = this.getKeySocketEmpty(true);
        return lore.contains(key);
    }
    
    public final boolean isSocketLockedLore(final String lore) {
        final String key = this.getKeySocketLocked(true);
        return lore.contains(key);
    }
    
    public final SocketGemsProperties getSocketProperties(final LivingEntity livingEntity) {
        return this.getSocketProperties(livingEntity, true);
    }
    
    public final SocketGemsProperties getSocketProperties(final LivingEntity livingEntity, final boolean checkDurability) {
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
        Slot[] values;
        for (int length = (values = Slot.values()).length, i = 0; i < length; ++i) {
            final Slot slot = values[i];
            final ItemStack item = Bridge.getBridgeEquipment().getEquipment(livingEntity, slot);
            if (EquipmentUtil.isSolid(item)) {
                final SocketGemsProperties build = this.getSocketProperties(item, checkDurability);
                additionalDamage += build.getAdditionalDamage();
                percentDamage += build.getPercentDamage();
                penetration += build.getPenetration();
                pvpDamage += build.getPvPDamage();
                pveDamage += build.getPvEDamage();
                additionalDefense += build.getAdditionalDefense();
                percentDefense += build.getPercentDefense();
                health += build.getHealth();
                healthRegen += build.getHealthRegen();
                staminaMax += build.getStaminaMax();
                staminaRegen += build.getStaminaRegen();
                attackAoERadius += build.getAttackAoERadius();
                attackAoEDamage += build.getAttackAoEDamage();
                pvpDefense += build.getPvPDefense();
                pveDefense += build.getPvEDefense();
                criticalChance += build.getCriticalChance();
                criticalDamage += build.getCriticalDamage();
                blockAmount += build.getBlockAmount();
                blockRate += build.getBlockRate();
                hitRate += build.getHitRate();
                dodgeRate += build.getDodgeRate();
            }
        }
        return new SocketGemsProperties(additionalDamage, percentDamage, penetration, pvpDamage, pveDamage, additionalDefense, percentDefense, health, healthRegen, staminaMax, staminaRegen, attackAoERadius, attackAoEDamage, pvpDefense, pveDefense, criticalChance, criticalDamage, blockAmount, blockRate, hitRate, dodgeRate);
    }
    
    public final SocketGemsProperties getSocketProperties(final ItemStack item) {
        return this.getSocketProperties(item, true);
    }
    
    public final SocketGemsProperties getSocketProperties(final ItemStack item, final boolean checkDurability) {
        final GameManager gameManager = this.plugin.getGameManager();
        final LoreStatsManager statsManager = gameManager.getStatsManager();
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
        if (EquipmentUtil.loreCheck(item) && (!checkDurability || statsManager.checkDurability(item))) {
            for (final String lore : EquipmentUtil.getLores(item)) {
                final SocketGems socket = this.getSocketBuild(lore);
                if (socket != null) {
                    final SocketGemsProperties build = socket.getSocketProperties();
                    additionalDamage += build.getAdditionalDamage();
                    percentDamage += build.getPercentDamage();
                    penetration += build.getPenetration();
                    pvpDamage += build.getPvPDamage();
                    pveDamage += build.getPvEDamage();
                    additionalDefense += build.getAdditionalDefense();
                    percentDefense += build.getPercentDefense();
                    health += build.getHealth();
                    healthRegen += build.getHealthRegen();
                    staminaMax += build.getStaminaMax();
                    staminaRegen += build.getStaminaRegen();
                    attackAoERadius += build.getAttackAoERadius();
                    attackAoEDamage += build.getAttackAoEDamage();
                    pvpDefense += build.getPvPDefense();
                    pveDefense += build.getPvEDefense();
                    criticalChance += build.getCriticalChance();
                    criticalDamage += build.getCriticalDamage();
                    blockAmount += build.getBlockAmount();
                    blockRate += build.getBlockRate();
                    hitRate += build.getHitRate();
                    dodgeRate += build.getDodgeRate();
                }
            }
        }
        return new SocketGemsProperties(additionalDamage, percentDamage, penetration, pvpDamage, pveDamage, additionalDefense, percentDefense, health, healthRegen, staminaMax, staminaRegen, attackAoERadius, attackAoEDamage, pvpDefense, pveDefense, criticalChance, criticalDamage, blockAmount, blockRate, hitRate, dodgeRate);
    }
    
    private String getKeySocketEmpty() {
        return this.getKeySocketEmpty(false);
    }
    
    private String getKeySocketEmpty(final boolean justCheck) {
        final MainConfig mainConfig = MainConfig.getInstance();
        final String key = MainConfig.KEY_SOCKET_SLOT;
        final String color = mainConfig.getSocketColorSlot();
        final String keylore = mainConfig.getSocketFormatSlotEmpty();
        return justCheck ? (String.valueOf(key) + color + keylore) : (String.valueOf(key) + color + keylore + key + color);
    }
    
    private String getKeySocketLocked() {
        return this.getKeySocketLocked(false);
    }
    
    private String getKeySocketLocked(final boolean justCheck) {
        final MainConfig mainConfig = MainConfig.getInstance();
        final String key = MainConfig.KEY_SOCKET_SLOT;
        final String color = mainConfig.getSocketColorSlot();
        final String keylore = mainConfig.getSocketFormatSlotLocked();
        return justCheck ? (String.valueOf(key) + color + keylore) : (String.valueOf(key) + color + keylore + key + color);
    }
    
    public final String getTextSocketGemsLore(final String socket, final int grade) {
        return this.getTextSocketGemsLore(socket, grade, false);
    }
    
    public final String getTextSocketGemsLore(final String socket, final int grade, final boolean justCheck) {
        final String key = MainConfig.KEY_SOCKET_LORE_GEMS;
        final String keylore = this.getSocketKeyLore(socket, grade);
        return justCheck ? (String.valueOf(key) + keylore) : (String.valueOf(key) + keylore + key);
    }
}
