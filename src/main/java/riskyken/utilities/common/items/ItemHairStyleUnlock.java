package riskyken.utilities.common.items;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;
import riskyken.utilities.common.hair.HairStyleType;
import riskyken.utilities.common.hair.PlayerHairStyleData;
import riskyken.utilities.common.lib.LibItemNames;
import riskyken.utilities.common.lib.LibModInfo;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemHairStyleUnlock extends AbstractModItem {

	public ItemHairStyleUnlock() {
		super(LibItemNames.HAIR_STYLE_UNLOCK);
		setHasSubtypes(true);
	}
	
	@Override
	public void getSubItems(Item item, CreativeTabs tab, List list) {
		for (int i = 0; i < HairStyleType.values().length; i++) {
			ItemStack stack  = new ItemStack(item, 1, i);
			list.add(stack);
		}
	}
	
	@Override
	public String getUnlocalizedName(ItemStack itemStack) {
		return super.getUnlocalizedName(itemStack) + itemStack.getItemDamage();
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister register) {
		itemIcon = register.registerIcon(LibModInfo.ID + ":" + "style-unlock");
	}
	
	@Override
    public void addInformation(ItemStack itemStack, EntityPlayer player, List par3List, boolean par4)
    {
		HairStyleType type = HairStyleType.values()[itemStack.getItemDamage()];

		par3List.add("Unlocks " + type.getName().toLowerCase() + " hair style.");

    }
	
	@Override
	public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player) {
		if (!world.isRemote) {
			PlayerHairStyleData props = PlayerHairStyleData.get(player);
			HairStyleType type = HairStyleType.values()[itemStack.getItemDamage()];
			if (props.setHasHairStyle(type)) {
				//player.destroyCurrentEquippedItem();
				//itemStack.damageItem(1, player);
				itemStack.stackSize--;
				player.addChatComponentMessage(new ChatComponentText(type.getName() + " hair style unlocked."));
			} else {
				player.addChatComponentMessage(new ChatComponentText("You already have this hair style."));
			}
		}
		return super.onItemRightClick(itemStack, world, player);
	}
}
