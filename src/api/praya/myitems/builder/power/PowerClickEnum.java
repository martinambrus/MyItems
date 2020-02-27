// 
// Decompiled by Procyon v0.5.36
// 

package api.praya.myitems.builder.power;

import java.util.Iterator;
import com.praya.myitems.config.plugin.MainConfig;
import java.util.Arrays;
import java.util.List;

public enum PowerClickEnum
{
    LEFT(Arrays.asList("Left", "LeftClick", "Left Click", "Left_Click")), 
    SHIFT_LEFT(Arrays.asList("Shift Left", "ShiftLeft", "Shift_Left", "Shift Left Click")), 
    RIGHT(Arrays.asList("Right", "RightClick,", "Right Click", "Right_Click")), 
    SHIFT_RIGHT(Arrays.asList("Shift Right", "ShiftRight", "Shift_Right", "Shift Right Click"));
    
    private final List<String> aliases;
    
    private PowerClickEnum(final List<String> aliases) {
        this.aliases = aliases;
    }
    
    public final List<String> getAliases() {
        return this.aliases;
    }
    
    public final String getText() {
        final MainConfig mainConfig = MainConfig.getInstance();
        switch (this) {
            case LEFT: {
                return mainConfig.getPowerClickLeft();
            }
            case RIGHT: {
                return mainConfig.getPowerClickRight();
            }
            case SHIFT_LEFT: {
                return mainConfig.getPowerClickShiftLeft();
            }
            case SHIFT_RIGHT: {
                return mainConfig.getPowerClickShiftRight();
            }
            default: {
                return null;
            }
        }
    }
    
    public static final PowerClickEnum get(final String click) {
        PowerClickEnum[] values;
        for (int length = (values = values()).length, i = 0; i < length; ++i) {
            final PowerClickEnum key = values[i];
            for (final String aliases : key.getAliases()) {
                if (aliases.equalsIgnoreCase(click)) {
                    return key;
                }
            }
        }
        return null;
    }
    
    public static final String getText(final PowerClickEnum click) {
        return click.getText();
    }
}
