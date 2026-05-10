package com.patricklestegosaure.client.render;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;

public class BrigitteModel extends EntityModel<LivingEntityRenderState> {
	public BrigitteModel(ModelPart root) {
		super(root);
	}

	public static LayerDefinition createLayer() {
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
	}
}
