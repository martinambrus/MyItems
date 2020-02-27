// 
// Decompiled by Procyon v0.5.36
// 

package api.praya.myitems.manager.game;

import com.praya.myitems.MyItems;
import com.praya.myitems.builder.handler.HandlerManager;

public class PowerManagerAPI extends HandlerManager
{
    private final PowerCommandManagerAPI powerCommandManagerAPI;
    private final PowerShootManagerAPI powerShootManagerAPI;
    private final PowerSpecialManagerAPI powerSpecialManagerAPI;
    
    protected PowerManagerAPI(final MyItems plugin) {
        super(plugin);
        this.powerCommandManagerAPI = new PowerCommandManagerAPI(plugin);
        this.powerShootManagerAPI = new PowerShootManagerAPI(plugin);
        this.powerSpecialManagerAPI = new PowerSpecialManagerAPI(plugin);
    }
    
    public final PowerCommandManagerAPI getPowerCommandManagerAPI() {
        return this.powerCommandManagerAPI;
    }
    
    public final PowerShootManagerAPI getPowerShootManagerAPI() {
        return this.powerShootManagerAPI;
    }
    
    public final PowerSpecialManagerAPI getPowerSpecialManagerAPI() {
        return this.powerSpecialManagerAPI;
    }
}
