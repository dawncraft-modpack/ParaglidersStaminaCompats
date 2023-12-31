package settingdust.paraglidersstaminacompats.mixin.syncparagliders.paraglider;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import settingdust.paraglidersstaminacompats.PlayerMovementPatch;
import tictim.paraglider.capabilities.PlayerMovement;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.entity.ai.attribute.EpicFightAttributes;

@Mixin(PlayerMovement.class)
public class MixinPlayerMovement implements PlayerMovementPatch {
    @Shadow(remap = false)
    @Final
    public Player player;

    @Unique
    private LazyOptional<PlayerPatch<? extends Player>> paraglidersStaminaCompats$playerPatch;

    @ModifyExpressionValue(
            method = "updateStamina",
            at = @At(value = "INVOKE", target = "Ltictim/paraglider/capabilities/PlayerState;doubleChange()D", ordinal = 2), remap = false)
    private double paraglidersStaminaCompats$applyEFRegen(double original) {
        return original + player.getAttributeValue(EpicFightAttributes.STAMINA_REGEN.get());
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void paraglidersStaminaCompats$initPlayerPatch(Player player, CallbackInfo ci) {
        paraglidersStaminaCompats$playerPatch =
                LazyOptional.of(() -> EpicFightCapabilities.getEntityPatch(player, PlayerPatch.class));
    }

    @Override
    public @NotNull PlayerPatch<? extends Player> paraglidersStaminaCompats$getPlayerPatch() {
        return paraglidersStaminaCompats$playerPatch.resolve().orElseThrow();
    }
}
