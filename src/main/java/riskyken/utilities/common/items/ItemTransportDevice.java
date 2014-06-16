package riskyken.utilities.common.items;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import riskyken.utilities.common.blocks.ModBlocks;
import riskyken.utilities.common.lib.LibItemNames;
import riskyken.utilities.common.lib.LibModInfo;
import riskyken.utilities.common.tileentities.TileEntityTransporterBeacon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemTransportDevice extends AbstractModItem {

	private static final int TIME_TO_USE = 10;
	
	public ItemTransportDevice() {
		super(LibItemNames.TRANSPORT_DEVICE);
		setMaxStackSize(1);
	}
	
	@Override
	public void onPlayerStoppedUsing(ItemStack itemStack, World world, EntityPlayer player, int itemUseCount) {
		
		if (!world.isRemote)
		{
			if (getMaxItemUseDuration(itemStack) - itemUseCount < (TIME_TO_USE * 2)) {
				return ;
			}
			
			
			if (!IsLinked(itemStack))
			{
				player.addChatMessage(new ChatComponentText("Not linked"));
				//player.addChatMessage("Not linked");
				return ;
			}
			
			int x = itemStack.getTagCompound().getInteger("xPos");
			int y = itemStack.getTagCompound().getInteger("yPos");
			int z = itemStack.getTagCompound().getInteger("zPos");
			int id = itemStack.getTagCompound().getInteger("worldId");
			
			if (world.provider.dimensionId != id)
			{
				player.addChatMessage(new ChatComponentText("Wrong dimension"));
				//player.addChatMessage("Wrong dimension");
				return ;
			}
			
			if (world.getBlock(x, y, z) != ModBlocks.transporterBeacon)
			{
				player.addChatMessage(new ChatComponentText("Can not find base"));
				//player.addChatMessage("Can not find base");
				return ;
			}

			TileEntity te = world.getTileEntity(x, y, z);
			if (te instanceof TileEntityTransporterBeacon)
			{
				TileEntityTransporterBeacon te2 = (TileEntityTransporterBeacon)te;
				double range = player.getDistance((double)te2.xCoord,(double)te2.yCoord,(double)te2.zCoord);
				if (te2.canDimensionJump() | te2.getMaxRange() >= range)
				{
					if (!te2.havePowerToJump()) {
						player.addChatMessage(new ChatComponentText("Low power"));
						//player.addChatMessage("Low power");
						return ;
					}
					if (world.provider.dimensionId != id)
					{
						player.setPosition(x + 0.5, y + 1, z + 0.5);
						player.travelToDimension(id);
					}
					te2.useJumpPower();
					player.setPositionAndUpdate(x + 0.5, y + 1, z + 0.5);
					world.playSoundAtEntity(player, LibModInfo.ID.toLowerCase() + ":" + "teleportfxrecieve", 1, 1);
				}
				else
				{
					player.addChatMessage(new ChatComponentText("Out of range " + String.format("%.1f", te2.getMaxRange() - range) + " blocks short"));
					//player.addChatMessage("Out of range " + (te2.getMaxRange() - range) + " short");
				}
			}
		}
	}
	
    @Override
    public EnumAction getItemUseAction(ItemStack par1ItemStack) {
    	return EnumAction.bow;
    }
    
	@Override
	public IIcon getIcon(ItemStack stack, int renderPass, EntityPlayer player,
			ItemStack usingItem, int useRemaining) {
		
		if (useRemaining == 0) {
			return itemIcon;
		}
		
		if (getMaxItemUseDuration(stack) - useRemaining >= (TIME_TO_USE * 2)) {
			return icons[1];
		}
		
		if (getMaxItemUseDuration(stack) - useRemaining >= TIME_TO_USE) {
			return icons[0];
		}
		
		return super.getIcon(stack, renderPass, player, usingItem, useRemaining);
	}
	
	@Override
	public int getMaxItemUseDuration(ItemStack par1ItemStack) {
		return 72000;
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World,
			EntityPlayer par3EntityPlayer) {
		par3EntityPlayer.setItemInUse(par1ItemStack, this.getMaxItemUseDuration(par1ItemStack));
		return super.onItemRightClick(par1ItemStack, par2World, par3EntityPlayer);
	}
	
	@SideOnly(Side.CLIENT)
	private IIcon[] icons;
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister register) {
		icons = new IIcon[2];
		itemIcon = register.registerIcon(LibModInfo.ID + ":" + "transport-device-0");
		icons[0] = register.registerIcon(LibModInfo.ID + ":" + "transport-device-1");
		icons[1] = register.registerIcon(LibModInfo.ID + ":" + "transport-device-2");
	}
	
	private boolean IsLinked(ItemStack itemStack) {
		if (!itemStack.hasTagCompound()) { return false; }
		return true;
	}
	
	private String GetOverleyText(ItemStack itemStack) {
		if (!IsLinked(itemStack)) { return "Not linked"; }
		
		int x = itemStack.getTagCompound().getInteger("xPos");
		int y = itemStack.getTagCompound().getInteger("yPos");
		int z = itemStack.getTagCompound().getInteger("zPos");
		int id = itemStack.getTagCompound().getInteger("worldId");
		
		return "Linked to X:" + x + " Y:" + y + " Z:" + z + " ID:" + id;
	}
	
	public void setLinkPos(ItemStack itemStack, World world, int x, int y, int z) {
		if (!itemStack.hasTagCompound())
		{itemStack.setTagCompound(new NBTTagCompound());}
		itemStack.getTagCompound().setInteger("xPos", x);
		itemStack.getTagCompound().setInteger("yPos", y);
		itemStack.getTagCompound().setInteger("zPos", z);
		itemStack.getTagCompound().setInteger("worldId", world.provider.dimensionId);
	}
	
	@Override
    public void addInformation(ItemStack itemStack, EntityPlayer player, List par3List, boolean par4)
    {
		par3List.add("Place in transporter beacon to set location.");
		par3List.add("Right click to use.");
		par3List.add("");
		par3List.add(GetOverleyText(itemStack));
    }
}
