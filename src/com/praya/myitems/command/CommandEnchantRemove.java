// 
// Decompiled by Procyon v0.5.36
// 

package com.praya.myitems.command;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.entity.Player;
import core.praya.agarthalib.builder.message.MessageBuild;
import com.praya.myitems.manager.plugin.LanguageManager;
import com.praya.myitems.manager.plugin.CommandManager;
import com.praya.myitems.manager.plugin.PluginManager;
import core.praya.agarthalib.enums.branch.EnchantmentEnum;
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

public class CommandEnchantRemove extends HandlerCommand implements CommandExecutor
{
    public CommandEnchantRemove(final MyItems plugin) {
        super(plugin);
    }
    
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        return removeEnchant(sender, command, label, args);
    }
    
    protected static final boolean removeEnchant(final CommandSender sender, final Command command, final String label, final String[] args) {
        final MyItems plugin = (MyItems)JavaPlugin.getPlugin((Class)MyItems.class);
        final PluginManager pluginManager = plugin.getPluginManager();
        final CommandManager commandManager = pluginManager.getCommandManager();
        final LanguageManager lang = pluginManager.getLanguageManager();
        if (!commandManager.checkPermission(sender, "Enchant_Remove")) {
            final String permission = commandManager.getPermission("Enchant_Remove");
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
            final String tooltip = TextUtil.getJsonTooltip(lang.getText(sender, "Tooltip_Enchant_Remove"));
            final MessageBuild message4 = lang.getMessage(sender, "Argument_RemoveEnchant");
            message4.sendMessage(sender, "tooltip_enchant_remove", tooltip);
            SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
            return true;
        }
        final String enchantmentName = args[0];
        final EnchantmentEnum enchantmentEnum = EnchantmentEnum.getEnchantmentEnum(enchantmentName);
        final Enchantment enchantment = (enchantmentEnum != null) ? enchantmentEnum.getEnchantment() : null;
        if (enchantmentEnum == null) {
            final MessageBuild message5 = lang.getMessage(sender, "Item_Enchantment_Not_Exist");
            message5.sendMessage(sender, "enchantment", enchantmentName);
            SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
            return true;
        }
        if (!item.containsEnchantment(enchantment)) {
            final MessageBuild message5 = lang.getMessage(sender, "Item_Enchantment_Empty");
            message5.sendMessage(sender, "enchantment", enchantmentName);
            SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
            return true;
        }
        final MessageBuild message5 = lang.getMessage(sender, "MyItems_RemoveEnchant_Success");
        EquipmentUtil.removeEnchantment(item, enchantment);
        message5.sendMessage(sender, "enchantment", enchantment.getName());
        SenderUtil.playSound(sender, SoundEnum.ENTITY_EXPERIENCE_ORB_PICKUP);
        player.updateInventory();
        return true;
    }
}
