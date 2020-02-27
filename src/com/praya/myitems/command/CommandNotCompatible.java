// 
// Decompiled by Procyon v0.5.36
// 

package com.praya.myitems.command;

import core.praya.agarthalib.builder.message.MessageBuild;
import com.praya.myitems.manager.plugin.LanguageManager;
import com.praya.myitems.manager.plugin.PluginManager;
import com.praya.agarthalib.utility.SenderUtil;
import core.praya.agarthalib.enums.branch.SoundEnum;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import com.praya.myitems.MyItems;
import org.bukkit.command.CommandExecutor;
import com.praya.myitems.builder.handler.HandlerCommand;

public class CommandNotCompatible extends HandlerCommand implements CommandExecutor
{
    public CommandNotCompatible(final MyItems plugin) {
        super(plugin);
    }
    
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        final PluginManager pluginManager = this.plugin.getPluginManager();
        final LanguageManager lang = pluginManager.getLanguageManager();
        final MessageBuild message = lang.getMessage(sender, "MyItems_Not_Compatible");
        message.sendMessage(sender);
        SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
        return true;
    }
}
