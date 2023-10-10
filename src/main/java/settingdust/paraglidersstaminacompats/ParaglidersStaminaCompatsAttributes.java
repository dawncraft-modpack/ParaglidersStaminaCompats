package settingdust.paraglidersstaminacompats;

import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class ParaglidersStaminaCompatsAttributes {

    public static final DeferredRegister<Attribute> ATTRIBUTES =
            DeferredRegister.create(ForgeRegistries.ATTRIBUTES, ParaglidersStaminaCompats.MOD_ID);

    public static final RegistryObject<Attribute> WEAPON_STAMINA_CONSUMPTION = ATTRIBUTES.register(
            "weapon_stamina_consumption",
            () -> new RangedAttribute("weapon_stamina_consumption", 0.0, 0.0, 1000.0).setSyncable(true));
}
