package net.akarisakai.fantasyisekaimod.events;

import net.akarisakai.fantasyisekaimod.FantasyIsekaiMod;
import net.akarisakai.fantasyisekaimod.client.custom.GoblinEntity;
import net.akarisakai.fantasyisekaimod.client.custom.GoblinShamanEntity;
import net.akarisakai.fantasyisekaimod.client.custom.GoblinWarriorEntity;
import net.akarisakai.fantasyisekaimod.init.EntityInit;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = FantasyIsekaiMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CommonModEvents {
    @SubscribeEvent
    public static void entityAttributes(EntityAttributeCreationEvent event) {
        event.put(EntityInit.GOBLINSHAMAN_ENTITY.get(), GoblinShamanEntity.createAttributes().build());
        event.put(EntityInit.GOBLIN_ENTITY.get(), GoblinEntity.createAttributes().build());
        event.put(EntityInit.GOBLINWARRIOR_ENTITY.get(), GoblinWarriorEntity.createAttributes().build());
    }

    @SubscribeEvent
    public static void registerSpawnPlacements(SpawnPlacementRegisterEvent event) {
        event.register(EntityInit.GOBLINSHAMAN_ENTITY.get(),
                SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.WORLD_SURFACE,
                GoblinShamanEntity::canSpawn,
                SpawnPlacementRegisterEvent.Operation.OR);
        event.register(EntityInit.GOBLIN_ENTITY.get(),
                SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.WORLD_SURFACE,
                GoblinEntity::canSpawn,
                SpawnPlacementRegisterEvent.Operation.OR);
        event.register(EntityInit.GOBLINWARRIOR_ENTITY.get(),
                SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.WORLD_SURFACE,
                GoblinWarriorEntity::canSpawn,
                SpawnPlacementRegisterEvent.Operation.OR);
    }
}
