// 
// Decompiled by Procyon v0.5.36
// 

package com.praya.myitems.manager.game;

import com.praya.agarthalib.utility.TextUtil;
import com.praya.agarthalib.utility.MathUtil;
import java.util.HashMap;
import com.praya.myitems.config.plugin.MainConfig;
import api.praya.myitems.builder.power.PowerClickEnum;
import api.praya.myitems.builder.power.PowerSpecialProperties;
import api.praya.myitems.builder.power.PowerSpecialEnum;
import java.util.Collection;
import com.praya.myitems.MyItems;
import com.praya.myitems.config.game.PowerSpecialConfig;
import com.praya.myitems.builder.handler.HandlerManager;

public class PowerSpecialManager extends HandlerManager
{
    private final PowerSpecialConfig powerSpecialConfig;
    
    protected PowerSpecialManager(final MyItems plugin) {
        super(plugin);
        this.powerSpecialConfig = new PowerSpecialConfig(plugin);
    }
    
    public final PowerSpecialConfig getPowerSpecialConfig() {
        return this.powerSpecialConfig;
    }
    
    public final Collection<PowerSpecialEnum> getPowerSpecialIDs() {
        return this.getPowerSpecialConfig().getPowerSpecialIDs();
    }
    
    public final Collection<PowerSpecialProperties> getPowerSpecialPropertyBuilds() {
        return this.getPowerSpecialConfig().getPowerSpecialPropertyBuilds();
    }
    
    public final PowerSpecialProperties getPowerSpecialProperties(final PowerSpecialEnum id) {
        return this.getPowerSpecialConfig().getPowerSpecialProperties(id);
    }
    
    public final boolean isPowerSpecialExists(final PowerSpecialEnum id) {
        return this.getPowerSpecialProperties(id) != null;
    }
    
    public final String getTextPowerSpecial(final PowerClickEnum click, final PowerSpecialEnum special, double cooldown) {
        final PowerManager powerManager = this.plugin.getGameManager().getPowerManager();
        final MainConfig mainConfig = MainConfig.getInstance();
        final HashMap<String, String> map = new HashMap<String, String>();
        String format = mainConfig.getPowerFormat();
        cooldown = MathUtil.roundNumber(cooldown, 1);
        map.put("click", powerManager.getKeyClick(click));
        map.put("type", this.getKeySpecial(special));
        map.put("cooldown", powerManager.getKeyCooldown(cooldown));
        format = TextUtil.placeholder((HashMap)map, format, "<", ">");
        return format;
    }
    
    public final PowerSpecialEnum getSpecial(final String lore) {
        final MainConfig mainConfig = MainConfig.getInstance();
        final String[] loreCheck = lore.split(MainConfig.KEY_SPECIAL);
        if (loreCheck.length > 1) {
            final String colorPowerType = mainConfig.getPowerColorType();
            final String loreStep = loreCheck[1].replaceFirst(colorPowerType, "");
            return PowerSpecialEnum.getSpecialByLore(loreStep);
        }
        return null;
    }
    
    public final String getKeySpecial(final PowerSpecialEnum special) {
        return this.getKeySpecial(special, false);
    }
    
    public final String getKeySpecial(final PowerSpecialEnum special, final boolean justCheck) {
        final MainConfig mainConfig = MainConfig.getInstance();
        final String key = MainConfig.KEY_SPECIAL;
        final String color = mainConfig.getPowerColorType();
        final String text = special.getText();
        return justCheck ? (String.valueOf(key) + color + text) : (String.valueOf(key) + color + text + key + color);
    }
}
