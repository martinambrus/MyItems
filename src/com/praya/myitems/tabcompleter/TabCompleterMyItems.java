// 
// Decompiled by Procyon v0.5.36
// 

package com.praya.myitems.tabcompleter;

import java.util.Iterator;
import core.praya.agarthalib.builder.message.MessageBuild;
import com.praya.myitems.manager.plugin.LanguageManager;
import com.praya.myitems.manager.plugin.CommandManager;
import com.praya.myitems.manager.game.ItemSetManager;
import com.praya.myitems.manager.game.ItemGeneratorManager;
import com.praya.myitems.manager.game.ItemManager;
import com.praya.myitems.manager.game.GameManager;
import com.praya.myitems.manager.plugin.PluginManager;
import com.praya.agarthalib.utility.TabCompleterUtil;
import org.bukkit.World;
import org.bukkit.Bukkit;
import java.util.Collection;
import com.praya.agarthalib.utility.TextUtil;
import com.praya.agarthalib.utility.SenderUtil;
import core.praya.agarthalib.enums.branch.SoundEnum;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import com.praya.myitems.MyItems;
import org.bukkit.command.TabCompleter;
import com.praya.myitems.builder.handler.HandlerTabCompleter;

public class TabCompleterMyItems extends HandlerTabCompleter implements TabCompleter
{
    public TabCompleterMyItems(final MyItems plugin) {
        super(plugin);
    }
    
    public List<String> onTabComplete(final CommandSender sender, final Command command, final String label, final String[] args) {
        final PluginManager pluginManager = this.plugin.getPluginManager();
        final GameManager gameManager = this.plugin.getGameManager();
        final ItemManager itemManager = gameManager.getItemManager();
        final ItemGeneratorManager itemGeneratorManager = gameManager.getItemGeneratorManager();
        final ItemSetManager itemSetManager = gameManager.getItemSetManager();
        final CommandManager commandManager = pluginManager.getCommandManager();
        final LanguageManager lang = pluginManager.getLanguageManager();
        final List<String> tabList = new ArrayList<String>();
        SenderUtil.playSound(sender, SoundEnum.BLOCK_WOOD_BUTTON_CLICK_ON);
        if (args.length == 1) {
            if (commandManager.checkPermission(sender, "MyItems_Help")) {
                tabList.add("Help");
            }
            if (commandManager.checkPermission(sender, "MyItems_About")) {
                tabList.add("About");
            }
            if (commandManager.checkPermission(sender, "MyItems_Reload")) {
                tabList.add("Reload");
            }
            if (commandManager.checkPermission(sender, "MyItems_Detail")) {
                tabList.add("Detail");
            }
            if (commandManager.checkPermission(sender, "MyItems_Data")) {
                tabList.add("Data");
            }
            if (commandManager.checkPermission(sender, "MyItems_Amount")) {
                tabList.add("Amount");
            }
            if (commandManager.checkPermission(sender, "MyItems_Material")) {
                tabList.add("Material");
            }
            if (commandManager.checkPermission(sender, "MyItems_Save")) {
                tabList.add("Save");
            }
            if (commandManager.checkPermission(sender, "MyItems_Load")) {
                tabList.add("Load");
            }
            if (commandManager.checkPermission(sender, "MyItems_Drop")) {
                tabList.add("Drop");
            }
            if (commandManager.checkPermission(sender, "MyItems_Remove")) {
                tabList.add("Remove");
            }
            if (commandManager.checkPermission(sender, "MyItems_Repair")) {
                tabList.add("Repair");
            }
            if (commandManager.checkPermission(sender, "MyItems_Simulation")) {
                tabList.add("Simulation");
            }
        }
        else if (args.length == 2) {
            final String argument1 = args[0];
            if (commandManager.checkCommand(argument1, "MyItems_Save")) {
                if (commandManager.checkPermission(sender, "MyItems_Save")) {
                    final String tooltip = TextUtil.getJsonTooltip(lang.getText(sender, "Tooltip_MyItems_Save"));
                    final MessageBuild message = lang.getMessage(sender, "Argument_MyItems_Save");
                    message.sendMessage(sender, "tooltip_save", tooltip);
                }
            }
            else if (commandManager.checkCommand(argument1, "MyItems_Load")) {
                if (commandManager.checkPermission(sender, "MyItems_Load")) {
                    tabList.add("Custom");
                    tabList.add("Generator");
                    tabList.add("Set");
                }
            }
            else if (commandManager.checkCommand(argument1, "MyItems_Drop")) {
                if (commandManager.checkPermission(sender, "MyItems_Drop")) {
                    tabList.add("Custom");
                    tabList.add("Generator");
                    tabList.add("Set");
                }
            }
            else if (commandManager.checkCommand(argument1, "MyItems_Remove") && commandManager.checkPermission(sender, "MyItems_Remove")) {
                final Collection<String> itemIDs = itemManager.getItemIDs();
                tabList.addAll(itemIDs);
            }
        }
        else if (args.length == 3) {
            final String argument1 = args[0];
            final String argument2 = args[1];
            if (commandManager.checkCommand(argument1, "MyItems_Load")) {
                if (commandManager.checkPermission(sender, "MyItems_Load")) {
                    if (commandManager.checkCommand(argument2, "MyItems_Load_Custom")) {
                        final Collection<String> itemIDs2 = itemManager.getItemIDs();
                        tabList.addAll(itemIDs2);
                    }
                    else if (commandManager.checkCommand(argument2, "MyItems_Load_Generator")) {
                        final Collection<String> itemIDs2 = itemGeneratorManager.getItemGeneratorIDs();
                        tabList.addAll(itemIDs2);
                    }
                    else if (commandManager.checkCommand(argument2, "MyItems_Load_Set")) {
                        final Collection<String> itemIDs2 = itemSetManager.getItemComponentIDs();
                        tabList.addAll(itemIDs2);
                    }
                }
            }
            else if (commandManager.checkCommand(argument1, "MyItems_Drop") && commandManager.checkPermission(sender, "MyItems_Drop")) {
                if (commandManager.checkCommand(argument2, "MyItems_Drop_Custom")) {
                    final Collection<String> itemIDs2 = itemManager.getItemIDs();
                    tabList.addAll(itemIDs2);
                }
                else if (commandManager.checkCommand(argument2, "MyItems_Drop_Generator")) {
                    final Collection<String> itemIDs2 = itemGeneratorManager.getItemGeneratorIDs();
                    tabList.addAll(itemIDs2);
                }
                else if (commandManager.checkCommand(argument2, "MyItems_Load_Set")) {
                    final Collection<String> itemIDs2 = itemSetManager.getItemComponentIDs();
                    tabList.addAll(itemIDs2);
                }
            }
        }
        else if (args.length == 4) {
            final String argument1 = args[0];
            if (commandManager.checkCommand(argument1, "MyItems_Drop") && commandManager.checkPermission(sender, "MyItems_Drop")) {
                final Collection<String> worldNames = itemSetManager.getItemComponentIDs();
                for (final World world : Bukkit.getWorlds()) {
                    final String worldName = world.getName();
                    worldNames.add(worldName);
                }
                tabList.addAll(worldNames);
            }
        }
        return (List<String>)TabCompleterUtil.returnList((List)tabList, args);
    }
}
