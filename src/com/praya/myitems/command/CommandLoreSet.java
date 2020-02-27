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
import java.util.HashMap;
import com.praya.agarthalib.utility.MathUtil;
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

public class CommandLoreSet extends HandlerCommand implements CommandExecutor
{
    public CommandLoreSet(final MyItems plugin) {
        super(plugin);
    }
    
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        return setLore(sender, command, label, args);
    }
    
    protected static final boolean setLore(final CommandSender sender, final Command command, final String label, final String[] args) {
        final MyItems plugin = (MyItems)JavaPlugin.getPlugin((Class)MyItems.class);
        final PluginManager pluginManager = plugin.getPluginManager();
        final CommandManager commandManager = pluginManager.getCommandManager();
        final LanguageManager lang = pluginManager.getLanguageManager();
        if (!commandManager.checkPermission(sender, "Lore_Set")) {
            final String permission = commandManager.getPermission("Lore_Set");
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
            final String tooltip = TextUtil.getJsonTooltip(lang.getText(sender, "Tooltip_Lore_Set"));
            final MessageBuild message4 = lang.getMessage(sender, "Argument_SetLore");
            message4.sendMessage(sender, "tooltip_lore_set", tooltip);
            SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
            return true;
        }
        final String textLine = args[0];
        if (!MathUtil.isNumber(textLine)) {
            final MessageBuild message4 = lang.getMessage(sender, "Argument_Invalid_Value");
            message4.sendMessage(sender);
            SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
            return true;
        }
        final int line = MathUtil.parseInteger(textLine);
        if (line < 1) {
            final MessageBuild message5 = lang.getMessage(sender, "Argument_Invalid_Line");
            message5.sendMessage(sender);
            SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
            return true;
        }
        final String lore = (args.length < 2) ? "" : TextUtil.hookPlaceholderAPI(player, TextUtil.concatenate(args, 2));
        final MessageBuild message6 = lang.getMessage(sender, "MyItems_SetLore_Success");
        final HashMap<String, String> mapPlaceholder = new HashMap<String, String>();
        mapPlaceholder.put("line", String.valueOf(line));
        mapPlaceholder.put("lore", lore);
        EquipmentUtil.setLore(item, line, lore);
        message6.sendMessage(sender, (HashMap)mapPlaceholder);
        SenderUtil.playSound(sender, SoundEnum.ENTITY_EXPERIENCE_ORB_PICKUP);
        player.updateInventory();
        return true;
    }
}
