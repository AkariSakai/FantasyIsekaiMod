package net.akarisakai.fantasyisekaimod.init;

import net.akarisakai.fantasyisekaimod.FantasyIsekaiMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
@Mod.EventBusSubscriber(modid = FantasyIsekaiMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class CreativeTabInit {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, FantasyIsekaiMod.MOD_ID);
    public static final List<Supplier<? extends ItemLike>> FANTASYISEKAI_TAB_ITEMS = new ArrayList<>();

    public static final RegistryObject<CreativeModeTab> FANTASYISEKAI_TAB = CREATIVE_MODE_TABS.register("fantasyisekai_tab",
            () -> CreativeModeTab.builder()
                    .icon(ItemInit.GOBLIN_SPAWN_EGG.get()::getDefaultInstance)
                    .title(Component.translatable("Fantasy Mobs"))
                    .displayItems((pParameters, pOutput) ->
                            FANTASYISEKAI_TAB_ITEMS.forEach(itemLike -> pOutput.accept(itemLike.get())))
                    .build()
    );

    public static <T extends Item> RegistryObject<T> addToTab(RegistryObject<T> itemLike) {
        FANTASYISEKAI_TAB_ITEMS.add(itemLike);
        return itemLike;
    }
    @SubscribeEvent
    public static void buildContents(BuildCreativeModeTabContentsEvent event) {
        if(event.getTabKey() == CreativeModeTabs.SPAWN_EGGS) {
            event.accept(ItemInit.GOBLIN_SPAWN_EGG);
            event.accept(ItemInit.GOBLINSHAMAN_SPAWN_EGG);
            event.accept(ItemInit.GOBLINWARRIOR_SPAWN_EGG);
            event.accept(ItemInit.GOBLINARCHER_SPAWN_EGG);
            event.accept(ItemInit.DIREWOLF_SPAWN_EGG);

        }

    }
}