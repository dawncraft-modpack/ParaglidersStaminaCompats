package settingdust.paraglidersstaminacompats.epicfight;

import yesman.epicfight.api.animation.LivingMotion;

/**
 * Take from EpicParagliders
 */
public enum ExhaustedMotions implements LivingMotion {
    EXHAUSTED_IDLE,
    EXHAUSTED_WALK,
    EXHAUSTED_IDLE_CROSSBOW,
    EXHAUSTED_WALK_CROSSBOW,
    EXHAUSTED_IDLE_GREATSWORD,
    EXHAUSTED_WALK_GREATSWORD,
    EXHAUSTED_IDLE_TACHI,
    EXHAUSTED_WALK_TACHI,
    EXHAUSTED_IDLE_SPEAR,
    EXHAUSTED_WALK_SPEAR,
    EXHAUSTED_IDLE_OCHS,
    EXHAUSTED_WALK_LIECHTENAUER,
    EXHAUSTED_IDLE_SHEATH,
    EXHAUSTED_WALK_SHEATH,
    EXHAUSTED_IDLE_UNSHEATH,
    EXHAUSTED_WALK_UNSHEATH,
    EXHAUSTED_WALK_UCHIGATANA;
    final int id;

    private ExhaustedMotions() {
        this.id = LivingMotion.ENUM_MANAGER.assign(this);
    }

    @Override
    public int universalOrdinal() {
        return this.id;
    }
}
