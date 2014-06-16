
package riskyken.utilities.client.renderer.entity;

import org.lwjgl.opengl.GL11;

import riskyken.utilities.common.lib.LibModInfo;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderWolf;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class RenderShepherd extends RenderWolf {
	
    //private static final ResourceLocation wolfTextures = new ResourceLocation(ModInformation.ID.toLowerCase(), "textures/entity/shepherd/wolf.png");
    //private static final ResourceLocation tamedWolfTextures = new ResourceLocation(ModInformation.ID.toLowerCase(), "textures/entity/shepherd/wolf_tame.png");
    //private static final ResourceLocation anrgyWolfTextures = new ResourceLocation(ModInformation.ID.toLowerCase(), "textures/entity/shepherd/wolf_angry.png");
    //private static final ResourceLocation wolfCollarTextures = new ResourceLocation("textures/entity/wolf/wolf_collar.png");
    
	public RenderShepherd(ModelBase par1ModelBase, ModelBase par2ModelBase, float par3) {
		super(par1ModelBase, par2ModelBase, par3);
	}
	
	
	//@Override
    //protected ResourceLocation func_110914_a(EntityWolf par1EntityWolf)
    //{
    //    return par1EntityWolf.isTamed() ? tamedWolfTextures : (par1EntityWolf.isAngry() ? anrgyWolfTextures : wolfTextures);
    //}
}
