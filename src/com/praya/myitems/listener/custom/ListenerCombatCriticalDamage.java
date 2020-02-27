// 
// Decompiled by Procyon v0.5.36
// 

package com.praya.myitems.listener.custom;

import org.bukkit.event.EventPriority;
import org.bukkit.event.EventHandler;
import org.bukkit.entity.Player;
import org.bukkit.Location;
import com.praya.myitems.manager.plugin.LanguageManager;
import org.bukkit.command.CommandSender;
import com.praya.agarthalib.utility.SenderUtil;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Entity;
import com.praya.agarthalib.utility.EntityUtil;
import core.praya.agarthalib.enums.branch.SoundEnum;
import java.util.Collection;
import core.praya.agarthalib.enums.branch.ParticleEnum;
import core.praya.agarthalib.bridge.unity.Bridge;
import com.praya.agarthalib.utility.PlayerUtil;
import com.praya.myitems.config.plugin.MainConfig;
import api.praya.myitems.builder.event.CombatCriticalDamageEvent;
import com.praya.myitems.MyItems;
import org.bukkit.event.Listener;
import com.praya.myitems.builder.handler.HandlerEvent;

public class ListenerCombatCriticalDamage extends HandlerEvent implements Listener
{
    public ListenerCombatCriticalDamage(final MyItems plugin) {
        super(plugin);
    }
    
    @EventHandler(priority = EventPriority.MONITOR)
    public void eventCombatCriticalDamage(final CombatCriticalDamageEvent event) {
        final LanguageManager lang = this.plugin.getPluginManager().getLanguageManager();
        final MainConfig mainConfig = MainConfig.getInstance();
        if (!event.isCancelled()) {
            final LivingEntity attacker = event.getAttacker();
            final LivingEntity victims = event.getVictims();
            final String criticalAttackType = mainConfig.getModifierCriticalAttackType();
            if (criticalAttackType != null) {
                if (criticalAttackType.equalsIgnoreCase("Effect")) {
                    final Location loc = victims.getEyeLocation();
                    final Collection<Player> players = (Collection<Player>)PlayerUtil.getNearbyPlayers(loc, mainConfig.getEffectRange());
                    Bridge.getBridgeParticle().playParticle((Collection)players, ParticleEnum.EXPLOSION_LARGE, loc, 3, 0.1, 0.1, 0.1, 0.5);
                    Bridge.getBridgeSound().playSound((Collection)players, loc, SoundEnum.ENTITY_GENERIC_EXPLODE, 0.5f, 1.0f);
                }
                else if (criticalAttackType.equalsIgnoreCase("Messages") && EntityUtil.isPlayer((Entity)attacker)) {
                    final Player player = EntityUtil.parsePlayer((Entity)attacker);
                    final String message = lang.getText((LivingEntity)player, "Attack_Critical");
                    SenderUtil.sendMessage((CommandSender)player, message);
                }
            }
        }
    }
}
