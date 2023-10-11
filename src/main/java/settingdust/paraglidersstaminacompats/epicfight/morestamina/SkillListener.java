package settingdust.paraglidersstaminacompats.epicfight.morestamina;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import settingdust.paraglidersstaminacompats.ParaglidersStaminaCompats;
import settingdust.paraglidersstaminacompats.ParaglidersStaminaCompatsAttributes;
import settingdust.paraglidersstaminacompats.ParaglidersStaminaCompatsConfig;
import settingdust.paraglidersstaminacompats.epicfight.PlayerPatchMovement;
import tictim.paraglider.capabilities.PlayerMovement;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.capabilities.item.WeaponCategory;
import yesman.epicfight.world.entity.eventlistener.PlayerEventListener;

import java.util.Map;
import java.util.UUID;

@Mod.EventBusSubscriber(modid = ParaglidersStaminaCompats.MOD_ID)
public class SkillListener {
    private static final UUID SKILL_CONSUME_EVENT_ID = UUID.fromString("b35ac1ea-8672-76a8-698e-78c41e8a6238");
    private static final UUID BASIC_ATTACK_EVENT_ID = UUID.fromString("b0ce42e4-1c44-dbb1-0a4d-29f68802b5fa");

    @SubscribeEvent
    public void onEntityJoinWorld(EntityJoinWorldEvent event) {
        if (event.getWorld().isClientSide) return;
        if (!(event.getEntity() instanceof Player player)) return;
        ServerPlayerPatch playerPatch = EpicFightCapabilities.getEntityPatch(player, ServerPlayerPatch.class);
        PlayerEventListener eventListener = playerPatch.getEventListener();
        eventListener.addEventListener(PlayerEventListener.EventType.SKILL_CONSUME_EVENT, SKILL_CONSUME_EVENT_ID, e -> {
            ItemStack itemStack = playerPatch.getOriginal().getMainHandItem();
            CapabilityItem itemCapability = EpicFightCapabilities.getItemStackCapability(itemStack);
            Map<Attribute, AttributeModifier> attributes =
                    itemCapability.getDamageAttributesInCondition(CapabilityItem.Styles.COMMON);
            Attribute attribute = ParaglidersStaminaCompatsAttributes.WEAPON_STAMINA_CONSUMPTION.get();
            PlayerMovement playerMovement = ((PlayerPatchMovement) playerPatch).paraglidersStaminaCompats$getPlayerMovement();
            if (attributes.containsKey(attribute)) {
                playerMovement.takeStamina((int) attributes.get(attribute).getAmount(), false, false);
            } else {
                WeaponCategory category = itemCapability.getWeaponCategory();
                ForgeConfigSpec.IntValue value = ParaglidersStaminaCompatsConfig.WEAPON_CATEGORIES_COMMON_CONSUMPTION.get(category);
                playerMovement.takeStamina(value.get(), false, false);
            }
        });
    }
}
