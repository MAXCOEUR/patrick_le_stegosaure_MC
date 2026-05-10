package com.patricklestegosaure.client.render;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;

public class ThierryModel extends EntityModel<LivingEntityRenderState> {
	public ThierryModel(ModelPart root) {
		super(root);
	}

	public static LayerDefinition createLayer() {
		MeshDefinition mesh = new MeshDefinition();
		PartDefinition root = mesh.getRoot();

		root.addOrReplaceChild("body", CubeListBuilder.create()
						.texOffs(0, 0).addBox(-22.0F, -44.0F, -42.0F, 44.0F, 46.0F, 72.0F)
						.texOffs(0, 124).addBox(-18.0F, -18.0F, -34.0F, 36.0F, 16.0F, 50.0F)
						.texOffs(170, 0).addBox(-18.0F, -56.0F, -20.0F, 36.0F, 16.0F, 36.0F),
				PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.18F, 0.0F, 0.0F));

		PartDefinition head = root.addOrReplaceChild("head", CubeListBuilder.create()
						.texOffs(0, 200).addBox(-18.0F, -18.0F, -28.0F, 36.0F, 27.0F, 32.0F)
						.texOffs(138, 200).addBox(-15.0F, -12.0F, -58.0F, 30.0F, 15.0F, 34.0F)
						.texOffs(268, 200).addBox(-13.0F, 3.0F, -54.0F, 26.0F, 9.0F, 30.0F),
				PartPose.offsetAndRotation(0.0F, -48.0F, -50.0F, 0.08F, 0.0F, 0.0F));
		head.addOrReplaceChild("upper_teeth", CubeListBuilder.create()
						.texOffs(236, 64).addBox(-12.0F, 2.0F, -53.0F, 24.0F, 2.0F, 5.0F),
				PartPose.ZERO);
		head.addOrReplaceChild("lower_teeth", CubeListBuilder.create()
						.texOffs(236, 64).addBox(-11.0F, 5.5F, -50.0F, 22.0F, 2.0F, 4.0F),
				PartPose.ZERO);
		head.addOrReplaceChild("mouth_shadow", CubeListBuilder.create()
						.texOffs(236, 84).addBox(-12.0F, 3.2F, -51.0F, 24.0F, 3.0F, 22.0F),
				PartPose.ZERO);
		head.addOrReplaceChild("left_eye", CubeListBuilder.create()
						.texOffs(236, 112).addBox(9.0F, -12.0F, -30.0F, 5.0F, 4.0F, 2.0F),
				PartPose.ZERO);
		head.addOrReplaceChild("right_eye", CubeListBuilder.create()
						.texOffs(236, 112).addBox(-14.0F, -12.0F, -30.0F, 5.0F, 4.0F, 2.0F),
				PartPose.ZERO);
		head.addOrReplaceChild("brow", CubeListBuilder.create()
						.texOffs(236, 124).addBox(-15.0F, -15.0F, -32.0F, 30.0F, 4.0F, 7.0F),
				PartPose.ZERO);

		PartDefinition tail = root.addOrReplaceChild("tail", CubeListBuilder.create()
						.texOffs(0, 280).addBox(-11.0F, -11.0F, 0.0F, 22.0F, 22.0F, 46.0F)
						.texOffs(94, 280).addBox(-8.0F, -8.0F, 40.0F, 16.0F, 16.0F, 48.0F)
						.texOffs(170, 280).addBox(-4.0F, -5.0F, 84.0F, 8.0F, 10.0F, 38.0F),
				PartPose.offsetAndRotation(0.0F, -18.0F, 24.0F, -0.28F, 0.0F, 0.0F));

		addTrexLeg(root, "right_back_leg", -16.0F, -2.0F, 0.0F);
		addTrexLeg(root, "left_back_leg", 4.0F, -2.0F, 0.0F);
		addTrexArm(root, "right_front_leg", -28.0F, -28.0F, -34.0F, -0.35F, true);
		addTrexArm(root, "left_front_leg", 23.0F, -28.0F, -34.0F, -0.35F, false);

		return LayerDefinition.create(mesh, 512, 512);
	}


	@Override
	public void setupAnim(LivingEntityRenderState state) {
		super.setupAnim(state);
	}

	private static void addTrexLeg(PartDefinition root, String name, float x, float y, float z) {
		root.addOrReplaceChild(name, CubeListBuilder.create()
						.texOffs(330, 90).addBox(0.0F, 0.0F, -8.0F, 14.0F, 24.0F, 16.0F)
						.texOffs(330, 132).addBox(1.0F, 20.0F, -3.0F, 11.0F, 20.0F, 11.0F)
						.texOffs(330, 168).addBox(-4.0F, 36.0F, -18.0F, 22.0F, 6.0F, 22.0F)
						.texOffs(330, 204).addBox(-4.0F, 39.0F, -23.0F, 4.0F, 3.0F, 7.0F)
						.texOffs(330, 204).addBox(3.0F, 39.0F, -24.0F, 4.0F, 3.0F, 8.0F)
						.texOffs(330, 204).addBox(10.0F, 39.0F, -23.0F, 4.0F, 3.0F, 7.0F),
				PartPose.offset(x, y, z));
	}

	private static void addTrexArm(PartDefinition root, String name, float x, float y, float z, float xRot, boolean mirrored) {
		float side = mirrored ? -1.0F : 1.0F;
		root.addOrReplaceChild(name, CubeListBuilder.create()
						.texOffs(330, 230).addBox(side < 0.0F ? -5.0F : 0.0F, 0.0F, -2.5F, 5.0F, 16.0F, 5.0F)
						.texOffs(330, 252).addBox(side < 0.0F ? -6.0F : -1.0F, 13.0F, -5.0F, 7.0F, 4.0F, 8.0F)
						.texOffs(330, 270).addBox(side < 0.0F ? -6.0F : -1.0F, 15.0F, -8.0F, 2.0F, 2.0F, 4.0F)
						.texOffs(330, 270).addBox(side < 0.0F ? -1.0F : 4.0F, 15.0F, -8.0F, 2.0F, 2.0F, 4.0F),
				PartPose.offsetAndRotation(x, y, z, xRot, 0.0F, side * 0.18F));
	}
}
