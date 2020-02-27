// 
// Decompiled by Procyon v0.5.36
// 

package api.praya.myitems.builder.power;

import java.util.List;

public class PowerCommandProperties
{
    private final String keyLore;
    private final boolean consume;
    private final List<String> commandOP;
    private final List<String> commandConsole;
    
    public PowerCommandProperties(final String keyLore, final boolean consume, final List<String> commandOP, final List<String> commandConsole) {
        this.keyLore = keyLore;
        this.consume = consume;
        this.commandOP = commandOP;
        this.commandConsole = commandConsole;
    }
    
    public final String getKeyLore() {
        return this.keyLore;
    }
    
    public final boolean isConsume() {
        return this.consume;
    }
    
    public final List<String> getCommandOP() {
        return this.commandOP;
    }
    
    public final List<String> getCommandConsole() {
        return this.commandConsole;
    }
    
    @Deprecated
    public final List<String> getCommands() {
        return this.commandOP;
    }
    
    @Deprecated
    public final String getLore() {
        return this.keyLore;
    }
}
