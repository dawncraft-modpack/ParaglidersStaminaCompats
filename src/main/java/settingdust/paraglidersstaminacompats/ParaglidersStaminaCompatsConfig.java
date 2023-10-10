package settingdust.paraglidersstaminacompats;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;

public class ParaglidersStaminaCompatsConfig {
    public static final ForgeConfigSpec.DoubleValue CHARGING_CONSUMPTION;

    static {
        ForgeConfigSpec.Builder serverConfig = new ForgeConfigSpec.Builder();
        serverConfig.push("EpicFight");
        serverConfig.push("Stamina");
        CHARGING_CONSUMPTION = serverConfig
                .comment("How much is the charging consuming stamina every tick(20t = 1s)")
                .defineInRange("chargingConsumption", 1, 0.0, 100.0);
        serverConfig.pop();
        serverConfig.pop();
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, serverConfig.build());
    }
}
