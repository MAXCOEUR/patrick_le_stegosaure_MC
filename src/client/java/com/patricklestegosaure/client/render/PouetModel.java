package com.patricklestegosaure.client.render;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;

public class PouetModel extends EntityModel<LivingEntityRenderState> {
	public PouetModel(ModelPart root) {
		super(root);
	}

	public static LayerDefinition createLayer() {
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


	@Override
	public void setupAnim(LivingEntityRenderState state) {
		super.setupAnim(state);
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
}
