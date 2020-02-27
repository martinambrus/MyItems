// 
// Decompiled by Procyon v0.5.36
// 

package com.praya.myitems.tabcompleter;

import java.util.Iterator;
import com.praya.myitems.manager.register.RegisterAbilityWeaponManager;
import com.praya.myitems.manager.game.PowerCommandManager;
import com.praya.myitems.manager.game.ElementManager;
import com.praya.myitems.manager.plugin.CommandManager;
import com.praya.myitems.manager.game.PowerManager;
import com.praya.myitems.manager.register.RegisterManager;
import com.praya.myitems.manager.game.GameManager;
import com.praya.myitems.manager.plugin.PluginManager;
import com.praya.agarthalib.utility.TabCompleterUtil;
import api.praya.myitems.builder.power.PowerSpecialEnum;
import core.praya.agarthalib.enums.branch.ProjectileEnum;
import api.praya.myitems.builder.power.PowerClickEnum;
import api.praya.myitems.requirement.RequirementEnum;
import api.praya.myitems.builder.power.PowerEnum;
import core.praya.agarthalib.enums.main.TagsAttribute;
import com.praya.agarthalib.utility.ServerUtil;
import core.praya.agarthalib.enums.main.VersionNMS;
import api.praya.myitems.builder.passive.PassiveTypeEnum;
import api.praya.myitems.builder.passive.PassiveEffectEnum;
import api.praya.myitems.builder.lorestats.LoreStatsEnum;
import com.praya.agarthalib.utility.SenderUtil;
import core.praya.agarthalib.enums.branch.SoundEnum;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import com.praya.myitems.MyItems;
import org.bukkit.command.TabCompleter;
import com.praya.myitems.builder.handler.HandlerTabCompleter;

public class TabCompleterAttributes extends HandlerTabCompleter implements TabCompleter
{
    public TabCompleterAttributes(final MyItems plugin) {
        super(plugin);
    }
    
    public List<String> onTabComplete(final CommandSender sender, final Command command, final String label, final String[] args) {
        final PluginManager pluginManager = this.plugin.getPluginManager();
        final GameManager gameManager = this.plugin.getGameManager();
        final RegisterManager registerManager = this.plugin.getRegisterManager();
        final PowerManager powerManager = gameManager.getPowerManager();
        final CommandManager commandManager = pluginManager.getCommandManager();
        final ElementManager elementManager = gameManager.getElementManager();
        final PowerCommandManager powerCommandManager = powerManager.getPowerCommandManager();
        final RegisterAbilityWeaponManager registerAbilityWeaponManager = registerManager.getRegisterAbilityWeaponManager();
        final List<String> tabList = new ArrayList<String>();
        SenderUtil.playSound(sender, SoundEnum.BLOCK_WOOD_BUTTON_CLICK_ON);
        if (args.length == 1) {
            if (commandManager.checkPermission(sender, "Attribute_Stats")) {
                tabList.add("Stats");
            }
            if (commandManager.checkPermission(sender, "Attribute_Buff")) {
                tabList.add("Buff");
            }
            if (commandManager.checkPermission(sender, "Attribute_Debuff")) {
                tabList.add("Debuff");
            }
            if (commandManager.checkPermission(sender, "Attribute_Ability")) {
                tabList.add("Ability");
            }
            if (commandManager.checkPermission(sender, "Attribute_Power")) {
                tabList.add("Power");
            }
            if (commandManager.checkPermission(sender, "Attribute_NBT")) {
                tabList.add("NBT");
            }
            if (commandManager.checkPermission(sender, "Attribute_Element")) {
                tabList.add("Element");
            }
            if (commandManager.checkPermission(sender, "Attribute_Requirement")) {
                tabList.add("Requirement");
            }
        }
        else if (args.length == 2) {
            final String argument1 = args[0];
            if (commandManager.checkCommand(argument1, "Attribute_Stats")) {
                if (commandManager.checkPermission(sender, "Attribute_Stats")) {
                    LoreStatsEnum[] values;
                    for (int length = (values = LoreStatsEnum.values()).length, i = 0; i < length; ++i) {
                        final LoreStatsEnum stats = values[i];
                        if (stats.isAllowed()) {
                            tabList.add(String.valueOf(stats));
                        }
                    }
                }
            }
            else if (commandManager.checkCommand(argument1, "Attribute_Element")) {
                if (commandManager.checkPermission(sender, "Attribute_Element")) {
                    for (final String element : elementManager.getElementConfig().getElements()) {
                        tabList.add(element);
                    }
                }
            }
            else if (commandManager.checkCommand(argument1, "Attribute_Buff")) {
                if (commandManager.checkPermission(sender, "Attribute_Buff")) {
                    PassiveEffectEnum[] values2;
                    for (int length2 = (values2 = PassiveEffectEnum.values()).length, j = 0; j < length2; ++j) {
                        final PassiveEffectEnum buff = values2[j];
                        if (buff.getType().equals(PassiveTypeEnum.BUFF)) {
                            if (ServerUtil.isCompatible(VersionNMS.V1_9_R2) || !buff.equals(PassiveEffectEnum.LUCK)) {
                                tabList.add(buff.toString());
                            }
                        }
                    }
                }
            }
            else if (commandManager.checkCommand(argument1, "Attribute_Debuff")) {
                if (commandManager.checkPermission(sender, "Attribute_Debuff")) {
                    PassiveEffectEnum[] values3;
                    for (int length3 = (values3 = PassiveEffectEnum.values()).length, k = 0; k < length3; ++k) {
                        final PassiveEffectEnum debuff = values3[k];
                        if (debuff.getType().equals(PassiveTypeEnum.DEBUFF)) {
                            if (!ServerUtil.isCompatible(VersionNMS.V1_9_R2)) {
                                if (debuff.equals(PassiveEffectEnum.UNLUCK)) {
                                    continue;
                                }
                                if (debuff.equals(PassiveEffectEnum.GLOW)) {
                                    continue;
                                }
                            }
                            tabList.add(debuff.toString());
                        }
                    }
                }
            }
            else if (commandManager.checkCommand(argument1, "Attribute_NBT")) {
                if (commandManager.checkPermission(sender, "Attribute_NBT")) {
                    TagsAttribute[] values4;
                    for (int length4 = (values4 = TagsAttribute.values()).length, l = 0; l < length4; ++l) {
                        final TagsAttribute tags = values4[l];
                        tabList.add(String.valueOf(tags));
                    }
                }
            }
            else if (commandManager.checkCommand(argument1, "Attribute_Ability")) {
                if (commandManager.checkPermission(sender, "Attribute_Ability")) {
                    for (final String ability : registerAbilityWeaponManager.getAbilityIDs()) {
                        tabList.add(ability.toString());
                    }
                }
            }
            else if (commandManager.checkCommand(argument1, "Attribute_Power")) {
                if (commandManager.checkPermission(sender, "Attribute_Power")) {
                    PowerEnum[] values5;
                    for (int length5 = (values5 = PowerEnum.values()).length, n = 0; n < length5; ++n) {
                        final PowerEnum power = values5[n];
                        tabList.add(String.valueOf(power));
                    }
                }
            }
            else if (commandManager.checkCommand(argument1, "Attribute_Requirement") && commandManager.checkPermission(sender, "Attribute_Requirement")) {
                RequirementEnum[] values6;
                for (int length6 = (values6 = RequirementEnum.values()).length, n2 = 0; n2 < length6; ++n2) {
                    final RequirementEnum requirement = values6[n2];
                    tabList.add(String.valueOf(requirement.getName()));
                }
            }
        }
        else if (args.length == 3) {
            final String argument1 = args[0];
            if (commandManager.checkCommand(argument1, "Attribute_Power") && commandManager.checkPermission(sender, "Attribute_Power")) {
                PowerClickEnum[] values7;
                for (int length7 = (values7 = PowerClickEnum.values()).length, n3 = 0; n3 < length7; ++n3) {
                    final PowerClickEnum click = values7[n3];
                    tabList.add(String.valueOf(click));
                }
            }
        }
        else if (args.length == 4) {
            final String argument1 = args[0];
            final String argument2 = args[1];
            if (commandManager.checkCommand(argument1, "Attribute_Power") && commandManager.checkPermission(sender, "Attribute_Power")) {
                final PowerEnum power2 = PowerEnum.get(argument2);
                if (power2 != null) {
                    if (power2.equals(PowerEnum.COMMAND)) {
                        for (final String powerCommand : powerCommandManager.getPowerCommandIDs()) {
                            tabList.add(powerCommand);
                        }
                    }
                    else if (power2.equals(PowerEnum.SHOOT)) {
                        ProjectileEnum[] values8;
                        for (int length8 = (values8 = ProjectileEnum.values()).length, n4 = 0; n4 < length8; ++n4) {
                            final ProjectileEnum projectile = values8[n4];
                            tabList.add(String.valueOf(projectile));
                        }
                    }
                    else if (power2.equals(PowerEnum.SPECIAL)) {
                        PowerSpecialEnum[] values9;
                        for (int length9 = (values9 = PowerSpecialEnum.values()).length, n5 = 0; n5 < length9; ++n5) {
                            final PowerSpecialEnum special = values9[n5];
                            tabList.add(String.valueOf(special));
                        }
                    }
                }
            }
        }
        return (List<String>)TabCompleterUtil.returnList((List)tabList, args);
    }
}
