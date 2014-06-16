package riskyken.utilities.common.items;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import riskyken.utilities.RiskyKensUtilities;
import riskyken.utilities.common.lib.LibGuiIds;
import riskyken.utilities.common.lib.LibItemNames;
import riskyken.utilities.common.lib.LibModInfo;
import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemHairStyleKit extends AbstractModItem {

	public ItemHairStyleKit() {
		super(LibItemNames.HAIR_STYLE_KIT);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister register) {
		itemIcon = register.registerIcon(LibModInfo.ID.toLowerCase() + ":" + "hair-style-kit");
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player) {
		if (!world.isRemote) {
			
			//PlayerClothingData props = PlayerClothingData.get(player);
			//props.toggleWings();
			FMLNetworkHandler.openGui(player, RiskyKensUtilities.instance, LibGuiIds.HAIR_STYLE, world, 0, 0, 0);
		}
		return super.onItemRightClick(itemStack, world, player);
	}
}
