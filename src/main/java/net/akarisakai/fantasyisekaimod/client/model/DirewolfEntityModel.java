package net.akarisakai.fantasyisekaimod.client.model;// Made with Blockbench 4.8.3
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.akarisakai.fantasyisekaimod.FantasyIsekaiMod;
import net.akarisakai.fantasyisekaimod.client.animation.DirewolfEntityAnimation;
import net.akarisakai.fantasyisekaimod.client.animation.GoblinEntityAnimation;
import net.akarisakai.fantasyisekaimod.client.custom.DirewolfEntity;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.attributes.Attributes;
import org.jetbrains.annotations.NotNull;

public class DirewolfEntityModel<T extends DirewolfEntity> extends HierarchicalModel<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(FantasyIsekaiMod.MOD_ID, "direwolf_entity"), "main");
	private final DirewolfEntityModel.ModelParts parts;

	public DirewolfEntityModel(ModelPart root) {
		ModelPart direwolf = root.getChild("direwolf");
		ModelPart Body = direwolf.getChild("Body");
		ModelPart Head = Body.getChild("Head");

		this.parts = new DirewolfEntityModel.ModelParts(direwolf, Body, Head);
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition direwolf = partdefinition.addOrReplaceChild("direwolf", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 18.0F, -8.0F, 0.0436F, 0.0F, 0.0F));

		PartDefinition Body = direwolf.addOrReplaceChild("Body", CubeListBuilder.create(), PartPose.offset(0.0F, -16.0F, 0.0F));

		PartDefinition Chest = Body.addOrReplaceChild("Chest", CubeListBuilder.create().texOffs(0, 28).addBox(-5.0F, -3.0F, -25.0F, 10.0F, 12.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 2.0F, 24.0F));

		PartDefinition cube_r1 = Chest.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(35, 0).mirror().addBox(-5.0F, 2.4627F, -0.8434F, 8.0F, 9.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, -3.0F, -24.0F, 0.4498F, -0.2368F, -0.1128F));

		PartDefinition cube_r2 = Chest.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(35, 0).addBox(-3.0F, 2.4627F, -0.8434F, 8.0F, 9.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -3.0F, -24.0F, 0.4498F, 0.2368F, 0.1128F));

		PartDefinition cube_r3 = Chest.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(45, 20).addBox(-4.0F, -2.5373F, -0.8434F, 8.0F, 6.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -3.0F, -24.0F, 0.4363F, 0.0F, 0.0F));

		PartDefinition LowerBody = Body.addOrReplaceChild("LowerBody", CubeListBuilder.create(), PartPose.offset(0.0F, 2.0F, 24.0F));

		PartDefinition cube_r4 = LowerBody.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, 0.001F, -1.0436F, 8.0F, 9.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.0F, -17.0F, -0.0436F, 0.0F, 0.0F));

		PartDefinition cube_r5 = LowerBody.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(37, 51).addBox(-3.0F, -1.2238F, -1.0603F, 6.0F, 6.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -3.0F, -19.0F, 0.2182F, 0.0F, 0.0F));

		PartDefinition Tail = LowerBody.addOrReplaceChild("Tail", CubeListBuilder.create(), PartPose.offset(0.0F, -1.0F, -1.0F));

		PartDefinition cube_r6 = Tail.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, 0.0086F, -0.8695F, 3.0F, 13.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, 1.0F, 0.0F, 0.2618F, 0.0F, 0.0F));

		PartDefinition Head = Body.addOrReplaceChild("Head", CubeListBuilder.create().texOffs(45, 35).addBox(-4.0F, -5.0F, -6.0F, 8.0F, 8.0F, 7.0F, new CubeDeformation(0.0F))
				.texOffs(66, 51).addBox(-2.0F, -1.0F, -11.0F, 4.0F, 3.0F, 5.0F, new CubeDeformation(0.0F))
				.texOffs(33, 30).addBox(-1.5F, 0.2F, -10.5F, 3.0F, 3.0F, 5.0F, new CubeDeformation(0.0F))
				.texOffs(73, 30).addBox(-1.0F, -1.5F, -10.1F, 2.0F, 3.0F, 5.0F, new CubeDeformation(0.0F))
				.texOffs(0, 35).addBox(-1.5F, -1.5F, -11.1F, 3.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 2.0F, -2.0F));

		PartDefinition cube_r7 = Head.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(0, 28).mirror().addBox(0.25F, -5.0F, 0.499F, 4.0F, 6.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(3.0F, 2.9564F, -3.999F, 0.0F, -0.48F, 0.0F));

		PartDefinition cube_r8 = Head.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(0, 28).addBox(-4.25F, -5.0F, 0.499F, 4.0F, 6.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.0F, 2.9564F, -3.999F, 0.0F, 0.48F, 0.0F));

		PartDefinition EarLeft = Head.addOrReplaceChild("EarLeft", CubeListBuilder.create(), PartPose.offset(3.0F, -5.0F, -1.0F));

		PartDefinition cube_r9 = EarLeft.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(49, 66).addBox(-0.4271F, -3.3071F, 0.1F, 3.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.3867F, -0.4529F, 0.8662F));

		PartDefinition EarRight = Head.addOrReplaceChild("EarRight", CubeListBuilder.create(), PartPose.offset(-3.0F, -5.0F, -1.0F));

		PartDefinition cube_r10 = EarRight.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(34, 53).addBox(-2.5729F, -3.3071F, 0.1F, 3.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.3867F, 0.4529F, -0.8662F));

		PartDefinition LeftLeg = direwolf.addOrReplaceChild("LeftLeg", CubeListBuilder.create(), PartPose.offset(5.0F, -11.0F, 3.0F));

		PartDefinition cube_r11 = LeftLeg.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(0, 53).addBox(-2.5F, -11.8653F, -1.1716F, 4.0F, 19.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 10.0F, -1.0F, -0.0436F, 0.0F, 0.0F));

		PartDefinition RightLeg = direwolf.addOrReplaceChild("RightLeg", CubeListBuilder.create(), PartPose.offset(-5.0F, -11.0F, 3.0F));

		PartDefinition cube_r12 = RightLeg.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(17, 53).addBox(-2.5F, 0.1669F, -1.6472F, 4.0F, 19.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -2.0F, 0.0F, -0.0436F, 0.0F, 0.0F));

		PartDefinition RightBackLeg = direwolf.addOrReplaceChild("RightBackLeg", CubeListBuilder.create(), PartPose.offset(-4.0F, -12.0F, 19.0F));

		PartDefinition cube_r13 = RightBackLeg.addOrReplaceChild("cube_r13", CubeListBuilder.create().texOffs(34, 66).addBox(-0.5F, -11.3419F, 1.8398F, 4.0F, 10.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, 11.0F, -5.0F, -0.0436F, 0.0F, 0.0F));

		PartDefinition cube_r14 = RightBackLeg.addOrReplaceChild("cube_r14", CubeListBuilder.create().texOffs(68, 0).addBox(-0.5F, -1.909F, 16.8274F, 4.0F, 9.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, 11.0F, -18.0F, -0.0436F, 0.0F, 0.0F));

		PartDefinition LeftBackLeg = direwolf.addOrReplaceChild("LeftBackLeg", CubeListBuilder.create(), PartPose.offset(4.0F, -12.0F, 19.0F));

		PartDefinition cube_r15 = LeftBackLeg.addOrReplaceChild("cube_r15", CubeListBuilder.create().texOffs(60, 60).addBox(-3.5F, -11.909F, 14.8274F, 4.0F, 10.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(70, 14).addBox(-3.5F, -1.909F, 16.8274F, 4.0F, 9.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, 11.0F, -18.0F, -0.0436F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		root().getAllParts().forEach(ModelPart::resetPose);
		animate(entity.idleAnimationState, DirewolfEntityAnimation.DIREWOLF_IDLE, ageInTicks);
		animate(entity.runAnimationstate, DirewolfEntityAnimation.DIREWOLF_RUN, ageInTicks);
		animate(entity.sleepAnimationstate, DirewolfEntityAnimation.DIREWOLF_SLEEP, ageInTicks);
		animate(entity.standupAnimationstate, DirewolfEntityAnimation.DIREWOLF_STANDUP, ageInTicks);
		animate(entity.liedownAnimationstate, DirewolfEntityAnimation.DIREWOLF_LIEDOWN, ageInTicks);
		animate(entity.attackAnimationstate, DirewolfEntityAnimation.DIREWOLF_ATTACK, ageInTicks);
		this.parts.Head().yRot = netHeadYaw * Mth.DEG_TO_RAD;
		this.parts.Head().xRot = headPitch * Mth.DEG_TO_RAD;

		if (!entity.isInWaterOrBubble() && !entity.isAggressive()) {
			animateWalk(DirewolfEntityAnimation.DIREWOLF_WALK, limbSwing, limbSwingAmount, 2.0F, 2.5F);
		}
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		this.parts.direwolf().render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	public @NotNull ModelPart root() {
		return this.parts.direwolf();
	}



	private record ModelParts(ModelPart direwolf, ModelPart Body, ModelPart Head){

	}
}