package riskyken.utilities.proxies;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.common.MinecraftForge;
import riskyken.utilities.client.model.ModelBigWings;
import riskyken.utilities.client.model.ModelExtraBigWings;
import riskyken.utilities.client.model.ModelPlayerHead;
import riskyken.utilities.client.renderer.GiftBlockRender;
import riskyken.utilities.client.renderer.HairRenderManager;
import riskyken.utilities.client.renderer.RenderBlockSun;
import riskyken.utilities.client.renderer.RenderItemMagicStaff;
import riskyken.utilities.common.hair.PlayerHairStyleData;
import riskyken.utilities.common.items.ModItems;
import riskyken.utilities.common.lib.LibModInfo;
import riskyken.utilities.common.lib.LibSounds;
import riskyken.utilities.common.tileentities.TileEntityStarLight;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class ClientProxy extends CommonProxy {
	
	public static int renderPass;
	
	public static int giftBlockRenderType;
	//public static int starBlockRenderType;
	
	public static ModelPlayerHead playerHeadModel = new ModelPlayerHead();
	
	//public static ModelWings wingModel = new ModelWings();
	public static ModelBigWings bigWings = new ModelBigWings();
	public static ModelExtraBigWings extraBigWings = new ModelExtraBigWings();
	
	public static HairRenderManager hairRenderManager;
	
	//public static ModelDroid modelDroid = new ModelDroid();
	
	public ClientProxy() {
		MinecraftForge.EVENT_BUS.register(this);
		hairRenderManager = new HairRenderManager();
	}
	
	@Override
	public void init() {
		
	}
	
	@Override
	public void initRenderers() {
		//RenderingRegistry.registerEntityRenderingHandler(EntityShepherd.class, new RenderShepherd(new ModelWolf(), new ModelWolf(), 0.5F));
		
		giftBlockRenderType = RenderingRegistry.getNextAvailableRenderId();
		RenderingRegistry.registerBlockHandler(new GiftBlockRender());
		
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityStarLight.class, new RenderBlockSun());
		
		MinecraftForgeClient.registerItemRenderer(ModItems.raisingHeart, new RenderItemMagicStaff());
		//MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ModBlocks.gift), new RenderItemGift());
	}
	
	@Override
	public void postInit() {
		//MinecraftForge.EVENT_BUS.register(new ModTickHandler());
		//FMLCommonHandler.instance().bus().register(new TickHandlerClient());
	}
	
	@Override
	public void playDimensionSound(byte soundId) {
		Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation(LibSounds.STAR_BORN), 1.0F));
	}
	
	@SubscribeEvent
	public void onRender(RenderPlayerEvent.SetArmorModel ev){
		//extraBigWings.render(ev.entityPlayer, ev.renderer);
		//bigWings.render(ev.entityPlayer, ev.renderer, 2);
		if (ev.entityPlayer.getDisplayName().equals("RiskyKen")) {
			bigWings.render(ev.entityPlayer, ev.renderer, 1);
		}
		if (ev.entityPlayer.getDisplayName().equals("Choccie_Bunny")) {
			bigWings.render(ev.entityPlayer, ev.renderer, 2);
		}
		if (ev.entityPlayer.getDisplayName().equals("Borro55")) {
			//TODO pink fairy wings
			//bigWings.render(ev.entityPlayer, ev.renderer, 2);
		}
	}
	
	@Override
	public void onPlayerTick(EntityPlayer player){
		//bigWings.onTick(player, 2);
		if (player.getDisplayName().equals("RiskyKen")) {
			bigWings.onTick(player, 1);
		}
		if (player.getDisplayName().equals("Choccie_Bunny")) {
			bigWings.onTick(player, 2);
		}
		if (player.getDisplayName().equals("Borro55")) {
			//bigWings.onTick(player, 2);
		}
	}
	
	@Override
	public void setHairStyleData(int hairStyleUnlockFlags, int hairAccessoriesUnlockFlags, int hairAccessoryColourUnlockFlags) {
		EntityClientPlayerMP player = Minecraft.getMinecraft().thePlayer;
		PlayerHairStyleData props = PlayerHairStyleData.get(player);
		props.setHairStyleUnlockFlags(hairStyleUnlockFlags);
		props.setHairAccessoriesUnlockFlags(hairAccessoriesUnlockFlags);
		props.setHairAccessoryColourUnlockFlags(hairAccessoryColourUnlockFlags);
	}
	
}