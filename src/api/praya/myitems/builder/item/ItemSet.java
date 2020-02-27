// 
// Decompiled by Procyon v0.5.36
// 

package api.praya.myitems.builder.item;

import java.util.ListIterator;
import org.bukkit.Material;
import com.praya.myitems.manager.plugin.PlaceholderManager;
import com.praya.myitems.manager.plugin.PluginManager;
import core.praya.agarthalib.bridge.unity.Bridge;
import org.bukkit.enchantments.Enchantment;
import com.praya.agarthalib.utility.EquipmentUtil;
import org.bukkit.entity.Player;
import java.util.List;
import com.praya.agarthalib.utility.TextUtil;
import java.util.Collections;
import java.util.ArrayList;
import core.praya.agarthalib.enums.branch.MaterialEnum;
import com.praya.myitems.config.plugin.MainConfig;
import org.bukkit.plugin.java.JavaPlugin;
import com.praya.myitems.MyItems;
import org.bukkit.inventory.ItemStack;
import java.util.Iterator;
import java.util.Collection;
import java.util.HashMap;

public class ItemSet
{
    private final String id;
    private final String name;
    private final HashMap<Integer, ItemSetBonus> mapBonus;
    private final HashMap<String, ItemSetComponent> mapComponent;
    
    public ItemSet(final String id, final String name, final HashMap<Integer, ItemSetBonus> mapBonus, final HashMap<String, ItemSetComponent> mapComponent) {
        this.id = id;
        this.name = name;
        this.mapBonus = mapBonus;
        this.mapComponent = mapComponent;
    }
    
    public final String getID() {
        return this.id;
    }
    
    public final String getName() {
        return this.name;
    }
    
    public final Collection<Integer> getBonusAmountIDs() {
        return this.mapBonus.keySet();
    }
    
    public final Collection<String> getComponentIDs() {
        return this.mapComponent.keySet();
    }
    
    public final Collection<ItemSetBonus> getAllItemSetBonus() {
        return this.mapBonus.values();
    }
    
    public final Collection<ItemSetComponent> getAllItemSetComponent() {
        return this.mapComponent.values();
    }
    
    public final ItemSetBonus getItemSetBonus(final int amountID) {
        return this.mapBonus.get(amountID);
    }
    
    public final boolean isBonusExists(final int amountID) {
        return this.getItemSetBonus(amountID) != null;
    }
    
    public final ItemSetComponent getItemSetComponent(final String componentID) {
        if (componentID != null) {
            for (final String key : this.getComponentIDs()) {
                if (key.equalsIgnoreCase(componentID)) {
                    return this.mapComponent.get(key);
                }
            }
        }
        return null;
    }
    
    public final boolean isComponentExists(final String componentID) {
        return this.getItemSetComponent(componentID) != null;
    }
    
    public final int getTotalComponent() {
        return this.mapComponent.size();
    }
    
    public final ItemStack generateItem(final String componentID) {
        final MyItems plugin = (MyItems)JavaPlugin.getPlugin((Class)MyItems.class);
        final PluginManager pluginManager = plugin.getPluginManager();
        final PlaceholderManager placeholderManager = pluginManager.getPlaceholderManager();
        final MainConfig mainConfig = MainConfig.getInstance();
        final ItemSetComponent itemSetComponent = this.getItemSetComponent(componentID);
        if (itemSetComponent != null) {
            final ItemSetComponentItem componentItem = itemSetComponent.getComponentItem();
            final Material material = componentItem.getMaterial();
            final short data = (short)componentItem.getData();
            final MaterialEnum materialEnum = MaterialEnum.getMaterialEnum(material, (int)data);
            if (materialEnum != null) {
                final String divider = "\n";
                final String keyLine = MainConfig.KEY_SET_LINE;
                final String keySetComponentSelf = MainConfig.KEY_SET_COMPONENT_SELF;
                final String keySetComponentOther = MainConfig.KEY_SET_COMPONENT_OTHER;
                final String loreBonusInactive = mainConfig.getSetLoreBonusInactive();
                final String loreComponentInactive = mainConfig.getSetLoreComponentInactive();
                final boolean shiny = componentItem.isShiny();
                final boolean unbreakable = componentItem.isUnbreakable();
                final List<String> flags = componentItem.getFlags();
                final List<String> lores = new ArrayList<String>(componentItem.getLores());
                final List<String> loresBonus = new ArrayList<String>();
                final List<String> loresComponent = new ArrayList<String>();
                final List<Integer> bonusAmountIDs = new ArrayList<Integer>(this.getBonusAmountIDs());
                final Collection<Enchantment> enchantments = componentItem.getEnchantments();
                final HashMap<String, String> mapPlaceholder = new HashMap<String, String>();
                final String display = componentItem.getDisplayName();
                List<String> loresSet = mainConfig.getSetFormat();
                Collections.sort(bonusAmountIDs);
                for (final ItemSetComponent partComponent : this.getAllItemSetComponent()) {
                    final String partComponentID = partComponent.getID();
                    final String keyLore = partComponent.getKeyLore();
                    String formatComponent = String.valueOf(loreComponentInactive) + mainConfig.getSetFormatComponent();
                    String replacementKeyLore;
                    if (partComponent.equals(itemSetComponent)) {
                        replacementKeyLore = String.valueOf(keySetComponentSelf) + loreComponentInactive + keyLore + keySetComponentSelf + loreComponentInactive;
                    }
                    else {
                        replacementKeyLore = String.valueOf(keySetComponentOther) + loreComponentInactive + keyLore + keySetComponentOther + loreComponentInactive;
                    }
                    mapPlaceholder.clear();
                    mapPlaceholder.put("item_set_component_id", partComponentID);
                    mapPlaceholder.put("item_set_component_keylore", replacementKeyLore);
                    formatComponent = TextUtil.placeholder((HashMap)mapPlaceholder, formatComponent, "<", ">");
                    loresComponent.add(formatComponent);
                }
                for (final int bonusAmountID : bonusAmountIDs) {
                    final ItemSetBonus partBonus = this.getItemSetBonus(bonusAmountID);
                    for (final String description : partBonus.getDescription()) {
                        String formatBonus = String.valueOf(loreBonusInactive) + mainConfig.getSetFormatBonus();
                        mapPlaceholder.clear();
                        mapPlaceholder.put("item_set_bonus_amount", String.valueOf(bonusAmountID));
                        mapPlaceholder.put("item_set_bonus_description", String.valueOf(description));
                        formatBonus = TextUtil.placeholder((HashMap)mapPlaceholder, formatBonus, "<", ">");
                        loresBonus.add(formatBonus);
                    }
                }
                final String lineBonus = TextUtil.convertListToString((List)loresBonus, "\n");
                final String lineComponent = TextUtil.convertListToString((List)loresComponent, "\n");
                mapPlaceholder.clear();
                mapPlaceholder.put("item_set_name", this.getName());
                mapPlaceholder.put("item_set_total", String.valueOf("0"));
                mapPlaceholder.put("item_set_max", String.valueOf(this.getTotalComponent()));
                mapPlaceholder.put("list_item_set_component", lineComponent);
                mapPlaceholder.put("list_item_set_bonus", lineBonus);
                loresSet = (List<String>)TextUtil.placeholder((HashMap)mapPlaceholder, (List)loresSet, "<", ">");
                loresSet = (List<String>)TextUtil.expandList((List)loresSet, "\n");
                final ListIterator<String> iteratorLoresSet = loresSet.listIterator();
                while (iteratorLoresSet.hasNext()) {
                    final String lore = String.valueOf(keyLine) + iteratorLoresSet.next();
                    iteratorLoresSet.set(lore);
                }
                lores.addAll(loresSet);
                final List<String> loresFinal = placeholderManager.placeholder(null, lores);
                final ItemStack item = EquipmentUtil.createItem(materialEnum, display, 1, (List)loresFinal);
                for (final String flag : flags) {
                    EquipmentUtil.addFlag(item, new String[] { flag });
                }
                for (final Enchantment enchantment : enchantments) {
                    final int grade = componentItem.getEnchantmentGrade(enchantment);
                    EquipmentUtil.addEnchantment(item, enchantment, grade);
                }
                if (shiny) {
                    EquipmentUtil.shiny(item);
                }
                if (unbreakable) {
                    Bridge.getBridgeTagsNBT().setUnbreakable(item, true);
                }
                return item;
            }
        }
        return null;
    }
}
