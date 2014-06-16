package riskyken.utilities.common.creativetab;

import riskyken.utilities.common.blocks.ModBlocks;
import riskyken.utilities.common.items.ModItems;
import riskyken.utilities.common.lib.LibModInfo;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class CreativeTabRiskyKensUtilities extends CreativeTabs {

	public CreativeTabRiskyKensUtilities(int id,String label) {
		super(id,label);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public Item getTabIconItem()
	{
		return  Item.getItemFromBlock(ModBlocks.deviceTeleporter); 
	}
	
	@Override
	@SideOnly(Side.CLIENT)
    public String getTranslatedTabLabel()
    {
        return LibModInfo.NAME;
    }
}
