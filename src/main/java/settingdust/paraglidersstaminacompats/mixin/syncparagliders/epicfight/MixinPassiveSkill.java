package settingdust.paraglidersstaminacompats.mixin.syncparagliders.epicfight;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import yesman.epicfight.skill.passive.PassiveSkill;

@Mixin(value = PassiveSkill.class, remap = false)
public class MixinPassiveSkill {
    @ModifyVariable(method = "setParams", at = @At(value = "STORE"))
    private String paraglidersstaminacompats$redirectEFStaminar(String attribute) {
        if (attribute.equals("epicfight:staminar")) return "paraglider:max_stamina";
        return attribute;
    }
}
