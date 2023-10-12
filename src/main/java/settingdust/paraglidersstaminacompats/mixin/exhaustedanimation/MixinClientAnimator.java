package settingdust.paraglidersstaminacompats.mixin.exhaustedanimation;

import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import settingdust.paraglidersstaminacompats.epicfight.exhaustedanimation.ExhaustedMotions;
import settingdust.paraglidersstaminacompats.epicfight.PlayerPatchMovement;
import tictim.paraglider.capabilities.PlayerMovement;
import yesman.epicfight.api.animation.Animator;
import yesman.epicfight.api.animation.LivingMotion;
import yesman.epicfight.api.animation.LivingMotions;
import yesman.epicfight.api.client.animation.ClientAnimator;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.capabilities.item.Style;
import yesman.epicfight.world.item.*;

@Mixin(ClientAnimator.class)
public abstract class MixinClientAnimator extends Animator {
    @Inject(method = "poseTick", at = @At("TAIL"), remap = false)
    private void paraglidersStaminaCompats$addExhaustedAnimations(CallbackInfo ci) {
        if (entitypatch instanceof PlayerPatch<?> playerPatch) {
            PlayerMovement playerMovement =
                    ((PlayerPatchMovement) playerPatch).paraglidersStaminaCompats$getPlayerMovement();

            if (playerMovement.isDepleted()) {
                this.entitypatch.currentLivingMotion =
                        paraglidersStaminaCompats$switchToExhaustedAnimations(this.entitypatch);
            }
        }
    }

    @Unique
    private LivingMotion paraglidersStaminaCompats$switchToExhaustedAnimations(LivingEntityPatch<?> entityPatch) {
        Item item = entityPatch.getOriginal().getMainHandItem().getItem();
        Style weaponStyle = EpicFightCapabilities.getItemStackCapability(
                        entityPatch.getOriginal().getMainHandItem())
                .getStyle(entityPatch);

        if (entityPatch.currentLivingMotion.equals(LivingMotions.IDLE)
                && !entityPatch.getEntityState().inaction()) {
            if (item instanceof CrossbowItem) {
                return ExhaustedMotions.EXHAUSTED_IDLE_CROSSBOW;
            }
            if (item instanceof LongswordItem) {
                if (weaponStyle.equals(CapabilityItem.Styles.OCHS)) {
                    return ExhaustedMotions.EXHAUSTED_IDLE_OCHS;
                } else {
                    return ExhaustedMotions.EXHAUSTED_IDLE_GREATSWORD;
                }
            }
            if (item instanceof GreatswordItem) {
                return ExhaustedMotions.EXHAUSTED_IDLE_GREATSWORD;
            }
            if (item instanceof TachiItem) {
                return ExhaustedMotions.EXHAUSTED_IDLE_TACHI;
            }
            if (item instanceof SpearItem && weaponStyle.equals(CapabilityItem.Styles.TWO_HAND)) {
                return ExhaustedMotions.EXHAUSTED_IDLE_SPEAR;
            }
            if (item instanceof UchigatanaItem && weaponStyle.equals(CapabilityItem.Styles.SHEATH)) {
                return ExhaustedMotions.EXHAUSTED_IDLE_SHEATH;
            }
            return ExhaustedMotions.EXHAUSTED_IDLE;
        }
        if (entityPatch.currentLivingMotion.equals(LivingMotions.WALK)) {
            if (item instanceof CrossbowItem) {
                return ExhaustedMotions.EXHAUSTED_WALK_CROSSBOW;
            }
            if (item instanceof LongswordItem) {
                if (weaponStyle.equals(CapabilityItem.Styles.OCHS)) {
                    return ExhaustedMotions.EXHAUSTED_WALK_LIECHTENAUER;
                } else {
                    return ExhaustedMotions.EXHAUSTED_WALK_GREATSWORD;
                }
            }
            if (item instanceof GreatswordItem) {
                return ExhaustedMotions.EXHAUSTED_WALK_GREATSWORD;
            }
            if (item instanceof SpearItem) {
                return ExhaustedMotions.EXHAUSTED_WALK_SPEAR;
            }
            if (item instanceof TachiItem) {
                return ExhaustedMotions.EXHAUSTED_WALK_TACHI;
            }
            if (item instanceof UchigatanaItem) {
                if (weaponStyle.equals(CapabilityItem.Styles.SHEATH)) {
                    return ExhaustedMotions.EXHAUSTED_WALK_SHEATH;
                } else {
                    return ExhaustedMotions.EXHAUSTED_WALK_UCHIGATANA;
                }
            } else {
                return ExhaustedMotions.EXHAUSTED_WALK;
            }
        }
        return entityPatch.currentLivingMotion;
    }
}
