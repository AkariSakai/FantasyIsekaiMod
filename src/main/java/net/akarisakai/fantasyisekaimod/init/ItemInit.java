package net.akarisakai.fantasyisekaimod.init;

import net.akarisakai.fantasyisekaimod.FantasyIsekaiMod;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ItemInit {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, FantasyIsekaiMod.MOD_ID);
    public static final RegistryObject<Item> GOBLIN_SPAWN_EGG = CreativeTabInit.addToTab(ITEMS.register("goblin_spawn_egg",
            () -> new ForgeSpawnEggItem(EntityInit.GOBLIN_ENTITY, 0x63902e, 0x2e1d10,
                    new Item.Properties()
                            .stacksTo(64)
                            .rarity(Rarity.COMMON)
            )));
    public static final RegistryObject<Item> GOBLINWARRIOR_SPAWN_EGG = CreativeTabInit.addToTab(ITEMS.register("goblinwarrior_spawn_egg",
            () -> new ForgeSpawnEggItem(EntityInit.GOBLINWARRIOR_ENTITY, 0x63902e, 0x5a4424,
                    new Item.Properties()
                            .stacksTo(64)
                            .rarity(Rarity.COMMON)
            )));
    public static final RegistryObject<Item> GOBLINSHAMAN_SPAWN_EGG = CreativeTabInit.addToTab(ITEMS.register("goblinshaman_spawn_egg",
            () -> new ForgeSpawnEggItem(EntityInit.GOBLINSHAMAN_ENTITY, 0x63902e, 0x494949,
                    new Item.Properties()
                            .stacksTo(64)
                            .rarity(Rarity.COMMON)
            )));
    public static final RegistryObject<Item> GOBLINARCHER_SPAWN_EGG = CreativeTabInit.addToTab(ITEMS.register("goblinarcher_spawn_egg",
            () -> new ForgeSpawnEggItem(EntityInit.GOBLINARCHER_ENTITY, 0x63902e, 0x122a33,
                    new Item.Properties()
                            .stacksTo(64)
                            .rarity(Rarity.COMMON)
            )));
    public static final RegistryObject<Item> DIREWOLF_SPAWN_EGG = CreativeTabInit.addToTab(ITEMS.register("direwolf_spawn_egg",
            () -> new ForgeSpawnEggItem(EntityInit.DIREWOLF_ENTITY, 0x4c494a, 0xbcb3b6,
                    new Item.Properties()
                            .stacksTo(64)
                            .rarity(Rarity.COMMON)
            )));
    public static final RegistryObject<Item> ORC_SPAWN_EGG = CreativeTabInit.addToTab(ITEMS.register("orc_spawn_egg",
            () -> new ForgeSpawnEggItem(EntityInit.ORC_ENTITY, 0xe8a074, 0x811d14,
                    new Item.Properties()
                            .stacksTo(64)
                            .rarity(Rarity.COMMON)
            )));


}
