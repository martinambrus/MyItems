// 
// Decompiled by Procyon v0.5.36
// 

package api.praya.myitems.builder.item;

import java.util.Collection;
import org.bukkit.Material;
import com.praya.myitems.manager.game.ItemTierManager;
import com.praya.myitems.manager.game.ItemTypeManager;
import com.praya.myitems.manager.plugin.PlaceholderManager;
import com.praya.myitems.manager.game.GameManager;
import com.praya.myitems.manager.plugin.PluginManager;
import core.praya.agarthalib.bridge.unity.Bridge;
import core.praya.agarthalib.enums.main.TagsAttribute;
import org.bukkit.enchantments.Enchantment;
import com.praya.agarthalib.utility.EquipmentUtil;
import org.bukkit.entity.Player;
import api.praya.myitems.builder.lorestats.LoreStatsModifier;
import api.praya.myitems.builder.lorestats.LoreStatsUniversal;
import api.praya.myitems.builder.lorestats.LoreStatsArmor;
import api.praya.myitems.builder.lorestats.LoreStatsWeapon;
import api.praya.myitems.builder.lorestats.LoreStatsEnum;
import com.praya.agarthalib.utility.MathUtil;
import com.praya.agarthalib.utility.TextUtil;
import core.praya.agarthalib.enums.branch.MaterialEnum;
import com.praya.agarthalib.utility.MapUtil;
import org.bukkit.plugin.java.JavaPlugin;
import com.praya.myitems.MyItems;
import org.bukkit.inventory.ItemStack;
import java.util.Iterator;
import core.praya.agarthalib.enums.main.Slot;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ItemGenerator
{
    private final String id;
    private final String displayName;
    private final boolean unbreakable;
    private final List<String> lores;
    private final List<String> flags;
    private final HashMap<ItemType, ItemGeneratorType> mapType;
    private final HashMap<ItemTier, ItemGeneratorTier> mapTier;
    
    public ItemGenerator(final String id, final String displayName, final boolean unbreakable, final List<String> lores, final List<String> flags, final HashMap<ItemType, ItemGeneratorType> mapType, final HashMap<ItemTier, ItemGeneratorTier> mapTier) {
        this.id = id;
        this.displayName = displayName;
        this.unbreakable = unbreakable;
        this.lores = lores;
        this.flags = flags;
        this.mapType = mapType;
        this.mapTier = mapTier;
    }
    
    @Deprecated
    public ItemGenerator(final String id, final String displayName, final List<String> lores, final HashMap<ItemType, ItemGeneratorType> mapType, final HashMap<ItemTier, ItemGeneratorTier> mapTier) {
        this.id = id;
        this.displayName = displayName;
        this.unbreakable = false;
        this.lores = lores;
        this.flags = new ArrayList<String>();
        this.mapType = mapType;
        this.mapTier = mapTier;
    }
    
    public final String getId() {
        return this.id;
    }
    
    public final String getDisplayName() {
        return this.displayName;
    }
    
    public final boolean isUnbreakable() {
        return this.unbreakable;
    }
    
    public final List<String> getLores() {
        return this.lores;
    }
    
    public final List<String> getFlags() {
        return this.flags;
    }
    
    public final HashMap<ItemType, ItemGeneratorType> getMapType() {
        return this.mapType;
    }
    
    public final HashMap<ItemTier, ItemGeneratorTier> getMapTier() {
        return this.mapTier;
    }
    
    public final HashMap<String, Integer> getMapPossibilityType() {
        return this.getMapPossibilityType(null);
    }
    
    public final HashMap<String, Integer> getMapPossibilityType(final Slot slot) {
        final HashMap<String, Integer> mapPossibilityType = new HashMap<String, Integer>();
        for (final ItemType key : this.getMapType().keySet()) {
            final String id = key.getId();
            final Slot slotDefault = key.getDefaultSlot();
            final int possibility = this.getMapType().get(key).getPossibility();
            if (slot == null) {
                mapPossibilityType.put(id, possibility);
            }
            else {
                if (!slot.equals((Object)slotDefault)) {
                    continue;
                }
                mapPossibilityType.put(id, possibility);
            }
        }
        return mapPossibilityType;
    }
    
    public final HashMap<String, Integer> getMapPossibilityTier() {
        final HashMap<String, Integer> mapPossibilityTier = new HashMap<String, Integer>();
        for (final ItemTier key : this.getMapTier().keySet()) {
            final String id = key.getId();
            final int possibility = this.getMapTier().get(key).getPossibility();
            mapPossibilityTier.put(id, possibility);
        }
        return mapPossibilityTier;
    }
    
    public final ItemStack generateItem() {
        return this.generateItem(null);
    }
    
    public final ItemStack generateItem(final Slot slot) {
        final MyItems plugin = (MyItems)JavaPlugin.getPlugin((Class)MyItems.class);
        final PluginManager pluginManager = plugin.getPluginManager();
        final GameManager gameManager = plugin.getGameManager();
        final PlaceholderManager placeholderManager = pluginManager.getPlaceholderManager();
        final ItemTypeManager itemTypeManager = gameManager.getItemTypeManager();
        final ItemTierManager itemTierManager = gameManager.getItemTierManager();
        final HashMap<String, Integer> mapPossibilityType = this.getMapPossibilityType(slot);
        final HashMap<String, Integer> mapPossibilityTier = this.getMapPossibilityTier();
        if (!mapPossibilityType.isEmpty() && !mapPossibilityTier.isEmpty()) {
            final String idType = MapUtil.getRandomIdByInteger((HashMap)mapPossibilityType);
            final String idTier = MapUtil.getRandomIdByInteger((HashMap)mapPossibilityTier);
            final ItemType itemType = itemTypeManager.getItemType(idType);
            final ItemTier itemTier = itemTierManager.getItemTier(idTier);
            if (itemType != null && itemType != null) {
                final Material material = itemType.getMaterial();
                final short data = itemType.getData();
                final MaterialEnum materialEnum = MaterialEnum.getMaterialEnum(material, (int)data);
                if (materialEnum != null) {
                    final boolean shiny = itemType.isShiny();
                    final ItemGeneratorType itemTypeProperties = this.getMapType().get(itemType);
                    final ItemGeneratorTier itemTierProperties = this.getMapTier().get(itemTier);
                    final LoreStatsModifier typeModifier = itemType.getStatsModifier();
                    final LoreStatsModifier tierModifier = itemTier.getStatsModifier();
                    final List<String> description = itemTypeProperties.getDescription();
                    final List<String> names = itemTypeProperties.getNames();
                    final List<String> additionalLores = itemTierProperties.getAdditionalLores();
                    final Collection<Enchantment> enchantments = itemType.getEnchantments();
                    final Collection<Slot> allSlotNBT = itemType.getAllSlotNBT();
                    final int random = (int)(Math.random() * names.size());
                    final String divider = "\n";
                    final String lineDescription = TextUtil.convertListToString((List)description, "\n");
                    final String lineLore = TextUtil.convertListToString((List)this.lores, "\n");
                    final String lineAdditionalLores = TextUtil.convertListToString((List)additionalLores, "\n");
                    final String name = names.isEmpty() ? null : names.get(MathUtil.limitInteger(random, 0, names.size() - 1));
                    final HashMap<String, String> map = new HashMap<String, String>();
                    final HashMap<LoreStatsEnum, Double> mapStatsModifier = new HashMap<LoreStatsEnum, Double>();
                    LoreStatsEnum[] values;
                    for (int length = (values = LoreStatsEnum.values()).length, i = 0; i < length; ++i) {
                        final LoreStatsEnum loreStats = values[i];
                        final double typeValue = typeModifier.getModifier(loreStats);
                        final double tierValue = tierModifier.getModifier(loreStats);
                        final double finalValue = typeValue * tierValue;
                        mapStatsModifier.put(loreStats, finalValue);
                    }
                    final double damage = mapStatsModifier.get(LoreStatsEnum.DAMAGE);
                    final double penetration = mapStatsModifier.get(LoreStatsEnum.PENETRATION);
                    final double pvpDamage = mapStatsModifier.get(LoreStatsEnum.PVP_DAMAGE);
                    final double pveDamage = mapStatsModifier.get(LoreStatsEnum.PVE_DAMAGE);
                    final double criticalChance = mapStatsModifier.get(LoreStatsEnum.CRITICAL_CHANCE);
                    final double criticalDamage = mapStatsModifier.get(LoreStatsEnum.CRITICAL_DAMAGE);
                    final double hitRate = mapStatsModifier.get(LoreStatsEnum.HIT_RATE);
                    final double defense = mapStatsModifier.get(LoreStatsEnum.DEFENSE);
                    final double pvpDefense = mapStatsModifier.get(LoreStatsEnum.PVP_DEFENSE);
                    final double pveDefense = mapStatsModifier.get(LoreStatsEnum.PVE_DEFENSE);
                    final double health = mapStatsModifier.get(LoreStatsEnum.HEALTH);
                    final double healthRegen = mapStatsModifier.get(LoreStatsEnum.HEALTH_REGEN);
                    final double staminaMax = mapStatsModifier.get(LoreStatsEnum.STAMINA_MAX);
                    final double staminaRegen = mapStatsModifier.get(LoreStatsEnum.STAMINA_REGEN);
                    final double attackAoERadius = mapStatsModifier.get(LoreStatsEnum.ATTACK_AOE_RADIUS);
                    final double attackAoEDamage = mapStatsModifier.get(LoreStatsEnum.ATTACK_AOE_DAMAGE);
                    final double blockAmount = mapStatsModifier.get(LoreStatsEnum.BLOCK_AMOUNT);
                    final double blockRate = mapStatsModifier.get(LoreStatsEnum.BLOCK_RATE);
                    final double dodgeRate = mapStatsModifier.get(LoreStatsEnum.DODGE_RATE);
                    final double durability = mapStatsModifier.get(LoreStatsEnum.DURABILITY);
                    final double level = mapStatsModifier.get(LoreStatsEnum.LEVEL);
                    final LoreStatsWeapon weaponModifier = new LoreStatsWeapon(damage, penetration, pvpDamage, pveDamage, attackAoERadius, attackAoEDamage, criticalChance, criticalDamage, hitRate);
                    final LoreStatsArmor armorModifier = new LoreStatsArmor(defense, pvpDefense, pveDefense, health, healthRegen, staminaMax, staminaRegen, blockAmount, blockRate, dodgeRate);
                    final LoreStatsUniversal universalModifier = new LoreStatsUniversal(durability, level);
                    final LoreStatsModifier statsModifier = new LoreStatsModifier(weaponModifier, armorModifier, universalModifier);
                    String loreBuilder = TextUtil.placeholder(lineLore, "description", lineDescription);
                    String display = this.displayName;
                    map.put("Name", name);
                    map.put("Type_ID", idType);
                    map.put("Tier_ID", idTier);
                    map.put("Tier_Name", itemTier.getName());
                    map.put("Tier_Prefix", itemTier.getPrefix());
                    display = TextUtil.placeholder((HashMap)map, display);
                    display = TextUtil.colorful(display);
                    loreBuilder = (lineAdditionalLores.isEmpty() ? loreBuilder : (String.valueOf(loreBuilder) + "\n" + lineAdditionalLores));
                    loreBuilder = TextUtil.placeholder((HashMap)map, loreBuilder);
                    loreBuilder = placeholderManager.placeholder(null, loreBuilder, statsModifier);
                    loreBuilder = TextUtil.colorful(loreBuilder);
                    final String[] finalLores = loreBuilder.split("\n");
                    final ItemStack item = EquipmentUtil.createItem(materialEnum, display, 1, finalLores);
                    for (final String flag : this.flags) {
                        EquipmentUtil.addFlag(item, new String[] { flag });
                    }
                    for (final Enchantment enchantment : enchantments) {
                        final int grade = itemType.getEnchantmentGrade(enchantment);
                        EquipmentUtil.addEnchantment(item, enchantment, grade);
                    }
                    for (final Slot slotNBT : allSlotNBT) {
                        final ItemTypeNBT itemTypeNBT = itemType.getSlotNBT(slotNBT);
                        for (final TagsAttribute tagsAttribute : itemTypeNBT.getAllTagsAttribute()) {
                            final double tagsValue = itemTypeNBT.getTagsAttributeValue(tagsAttribute);
                            Bridge.getBridgeTagsNBT().addNBT(item, tagsAttribute, tagsValue, slotNBT);
                        }
                    }
                    if (shiny) {
                        EquipmentUtil.shiny(item);
                    }
                    if (this.unbreakable) {
                        Bridge.getBridgeTagsNBT().setUnbreakable(item, true);
                    }
                    return item;
                }
            }
        }
        return null;
    }
}
