// 
// Decompiled by Procyon v0.5.36
// 

package com.praya.myitems.listener.custom;

import org.bukkit.event.EventPriority;
import org.bukkit.event.EventHandler;
import core.praya.agarthalib.builder.menu.MenuExecutor;
import org.bukkit.entity.Player;
import core.praya.agarthalib.builder.menu.Menu;
import com.praya.myitems.menu.MenuStats;
import com.praya.myitems.menu.MenuSocket;
import org.bukkit.command.CommandSender;
import com.praya.agarthalib.utility.SenderUtil;
import core.praya.agarthalib.enums.branch.SoundEnum;
import core.praya.agarthalib.builder.menu.MenuGUI;
import api.praya.agarthalib.builder.event.MenuOpenEvent;
import com.praya.myitems.MyItems;
import org.bukkit.event.Listener;
import com.praya.myitems.builder.handler.HandlerEvent;

public class ListenerMenuOpen extends HandlerEvent implements Listener
{
    public ListenerMenuOpen(final MyItems plugin) {
        super(plugin);
    }
    
    @EventHandler(priority = EventPriority.NORMAL)
    public void menuOpenEvent(final MenuOpenEvent event) {
        final Menu menu = event.getMenu();
        final Player player = event.getPlayer();
        final String id = menu.getID();
        if (!event.isCancelled() && menu instanceof MenuGUI) {
            final MenuGUI menuGUI = (MenuGUI)menu;
            if (id.startsWith("MyItems")) {
                SenderUtil.playSound((CommandSender)player, SoundEnum.ENTITY_EXPERIENCE_ORB_PICKUP);
            }
            if (id.equalsIgnoreCase("MyItems Socket")) {
                final MenuExecutor executor = menu.getExecutor();
                if (executor instanceof MenuSocket) {
                    final MenuSocket executorSocket = (MenuSocket)executor;
                    executorSocket.updateSocketMenu(menuGUI, player);
                }
            }
            else if (id.equalsIgnoreCase("MyItems Stats")) {
                final MenuExecutor executor = menu.getExecutor();
                if (executor instanceof MenuStats) {
                    final MenuStats executorStats = (MenuStats)executor;
                    executorStats.updateStatsMenu(menuGUI, player);
                }
            }
        }
    }
}
