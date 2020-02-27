// 
// Decompiled by Procyon v0.5.36
// 

package com.praya.myitems.tabcompleter;

import com.praya.myitems.manager.plugin.CommandManager;
import com.praya.myitems.manager.plugin.PluginManager;
import com.praya.agarthalib.utility.TabCompleterUtil;
import org.bukkit.inventory.ItemFlag;
import com.praya.agarthalib.utility.SenderUtil;
import core.praya.agarthalib.enums.branch.SoundEnum;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import com.praya.myitems.MyItems;
import org.bukkit.command.TabCompleter;
import com.praya.myitems.builder.handler.HandlerTabCompleter;

public class TabCompleterFlagAdd extends HandlerTabCompleter implements TabCompleter
{
    public TabCompleterFlagAdd(final MyItems plugin) {
        super(plugin);
    }
    
    public List<String> onTabComplete(final CommandSender sender, final Command command, final String label, final String[] args) {
        final PluginManager pluginManager = this.plugin.getPluginManager();
        final CommandManager commandManager = pluginManager.getCommandManager();
        final List<String> tabList = new ArrayList<String>();
        SenderUtil.playSound(sender, SoundEnum.BLOCK_WOOD_BUTTON_CLICK_ON);
        if (SenderUtil.isPlayer(sender) && args.length == 1 && commandManager.checkPermission(sender, "Flag_Add")) {
            ItemFlag[] values;
            for (int length = (values = ItemFlag.values()).length, i = 0; i < length; ++i) {
                final ItemFlag flag = values[i];
                tabList.add(flag.toString());
            }
        }
        return (List<String>)TabCompleterUtil.returnList((List)tabList, args);
    }
}
