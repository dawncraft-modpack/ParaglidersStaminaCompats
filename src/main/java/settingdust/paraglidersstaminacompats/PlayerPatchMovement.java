package settingdust.paraglidersstaminacompats;

import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Unique;
import tictim.paraglider.capabilities.PlayerMovement;

public interface PlayerPatchMovement {
    @Unique
    @NotNull PlayerMovement paraglidersStaminaCompats$getPlayerMovement();
}
