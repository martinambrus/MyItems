// 
// Decompiled by Procyon v0.5.36
// 

package com.praya.myitems.config.game;

import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.inventory.meta.ItemMeta;
import java.util.Map;
import api.praya.myitems.builder.socket.SocketGems;
import com.praya.agarthalib.utility.MathUtil;
import api.praya.myitems.builder.power.PowerSpecialEnum;
import core.praya.agarthalib.enums.branch.ProjectileEnum;
import api.praya.myitems.builder.power.PowerClickEnum;
import api.praya.myitems.builder.passive.PassiveEffectEnum;
import api.praya.myitems.builder.ability.AbilityItemWeapon;
import java.util.List;
import com.praya.myitems.manager.game.LoreStatsManager;
import com.praya.myitems.manager.game.PassiveEffectManager;
import com.praya.myitems.manager.game.SocketManager;
import com.praya.myitems.manager.game.ElementManager;
import com.praya.myitems.manager.game.AbilityWeaponManager;
import com.praya.myitems.manager.game.PowerSpecialManager;
import com.praya.myitems.manager.game.PowerShootManager;
import com.praya.myitems.manager.game.PowerCommandManager;
import com.praya.myitems.manager.game.PowerManager;
import com.praya.myitems.manager.game.GameManager;
import api.praya.myitems.builder.power.PowerEnum;
import com.praya.agarthalib.utility.TextUtil;
import api.praya.myitems.builder.lorestats.LoreStatsOption;
import api.praya.myitems.builder.lorestats.LoreStatsEnum;
import org.bukkit.configuration.file.FileConfiguration;
import java.io.File;
import com.praya.myitems.manager.plugin.DataManager;
import com.praya.myitems.manager.plugin.PluginManager;
import com.praya.agarthalib.utility.EquipmentUtil;
import org.bukkit.plugin.java.JavaPlugin;
import com.praya.agarthalib.utility.FileUtil;
import java.util.Iterator;
import java.util.Collection;
import com.praya.myitems.MyItems;
import org.bukkit.inventory.ItemStack;
import java.util.HashMap;
import com.praya.myitems.builder.handler.HandlerConfig;

public class ItemConfig extends HandlerConfig
{
    private final HashMap<String, ItemStack> mapItem;
    
    public ItemConfig(final MyItems plugin) {
        super(plugin);
        this.mapItem = new HashMap<String, ItemStack>();
    }
    
    public final Collection<String> getItemIDs() {
        return this.mapItem.keySet();
    }
    
    public final ItemStack getItem(final String id) {
        for (final String key : this.getItemIDs()) {
            if (key.equalsIgnoreCase(id)) {
                return this.mapItem.get(key).clone();
            }
        }
        return null;
    }
    
    public final void setup() {
        this.moveOldFile();
        this.reset();
        this.loadConfig();
    }
    
    private final void reset() {
        this.mapItem.clear();
    }
    
    private final void loadConfig() {
        final PluginManager pluginManager = this.plugin.getPluginManager();
        final DataManager dataManager = pluginManager.getDataManager();
        final String path = dataManager.getPath("Path_File_Item");
        final File file = FileUtil.getFile((JavaPlugin)this.plugin, path);
        this.convertOldDatabase();
        if (!file.exists()) {
            FileUtil.saveResource((JavaPlugin)this.plugin, path);
        }
        final FileConfiguration config = FileUtil.getFileConfiguration(file);
        for (final String key : config.getKeys(false)) {
            final ItemStack item = config.getItemStack(key);
            if (item != null) {
                final ItemStack gameItem = this.convertToGame(item);
                EquipmentUtil.colorful(gameItem);
                this.mapItem.put(key, gameItem);
            }
        }
    }
    
    private final ItemStack convertToFile(final ItemStack convertItem) {
        final GameManager gameManager = this.plugin.getGameManager();
        final PowerManager powerManager = gameManager.getPowerManager();
        final PowerCommandManager powerCommandManager = powerManager.getPowerCommandManager();
        final PowerShootManager powerShootManager = powerManager.getPowerShootManager();
        final PowerSpecialManager powerSpecialManager = powerManager.getPowerSpecialManager();
        final AbilityWeaponManager abilityWeaponManager = gameManager.getAbilityWeaponManager();
        final ElementManager elementManager = gameManager.getElementManager();
        final SocketManager socketManager = gameManager.getSocketManager();
        final PassiveEffectManager passiveEffectManager = gameManager.getPassiveEffectManager();
        final LoreStatsManager statsManager = gameManager.getStatsManager();
        final ItemStack item = new ItemStack(convertItem);
        if (EquipmentUtil.hasDisplayName(item)) {
            EquipmentUtil.setDisplayName(item, EquipmentUtil.getDisplayName(item).replaceAll("\ufffd", "&"));
        }
        if (EquipmentUtil.loreCheck(item)) {
            final List<String> lores = (List<String>)EquipmentUtil.getLores(item);
            final HashMap<String, String> mapPlaceholder = new HashMap<String, String>();
            for (int i = 0; i < lores.size(); ++i) {
                final int line = i + 1;
                final String lore = lores.get(i);
                if (statsManager.isLoreStats(lore)) {
                    final LoreStatsEnum loreStats = statsManager.getLoreStats(lore);
                    final double minValue = loreStats.equals(LoreStatsEnum.CRITICAL_DAMAGE) ? (statsManager.getLoreValue(loreStats, LoreStatsOption.MIN, lore) + 1.0) : statsManager.getLoreValue(loreStats, LoreStatsOption.MIN, lore);
                    final double maxValue = loreStats.equals(LoreStatsEnum.CRITICAL_DAMAGE) ? (statsManager.getLoreValue(loreStats, LoreStatsOption.MAX, lore) + 1.0) : statsManager.getLoreValue(loreStats, LoreStatsOption.MAX, lore);
                    if (minValue == maxValue) {
                        String replacement = "@MyItems, LoreStats = {lorestats}, Value = {value}";
                        mapPlaceholder.clear();
                        mapPlaceholder.put("lorestats", String.valueOf(loreStats));
                        mapPlaceholder.put("value", String.valueOf(minValue));
                        replacement = TextUtil.placeholder((HashMap)mapPlaceholder, replacement);
                        EquipmentUtil.setLore(item, line, replacement);
                    }
                    else {
                        String replacement = "@MyItems, LoreStats = {lorestats}, MinValue = {minvalue}, MaxValue = {maxvalue}";
                        mapPlaceholder.clear();
                        mapPlaceholder.put("lorestats", String.valueOf(loreStats));
                        mapPlaceholder.put("minvalue", String.valueOf(minValue));
                        mapPlaceholder.put("maxvalue", String.valueOf(maxValue));
                        replacement = TextUtil.placeholder((HashMap)mapPlaceholder, replacement);
                        EquipmentUtil.setLore(item, line, replacement);
                    }
                }
                else if (socketManager.isSocketEmptyLore(lore)) {
                    final String replacement2 = "@MyItems, SlotSocket = Empty";
                    EquipmentUtil.setLore(item, line, "@MyItems, SlotSocket = Empty");
                }
                else if (socketManager.isSocketGemsLore(lore)) {
                    final String socket = socketManager.getSocket(lore);
                    String replacement3 = "@MyItems, Socket = {socket}";
                    mapPlaceholder.clear();
                    mapPlaceholder.put("socket", String.valueOf(socket));
                    replacement3 = TextUtil.placeholder((HashMap)mapPlaceholder, replacement3);
                    EquipmentUtil.setLore(item, line, replacement3);
                }
                else if (abilityWeaponManager.isAbilityItemWeapon(lore)) {
                    final AbilityItemWeapon abilityItemWeapon = abilityWeaponManager.getAbilityItemWeapon(lore);
                    final String ability = abilityItemWeapon.getAbility();
                    final int grade = abilityItemWeapon.getGrade();
                    final double chance = abilityItemWeapon.getChance();
                    String replacement = "@MyItems, Ability = {ability}, Grade = {grade}, Chance = {chance}";
                    mapPlaceholder.clear();
                    mapPlaceholder.put("ability", String.valueOf(ability));
                    mapPlaceholder.put("grade", String.valueOf(grade));
                    mapPlaceholder.put("chance", String.valueOf(chance));
                    replacement = TextUtil.placeholder((HashMap)mapPlaceholder, replacement);
                    EquipmentUtil.setLore(item, line, replacement);
                }
                else if (elementManager.isElement(lore)) {
                    final String element = elementManager.getElement(lore);
                    final double value = elementManager.getElementValue(lore);
                    String replacement4 = "@MyItems, Element = {element}, Value = {value}";
                    mapPlaceholder.clear();
                    mapPlaceholder.put("element", element);
                    mapPlaceholder.put("value", String.valueOf(value));
                    replacement4 = TextUtil.placeholder((HashMap)mapPlaceholder, replacement4);
                    EquipmentUtil.setLore(item, line, replacement4);
                }
                else if (passiveEffectManager.isPassiveEffect(lore)) {
                    final PassiveEffectEnum buff = passiveEffectManager.getPassiveEffect(lore);
                    final int grade2 = passiveEffectManager.passiveEffectGrade(buff, lore);
                    String replacement5 = "@MyItems, Buff = {buff}, Grade = {grade}";
                    mapPlaceholder.clear();
                    mapPlaceholder.put("buff", String.valueOf(buff));
                    mapPlaceholder.put("grade", String.valueOf(grade2));
                    replacement5 = TextUtil.placeholder((HashMap)mapPlaceholder, replacement5);
                    EquipmentUtil.setLore(item, line, replacement5);
                }
                else if (powerManager.isPower(lore)) {
                    final PowerEnum power = powerManager.getPower(lore);
                    final PowerClickEnum click = powerManager.getClick(lore);
                    final double cooldown = powerManager.getCooldown(lore);
                    if (click != null) {
                        if (power.equals(PowerEnum.COMMAND)) {
                            final String type = powerCommandManager.getCommand(lore);
                            if (type != null) {
                                String replacement = "@MyItems, Power = {power}, Click = {click}, Type = {type}, Cooldown = {cooldown}";
                                mapPlaceholder.clear();
                                mapPlaceholder.put("power", String.valueOf(power));
                                mapPlaceholder.put("click", String.valueOf(click));
                                mapPlaceholder.put("type", type);
                                mapPlaceholder.put("cooldown", String.valueOf(cooldown));
                                replacement = TextUtil.placeholder((HashMap)mapPlaceholder, replacement);
                                EquipmentUtil.setLore(item, line, replacement);
                            }
                        }
                        else if (power.equals(PowerEnum.SHOOT)) {
                            final ProjectileEnum type2 = powerShootManager.getShoot(lore);
                            if (type2 != null) {
                                String replacement = "@MyItems, Power = {power}, Click = {click}, Type = {type}, Cooldown = {cooldown}";
                                mapPlaceholder.clear();
                                mapPlaceholder.put("power", String.valueOf(power));
                                mapPlaceholder.put("click", String.valueOf(click));
                                mapPlaceholder.put("type", String.valueOf(type2));
                                mapPlaceholder.put("cooldown", String.valueOf(cooldown));
                                replacement = TextUtil.placeholder((HashMap)mapPlaceholder, replacement);
                                EquipmentUtil.setLore(item, line, replacement);
                            }
                        }
                        else if (power.equals(PowerEnum.SPECIAL)) {
                            final PowerSpecialEnum type3 = powerSpecialManager.getSpecial(lore);
                            if (type3 != null) {
                                String replacement = "@MyItems, Power = {power}, Click = {click}, Type = {type}, Cooldown = {cooldown}";
                                mapPlaceholder.clear();
                                mapPlaceholder.put("power", String.valueOf(power));
                                mapPlaceholder.put("click", String.valueOf(click));
                                mapPlaceholder.put("type", String.valueOf(type3));
                                mapPlaceholder.put("cooldown", String.valueOf(cooldown));
                                replacement = TextUtil.placeholder((HashMap)mapPlaceholder, replacement);
                                EquipmentUtil.setLore(item, line, replacement);
                            }
                        }
                    }
                }
            }
        }
        return item;
    }
    
    private final ItemStack convertToGame(final ItemStack convertItem) {
        final GameManager gameManager = this.plugin.getGameManager();
        final PowerManager powerManager = gameManager.getPowerManager();
        final PowerCommandManager powerCommandManager = powerManager.getPowerCommandManager();
        final PowerShootManager powerShootManager = powerManager.getPowerShootManager();
        final PowerSpecialManager powerSpecialManager = powerManager.getPowerSpecialManager();
        final AbilityWeaponManager abilityWeaponManager = gameManager.getAbilityWeaponManager();
        final ElementManager elementManager = gameManager.getElementManager();
        final SocketManager socketManager = gameManager.getSocketManager();
        final PassiveEffectManager passiveEffectManager = gameManager.getPassiveEffectManager();
        final LoreStatsManager statsManager = gameManager.getStatsManager();
        final ItemStack item = new ItemStack(convertItem);
        if (EquipmentUtil.loreCheck(item)) {
            final List<String> lores = (List<String>)EquipmentUtil.getLores(item);
            for (int i = 0; i < lores.size(); ++i) {
                final int line = i + 1;
                final String lore = lores.get(i).replaceAll(" ", "");
                if (lore.contains("@MyItems")) {
                    final String[] part = lore.split(",");
                    if (part.length > 1) {
                        if (lore.contains("LoreStats=") || lore.contains("Stats=")) {
                            LoreStatsEnum loreStats = null;
                            double minValue = -1.0;
                            double maxValue = -1.0;
                            for (int t = 0; t < part.length; ++t) {
                                final String fullElement = part[t];
                                final String[] element = fullElement.split("=");
                                if (element.length == 2) {
                                    final String key = element[0];
                                    final String value = element[1];
                                    if (key.equalsIgnoreCase("LoreStats") || key.equalsIgnoreCase("Stats")) {
                                        loreStats = LoreStatsEnum.get(value);
                                    }
                                    else if (key.equalsIgnoreCase("MinValue") || key.equalsIgnoreCase("Value")) {
                                        if (MathUtil.isNumber(value)) {
                                            minValue = MathUtil.parseDouble(value);
                                        }
                                    }
                                    else if ((key.equalsIgnoreCase("MaxValue") || key.equalsIgnoreCase("Max")) && MathUtil.isNumber(value)) {
                                        maxValue = MathUtil.parseDouble(value);
                                    }
                                }
                            }
                            if (loreStats != null && minValue != -1.0) {
                                final String replacement = (maxValue != -1.0) ? statsManager.getTextLoreStats(loreStats, minValue, maxValue) : statsManager.getTextLoreStats(loreStats, minValue);
                                EquipmentUtil.setLore(item, line, replacement);
                            }
                        }
                        else if (lore.contains("SlotSocket=") || lore.contains("SlotSockets=")) {
                            String slot = null;
                            for (int t2 = 0; t2 < part.length; ++t2) {
                                final String fullElement2 = part[t2];
                                final String[] element2 = fullElement2.split("=");
                                if (element2.length == 2) {
                                    final String key2 = element2[0];
                                    final String value2 = element2[1];
                                    if (key2.equalsIgnoreCase("SlotSocket") || key2.equalsIgnoreCase("SlotSockets")) {
                                        slot = value2;
                                    }
                                }
                            }
                            if (slot != null && slot.equalsIgnoreCase("Empty")) {
                                final String replacement2 = socketManager.getTextSocketSlotEmpty();
                                EquipmentUtil.setLore(item, line, replacement2);
                            }
                        }
                        else if (lore.contains("Socket=") || lore.contains("Sockets=")) {
                            SocketGems socket = null;
                            for (int t2 = 0; t2 < part.length; ++t2) {
                                final String fullElement2 = part[t2];
                                final String[] element2 = fullElement2.split("=");
                                if (element2.length >= 2) {
                                    final String key2 = element2[0];
                                    final String value2 = element2[1];
                                    final String gradeText = (element2.length > 2) ? element2[2] : String.valueOf(1);
                                    if (key2.equalsIgnoreCase("Socket") || key2.equalsIgnoreCase("Sockets")) {
                                        final int grade = MathUtil.isNumber(gradeText) ? MathUtil.parseInteger(gradeText) : 1;
                                        socket = socketManager.getSocketBuild(value2, grade);
                                    }
                                }
                            }
                            if (socket != null) {
                                final String gems = socket.getGems();
                                final int grade2 = socket.getGrade();
                                final String replacement3 = socketManager.getTextSocketGemsLore(gems, grade2);
                                EquipmentUtil.setLore(item, line, replacement3);
                            }
                        }
                        else if (lore.contains("Element=") || lore.contains("Elements=")) {
                            String keyElement = null;
                            double valueElement = 1.0;
                            for (int t3 = 0; t3 < part.length; ++t3) {
                                final String fullElement3 = part[t3];
                                final String[] element3 = fullElement3.split("=");
                                if (element3.length == 2) {
                                    final String key3 = element3[0];
                                    final String value3 = element3[1];
                                    if (key3.equalsIgnoreCase("Element") || key3.equalsIgnoreCase("Elements")) {
                                        keyElement = value3;
                                    }
                                    else if ((key3.equalsIgnoreCase("Value") || key3.equalsIgnoreCase("Values")) && MathUtil.isNumber(value3)) {
                                        valueElement = MathUtil.parseDouble(value3);
                                    }
                                }
                            }
                            if (keyElement != null) {
                                final String replacement3 = elementManager.getTextElement(keyElement, valueElement);
                                EquipmentUtil.setLore(item, line, replacement3);
                            }
                        }
                        else if (lore.contains("Buff=") || lore.contains("Buffs=")) {
                            PassiveEffectEnum effect = null;
                            int grade3 = -1;
                            for (int t4 = 0; t4 < part.length; ++t4) {
                                final String fullElement4 = part[t4];
                                final String[] element4 = fullElement4.split("=");
                                if (element4.length == 2) {
                                    final String key4 = element4[0];
                                    final String value4 = element4[1];
                                    if (key4.equalsIgnoreCase("Buff") || key4.equalsIgnoreCase("Buffs")) {
                                        effect = PassiveEffectEnum.get(value4);
                                    }
                                    else if ((key4.equalsIgnoreCase("Grade") || key4.equalsIgnoreCase("Grades")) && MathUtil.isNumber(value4)) {
                                        grade3 = MathUtil.parseInteger(value4);
                                    }
                                }
                            }
                            if (effect != null && grade3 != -1) {
                                final String replacement4 = passiveEffectManager.getTextPassiveEffect(effect, grade3);
                                EquipmentUtil.setLore(item, line, replacement4);
                            }
                        }
                        else if (lore.contains("Ability=")) {
                            String ability = null;
                            int grade3 = -1;
                            double chance = 0.0;
                            for (int t5 = 0; t5 < part.length; ++t5) {
                                final String fullElement5 = part[t5];
                                final String[] element5 = fullElement5.split("=");
                                if (element5.length == 2) {
                                    final String key5 = element5[0];
                                    final String value5 = element5[1];
                                    if (key5.equalsIgnoreCase("Ability")) {
                                        ability = value5;
                                    }
                                    else if (key5.equalsIgnoreCase("Grade")) {
                                        if (MathUtil.isNumber(value5)) {
                                            grade3 = MathUtil.parseInteger(value5);
                                        }
                                    }
                                    else if (key5.equalsIgnoreCase("chance") && MathUtil.isNumber(value5)) {
                                        chance = MathUtil.parseDouble(value5);
                                    }
                                }
                            }
                            if (ability != null && grade3 != -1 && chance != 0.0) {
                                final String replacement5 = abilityWeaponManager.getTextAbility(ability, grade3, chance);
                                EquipmentUtil.setLore(item, line, replacement5);
                            }
                        }
                        else if (lore.contains("Power=")) {
                            PowerEnum power = null;
                            PowerClickEnum click = null;
                            double cooldown = 0.0;
                            String type = null;
                            for (int t = 0; t < part.length; ++t) {
                                final String fullElement = part[t];
                                final String[] element = fullElement.split("=");
                                if (element.length == 2) {
                                    final String key = element[0];
                                    final String value = element[1];
                                    if (key.equalsIgnoreCase("Power")) {
                                        power = PowerEnum.get(value);
                                    }
                                    else if (key.equalsIgnoreCase("Click")) {
                                        click = PowerClickEnum.get(value);
                                    }
                                    else if (key.equalsIgnoreCase("Cooldown")) {
                                        if (MathUtil.isNumber(value)) {
                                            cooldown = MathUtil.parseDouble(value);
                                        }
                                    }
                                    else if (key.equalsIgnoreCase("Type")) {
                                        type = value;
                                    }
                                }
                            }
                            if (power != null && click != null && type != null) {
                                if (power.equals(PowerEnum.COMMAND)) {
                                    if (powerCommandManager.isPowerCommandExists(type)) {
                                        final String keyCommand = powerCommandManager.getCommandKey(type);
                                        final String replacement6 = powerCommandManager.getTextPowerCommand(click, keyCommand, cooldown);
                                        EquipmentUtil.setLore(item, line, replacement6);
                                    }
                                }
                                else if (power.equals(PowerEnum.SHOOT)) {
                                    final ProjectileEnum projectile = ProjectileEnum.getProjectileEnum(type);
                                    if (projectile != null) {
                                        final String replacement6 = powerShootManager.getTextPowerShoot(click, projectile, cooldown);
                                        EquipmentUtil.setLore(item, line, replacement6);
                                    }
                                }
                                else if (power.equals(PowerEnum.SPECIAL)) {
                                    final PowerSpecialEnum special = PowerSpecialEnum.get(type);
                                    if (special != null) {
                                        final String replacement6 = powerSpecialManager.getTextPowerSpecial(click, special, cooldown);
                                        EquipmentUtil.setLore(item, line, replacement6);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return item;
    }
    
    public final void saveItem(final ItemStack item, final String nameid) {
        final PluginManager pluginManager = this.plugin.getPluginManager();
        final DataManager dataManager = pluginManager.getDataManager();
        final String path = dataManager.getPath("Path_File_Item");
        final ItemStack fileItem = this.convertToFile(item);
        this.mapItem.put(nameid, item);
        FileUtil.addObject((JavaPlugin)this.plugin, path, nameid, (Object)fileItem);
    }
    
    public final void removeItem(final String nameid) {
        final PluginManager pluginManager = this.plugin.getPluginManager();
        final DataManager dataManager = pluginManager.getDataManager();
        final String path = dataManager.getPath("Path_File_Item");
        this.mapItem.remove(nameid);
        FileUtil.removeObject((JavaPlugin)this.plugin, path, nameid);
    }
    
    public final void convertOldDatabase() {
        final File dirDatabase = FileUtil.getFile((JavaPlugin)this.plugin, "database");
        if (dirDatabase.exists()) {
            final File oldItemsFile = FileUtil.getFile((JavaPlugin)this.plugin, "database/items.dat");
            if (oldItemsFile.exists()) {
                final HashMap<String, Object> oldItems = (HashMap<String, Object>)FileUtil.loadObjectSilent(oldItemsFile);
                for (final String key : oldItems.keySet()) {
                    final Object rawItem = oldItems.get(key);
                    final ItemStack item = this.deserializeItemStack((HashMap<Map<String, Object>, Map<String, Object>>)rawItem);
                    this.convertToFile(item);
                    this.saveItem(item, key);
                }
                oldItemsFile.delete();
                dirDatabase.delete();
            }
        }
    }
    
    private ItemStack deserializeItemStack(final HashMap<Map<String, Object>, Map<String, Object>> serializedItemStackMap) {
        final Map.Entry<Map<String, Object>, Map<String, Object>> serializedItemStack = serializedItemStackMap.entrySet().iterator().next();
        final ItemStack itemStack = ItemStack.deserialize((Map)serializedItemStack.getKey());
        if (serializedItemStack.getValue() != null) {
            final ItemMeta itemMeta = (ItemMeta)ConfigurationSerialization.deserializeObject((Map)serializedItemStack.getValue(), ConfigurationSerialization.getClassByAlias("ItemMeta"));
            itemStack.setItemMeta(itemMeta);
        }
        return itemStack;
    }
    
    private final void moveOldFile() {
        final PluginManager pluginManager = this.plugin.getPluginManager();
        final DataManager dataManager = pluginManager.getDataManager();
        final String pathSource = "item.yml";
        final String pathTarget = dataManager.getPath("Path_File_Item");
        final File fileSource = FileUtil.getFile((JavaPlugin)this.plugin, "item.yml");
        final File fileTarget = FileUtil.getFile((JavaPlugin)this.plugin, pathTarget);
        if (fileSource.exists()) {
            FileUtil.moveFileSilent(fileSource, fileTarget);
        }
    }
}
