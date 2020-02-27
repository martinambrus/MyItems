// 
// Decompiled by Procyon v0.5.36
// 

package api.praya.myitems.builder.passive;

public enum PassiveEffectTypeEnum
{
    FREEZE("FREEZE", 0, "Freeze"), 
    CURSE("CURSE", 1, "Curse"), 
    ROOTS("ROOTS", 2, "Roots"), 
    DARK_FLAME("DARK_FLAME", 3, "Dark_Flame");
    
    private final String name;
    
    private PassiveEffectTypeEnum(final String name2, final int ordinal, final String name) {
        this.name = name;
    }
    
    public final String getName() {
        return this.name;
    }
}
