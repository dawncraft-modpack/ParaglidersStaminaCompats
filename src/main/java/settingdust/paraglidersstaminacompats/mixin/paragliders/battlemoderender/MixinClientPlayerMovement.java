package settingdust.paraglidersstaminacompats.mixin.paragliders.battlemoderender;

import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.util.LazyOptional;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tictim.paraglider.capabilities.ClientPlayerMovement;
import tictim.paraglider.capabilities.RemotePlayerMovement;
import yesman.epicfight.client.world.capabilites.entitypatch.player.LocalPlayerPatch;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;

@Mixin(value = ClientPlayerMovement.class, remap = false)
public abstract class MixinClientPlayerMovement extends RemotePlayerMovement {
    @Unique
    private boolean paraglidersStaminaCompats$isBattling = false;

    @Unique
    private LazyOptional<LocalPlayerPatch> paraglidersStaminaCompats$playerPatch;

    private MixinClientPlayerMovement(Player player) {
        super(player);
    }

    @Inject(method = "<init>", at = @At("RETURN"))
    private void paraglidersStaminaCompats$initPlayerPatch(Player player, CallbackInfo ci) {
        paraglidersStaminaCompats$playerPatch = LazyOptional.of(() -> EpicFightCapabilities.getEntityPatch(player, LocalPlayerPatch.class));
    }

    /**
     * For fix the wrong render of paragliders when in battle mode
     */
    @Inject(method = "update", at = @At("TAIL"))
    private void paraglidersStaminaCompats$handleBattleModeWhenParagliding(CallbackInfo ci) {
        LocalPlayerPatch playerPatch = paraglidersStaminaCompats$playerPatch.resolve().orElseThrow();
        if (paraglidersStaminaCompats$isBattling && !isParagliding()) {
            paraglidersStaminaCompats$isBattling = false;
            playerPatch.toBattleMode(true);
        }
        if (isParagliding() && playerPatch.getPlayerMode().equals(PlayerPatch.PlayerMode.BATTLE)) {
            paraglidersStaminaCompats$isBattling = true;
            playerPatch.toMiningMode(true);
        }
    }
}
