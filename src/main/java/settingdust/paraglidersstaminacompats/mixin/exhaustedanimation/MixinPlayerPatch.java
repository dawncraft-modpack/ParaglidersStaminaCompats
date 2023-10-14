package settingdust.paraglidersstaminacompats.mixin.exhaustedanimation;

import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import settingdust.paraglidersstaminacompats.exhaustedanimation.ExhaustedAnimations;
import settingdust.paraglidersstaminacompats.exhaustedanimation.ExhaustedMotions;
import yesman.epicfight.api.client.animation.ClientAnimator;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;

/**
 * Take from EpicParagliders
 */
@Mixin(PlayerPatch.class)
public abstract class MixinPlayerPatch<T extends Player> extends LivingEntityPatch<T> {
    @OnlyIn(Dist.CLIENT)
    @Inject(method = "initAnimator", at = @At("HEAD"), remap = false)
    private void paraglidersStaminaCompats$addExhaustedAnimations(ClientAnimator clientAnimator, CallbackInfo ci) {
        clientAnimator.addLivingAnimation(ExhaustedMotions.EXHAUSTED_IDLE, ExhaustedAnimations.EXHAUSTED_IDLE);
        clientAnimator.addLivingAnimation(ExhaustedMotions.EXHAUSTED_WALK, ExhaustedAnimations.EXHAUSTED_WALK);
        clientAnimator.addLivingAnimation(
                ExhaustedMotions.EXHAUSTED_IDLE_CROSSBOW, ExhaustedAnimations.EXHAUSTED_IDLE_CROSSBOW);
        clientAnimator.addLivingAnimation(
                ExhaustedMotions.EXHAUSTED_WALK_CROSSBOW, ExhaustedAnimations.EXHAUSTED_WALK_CROSSBOW);
        clientAnimator.addLivingAnimation(
                ExhaustedMotions.EXHAUSTED_IDLE_GREATSWORD, ExhaustedAnimations.EXHAUSTED_IDLE_GREATSWORD);
        clientAnimator.addLivingAnimation(
                ExhaustedMotions.EXHAUSTED_WALK_GREATSWORD, ExhaustedAnimations.EXHAUSTED_WALK_GREATSWORD);
        clientAnimator.addLivingAnimation(
                ExhaustedMotions.EXHAUSTED_IDLE_TACHI, ExhaustedAnimations.EXHAUSTED_IDLE_TACHI);
        clientAnimator.addLivingAnimation(
                ExhaustedMotions.EXHAUSTED_WALK_TACHI, ExhaustedAnimations.EXHAUSTED_WALK_TACHI);
        clientAnimator.addLivingAnimation(
                ExhaustedMotions.EXHAUSTED_IDLE_SPEAR, ExhaustedAnimations.EXHAUSTED_IDLE_SPEAR);
        clientAnimator.addLivingAnimation(
                ExhaustedMotions.EXHAUSTED_WALK_SPEAR, ExhaustedAnimations.EXHAUSTED_WALK_SPEAR);
        clientAnimator.addLivingAnimation(
                ExhaustedMotions.EXHAUSTED_IDLE_OCHS, ExhaustedAnimations.EXHAUSTED_IDLE_OCHS);
        clientAnimator.addLivingAnimation(
                ExhaustedMotions.EXHAUSTED_WALK_LIECHTENAUER, ExhaustedAnimations.EXHAUSTED_WALK_OCHS);
        clientAnimator.addLivingAnimation(
                ExhaustedMotions.EXHAUSTED_IDLE_SHEATH, ExhaustedAnimations.EXHAUSTED_IDLE_SHEATH);
        clientAnimator.addLivingAnimation(
                ExhaustedMotions.EXHAUSTED_WALK_SHEATH, ExhaustedAnimations.EXHAUSTED_WALK_SHEATH);
        clientAnimator.addLivingAnimation(
                ExhaustedMotions.EXHAUSTED_IDLE_UNSHEATH, ExhaustedAnimations.EXHAUSTED_IDLE_UNSHEATH);
        clientAnimator.addLivingAnimation(
                ExhaustedMotions.EXHAUSTED_WALK_UNSHEATH, ExhaustedAnimations.EXHAUSTED_WALK_UNSHEATH);
        clientAnimator.addLivingAnimation(
                ExhaustedMotions.EXHAUSTED_WALK_UCHIGATANA, ExhaustedAnimations.EXHAUSTED_WALK_UCHIGATANA);
    }
}
