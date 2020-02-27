// 
// Decompiled by Procyon v0.5.36
// 

package api.praya.myitems.builder.ability;

import org.bukkit.entity.Entity;
import java.util.List;
import org.bukkit.plugin.Plugin;

public abstract class Ability
{
    private final Plugin plugin;
    private final String id;
    
    protected Ability(final Plugin plugin, final String id) {
        this.plugin = plugin;
        this.id = id;
    }
    
    public abstract String getKeyLore();
    
    public abstract List<String> getDescription();
    
    public abstract int getMaxGrade();
    
    public abstract void cast(final Entity p0, final Entity p1, final int p2, final double p3);
    
    public final Plugin getPlugin() {
        return this.plugin;
    }
    
    public final String getID() {
        return this.id;
    }
}
