package net.akarisakai.fantasyisekaimod.client.renderer;

import net.akarisakai.fantasyisekaimod.FantasyIsekaiMod;
import net.akarisakai.fantasyisekaimod.client.custom.GoblinShamanEntity;
import net.akarisakai.fantasyisekaimod.client.model.GoblinShamanEntityModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class GoblinShamanRenderer extends MobRenderer<GoblinShamanEntity, GoblinShamanEntityModel<GoblinShamanEntity>> {
    private static final ResourceLocation TEXTURE
            = new ResourceLocation(FantasyIsekaiMod.MOD_ID,"textures/entity/goblinshaman_entity.png");
    public GoblinShamanRenderer(EntityRendererProvider.Context ctx) {
        super(ctx, new GoblinShamanEntityModel<>(ctx.bakeLayer(GoblinShamanEntityModel.LAYER_LOCATION)), 0.36f);
    }

    @Override
    public ResourceLocation getTextureLocation(GoblinShamanEntity entity) {
        return TEXTURE;
    }
}
