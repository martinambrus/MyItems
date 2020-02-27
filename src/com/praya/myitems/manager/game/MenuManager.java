// 
// Decompiled by Procyon v0.5.36
// 

package com.praya.myitems.manager.game;

import org.bukkit.inventory.ItemStack;
import core.praya.agarthalib.builder.text.Text;
import com.praya.myitems.manager.plugin.LanguageManager;
import com.praya.myitems.manager.plugin.PlaceholderManager;
import com.praya.myitems.manager.plugin.PluginManager;
import core.praya.agarthalib.builder.menu.Menu;
import org.bukkit.entity.Entity;
import com.praya.agarthalib.utility.MetadataUtil;
import java.util.Map;
import core.praya.agarthalib.builder.menu.MenuSlotAction;
import java.util.List;
import com.praya.agarthalib.utility.EquipmentUtil;
import core.praya.agarthalib.enums.branch.MaterialEnum;
import java.util.concurrent.ConcurrentHashMap;
import core.praya.agarthalib.builder.menu.MenuSlot;
import core.praya.agarthalib.builder.menu.MenuGUI;
import core.praya.agarthalib.builder.text.TextLine;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import com.praya.myitems.menu.MenuStats;
import com.praya.myitems.menu.MenuSocket;
import com.praya.myitems.MyItems;
import core.praya.agarthalib.builder.menu.MenuExecutor;
import com.praya.myitems.builder.handler.HandlerManager;

public class MenuManager extends HandlerManager
{
    private final MenuExecutor executorSocket;
    private final MenuExecutor executorStats;
    
    protected MenuManager(final MyItems plugin) {
        super(plugin);
        this.executorSocket = (MenuExecutor)new MenuSocket(plugin);
        this.executorStats = (MenuExecutor)new MenuStats(plugin);
    }
    
    public final void openMenuSocket(final Player player) {
        final PluginManager pluginManager = this.plugin.getPluginManager();
        final PlaceholderManager placeholderManager = pluginManager.getPlaceholderManager();
        final LanguageManager lang = pluginManager.getLanguageManager();
        final MenuExecutor executor = this.executorSocket;
        final String metadataID = "MyItems Socket Line_Selector";
        final String id = "MyItems Socket";
        final String textTitle = lang.getText((LivingEntity)player, "Menu_Page_Title_Socket");
        final int row = 4;
        final Text title = (Text)new TextLine(textTitle);
        final String prefix = placeholderManager.getPlaceholder("prefix");
        final String headerItemInput = lang.getText((LivingEntity)player, "Menu_Item_Header_Socket_Item_Input");
        final String headerSocketInput = lang.getText((LivingEntity)player, "Menu_Item_Header_Socket_Socket_Input");
        final String headerItemInformation = lang.getText((LivingEntity)player, "Menu_Item_Header_Socket_Item_Information");
        final String headerSocketInformation = lang.getText((LivingEntity)player, "Menu_Item_Header_Socket_Socket_Information");
        final String headerAccept = lang.getText((LivingEntity)player, "Menu_Item_Header_Socket_Accept");
        final String headerCancel = lang.getText((LivingEntity)player, "Menu_Item_Header_Socket_Cancel");
        final MenuGUI.SlotCell cellItemInput = MenuGUI.SlotCell.B3;
        final MenuGUI.SlotCell cellSocketInput = MenuGUI.SlotCell.C3;
        final MenuGUI.SlotCell cellItemInformation = MenuGUI.SlotCell.B2;
        final MenuGUI.SlotCell cellSocketInformation = MenuGUI.SlotCell.C2;
        final MenuGUI.SlotCell cellAccept = MenuGUI.SlotCell.H3;
        final MenuGUI.SlotCell cellCancel = MenuGUI.SlotCell.F3;
        final MenuSlot menuSlotItemInput = new MenuSlot(cellItemInput.getIndex());
        final MenuSlot menuSlotSocketInput = new MenuSlot(cellSocketInput.getIndex());
        final MenuSlot menuSlotItemInformation = new MenuSlot(cellItemInformation.getIndex());
        final MenuSlot menuSlotSocketInformation = new MenuSlot(cellSocketInformation.getIndex());
        final MenuSlot menuSlotAccept = new MenuSlot(cellAccept.getIndex());
        final MenuSlot menuSlotCancel = new MenuSlot(cellCancel.getIndex());
        final Map<Integer, MenuSlot> mapSlot = new ConcurrentHashMap<Integer, MenuSlot>();
        final List<String> loreItemInformation = lang.getListText((LivingEntity)player, "Menu_Item_Lores_Socket_Item_Information");
        final List<String> loreSocketInformation = lang.getListText((LivingEntity)player, "Menu_Item_Lores_Socket_Socket_Information");
        final List<String> loreAccept = lang.getListText((LivingEntity)player, "Menu_Item_Lores_Socket_Accept");
        final List<String> loreCancel = lang.getListText((LivingEntity)player, "Menu_Item_Lores_Socket_Cancel");
        final ItemStack itemPaneBlack = EquipmentUtil.createItem(MaterialEnum.BLACK_STAINED_GLASS_PANE, prefix, 1);
        final ItemStack itemItemInput = EquipmentUtil.createItem(MaterialEnum.WHITE_STAINED_GLASS_PANE, headerItemInput, 1);
        final ItemStack itemSocketInput = EquipmentUtil.createItem(MaterialEnum.WHITE_STAINED_GLASS_PANE, headerSocketInput, 1);
        final ItemStack itemItemInformation = EquipmentUtil.createItem(MaterialEnum.SIGN, headerItemInformation, 1, (List)loreItemInformation);
        final ItemStack itemSocketInformation = EquipmentUtil.createItem(MaterialEnum.SIGN, headerSocketInformation, 1, (List)loreSocketInformation);
        final ItemStack itemAccept = EquipmentUtil.createItem(MaterialEnum.GREEN_WOOL, headerAccept, 1, (List)loreAccept);
        final ItemStack itemCancel = EquipmentUtil.createItem(MaterialEnum.RED_WOOL, headerCancel, 1, (List)loreCancel);
        for (int index = 0; index < 36; ++index) {
            final MenuSlot menuSlot = new MenuSlot(index);
            menuSlot.setItem(itemPaneBlack);
            mapSlot.put(index, menuSlot);
        }
        menuSlotItemInput.setItem(itemItemInput);
        menuSlotSocketInput.setItem(itemSocketInput);
        menuSlotItemInformation.setItem(itemItemInformation);
        menuSlotSocketInformation.setItem(itemSocketInformation);
        menuSlotAccept.setItem(itemAccept);
        menuSlotCancel.setItem(itemCancel);
        menuSlotItemInput.setActionArguments(MenuSlotAction.ActionCategory.ALL_CLICK, "MyItems Socket Item_Input " + cellItemInput.toString());
        menuSlotSocketInput.setActionArguments(MenuSlotAction.ActionCategory.ALL_CLICK, "MyItems Socket Socket_Input " + cellSocketInput.toString());
        menuSlotAccept.setActionArguments(MenuSlotAction.ActionCategory.ALL_CLICK, "MyItems Socket Accept");
        menuSlotCancel.setActionClosed(MenuSlotAction.ActionCategory.ALL_CLICK, true);
        menuSlotAccept.setActionClosed(MenuSlotAction.ActionCategory.ALL_CLICK, true);
        mapSlot.put(menuSlotItemInput.getSlot(), menuSlotItemInput);
        mapSlot.put(menuSlotSocketInput.getSlot(), menuSlotSocketInput);
        mapSlot.put(menuSlotItemInformation.getSlot(), menuSlotItemInformation);
        mapSlot.put(menuSlotSocketInformation.getSlot(), menuSlotSocketInformation);
        mapSlot.put(menuSlotAccept.getSlot(), menuSlotAccept);
        mapSlot.put(menuSlotCancel.getSlot(), menuSlotCancel);
        final Menu menu = (Menu)new MenuGUI((Player)null, "MyItems Socket", 4, title, executor, false, (Map)mapSlot);
        MetadataUtil.removeMetadata((Entity)player, "MyItems Socket Line_Selector");
        Menu.openMenu(player, menu);
    }
    
    public final void openMenuStats(final Player player) {
        final PluginManager pluginManager = this.plugin.getPluginManager();
        final PlaceholderManager placeholderManager = pluginManager.getPlaceholderManager();
        final LanguageManager lang = pluginManager.getLanguageManager();
        final MenuExecutor executor = this.executorStats;
        final String id = "MyItems Stats";
        final String textTitle = lang.getText((LivingEntity)player, "Menu_Page_Title_Stats");
        final int row = 6;
        final Text title = (Text)new TextLine(textTitle);
        final String prefix = placeholderManager.getPlaceholder("prefix");
        final int[] arrayPaneWhite = { 0, 8, 9, 17, 18, 26, 27, 35, 36, 44, 45, 53 };
        final Map<Integer, MenuSlot> mapSlot = new ConcurrentHashMap<Integer, MenuSlot>();
        final ItemStack itemPaneBlack = EquipmentUtil.createItem(MaterialEnum.BLACK_STAINED_GLASS_PANE, prefix, 1);
        final ItemStack itemPaneWhite = EquipmentUtil.createItem(MaterialEnum.WHITE_STAINED_GLASS_PANE, prefix, 1);
        for (int index = 0; index < 54; ++index) {
            final MenuSlot menuSlot = new MenuSlot(index);
            menuSlot.setItem(itemPaneBlack);
            mapSlot.put(index, menuSlot);
        }
        int[] array;
        for (int length = (array = arrayPaneWhite).length, i = 0; i < length; ++i) {
            final int index = array[i];
            final MenuSlot menuSlot2 = new MenuSlot(index);
            menuSlot2.setItem(itemPaneWhite);
            mapSlot.put(index, menuSlot2);
        }
        final MenuGUI menuGUI = new MenuGUI((Player)null, "MyItems Stats", 6, title, executor, false, (Map)mapSlot);
        Menu.openMenu(player, (Menu)menuGUI);
    }
}
