// 
// Decompiled by Procyon v0.5.36
// 

package com.praya.myitems.manager.game;

import com.praya.agarthalib.utility.TextUtil;
import api.praya.myitems.builder.ability.AbilityWeaponAttributeCastDamage;
import api.praya.myitems.builder.ability.AbilityWeaponAttributeBaseDamage;
import core.praya.agarthalib.bridge.unity.Bridge;
import core.praya.agarthalib.enums.main.SlotType;
import com.praya.myitems.config.plugin.MainConfig;
import core.praya.agarthalib.enums.main.Slot;
import java.util.HashMap;
import org.bukkit.entity.LivingEntity;
import java.util.Iterator;
import com.praya.agarthalib.utility.EquipmentUtil;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.inventory.ItemStack;
import api.praya.myitems.builder.ability.AbilityWeapon;
import com.praya.myitems.manager.register.RegisterAbilityWeaponManager;
import com.praya.myitems.manager.register.RegisterManager;
import com.praya.agarthalib.utility.MathUtil;
import core.praya.agarthalib.enums.main.RomanNumber;
import api.praya.myitems.builder.ability.AbilityItemWeapon;
import api.praya.myitems.builder.ability.AbilityWeaponProperties;
import java.util.Collection;
import com.praya.myitems.MyItems;
import com.praya.myitems.config.game.AbilityWeaponConfig;
import com.praya.myitems.builder.handler.HandlerManager;

public class AbilityWeaponManager extends HandlerManager
{
    private final AbilityWeaponConfig abilityWeaponConfig;
    
    protected AbilityWeaponManager(final MyItems plugin) {
        super(plugin);
        this.abilityWeaponConfig = new AbilityWeaponConfig(plugin);
    }
    
    public final AbilityWeaponConfig getAbilityWeaponConfig() {
        return this.abilityWeaponConfig;
    }
    
    public final Collection<String> getAbilityWeaponPropertiesIDs() {
        return this.getAbilityWeaponConfig().getAbilityWeaponPropertiesIDs();
    }
    
    public final Collection<AbilityWeaponProperties> getAllAbilityWeaponProperties() {
        return this.getAbilityWeaponConfig().getAllAbilityWeaponProperties();
    }
    
    public final AbilityWeaponProperties getAbilityWeaponProperties(final String ability) {
        return this.getAbilityWeaponConfig().getAbilityWeaponProperties(ability);
    }
    
    public final AbilityItemWeapon getAbilityItemWeapon(final String lore) {
        final RegisterManager registerManager = this.plugin.getRegisterManager();
        final RegisterAbilityWeaponManager registerAbilityWeaponManager = registerManager.getRegisterAbilityWeaponManager();
        if (lore != null) {
            final String keyAbility = this.getKeyAbility();
            final String keyChance = this.getKeyChance();
            if (lore.contains(keyAbility) && lore.contains(keyChance)) {
                final String[] partsAbility = lore.split(keyAbility);
                final int partsAbilityLength = partsAbility.length;
                if (partsAbilityLength > 1) {
                    final String[] partsKeyLore = partsAbility[1].split(" ");
                    final int partsKeyLoreLength = partsKeyLore.length;
                    final String textGrade = partsKeyLore[partsKeyLoreLength - 1];
                    String keyLore = null;
                    for (int index = 0; index < partsKeyLoreLength - 1; ++index) {
                        final String part = partsKeyLore[index];
                        if (index == 0) {
                            keyLore = part;
                        }
                        else {
                            keyLore = String.valueOf(keyLore) + " " + part;
                        }
                    }
                    if (keyLore != null) {
                        final AbilityWeapon abilityWeapon = registerAbilityWeaponManager.getAbilityWeaponByKeyLore(keyLore);
                        final int grade = RomanNumber.romanConvert(textGrade);
                        if (grade > 0) {
                            final String[] partsChance = lore.split(keyChance);
                            final int partsChanceLength = partsChance.length;
                            if (partsChanceLength > 1) {
                                final String textChance = partsChance[1];
                                if (MathUtil.isNumber(textChance)) {
                                    final String ability = abilityWeapon.getID();
                                    final double chance = MathUtil.parseDouble(textChance);
                                    final AbilityItemWeapon abilityItemWeapon = new AbilityItemWeapon(ability, grade, chance);
                                    return abilityItemWeapon;
                                }
                            }
                        }
                    }
                }
            }
        }
        return null;
    }
    
    public final boolean isAbilityItemWeapon(final String lore) {
        return this.getAbilityItemWeapon(lore) != null;
    }
    
    public final List<AbilityItemWeapon> getListAbilityItemWeapon(final ItemStack item) {
        final List<AbilityItemWeapon> listAbilityItemWeapon = new ArrayList<AbilityItemWeapon>();
        if (item != null) {
            final String keyAbilityCheck = this.getKeyAbility();
            final List<String> lores = (List<String>)EquipmentUtil.getLores(item);
            for (final String lore : lores) {
                if (lore.contains(keyAbilityCheck)) {
                    final AbilityItemWeapon abilityItemWeapon = this.getAbilityItemWeapon(lore);
                    if (abilityItemWeapon == null) {
                        continue;
                    }
                    listAbilityItemWeapon.add(abilityItemWeapon);
                }
            }
        }
        return listAbilityItemWeapon;
    }
    
    public final boolean hasAbilityItemWeapon(final ItemStack item) {
        return !this.getListAbilityItemWeapon(item).isEmpty();
    }
    
    public final Integer getLineAbilityItemWeapon(final ItemStack item, final String ability) {
        if (item != null && ability != null) {
            final String keyAbility = this.getKeyAbility(ability);
            if (keyAbility != null) {
                final List<String> lores = (List<String>)EquipmentUtil.getLores(item);
                for (int index = 0; index < lores.size(); ++index) {
                    final String lore = lores.get(index);
                    if (lore.contains(keyAbility)) {
                        final int line = index + 1;
                        return line;
                    }
                }
            }
        }
        return null;
    }
    
    public final HashMap<Slot, Collection<AbilityItemWeapon>> getMapListAbilityItemWeapon(final LivingEntity entity) {
        final MainConfig mainConfig = MainConfig.getInstance();
        final HashMap<Slot, Collection<AbilityItemWeapon>> mapListAbilityItemWeapon = new HashMap<Slot, Collection<AbilityItemWeapon>>();
        if (entity != null) {
            final boolean enableAbilityOffHand = mainConfig.isAbilityWeaponEnableOffHand();
            Slot[] values;
            for (int length = (values = Slot.values()).length, i = 0; i < length; ++i) {
                final Slot slot = values[i];
                if (slot.getType().equals((Object)SlotType.WEAPON) && (!slot.equals((Object)Slot.OFFHAND) || enableAbilityOffHand)) {
                    final ItemStack item = Bridge.getBridgeEquipment().getEquipment(entity, slot);
                    final List<AbilityItemWeapon> listAbilityItemWeapon = this.getListAbilityItemWeapon(item);
                    mapListAbilityItemWeapon.put(slot, listAbilityItemWeapon);
                }
            }
        }
        return mapListAbilityItemWeapon;
    }
    
    public final HashMap<AbilityWeapon, Integer> getMapAbilityWeapon(final Collection<AbilityItemWeapon> listAbilityItemWeapon) {
        return this.getMapAbilityWeapon(listAbilityItemWeapon, false);
    }
    
    public final HashMap<AbilityWeapon, Integer> getMapAbilityWeapon(final Collection<AbilityItemWeapon> listAbilityItemWeapon, final boolean checkChance) {
        final RegisterManager registerManager = this.plugin.getRegisterManager();
        final RegisterAbilityWeaponManager registerAbilityWeaponManager = registerManager.getRegisterAbilityWeaponManager();
        final HashMap<AbilityWeapon, Integer> mapAbilityWeapon = new HashMap<AbilityWeapon, Integer>();
        if (listAbilityItemWeapon != null) {
            final HashMap<String, Integer> mapAbilityGrade = new HashMap<String, Integer>();
            for (final AbilityItemWeapon abilityItemWeapon : listAbilityItemWeapon) {
                final double chance = abilityItemWeapon.getChance();
                if (!checkChance || MathUtil.chanceOf(chance)) {
                    final String ability = abilityItemWeapon.getAbility();
                    final int grade = abilityItemWeapon.getGrade();
                    if (mapAbilityGrade.containsKey(ability)) {
                        final int totalGrade = mapAbilityGrade.get(ability) + grade;
                        mapAbilityGrade.put(ability, totalGrade);
                    }
                    else {
                        mapAbilityGrade.put(ability, grade);
                    }
                }
            }
            for (final String ability2 : mapAbilityGrade.keySet()) {
                final AbilityWeapon abilityWeapon = registerAbilityWeaponManager.getAbilityWeapon(ability2);
                if (abilityWeapon != null) {
                    final int grade2 = mapAbilityGrade.get(ability2);
                    mapAbilityWeapon.put(abilityWeapon, grade2);
                }
            }
        }
        return mapAbilityWeapon;
    }
    
    public final HashMap<AbilityWeapon, Integer> getMapAbilityWeapon(final LivingEntity entity) {
        return this.getMapAbilityWeapon(entity, false);
    }
    
    public final HashMap<AbilityWeapon, Integer> getMapAbilityWeapon(final LivingEntity entity, final boolean checkChance) {
        final HashMap<AbilityWeapon, Integer> mapAbilityWeapon = new HashMap<AbilityWeapon, Integer>();
        if (entity != null) {
            final HashMap<Slot, Collection<AbilityItemWeapon>> mapListAbilityItemWeapon = this.getMapListAbilityItemWeapon(entity);
            for (final Collection<AbilityItemWeapon> listAbilityItemWeapon : mapListAbilityItemWeapon.values()) {
                final HashMap<AbilityWeapon, Integer> mapAbilityWeaponSlot = this.getMapAbilityWeapon(listAbilityItemWeapon, checkChance);
                for (final AbilityWeapon abilityWeapon : mapAbilityWeaponSlot.keySet()) {
                    final int grade = mapAbilityWeaponSlot.get(abilityWeapon);
                    if (mapAbilityWeapon.containsKey(abilityWeapon)) {
                        final int totalGrade = mapAbilityWeapon.get(abilityWeapon) + grade;
                        mapAbilityWeapon.put(abilityWeapon, totalGrade);
                    }
                    else {
                        mapAbilityWeapon.put(abilityWeapon, grade);
                    }
                }
            }
        }
        return mapAbilityWeapon;
    }
    
    public final double getTotalBaseBonusDamage(final HashMap<AbilityWeapon, Integer> mapAbilityWeapon) {
        double totalBaseBonusDamage = 0.0;
        if (mapAbilityWeapon != null) {
            for (final AbilityWeapon abilityWeapon : mapAbilityWeapon.keySet()) {
                if (abilityWeapon instanceof AbilityWeaponAttributeBaseDamage) {
                    final AbilityWeaponAttributeBaseDamage abilityWeaponAttributeBaseDamage = (AbilityWeaponAttributeBaseDamage)abilityWeapon;
                    final int grade = mapAbilityWeapon.get(abilityWeapon);
                    final double baseBonusDamage = abilityWeaponAttributeBaseDamage.getBaseBonusDamage(grade);
                    totalBaseBonusDamage += baseBonusDamage;
                }
            }
        }
        return totalBaseBonusDamage;
    }
    
    public final double getTotalBasePercentDamage(final HashMap<AbilityWeapon, Integer> mapAbilityWeapon) {
        double totalBasePercentDamage = 0.0;
        if (mapAbilityWeapon != null) {
            for (final AbilityWeapon abilityWeapon : mapAbilityWeapon.keySet()) {
                if (abilityWeapon instanceof AbilityWeaponAttributeBaseDamage) {
                    final AbilityWeaponAttributeBaseDamage abilityWeaponAttributeBaseDamage = (AbilityWeaponAttributeBaseDamage)abilityWeapon;
                    final int grade = mapAbilityWeapon.get(abilityWeapon);
                    final double basePercentDamage = abilityWeaponAttributeBaseDamage.getBasePercentDamage(grade);
                    totalBasePercentDamage += basePercentDamage;
                }
            }
        }
        return totalBasePercentDamage;
    }
    
    public final double getTotalCastBonusDamage(final HashMap<AbilityWeapon, Integer> mapAbilityWeapon) {
        double totalCastBonusDamage = 0.0;
        if (mapAbilityWeapon != null) {
            for (final AbilityWeapon abilityWeapon : mapAbilityWeapon.keySet()) {
                if (abilityWeapon instanceof AbilityWeaponAttributeCastDamage) {
                    final AbilityWeaponAttributeCastDamage abilityWeaponAttributeCastDamage = (AbilityWeaponAttributeCastDamage)abilityWeapon;
                    final int grade = mapAbilityWeapon.get(abilityWeapon);
                    final double castBonusDamage = abilityWeaponAttributeCastDamage.getCastBonusDamage(grade);
                    totalCastBonusDamage += castBonusDamage;
                }
            }
        }
        return totalCastBonusDamage;
    }
    
    public final double getTotalCastPercentDamage(final HashMap<AbilityWeapon, Integer> mapAbilityWeapon) {
        double totalCastPercentDamage = 0.0;
        if (mapAbilityWeapon != null) {
            for (final AbilityWeapon abilityWeapon : mapAbilityWeapon.keySet()) {
                if (abilityWeapon instanceof AbilityWeaponAttributeCastDamage) {
                    final AbilityWeaponAttributeCastDamage abilityWeaponAttributeCastDamage = (AbilityWeaponAttributeCastDamage)abilityWeapon;
                    final int grade = mapAbilityWeapon.get(abilityWeapon);
                    final double castPercentDamage = abilityWeaponAttributeCastDamage.getCastPercentDamage(grade);
                    totalCastPercentDamage += castPercentDamage;
                }
            }
        }
        return totalCastPercentDamage;
    }
    
    public final String getTextAbility(final String ability, final int grade) {
        return this.getTextAbility(ability, grade, 100.0);
    }
    
    public final String getTextAbility(final String ability, final int grade, final double chance) {
        final MainConfig mainConfig = MainConfig.getInstance();
        final String keyAbility = this.getKeyAbility(ability, grade);
        final String keyChance = this.getKeyChance(chance);
        final HashMap<String, String> mapPlaceholder = new HashMap<String, String>();
        String format = mainConfig.getAbilityFormat();
        mapPlaceholder.put("ability", keyAbility);
        mapPlaceholder.put("chance", keyChance);
        format = TextUtil.placeholder((HashMap)mapPlaceholder, format, "<", ">");
        return format;
    }
    
    private final String getKeyAbility() {
        return this.getKeyAbility(null);
    }
    
    private final String getKeyAbility(final String ability) {
        return this.getKeyAbility(ability, null);
    }
    
    private final String getKeyAbility(final String ability, final Integer grade) {
        final RegisterManager registerManager = this.plugin.getRegisterManager();
        final RegisterAbilityWeaponManager registerAbilityWeaponManager = registerManager.getRegisterAbilityWeaponManager();
        final MainConfig mainConfig = MainConfig.getInstance();
        final String abilityKey = MainConfig.KEY_ABILITY_WEAPON;
        final String abilityColor = mainConfig.getAbilityColor();
        if (ability == null) {
            final String keyAbility = String.valueOf(abilityKey) + abilityColor;
            return keyAbility;
        }
        final AbilityWeapon abilityWeapon = registerAbilityWeaponManager.getAbilityWeapon(ability);
        if (abilityWeapon == null) {
            return null;
        }
        final String keyLore = TextUtil.colorful(abilityWeapon.getKeyLore());
        if (grade != null) {
            final int maxGrade = abilityWeapon.getMaxGrade();
            final int abilityGrade = MathUtil.limitInteger((int)grade, 1, maxGrade);
            final String keyAbility2 = String.valueOf(abilityKey) + abilityColor + keyLore + " " + RomanNumber.getRomanNumber(abilityGrade) + abilityKey + abilityColor;
            return keyAbility2;
        }
        final String keyAbility3 = String.valueOf(abilityKey) + abilityColor + keyLore;
        return keyAbility3;
    }
    
    private final String getKeyChance() {
        return this.getKeyChance(null);
    }
    
    private final String getKeyChance(final Double chance) {
        final MainConfig mainConfig = MainConfig.getInstance();
        final String abilityKeyPercent = MainConfig.KEY_ABILITY_PERCENT;
        final String abilityColorPercent = mainConfig.getAbilityColorPercent();
        if (chance != null) {
            final double abilityChance = MathUtil.limitDouble((double)chance, 0.0, 100.0);
            final String keyChance = String.valueOf(abilityKeyPercent) + abilityColorPercent + abilityChance + abilityKeyPercent + abilityColorPercent + "%";
            return keyChance;
        }
        final String keyChance2 = String.valueOf(abilityKeyPercent) + abilityColorPercent;
        return keyChance2;
    }
}
