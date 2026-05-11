// Blockbench import source. Import this file with File > Import > Java Entity, then apply the matching PNG texture.
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;

public class PascalModel extends EntityModel<LivingEntityRenderState> {
	public PascalModel(ModelPart root) {
		super(root);
	}

	public static LayerDefinition createBodyLayer() {
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
				PartPose.offsetAndRotation(0.0F, -20.0F, -58.0F, 0.05F, 0.0F, 0.0F));
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
		addSmallCarnivoreArm(root, "right_front_leg", -21.0F, -15.0F, -38.0F, true);
		addSmallCarnivoreArm(root, "left_front_leg", 17.0F, -15.0F, -38.0F, false);

		return LayerDefinition.create(mesh, 512, 512);
	}


	@Override
	public void setupAnim(LivingEntityRenderState state) {
		super.setupAnim(state);
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

	private static void addSmallCarnivoreArm(PartDefinition root, String name, float x, float y, float z, boolean mirrored) {
		float side = mirrored ? -1.0F : 1.0F;
		root.addOrReplaceChild(name, CubeListBuilder.create()
						.texOffs(410, 0).addBox(side < 0.0F ? -4.0F : 0.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F)
						.texOffs(410, 22).addBox(side < 0.0F ? -5.0F : -2.0F, 10.0F, -5.0F, 7.0F, 3.0F, 6.0F)
						.texOffs(410, 36).addBox(side < 0.0F ? -5.0F : -3.0F, 12.0F, -8.0F, 2.0F, 2.0F, 4.0F)
						.texOffs(410, 36).addBox(side < 0.0F ? 0.0F : 2.0F, 12.0F, -8.5F, 2.0F, 2.0F, 5.0F),
				PartPose.offsetAndRotation(x, y, z, 0.75F, 0.0F, side * 0.22F));
	}
}
