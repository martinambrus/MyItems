// 
// Decompiled by Procyon v0.5.36
// 

package api.praya.myitems.manager.game;

import com.praya.myitems.manager.game.GameManager;
import com.praya.myitems.manager.game.SocketManager;
import org.bukkit.entity.LivingEntity;
import api.praya.myitems.builder.socket.SocketEnum;
import api.praya.myitems.builder.socket.SocketGemsProperties;
import org.bukkit.inventory.ItemStack;
import api.praya.myitems.builder.socket.SocketGems;
import api.praya.myitems.builder.socket.SocketGemsTree;
import java.util.Collection;
import com.praya.myitems.MyItems;
import com.praya.myitems.builder.handler.HandlerManager;

public class SocketManagerAPI extends HandlerManager
{
    protected SocketManagerAPI(final MyItems plugin) {
        super(plugin);
    }
    
    public final Collection<String> getSocketIDs() {
        return this.getSocketManager().getSocketIDs();
    }
    
    public final Collection<SocketGemsTree> getSocketTreeBuilds() {
        return this.getSocketManager().getSocketTreeBuilds();
    }
    
    public final SocketGemsTree getSocketTree(final String id) {
        return this.getSocketManager().getSocketTree(id);
    }
    
    public final boolean isExist(final String name) {
        return this.getSocketManager().isExist(name);
    }
    
    public final String getTextSocketSlotEmpty() {
        return this.getSocketManager().getTextSocketSlotEmpty();
    }
    
    public final SocketGems getSocketBuild(final String socket, final int grade) {
        return this.getSocketManager().getSocketBuild(socket, grade);
    }
    
    public final ItemStack getItem(final String socket, final int grade) {
        return this.getSocketManager().getItem(socket, grade);
    }
    
    public final SocketGemsProperties getSocketProperties(final String socket, final int grade) {
        return this.getSocketManager().getSocketProperties(socket, grade);
    }
    
    public final double getSocketValue(final String socket, final int grade, final SocketEnum typeValue) {
        return this.getSocketManager().getSocketValue(socket, grade, typeValue);
    }
    
    public final double getSocketValue(final SocketGemsProperties socketProperties, final SocketEnum typeValue) {
        return this.getSocketManager().getSocketValue(socketProperties, typeValue);
    }
    
    public final boolean isSocketItem(final ItemStack item) {
        return this.getSocketManager().isSocketItem(item);
    }
    
    public final String getSocketName(final ItemStack item) {
        return this.getSocketManager().getSocketName(item);
    }
    
    public final SocketGems getSocketBuild(final ItemStack item) {
        return this.getSocketManager().getSocketBuild(item);
    }
    
    public final boolean containSocketEmpty(final ItemStack item) {
        return this.getSocketManager().containsSocketEmpty(item);
    }
    
    public final int getLineSocketEmpty(final ItemStack item) {
        return this.getSocketManager().getFirstLineSocketEmpty(item);
    }
    
    public final SocketGemsProperties getSocketProperties(final LivingEntity livingEntity) {
        return this.getSocketManager().getSocketProperties(livingEntity);
    }
    
    public final SocketGemsProperties getSocketProperties(final LivingEntity livingEntity, final boolean checkDurability) {
        return this.getSocketManager().getSocketProperties(livingEntity, checkDurability);
    }
    
    public final SocketGemsProperties getSocketProperties(final ItemStack item) {
        return this.getSocketManager().getSocketProperties(item);
    }
    
    public final SocketGemsProperties getSocketProperties(final ItemStack item, final boolean checkDurability) {
        return this.getSocketManager().getSocketProperties(item, checkDurability);
    }
    
    public final String getTextSocketGemsLore(final String socket, final int grade) {
        return this.getSocketManager().getTextSocketGemsLore(socket, grade);
    }
    
    private final SocketManager getSocketManager() {
        final GameManager gameManager = this.plugin.getGameManager();
        final SocketManager socketManager = gameManager.getSocketManager();
        return socketManager;
    }
}
