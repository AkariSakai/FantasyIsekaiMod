package net.akarisakai.fantasyisekaimod.client.renderer;

import net.akarisakai.fantasyisekaimod.FantasyIsekaiMod;
import net.akarisakai.fantasyisekaimod.client.custom.GoblinWarriorEntity;
import net.akarisakai.fantasyisekaimod.client.model.GoblinWarriorEntityModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;
import org.joml.Random;


public class GoblinWarriorRenderer extends MobRenderer<GoblinWarriorEntity, GoblinWarriorEntityModel<GoblinWarriorEntity>> {
    private static final ResourceLocation[] TEXTURES = {
            new ResourceLocation(FantasyIsekaiMod.MOD_ID, "textures/entity/goblinwarrior_entity.png"),
            new ResourceLocation(FantasyIsekaiMod.MOD_ID, "textures/entity/goblinwarrior_entity1.png"),
            new ResourceLocation(FantasyIsekaiMod.MOD_ID, "textures/entity/goblinwarrior_entity2.png")
    };
    private final Random random = new Random();
    public GoblinWarriorRenderer(EntityRendererProvider.Context context) {
        super(context, new GoblinWarriorEntityModel<>(context.bakeLayer(GoblinWarriorEntityModel.LAYER_LOCATION)), 0.36f);
        this.addLayer(new ItemInHandLayer<GoblinWarriorEntity, GoblinWarriorEntityModel<GoblinWarriorEntity>>(this, context.getItemInHandRenderer()));
    }

    @Override
    public ResourceLocation getTextureLocation(GoblinWarriorEntity entity) {
        // Generate a random index when the entity is first created and use it as the texture index.
        // Store this index on the entity to ensure it keeps the same texture.
        if (!entity.getPersistentData().contains("TextureIndex")) {
            int randomIndex = random.nextInt(TEXTURES.length);
            entity.getPersistentData().putInt("TextureIndex", randomIndex);
        }
        int textureIndex = entity.getPersistentData().getInt("TextureIndex");
        return TEXTURES[textureIndex];
    }
}

