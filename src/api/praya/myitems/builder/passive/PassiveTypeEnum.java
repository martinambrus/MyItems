// 
// Decompiled by Procyon v0.5.36
// 

package api.praya.myitems.builder.passive;

public enum PassiveTypeEnum
{
    BUFF("BUFF", 0, "Buff"), 
    DEBUFF("DEBUFF", 1, "Debuff");
    
    private final String name;
    
    private PassiveTypeEnum(final String name2, final int ordinal, final String name) {
        this.name = name;
    }
    
    public final String getName() {
        return this.name;
    }
}
