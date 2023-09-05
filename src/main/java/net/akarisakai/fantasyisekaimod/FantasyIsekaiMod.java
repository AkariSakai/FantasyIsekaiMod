package net.akarisakai.fantasyisekaimod;

import com.mojang.logging.LogUtils;
import net.akarisakai.fantasyisekaimod.init.CreativeTabInit;
import net.akarisakai.fantasyisekaimod.init.EntityInit;
import net.akarisakai.fantasyisekaimod.init.ItemInit;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(FantasyIsekaiMod.MOD_ID)
public class FantasyIsekaiMod {
    public static final String MOD_ID = "fantasyisekaimod";
    private static final Logger LOGGER = LogUtils.getLogger();

    public FantasyIsekaiMod() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::commonSetup);
        MinecraftForge.EVENT_BUS.register(this);
        modEventBus.addListener(this::addCreative);

        IEventBus EventBus = FMLJavaModLoadingContext.get().getModEventBus();
        EntityInit.ENTITIES.register(EventBus);
        ItemInit.ITEMS.register(EventBus);
        CreativeTabInit.CREATIVE_MODE_TABS.register(EventBus);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {

    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if(event.getTabKey() == CreativeModeTabs.SPAWN_EGGS ) {
            event.accept(ItemInit.GOBLIN_SPAWN_EGG);
            event.accept(ItemInit.GOBLINSHAMAN_SPAWN_EGG);
            event.accept(ItemInit.GOBLINWARRIOR_SPAWN_EGG);
            event.accept(ItemInit.GOBLINARCHER_SPAWN_EGG);
        }
    }


    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
        }
    }
}
