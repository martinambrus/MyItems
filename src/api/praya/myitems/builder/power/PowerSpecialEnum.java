// 
// Decompiled by Procyon v0.5.36
// 

package api.praya.myitems.builder.power;

import java.util.Iterator;
import com.praya.myitems.manager.game.PowerSpecialManager;
import com.praya.myitems.manager.game.PowerManager;
import com.praya.myitems.manager.game.GameManager;
import org.bukkit.plugin.java.JavaPlugin;
import com.praya.myitems.MyItems;
import com.praya.myitems.config.plugin.MainConfig;
import java.util.Arrays;
import java.util.List;

public enum PowerSpecialEnum
{
    BLINK(Arrays.asList("Blink")), 
    FISSURE(Arrays.asList("Fissure")), 
    ICE_SPIKES(Arrays.asList("Ice Spikes", "IceSpikes", "Ice_Spikes")), 
    AMATERASU(Arrays.asList("Amaterasu", "DarkFlame", "Dark_Flame")), 
    NERO_BEAM(Arrays.asList("Nero Beam", "NeroBeam", "Nero_Beam"));
    
    private final List<String> aliases;
    
    private PowerSpecialEnum(final List<String> aliases) {
        this.aliases = aliases;
    }
    
    public final List<String> getAliases() {
        return this.aliases;
    }
    
    public final String getText() {
        final MainConfig mainConfig = MainConfig.getInstance();
        switch (this) {
            case BLINK: {
                return mainConfig.getPowerSpecialIdentifierBlink();
            }
            case FISSURE: {
                return mainConfig.getPowerSpecialIdentifierFissure();
            }
            case ICE_SPIKES: {
                return mainConfig.getPowerSpecialIdentifierIceSpikes();
            }
            case AMATERASU: {
                return mainConfig.getPowerSpecialIdentifierAmaterasu();
            }
            case NERO_BEAM: {
                return mainConfig.getPowerSpecialIdentifierNeroBeam();
            }
            default: {
                return null;
            }
        }
    }
    
    public final PowerSpecialProperties getProperties() {
        final MyItems plugin = (MyItems)JavaPlugin.getProvidingPlugin((Class)MyItems.class);
        final GameManager gameManager = plugin.getGameManager();
        final PowerManager powerManager = gameManager.getPowerManager();
        final PowerSpecialManager powerSpecialManager = powerManager.getPowerSpecialManager();
        for (final PowerSpecialEnum key : powerSpecialManager.getPowerSpecialIDs()) {
            if (key.equals(this)) {
                return powerSpecialManager.getPowerSpecialProperties(key);
            }
        }
        return null;
    }
    
    public final int getDuration() {
        final PowerSpecialProperties build = this.getProperties();
        return (build != null) ? build.getDurationEffect() : 1;
    }
    
    public final double getBaseAdditionalDamage() {
        final PowerSpecialProperties build = this.getProperties();
        return (build != null) ? build.getBaseAdditionalDamage() : 0.0;
    }
    
    public final double getBasePercentDamage() {
        final PowerSpecialProperties build = this.getProperties();
        return (build != null) ? build.getBasePercentDamage() : 100.0;
    }
    
    public static final PowerSpecialEnum get(final String special) {
        final String upperCase;
        switch (upperCase = special.toUpperCase()) {
            case "ICE_SPIKES": {
                return PowerSpecialEnum.ICE_SPIKES;
            }
            case "NERO_BEAM": {
                return PowerSpecialEnum.NERO_BEAM;
            }
            case "AMATERASU": {
                return PowerSpecialEnum.AMATERASU;
            }
            case "ICESPIKE": {
                return PowerSpecialEnum.ICE_SPIKES;
            }
            case "FISSURE": {
                return PowerSpecialEnum.FISSURE;
            }
            case "BLINK": {
                return PowerSpecialEnum.BLINK;
            }
            case "ICESPIKES": {
                return PowerSpecialEnum.ICE_SPIKES;
            }
            case "NEROBEAM": {
                return PowerSpecialEnum.NERO_BEAM;
            }
            default:
                break;
        }
        return null;
    }
    
    public static final boolean isSpecialExists(final String special) {
        return getSpecial(special) != null;
    }
    
    public static final PowerSpecialEnum getSpecial(final String special) {
        PowerSpecialEnum[] values;
        for (int length = (values = values()).length, i = 0; i < length; ++i) {
            final PowerSpecialEnum key = values[i];
            for (final String aliases : key.getAliases()) {
                if (aliases.equalsIgnoreCase(special)) {
                    return key;
                }
            }
        }
        return null;
    }
    
    public static final PowerSpecialEnum getSpecialByLore(final String lore) {
        PowerSpecialEnum[] values;
        for (int length = (values = values()).length, i = 0; i < length; ++i) {
            final PowerSpecialEnum special = values[i];
            if (special.getText().equalsIgnoreCase(lore)) {
                return special;
            }
        }
        return null;
    }
}
