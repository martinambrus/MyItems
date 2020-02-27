// 
// Decompiled by Procyon v0.5.36
// 

package com.praya.myitems.builder.task;

import org.bukkit.Location;
import java.util.Set;
import api.praya.myitems.builder.player.PlayerPowerCooldown;
import java.util.Iterator;
import com.praya.myitems.manager.player.PlayerPowerManager;
import com.praya.myitems.manager.player.PlayerManager;
import com.praya.myitems.manager.plugin.LanguageManager;
import com.praya.myitems.manager.plugin.PluginManager;
import api.praya.myitems.builder.power.PowerSpecialEnum;
import com.praya.myitems.utility.main.ProjectileUtil;
import core.praya.agarthalib.enums.branch.ProjectileEnum;
import core.praya.agarthalib.enums.branch.SoundEnum;
import core.praya.agarthalib.bridge.unity.Bridge;
import com.praya.agarthalib.utility.TextUtil;
import org.bukkit.entity.LivingEntity;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import com.praya.agarthalib.utility.PlayerUtil;
import com.praya.myitems.MyItems;
import java.util.HashMap;
import com.praya.myitems.builder.handler.HandlerTask;

public class TaskPowerCooldown extends HandlerTask implements Runnable
{
    private final HashMap<String, String> mapPlaceholder;
    
    public TaskPowerCooldown(final MyItems plugin) {
        super(plugin);
        this.mapPlaceholder = new HashMap<String, String>();
    }
    
    @Override
    public void run() {
        final PluginManager pluginManager = this.plugin.getPluginManager();
        final LanguageManager lang = pluginManager.getLanguageManager();
        final PlayerManager playerManager = this.plugin.getPlayerManager();
        final PlayerPowerManager playerPowerManager = playerManager.getPlayerPowerManager();
        for (final Player player : PlayerUtil.getOnlinePlayers()) {
            final PlayerPowerCooldown playerPowerCooldown = playerPowerManager.getPlayerPowerCooldown((OfflinePlayer)player);
            final Set<String> cooldownCommandKeySet = playerPowerCooldown.getCooldownCommandKeySet();
            final Set<ProjectileEnum> cooldownProjectileKeySet = playerPowerCooldown.getCooldownProjectileKeySet();
            final Set<PowerSpecialEnum> cooldownSpecialKeySet = playerPowerCooldown.getCooldownSpecialKeySet();
            if (!cooldownCommandKeySet.isEmpty()) {
                final Object[] arrayObjectCommand = cooldownCommandKeySet.toArray();
                Object[] array;
                for (int length = (array = arrayObjectCommand).length, i = 0; i < length; ++i) {
                    final Object objectCommand = array[i];
                    final String keyCommand = (String)objectCommand;
                    if (!playerPowerCooldown.isPowerCommandCooldown(keyCommand)) {
                        final Location location = player.getLocation();
                        String message = lang.getText((LivingEntity)player, "Power_Command_Refresh");
                        this.mapPlaceholder.clear();
                        this.mapPlaceholder.put("power", keyCommand);
                        message = TextUtil.placeholder((HashMap)this.mapPlaceholder, message);
                        playerPowerCooldown.removePowerCommandCooldown(keyCommand);
                        Bridge.getBridgeSound().playSound(player, location, SoundEnum.BLOCK_WOOD_BUTTON_CLICK_ON, 1.0f, 1.0f);
                        PlayerUtil.sendMessage(player, message);
                    }
                }
            }
            if (!cooldownProjectileKeySet.isEmpty()) {
                final Object[] arrayObjectProjectile = cooldownProjectileKeySet.toArray();
                Object[] array2;
                for (int length2 = (array2 = arrayObjectProjectile).length, j = 0; j < length2; ++j) {
                    final Object objectProjectile = array2[j];
                    final ProjectileEnum keyProjectile = (ProjectileEnum)objectProjectile;
                    if (!playerPowerCooldown.isPowerShootCooldown(keyProjectile)) {
                        final Location location = player.getLocation();
                        String message = lang.getText((LivingEntity)player, "Power_Shoot_Refresh");
                        this.mapPlaceholder.clear();
                        this.mapPlaceholder.put("power", ProjectileUtil.getText(keyProjectile));
                        message = TextUtil.placeholder((HashMap)this.mapPlaceholder, message);
                        playerPowerCooldown.removePowerShootCooldown(keyProjectile);
                        Bridge.getBridgeSound().playSound(player, location, SoundEnum.BLOCK_WOOD_BUTTON_CLICK_ON, 1.0f, 1.0f);
                        PlayerUtil.sendMessage(player, message);
                    }
                }
            }
            if (!cooldownSpecialKeySet.isEmpty()) {
                final Object[] arrayObjectSpecial = cooldownSpecialKeySet.toArray();
                Object[] array3;
                for (int length3 = (array3 = arrayObjectSpecial).length, k = 0; k < length3; ++k) {
                    final Object objectSpecial = array3[k];
                    final PowerSpecialEnum keySpecial = (PowerSpecialEnum)objectSpecial;
                    if (!playerPowerCooldown.isPowerSpecialCooldown(keySpecial)) {
                        final Location location = player.getLocation();
                        String message = lang.getText((LivingEntity)player, "Power_Special_Refresh");
                        this.mapPlaceholder.clear();
                        this.mapPlaceholder.put("power", keySpecial.getText());
                        message = TextUtil.placeholder((HashMap)this.mapPlaceholder, message);
                        playerPowerCooldown.removePowerSpecialCooldown(keySpecial);
                        Bridge.getBridgeSound().playSound(player, location, SoundEnum.BLOCK_WOOD_BUTTON_CLICK_ON, 1.0f, 1.0f);
                        PlayerUtil.sendMessage(player, message);
                    }
                }
            }
        }
    }
}
