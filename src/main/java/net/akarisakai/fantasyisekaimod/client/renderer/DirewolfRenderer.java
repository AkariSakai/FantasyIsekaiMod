package net.akarisakai.fantasyisekaimod.client.renderer;


import net.akarisakai.fantasyisekaimod.FantasyIsekaiMod;
import net.akarisakai.fantasyisekaimod.client.custom.DirewolfEntity;
import net.akarisakai.fantasyisekaimod.client.model.DirewolfEntityModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;


public class DirewolfRenderer extends MobRenderer<DirewolfEntity, DirewolfEntityModel<DirewolfEntity>> {
    private static final ResourceLocation TEXTURE
            = new ResourceLocation(FantasyIsekaiMod.MOD_ID,"textures/entity/direwolf_entity.png");
    public DirewolfRenderer(EntityRendererProvider.Context ctx) {
        super(ctx, new DirewolfEntityModel<>(ctx.bakeLayer(DirewolfEntityModel.LAYER_LOCATION)), 0.8F);
    }
    @Override
    public ResourceLocation getTextureLocation(DirewolfEntity entity) {
        return TEXTURE;
    }
}