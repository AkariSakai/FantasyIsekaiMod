package net.akarisakai.fantasyisekaimod.client.renderer;

import net.akarisakai.fantasyisekaimod.FantasyIsekaiMod;
import net.akarisakai.fantasyisekaimod.client.custom.OrcEntity;
import net.akarisakai.fantasyisekaimod.client.model.OrcEntityModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class OrcRenderer extends MobRenderer<OrcEntity, OrcEntityModel<OrcEntity>> {
    private static final ResourceLocation TEXTURE
            = new ResourceLocation(FantasyIsekaiMod.MOD_ID,"textures/entity/orc_entity.png");
    public OrcRenderer(EntityRendererProvider.Context ctx) {
        super(ctx, new OrcEntityModel<>(ctx.bakeLayer(OrcEntityModel.LAYER_LOCATION)), 1.1f);
    }

    @Override
    public ResourceLocation getTextureLocation(OrcEntity entity) {
        return TEXTURE;
    }
}
