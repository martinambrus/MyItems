// 
// Decompiled by Procyon v0.5.36
// 

package com.praya.myitems.utility.main;

import java.util.Iterator;
import org.bukkit.Material;
import org.bukkit.Location;
import com.praya.agarthalib.utility.BlockUtil;

public class AntiBugUtil
{
    public static void antiBugCustomStats() {
        for (final Location loc : BlockUtil.getDataLoc()) {
            loc.getBlock().setType(Material.AIR);
        }
    }
}
