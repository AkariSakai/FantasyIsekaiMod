package net.akarisakai.fantasyisekaimod.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.akarisakai.fantasyisekaimod.FantasyIsekaiMod;
import net.akarisakai.fantasyisekaimod.client.custom.GoblinArcherEntity;
import net.akarisakai.fantasyisekaimod.client.model.GoblinArcherEntityModel;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class GoblinArcherRenderer extends MobRenderer<GoblinArcherEntity, GoblinArcherEntityModel<GoblinArcherEntity>> {
    private static final ResourceLocation TEXTURE
            = new ResourceLocation(FantasyIsekaiMod.MOD_ID,"textures/entity/goblinarcher_entity.png");
    public GoblinArcherRenderer(EntityRendererProvider.Context ctx) {
        super(ctx, new GoblinArcherEntityModel<>(ctx.bakeLayer(GoblinArcherEntityModel.LAYER_LOCATION)), 0.36f);
        this.addLayer(new ItemInHandLayer<GoblinArcherEntity, GoblinArcherEntityModel<GoblinArcherEntity>>(this, ctx.getItemInHandRenderer()));
    }
    protected void renderArmWithItem(LivingEntity pLivingEntity, ItemStack pItemStack, ItemDisplayContext pDisplayContext, HumanoidArm pArm, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {
        if (!pItemStack.isEmpty()) {
            pPoseStack.pushPose();
            ((ArmedModel)this.getParentModel()).translateToHand(pArm, pPoseStack);
            pPoseStack.mulPose(Axis.XP.rotationDegrees(-90.0F));
            pPoseStack.mulPose(Axis.YP.rotationDegrees(180.0F));
            boolean $$7 = pArm == HumanoidArm.RIGHT;
            pPoseStack.translate((float)($$7 ? -1 : 1) / 16.0F, 0.125F, -0.625F);

            this.itemInHandRenderer.renderItem(pLivingEntity, pItemStack, pDisplayContext, $$7, pPoseStack, pBuffer, pPackedLight);
            pPoseStack.popPose();
        }
    }
    private ItemInHandRenderer itemInHandRenderer;
    private Object getParentModel() {
        return null;
    }

    @Override
    public ResourceLocation getTextureLocation(GoblinArcherEntity entity) {
        return TEXTURE;
    }
}
