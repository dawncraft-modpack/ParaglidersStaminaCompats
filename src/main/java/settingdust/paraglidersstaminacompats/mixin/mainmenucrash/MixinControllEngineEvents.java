package settingdust.paraglidersstaminacompats.mixin.mainmenucrash;

import com.llamalad7.mixinextras.injector.WrapWithCondition;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import yesman.epicfight.client.events.engine.ControllEngine;

@Mixin(ControllEngine.Events.class)
public class MixinControllEngineEvents {
    @WrapWithCondition(method = "clientTickEndEvent", at = @At(value = "INVOKE", target = "Lyesman/epicfight/client/events/engine/ControllEngine;tick()V"), remap = false)
    private static boolean paraglidersStaminaCompats$confirmConnection(ControllEngine engine) {
        return Minecraft.getInstance().getConnection() != null;
    }
}
