package settingdust.paraglidersstaminacompats.mixin.syncparagliders.epicfight;

import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import tictim.paraglider.contents.Contents;
import yesman.epicfight.world.entity.ai.attribute.EpicFightAttributes;

import java.util.function.Supplier;

@Mixin(EpicFightAttributes.class)
public class MixinEpicFightAttributes {
    @Redirect(
            method = "<clinit>",
            at =
                    @At(
                            value = "INVOKE",
                            target =
                                    "Lnet/minecraftforge/registries/DeferredRegister;register(Ljava/lang/String;Ljava/util/function/Supplier;)Lnet/minecraftforge/registries/RegistryObject;",
                            ordinal = 0))
    private static RegistryObject<Attribute> paraglidersStaminaCompats$delegateToParaglider(
            DeferredRegister<Attribute> owner, final String name, final Supplier<?> sup) {
        return Contents.MAX_STAMINA;
    }
}
