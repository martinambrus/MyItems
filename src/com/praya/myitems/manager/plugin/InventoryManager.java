// 
// Decompiled by Procyon v0.5.36
// 

package com.praya.myitems.manager.plugin;

import org.bukkit.inventory.InventoryHolder;
import org.bukkit.Bukkit;
import com.praya.agarthalib.utility.TextUtil;
import com.praya.agarthalib.utility.MathUtil;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.command.CommandSender;
import com.praya.agarthalib.utility.SenderUtil;
import org.bukkit.entity.Player;
import com.praya.myitems.MyItems;
import core.praya.agarthalib.builder.inventory.InventoryBuild;
import java.util.UUID;
import java.util.HashMap;
import com.praya.myitems.builder.handler.HandlerManager;

public class InventoryManager extends HandlerManager
{
    private final HashMap<UUID, InventoryBuild> mapInventoryBuild;
    private final HashMap<UUID, Long> mapInventoryCooldown;
    private final HashMap<UUID, Long> mapInventoryCloseCooldown;
    
    protected InventoryManager(final MyItems plugin) {
        super(plugin);
        this.mapInventoryBuild = new HashMap<UUID, InventoryBuild>();
        this.mapInventoryCooldown = new HashMap<UUID, Long>();
        this.mapInventoryCloseCooldown = new HashMap<UUID, Long>();
    }
    
    public final HashMap<UUID, InventoryBuild> getMapInventoryBuild() {
        return this.mapInventoryBuild;
    }
    
    public final HashMap<UUID, Long> getMapInventoryCooldown() {
        return this.mapInventoryCooldown;
    }
    
    public final HashMap<UUID, Long> getMapInventoryCloseCooldown() {
        return this.mapInventoryCloseCooldown;
    }
    
    public final long getInventoryCooldown(final Player player) {
        return this.hasInventoryCooldown(player) ? this.getMapInventoryCooldown().get(player.getUniqueId()) : 0L;
    }
    
    public final long getInventoryCloseCooldown(final Player player) {
        return this.hasInventoryCloseCooldown(player) ? this.getMapInventoryCloseCooldown().get(player.getUniqueId()) : 0L;
    }
    
    public final InventoryBuild getInventoryBuild(final Player player) {
        return this.hasInventoryBuild(player) ? this.getMapInventoryBuild().get(player.getUniqueId()) : null;
    }
    
    public final void setInventoryCooldown(final Player player, final long cooldown) {
        final long expired = System.currentTimeMillis() + cooldown;
        this.getMapInventoryCooldown().put(player.getUniqueId(), expired);
    }
    
    public final void setInventoryCloseCooldown(final Player player, final long cooldown) {
        final long expired = System.currentTimeMillis() + cooldown;
        this.getMapInventoryCloseCooldown().put(player.getUniqueId(), expired);
    }
    
    public final boolean isCooldown(final Player player) {
        if (!this.hasInventoryCooldown(player)) {
            return false;
        }
        final long expired = this.getInventoryCooldown(player);
        final long time = System.currentTimeMillis();
        if (time < expired) {
            return true;
        }
        this.removeInventoryCooldown(player);
        return false;
    }
    
    public final boolean isCloseCooldown(final Player player) {
        if (!this.hasInventoryCloseCooldown(player)) {
            return false;
        }
        final long expired = this.getInventoryCloseCooldown(player);
        final long time = System.currentTimeMillis();
        if (time < expired) {
            return true;
        }
        this.removeInventoryCloseCooldown(player);
        return false;
    }
    
    public final void openInventory(final Player player, final InventoryBuild inventoryBuild) {
        final UUID playerID = player.getUniqueId();
        final Inventory inventory = inventoryBuild.getInventory();
        if (inventoryBuild.hasSoundOpen()) {
            SenderUtil.playSound((CommandSender)player, inventoryBuild.getSoundOpen());
        }
        this.setInventoryCooldown(player, 50L);
        player.openInventory(inventory);
        this.getMapInventoryBuild().put(playerID, inventoryBuild);
    }
    
    public final InventoryBuild createInventory(final Player player, final String title, final InventoryType type, final int row) {
        return this.createInventory(player, title, type, row, false, false);
    }
    
    public final InventoryBuild createInventory(final Player player, String title, final InventoryType type, final int row, final boolean isEditable, final boolean isOwned) {
        Inventory inventory;
        if (type.equals((Object)InventoryType.CHEST)) {
            final int size = MathUtil.limitInteger(row, 1, 6) * 9;
            title = (title.isEmpty() ? "Menu" : ((title.length() > 32) ? title.substring(0, 32) : title));
            title = TextUtil.colorful(title);
            inventory = Bukkit.createInventory((InventoryHolder)(isOwned ? player : null), size, title);
        }
        else {
            title = TextUtil.colorful(title);
            inventory = Bukkit.createInventory((InventoryHolder)(isOwned ? player : null), type, title);
        }
        return new InventoryBuild(inventory, isEditable);
    }
    
    public final boolean hasInventoryBuild(final Player player) {
        return this.getMapInventoryBuild().containsKey(player.getUniqueId());
    }
    
    public final boolean hasInventoryCooldown(final Player player) {
        return this.getMapInventoryCooldown().containsKey(player.getUniqueId());
    }
    
    public final boolean hasInventoryCloseCooldown(final Player player) {
        return this.getMapInventoryCloseCooldown().containsKey(player.getUniqueId());
    }
    
    public final void removeInventoryBuild(final Player player) {
        this.getMapInventoryBuild().remove(player.getUniqueId());
    }
    
    public final void removeInventoryCooldown(final Player player) {
        this.getMapInventoryCooldown().remove(player.getUniqueId());
    }
    
    public final void removeInventoryCloseCooldown(final Player player) {
        this.getMapInventoryCloseCooldown().remove(player.getUniqueId());
    }
}
