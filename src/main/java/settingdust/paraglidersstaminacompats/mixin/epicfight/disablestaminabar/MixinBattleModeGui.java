package settingdust.paraglidersstaminacompats.mixin.epicfight.disablestaminabar;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import yesman.epicfight.client.gui.BattleModeGui;
import yesman.epicfight.client.world.capabilites.entitypatch.player.LocalPlayerPatch;

@Mixin(value = BattleModeGui.class, remap = false)
public class MixinBattleModeGui {
    @Redirect(method = "renderGui", at = @At(value = "INVOKE", target = "Lyesman/epicfight/client/world/capabilites/entitypatch/player/LocalPlayerPatch;getMaxStamina()F"))
    private float paraglidersStaminaCompats$disableStaminaBar(LocalPlayerPatch instance) {
        return 0;
    }
}
