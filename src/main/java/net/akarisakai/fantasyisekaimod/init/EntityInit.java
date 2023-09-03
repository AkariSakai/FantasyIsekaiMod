package net.akarisakai.fantasyisekaimod.init;

import net.akarisakai.fantasyisekaimod.FantasyIsekaiMod;
import net.akarisakai.fantasyisekaimod.client.custom.GoblinEntity;
import net.akarisakai.fantasyisekaimod.client.custom.GoblinShamanEntity;
import net.akarisakai.fantasyisekaimod.client.custom.GoblinWarriorEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class EntityInit {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, FantasyIsekaiMod.MOD_ID);

    public  static  final RegistryObject<EntityType<GoblinShamanEntity>> GOBLINSHAMAN_ENTITY = ENTITIES.register("goblinshaman_entity",
            () -> EntityType.Builder.<GoblinShamanEntity>of(GoblinShamanEntity::new, MobCategory.MONSTER )
                    .sized(0.5f,1.35f)
                    .build(new ResourceLocation(FantasyIsekaiMod.MOD_ID,"goblinshaman_entity").toString())
    );
    public  static  final RegistryObject<EntityType<GoblinEntity>> GOBLIN_ENTITY = ENTITIES.register("goblin_entity",
            () -> EntityType.Builder.of(GoblinEntity::new, MobCategory.MONSTER )
                    .sized(0.5f,1.35f)
                    .build(new ResourceLocation(FantasyIsekaiMod.MOD_ID,"goblin_entity").toString())
    );
    public  static  final RegistryObject<EntityType<GoblinWarriorEntity>> GOBLINWARRIOR_ENTITY = ENTITIES.register("goblinwarrior_entity",
            () -> EntityType.Builder.of(GoblinWarriorEntity::new, MobCategory.MONSTER)
                    .sized(0.5f,1.35f)
                    .build(new ResourceLocation(FantasyIsekaiMod.MOD_ID,"goblinwarrior_entity").toString())
    );
}
