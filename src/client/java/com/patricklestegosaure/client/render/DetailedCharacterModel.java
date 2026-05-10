package com.patricklestegosaure.client.render;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;

public class DetailedCharacterModel extends EntityModel<LivingEntityRenderState> {
	private final ModelPart head;
	private final ModelPart rightBackLeg;
	private final ModelPart leftBackLeg;
	private final ModelPart rightFrontLeg;
	private final ModelPart leftFrontLeg;
	private final ModelPart tail;

	public DetailedCharacterModel(ModelPart root) {
		super(root);
		this.head = childOrNull(root, "head");
		this.rightBackLeg = childOrNull(root, "right_back_leg");
		this.leftBackLeg = childOrNull(root, "left_back_leg");
		this.rightFrontLeg = childOrNull(root, "right_front_leg");
		this.leftFrontLeg = childOrNull(root, "left_front_leg");
		this.tail = childOrNull(root, "tail");
	}

	public static LayerDefinition createSaucisseLayer() {
		MeshDefinition mesh = new MeshDefinition();
		PartDefinition root = mesh.getRoot();

		root.addOrReplaceChild("body", CubeListBuilder.create()
						.texOffs(0, 0).addBox(-5.0F, -3.0F, -4.0F, 10.0F, 5.0F, 8.0F)
						.texOffs(0, 16).addBox(-4.0F, -4.0F, -3.0F, 8.0F, 2.0F, 6.0F)
						.texOffs(40, 0).addBox(-4.0F, -5.0F, -2.0F, 8.0F, 1.0F, 4.0F)
						.texOffs(80, 0).addBox(-4.0F, -5.2F, -2.5F, 2.0F, 1.0F, 2.0F)
						.texOffs(80, 0).addBox(1.0F, -5.2F, -2.5F, 2.0F, 1.0F, 2.0F)
						.texOffs(80, 0).addBox(-1.0F, -5.3F, 0.8F, 2.0F, 1.0F, 2.0F),
				PartPose.offset(0.0F, 20.0F, 0.0F));
		PartDefinition head = root.addOrReplaceChild("head", CubeListBuilder.create()
						.texOffs(64, 0).addBox(-3.0F, -2.5F, -3.0F, 6.0F, 4.0F, 4.0F)
						.texOffs(96, 0).addBox(-1.0F, -1.0F, -3.8F, 1.0F, 1.0F, 1.0F)
						.texOffs(96, 0).addBox(1.0F, -1.0F, -3.8F, 1.0F, 1.0F, 1.0F),
				PartPose.offset(0.0F, 18.5F, -4.0F));
		head.addOrReplaceChild("left_antenna", CubeListBuilder.create()
						.texOffs(96, 8).addBox(0.0F, -5.0F, -1.0F, 1.0F, 4.0F, 1.0F),
				PartPose.offsetAndRotation(2.0F, -1.0F, -2.0F, -0.6F, 0.0F, 0.55F));
		head.addOrReplaceChild("right_antenna", CubeListBuilder.create()
						.texOffs(96, 8).addBox(0.0F, -5.0F, -1.0F, 1.0F, 4.0F, 1.0F),
				PartPose.offsetAndRotation(-2.0F, -1.0F, -2.0F, -0.6F, 0.0F, -0.55F));
		head.addOrReplaceChild("left_eye_highlight", CubeListBuilder.create()
						.texOffs(112, 0).addBox(1.2F, -1.4F, -4.0F, 1.0F, 1.0F, 1.0F),
				PartPose.ZERO);
		head.addOrReplaceChild("right_eye_highlight", CubeListBuilder.create()
						.texOffs(112, 0).addBox(-2.2F, -1.4F, -4.0F, 1.0F, 1.0F, 1.0F),
				PartPose.ZERO);

		addTinyLeg(root, "right_front_leg", -5.0F, 22.0F, -3.0F, -0.55F);
		addTinyLeg(root, "left_front_leg", 4.0F, 22.0F, -3.0F, 0.55F);
		addTinyLeg(root, "right_back_leg", -5.0F, 22.0F, 2.0F, -0.55F);
		addTinyLeg(root, "left_back_leg", 4.0F, 22.0F, 2.0F, 0.55F);
		root.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.ZERO);

		return LayerDefinition.create(mesh, 128, 128);
	}

	public static LayerDefinition createPouetLayer() {
		MeshDefinition mesh = new MeshDefinition();
		PartDefinition root = mesh.getRoot();

		root.addOrReplaceChild("body", CubeListBuilder.create()
						.texOffs(0, 0).addBox(-7.0F, -14.0F, -8.0F, 14.0F, 18.0F, 18.0F)
						.texOffs(0, 42).addBox(-6.0F, -10.0F, -9.0F, 12.0F, 13.0F, 6.0F)
						.texOffs(70, 0).addBox(-7.5F, -12.0F, -6.0F, 3.0F, 14.0F, 14.0F)
						.texOffs(70, 0).addBox(4.5F, -12.0F, -6.0F, 3.0F, 14.0F, 14.0F),
				PartPose.offset(0.0F, 15.0F, 0.0F));
		PartDefinition head = root.addOrReplaceChild("head", CubeListBuilder.create()
						.texOffs(0, 70).addBox(-4.0F, -7.0F, -5.0F, 8.0F, 8.0F, 7.0F)
						.texOffs(36, 70).addBox(-2.0F, -2.0F, -9.0F, 4.0F, 3.0F, 5.0F)
						.texOffs(58, 70).addBox(-1.5F, 1.0F, -6.0F, 3.0F, 4.0F, 2.0F)
						.texOffs(76, 70).addBox(-4.5F, -11.0F, -3.0F, 9.0F, 5.0F, 2.0F)
						.texOffs(76, 78).addBox(-3.5F, -15.0F, -2.5F, 7.0F, 5.0F, 2.0F)
						.texOffs(76, 86).addBox(-2.0F, -18.0F, -2.0F, 4.0F, 4.0F, 2.0F),
				PartPose.offset(0.0F, 4.0F, -8.0F));
		head.addOrReplaceChild("left_eye", CubeListBuilder.create()
						.texOffs(104, 70).addBox(2.6F, -4.4F, -5.6F, 2.0F, 2.0F, 1.0F),
				PartPose.ZERO);
		head.addOrReplaceChild("right_eye", CubeListBuilder.create()
						.texOffs(104, 70).addBox(-4.6F, -4.4F, -5.6F, 2.0F, 2.0F, 1.0F),
				PartPose.ZERO);
		head.addOrReplaceChild("beak_tip", CubeListBuilder.create()
						.texOffs(120, 70).addBox(-1.5F, -1.5F, -10.8F, 3.0F, 2.0F, 2.0F),
				PartPose.ZERO);
		head.addOrReplaceChild("neck_feather_1", CubeListBuilder.create()
						.texOffs(132, 70).addBox(-4.5F, 0.5F, -5.5F, 2.0F, 8.0F, 1.0F),
				PartPose.ZERO);
		head.addOrReplaceChild("neck_feather_2", CubeListBuilder.create()
						.texOffs(132, 70).addBox(2.5F, 0.5F, -5.5F, 2.0F, 8.0F, 1.0F),
				PartPose.ZERO);

		PartDefinition tail = root.addOrReplaceChild("tail", CubeListBuilder.create()
						.texOffs(110, 0).addBox(-3.0F, -13.0F, 0.0F, 6.0F, 22.0F, 4.0F)
						.texOffs(130, 0).addBox(-8.0F, -10.0F, 1.0F, 6.0F, 18.0F, 4.0F)
						.texOffs(150, 0).addBox(2.0F, -10.0F, 1.0F, 6.0F, 18.0F, 4.0F),
				PartPose.offsetAndRotation(0.0F, 8.0F, 9.0F, -0.65F, 0.0F, 0.0F));

		addBirdLeg(root, "right_back_leg", -4.0F, 18.0F, 2.0F);
		addBirdLeg(root, "left_back_leg", 2.0F, 18.0F, 2.0F);
		root.addOrReplaceChild("right_front_leg", CubeListBuilder.create(), PartPose.ZERO);
		root.addOrReplaceChild("left_front_leg", CubeListBuilder.create(), PartPose.ZERO);

		return LayerDefinition.create(mesh, 256, 256);
	}

	public static LayerDefinition createPascalLayer() {
		MeshDefinition mesh = new MeshDefinition();
		PartDefinition root = mesh.getRoot();

		root.addOrReplaceChild("body", CubeListBuilder.create()
						.texOffs(0, 0).addBox(-18.0F, -24.0F, -54.0F, 36.0F, 28.0F, 92.0F)
						.texOffs(0, 126).addBox(-16.0F, -8.0F, -48.0F, 32.0F, 12.0F, 68.0F)
						.texOffs(180, 0).addBox(-16.0F, -18.0F, 34.0F, 32.0F, 18.0F, 28.0F),
				PartPose.offset(0.0F, -2.0F, 0.0F));
		PartDefinition head = root.addOrReplaceChild("head", CubeListBuilder.create()
						.texOffs(0, 150).addBox(-9.0F, -9.0F, -18.0F, 18.0F, 14.0F, 20.0F)
						.texOffs(78, 150).addBox(-6.0F, -6.0F, -44.0F, 12.0F, 9.0F, 28.0F)
						.texOffs(136, 150).addBox(-5.0F, 3.0F, -41.0F, 10.0F, 5.0F, 25.0F),
				PartPose.offsetAndRotation(0.0F, -20.0F, -58.0F, 0.05F, 3.1416F, 0.0F));
		head.addOrReplaceChild("crown", CubeListBuilder.create()
						.texOffs(210, 150).addBox(-7.0F, -16.0F, -17.0F, 14.0F, 4.0F, 12.0F)
						.texOffs(210, 170).addBox(-8.0F, -23.0F, -15.0F, 3.0F, 7.0F, 3.0F)
						.texOffs(224, 170).addBox(-2.0F, -25.0F, -15.0F, 4.0F, 9.0F, 4.0F)
						.texOffs(240, 170).addBox(5.0F, -23.0F, -15.0F, 3.0F, 7.0F, 3.0F),
				PartPose.ZERO);
		head.addOrReplaceChild("teeth", CubeListBuilder.create()
						.texOffs(210, 190).addBox(-5.0F, 1.0F, -43.0F, 10.0F, 3.0F, 20.0F),
				PartPose.ZERO);
		head.addOrReplaceChild("left_eye", CubeListBuilder.create()
						.texOffs(250, 190).addBox(5.0F, -5.0F, -22.0F, 2.0F, 2.0F, 1.0F),
				PartPose.ZERO);
		head.addOrReplaceChild("right_eye", CubeListBuilder.create()
						.texOffs(250, 190).addBox(-7.0F, -5.0F, -22.0F, 2.0F, 2.0F, 1.0F),
				PartPose.ZERO);
		head.addOrReplaceChild("left_nostril", CubeListBuilder.create()
						.texOffs(258, 190).addBox(3.0F, -2.0F, -43.5F, 2.0F, 2.0F, 1.0F),
				PartPose.ZERO);
		head.addOrReplaceChild("right_nostril", CubeListBuilder.create()
						.texOffs(258, 190).addBox(-5.0F, -2.0F, -43.5F, 2.0F, 2.0F, 1.0F),
				PartPose.ZERO);
		head.addOrReplaceChild("crown_red_gem", CubeListBuilder.create()
						.texOffs(270, 190).addBox(-1.0F, -18.0F, -18.0F, 2.0F, 2.0F, 1.0F),
				PartPose.ZERO);
		head.addOrReplaceChild("crown_blue_gem", CubeListBuilder.create()
						.texOffs(276, 190).addBox(4.0F, -17.0F, -18.0F, 2.0F, 2.0F, 1.0F),
				PartPose.ZERO);

		PartDefinition tail = root.addOrReplaceChild("tail", CubeListBuilder.create()
						.texOffs(0, 210).addBox(-11.0F, -9.0F, 0.0F, 22.0F, 18.0F, 70.0F)
						.texOffs(114, 210).addBox(-6.0F, -6.0F, 60.0F, 12.0F, 12.0F, 58.0F)
						.texOffs(198, 210).addBox(-3.0F, -4.0F, 110.0F, 6.0F, 8.0F, 40.0F),
				PartPose.offsetAndRotation(0.0F, -10.0F, 42.0F, -0.12F, 0.0F, 0.0F));

		addSail(root, "sail_1", 0.0F, -27.0F, -35.0F, 30.0F);
		addSail(root, "sail_2", 0.0F, -32.0F, -18.0F, 44.0F);
		addSail(root, "sail_3", 0.0F, -34.0F, 0.0F, 50.0F);
		addSail(root, "sail_4", 0.0F, -32.0F, 18.0F, 44.0F);
		addSail(root, "sail_5", 0.0F, -27.0F, 34.0F, 30.0F);
		addDinoLeg(root, "right_back_leg", -15.0F, -3.0F, 10.0F);
		addDinoLeg(root, "left_back_leg", 5.0F, -3.0F, 10.0F);
		addSmallCarnivoreArm(root, "right_front_leg", -16.0F, -15.0F, -38.0F);
		addSmallCarnivoreArm(root, "left_front_leg", 10.0F, -15.0F, -38.0F);

		return LayerDefinition.create(mesh, 512, 512);
	}

	public static LayerDefinition createPamoukLayer() {
		MeshDefinition mesh = new MeshDefinition();
		PartDefinition root = mesh.getRoot();

		root.addOrReplaceChild("body", CubeListBuilder.create()
						.texOffs(0, 0).addBox(-22.0F, -22.0F, -48.0F, 44.0F, 24.0F, 86.0F)
						.texOffs(0, 116).addBox(-19.0F, -30.0F, -36.0F, 38.0F, 12.0F, 60.0F),
				PartPose.offset(0.0F, 4.0F, 0.0F));
		PartDefinition head = root.addOrReplaceChild("head", CubeListBuilder.create()
						.texOffs(0, 200).addBox(-13.0F, -12.0F, -18.0F, 26.0F, 18.0F, 22.0F)
						.texOffs(96, 200).addBox(-9.0F, -4.0F, -28.0F, 18.0F, 8.0F, 12.0F)
						.texOffs(150, 200).addBox(-5.0F, 3.0F, -29.0F, 10.0F, 4.0F, 8.0F),
				PartPose.offsetAndRotation(0.0F, -10.0F, -52.0F, 0.0F, 3.1416F, 0.0F));
		head.addOrReplaceChild("left_horn", CubeListBuilder.create()
						.texOffs(190, 200).addBox(10.0F, -7.0F, -12.0F, 10.0F, 5.0F, 5.0F),
				PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.45F));
		head.addOrReplaceChild("right_horn", CubeListBuilder.create()
						.texOffs(190, 200).addBox(-20.0F, -7.0F, -12.0F, 10.0F, 5.0F, 5.0F),
				PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.45F));
		head.addOrReplaceChild("tongue", CubeListBuilder.create()
						.texOffs(230, 200).addBox(-5.0F, 6.0F, -28.0F, 10.0F, 2.0F, 6.0F),
				PartPose.ZERO);
		head.addOrReplaceChild("left_eye", CubeListBuilder.create()
						.texOffs(260, 200).addBox(7.0F, -6.0F, -19.0F, 4.0F, 4.0F, 1.0F),
				PartPose.ZERO);
		head.addOrReplaceChild("right_eye", CubeListBuilder.create()
						.texOffs(260, 200).addBox(-11.0F, -6.0F, -19.0F, 4.0F, 4.0F, 1.0F),
				PartPose.ZERO);
		head.addOrReplaceChild("left_nostril", CubeListBuilder.create()
						.texOffs(274, 200).addBox(3.0F, -1.0F, -28.5F, 2.0F, 2.0F, 1.0F),
				PartPose.ZERO);
		head.addOrReplaceChild("right_nostril", CubeListBuilder.create()
						.texOffs(274, 200).addBox(-5.0F, -1.0F, -28.5F, 2.0F, 2.0F, 1.0F),
				PartPose.ZERO);

		PartDefinition tail = root.addOrReplaceChild("tail", CubeListBuilder.create()
						.texOffs(0, 260).addBox(-8.0F, -7.0F, 0.0F, 16.0F, 14.0F, 64.0F)
						.texOffs(96, 260).addBox(-14.0F, -10.0F, 58.0F, 28.0F, 20.0F, 22.0F)
						.texOffs(180, 260).addBox(-12.0F, -7.0F, 62.0F, 24.0F, 4.0F, 16.0F),
				PartPose.offsetAndRotation(0.0F, -7.0F, 34.0F, -0.10F, 0.0F, 0.0F));

		for (int col = 0; col < 8; col++) {
			float z = -40.0F + col * 11.0F;
			addArmorSpike(root, "top_armor_" + col, 0.0F, -30.5F, z, 11.0F);
			addArmorSpike(root, "left_armor_" + col, -12.0F, -25.0F, z + 3.0F, 7.0F);
			addArmorSpike(root, "right_armor_" + col, 12.0F, -25.0F, z + 3.0F, 7.0F);
		}
		addArmorSpike(root, "tail_armor_1", 0.0F, -15.0F, 48.0F, 6.0F);
		addArmorSpike(root, "tail_armor_2", 0.0F, -13.0F, 62.0F, 5.0F);
		addAnkyLeg(root, "right_back_leg", -16.0F, 4.0F, 18.0F);
		addAnkyLeg(root, "left_back_leg", 8.0F, 4.0F, 18.0F);
		addAnkyLeg(root, "right_front_leg", -16.0F, 4.0F, -32.0F);
		addAnkyLeg(root, "left_front_leg", 8.0F, 4.0F, -32.0F);

		return LayerDefinition.create(mesh, 512, 512);
	}

	public static LayerDefinition createKalashLayer() {
		MeshDefinition mesh = new MeshDefinition();
		PartDefinition root = mesh.getRoot();

		root.addOrReplaceChild("body", CubeListBuilder.create()
						.texOffs(0, 0).addBox(-8.0F, -4.0F, -10.0F, 16.0F, 8.0F, 20.0F)
						.texOffs(0, 34).addBox(-6.0F, -3.0F, -13.0F, 12.0F, 6.0F, 5.0F)
						.texOffs(60, 0).addBox(-7.0F, -2.0F, -16.0F, 14.0F, 4.0F, 4.0F)
						.texOffs(92, 0).addBox(-1.0F, -9.0F, -1.0F, 2.0F, 7.0F, 9.0F),
				PartPose.offsetAndRotation(0.0F, 20.0F, 0.0F, 0.0F, 0.0F, -0.35F));
		root.addOrReplaceChild("head", CubeListBuilder.create()
						.texOffs(120, 0).addBox(-5.0F, -4.0F, -5.0F, 10.0F, 8.0F, 6.0F)
						.texOffs(150, 0).addBox(-3.5F, -2.5F, -9.0F, 7.0F, 5.0F, 4.0F)
						.texOffs(170, 0).addBox(-5.0F, -7.0F, -5.0F, 4.0F, 4.0F, 1.0F)
						.texOffs(170, 0).addBox(1.0F, -7.0F, -5.0F, 4.0F, 4.0F, 1.0F)
						.texOffs(190, 0).addBox(-3.0F, -1.5F, -11.0F, 6.0F, 3.0F, 2.0F),
				PartPose.offsetAndRotation(0.0F, 19.0F, -11.0F, 0.0F, 0.0F, -0.35F));
		root.addOrReplaceChild("left_eye", CubeListBuilder.create()
						.texOffs(210, 0).addBox(1.0F, -2.0F, -1.0F, 5.0F, 5.0F, 2.0F)
						.texOffs(224, 0).addBox(3.0F, 0.0F, -1.5F, 2.0F, 2.0F, 1.0F),
				PartPose.offsetAndRotation(4.0F, 12.0F, -15.0F, 0.0F, 0.0F, -0.35F));
		root.addOrReplaceChild("right_eye", CubeListBuilder.create()
						.texOffs(210, 0).addBox(-6.0F, -2.0F, -1.0F, 5.0F, 5.0F, 2.0F)
						.texOffs(224, 0).addBox(-5.0F, 0.0F, -1.5F, 2.0F, 2.0F, 1.0F),
				PartPose.offsetAndRotation(-4.0F, 12.0F, -15.0F, 0.0F, 0.0F, -0.35F));
		root.addOrReplaceChild("tail", CubeListBuilder.create()
						.texOffs(0, 60).addBox(-1.5F, -8.0F, 0.0F, 3.0F, 16.0F, 11.0F),
				PartPose.offsetAndRotation(0.0F, 20.0F, 9.0F, 0.0F, 0.0F, -0.35F));
		addWhisker(root, "left_whisker", 5.0F, 19.0F, -15.0F, 1.45F);
		addWhisker(root, "right_whisker", -5.0F, 19.0F, -15.0F, -1.45F);
		root.addOrReplaceChild("right_back_leg", CubeListBuilder.create(), PartPose.ZERO);
		root.addOrReplaceChild("left_back_leg", CubeListBuilder.create(), PartPose.ZERO);
		root.addOrReplaceChild("right_front_leg", CubeListBuilder.create()
						.texOffs(44, 60).addBox(-1.0F, -4.0F, -2.0F, 2.0F, 8.0F, 5.0F),
				PartPose.offsetAndRotation(-6.0F, 16.0F, -2.0F, 0.0F, 0.0F, -0.65F));
		root.addOrReplaceChild("left_front_leg", CubeListBuilder.create()
						.texOffs(44, 60).addBox(-1.0F, -4.0F, -2.0F, 2.0F, 8.0F, 5.0F),
				PartPose.offsetAndRotation(6.0F, 16.0F, -2.0F, 0.0F, 0.0F, 0.65F));

		return LayerDefinition.create(mesh, 256, 256);
	}

	public static LayerDefinition createBrigitteLayer() {
		MeshDefinition mesh = new MeshDefinition();
		PartDefinition root = mesh.getRoot();

		root.addOrReplaceChild("body", CubeListBuilder.create()
						.texOffs(0, 0).addBox(-8.0F, -22.0F, -5.0F, 16.0F, 29.0F, 10.0F)
						.texOffs(60, 0).addBox(-11.0F, -28.0F, 0.0F, 22.0F, 24.0F, 12.0F)
						.texOffs(0, 30).addBox(-10.0F, -8.0F, -9.0F, 20.0F, 20.0F, 13.0F)
						.texOffs(60, 34).addBox(-9.0F, 7.0F, -10.0F, 18.0F, 8.0F, 12.0F),
				PartPose.offsetAndRotation(0.0F, 11.0F, 2.0F, 0.45F, 0.0F, 0.0F));
		PartDefinition head = root.addOrReplaceChild("head", CubeListBuilder.create()
						.texOffs(0, 60).addBox(-5.0F, -8.0F, -5.0F, 10.0F, 10.0F, 8.0F)
						.texOffs(42, 60).addBox(-5.0F, -1.0F, -6.5F, 10.0F, 24.0F, 5.0F)
						.texOffs(42, 92).addBox(-3.0F, 20.0F, -6.0F, 6.0F, 8.0F, 4.0F)
						.texOffs(72, 60).addBox(-8.0F, -6.0F, -1.0F, 3.0F, 22.0F, 3.0F)
						.texOffs(86, 60).addBox(5.0F, -6.0F, -1.0F, 3.0F, 22.0F, 3.0F)
						.texOffs(100, 60).addBox(-1.0F, -12.0F, -1.0F, 2.0F, 5.0F, 2.0F),
				PartPose.offsetAndRotation(0.0F, -7.0F, -9.0F, 0.35F, 0.0F, 0.0F));
		head.addOrReplaceChild("left_eye", CubeListBuilder.create()
						.texOffs(112, 60).addBox(2.0F, -4.0F, -5.5F, 2.0F, 2.0F, 1.0F),
				PartPose.ZERO);
		head.addOrReplaceChild("right_eye", CubeListBuilder.create()
						.texOffs(112, 60).addBox(-4.0F, -4.0F, -5.5F, 2.0F, 2.0F, 1.0F),
				PartPose.ZERO);
		head.addOrReplaceChild("forehead_wrinkle_1", CubeListBuilder.create()
						.texOffs(122, 60).addBox(-3.0F, -6.0F, -5.7F, 6.0F, 1.0F, 1.0F),
				PartPose.ZERO);
		head.addOrReplaceChild("forehead_wrinkle_2", CubeListBuilder.create()
						.texOffs(122, 64).addBox(-2.0F, -4.8F, -5.8F, 4.0F, 1.0F, 1.0F),
				PartPose.ZERO);

		root.addOrReplaceChild("right_front_leg", CubeListBuilder.create()
						.texOffs(130, 0).addBox(-3.0F, -2.0F, -2.0F, 5.0F, 18.0F, 5.0F),
				PartPose.offsetAndRotation(-10.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.95F));
		root.addOrReplaceChild("left_front_leg", CubeListBuilder.create()
						.texOffs(130, 0).addBox(-2.0F, -2.0F, -2.0F, 5.0F, 16.0F, 5.0F),
				PartPose.offsetAndRotation(9.0F, -1.0F, -5.0F, 0.35F, 0.0F, -0.35F));
		root.addOrReplaceChild("cane", CubeListBuilder.create()
						.texOffs(160, 0).addBox(-1.5F, -15.0F, -1.5F, 3.0F, 48.0F, 3.0F)
						.texOffs(174, 0).addBox(-4.0F, -7.0F, -2.0F, 8.0F, 3.0F, 4.0F)
						.texOffs(174, 10).addBox(-4.0F, 3.0F, -2.0F, 8.0F, 3.0F, 4.0F)
						.texOffs(174, 20).addBox(-3.0F, -12.0F, -3.0F, 6.0F, 6.0F, 6.0F),
				PartPose.offsetAndRotation(12.0F, -3.0F, -8.0F, 0.0F, 0.0F, -0.10F));
		root.addOrReplaceChild("right_back_leg", CubeListBuilder.create()
						.texOffs(200, 0).addBox(-4.0F, 0.0F, -7.0F, 7.0F, 4.0F, 10.0F),
				PartPose.offset(-5.0F, 22.0F, 0.0F));
		root.addOrReplaceChild("left_back_leg", CubeListBuilder.create()
						.texOffs(200, 0).addBox(-3.0F, 0.0F, -7.0F, 7.0F, 4.0F, 10.0F),
				PartPose.offset(5.0F, 22.0F, 0.0F));
		root.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.ZERO);

		return LayerDefinition.create(mesh, 256, 256);
	}

	@Override
	public void setupAnim(LivingEntityRenderState state) {
		super.setupAnim(state);
		float walk = state.walkAnimationPos;
		float speed = Math.min(state.walkAnimationSpeed, 1.0F);

		if (this.head != null) {
			this.head.xRot += state.xRot * ((float) Math.PI / 180.0F) * 0.45F;
			this.head.yRot = state.yRot * ((float) Math.PI / 180.0F) * 0.45F;
		}
		animateLeg(this.rightBackLeg, walk, speed, 0.0F);
		animateLeg(this.leftBackLeg, walk, speed, (float) Math.PI);
		animateLeg(this.rightFrontLeg, walk, speed, (float) Math.PI);
		animateLeg(this.leftFrontLeg, walk, speed, 0.0F);
		if (this.tail != null) {
			this.tail.yRot = (float) Math.cos(walk * 0.25F) * 0.10F * speed;
		}
	}

	private static ModelPart childOrNull(ModelPart root, String child) {
		return root.hasChild(child) ? root.getChild(child) : null;
	}

	private static void animateLeg(ModelPart leg, float walk, float speed, float phase) {
		if (leg != null) {
			leg.xRot = (float) Math.cos(walk * 0.6662F + phase) * 0.45F * speed;
		}
	}

	private static void addTinyLeg(PartDefinition root, String name, float x, float y, float z, float zRot) {
		root.addOrReplaceChild(name, CubeListBuilder.create()
						.texOffs(108, 0).addBox(0.0F, 0.0F, -0.5F, 1.0F, 5.0F, 1.0F),
				PartPose.offsetAndRotation(x, y, z, 0.0F, 0.0F, zRot));
	}

	private static void addBirdLeg(PartDefinition root, String name, float x, float y, float z) {
		root.addOrReplaceChild(name, CubeListBuilder.create()
						.texOffs(170, 70).addBox(0.0F, 0.0F, -1.0F, 2.0F, 9.0F, 2.0F)
						.texOffs(170, 84).addBox(-4.0F, 8.0F, -6.0F, 9.0F, 2.0F, 8.0F)
						.texOffs(170, 98).addBox(-4.0F, 9.0F, -9.0F, 2.0F, 1.0F, 4.0F)
						.texOffs(170, 98).addBox(0.0F, 9.0F, -9.0F, 2.0F, 1.0F, 4.0F)
						.texOffs(170, 98).addBox(3.0F, 9.0F, -8.0F, 2.0F, 1.0F, 4.0F),
				PartPose.offset(x, y, z));
	}

	private static void addSail(PartDefinition root, String name, float x, float y, float z, float height) {
		root.addOrReplaceChild(name, CubeListBuilder.create()
						.texOffs(300, 0).addBox(-2.0F, -height, -7.0F, 4.0F, height, 14.0F)
						.texOffs(330, 0).addBox(-1.0F, -height + 3.0F, -8.0F, 2.0F, height - 5.0F, 2.0F),
				PartPose.offset(x, y, z));
	}

	private static void addDinoLeg(PartDefinition root, String name, float x, float y, float z) {
		root.addOrReplaceChild(name, CubeListBuilder.create()
						.texOffs(360, 0).addBox(0.0F, 0.0F, -7.0F, 12.0F, 30.0F, 14.0F)
						.texOffs(360, 42).addBox(-3.0F, 26.0F, -15.0F, 17.0F, 6.0F, 18.0F)
						.texOffs(360, 68).addBox(-3.0F, 30.0F, -20.0F, 4.0F, 3.0F, 7.0F)
						.texOffs(360, 68).addBox(4.0F, 30.0F, -21.0F, 4.0F, 3.0F, 8.0F)
						.texOffs(360, 68).addBox(10.0F, 30.0F, -20.0F, 4.0F, 3.0F, 7.0F),
				PartPose.offset(x, y, z));
	}

	private static void addSmallCarnivoreArm(PartDefinition root, String name, float x, float y, float z) {
		root.addOrReplaceChild(name, CubeListBuilder.create()
						.texOffs(410, 0).addBox(0.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F)
						.texOffs(410, 22).addBox(-2.0F, 10.0F, -5.0F, 7.0F, 3.0F, 6.0F)
						.texOffs(410, 36).addBox(-3.0F, 12.0F, -8.0F, 2.0F, 2.0F, 4.0F)
						.texOffs(410, 36).addBox(2.0F, 12.0F, -8.5F, 2.0F, 2.0F, 5.0F),
				PartPose.offsetAndRotation(x, y, z, 0.75F, 0.0F, 0.0F));
	}

	private static void addArmorSpike(PartDefinition root, String name, float x, float y, float z, float height) {
		root.addOrReplaceChild(name, CubeListBuilder.create()
						.texOffs(300, 200).addBox(-2.5F, -height, -2.5F, 5.0F, height, 5.0F)
						.texOffs(300, 218).addBox(-1.5F, -height - 4.0F, -1.5F, 3.0F, 5.0F, 3.0F),
				PartPose.offset(x, y, z));
	}

	private static void addAnkyLeg(PartDefinition root, String name, float x, float y, float z) {
		root.addOrReplaceChild(name, CubeListBuilder.create()
						.texOffs(340, 200).addBox(0.0F, 0.0F, -5.0F, 9.0F, 17.0F, 10.0F)
						.texOffs(340, 232).addBox(-2.0F, 14.0F, -9.0F, 13.0F, 5.0F, 12.0F),
				PartPose.offset(x, y, z));
	}

	private static void addWhisker(PartDefinition root, String name, float x, float y, float z, float zRot) {
		root.addOrReplaceChild(name, CubeListBuilder.create()
						.texOffs(198, 0).addBox(0.0F, 0.0F, -1.0F, 1.0F, 18.0F, 1.0F),
				PartPose.offsetAndRotation(x, y, z, 1.35F, 0.0F, zRot));
	}
}
