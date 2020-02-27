// 
// Decompiled by Procyon v0.5.36
// 

package api.praya.myitems.builder.lorestats;

import java.util.Iterator;
import java.util.Arrays;
import java.util.List;

public enum LoreStatsOption
{
    MIN("Min", Arrays.asList("Min", "Minimal", "Low", "Lowest")), 
    MAX("Max", Arrays.asList("Max", "Maximal", "High", "Highest")), 
    CURRENT("Current", Arrays.asList("Current", "Now"));
    
    private final String text;
    private final List<String> aliases;
    
    private LoreStatsOption(final String text, final List<String> aliases) {
        this.text = text;
        this.aliases = aliases;
    }
    
    public final String getText() {
        return this.text;
    }
    
    public final List<String> getAliases() {
        return this.aliases;
    }
    
    public static final LoreStatsOption get(final String option) {
        LoreStatsOption[] values;
        for (int length = (values = values()).length, i = 0; i < length; ++i) {
            final LoreStatsOption key = values[i];
            for (final String aliase : key.getAliases()) {
                if (aliase.equalsIgnoreCase(option)) {
                    return key;
                }
            }
        }
        return null;
    }
}
