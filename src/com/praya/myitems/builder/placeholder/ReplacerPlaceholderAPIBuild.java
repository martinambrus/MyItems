// 
// Decompiled by Procyon v0.5.36
// 

package com.praya.myitems.builder.placeholder;

import com.praya.myitems.manager.plugin.PlaceholderManager;
import org.bukkit.entity.Player;
import me.clip.placeholderapi.PlaceholderAPI;
import com.praya.myitems.MyItems;
import me.clip.placeholderapi.PlaceholderHook;

public class ReplacerPlaceholderAPIBuild extends PlaceholderHook
{
    private final String placeholder;
    private final MyItems plugin;
    
    public ReplacerPlaceholderAPIBuild(final MyItems plugin, final String placeholder) {
        this.plugin = plugin;
        this.placeholder = placeholder;
    }
    
    public final String getPlaceholder() {
        return this.placeholder;
    }
    
    public final boolean hook() {
        return PlaceholderAPI.registerPlaceholderHook(this.placeholder, (PlaceholderHook)this);
    }
    
    public String onPlaceholderRequest(final Player player, final String identifier) {
        final PlaceholderManager placeholderManager = this.plugin.getPluginManager().getPlaceholderManager();
        return placeholderManager.getReplacement(player, identifier);
    }
}
