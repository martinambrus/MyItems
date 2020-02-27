// 
// Decompiled by Procyon v0.5.36
// 

package com.praya.myitems.command;

import java.util.Collection;
import com.praya.agarthalib.utility.SortUtil;
import java.util.ArrayList;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.List;
import api.praya.myitems.builder.socket.SocketGems;
import api.praya.myitems.builder.socket.SocketGemsTree;
import org.bukkit.World;
import org.bukkit.inventory.ItemStack;
import core.praya.agarthalib.builder.message.MessageBuild;
import com.praya.myitems.manager.plugin.LanguageManager;
import com.praya.myitems.manager.plugin.CommandManager;
import com.praya.myitems.manager.game.SocketManager;
import com.praya.myitems.manager.game.GameManager;
import com.praya.myitems.manager.plugin.PluginManager;
import org.bukkit.entity.LivingEntity;
import core.praya.agarthalib.enums.main.RomanNumber;
import org.bukkit.Location;
import com.praya.agarthalib.utility.WorldUtil;
import org.bukkit.entity.Player;
import java.util.HashMap;
import com.praya.agarthalib.utility.MathUtil;
import com.praya.agarthalib.utility.EquipmentUtil;
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

public class CommandSocket extends HandlerCommand implements CommandExecutor
{
    public CommandSocket(final MyItems plugin) {
        super(plugin);
    }
    
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        final PluginManager pluginManager = this.plugin.getPluginManager();
        final GameManager gameManager = this.plugin.getGameManager();
        final SocketManager socketManager = gameManager.getSocketManager();
        final CommandManager commandManager = pluginManager.getCommandManager();
        final LanguageManager lang = pluginManager.getLanguageManager();
        final MainConfig mainConfig = MainConfig.getInstance();
        if (args.length <= 0) {
            final String[] fullArgs = TextUtil.pressList(args, 2);
            return CommandMyItems.help(sender, command, label, fullArgs);
        }
        final String subCommand = args[0];
        if (commandManager.checkCommand(subCommand, "Socket_Add")) {
            if (!commandManager.checkPermission(sender, "Socket_Add")) {
                final String permission = commandManager.getPermission("Socket_Add");
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
                final String tooltip = TextUtil.getJsonTooltip(lang.getText(sender, "Tooltip_Socket_Add"));
                final MessageBuild message = lang.getMessage(sender, "Argument_Socket_Add");
                message.sendMessage(sender, "tooltip_socket_add", tooltip);
                SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                return true;
            }
            final String slotType = args[1];
            final Player player = PlayerUtil.parse(sender);
            final ItemStack item = Bridge.getBridgeEquipment().getEquipment(player, Slot.MAINHAND);
            if (!EquipmentUtil.isSolid(item)) {
                final MessageBuild message3 = lang.getMessage(sender, "Item_MainHand_Empty");
                message3.sendMessage(sender);
                SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                return true;
            }
            int line = 1;
            String lore;
            String type;
            if (slotType.equalsIgnoreCase("Empty") || slotType.equalsIgnoreCase("Unlock")) {
                lore = socketManager.getTextSocketSlotEmpty();
                type = lang.getText(sender, "Socket_Slot_Type_Empty");
            }
            else {
                if (!slotType.equalsIgnoreCase("Locked") && !slotType.equalsIgnoreCase("Lock")) {
                    final String tooltip2 = TextUtil.getJsonTooltip(lang.getText(sender, "Tooltip_Socket_Add"));
                    final MessageBuild message4 = lang.getMessage(sender, "Argument_Socket_Add");
                    message4.sendMessage(sender, "tooltip_socket_add", tooltip2);
                    SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                    return true;
                }
                lore = socketManager.getTextSocketSlotLocked();
                type = lang.getText(sender, "Socket_Slot_Type_Locked");
            }
            if (EquipmentUtil.loreCheck(item)) {
                line = EquipmentUtil.getLores(item).size() + 1;
            }
            if (args.length > 2) {
                final String textLine = args[2];
                if (!MathUtil.isNumber(textLine)) {
                    final MessageBuild message4 = lang.getMessage(sender, "Argument_Invalid_Value");
                    message4.sendMessage(sender);
                    SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                    return true;
                }
                line = MathUtil.parseInteger(textLine);
                if (line < 1) {
                    final MessageBuild message4 = lang.getMessage(sender, "Argument_Invalid_Value");
                    message4.sendMessage(sender);
                    SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                    return true;
                }
            }
            final MessageBuild message5 = lang.getMessage(sender, "MyItems_Socket_Add_Slot_Success");
            final HashMap<String, String> mapPlaceholder = new HashMap<String, String>();
            mapPlaceholder.put("line", String.valueOf(line));
            mapPlaceholder.put("type", type);
            EquipmentUtil.setLore(item, line, lore);
            SenderUtil.playSound(sender, SoundEnum.ENTITY_EXPERIENCE_ORB_PICKUP);
            message5.sendMessage(sender, (HashMap)mapPlaceholder);
            player.updateInventory();
            return true;
        }
        else if (commandManager.checkCommand(subCommand, "Socket_Drop")) {
            if (!commandManager.checkPermission(sender, "Socket_Drop")) {
                final String permission = commandManager.getPermission("Socket_Drop");
                final MessageBuild message = lang.getMessage(sender, "Permission_Lack");
                message.sendMessage(sender, "permission", permission);
                SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                return true;
            }
            if (args.length < 2) {
                final String tooltip = TextUtil.getJsonTooltip(lang.getText(sender, "Tooltip_Socket_Drop"));
                final MessageBuild message = lang.getMessage(sender, "Argument_Socket_Drop");
                message.sendMessage(sender, "tooltip_socket_drop", tooltip);
                SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                return true;
            }
            final String dropType = args[1];
            if (commandManager.checkCommand(dropType, "Socket_Drop_Rod")) {
                if (!commandManager.checkPermission(sender, "Socket_Drop_Rod")) {
                    final String permission2 = commandManager.getPermission("Socket_Drop_Rod");
                    final MessageBuild message6 = lang.getMessage(sender, "Permission_Lack");
                    message6.sendMessage(sender, "permission", permission2);
                    SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                    return true;
                }
                if (args.length < ((sender instanceof Player) ? 3 : 7)) {
                    final String tooltip3 = TextUtil.getJsonTooltip(lang.getText(sender, "Tooltip_Socket_Drop_Rod"));
                    final MessageBuild message6 = lang.getMessage(sender, "Argument_Socket_Drop_Rod");
                    message6.sendMessage(sender, "tooltip_socket_drop_rod", tooltip3);
                    SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                    return true;
                }
                final String textRod = args[2];
                ItemStack itemRod;
                if (textRod.equalsIgnoreCase("Unlock")) {
                    itemRod = mainConfig.getSocketItemRodUnlock();
                }
                else {
                    if (!textRod.equalsIgnoreCase("Remove")) {
                        final String tooltip4 = TextUtil.getJsonTooltip(lang.getText(sender, "Tooltip_Socket_Drop_Rod"));
                        final MessageBuild message7 = lang.getMessage(sender, "Argument_Socket_Drop_Rod");
                        message7.sendMessage(sender, "tooltip_socket_drop_rod", tooltip4);
                        SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                        return true;
                    }
                    itemRod = mainConfig.getSocketItemRodRemove();
                }
                World world;
                if (args.length > 3) {
                    final String textWorld = args[3];
                    if (textWorld.equalsIgnoreCase("~") && sender instanceof Player) {
                        final Player player2 = (Player)sender;
                        world = player2.getWorld();
                    }
                    else {
                        world = WorldUtil.getWorld(textWorld);
                    }
                }
                else {
                    final Player player3 = (Player)sender;
                    world = player3.getWorld();
                }
                if (world == null) {
                    final MessageBuild message8 = lang.getMessage(sender, "MyItems_World_Not_Exists");
                    message8.sendMessage(sender, "world", args[3]);
                    SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                    return true;
                }
                double x;
                if (args.length > 4) {
                    final String textX = args[4];
                    if (!MathUtil.isNumber(textX)) {
                        if (!textX.equalsIgnoreCase("~") || !(sender instanceof Player)) {
                            final MessageBuild message9 = lang.getMessage(sender, "Argument_Invalid_Value");
                            message9.sendMessage(sender);
                            SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                            return true;
                        }
                        final Player player4 = (Player)sender;
                        final Location location = player4.getLocation();
                        x = location.getX();
                    }
                    else {
                        x = MathUtil.parseDouble(textX);
                    }
                }
                else {
                    final Player player5 = (Player)sender;
                    final Location location2 = player5.getLocation();
                    x = location2.getX();
                }
                double y;
                if (args.length > 5) {
                    final String textY = args[5];
                    if (!MathUtil.isNumber(textY)) {
                        if (!textY.equalsIgnoreCase("~") || !(sender instanceof Player)) {
                            final MessageBuild message9 = lang.getMessage(sender, "Argument_Invalid_Value");
                            message9.sendMessage(sender);
                            SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                            return true;
                        }
                        final Player player4 = (Player)sender;
                        final Location location = player4.getLocation();
                        y = location.getY();
                    }
                    else {
                        y = MathUtil.parseDouble(textY);
                    }
                }
                else {
                    final Player player5 = (Player)sender;
                    final Location location2 = player5.getLocation();
                    y = location2.getY();
                }
                double z;
                if (args.length > 6) {
                    final String textZ = args[6];
                    if (!MathUtil.isNumber(textZ)) {
                        if (!textZ.equalsIgnoreCase("~") || !(sender instanceof Player)) {
                            final MessageBuild message9 = lang.getMessage(sender, "Argument_Invalid_Value");
                            message9.sendMessage(sender);
                            SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                            return true;
                        }
                        final Player player4 = (Player)sender;
                        final Location location = player4.getLocation();
                        z = location.getZ();
                    }
                    else {
                        z = MathUtil.parseDouble(textZ);
                    }
                }
                else {
                    final Player player5 = (Player)sender;
                    final Location location2 = player5.getLocation();
                    z = location2.getZ();
                }
                int amount;
                if (args.length > 7) {
                    final String textAmount = args[7];
                    if (!MathUtil.isNumber(textAmount)) {
                        final MessageBuild message9 = lang.getMessage(sender, "Argument_Invalid_Value");
                        message9.sendMessage(sender);
                        SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                        return true;
                    }
                    amount = MathUtil.parseInteger(textAmount);
                }
                else {
                    amount = 1;
                }
                final Location location3 = new Location(world, x, y, z);
                if (SenderUtil.isPlayer(sender)) {
                    final MessageBuild message9 = lang.getMessage(sender, "MyItems_Socket_Drop_Rod_Success");
                    final HashMap<String, String> mapPlaceholder2 = new HashMap<String, String>();
                    mapPlaceholder2.put("rod", EquipmentUtil.getDisplayName(itemRod));
                    mapPlaceholder2.put("amount", String.valueOf(amount));
                    mapPlaceholder2.put("world", world.getName());
                    mapPlaceholder2.put("x", String.valueOf(x));
                    mapPlaceholder2.put("y", String.valueOf(y));
                    mapPlaceholder2.put("z", String.valueOf(z));
                    message9.sendMessage(sender, (HashMap)mapPlaceholder2);
                    SenderUtil.playSound(sender, SoundEnum.ENTITY_EXPERIENCE_ORB_PICKUP);
                }
                itemRod.setAmount(amount);
                world.dropItem(location3, itemRod);
                return true;
            }
            else {
                if (!commandManager.checkCommand(dropType, "Socket_Drop_Gems")) {
                    final String tooltip3 = TextUtil.getJsonTooltip(lang.getText(sender, "Tooltip_Socket_Drop"));
                    final MessageBuild message6 = lang.getMessage(sender, "Argument_Socket_Drop");
                    message6.sendMessage(sender, "tooltip_socket_drop", tooltip3);
                    SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                    return true;
                }
                if (!commandManager.checkPermission(sender, "Socket_Drop_Gems")) {
                    final String permission2 = commandManager.getPermission("Socket_Drop_Gems");
                    final MessageBuild message6 = lang.getMessage(sender, "Permission_Lack");
                    message6.sendMessage(sender, "permission", permission2);
                    SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                    return true;
                }
                if (args.length < 8) {
                    final String tooltip3 = TextUtil.getJsonTooltip(lang.getText(sender, "Tooltip_Socket_Drop"));
                    final MessageBuild message6 = lang.getMessage(sender, "Argument_Socket_Drop_Gems");
                    message6.sendMessage(sender, "tooltip_socket_drop_gems", tooltip3);
                    SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                    return true;
                }
                if (args[2].contains(".")) {
                    final MessageBuild message = lang.getMessage(sender, "Character_Special");
                    message.sendMessage(sender);
                    SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                    return true;
                }
                final SocketGemsTree socketTree = socketManager.getSocketTree(args[2]);
                if (socketTree == null) {
                    final MessageBuild message6 = lang.getMessage(sender, "Item_Not_Exist");
                    message6.sendMessage(sender, "nameid", args[1]);
                    SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                    return true;
                }
                final String textGrade = args[3];
                if (!MathUtil.isNumber(textGrade)) {
                    final MessageBuild message7 = lang.getMessage(sender, "Argument_Invalid_Value");
                    message7.sendMessage(sender);
                    SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                    return true;
                }
                final int maxGrade = socketTree.getMaxGrade();
                final int grade = MathUtil.limitInteger(MathUtil.parseInteger(textGrade), 1, maxGrade);
                World world;
                if (args.length > 4) {
                    final String textWorld2 = args[4];
                    if (textWorld2.equalsIgnoreCase("~") && sender instanceof Player) {
                        final Player player6 = (Player)sender;
                        world = player6.getWorld();
                    }
                    else {
                        world = WorldUtil.getWorld(textWorld2);
                    }
                }
                else {
                    final Player player2 = (Player)sender;
                    world = player2.getWorld();
                }
                if (world == null) {
                    final MessageBuild message7 = lang.getMessage(sender, "MyItems_World_Not_Exists");
                    message7.sendMessage(sender, "world", args[4]);
                    SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                    return true;
                }
                double x2;
                if (args.length > 5) {
                    final String textX2 = args[5];
                    if (!MathUtil.isNumber(textX2)) {
                        if (!textX2.equalsIgnoreCase("~") || !(sender instanceof Player)) {
                            final MessageBuild message10 = lang.getMessage(sender, "Argument_Invalid_Value");
                            message10.sendMessage(sender);
                            SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                            return true;
                        }
                        final Player player7 = (Player)sender;
                        final Location location4 = player7.getLocation();
                        x2 = location4.getX();
                    }
                    else {
                        x2 = MathUtil.parseDouble(textX2);
                    }
                }
                else {
                    final Player player4 = (Player)sender;
                    final Location location = player4.getLocation();
                    x2 = location.getX();
                }
                double y2;
                if (args.length > 6) {
                    final String textY2 = args[6];
                    if (!MathUtil.isNumber(textY2)) {
                        if (!textY2.equalsIgnoreCase("~") || !(sender instanceof Player)) {
                            final MessageBuild message10 = lang.getMessage(sender, "Argument_Invalid_Value");
                            message10.sendMessage(sender);
                            SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                            return true;
                        }
                        final Player player7 = (Player)sender;
                        final Location location4 = player7.getLocation();
                        y2 = location4.getY();
                    }
                    else {
                        y2 = MathUtil.parseDouble(textY2);
                    }
                }
                else {
                    final Player player4 = (Player)sender;
                    final Location location = player4.getLocation();
                    y2 = location.getY();
                }
                double z2;
                if (args.length > 7) {
                    final String textZ2 = args[7];
                    if (!MathUtil.isNumber(textZ2)) {
                        if (!textZ2.equalsIgnoreCase("~") || !(sender instanceof Player)) {
                            final MessageBuild message10 = lang.getMessage(sender, "Argument_Invalid_Value");
                            message10.sendMessage(sender);
                            SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                            return true;
                        }
                        final Player player7 = (Player)sender;
                        final Location location4 = player7.getLocation();
                        z2 = location4.getZ();
                    }
                    else {
                        z2 = MathUtil.parseDouble(textZ2);
                    }
                }
                else {
                    final Player player4 = (Player)sender;
                    final Location location = player4.getLocation();
                    z2 = location.getZ();
                }
                int amount2;
                if (args.length > 8) {
                    final String textAmount2 = args[8];
                    if (!MathUtil.isNumber(textAmount2)) {
                        final MessageBuild message10 = lang.getMessage(sender, "Argument_Invalid_Value");
                        message10.sendMessage(sender);
                        SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                        return true;
                    }
                    amount2 = MathUtil.parseInteger(textAmount2);
                }
                else {
                    amount2 = 1;
                }
                final SocketGems socketGems = socketTree.getSocketBuild(grade);
                final ItemStack item2 = socketGems.getItem();
                final Location location4 = new Location(world, x2, y2, z2);
                if (SenderUtil.isPlayer(sender)) {
                    final MessageBuild message11 = lang.getMessage(sender, "MyItems_Socket_Drop_Gems_Success");
                    final HashMap<String, String> mapPlaceholder3 = new HashMap<String, String>();
                    mapPlaceholder3.put("amount", String.valueOf(amount2));
                    mapPlaceholder3.put("nameid", socketTree.getGems());
                    mapPlaceholder3.put("grade", String.valueOf(grade));
                    mapPlaceholder3.put("world", world.getName());
                    mapPlaceholder3.put("x", String.valueOf(x2));
                    mapPlaceholder3.put("y", String.valueOf(y2));
                    mapPlaceholder3.put("z", String.valueOf(z2));
                    message11.sendMessage(sender, (HashMap)mapPlaceholder3);
                    SenderUtil.playSound(sender, SoundEnum.ENTITY_EXPERIENCE_ORB_PICKUP);
                }
                item2.setAmount(amount2);
                world.dropItem(location4, item2);
                return true;
            }
        }
        else if (commandManager.checkCommand(subCommand, "Socket_Load")) {
            if (!commandManager.checkPermission(sender, "Socket_Load")) {
                final String permission = commandManager.getPermission("Socket_Load");
                final MessageBuild message = lang.getMessage(sender, "Permission_Lack");
                message.sendMessage(sender, "permission", permission);
                SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                return true;
            }
            if (args.length < 2) {
                final String tooltip = TextUtil.getJsonTooltip(lang.getText(sender, "Tooltip_Socket_Load"));
                final MessageBuild message = lang.getMessage(sender, "Argument_Socket_Load");
                message.sendMessage(sender, "tooltip_socket_load", tooltip);
                SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                return true;
            }
            final String loadType = args[1];
            if (commandManager.checkCommand(loadType, "Socket_Load_Gems")) {
                if (!commandManager.checkPermission(sender, "Socket_Load_Gems")) {
                    final String permission2 = commandManager.getPermission("Socket_Load_Gems");
                    final MessageBuild message6 = lang.getMessage(sender, "Permission_Lack");
                    message6.sendMessage(sender, "permission", permission2);
                    SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                    return true;
                }
                if (args.length < (SenderUtil.isPlayer(sender) ? 3 : 5)) {
                    final String tooltip3 = TextUtil.getJsonTooltip(lang.getText(sender, "Tooltip_Socket_Load_Gems"));
                    final MessageBuild message6 = lang.getMessage(sender, "Argument_Socket_Load_Gems");
                    message6.sendMessage(sender, "tooltip_socket_load_gems", tooltip3);
                    SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                    return true;
                }
                if (args[2].contains(".")) {
                    final MessageBuild message = lang.getMessage(sender, "Contains_Special_Character");
                    message.sendMessage(sender);
                    SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                    return true;
                }
                final SocketGemsTree socketTree = socketManager.getSocketTree(args[2]);
                if (socketTree == null) {
                    final MessageBuild message6 = lang.getMessage(sender, "Item_Not_Exist");
                    message6.sendMessage(sender, "nameid", args[2]);
                    SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                    return true;
                }
                int grade2;
                if (args.length > 3) {
                    final String textGrade2 = args[3];
                    if (!MathUtil.isNumber(textGrade2)) {
                        final MessageBuild message5 = lang.getMessage(sender, "Argument_Invalid_Value");
                        message5.sendMessage(sender);
                        SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                        return true;
                    }
                    final int maxGrade2 = socketTree.getMaxGrade();
                    grade2 = MathUtil.limitInteger(MathUtil.parseInteger(textGrade2), 1, maxGrade2);
                }
                else {
                    grade2 = 1;
                }
                Player target;
                if (args.length > 4) {
                    final String nameTarget = args[4];
                    if (!PlayerUtil.isOnline(nameTarget)) {
                        final MessageBuild message5 = lang.getMessage(sender, "Player_Target_Offline");
                        message5.sendMessage(sender);
                        SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                        return true;
                    }
                    target = PlayerUtil.getOnlinePlayer(nameTarget);
                }
                else {
                    target = PlayerUtil.parse(sender);
                }
                int amount3;
                if (args.length > 5) {
                    final String textAmount3 = args[5];
                    if (!MathUtil.isNumber(textAmount3)) {
                        final MessageBuild message5 = lang.getMessage(sender, "Argument_Invalid_Value");
                        message5.sendMessage(sender);
                        SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                        return true;
                    }
                    amount3 = Math.max(1, MathUtil.parseInteger(textAmount3));
                }
                else {
                    amount3 = 1;
                }
                final ItemStack item3 = socketTree.getSocketBuild(grade2).getItem();
                if (target.equals(sender)) {
                    final MessageBuild message5 = lang.getMessage(sender, "MyItems_Socket_Load_Gems_Success_Self");
                    final HashMap<String, String> mapPlaceholder = new HashMap<String, String>();
                    mapPlaceholder.put("amount", String.valueOf(amount3));
                    mapPlaceholder.put("nameID", socketTree.getGems());
                    mapPlaceholder.put("grade", String.valueOf(RomanNumber.getRomanNumber(grade2)));
                    EquipmentUtil.setAmount(item3, amount3);
                    PlayerUtil.addItem(target, item3);
                    message5.sendMessage(sender, (HashMap)mapPlaceholder);
                    SenderUtil.playSound(sender, SoundEnum.ENTITY_EXPERIENCE_ORB_PICKUP);
                    return true;
                }
                final MessageBuild messageToSender = lang.getMessage(sender, "MyItems_Socket_Load_Gems_Success_To_Sender");
                final MessageBuild messageToTarget = lang.getMessage((LivingEntity)target, "MyItems_Socket_Load_Gems_Success_To_Target");
                final HashMap<String, String> mapPlaceholder4 = new HashMap<String, String>();
                mapPlaceholder4.put("nameID", socketTree.getGems());
                mapPlaceholder4.put("grade", String.valueOf(RomanNumber.getRomanNumber(grade2)));
                mapPlaceholder4.put("amount", String.valueOf(amount3));
                mapPlaceholder4.put("target", target.getName());
                mapPlaceholder4.put("sender", sender.getName());
                EquipmentUtil.setAmount(item3, amount3);
                PlayerUtil.addItem(target, item3);
                messageToSender.sendMessage(sender, (HashMap)mapPlaceholder4);
                messageToTarget.sendMessage((CommandSender)target, (HashMap)mapPlaceholder4);
                SenderUtil.playSound(sender, SoundEnum.ENTITY_EXPERIENCE_ORB_PICKUP);
                SenderUtil.playSound((CommandSender)target, SoundEnum.ENTITY_EXPERIENCE_ORB_PICKUP);
                return true;
            }
            else {
                if (!commandManager.checkCommand(loadType, "Socket_Load_Rod")) {
                    final String tooltip3 = TextUtil.getJsonTooltip(lang.getText(sender, "Tooltip_Socket_Load"));
                    final MessageBuild message6 = lang.getMessage(sender, "Argument_Socket_Load");
                    message6.sendMessage(sender, "tooltip_socket_load", tooltip3);
                    SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                    return true;
                }
                if (!commandManager.checkPermission(sender, "Socket_Load_Rod")) {
                    final String permission2 = commandManager.getPermission("Socket_Load_Rod");
                    final MessageBuild message6 = lang.getMessage(sender, "Permission_Lack");
                    message6.sendMessage(sender, "permission", permission2);
                    SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                    return true;
                }
                if (args.length < (SenderUtil.isPlayer(sender) ? 3 : 4)) {
                    final String tooltip3 = TextUtil.getJsonTooltip(lang.getText(sender, "Tooltip_Socket_Load_Rod"));
                    final MessageBuild message6 = lang.getMessage(sender, "Argument_Socket_Load_Rod");
                    message6.sendMessage(sender, "tooltip_socket_load_rod", tooltip3);
                    SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                    return true;
                }
                final String textRod = args[2];
                ItemStack rod;
                if (textRod.equalsIgnoreCase("Unlock")) {
                    rod = mainConfig.getSocketItemRodUnlock();
                }
                else {
                    if (!textRod.equalsIgnoreCase("Remove")) {
                        final String tooltip5 = TextUtil.getJsonTooltip(lang.getText(sender, "Tooltip_Socket_Load_Rod"));
                        final MessageBuild message5 = lang.getMessage(sender, "Argument_Socket_Load_Rod");
                        message5.sendMessage(sender, "tooltip_socket_load_rod", tooltip5);
                        SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                        return true;
                    }
                    rod = mainConfig.getSocketItemRodRemove();
                }
                Player target2;
                if (args.length > 3) {
                    final String nameTarget = args[3];
                    if (!PlayerUtil.isOnline(nameTarget)) {
                        final MessageBuild message5 = lang.getMessage(sender, "Player_Target_Offline");
                        message5.sendMessage(sender);
                        SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                        return true;
                    }
                    target2 = PlayerUtil.getOnlinePlayer(nameTarget);
                }
                else {
                    target2 = PlayerUtil.parse(sender);
                }
                int amount3;
                if (args.length > 4) {
                    final String textAmount3 = args[4];
                    if (!MathUtil.isNumber(textAmount3)) {
                        final MessageBuild message5 = lang.getMessage(sender, "Argument_Invalid_Value");
                        message5.sendMessage(sender);
                        SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                        return true;
                    }
                    final int rawAmount = MathUtil.parseInteger(textAmount3);
                    amount3 = MathUtil.limitInteger(rawAmount, 1, rawAmount);
                }
                else {
                    amount3 = 1;
                }
                if (target2.equals(sender)) {
                    final MessageBuild message7 = lang.getMessage(sender, "MyItems_Socket_Load_Rod_Success_Self");
                    final HashMap<String, String> mapPlaceholder5 = new HashMap<String, String>();
                    mapPlaceholder5.put("socket_rod", EquipmentUtil.getDisplayName(rod));
                    mapPlaceholder5.put("amount", String.valueOf(amount3));
                    EquipmentUtil.setAmount(rod, amount3);
                    PlayerUtil.addItem(target2, rod);
                    message7.sendMessage(sender, (HashMap)mapPlaceholder5);
                    SenderUtil.playSound(sender, SoundEnum.ENTITY_EXPERIENCE_ORB_PICKUP);
                    return true;
                }
                final MessageBuild messageToSender2 = lang.getMessage(sender, "MyItems_Socket_Load_Rod_Success_To_Sender");
                final MessageBuild messageToTarget2 = lang.getMessage(sender, "MyItems_Socket_Load_Rod_Success_To_Target");
                final HashMap<String, String> mapPlaceholder = new HashMap<String, String>();
                mapPlaceholder.put("socket_rod", EquipmentUtil.getDisplayName(rod));
                mapPlaceholder.put("amount", String.valueOf(amount3));
                EquipmentUtil.setAmount(rod, amount3);
                PlayerUtil.addItem(target2, rod);
                messageToSender2.sendMessage(sender, (HashMap)mapPlaceholder);
                messageToTarget2.sendMessage((CommandSender)target2, (HashMap)mapPlaceholder);
                SenderUtil.playSound(sender, SoundEnum.ENTITY_EXPERIENCE_ORB_PICKUP);
                SenderUtil.playSound((CommandSender)target2, SoundEnum.ENTITY_EXPERIENCE_ORB_PICKUP);
                return true;
            }
        }
        else {
            if (commandManager.checkCommand(subCommand, "Socket_List")) {
                final String[] fullArgs2 = TextUtil.pressList(args, 2);
                list(sender, command, label, fullArgs2);
                return true;
            }
            final MessageBuild message2 = lang.getMessage(sender, "Argument_Invalid_Command");
            message2.sendMessage(sender);
            SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
            return true;
        }
    }
    
    private static final List<String> list(final CommandSender sender, final Command command, final String label, final String[] args) {
        final MyItems plugin = (MyItems)JavaPlugin.getPlugin((Class)MyItems.class);
        final SocketManager socketManager = plugin.getGameManager().getSocketManager();
        final PluginManager pluginManager = plugin.getPluginManager();
        final CommandManager commandManager = pluginManager.getCommandManager();
        final LanguageManager lang = pluginManager.getLanguageManager();
        final List<String> list = new ArrayList<String>();
        if (!commandManager.checkPermission(sender, "Socket_List")) {
            final String permission = commandManager.getPermission("Socket_List");
            final MessageBuild message = lang.getMessage(sender, "Permission_Lack");
            message.sendMessage(sender, "permission", permission);
            SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
            return list;
        }
        if (socketManager.getSocketIDs().isEmpty()) {
            final MessageBuild message2 = lang.getMessage(sender, "Item_Database_Empty");
            message2.sendMessage(sender);
            SenderUtil.playSound(sender, SoundEnum.BLOCK_WOOD_BUTTON_CLICK_ON);
            return list;
        }
        final List<String> keyList = (List<String>)SortUtil.toList((Collection)socketManager.getSocketIDs());
        final int size = keyList.size();
        final int maxRow = 5;
        final int maxPage = MathUtil.isDividedBy((double)size, 5.0) ? (size / 5) : (size / 5 + 1);
        int page = 1;
        if (args.length > 0) {
            final String textPage = args[0];
            if (MathUtil.isNumber(textPage)) {
                page = MathUtil.parseInteger(textPage);
                page = MathUtil.limitInteger(page, 1, maxPage);
            }
        }
        final HashMap<String, String> map = new HashMap<String, String>();
        String listHeaderMessage = lang.getText(sender, "List_Header");
        map.put("page", String.valueOf(page));
        map.put("maxpage", String.valueOf(maxPage));
        listHeaderMessage = TextUtil.placeholder((HashMap)map, listHeaderMessage);
        SenderUtil.sendMessage(sender, listHeaderMessage);
        for (int addNum = (page - 1) * 5, t = 0; t < 5 && t + addNum < size; ++t) {
            final int index = t + addNum;
            final String key = keyList.get(index);
            final HashMap<String, String> subMap = new HashMap<String, String>();
            String listItemMessage = lang.getText(sender, "List_Container");
            subMap.put("index", String.valueOf(index + 1));
            subMap.put("container", key);
            subMap.put("maxpage", String.valueOf(page));
            listItemMessage = TextUtil.placeholder((HashMap)subMap, listItemMessage);
            list.add(key);
            SenderUtil.sendMessage(sender, listItemMessage);
        }
        SenderUtil.playSound(sender, SoundEnum.BLOCK_WOOD_BUTTON_CLICK_ON);
        return list;
    }
}
