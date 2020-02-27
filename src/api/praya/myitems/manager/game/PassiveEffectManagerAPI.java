// 
// Decompiled by Procyon v0.5.36
// 

package api.praya.myitems.manager.game;

import com.praya.myitems.manager.game.GameManager;
import com.praya.myitems.manager.game.PassiveEffectManager;
import org.bukkit.entity.Player;
import java.util.Collection;
import org.bukkit.inventory.ItemStack;
import core.praya.agarthalib.enums.main.Slot;
import org.bukkit.entity.LivingEntity;
import api.praya.myitems.builder.passive.PassiveEffectEnum;
import com.praya.myitems.MyItems;
import com.praya.myitems.builder.handler.HandlerManager;

public class PassiveEffectManagerAPI extends HandlerManager
{
    protected PassiveEffectManagerAPI(final MyItems plugin) {
        super(plugin);
    }
    
    public final String getTextPassiveEffect(final PassiveEffectEnum effect, final int grade) {
        return this.getPassiveEffectManager().getTextPassiveEffect(effect, grade);
    }
    
    public final int getHighestGradePassiveEffect(final PassiveEffectEnum effect, final LivingEntity livingEntity) {
        return this.getPassiveEffectManager().getHighestGradePassiveEffect(effect, livingEntity);
    }
    
    public final int getTotalGradePassiveEffect(final PassiveEffectEnum effect, final LivingEntity livingEntity) {
        return this.getPassiveEffectManager().getTotalGradePassiveEffect(effect, livingEntity);
    }
    
    public final int passiveEffectGrade(final LivingEntity livingEntity, final PassiveEffectEnum effect, final Slot slot) {
        return this.getPassiveEffectManager().passiveEffectGrade(livingEntity, effect, slot);
    }
    
    public final int passiveEffectGrade(final ItemStack item, final PassiveEffectEnum effect) {
        return this.getPassiveEffectManager().passiveEffectGrade(item, effect);
    }
    
    public Collection<PassiveEffectEnum> getPassiveEffects(final ItemStack item) {
        return this.getPassiveEffectManager().getPassiveEffects(item);
    }
    
    public final void applyPassiveEffect(final Player player, final PassiveEffectEnum effect, final int grade) {
        this.getPassiveEffectManager().applyPassiveEffect(player, effect, grade);
    }
    
    public final boolean checkAllowedSlot(final Slot slot) {
        return this.getPassiveEffectManager().checkAllowedSlot(slot);
    }
    
    private final PassiveEffectManager getPassiveEffectManager() {
        final GameManager gameManager = this.plugin.getGameManager();
        final PassiveEffectManager passiveEffectManager = gameManager.getPassiveEffectManager();
        return passiveEffectManager;
    }
}
