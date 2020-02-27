// 
// Decompiled by Procyon v0.5.36
// 

package com.praya.myitems.manager.plugin;

import core.praya.agarthalib.enums.main.Dependency;
import com.praya.myitems.MyItems;
import com.praya.myitems.config.plugin.HookConfig;
import com.praya.myitems.builder.handler.HandlerManager;

public class HookManager extends HandlerManager
{
    private final HookConfig hookConfig;
    
    protected HookManager(final MyItems plugin) {
        super(plugin);
        this.hookConfig = new HookConfig(plugin);
    }
    
    public final HookConfig getHookConfig() {
        return this.hookConfig;
    }
    
    public final boolean isHookPlaceholderAPI() {
        return this.isHook("PlaceholderAPI");
    }
    
    public final boolean isHookMVdWPlaceholderAPI() {
        return this.isHook("MVdWPlaceholderAPI");
    }
    
    public final boolean isHookVault() {
        return this.isHook("Vault");
    }
    
    public final boolean isHookHolographicDisplays() {
        return this.isHook("HolographicDisplays");
    }
    
    public final boolean isHookCitizens() {
        return this.isHook("Citizens");
    }
    
    public final boolean isHookMcmmo() {
        return this.isHook("mcMMO");
    }
    
    public final boolean isHookJobs() {
        return this.isHook("JobsReborn");
    }
    
    public final boolean isHookSkillAPI() {
        return this.isHook("SkillAPI");
    }
    
    public final boolean isHookMythicMobs() {
        return this.isHook("MythicMobs");
    }
    
    public final boolean isHook(final String plugin) {
        return this.getHookConfig().isHook(plugin);
    }
    
    public final Dependency getTypeDependency(final String plugin) {
        return this.getHookConfig().getTypeDependency(plugin);
    }
    
    public final void removeHook(final String plugin) {
        this.getHookConfig().removeHook(plugin);
    }
}
