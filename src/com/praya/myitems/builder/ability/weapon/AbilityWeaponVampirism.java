// 
// Decompiled by Procyon v0.5.36
// 

package com.praya.myitems.builder.ability.weapon;

import org.bukkit.entity.Player;
import core.praya.agarthalib.builder.message.MessageBuild;
import org.bukkit.Location;
import org.bukkit.projectiles.ProjectileSource;
import com.praya.myitems.manager.plugin.LanguageManager;
import com.praya.myitems.manager.plugin.PluginManager;
import core.praya.agarthalib.enums.branch.SoundEnum;
import org.bukkit.command.CommandSender;
import com.praya.agarthalib.utility.EntityUtil;
import java.util.Collection;
import core.praya.agarthalib.enums.branch.ParticleEnum;
import core.praya.agarthalib.bridge.unity.Bridge;
import com.praya.agarthalib.utility.MathUtil;
import java.util.HashMap;
import com.praya.agarthalib.utility.PlayerUtil;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Entity;
import api.praya.myitems.builder.ability.AbilityWeaponProperties;
import com.praya.myitems.manager.game.AbilityWeaponManager;
import com.praya.myitems.manager.game.GameManager;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.List;
import com.praya.myitems.config.plugin.MainConfig;
import org.bukkit.plugin.Plugin;
import com.praya.myitems.MyItems;
import api.praya.myitems.builder.ability.AbilityWeaponAttributeBaseDamage;
import api.praya.myitems.builder.ability.AbilityWeapon;

public class AbilityWeaponVampirism extends AbilityWeapon implements AbilityWeaponAttributeBaseDamage
{
    private static final String ABILITY_ID = "Vampirism";
    
    private AbilityWeaponVampirism(final MyItems plugin, final String id) {
        super((Plugin)plugin, id);
    }
    
    public static final AbilityWeaponVampirism getInstance() {
        return AbilityVampirismHelper.instance;
    }
    
    @Override
    public String getKeyLore() {
        final MainConfig mainConfig = MainConfig.getInstance();
        return mainConfig.getAbilityWeaponIdentifierVampirism();
    }
    
    @Override
    public List<String> getDescription() {
        return null;
    }
    
    @Override
    public int getMaxGrade() {
        final MyItems plugin = (MyItems)JavaPlugin.getPlugin((Class)MyItems.class);
        final GameManager gameManager = plugin.getGameManager();
        final AbilityWeaponManager abilityWeaponManager = gameManager.getAbilityWeaponManager();
        final AbilityWeaponProperties abilityWeaponProperties = abilityWeaponManager.getAbilityWeaponProperties("Vampirism");
        return abilityWeaponProperties.getMaxGrade();
    }
    
    @Override
    public double getBaseBonusDamage(final int grade) {
        final MyItems plugin = (MyItems)JavaPlugin.getPlugin((Class)MyItems.class);
        final GameManager gameManager = plugin.getGameManager();
        final AbilityWeaponManager abilityWeaponManager = gameManager.getAbilityWeaponManager();
        final AbilityWeaponProperties abilityWeaponProperties = abilityWeaponManager.getAbilityWeaponProperties("Vampirism");
        final double baseBonusDamage = grade * abilityWeaponProperties.getScaleBaseBonusDamage();
        return baseBonusDamage;
    }
    
    @Override
    public double getBasePercentDamage(final int grade) {
        final MyItems plugin = (MyItems)JavaPlugin.getPlugin((Class)MyItems.class);
        final GameManager gameManager = plugin.getGameManager();
        final AbilityWeaponManager abilityWeaponManager = gameManager.getAbilityWeaponManager();
        final AbilityWeaponProperties abilityWeaponProperties = abilityWeaponManager.getAbilityWeaponProperties("Vampirism");
        final double basePercentDamage = grade * abilityWeaponProperties.getScaleBasePercentDamage();
        return basePercentDamage;
    }
    
    @Override
    public void cast(final Entity caster, final Entity target, final int grade, final double damage) {
        final MyItems plugin = (MyItems)JavaPlugin.getPlugin((Class)MyItems.class);
        final PluginManager pluginManager = plugin.getPluginManager();
        final LanguageManager lang = pluginManager.getLanguageManager();
        final MainConfig mainConfig = MainConfig.getInstance();
        LivingEntity attacker;
        if (caster instanceof Projectile) {
            final Projectile projectile = (Projectile)caster;
            final ProjectileSource projectileSource = projectile.getShooter();
            if (projectileSource == null || !(projectileSource instanceof LivingEntity)) {
                return;
            }
            attacker = (LivingEntity)projectileSource;
        }
        else {
            attacker = (LivingEntity)caster;
        }
        if (target instanceof LivingEntity) {
            final LivingEntity victims = (LivingEntity)target;
            final Location location = attacker.getLocation();
            final double drain = this.getDrain(attacker, victims, grade, damage);
            final double attackerNewHealth = this.getAttackerNewHealth(attacker, victims, drain);
            final MessageBuild messageAttacker = lang.getMessage(attacker, "Ability_Vampirism_Attacker");
            final MessageBuild messageVictims = lang.getMessage(victims, "Ability_Vampirism_Victims");
            final Collection<Player> players = (Collection<Player>)PlayerUtil.getNearbyPlayers(location, mainConfig.getEffectRange());
            final HashMap<String, String> mapPlaceholder = new HashMap<String, String>();
            mapPlaceholder.put("health", String.valueOf(MathUtil.roundNumber(drain)));
            Bridge.getBridgeParticle().playParticle((Collection)players, ParticleEnum.HEART, location, 10, 0.25, 0.25, 0.25, 0.10000000149011612);
            if (EntityUtil.isPlayer((Entity)attacker)) {
                final Player playerAttacker = PlayerUtil.parse(attacker);
                messageAttacker.sendMessage((CommandSender)playerAttacker, (HashMap)mapPlaceholder);
                Bridge.getBridgeSound().playSound(playerAttacker, location, SoundEnum.ENTITY_WITCH_DRINK, 1.0f, 1.0f);
            }
            if (EntityUtil.isPlayer((Entity)victims)) {
                final Player playerVictims = PlayerUtil.parse(victims);
                messageVictims.sendMessage((CommandSender)playerVictims, (HashMap)mapPlaceholder);
                Bridge.getBridgeSound().playSound(playerVictims, location, SoundEnum.ENTITY_WITCH_DRINK, 1.0f, 1.0f);
            }
            EntityUtil.setHealth(attacker, attackerNewHealth);
        }
    }
    
    private final double getDrain(final LivingEntity attacker, final LivingEntity victims, final int grade, final double damage) {
        final double attackerHealth = attacker.getHealth();
        final double attackerMaxHealth = attacker.getMaxHealth();
        final int maxGrade = this.getMaxGrade();
        double drain = damage * grade / maxGrade;
        if (victims.getHealth() - drain < 0.0) {
            drain = victims.getHealth();
        }
        if (!(victims instanceof Player)) {
            drain /= 2.0;
        }
        if (attackerHealth + drain > attackerMaxHealth) {
            drain = attackerMaxHealth - attackerHealth;
        }
        return drain;
    }
    
    private final double getAttackerNewHealth(final LivingEntity attacker, final LivingEntity victims, final double drain) {
        final double attackerHealth = attacker.getHealth();
        final double attackerMaxHealth = attacker.getMaxHealth();
        if (attackerHealth + drain > attackerMaxHealth) {
            return attackerMaxHealth;
        }
        return attackerHealth + drain;
    }
    
    private static class AbilityVampirismHelper
    {
        private static final AbilityWeaponVampirism instance;
        
        static {
            final MyItems plugin = (MyItems)JavaPlugin.getPlugin((Class)MyItems.class);
            instance = new AbilityWeaponVampirism(plugin, "Vampirism");
        }
    }
}
