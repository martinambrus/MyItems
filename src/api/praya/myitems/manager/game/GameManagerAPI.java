// 
// Decompiled by Procyon v0.5.36
// 

package api.praya.myitems.manager.game;

import com.praya.myitems.MyItems;
import com.praya.myitems.builder.handler.HandlerManager;

public class GameManagerAPI extends HandlerManager
{
    private final ElementManagerAPI elementManagerAPI;
    private final ItemGeneratorManagerAPI itemGeneratorManagerAPI;
    private final ItemManagerAPI itemManagerAPI;
    private final ItemTierManagerAPI itemTierManagerAPI;
    private final ItemTypeManagerAPI itemTypeManagerAPI;
    private final LoreStatsManagerAPI loreStatsManagerAPI;
    private final PassiveEffectManagerAPI passiveEffectManagerAPI;
    private final PowerManagerAPI powerManagerAPI;
    private final SocketManagerAPI socketManagerAPI;
    
    public GameManagerAPI(final MyItems plugin) {
        super(plugin);
        this.elementManagerAPI = new ElementManagerAPI(plugin);
        this.itemGeneratorManagerAPI = new ItemGeneratorManagerAPI(plugin);
        this.itemManagerAPI = new ItemManagerAPI(plugin);
        this.itemTierManagerAPI = new ItemTierManagerAPI(plugin);
        this.itemTypeManagerAPI = new ItemTypeManagerAPI(plugin);
        this.loreStatsManagerAPI = new LoreStatsManagerAPI(plugin);
        this.passiveEffectManagerAPI = new PassiveEffectManagerAPI(plugin);
        this.powerManagerAPI = new PowerManagerAPI(plugin);
        this.socketManagerAPI = new SocketManagerAPI(plugin);
    }
    
    public final ElementManagerAPI getElementManagerAPI() {
        return this.elementManagerAPI;
    }
    
    public final ItemGeneratorManagerAPI getItemGeneratorManagerAPI() {
        return this.itemGeneratorManagerAPI;
    }
    
    public final ItemManagerAPI getItemManagerAPI() {
        return this.itemManagerAPI;
    }
    
    public final ItemTierManagerAPI getItemTierManagerAPI() {
        return this.itemTierManagerAPI;
    }
    
    public final ItemTypeManagerAPI getItemTypeManagerAPI() {
        return this.itemTypeManagerAPI;
    }
    
    public final LoreStatsManagerAPI getLoreStatsManagerAPI() {
        return this.loreStatsManagerAPI;
    }
    
    public final PassiveEffectManagerAPI getPassiveEffectManagerAPI() {
        return this.passiveEffectManagerAPI;
    }
    
    public final PowerManagerAPI getPowerManagerAPI() {
        return this.powerManagerAPI;
    }
    
    public final SocketManagerAPI getSocketManagerAPI() {
        return this.socketManagerAPI;
    }
}
