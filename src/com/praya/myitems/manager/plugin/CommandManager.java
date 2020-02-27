// 
// Decompiled by Procyon v0.5.36
// 

package com.praya.myitems.manager.plugin;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.command.CommandSender;
import java.util.Iterator;
import core.praya.agarthalib.builder.command.CommandBuild;
import java.util.Collection;
import com.praya.myitems.MyItems;
import com.praya.myitems.config.plugin.CommandConfig;
import com.praya.myitems.builder.handler.HandlerManager;

public class CommandManager extends HandlerManager
{
    private final CommandConfig commandConfig;
    
    protected CommandManager(final MyItems plugin) {
        super(plugin);
        this.commandConfig = new CommandConfig(plugin);
    }
    
    public final CommandConfig getCommandConfig() {
        return this.commandConfig;
    }
    
    public final Collection<String> getCommandIDs() {
        return this.getCommandConfig().getCommandIDs();
    }
    
    public final Collection<CommandBuild> getCommandBuilds() {
        return this.getCommandConfig().getCommandBuilds();
    }
    
    public final CommandBuild getCommand(final String id) {
        return this.getCommandConfig().getCommand(id);
    }
    
    public final boolean isCommandExists(final String id) {
        return this.getCommand(id) != null;
    }
    
    public final boolean checkCommand(final String arg, final String id) {
        final CommandBuild commandBuild = this.getCommand(id);
        if (commandBuild != null) {
            for (final String aliases : commandBuild.getAliases()) {
                if (aliases.equalsIgnoreCase(arg)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public final boolean checkPermission(final CommandSender sender, final String id) {
        final CommandBuild commandBuild = this.getCommand(id);
        if (commandBuild != null) {
            final String permission = commandBuild.getPermission();
            return permission == null || sender.hasPermission(permission);
        }
        return true;
    }
    
    public final List<String> getAliases(final String id) {
        final CommandBuild commandBuild = this.getCommand(id);
        return (commandBuild != null) ? commandBuild.getAliases() : new ArrayList<String>();
    }
    
    public final String getPermission(final String id) {
        final CommandBuild commandBuild = this.getCommand(id);
        return (commandBuild != null) ? commandBuild.getPermission() : null;
    }
}
