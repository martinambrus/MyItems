// 
// Decompiled by Procyon v0.5.36
// 

package com.praya.myitems.builder.task;

import com.praya.myitems.manager.game.PassiveEffectManager;
import com.praya.myitems.manager.game.GameManager;
import com.praya.myitems.config.plugin.MainConfig;
import com.praya.myitems.MyItems;
import com.praya.myitems.builder.handler.HandlerTask;

public class TaskPassiveEffect extends HandlerTask implements Runnable
{
    public TaskPassiveEffect(final MyItems plugin) {
        super(plugin);
    }
    
    @Override
    public void run() {
        final GameManager gameManager = this.plugin.getGameManager();
        final PassiveEffectManager passiveEffectManager = gameManager.getPassiveEffectManager();
        final MainConfig mainConfig = MainConfig.getInstance();
        final boolean enableGradeCalculation = mainConfig.isPassiveEnableGradeCalculation();
        passiveEffectManager.loadPassiveEffect(enableGradeCalculation);
    }
}
