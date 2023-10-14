package settingdust.paraglidersstaminacompats.mixin.morestamina;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.UseAnim;
import net.minecraftforge.event.entity.living.LivingEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import settingdust.paraglidersstaminacompats.ParaglidersStaminaCompatsConfig;
import settingdust.paraglidersstaminacompats.PlayerPatchMovement;
import tictim.paraglider.capabilities.PlayerMovement;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;

@Mixin(PlayerPatch.class)
public abstract class MixinPlayerPatch extends LivingEntityPatch<Player> {

    @Shadow(remap = false)
    public abstract boolean isChargingSkill();

    @Shadow(remap = false)
    public abstract boolean hasStamina(float amount);

    @Inject(method = "tick", at = @At("HEAD"), remap = false)
    private void paraglidersStaminaCompats$disableRegenInActionCharging(
            LivingEvent.LivingUpdateEvent event, CallbackInfo ci) {
        PlayerMovement playerMovement = ((PlayerPatchMovement) this).paraglidersStaminaCompats$getPlayerMovement();
        if (state.inaction() || !state.canBasicAttack() || isChargingSkill()) playerMovement.setRecoveryDelay(30);
        var result = 0.0;
        if (isChargingSkill()) {
            result += ParaglidersStaminaCompatsConfig.CHARGING_CONSUMPTION.get();
        }

        var consuming = false;
        if (original.isUsingItem()) {
            UseAnim useAnim = original.getUseItem().getUseAnimation();
            if (useAnim == UseAnim.BOW || useAnim == UseAnim.SPEAR || useAnim == UseAnim.CROSSBOW) consuming = true;
        }
        if (consuming) {
            result += ParaglidersStaminaCompatsConfig.AIMING_CONSUMPTION.get();
        }

        if (result > 0) {
            if (hasStamina((float) result)) {
                playerMovement.takeStamina(result, false, false);
                playerMovement.setRecoveryDelay(30);
            } else cancelAnyAction();
        }
    }
}
