package riskyken.utilities.proxies;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;
import riskyken.utilities.client.model.ModelPlayerHead;
import riskyken.utilities.client.renderer.GiftBlockRender;
import riskyken.utilities.client.renderer.HairRenderManager;
import riskyken.utilities.client.renderer.RenderBlockSun;
import riskyken.utilities.client.renderer.RenderItemMagicStaff;
import riskyken.utilities.common.hair.PlayerHairStyleData;
import riskyken.utilities.common.items.ModItems;
import riskyken.utilities.common.lib.LibSounds;
import riskyken.utilities.common.tileentities.TileEntityStarLight;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy {
	
	public static int renderPass;
	
	public static int giftBlockRenderType;
	//public static int starBlockRenderType;
	
	public static ModelPlayerHead playerHeadModel = new ModelPlayerHead();
	
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
	
	@Override
	public void setHairStyleData(int hairStyleUnlockFlags, int hairAccessoriesUnlockFlags, int hairAccessoryColourUnlockFlags) {
		EntityClientPlayerMP player = Minecraft.getMinecraft().thePlayer;
		PlayerHairStyleData props = PlayerHairStyleData.get(player);
		props.setHairStyleUnlockFlags(hairStyleUnlockFlags);
		props.setHairAccessoriesUnlockFlags(hairAccessoriesUnlockFlags);
		props.setHairAccessoryColourUnlockFlags(hairAccessoryColourUnlockFlags);
	}
}
