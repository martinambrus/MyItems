// 
// Decompiled by Procyon v0.5.36
// 

package com.praya.myitems.manager.player;

import org.bukkit.OfflinePlayer;
import com.praya.myitems.MyItems;
import api.praya.myitems.builder.player.PlayerPowerCooldown;
import java.util.UUID;
import java.util.HashMap;
import com.praya.myitems.builder.handler.HandlerManager;

public class PlayerPowerManager extends HandlerManager
{
    private final HashMap<UUID, PlayerPowerCooldown> playerPowerCooldown;
    
    protected PlayerPowerManager(final MyItems plugin) {
        super(plugin);
        this.playerPowerCooldown = new HashMap<UUID, PlayerPowerCooldown>();
    }
    
    public final PlayerPowerCooldown getPlayerPowerCooldown(final OfflinePlayer player) {
        final UUID uuid = player.getUniqueId();
        if (!this.playerPowerCooldown.containsKey(uuid)) {
            this.playerPowerCooldown.put(uuid, new PlayerPowerCooldown());
        }
        return this.playerPowerCooldown.get(uuid);
    }
    
    public final void removePlayerPowerCooldown(final OfflinePlayer player) {
        final UUID uuid = player.getUniqueId();
        this.playerPowerCooldown.remove(uuid);
    }
}
