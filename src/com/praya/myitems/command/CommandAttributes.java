// 
// Decompiled by Procyon v0.5.36
// 

package com.praya.myitems.command;

import org.bukkit.OfflinePlayer;
import api.praya.myitems.builder.ability.AbilityWeapon;
import org.bukkit.inventory.ItemStack;
import org.bukkit.entity.Player;
import core.praya.agarthalib.builder.message.MessageBuild;
import com.praya.myitems.manager.plugin.LanguageManager;
import com.praya.myitems.manager.register.RegisterAbilityWeaponManager;
import com.praya.myitems.manager.game.LoreStatsManager;
import com.praya.myitems.manager.game.PassiveEffectManager;
import com.praya.myitems.manager.game.RequirementManager;
import com.praya.myitems.manager.plugin.CommandManager;
import com.praya.myitems.manager.game.ElementManager;
import com.praya.myitems.manager.game.AbilityWeaponManager;
import com.praya.myitems.manager.game.PowerSpecialManager;
import com.praya.myitems.manager.game.PowerShootManager;
import com.praya.myitems.manager.game.PowerCommandManager;
import com.praya.myitems.manager.game.PowerManager;
import com.praya.myitems.manager.register.RegisterManager;
import com.praya.myitems.manager.game.GameManager;
import com.praya.myitems.manager.plugin.PluginManager;
import api.praya.myitems.requirement.RequirementEnum;
import api.praya.myitems.builder.power.PowerSpecialEnum;
import com.praya.myitems.utility.main.ProjectileUtil;
import core.praya.agarthalib.enums.branch.ProjectileEnum;
import api.praya.myitems.builder.power.PowerClickEnum;
import api.praya.myitems.builder.power.PowerEnum;
import core.praya.agarthalib.enums.main.TagsAttribute;
import com.praya.agarthalib.utility.ServerUtil;
import core.praya.agarthalib.enums.main.VersionNMS;
import core.praya.agarthalib.enums.main.RomanNumber;
import api.praya.myitems.builder.passive.PassiveTypeEnum;
import api.praya.myitems.builder.passive.PassiveEffectEnum;
import com.praya.myitems.utility.main.TriggerSupportUtil;
import java.util.HashMap;
import com.praya.agarthalib.utility.MathUtil;
import com.praya.agarthalib.utility.EquipmentUtil;
import api.praya.myitems.builder.lorestats.LoreStatsEnum;
import core.praya.agarthalib.enums.main.Slot;
import core.praya.agarthalib.bridge.unity.Bridge;
import com.praya.agarthalib.utility.PlayerUtil;
import com.praya.agarthalib.utility.SenderUtil;
import core.praya.agarthalib.enums.branch.SoundEnum;
import com.praya.agarthalib.utility.TextUtil;
import com.praya.myitems.config.plugin.MainConfig;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import com.praya.myitems.MyItems;
import org.bukkit.command.CommandExecutor;
import com.praya.myitems.builder.handler.HandlerCommand;

public class CommandAttributes extends HandlerCommand implements CommandExecutor
{
    public CommandAttributes(final MyItems plugin) {
        super(plugin);
    }
    
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        final PluginManager pluginManager = this.plugin.getPluginManager();
        final GameManager gameManager = this.plugin.getGameManager();
        final RegisterManager registerManager = this.plugin.getRegisterManager();
        final PowerManager powerManager = gameManager.getPowerManager();
        final PowerCommandManager powerCommandManager = powerManager.getPowerCommandManager();
        final PowerShootManager powerShootManager = powerManager.getPowerShootManager();
        final PowerSpecialManager powerSpecialManager = powerManager.getPowerSpecialManager();
        final AbilityWeaponManager abilityWeaponManager = gameManager.getAbilityWeaponManager();
        final ElementManager elementManager = gameManager.getElementManager();
        final CommandManager commandManager = pluginManager.getCommandManager();
        final RequirementManager requirementManager = gameManager.getRequirementManager();
        final PassiveEffectManager passiveEffectManager = gameManager.getPassiveEffectManager();
        final LoreStatsManager statsManager = gameManager.getStatsManager();
        final RegisterAbilityWeaponManager registerAbilityWeaponManager = registerManager.getRegisterAbilityWeaponManager();
        final LanguageManager lang = pluginManager.getLanguageManager();
        final MainConfig mainConfig = MainConfig.getInstance();
        if (args.length <= 0) {
            final String[] fullArgs = TextUtil.pressList(args, 2);
            return CommandMyItems.help(sender, command, label, fullArgs);
        }
        final String subCommand = args[0];
        if (commandManager.checkCommand(subCommand, "Attribute_Stats")) {
            if (!commandManager.checkPermission(sender, "Attribute_Stats")) {
                final String permission = commandManager.getPermission("Attribute_Stats");
                final MessageBuild message = lang.getMessage(sender, "Permission_Lack");
                message.sendMessage(sender, "permission", permission);
                SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                return true;
            }
            if (!SenderUtil.isPlayer(sender)) {
                final MessageBuild message2 = lang.getMessage(sender, "Console_Command_Forbiden");
                message2.sendMessage(sender);
                SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                return true;
            }
            if (args.length < 3) {
                final String tooltip = TextUtil.getJsonTooltip(lang.getText(sender, "Tooltip_Attribute_Stats"));
                final MessageBuild message = lang.getMessage(sender, "Argument_Attribute_Stats");
                message.sendMessage(sender, "tooltip_att_stats", tooltip);
                SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                return true;
            }
            final Player player = PlayerUtil.parse(sender);
            final ItemStack item = Bridge.getBridgeEquipment().getEquipment(player, Slot.MAINHAND);
            final LoreStatsEnum loreStats = LoreStatsEnum.get(args[1]);
            if (!EquipmentUtil.isSolid(item)) {
                final MessageBuild message3 = lang.getMessage(sender, "Item_MainHand_Empty");
                message3.sendMessage(sender);
                SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                return true;
            }
            if (loreStats == null) {
                final MessageBuild message3 = lang.getMessage(sender, "MyItems_Attribute_Stats_Not_Exists");
                message3.sendMessage(sender, "stats", args[1]);
                SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                return true;
            }
            final String textValue = args[2];
            double value1;
            double value2;
            if (args[2].contains(":")) {
                final String[] valueBuild = textValue.split(":");
                if (!MathUtil.isNumber(valueBuild[0]) || !MathUtil.isNumber(valueBuild[1])) {
                    final MessageBuild message4 = lang.getMessage(sender, "Argument_Invalid_Value");
                    message4.sendMessage(sender);
                    SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                    return true;
                }
                value1 = MathUtil.parseDouble(valueBuild[0]);
                value2 = MathUtil.parseDouble(valueBuild[1]);
            }
            else {
                if (!MathUtil.isNumber(textValue)) {
                    final MessageBuild message5 = lang.getMessage(sender, "Argument_Invalid_Value");
                    message5.sendMessage(sender);
                    SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                    return true;
                }
                value1 = MathUtil.parseDouble(args[2]);
                if (loreStats.equals(LoreStatsEnum.LEVEL)) {
                    value2 = 0.0;
                }
                else {
                    value2 = value1;
                }
            }
            int line;
            if (args.length > 3) {
                final String textLine = args[3];
                if (!MathUtil.isNumber(textLine)) {
                    final MessageBuild message4 = lang.getMessage(sender, "Argument_Invalid_Value");
                    message4.sendMessage(sender);
                    SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                    return true;
                }
                line = MathUtil.parseInteger(args[3]);
                if (line < 1) {
                    final MessageBuild message4 = lang.getMessage(sender, "Argument_Invalid_Value");
                    message4.sendMessage(sender);
                    SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                    return true;
                }
                if (EquipmentUtil.hasLore(item)) {
                    final int lastLine = statsManager.getLineLoreStats(item, loreStats);
                    if (lastLine != -1 && lastLine != line) {
                        EquipmentUtil.setLore(item, lastLine, "");
                    }
                }
            }
            else if (EquipmentUtil.hasLore(item)) {
                line = statsManager.getLineLoreStats(item, loreStats);
                if (line == -1) {
                    line = EquipmentUtil.getLores(item).size() + 1;
                }
            }
            else {
                line = 1;
            }
            final String lore = statsManager.getTextLoreStats(loreStats, value1, value2);
            final MessageBuild message4 = lang.getMessage(sender, "MyItems_Attribute_Stats_Success");
            final HashMap<String, String> mapPlaceholder = new HashMap<String, String>();
            mapPlaceholder.put("stats", loreStats.getText());
            mapPlaceholder.put("value", statsManager.statsValue(loreStats, value1, value2));
            EquipmentUtil.setLore(item, line, lore);
            TriggerSupportUtil.updateSupport(player);
            message4.sendMessage(sender, (HashMap)mapPlaceholder);
            SenderUtil.playSound(sender, SoundEnum.ENTITY_EXPERIENCE_ORB_PICKUP);
            player.updateInventory();
            return true;
        }
        else if (commandManager.checkCommand(subCommand, "Attribute_Element")) {
            if (!commandManager.checkPermission(sender, "Attribute_Element")) {
                final String permission = commandManager.getPermission("Attribute_Element");
                final MessageBuild message = lang.getMessage(sender, "Permission_Lack");
                message.sendMessage(sender, "permission", permission);
                SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                return true;
            }
            if (!SenderUtil.isPlayer(sender)) {
                final MessageBuild message2 = lang.getMessage(sender, "Console_Command_Forbiden");
                message2.sendMessage(sender);
                SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                return true;
            }
            if (args.length < 3) {
                final String tooltip = TextUtil.getJsonTooltip(lang.getText(sender, "Tooltip_Attribute_Element"));
                final MessageBuild message = lang.getMessage(sender, "Argument_Attribute_Element");
                message.sendMessage(sender, "tooltip_att_element", tooltip);
                SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                return true;
            }
            final Player player = PlayerUtil.parse(sender);
            final ItemStack item = Bridge.getBridgeEquipment().getEquipment(player, Slot.MAINHAND);
            final String element = elementManager.getRawElement(args[1]);
            if (!EquipmentUtil.isSolid(item)) {
                final MessageBuild message3 = lang.getMessage(sender, "Item_MainHand_Empty");
                message3.sendMessage(sender);
                SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                return true;
            }
            if (element == null) {
                final MessageBuild message3 = lang.getMessage(sender, "MyItems_Attribute_Element_Not_Exists");
                message3.sendMessage(sender, "element", args[1]);
                SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                return true;
            }
            final String textValue = args[2];
            if (!MathUtil.isNumber(textValue)) {
                final MessageBuild message6 = lang.getMessage(sender, "Argument_Invalid_Value");
                message6.sendMessage(sender);
                SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                return true;
            }
            final double value3 = MathUtil.parseDouble(textValue);
            int line2;
            if (args.length > 3) {
                final String textLine2 = args[3];
                if (!MathUtil.isNumber(textLine2)) {
                    final MessageBuild message7 = lang.getMessage(sender, "Argument_Invalid_Value");
                    message7.sendMessage(sender);
                    SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                    return true;
                }
                line2 = MathUtil.parseInteger(args[3]);
                if (line2 < 1) {
                    final MessageBuild message7 = lang.getMessage(sender, "Argument_Invalid_Value");
                    message7.sendMessage(sender);
                    SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                    return true;
                }
                if (EquipmentUtil.hasLore(item)) {
                    final int lastLine2 = elementManager.getLineElement(item, element);
                    if (lastLine2 != -1 && lastLine2 != line2) {
                        EquipmentUtil.setLore(item, lastLine2, "");
                    }
                }
            }
            else if (EquipmentUtil.hasLore(item)) {
                line2 = elementManager.getLineElement(item, element);
                if (line2 == -1) {
                    line2 = EquipmentUtil.getLores(item).size() + 1;
                }
            }
            else {
                line2 = 1;
            }
            final String lore2 = elementManager.getTextElement(element, value3);
            final MessageBuild message7 = lang.getMessage(sender, "MyItems_Attribute_Element_Success");
            final HashMap<String, String> mapPlaceholder2 = new HashMap<String, String>();
            mapPlaceholder2.put("element", element);
            mapPlaceholder2.put("value", String.valueOf(value3));
            EquipmentUtil.setLore(item, line2, lore2);
            message7.sendMessage(sender, (HashMap)mapPlaceholder2);
            SenderUtil.playSound(sender, SoundEnum.ENTITY_EXPERIENCE_ORB_PICKUP);
            player.updateInventory();
            return true;
        }
        else if (commandManager.checkCommand(subCommand, "Attribute_Buff")) {
            if (!commandManager.checkPermission(sender, "Attribute_Buff")) {
                final String permission = commandManager.getPermission("Attribute_Buff");
                final MessageBuild message = lang.getMessage(sender, "Permission_Lack");
                message.sendMessage(sender, "permission", permission);
                SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                return true;
            }
            if (!SenderUtil.isPlayer(sender)) {
                final MessageBuild message2 = lang.getMessage(sender, "Console_Command_Forbiden");
                message2.sendMessage(sender);
                SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                return true;
            }
            if (args.length < 3) {
                final String tooltip = TextUtil.getJsonTooltip(lang.getText(sender, "Tooltip_Attribute_Buffs"));
                final MessageBuild message = lang.getMessage(sender, "Argument_Attribute_Buffs");
                message.sendMessage(sender, "tooltip_att_buffs", tooltip);
                SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                return true;
            }
            final Player player = PlayerUtil.parse(sender);
            final ItemStack item = Bridge.getBridgeEquipment().getEquipment(player, Slot.MAINHAND);
            final PassiveEffectEnum effect = PassiveEffectEnum.get(args[1]);
            if (!EquipmentUtil.isSolid(item)) {
                final MessageBuild message3 = lang.getMessage(sender, "Item_MainHand_Empty");
                message3.sendMessage(sender);
                SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                return true;
            }
            if (effect != null) {
                if (effect.getType().equals(PassiveTypeEnum.BUFF)) {
                    final String textGrade = args[2];
                    if (!MathUtil.isNumber(textGrade)) {
                        final MessageBuild message8 = lang.getMessage(sender, "Argument_Invalid_Value");
                        message8.sendMessage(sender);
                        SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                        return true;
                    }
                    int grade = MathUtil.parseInteger(textGrade);
                    grade = MathUtil.limitInteger(grade, 1, 10);
                    int line3;
                    if (args.length > 3) {
                        final String textLine3 = args[3];
                        if (!MathUtil.isNumber(textLine3)) {
                            final MessageBuild message6 = lang.getMessage(sender, "Argument_Invalid_Value");
                            message6.sendMessage(sender);
                            SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                            return true;
                        }
                        line3 = MathUtil.parseInteger(args[3]);
                        if (line3 < 1) {
                            final MessageBuild message6 = lang.getMessage(sender, "Argument_Invalid_Value");
                            message6.sendMessage(sender);
                            SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                            return true;
                        }
                        if (EquipmentUtil.hasLore(item)) {
                            final int lastLine3 = passiveEffectManager.getLinePassiveEffect(item, effect);
                            if (lastLine3 != -1 && lastLine3 != line3) {
                                EquipmentUtil.setLore(item, lastLine3, "");
                            }
                        }
                    }
                    else if (EquipmentUtil.hasLore(item)) {
                        line3 = passiveEffectManager.getLinePassiveEffect(item, effect);
                        if (line3 == -1) {
                            line3 = EquipmentUtil.getLores(item).size() + 1;
                        }
                    }
                    else {
                        line3 = 1;
                    }
                    final String lore3 = passiveEffectManager.getTextPassiveEffect(effect, grade);
                    final MessageBuild message6 = lang.getMessage(sender, "MyItems_Attribute_Buffs_Success");
                    final HashMap<String, String> mapPlaceholder3 = new HashMap<String, String>();
                    mapPlaceholder3.put("buff", effect.getText());
                    mapPlaceholder3.put("buffs", effect.getText());
                    mapPlaceholder3.put("grade", RomanNumber.getRomanNumber(grade));
                    EquipmentUtil.setLore(item, line3, lore3);
                    message6.sendMessage(sender, (HashMap)mapPlaceholder3);
                    SenderUtil.playSound(sender, SoundEnum.ENTITY_EXPERIENCE_ORB_PICKUP);
                    player.updateInventory();
                    return true;
                }
            }
            final MessageBuild message3 = lang.getMessage(sender, "MyItems_Attribute_Buffs_Not_Exists");
            message3.sendMessage(sender, "buff", args[1]);
            SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
            return true;
        }
        else if (commandManager.checkCommand(subCommand, "Attribute_Debuff")) {
            if (!commandManager.checkPermission(sender, "Attribute_Debuff")) {
                final String permission = commandManager.getPermission("Attribute_Debuff");
                final MessageBuild message = lang.getMessage(sender, "Permission_Lack");
                message.sendMessage(sender, "permission", permission);
                SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                return true;
            }
            if (!SenderUtil.isPlayer(sender)) {
                final MessageBuild message2 = lang.getMessage(sender, "Console_Command_Forbiden");
                message2.sendMessage(sender);
                SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                return true;
            }
            if (args.length < 3) {
                final String tooltip = TextUtil.getJsonTooltip(lang.getText(sender, "Tooltip_Attribute_Debuffs"));
                final MessageBuild message = lang.getMessage(sender, "Argument_Attribute_Debuffs");
                message.sendMessage(sender, "tooltip_att_debuffs", tooltip);
                SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                return true;
            }
            final Player player = PlayerUtil.parse(sender);
            final ItemStack item = Bridge.getBridgeEquipment().getEquipment(player, Slot.MAINHAND);
            final PassiveEffectEnum effect = PassiveEffectEnum.get(args[1]);
            if (!EquipmentUtil.isSolid(item)) {
                final MessageBuild message3 = lang.getMessage(sender, "Item_MainHand_Empty");
                message3.sendMessage(sender);
                SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                return true;
            }
            if (effect != null) {
                if (effect.getType().equals(PassiveTypeEnum.DEBUFF)) {
                    final String textGrade = args[2];
                    if (!MathUtil.isNumber(textGrade)) {
                        final MessageBuild message8 = lang.getMessage(sender, "Argument_Invalid_Value");
                        message8.sendMessage(sender);
                        SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                        return true;
                    }
                    int grade = MathUtil.parseInteger(textGrade);
                    grade = MathUtil.limitInteger(grade, 1, 10);
                    int line3;
                    if (args.length > 3) {
                        final String textLine3 = args[3];
                        if (!MathUtil.isNumber(textLine3)) {
                            final MessageBuild message6 = lang.getMessage(sender, "Argument_Invalid_Value");
                            message6.sendMessage(sender);
                            SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                            return true;
                        }
                        line3 = MathUtil.parseInteger(args[3]);
                        if (line3 < 1) {
                            final MessageBuild message6 = lang.getMessage(sender, "Argument_Invalid_Value");
                            message6.sendMessage(sender);
                            SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                            return true;
                        }
                        if (EquipmentUtil.hasLore(item)) {
                            final int lastLine3 = passiveEffectManager.getLinePassiveEffect(item, effect);
                            if (lastLine3 != -1 && lastLine3 != line3) {
                                EquipmentUtil.setLore(item, lastLine3, "");
                            }
                        }
                    }
                    else if (EquipmentUtil.hasLore(item)) {
                        line3 = passiveEffectManager.getLinePassiveEffect(item, effect);
                        if (line3 == -1) {
                            line3 = EquipmentUtil.getLores(item).size() + 1;
                        }
                    }
                    else {
                        line3 = 1;
                    }
                    final String lore3 = passiveEffectManager.getTextPassiveEffect(effect, grade);
                    final MessageBuild message6 = lang.getMessage(sender, "MyItems_Attribute_Debuffs_Success");
                    final HashMap<String, String> mapPlaceholder3 = new HashMap<String, String>();
                    mapPlaceholder3.put("debuff", effect.getText());
                    mapPlaceholder3.put("debuffs", effect.getText());
                    mapPlaceholder3.put("grade", RomanNumber.getRomanNumber(grade));
                    EquipmentUtil.setLore(item, line3, lore3);
                    message6.sendMessage(sender, (HashMap)mapPlaceholder3);
                    SenderUtil.playSound(sender, SoundEnum.ENTITY_EXPERIENCE_ORB_PICKUP);
                    player.updateInventory();
                    return true;
                }
            }
            final MessageBuild message3 = lang.getMessage(sender, "MyItems_Attribute_Debuffs_Not_Exists");
            message3.sendMessage(sender, "debuff", args[1]);
            SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
            return true;
        }
        else if (commandManager.checkCommand(subCommand, "Attribute_NBT")) {
            if (!ServerUtil.isCompatible(VersionNMS.V1_8_R3)) {
                final MessageBuild message2 = lang.getMessage(sender, "MyItems_Not_Compatible");
                message2.sendMessage(sender);
                SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                return true;
            }
            if (!commandManager.checkPermission(sender, "Attribute_NBT")) {
                final String permission = commandManager.getPermission("Attribute_NBT");
                final MessageBuild message = lang.getMessage(sender, "Permission_Lack");
                message.sendMessage(sender, "permission", permission);
                SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                return true;
            }
            if (!SenderUtil.isPlayer(sender)) {
                final MessageBuild message2 = lang.getMessage(sender, "Console_Command_Forbiden");
                message2.sendMessage(sender);
                SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                return true;
            }
            if (args.length < 3) {
                final String tooltip = TextUtil.getJsonTooltip(lang.getText(sender, "Tooltip_Attribute_NBT"));
                final MessageBuild message = lang.getMessage(sender, "Argument_Attribute_NBT");
                message.sendMessage(sender, "tooltip_att_nbt", tooltip);
                SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                return true;
            }
            final Player player = PlayerUtil.parse(sender);
            final ItemStack item = Bridge.getBridgeEquipment().getEquipment(player, Slot.MAINHAND);
            final TagsAttribute tags = TagsAttribute.getTagsAttribute(args[1]);
            if (!EquipmentUtil.isSolid(item)) {
                final MessageBuild message3 = lang.getMessage(sender, "Item_MainHand_Empty");
                message3.sendMessage(sender);
                SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                return true;
            }
            if (tags == null) {
                final MessageBuild message3 = lang.getMessage(sender, "MyItems_Attribute_NBT_Not_Exists");
                final HashMap<String, String> mapPlaceholder4 = new HashMap<String, String>();
                mapPlaceholder4.put("NBT", args[1]);
                mapPlaceholder4.put("Tags", args[1]);
                message3.sendMessage(sender, (HashMap)mapPlaceholder4);
                SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                return true;
            }
            final String textValue = args[2];
            if (!MathUtil.isNumber(textValue)) {
                final MessageBuild message9 = lang.getMessage(sender, "Argument_Invalid_Value");
                message9.sendMessage(sender);
                SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                return true;
            }
            final double value3 = MathUtil.parseDouble(textValue);
            Slot slot;
            if (args.length > 3) {
                final String textSlot = args[3];
                slot = Slot.get(textSlot);
                if (slot == null) {
                    slot = Slot.getDefault(item.getType());
                }
            }
            else {
                slot = Slot.getDefault(item.getType());
            }
            final MessageBuild message6 = lang.getMessage(sender, "MyItems_Attribute_NBT_Success");
            final HashMap<String, String> mapPlaceholder3 = new HashMap<String, String>();
            mapPlaceholder3.put("NBT", TextUtil.firstSolidCharacter(String.valueOf(tags)));
            mapPlaceholder3.put("Value", String.valueOf(value3));
            Bridge.getBridgeTagsNBT().addNBT(item, tags, value3, slot);
            message6.sendMessage(sender, (HashMap)mapPlaceholder3);
            SenderUtil.playSound(sender, SoundEnum.ENTITY_EXPERIENCE_ORB_PICKUP);
            player.updateInventory();
            return true;
        }
        else if (commandManager.checkCommand(subCommand, "Attribute_Ability")) {
            if (!commandManager.checkPermission(sender, "Attribute_Ability")) {
                final String permission = commandManager.getPermission("Attribute_Ability");
                final MessageBuild message = lang.getMessage(sender, "Permission_Lack");
                message.sendMessage(sender, "permission", permission);
                SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                return true;
            }
            if (!SenderUtil.isPlayer(sender)) {
                final MessageBuild message2 = lang.getMessage(sender, "Console_Command_Forbiden");
                message2.sendMessage(sender);
                SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                return true;
            }
            if (args.length < 3) {
                final String tooltip = TextUtil.getJsonTooltip(lang.getText(sender, "Tooltip_Attribute_Ability"));
                final MessageBuild message = lang.getMessage(sender, "Argument_Attribute_Ability");
                message.sendMessage(sender, "tooltip_att_ability", tooltip);
                SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                return true;
            }
            final String ability = args[1];
            final Player player2 = PlayerUtil.parse(sender);
            final ItemStack item2 = Bridge.getBridgeEquipment().getEquipment(player2, Slot.MAINHAND);
            final AbilityWeapon abilityWeapon = registerAbilityWeaponManager.getAbilityWeapon(ability);
            if (!EquipmentUtil.isSolid(item2)) {
                final MessageBuild message9 = lang.getMessage(sender, "Item_MainHand_Empty");
                message9.sendMessage(sender);
                SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                return true;
            }
            if (abilityWeapon == null) {
                final MessageBuild message9 = lang.getMessage(sender, "MyItems_Attribute_Ability_Not_Exists");
                message9.sendMessage(sender, "ability", args[1]);
                SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                return true;
            }
            final String textGrade2 = args[2];
            double chance;
            if (args.length > 3) {
                final String textChance = args[3];
                if (!MathUtil.isNumber(textChance)) {
                    final MessageBuild message4 = lang.getMessage(sender, "Argument_Invalid_Value");
                    message4.sendMessage(sender);
                    SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                    return true;
                }
                chance = MathUtil.parseDouble(textChance);
                chance = MathUtil.roundNumber(chance);
                chance = MathUtil.limitDouble(chance, 0.0, 100.0);
            }
            else {
                chance = 100.0;
            }
            if (!MathUtil.isNumber(textGrade2)) {
                final MessageBuild message5 = lang.getMessage(sender, "Argument_Invalid_Value");
                message5.sendMessage(sender);
                SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                return true;
            }
            final int maxGrade = abilityWeapon.getMaxGrade();
            int grade2 = MathUtil.parseInteger(textGrade2);
            grade2 = MathUtil.limitInteger(grade2, 1, maxGrade);
            int line;
            if (args.length > 4) {
                final String textLine = args[4];
                if (!MathUtil.isNumber(textLine)) {
                    final MessageBuild message4 = lang.getMessage(sender, "Argument_Invalid_Value");
                    message4.sendMessage(sender);
                    SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                    return true;
                }
                line = MathUtil.parseInteger(args[4]);
                if (line < 1) {
                    final MessageBuild message4 = lang.getMessage(sender, "Argument_Invalid_Value");
                    message4.sendMessage(sender);
                    SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                    return true;
                }
                if (EquipmentUtil.hasLore(item2)) {
                    final Integer abilityLine = abilityWeaponManager.getLineAbilityItemWeapon(item2, ability);
                    if (abilityLine != null && abilityLine != line) {
                        EquipmentUtil.setLore(item2, (int)abilityLine, "");
                    }
                }
            }
            else if (EquipmentUtil.hasLore(item2)) {
                final Integer abilityLine2 = abilityWeaponManager.getLineAbilityItemWeapon(item2, ability);
                line = ((abilityLine2 != null) ? abilityLine2 : (EquipmentUtil.getLores(item2).size() + 1));
            }
            else {
                line = 1;
            }
            final String lore = abilityWeaponManager.getTextAbility(ability, grade2, chance);
            final MessageBuild message4 = lang.getMessage(sender, "MyItems_Attribute_Ability_Success");
            final HashMap<String, String> mapPlaceholder = new HashMap<String, String>();
            mapPlaceholder.put("ability", abilityWeapon.getKeyLore());
            mapPlaceholder.put("grade", RomanNumber.getRomanNumber(grade2));
            EquipmentUtil.setLore(item2, line, lore);
            message4.sendMessage(sender, (HashMap)mapPlaceholder);
            SenderUtil.playSound(sender, SoundEnum.ENTITY_EXPERIENCE_ORB_PICKUP);
            player2.updateInventory();
            return true;
        }
        else if (commandManager.checkCommand(subCommand, "Attribute_Power")) {
            if (!commandManager.checkPermission(sender, "Attribute_Power")) {
                final String permission = commandManager.getPermission("Attribute_Power");
                final MessageBuild message = lang.getMessage(sender, "Permission_Lack");
                message.sendMessage(sender, "permission", permission);
                SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                return true;
            }
            if (!SenderUtil.isPlayer(sender)) {
                final MessageBuild message2 = lang.getMessage(sender, "Console_Command_Forbiden");
                message2.sendMessage(sender);
                SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                return true;
            }
            if (args.length < 4) {
                final String tooltip = TextUtil.getJsonTooltip(lang.getText(sender, "Tooltip_Attribute_Power"));
                final MessageBuild message = lang.getMessage(sender, "Argument_Attribute_Power");
                message.sendMessage(sender, "tooltip_att_power", tooltip);
                SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                return true;
            }
            final Player player = PlayerUtil.parse(sender);
            final ItemStack item = Bridge.getBridgeEquipment().getEquipment(player, Slot.MAINHAND);
            final PowerEnum power = PowerEnum.get(args[1]);
            final PowerClickEnum click = PowerClickEnum.get(args[2]);
            if (!EquipmentUtil.isSolid(item)) {
                final MessageBuild message6 = lang.getMessage(sender, "Item_MainHand_Empty");
                message6.sendMessage(sender);
                SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                return true;
            }
            if (power == null) {
                final MessageBuild message6 = lang.getMessage(sender, "MyItems_Attribute_Power_Not_Exists");
                message6.sendMessage(sender, "Power", args[1]);
                SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                return true;
            }
            if (click == null) {
                final MessageBuild message6 = lang.getMessage(sender, "Utility_Slot_Not_Exists");
                message6.sendMessage(sender, "slot", args[2]);
                SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                return true;
            }
            double cooldown;
            if (args.length > 4) {
                final String textCooldown = args[4];
                if (!MathUtil.isNumber(textCooldown)) {
                    final MessageBuild message7 = lang.getMessage(sender, "Argument_Invalid_Value");
                    message7.sendMessage(sender);
                    SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                    return true;
                }
                cooldown = MathUtil.parseDouble(textCooldown);
                cooldown = MathUtil.limitDouble(cooldown, 0.0, cooldown);
            }
            else {
                cooldown = 0.0;
            }
            int line2;
            if (args.length > 5) {
                final String textLine2 = args[5];
                if (!MathUtil.isNumber(textLine2)) {
                    final MessageBuild message7 = lang.getMessage(sender, "Argument_Invalid_Value");
                    message7.sendMessage(sender);
                    SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                    return true;
                }
                line2 = MathUtil.parseInteger(textLine2);
                if (line2 < 1) {
                    final MessageBuild message7 = lang.getMessage(sender, "Argument_Invalid_Value");
                    message7.sendMessage(sender);
                    SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                    return true;
                }
                if (EquipmentUtil.hasLore(item)) {
                    final int lastLine2 = powerManager.getLineClick(item, click);
                    if (lastLine2 != -1 && lastLine2 != line2) {
                        EquipmentUtil.setLore(item, lastLine2, "");
                    }
                }
            }
            else if (EquipmentUtil.hasLore(item)) {
                line2 = powerManager.getLineClick(item, click);
                if (line2 == -1) {
                    line2 = EquipmentUtil.getLores(item).size() + 1;
                }
            }
            else {
                line2 = 1;
            }
            if (power.equals(PowerEnum.COMMAND)) {
                final String powerCommand = args[3];
                if (!powerCommandManager.isPowerCommandExists(powerCommand)) {
                    final MessageBuild message7 = lang.getMessage(sender, "MyItems_Attribute_Power_Command_Not_Exists");
                    message7.sendMessage(sender, "Command", powerCommand);
                    SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                    return true;
                }
                final String keyCommand = powerCommandManager.getCommandKey(args[3]);
                final String lore = powerCommandManager.getTextPowerCommand(click, keyCommand, cooldown);
                final MessageBuild message4 = lang.getMessage(sender, "MyItems_Attribute_Power_Command_Success");
                EquipmentUtil.setLore(item, line2, lore);
                message4.sendMessage(sender, "command", keyCommand);
                SenderUtil.playSound(sender, SoundEnum.ENTITY_EXPERIENCE_ORB_PICKUP);
                player.updateInventory();
                return true;
            }
            else if (power.equals(PowerEnum.SHOOT)) {
                final String powerShoot = args[3];
                final ProjectileEnum projectile = ProjectileEnum.getProjectileEnum(powerShoot);
                if (projectile == null) {
                    final MessageBuild message5 = lang.getMessage(sender, "Myitems_Attribute_Power_Shoot_Not_Exists");
                    message5.sendMessage(sender, "projectile", powerShoot);
                    SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                    return true;
                }
                final String lore = powerShootManager.getTextPowerShoot(click, projectile, cooldown);
                final MessageBuild message4 = lang.getMessage(sender, "Myitems_Attribute_Power_Shoot_Success");
                EquipmentUtil.setLore(item, line2, lore);
                message4.sendMessage(sender, "projectile", ProjectileUtil.getText(projectile));
                SenderUtil.playSound(sender, SoundEnum.ENTITY_EXPERIENCE_ORB_PICKUP);
                player.updateInventory();
                return true;
            }
            else {
                if (!power.equals(PowerEnum.SPECIAL)) {
                    final MessageBuild message6 = lang.getMessage(sender, "Argument_Invalid_Command");
                    message6.sendMessage(sender);
                    SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                    return true;
                }
                final String powerSpecial = args[3];
                if (PowerSpecialEnum.get(powerSpecial) == null) {
                    final MessageBuild message7 = lang.getMessage(sender, "MyItems_Attribute_Power_Special_Not_Exists");
                    message7.sendMessage(sender, "special", powerSpecial);
                    SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                    return true;
                }
                final PowerSpecialEnum special = PowerSpecialEnum.get(powerSpecial);
                final String lore = powerSpecialManager.getTextPowerSpecial(click, special, cooldown);
                final MessageBuild message4 = lang.getMessage(sender, "MyItems_Attribute_Power_Special_Success");
                EquipmentUtil.setLore(item, line2, lore);
                message4.sendMessage(sender, "special", special.getText());
                SenderUtil.playSound(sender, SoundEnum.ENTITY_EXPERIENCE_ORB_PICKUP);
                player.updateInventory();
                return true;
            }
        }
        else {
            if (!commandManager.checkCommand(subCommand, "Attribute_Requirement")) {
                final MessageBuild message2 = lang.getMessage(sender, "Argument_Invalid_Command");
                message2.sendMessage(sender);
                SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                return true;
            }
            if (!commandManager.checkPermission(sender, "Attribute_Requirement")) {
                final String permission = commandManager.getPermission("Attribute_Requirement");
                final MessageBuild message = lang.getMessage(sender, "Permission_Lack");
                message.sendMessage(sender, "permission", permission);
                SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                return true;
            }
            if (!SenderUtil.isPlayer(sender)) {
                final MessageBuild message2 = lang.getMessage(sender, "Console_Command_Forbiden");
                message2.sendMessage(sender);
                SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                return true;
            }
            if (args.length < 2) {
                final String tooltip = TextUtil.getJsonTooltip(lang.getText(sender, "Tooltip_Attribute_Req"));
                final MessageBuild message = lang.getMessage(sender, "Argument_Attribute_Requirement");
                message.sendMessage(sender, "tooltip_att_req", tooltip);
                SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                return true;
            }
            final Player player = PlayerUtil.parse(sender);
            final ItemStack item = Bridge.getBridgeEquipment().getEquipment(player, Slot.MAINHAND);
            final RequirementEnum requirementEnum = RequirementEnum.getRequirement(args[1]);
            if (requirementEnum.equals(RequirementEnum.REQUIREMENT_SOUL_UNBOUND)) {
                final String format = mainConfig.getRequirementFormatSoulUnbound();
                int line4;
                if (args.length > 2) {
                    final String textLine4 = args[2];
                    if (!MathUtil.isNumber(textLine4)) {
                        final MessageBuild message8 = lang.getMessage(sender, "Argument_Invalid_Value");
                        message8.sendMessage(sender);
                        SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                        return true;
                    }
                    line4 = MathUtil.parseInteger(textLine4);
                    if (line4 < 1) {
                        final MessageBuild message8 = lang.getMessage(sender, "Argument_Invalid_Value");
                        message8.sendMessage(sender);
                        SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                        return true;
                    }
                    if (EquipmentUtil.hasLore(item)) {
                        final Integer lastLine4 = requirementManager.getLineRequirementSoulUnbound(item);
                        if (lastLine4 != null && lastLine4 != line4) {
                            EquipmentUtil.setLore(item, (int)lastLine4, "");
                        }
                    }
                }
                else if (EquipmentUtil.hasLore(item)) {
                    final Integer lineReq = requirementManager.getLineRequirementSoulUnbound(item);
                    final int loreSize = EquipmentUtil.getLores(item).size();
                    line4 = ((lineReq != null) ? lineReq : (loreSize + 1));
                }
                else {
                    line4 = 1;
                }
                final MessageBuild message10 = lang.getMessage(sender, "MyItems_Attribute_Requirement_Unbound_Success");
                EquipmentUtil.setLore(item, line4, format);
                message10.sendMessage(sender, "line", String.valueOf(line4));
                SenderUtil.playSound(sender, SoundEnum.ENTITY_EXPERIENCE_ORB_PICKUP);
                player.updateInventory();
                return true;
            }
            if (requirementEnum.equals(RequirementEnum.REQUIREMENT_SOUL_BOUND)) {
                OfflinePlayer bound;
                if (args.length > 2) {
                    final String textBound = args[2];
                    bound = (OfflinePlayer)(PlayerUtil.isOnline(textBound) ? PlayerUtil.getOnlinePlayer(textBound) : PlayerUtil.getPlayer(textBound));
                    if (bound == null) {
                        final MessageBuild message8 = lang.getMessage(sender, "Player_Not_Exists");
                        message8.sendMessage(sender, "Player", textBound);
                        SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                        return true;
                    }
                }
                else {
                    bound = (OfflinePlayer)player;
                }
                int line4;
                if (args.length > 3) {
                    final String textLine4 = args[3];
                    if (!MathUtil.isNumber(textLine4)) {
                        final MessageBuild message8 = lang.getMessage(sender, "Argument_Invalid_Value");
                        message8.sendMessage(sender);
                        SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                        return true;
                    }
                    line4 = MathUtil.parseInteger(textLine4);
                    if (line4 < 1) {
                        final MessageBuild message8 = lang.getMessage(sender, "Argument_Invalid_Value");
                        message8.sendMessage(sender);
                        SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                        return true;
                    }
                    if (EquipmentUtil.hasLore(item)) {
                        final Integer lastLine4 = requirementManager.getLineRequirementSoulBound(item);
                        if (lastLine4 != null && lastLine4 != line4) {
                            EquipmentUtil.setLore(item, (int)lastLine4, "");
                        }
                    }
                }
                else if (EquipmentUtil.hasLore(item)) {
                    final Integer lineReq = requirementManager.getLineRequirementSoulBound(item);
                    final int loreSize = EquipmentUtil.getLores(item).size();
                    line4 = ((lineReq != null) ? lineReq : (loreSize + 1));
                }
                else {
                    line4 = 1;
                }
                final String lore4 = requirementManager.getTextSoulBound(bound);
                final MessageBuild message8 = lang.getMessage(sender, "MyItems_Attribute_Requirement_Bound_Success");
                final HashMap<String, String> mapPlaceholder5 = new HashMap<String, String>();
                mapPlaceholder5.put("player", bound.getName());
                mapPlaceholder5.put("line", String.valueOf(line4));
                requirementManager.setMetadataSoulbound(bound, item);
                EquipmentUtil.setLore(item, line4, lore4);
                message8.sendMessage(sender, (HashMap)mapPlaceholder5);
                SenderUtil.playSound(sender, SoundEnum.ENTITY_EXPERIENCE_ORB_PICKUP);
                player.updateInventory();
                return true;
            }
            if (requirementEnum.equals(RequirementEnum.REQUIREMENT_PERMISSION)) {
                if (args.length < 3) {
                    final String tooltip2 = TextUtil.getJsonTooltip(lang.getText(sender, "Tooltip_Attribute_Req_Permission"));
                    final MessageBuild message9 = lang.getMessage(sender, "Argument_Attribute_Requirement_Permission");
                    message9.sendMessage(sender, "tooltip_att_req_permission", tooltip2);
                    SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                    return true;
                }
                final String permission2 = args[2];
                int line4;
                if (args.length > 3) {
                    final String textLine4 = args[3];
                    if (!MathUtil.isNumber(textLine4)) {
                        final MessageBuild message8 = lang.getMessage(sender, "Argument_Invalid_Value");
                        message8.sendMessage(sender);
                        SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                        return true;
                    }
                    line4 = MathUtil.parseInteger(textLine4);
                    if (line4 < 1) {
                        final MessageBuild message8 = lang.getMessage(sender, "Argument_Invalid_Value");
                        message8.sendMessage(sender);
                        SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                        return true;
                    }
                    if (EquipmentUtil.hasLore(item)) {
                        final Integer lastLine4 = requirementManager.getLineRequirementPermission(item);
                        if (lastLine4 != null && lastLine4 != line4) {
                            EquipmentUtil.setLore(item, (int)lastLine4, "");
                        }
                    }
                }
                else if (EquipmentUtil.hasLore(item)) {
                    final Integer lineReq = requirementManager.getLineRequirementPermission(item);
                    final int loreSize = EquipmentUtil.getLores(item).size();
                    line4 = ((lineReq != null) ? lineReq : (loreSize + 1));
                }
                else {
                    line4 = 1;
                }
                final String lore4 = requirementManager.getTextPermission(permission2);
                final MessageBuild message8 = lang.getMessage(sender, "MyItems_Attribute_Requirement_Permission_Success");
                final HashMap<String, String> mapPlaceholder5 = new HashMap<String, String>();
                mapPlaceholder5.put("permission", permission2);
                mapPlaceholder5.put("line", String.valueOf(line4));
                EquipmentUtil.setLore(item, line4, lore4);
                message8.sendMessage(sender, (HashMap)mapPlaceholder5);
                SenderUtil.playSound(sender, SoundEnum.ENTITY_EXPERIENCE_ORB_PICKUP);
                player.updateInventory();
                return true;
            }
            else if (requirementEnum.equals(RequirementEnum.REQUIREMENT_LEVEL)) {
                if (args.length < 3) {
                    final String tooltip2 = TextUtil.getJsonTooltip(lang.getText(sender, "Tooltip_Attribute_Req_Level"));
                    final MessageBuild message9 = lang.getMessage(sender, "Argument_Attribute_Level_Permission");
                    message9.sendMessage(sender, "tooltip_att_req_level", tooltip2);
                    SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                    return true;
                }
                final String textLevel = args[2];
                if (MathUtil.isNumber(textLevel)) {
                    int level = MathUtil.parseInteger(textLevel);
                    level = MathUtil.limitInteger(level, 0, level);
                    int line3;
                    if (args.length > 3) {
                        final String textLine3 = args[3];
                        if (!MathUtil.isNumber(textLine3)) {
                            final MessageBuild message6 = lang.getMessage(sender, "Argument_Invalid_Value");
                            message6.sendMessage(sender);
                            SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                            return true;
                        }
                        line3 = MathUtil.parseInteger(textLine3);
                        if (line3 < 1) {
                            final MessageBuild message6 = lang.getMessage(sender, "Argument_Invalid_Value");
                            message6.sendMessage(sender);
                            SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                            return true;
                        }
                        if (EquipmentUtil.hasLore(item)) {
                            final Integer lastLine5 = requirementManager.getLineRequirementLevel(item);
                            if (lastLine5 != null && lastLine5 != line3) {
                                EquipmentUtil.setLore(item, (int)lastLine5, "");
                            }
                        }
                    }
                    else if (EquipmentUtil.hasLore(item)) {
                        final Integer lineReq2 = requirementManager.getLineRequirementLevel(item);
                        final int loreSize2 = EquipmentUtil.getLores(item).size();
                        line3 = ((lineReq2 != null) ? lineReq2 : (loreSize2 + 1));
                    }
                    else {
                        line3 = 1;
                    }
                    final String lore3 = requirementManager.getTextLevel(level);
                    final MessageBuild message6 = lang.getMessage(sender, "MyItems_Attribute_Requirement_Level_Success");
                    final HashMap<String, String> mapPlaceholder3 = new HashMap<String, String>();
                    mapPlaceholder3.put("level", String.valueOf(level));
                    mapPlaceholder3.put("line", String.valueOf(line3));
                    EquipmentUtil.setLore(item, line3, lore3);
                    message6.sendMessage(sender, (HashMap)mapPlaceholder3);
                    SenderUtil.playSound(sender, SoundEnum.ENTITY_EXPERIENCE_ORB_PICKUP);
                    player.updateInventory();
                    return true;
                }
                final MessageBuild message8 = lang.getMessage(sender, "Argument_Invalid_Value");
                message8.sendMessage(sender);
                SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                return true;
            }
            else {
                if (!requirementEnum.equals(RequirementEnum.REQUIREMENT_CLASS)) {
                    final String tooltip2 = TextUtil.getJsonTooltip(lang.getText(sender, "Tooltip_Attribute_Req"));
                    final MessageBuild message9 = lang.getMessage(sender, "Argument_Attribute_Requirement");
                    message9.sendMessage(sender, "tooltip_att_req", tooltip2);
                    SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                    return true;
                }
                if (!requirementManager.isSupportReqClass()) {
                    final MessageBuild message3 = lang.getMessage(sender, "Argument_Not_Support_Class");
                    message3.sendMessage(sender);
                    SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                    return true;
                }
                if (args.length < 3) {
                    final String tooltip2 = TextUtil.getJsonTooltip(lang.getText(sender, "Tooltip_Attribute_Req_Class"));
                    final MessageBuild message9 = lang.getMessage(sender, "Argument_Attribute_Class_Permission");
                    message9.sendMessage(sender, "tooltip_att_req_class", tooltip2);
                    SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                    return true;
                }
                final String textClass = args[2];
                int line4;
                if (args.length > 3) {
                    final String textLine4 = args[3];
                    if (!MathUtil.isNumber(textLine4)) {
                        final MessageBuild message8 = lang.getMessage(sender, "Argument_Invalid_Value");
                        message8.sendMessage(sender);
                        SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                        return true;
                    }
                    line4 = MathUtil.parseInteger(textLine4);
                    if (line4 < 1) {
                        final MessageBuild message8 = lang.getMessage(sender, "Argument_Invalid_Value");
                        message8.sendMessage(sender);
                        SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                        return true;
                    }
                    if (EquipmentUtil.hasLore(item)) {
                        final Integer lastLine4 = requirementManager.getLineRequirementClass(item);
                        if (lastLine4 != null && lastLine4 != line4) {
                            EquipmentUtil.setLore(item, (int)lastLine4, "");
                        }
                    }
                }
                else if (EquipmentUtil.hasLore(item)) {
                    final Integer lineReq = requirementManager.getLineRequirementClass(item);
                    final int loreSize = EquipmentUtil.getLores(item).size();
                    line4 = ((lineReq != null) ? lineReq : (loreSize + 1));
                }
                else {
                    line4 = 1;
                }
                final String lore4 = requirementManager.getTextClass(textClass);
                final MessageBuild message8 = lang.getMessage(sender, "MyItems_Attribute_Requirement_Class_Success");
                final HashMap<String, String> mapPlaceholder5 = new HashMap<String, String>();
                mapPlaceholder5.put("class", textClass);
                mapPlaceholder5.put("line", String.valueOf(line4));
                EquipmentUtil.setLore(item, line4, lore4);
                message8.sendMessage(sender, (HashMap)mapPlaceholder5);
                SenderUtil.playSound(sender, SoundEnum.ENTITY_EXPERIENCE_ORB_PICKUP);
                player.updateInventory();
                return true;
            }
        }
    }
}
