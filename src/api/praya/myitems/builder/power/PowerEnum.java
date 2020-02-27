// 
// Decompiled by Procyon v0.5.36
// 

package api.praya.myitems.builder.power;

public enum PowerEnum
{
    COMMAND("COMMAND", 0), 
    SHOOT("SHOOT", 1), 
    SPECIAL("SPECIAL", 2);
    
    private PowerEnum(final String name, final int ordinal) {
    }
    
    public static final PowerEnum get(final String power) {
        final String upperCase;
        switch (upperCase = power.toUpperCase()) {
            case "LAUNCH": {
                return PowerEnum.SHOOT;
            }
            case "SPECIAL": {
                return PowerEnum.SPECIAL;
            }
            case "CMD": {
                return PowerEnum.COMMAND;
            }
            case "SHOOT": {
                return PowerEnum.SHOOT;
            }
            case "COMMAND": {
                return PowerEnum.COMMAND;
            }
            default:
                break;
        }
        return null;
    }
}
