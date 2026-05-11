// Blockbench import source. Import this file with File > Import > Java Entity, then apply the matching PNG texture.
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;

public class PamoukModel extends EntityModel<LivingEntityRenderState> {
	public PamoukModel(ModelPart root) {
		super(root);
	}

	public static LayerDefinition createBodyLayer() {
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
				PartPose.offsetAndRotation(0.0F, -10.0F, -52.0F, 0.0F, 0.0F, 0.0F));
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


	@Override
	public void setupAnim(LivingEntityRenderState state) {
		super.setupAnim(state);
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
}
