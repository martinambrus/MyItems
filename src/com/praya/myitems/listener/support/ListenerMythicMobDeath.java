// 
// Decompiled by Procyon v0.5.36
// 

package com.praya.myitems.listener.support;

import api.praya.myitems.builder.socket.SocketGemsTree;
import api.praya.myitems.builder.item.ItemSet;
import api.praya.myitems.builder.item.ItemGenerator;
import io.lumine.xikage.mythicmobs.drops.DropTable;
import org.bukkit.World;
import org.bukkit.inventory.ItemStack;
import com.praya.myitems.manager.game.SocketManager;
import com.praya.myitems.manager.game.ItemSetManager;
import com.praya.myitems.manager.game.ItemGeneratorManager;
import com.praya.myitems.manager.game.ItemManager;
import com.praya.myitems.manager.game.GameManager;
import com.praya.agarthalib.utility.EquipmentUtil;
import com.praya.agarthalib.utility.MathUtil;
import org.bukkit.event.EventHandler;
import java.util.Optional;
import java.util.Iterator;
import java.util.List;
import org.bukkit.Location;
import io.lumine.xikage.mythicmobs.mobs.MythicMob;
import io.lumine.xikage.mythicmobs.drops.MythicDropTable;
import io.lumine.xikage.mythicmobs.MythicMobs;
import io.lumine.xikage.mythicmobs.api.bukkit.events.MythicMobDeathEvent;
import com.praya.myitems.MyItems;
import org.bukkit.event.Listener;
import com.praya.myitems.builder.handler.HandlerEvent;

public class ListenerMythicMobDeath extends HandlerEvent implements Listener
{
    public ListenerMythicMobDeath(final MyItems plugin) {
        super(plugin);
    }
    
    @EventHandler
    public void eventMythicMobDeath(final MythicMobDeathEvent event) {
        final MythicMob mythicMob = event.getMobType();
        final Location location = event.getEntity().getLocation();
        final List<String> drops = (List<String>)mythicMob.getDrops();
        for (final String lineDrops : drops) {
            final String[] args = lineDrops.split(" ");
            if (args.length > 0) {
                final String key = args[0];
                if (args.length == 1) {
                    final Optional<DropTable> mythicDropTable = MythicMobs.inst().getDropManager().getDropTable(key);
                    if (!mythicDropTable.isPresent()) {
                        continue;
                    }
                    final List<String> dropTable = (List<String>)mythicDropTable.get();
                    for (final String drop : dropTable) {
                        this.dropItems(drop, location);
                    }
                }
                else {
                    this.dropItems(lineDrops, location);
                }
            }
        }
    }
    
    private final void dropItems(final String drop, final Location location) {
        final GameManager gameManager = this.plugin.getGameManager();
        final ItemManager itemManager = gameManager.getItemManager();
        final ItemGeneratorManager itemGeneratorManager = gameManager.getItemGeneratorManager();
        final ItemSetManager itemSetManager = gameManager.getItemSetManager();
        final SocketManager socketManager = gameManager.getSocketManager();
        final String[] args = drop.split(" ");
        final String key = args[0];
        if ((key.equalsIgnoreCase("MyItems") || key.equalsIgnoreCase("MyItem")) && args.length > 1) {
            final String category = args[1];
            if (category.equalsIgnoreCase("Custom") || category.equalsIgnoreCase("Items") || category.equalsIgnoreCase("Item")) {
                if (args.length > 2) {
                    final String name = args[2];
                    final ItemStack item = itemManager.getItem(name);
                    if (item != null) {
                        int amount3;
                        if (args.length > 3) {
                            final String textAmount = args[3];
                            if (textAmount.contains("~")) {
                                final String[] componentsAmount = textAmount.split("~");
                                final String textAmount2 = componentsAmount[0];
                                final String textAmount3 = componentsAmount[1];
                                if (!MathUtil.isNumber(textAmount2) || !MathUtil.isNumber(textAmount3)) {
                                    return;
                                }
                                final int amount1 = MathUtil.parseInteger(textAmount2);
                                final int amount2 = MathUtil.parseInteger(textAmount3);
                                amount3 = (int)MathUtil.valueBetween((double)amount1, (double)amount2);
                            }
                            else {
                                if (!MathUtil.isNumber(textAmount)) {
                                    return;
                                }
                                amount3 = MathUtil.parseInteger(textAmount);
                            }
                        }
                        else {
                            amount3 = 1;
                        }
                        double chance3;
                        if (args.length > 4) {
                            final String textChance = args[4];
                            if (textChance.contains("~")) {
                                final String[] componentsChance = textChance.split("~");
                                final String textChance2 = componentsChance[0];
                                final String textChance3 = componentsChance[1];
                                if (!MathUtil.isNumber(textChance2) || !MathUtil.isNumber(textChance3)) {
                                    return;
                                }
                                final double chance1 = MathUtil.parseDouble(textChance2);
                                final double chance2 = MathUtil.parseDouble(textChance3);
                                chance3 = MathUtil.valueBetween(chance1, chance2);
                            }
                            else {
                                if (!MathUtil.isNumber(textChance)) {
                                    return;
                                }
                                chance3 = MathUtil.roundNumber(MathUtil.parseDouble(textChance));
                            }
                        }
                        else {
                            chance3 = 1.0;
                        }
                        EquipmentUtil.setAmount(item, amount3);
                        if (MathUtil.chanceOf(chance3, 1.0)) {
                            final World world = location.getWorld();
                            world.dropItem(location, item);
                        }
                    }
                }
            }
            else if (category.equalsIgnoreCase("Generator") || category.equalsIgnoreCase("Generate") || category.equalsIgnoreCase("Gen")) {
                if (args.length > 2) {
                    final String textGenerator = args[2];
                    final ItemGenerator itemGenerator = itemGeneratorManager.getItemGenerator(textGenerator);
                    if (itemGenerator != null) {
                        int loop3;
                        if (args.length > 3) {
                            final String textLoop = args[3];
                            if (textLoop.contains("~")) {
                                final String[] componentsLoop = textLoop.split("~");
                                final String textLoop2 = componentsLoop[0];
                                final String textLoop3 = componentsLoop[1];
                                if (!MathUtil.isNumber(textLoop2) || !MathUtil.isNumber(textLoop3)) {
                                    return;
                                }
                                final int loop1 = MathUtil.parseInteger(textLoop2);
                                final int loop2 = MathUtil.parseInteger(textLoop3);
                                loop3 = (int)MathUtil.valueBetween((double)loop1, (double)loop2);
                            }
                            else {
                                if (!MathUtil.isNumber(textLoop)) {
                                    return;
                                }
                                loop3 = MathUtil.parseInteger(textLoop);
                            }
                        }
                        else {
                            loop3 = 1;
                        }
                        int amount6;
                        if (args.length > 4) {
                            final String textAmount4 = args[4];
                            if (textAmount4.contains("~")) {
                                final String[] componentsAmount2 = textAmount4.split("~");
                                final String textAmount5 = componentsAmount2[0];
                                final String textAmount6 = componentsAmount2[1];
                                if (!MathUtil.isNumber(textAmount5) || !MathUtil.isNumber(textAmount6)) {
                                    return;
                                }
                                final int amount4 = MathUtil.parseInteger(textAmount5);
                                final int amount5 = MathUtil.parseInteger(textAmount6);
                                amount6 = (int)MathUtil.valueBetween((double)amount4, (double)amount5);
                            }
                            else {
                                if (!MathUtil.isNumber(textAmount4)) {
                                    return;
                                }
                                amount6 = MathUtil.parseInteger(textAmount4);
                            }
                        }
                        else {
                            amount6 = 1;
                        }
                        double chance6;
                        if (args.length > 5) {
                            final String textChance4 = args[5];
                            if (textChance4.contains("~")) {
                                final String[] componentsChance2 = textChance4.split("~");
                                final String textChance5 = componentsChance2[0];
                                final String textChance6 = componentsChance2[1];
                                if (!MathUtil.isNumber(textChance5) || !MathUtil.isNumber(textChance6)) {
                                    return;
                                }
                                final double chance4 = MathUtil.parseDouble(textChance5);
                                final double chance5 = MathUtil.parseDouble(textChance6);
                                chance6 = MathUtil.valueBetween(chance4, chance5);
                            }
                            else {
                                if (!MathUtil.isNumber(textChance4)) {
                                    return;
                                }
                                chance6 = MathUtil.roundNumber(MathUtil.parseDouble(textChance4));
                            }
                        }
                        else {
                            chance6 = 1.0;
                        }
                        for (int t = 0; t < loop3; ++t) {
                            final ItemStack item2 = itemGenerator.generateItem();
                            EquipmentUtil.setAmount(item2, amount6);
                            if (MathUtil.chanceOf(chance6, 1.0)) {
                                final World world2 = location.getWorld();
                                world2.dropItem(location, item2);
                            }
                        }
                    }
                }
            }
            else if (category.equalsIgnoreCase("Set") || category.equalsIgnoreCase("SetItem") || category.equalsIgnoreCase("ItemSet")) {
                if (args.length > 2) {
                    final String componentID = args[2];
                    final ItemSet itemSet = itemSetManager.getItemSetByComponentID(componentID);
                    if (itemSet != null) {
                        int loop3;
                        if (args.length > 3) {
                            final String textLoop = args[3];
                            if (textLoop.contains("~")) {
                                final String[] componentsLoop = textLoop.split("~");
                                final String textLoop2 = componentsLoop[0];
                                final String textLoop3 = componentsLoop[1];
                                if (!MathUtil.isNumber(textLoop2) || !MathUtil.isNumber(textLoop3)) {
                                    return;
                                }
                                final int loop1 = MathUtil.parseInteger(textLoop2);
                                final int loop2 = MathUtil.parseInteger(textLoop3);
                                loop3 = (int)MathUtil.valueBetween((double)loop1, (double)loop2);
                            }
                            else {
                                if (!MathUtil.isNumber(textLoop)) {
                                    return;
                                }
                                loop3 = MathUtil.parseInteger(textLoop);
                            }
                        }
                        else {
                            loop3 = 1;
                        }
                        int amount6;
                        if (args.length > 4) {
                            final String textAmount4 = args[4];
                            if (textAmount4.contains("~")) {
                                final String[] componentsAmount2 = textAmount4.split("~");
                                final String textAmount5 = componentsAmount2[0];
                                final String textAmount6 = componentsAmount2[1];
                                if (!MathUtil.isNumber(textAmount5) || !MathUtil.isNumber(textAmount6)) {
                                    return;
                                }
                                final int amount4 = MathUtil.parseInteger(textAmount5);
                                final int amount5 = MathUtil.parseInteger(textAmount6);
                                amount6 = (int)MathUtil.valueBetween((double)amount4, (double)amount5);
                            }
                            else {
                                if (!MathUtil.isNumber(textAmount4)) {
                                    return;
                                }
                                amount6 = MathUtil.parseInteger(textAmount4);
                            }
                        }
                        else {
                            amount6 = 1;
                        }
                        double chance6;
                        if (args.length > 5) {
                            final String textChance4 = args[5];
                            if (textChance4.contains("~")) {
                                final String[] componentsChance2 = textChance4.split("~");
                                final String textChance5 = componentsChance2[0];
                                final String textChance6 = componentsChance2[1];
                                if (!MathUtil.isNumber(textChance5) || !MathUtil.isNumber(textChance6)) {
                                    return;
                                }
                                final double chance4 = MathUtil.parseDouble(textChance5);
                                final double chance5 = MathUtil.parseDouble(textChance6);
                                chance6 = MathUtil.valueBetween(chance4, chance5);
                            }
                            else {
                                if (!MathUtil.isNumber(textChance4)) {
                                    return;
                                }
                                chance6 = MathUtil.roundNumber(MathUtil.parseDouble(textChance4));
                            }
                        }
                        else {
                            chance6 = 1.0;
                        }
                        for (int t = 0; t < loop3; ++t) {
                            final ItemStack item2 = itemSet.generateItem(componentID);
                            EquipmentUtil.setAmount(item2, amount6);
                            if (MathUtil.chanceOf(chance6, 1.0)) {
                                final World world2 = location.getWorld();
                                world2.dropItem(location, item2);
                            }
                        }
                    }
                }
            }
            else if ((category.equalsIgnoreCase("Gems") || category.equalsIgnoreCase("Gem")) && args.length > 2) {
                final String textSocket = args[2];
                final SocketGemsTree socketTree = socketManager.getSocketTree(textSocket);
                if (socketTree != null) {
                    final int maxGrade = socketTree.getMaxGrade();
                    int grade3;
                    if (args.length > 3) {
                        final String textGrade = args[3];
                        if (textGrade.contains("~")) {
                            final String[] componentsGrade = textGrade.split("~");
                            final String textGrade2 = componentsGrade[0];
                            final String textGrade3 = componentsGrade[1];
                            if (!MathUtil.isNumber(textGrade2) || !MathUtil.isNumber(textGrade3)) {
                                return;
                            }
                            final int grade1 = MathUtil.parseInteger(textGrade2);
                            final int grade2 = MathUtil.parseInteger(textGrade3);
                            final int rawGrade = (int)MathUtil.valueBetween((double)grade1, (double)grade2);
                            grade3 = MathUtil.limitInteger(rawGrade, 1, maxGrade);
                        }
                        else {
                            if (!MathUtil.isNumber(textGrade)) {
                                return;
                            }
                            final int rawGrade2 = MathUtil.parseInteger(textGrade);
                            grade3 = MathUtil.limitInteger(rawGrade2, 1, maxGrade);
                        }
                    }
                    else {
                        grade3 = 1;
                    }
                    int amount9;
                    if (args.length > 4) {
                        final String textAmount7 = args[4];
                        if (textAmount7.contains("~")) {
                            final String[] componentsAmount3 = textAmount7.split("~");
                            final String textAmount8 = componentsAmount3[0];
                            final String textAmount9 = componentsAmount3[1];
                            if (!MathUtil.isNumber(textAmount8) || !MathUtil.isNumber(textAmount9)) {
                                return;
                            }
                            final int amount7 = MathUtil.parseInteger(textAmount8);
                            final int amount8 = MathUtil.parseInteger(textAmount9);
                            amount9 = (int)MathUtil.valueBetween((double)amount7, (double)amount8);
                        }
                        else {
                            if (!MathUtil.isNumber(textAmount7)) {
                                return;
                            }
                            amount9 = MathUtil.parseInteger(textAmount7);
                        }
                    }
                    else {
                        amount9 = 1;
                    }
                    double chance9;
                    if (args.length > 5) {
                        final String textChance7 = args[5];
                        if (textChance7.contains("~")) {
                            final String[] componentsChance3 = textChance7.split("~");
                            final String textChance8 = componentsChance3[0];
                            final String textChance9 = componentsChance3[1];
                            if (!MathUtil.isNumber(textChance8) || !MathUtil.isNumber(textChance9)) {
                                return;
                            }
                            final double chance7 = MathUtil.parseDouble(textChance8);
                            final double chance8 = MathUtil.parseDouble(textChance9);
                            chance9 = MathUtil.valueBetween(chance7, chance8);
                        }
                        else {
                            if (!MathUtil.isNumber(textChance7)) {
                                return;
                            }
                            chance9 = MathUtil.roundNumber(MathUtil.parseDouble(textChance7));
                        }
                    }
                    else {
                        chance9 = 1.0;
                    }
                    final ItemStack item2 = socketTree.getSocketBuild(grade3).getItem();
                    EquipmentUtil.setAmount(item2, amount9);
                    if (MathUtil.chanceOf(chance9, 1.0)) {
                        final World world2 = location.getWorld();
                        world2.dropItem(location, item2);
                    }
                }
            }
        }
    }
}
