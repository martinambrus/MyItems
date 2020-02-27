// 
// Decompiled by Procyon v0.5.36
// 

package com.praya.myitems.command;

import core.praya.agarthalib.bridge.unity.BridgeTagsNBT;
import org.bukkit.inventory.ItemStack;
import org.bukkit.entity.Player;
import core.praya.agarthalib.builder.message.MessageBuild;
import com.praya.myitems.manager.plugin.LanguageManager;
import com.praya.myitems.manager.plugin.CommandManager;
import com.praya.myitems.manager.plugin.PluginManager;
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

public class CommandUnbreakable extends HandlerCommand implements CommandExecutor
{
    public CommandUnbreakable(final MyItems plugin) {
        super(plugin);
    }
    
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        return unbreakable(sender, command, label, args);
    }
    
    protected static final boolean unbreakable(final CommandSender sender, final Command command, final String label, final String[] args) {
        final MyItems plugin = (MyItems)JavaPlugin.getPlugin((Class)MyItems.class);
        final PluginManager pluginManager = plugin.getPluginManager();
        final CommandManager commandManager = pluginManager.getCommandManager();
        final LanguageManager lang = pluginManager.getLanguageManager();
        if (!commandManager.checkPermission(sender, "MyItems_Unbreakable")) {
            final String permission = commandManager.getPermission("MyItems_Unbreakable");
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
        final BridgeTagsNBT bridgeTagsNBT = Bridge.getBridgeTagsNBT();
        final boolean isUnbreakable = bridgeTagsNBT.isUnbreakable(item);
        if (args.length > 0) {
            final String textBoolean = args[0];
            if (textBoolean.equalsIgnoreCase("true") || textBoolean.equalsIgnoreCase("on")) {
                final MessageBuild message4 = lang.getMessage(sender, "MyItems_Unbreakable_Turn_On");
                bridgeTagsNBT.setUnbreakable(item, true);
                message4.sendMessage(sender);
                SenderUtil.playSound(sender, SoundEnum.ENTITY_EXPERIENCE_ORB_PICKUP);
                return true;
            }
            if (textBoolean.equalsIgnoreCase("false") || textBoolean.equalsIgnoreCase("off")) {
                final MessageBuild message4 = lang.getMessage(sender, "MyItems_Unbreakable_Turn_Off");
                bridgeTagsNBT.setUnbreakable(item, false);
                message4.sendMessage(sender);
                SenderUtil.playSound(sender, SoundEnum.ENTITY_EXPERIENCE_ORB_PICKUP);
                return true;
            }
        }
        if (isUnbreakable) {
            final MessageBuild message5 = lang.getMessage(sender, "MyItems_Unbreakable_Turn_Off");
            bridgeTagsNBT.setUnbreakable(item, false);
            message5.sendMessage(sender);
            SenderUtil.playSound(sender, SoundEnum.ENTITY_EXPERIENCE_ORB_PICKUP);
            return true;
        }
        final MessageBuild message5 = lang.getMessage(sender, "MyItems_Unbreakable_Turn_On");
        bridgeTagsNBT.setUnbreakable(item, true);
        message5.sendMessage(sender);
        SenderUtil.playSound(sender, SoundEnum.ENTITY_EXPERIENCE_ORB_PICKUP);
        player.updateInventory();
        return true;
    }
}
