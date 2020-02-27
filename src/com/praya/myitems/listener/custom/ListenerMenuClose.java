// 
// Decompiled by Procyon v0.5.36
// 

package com.praya.myitems.listener.custom;

import org.bukkit.event.EventPriority;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Inventory;
import org.bukkit.entity.Player;
import core.praya.agarthalib.builder.menu.Menu;
import com.praya.myitems.manager.plugin.LanguageManager;
import com.praya.myitems.manager.plugin.PluginManager;
import com.praya.agarthalib.utility.PlayerUtil;
import com.praya.agarthalib.utility.EquipmentUtil;
import core.praya.agarthalib.enums.branch.MaterialEnum;
import org.bukkit.entity.LivingEntity;
import core.praya.agarthalib.builder.menu.MenuGUI;
import api.praya.agarthalib.builder.event.MenuCloseEvent;
import com.praya.myitems.MyItems;
import org.bukkit.event.Listener;
import com.praya.myitems.builder.handler.HandlerEvent;

public class ListenerMenuClose extends HandlerEvent implements Listener
{
    public ListenerMenuClose(final MyItems plugin) {
        super(plugin);
    }
    
    @EventHandler(priority = EventPriority.NORMAL)
    public void menuCloseEvent(final MenuCloseEvent event) {
        final PluginManager pluginManager = this.plugin.getPluginManager();
        final LanguageManager lang = pluginManager.getLanguageManager();
        final Menu menu = event.getMenu();
        final Player player = event.getPlayer();
        final String id = menu.getID();
        if (menu instanceof MenuGUI) {
            final MenuGUI menuGUI = (MenuGUI)menu;
            if (id.equalsIgnoreCase("MyItems Socket")) {
                final Inventory inventory = menuGUI.getInventory();
                final MenuGUI.SlotCell cellItemInput = MenuGUI.SlotCell.B3;
                final MenuGUI.SlotCell cellSocketInput = MenuGUI.SlotCell.C3;
                final String headerItemInput = lang.getText((LivingEntity)player, "Menu_Item_Header_Socket_Item_Input");
                final String headerSocketInput = lang.getText((LivingEntity)player, "Menu_Item_Header_Socket_Socket_Input");
                final ItemStack itemItemInput = EquipmentUtil.createItem(MaterialEnum.WHITE_STAINED_GLASS_PANE, headerItemInput, 1);
                final ItemStack itemSocketInput = EquipmentUtil.createItem(MaterialEnum.WHITE_STAINED_GLASS_PANE, headerSocketInput, 1);
                final ItemStack itemItem = inventory.getItem(cellItemInput.getIndex());
                final ItemStack itemSocket = inventory.getItem(cellSocketInput.getIndex());
                if (!itemItem.isSimilar(itemItemInput)) {
                    PlayerUtil.addItem(player, itemItem);
                }
                if (!itemSocket.isSimilar(itemSocketInput)) {
                    PlayerUtil.addItem(player, itemSocket);
                }
            }
        }
    }
}
