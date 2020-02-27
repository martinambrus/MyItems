// 
// Decompiled by Procyon v0.5.36
// 

package com.praya.myitems.manager.player;

import com.praya.myitems.MyItems;
import com.praya.myitems.builder.handler.HandlerManager;

public class PlayerManager extends HandlerManager
{
    private final PlayerItemStatsManager playerItemStatsManager;
    private final PlayerPassiveEffectManager playerPassiveEffectManager;
    private final PlayerPowerManager playerPowerManager;
    
    public PlayerManager(final MyItems plugin) {
        super(plugin);
        this.playerItemStatsManager = new PlayerItemStatsManager(plugin);
        this.playerPassiveEffectManager = new PlayerPassiveEffectManager(plugin);
        this.playerPowerManager = new PlayerPowerManager(plugin);
    }
    
    public final PlayerItemStatsManager getPlayerItemStatsManager() {
        return this.playerItemStatsManager;
    }
    
    public final PlayerPassiveEffectManager getPlayerPassiveEffectManager() {
        return this.playerPassiveEffectManager;
    }
    
    public final PlayerPowerManager getPlayerPowerManager() {
        return this.playerPowerManager;
    }
}
