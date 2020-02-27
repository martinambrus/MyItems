// 
// Decompiled by Procyon v0.5.36
// 

package com.praya.myitems.command;

import com.praya.myitems.manager.plugin.CommandManager;
import com.praya.agarthalib.utility.TextUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import com.praya.myitems.MyItems;
import org.bukkit.command.CommandExecutor;
import com.praya.myitems.builder.handler.HandlerCommand;

public class CommandEnchant extends HandlerCommand implements CommandExecutor
{
    public CommandEnchant(final MyItems plugin) {
        super(plugin);
    }
    
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        final CommandManager commandManager = this.plugin.getPluginManager().getCommandManager();
        if (args.length <= 0) {
            return true;
        }
        final String subCommand = args[0];
        if (commandManager.checkCommand(subCommand, "Enchant_Add")) {
            final String[] fullArgs = TextUtil.pressList(args, 2);
            return CommandEnchantAdd.addEnchant(sender, command, label, fullArgs);
        }
        if (commandManager.checkCommand(subCommand, "Enchant_Remove")) {
            final String[] fullArgs = TextUtil.pressList(args, 2);
            return CommandEnchantRemove.removeEnchant(sender, command, label, fullArgs);
        }
        if (commandManager.checkCommand(subCommand, "Enchant_Clear")) {
            final String[] fullArgs = TextUtil.pressList(args, 2);
            return CommandEnchantClear.clearEnchant(sender, command, label, fullArgs);
        }
        return true;
    }
}
