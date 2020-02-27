// 
// Decompiled by Procyon v0.5.36
// 

package com.praya.myitems.config.plugin;

import core.praya.agarthalib.builder.plugin.PluginPropertiesResourceBuild;
import com.praya.myitems.manager.plugin.PluginPropertiesManager;
import core.praya.agarthalib.builder.message.MessageBuild;
import com.praya.myitems.manager.plugin.LanguageManager;
import com.praya.myitems.manager.plugin.DependencyManager;
import com.praya.myitems.manager.plugin.PluginManager;
import org.bukkit.plugin.Plugin;
import org.bukkit.Bukkit;
import java.util.Iterator;
import java.util.Collection;
import com.praya.myitems.MyItems;
import core.praya.agarthalib.enums.main.Dependency;
import java.util.HashMap;
import com.praya.myitems.builder.handler.HandlerConfig;

public class HookConfig extends HandlerConfig
{
    private final HashMap<String, Dependency> mapHook;
    
    public HookConfig(final MyItems plugin) {
        super(plugin);
        this.mapHook = new HashMap<String, Dependency>();
    }
    
    public final Collection<String> getHookKeys() {
        return this.mapHook.keySet();
    }
    
    public final boolean isHook(final String plugin) {
        return this.getTypeDependency(plugin) != null;
    }
    
    public final Dependency getTypeDependency(final String plugin) {
        for (final String key : this.getHookKeys()) {
            if (key.equalsIgnoreCase(plugin)) {
                return this.mapHook.get(key);
            }
        }
        return null;
    }
    
    public final void removeHook(final String plugin) {
        this.mapHook.remove(plugin);
    }
    
    public final void setup() {
        this.reset();
        this.loadConfig();
    }
    
    private final void reset() {
        this.mapHook.clear();
    }
    
    private final void loadConfig() {
        final PluginManager pluginManager = this.plugin.getPluginManager();
        final DependencyManager dependencyManager = pluginManager.getDependencyManager();
        final LanguageManager lang = pluginManager.getLanguageManager();
        final MainConfig mainConfig = MainConfig.getInstance();
        Dependency[] values;
        for (int length = (values = Dependency.values()).length, i = 0; i < length; ++i) {
            final Dependency type = values[i];
            for (final String dependency : dependencyManager.getDependency(type)) {
                if (Bukkit.getPluginManager().isPluginEnabled(dependency)) {
                    this.mapHook.put(dependency, type);
                }
                else {
                    if (dependencyManager.getTypeDependency(dependency).equals((Object)Dependency.HARD_DEPENDENCY)) {
                        final MessageBuild message = lang.getMessage("Plugin_Lack_Dependency");
                        message.sendMessage("dependency", dependency);
                        this.plugin.getPluginLoader().disablePlugin((Plugin)this.plugin);
                        return;
                    }
                    continue;
                }
            }
        }
        if (mainConfig.isHookMessage()) {
            this.sendMessage();
        }
    }
    
    private final void sendMessage() {
        final PluginManager pluginManager = this.plugin.getPluginManager();
        final DependencyManager dependencyManager = pluginManager.getDependencyManager();
        if (dependencyManager.hasAnyDependency()) {
            final PluginPropertiesManager pluginPropertiesManager = pluginManager.getPluginPropertiesManager();
            final PluginPropertiesResourceBuild pluginPropertiesResource = pluginPropertiesManager.getPluginPropertiesResource();
            final LanguageManager lang = pluginManager.getLanguageManager();
            final HashMap<String, String> map = new HashMap<String, String>();
            final MessageBuild messageHeader = lang.getMessage("Hook_Header");
            final MessageBuild messageFooter = lang.getMessage("Hook_Footer");
            map.put("plugin", pluginPropertiesResource.getName());
            messageHeader.sendMessage((HashMap)map);
            Dependency[] values;
            for (int length = (values = Dependency.values()).length, i = 0; i < length; ++i) {
                final Dependency type = values[i];
                if (dependencyManager.hasDependency(type)) {
                    final MessageBuild messageListHeader = lang.getMessage(type.equals((Object)Dependency.HARD_DEPENDENCY) ? "Hook_Hard_Dependency_Header" : "Hook_Soft_Dependency_Header");
                    messageListHeader.sendMessage();
                    for (final String hook : dependencyManager.getDependency(type)) {
                        final String statusYes = lang.getText("Hook_Status_Hooked");
                        final String statusNo = lang.getText("Hook_Status_Not_Hook");
                        final MessageBuild messageList = lang.getMessage(type.equals((Object)Dependency.HARD_DEPENDENCY) ? "Hook_Hard_Dependency_Header" : "Hook_Soft_Dependency_List");
                        final HashMap<String, String> subMap = new HashMap<String, String>();
                        subMap.put("hook", hook);
                        subMap.put("status", this.isHook(hook) ? statusYes : statusNo);
                        messageList.sendMessage((HashMap)subMap);
                    }
                }
            }
            messageFooter.sendMessage((HashMap)map);
        }
    }
}
