// 
// Decompiled by Procyon v0.5.36
// 

package com.praya.myitems.listener.support;

import org.bukkit.event.EventPriority;
import org.bukkit.event.EventHandler;
import org.bukkit.entity.Player;
import com.praya.myitems.utility.main.TriggerSupportUtil;
import com.sucy.skill.api.event.PlayerLevelUpEvent;
import com.praya.myitems.MyItems;
import org.bukkit.event.Listener;
import com.praya.myitems.builder.handler.HandlerEvent;

public class ListenerPlayerLevelUp extends HandlerEvent implements Listener
{
    public ListenerPlayerLevelUp(final MyItems plugin) {
        super(plugin);
    }
    
    @EventHandler(priority = EventPriority.NORMAL)
    public void eventPlayerLevelUp(final PlayerLevelUpEvent event) {
        final Player player = event.getPlayerData().getPlayer();
        TriggerSupportUtil.updateSupport(player);
    }
}
