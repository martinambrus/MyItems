// 
// Decompiled by Procyon v0.5.36
// 

package com.praya.myitems.command;

import com.praya.agarthalib.utility.JsonUtil;
import java.util.Collection;
import com.praya.agarthalib.utility.SortUtil;
import java.util.ArrayList;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.Map;
import java.util.List;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.Material;
import org.bukkit.World;
import api.praya.myitems.builder.item.ItemSetComponent;
import api.praya.myitems.builder.item.ItemSet;
import api.praya.myitems.builder.item.ItemGenerator;
import org.bukkit.inventory.ItemStack;
import java.util.Iterator;
import core.praya.agarthalib.builder.plugin.PluginPropertiesResourceBuild;
import com.praya.myitems.manager.plugin.PluginPropertiesManager;
import core.praya.agarthalib.builder.message.MessageBuild;
import com.praya.myitems.manager.plugin.LanguageManager;
import com.praya.myitems.manager.plugin.PlaceholderManager;
import com.praya.myitems.manager.plugin.CommandManager;
import com.praya.myitems.manager.game.ItemSetManager;
import com.praya.myitems.manager.game.ItemGeneratorManager;
import com.praya.myitems.manager.game.ItemManager;
import com.praya.myitems.manager.game.LoreStatsManager;
import com.praya.myitems.manager.game.MenuManager;
import com.praya.myitems.manager.task.TaskManager;
import com.praya.myitems.manager.game.GameManager;
import com.praya.myitems.manager.plugin.PluginManager;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.enchantments.Enchantment;
import api.praya.myitems.builder.lorestats.LoreStatsEnum;
import com.praya.agarthalib.utility.MaterialUtil;
import org.bukkit.entity.LivingEntity;
import org.bukkit.Location;
import com.praya.agarthalib.utility.MathUtil;
import com.praya.agarthalib.utility.WorldUtil;
import org.bukkit.entity.Player;
import com.praya.agarthalib.utility.EquipmentUtil;
import core.praya.agarthalib.enums.main.Slot;
import core.praya.agarthalib.bridge.unity.Bridge;
import com.praya.agarthalib.utility.PlayerUtil;
import java.util.HashMap;
import com.praya.agarthalib.utility.SenderUtil;
import core.praya.agarthalib.enums.branch.SoundEnum;
import com.praya.agarthalib.utility.TextUtil;
import com.praya.myitems.config.plugin.MainConfig;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import com.praya.myitems.MyItems;
import org.bukkit.command.CommandExecutor;
import com.praya.myitems.builder.handler.HandlerCommand;

public class CommandMyItems extends HandlerCommand implements CommandExecutor
{
    public CommandMyItems(final MyItems plugin) {
        super(plugin);
    }
    
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        final PluginManager pluginManager = this.plugin.getPluginManager();
        final GameManager gameManager = this.plugin.getGameManager();
        final TaskManager taskManager = this.plugin.getTaskManager();
        final MenuManager menuManager = gameManager.getMenuManager();
        final LoreStatsManager statsManager = gameManager.getStatsManager();
        final ItemManager itemManager = gameManager.getItemManager();
        final ItemGeneratorManager itemGeneratorManager = gameManager.getItemGeneratorManager();
        final ItemSetManager itemSetManager = gameManager.getItemSetManager();
        final CommandManager commandManager = pluginManager.getCommandManager();
        final PlaceholderManager placeholderManager = pluginManager.getPlaceholderManager();
        final LanguageManager lang = pluginManager.getLanguageManager();
        final MainConfig mainConfig = MainConfig.getInstance();
        if (args.length <= 0) {
            final String[] fullArgs = TextUtil.pressList(args, 2);
            return help(sender, command, label, fullArgs);
        }
        final String subCommand = args[0];
        if (commandManager.checkCommand(subCommand, "MyItems_Reload")) {
            if (!commandManager.checkPermission(sender, "MyItems_Reload")) {
                final String permission = commandManager.getPermission("MyItems_Reload");
                final MessageBuild message = lang.getMessage(sender, "Permission_Lack");
                message.sendMessage(sender, "permission", permission);
                SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                return true;
            }
            final MessageBuild message2 = lang.getMessage(sender, "MyItems_Reload_Success");
            mainConfig.setup();
            pluginManager.getPlaceholderManager().getPlaceholderConfig().setup();
            pluginManager.getLanguageManager().getLangConfig().setup();
            pluginManager.getCommandManager().getCommandConfig().setup();
            gameManager.getAbilityWeaponManager().getAbilityWeaponConfig().setup();
            gameManager.getElementManager().getElementConfig().setup();
            gameManager.getPowerManager().getPowerCommandManager().getPowerCommandConfig().setup();
            gameManager.getPowerManager().getPowerSpecialManager().getPowerSpecialConfig().setup();
            gameManager.getSocketManager().getSocketConfig().setup();
            gameManager.getItemManager().getItemConfig().setup();
            gameManager.getItemTypeManager().getItemTypeConfig().setup();
            gameManager.getItemTierManager().getItemTierConfig().setup();
            gameManager.getItemGeneratorManager().getItemGeneratorConfig().setup();
            gameManager.getItemSetManager().getItemSetConfig().setup();
            taskManager.getTaskCustomEffectManager().reloadTaskCustomEffect();
            taskManager.getTaskPassiveEffectManager().reloadTaskLoadPassiveEffect();
            message2.sendMessage(sender);
            SenderUtil.playSound(sender, SoundEnum.ENTITY_EXPERIENCE_ORB_PICKUP);
            return true;
        }
        else if (commandManager.checkCommand(subCommand, "MyItems_About")) {
            if (!commandManager.checkPermission(sender, "MyItems_About")) {
                final String permission = commandManager.getPermission("MyItems_About");
                final MessageBuild message = lang.getMessage(sender, "Permission_Lack");
                message.sendMessage(sender, "permission", permission);
                SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                return true;
            }
            final PluginPropertiesManager pluginPropertiesManager = pluginManager.getPluginPropertiesManager();
            final PluginPropertiesResourceBuild pluginPropertiesResource = pluginPropertiesManager.getPluginPropertiesResource();
            final String prefix = String.valueOf(placeholderManager.getPlaceholder("Prefix")) + " ";
            final HashMap<String, String> map = new HashMap<String, String>();
            String aboutHeader = String.valueOf(prefix) + "&7=-=-=-=-=-=-= &6About&7 =-=-=-=-=-=-=";
            String aboutFooter = String.valueOf(prefix) + "&7=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=";
            final String aboutBlank = new StringBuilder(String.valueOf(prefix)).toString();
            String aboutPlugin = String.valueOf(prefix) + "Plugin&f: &c{plugin}";
            String aboutType = String.valueOf(prefix) + "Type&f: &c{type}";
            String aboutVersion = String.valueOf(prefix) + "Version&f: &c{version}";
            String aboutAuthor = String.valueOf(prefix) + "Author&f: &c{author}";
            map.put("plugin", pluginPropertiesResource.getName());
            map.put("type", pluginPropertiesResource.getType());
            map.put("version", pluginPropertiesResource.getVersion());
            map.put("author", pluginPropertiesResource.getAuthor());
            map.put("company", pluginPropertiesResource.getCompany());
            aboutHeader = TextUtil.placeholder((HashMap)map, aboutHeader);
            aboutFooter = TextUtil.placeholder((HashMap)map, aboutFooter);
            aboutPlugin = TextUtil.placeholder((HashMap)map, aboutPlugin);
            aboutType = TextUtil.placeholder((HashMap)map, aboutType);
            aboutVersion = TextUtil.placeholder((HashMap)map, aboutVersion);
            aboutAuthor = TextUtil.placeholder((HashMap)map, aboutAuthor);
            SenderUtil.sendMessage(sender, aboutHeader);
            SenderUtil.sendMessage(sender, aboutBlank);
            SenderUtil.sendMessage(sender, aboutPlugin);
            SenderUtil.sendMessage(sender, aboutType);
            SenderUtil.sendMessage(sender, aboutVersion);
            SenderUtil.sendMessage(sender, aboutAuthor);
            if (pluginPropertiesManager.getCompany() != null) {
                final HashMap<String, String> subMap = new HashMap<String, String>();
                String aboutCompany = String.valueOf(prefix) + "Company&7: &c{company}";
                subMap.put("company", pluginPropertiesManager.getCompany());
                aboutCompany = TextUtil.placeholder((HashMap)subMap, aboutCompany);
            }
            if (!pluginPropertiesManager.getDevelopers().isEmpty()) {
                final String aboutDeveloper = String.valueOf(prefix) + "Developer&7:";
                SenderUtil.sendMessage(sender, aboutDeveloper);
                for (final String developer : pluginPropertiesManager.getDevelopers()) {
                    final HashMap<String, String> subMap2 = new HashMap<String, String>();
                    String aboutListDeveloper = String.valueOf(prefix) + "&7&l\u27a8 &d{developer}";
                    subMap2.put("developer", developer);
                    aboutListDeveloper = TextUtil.placeholder((HashMap)subMap2, aboutListDeveloper);
                    SenderUtil.sendMessage(sender, aboutListDeveloper);
                }
            }
            SenderUtil.sendMessage(sender, aboutBlank);
            SenderUtil.sendMessage(sender, aboutFooter);
            SenderUtil.playSound(sender, SoundEnum.ENTITY_EXPERIENCE_ORB_PICKUP);
            return true;
        }
        else if (commandManager.checkCommand(subCommand, "MyItems_Save")) {
            if (!commandManager.checkPermission(sender, "MyItems_Save")) {
                final String permission = commandManager.getPermission("MyItems_Save");
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
                final String tooltip = TextUtil.getJsonTooltip(lang.getText(sender, "Tooltip_MyItems_Save"));
                final MessageBuild message = lang.getMessage(sender, "Argument_MyItems_Save");
                message.sendMessage(sender, "tooltip_save", tooltip);
                SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                return true;
            }
            if (args[1].contains(".")) {
                final MessageBuild message2 = lang.getMessage(sender, "Character_Special");
                message2.sendMessage(sender);
                SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                return true;
            }
            final String nameid = itemManager.isExist(args[1]) ? itemManager.getRawName(args[1]) : args[1];
            final Player player = PlayerUtil.parse(sender);
            final ItemStack item = Bridge.getBridgeEquipment().getEquipment(player, Slot.MAINHAND);
            if (!EquipmentUtil.isSolid(item)) {
                final MessageBuild message3 = lang.getMessage(sender, "Item_MainHand_Empty");
                message3.sendMessage(sender);
                SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                return true;
            }
            final ItemStack clone = item.clone();
            final MessageBuild message4 = lang.getMessage(sender, "MyItems_Save_Success");
            clone.setAmount(1);
            itemManager.getItemConfig().saveItem(clone, nameid);
            message4.sendMessage(sender, "nameid", nameid);
            SenderUtil.playSound(sender, SoundEnum.ENTITY_EXPERIENCE_ORB_PICKUP);
            return true;
        }
        else if (commandManager.checkCommand(subCommand, "MyItems_Remove")) {
            if (!commandManager.checkPermission(sender, "MyItems_Remove")) {
                final String permission = commandManager.getPermission("MyItems_Remove");
                final MessageBuild message = lang.getMessage(sender, "Permission_Lack");
                message.sendMessage(sender, "permission", permission);
                SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                return true;
            }
            if (args.length < 2) {
                final String tooltip = TextUtil.getJsonTooltip(lang.getText(sender, "Tooltip_MyItems_Remove"));
                final MessageBuild message = lang.getMessage(sender, "Argument_MyItems_Remove");
                message.sendMessage(sender, "tooltip_remove", tooltip);
                SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                return true;
            }
            if (args[1].contains(".")) {
                final MessageBuild message2 = lang.getMessage(sender, "Character_Special");
                message2.sendMessage(sender);
                SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                return true;
            }
            final String nameid = itemManager.getRawName(args[1]);
            if (nameid == null) {
                final MessageBuild message = lang.getMessage(sender, "Item_Not_Exist");
                message.sendMessage(sender, "nameid", args[1]);
                SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                return true;
            }
            final MessageBuild message = lang.getMessage(sender, "MyItems_Remove_Success");
            itemManager.getItemConfig().removeItem(nameid);
            message.sendMessage(sender, "nameid", nameid);
            SenderUtil.playSound(sender, SoundEnum.ENTITY_EXPERIENCE_ORB_PICKUP);
            return true;
        }
        else if (commandManager.checkCommand(subCommand, "MyItems_Drop")) {
            if (!commandManager.checkPermission(sender, "MyItems_Drop")) {
                final String permission = commandManager.getPermission("MyItems_Drop");
                final MessageBuild message = lang.getMessage(sender, "Permission_Lack");
                message.sendMessage(sender, "permission", permission);
                SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                return true;
            }
            if (args.length < ((sender instanceof Player) ? 3 : 7)) {
                final String tooltip = TextUtil.getJsonTooltip(lang.getText(sender, "Tooltip_MyItems_Drop"));
                final MessageBuild message = lang.getMessage(sender, "Argument_MyItems_Drop");
                message.sendMessage(sender, "tooltip_drop", tooltip);
                SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                return true;
            }
            if (args[2].contains(".")) {
                final MessageBuild message2 = lang.getMessage(sender, "Character_Special");
                message2.sendMessage(sender);
                SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                return true;
            }
            final String textCategory = args[1];
            final String textItemName = args[2];
            ItemStack item2;
            String name;
            if (commandManager.checkCommand(textCategory, "MyItems_Drop_Custom")) {
                item2 = itemManager.getItem(textItemName);
                name = ((item2 != null) ? itemManager.getRawName(textItemName) : textItemName);
            }
            else if (commandManager.checkCommand(textCategory, "MyItems_Drop_Generator")) {
                final ItemGenerator itemGenerator = itemGeneratorManager.getItemGenerator(textItemName);
                item2 = ((itemGenerator != null) ? itemGenerator.generateItem() : null);
                name = ((itemGenerator != null) ? itemGenerator.getId() : textItemName);
            }
            else {
                if (!commandManager.checkCommand(textCategory, "MyItems_Drop_Set")) {
                    final MessageBuild message4 = lang.getMessage(sender, "MyItems_Category_Not_Exists");
                    message4.sendMessage(sender, "category", textCategory);
                    SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                    return true;
                }
                final ItemSet itemSet = itemSetManager.getItemSetByComponentID(textItemName);
                final ItemSetComponent itemSetComponent = (itemSet != null) ? itemSet.getItemSetComponent(textItemName) : null;
                item2 = ((itemSetComponent != null) ? itemSet.generateItem(textItemName) : null);
                name = ((itemSetComponent != null) ? itemSetComponent.getID() : textItemName);
            }
            if (item2 == null) {
                final MessageBuild message4 = lang.getMessage(sender, "Item_Not_Exist");
                message4.sendMessage(sender, "nameid", name);
                SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                return true;
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
                final MessageBuild message5 = lang.getMessage(sender, "MyItems_World_Not_Exists");
                message5.sendMessage(sender, "world", args[3]);
                SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                return true;
            }
            double x;
            if (args.length > 4) {
                final String textX = args[4];
                if (!MathUtil.isNumber(textX)) {
                    if (!textX.equalsIgnoreCase("~") || !(sender instanceof Player)) {
                        final MessageBuild message6 = lang.getMessage(sender, "Argument_Invalid_Value");
                        message6.sendMessage(sender);
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
                        final MessageBuild message6 = lang.getMessage(sender, "Argument_Invalid_Value");
                        message6.sendMessage(sender);
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
                        final MessageBuild message6 = lang.getMessage(sender, "Argument_Invalid_Value");
                        message6.sendMessage(sender);
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
                    final MessageBuild message6 = lang.getMessage(sender, "Argument_Invalid_Value");
                    message6.sendMessage(sender);
                    SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                    return true;
                }
                amount = MathUtil.parseInteger(textAmount);
            }
            else {
                amount = 1;
            }
            final ItemStack clone2 = item2.clone();
            final Location location2 = new Location(world, x, y, z);
            if (SenderUtil.isPlayer(sender)) {
                final MessageBuild message7 = lang.getMessage(sender, "MyItems_Drop_Success");
                final HashMap<String, String> mapPlaceholder = new HashMap<String, String>();
                mapPlaceholder.put("amount", String.valueOf(amount));
                mapPlaceholder.put("nameid", name);
                mapPlaceholder.put("world", world.getName());
                mapPlaceholder.put("x", String.valueOf(x));
                mapPlaceholder.put("y", String.valueOf(y));
                mapPlaceholder.put("z", String.valueOf(z));
                message7.sendMessage(sender, (HashMap)mapPlaceholder);
                SenderUtil.playSound(sender, SoundEnum.ENTITY_EXPERIENCE_ORB_PICKUP);
            }
            clone2.setAmount(amount);
            world.dropItem(location2, clone2);
            return true;
        }
        else if (commandManager.checkCommand(subCommand, "MyItems_Load")) {
            if (!commandManager.checkPermission(sender, "MyItems_Load")) {
                final String permission = commandManager.getPermission("MyItems_Load");
                final MessageBuild message = lang.getMessage(sender, "Permission_Lack");
                message.sendMessage(sender, "permission", permission);
                SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                return true;
            }
            if (args.length < (SenderUtil.isPlayer(sender) ? 3 : 4)) {
                final String tooltip = TextUtil.getJsonTooltip(lang.getText(sender, "Tooltip_MyItems_Load"));
                final MessageBuild message = lang.getMessage(sender, "Argument_MyItems_Load");
                message.sendMessage(sender, "tooltip_load", tooltip);
                SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                return true;
            }
            if (args[2].contains(".")) {
                final MessageBuild message2 = lang.getMessage(sender, "Character_Special");
                message2.sendMessage(sender);
                SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                return true;
            }
            final String textCategory = args[1];
            final String textItemName = args[2];
            ItemStack item2;
            String name;
            if (commandManager.checkCommand(textCategory, "MyItems_Load_Custom")) {
                item2 = itemManager.getItem(textItemName);
                name = ((item2 != null) ? itemManager.getRawName(textItemName) : textItemName);
            }
            else if (commandManager.checkCommand(textCategory, "MyItems_Load_Generator")) {
                final ItemGenerator itemGenerator = itemGeneratorManager.getItemGenerator(textItemName);
                item2 = ((itemGenerator != null) ? itemGenerator.generateItem() : null);
                name = ((itemGenerator != null) ? itemGenerator.getId() : textItemName);
            }
            else {
                if (!commandManager.checkCommand(textCategory, "MyItems_Load_Set")) {
                    final MessageBuild message4 = lang.getMessage(sender, "MyItems_Category_Not_Exists");
                    message4.sendMessage(sender, "category", textCategory);
                    SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                    return true;
                }
                final ItemSet itemSet = itemSetManager.getItemSetByComponentID(textItemName);
                final ItemSetComponent itemSetComponent = (itemSet != null) ? itemSet.getItemSetComponent(textItemName) : null;
                item2 = ((itemSetComponent != null) ? itemSet.generateItem(textItemName) : null);
                name = ((itemSetComponent != null) ? itemSetComponent.getID() : textItemName);
            }
            if (item2 == null) {
                final MessageBuild message4 = lang.getMessage(sender, "Item_Not_Exist");
                message4.sendMessage(sender, "nameid", name);
                SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                return true;
            }
            Player target;
            if (args.length > 3) {
                final String nameTarget = args[3];
                if (!PlayerUtil.isOnline(nameTarget)) {
                    final MessageBuild message8 = lang.getMessage(sender, "Player_Target_Offline");
                    message8.sendMessage(sender);
                    SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                    return true;
                }
                target = PlayerUtil.getOnlinePlayer(nameTarget);
            }
            else {
                target = PlayerUtil.parse(sender);
            }
            int amount2;
            if (args.length > 4) {
                final String textAmount2 = args[4];
                if (!MathUtil.isNumber(textAmount2)) {
                    final MessageBuild message8 = lang.getMessage(sender, "Argument_Invalid_Value");
                    message8.sendMessage(sender);
                    SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                    return true;
                }
                final int rawAmount = MathUtil.parseInteger(textAmount2);
                amount2 = MathUtil.limitInteger(rawAmount, 1, rawAmount);
            }
            else {
                amount2 = 1;
            }
            if (target.equals(sender)) {
                final ItemStack clone3 = item2.clone();
                final MessageBuild message8 = lang.getMessage(sender, "MyItems_Load_Success_Self");
                final HashMap<String, String> mapPlaceholder2 = new HashMap<String, String>();
                mapPlaceholder2.put("amount", String.valueOf(amount2));
                mapPlaceholder2.put("nameID", name);
                EquipmentUtil.setAmount(clone3, amount2);
                EquipmentUtil.hookPlaceholderAPI(clone3);
                PlayerUtil.addItem(target, clone3);
                message8.sendMessage(sender, (HashMap)mapPlaceholder2);
                SenderUtil.playSound(sender, SoundEnum.ENTITY_EXPERIENCE_ORB_PICKUP);
            }
            else {
                final MessageBuild messageToSender = lang.getMessage(sender, "MyItems_Load_Success_To_Sender");
                final MessageBuild messageToTarget = lang.getMessage((LivingEntity)target, "MyItems_Load_Success_To_Target");
                final HashMap<String, String> mapPlaceholder2 = new HashMap<String, String>();
                mapPlaceholder2.put("nameID", name);
                mapPlaceholder2.put("amount", String.valueOf(amount2));
                mapPlaceholder2.put("target", target.getName());
                mapPlaceholder2.put("sender", sender.getName());
                EquipmentUtil.hookPlaceholderAPI(item2, target);
                EquipmentUtil.setAmount(item2, amount2);
                PlayerUtil.addItem(target, item2);
                messageToSender.sendMessage(sender, (HashMap)mapPlaceholder2);
                messageToTarget.sendMessage((CommandSender)target, (HashMap)mapPlaceholder2);
                SenderUtil.playSound(sender, SoundEnum.ENTITY_EXPERIENCE_ORB_PICKUP);
                SenderUtil.playSound((CommandSender)target, SoundEnum.ENTITY_EXPERIENCE_ORB_PICKUP);
            }
            if (commandManager.checkCommand(textCategory, "MyItems_Load_Set")) {
                itemSetManager.updateItemSet((LivingEntity)target);
            }
            return true;
        }
        else if (commandManager.checkCommand(subCommand, "MyItems_Amount")) {
            if (!commandManager.checkPermission(sender, "MyItems_Amount")) {
                final String permission = commandManager.getPermission("MyItems_Amount");
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
                final String tooltip = TextUtil.getJsonTooltip(lang.getText(sender, "Tooltip_MyItems_Amount"));
                final MessageBuild message = lang.getMessage(sender, "Argument_MyItems_Amount");
                message.sendMessage(sender, "tooltip_amount", tooltip);
                SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                return true;
            }
            final Player player6 = PlayerUtil.parse(sender);
            final ItemStack item3 = Bridge.getBridgeEquipment().getEquipment(player6, Slot.MAINHAND);
            if (!EquipmentUtil.isSolid(item3)) {
                final MessageBuild message9 = lang.getMessage(sender, "Item_MainHand_Empty");
                message9.sendMessage(sender);
                SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                return true;
            }
            final String textAmount3 = args[1];
            int amount3 = 1;
            if (MathUtil.isNumber(textAmount3)) {
                amount3 = MathUtil.parseInteger(textAmount3);
                amount3 = MathUtil.limitInteger(amount3, 1, 64);
                final MessageBuild message4 = lang.getMessage(sender, "MyItems_Amount_Success");
                EquipmentUtil.setAmount(item3, amount3);
                message4.sendMessage(sender, "amount", String.valueOf(amount3));
                SenderUtil.playSound(sender, SoundEnum.ENTITY_EXPERIENCE_ORB_PICKUP);
                player6.updateInventory();
                return true;
            }
            final MessageBuild message4 = lang.getMessage(sender, "Argument_Invalid_Value");
            message4.sendMessage(sender);
            SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
            return true;
        }
        else if (commandManager.checkCommand(subCommand, "MyItems_Data")) {
            if (!commandManager.checkPermission(sender, "MyItems_Data")) {
                final String permission = commandManager.getPermission("MyItems_Data");
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
                final String tooltip = TextUtil.getJsonTooltip(lang.getText(sender, "Tooltip_MyItems_Data"));
                final MessageBuild message = lang.getMessage(sender, "Argument_MyItems_Data");
                message.sendMessage(sender, "tooltip_data", tooltip);
                SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                return true;
            }
            final Player player6 = PlayerUtil.parse(sender);
            final ItemStack item3 = Bridge.getBridgeEquipment().getEquipment(player6, Slot.MAINHAND);
            if (!EquipmentUtil.isSolid(item3)) {
                final MessageBuild message9 = lang.getMessage(sender, "Item_MainHand_Empty");
                message9.sendMessage(sender);
                SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                return true;
            }
            final String textAmount3 = args[1];
            short data = 0;
            if (MathUtil.isNumber(textAmount3)) {
                data = MathUtil.parseShort(textAmount3);
                data = MathUtil.limitShort(data, (short)0, (short)4096);
                final MessageBuild message4 = lang.getMessage(sender, "MyItems_Data_Success");
                EquipmentUtil.setData(item3, data);
                message4.sendMessage(sender, "data", String.valueOf(data));
                SenderUtil.playSound(sender, SoundEnum.ENTITY_EXPERIENCE_ORB_PICKUP);
                player6.updateInventory();
                return true;
            }
            final MessageBuild message4 = lang.getMessage(sender, "Argument_Invalid_Value");
            message4.sendMessage(sender);
            SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
            return true;
        }
        else if (commandManager.checkCommand(subCommand, "MyItems_Material")) {
            if (!commandManager.checkPermission(sender, "MyItems_Material")) {
                final String permission = commandManager.getPermission("MyItems_Material");
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
                final String tooltip = TextUtil.getJsonTooltip(lang.getText(sender, "Tooltip_MyItems_Material"));
                final MessageBuild message = lang.getMessage(sender, "Argument_MyItems_Material");
                message.sendMessage(sender, "tooltip_material", tooltip);
                SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                return true;
            }
            final Player player6 = PlayerUtil.parse(sender);
            final ItemStack item3 = Bridge.getBridgeEquipment().getEquipment(player6, Slot.MAINHAND);
            final Material material = MaterialUtil.getMaterial(args[1]);
            if (!EquipmentUtil.isSolid(item3)) {
                final MessageBuild message3 = lang.getMessage(sender, "Item_MainHand_Empty");
                message3.sendMessage(sender);
                SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                return true;
            }
            if (material == null) {
                final MessageBuild message3 = lang.getMessage(sender, "MyItems_Material_Not_Existss");
                message3.sendMessage(sender, "material", args[1]);
                SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                return true;
            }
            final MessageBuild message3 = lang.getMessage(sender, "MyItems_Material_Success");
            EquipmentUtil.setMaterial(item3, material);
            message3.sendMessage(sender, "material", material.toString());
            SenderUtil.playSound(sender, SoundEnum.ENTITY_EXPERIENCE_ORB_PICKUP);
            player6.updateInventory();
            return true;
        }
        else if (commandManager.checkCommand(subCommand, "MyItems_Repair")) {
            if (!commandManager.checkPermission(sender, "MyItems_Repair")) {
                final String permission = commandManager.getPermission("MyItems_Repair");
                final MessageBuild message = lang.getMessage(sender, "Permission_Lack");
                message.sendMessage(sender, "permission", permission);
                SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                return true;
            }
            if (!SenderUtil.isPlayer(sender) && args.length < 2) {
                final String tooltip = TextUtil.getJsonTooltip(lang.getText(sender, "Tooltip_MyItems_Repair"));
                final MessageBuild message = lang.getMessage(sender, "Argument_MyItems_Repair");
                message.sendMessage(sender, "tooltip_repair", tooltip);
                SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                return true;
            }
            Player target2;
            if (args.length > 1) {
                final String nameTarget2 = args[1];
                if (!PlayerUtil.isOnline(nameTarget2)) {
                    final MessageBuild message3 = lang.getMessage(sender, "Player_Target_Offline");
                    message3.sendMessage(sender);
                    SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                    return true;
                }
                target2 = PlayerUtil.getOnlinePlayer(nameTarget2);
            }
            else {
                target2 = PlayerUtil.parse(sender);
            }
            int repair;
            if (args.length > 2) {
                final String textRepair = args[2];
                if (!MathUtil.isNumber(textRepair)) {
                    final MessageBuild message3 = lang.getMessage(sender, "Argument_Invalid_Value");
                    message3.sendMessage(sender);
                    SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                    return true;
                }
                repair = Integer.valueOf(textRepair);
            }
            else {
                repair = -1;
            }
            final ItemStack item = Bridge.getBridgeEquipment().getEquipment(target2, Slot.MAINHAND);
            if (!statsManager.hasLoreStats(item, LoreStatsEnum.DURABILITY)) {
                final MessageBuild message3 = lang.getMessage(sender, "Item_Durability_Not_Exist");
                message3.sendMessage(sender);
                SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                return true;
            }
            if (target2.equals(sender)) {
                final MessageBuild message3 = lang.getMessage(sender, "MyItems_Repair_Success");
                statsManager.itemRepair(item, repair);
                message3.sendMessage(sender);
                SenderUtil.playSound(sender, SoundEnum.ENTITY_EXPERIENCE_ORB_PICKUP);
                target2.updateInventory();
                return true;
            }
            final MessageBuild senderMessage = lang.getMessage(sender, "MyItems_Repair_Success_To_Sender");
            final MessageBuild targetMessage = lang.getMessage((LivingEntity)target2, "MyItems_Repair_Success_To_Target");
            final HashMap<String, String> mapPlaceholder3 = new HashMap<String, String>();
            mapPlaceholder3.put("sender", sender.getName());
            mapPlaceholder3.put("target", target2.getName());
            statsManager.itemRepair(item, repair);
            senderMessage.sendMessage(sender, (HashMap)mapPlaceholder3);
            targetMessage.sendMessage((CommandSender)target2, (HashMap)mapPlaceholder3);
            SenderUtil.playSound(sender, SoundEnum.ENTITY_EXPERIENCE_ORB_PICKUP);
            SenderUtil.playSound((CommandSender)target2, SoundEnum.ENTITY_EXPERIENCE_ORB_PICKUP);
            target2.updateInventory();
            return true;
        }
        else if (commandManager.checkCommand(subCommand, "MyItems_Detail")) {
            if (!commandManager.checkPermission(sender, "MyItems_Detail")) {
                final String permission = commandManager.getPermission("MyItems_Detail");
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
            final Player player6 = PlayerUtil.parse(sender);
            final ItemStack item3 = Bridge.getBridgeEquipment().getEquipment(player6, Slot.MAINHAND);
            if (!EquipmentUtil.isSolid(item3)) {
                final String message10 = lang.getText(sender, "Item_MainHand_Empty");
                SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
                SenderUtil.sendMessage(sender, message10);
                return true;
            }
            final String displayName = EquipmentUtil.getDisplayName(item3);
            final Material material2 = item3.getType();
            final ItemMeta meta = item3.getItemMeta();
            final int durability = material2.getMaxDurability();
            final int data2 = item3.getDurability();
            final int hashCode = item3.hashCode();
            final List<String> lores = (List<String>)EquipmentUtil.getLores(item3);
            final HashMap<String, String> mapPlaceholder4 = new HashMap<String, String>();
            String detailHeader = lang.getText(sender, "Detail_Header");
            String detailName = lang.getText(sender, "Detail_Name");
            String detailDurability = lang.getText(sender, "Detail_Durability");
            String detailData = lang.getText(sender, "Detail_Data");
            String detailHashCode = lang.getText(sender, "Detail_HashCode");
            mapPlaceholder4.put("name", displayName);
            mapPlaceholder4.put("durability", String.valueOf(durability));
            mapPlaceholder4.put("data", String.valueOf(data2));
            mapPlaceholder4.put("hashcode", String.valueOf(hashCode));
            detailHeader = TextUtil.placeholder((HashMap)mapPlaceholder4, detailHeader);
            detailName = TextUtil.placeholder((HashMap)mapPlaceholder4, detailName);
            detailDurability = TextUtil.placeholder((HashMap)mapPlaceholder4, detailDurability);
            detailData = TextUtil.placeholder((HashMap)mapPlaceholder4, detailData);
            detailHashCode = TextUtil.placeholder((HashMap)mapPlaceholder4, detailHashCode);
            SenderUtil.sendMessage(sender, detailHeader);
            SenderUtil.sendMessage(sender, " ");
            SenderUtil.sendMessage(sender, detailName);
            SenderUtil.sendMessage(sender, detailDurability);
            SenderUtil.sendMessage(sender, detailData);
            SenderUtil.sendMessage(sender, detailHashCode);
            if (!lores.isEmpty()) {
                final String detailLoresHead = lang.getText(sender, "Detail_Lores_Head");
                SenderUtil.sendMessage(sender, " ");
                SenderUtil.sendMessage(sender, detailLoresHead);
                for (int i = 0; i < lores.size(); ++i) {
                    final MessageBuild loreMessage = lang.getMessage(sender, "Detail_Lores_List");
                    mapPlaceholder4.clear();
                    mapPlaceholder4.put("line", String.valueOf(i + 1));
                    mapPlaceholder4.put("lore", lores.get(i));
                    loreMessage.sendMessage(sender, (HashMap)mapPlaceholder4);
                }
            }
            if (meta.hasEnchants()) {
                final String detailEnchantmentHead = lang.getText(sender, "Detail_Enchantment_Head");
                final Map<Enchantment, Integer> mapEnchantment = (Map<Enchantment, Integer>)meta.getEnchants();
                SenderUtil.sendMessage(sender, " ");
                SenderUtil.sendMessage(sender, detailEnchantmentHead);
                for (final Enchantment enchant : meta.getEnchants().keySet()) {
                    final MessageBuild enchantmentMessage = lang.getMessage(sender, "Detail_Enchantment_List");
                    mapPlaceholder4.clear();
                    mapPlaceholder4.put("enchantment", enchant.getName());
                    mapPlaceholder4.put("grade", String.valueOf(mapEnchantment.get(enchant)));
                    enchantmentMessage.sendMessage(sender, (HashMap)mapPlaceholder4);
                }
            }
            if (!meta.getItemFlags().isEmpty()) {
                final String detailFlagsHead = lang.getText(sender, "Detail_Flags_Head");
                SenderUtil.sendMessage(sender, " ");
                SenderUtil.sendMessage(sender, detailFlagsHead);
                for (final ItemFlag flag : meta.getItemFlags()) {
                    final MessageBuild flagMessage = lang.getMessage(sender, "Detail_Flags_List");
                    mapPlaceholder4.clear();
                    mapPlaceholder4.put("flag", String.valueOf(flag));
                    flagMessage.sendMessage(sender, (HashMap)mapPlaceholder4);
                }
            }
            SenderUtil.playSound((CommandSender)player6, SoundEnum.ENTITY_EXPERIENCE_ORB_PICKUP);
            return true;
        }
        else if (commandManager.checkCommand(subCommand, "MyItems_Stats")) {
            if (!commandManager.checkPermission(sender, "MyItems_Stats")) {
                final String permission = commandManager.getPermission("MyItems_Stats");
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
            final Player player6 = PlayerUtil.parse(sender);
            menuManager.openMenuStats(player6);
            return true;
        }
        else {
            if (commandManager.checkCommand(subCommand, "MyItems_Help")) {
                final String[] fullArgs2 = TextUtil.pressList(args, 2);
                return help(sender, command, label, fullArgs2);
            }
            if (commandManager.checkCommand(subCommand, "MyItems_List")) {
                final String[] fullArgs2 = TextUtil.pressList(args, 2);
                list(sender, command, label, fullArgs2);
                return true;
            }
            if (commandManager.checkCommand(subCommand, "MyItems_Name")) {
                final String[] fullArgs2 = TextUtil.pressList(args, 2);
                return CommandItemName.setName(sender, command, label, fullArgs2);
            }
            if (commandManager.checkCommand(subCommand, "MyItems_Lore_Set")) {
                final String[] fullArgs2 = TextUtil.pressList(args, 2);
                return CommandLoreSet.setLore(sender, command, label, fullArgs2);
            }
            if (commandManager.checkCommand(subCommand, "MyItems_Lore_Insert")) {
                final String[] fullArgs2 = TextUtil.pressList(args, 2);
                return CommandLoreInsert.insertLore(sender, command, label, fullArgs2);
            }
            if (commandManager.checkCommand(subCommand, "MyItems_Lore_Add")) {
                final String[] fullArgs2 = TextUtil.pressList(args, 2);
                return CommandLoreAdd.addLore(sender, command, label, fullArgs2);
            }
            if (commandManager.checkCommand(subCommand, "MyItems_Lore_Remove")) {
                final String[] fullArgs2 = TextUtil.pressList(args, 2);
                return CommandLoreRemove.removeLore(sender, command, label, fullArgs2);
            }
            if (commandManager.checkCommand(subCommand, "MyItems_Lore_Clear")) {
                final String[] fullArgs2 = TextUtil.pressList(args, 2);
                return CommandLoreClear.clearLore(sender, command, label, fullArgs2);
            }
            if (commandManager.checkCommand(subCommand, "MyItems_Flag_Add")) {
                final String[] fullArgs2 = TextUtil.pressList(args, 2);
                return CommandFlagAdd.addFlag(sender, command, label, fullArgs2);
            }
            if (commandManager.checkCommand(subCommand, "MyItems_Flag_Remove")) {
                final String[] fullArgs2 = TextUtil.pressList(args, 2);
                return CommandFlagRemove.removeFlag(sender, command, label, fullArgs2);
            }
            if (commandManager.checkCommand(subCommand, "MyItems_Flag_Clear")) {
                final String[] fullArgs2 = TextUtil.pressList(args, 2);
                return CommandFlagClear.clearFlag(sender, command, label, fullArgs2);
            }
            if (commandManager.checkCommand(subCommand, "MyItems_Enchant_Add")) {
                final String[] fullArgs2 = TextUtil.pressList(args, 2);
                return CommandEnchantAdd.addEnchant(sender, command, label, fullArgs2);
            }
            if (commandManager.checkCommand(subCommand, "MyItems_Enchant_Remove")) {
                final String[] fullArgs2 = TextUtil.pressList(args, 2);
                return CommandEnchantRemove.removeEnchant(sender, command, label, fullArgs2);
            }
            if (commandManager.checkCommand(subCommand, "MyItems_Enchant_Clear")) {
                final String[] fullArgs2 = TextUtil.pressList(args, 2);
                return CommandEnchantClear.clearEnchant(sender, command, label, fullArgs2);
            }
            if (commandManager.checkCommand(subCommand, "MyItems_Unbreakable")) {
                final String[] fullArgs2 = TextUtil.pressList(args, 2);
                return CommandUnbreakable.unbreakable(sender, command, label, fullArgs2);
            }
            final MessageBuild message2 = lang.getMessage(sender, "Argument_Invalid_Command");
            message2.sendMessage(sender);
            SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
            return true;
        }
    }
    
    protected static final boolean help(final CommandSender sender, final Command command, final String label, final String[] args) {
        final MyItems plugin = (MyItems)JavaPlugin.getPlugin((Class)MyItems.class);
        final PluginManager pluginManager = plugin.getPluginManager();
        final CommandManager commandManager = pluginManager.getCommandManager();
        final LanguageManager lang = pluginManager.getLanguageManager();
        if (!commandManager.checkPermission(sender, "MyItems_Help")) {
            final String permission = commandManager.getPermission("MyItems_Help");
            final MessageBuild message = lang.getMessage(sender, "Permission_Lack");
            message.sendMessage(sender, "permission", permission);
            SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
            return true;
        }
        if (args.length < 1) {
            final String tooltip = TextUtil.getJsonTooltip(lang.getText(sender, "Tooltip_MyItems_Help"));
            final MessageBuild message = lang.getMessage(sender, "Argument_MyItems_Help");
            message.sendMessage(sender, "tooltip_help", tooltip);
            SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
            return true;
        }
        final int maxPage = 7;
        final PluginPropertiesManager pluginPropertiesManager = pluginManager.getPluginPropertiesManager();
        final PluginPropertiesResourceBuild pluginPropertiesResource = pluginPropertiesManager.getPluginPropertiesResource();
        final HashMap<String, String> map = new HashMap<String, String>();
        int page = 1;
        if (args.length > 0) {
            final String textPage = args[0];
            if (MathUtil.isNumber(textPage)) {
                page = MathUtil.parseInteger(textPage);
                page = MathUtil.limitInteger(page, 1, 7);
            }
        }
        String helpLeader = lang.getText(sender, "Help_Header");
        String helpPage = lang.getText(sender, "Help_Page");
        String previousTooltip = "||&6&l\u25c0||ttp: {text_previous_page}||cmd: /{plugin} help {previous_page}||";
        String nextTooltip = "||&6&l\u25b6||ttp: {text_next_page}||cmd: /{plugin} help {next_page}||";
        String myitems_Help = lang.getText(sender, "Argument_MyItems_Help");
        String myitems_About = lang.getText(sender, "Argument_MyItems_About");
        String myitems_Reload = lang.getText(sender, "Argument_MyItems_Reload");
        String myitems_Detail = lang.getText(sender, "Argument_MyItems_Detail");
        String myitems_Stats = lang.getText(sender, "Argument_MyItems_Detail");
        String myitems_List = lang.getText(sender, "Argument_MyItems_List");
        String myitems_Save = lang.getText(sender, "Argument_MyItems_Save");
        String myitems_Load = lang.getText(sender, "Argument_MyItems_Load");
        String myitems_Drop = lang.getText(sender, "Argument_MyItems_Drop");
        String myitems_Remove = lang.getText(sender, "Argument_MyItems_Remove");
        String myitems_Repair = lang.getText(sender, "Argument_MyItems_Repair");
        String myitems_Amount = lang.getText(sender, "Argument_MyItems_Amount");
        String myitems_Data = lang.getText(sender, "Argument_MyItems_Data");
        String myitems_Material = lang.getText(sender, "Argument_MyItems_Material");
        String myitems_Unbreakable = lang.getText(sender, "Argument_Unbreakable");
        String myitems_Setname = lang.getText(sender, "Argument_SetName");
        String myitems_Lore_Set = lang.getText(sender, "Argument_SetLore");
        String myitems_Lore_Insert = lang.getText(sender, "Argument_InsertLore");
        String myitems_Lore_Add = lang.getText(sender, "Argument_AddLore");
        String myitems_Lore_Remove = lang.getText(sender, "Argument_RemoveLore");
        String myitems_Lore_Clear = lang.getText(sender, "Argument_ClearLore");
        String myitems_Flag_Add = lang.getText(sender, "Argument_AddFlag");
        String myitems_Flag_Remove = lang.getText(sender, "Argument_RemoveFlag");
        String myitems_Flag_Clear = lang.getText(sender, "Argument_ClearFlag");
        String myitems_Enchant_Add = lang.getText(sender, "Argument_AddEnchant");
        String myitems_Enchant_Remove = lang.getText(sender, "Argument_RemoveEnchant");
        String myitems_Enchant_Clear = lang.getText(sender, "Argument_ClearEnchant");
        String myitems_Attribute_Stats = lang.getText(sender, "Argument_Attribute_Stats");
        String myitems_Attribute_Element = lang.getText(sender, "Argument_Attribute_Element");
        String myitems_Attribute_Buff = lang.getText(sender, "Argument_Attribute_Buffs");
        String myitems_Attribute_Debuff = lang.getText(sender, "Argument_Attribute_Debuffs");
        String myitems_Attribute_NBT = lang.getText(sender, "Argument_Attribute_NBT");
        String myitems_Attribute_Power = lang.getText(sender, "Argument_Attribute_Power");
        String myitems_Attribute_Ability = lang.getText(sender, "Argument_Attribute_Ability");
        String myitems_Socket_Add = lang.getText(sender, "Argument_Socket_Add");
        String myitems_Socket_Load = lang.getText(sender, "Argument_Socket_Load");
        String myitems_Socket_Drop = lang.getText(sender, "Argument_Socket_Drop");
        String myitems_Socket_List = lang.getText(sender, "Argument_Socket_List");
        map.put("plugin", pluginPropertiesResource.getName());
        map.put("page", String.valueOf(page));
        map.put("maxpage", String.valueOf(7));
        map.put("previous_page", String.valueOf(page - 1));
        map.put("next_page", String.valueOf(page + 1));
        map.put("text_previous_page", lang.getText(sender, "Help_Previous_Page"));
        map.put("text_next_page", lang.getText(sender, "Help_Next_Page"));
        map.put("tooltip_help", TextUtil.getJsonTooltip(lang.getText(sender, "Tooltip_MyItems_Help")));
        map.put("tooltip_about", TextUtil.getJsonTooltip(lang.getText(sender, "Tooltip_MyItems_About")));
        map.put("tooltip_reload", TextUtil.getJsonTooltip(lang.getText(sender, "Tooltip_MyItems_Reload")));
        map.put("tooltip_detail", TextUtil.getJsonTooltip(lang.getText(sender, "Tooltip_MyItems_Detail")));
        map.put("tooltip_stats", TextUtil.getJsonTooltip(lang.getText(sender, "Tooltip_MyItems_Stats")));
        map.put("tooltip_list", TextUtil.getJsonTooltip(lang.getText(sender, "Tooltip_MyItems_List")));
        map.put("tooltip_save", TextUtil.getJsonTooltip(lang.getText(sender, "Tooltip_MyItems_Save")));
        map.put("tooltip_load", TextUtil.getJsonTooltip(lang.getText(sender, "Tooltip_MyItems_Load")));
        map.put("tooltip_drop", TextUtil.getJsonTooltip(lang.getText(sender, "Tooltip_MyItems_Drop")));
        map.put("tooltip_remove", TextUtil.getJsonTooltip(lang.getText(sender, "Tooltip_MyItems_Remove")));
        map.put("tooltip_repair", TextUtil.getJsonTooltip(lang.getText(sender, "Tooltip_MyItems_Repair")));
        map.put("tooltip_amount", TextUtil.getJsonTooltip(lang.getText(sender, "Tooltip_MyItems_Amount")));
        map.put("tooltip_data", TextUtil.getJsonTooltip(lang.getText(sender, "Tooltip_MyItems_Data")));
        map.put("tooltip_material", TextUtil.getJsonTooltip(lang.getText(sender, "Tooltip_MyItems_Material")));
        map.put("tooltip_setname", TextUtil.getJsonTooltip(lang.getText(sender, "Tooltip_Setname")));
        map.put("tooltip_lore_set", TextUtil.getJsonTooltip(lang.getText(sender, "Tooltip_Lore_Set")));
        map.put("tooltip_lore_insert", TextUtil.getJsonTooltip(lang.getText(sender, "Tooltip_Lore_Insert")));
        map.put("tooltip_lore_add", TextUtil.getJsonTooltip(lang.getText(sender, "Tooltip_Lore_Add")));
        map.put("tooltip_lore_remove", TextUtil.getJsonTooltip(lang.getText(sender, "Tooltip_Lore_Remove")));
        map.put("tooltip_lore_clear", TextUtil.getJsonTooltip(lang.getText(sender, "Tooltip_Lore_Clear")));
        map.put("tooltip_flag_add", TextUtil.getJsonTooltip(lang.getText(sender, "Tooltip_Flag_Add")));
        map.put("tooltip_flag_remove", TextUtil.getJsonTooltip(lang.getText(sender, "Tooltip_Flag_Remove")));
        map.put("tooltip_flag_clear", TextUtil.getJsonTooltip(lang.getText(sender, "Tooltip_Flag_Clear")));
        map.put("tooltip_enchant_add", TextUtil.getJsonTooltip(lang.getText(sender, "Tooltip_Enchant_Add")));
        map.put("tooltip_enchant_remove", TextUtil.getJsonTooltip(lang.getText(sender, "Tooltip_Enchant_Remove")));
        map.put("tooltip_enchant_clear", TextUtil.getJsonTooltip(lang.getText(sender, "Tooltip_Enchant_Clear")));
        map.put("tooltip_unbreakable", TextUtil.getJsonTooltip(lang.getText(sender, "Tooltip_Unbreakable")));
        map.put("tooltip_att_stats", TextUtil.getJsonTooltip(lang.getText(sender, "Tooltip_Attribute_Stats")));
        map.put("tooltip_att_buffs", TextUtil.getJsonTooltip(lang.getText(sender, "Tooltip_Attribute_Buffs")));
        map.put("tooltip_att_debuffs", TextUtil.getJsonTooltip(lang.getText(sender, "Tooltip_Attribute_Debuffs")));
        map.put("tooltip_att_ability", TextUtil.getJsonTooltip(lang.getText(sender, "Tooltip_Attribute_Ability")));
        map.put("tooltip_att_power", TextUtil.getJsonTooltip(lang.getText(sender, "Tooltip_Attribute_Power")));
        map.put("tooltip_att_nbt", TextUtil.getJsonTooltip(lang.getText(sender, "Tooltip_Attribute_NBT")));
        map.put("tooltip_att_element", TextUtil.getJsonTooltip(lang.getText(sender, "Tooltip_Attribute_Element")));
        map.put("tooltip_socket_add", TextUtil.getJsonTooltip(lang.getText(sender, "Tooltip_Socket_Add")));
        map.put("tooltip_socket_load", TextUtil.getJsonTooltip(lang.getText(sender, "Tooltip_Socket_Load")));
        map.put("tooltip_socket_drop", TextUtil.getJsonTooltip(lang.getText(sender, "Tooltip_Socket_Drop")));
        map.put("tooltip_socket_list", TextUtil.getJsonTooltip(lang.getText(sender, "Tooltip_Socket_List")));
        previousTooltip = TextUtil.placeholder((HashMap)map, previousTooltip);
        nextTooltip = TextUtil.placeholder((HashMap)map, nextTooltip);
        myitems_Help = TextUtil.placeholder((HashMap)map, myitems_Help);
        myitems_About = TextUtil.placeholder((HashMap)map, myitems_About);
        myitems_Reload = TextUtil.placeholder((HashMap)map, myitems_Reload);
        myitems_Detail = TextUtil.placeholder((HashMap)map, myitems_Detail);
        myitems_Stats = TextUtil.placeholder((HashMap)map, myitems_Stats);
        myitems_List = TextUtil.placeholder((HashMap)map, myitems_List);
        myitems_Save = TextUtil.placeholder((HashMap)map, myitems_Save);
        myitems_Load = TextUtil.placeholder((HashMap)map, myitems_Load);
        myitems_Drop = TextUtil.placeholder((HashMap)map, myitems_Drop);
        myitems_Remove = TextUtil.placeholder((HashMap)map, myitems_Remove);
        myitems_Repair = TextUtil.placeholder((HashMap)map, myitems_Repair);
        myitems_Amount = TextUtil.placeholder((HashMap)map, myitems_Amount);
        myitems_Data = TextUtil.placeholder((HashMap)map, myitems_Data);
        myitems_Material = TextUtil.placeholder((HashMap)map, myitems_Material);
        myitems_Unbreakable = TextUtil.placeholder((HashMap)map, myitems_Unbreakable);
        myitems_Setname = TextUtil.placeholder((HashMap)map, myitems_Setname);
        myitems_Lore_Set = TextUtil.placeholder((HashMap)map, myitems_Lore_Set);
        myitems_Lore_Add = TextUtil.placeholder((HashMap)map, myitems_Lore_Add);
        myitems_Lore_Insert = TextUtil.placeholder((HashMap)map, myitems_Lore_Insert);
        myitems_Lore_Remove = TextUtil.placeholder((HashMap)map, myitems_Lore_Remove);
        myitems_Lore_Clear = TextUtil.placeholder((HashMap)map, myitems_Lore_Clear);
        myitems_Flag_Add = TextUtil.placeholder((HashMap)map, myitems_Flag_Add);
        myitems_Flag_Remove = TextUtil.placeholder((HashMap)map, myitems_Flag_Remove);
        myitems_Flag_Clear = TextUtil.placeholder((HashMap)map, myitems_Flag_Clear);
        myitems_Enchant_Add = TextUtil.placeholder((HashMap)map, myitems_Enchant_Add);
        myitems_Enchant_Remove = TextUtil.placeholder((HashMap)map, myitems_Enchant_Remove);
        myitems_Enchant_Clear = TextUtil.placeholder((HashMap)map, myitems_Enchant_Clear);
        myitems_Attribute_Stats = TextUtil.placeholder((HashMap)map, myitems_Attribute_Stats);
        myitems_Attribute_Buff = TextUtil.placeholder((HashMap)map, myitems_Attribute_Buff);
        myitems_Attribute_Debuff = TextUtil.placeholder((HashMap)map, myitems_Attribute_Debuff);
        myitems_Attribute_Ability = TextUtil.placeholder((HashMap)map, myitems_Attribute_Ability);
        myitems_Attribute_Power = TextUtil.placeholder((HashMap)map, myitems_Attribute_Power);
        myitems_Attribute_NBT = TextUtil.placeholder((HashMap)map, myitems_Attribute_NBT);
        myitems_Attribute_Element = TextUtil.placeholder((HashMap)map, myitems_Attribute_Element);
        myitems_Socket_Add = TextUtil.placeholder((HashMap)map, myitems_Socket_Add);
        myitems_Socket_Load = TextUtil.placeholder((HashMap)map, myitems_Socket_Load);
        myitems_Socket_Drop = TextUtil.placeholder((HashMap)map, myitems_Socket_Drop);
        myitems_Socket_List = TextUtil.placeholder((HashMap)map, myitems_Socket_List);
        map.put("previous", previousTooltip);
        map.put("next", nextTooltip);
        helpLeader = TextUtil.placeholder((HashMap)map, helpLeader);
        helpPage = TextUtil.placeholder((HashMap)map, helpPage);
        SenderUtil.sendMessage(sender, helpLeader);
        SenderUtil.sendMessage(sender, "");
        SenderUtil.sendMessage(sender, helpPage);
        if (page == 1) {
            SenderUtil.sendMessage(sender, myitems_Help);
            SenderUtil.sendMessage(sender, myitems_List);
            SenderUtil.sendMessage(sender, myitems_Save);
            SenderUtil.sendMessage(sender, myitems_Remove);
            SenderUtil.sendMessage(sender, myitems_Load);
            SenderUtil.sendMessage(sender, myitems_Drop);
        }
        else if (page == 2) {
            SenderUtil.sendMessage(sender, myitems_Detail);
            SenderUtil.sendMessage(sender, myitems_Stats);
            SenderUtil.sendMessage(sender, myitems_Amount);
            SenderUtil.sendMessage(sender, myitems_Data);
            SenderUtil.sendMessage(sender, myitems_Material);
            SenderUtil.sendMessage(sender, myitems_Repair);
        }
        else if (page == 3) {
            SenderUtil.sendMessage(sender, myitems_Setname);
            SenderUtil.sendMessage(sender, myitems_Lore_Set);
            SenderUtil.sendMessage(sender, myitems_Lore_Insert);
            SenderUtil.sendMessage(sender, myitems_Lore_Add);
            SenderUtil.sendMessage(sender, myitems_Lore_Remove);
            SenderUtil.sendMessage(sender, myitems_Lore_Clear);
        }
        else if (page == 4) {
            SenderUtil.sendMessage(sender, myitems_Flag_Add);
            SenderUtil.sendMessage(sender, myitems_Flag_Remove);
            SenderUtil.sendMessage(sender, myitems_Flag_Clear);
            SenderUtil.sendMessage(sender, myitems_Enchant_Add);
            SenderUtil.sendMessage(sender, myitems_Enchant_Remove);
            SenderUtil.sendMessage(sender, myitems_Enchant_Clear);
        }
        else if (page == 5) {
            SenderUtil.sendMessage(sender, myitems_Attribute_Stats);
            SenderUtil.sendMessage(sender, myitems_Attribute_Buff);
            SenderUtil.sendMessage(sender, myitems_Attribute_Debuff);
            SenderUtil.sendMessage(sender, myitems_Attribute_Ability);
            SenderUtil.sendMessage(sender, myitems_Attribute_Power);
            SenderUtil.sendMessage(sender, myitems_Attribute_NBT);
        }
        else if (page == 6) {
            SenderUtil.sendMessage(sender, myitems_Attribute_Element);
            SenderUtil.sendMessage(sender, myitems_Socket_Add);
            SenderUtil.sendMessage(sender, myitems_Socket_Load);
            SenderUtil.sendMessage(sender, myitems_Socket_Drop);
            SenderUtil.sendMessage(sender, myitems_Socket_List);
            SenderUtil.sendMessage(sender, myitems_Unbreakable);
        }
        else if (page == 7) {
            SenderUtil.sendMessage(sender, myitems_About);
            SenderUtil.sendMessage(sender, myitems_Reload);
        }
        SenderUtil.playSound(sender, SoundEnum.ENTITY_EXPERIENCE_ORB_PICKUP);
        SenderUtil.sendMessage(sender, helpPage);
        return true;
    }
    
    public static final List<String> list(final CommandSender sender, final Command command, final String label, final String[] args) {
        final MyItems plugin = (MyItems)JavaPlugin.getPlugin((Class)MyItems.class);
        final ItemManager itemManager = plugin.getGameManager().getItemManager();
        final PluginManager pluginManager = plugin.getPluginManager();
        final CommandManager commandManager = pluginManager.getCommandManager();
        final LanguageManager lang = pluginManager.getLanguageManager();
        final MainConfig mainConfig = MainConfig.getInstance();
        final List<String> list = new ArrayList<String>();
        if (!commandManager.checkPermission(sender, "MyItems_List")) {
            final String permission = commandManager.getPermission("MyItems_List");
            final MessageBuild message = lang.getMessage(sender, "Permission_Lack");
            message.sendMessage(sender, "permission", permission);
            SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
            return list;
        }
        if (itemManager.getItems().isEmpty()) {
            final MessageBuild message2 = lang.getMessage(sender, "Item_Database_Empty");
            message2.sendMessage(sender);
            SenderUtil.playSound(sender, SoundEnum.BLOCK_WOOD_BUTTON_CLICK_ON);
            return list;
        }
        final List<String> keyList = (List<String>)SortUtil.toList((Collection)itemManager.getItemIDs());
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
        final String codeTooltip = mainConfig.getUtilityTooltip();
        final HashMap<String, String> map = new HashMap<String, String>();
        String listHeaderMessage = lang.getText(sender, "List_Header");
        map.put("page", String.valueOf(page));
        map.put("maxpage", String.valueOf(maxPage));
        listHeaderMessage = TextUtil.placeholder((HashMap)map, listHeaderMessage);
        SenderUtil.sendMessage(sender, listHeaderMessage);
        for (int addNum = (page - 1) * 5, t = 0; t < 5 && t + addNum < size; ++t) {
            final int index = t + addNum;
            final String key = keyList.get(index);
            final ItemStack item = itemManager.getItem(key);
            final HashMap<String, String> subMap = new HashMap<String, String>();
            String listItemMessage = lang.getText(sender, "List_Item");
            subMap.put("index", String.valueOf(index + 1));
            subMap.put("item", key);
            subMap.put("maxpage", String.valueOf(page));
            subMap.put("tooltip", JsonUtil.generateJsonItem(codeTooltip, item));
            listItemMessage = TextUtil.placeholder((HashMap)subMap, listItemMessage);
            list.add(key);
            SenderUtil.sendMessage(sender, listItemMessage);
        }
        SenderUtil.playSound(sender, SoundEnum.BLOCK_WOOD_BUTTON_CLICK_ON);
        return list;
    }
}
