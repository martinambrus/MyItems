// 
// Decompiled by Procyon v0.5.36
// 

package com.praya.myitems.listener.main;

import org.bukkit.event.EventHandler;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.ItemStack;
import org.bukkit.entity.Player;
import org.bukkit.entity.LivingEntity;
import com.praya.myitems.manager.game.LoreStatsManager;
import com.praya.myitems.manager.game.GameManager;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import api.praya.myitems.builder.lorestats.LoreStatsOption;
import com.praya.agarthalib.utility.MathUtil;
import api.praya.myitems.builder.lorestats.LoreStatsEnum;
import com.praya.agarthalib.utility.EquipmentUtil;
import core.praya.agarthalib.bridge.unity.Bridge;
import org.bukkit.entity.Entity;
import com.praya.agarthalib.utility.EntityUtil;
import com.praya.myitems.config.plugin.MainConfig;
import org.bukkit.event.entity.EntityDeathEvent;
import com.praya.myitems.MyItems;
import org.bukkit.event.Listener;
import com.praya.myitems.builder.handler.HandlerEvent;

public class ListenerEntityDeath extends HandlerEvent implements Listener
{
    public ListenerEntityDeath(final MyItems plugin) {
        super(plugin);
    }
    
    @EventHandler
    public void onDeath(final EntityDeathEvent event) {
        final GameManager gameManager = this.plugin.getGameManager();
        final LoreStatsManager statsManager = gameManager.getStatsManager();
        final LivingEntity victims = event.getEntity();
        final MainConfig mainConfig = MainConfig.getInstance();
        if (victims.getKiller() != null) {
            final Player player = victims.getKiller();
            final double expGain = EntityUtil.isPlayer((Entity)victims) ? mainConfig.getDropExpPlayer() : mainConfig.getDropExpMobs();
            for (int itemCode = 0; itemCode < 6; ++itemCode) {
                final ItemStack item = Bridge.getBridgeEquipment().getEquipment(player, itemCode);
                if (EquipmentUtil.loreCheck(item)) {
                    final int line = statsManager.getLineLoreStats(item, LoreStatsEnum.LEVEL);
                    if (line != -1) {
                        double scaleExp;
                        if (itemCode == 0) {
                            scaleExp = 1.0;
                        }
                        else if (itemCode == 1) {
                            scaleExp = mainConfig.getModifierScaleExpOffHand();
                        }
                        else {
                            scaleExp = mainConfig.getModifierScaleExpArmor();
                        }
                        final String loreLevel = EquipmentUtil.getLores(item).get(line - 1);
                        final String[] expLores = loreLevel.split(MainConfig.KEY_EXP_CURRENT);
                        final String[] upLores = loreLevel.split(MainConfig.KEY_EXP_UP);
                        final String colorExpCurrent = mainConfig.getStatsColorExpCurrent();
                        final String colorExpUp = mainConfig.getStatsColorExpUp();
                        final double exp = MathUtil.parseDouble(expLores[1].replaceAll(colorExpCurrent, ""));
                        final double up = MathUtil.parseDouble(upLores[1].replaceAll(colorExpUp, ""));
                        final int level = (int)statsManager.getLoreValue(item, LoreStatsEnum.LEVEL, null);
                        final int maxLevel = mainConfig.getStatsMaxLevelValue();
                        if (exp + expGain * scaleExp < up) {
                            if (level < maxLevel) {
                                final double newExp = MathUtil.roundNumber(exp + expGain * scaleExp, 1);
                                final String newExpLore = String.valueOf(expLores[0]) + MainConfig.KEY_EXP_CURRENT + colorExpCurrent + newExp + MainConfig.KEY_EXP_CURRENT + expLores[2];
                                EquipmentUtil.setLore(item, line, newExpLore);
                            }
                        }
                        else {
                            final ItemMeta meta = item.getItemMeta();
                            final double scaleUp = mainConfig.getStatsScaleUpValue();
                            final double calculation = (1.0 + scaleUp * level) / (1.0 + scaleUp * (level - 1));
                            double nextExp = MathUtil.roundNumber(exp + expGain * scaleExp - up, 1);
                            if (level + 1 >= maxLevel) {
                                nextExp = 0.0;
                            }
                            final String newLoreLevel = statsManager.getTextLoreStats(LoreStatsEnum.LEVEL, level + 1, nextExp);
                            final List<String> lores = (List<String>)meta.getLore();
                            final HashMap<Integer, String> mapLore = new HashMap<Integer, String>();
                            for (int i = 0; i < meta.getLore().size(); ++i) {
                                mapLore.put(i, lores.get(i));
                            }
                            mapLore.put(line - 1, newLoreLevel);
                            if (itemCode < 2) {
                                final int lineAdditional = statsManager.getLineLoreStats(item, LoreStatsEnum.DAMAGE);
                                if (lineAdditional != -1) {
                                    double minDamage = statsManager.getLoreValue(item, LoreStatsEnum.DAMAGE, LoreStatsOption.MIN);
                                    double maxDamage = statsManager.getLoreValue(item, LoreStatsEnum.DAMAGE, LoreStatsOption.MAX);
                                    final double scale = maxDamage / minDamage;
                                    minDamage = MathUtil.roundNumber(minDamage * calculation, 2);
                                    maxDamage = MathUtil.roundNumber(minDamage * scale, 2);
                                    final String newLoreDamage = statsManager.getTextLoreStats(LoreStatsEnum.DAMAGE, minDamage, maxDamage);
                                    mapLore.put(lineAdditional - 1, newLoreDamage);
                                }
                            }
                            else {
                                final int lineAdditional = statsManager.getLineLoreStats(item, LoreStatsEnum.DEFENSE);
                                if (lineAdditional != -1) {
                                    double defense = statsManager.getLoreValue(item, LoreStatsEnum.DEFENSE, null);
                                    defense = MathUtil.roundNumber(defense * calculation, 2);
                                    final String newLoreDefense = statsManager.getTextLoreStats(LoreStatsEnum.DEFENSE, defense);
                                    mapLore.put(lineAdditional - 1, newLoreDefense);
                                }
                            }
                            final List<String> newLores = new ArrayList<String>();
                            for (int j = 0; j < mapLore.size(); ++j) {
                                newLores.add(mapLore.get(j));
                            }
                            meta.setLore((List)newLores);
                            item.setItemMeta(meta);
                        }
                    }
                }
            }
        }
    }
}
