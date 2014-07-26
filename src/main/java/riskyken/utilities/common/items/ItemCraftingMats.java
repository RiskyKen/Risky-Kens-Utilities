package riskyken.utilities.common.items;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import riskyken.utilities.common.lib.LibItemNames;
import riskyken.utilities.common.lib.LibModInfo;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemCraftingMats extends AbstractModItem {

	public static final String[] CRAFTING_MAT_NAMES =
		{"Ribbon", "Super Note Block"};
	
	public ItemCraftingMats() {
		super(LibItemNames.CRAFTING_MATS);
		setHasSubtypes(true);
	}
	
	@Override
	public void getSubItems(Item item, CreativeTabs tab, List list) {
		for (int i = 0; i < CRAFTING_MAT_NAMES.length; i++) {
			ItemStack stack  = new ItemStack(item, 1, i);
			list.add(stack);
		}
	}
	
	@Override
	public String getUnlocalizedName(ItemStack itemStack) {
		return super.getUnlocalizedName(itemStack) + itemStack.getItemDamage();
	}
	
	@Override
    public void addInformation(ItemStack itemStack, EntityPlayer player, List par3List, boolean par4)
    {
		if (itemStack.getItemDamage() == 0)
		{
			//par3List.add("Upgrades the range of a block.");
		}
    }
	
	@Override
	public IIcon getIconFromDamage(int par1) {
		if (par1 <= icons.length)
		{
			return icons[par1];
		}
		// TODO Auto-generated method stub
		return super.getIconFromDamage(par1);
	}
	
	@Override
	public IIcon getIcon(ItemStack stack, int pass) {
		if (stack.getItemDamage() <= icons.length)
		{
			return icons[stack.getItemDamage()];
		}
		return super.getIcon(stack, pass);
	}
	
	@SideOnly(Side.CLIENT)
	private IIcon[] icons;
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister register) {
		icons = new IIcon[CRAFTING_MAT_NAMES.length];
		for (int i = 0; i < CRAFTING_MAT_NAMES.length; i++)
		{
			icons[i] = register.registerIcon(LibModInfo.ID + ":" + "mat" + i);
		}
		itemIcon = register.registerIcon(LibModInfo.ID + ":" + "mat0");
	}
}
