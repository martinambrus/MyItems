// 
// Decompiled by Procyon v0.5.36
// 

package com.praya.myitems.tabcompleter;

import java.util.Iterator;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.ItemStack;
import org.bukkit.entity.Player;
import com.praya.myitems.manager.plugin.CommandManager;
import com.praya.myitems.manager.plugin.PluginManager;
import com.praya.agarthalib.utility.TabCompleterUtil;
import org.bukkit.enchantments.Enchantment;
import core.praya.agarthalib.enums.main.Slot;
import core.praya.agarthalib.bridge.unity.Bridge;
import com.praya.agarthalib.utility.PlayerUtil;
import com.praya.agarthalib.utility.SenderUtil;
import core.praya.agarthalib.enums.branch.SoundEnum;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import com.praya.myitems.MyItems;
import org.bukkit.command.TabCompleter;
import com.praya.myitems.builder.handler.HandlerTabCompleter;

public class TabCompleterEnchantmentRemove extends HandlerTabCompleter implements TabCompleter
{
    public TabCompleterEnchantmentRemove(final MyItems plugin) {
        super(plugin);
    }
    
    public List<String> onTabComplete(final CommandSender sender, final Command command, final String label, final String[] args) {
        final PluginManager pluginManager = this.plugin.getPluginManager();
        final CommandManager commandManager = pluginManager.getCommandManager();
        final List<String> tabList = new ArrayList<String>();
        SenderUtil.playSound(sender, SoundEnum.BLOCK_WOOD_BUTTON_CLICK_ON);
        if (SenderUtil.isPlayer(sender)) {
            final Player player = PlayerUtil.parse(sender);
            final ItemStack item = Bridge.getBridgeEquipment().getEquipment(player, Slot.MAINHAND);
            final ItemMeta itemmeta = item.getItemMeta();
            if (args.length == 1 && commandManager.checkPermission(sender, "Enchant_Remove") && itemmeta.hasEnchants()) {
                for (final Enchantment enchant : itemmeta.getEnchants().keySet()) {
                    tabList.add(enchant.getName());
                }
            }
        }
        return (List<String>)TabCompleterUtil.returnList((List)tabList, args);
    }
}
