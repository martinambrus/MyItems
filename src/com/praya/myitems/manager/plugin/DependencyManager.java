// 
// Decompiled by Procyon v0.5.36
// 

package com.praya.myitems.manager.plugin;

import java.util.Iterator;
import java.util.Collection;
import core.praya.agarthalib.enums.main.Dependency;
import com.praya.myitems.MyItems;
import com.praya.myitems.config.plugin.DependencyConfig;
import com.praya.myitems.builder.handler.HandlerManager;

public class DependencyManager extends HandlerManager
{
    private final DependencyConfig dependencyConfig;
    
    protected DependencyManager(final MyItems plugin) {
        super(plugin);
        this.dependencyConfig = new DependencyConfig(plugin);
    }
    
    public final DependencyConfig getDependencyConfig() {
        return this.dependencyConfig;
    }
    
    public final Collection<String> getDependency(final Dependency type) {
        return this.getDependencyConfig().getDependency(type);
    }
    
    public final boolean hasDependency(final Dependency type) {
        return this.getDependencyConfig().hasDependency(type);
    }
    
    public final boolean hasAnyDependency() {
        return this.getDependencyConfig().hasAnyDependency();
    }
    
    public final Dependency getTypeDependency(final String dependency) {
        Dependency[] values;
        for (int length = (values = Dependency.values()).length, i = 0; i < length; ++i) {
            final Dependency type = values[i];
            for (final String depends : this.getDependency(type)) {
                if (depends.equalsIgnoreCase(dependency)) {
                    return type;
                }
            }
        }
        return null;
    }
    
    public final boolean containsDependency(final String dependency) {
        return this.getTypeDependency(dependency) != null;
    }
}
