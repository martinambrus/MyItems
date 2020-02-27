// 
// Decompiled by Procyon v0.5.36
// 

package api.praya.myitems.builder.player;

import api.praya.myitems.builder.power.PowerEnum;
import java.util.Set;
import api.praya.myitems.builder.power.PowerSpecialEnum;
import core.praya.agarthalib.enums.branch.ProjectileEnum;
import java.util.HashMap;

public class PlayerPowerCooldown
{
    private final HashMap<String, Long> mapPowerCommandCooldown;
    private final HashMap<ProjectileEnum, Long> mapPowerShootCooldown;
    private final HashMap<PowerSpecialEnum, Long> mapPowerSpecialCooldown;
    
    public PlayerPowerCooldown() {
        this.mapPowerCommandCooldown = new HashMap<String, Long>();
        this.mapPowerShootCooldown = new HashMap<ProjectileEnum, Long>();
        this.mapPowerSpecialCooldown = new HashMap<PowerSpecialEnum, Long>();
    }
    
    public final Set<String> getCooldownCommandKeySet() {
        return this.mapPowerCommandCooldown.keySet();
    }
    
    public final Set<ProjectileEnum> getCooldownProjectileKeySet() {
        return this.mapPowerShootCooldown.keySet();
    }
    
    public final Set<PowerSpecialEnum> getCooldownSpecialKeySet() {
        return this.mapPowerSpecialCooldown.keySet();
    }
    
    public final void setPowerCommandCooldown(final String command, final long cooldown) {
        final long expired = System.currentTimeMillis() + cooldown;
        this.mapPowerCommandCooldown.put(command, expired);
    }
    
    public final void setPowerShootCooldown(final ProjectileEnum projectile, final long cooldown) {
        final long expired = System.currentTimeMillis() + cooldown;
        this.mapPowerShootCooldown.put(projectile, expired);
    }
    
    public final void setPowerSpecialCooldown(final PowerSpecialEnum special, final long cooldown) {
        final long expired = System.currentTimeMillis() + cooldown;
        this.mapPowerSpecialCooldown.put(special, expired);
    }
    
    public final boolean isPowerCommandCooldown(final String command) {
        if (this.mapPowerCommandCooldown.containsKey(command)) {
            final long expired = this.mapPowerCommandCooldown.get(command);
            final long now = System.currentTimeMillis();
            return now < expired;
        }
        return false;
    }
    
    public final boolean isPowerShootCooldown(final ProjectileEnum projectile) {
        if (this.mapPowerShootCooldown.containsKey(projectile)) {
            final long expired = this.mapPowerShootCooldown.get(projectile);
            final long now = System.currentTimeMillis();
            return now < expired;
        }
        return false;
    }
    
    public final boolean isPowerSpecialCooldown(final PowerSpecialEnum special) {
        if (this.mapPowerSpecialCooldown.containsKey(special)) {
            final long expired = this.mapPowerSpecialCooldown.get(special);
            final long now = System.currentTimeMillis();
            return now < expired;
        }
        return false;
    }
    
    public final void removePowerCommandCooldown(final String command) {
        this.mapPowerCommandCooldown.remove(command);
    }
    
    public final void removePowerShootCooldown(final ProjectileEnum projectile) {
        this.mapPowerShootCooldown.remove(projectile);
    }
    
    public final void removePowerSpecialCooldown(final PowerSpecialEnum special) {
        this.mapPowerSpecialCooldown.remove(special);
    }
    
    public final long getPowerCommandExpired(final String command) {
        return this.mapPowerCommandCooldown.containsKey(command) ? this.mapPowerCommandCooldown.get(command) : System.currentTimeMillis();
    }
    
    public final long getPowerShootExpired(final ProjectileEnum projectile) {
        return this.mapPowerShootCooldown.containsKey(projectile) ? this.mapPowerShootCooldown.get(projectile) : System.currentTimeMillis();
    }
    
    public final long getPowerSpecialExpired(final PowerSpecialEnum special) {
        return this.mapPowerSpecialCooldown.containsKey(special) ? this.mapPowerSpecialCooldown.get(special) : System.currentTimeMillis();
    }
    
    public final long getPowerCommandTimeLeft(final String command) {
        return this.isPowerCommandCooldown(command) ? (this.mapPowerCommandCooldown.get(command) - System.currentTimeMillis()) : 0L;
    }
    
    public final long getPowerShootTimeLeft(final ProjectileEnum projectile) {
        return this.isPowerShootCooldown(projectile) ? (this.mapPowerShootCooldown.get(projectile) - System.currentTimeMillis()) : 0L;
    }
    
    public final long getPowerSpecialTimeLeft(final PowerSpecialEnum special) {
        return this.isPowerSpecialCooldown(special) ? (this.mapPowerSpecialCooldown.get(special) - System.currentTimeMillis()) : 0L;
    }
    
    public final void clearPowerCooldown(final PowerEnum power) {
        switch (power) {
            case COMMAND: {
                this.mapPowerCommandCooldown.clear();
            }
            case SHOOT: {
                this.mapPowerShootCooldown.clear();
            }
            case SPECIAL: {
                this.mapPowerSpecialCooldown.clear();
            }
            default: {}
        }
    }
    
    public final void clearAllCooldown() {
        this.mapPowerCommandCooldown.clear();
        this.mapPowerShootCooldown.clear();
        this.mapPowerSpecialCooldown.clear();
    }
}
