// 
// Decompiled by Procyon v0.5.36
// 

package com.praya.myitems.manager.task;

import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.plugin.Plugin;
import com.praya.myitems.builder.task.TaskPowerCooldown;
import org.bukkit.Bukkit;
import com.praya.myitems.MyItems;
import org.bukkit.scheduler.BukkitTask;
import com.praya.myitems.builder.handler.HandlerManager;

public class TaskPowerCooldownManager extends HandlerManager
{
    private BukkitTask taskPowerCooldown;
    
    protected TaskPowerCooldownManager(final MyItems plugin) {
        super(plugin);
        this.reloadTaskPowerCooldown();
    }
    
    public final void reloadTaskPowerCooldown() {
        if (this.taskPowerCooldown != null) {
            this.taskPowerCooldown.cancel();
        }
        this.taskPowerCooldown = this.createTaskPowerCooldown();
    }
    
    private final BukkitTask createTaskPowerCooldown() {
        final BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
        final Runnable runnable = new TaskPowerCooldown(this.plugin);
        final int delay = 0;
        final int period = 1;
        final BukkitTask task = scheduler.runTaskTimer((Plugin)this.plugin, runnable, 0L, 1L);
        return task;
    }
}
