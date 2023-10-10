package settingdust.paraglidersstaminacompats.epicfight.morestamina;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import settingdust.paraglidersstaminacompats.ParaglidersStaminaCompats;
import settingdust.paraglidersstaminacompats.ParaglidersStaminaCompatsAttributes;
import settingdust.paraglidersstaminacompats.epicfight.PlayerPatchMovement;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.entity.eventlistener.PlayerEventListener;

import java.util.Map;
import java.util.UUID;

@Mod.EventBusSubscriber(modid = ParaglidersStaminaCompats.MOD_ID)
public class SkillConsumingListener {
    private static final UUID SKILL_CONSUME_EVENT_ID = UUID.fromString("b35ac1ea-8672-76a8-698e-78c41e8a6238");

    @SubscribeEvent
    public void onEntityJoinWorld(EntityJoinWorldEvent event) {
        if (!(event.getEntity() instanceof Player player)) return;
        ServerPlayerPatch playerPatch = EpicFightCapabilities.getEntityPatch(player, ServerPlayerPatch.class);
        playerPatch
                .getEventListener()
                .addEventListener(PlayerEventListener.EventType.SKILL_CONSUME_EVENT, SKILL_CONSUME_EVENT_ID, e -> {
                    CapabilityItem item = playerPatch.getHoldingItemCapability(InteractionHand.MAIN_HAND);
                    Map<Attribute, AttributeModifier> attributes =
                            item.getDamageAttributesInCondition(CapabilityItem.Styles.COMMON);
                    Attribute attribute = ParaglidersStaminaCompatsAttributes.WEAPON_STAMINA_CONSUMPTION.get();
                    if (attributes.containsKey(attribute)) {
                        ((PlayerPatchMovement) playerPatch)
                                .paraglidersStaminaCompats$getPlayerMovement()
                                .takeStamina((int) attributes.get(attribute).getAmount(), false, false);
                    }
                });
    }
}
