package net.akarisakai.fantasyisekaimod.client.renderer;


import net.akarisakai.fantasyisekaimod.FantasyIsekaiMod;
import net.akarisakai.fantasyisekaimod.client.custom.DirewolfEntity;
import net.akarisakai.fantasyisekaimod.client.custom.GoblinEntity;
import net.akarisakai.fantasyisekaimod.client.model.DirewolfEntityModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.joml.Random;


public class DirewolfRenderer extends MobRenderer<DirewolfEntity, DirewolfEntityModel<DirewolfEntity>> {
    private static final ResourceLocation[] TEXTURES = {
            new ResourceLocation(FantasyIsekaiMod.MOD_ID, "textures/entity/direwolf_entity.png"),
            new ResourceLocation(FantasyIsekaiMod.MOD_ID, "textures/entity/direwolf_entity1.png"),


    };
    private final Random random = new Random();

    public DirewolfRenderer(EntityRendererProvider.Context ctx) {
        super(ctx, new DirewolfEntityModel<>(ctx.bakeLayer(DirewolfEntityModel.LAYER_LOCATION)), 0.8F);
    }
    @Override
    public ResourceLocation getTextureLocation(DirewolfEntity entity) {
        if (!entity.getPersistentData().contains("TextureIndex")) {
            int randomIndex = random.nextInt(TEXTURES.length);
            entity.getPersistentData().putInt("TextureIndex", randomIndex);
        }
        int textureIndex = entity.getPersistentData().getInt("TextureIndex");
        return TEXTURES[textureIndex];
    }
}