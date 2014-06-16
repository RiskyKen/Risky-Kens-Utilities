package riskyken.utilities.client.renderer;

import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.common.MinecraftForge;

import org.lwjgl.opengl.GL11;

import riskyken.utilities.client.model.hair.ModelBun;
import riskyken.utilities.client.model.hair.ModelBuns;
import riskyken.utilities.client.model.hair.ModelLongHair;
import riskyken.utilities.client.model.hair.ModelMohican;
import riskyken.utilities.client.model.hair.ModelOdango;
import riskyken.utilities.client.model.hair.ModelPonyTail;
import riskyken.utilities.client.model.hair.ModelTwinTails;
import riskyken.utilities.common.hair.PlayerHairStyleData;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class HairRenderManager {
	
	public static ModelLongHair hairLong = new ModelLongHair();
	public static ModelPonyTail hairPonyTail = new ModelPonyTail();
	public static ModelTwinTails hairTwinTails = new ModelTwinTails();
	public static ModelOdango hairOdango = new ModelOdango();
	public static ModelBun hairBun = new ModelBun();
	public static ModelBuns hairBuns = new ModelBuns();
	public static ModelMohican hairMohican = new ModelMohican();
	
	public HairRenderManager() {
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	public void renderHairOnPlayer(EntityPlayer player, RenderPlayer renderer) {
		PlayerHairStyleData props = PlayerHairStyleData.get(player);
		
		if (props.getHairType() == 0) {
			return;
		}
		
		GL11.glPushMatrix();
		if (renderer != null) {
			float rotationY = renderer.modelBipedMain.bipedHead.rotateAngleY * (180F / (float)Math.PI);
			float rotationX = renderer.modelBipedMain.bipedHead.rotateAngleX * (180F / (float)Math.PI);
			GL11.glRotatef(rotationY, 0, 1, 0);
			GL11.glRotatef(rotationX, 1, 0, 0);
		}
		
		
		switch (props.getHairType()) {
		case 1:
			hairLong.renderHair(player, props.getHairColour());
			break;
		case 2:
			hairPonyTail.renderHair(player, props.getHairColour());
			break;
		case 3:
			hairTwinTails.renderHair(player, props.getHairColour());
			break;
		case 4:
			hairOdango.renderHair(player, props.getHairColour());
			break;
		case 5:
			hairBun.renderHair(player, props.getHairColour());
			break;
		case 6:
			hairBuns.renderHair(player, props.getHairColour());
			break;
		case 7:
			//hairUpdo.renderHair(player, props.getHairColour());
			break;
		case 8:
			hairMohican.renderHair(player, props.getHairColour());
			break;
		case 9:
			//hairAfro.renderHair(player, props.getHairColour());
			break;
			
		default:
			break;
		}
		GL11.glPopMatrix();
	}
	
	private boolean playerHasHair(EntityPlayer player) {
		PlayerHairStyleData props = PlayerHairStyleData.get(player);
		return props.getHasHair();
	}
	
	@SubscribeEvent
	public void onRender(RenderPlayerEvent.SetArmorModel ev){
		renderHairOnPlayer(ev.entityPlayer, ev.renderer);
	}
	
}
