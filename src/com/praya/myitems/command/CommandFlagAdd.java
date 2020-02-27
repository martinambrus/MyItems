// 
// Decompiled by Procyon v0.5.36
// 

package com.praya.myitems.command;

import org.bukkit.inventory.ItemStack;
import org.bukkit.entity.Player;
import core.praya.agarthalib.builder.message.MessageBuild;
import com.praya.myitems.manager.plugin.LanguageManager;
import com.praya.myitems.manager.plugin.CommandManager;
import com.praya.myitems.manager.plugin.PluginManager;
import org.bukkit.inventory.ItemFlag;
import com.praya.agarthalib.utility.ItemFlagUtil;
import com.praya.agarthalib.utility.TextUtil;
import com.praya.agarthalib.utility.EquipmentUtil;
import core.praya.agarthalib.enums.main.Slot;
import core.praya.agarthalib.bridge.unity.Bridge;
import com.praya.agarthalib.utility.PlayerUtil;
import com.praya.agarthalib.utility.SenderUtil;
import core.praya.agarthalib.enums.branch.SoundEnum;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import com.praya.myitems.MyItems;
import org.bukkit.command.CommandExecutor;
import com.praya.myitems.builder.handler.HandlerCommand;

public class CommandFlagAdd extends HandlerCommand implements CommandExecutor
{
    public CommandFlagAdd(final MyItems plugin) {
        super(plugin);
    }
    
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        return addFlag(sender, command, label, args);
    }
    
    protected static final boolean addFlag(final CommandSender sender, final Command command, final String label, final String[] args) {
        final MyItems plugin = (MyItems)JavaPlugin.getPlugin((Class)MyItems.class);
        final PluginManager pluginManager = plugin.getPluginManager();
        final CommandManager commandManager = pluginManager.getCommandManager();
        final LanguageManager lang = pluginManager.getLanguageManager();
        if (!commandManager.checkPermission(sender, "Flag_Add")) {
            final String permission = commandManager.getPermission("Flag_Add");
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
        final Player player = PlayerUtil.parse(sender);
        final ItemStack item = Bridge.getBridgeEquipment().getEquipment(player, Slot.MAINHAND);
        if (!EquipmentUtil.isSolid(item)) {
            final MessageBuild message3 = lang.getMessage(sender, "Item_MainHand_Empty");
            message3.sendMessage(sender);
            SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
            return true;
        }
        if (args.length < 1) {
            final String tooltip = TextUtil.getJsonTooltip(lang.getText(sender, "Tooltip_Flag_Add"));
            final MessageBuild message4 = lang.getMessage(sender, "Argument_AddFlag");
            message4.sendMessage(sender, "tooltip_flag_add", tooltip);
            SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
            return true;
        }
        final String flagName = args[0];
        final ItemFlag flag = ItemFlagUtil.getFlag(flagName);
        if (flag == null) {
            final MessageBuild message5 = lang.getMessage(sender, "Item_Flag_Not_Exist");
            message5.sendMessage(sender, "flag", flagName);
            SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
            return true;
        }
        final MessageBuild message5 = lang.getMessage(sender, "MyItems_AddFlag_Success");
        ItemFlagUtil.addFlag(item, new ItemFlag[] { flag });
        message5.sendMessage(sender, "flag", flag.toString());
        SenderUtil.playSound(sender, SoundEnum.ENTITY_EXPERIENCE_ORB_PICKUP);
        player.updateInventory();
        return true;
    }
}
