package settingdust.paraglidersstaminacompats.mixin.staminawheel;

import com.llamalad7.mixinextras.sugar.Local;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tictim.paraglider.capabilities.PlayerMovement;
import tictim.paraglider.capabilities.PlayerState;
import tictim.paraglider.client.InGameStaminaWheelRenderer;
import tictim.paraglider.client.StaminaWheelRenderer;
import tictim.paraglider.utils.Color;

@Mixin(value = InGameStaminaWheelRenderer.class, remap = false)
public abstract class MixinInGameStaminaWheelRenderer extends StaminaWheelRenderer {
    @Shadow
    private int prevStamina;

    @Unique
    private int paraglidersStaminaCompats$deltaStamina = 0;

    @Unique
    private static final int paraglidersStaminaCompats$deltaRenderFactor = 1;

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

    @Inject(
            method = "makeWheel",
            at =
                    @At(
                            value = "INVOKE",
                            target = "Ltictim/paraglider/capabilities/PlayerState;isConsume()Z",
                            shift = At.Shift.BEFORE))
    private void paraglidersStaminaCompats$applyFinalDeltaWhenNoConsume(
            PlayerMovement playerMovement,
            CallbackInfo ci,
            @Local(name = "stamina") int stamina,
            @Local(ordinal = 0) WheelLevel level,
            @Local Color color,
            @Local PlayerState state) {
        if (!state.isConsume() && playerMovement.getRecoveryDelay() > 0 && paraglidersStaminaCompats$deltaStamina > 0) {
            this.addWheel(
                    level,
                    level.getProportion(stamina),
                    level.getProportion(stamina
                            + paraglidersStaminaCompats$deltaStamina / paraglidersStaminaCompats$deltaRenderFactor),
                    color);
        }
        if (playerMovement.getRecoveryDelay() == 0) paraglidersStaminaCompats$deltaStamina = 0;
        if (paraglidersStaminaCompats$deltaStamina > 0) paraglidersStaminaCompats$deltaStamina--;
    }
}
