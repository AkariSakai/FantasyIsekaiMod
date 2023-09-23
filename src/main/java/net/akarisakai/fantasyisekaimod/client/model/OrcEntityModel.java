package net.akarisakai.fantasyisekaimod.client.model;// Made with Blockbench 4.8.3
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.akarisakai.fantasyisekaimod.FantasyIsekaiMod;
import net.akarisakai.fantasyisekaimod.client.animation.OrcEntityAnimation;
import net.akarisakai.fantasyisekaimod.client.custom.OrcEntity;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

public class OrcEntityModel<T extends OrcEntity> extends HierarchicalModel<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(FantasyIsekaiMod.MOD_ID, "orc_entity"), "main");
	private final OrcEntityModel.ModelParts parts;

	public OrcEntityModel(ModelPart root) {
		ModelPart Orc = root.getChild("Orc");
		ModelPart Body = Orc.getChild("Body");
		ModelPart head = Body.getChild("head");

		this.parts = new OrcEntityModel.ModelParts(Orc, Body, head);
	}
	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition Orc = partdefinition.addOrReplaceChild("Orc", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition RightLeg = Orc.addOrReplaceChild("RightLeg", CubeListBuilder.create().texOffs(47, 124).addBox(-4.1F, 0.0F, -5.0F, 9.0F, 12.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offset(-6.9F, -12.0F, 3.0F));

		PartDefinition Body = Orc.addOrReplaceChild("Body", CubeListBuilder.create().texOffs(0, 80).addBox(-14.0F, -11.0F, -5.0F, 28.0F, 12.0F, 16.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(-15.0F, 1.0F, -6.0F, 30.0F, 23.0F, 18.0F, new CubeDeformation(0.0F))
				.texOffs(0, 42).addBox(-15.0F, 10.0F, -6.0F, 30.0F, 19.0F, 18.0F, new CubeDeformation(0.25F)), PartPose.offset(0.0F, -34.0F, 0.0F));

		PartDefinition Fur = Body.addOrReplaceChild("Fur", CubeListBuilder.create().texOffs(11, 155).addBox(-13.0F, -4.5F, -4.5F, 26.0F, 14.0F, 15.0F, new CubeDeformation(-0.5F)), PartPose.offset(0.0F, -7.5F, 0.5F));

		PartDefinition Body_r1 = Fur.addOrReplaceChild("Body_r1", CubeListBuilder.create().texOffs(12, 154).addBox(-15.0F, -5.5F, -1.5F, 28.0F, 14.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.7854F, 0.0F, 0.0F));

		PartDefinition Body_r2 = Fur.addOrReplaceChild("Body_r2", CubeListBuilder.create().texOffs(9, 156).addBox(-16.0F, -5.5F, -6.5F, 32.0F, 14.0F, 15.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.2618F, 0.0F, 0.0F));

		PartDefinition head = Body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(116, 47).addBox(-5.0F, -5.0F, -4.0F, 10.0F, 9.0F, 9.0F, new CubeDeformation(0.98F))
				.texOffs(0, 42).addBox(-2.0F, 0.0F, -6.0F, 4.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -9.0F, -9.0F));

		PartDefinition head_r1 = head.addOrReplaceChild("head_r1", CubeListBuilder.create().texOffs(0, 11).addBox(-0.5F, -1.0F, 0.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(11, 0).addBox(4.5F, -1.0F, 0.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.5F, 2.0F, -6.5F, 0.3491F, 0.0F, 0.0F));

		PartDefinition leftear = head.addOrReplaceChild("leftear", CubeListBuilder.create().texOffs(7, 7).addBox(-0.134F, 0.5F, -2.0F, 1.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.0F, -3.0F, 0.0F, 0.0F, 0.0F, -0.5236F));

		PartDefinition rightear = head.addOrReplaceChild("rightear", CubeListBuilder.create().texOffs(0, 0).addBox(-0.866F, 0.5F, -2.0F, 1.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.0F, -3.0F, 0.0F, 0.0F, 0.0F, 0.5236F));

		PartDefinition RightArm = Body.addOrReplaceChild("RightArm", CubeListBuilder.create().texOffs(0, 109).addBox(-5.0F, -4.0F, -3.0F, 11.0F, 20.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(-20.0F, -5.0F, 0.0F));

		PartDefinition LowerRight = RightArm.addOrReplaceChild("LowerRight", CubeListBuilder.create().texOffs(89, 66).addBox(-6.0F, -3.0F, -7.0F, 13.0F, 19.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 19.0F, 3.0F));

		PartDefinition LeftArm = Body.addOrReplaceChild("LeftArm", CubeListBuilder.create().texOffs(77, 100).addBox(-6.0F, -4.0F, -3.0F, 11.0F, 20.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(20.0F, -5.0F, 0.0F));

		PartDefinition LowerLeft = LeftArm.addOrReplaceChild("LowerLeft", CubeListBuilder.create().texOffs(97, 0).addBox(-8.5F, -1.3432F, -7.8036F, 13.0F, 19.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offset(1.5F, 17.3432F, 3.8036F));

		PartDefinition LeftLeg = Orc.addOrReplaceChild("LeftLeg", CubeListBuilder.create().texOffs(88, 34).addBox(-3.9F, 0.0F, -5.0F, 9.0F, 12.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offset(5.9F, -12.0F, 3.0F));

		return LayerDefinition.create(meshdefinition, 256, 256);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		root().getAllParts().forEach(ModelPart::resetPose);
		animate(entity.idleAnimationState, OrcEntityAnimation.ORC_IDLE, ageInTicks);
		animate(entity.attackAnimationState, OrcEntityAnimation.ORC_ATTACK, ageInTicks);
		animate(entity.walkAnimationState, OrcEntityAnimation.ORC_WALK, ageInTicks);


		// Define the maximum allowed head rotation angles in degrees
		float maxHeadYaw = 45.0F; // Adjust this value as needed
		float minHeadYaw = -45.0F; // Adjust this value as needed
		float maxHeadPitch = 30.0F; // Adjust this value as needed
		float minHeadPitch = -30.0F; // Adjust this value as needed

		// Clamp the head rotation angles within the defined range
		if (netHeadYaw > maxHeadYaw) {
			netHeadYaw = maxHeadYaw;
		} else if (netHeadYaw < minHeadYaw) {
			netHeadYaw = minHeadYaw;
		}

		if (headPitch > maxHeadPitch) {
			headPitch = maxHeadPitch;
		} else if (headPitch < minHeadPitch) {
			headPitch = minHeadPitch;
		}

		this.parts.head.yRot = netHeadYaw * 0.017453292F;
		this.parts.head.xRot = headPitch * 0.017453292F;

		if (!entity.isInWaterOrBubble()) {
			animateWalk(OrcEntityAnimation.ORC_WALK, limbSwing, limbSwingAmount, 2.0F, 4.5F);
		}
	}


	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		this.parts.Orc().render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	public @NotNull ModelPart root() {
		return this.parts.Orc();
	}



	private record ModelParts(ModelPart Orc, ModelPart Body, ModelPart head){

	}
}