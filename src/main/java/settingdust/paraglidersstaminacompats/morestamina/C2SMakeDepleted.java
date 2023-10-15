package settingdust.paraglidersstaminacompats.morestamina;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;
import tictim.paraglider.capabilities.PlayerMovement;

import java.util.function.Supplier;

public record C2SMakeDepleted() {
    public C2SMakeDepleted(FriendlyByteBuf buf) {
        this();
    }

    public static void handle(C2SMakeDepleted msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayer sender = ctx.get().getSender();
            PlayerMovement playerMovement = PlayerMovement.of(sender);
            playerMovement.setDepleted(true);
        });
        ctx.get().setPacketHandled(true);
    }
}
