// 
// Decompiled by Procyon v0.5.36
// 

package com.praya.myitems.manager.game;

import com.praya.agarthalib.utility.MathUtil;
import java.util.HashMap;
import api.praya.myitems.builder.power.PowerClickEnum;
import com.praya.myitems.config.plugin.MainConfig;
import com.praya.agarthalib.utility.TextUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
import api.praya.myitems.builder.power.PowerCommandProperties;
import java.util.Collection;
import com.praya.myitems.MyItems;
import com.praya.myitems.config.game.PowerCommandConfig;
import com.praya.myitems.builder.handler.HandlerManager;

public class PowerCommandManager extends HandlerManager
{
    private final PowerCommandConfig powerCommandConfig;
    
    protected PowerCommandManager(final MyItems plugin) {
        super(plugin);
        this.powerCommandConfig = new PowerCommandConfig(plugin);
    }
    
    public final PowerCommandConfig getPowerCommandConfig() {
        return this.powerCommandConfig;
    }
    
    public final Collection<String> getPowerCommandIDs() {
        return this.getPowerCommandConfig().getPowerCommandIDs();
    }
    
    public final Collection<PowerCommandProperties> getPowerCommandPropertyBuilds() {
        return this.getPowerCommandConfig().getPowerCommandPropertyBuilds();
    }
    
    public final PowerCommandProperties getPowerCommandProperties(final String id) {
        return this.getPowerCommandConfig().getPowerCommandProperties(id);
    }
    
    public final boolean isPowerCommandExists(final String id) {
        return this.getPowerCommandProperties(id) != null;
    }
    
    public final String getCommandKey(final String powerCommand) {
        for (final String key : this.getPowerCommandIDs()) {
            if (key.equalsIgnoreCase(powerCommand)) {
                return key;
            }
        }
        return null;
    }
    
    @Deprecated
    public final String getCommandLore(final String powerCommand) {
        final PowerCommandProperties build = this.getPowerCommandProperties(powerCommand);
        return (build != null) ? build.getLore() : null;
    }
    
    @Deprecated
    public final List<String> getCommands(final String powerCommand) {
        final PowerCommandProperties build = this.getPowerCommandProperties(powerCommand);
        return (build != null) ? build.getCommands() : new ArrayList<String>();
    }
    
    public final String getCommandKeyByLore(final String lore) {
        final String coloredLore = TextUtil.colorful(lore);
        for (final String key : this.getPowerCommandIDs()) {
            final PowerCommandProperties powerCommandProperties = this.getPowerCommandProperties(key);
            if (powerCommandProperties.getKeyLore().equalsIgnoreCase(coloredLore)) {
                return key;
            }
        }
        return null;
    }
    
    public final String getCommand(final String lore) {
        final MainConfig mainConfig = MainConfig.getInstance();
        final String[] loreCheck = lore.split(MainConfig.KEY_COMMAND);
        if (loreCheck.length > 1) {
            final String colorPowerType = mainConfig.getPowerColorType();
            final String loreStep = loreCheck[1].replaceFirst(colorPowerType, "");
            return this.getCommandKeyByLore(loreStep);
        }
        return null;
    }
    
    public final String getTextPowerCommand(final PowerClickEnum click, final String keyCommand, double cooldown) {
        final PowerManager powerManager = this.plugin.getGameManager().getPowerManager();
        final MainConfig mainConfig = MainConfig.getInstance();
        final HashMap<String, String> map = new HashMap<String, String>();
        String format = mainConfig.getPowerFormat();
        cooldown = MathUtil.roundNumber(cooldown, 1);
        map.put("click", powerManager.getKeyClick(click));
        map.put("type", this.getKeyCommand(keyCommand));
        map.put("cooldown", powerManager.getKeyCooldown(cooldown));
        format = TextUtil.placeholder((HashMap)map, format, "<", ">");
        return format;
    }
    
    public final String getKeyCommand(final String keyCommand) {
        return this.getKeyCommand(keyCommand, false);
    }
    
    public final String getKeyCommand(final String keyCommand, final boolean justCheck) {
        final MainConfig mainConfig = MainConfig.getInstance();
        final String key = MainConfig.KEY_COMMAND;
        final String color = mainConfig.getPowerColorType();
        final String keylore = this.getCommandLore(keyCommand);
        return justCheck ? (String.valueOf(key) + color + keylore) : (String.valueOf(key) + color + keylore + key + color);
    }
}
