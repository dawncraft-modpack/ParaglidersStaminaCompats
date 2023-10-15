package settingdust.paraglidersstaminacompats;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import settingdust.paraglidersstaminacompats.morestamina.C2SMakeDepleted;
import settingdust.paraglidersstaminacompats.morestamina.ShieldListener;
import settingdust.paraglidersstaminacompats.morestamina.SkillListener;

import java.util.Objects;
import java.util.Optional;
import java.util.function.BiFunction;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(ParaglidersStaminaCompats.MOD_ID)
public class ParaglidersStaminaCompats {
    public static final String MOD_ID = "paraglidersstaminacompats";
    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel NETWORK_MANAGER = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(MOD_ID, "network_manager"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals);
    //
    //    // Directly reference a slf4j logger
    //    private static final Logger LOGGER = LogUtils.getLogger();
    //
    public ParaglidersStaminaCompats() {
        // Register the setup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        //        // Register the enqueueIMC method for modloading
        //        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
        //        // Register the processIMC method for modloading
        //        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);
        //
        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ParaglidersStaminaCompatsAttributes.ATTRIBUTES.register(modEventBus);

        Objects.requireNonNull(ParaglidersStaminaCompatsConfig.CHARGING_CONSUMPTION);

        MinecraftForge.EVENT_BUS.register(new SkillListener());
        MinecraftForge.EVENT_BUS.register(new ShieldListener());
    }
    //
    private void setup(final FMLCommonSetupEvent event) {
        int id = 0;
        NETWORK_MANAGER.registerMessage(
                id++,
                C2SMakeDepleted.class,
                (msg, buf) -> {},
                C2SMakeDepleted::new,
                C2SMakeDepleted::handle,
                Optional.of(NetworkDirection.PLAY_TO_SERVER));
    }

    //
    //    private void enqueueIMC(final InterModEnqueueEvent event) {
    //        // Some example code to dispatch IMC to another mod
    //        InterModComms.sendTo("paraglidersstaminaapi", "helloworld", () -> { LOGGER.info("Hello world from the
    // MDK"); return "Hello world";});
    //    }
    //
    //    private void processIMC(final InterModProcessEvent event) {
    //        // Some example code to receive and process InterModComms from other mods
    //        LOGGER.info("Got IMC {}", event.getIMCStream().
    //                map(m->m.messageSupplier().get()).
    //                collect(Collectors.toList()));
    //    }
    //
    //    // You can use SubscribeEvent and let the Event Bus discover methods to call
    //    @SubscribeEvent
    //    public void onServerStarting(ServerStartingEvent event) {
    //        // Do something when the server starts
    //        LOGGER.info("HELLO from server starting");
    //    }
    //
    //    // You can use EventBusSubscriber to automatically subscribe events on the contained class (this is
    // subscribing to the MOD
    //    // Event bus for receiving Registry Events)
    //    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
    //    public static class RegistryEvents {
    //        @SubscribeEvent
    //        public static void onBlocksRegistry(final RegistryEvent.Register<Block> blockRegistryEvent) {
    //            // Register a new block here
    //            LOGGER.info("HELLO from Register Block");
    //        }
    //    }
}
