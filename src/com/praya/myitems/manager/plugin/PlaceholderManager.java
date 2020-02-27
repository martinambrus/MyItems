// 
// Decompiled by Procyon v0.5.36
// 

package com.praya.myitems.manager.plugin;

import org.bukkit.OfflinePlayer;
import api.praya.myitems.builder.ability.AbilityWeapon;
import com.praya.myitems.manager.register.RegisterAbilityWeaponManager;
import com.praya.myitems.manager.game.RequirementManager;
import com.praya.myitems.manager.game.LoreStatsManager;
import com.praya.myitems.manager.game.PassiveEffectManager;
import com.praya.myitems.manager.game.SocketManager;
import com.praya.myitems.manager.game.ElementManager;
import com.praya.myitems.manager.game.AbilityWeaponManager;
import com.praya.myitems.manager.game.PowerSpecialManager;
import com.praya.myitems.manager.game.PowerShootManager;
import com.praya.myitems.manager.game.PowerCommandManager;
import com.praya.myitems.manager.game.PowerManager;
import com.praya.myitems.manager.register.RegisterManager;
import com.praya.myitems.manager.game.GameManager;
import com.praya.agarthalib.utility.PlayerUtil;
import api.praya.myitems.builder.power.PowerSpecialEnum;
import core.praya.agarthalib.enums.branch.ProjectileEnum;
import api.praya.myitems.builder.power.PowerClickEnum;
import api.praya.myitems.builder.power.PowerEnum;
import api.praya.myitems.builder.passive.PassiveEffectEnum;
import com.praya.agarthalib.utility.MathUtil;
import api.praya.myitems.builder.lorestats.LoreStatsEnum;
import java.util.regex.Pattern;
import java.util.ListIterator;
import api.praya.myitems.builder.lorestats.LoreStatsModifier;
import java.util.ArrayList;
import com.praya.agarthalib.utility.EquipmentUtil;
import org.bukkit.inventory.ItemStack;
import org.bukkit.entity.Player;
import com.praya.agarthalib.utility.ListUtil;
import com.praya.agarthalib.utility.TextUtil;
import java.util.List;
import api.praya.agarthalib.manager.plugin.SupportManagerAPI;
import com.praya.myitems.builder.placeholder.ReplacerMVDWPlaceholderAPIBuild;
import com.praya.myitems.builder.placeholder.ReplacerPlaceholderAPIBuild;
import api.praya.agarthalib.main.AgarthaLibAPI;
import java.util.HashMap;
import java.util.Collection;
import com.praya.myitems.MyItems;
import com.praya.myitems.config.plugin.PlaceholderConfig;
import com.praya.myitems.builder.handler.HandlerManager;

public class PlaceholderManager extends HandlerManager
{
    private final PlaceholderConfig placeholderConfig;
    
    protected PlaceholderManager(final MyItems plugin) {
        super(plugin);
        this.placeholderConfig = new PlaceholderConfig(plugin);
    }
    
    public final PlaceholderConfig getPlaceholderConfig() {
        return this.placeholderConfig;
    }
    
    public final Collection<String> getPlaceholderIDs() {
        return this.getPlaceholderConfig().getPlaceholderIDs();
    }
    
    public final Collection<String> getPlaceholders() {
        return this.getPlaceholderConfig().getPlaceholders();
    }
    
    public final String getPlaceholder(final String id) {
        return this.getPlaceholderConfig().getPlaceholder(id);
    }
    
    public final HashMap<String, String> getPlaceholderCopy() {
        return this.getPlaceholderConfig().getPlaceholderCopy();
    }
    
    public final boolean isPlaceholderExists(final String id) {
        return this.getPlaceholder(id) != null;
    }
    
    public final void registerAll() {
        final AgarthaLibAPI agarthaLibAPI = AgarthaLibAPI.getInstance();
        final SupportManagerAPI supportManagerAPI = agarthaLibAPI.getPluginManagerAPI().getSupportManager();
        final String placeholder = this.plugin.getPluginPlaceholder();
        if (supportManagerAPI.isSupportPlaceholderAPI()) {
            new ReplacerPlaceholderAPIBuild(this.plugin, placeholder).hook();
        }
        if (supportManagerAPI.isSupportMVdWPlaceholder()) {
            new ReplacerMVDWPlaceholderAPIBuild(this.plugin, placeholder).register();
        }
    }
    
    public final List<String> localPlaceholder(final List<String> list) {
        final String divider = "\n";
        final String builder = TextUtil.convertListToString((List)list, "\n");
        final String text = this.localPlaceholder(builder);
        return (List<String>)ListUtil.convertStringToList(text, "\n");
    }
    
    public final String localPlaceholder(final String text) {
        return TextUtil.placeholder((HashMap)this.getPlaceholderCopy(), text);
    }
    
    public final List<String> pluginPlaceholder(final List<String> list, final String... identifiers) {
        return this.pluginPlaceholder(list, (Player)null, identifiers);
    }
    
    public final List<String> pluginPlaceholder(final List<String> list, final Player player, final String... identifiers) {
        final String divider = "\n";
        final String builder = TextUtil.convertListToString((List)list, "\n");
        final String text = this.pluginPlaceholder(builder, player, identifiers);
        return (List<String>)ListUtil.convertStringToList(text, "\n");
    }
    
    public final String pluginPlaceholder(final String text, final String... identifiers) {
        return this.pluginPlaceholder(text, (Player)null, identifiers);
    }
    
    public final String pluginPlaceholder(final String text, final Player player, final String... identifiers) {
        final HashMap<String, String> map = this.getMapPluginPlaceholder(player, identifiers);
        return TextUtil.placeholder((HashMap)map, text);
    }
    
    public final HashMap<String, String> getMapPluginPlaceholder(final String... identifiers) {
        return this.getMapPluginPlaceholder((Player)null, identifiers);
    }
    
    public final HashMap<String, String> getMapPluginPlaceholder(final Player player, final String... identifiers) {
        final String placeholder = this.plugin.getPluginPlaceholder();
        final HashMap<String, String> map = new HashMap<String, String>();
        for (final String identifier : identifiers) {
            final String replacement = this.getReplacement(player, identifier);
            if (replacement != null) {
                final String key = String.valueOf(placeholder) + "_" + identifier;
                map.put(key, replacement);
            }
        }
        return map;
    }
    
    public final ItemStack parseItem(final Player player, final ItemStack item) {
        final String divider = "\n";
        if (EquipmentUtil.hasDisplayName(item)) {
            final String oldDisplayName = EquipmentUtil.getDisplayName(item);
            final String newDisplayName = this.placeholder(player, oldDisplayName);
            EquipmentUtil.setDisplayName(item, newDisplayName);
        }
        if (EquipmentUtil.hasLore(item)) {
            final List<String> oldLores = (List<String>)EquipmentUtil.getLores(item);
            final String oldLineLore = TextUtil.convertListToString((List)oldLores, "\n");
            final String newLineLore = this.placeholder(player, oldLineLore);
            final String[] split = newLineLore.split("\n");
            final List<String> newLores = new ArrayList<String>();
            String[] array;
            for (int length = (array = split).length, i = 0; i < length; ++i) {
                final String lore = array[i];
                newLores.add(lore);
            }
            EquipmentUtil.setLores(item, (List)newLores);
        }
        return item;
    }
    
    public final List<String> placeholder(final Player player, final List<String> listText) {
        return this.placeholder(player, listText, null);
    }
    
    public final List<String> placeholder(final Player player, final List<String> listText, final LoreStatsModifier modifier) {
        return this.placeholder(player, listText, modifier, "{", "}");
    }
    
    public final List<String> placeholder(final Player player, final List<String> listText, final LoreStatsModifier modifier, final String leftKey, final String rightKey) {
        final List<String> list = new ArrayList<String>();
        if (listText != null) {
            final ListIterator<String> iteratorText = listText.listIterator();
            while (iteratorText.hasNext()) {
                final String text = iteratorText.next();
                final String textPlaceholder = this.placeholder(player, text, modifier, leftKey, rightKey);
                iteratorText.set(textPlaceholder);
            }
            list.addAll(listText);
        }
        return listText;
    }
    
    public final String placeholder(final Player player, final String text) {
        return this.placeholder(player, text, null);
    }
    
    public final String placeholder(final Player player, final String text, final LoreStatsModifier modifier) {
        return this.placeholder(player, text, modifier, "{", "}");
    }
    
    public final String placeholder(final Player player, String text, final LoreStatsModifier modifier, final String leftKey, final String rightKey) {
        final String placeholder = this.plugin.getPluginPlaceholder();
        if (text.contains(leftKey)) {
            final String[] fullPartFirst = text.split(Pattern.quote(leftKey));
            String[] array;
            for (int length = (array = fullPartFirst).length, i = 0; i < length; ++i) {
                final String checkPartFirst = array[i];
                if (checkPartFirst.contains(rightKey)) {
                    final String check = checkPartFirst.split(Pattern.quote(rightKey))[0];
                    if (check.contains("_")) {
                        final String[] elements = check.split("_", 2);
                        final String textholder = elements[0];
                        final String identifier = elements[1];
                        if (textholder.equalsIgnoreCase(placeholder)) {
                            final CharSequence replacement = this.getReplacement(player, identifier, modifier);
                            final CharSequence sequence = String.valueOf(String.valueOf(leftKey)) + check + rightKey;
                            if (replacement != null) {
                                text = text.replace(sequence, replacement);
                            }
                        }
                    }
                }
            }
        }
        return text;
    }
    
    public final String getReplacement(final Player player, final String identifier) {
        return this.getReplacement(player, identifier, null);
    }
    
    public final String getReplacement(final Player player, final String identifier, final LoreStatsModifier statsModifier) {
        final GameManager gameManager = this.plugin.getGameManager();
        final RegisterManager registerManager = this.plugin.getRegisterManager();
        final PowerManager powerManager = gameManager.getPowerManager();
        final PowerCommandManager powerCommandManager = powerManager.getPowerCommandManager();
        final PowerShootManager powerShootManager = powerManager.getPowerShootManager();
        final PowerSpecialManager powerSpecialManager = powerManager.getPowerSpecialManager();
        final AbilityWeaponManager abilityWeaponManager = gameManager.getAbilityWeaponManager();
        final ElementManager elementManager = gameManager.getElementManager();
        final SocketManager socketManager = gameManager.getSocketManager();
        final PassiveEffectManager passiveEffectManager = gameManager.getPassiveEffectManager();
        final LoreStatsManager statsManager = gameManager.getStatsManager();
        final RequirementManager requirementManager = gameManager.getRequirementManager();
        final RegisterAbilityWeaponManager registerAbilityWeaponManager = registerManager.getRegisterAbilityWeaponManager();
        final String[] parts = identifier.split(":");
        final int length = parts.length;
        if (length > 0) {
            final String key = parts[0];
            if (key.equalsIgnoreCase("text_lorestats")) {
                if (length >= 3) {
                    final String textLoreStats = parts[1];
                    final LoreStatsEnum loreStats = LoreStatsEnum.get(textLoreStats);
                    if (loreStats != null) {
                        final String textMinValue = parts[2];
                        final double modifier = (statsModifier != null) ? statsModifier.getModifier(loreStats) : 1.0;
                        double minValue3;
                        if (textMinValue.contains("~")) {
                            final String[] componentsMinValue = textMinValue.split("~");
                            final String textMinValue2 = componentsMinValue[0];
                            final String textMinValue3 = componentsMinValue[1];
                            if (!MathUtil.isNumber(textMinValue2) || !MathUtil.isNumber(textMinValue3)) {
                                return null;
                            }
                            final double minValue1 = MathUtil.parseDouble(textMinValue2);
                            final double minValue2 = MathUtil.parseDouble(textMinValue3);
                            minValue3 = MathUtil.valueBetween(minValue1, minValue2);
                        }
                        else {
                            if (!MathUtil.isNumber(textMinValue)) {
                                return null;
                            }
                            minValue3 = MathUtil.roundNumber(MathUtil.parseDouble(textMinValue));
                        }
                        double maxValue;
                        if (length == 3) {
                            maxValue = minValue3;
                        }
                        else {
                            final String textMaxValue = parts[3];
                            if (textMaxValue.contains("~")) {
                                final String[] componentsMaxValue = textMaxValue.split("~");
                                final String textMaxValue2 = componentsMaxValue[0];
                                final String textMaxValue3 = componentsMaxValue[1];
                                if (!MathUtil.isNumber(textMaxValue2) || !MathUtil.isNumber(textMaxValue3)) {
                                    return null;
                                }
                                final double maxValue2 = MathUtil.parseDouble(textMaxValue2);
                                final double maxValue3 = MathUtil.parseDouble(textMaxValue3);
                                maxValue = MathUtil.valueBetween(maxValue2, maxValue3);
                            }
                            else {
                                if (!MathUtil.isNumber(textMaxValue)) {
                                    return null;
                                }
                                maxValue = MathUtil.roundNumber(MathUtil.parseDouble(textMaxValue));
                            }
                        }
                        return statsManager.getTextLoreStats(loreStats, MathUtil.roundNumber(minValue3 * modifier, 2), MathUtil.roundNumber(maxValue * modifier, 2));
                    }
                }
            }
            else if (key.equalsIgnoreCase("text_ability")) {
                if (length >= 4) {
                    final String ability = parts[1];
                    final AbilityWeapon abilityWeapon = registerAbilityWeaponManager.getAbilityWeapon(ability);
                    if (abilityWeapon != null) {
                        final String textGrade = parts[2];
                        final String textChance = parts[3];
                        int grade3;
                        if (textGrade.contains("~")) {
                            final String[] componentsGrade = textGrade.split("~");
                            final String textGrade2 = componentsGrade[0];
                            final String textGrade3 = componentsGrade[1];
                            if (!MathUtil.isNumber(textGrade2) || !MathUtil.isNumber(textGrade3)) {
                                return null;
                            }
                            final int grade1 = MathUtil.parseInteger(textGrade2);
                            final int grade2 = MathUtil.parseInteger(textGrade3);
                            final int rawGrade = (int)MathUtil.valueBetween((double)grade1, (double)grade2);
                            grade3 = MathUtil.limitInteger(rawGrade, 1, rawGrade);
                        }
                        else {
                            if (!MathUtil.isNumber(textGrade)) {
                                return null;
                            }
                            final int rawGrade2 = MathUtil.parseInteger(textGrade);
                            grade3 = MathUtil.limitInteger(rawGrade2, 1, rawGrade2);
                        }
                        double chance3;
                        if (textChance.contains("~")) {
                            final String[] componentsChance = textChance.split("~");
                            final String textChance2 = componentsChance[0];
                            final String textChance3 = componentsChance[1];
                            if (!MathUtil.isNumber(textChance2) || !MathUtil.isNumber(textChance3)) {
                                return null;
                            }
                            final double chance1 = MathUtil.parseDouble(textChance2);
                            final double chance2 = MathUtil.parseDouble(textChance3);
                            chance3 = MathUtil.valueBetween(chance1, chance2);
                        }
                        else {
                            if (!MathUtil.isNumber(textChance)) {
                                return null;
                            }
                            chance3 = MathUtil.roundNumber(MathUtil.parseDouble(textChance));
                        }
                        return abilityWeaponManager.getTextAbility(ability, grade3, chance3);
                    }
                }
            }
            else if (key.equalsIgnoreCase("text_buff")) {
                if (length >= 3) {
                    final String textBuff = parts[1];
                    final PassiveEffectEnum effect = PassiveEffectEnum.get(textBuff);
                    if (effect != null) {
                        final String textGrade = parts[2];
                        int grade6;
                        if (textGrade.contains("~")) {
                            final String[] componentsGrade2 = textGrade.split("~");
                            final String textGrade4 = componentsGrade2[0];
                            final String textGrade5 = componentsGrade2[1];
                            if (!MathUtil.isNumber(textGrade4) || !MathUtil.isNumber(textGrade5)) {
                                return null;
                            }
                            final int grade4 = MathUtil.parseInteger(textGrade4);
                            final int grade5 = MathUtil.parseInteger(textGrade5);
                            grade6 = (int)MathUtil.valueBetween((double)grade4, (double)grade5);
                        }
                        else {
                            if (!MathUtil.isNumber(textGrade)) {
                                return null;
                            }
                            grade6 = MathUtil.parseInteger(textGrade);
                        }
                        return passiveEffectManager.getTextPassiveEffect(effect, grade6);
                    }
                }
            }
            else if (key.equalsIgnoreCase("text_power")) {
                if (length >= 4) {
                    final String textPower = parts[1];
                    final PowerEnum power = PowerEnum.get(textPower);
                    if (power != null) {
                        final String textClick = parts[2];
                        final PowerClickEnum click = PowerClickEnum.get(textClick);
                        if (click != null) {
                            final String textType = parts[3];
                            double cooldown;
                            if (length == 4) {
                                cooldown = 0.0;
                            }
                            else {
                                final String textCooldown = parts[4];
                                if (textCooldown.contains("~")) {
                                    final String[] componentsCooldown = textCooldown.split("~");
                                    final String textCooldown2 = componentsCooldown[0];
                                    final String textCooldown3 = componentsCooldown[1];
                                    if (!MathUtil.isNumber(textCooldown2) || !MathUtil.isNumber(textCooldown3)) {
                                        return null;
                                    }
                                    final double cooldown2 = MathUtil.parseDouble(textCooldown2);
                                    final double cooldown3 = MathUtil.parseDouble(textCooldown3);
                                    cooldown = MathUtil.valueBetween(cooldown2, cooldown3);
                                }
                                else {
                                    if (!MathUtil.isNumber(textCooldown)) {
                                        return null;
                                    }
                                    cooldown = MathUtil.roundNumber(MathUtil.parseDouble(textCooldown));
                                }
                            }
                            if (power.equals(PowerEnum.COMMAND)) {
                                final String type = powerCommandManager.getCommandKey(textType);
                                if (type != null) {
                                    return powerCommandManager.getTextPowerCommand(click, type, cooldown);
                                }
                            }
                            else if (power.equals(PowerEnum.SHOOT)) {
                                final ProjectileEnum type2 = ProjectileEnum.getProjectileEnum(textType);
                                if (type2 != null) {
                                    return powerShootManager.getTextPowerShoot(click, type2, cooldown);
                                }
                            }
                            else if (power.equals(PowerEnum.SPECIAL)) {
                                final PowerSpecialEnum type3 = PowerSpecialEnum.get(textType);
                                if (type3 != null) {
                                    return powerSpecialManager.getTextPowerSpecial(click, type3, cooldown);
                                }
                            }
                        }
                    }
                }
            }
            else if (key.equalsIgnoreCase("text_socket_empty")) {
                if (length >= 1) {
                    return socketManager.getTextSocketSlotEmpty();
                }
            }
            else if (key.equalsIgnoreCase("text_socket_fill")) {
                if (length >= 3) {
                    final String gems = parts[1];
                    if (socketManager.isExist(gems)) {
                        final String textGrade6 = parts[2];
                        int grade9;
                        if (textGrade6.contains("~")) {
                            final String[] componentsGrade3 = textGrade6.split("~");
                            final String textGrade7 = componentsGrade3[0];
                            final String textGrade8 = componentsGrade3[1];
                            if (!MathUtil.isNumber(textGrade7) || !MathUtil.isNumber(textGrade8)) {
                                return null;
                            }
                            final int grade7 = MathUtil.parseInteger(textGrade7);
                            final int grade8 = MathUtil.parseInteger(textGrade8);
                            final int rawGrade3 = (int)MathUtil.valueBetween((double)grade7, (double)grade8);
                            grade9 = MathUtil.limitInteger(rawGrade3, 1, rawGrade3);
                        }
                        else {
                            if (!MathUtil.isNumber(textGrade6)) {
                                return null;
                            }
                            grade9 = MathUtil.parseInteger(textGrade6);
                        }
                        return socketManager.getTextSocketGemsLore(gems, grade9);
                    }
                }
            }
            else if (key.equalsIgnoreCase("text_element") && length >= 3) {
                final String textElement = parts[1];
                if (elementManager.isExists(textElement)) {
                    final String textValue = parts[2];
                    double value3;
                    if (textValue.contains("~")) {
                        final String[] componentsValue = textValue.split("~");
                        final String textValue2 = componentsValue[0];
                        final String textValue3 = componentsValue[1];
                        if (!MathUtil.isNumber(textValue2) || !MathUtil.isNumber(textValue3)) {
                            return null;
                        }
                        final double value1 = MathUtil.parseDouble(textValue2);
                        final double value2 = MathUtil.parseDouble(textValue3);
                        value3 = MathUtil.valueBetween(value1, value2);
                    }
                    else {
                        if (!MathUtil.isNumber(textValue)) {
                            return null;
                        }
                        value3 = MathUtil.roundNumber(MathUtil.parseDouble(textValue));
                    }
                    return elementManager.getTextElement(textElement, value3);
                }
            }
            else {
                if (key.equalsIgnoreCase("text_requirement_unbound")) {
                    return requirementManager.getTextSoulUnbound();
                }
                if (key.equalsIgnoreCase("text_requirement_bound") && length >= 2) {
                    final String textBound = parts[1];
                    final OfflinePlayer bound = PlayerUtil.getPlayer(textBound);
                    if (bound != null) {
                        return bound.getName();
                    }
                }
                else {
                    if (key.equalsIgnoreCase("text_requirement_level") && length >= 2) {
                        final String textLevel = parts[1];
                        int level3;
                        if (textLevel.contains("~")) {
                            final String[] componentsLevel = textLevel.split("~");
                            final String textLevel2 = componentsLevel[0];
                            final String textLevel3 = componentsLevel[1];
                            if (!MathUtil.isNumber(textLevel2) || !MathUtil.isNumber(textLevel3)) {
                                return null;
                            }
                            final int level1 = MathUtil.parseInteger(textLevel2);
                            final int level2 = MathUtil.parseInteger(textLevel3);
                            final int rawLevel = (int)MathUtil.valueBetween((double)level1, (double)level2);
                            level3 = MathUtil.limitInteger(rawLevel, 0, rawLevel);
                        }
                        else {
                            if (!MathUtil.isNumber(textLevel)) {
                                return null;
                            }
                            level3 = MathUtil.parseInteger(textLevel);
                            level3 = MathUtil.limitInteger(level3, 0, level3);
                        }
                        return requirementManager.getTextLevel(level3);
                    }
                    if (key.equalsIgnoreCase("text_requirement_permission") && length >= 2) {
                        final String textPermission = parts[1];
                        return requirementManager.getTextPermission(textPermission);
                    }
                    if (key.equalsIgnoreCase("text_requirement_class") && length >= 2) {
                        final String textClass = parts[1];
                        return requirementManager.getTextClass(textClass);
                    }
                }
            }
        }
        return null;
    }
}
