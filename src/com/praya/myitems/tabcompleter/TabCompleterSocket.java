// 
// Decompiled by Procyon v0.5.36
// 

package com.praya.myitems.tabcompleter;

import java.util.Iterator;
import com.praya.myitems.manager.plugin.CommandManager;
import com.praya.myitems.manager.game.SocketManager;
import com.praya.myitems.manager.game.GameManager;
import com.praya.myitems.manager.plugin.PluginManager;
import com.praya.agarthalib.utility.TabCompleterUtil;
import com.praya.agarthalib.utility.ListUtil;
import org.bukkit.World;
import org.bukkit.Bukkit;
import com.praya.agarthalib.utility.MathUtil;
import java.util.Collection;
import com.praya.agarthalib.utility.SenderUtil;
import core.praya.agarthalib.enums.branch.SoundEnum;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import com.praya.myitems.MyItems;
import org.bukkit.command.TabCompleter;
import com.praya.myitems.builder.handler.HandlerTabCompleter;

public class TabCompleterSocket extends HandlerTabCompleter implements TabCompleter
{
    public TabCompleterSocket(final MyItems plugin) {
        super(plugin);
    }
    
    public List<String> onTabComplete(final CommandSender sender, final Command command, final String label, final String[] args) {
        final PluginManager pluginManager = this.plugin.getPluginManager();
        final GameManager gameManager = this.plugin.getGameManager();
        final SocketManager socketManager = gameManager.getSocketManager();
        final CommandManager commandManager = pluginManager.getCommandManager();
        final List<String> tabList = new ArrayList<String>();
        SenderUtil.playSound(sender, SoundEnum.BLOCK_WOOD_BUTTON_CLICK_ON);
        if (args.length == 1) {
            if (commandManager.checkPermission(sender, "Socket_Add")) {
                tabList.add("Add");
            }
            if (commandManager.checkPermission(sender, "Socket_Load")) {
                tabList.add("Load");
            }
            if (commandManager.checkPermission(sender, "Socket_Drop")) {
                tabList.add("Drop");
            }
            if (commandManager.checkPermission(sender, "Socket_List")) {
                tabList.add("List");
            }
        }
        else if (args.length == 2) {
            final String argument1 = args[0];
            if (commandManager.checkCommand(argument1, "Socket_Add")) {
                if (commandManager.checkPermission(sender, "Socket_Add")) {
                    tabList.add("Empty");
                    tabList.add("Locked");
                }
            }
            else if (commandManager.checkCommand(argument1, "Socket_Load")) {
                if (commandManager.checkPermission(sender, "Socket_Load")) {
                    tabList.add("Gems");
                    tabList.add("Rod");
                }
            }
            else if (commandManager.checkCommand(argument1, "Socket_Drop") && commandManager.checkPermission(sender, "Socket_Drop")) {
                tabList.add("Gems");
                tabList.add("Rod");
            }
        }
        else if (args.length == 3) {
            final String argument1 = args[0];
            final String argument2 = args[1];
            if (commandManager.checkCommand(argument1, "Socket_Load")) {
                if (commandManager.checkPermission(sender, "Socket_Load")) {
                    if (commandManager.checkCommand(argument2, "Socket_Load_Gems")) {
                        if (commandManager.checkPermission(sender, "Socket_Load_Gems")) {
                            final Collection<String> gems = socketManager.getSocketIDs();
                            if (gems.isEmpty()) {
                                tabList.add("");
                            }
                            else {
                                tabList.addAll(gems);
                            }
                        }
                    }
                    else if (commandManager.checkCommand(argument2, "Socket_Load_Rod") && commandManager.checkPermission(sender, "Socket_Load_Rod")) {
                        tabList.add("Unlock");
                        tabList.add("Remove");
                    }
                }
            }
            else if (commandManager.checkCommand(argument1, "Socket_Drop") && commandManager.checkPermission(sender, "Socket_Drop")) {
                if (commandManager.checkCommand(argument2, "Socket_Drop_Gems")) {
                    if (commandManager.checkPermission(sender, "Socket_Drop_Gems")) {
                        final Collection<String> gems = socketManager.getSocketIDs();
                        if (gems.isEmpty()) {
                            tabList.add("");
                        }
                        else {
                            tabList.addAll(gems);
                        }
                    }
                }
                else if (commandManager.checkCommand(argument2, "Socket_Drop_Rod") && commandManager.checkPermission(sender, "Socket_Drop_Rod")) {
                    tabList.add("Unlock");
                    tabList.add("Remove");
                }
            }
        }
        else if (args.length == 4) {
            final String argument1 = args[0];
            final String argument2 = args[1];
            final String argument3 = args[3];
            if (commandManager.checkCommand(argument1, "Socket_Drop") && commandManager.checkPermission(sender, "Socket_Drop") && commandManager.checkCommand(argument2, "Socket_Drop_Rod") && commandManager.checkPermission(sender, "Socket_Drop_Rod")) {
                final int page = MathUtil.isNumber(argument3) ? MathUtil.parseInteger(argument3) : 1;
                final List<String> keyList = new ArrayList<String>();
                for (final World world : Bukkit.getWorlds()) {
                    keyList.add(world.getName());
                }
                return (List<String>)ListUtil.sendList(sender, (List)keyList, page);
            }
        }
        else if (args.length == 5) {
            final String argument1 = args[0];
            final String argument2 = args[1];
            final String argument4 = args[4];
            if (commandManager.checkCommand(argument1, "Socket_Drop") && commandManager.checkPermission(sender, "Socket_Drop") && commandManager.checkCommand(argument2, "Socket_Drop_Gems") && commandManager.checkPermission(sender, "Socket_Drop_Gems")) {
                final int page = MathUtil.isNumber(argument4) ? MathUtil.parseInteger(argument4) : 1;
                final List<String> keyList = new ArrayList<String>();
                for (final World world : Bukkit.getWorlds()) {
                    keyList.add(world.getName());
                }
                return (List<String>)ListUtil.sendList(sender, (List)keyList, page);
            }
        }
        return (List<String>)TabCompleterUtil.returnList((List)tabList, args);
    }
}
