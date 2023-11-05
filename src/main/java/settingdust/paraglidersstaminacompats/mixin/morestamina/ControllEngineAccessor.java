package settingdust.paraglidersstaminacompats.mixin.morestamina;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;
import yesman.epicfight.client.events.engine.ControllEngine;

@Mixin(value = ControllEngine.class, remap = false)
public interface ControllEngineAccessor {
    @Invoker("releaseAllServedKeys")
    void dcfixes$releaseAllServedKeys();
}
