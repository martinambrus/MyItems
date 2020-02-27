// 
// Decompiled by Procyon v0.5.36
// 

package api.praya.myitems.manager.game;

import com.praya.myitems.manager.game.GameManager;
import com.praya.myitems.manager.game.PowerCommandManager;
import api.praya.myitems.builder.power.PowerClickEnum;
import java.util.List;
import api.praya.myitems.builder.power.PowerCommandProperties;
import java.util.Collection;
import com.praya.myitems.MyItems;
import com.praya.myitems.builder.handler.HandlerManager;

public class PowerCommandManagerAPI extends HandlerManager
{
    protected PowerCommandManagerAPI(final MyItems plugin) {
        super(plugin);
    }
    
    public final Collection<String> getPowerCommandIDs() {
        return this.getPowerCommandManager().getPowerCommandIDs();
    }
    
    public final Collection<PowerCommandProperties> getPowerCommandPropertyBuilds() {
        return this.getPowerCommandManager().getPowerCommandPropertyBuilds();
    }
    
    public final PowerCommandProperties getPowerCommandProperties(final String id) {
        return this.getPowerCommandManager().getPowerCommandProperties(id);
    }
    
    public final boolean isPowerCommandExists(final String id) {
        return this.getPowerCommandManager().isPowerCommandExists(id);
    }
    
    @Deprecated
    public final List<String> getCommands(final String powerCommand) {
        return this.getPowerCommandManager().getCommands(powerCommand);
    }
    
    public final String getTextPowerCommand(final PowerClickEnum click, final String keyCommand, final double cooldown) {
        return this.getPowerCommandManager().getTextPowerCommand(click, keyCommand, cooldown);
    }
    
    private final PowerCommandManager getPowerCommandManager() {
        final GameManager gameManager = this.plugin.getGameManager();
        final PowerCommandManager powerCommandManager = gameManager.getPowerManager().getPowerCommandManager();
        return powerCommandManager;
    }
}
