// 
// Decompiled by Procyon v0.5.36
// 

package api.praya.myitems.builder.item;

import java.util.List;

public class ItemSetBonus
{
    private final int amountID;
    private final List<String> description;
    private final ItemSetBonusEffect effect;
    
    public ItemSetBonus(final int amountID, final List<String> description, final ItemSetBonusEffect effect) {
        this.amountID = amountID;
        this.description = description;
        this.effect = effect;
    }
    
    public final int getAmountID() {
        return this.amountID;
    }
    
    public final List<String> getDescription() {
        return this.description;
    }
    
    public final ItemSetBonusEffect getEffect() {
        return this.effect;
    }
}
