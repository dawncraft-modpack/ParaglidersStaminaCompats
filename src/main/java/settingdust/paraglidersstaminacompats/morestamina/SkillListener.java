package settingdust.paraglidersstaminacompats.morestamina;

import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import settingdust.paraglidersstaminacompats.ParaglidersStaminaCompats;
import settingdust.paraglidersstaminacompats.ParaglidersStaminaCompatsAttributes;
import settingdust.paraglidersstaminacompats.ParaglidersStaminaCompatsConfig;
import settingdust.paraglidersstaminacompats.PlayerPatchMovement;
import tictim.paraglider.capabilities.ServerPlayerMovement;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.entity.eventlistener.PlayerEventListener;

import java.util.Map;
import java.util.UUID;

@Mod.EventBusSubscriber(modid = ParaglidersStaminaCompats.MOD_ID)
public class SkillListener {
    private static final UUID SKILL_CONSUME_EVENT_ID = UUID.fromString("b35ac1ea-8672-76a8-698e-78c41e8a6238");

    @SubscribeEvent
    public void onEntityJoinWorld(EntityJoinWorldEvent event) {
        if (!(event.getEntity() instanceof Player player)) return;
        final var playerPatch = EpicFightCapabilities.getEntityPatch(player, PlayerPatch.class);
        final var playerMovement = ((PlayerPatchMovement) playerPatch).paraglidersStaminaCompats$getPlayerMovement();
        PlayerEventListener eventListener = playerPatch.getEventListener();
        eventListener.addEventListener(
                PlayerEventListener.EventType.SKILL_CONSUME_EVENT,
                SKILL_CONSUME_EVENT_ID,
                e -> {
                    ItemStack itemStack = player.getMainHandItem();
                    CapabilityItem itemCapability = EpicFightCapabilities.getItemStackCapability(itemStack);
                    Map<Attribute, AttributeModifier> attributes =
                            itemCapability.getDamageAttributesInCondition(CapabilityItem.Styles.COMMON);

                    Attribute attribute = ParaglidersStaminaCompatsAttributes.WEAPON_STAMINA_CONSUMPTION.get();
                    if (attributes.containsKey(attribute)) {
                        double attributeValue = attributes.get(attribute).getAmount();
                        if (e.getResourceType() == Skill.Resource.WEAPON_INNATE_ENERGY) {
                            if (!e.shouldConsume()) return;
                            playerMovement.takeStamina(attributeValue, false, false);
                        } else {
                            e.setAmount((float) (e.getAmount() + attributeValue));
                        }
                        if (e.getResourceType() == Skill.Resource.STAMINA)
                            e.setAmount((float) (e.getAmount() + attributeValue));
                    } else {
                        final var category = itemCapability.getWeaponCategory();
                        final var value =
                                ParaglidersStaminaCompatsConfig.WEAPON_CATEGORIES_COMMON_CONSUMPTION.get(category);
                        if (e.getResourceType() == Skill.Resource.WEAPON_INNATE_ENERGY) {
                            if (!e.shouldConsume()) return;
                            playerMovement.takeStamina(value.get(), false, false);
                        } else {
                            e.setAmount((float) (e.getAmount() + value.get()));
                        }
                    }
                    if (playerMovement instanceof ServerPlayerMovement serverPlayerMovement)
                        serverPlayerMovement.movementNeedsSync = true;
                },
                1000 // priority need higher that ForbiddenStrengthSkill
                );
    }
}
