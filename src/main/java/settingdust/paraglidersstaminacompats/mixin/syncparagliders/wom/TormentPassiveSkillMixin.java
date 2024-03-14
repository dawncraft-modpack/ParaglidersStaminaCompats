package settingdust.paraglidersstaminacompats.mixin.syncparagliders.wom;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import reascer.wom.skill.weaponpassive.TormentPassiveSkill;
import yesman.epicfight.skill.SkillContainer;

@Mixin(TormentPassiveSkill.class)
public class TormentPassiveSkillMixin {
    @ModifyVariable(method = "updateContainer", at = @At(value = "STORE"), name = "staminaRegen", remap = false)
    private float paraglidersStaminaCompats$makeStaminaRegenNonZero(float value, SkillContainer container) {
        return 1F;
    }
}
