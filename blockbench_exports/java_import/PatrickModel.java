// Blockbench import source. Import this file with File > Import > Java Entity, then apply the matching PNG texture.
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;

public class PatrickModel extends EntityModel<LivingEntityRenderState> {
	public PatrickModel(ModelPart root) {
		super(root);
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition mesh = new MeshDefinition();
		PartDefinition root = mesh.getRoot();

		root.addOrReplaceChild("body", CubeListBuilder.create()
						.texOffs(0, 0).addBox(-18.0F, -25.0F, -38.0F, 36.0F, 25.0F, 66.0F)
						.texOffs(0, 96).addBox(-15.0F, -31.0F, -28.0F, 30.0F, 10.0F, 48.0F)
						.texOffs(144, 0).addBox(-15.0F, -20.0F, 24.0F, 30.0F, 18.0F, 18.0F)
						.texOffs(144, 40).addBox(-14.0F, -18.0F, -52.0F, 28.0F, 17.0F, 20.0F),
				PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition head = root.addOrReplaceChild("head", CubeListBuilder.create()
						.texOffs(0, 160).addBox(-7.0F, -8.0F, -10.0F, 14.0F, 12.0F, 15.0F)
						.texOffs(60, 160).addBox(-5.0F, -6.0F, -24.0F, 10.0F, 8.0F, 16.0F)
						.texOffs(112, 160).addBox(-4.0F, 0.0F, -25.0F, 8.0F, 4.0F, 15.0F),
				PartPose.offsetAndRotation(0.0F, -15.0F, -58.0F, 0.12F, 0.0F, 0.0F));
		head.addOrReplaceChild("left_eye", CubeListBuilder.create()
						.texOffs(236, 64).addBox(4.0F, -5.0F, -23.5F, 2.0F, 2.0F, 1.0F),
				PartPose.ZERO);
		head.addOrReplaceChild("right_eye", CubeListBuilder.create()
						.texOffs(236, 64).addBox(-6.0F, -5.0F, -23.5F, 2.0F, 2.0F, 1.0F),
				PartPose.ZERO);

		root.addOrReplaceChild("neck", CubeListBuilder.create()
						.texOffs(128, 160).addBox(-8.0F, -10.0F, -16.0F, 16.0F, 17.0F, 30.0F),
				PartPose.offsetAndRotation(0.0F, -11.0F, -42.0F, 0.35F, 0.0F, 0.0F));

		PartDefinition tail = root.addOrReplaceChild("tail", CubeListBuilder.create()
						.texOffs(0, 208).addBox(-9.0F, -8.0F, 0.0F, 18.0F, 16.0F, 28.0F)
						.texOffs(76, 208).addBox(-6.0F, -6.0F, 25.0F, 12.0F, 12.0F, 27.0F)
						.texOffs(140, 208).addBox(-3.0F, -4.0F, 48.0F, 6.0F, 8.0F, 24.0F),
				PartPose.offsetAndRotation(0.0F, -8.0F, 28.0F, -0.25F, 0.0F, 0.0F));
		addTailSpike(tail, "top_tail_spike", 0.0F, -7.0F, 67.0F, -0.75F, 0.0F, 0.0F);
		addTailSpike(tail, "left_tail_spike", -3.5F, -3.0F, 64.0F, -0.65F, 0.0F, -0.95F);
		addTailSpike(tail, "right_tail_spike", 3.5F, -3.0F, 64.0F, -0.65F, 0.0F, 0.95F);
		addTailSpike(tail, "lower_tail_spike", 0.0F, 3.0F, 63.0F, 0.25F, 0.0F, 3.14F);

		addStegoLeg(root, "right_back_leg", -13.0F, 0.0F, 18.0F);
		addStegoLeg(root, "left_back_leg", 5.0F, 0.0F, 18.0F);
		addStegoLeg(root, "right_front_leg", -12.0F, 0.0F, -34.0F);
		addStegoLeg(root, "left_front_leg", 4.0F, 0.0F, -34.0F);

		addStegoPlate(root, "neck_plate", 0.0F, -26.0F, -47.0F, 14.0F, 9.0F);
		addStegoPlate(root, "plate_1", 0.0F, -31.0F, -34.0F, 22.0F, 13.0F);
		addStegoPlate(root, "plate_2", 0.0F, -32.0F, -20.0F, 27.0F, 15.0F);
		addStegoPlate(root, "plate_3", 0.0F, -32.0F, -4.0F, 30.0F, 16.0F);
		addStegoPlate(root, "plate_4", 0.0F, -31.0F, 12.0F, 25.0F, 14.0F);
		addStegoPlate(root, "plate_5", 0.0F, -27.0F, 28.0F, 18.0F, 11.0F);
		addStegoPlate(root, "tail_plate_1", 0.0F, -18.0F, 45.0F, 10.0F, 8.0F);
		addStegoPlate(root, "tail_plate_2", 0.0F, -14.0F, 58.0F, 7.0F, 6.0F);

		return LayerDefinition.create(mesh, 512, 512);
	}


	@Override
	public void setupAnim(LivingEntityRenderState state) {
		super.setupAnim(state);
	}

	private static void addTailSpike(PartDefinition parent, String name, float x, float y, float z, float xRot, float yRot, float zRot) {
		parent.addOrReplaceChild(name, CubeListBuilder.create()
						.texOffs(410, 130).addBox(-1.5F, -16.0F, -1.5F, 3.0F, 16.0F, 3.0F)
						.texOffs(410, 154).addBox(-1.0F, -22.0F, -1.0F, 2.0F, 7.0F, 2.0F),
				PartPose.offsetAndRotation(x, y, z, xRot, yRot, zRot));
	}

	private static void addStegoLeg(PartDefinition root, String name, float x, float y, float z) {
		root.addOrReplaceChild(name, CubeListBuilder.create()
						.texOffs(330, 0).addBox(0.0F, 0.0F, -4.5F, 8.0F, 20.0F, 9.0F)
						.texOffs(330, 32).addBox(-1.0F, 16.0F, -8.0F, 10.0F, 5.0F, 12.0F)
						.texOffs(330, 54).addBox(2.0F, 19.0F, -10.0F, 2.0F, 3.0F, 4.0F)
						.texOffs(330, 54).addBox(5.0F, 19.0F, -10.0F, 2.0F, 3.0F, 4.0F),
				PartPose.offset(x, y, z));
	}

	private static void addStegoPlate(PartDefinition root, String name, float x, float y, float z, float height, float depth) {
		float bottom = height * 0.35F;
		float middle = height * 0.30F;
		float upper = height * 0.22F;
		root.addOrReplaceChild(name, CubeListBuilder.create()
						.texOffs(410, 0).addBox(-2.0F, -bottom, -depth * 0.5F, 4.0F, bottom, depth)
						.texOffs(410, 38).addBox(-1.5F, -bottom - middle, -depth * 0.36F, 3.0F, middle, depth * 0.72F)
						.texOffs(410, 70).addBox(-1.0F, -bottom - middle - upper, -depth * 0.20F, 2.0F, upper, depth * 0.40F)
						.texOffs(410, 96).addBox(-0.5F, -height, -1.0F, 1.0F, height * 0.13F, 2.0F),
				PartPose.offset(x, y, z));
	}
}
