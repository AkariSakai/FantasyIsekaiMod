package net.akarisakai.fantasyisekaimod.client.renderer;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.akarisakai.fantasyisekaimod.FantasyIsekaiMod;
import net.akarisakai.fantasyisekaimod.client.model.GoblinEntityModel;
import net.akarisakai.fantasyisekaimod.client.custom.GoblinEntity;
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
import org.joml.Random;


public class GoblinRenderer extends MobRenderer<GoblinEntity, GoblinEntityModel<GoblinEntity>> {
    private static final ResourceLocation[] TEXTURES = {
            new ResourceLocation(FantasyIsekaiMod.MOD_ID, "textures/entity/goblin_entity.png"),
            new ResourceLocation(FantasyIsekaiMod.MOD_ID, "textures/entity/goblin_entity1.png"),
            new ResourceLocation(FantasyIsekaiMod.MOD_ID, "textures/entity/goblin_entity2.png")
    };
    private final Random random = new Random();

    private ItemInHandRenderer itemInHandRenderer;

    public GoblinRenderer(EntityRendererProvider.Context context) {
        super(context, new GoblinEntityModel<>(context.bakeLayer(GoblinEntityModel.LAYER_LOCATION)), 0.36f);
        this.addLayer(new ItemInHandLayer<GoblinEntity, GoblinEntityModel<GoblinEntity>>(this, context.getItemInHandRenderer()));
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



    private Object getParentModel() {
        return null;
    }


    @Override
    public ResourceLocation getTextureLocation(GoblinEntity entity) {
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