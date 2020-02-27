// 
// Decompiled by Procyon v0.5.36
// 

package api.praya.myitems.builder.socket;

public enum SocketEnum
{
    ADDITIONAL_DAMAGE("ADDITIONAL_DAMAGE", 0), 
    PERCENT_DAMAGE("PERCENT_DAMAGE", 1), 
    PENETRATION("PENETRATION", 2), 
    PVP_DAMAGE("PVP_DAMAGE", 3), 
    PVE_DAMAGE("PVE_DAMAGE", 4), 
    ADDITIONAL_DEFENSE("ADDITIONAL_DEFENSE", 5), 
    PERCENT_DEFENSE("PERCENT_DEFENSE", 6), 
    HEALTH("HEALTH", 7), 
    PVP_DEFENSE("PVP_DEFENSE", 8), 
    PVE_DEFENSE("PVE_DEFENSE", 9), 
    CRITICAL_CHANCE("CRITICAL_CHANCE", 10), 
    CRITICAL_DAMAGE("CRITICAL_DAMAGE", 11), 
    BLOCK_AMOUNT("BLOCK_AMOUNT", 12), 
    BLOCK_RATE("BLOCK_RATE", 13), 
    HIT_RATE("HIT_RATE", 14), 
    DODGE_RATE("DODGE_RATE", 15);
    
    private SocketEnum(final String name, final int ordinal) {
    }
}
