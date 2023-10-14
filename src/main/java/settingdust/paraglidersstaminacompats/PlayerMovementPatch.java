package settingdust.paraglidersstaminacompats;

import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Unique;
import tictim.paraglider.capabilities.PlayerMovement;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;

public interface PlayerMovementPatch {
    @Unique
    @NotNull PlayerPatch<? extends Player> paraglidersStaminaCompats$getPlayerPatch();
}
