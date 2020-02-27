// 
// Decompiled by Procyon v0.5.36
// 

package com.praya.myitems.manager.game;

import com.praya.myitems.builder.abs.PassiveEffect;
import com.praya.agarthalib.utility.MathUtil;
import com.praya.myitems.builder.passive.buff.BuffHealthBoost;
import org.bukkit.potion.PotionEffectType;
import com.praya.agarthalib.utility.ServerUtil;
import core.praya.agarthalib.enums.main.VersionNMS;
import com.praya.agarthalib.utility.PlayerUtil;
import java.util.Iterator;
import org.bukkit.entity.Player;
import api.praya.myitems.builder.player.PlayerPassiveEffectCooldown;
import com.praya.myitems.manager.player.PlayerPassiveEffectManager;
import org.bukkit.OfflinePlayer;
import java.util.ArrayList;
import java.util.Collection;
import core.praya.agarthalib.enums.main.RomanNumber;
import org.bukkit.inventory.ItemStack;
import com.praya.agarthalib.utility.EquipmentUtil;
import core.praya.agarthalib.bridge.unity.Bridge;
import core.praya.agarthalib.enums.main.Slot;
import org.bukkit.entity.LivingEntity;
import com.praya.agarthalib.utility.TextUtil;
import api.praya.myitems.builder.passive.PassiveTypeEnum;
import java.util.HashMap;
import com.praya.myitems.config.plugin.MainConfig;
import api.praya.myitems.builder.passive.PassiveEffectEnum;
import com.praya.myitems.MyItems;
import com.praya.myitems.builder.handler.HandlerManager;

public class PassiveEffectManager extends HandlerManager
{
    protected PassiveEffectManager(final MyItems plugin) {
        super(plugin);
    }
    
    public final String getTextPassiveEffect(final PassiveEffectEnum effect, final int grade) {
        final MainConfig mainConfig = MainConfig.getInstance();
        final HashMap<String, String> map = new HashMap<String, String>();
        String format = effect.getType().equals(PassiveTypeEnum.BUFF) ? mainConfig.getPassiveBuffFormat() : mainConfig.getPassiveDebuffFormat();
        map.put("buff", this.getKeyPassiveEffect(effect, grade));
        map.put("buffs", this.getKeyPassiveEffect(effect, grade));
        map.put("debuff", this.getKeyPassiveEffect(effect, grade));
        map.put("debuffs", this.getKeyPassiveEffect(effect, grade));
        format = TextUtil.placeholder((HashMap)map, format, "<", ">");
        return format;
    }
    
    public final int getHighestGradePassiveEffect(final PassiveEffectEnum effect, final LivingEntity livingEntity) {
        int grade = 0;
        Slot[] values;
        for (int length = (values = Slot.values()).length, i = 0; i < length; ++i) {
            final Slot slot = values[i];
            if (this.checkAllowedSlot(slot)) {
                final ItemStack item = Bridge.getBridgeEquipment().getEquipment(livingEntity, slot);
                if (EquipmentUtil.loreCheck(item)) {
                    final int passiveEffectGrade = this.passiveEffectGrade(item, effect);
                    if (passiveEffectGrade > grade) {
                        grade = passiveEffectGrade;
                    }
                }
            }
        }
        return grade;
    }
    
    public final int getTotalGradePassiveEffect(final PassiveEffectEnum effect, final LivingEntity livingEntity) {
        int grade = 0;
        Slot[] values;
        for (int length = (values = Slot.values()).length, i = 0; i < length; ++i) {
            final Slot slot = values[i];
            if (this.checkAllowedSlot(slot)) {
                final ItemStack item = Bridge.getBridgeEquipment().getEquipment(livingEntity, slot);
                if (EquipmentUtil.loreCheck(item)) {
                    grade += this.passiveEffectGrade(item, effect);
                }
            }
        }
        return grade;
    }
    
    public final PassiveEffectEnum getPassiveEffect(final String lore) {
        PassiveEffectEnum[] values;
        for (int length = (values = PassiveEffectEnum.values()).length, i = 0; i < length; ++i) {
            final PassiveEffectEnum passiveEffect = values[i];
            if (lore.contains(this.getKeyPassiveEffect(passiveEffect, true))) {
                return passiveEffect;
            }
        }
        return null;
    }
    
    public final PassiveEffectEnum getPassiveEffect(final ItemStack item, final int line) {
        if (line > 0 && EquipmentUtil.hasLore(item) && line <= EquipmentUtil.getLores(item).size()) {
            final String lore = EquipmentUtil.getLores(item).get(line - 1);
            return this.getPassiveEffect(lore);
        }
        return null;
    }
    
    public final boolean isPassiveEffect(final String lore) {
        return this.getPassiveEffect(lore) != null;
    }
    
    public final boolean isPassiveEffect(final ItemStack item, final int line) {
        return this.getPassiveEffect(item, line) != null;
    }
    
    public final int passiveEffectGrade(final LivingEntity livingEntity, final PassiveEffectEnum effect, final Slot slot) {
        return this.passiveEffectGrade(Bridge.getBridgeEquipment().getEquipment(livingEntity, slot), effect);
    }
    
    public final int passiveEffectGrade(final ItemStack item, final PassiveEffectEnum effect) {
        final int line = this.getLinePassiveEffect(item, effect);
        return (line != -1) ? this.passiveEffectGrade(item, effect, line) : 0;
    }
    
    public final int passiveEffectGrade(final ItemStack item, final PassiveEffectEnum effect, final int line) {
        return this.passiveEffectGrade(effect, EquipmentUtil.getLores(item).get(line - 1));
    }
    
    public final int passiveEffectGrade(final PassiveEffectEnum effect, final String lore) {
        final MainConfig mainConfig = MainConfig.getInstance();
        final String[] textListValue = lore.split(MainConfig.KEY_PASSIVE_EFFECT);
        if (textListValue.length > 1) {
            final String regex = String.valueOf(effect.getType().equals(PassiveTypeEnum.BUFF) ? mainConfig.getPassiveBuffColor() : mainConfig.getPassiveDebuffColor()) + effect.getText() + " ";
            String textValue = textListValue[1];
            if (textValue.contains(regex)) {
                textValue = textValue.replaceAll(regex, "");
                return RomanNumber.romanConvert(textValue);
            }
        }
        return 0;
    }
    
    public final int getLinePassiveEffect(final ItemStack item, final PassiveEffectEnum effect) {
        return EquipmentUtil.loreGetLineKey(item, this.getKeyPassiveEffect(effect, true));
    }
    
    public Collection<PassiveEffectEnum> getPassiveEffects(final ItemStack item) {
        final Collection<PassiveEffectEnum> listEffect = new ArrayList<PassiveEffectEnum>();
        if (EquipmentUtil.loreCheck(item)) {
            PassiveEffectEnum[] values;
            for (int length = (values = PassiveEffectEnum.values()).length, i = 0; i < length; ++i) {
                final PassiveEffectEnum effect = values[i];
                final int line = this.getLinePassiveEffect(item, effect);
                if (line != -1) {
                    listEffect.add(effect);
                }
            }
        }
        return listEffect;
    }
    
    public final void setPassiveEffectCooldown(final PassiveEffectEnum effect, final OfflinePlayer player, final long cooldown) {
        final PlayerPassiveEffectManager playerPassiveEffectManager = this.plugin.getPlayerManager().getPlayerPassiveEffectManager();
        final PlayerPassiveEffectCooldown playerPassiveEffectCooldown = playerPassiveEffectManager.getPlayerPassiveEffectCooldown(player);
        playerPassiveEffectCooldown.setPassiveEffectCooldown(effect, cooldown);
    }
    
    public final boolean isPassiveEffectCooldown(final PassiveEffectEnum effect, final OfflinePlayer player) {
        final PlayerPassiveEffectManager playerPassiveEffectManager = this.plugin.getPlayerManager().getPlayerPassiveEffectManager();
        final PlayerPassiveEffectCooldown playerPassiveEffectCooldown = playerPassiveEffectManager.getPlayerPassiveEffectCooldown(player);
        return playerPassiveEffectCooldown.isPassiveEffectCooldown(effect);
    }
    
    public final void reloadPassiveEffect(final Player player, final ItemStack item, final boolean sum) {
        this.reloadPassiveEffect(player, this.getPassiveEffects(item), sum);
    }
    
    public final void reloadPassiveEffect(final Player player, final Collection<PassiveEffectEnum> effects, final boolean sum) {
        for (final PassiveEffectEnum effect : effects) {
            this.runPassiveEffect(player, effect, true, sum);
        }
    }
    
    public final void loadPassiveEffect(final boolean sum) {
        for (final Player player : PlayerUtil.getOnlinePlayers()) {
            this.runAllPassiveEffect(player, sum);
        }
    }
    
    public final void runAllPassiveEffect(final Player player, final boolean sum) {
        this.runAllPassiveEffect(player, false, sum);
    }
    
    public final void runAllPassiveEffect(final Player player, final boolean reset, final boolean sum) {
        PassiveEffectEnum[] values;
        for (int length = (values = PassiveEffectEnum.values()).length, i = 0; i < length; ++i) {
            final PassiveEffectEnum effect = values[i];
            this.runPassiveEffect(player, effect, reset, sum);
        }
    }
    
    public final void runPassiveEffect(final Player player, final PassiveEffectEnum effect, final boolean sum) {
        this.runPassiveEffect(player, effect, false, sum);
    }
    
    public final void runPassiveEffect(final Player player, final PassiveEffectEnum effect, final boolean reset, final boolean sum) {
        final int grade = sum ? this.getTotalGradePassiveEffect(effect, (LivingEntity)player) : this.getHighestGradePassiveEffect(effect, (LivingEntity)player);
        this.applyPassiveEffect(player, effect, grade, reset);
    }
    
    public final void applyPassiveEffect(final Player player, final PassiveEffectEnum effect, final int grade) {
        this.applyPassiveEffect(player, effect, grade, false);
    }
    
    public final void applyPassiveEffect(final Player player, final PassiveEffectEnum effect, int grade, final boolean reset) {
        if (!ServerUtil.isCompatible(VersionNMS.V1_9_R2) && effect.equals(PassiveEffectEnum.LUCK)) {
            return;
        }
        if (reset) {
            final PotionEffectType potion = effect.getPotion();
            if (potion != null) {
                if (potion.equals((Object)PotionEffectType.HEALTH_BOOST)) {
                    final BuffHealthBoost buffHealth = new BuffHealthBoost();
                    buffHealth.reset(player);
                }
                else {
                    player.removePotionEffect(potion);
                }
            }
        }
        grade = MathUtil.limitInteger(grade, grade, effect.getMaxGrade());
        if (grade != 0) {
            final PassiveEffect passiveEffect = PassiveEffect.getPassiveEffect(effect, grade);
            passiveEffect.cast(player);
        }
    }
    
    public final String getKeyPassiveEffect(final PassiveEffectEnum effect, final boolean justCheck) {
        return this.getKeyPassiveEffect(effect, 1, justCheck);
    }
    
    public final String getKeyPassiveEffect(final PassiveEffectEnum effect, final int grade) {
        return this.getKeyPassiveEffect(effect, grade, false);
    }
    
    public final String getKeyPassiveEffect(final PassiveEffectEnum effect, final int grade, final boolean justCheck) {
        final MainConfig mainConfig = MainConfig.getInstance();
        final String key = MainConfig.KEY_PASSIVE_EFFECT;
        final String color = effect.getType().equals(PassiveTypeEnum.BUFF) ? mainConfig.getPassiveBuffColor() : mainConfig.getPassiveDebuffColor();
        final String text = effect.getText();
        final String roman = RomanNumber.getRomanNumber(grade);
        return justCheck ? (String.valueOf(key) + color + text) : (String.valueOf(key) + color + text + " " + roman + key + color);
    }
    
    public final boolean checkAllowedSlot(final Slot slot) {
        final MainConfig mainConfig = MainConfig.getInstance();
        return mainConfig.isPassiveEnableHand() || (!slot.equals((Object)Slot.MAINHAND) && !slot.equals((Object)Slot.OFFHAND));
    }
}
