// 
// Decompiled by Procyon v0.5.36
// 

package com.praya.myitems.listener.main;

import org.bukkit.Location;
import java.util.Iterator;
import api.praya.myitems.builder.item.ItemSetBonusEffectStats;
import api.praya.myitems.builder.item.ItemSetBonusEffectEntity;
import api.praya.myitems.builder.element.ElementBoostStats;
import api.praya.myitems.builder.socket.SocketGemsProperties;
import api.praya.myitems.builder.lorestats.LoreStatsArmor;
import api.praya.myitems.builder.lorestats.LoreStatsWeapon;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.entity.Projectile;
import com.praya.myitems.manager.game.ItemSetManager;
import com.praya.myitems.manager.game.AbilityWeaponManager;
import com.praya.myitems.manager.game.ElementManager;
import com.praya.myitems.manager.game.SocketManager;
import com.praya.myitems.manager.game.LoreStatsManager;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import api.praya.myitems.builder.event.CombatCriticalDamageEvent;
import org.bukkit.event.Event;
import core.praya.agarthalib.utility.ServerEventUtil;
import api.praya.myitems.builder.event.CombatPreCriticalEvent;
import core.praya.agarthalib.utility.MathUtil;
import api.praya.myitems.builder.lorestats.LoreStatsEnum;
import api.praya.myitems.builder.ability.AbilityWeapon;
import core.praya.agarthalib.enums.main.SlotType;
import org.bukkit.Material;
import org.bukkit.event.entity.EntityDamageEvent;
import core.praya.agarthalib.utility.ServerUtil;
import core.praya.agarthalib.utility.CombatUtil;
import core.praya.agarthalib.utility.EntityUtil;
import org.bukkit.entity.LivingEntity;
import core.praya.agarthalib.utility.ProjectileUtil;
import com.praya.myitems.config.plugin.MainConfig;
import org.bukkit.event.EventPriority;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;
import org.bukkit.entity.Entity;
import com.praya.myitems.manager.plugin.LanguageManager;
import com.praya.myitems.manager.game.RequirementManager;
import com.praya.myitems.manager.plugin.PluginManager;
import com.praya.myitems.manager.game.GameManager;
import java.util.HashMap;
import org.bukkit.OfflinePlayer;
import core.praya.agarthalib.enums.branch.SoundEnum;
import org.bukkit.command.CommandSender;
import core.praya.agarthalib.utility.SenderUtil;
import core.praya.agarthalib.utility.TextUtil;
import core.praya.agarthalib.utility.EquipmentUtil;
import core.praya.agarthalib.bridge.unity.Bridge;
import core.praya.agarthalib.enums.main.Slot;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import com.praya.myitems.MyItems;
import org.bukkit.event.Listener;
import com.praya.myitems.builder.handler.HandlerEvent;

public class ListenerEntityDamageByEntity extends HandlerEvent implements Listener
{
    public ListenerEntityDamageByEntity(final MyItems plugin) {
        super(plugin);
    }
    
    @EventHandler(priority = EventPriority.MONITOR)
    public void checkBoundAttacker(final EntityDamageByEntityEvent event) {
        final GameManager gameManager = this.plugin.getGameManager();
        final PluginManager pluginManager = this.plugin.getPluginManager();
        final RequirementManager requirementManager = gameManager.getRequirementManager();
        final LanguageManager lang = pluginManager.getLanguageManager();
        if (!event.isCancelled()) {
            final Entity attacker = event.getDamager();
            if (attacker instanceof Player) {
                final Player player = (Player)attacker;
                Slot[] values;
                for (int length = (values = Slot.values()).length, i = 0; i < length; ++i) {
                    final Slot slot = values[i];
                    if (slot.getID() < 2) {
                        final ItemStack item = Bridge.getBridgeEquipment().getEquipment(player, slot);
                        if (EquipmentUtil.loreCheck(item)) {
                            if (!requirementManager.isAllowed(player, item)) {
                                final String message = TextUtil.placeholder(lang.getText("Item_Lack_Requirement"), "Item", EquipmentUtil.getDisplayName(item));
                                event.setCancelled(true);
                                SenderUtil.sendMessage((CommandSender)player, message);
                                SenderUtil.playSound((CommandSender)player, SoundEnum.ENTITY_BLAZE_DEATH);
                            }
                            else {
                                final Integer lineUnbound = requirementManager.getLineRequirementSoulUnbound(item);
                                if (lineUnbound != null) {
                                    final String loreBound = requirementManager.getTextSoulBound((OfflinePlayer)player);
                                    final Integer lineOld = requirementManager.getLineRequirementSoulBound(item);
                                    final HashMap<String, String> map = new HashMap<String, String>();
                                    if (lineOld != null) {
                                        EquipmentUtil.removeLore(item, (int)lineOld);
                                    }
                                    String message2 = lang.getText("Item_Bound");
                                    map.put("item", EquipmentUtil.getDisplayName(item));
                                    map.put("player", player.getName());
                                    message2 = TextUtil.placeholder((HashMap)map, message2);
                                    requirementManager.setMetadataSoulbound((OfflinePlayer)player, item);
                                    EquipmentUtil.setLore(item, (int)lineUnbound, loreBound);
                                    SenderUtil.sendMessage((CommandSender)player, message2);
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    
    @EventHandler(priority = EventPriority.HIGHEST)
    public void entityDamageByEntityEvent(final EntityDamageByEntityEvent event) {
        final PluginManager pluginManager = this.plugin.getPluginManager();
        final GameManager gameManager = this.plugin.getGameManager();
        final LoreStatsManager statsManager = gameManager.getStatsManager();
        final SocketManager socketManager = gameManager.getSocketManager();
        final ElementManager elementManager = gameManager.getElementManager();
        final AbilityWeaponManager abilityWeaponManager = gameManager.getAbilityWeaponManager();
        final ItemSetManager itemSetManager = gameManager.getItemSetManager();
        final LanguageManager lang = pluginManager.getLanguageManager();
        final MainConfig mainConfig = MainConfig.getInstance();
        if (!event.isCancelled()) {
            final Entity entityAttacker = event.getDamager();
            final Entity entityVictims = event.getEntity();
            LivingEntity attacker;
            boolean reverse;
            if (ProjectileUtil.isProjectile(entityAttacker)) {
                final Projectile projectile = ProjectileUtil.parseProjectile(entityAttacker);
                final ProjectileSource source = projectile.getShooter();
                if (!(source instanceof LivingEntity)) {
                    return;
                }
                attacker = EntityUtil.parseLivingEntity(source);
                reverse = (EquipmentUtil.holdBow(attacker) && EquipmentUtil.getActiveSlotBow(attacker).equals((Object)Slot.OFFHAND));
            }
            else {
                if (!EntityUtil.isLivingEntity(entityAttacker)) {
                    return;
                }
                attacker = EntityUtil.parseLivingEntity(entityAttacker);
                reverse = false;
            }
            if (!EntityUtil.isLivingEntity(entityVictims)) {
                return;
            }
            final LivingEntity victims = EntityUtil.parseLivingEntity(entityVictims);
            if (!Bridge.getBridgeLivingEntity().isLivingEntity((Entity)attacker)) {
                return;
            }
            if (!Bridge.getBridgeLivingEntity().isLivingEntity((Entity)victims)) {
                return;
            }
            final boolean isSkillDamage = CombatUtil.isSkillDamage(victims);
            final boolean isAreaDamage = CombatUtil.isAreaDamage(victims);
            if (CombatUtil.hasMetadataInstantDamage((Entity)victims)) {
                CombatUtil.removeMetadataInstantDamage((Entity)victims);
                return;
            }
            final EntityDamageEvent.DamageCause damageCause = event.getCause();
            final ItemStack itemMainHand = Bridge.getBridgeEquipment().getEquipment(attacker, Slot.MAINHAND);
            final ItemStack itemOffHand = Bridge.getBridgeEquipment().getEquipment(attacker, Slot.OFFHAND);
            final double baseDamage = ServerUtil.isVersion_1_12() ? 0.0 : event.getOriginalDamage(EntityDamageEvent.DamageModifier.BASE);
            final boolean enableVanillaDamage = mainConfig.isModifierEnableVanillaDamage();
            double damage = event.getDamage() - baseDamage;
            boolean customDamage = true;
            if (!EntityUtil.isPlayer((Entity)attacker)) {
                final boolean enableCustomMobDamage = customDamage = mainConfig.isModifierEnableCustomMobDamage();
            }
            else {
                final boolean enableCustomDamage = customDamage = mainConfig.isModifierEnableCustomModifier();
            }
            if (!damageCause.equals((Object)EntityDamageEvent.DamageCause.PROJECTILE) && EquipmentUtil.isSolid(itemMainHand)) {
                final Material materialMainHand = itemMainHand.getType();
                if (materialMainHand.equals((Object)Material.BOW) && !isSkillDamage && !isAreaDamage) {
                    customDamage = false;
                }
            }
            if (enableVanillaDamage) {
                final double scaleDamageVanilla = mainConfig.getModifierScaleDamageVanilla();
                if (scaleDamageVanilla > 0.0) {
                    final double vanillaDamage = scaleDamageVanilla * baseDamage;
                    damage += vanillaDamage;
                }
            }
            else {
                --damage;
            }
            Label_2213: {
                if (customDamage) {
                    final LoreStatsWeapon loreStatsAttacker = statsManager.getLoreStatsWeapon(attacker, reverse);
                    final LoreStatsArmor loreStatsVictims = statsManager.getLoreStatsArmor(victims);
                    final SocketGemsProperties socketAttacker = socketManager.getSocketProperties(attacker);
                    final SocketGemsProperties socketVictims = socketManager.getSocketProperties(victims);
                    final HashMap<String, Double> elementAttacker = elementManager.getMapElement(attacker, SlotType.WEAPON);
                    final HashMap<String, Double> elementVictims = elementManager.getMapElement(victims, SlotType.ARMOR);
                    final HashMap<String, Double> mapElement = elementManager.getElementCalculation(elementAttacker, elementVictims);
                    final ElementBoostStats elementBoostStatsAttacker = elementManager.getElementBoostStats(mapElement);
                    final HashMap<AbilityWeapon, Integer> mapAbilityWeapon = abilityWeaponManager.getMapAbilityWeapon(attacker, true);
                    final ItemSetBonusEffectEntity itemSetBonusEffectEntityAttacker = itemSetManager.getItemSetBonusEffectEntity(attacker);
                    final ItemSetBonusEffectEntity itemSetBonusEffectEntityVictims = itemSetManager.getItemSetBonusEffectEntity(victims);
                    final ItemSetBonusEffectStats itemSetBonusEffectStatsAttacker = itemSetBonusEffectEntityAttacker.getEffectStats();
                    final ItemSetBonusEffectStats itemSetBonusEffectStatsVictims = itemSetBonusEffectEntityVictims.getEffectStats();
                    for (final AbilityWeapon abilityWeaponItemSet : itemSetBonusEffectEntityAttacker.getAllAbilityWeapon()) {
                        final int maxGrade = abilityWeaponItemSet.getMaxGrade();
                        if (mapAbilityWeapon.containsKey(abilityWeaponItemSet)) {
                            final int totalGrade = mapAbilityWeapon.get(abilityWeaponItemSet) + itemSetBonusEffectEntityAttacker.getGradeAbilityWeapon(abilityWeaponItemSet);
                            mapAbilityWeapon.put(abilityWeaponItemSet, Math.min(maxGrade, totalGrade));
                        }
                        else {
                            final int grade = itemSetBonusEffectEntityAttacker.getGradeAbilityWeapon(abilityWeaponItemSet);
                            mapAbilityWeapon.put(abilityWeaponItemSet, Math.min(maxGrade, grade));
                        }
                    }
                    final double scaleDamageCustom = mainConfig.getModifierScaleDamageCustom();
                    final double statsDamage = loreStatsAttacker.getDamage();
                    final double socketDamage = socketAttacker.getAdditionalDamage() + statsDamage * (socketAttacker.getPercentDamage() / 100.0);
                    final double abilityDamage = abilityWeaponManager.getTotalBaseBonusDamage(mapAbilityWeapon) + statsDamage * (abilityWeaponManager.getTotalBasePercentDamage(mapAbilityWeapon) / 100.0);
                    final double elementDamage = elementBoostStatsAttacker.getBaseAdditionalDamage() + statsDamage * (elementBoostStatsAttacker.getBasePercentDamage() / 100.0);
                    final double itemSetDamage = itemSetBonusEffectStatsAttacker.getAdditionalDamage() + statsDamage * (itemSetBonusEffectStatsAttacker.getPercentDamage() / 100.0);
                    final double attributeDamage = scaleDamageCustom * (statsDamage + socketDamage + abilityDamage + elementDamage + itemSetDamage);
                    final double statsDefense = loreStatsVictims.getDefense();
                    final double socketDefense = socketVictims.getAdditionalDefense() + statsDefense * (socketVictims.getPercentDefense() / 100.0);
                    final double itemSetDefense = itemSetBonusEffectStatsVictims.getAdditionalDefense() + statsDefense * (itemSetBonusEffectStatsVictims.getPercentDefense() / 100.0);
                    final double attributeDefense = statsDefense + socketDefense + itemSetDefense;
                    final boolean enableOffHand = mainConfig.isAbilityWeaponEnableOffHand();
                    final boolean hasStatsCriticalChance = statsManager.hasLoreStats(itemMainHand, LoreStatsEnum.CRITICAL_CHANCE) || (enableOffHand && statsManager.hasLoreStats(itemOffHand, LoreStatsEnum.CRITICAL_CHANCE));
                    final boolean hasStatsCriticalDamage = statsManager.hasLoreStats(itemMainHand, LoreStatsEnum.CRITICAL_DAMAGE) || (enableOffHand && statsManager.hasLoreStats(itemOffHand, LoreStatsEnum.CRITICAL_DAMAGE));
                    double penetration = 0.0;
                    double pvpDamage = 0.0;
                    double pveDamage = 0.0;
                    double defense = 0.0;
                    double pvpDefense = 0.0;
                    double pveDefense = 0.0;
                    double cc = 5.0;
                    double cd = 1.2;
                    double attackAoERadius = 0.0;
                    double attackAoEDamage = 0.0;
                    double blockAmount = 25.0;
                    double blockRate = 0.0;
                    double accuration = 100.0;
                    damage = ((isSkillDamage || isAreaDamage) ? damage : (damage + attributeDamage));
                    penetration = penetration + loreStatsAttacker.getPenetration() + socketAttacker.getPenetration() + itemSetBonusEffectStatsAttacker.getPenetration();
                    pvpDamage = pvpDamage + loreStatsAttacker.getPvPDamage() + socketAttacker.getPvPDamage() + itemSetBonusEffectStatsAttacker.getPvPDamage();
                    pveDamage = pveDamage + loreStatsAttacker.getPvEDamage() + socketAttacker.getPvEDamage() + itemSetBonusEffectStatsAttacker.getPvEDamage();
                    defense += attributeDefense;
                    pvpDefense = pvpDefense + loreStatsVictims.getPvPDefense() + socketVictims.getPvPDefense() + itemSetBonusEffectStatsVictims.getPvPDefense();
                    pveDefense = pveDefense + loreStatsVictims.getPvEDefense() + socketVictims.getPvEDefense() + itemSetBonusEffectStatsVictims.getPvEDefense();
                    cc = (hasStatsCriticalChance ? (loreStatsAttacker.getCriticalChance() + socketAttacker.getCriticalChance() + itemSetBonusEffectStatsAttacker.getCriticalChance()) : cc);
                    cd = (hasStatsCriticalDamage ? (1.0 + loreStatsAttacker.getCriticalDamage() + (socketAttacker.getCriticalDamage() + itemSetBonusEffectStatsAttacker.getCriticalDamage()) / 100.0) : cd);
                    attackAoERadius = attackAoERadius + loreStatsAttacker.getAttackAoERadius() + socketAttacker.getAttackAoERadius() + itemSetBonusEffectStatsAttacker.getAttackAoERadius();
                    attackAoEDamage = attackAoEDamage + loreStatsAttacker.getAttackAoEDamage() + socketAttacker.getAttackAoEDamage() + itemSetBonusEffectStatsAttacker.getAttackAoEDamage();
                    blockAmount = blockAmount + loreStatsVictims.getBlockAmount() + socketVictims.getBlockAmount() + itemSetBonusEffectStatsVictims.getBlockAmount();
                    blockRate = blockRate + loreStatsVictims.getBlockRate() + socketVictims.getBlockRate() + itemSetBonusEffectStatsVictims.getBlockRate();
                    accuration = accuration + (loreStatsAttacker.getHitRate() + socketAttacker.getHitRate() + itemSetBonusEffectStatsAttacker.getHitRate()) - (loreStatsVictims.getDodgeRate() + socketVictims.getDodgeRate() + itemSetBonusEffectStatsVictims.getDodgeRate());
                    if (MathUtil.chanceOf(100.0 - accuration)) {
                        if (EntityUtil.isPlayer((Entity)attacker)) {
                            final Player player = EntityUtil.parsePlayer((Entity)attacker);
                            final String message = lang.getText("Attack_Miss");
                            SenderUtil.playSound((CommandSender)player, SoundEnum.ENTITY_BAT_TAKEOFF);
                            SenderUtil.sendMessage((CommandSender)player, message);
                        }
                        if (EntityUtil.isPlayer((Entity)victims)) {
                            final Player player = EntityUtil.parsePlayer((Entity)victims);
                            final String message = lang.getText("Attack_Dodge");
                            SenderUtil.playSound((CommandSender)player, SoundEnum.ENTITY_BAT_TAKEOFF);
                            SenderUtil.sendMessage((CommandSender)player, message);
                        }
                        event.setCancelled(true);
                        return;
                    }
                    final boolean enableCustomCritical = mainConfig.isModifierEnableCustomCritical();
                    accuration = MathUtil.limitDouble(accuration, 0.0, accuration);
                    damage *= 1.0 + Math.log10(accuration / 100.0);
                    if (MathUtil.chanceOf(blockRate)) {
                        blockAmount = MathUtil.limitDouble(blockAmount, 0.0, 100.0);
                        damage = damage * (100.0 - blockAmount) / 100.0;
                        if (EntityUtil.isPlayer((Entity)attacker)) {
                            final Player player2 = EntityUtil.parsePlayer((Entity)attacker);
                            final String message2 = lang.getText("Attack_Block");
                            SenderUtil.playSound((CommandSender)player2, SoundEnum.BLOCK_ANVIL_PLACE);
                            SenderUtil.sendMessage((CommandSender)player2, message2);
                        }
                        if (EntityUtil.isPlayer((Entity)victims)) {
                            final Player player2 = EntityUtil.parsePlayer((Entity)victims);
                            final String message2 = lang.getText("Attack_Blocked");
                            SenderUtil.playSound((CommandSender)player2, SoundEnum.BLOCK_ANVIL_PLACE);
                            SenderUtil.sendMessage((CommandSender)player2, message2);
                        }
                    }
                    if (enableCustomCritical) {
                        final CombatPreCriticalEvent combatPreCriticalEvent = new CombatPreCriticalEvent(attacker, victims, cc);
                        ServerEventUtil.callEvent((Event)combatPreCriticalEvent);
                        if (!combatPreCriticalEvent.isCancelled() && combatPreCriticalEvent.isCritical()) {
                            final CombatCriticalDamageEvent combatCriticalDamageEvent = new CombatCriticalDamageEvent(attacker, victims, damage, cd, 0.0);
                            ServerEventUtil.callEvent((Event)combatCriticalDamageEvent);
                            if (!combatCriticalDamageEvent.isCancelled()) {
                                damage = combatCriticalDamageEvent.getCalculationDamage();
                            }
                        }
                    }
                    if (!isAreaDamage && !isSkillDamage) {
                        Slot[] values;
                        for (int length = (values = Slot.values()).length, i = 0; i < length; ++i) {
                            final Slot slot = values[i];
                            final LivingEntity holder = slot.getType().equals((Object)SlotType.WEAPON) ? attacker : victims;
                            final ItemStack item = Bridge.getBridgeEquipment().getEquipment(holder, slot);
                            if (EquipmentUtil.isSolid(item) && !item.getType().equals((Object)Material.BOW)) {
                                final boolean enableItemBrokenMessage = mainConfig.isStatsEnableItemBrokenMessage();
                                statsManager.damageDurability(item);
                                if (enableItemBrokenMessage && !statsManager.checkDurability(item)) {
                                    statsManager.sendBrokenCode(holder, slot);
                                }
                            }
                        }
                    }
                    if (!isAreaDamage) {
                        elementManager.applyElementPotion(attacker, victims, mapElement);
                        for (final AbilityWeapon abilityWeapon : mapAbilityWeapon.keySet()) {
                            final int grade2 = mapAbilityWeapon.get(abilityWeapon);
                            abilityWeapon.cast(entityAttacker, entityVictims, grade2, damage);
                        }
                    }
                    final double bonusPercentDamage = EntityUtil.isPlayer((Entity)victims) ? pvpDamage : pveDamage;
                    final double bonusPercentDefense = EntityUtil.isPlayer((Entity)attacker) ? pvpDefense : pveDefense;
                    final double scaleDefenseOverall = mainConfig.getModifierScaleDefenseOverall();
                    final double scaleDamageMobReceive = mainConfig.getModifierScaleMobDamageReceive();
                    final boolean enableFlatDamage = mainConfig.isModifierEnableFlatDamage();
                    penetration = MathUtil.limitDouble(penetration, 0.0, 100.0);
                    defense *= scaleDefenseOverall;
                    defense = defense * (100.0 + bonusPercentDefense) / 100.0;
                    defense = defense * (100.0 - penetration) / 100.0;
                    damage = damage * (100.0 + bonusPercentDamage) / 100.0;
                    damage -= defense;
                    damage = MathUtil.limitDouble(damage, 0.0, damage);
                    damage = (EntityUtil.isPlayer((Entity)victims) ? damage : (damage * scaleDamageMobReceive));
                    if (!isAreaDamage && !isSkillDamage && attackAoERadius > 0.0 && attackAoEDamage > 0.0) {
                        final double attackAoEDamageEntity = damage * (attackAoEDamage / 100.0);
                        final Location attackAoELocation = victims.getLocation();
                        for (final LivingEntity attackAoEVictim : CombatUtil.getNearbyUnits(attackAoELocation, attackAoERadius)) {
                            if (!attackAoEVictim.equals(attacker) && !attackAoEVictim.equals(victims)) {
                                CombatUtil.areaDamage(attacker, attackAoEVictim, attackAoEDamageEntity);
                            }
                        }
                    }
                    if (!enableFlatDamage) {
                        final boolean enableBalancingSystem = mainConfig.isModifierEnableBalancingSystem();
                        final boolean enableBalancingMob = mainConfig.isModifierEnableBalancingMob();
                        if (EntityUtil.isPlayer((Entity)victims)) {
                            if (!enableBalancingSystem) {
                                break Label_2213;
                            }
                        }
                        else if (!enableBalancingMob) {
                            break Label_2213;
                        }
                        final double modusValue = mainConfig.getModifierModusValue();
                        final double divider = 1.0 + Math.log10(1.0 + 10.0 * defense / modusValue);
                        damage /= divider;
                        damage = damage * 10.0 / modusValue;
                    }
                }
            }
            if (!event.isCancelled()) {
                final double scaleDamageOverall = mainConfig.getModifierScaleDamageOverall();
                final boolean enableVanillaModifier = mainConfig.isModifierEnableVanillaDamage();
                damage *= scaleDamageOverall;
                if (enableVanillaModifier) {
                    if (attacker.hasPotionEffect(PotionEffectType.INCREASE_DAMAGE)) {
                        for (final PotionEffect potion : attacker.getActivePotionEffects()) {
                            if (potion.getType().equals((Object)PotionEffectType.INCREASE_DAMAGE)) {
                                damage += damage * potion.getAmplifier() / 10.0;
                                break;
                            }
                        }
                    }
                    if (victims.hasPotionEffect(PotionEffectType.DAMAGE_RESISTANCE)) {
                        for (final PotionEffect potion : victims.getActivePotionEffects()) {
                            if (potion.getType().equals((Object)PotionEffectType.DAMAGE_RESISTANCE)) {
                                damage -= damage * potion.getAmplifier() / 20.0;
                                break;
                            }
                        }
                    }
                    if (!ServerUtil.isVersion_1_12() && event.isApplicable(EntityDamageEvent.DamageModifier.ABSORPTION) && event.getDamage(EntityDamageEvent.DamageModifier.ABSORPTION) < 0.0) {
                        final double scaleAbsorbEffect = mainConfig.getModifierScaleAbsorbEffect();
                        if (scaleAbsorbEffect > 1.0) {
                            event.setDamage(EntityDamageEvent.DamageModifier.ABSORPTION, -damage);
                        }
                        else if (scaleAbsorbEffect < 0.1) {
                            event.setDamage(EntityDamageEvent.DamageModifier.ABSORPTION, -(damage * 0.1));
                        }
                        else {
                            event.setDamage(EntityDamageEvent.DamageModifier.ABSORPTION, -(damage * scaleAbsorbEffect));
                        }
                    }
                }
                if (!ServerUtil.isVersion_1_12()) {
                    final boolean enableVanillaFormulaArmor = mainConfig.isModifierEnableVanillaFormulaArmor();
                    if (enableVanillaFormulaArmor && customDamage) {
                        double modDamage = 0.0;
                        double modArmor = 0.0;
                        if (event.isApplicable(EntityDamageEvent.DamageModifier.BASE)) {
                            modDamage = event.getDamage(EntityDamageEvent.DamageModifier.BASE);
                        }
                        if (event.isApplicable(EntityDamageEvent.DamageModifier.ARMOR)) {
                            modArmor = event.getDamage(EntityDamageEvent.DamageModifier.ARMOR);
                        }
                        final double calculation = (modDamage != 0.0) ? ((modDamage + modArmor) / modDamage) : 0.0;
                        damage *= calculation;
                    }
                }
                event.setDamage(damage);
            }
        }
    }
}
