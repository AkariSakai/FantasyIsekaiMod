package net.akarisakai.fantasyisekaimod.events;

import net.akarisakai.fantasyisekaimod.FantasyIsekaiMod;
import net.akarisakai.fantasyisekaimod.client.model.*;
import net.akarisakai.fantasyisekaimod.client.renderer.*;
import net.akarisakai.fantasyisekaimod.init.EntityInit;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = FantasyIsekaiMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientModEvents {
    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(EntityInit.GOBLINSHAMAN_ENTITY.get(), GoblinShamanRenderer::new);
        event.registerEntityRenderer(EntityInit.GOBLIN_ENTITY.get(), GoblinRenderer::new);
        event.registerEntityRenderer(EntityInit.GOBLINWARRIOR_ENTITY.get(), GoblinWarriorRenderer::new);
        event.registerEntityRenderer(EntityInit.GOBLINARCHER_ENTITY.get(), GoblinArcherRenderer::new);
        event.registerEntityRenderer(EntityInit.DIREWOLF_ENTITY.get(), DirewolfRenderer::new);
        event.registerEntityRenderer(EntityInit.ORC_ENTITY.get(), OrcRenderer::new);
    }

    @SubscribeEvent
    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(GoblinShamanEntityModel.LAYER_LOCATION, GoblinShamanEntityModel::createBodyLayer);
        event.registerLayerDefinition(GoblinEntityModel.LAYER_LOCATION, GoblinEntityModel::createBodyLayer);
        event.registerLayerDefinition(GoblinWarriorEntityModel.LAYER_LOCATION, GoblinWarriorEntityModel::createBodyLayer);
        event.registerLayerDefinition(GoblinArcherEntityModel.LAYER_LOCATION, GoblinArcherEntityModel::createBodyLayer);
        event.registerLayerDefinition(DirewolfEntityModel.LAYER_LOCATION, DirewolfEntityModel::createBodyLayer);
        event.registerLayerDefinition(OrcEntityModel.LAYER_LOCATION, OrcEntityModel::createBodyLayer);
    }
}
