// 
// Decompiled by Procyon v0.5.36
// 

package com.praya.myitems.listener.main;

import org.bukkit.event.EventHandler;
import org.bukkit.entity.Player;
import com.praya.myitems.utility.main.TriggerSupportUtil;
import org.bukkit.event.player.PlayerRespawnEvent;
import com.praya.myitems.MyItems;
import org.bukkit.event.Listener;
import com.praya.myitems.builder.handler.HandlerEvent;

public class ListenerPlayerRespawn extends HandlerEvent implements Listener
{
    public ListenerPlayerRespawn(final MyItems plugin) {
        super(plugin);
    }
    
    @EventHandler
    public void triggerSupport(final PlayerRespawnEvent event) {
        final Player player = event.getPlayer();
        TriggerSupportUtil.updateSupport(player);
    }
}
