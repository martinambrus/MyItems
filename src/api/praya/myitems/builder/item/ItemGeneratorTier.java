// 
// Decompiled by Procyon v0.5.36
// 

package api.praya.myitems.builder.item;

import java.util.List;

public class ItemGeneratorTier
{
    private final int possibility;
    private final List<String> additionalLores;
    
    public ItemGeneratorTier(final int possibility, final List<String> additionalLores) {
        this.possibility = possibility;
        this.additionalLores = additionalLores;
    }
    
    public final int getPossibility() {
        return this.possibility;
    }
    
    public final List<String> getAdditionalLores() {
        return this.additionalLores;
    }
}
