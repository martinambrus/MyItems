// 
// Decompiled by Procyon v0.5.36
// 

package com.praya.myitems.manager.game;

import com.praya.myitems.utility.main.ProjectileUtil;
import com.praya.agarthalib.utility.TextUtil;
import com.praya.agarthalib.utility.MathUtil;
import java.util.HashMap;
import com.praya.myitems.config.plugin.MainConfig;
import core.praya.agarthalib.enums.branch.ProjectileEnum;
import api.praya.myitems.builder.power.PowerClickEnum;
import com.praya.myitems.MyItems;
import com.praya.myitems.builder.handler.HandlerManager;

public class PowerShootManager extends HandlerManager
{
    protected PowerShootManager(final MyItems plugin) {
        super(plugin);
    }
    
    public final String getTextPowerShoot(final PowerClickEnum click, final ProjectileEnum projectile, double cooldown) {
        final PowerManager powerManager = this.plugin.getGameManager().getPowerManager();
        final MainConfig mainConfig = MainConfig.getInstance();
        final HashMap<String, String> map = new HashMap<String, String>();
        String format = mainConfig.getPowerFormat();
        cooldown = MathUtil.roundNumber(cooldown, 1);
        map.put("click", powerManager.getKeyClick(click));
        map.put("type", this.getKeyShoot(projectile));
        map.put("cooldown", powerManager.getKeyCooldown(cooldown));
        format = TextUtil.placeholder((HashMap)map, format, "<", ">");
        return format;
    }
    
    public final ProjectileEnum getShoot(final String lore) {
        final MainConfig mainConfig = MainConfig.getInstance();
        final String[] loreCheck = lore.split(MainConfig.KEY_SHOOT);
        if (loreCheck.length > 1) {
            final String colorPowerType = mainConfig.getPowerColorType();
            final String loreStep = loreCheck[1].replaceFirst(colorPowerType, "");
            return ProjectileUtil.getProjectileByLore(loreStep);
        }
        return null;
    }
    
    public final String getKeyShoot(final ProjectileEnum projectile) {
        return this.getKeyShoot(projectile, false);
    }
    
    public final String getKeyShoot(final ProjectileEnum projectile, final boolean justCheck) {
        final MainConfig mainConfig = MainConfig.getInstance();
        final String key = MainConfig.KEY_SHOOT;
        final String color = mainConfig.getPowerColorType();
        final String text = ProjectileUtil.getText(projectile);
        return justCheck ? (String.valueOf(key) + color + text) : (String.valueOf(key) + color + text + key + color);
    }
}
