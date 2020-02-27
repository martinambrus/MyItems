// 
// Decompiled by Procyon v0.5.36
// 

package com.praya.myitems.builder.placeholder;

import org.bukkit.plugin.Plugin;
import be.maximvdw.placeholderapi.PlaceholderAPI;
import be.maximvdw.placeholderapi.PlaceholderReplaceEvent;
import com.praya.myitems.manager.plugin.PlaceholderManager;
import be.maximvdw.placeholderapi.PlaceholderReplacer;
import com.praya.myitems.MyItems;

public class ReplacerMVDWPlaceholderAPIBuild
{
    private final MyItems plugin;
    private final String placeholder;
    
    public ReplacerMVDWPlaceholderAPIBuild(final MyItems plugin, final String placeholder) {
        this.plugin = plugin;
        this.placeholder = placeholder;
    }
    
    public final String getPlaceholder() {
        return this.placeholder;
    }
    
    public final void register() {
        final PlaceholderManager placeholderManager = this.plugin.getPluginManager().getPlaceholderManager();
        final String identifier = String.valueOf(this.getPlaceholder()) + "_*";
        PlaceholderAPI.registerPlaceholder((Plugin)this.plugin, identifier, (PlaceholderReplacer)new PlaceholderReplacer() {
            public String onPlaceholderReplace(final PlaceholderReplaceEvent event) {
                return placeholderManager.getReplacement(event.getPlayer(), event.getPlaceholder().split(String.valueOf(ReplacerMVDWPlaceholderAPIBuild.this.getPlaceholder()) + "_")[1]);
            }
        });
    }
}
