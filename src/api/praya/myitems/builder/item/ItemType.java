// 
// Decompiled by Procyon v0.5.36
// 

package api.praya.myitems.builder.item;

import java.util.Collection;
import core.praya.agarthalib.enums.main.Slot;
import org.bukkit.enchantments.Enchantment;
import java.util.HashMap;
import api.praya.myitems.builder.lorestats.LoreStatsModifier;
import org.bukkit.Material;

public class ItemType
{
    private final String id;
    private final Material material;
    private final short data;
    private final boolean shiny;
    private final LoreStatsModifier statsModifier;
    private final HashMap<Enchantment, Integer> mapEnchantment;
    private final HashMap<Slot, ItemTypeNBT> mapNBT;
    
    public ItemType(final String id, final Material material, final short data, final boolean shiny, final LoreStatsModifier statsModifier, final HashMap<Enchantment, Integer> mapEnchantment, final HashMap<Slot, ItemTypeNBT> mapNBT) {
        this.id = id;
        this.material = material;
        this.data = data;
        this.shiny = shiny;
        this.statsModifier = statsModifier;
        this.mapEnchantment = mapEnchantment;
        this.mapNBT = mapNBT;
    }
    
    public final String getId() {
        return this.id;
    }
    
    public final Material getMaterial() {
        return this.material;
    }
    
    public final short getData() {
        return this.data;
    }
    
    public final boolean isShiny() {
        return this.shiny;
    }
    
    public final LoreStatsModifier getStatsModifier() {
        return this.statsModifier;
    }
    
    public final Collection<Enchantment> getEnchantments() {
        return this.mapEnchantment.keySet();
    }
    
    public final int getEnchantmentGrade(final Enchantment enchantment) {
        return (enchantment != null && this.mapEnchantment.containsKey(enchantment)) ? this.mapEnchantment.get(enchantment) : 0;
    }
    
    public final Collection<Slot> getAllSlotNBT() {
        return this.mapNBT.keySet();
    }
    
    public final ItemTypeNBT getSlotNBT(final Slot slot) {
        return this.mapNBT.get(slot);
    }
    
    public final Slot getDefaultSlot() {
        return Slot.getDefault(this.getMaterial());
    }
}
