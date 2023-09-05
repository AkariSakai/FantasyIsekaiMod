package net.akarisakai.fantasyisekaimod.client.model;// Made with Blockbench 4.8.3
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.akarisakai.fantasyisekaimod.FantasyIsekaiMod;
import net.akarisakai.fantasyisekaimod.client.animation.GoblinArcherEntityAnimation;
import net.akarisakai.fantasyisekaimod.client.custom.GoblinArcherEntity;
import net.minecraft.client.model.AnimationUtils;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;

public class GoblinArcherEntityModel<T extends GoblinArcherEntity> extends HierarchicalModel<T> implements ArmedModel {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(FantasyIsekaiMod.MOD_ID, "goblinarcher_entity"), "main");
	private final GoblinArcherEntityModel.ModelParts parts;

	public GoblinArcherEntityModel(ModelPart root) {
		ModelPart Body = root.getChild("Body");
		ModelPart Head = Body.getChild("h_head");
		ModelPart RightArm = Body.getChild("RightArm");
		ModelPart LeftArm = Body.getChild("LeftArm");
		ModelPart LeftHand = LeftArm.getChild("LeftHand");
		ModelPart RightHand = RightArm.getChild("RightHand");

		this.parts = new GoblinArcherEntityModel.ModelParts(Body, Head, RightArm, LeftArm, RightHand, LeftHand);
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition Body = partdefinition.addOrReplaceChild("Body", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition h_head = Body.addOrReplaceChild("h_head", CubeListBuilder.create().texOffs(24, 4).addBox(-4.0F, -7.0F, -4.0F, 8.0F, 7.0F, 8.0F, new CubeDeformation(0.0F))
		.texOffs(1, 13).addBox(-4.0F, -7.0F, -4.0F, 8.0F, 7.0F, 7.0F, new CubeDeformation(0.3F)), PartPose.offset(0.0F, -15.0F, -3.0F));

		PartDefinition Hood = h_head.addOrReplaceChild("Hood", CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, -3.0F, -6.0F, 8.0F, 0.0F, 12.0F, new CubeDeformation(0.0F))
		.texOffs(28, 0).addBox(0.0F, -3.0F, -5.0F, 8.0F, 4.0F, 0.0F, new CubeDeformation(0.0F))
		.texOffs(24, 19).addBox(-1.0F, -3.0F, -4.0F, 10.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
		.texOffs(0, 29).addBox(-1.0F, 5.0F, -3.0F, 10.0F, 1.0F, 7.0F, new CubeDeformation(0.25F))
		.texOffs(25, 20).addBox(-1.0F, 5.0F, -3.0F, 10.0F, 2.0F, 7.0F, new CubeDeformation(0.25F)), PartPose.offset(-4.0F, -5.0F, 0.0F));

		PartDefinition cube_r1 = Hood.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 7).addBox(8.4F, -2.0F, -2.0F, 0.0F, 8.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(0, 24).addBox(8.4F, -2.0F, -5.0F, 0.0F, 9.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.0873F));

		PartDefinition cube_r2 = Hood.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(47, 25).addBox(4.4F, -2.0F, 2.0F, 0.0F, 7.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.0873F));

		PartDefinition cube_r3 = Hood.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(8, 49).addBox(-0.3F, -2.0F, -2.0F, 0.0F, 7.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(16, 51).addBox(-0.3F, -2.0F, 2.0F, 0.0F, 7.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(24, 52).addBox(-0.3F, -2.0F, -5.0F, 0.0F, 9.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0873F));

		PartDefinition cube_r4 = Hood.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(48, 0).addBox(-1.0F, -5.0F, 5.0F, 10.0F, 9.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.2182F, 0.0F, 0.0F));

		PartDefinition cube_r5 = Hood.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(0, 26).addBox(1.0F, -4.0F, -5.0F, 0.0F, 4.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.48F));

		PartDefinition cube_r6 = Hood.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(22, 26).addBox(-1.0F, -4.0F, -5.0F, 0.0F, 4.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.5236F));

		PartDefinition h_ears = h_head.addOrReplaceChild("h_ears", CubeListBuilder.create(), PartPose.offset(0.0F, 16.0F, 0.0F));

		PartDefinition cube_r7 = h_ears.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(16, 27).addBox(-1.0F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.0F, -18.0F, 0.0F, 1.7423F, 1.1111F, 0.8803F));

		PartDefinition cube_r8 = h_ears.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(26, 37).addBox(1.2F, -1.0F, 0.0F, 0.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.0F, -19.0F, -2.0F, 0.2725F, -0.6364F, -0.1646F));

		PartDefinition cube_r9 = h_ears.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(26, 37).addBox(-1.2F, -1.0F, 0.0F, 0.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.0F, -19.0F, -2.0F, 0.2725F, 0.6364F, 0.1646F));

		PartDefinition h_nose = h_head.addOrReplaceChild("h_nose", CubeListBuilder.create(), PartPose.offset(0.0F, 16.0F, 0.0F));

		PartDefinition Mole_r1 = h_nose.addOrReplaceChild("Mole_r1", CubeListBuilder.create().texOffs(7, 0).addBox(-1.8F, 2.0F, -1.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(52, 19).addBox(-1.0F, 0.0F, -2.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -19.0F, -4.0F, 0.0873F, 0.0F, 0.0F));

		PartDefinition Chest = Body.addOrReplaceChild("Chest", CubeListBuilder.create().texOffs(0, 41).addBox(-3.0F, -8.0F, -2.0F, 6.0F, 8.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(20, 47).addBox(-3.0F, -1.0F, -2.0F, 6.0F, 4.0F, 3.9F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(0.0F, -8.0F, 0.0F, 0.1745F, 0.0F, 0.0F));

		PartDefinition BodyLayer_r1 = Chest.addOrReplaceChild("BodyLayer_r1", CubeListBuilder.create().texOffs(27, 29).addBox(-3.0F, -3.0F, -2.0F, 6.0F, 3.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.1745F, 0.0F, 0.0F));

		PartDefinition quiver = Chest.addOrReplaceChild("quiver", CubeListBuilder.create(), PartPose.offset(-7.0F, 0.0F, 2.0F));

		PartDefinition Quiver_r1 = quiver.addOrReplaceChild("Quiver_r1", CubeListBuilder.create().texOffs(62, 38).addBox(4.0622F, -9.5F, 1.0F, 3.0F, 4.0F, 3.0F, new CubeDeformation(-0.5F))
		.texOffs(62, 38).addBox(4.0622F, -8.5F, 1.0F, 3.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(39, 36).addBox(3.0622F, -6.5F, 0.0F, 5.0F, 10.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.1745F, 0.0F, -0.5236F));

		PartDefinition Fur = quiver.addOrReplaceChild("Fur", CubeListBuilder.create(), PartPose.offset(0.0F, -6.0F, 3.0F));

		PartDefinition Fur_r1 = Fur.addOrReplaceChild("Fur_r1", CubeListBuilder.create().texOffs(48, 9).addBox(-0.8038F, 0.1667F, -0.0934F, 5.0F, 2.0F, 0.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(1.0F, 0.0F, 3.0F, 0.1309F, 0.0F, -0.5236F));

		PartDefinition Fur_r2 = Fur.addOrReplaceChild("Fur_r2", CubeListBuilder.create().texOffs(48, 4).addBox(0.0981F, 0.3798F, 0.0213F, 0.0F, 2.0F, 5.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(4.0F, -3.0F, -2.0F, -0.164F, -0.0647F, -0.7778F));

		PartDefinition Fur_r3 = Fur.addOrReplaceChild("Fur_r3", CubeListBuilder.create().texOffs(48, 9).addBox(-1.3038F, 0.1513F, -0.0189F, 5.0F, 2.0F, 0.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(1.0F, -1.0F, -2.0F, -0.48F, 0.0F, -0.5236F));

		PartDefinition Fur_r4 = Fur.addOrReplaceChild("Fur_r4", CubeListBuilder.create().texOffs(48, 4).addBox(0.0622F, 0.1382F, -2.0521F, 0.0F, 2.0F, 5.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.1666F, 0.0522F, -0.2225F));

		PartDefinition LeftArm = Body.addOrReplaceChild("LeftArm", CubeListBuilder.create().texOffs(0, 53).addBox(-2.0F, 1.0F, -2.0F, 2.0F, 9.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(5.0F, -16.0F, -2.0F));

		PartDefinition LeftHand = LeftArm.addOrReplaceChild("LeftHand", CubeListBuilder.create().texOffs(0, 59).addBox(3.0F, -9.0F, -2.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-5.0F, 16.0F, 0.0F));

		PartDefinition RightLeg = Body.addOrReplaceChild("RightLeg", CubeListBuilder.create().texOffs(40, 51).addBox(-3.6F, 0.0F, -2.0F, 2.0F, 8.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(1.1F, -8.0F, 0.0F));

		PartDefinition LeftLeg = Body.addOrReplaceChild("LeftLeg", CubeListBuilder.create().texOffs(0, 0).addBox(1.6F, 0.0F, -2.0F, 2.0F, 8.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.1F, -8.0F, 0.0F));

		PartDefinition RightArm = Body.addOrReplaceChild("RightArm", CubeListBuilder.create().texOffs(50, 51).addBox(0.0F, 1.0F, -1.0F, 2.0F, 9.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-5.0F, -16.0F, -3.0F));

		PartDefinition RightHand = RightArm.addOrReplaceChild("RightHand", CubeListBuilder.create().texOffs(50, 57).addBox(-5.0F, -9.0F, -4.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(5.0F, 16.0F, 3.0F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		root().getAllParts().forEach(ModelPart::resetPose);
		animate(entity.idleAnimationState, GoblinArcherEntityAnimation.GOBLINARCHER_IDLE, ageInTicks);
		this.parts.Head().yRot = netHeadYaw * Mth.DEG_TO_RAD;
		this.parts.Head().xRot = headPitch * Mth.DEG_TO_RAD;

		if(!entity.isInWaterOrBubble()) {
			animateWalk(GoblinArcherEntityAnimation.GOBLINARCHER_WALK, limbSwing, limbSwingAmount, 1.0F, 2.5F);
		}

		ItemStack $$6 = entity.getMainHandItem();
		if (entity.isAggressive() && $$6.is(Items.BOW)) {
			float $$7 = Mth.sin(this.attackTime * 3.1415927F);
			float $$8 = Mth.sin((1.0F - (1.0F - this.attackTime) * (1.0F - this.attackTime)) * 3.1415927F);
			this.parts.RightArm.zRot = 0.0F;
			this.parts.LeftArm.zRot = 0.0F;
			this.parts.RightArm.yRot = -(0.1F - $$7 * 0.6F);
			this.parts.LeftArm.yRot = 0.1F - $$7 * 0.6F;
			this.parts.RightArm.xRot = -1.5707964F;
			this.parts.LeftArm.xRot = -1.5707964F;
			ModelPart var10000 = this.parts.RightArm;
			var10000.xRot -= $$7 * 1.2F - $$8 * 0.4F;
			var10000 = this.parts.LeftArm;
			var10000.xRot -= $$7 * 1.2F - $$8 * 0.4F;
			AnimationUtils.bobArms(this.parts.RightArm, this.parts.LeftArm, ageInTicks);
		}

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		this.parts.Body().render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	public @NotNull ModelPart root() {
		return this.parts.Body();
	}

	public void translateToHand(HumanoidArm humanoidArm, PoseStack poseStack) {
		if (humanoidArm == HumanoidArm.LEFT) {
			this.parts.LeftHand().translateAndRotate(poseStack);

			poseStack.mulPose(Axis.XP.rotationDegrees(0F)); // Rotate the item along the X-axis
			poseStack.translate(-0.5, 0.5, -0.3); // Adjust the position as needed (X, Y, Z)
			// Handle left hand translation or transformations if needed
		} else if (humanoidArm == HumanoidArm.RIGHT) {
			// Translate for the right hand
			this.parts.RightHand().translateAndRotate(poseStack);


			poseStack.mulPose(Axis.XP.rotationDegrees(0F)); // Rotate the item along the X-axis
			poseStack.translate(0.5, 0.5, 0); // Adjust the position as needed (X, Y, Z)


		}
		switch (humanoidArm) {
			case LEFT -> {
				this.parts.LeftArm.translateAndRotate(poseStack);
				poseStack.translate(0.045, 0.096, 0.0);
				poseStack.scale(0.75F, 0.75F, 0.75F);
			}
			case RIGHT -> {
				this.parts.RightArm.translateAndRotate(poseStack);
				poseStack.translate(-0.045, 0.096, 0.0);
				poseStack.scale(0.75F, 0.75F, 0.75F);
			}
		}
	}

	private record ModelParts(ModelPart Body, ModelPart Head, ModelPart RightArm, ModelPart LeftArm, ModelPart LeftHand, ModelPart RightHand){

	}
}