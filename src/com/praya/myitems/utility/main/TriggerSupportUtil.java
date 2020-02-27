// 
// Decompiled by Procyon v0.5.36
// 

package com.praya.myitems.utility.main;

import api.praya.agarthalib.builder.support.SupportLifeEssence;
import api.praya.agarthalib.builder.support.SupportCombatStamina;
import api.praya.agarthalib.manager.plugin.SupportManagerAPI;
import org.bukkit.OfflinePlayer;
import com.praya.agarthalib.utility.PlayerUtil;
import com.praya.myitems.config.plugin.MainConfig;
import api.praya.agarthalib.main.AgarthaLibAPI;
import org.bukkit.entity.Player;

public class TriggerSupportUtil
{
    public static final void updateSupport(final Player player) {
        final AgarthaLibAPI agarthaLibAPI = AgarthaLibAPI.getInstance();
        final SupportManagerAPI supportManagerAPI = agarthaLibAPI.getPluginManagerAPI().getSupportManager();
        final MainConfig mainConfig = MainConfig.getInstance();
        final boolean enableMaxHealth = mainConfig.isStatsEnableMaxHealth();
        if (enableMaxHealth) {
            PlayerUtil.setMaxHealth(player);
        }
        if (supportManagerAPI.isSupportCombatStamina()) {
            final SupportCombatStamina supportCombatStamina = supportManagerAPI.getSupportCombatStamina();
            supportCombatStamina.updateMaxStamina((OfflinePlayer)player);
            supportCombatStamina.updateStaminaRegen((OfflinePlayer)player);
        }
        if (supportManagerAPI.isSupportLifeEssence()) {
            final SupportLifeEssence supportLifeEssence = supportManagerAPI.getSupportLifeEssence();
            supportLifeEssence.updateHealthRegen((OfflinePlayer)player);
        }
    }
}
