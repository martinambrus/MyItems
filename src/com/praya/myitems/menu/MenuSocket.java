// 
// Decompiled by Procyon v0.5.36
// 

package com.praya.myitems.menu;

import api.praya.myitems.builder.socket.SocketGemsTree;
import java.util.List;
import java.util.ArrayList;
import core.praya.agarthalib.enums.main.SlotType;
import core.praya.agarthalib.builder.menu.MenuSlot;
import api.praya.myitems.builder.socket.SocketGems;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.inventory.Inventory;
import api.praya.agarthalib.builder.support.SupportVault;
import api.praya.agarthalib.manager.plugin.SupportManagerAPI;
import com.praya.myitems.manager.plugin.LanguageManager;
import com.praya.myitems.manager.game.SocketManager;
import com.praya.myitems.manager.game.GameManager;
import com.praya.myitems.manager.plugin.PluginManager;
import com.praya.agarthalib.utility.PlayerUtil;
import com.praya.agarthalib.utility.TextUtil;
import core.praya.agarthalib.enums.main.RomanNumber;
import java.util.HashMap;
import core.praya.agarthalib.enums.branch.SoundEnum;
import org.bukkit.command.CommandSender;
import com.praya.agarthalib.utility.SenderUtil;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Entity;
import com.praya.agarthalib.utility.MetadataUtil;
import com.praya.agarthalib.utility.MathUtil;
import org.bukkit.inventory.ItemStack;
import core.praya.agarthalib.enums.branch.MaterialEnum;
import org.bukkit.entity.LivingEntity;
import com.praya.agarthalib.utility.EquipmentUtil;
import core.praya.agarthalib.builder.menu.MenuGUI;
import com.praya.myitems.config.plugin.MainConfig;
import api.praya.agarthalib.main.AgarthaLibAPI;
import core.praya.agarthalib.builder.menu.MenuSlotAction;
import core.praya.agarthalib.builder.menu.Menu;
import org.bukkit.entity.Player;
import com.praya.myitems.MyItems;
import core.praya.agarthalib.builder.menu.MenuExecutor;
import com.praya.myitems.builder.handler.HandlerMenu;

public class MenuSocket extends HandlerMenu implements MenuExecutor
{
    public MenuSocket(final MyItems plugin) {
        super(plugin);
    }
    
    public void onClick(final Player player, final Menu menu, final MenuSlotAction.ActionType actionType, final String... args) {
        final AgarthaLibAPI agarthaLibAPI = AgarthaLibAPI.getInstance();
        final PluginManager pluginManager = this.plugin.getPluginManager();
        final GameManager gameManager = this.plugin.getGameManager();
        final SocketManager socketManager = gameManager.getSocketManager();
        final LanguageManager lang = pluginManager.getLanguageManager();
        final SupportManagerAPI supportManager = agarthaLibAPI.getPluginManagerAPI().getSupportManager();
        final SupportVault supportVault = supportManager.getSupportVault();
        final MainConfig mainConfig = MainConfig.getInstance();
        if (menu instanceof MenuGUI) {
            final MenuGUI menuGUI = (MenuGUI)menu;
            if (args.length > 0) {
                final String label = args[0];
                if (label.equalsIgnoreCase("MyItems") && args.length > 1) {
                    final String key = args[1];
                    if (key.equalsIgnoreCase("Socket") && args.length > 2) {
                        final String subArg = args[2];
                        final String metadataID = "MyItems Socket Line_Selector";
                        if (subArg.equalsIgnoreCase("Item_Input") || subArg.equalsIgnoreCase("Socket_Input")) {
                            if (args.length > 3) {
                                final String textCell = args[3];
                                final MenuGUI.SlotCell cell = MenuGUI.SlotCell.valueOf(textCell);
                                final Inventory inventory = menu.getInventory();
                                final int index = cell.getIndex();
                                final ItemStack itemCurrent = inventory.getItem(index);
                                final ItemStack itemCursor = player.getItemOnCursor();
                                final boolean isSocketSlot = subArg.equalsIgnoreCase("Socket_Input");
                                if (EquipmentUtil.isSolid(itemCursor)) {
                                    if (isSocketSlot) {
                                        final ItemStack itemRodUnlock = mainConfig.getSocketItemRodUnlock();
                                        final ItemStack itemRodRemove = mainConfig.getSocketItemRodRemove();
                                        final boolean isGems = socketManager.isSocketItem(itemCursor);
                                        final boolean isRodUnlock = itemCursor.isSimilar(itemRodUnlock);
                                        final boolean isRodRemove = itemCursor.isSimilar(itemRodRemove);
                                        if (!isGems && !isRodUnlock && !isRodRemove) {
                                            return;
                                        }
                                    }
                                    else {
                                        final boolean containsSocketEmpty = socketManager.containsSocketEmpty(itemCursor);
                                        final boolean containsSocketGems = socketManager.containsSocketGems(itemCursor);
                                        final boolean containsSocketLocked = socketManager.containsSocketLocked(itemCursor);
                                        if (!containsSocketEmpty && !containsSocketGems && !containsSocketLocked) {
                                            return;
                                        }
                                    }
                                }
                                if (EquipmentUtil.isSolid(itemCurrent)) {
                                    final String headerItemInput = lang.getText((LivingEntity)player, "Menu_Item_Header_Socket_Item_Input");
                                    final String headerSocketInput = lang.getText((LivingEntity)player, "Menu_Item_Header_Socket_Socket_Input");
                                    final ItemStack itemItemInput = EquipmentUtil.createItem(MaterialEnum.WHITE_STAINED_GLASS_PANE, headerItemInput, 1);
                                    final ItemStack itemSocketInput = EquipmentUtil.createItem(MaterialEnum.WHITE_STAINED_GLASS_PANE, headerSocketInput, 1);
                                    if (itemCurrent.isSimilar(itemItemInput) || itemCurrent.isSimilar(itemSocketInput)) {
                                        if (!EquipmentUtil.isSolid(itemCursor)) {
                                            return;
                                        }
                                        inventory.setItem(index, itemCursor);
                                        player.setItemOnCursor((ItemStack)null);
                                    }
                                    else if (!EquipmentUtil.isSolid(itemCursor)) {
                                        final ItemStack itemReplace = isSocketSlot ? itemSocketInput : itemItemInput;
                                        player.setItemOnCursor(itemCurrent);
                                        inventory.setItem(index, itemReplace);
                                    }
                                    else if (itemCurrent.isSimilar(itemCursor)) {
                                        final int amountCurrent = itemCurrent.getAmount();
                                        final int amountCursor = itemCursor.getAmount();
                                        final int amountTotal = amountCurrent + amountCursor;
                                        final int maxStack = itemCurrent.getMaxStackSize();
                                        final int newCurrent = MathUtil.limitInteger(amountTotal, 1, maxStack);
                                        final int newCursor = amountTotal - newCurrent;
                                        itemCurrent.setAmount(newCurrent);
                                        itemCursor.setAmount(newCursor);
                                        inventory.setItem(index, itemCurrent);
                                        if (newCursor == 0) {
                                            player.setItemOnCursor((ItemStack)null);
                                        }
                                        else {
                                            player.setItemOnCursor(itemCursor);
                                        }
                                    }
                                    else {
                                        inventory.setItem(index, itemCursor);
                                        player.setItemOnCursor(itemCurrent);
                                    }
                                    MetadataUtil.removeMetadata((Entity)player, "MyItems Socket Line_Selector");
                                    this.updateSocketMenu(menuGUI, player);
                                }
                            }
                        }
                        else if (subArg.equalsIgnoreCase("Line_Selector")) {
                            if (args.length > 3) {
                                final String textLine = args[3];
                                final int line = MathUtil.parseInteger(textLine);
                                final MetadataValue metadata = MetadataUtil.createMetadata((Object)line);
                                player.setMetadata("MyItems Socket Line_Selector", metadata);
                                this.updateSocketMenu(menuGUI, player);
                            }
                        }
                        else if (subArg.equalsIgnoreCase("Accept")) {
                            final MenuGUI.SlotCell cellItemResult = MenuGUI.SlotCell.G3;
                            final Inventory inventory2 = menu.getInventory();
                            final String headerItemItemResult = lang.getText((LivingEntity)player, "Menu_Item_Header_Socket_Item_Result");
                            final ItemStack itemItemResult = EquipmentUtil.createItem(MaterialEnum.RED_STAINED_GLASS_PANE, headerItemItemResult, 1);
                            final ItemStack itemResult = inventory2.getItem(cellItemResult.getIndex());
                            if (!itemResult.isSimilar(itemItemResult)) {
                                final MenuGUI.SlotCell cellItemInput = MenuGUI.SlotCell.B3;
                                final MenuGUI.SlotCell cellSocketInput = MenuGUI.SlotCell.C3;
                                final String headerItemInput = lang.getText((LivingEntity)player, "Menu_Item_Header_Socket_Item_Input");
                                final String headerSocketInput = lang.getText((LivingEntity)player, "Menu_Item_Header_Socket_Socket_Input");
                                final ItemStack itemItemInput = EquipmentUtil.createItem(MaterialEnum.WHITE_STAINED_GLASS_PANE, headerItemInput, 1);
                                final ItemStack itemSocketInput = EquipmentUtil.createItem(MaterialEnum.WHITE_STAINED_GLASS_PANE, headerSocketInput, 1);
                                final ItemStack itemItem = inventory2.getItem(cellItemInput.getIndex());
                                final ItemStack itemSocket = inventory2.getItem(cellSocketInput.getIndex());
                                final ItemStack itemRodUnlock2 = mainConfig.getSocketItemRodUnlock();
                                final ItemStack itemRodRemove2 = mainConfig.getSocketItemRodRemove();
                                final int line2 = MetadataUtil.getMetadata((Entity)player, "MyItems Socket Line_Selector").asInt();
                                final int amountItem = itemItem.getAmount();
                                final int amountSocket = itemSocket.getAmount();
                                int actionID;
                                double actionCost;
                                if (socketManager.isSocketItem(itemSocket)) {
                                    actionID = 0;
                                    actionCost = mainConfig.getSocketCostSocket();
                                }
                                else if (itemSocket.isSimilar(itemRodUnlock2)) {
                                    actionID = 1;
                                    actionCost = mainConfig.getSocketCostUnlock();
                                }
                                else if (itemSocket.isSimilar(itemRodRemove2)) {
                                    actionID = 2;
                                    actionCost = mainConfig.getSocketCostDesocket();
                                }
                                else {
                                    actionID = -1;
                                    actionCost = 0.0;
                                }
                                if (supportVault != null) {
                                    final double playerBalance = supportVault.getBalance((OfflinePlayer)player);
                                    if (playerBalance < actionCost) {
                                        final String message = lang.getText((LivingEntity)player, "Argument_Lack_Money");
                                        SenderUtil.sendMessage((CommandSender)player, message);
                                        SenderUtil.playSound((CommandSender)player, SoundEnum.ENTITY_BLAZE_DEATH);
                                        return;
                                    }
                                    supportVault.remBalance((OfflinePlayer)player, actionCost);
                                }
                                if (amountSocket > 1) {
                                    final int amountSocketLeft = amountSocket - 1;
                                    itemSocket.setAmount(amountSocketLeft);
                                    inventory2.setItem(cellSocketInput.getIndex(), itemSocket);
                                }
                                else {
                                    inventory2.setItem(cellSocketInput.getIndex(), itemSocketInput);
                                }
                                if (actionID == 0) {
                                    final SocketGems socketBuild = socketManager.getSocketBuild(itemSocket);
                                    final String socketID = socketManager.getSocketName(itemSocket);
                                    final double chance = socketBuild.getSuccessRate();
                                    final int grade = socketBuild.getGrade();
                                    if (!MathUtil.chanceOf(chance)) {
                                        final String message2 = lang.getText((LivingEntity)player, "Socket_Input_Failure");
                                        SenderUtil.sendMessage((CommandSender)player, message2);
                                        SenderUtil.playSound((CommandSender)player, SoundEnum.ENTITY_BLAZE_DEATH);
                                        return;
                                    }
                                    final HashMap<String, String> map = new HashMap<String, String>();
                                    String message3 = lang.getText((LivingEntity)player, "Socket_Input_Success");
                                    map.put("socket", socketID);
                                    map.put("grade", RomanNumber.getRomanNumber(grade));
                                    map.put("line", String.valueOf(line2));
                                    message3 = TextUtil.placeholder((HashMap)map, message3);
                                    PlayerUtil.addItem(player, itemResult);
                                    SenderUtil.sendMessage((CommandSender)player, message3);
                                    SenderUtil.playSound((CommandSender)player, SoundEnum.ENTITY_EXPERIENCE_ORB_PICKUP);
                                }
                                else if (actionID == 1) {
                                    final HashMap<String, String> map2 = new HashMap<String, String>();
                                    String message4 = lang.getText((LivingEntity)player, "Socket_Unlock_Success");
                                    map2.put("line", String.valueOf(line2));
                                    message4 = TextUtil.placeholder((HashMap)map2, message4);
                                    PlayerUtil.addItem(player, itemResult);
                                    SenderUtil.sendMessage((CommandSender)player, message4);
                                    SenderUtil.playSound((CommandSender)player, SoundEnum.ENTITY_EXPERIENCE_ORB_PICKUP);
                                }
                                else if (actionID == 2) {
                                    final HashMap<String, String> map2 = new HashMap<String, String>();
                                    String message4 = lang.getText((LivingEntity)player, "Socket_Remove_Success");
                                    map2.put("line", String.valueOf(line2));
                                    message4 = TextUtil.placeholder((HashMap)map2, message4);
                                    PlayerUtil.addItem(player, itemResult);
                                    SenderUtil.sendMessage((CommandSender)player, message4);
                                    SenderUtil.playSound((CommandSender)player, SoundEnum.ENTITY_EXPERIENCE_ORB_PICKUP);
                                }
                                if (amountItem > 1) {
                                    final int amountItemLeft = amountItem - 1;
                                    itemItem.setAmount(amountItemLeft);
                                    inventory2.setItem(cellItemInput.getIndex(), itemItem);
                                }
                                else {
                                    inventory2.setItem(cellItemInput.getIndex(), itemItemInput);
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    
    public final void updateSocketMenu(final MenuGUI menuGUI, final Player player) {
        final PluginManager pluginManager = this.plugin.getPluginManager();
        final GameManager gameManager = this.plugin.getGameManager();
        final SocketManager socketManager = gameManager.getSocketManager();
        final LanguageManager lang = pluginManager.getLanguageManager();
        final MainConfig mainConfig = MainConfig.getInstance();
        final String metadataID = "MyItems Socket Line_Selector";
        final String headerLineSelector = lang.getText((LivingEntity)player, "Menu_Item_Header_Socket_Line_Selector");
        final MenuGUI.SlotCell cellItemInput = MenuGUI.SlotCell.B3;
        final MenuGUI.SlotCell cellSocketInput = MenuGUI.SlotCell.C3;
        final MenuGUI.SlotCell cellItemResult = MenuGUI.SlotCell.G3;
        final MenuGUI.SlotCell[] cellsLineSelector = { MenuGUI.SlotCell.F2, MenuGUI.SlotCell.G2, MenuGUI.SlotCell.H2 };
        final MenuSlot menuSlotItemResult = new MenuSlot(cellItemResult.getIndex());
        final Inventory inventory = menuGUI.getInventory();
        final ItemStack itemItemInput = inventory.getItem(cellItemInput.getIndex());
        final ItemStack itemSocketInput = inventory.getItem(cellSocketInput.getIndex());
        final HashMap<String, String> map = new HashMap<String, String>();
        int socketActionID = -1;
        int socketLine = -1;
        int socketLinePrevious = -1;
        int socketLineNext = -1;
        List<String> loreLineSelector = lang.getListText((LivingEntity)player, "Menu_Item_Lores_Socket_Line_Selector");
        if (EquipmentUtil.isSolid(itemItemInput) && EquipmentUtil.isSolid(itemSocketInput)) {
            final ItemStack itemRodUnlock = mainConfig.getSocketItemRodUnlock();
            final ItemStack itemRodRemove = mainConfig.getSocketItemRodRemove();
            final boolean containsSocketEmpty = socketManager.containsSocketEmpty(itemItemInput);
            final boolean containsSocketLocked = socketManager.containsSocketLocked(itemItemInput);
            final boolean containsSocketGems = socketManager.containsSocketGems(itemItemInput);
            final boolean isSocketGems = socketManager.isSocketItem(itemSocketInput);
            final boolean isSocketRodUnlock = itemSocketInput.isSimilar(itemRodUnlock);
            final boolean isSocketRodRemove = itemSocketInput.isSimilar(itemRodRemove);
            if (containsSocketEmpty && isSocketGems) {
                final SocketGems socket = socketManager.getSocketBuild(itemSocketInput);
                final SocketGemsTree socketTree = socket.getSocketTree();
                final SlotType typeItem = socketTree.getTypeItem();
                final SlotType typeDefault = SlotType.getSlotType(itemItemInput);
                if (typeItem.equals((Object)typeDefault) || typeItem.equals((Object)SlotType.UNIVERSAL)) {
                    socketActionID = 0;
                }
            }
            else if (containsSocketLocked && isSocketRodUnlock) {
                socketActionID = 1;
            }
            else if (containsSocketGems && isSocketRodRemove) {
                socketActionID = 2;
            }
            if (socketActionID != -1) {
                List<Integer> listLineSocket = null;
                switch (socketActionID) {
                    case 0: {
                        listLineSocket = socketManager.getLineLoresSocketEmpty(itemItemInput);
                        break;
                    }
                    case 1: {
                        listLineSocket = socketManager.getLineLoresSocketLocked(itemItemInput);
                        break;
                    }
                    case 2: {
                        listLineSocket = socketManager.getLineLoresSocket(itemItemInput);
                        break;
                    }
                    default: {
                        listLineSocket = new ArrayList<Integer>();
                        break;
                    }
                }
                final int size = listLineSocket.size();
                final boolean hasMetadataLine = MetadataUtil.hasMetadata((Entity)player, "MyItems Socket Line_Selector");
                if (hasMetadataLine) {
                    final int metadataLine = MetadataUtil.getMetadata((Entity)player, "MyItems Socket Line_Selector").asInt();
                    int order = 0;
                    for (int index = 0; index < size; ++index) {
                        if (listLineSocket.get(index) == metadataLine) {
                            order = index;
                            break;
                        }
                    }
                    socketLine = (listLineSocket.contains(metadataLine) ? metadataLine : listLineSocket.get(0));
                    socketLinePrevious = ((order == 0) ? listLineSocket.get(size - 1) : listLineSocket.get(order - 1));
                    socketLineNext = ((order == size - 1) ? listLineSocket.get(0) : listLineSocket.get(order + 1));
                }
                else {
                    socketLine = listLineSocket.get(0);
                    socketLinePrevious = listLineSocket.get(size - 1);
                    socketLineNext = ((size > 1) ? listLineSocket.get(1) : listLineSocket.get(0));
                }
            }
        }
        String socketAction;
        double socketCost;
        if (socketActionID == 0) {
            socketAction = lang.getText((LivingEntity)player, "Socket_Action_Gems");
            socketCost = mainConfig.getSocketCostSocket();
        }
        else if (socketActionID == 1) {
            socketAction = lang.getText((LivingEntity)player, "Socket_Action_Unlock");
            socketCost = mainConfig.getSocketCostUnlock();
        }
        else if (socketActionID == 2) {
            socketAction = lang.getText((LivingEntity)player, "Socket_Action_Desocket");
            socketCost = mainConfig.getSocketCostDesocket();
        }
        else {
            socketAction = lang.getText((LivingEntity)player, "Socket_Action_Unknown");
            socketCost = 0.0;
        }
        map.put("socket_line", (socketLine == -1) ? "None" : String.valueOf(socketLine));
        map.put("socket_action", String.valueOf(socketAction));
        map.put("socket_cost", String.valueOf(socketCost));
        map.put("symbol_currency", mainConfig.getUtilityCurrency());
        loreLineSelector = (List<String>)TextUtil.placeholder((HashMap)map, (List)loreLineSelector);
        final ItemStack itemLineSelector = EquipmentUtil.createItem(MaterialEnum.SIGN, headerLineSelector, 1, (List)loreLineSelector);
        MenuGUI.SlotCell[] array;
        for (int length = (array = cellsLineSelector).length, i = 0; i < length; ++i) {
            final MenuGUI.SlotCell cell = array[i];
            final int slot = cell.getIndex();
            final MenuSlot menuSlot = new MenuSlot(slot);
            final String actionLinePrevious = "MyItems Socket Line_Selector " + socketLinePrevious;
            final String actionLineNext = "MyItems Socket Line_Selector " + socketLineNext;
            menuSlot.setItem(itemLineSelector);
            menuSlot.setActionArguments(MenuSlotAction.ActionCategory.ALL_LEFT_CLICK, actionLinePrevious);
            menuSlot.setActionArguments(MenuSlotAction.ActionCategory.ALL_RIGHT_CLICK, actionLineNext);
            menuGUI.setMenuSlot(menuSlot);
        }
        if (socketActionID == -1) {
            final String headerItemItemResult = lang.getText((LivingEntity)player, "Menu_Item_Header_Socket_Item_Result");
            final ItemStack itemItemResult = EquipmentUtil.createItem(MaterialEnum.RED_STAINED_GLASS_PANE, headerItemItemResult, 1);
            menuSlotItemResult.setItem(itemItemResult);
            menuGUI.setMenuSlot(menuSlotItemResult);
        }
        else {
            final ItemStack itemItemResult2 = itemItemInput.clone();
            final MetadataValue metadata = MetadataUtil.createMetadata((Object)socketLine);
            if (socketActionID == 0) {
                final SocketGems socketBuild = socketManager.getSocketBuild(itemSocketInput);
                final String gemsID = socketManager.getSocketName(itemSocketInput);
                final int gemsGrade = socketBuild.getGrade();
                final String loreGems = socketManager.getTextSocketGemsLore(gemsID, gemsGrade);
                EquipmentUtil.setLore(itemItemResult2, socketLine, loreGems);
            }
            else if (socketActionID == 1 || socketActionID == 2) {
                final String loreEmpty = socketManager.getTextSocketSlotEmpty();
                EquipmentUtil.setLore(itemItemResult2, socketLine, loreEmpty);
            }
            itemItemResult2.setAmount(1);
            player.setMetadata("MyItems Socket Line_Selector", metadata);
            menuSlotItemResult.setItem(itemItemResult2);
            menuGUI.setMenuSlot(menuSlotItemResult);
        }
        player.updateInventory();
    }
}
