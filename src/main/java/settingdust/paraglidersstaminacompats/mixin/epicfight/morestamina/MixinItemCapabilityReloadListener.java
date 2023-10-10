package settingdust.paraglidersstaminacompats.mixin.epicfight.morestamina;

import com.google.gson.Gson;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import settingdust.paraglidersstaminacompats.ParaglidersStaminaCompatsAttributes;
import yesman.epicfight.api.data.reloader.ItemCapabilityReloadListener;
import yesman.epicfight.main.EpicFightMod;

import java.util.Map;
import java.util.UUID;

@Mixin(ItemCapabilityReloadListener.class)
public abstract class MixinItemCapabilityReloadListener extends SimpleJsonResourceReloadListener {
    @Unique
    private static final UUID STAMINA_COST_MODIFIER = UUID.fromString("517634d1-8807-4555-906d-2b846b1a73b1");

    public MixinItemCapabilityReloadListener(Gson p_10768_, String p_10769_) {
        super(p_10768_, p_10769_);
    }

    @ModifyReturnValue(method = "deserializeAttributes", at = @At("TAIL"), remap = false)
    private static Map<Attribute, AttributeModifier> paraglidersStaminaCompats$addStamina(
            Map<Attribute, AttributeModifier> modifierMap, CompoundTag tag) {
        if (tag.contains("stamina_cost")) {
            modifierMap.put(
                    ParaglidersStaminaCompatsAttributes.WEAPON_STAMINA_CONSUMPTION.get(),
                    new AttributeModifier(
                            STAMINA_COST_MODIFIER,
                            EpicFightMod.MODID + ":stamina_cost",
                            tag.getDouble("stamina_cost"),
                            AttributeModifier.Operation.ADDITION));
        }
        return modifierMap;
    }
}
