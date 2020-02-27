// 
// Decompiled by Procyon v0.5.36
// 

package api.praya.myitems.manager.game;

import com.praya.myitems.manager.game.GameManager;
import com.praya.myitems.manager.game.PowerShootManager;
import core.praya.agarthalib.enums.branch.ProjectileEnum;
import api.praya.myitems.builder.power.PowerClickEnum;
import com.praya.myitems.MyItems;
import com.praya.myitems.builder.handler.HandlerManager;

public class PowerShootManagerAPI extends HandlerManager
{
    protected PowerShootManagerAPI(final MyItems plugin) {
        super(plugin);
    }
    
    public final String getTextPowerShoot(final PowerClickEnum click, final ProjectileEnum projectile, final double cooldown) {
        return this.getPowerShootManager().getTextPowerShoot(click, projectile, cooldown);
    }
    
    private final PowerShootManager getPowerShootManager() {
        final GameManager gameManager = this.plugin.getGameManager();
        final PowerShootManager powerShootManager = gameManager.getPowerManager().getPowerShootManager();
        return powerShootManager;
    }
}
