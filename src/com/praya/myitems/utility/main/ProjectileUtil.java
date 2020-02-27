// 
// Decompiled by Procyon v0.5.36
// 

package com.praya.myitems.utility.main;

import com.praya.myitems.config.plugin.MainConfig;
import core.praya.agarthalib.enums.branch.ProjectileEnum;

public class ProjectileUtil
{
    public static final String getText(final ProjectileEnum projectile) {
        final MainConfig mainConfig = MainConfig.getInstance();
        switch (projectile) {
            case ARROW: {
                return mainConfig.getPowerProjectileIdentifierArrow();
            }
            case SNOWBALL: {
                return mainConfig.getPowerProjectileIdentifierSnowBall();
            }
            case EGG: {
                return mainConfig.getPowerProjectileIdentifierEgg();
            }
            case FLAME_ARROW: {
                return mainConfig.getPowerProjectileIdentifierFlameArrow();
            }
            case FLAME_BALL: {
                return mainConfig.getPowerProjectileIdentifierFlameBall();
            }
            case FLAME_EGG: {
                return mainConfig.getPowerProjectileIdentifierFlameEgg();
            }
            case SMALL_FIREBALL: {
                return mainConfig.getPowerProjectileIdentifierSmallFireball();
            }
            case LARGE_FIREBALL: {
                return mainConfig.getPowerProjectileIdentifierLargeFireball();
            }
            case WITHER_SKULL: {
                return mainConfig.getPowerProjectileIdentifierWitherSkull();
            }
            default: {
                return null;
            }
        }
    }
    
    public static final ProjectileEnum getProjectileByLore(final String lore) {
        ProjectileEnum[] values;
        for (int length = (values = ProjectileEnum.values()).length, i = 0; i < length; ++i) {
            final ProjectileEnum projectile = values[i];
            if (getText(projectile).equalsIgnoreCase(lore)) {
                return projectile;
            }
        }
        return null;
    }
}
