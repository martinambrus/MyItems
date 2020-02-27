// 
// Decompiled by Procyon v0.5.36
// 

package api.praya.myitems.manager.game;

import com.praya.myitems.manager.game.GameManager;
import com.praya.myitems.manager.game.PowerSpecialManager;
import api.praya.myitems.builder.power.PowerClickEnum;
import api.praya.myitems.builder.power.PowerSpecialProperties;
import api.praya.myitems.builder.power.PowerSpecialEnum;
import java.util.Collection;
import com.praya.myitems.MyItems;
import com.praya.myitems.builder.handler.HandlerManager;

public class PowerSpecialManagerAPI extends HandlerManager
{
    protected PowerSpecialManagerAPI(final MyItems plugin) {
        super(plugin);
    }
    
    public final Collection<PowerSpecialEnum> getPowerSpecialIDs() {
        return this.getPowerSpecialManager().getPowerSpecialIDs();
    }
    
    public final Collection<PowerSpecialProperties> getPowerSpecialPropertyBuilds() {
        return this.getPowerSpecialManager().getPowerSpecialPropertyBuilds();
    }
    
    public final PowerSpecialProperties getPowerSpecialProperties(final PowerSpecialEnum id) {
        return this.getPowerSpecialManager().getPowerSpecialProperties(id);
    }
    
    public final boolean isPowerSpecialExists(final PowerSpecialEnum id) {
        return this.getPowerSpecialManager().isPowerSpecialExists(id);
    }
    
    public final String getTextPowerSpecial(final PowerClickEnum click, final PowerSpecialEnum special, final double cooldown) {
        return this.getPowerSpecialManager().getTextPowerSpecial(click, special, cooldown);
    }
    
    private final PowerSpecialManager getPowerSpecialManager() {
        final GameManager gameManager = this.plugin.getGameManager();
        final PowerSpecialManager powerSpecialManager = gameManager.getPowerManager().getPowerSpecialManager();
        return powerSpecialManager;
    }
}
