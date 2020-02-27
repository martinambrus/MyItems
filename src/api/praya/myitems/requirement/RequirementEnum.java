// 
// Decompiled by Procyon v0.5.36
// 

package api.praya.myitems.requirement;

import java.util.Iterator;
import java.util.Arrays;
import java.util.List;

public enum RequirementEnum
{
    REQUIREMENT_SOUL_UNBOUND("Unbound", Arrays.asList("Unbound")), 
    REQUIREMENT_SOUL_BOUND("Bound", Arrays.asList("Bound", "Soulbound")), 
    REQUIREMENT_PERMISSION("Permission", Arrays.asList("Permission")), 
    REQUIREMENT_LEVEL("Level", Arrays.asList("Level")), 
    REQUIREMENT_CLASS("Class", Arrays.asList("Class", "Hero"));
    
    private final String name;
    private final List<String> aliases;
    
    private RequirementEnum(final String name, final List<String> aliases) {
        this.name = name;
        this.aliases = aliases;
    }
    
    public final String getName() {
        return this.name;
    }
    
    public final List<String> getAliases() {
        return this.aliases;
    }
    
    public static final RequirementEnum getRequirement(final String requirement) {
        RequirementEnum[] values;
        for (int length = (values = values()).length, i = 0; i < length; ++i) {
            final RequirementEnum key = values[i];
            for (final String aliases : key.getAliases()) {
                if (aliases.equalsIgnoreCase(requirement)) {
                    return key;
                }
            }
        }
        return null;
    }
}
