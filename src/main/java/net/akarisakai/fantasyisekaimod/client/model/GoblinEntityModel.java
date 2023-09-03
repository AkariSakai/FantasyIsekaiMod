package net.akarisakai.fantasyisekaimod.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.akarisakai.fantasyisekaimod.FantasyIsekaiMod;
import net.akarisakai.fantasyisekaimod.client.animation.GoblinEntityAnimation;
import net.akarisakai.fantasyisekaimod.client.custom.GoblinEntity;
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
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.item.TridentItem;
import org.jetbrains.annotations.NotNull;

public class GoblinEntityModel<T extends GoblinEntity> extends HierarchicalModel<T> implements ArmedModel {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(FantasyIsekaiMod.MOD_ID, "goblin_entity"), "main");
	private final ModelParts parts;

	public GoblinEntityModel(ModelPart root) {
		ModelPart Body = root.getChild("Body");
		ModelPart Head = Body.getChild("h_head");
		ModelPart RightArm = Body.getChild("RightArm");
		ModelPart LeftArm = Body.getChild("LeftArm");
		ModelPart LeftHand = LeftArm.getChild("LeftHand");
		ModelPart RightHand = RightArm.getChild("RightHand");

		this.parts = new ModelParts(Body, Head, RightArm, LeftArm, RightHand, LeftHand);
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition Body = partdefinition.addOrReplaceChild("Body", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition h_head = Body.addOrReplaceChild("h_head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -7.0F, -4.0F, 8.0F, 7.0F, 8.0F, new CubeDeformation(0.0F))
				.texOffs(32, 0).addBox(-4.0F, -7.0F, -4.0F, 8.0F, 7.0F, 8.0F, new CubeDeformation(0.3F)), PartPose.offset(0.0F, -15.0F, -3.0F));

		PartDefinition h_ears = h_head.addOrReplaceChild("h_ears", CubeListBuilder.create(), PartPose.offset(0.0F, 16.0F, 0.0F));

		PartDefinition cube_r1 = h_ears.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(51, 55).addBox(-1.0F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.0F, -18.0F, 0.0F, 1.7423F, 1.1111F, 0.8803F));

		PartDefinition cube_r2 = h_ears.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 0).addBox(1.2F, -1.0F, 0.0F, 0.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.0F, -19.0F, -2.0F, 0.2449F, -0.4677F, -0.1122F));

		PartDefinition cube_r3 = h_ears.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(24, 0).addBox(-1.2F, -1.0F, 0.0F, 0.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.0F, -19.0F, -2.0F, 0.2449F, 0.4677F, 0.1122F));

		PartDefinition h_nose = h_head.addOrReplaceChild("h_nose", CubeListBuilder.create(), PartPose.offset(0.0F, 16.0F, 0.0F));

		PartDefinition Mole_r1 = h_nose.addOrReplaceChild("Mole_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-1.8F, 2.0F, -1.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(27, 26).addBox(-1.0F, 0.0F, -2.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -19.0F, -4.0F, 0.0873F, 0.0F, 0.0F));

		PartDefinition Chest = Body.addOrReplaceChild("Chest", CubeListBuilder.create().texOffs(0, 15).addBox(-3.0F, -8.0F, -2.0F, 6.0F, 8.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(13, 38).addBox(-3.0F, -1.0F, -2.0F, 6.0F, 4.0F, 3.9F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(0.0F, -8.0F, 0.0F, 0.1745F, 0.0F, 0.0F));

		PartDefinition BodyLayer_r1 = Chest.addOrReplaceChild("BodyLayer_r1", CubeListBuilder.create().texOffs(15, 49).addBox(-3.0F, -3.0F, -2.0F, 6.0F, 3.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.1745F, 0.0F, 0.0F));

		PartDefinition LeftArm = Body.addOrReplaceChild("LeftArm", CubeListBuilder.create().texOffs(8, 27).addBox(-2.0F, 0.0F, -2.0F, 2.0F, 9.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(5.0F, -16.0F, -2.0F));

		PartDefinition LeftHand = LeftArm.addOrReplaceChild("LeftHand", CubeListBuilder.create().texOffs(8, 33).addBox(3.0F, -10.0F, -2.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-5.0F, 16.0F, 0.0F));

		PartDefinition RightLeg = Body.addOrReplaceChild("RightLeg", CubeListBuilder.create().texOffs(17, 26).addBox(-3.6F, 0.0F, -2.0F, 2.0F, 8.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(1.1F, -8.0F, 0.0F));

		PartDefinition LeftLeg = Body.addOrReplaceChild("LeftLeg", CubeListBuilder.create().texOffs(20, 15).addBox(1.6F, 0.0F, -2.0F, 2.0F, 8.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.1F, -8.0F, 0.0F));

		PartDefinition RightArm = Body.addOrReplaceChild("RightArm", CubeListBuilder.create().texOffs(0, 27).addBox(0.0F, 0.0F, -1.0F, 2.0F, 9.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-5.0F, -16.0F, -3.0F));

		PartDefinition RightHand = RightArm.addOrReplaceChild("RightHand", CubeListBuilder.create().texOffs(0, 33).addBox(-5.0F, -10.0F, -4.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(5.0F, 16.0F, 3.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		root().getAllParts().forEach(ModelPart::resetPose);
		animate(entity.idleAnimationState, GoblinEntityAnimation.GOBLIN_IDLE, ageInTicks);
		animate(entity.attackAnimationState , GoblinEntityAnimation.GOBLIN_ATTACK, ageInTicks);
		this.parts.Head().yRot = netHeadYaw * Mth.DEG_TO_RAD;
		this.parts.Head().xRot = headPitch * Mth.DEG_TO_RAD;

		if(!entity.isInWaterOrBubble()) {
			animateWalk(GoblinEntityAnimation.GOBLIN_WALK, limbSwing, limbSwingAmount, 1.0F, 2.5F);
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
			} else if (entity.getMainHandItem().getItem() instanceof CrossbowItem) {
				if (entity.isLeftHanded()) {
					if (entity.isCharging()) {
						this.parts.LeftArm.xRot = -0.6981F;
						this.parts.LeftArm.yRot = 0.3491F;
						this.parts.RightArm.xRot = -1.1345F;
						this.parts.RightArm.yRot = -0.5672F;
					} else if (CrossbowItem.isCharged(entity.getMainHandItem())) {
						this.parts.LeftArm.xRot = -1.4399F;
						this.parts.LeftArm.yRot = 0.2618F;
						this.parts.RightArm.xRot = -1.3963F;
						this.parts.RightArm.yRot = -0.3054F;
					}
				} else {
					if (entity.isCharging()) {
						this.parts.RightArm.xRot = -0.6981F;
						this.parts.RightArm.yRot = -0.3491F;
						this.parts.LeftArm.xRot = -1.1345F;
						this.parts.LeftArm.yRot = 0.5672F;
					} else if (CrossbowItem.isCharged(entity.getMainHandItem())) {
						this.parts.RightArm.xRot = -1.4399F;
						this.parts.RightArm.yRot = -0.2618F;
						this.parts.LeftArm.xRot = -1.3963F;
						this.parts.LeftArm.yRot = 0.3054F;
					}
				}
			}
		}
		if (entity.hasItemInSlot(EquipmentSlot.OFFHAND)) {
			if (entity.getOffhandItem().getItem() instanceof TridentItem) {
				if (entity.isAggressive()) {
					if (entity.isLeftHanded()) {
						this.parts.RightArm.xRot = 2.8798F;
						this.parts.LeftArm.xRot = 0.0F;
					} else {
						this.parts.LeftArm.xRot = 2.8798F;
						this.parts.RightArm.xRot = 0.0F;
					}
				}
			} else if (entity.getOffhandItem().getItem() instanceof ShieldItem) {
				if (entity.isBlocking()) {
					if (entity.isLeftHanded()) {
						this.parts.RightArm.xRot = -0.6981F;
						this.parts.RightArm.yRot = -0.2618F;
					} else {
						this.parts.LeftArm.xRot = -0.6981F;
						this.parts.LeftArm.yRot = 0.2618F;
					}
				}
			} else {
				if (entity.isLeftHanded()) {
					this.parts.RightArm.xRot = -0.8727F;
					this.parts.RightArm.yRot = 0.0873F;
                } else {
					this.parts.LeftArm.xRot = -0.8727F;
					this.parts.LeftArm.yRot = 0.0873F;
                }
                this.parts.Head.xRot = 0.1745F;
            }
		}
		if (this.attackTime > 0.0F) {
			if (entity.isAggressive()) {
				if (entity.isLeftHanded()) {
					float progress = this.attackTime;
					progress = 1.0F - this.attackTime;
					progress = progress * progress;
					progress = progress * progress;
					progress = 1.0F - progress;
					float f2 = Mth.sin(progress * (float) Math.PI);
					this.parts.LeftArm.xRot = (float) ((double) this.parts.LeftArm.xRot - ((double) f2 / 1.2D - (double) 1.0F));
				} else {
					float progress = this.attackTime;
					progress = 1.0F - this.attackTime;
					progress = progress * progress;
					progress = progress * progress;
					progress = 1.0F - progress;
					float f2 = Mth.sin(progress * (float) Math.PI);
					this.parts.RightArm.xRot = (float) ((double) this.parts.RightArm.xRot - ((double) f2 / 1.2D - (double) 1.0F));
				}
			} else {
				if (entity.hasItemInSlot(EquipmentSlot.OFFHAND)) {
					float progress = this.attackTime;
					this.parts.Body.yRot = Mth.sin(Mth.sqrt(progress) * ((float) Math.PI * 2F)) * 0.2F;
					this.parts.RightArm.yRot += this.parts.Body.yRot;
					this.parts.LeftArm.yRot += this.parts.Body.yRot;
					this.parts.LeftArm.xRot += this.parts.Body.yRot;
					progress = 1.0F - this.attackTime;
					progress = progress * progress;
					progress = progress * progress;
					progress = 1.0F - progress;
					float f2 = Mth.sin(progress * (float) Math.PI);
					float f3 = Mth.sin(this.attackTime * (float) Math.PI) * -(this.parts.Head.xRot - 0.7F) * 0.75F;
					this.parts.RightArm.xRot = (float) ((double) this.parts.RightArm.xRot - ((double) f2 * 1.2D + (double) f3));
					this.parts.RightArm.yRot += this.parts.Body.yRot * 2.0F;
					this.parts.RightArm.zRot += Mth.sin(this.attackTime * (float) Math.PI) * -0.4F;
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

	@Override
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