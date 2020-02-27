// 
// Decompiled by Procyon v0.5.36
// 

package com.praya.myitems.manager.game;

import api.praya.myitems.builder.power.PowerEnum;
import com.praya.agarthalib.utility.MathUtil;
import com.praya.myitems.config.plugin.MainConfig;
import com.praya.agarthalib.utility.EquipmentUtil;
import api.praya.myitems.builder.power.PowerClickEnum;
import org.bukkit.inventory.ItemStack;
import com.praya.myitems.MyItems;
import com.praya.myitems.builder.handler.HandlerManager;

public class PowerManager extends HandlerManager
{
    private final PowerCommandManager powerCommandManager;
    private final PowerShootManager powerShootManager;
    private final PowerSpecialManager powerSpecialManager;
    
    protected PowerManager(final MyItems plugin) {
        super(plugin);
        this.powerCommandManager = new PowerCommandManager(plugin);
        this.powerShootManager = new PowerShootManager(plugin);
        this.powerSpecialManager = new PowerSpecialManager(plugin);
    }
    
    public final PowerCommandManager getPowerCommandManager() {
        return this.powerCommandManager;
    }
    
    public final PowerShootManager getPowerShootManager() {
        return this.powerShootManager;
    }
    
    public final PowerSpecialManager getPowerSpecialManager() {
        return this.powerSpecialManager;
    }
    
    public final int getLineClick(final ItemStack item, final PowerClickEnum click) {
        return EquipmentUtil.hasLore(item) ? EquipmentUtil.loreGetLineKey(item, this.getKeyClick(click)) : -1;
    }
    
    public final PowerClickEnum getClick(final String lore) {
        PowerClickEnum[] values;
        for (int length = (values = PowerClickEnum.values()).length, i = 0; i < length; ++i) {
            final PowerClickEnum click = values[i];
            if (lore.contains(this.getKeyClick(click, true))) {
                return click;
            }
        }
        return null;
    }
    
    public final double getCooldown(final String lore) {
        final MainConfig mainConfig = MainConfig.getInstance();
        final String[] loreCheck = lore.split(MainConfig.KEY_COOLDOWN);
        if (loreCheck.length > 1) {
            final String colorPowerCooldown = mainConfig.getPowerColorCooldown();
            final String loreStep = loreCheck[1].replaceFirst(colorPowerCooldown, "");
            if (MathUtil.isNumber(loreStep)) {
                return MathUtil.parseDouble(loreStep);
            }
        }
        return 0.0;
    }
    
    public final PowerEnum getPower(final ItemStack item, final PowerClickEnum click) {
        final int line = this.getLineClick(item, click);
        if (line != -1) {
            final String lore = EquipmentUtil.getLores(item).get(line - 1);
            return this.getPower(lore);
        }
        return null;
    }
    
    public final PowerEnum getPower(final String lore) {
        if (lore.contains(MainConfig.KEY_COMMAND)) {
            return PowerEnum.COMMAND;
        }
        if (lore.contains(MainConfig.KEY_SHOOT)) {
            return PowerEnum.SHOOT;
        }
        if (lore.contains(MainConfig.KEY_SPECIAL)) {
            return PowerEnum.SPECIAL;
        }
        return null;
    }
    
    public final boolean isPower(final String lore) {
        return this.getPower(lore) != null;
    }
    
    public final boolean hasPower(final ItemStack item, final PowerClickEnum click) {
        if (EquipmentUtil.hasLore(item)) {
            final String lores = EquipmentUtil.getLores(item).toString();
            final String key = this.getKeyClick(click, true);
            if (lores.contains(key)) {
                return true;
            }
        }
        return false;
    }
    
    public final String getKeyClick(final PowerClickEnum click) {
        return this.getKeyClick(click, false);
    }
    
    public final String getKeyClick(final PowerClickEnum click, final boolean justCheck) {
        final MainConfig mainConfig = MainConfig.getInstance();
        final String key = MainConfig.KEY_CLICK;
        final String color = mainConfig.getPowerColorClick();
        final String text = click.getText();
        return justCheck ? (String.valueOf(key) + color + text) : (String.valueOf(key) + color + text + key + color);
    }
    
    public final String getKeyCooldown(final double cooldown) {
        return this.getKeyCooldown(cooldown, false);
    }
    
    public final String getKeyCooldown(final double cooldown, final boolean justCheck) {
        final MainConfig mainConfig = MainConfig.getInstance();
        final String key = MainConfig.KEY_COOLDOWN;
        final String color = mainConfig.getPowerColorCooldown();
        return justCheck ? (String.valueOf(key) + color) : (String.valueOf(key) + color + cooldown + key + color);
    }
}
