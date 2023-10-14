package settingdust.paraglidersstaminacompats.morestamina;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.ShieldBlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import settingdust.paraglidersstaminacompats.ParaglidersStaminaCompats;
import settingdust.paraglidersstaminacompats.ParaglidersStaminaCompatsConfig;
import tictim.paraglider.capabilities.Caps;
import tictim.paraglider.capabilities.PlayerMovement;

@Mod.EventBusSubscriber(modid = ParaglidersStaminaCompats.MOD_ID)
public class ShieldListener {
    @SubscribeEvent
    public void onShieldBlock(ShieldBlockEvent event) {
        if (!(event.getEntityLiving() instanceof Player player)) return;
        PlayerMovement playerMovement =
                player.getCapability(Caps.playerMovement, null).resolve().orElseThrow();
        playerMovement.takeStamina(ParaglidersStaminaCompatsConfig.SHIELD_BLOCK_CONSUMPTION.get(), false, false);
        playerMovement.setRecoveryDelay(30);
    }
}
