package net.akarisakai.fantasyisekaimod.client.model;// Made with Blockbench 4.8.2
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.akarisakai.fantasyisekaimod.FantasyIsekaiMod;
import net.akarisakai.fantasyisekaimod.client.animation.GoblinShamanEntityAnimation;
import net.akarisakai.fantasyisekaimod.client.custom.GoblinShamanEntity;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class GoblinShamanEntityModel<T extends GoblinShamanEntity> extends HierarchicalModel<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(FantasyIsekaiMod.MOD_ID, "goblinshaman_entity"), "main");
	private final GoblinShamanEntityModel.ModelParts parts;

	public GoblinShamanEntityModel(ModelPart root) {
		ModelPart Body = root.getChild("Body");
		ModelPart Head = Body.getChild("h_head");

		this.parts = new ModelParts(Body, Head);
	}
	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition Body = partdefinition.addOrReplaceChild("Body", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition h_head = Body.addOrReplaceChild("h_head", CubeListBuilder.create().texOffs(0, 15).addBox(-4.0F, -7.0F, -4.0F, 8.0F, 7.0F, 8.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-4.0F, -7.0F, -4.0F, 8.0F, 7.0F, 8.0F, new CubeDeformation(0.3F)), PartPose.offset(0.0F, -15.0F, -3.0F));

		PartDefinition h_ears = h_head.addOrReplaceChild("h_ears", CubeListBuilder.create(), PartPose.offset(0.0F, 16.0F, 0.0F));

		PartDefinition cube_r1 = h_ears.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(24, 2).addBox(-1.0F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.0F, -18.0F, 0.0F, 1.7423F, 1.1111F, 0.8803F));

		PartDefinition cube_r2 = h_ears.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 0).addBox(1.2F, -1.0F, 0.0F, 0.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.0F, -19.0F, -2.0F, 0.2449F, -0.4677F, -0.1122F));

		PartDefinition cube_r3 = h_ears.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(0, 11).addBox(-1.2F, -1.0F, 0.0F, 0.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.0F, -19.0F, -2.0F, 0.2449F, 0.4677F, 0.1122F));

		PartDefinition h_nose = h_head.addOrReplaceChild("h_nose", CubeListBuilder.create(), PartPose.offset(0.0F, 16.0F, 0.0F));

		PartDefinition Mole_r1 = h_nose.addOrReplaceChild("Mole_r1", CubeListBuilder.create().texOffs(24, 0).addBox(-1.8F, 2.0F, -1.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(48, 32).addBox(-1.0F, 0.0F, -2.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -19.0F, -4.0F, 0.0873F, 0.0F, 0.0F));

		PartDefinition Chest = Body.addOrReplaceChild("Chest", CubeListBuilder.create().texOffs(28, 11).addBox(-3.0F, -8.0F, -2.0F, 6.0F, 8.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(30, 27).addBox(-3.0F, -1.0F, -2.0F, 6.0F, 3.0F, 3.9F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(0.0F, -8.0F, 0.0F, 0.1745F, 0.0F, 0.0F));

		PartDefinition cube_r4 = Chest.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(20, 30).addBox(-1.0F, -2.0F, -2.0F, 2.0F, 6.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.1745F, 0.0F, 0.0F));

		PartDefinition BodyLayer_r1 = Chest.addOrReplaceChild("BodyLayer_r1", CubeListBuilder.create().texOffs(24, 0).addBox(-3.0F, -3.0F, -2.0F, 6.0F, 3.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.1745F, 0.0F, 0.0F));

		PartDefinition LeftArm = Body.addOrReplaceChild("LeftArm", CubeListBuilder.create().texOffs(39, 39).addBox(-2.0F, 0.0F, -2.0F, 2.0F, 9.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(20, 47).addBox(-2.0F, 5.0F, -2.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.15F)), PartPose.offset(5.0F, -16.0F, -2.0F));

		PartDefinition Skull1 = LeftArm.addOrReplaceChild("Skull1", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.48F));

		PartDefinition cube_r5 = Skull1.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(40, 24).addBox(-1.0F, 1.0F, -2.0F, 4.0F, 1.5F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(42, 5).addBox(-1.0F, 0.5F, -2.0F, 4.0F, 1.5F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(16, 30).addBox(-1.0F, 0.0F, -2.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.2182F));

		PartDefinition RightLeg = Body.addOrReplaceChild("RightLeg", CubeListBuilder.create().texOffs(29, 39).addBox(-3.6F, 0.0F, -2.0F, 2.0F, 8.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(1.1F, -8.0F, 0.0F));

		PartDefinition LeftLeg = Body.addOrReplaceChild("LeftLeg", CubeListBuilder.create().texOffs(4, 38).addBox(1.6F, 0.0F, -2.0F, 2.0F, 8.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.1F, -8.0F, 0.0F));

		PartDefinition RightArm = Body.addOrReplaceChild("RightArm", CubeListBuilder.create().texOffs(14, 38).addBox(0.0F, 0.0F, -1.0F, 2.0F, 9.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(0, 18).addBox(0.0F, 5.0F, -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.15F)), PartPose.offsetAndRotation(-5.0F, -16.0F, -3.0F, -0.5958F, 0.2926F, 0.025F));

		PartDefinition Skull2 = RightArm.addOrReplaceChild("Skull2", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 1.0F, 0.0F, 0.0F, -0.48F));

		PartDefinition cube_r6 = Skull2.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(40, 0).addBox(-3.0F, 1.0F, -2.0F, 4.0F, 1.5F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(40, 8).addBox(-3.0F, 0.5F, -2.0F, 4.0F, 1.5F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-1.0F, 0.0F, -2.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.2618F));

		PartDefinition Staff = RightArm.addOrReplaceChild("Staff", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 8.0F, 0.0F, 0.0786F, -0.1376F, -0.0471F));

		PartDefinition Skullskel_r1 = Staff.addOrReplaceChild("Skullskel_r1", CubeListBuilder.create().texOffs(4, 30).addBox(-1.0F, -16.0F, -2.5F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(22, 40).addBox(0.5F, -15.0F, -1.0F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.35F))
		.texOffs(24, 15).addBox(0.5F, -15.0F, -1.0F, 1.0F, 7.0F, 1.0F, new CubeDeformation(0.25F))
		.texOffs(0, 30).addBox(0.5F, -15.0F, -1.0F, 1.0F, 22.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.7375F, -0.0883F, -0.1214F));

		PartDefinition Fur = Staff.addOrReplaceChild("Fur", CubeListBuilder.create(), PartPose.offset(0.5777F, -8.2689F, -9.181F));

		PartDefinition Fur_r1 = Fur.addOrReplaceChild("Fur_r1", CubeListBuilder.create().texOffs(29, 7).addBox(-1.9581F, 0.0197F, -0.5623F, 5.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5769F, -1.0746F, 1.5851F, -0.0915F, -0.0883F, -0.1214F));

		PartDefinition Fur_r2 = Fur.addOrReplaceChild("Fur_r2", CubeListBuilder.create().texOffs(27, 34).addBox(0.059F, -0.5F, -2.1389F, 3.0F, 0.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.3633F, -0.2311F, 0.9305F, 0.5575F, -0.4917F, 0.4752F));

		PartDefinition Fur_r3 = Fur.addOrReplaceChild("Fur_r3", CubeListBuilder.create().texOffs(33, 34).addBox(-3.1086F, -0.1938F, -2.5814F, 3.0F, 0.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.2885F, -0.5373F, 0.7925F, 0.6445F, 0.4063F, -0.7431F));

		PartDefinition Fur_r4 = Fur.addOrReplaceChild("Fur_r4", CubeListBuilder.create().texOffs(29, 23).addBox(-2.2591F, 0.2664F, -2.6005F, 5.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.4792F, -0.0887F, -0.1209F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		root().getAllParts().forEach(ModelPart::resetPose);
		animate(entity.idleAnimationState, GoblinShamanEntityAnimation.GOBLINSHAMAN_IDLE, ageInTicks);
		animate(entity.attackAnimationState, GoblinShamanEntityAnimation.GOBLINSHAMAN_ATTACK, ageInTicks);
		this.parts.Head().yRot = netHeadYaw * Mth.DEG_TO_RAD;
		this.parts.Head().xRot = headPitch * Mth.DEG_TO_RAD;

		if(!entity.isInWaterOrBubble()) {
			animateWalk(GoblinShamanEntityAnimation.GOBLINSHAMAN_WALK, limbSwing, limbSwingAmount, 1.0F, 2.5F);
		}

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		this.parts.Body().render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	public ModelPart root() {
		return this.parts.Body();
	}

	private record ModelParts(ModelPart Body, ModelPart Head){}
}