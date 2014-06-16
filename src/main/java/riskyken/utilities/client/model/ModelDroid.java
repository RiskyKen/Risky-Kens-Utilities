package riskyken.utilities.client.model;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import riskyken.utilities.common.entities.EntityDroid;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

@SideOnly(Side.CLIENT)
public class ModelDroid extends ModelBase {

	private ModelRenderer main;
	private ArrayList<ModelRenderer> parts;
	
	
	public ModelDroid() {
		parts = new ArrayList<ModelRenderer>();
		
		
		
		main = new ModelRenderer(this);
		main.addBox(-6, 1, -6,
					12, 12, 12);
		main.setRotationPoint(0, 0, 0);
		//parts.add(main);
		
		
		//top
		ModelRenderer ribbon1 = new ModelRenderer(this);
		ribbon1.addBox(-7, 13, -2F,
						14, 1, 4);
		ribbon1.setRotationPoint(0, 0, 0);
		parts.add(ribbon1);
		//main.addChild(ribbon1);
		
		ModelRenderer ribbon2 = new ModelRenderer(this);
		ribbon2.addBox(-7, 1, -2F,
						1, 12, 4);
		ribbon2.setRotationPoint(0, 0, 0);
		parts.add(ribbon2);
		//main.addChild(ribbon2);
		
		ModelRenderer ribbon3 = new ModelRenderer(this);
		ribbon3.addBox(6, 1, -2F,
						1, 12, 4);
		ribbon3.setRotationPoint(0, 0, 0);
		parts.add(ribbon3);
		
		//bottom
		ModelRenderer ribbon4 = new ModelRenderer(this);
		ribbon4.addBox(-7, 0, -2F,
						14, 1, 4);
		ribbon4.setRotationPoint(0, 0, 0);
		parts.add(ribbon4);
		
		ModelRenderer ribbon5 = new ModelRenderer(this);
		ribbon5.addBox(-2F, 1, 6,
						4, 12, 1);
		ribbon5.setRotationPoint(0, 0, 0);
		parts.add(ribbon5);
		
		ModelRenderer ribbon6 = new ModelRenderer(this);
		ribbon6.addBox(-2F, 1, -7,
						4, 12, 1);
		ribbon6.setRotationPoint(0, 0, 0);
		parts.add(ribbon6);
		
		//top extra
		ModelRenderer ribbon7 = new ModelRenderer(this);
		ribbon7.addBox(-2F, 13, -7,
						4, 1, 5);
		ribbon7.setRotationPoint(0, 0, 0);
		parts.add(ribbon7);
		
		ModelRenderer ribbon8 = new ModelRenderer(this);
		ribbon8.addBox(-2F, 13, 2,
						4, 1, 5);
		ribbon8.setRotationPoint(0, 0, 0);
		parts.add(ribbon8);
		
		//bottom extra
		ModelRenderer ribbon9 = new ModelRenderer(this);
		ribbon9.addBox(-2F, 0, -7,
						4, 1, 5);
		ribbon9.setRotationPoint(0, 0, 0);
		parts.add(ribbon9);
		
		ModelRenderer ribbon10 = new ModelRenderer(this);
		ribbon10.addBox(-2F, 0, 2,
						4, 1, 5);
		ribbon10.setRotationPoint(0, 0, 0);
		parts.add(ribbon10);
	}
	
	@Override
	public void render(Entity entity, float par2, float par3, float par4, float par5, float par6, float mult) {
		//GL11.glRotated(((EntityDroid)entity).getRotateAngle() * Math.PI * 4, 0, 1, 0);
		//GL11.glTranslatef(0F, ((EntityDroid)entity).getRotateAngle(), 0F);
		//main.rotateAngleY = ((EntityDroid)entity).getRotateAngle();
		

		
		GL11.glColor4f(1F, 0F, 0F, 1F);
		
		main.render(mult);
		
		GL11.glColor4f(1F, 1F, 1F, 1F);
		for(ModelRenderer part : parts) {
			part.render(mult);
		}
	}
	
	public void render(float cameraPitch, float cameraYaw) {
		//GL11.glRotated(((EntityDroid)entity).getRotateAngle() * Math.PI * 4, 0, 1, 0);
		//GL11.glTranslatef(0F, ((EntityDroid)entity).getRotateAngle(), 0F);
		//main.rotateAngleY = ((EntityDroid)entity).getRotateAngle();
		float mult = 0.0625F;
		
		//GL11.glRotatef(-cameraYaw, 1, 0, 0);
		
		
		//GL11.glTranslatef(0, -0.9f, 0);
		
		GL11.glRotatef(cameraPitch, 0, 1, 0);
		
		
		
		//GL11.glRotatef(angle, x, y, z);

		
		//GL11.glColor4f(1F, 0F, 0F, 1F);
		main.render(mult);
		
		//GL11.glColor4f(1F, 1F, 1F, 1F);
		for(ModelRenderer part : parts) {
			part.render(mult);
		}
		
		
		
	}
	
}
