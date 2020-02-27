// 
// Decompiled by Procyon v0.5.36
// 

package com.praya.myitems.manager.plugin;

import core.praya.agarthalib.builder.message.MessageBuild;
import java.util.ArrayList;
import com.praya.myitems.config.plugin.MainConfig;
import org.bukkit.command.CommandSender;
import core.praya.agarthalib.bridge.unity.Bridge;
import org.bukkit.entity.Player;
import java.util.List;
import org.bukkit.entity.LivingEntity;
import core.praya.agarthalib.builder.main.LanguageBuild;
import java.util.Collection;
import com.praya.myitems.MyItems;
import com.praya.myitems.config.plugin.LanguageConfig;
import com.praya.myitems.builder.handler.HandlerManager;

public class LanguageManager extends HandlerManager
{
    private final LanguageConfig langConfig;
    
    protected LanguageManager(final MyItems plugin) {
        super(plugin);
        this.langConfig = new LanguageConfig(plugin);
    }
    
    public final LanguageConfig getLangConfig() {
        return this.langConfig;
    }
    
    public final Collection<String> getLanguageIDs() {
        return this.getLangConfig().getLanguageIDs();
    }
    
    public final Collection<LanguageBuild> getLanguageBuilds() {
        return this.getLangConfig().getLanguageBuilds();
    }
    
    public final LanguageBuild getLanguageBuild(final String id) {
        return this.getLangConfig().getLanguageBuild(id);
    }
    
    public final LanguageBuild getLanguage(final String id) {
        return this.getLangConfig().getLanguage(id);
    }
    
    public final boolean isLanguageExists(final String id) {
        return this.getLanguageBuild(id) != null;
    }
    
    public final List<String> getListText(final LivingEntity entity, final String key) {
        if (entity != null && entity instanceof Player) {
            final Player player = (Player)entity;
            final String locale = Bridge.getBridgePlayer().getLocale(player);
            return this.getListText(locale, key);
        }
        return this.getListText(key);
    }
    
    public final List<String> getListText(final CommandSender sender, final String key) {
        if (sender != null && sender instanceof Player) {
            final Player player = (Player)sender;
            final String locale = Bridge.getBridgePlayer().getLocale(player);
            return this.getListText(locale, key);
        }
        return this.getListText(key);
    }
    
    public final List<String> getListText(final String key) {
        final MainConfig mainConfig = MainConfig.getInstance();
        final String locale = mainConfig.getGeneralLocale();
        return this.getListText(locale, key);
    }
    
    public final List<String> getListText(final String id, final String key) {
        final MessageBuild message = this.getMessage(id, key);
        return (message != null) ? message.getListText() : new ArrayList<String>();
    }
    
    public final String getText(final LivingEntity entity, final String key) {
        if (entity != null && entity instanceof Player) {
            final Player player = (Player)entity;
            final String locale = Bridge.getBridgePlayer().getLocale(player);
            return this.getText(locale, key);
        }
        return this.getText(key);
    }
    
    public final String getText(final CommandSender sender, final String key) {
        if (sender != null && sender instanceof Player) {
            final Player player = (Player)sender;
            final String locale = Bridge.getBridgePlayer().getLocale(player);
            return this.getText(locale, key);
        }
        return this.getText(key);
    }
    
    public final String getText(final String key) {
        final MainConfig mainConfig = MainConfig.getInstance();
        final String locale = mainConfig.getGeneralLocale();
        return this.getText(locale, key);
    }
    
    public final String getText(final String id, final String key) {
        final MessageBuild message = this.getMessage(id, key);
        return (message != null) ? message.getText() : "";
    }
    
    public final MessageBuild getMessage(final LivingEntity entity, final String key) {
        if (entity != null && entity instanceof Player) {
            final Player player = (Player)entity;
            final String locale = Bridge.getBridgePlayer().getLocale(player);
            return this.getMessage(locale, key);
        }
        return this.getMessage(key);
    }
    
    public final MessageBuild getMessage(final CommandSender sender, final String key) {
        if (sender != null && sender instanceof Player) {
            final Player player = (Player)sender;
            final String locale = Bridge.getBridgePlayer().getLocale(player);
            return this.getMessage(locale, key);
        }
        return this.getMessage(key);
    }
    
    public final MessageBuild getMessage(final String key) {
        final MainConfig mainConfig = MainConfig.getInstance();
        final String locale = mainConfig.getGeneralLocale();
        return this.getMessage(locale, key);
    }
    
    public final MessageBuild getMessage(final String id, final String key) {
        return this.getLangConfig().getMessage(id, key);
    }
}
