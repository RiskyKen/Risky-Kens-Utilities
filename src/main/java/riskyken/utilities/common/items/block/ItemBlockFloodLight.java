package riskyken.utilities.common.items.block;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import riskyken.utilities.common.blocks.BlockFloodLightTypes;
import riskyken.utilities.common.lib.LibModInfo;

public class ItemBlockFloodLight extends ItemBlock {

	public ItemBlockFloodLight(Block block) {
		super(block);
		setHasSubtypes(true);
	}
	
	@Override
	public String getUnlocalizedName(ItemStack itemstack) {
		return field_150939_a.getUnlocalizedName() + itemstack.getItemDamage();
	}
	
	@Override
	public int getMetadata(int dmg)
	{
		return dmg;
	}
	
	@Override
	public void addInformation(ItemStack itemStack, EntityPlayer player, List hoverText, boolean par4) {
		BlockFloodLightTypes.addHoverText(itemStack, player, hoverText);
		super.addInformation(itemStack, player, hoverText, par4);
	}

}
