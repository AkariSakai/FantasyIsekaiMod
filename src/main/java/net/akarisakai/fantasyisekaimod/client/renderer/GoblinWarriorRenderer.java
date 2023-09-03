package net.akarisakai.fantasyisekaimod.client.renderer;

import net.akarisakai.fantasyisekaimod.FantasyIsekaiMod;
import net.akarisakai.fantasyisekaimod.client.custom.GoblinWarriorEntity;
import net.akarisakai.fantasyisekaimod.client.model.GoblinWarriorEntityModel;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;


public class GoblinWarriorRenderer extends MobRenderer<GoblinWarriorEntity, GoblinWarriorEntityModel<GoblinWarriorEntity>> {
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(FantasyIsekaiMod.MOD_ID,"textures/entity/goblinwarrior_entity.png");

    public GoblinWarriorRenderer(EntityRendererProvider.Context context) {
        super(context, new GoblinWarriorEntityModel<>(context.bakeLayer(GoblinWarriorEntityModel.LAYER_LOCATION)), 0.36f);
        this.addLayer(new ItemInHandLayer<GoblinWarriorEntity, GoblinWarriorEntityModel<GoblinWarriorEntity>>(this, context.getItemInHandRenderer()));
    }

    @Override
    public ResourceLocation getTextureLocation(GoblinWarriorEntity entity) {
        return TEXTURE;
    }
}

