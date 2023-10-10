package settingdust.paraglidersstaminacompats.mixin.paragliders.wheel;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tictim.paraglider.capabilities.PlayerMovement;
import tictim.paraglider.client.InGameStaminaWheelRenderer;
import tictim.paraglider.client.StaminaWheelRenderer;
import tictim.paraglider.utils.Color;

@Mixin(value = InGameStaminaWheelRenderer.class, remap = false)
public abstract class InGameStaminaWheelRendererMixin extends StaminaWheelRenderer {
    @Shadow
    private int prevStamina;

    @Unique
    private int paraglidersStaminaCompats$deltaStamina = 0;

    @Unique
    private static final int paraglidersStaminaCompats$deltaRenderFactor = 6;

    @Inject(
            method = "makeWheel",
            at =
                    @At(
                            value = "INVOKE",
                            target = "Ltictim/paraglider/capabilities/PlayerMovement;getMaxStamina()I",
                            shift = At.Shift.BEFORE),
            remap = false)
    public void paraglidersStaminaCompats$getDelta(
            PlayerMovement movement, CallbackInfo ci, @Local(name = "stamina") int stamina) {
        if (!movement.getState().isConsume() && prevStamina > stamina)
            paraglidersStaminaCompats$deltaStamina +=
                    (prevStamina - stamina) * paraglidersStaminaCompats$deltaRenderFactor;
    }

    @ModifyVariable(method = "makeWheel", at = @At(value = "LOAD", ordinal = 7), name = "stamina")
    private int paraglidersStaminaCompats$applyFinalDelta(int stamina) {
        if (paraglidersStaminaCompats$deltaStamina > 0) {
            paraglidersStaminaCompats$deltaStamina--;
        }
        return stamina;
    }

    @ModifyExpressionValue(
            method = "makeWheel",
            at = @At(value = "INVOKE", target = "Ltictim/paraglider/capabilities/PlayerState;isConsume()Z"))
    private boolean paraglidersStaminaCompats$applyFinalDeltaWhenNoConsume(
            boolean isConsume,
            @Local(name = "stamina") int stamina,
            @Local(ordinal = 0) StaminaWheelRenderer.WheelLevel level,
            @Local Color color) {
        if (!isConsume && paraglidersStaminaCompats$deltaStamina > 0) {
            this.addWheel(
                    level,
                    level.getProportion(stamina),
                    level.getProportion(stamina
                            + paraglidersStaminaCompats$deltaStamina / paraglidersStaminaCompats$deltaRenderFactor),
                    color);
            paraglidersStaminaCompats$deltaStamina--;
        }
        return isConsume;
    }
}
