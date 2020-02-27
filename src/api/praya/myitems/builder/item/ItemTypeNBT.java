// 
// Decompiled by Procyon v0.5.36
// 

package api.praya.myitems.builder.item;

import java.util.Collection;
import core.praya.agarthalib.enums.main.TagsAttribute;
import java.util.HashMap;

public class ItemTypeNBT
{
    private final HashMap<TagsAttribute, Double> mapTagsAttribute;
    
    public ItemTypeNBT(final HashMap<TagsAttribute, Double> mapTagsAttribute) {
        this.mapTagsAttribute = mapTagsAttribute;
    }
    
    public final Collection<TagsAttribute> getAllTagsAttribute() {
        return this.mapTagsAttribute.keySet();
    }
    
    public final double getTagsAttributeValue(final TagsAttribute attribute) {
        return (attribute != null && this.mapTagsAttribute.containsKey(attribute)) ? this.mapTagsAttribute.get(attribute) : 0.0;
    }
}
