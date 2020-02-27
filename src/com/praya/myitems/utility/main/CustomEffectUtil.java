// 
// Decompiled by Procyon v0.5.36
// 

package com.praya.myitems.utility.main;

import com.praya.agarthalib.utility.MetadataUtil;
import api.praya.myitems.builder.passive.PassiveEffectTypeEnum;
import org.bukkit.entity.Entity;

public class CustomEffectUtil
{
    private static final String METADATA_CUSTOM_EFFECT = "Custom_Effect_Time";
    private static final String METADATA_SPEED_BASE = "Speed_Base";
    
    public static final void setCustomEffect(final Entity entity, final long duration, final PassiveEffectTypeEnum customEffect) {
        MetadataUtil.setCooldown(entity, getMetadataCustomEffect(customEffect), duration);
    }
    
    public static final double getLeftTimeEffect(final Entity entity, final PassiveEffectTypeEnum customEffect) {
        return MetadataUtil.getTimeCooldown(entity, getMetadataCustomEffect(customEffect));
    }
    
    public static final boolean isRunCustomEffect(final Entity entity, final PassiveEffectTypeEnum customEffect) {
        return !MetadataUtil.isExpired(entity, getMetadataCustomEffect(customEffect));
    }
    
    public static final void setSpeedBase(final Entity entity, final float speed) {
        entity.setMetadata(getMetadataSpeedBase(), MetadataUtil.createMetadata((Object)speed));
    }
    
    public static final float getSpeedBase(final Entity entity) {
        return MetadataUtil.getMetadata(entity, getMetadataSpeedBase()).asFloat();
    }
    
    public static final boolean hasSpeedBase(final Entity entity) {
        return MetadataUtil.hasMetadata(entity, getMetadataSpeedBase());
    }
    
    public static final void removeSpeedBase(final Entity entity) {
        MetadataUtil.removeMetadata(entity, getMetadataSpeedBase());
    }
    
    private static final String getMetadataCustomEffect(final PassiveEffectTypeEnum customEffect) {
        return "Custom_Effect_Time:" + customEffect.getName();
    }
    
    private static final String getMetadataSpeedBase() {
        return "Speed_Base";
    }
}
