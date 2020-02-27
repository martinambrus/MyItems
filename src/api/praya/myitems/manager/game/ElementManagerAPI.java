// 
// Decompiled by Procyon v0.5.36
// 

package api.praya.myitems.manager.game;

import com.praya.myitems.manager.game.GameManager;
import com.praya.myitems.manager.game.ElementManager;
import api.praya.myitems.builder.element.ElementPotion;
import api.praya.myitems.builder.element.ElementBoost;
import org.bukkit.inventory.ItemStack;
import api.praya.myitems.builder.element.ElementBoostStats;
import core.praya.agarthalib.enums.main.SlotType;
import java.util.HashMap;
import org.bukkit.entity.LivingEntity;
import api.praya.myitems.builder.element.Element;
import java.util.Collection;
import com.praya.myitems.MyItems;
import com.praya.myitems.builder.handler.HandlerManager;

public class ElementManagerAPI extends HandlerManager
{
    protected ElementManagerAPI(final MyItems plugin) {
        super(plugin);
    }
    
    public final Collection<String> getElements() {
        return this.getElementManager().getElements();
    }
    
    public final Collection<Element> getElementBuilds() {
        return this.getElementManager().getElementBuilds();
    }
    
    public final Element getElementBuild(final String element) {
        return this.getElementManager().getElementBuild(element);
    }
    
    public final String getTextElement(final String element, final double value) {
        return this.getElementManager().getTextElement(element, value);
    }
    
    public final boolean isExists(final String element) {
        return this.getElementManager().isExists(element);
    }
    
    public final HashMap<String, Double> getMapElement(final LivingEntity livingEntity) {
        return this.getElementManager().getMapElement(livingEntity, SlotType.UNIVERSAL, false);
    }
    
    public final HashMap<String, Double> getElementCalculation(final HashMap<String, Double> elementAttacker, final HashMap<String, Double> elementVictims) {
        return this.getElementManager().getElementCalculation(elementAttacker, elementVictims);
    }
    
    public final void applyElementPotion(final LivingEntity attacker, final LivingEntity victims, final HashMap<String, Double> mapElement) {
        this.getElementManager().applyElementPotion(attacker, victims, mapElement);
    }
    
    public final ElementBoostStats getElementBoostStats(final HashMap<String, Double> mapElement) {
        return this.getElementManager().getElementBoostStats(mapElement);
    }
    
    public final double getElementValue(final ItemStack item, final String element) {
        return this.getElementManager().getElementValue(item, element);
    }
    
    public final double getScaleResistance(final String baseElement, final String targetElement) {
        return this.getElementManager().getScaleResistance(baseElement, targetElement);
    }
    
    public final double getScaleResistance(final Element elementBuild, final String element) {
        return this.getElementManager().getScaleResistance(elementBuild, element);
    }
    
    public final ElementBoost getElementBoostBuild(final String element) {
        return this.getElementManager().getElementBoostBuild(element);
    }
    
    public final ElementPotion getElementPotionBuild(final String element) {
        return this.getElementManager().getElementPotionBuild(element);
    }
    
    private final ElementManager getElementManager() {
        final GameManager gameManager = this.plugin.getGameManager();
        final ElementManager elementManager = gameManager.getElementManager();
        return elementManager;
    }
}
