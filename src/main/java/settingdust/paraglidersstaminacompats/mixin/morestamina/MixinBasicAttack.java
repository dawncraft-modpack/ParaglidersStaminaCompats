package settingdust.paraglidersstaminacompats.mixin.morestamina;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import yesman.epicfight.skill.BasicAttack;
import yesman.epicfight.skill.Skill;

@Mixin(value = BasicAttack.class ,remap = false)
public class MixinBasicAttack {
    @Redirect(
            method = "createBasicAttackBuilder",
            at =
                    @At(
                            value = "FIELD",
                            target =
                                    "Lyesman/epicfight/skill/Skill$Resource;NONE:Lyesman/epicfight/skill/Skill$Resource;"))
    private static Skill.Resource paraglidersStaminaCompats$usingStamina() {
        return Skill.Resource.STAMINA;
    }
}
