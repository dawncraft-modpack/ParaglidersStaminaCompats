package settingdust.paraglidersstaminacompats.mixin.epicfight.syncparagliders;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.entity.living.LivingEvent;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import settingdust.paraglidersstaminacompats.ParaglidersStaminaCompatsConfig;
import settingdust.paraglidersstaminacompats.epicfight.PlayerPatchMovement;
import tictim.paraglider.capabilities.Caps;
import tictim.paraglider.capabilities.PlayerMovement;
import tictim.paraglider.capabilities.ServerPlayerMovement;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;

@Mixin(value = PlayerPatch.class)
public abstract class MixinPlayerPatch<T extends Player> extends LivingEntityPatch<T> implements PlayerPatchMovement {
    @Shadow(remap = false)
    public abstract float getStamina();

    @Shadow(remap = false)
    protected int chargingAmount;

    @Shadow(remap = false)
    public abstract boolean isChargingSkill();

    @Shadow(remap = false)
    public abstract void resetSkillCharging();

    @Shadow(remap = false)
    public abstract boolean hasStamina(float amount);

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
        paraglidersStaminaCompats$playerMovement = LazyOptional.of(
                () -> player.getCapability(Caps.playerMovement).resolve().orElseThrow());
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
        cir.setReturnValue((float) playerMovement.getMaxStamina());
        cir.cancel();
    }

    @Inject(method = "getStamina", at = @At("HEAD"), cancellable = true, remap = false)
    private void paraglidersStaminaCompats$getStamina(CallbackInfoReturnable<Float> cir) {
        PlayerMovement playerMovement = paraglidersStaminaCompats$getPlayerMovement();
        cir.setReturnValue(playerMovement.isDepleted() ? 0 : (float) playerMovement.getStamina());
        cir.cancel();
    }

    @Inject(method = "setStamina", at = @At("HEAD"), cancellable = true, remap = false)
    private void paraglidersStaminaCompats$setStamina(float value, CallbackInfo ci) {
        PlayerMovement playerMovement = paraglidersStaminaCompats$getPlayerMovement();
        playerMovement.setStamina((int) value);
        if (playerMovement.getRecoveryDelay() <= 0) playerMovement.setRecoveryDelay(30);
        if (playerMovement instanceof ServerPlayerMovement serverPlayerMovement) {
            serverPlayerMovement.movementNeedsSync = true;
        }
        ci.cancel();
    }

    @Inject(method = "hasStamina", at = @At("HEAD"), cancellable = true, remap = false)
    private void paraglidersStaminaCompats$hasStamina(float amount, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(getStamina() >= amount);
        cir.cancel();
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
                                    "Lyesman/epicfight/world/capabilities/entitypatch/player/PlayerPatch;getStamina()F"), remap = false)
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
                                    "Lyesman/epicfight/world/capabilities/entitypatch/player/PlayerPatch;getMaxStamina()F"), remap = false)
    private float paraglidersStaminaCompats$makeMaxStaminaZero(PlayerPatch<T> instance) {
        return 0;
    }

    @Unique
    private double paraglidersStaminaCompats$queuedStamina = 0.0;

    @Inject(method = "tick", at = @At("HEAD"), remap = false)
    private void paraglidersStaminaCompats$disableRegenInActionCharging(
            LivingEvent.LivingUpdateEvent event, CallbackInfo ci) {
        if (state.inaction() || !state.canBasicAttack() || isChargingSkill())
            paraglidersStaminaCompats$getPlayerMovement().setRecoveryDelay(30);
        if (isChargingSkill()) {
            Double chargingConsumption = ParaglidersStaminaCompatsConfig.CHARGING_CONSUMPTION.get();
            paraglidersStaminaCompats$queuedStamina += chargingConsumption;
            if (paraglidersStaminaCompats$queuedStamina > 1) {
                double floored = Math.floor(paraglidersStaminaCompats$queuedStamina);
                if (hasStamina((float) floored)) {
                    paraglidersStaminaCompats$queuedStamina -= floored;
                    paraglidersStaminaCompats$getPlayerMovement().takeStamina((int) floored, false, false);
                } else cancelAnyAction();
            }
        }
    }
}
