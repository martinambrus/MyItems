// 
// Decompiled by Procyon v0.5.36
// 

package api.praya.myitems.builder.ability;

import com.praya.myitems.manager.register.RegisterAbilityWeaponManager;
import com.praya.myitems.manager.register.RegisterManager;
import org.bukkit.plugin.java.JavaPlugin;
import com.praya.myitems.MyItems;
import org.bukkit.plugin.Plugin;

public abstract class AbilityWeapon extends Ability
{
    public AbilityWeapon(final Plugin plugin, final String id) {
        super(plugin, id);
    }
    
    public final boolean register() {
        final MyItems plugin = (MyItems)JavaPlugin.getPlugin((Class)MyItems.class);
        final RegisterManager registerManager = plugin.getRegisterManager();
        final RegisterAbilityWeaponManager registerAbilityWeaponManager = registerManager.getRegisterAbilityWeaponManager();
        return registerAbilityWeaponManager.register(this);
    }
}
