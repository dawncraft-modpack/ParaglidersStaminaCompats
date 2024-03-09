package settingdust.paraglidersstaminacompats.mixin.syncparagliders.wom;

import com.llamalad7.mixinextras.sugar.Local;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import reascer.wom.skill.weaponpassive.TormentPassiveSkill;
import settingdust.paraglidersstaminacompats.PlayerPatchMovement;
import yesman.epicfight.skill.SkillContainer;

@Mixin(TormentPassiveSkill.class)
public class TormentPassiveSkillMixin {
    @ModifyVariable(method = "updateContainer", at = @At(value = "STORE"), name = "staminaRegen", remap = false)
    private float paraglidersStaminaCompats$makeStaminaRegenNonZero(float value, SkillContainer container) {
        return (float) (value
                + ((PlayerPatchMovement) container.getExecuter())
                        .paraglidersStaminaCompats$getPlayerMovement()
                        .getState()
                        .doubleChange());
    }

    @ModifyVariable(method = "updateContainer", at = @At(value = "LOAD", ordinal = 0), name = "staminaRegen", remap = false)
    private float paraglidersStaminaCompats$avoidStaminaRegenZero(float value) {
        return value == 0 ? 1F : value;
    }

    @ModifyVariable(method = "updateContainer", at = @At(value = "STORE"), name = "regenStandbyTime", remap = false)
    private int paraglidersStaminaCompats$avoidStaminaRegenZero(
            int value, @Local(name = "staminaRegen") float staminaRegen) {
        return staminaRegen == 0 ? 900 : value;
    }
}
