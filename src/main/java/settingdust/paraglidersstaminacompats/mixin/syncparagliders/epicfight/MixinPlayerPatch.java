package settingdust.paraglidersstaminacompats.mixin.syncparagliders.epicfight;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import settingdust.paraglidersstaminacompats.ParaglidersStaminaCompats;
import settingdust.paraglidersstaminacompats.PlayerPatchMovement;
import settingdust.paraglidersstaminacompats.morestamina.C2SMakeDepleted;
import tictim.paraglider.capabilities.PlayerMovement;
import tictim.paraglider.capabilities.ServerPlayerMovement;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;

@Mixin(value = PlayerPatch.class)
public abstract class MixinPlayerPatch<T extends Player> extends LivingEntityPatch<T> implements PlayerPatchMovement {
    @Shadow(remap = false)
    public abstract float getStamina();

    @Unique
    private LazyOptional<PlayerMovement> paraglidersStaminaCompats$playerMovement;

    @Redirect(
            method = "onConstructed*",
            at =
                    @At(
                            value = "INVOKE",
                            target =
                                    "Lnet/minecraft/network/syncher/SynchedEntityData;define(Lnet/minecraft/network/syncher/EntityDataAccessor;Ljava/lang/Object;)V"))
    private void paraglidersStaminaCompats$removeStaminaRegistering(
            SynchedEntityData instance, EntityDataAccessor<?> p_135373_, Object p_135374_) {}

    @Inject(method = "onConstructed*", at = @At("RETURN"), remap = false)
    private void paraglidersStaminaCompats$initPlayerMovement(T player, CallbackInfo ci) {
        paraglidersStaminaCompats$playerMovement = LazyOptional.of(() -> PlayerMovement.of(player));
    }

    @ModifyConstant(method = "initAttributes", constant = @Constant(doubleValue = 15.0), remap = false)
    private double paraglidersStaminaCompats$removeDefaultMaxStamina(double constant) {
        return 0.0;
    }

    @ModifyConstant(method = "initAttributes", constant = @Constant(doubleValue = 1.0), remap = false)
    private double paraglidersStaminaCompats$removeDefaultStaminaRegen(double constant) {
        return 0.0;
    }

    /**
     * For removing default stamina regen
     */
    @ModifyVariable(method = "serverTick", at = @At(value = "STORE"), name = "staminaRegen", remap = false)
    private float paraglidersStaminaCompats$makeStaminaRegenNonZero(float value) {
        return 1F;
    }

    @Unique
    @NotNull
    @Override
    public PlayerMovement paraglidersStaminaCompats$getPlayerMovement() {
        return paraglidersStaminaCompats$playerMovement.orElseThrow(
                () -> new IllegalStateException("PlayerMovement not initialized for " + original.getName()));
    }

    @Inject(method = "getMaxStamina", at = @At("HEAD"), cancellable = true, remap = false)
    private void paraglidersStaminaCompats$getMaxStamina(CallbackInfoReturnable<Float> cir) {
        PlayerMovement playerMovement = paraglidersStaminaCompats$getPlayerMovement();
        cir.setReturnValue((float) playerMovement.getDoubleMaxStamina());
    }

    @Inject(method = "getStamina", at = @At("HEAD"), cancellable = true, remap = false)
    private void paraglidersStaminaCompats$getStamina(CallbackInfoReturnable<Float> cir) {
        PlayerMovement playerMovement = paraglidersStaminaCompats$getPlayerMovement();
        cir.setReturnValue(playerMovement.isDepleted() ? 0 : (float) playerMovement.getDoubleStamina());
    }

    @Inject(method = "setStamina", at = @At("HEAD"), cancellable = true, remap = false)
    private void paraglidersStaminaCompats$setStamina(float value, CallbackInfo ci) {
        PlayerMovement playerMovement = paraglidersStaminaCompats$getPlayerMovement();
        playerMovement.setStamina(value);
        if (playerMovement.getRecoveryDelay() <= 0) playerMovement.setRecoveryDelay(30);
        if (playerMovement instanceof ServerPlayerMovement serverPlayerMovement) {
            serverPlayerMovement.movementNeedsSync = true;
        }
        ci.cancel();
    }

    @Inject(method = "hasStamina", at = @At("HEAD"), cancellable = true, remap = false)
    private void paraglidersStaminaCompats$hasStamina(float amount, CallbackInfoReturnable<Boolean> cir) {
        boolean result = getStamina() >= amount;
        if (!result) {
            PlayerMovement playerMovement = paraglidersStaminaCompats$getPlayerMovement();
            if (!playerMovement.isDepleted()) {
                playerMovement.setDepleted(true);
                if (playerMovement instanceof ServerPlayerMovement serverPlayerMovement) {
                    serverPlayerMovement.movementNeedsSync = true;
                } else {
                    ParaglidersStaminaCompats.NETWORK_MANAGER.sendToServer(new C2SMakeDepleted());
                }
            }
        }
        cir.setReturnValue(result);
    }

    /**
     * For disable regen
     */
    @Redirect(
            method = "serverTick",
            at =
                    @At(
                            value = "INVOKE",
                            target =
                                    "Lyesman/epicfight/world/capabilities/entitypatch/player/PlayerPatch;getStamina()F"),
            remap = false)
    private float paraglidersStaminaCompats$makeStaminaZero(PlayerPatch<T> instance) {
        return 0;
    }

    /**
     * For disable regen
     */
    @Redirect(
            method = "serverTick",
            at =
                    @At(
                            value = "INVOKE",
                            target =
                                    "Lyesman/epicfight/world/capabilities/entitypatch/player/PlayerPatch;getMaxStamina()F"),
            remap = false)
    private float paraglidersStaminaCompats$makeMaxStaminaZero(PlayerPatch<T> instance) {
        return 0;
    }
}
