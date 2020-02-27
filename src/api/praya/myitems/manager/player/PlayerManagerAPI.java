// 
// Decompiled by Procyon v0.5.36
// 

package api.praya.myitems.manager.player;

import com.praya.myitems.MyItems;
import com.praya.myitems.builder.handler.HandlerManager;

public class PlayerManagerAPI extends HandlerManager
{
    private final PlayerItemStatsManagerAPI playerItemStatsManagerAPI;
    
    public PlayerManagerAPI(final MyItems plugin) {
        super(plugin);
        this.playerItemStatsManagerAPI = new PlayerItemStatsManagerAPI(plugin);
    }
    
    public final PlayerItemStatsManagerAPI getPlayerItemStatsManagerAPI() {
        return this.playerItemStatsManagerAPI;
    }
}
