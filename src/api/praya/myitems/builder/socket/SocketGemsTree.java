// 
// Decompiled by Procyon v0.5.36
// 

package api.praya.myitems.builder.socket;

import java.util.HashMap;
import core.praya.agarthalib.enums.main.SlotType;

public class SocketGemsTree
{
    private final String gems;
    private final int maxGrade;
    private final SlotType typeItem;
    private final HashMap<Integer, SocketGems> mapSocket;
    
    public SocketGemsTree(final String gems, final int maxGrade, final SlotType typeItem, final HashMap<Integer, SocketGems> mapSocket) {
        this.gems = gems;
        this.maxGrade = maxGrade;
        this.typeItem = typeItem;
        this.mapSocket = mapSocket;
    }
    
    public final String getGems() {
        return this.gems;
    }
    
    public final int getMaxGrade() {
        return this.maxGrade;
    }
    
    public final SlotType getTypeItem() {
        return this.typeItem;
    }
    
    public final HashMap<Integer, SocketGems> getMapSocket() {
        return this.mapSocket;
    }
    
    public final boolean isGemsAvailable(final int grade) {
        return this.mapSocket.containsKey(grade);
    }
    
    public final SocketGems getSocketBuild(final int grade) {
        return this.isGemsAvailable(grade) ? this.mapSocket.get(grade) : null;
    }
}
