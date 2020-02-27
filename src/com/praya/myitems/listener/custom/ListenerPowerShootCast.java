// 
// Decompiled by Procyon v0.5.36
// 

package com.praya.myitems.listener.custom;

import org.bukkit.event.EventPriority;
import org.bukkit.event.EventHandler;
import api.praya.myitems.builder.player.PlayerPowerCooldown;
import core.praya.agarthalib.enums.branch.ProjectileEnum;
import org.bukkit.inventory.ItemStack;
import org.bukkit.entity.Player;
import com.praya.myitems.manager.player.PlayerPowerManager;
import com.praya.myitems.manager.game.LoreStatsManager;
import com.praya.myitems.manager.game.GameManager;
import core.praya.agarthalib.enums.main.Slot;
import org.bukkit.entity.LivingEntity;
import com.praya.agarthalib.utility.CombatUtil;
import org.bukkit.OfflinePlayer;
import api.praya.myitems.builder.lorestats.LoreStatsOption;
import api.praya.myitems.builder.lorestats.LoreStatsEnum;
import com.praya.agarthalib.utility.MathUtil;
import api.praya.myitems.builder.event.PowerShootCastEvent;
import com.praya.myitems.MyItems;
import org.bukkit.event.Listener;
import com.praya.myitems.builder.handler.HandlerEvent;

public class ListenerPowerShootCast extends HandlerEvent implements Listener
{
    public ListenerPowerShootCast(final MyItems plugin) {
        super(plugin);
    }
    
    @EventHandler(priority = EventPriority.MONITOR)
    public void eventPowerShootCast(final PowerShootCastEvent event) {
        final GameManager gameManager = this.plugin.getGameManager();
        final LoreStatsManager statsManager = gameManager.getStatsManager();
        final PlayerPowerManager playerPowerManager = this.plugin.getPlayerManager().getPlayerPowerManager();
        if (!event.isCancelled()) {
            final Player player = event.getPlayer();
            final ItemStack item = event.getItem();
            final ProjectileEnum projectile = event.getProjectile();
            final double cooldown = event.getCooldown();
            final double speed = event.getSpeed();
            final long timeCooldown = MathUtil.convertSecondsToMilis(cooldown);
            final int durability = (int)statsManager.getLoreValue(item, LoreStatsEnum.DURABILITY, LoreStatsOption.CURRENT);
            final PlayerPowerCooldown powerCooldown = playerPowerManager.getPlayerPowerCooldown((OfflinePlayer)player);
            CombatUtil.launchProjectile((LivingEntity)player, projectile, speed, false, true);
            if (timeCooldown > 0L) {
                powerCooldown.setPowerShootCooldown(projectile, timeCooldown);
            }
            if (!statsManager.durability((LivingEntity)player, item, durability, true)) {
                statsManager.sendBrokenCode((LivingEntity)player, Slot.MAINHAND);
            }
        }
    }
}
