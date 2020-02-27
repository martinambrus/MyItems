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

public class CommandFlag extends HandlerCommand implements CommandExecutor
{
    public CommandFlag(final MyItems plugin) {
        super(plugin);
    }
    
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        final CommandManager commandManager = this.plugin.getPluginManager().getCommandManager();
        if (args.length <= 0) {
            return true;
        }
        final String subCommand = args[0];
        if (commandManager.checkCommand(subCommand, "Flag_Add")) {
            final String[] fullArgs = TextUtil.pressList(args, 2);
            return CommandFlagAdd.addFlag(sender, command, label, fullArgs);
        }
        if (commandManager.checkCommand(subCommand, "Flag_Remove")) {
            final String[] fullArgs = TextUtil.pressList(args, 2);
            return CommandFlagRemove.removeFlag(sender, command, label, fullArgs);
        }
        if (commandManager.checkCommand(subCommand, "Flag_Clear")) {
            final String[] fullArgs = TextUtil.pressList(args, 2);
            return CommandFlagClear.clearFlag(sender, command, label, fullArgs);
        }
        return true;
    }
}
