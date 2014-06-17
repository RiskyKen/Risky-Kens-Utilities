package riskyken.utilities.common.blocks;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import riskyken.utilities.common.items.block.ModItemBlockWithMetadata;
import riskyken.utilities.common.lib.LibBlockNames;
import riskyken.utilities.common.lib.LibModInfo;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockDevice extends AbstractModBlock {

	public BlockDevice() {
		super(LibBlockNames.DEVICE_BLOCK);
	}
	
	@Override
	public Block setBlockName(String name) {
		GameRegistry.registerBlock(this, ModItemBlockWithMetadata.class, "block." + name);
		return super.setBlockName(name);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister register) {
		blockIcons = new IIcon[2];
		blockIcon = register.registerIcon(LibModInfo.ID + ":"
				+ "device-block-3");
		
		blockIcons[0] = register.registerIcon(LibModInfo.ID + ":"
				+ "device-block-depth-gauge");
		
		blockIcons[1] = register.registerIcon(LibModInfo.ID + ":"
				+ "device-block-water-drain");
	}

	@SideOnly(Side.CLIENT)
	private IIcon[] blockIcons;

	@Override
	public IIcon getIcon(int side, int meta) {
		switch (meta) {
		case 0:
			if (side > 1) {
				return blockIcons[0];
			}
			break;
		case 1:
			if (side > 1) {
				return blockIcons[1];
			}
			break;
		}
		return blockIcon;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {
		for (int i = 0; i < 2; i++) {
			list.add(new ItemStack(item, 1, i));
		}
	}

	@Override
	public int damageDropped(int meta) {
		return meta;
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z,
			EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if (!world.isRemote) {
			switch (world.getBlockMetadata(x, y, z)) {
			case 0:
				depthGauge(world, x, y, z, player);
				break;
			case 1:
				waterDrain(world, x, y, z, player);
				break;
			case 2:
				// getBlockInfo(world, x, y, z, player);
				break;
			}
		}
		return true;// super.onBlockActivated(world, x, y, z, player, side,
					// hitX, hitY, hitZ);
	}

	private void getBlockInfo(World world, int x, int y, int z,
			EntityPlayer player) {
		// player.addChatMessage("Block id at 0 10 0 is " + world.getBlockId(0,
		// 10, 0));
	}

	private void waterDrain(World world, int x, int y, int z,
			EntityPlayer player) {
		player.addChatMessage(new ChatComponentText("Sorry not added yet!"));
		// player.addChatMessage("Draining water!");
	}

	private void depthGauge(World world, int x, int y, int z,
			EntityPlayer player) {
		int depth = findDepth(world, x, y, z);
		int down = y - depth;

		player.addChatMessage(new ChatComponentText("Depth: " + down));
		player.addChatMessage(new ChatComponentText("Height from bedrock: "
				+ depth));
		// player.addChatMessage("Depth: " + down);
		// player.addChatMessage("Height from bedrock: " + depth);

	}

	private int findDepth(World world, int x, int y, int z) {
		int lowest = y;
		for (int ix = -2; ix < 3; ix++) {
			for (int iz = -2; iz < 3; iz++) {
				int rowLow = checkDownRow(world, x + ix, y, z + iz);
				if (rowLow < lowest) {
					lowest = rowLow;
				}
			}
		}

		return lowest;
	}

	private int checkDownRow(World world, int x, int y, int z) {
		int lowest = y;
		for (int d = y - 1; d >= 0; d--) {
			if (world.isAirBlock(x, d, z)) {
				if (d < lowest) {
					lowest = d;
				}
			} else {
				// world.getBlock(p_147439_1_, p_147439_2_, p_147439_3_)
				Block block = world.getBlock(x, d, z);
				// Block someBlock = Block.blocksList[blockId];
				if (block.isReplaceable(world, x, d, z)) {
					if (d < lowest) {
						lowest = d;
					}
				} else {
					return lowest;
				}
			}
		}
		return y;
	}
}
