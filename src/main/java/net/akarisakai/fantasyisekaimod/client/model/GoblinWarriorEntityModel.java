package net.akarisakai.fantasyisekaimod.client.model;// Made with Blockbench 4.8.3
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.akarisakai.fantasyisekaimod.FantasyIsekaiMod;
import net.akarisakai.fantasyisekaimod.client.animation.GoblinWarriorEntityAnimation;
import net.akarisakai.fantasyisekaimod.client.custom.GoblinWarriorEntity;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.HumanoidArm;
import org.jetbrains.annotations.NotNull;

public class GoblinWarriorEntityModel<T extends GoblinWarriorEntity> extends HierarchicalModel<T> implements ArmedModel {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(FantasyIsekaiMod.MOD_ID, "goblinwarrior_entity"), "main");
	private final ModelParts parts;

	public GoblinWarriorEntityModel(ModelPart root) {
		ModelPart Body = root.getChild("Body");
		ModelPart Head = Body.getChild("h_head");
		ModelPart RightArm = Body.getChild("RightArm");
		ModelPart LeftArm = Body.getChild("LeftArm");
		ModelPart LeftHand = LeftArm.getChild("LeftHand");
		ModelPart RightHand = RightArm.getChild("RightHand");

		this.parts = new GoblinWarriorEntityModel.ModelParts(Body, Head, RightArm, LeftArm, RightHand, LeftHand);
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition Body = partdefinition.addOrReplaceChild("Body", CubeListBuilder.create(), PartPose.offset(0.0F, 25.0F, 0.0F));

		PartDefinition h_head = Body.addOrReplaceChild("h_head", CubeListBuilder.create().texOffs(0, 15).addBox(-4.0F, -7.0F, -4.0F, 8.0F, 7.0F, 8.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(-4.0F, -7.0F, -4.0F, 8.0F, 7.0F, 8.0F, new CubeDeformation(0.3F)), PartPose.offset(0.0F, -16.0F, -3.0F));

		PartDefinition h_ears = h_head.addOrReplaceChild("h_ears", CubeListBuilder.create(), PartPose.offset(0.0F, 16.0F, 0.0F));

		PartDefinition cube_r1 = h_ears.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(4, 20).addBox(-1.0F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.0F, -18.0F, 0.0F, 1.7423F, 1.1111F, 0.8803F));

		PartDefinition cube_r2 = h_ears.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 0).addBox(1.2F, -1.0F, 0.0F, 0.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.0F, -19.0F, -2.0F, 0.2449F, -0.4677F, -0.1122F));

		PartDefinition cube_r3 = h_ears.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(0, 0).addBox(-1.2F, -1.0F, 0.0F, 0.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.0F, -19.0F, -2.0F, 0.2449F, 0.4677F, 0.1122F));

		PartDefinition h_nose = h_head.addOrReplaceChild("h_nose", CubeListBuilder.create(), PartPose.offset(0.0F, 16.0F, 0.0F));

		PartDefinition Mole_r1 = h_nose.addOrReplaceChild("Mole_r1", CubeListBuilder.create().texOffs(0, 20).addBox(-1.8F, 2.0F, -1.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(8, 39).addBox(-1.0F, 0.0F, -2.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -19.0F, -4.0F, 0.0873F, 0.0F, 0.0F));

		PartDefinition Chest = Body.addOrReplaceChild("Chest", CubeListBuilder.create().texOffs(28, 11).addBox(-3.0F, -8.0F, -2.0F, 6.0F, 8.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(28, 26).addBox(-3.0F, -1.0F, -2.0F, 6.0F, 4.0F, 3.9F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(0.0F, -9.0F, 0.0F, 0.1745F, 0.0F, 0.0F));

		PartDefinition BodyLayer_r1 = Chest.addOrReplaceChild("BodyLayer_r1", CubeListBuilder.create().texOffs(24, 0).addBox(-3.0F, -3.0F, -2.0F, 6.0F, 3.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.1745F, 0.0F, 0.0F));

		PartDefinition LeftArm = Body.addOrReplaceChild("LeftArm", CubeListBuilder.create().texOffs(0, 39).addBox(-2.0F, 0.0F, -2.0F, 2.0F, 9.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.0F, -17.0F, -2.0F, -0.0873F, 0.0F, 0.0F));

		PartDefinition LeftHand = LeftArm.addOrReplaceChild("LeftHand", CubeListBuilder.create().texOffs(0, 45).addBox(3.0F, -10.0F, -2.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-5.0F, 16.0F, 0.0F));

		PartDefinition Shield = LeftArm.addOrReplaceChild("Shield", CubeListBuilder.create().texOffs(0, 30).addBox(-4.0F, -5.0F, -2.0F, 8.0F, 8.0F, 1.0F, new CubeDeformation(0.15F))
				.texOffs(0, 18).addBox(-2.0F, -1.0F, -1.0F, 3.5F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 7.0F, -2.0F, 0.1794F, -0.8095F, -0.1592F));

		PartDefinition RightLeg = Body.addOrReplaceChild("RightLeg", CubeListBuilder.create().texOffs(28, 38).addBox(-3.6F, 0.0F, -2.0F, 2.0F, 8.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(1.1F, -9.0F, 0.0F));

		PartDefinition LeftLeg = Body.addOrReplaceChild("LeftLeg", CubeListBuilder.create().texOffs(18, 30).addBox(1.6F, 0.0F, -2.0F, 2.0F, 8.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.1F, -9.0F, 0.0F));

		PartDefinition RightArm = Body.addOrReplaceChild("RightArm", CubeListBuilder.create().texOffs(38, 39).addBox(0.0F, 1.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(0.0F, 0.5F, -1.0F, 2.0F, 1.5F, 2.0F, new CubeDeformation(0.5F)), PartPose.offset(-5.0F, -17.0F, -3.0F));

		PartDefinition RightHand = RightArm.addOrReplaceChild("RightHand", CubeListBuilder.create().texOffs(38, 44).addBox(-5.0F, -10.0F, -4.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(5.0F, 16.0F, 3.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		root().getAllParts().forEach(ModelPart::resetPose);
		animate(entity.idleAnimationState, GoblinWarriorEntityAnimation.GOBLINWARRIOR_IDLE, ageInTicks);
		this.parts.Head().yRot = netHeadYaw * Mth.DEG_TO_RAD;
		this.parts.Head().xRot = headPitch * Mth.DEG_TO_RAD;

		if(!entity.isInWaterOrBubble()) {
			animateWalk(GoblinWarriorEntityAnimation.GOBLINWARRIOR_WALK, limbSwing, limbSwingAmount, 1.0F, 2.5F);
		}

		if (entity.hasItemInSlot(EquipmentSlot.MAINHAND)) {
			if (entity.isAggressive()) {
				if (entity.isLeftHanded()) {
					this.parts.LeftArm.xRot = -2.0944F;
					this.parts.LeftArm.yRot = -0.1745F;
				} else {
					this.parts.RightArm.xRot = -2.0944F;
					this.parts.RightArm.yRot = 0.1745F;
				}
			}
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


	private record ModelParts(ModelPart Body, ModelPart Head, ModelPart RightArm, ModelPart LeftArm, ModelPart LeftHand, ModelPart RightHand){}
}