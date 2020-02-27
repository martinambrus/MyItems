// 
// Decompiled by Procyon v0.5.36
// 

package api.praya.myitems.manager.game;

import com.praya.myitems.manager.game.GameManager;
import com.praya.myitems.manager.game.LoreStatsManager;
import api.praya.myitems.builder.lorestats.LoreStatsArmor;
import core.praya.agarthalib.enums.main.Slot;
import api.praya.myitems.builder.lorestats.LoreStatsWeapon;
import org.bukkit.entity.LivingEntity;
import api.praya.myitems.builder.lorestats.LoreStatsOption;
import api.praya.myitems.builder.lorestats.LoreStatsEnum;
import org.bukkit.inventory.ItemStack;
import com.praya.myitems.MyItems;
import com.praya.myitems.builder.handler.HandlerManager;

public class LoreStatsManagerAPI extends HandlerManager
{
    protected LoreStatsManagerAPI(final MyItems plugin) {
        super(plugin);
    }
    
    public final boolean hasLoreStats(final ItemStack item, final LoreStatsEnum loreStats) {
        return this.getLoreStatsManager().hasLoreStats(item, loreStats);
    }
    
    public final String getTextLoreStats(final LoreStatsEnum loreStats, final double value) {
        return this.getLoreStatsManager().getTextLoreStats(loreStats, value);
    }
    
    public final String getTextLoreStats(final LoreStatsEnum loreStats, final double value1, final double value2) {
        return this.getLoreStatsManager().getTextLoreStats(loreStats, value1, value2);
    }
    
    public final double getLoreValue(final ItemStack item, final LoreStatsEnum loreStats, final LoreStatsOption option) {
        return this.getLoreStatsManager().getLoreValue(item, loreStats, option);
    }
    
    public final void itemRepair(final ItemStack item, final int repair) {
        this.getLoreStatsManager().itemRepair(item, repair);
    }
    
    public final double getUpExp(final int level) {
        return this.getLoreStatsManager().getUpExp(level);
    }
    
    public final boolean checkDurability(final ItemStack item) {
        return this.getLoreStatsManager().checkDurability(item);
    }
    
    public final boolean checkLevel(final ItemStack item, final int requirement) {
        return this.getLoreStatsManager().checkLevel(item, requirement);
    }
    
    public final void damageDurability(final ItemStack item) {
        this.getLoreStatsManager().damageDurability(item);
    }
    
    public final LoreStatsWeapon getLoreStatsWeapon(final LivingEntity attacker) {
        return this.getLoreStatsManager().getLoreStatsWeapon(attacker);
    }
    
    public final LoreStatsWeapon getLoreStatsWeapon(final LivingEntity attacker, final boolean reverse) {
        return this.getLoreStatsManager().getLoreStatsWeapon(attacker, reverse);
    }
    
    public final LoreStatsWeapon getLoreStatsWeapon(final LivingEntity attacker, final boolean checkDurability, final boolean reverse) {
        return this.getLoreStatsManager().getLoreStatsWeapon(attacker, checkDurability, reverse);
    }
    
    public final LoreStatsWeapon getLoreStatsWeapon(final ItemStack item) {
        return this.getLoreStatsManager().getLoreStatsWeapon(item);
    }
    
    public final LoreStatsWeapon getLoreStatsWeapon(final ItemStack item, final boolean reverse) {
        return this.getLoreStatsManager().getLoreStatsWeapon(item, reverse);
    }
    
    public final LoreStatsWeapon getLoreStatsWeapon(final ItemStack item, final boolean checkDurability, final boolean reverse) {
        return this.getLoreStatsManager().getLoreStatsWeapon(item, checkDurability, reverse);
    }
    
    public final LoreStatsWeapon getLoreStatsWeapon(final ItemStack item, final Slot slot, final boolean checkDurability, final boolean reverse) {
        return this.getLoreStatsManager().getLoreStatsWeapon(item, slot, checkDurability, reverse);
    }
    
    public final LoreStatsArmor getLoreStatsArmor(final LivingEntity victims) {
        return this.getLoreStatsManager().getLoreStatsArmor(victims);
    }
    
    public final LoreStatsArmor getLoreStatsArmor(final LivingEntity victims, final boolean checkDurability) {
        return this.getLoreStatsManager().getLoreStatsArmor(victims, checkDurability);
    }
    
    public final LoreStatsArmor getLoreStatsArmor(final ItemStack item) {
        return this.getLoreStatsManager().getLoreStatsArmor(item);
    }
    
    public final LoreStatsArmor getLoreStatsArmor(final ItemStack item, final boolean checkDurability) {
        return this.getLoreStatsManager().getLoreStatsArmor(item, checkDurability);
    }
    
    public final LoreStatsArmor getLoreStatsArmor(final ItemStack item, final Slot slot, final boolean checkDurability) {
        return this.getLoreStatsManager().getLoreStatsArmor(item, slot, checkDurability);
    }
    
    private final LoreStatsManager getLoreStatsManager() {
        final GameManager gameManager = this.plugin.getGameManager();
        final LoreStatsManager loreStatsManager = gameManager.getStatsManager();
        return loreStatsManager;
    }
}
