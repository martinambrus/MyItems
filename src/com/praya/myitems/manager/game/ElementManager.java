// 
// Decompiled by Procyon v0.5.36
// 

package com.praya.myitems.manager.game;

import api.praya.myitems.builder.element.ElementBoost;
import api.praya.myitems.builder.element.ElementBoostStats;
import api.praya.myitems.builder.element.ElementPotion;
import com.praya.agarthalib.utility.CombatUtil;
import api.praya.myitems.builder.potion.PotionProperties;
import org.bukkit.potion.PotionEffectType;
import com.praya.agarthalib.utility.MathUtil;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import com.praya.agarthalib.utility.EquipmentUtil;
import core.praya.agarthalib.bridge.unity.Bridge;
import core.praya.agarthalib.enums.main.Slot;
import core.praya.agarthalib.enums.main.SlotType;
import org.bukkit.entity.LivingEntity;
import java.util.Iterator;
import com.praya.agarthalib.utility.TextUtil;
import java.util.HashMap;
import com.praya.myitems.config.plugin.MainConfig;
import api.praya.myitems.builder.element.Element;
import java.util.Collection;
import com.praya.myitems.MyItems;
import com.praya.myitems.config.game.ElementConfig;
import com.praya.myitems.builder.handler.HandlerManager;

public class ElementManager extends HandlerManager
{
    private final ElementConfig elementConfig;
    
    protected ElementManager(final MyItems plugin) {
        super(plugin);
        this.elementConfig = new ElementConfig(plugin);
    }
    
    public final ElementConfig getElementConfig() {
        return this.elementConfig;
    }
    
    public final Collection<String> getElements() {
        return this.getElementConfig().getElements();
    }
    
    public final Collection<Element> getElementBuilds() {
        return this.getElementConfig().getElementBuilds();
    }
    
    public final Element getElementBuild(final String element) {
        return this.getElementConfig().getElementBuild(element);
    }
    
    public final String getTextElement(final String element, final double value) {
        final MainConfig mainConfig = MainConfig.getInstance();
        final HashMap<String, String> map = new HashMap<String, String>();
        String format = mainConfig.getElementFormat();
        map.put("element", this.getKeyElement(element));
        map.put("value", this.getKeyValue(value));
        format = TextUtil.placeholder((HashMap)map, format, "<", ">");
        return format;
    }
    
    public final String getElement(final String lore) {
        final MainConfig mainConfig = MainConfig.getInstance();
        final String[] part = lore.split(MainConfig.KEY_ELEMENT);
        if (part.length > 1) {
            final String loreElement = part[1].replaceAll(mainConfig.getElementColor(), "");
            for (final String key : this.getElementConfig().getElements()) {
                if (this.getElementConfig().getElementBuild(key).getKeyLore().equalsIgnoreCase(loreElement)) {
                    return key;
                }
            }
        }
        return null;
    }
    
    public final String getRawElement(final String element) {
        for (final String key : this.getElementConfig().getElements()) {
            if (key.equalsIgnoreCase(element)) {
                return key;
            }
        }
        return null;
    }
    
    public final boolean isExists(final String element) {
        for (final String key : this.getElementConfig().getElements()) {
            if (key.equalsIgnoreCase(element)) {
                return true;
            }
        }
        return false;
    }
    
    public final boolean isElement(final String lore) {
        return this.getElement(lore) != null;
    }
    
    public final HashMap<String, Double> getMapElement(final LivingEntity livingEntity) {
        return this.getMapElement(livingEntity, SlotType.UNIVERSAL);
    }
    
    public final HashMap<String, Double> getMapElement(final LivingEntity livingEntity, final SlotType slotType) {
        return this.getMapElement(livingEntity, slotType, true);
    }
    
    public final HashMap<String, Double> getMapElement(final LivingEntity livingEntity, final SlotType slotType, final boolean checkDurability) {
        final HashMap<String, Double> map = new HashMap<String, Double>();
        Slot[] values;
        for (int length = (values = Slot.values()).length, i = 0; i < length; ++i) {
            final Slot slot = values[i];
            final ItemStack item = Bridge.getBridgeEquipment().getEquipment(livingEntity, slot);
            if (EquipmentUtil.isSolid(item)) {
                final Material itemMaterial = item.getType();
                final SlotType itemSlotType = SlotType.getSlotType(itemMaterial);
                if (slotType.equals((Object)SlotType.UNIVERSAL) || slotType.equals((Object)itemSlotType)) {
                    final HashMap<String, Double> subMap = this.getMapElement(item, checkDurability);
                    for (final String key : subMap.keySet()) {
                        final double value = subMap.get(key);
                        map.put(key, map.containsKey(key) ? (map.get(key) + value) : value);
                    }
                }
            }
        }
        return map;
    }
    
    public final HashMap<String, Double> getMapElement(final ItemStack item, final boolean checkDurability) {
        final GameManager gameManager = this.plugin.getGameManager();
        final LoreStatsManager statsManager = gameManager.getStatsManager();
        final HashMap<String, Double> map = new HashMap<String, Double>();
        if (EquipmentUtil.loreCheck(item) && (!checkDurability || statsManager.checkDurability(item))) {
            for (final String key : this.getElementConfig().getElements()) {
                final double value = this.getElementValue(item, key);
                if (value != 0.0) {
                    map.put(key, value);
                }
            }
        }
        return map;
    }
    
    public final HashMap<String, Double> getElementCalculation(final HashMap<String, Double> elementAttacker, final HashMap<String, Double> elementVictims) {
        final HashMap<String, Double> map = new HashMap<String, Double>();
        for (final String key : elementAttacker.keySet()) {
            if (this.isExists(key)) {
                final double valueAttack = elementAttacker.get(key);
                final double minValue = MathUtil.negative(valueAttack);
                double resistance = 0.0;
                for (final String resist : elementVictims.keySet()) {
                    if (this.isExists(resist)) {
                        final Element build = this.getElementBuild(resist);
                        final double scale = this.getScaleResistance(build, key);
                        final double valueDefense = elementVictims.get(resist);
                        resistance += scale * valueDefense;
                    }
                }
                final double value = valueAttack - resistance;
                map.put(key, MathUtil.limitDouble(value, minValue, value));
            }
        }
        return map;
    }
    
    public final void applyElementPotion(final LivingEntity attacker, final LivingEntity victims, final HashMap<String, Double> mapElement) {
        for (final String key : mapElement.keySet()) {
            if (this.isExists(key)) {
                final double value = mapElement.get(key);
                if (value <= 0.0) {
                    continue;
                }
                final ElementPotion build = this.getElementPotionBuild(key);
                for (final PotionEffectType potion : build.getPotionAttacker().keySet()) {
                    final PotionProperties potionAttributes = build.getPotionAttacker().get(potion);
                    final double chance = value * potionAttributes.getScaleChance();
                    if (MathUtil.chanceOf(chance)) {
                        final int grade = potionAttributes.getGrade();
                        final int duration = (int)(value * potionAttributes.getScaleDuration());
                        CombatUtil.applyPotion(attacker, potion, grade, duration);
                    }
                }
                for (final PotionEffectType potion : build.getPotionVictims().keySet()) {
                    final PotionProperties potionAttributes = build.getPotionVictims().get(potion);
                    final double chance = value * potionAttributes.getScaleChance();
                    if (MathUtil.chanceOf(chance)) {
                        final int grade = potionAttributes.getGrade();
                        final int duration = (int)(value * potionAttributes.getScaleDuration());
                        CombatUtil.applyPotion(victims, potion, grade, duration);
                    }
                }
            }
        }
    }
    
    public final ElementBoostStats getElementBoostStats(final HashMap<String, Double> mapElement) {
        double baseAdditionalDamage = 0.0;
        double basePercentDamage = 0.0;
        for (final String key : mapElement.keySet()) {
            if (this.isExists(key)) {
                final double value = mapElement.get(key);
                final ElementBoost build = this.getElementBoostBuild(key);
                baseAdditionalDamage += value * build.getScaleBaseAdditionalDamage();
                basePercentDamage += value * build.getScaleBasePercentDamage();
            }
        }
        return new ElementBoostStats(baseAdditionalDamage, basePercentDamage);
    }
    
    public final double getElementValue(final ItemStack item, final String element) {
        final int line = this.getLineElement(item, element);
        return (line != -1) ? this.getElementValue(item, line) : 0.0;
    }
    
    public final double getElementValue(final ItemStack item, final int line) {
        final String lore = EquipmentUtil.getLores(item).get(line - 1);
        return this.getElementValue(lore);
    }
    
    public final double getElementValue(final String lore) {
        final MainConfig mainConfig = MainConfig.getInstance();
        final String[] part = lore.split(MainConfig.KEY_ELEMENT_VALUE);
        if (part.length > 1) {
            final String positiveValue = mainConfig.getStatsLorePositiveValue();
            final String negativeValue = mainConfig.getStatsLoreNegativeValue();
            final String textValue = part[1].replaceAll(positiveValue, "").replaceAll(negativeValue, "");
            if (MathUtil.isNumber(textValue)) {
                return MathUtil.parseDouble(textValue);
            }
        }
        return 0.0;
    }
    
    public final double getScaleResistance(final String baseElement, final String targetElement) {
        final Element elementBuild = this.getElementBuild(baseElement);
        return (elementBuild != null) ? this.getScaleResistance(elementBuild, targetElement) : 0.0;
    }
    
    public final double getScaleResistance(final Element elementBuild, final String element) {
        for (final String key : elementBuild.getResistance().keySet()) {
            if (key.equalsIgnoreCase(element)) {
                return elementBuild.getResistance().get(key);
            }
        }
        return 0.0;
    }
    
    public final ElementBoost getElementBoostBuild(final String element) {
        if (this.isExists(element)) {
            return this.getElementBuild(element).getBoostBuild();
        }
        return null;
    }
    
    public final ElementPotion getElementPotionBuild(final String element) {
        if (this.isExists(element)) {
            return this.getElementBuild(element).getPotionBuild();
        }
        return null;
    }
    
    public final String getElementKeyLore(final String element) {
        if (this.isExists(element)) {
            return this.getElementBuild(element).getKeyLore();
        }
        return null;
    }
    
    public final int getLineElement(final ItemStack item, final String element) {
        if (this.isExists(element)) {
            return EquipmentUtil.loreGetLineKey(item, this.getKeyElement(element, true));
        }
        return -1;
    }
    
    private final String getKeyElement(final String element) {
        return this.getKeyElement(element, false);
    }
    
    private final String getKeyElement(final String element, final boolean justCheck) {
        final MainConfig mainConfig = MainConfig.getInstance();
        return justCheck ? (String.valueOf(MainConfig.KEY_ELEMENT) + mainConfig.getElementColor() + this.getElementKeyLore(element)) : (String.valueOf(MainConfig.KEY_ELEMENT) + mainConfig.getElementColor() + this.getElementKeyLore(element) + MainConfig.KEY_ELEMENT + mainConfig.getElementColor());
    }
    
    private final String getKeyValue(final double value) {
        final MainConfig mainConfig = MainConfig.getInstance();
        return String.valueOf(MainConfig.KEY_ELEMENT_VALUE) + ((value > 0.0) ? mainConfig.getStatsLorePositiveValue() : mainConfig.getStatsLoreNegativeValue()) + value + MainConfig.KEY_ELEMENT_VALUE + mainConfig.getElementColor();
    }
}
