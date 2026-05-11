// Blockbench import source. Import this file with File > Import > Java Entity, then apply the matching PNG texture.
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;

public class KalashModel extends EntityModel<LivingEntityRenderState> {
	public KalashModel(ModelPart root) {
		super(root);
	}

	public static LayerDefinition createBodyLayer() {
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


	@Override
	public void setupAnim(LivingEntityRenderState state) {
		super.setupAnim(state);
	}

	private static void addWhisker(PartDefinition root, String name, float x, float y, float z, float zRot) {
		root.addOrReplaceChild(name, CubeListBuilder.create()
						.texOffs(198, 0).addBox(0.0F, 0.0F, -1.0F, 1.0F, 18.0F, 1.0F),
				PartPose.offsetAndRotation(x, y, z, 1.35F, 0.0F, zRot));
	}
}
