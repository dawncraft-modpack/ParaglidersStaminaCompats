package settingdust.paraglidersstaminacompats;

import com.google.common.collect.Maps;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.capabilities.item.WeaponCategory;

import java.util.Map;
import java.util.Objects;

public class ParaglidersStaminaCompatsConfig {
    public static final ForgeConfigSpec.DoubleValue CHARGING_CONSUMPTION;

    public static final ForgeConfigSpec.DoubleValue AIMING_CONSUMPTION;

    public static final ForgeConfigSpec.DoubleValue SHIELD_BLOCK_CONSUMPTION;
    public static final ForgeConfigSpec.BooleanValue WEAPON_INNATE_SKILL_CONSUMPTION;

    public static final Map<WeaponCategory, ForgeConfigSpec.DoubleValue> WEAPON_CATEGORIES_COMMON_CONSUMPTION =
            Maps.newHashMap();

    static {
        ForgeConfigSpec.Builder serverConfig = new ForgeConfigSpec.Builder();
        serverConfig.push("EpicFight");
        serverConfig.push("Stamina consumption");
        WEAPON_INNATE_SKILL_CONSUMPTION = serverConfig
                .comment(
                        "Whether the skills that consuming weapon innate consume stamina defined in the weapon category")
                .define("weapon_innate_skill_consumption", true);
        CHARGING_CONSUMPTION = serverConfig
                .comment("How much is the charging consuming stamina every tick(20t = 1s)")
                .defineInRange("charging", 1, 0.0, Integer.MAX_VALUE);
        AIMING_CONSUMPTION = serverConfig
                .comment("How much is the aiming consuming stamina every tick(20t = 1s)")
                .defineInRange("aiming", 1, 0.0, Integer.MAX_VALUE);
        SHIELD_BLOCK_CONSUMPTION = serverConfig
                .comment("Consumption when blocking with shield")
                .defineInRange("shield_blocking", 1, 0.0, Integer.MAX_VALUE);
        serverConfig.push("Weapon categories");
        serverConfig
                .comment(
                        "Define consumption for every weapon category. It's sum with the current skill consumption. This option will be overwrite by weapon's stamina cost attribute from data pack")
                .push("Common");
        Objects.requireNonNull(CapabilityItem.WeaponCategories.NOT_WEAPON);
        for (WeaponCategory category : WeaponCategory.ENUM_MANAGER.universalValues()) {
            WEAPON_CATEGORIES_COMMON_CONSUMPTION.put(
                    category,
                    serverConfig.defineInRange(category.toString(), 2.0, Integer.MIN_VALUE, Integer.MAX_VALUE));
        }
        serverConfig.pop();
        serverConfig.pop();
        serverConfig.pop();
        serverConfig.pop();
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, serverConfig.build());
    }
}
