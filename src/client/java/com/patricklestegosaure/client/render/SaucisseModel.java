package com.patricklestegosaure.client.render;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;

public class SaucisseModel extends EntityModel<LivingEntityRenderState> {
	public SaucisseModel(ModelPart root) {
		super(root);
	}

	public static LayerDefinition createLayer() {
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


	@Override
	public void setupAnim(LivingEntityRenderState state) {
		super.setupAnim(state);
	}

	private static void addTinyLeg(PartDefinition root, String name, float x, float y, float z, float zRot) {
		root.addOrReplaceChild(name, CubeListBuilder.create()
						.texOffs(108, 0).addBox(0.0F, 0.0F, -0.5F, 1.0F, 5.0F, 1.0F),
				PartPose.offsetAndRotation(x, y, z, 0.0F, 0.0F, zRot));
	}
}
