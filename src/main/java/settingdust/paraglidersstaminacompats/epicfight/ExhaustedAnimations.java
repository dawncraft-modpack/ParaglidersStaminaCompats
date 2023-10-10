package settingdust.paraglidersstaminacompats.epicfight;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import settingdust.paraglidersstaminacompats.ParaglidersStaminaCompats;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.forgeevent.AnimationRegistryEvent;
import yesman.epicfight.gameasset.Armatures;

/**
 * Take from EpicParagliders
 */
@Mod.EventBusSubscriber(modid = ParaglidersStaminaCompats.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class ExhaustedAnimations {
    public static StaticAnimation EXHAUSTED_IDLE;
    public static StaticAnimation EXHAUSTED_WALK;
    public static StaticAnimation EXHAUSTED_IDLE_CROSSBOW;
    public static StaticAnimation EXHAUSTED_WALK_CROSSBOW;
    public static StaticAnimation EXHAUSTED_IDLE_GREATSWORD;
    public static StaticAnimation EXHAUSTED_WALK_GREATSWORD;
    public static StaticAnimation EXHAUSTED_IDLE_TACHI;
    public static StaticAnimation EXHAUSTED_WALK_TACHI;
    public static StaticAnimation EXHAUSTED_IDLE_SPEAR;
    public static StaticAnimation EXHAUSTED_WALK_SPEAR;
    public static StaticAnimation EXHAUSTED_IDLE_OCHS;
    public static StaticAnimation EXHAUSTED_WALK_OCHS;
    public static StaticAnimation EXHAUSTED_IDLE_SHEATH;
    public static StaticAnimation EXHAUSTED_WALK_SHEATH;
    public static StaticAnimation EXHAUSTED_IDLE_UNSHEATH;
    public static StaticAnimation EXHAUSTED_WALK_UNSHEATH;
    public static StaticAnimation EXHAUSTED_WALK_UCHIGATANA;

    @SubscribeEvent
    public static void registerAnimations(AnimationRegistryEvent event) {
        event.getRegistryMap().put(ParaglidersStaminaCompats.MOD_ID, ExhaustedAnimations::build);
    }

    private static void build() {
        EXHAUSTED_IDLE = new StaticAnimation(true, "biped/living/exhausted_idle", Armatures.BIPED);
        EXHAUSTED_WALK = new StaticAnimation(true, "biped/living/exhausted_walk", Armatures.BIPED);
        EXHAUSTED_IDLE_CROSSBOW = new StaticAnimation(true, "biped/living/exhausted_idle_crossbow", Armatures.BIPED);
        EXHAUSTED_WALK_CROSSBOW = new StaticAnimation(true, "biped/living/exhausted_walk_crossbow", Armatures.BIPED);
        EXHAUSTED_IDLE_GREATSWORD = new StaticAnimation(true, "biped/living/exhausted_idle", Armatures.BIPED);
        EXHAUSTED_WALK_GREATSWORD =
                new StaticAnimation(true, "biped/living/exhausted_walk_greatsword", Armatures.BIPED);
        EXHAUSTED_IDLE_TACHI = new StaticAnimation(true, "biped/living/exhausted_idle_tachi", Armatures.BIPED);
        EXHAUSTED_WALK_TACHI = new StaticAnimation(true, "biped/living/exhausted_walk_tachi", Armatures.BIPED);
        EXHAUSTED_IDLE_SPEAR = new StaticAnimation(true, "biped/living/exhausted_idle_spear", Armatures.BIPED);
        EXHAUSTED_WALK_SPEAR = new StaticAnimation(true, "biped/living/exhausted_walk", Armatures.BIPED);
        EXHAUSTED_IDLE_OCHS =
                new StaticAnimation(true, "biped/living/exhausted_idle_ochs", Armatures.BIPED);
        EXHAUSTED_WALK_OCHS =
                new StaticAnimation(true, "biped/living/exhausted_walk_ochs", Armatures.BIPED);
        EXHAUSTED_IDLE_SHEATH = new StaticAnimation(true, "biped/living/exhausted_idle_sheath", Armatures.BIPED);
        EXHAUSTED_WALK_SHEATH = new StaticAnimation(true, "biped/living/exhausted_walk_sheath", Armatures.BIPED);
        EXHAUSTED_IDLE_UNSHEATH = new StaticAnimation(true, "biped/living/exhausted_idle_unsheath", Armatures.BIPED);
        EXHAUSTED_WALK_UNSHEATH = new StaticAnimation(true, "biped/living/exhausted_walk_unsheath", Armatures.BIPED);
        EXHAUSTED_WALK_UCHIGATANA = new StaticAnimation(true, "biped/living/exhausted_walk_uchigatana", Armatures.BIPED);
    }
}
