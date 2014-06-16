package riskyken.utilities.common;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import riskyken.utilities.RiskyKensUtilities;
import riskyken.utilities.client.gui.GuiHollower;
import riskyken.utilities.client.gui.GuiTeleporter;
import riskyken.utilities.client.gui.GuiGift;
import riskyken.utilities.client.gui.GuiHairStyleKit;
import riskyken.utilities.client.gui.GuiLeafCatcher;
import riskyken.utilities.client.gui.GuiMusicSequencer;
import riskyken.utilities.client.gui.GuiTransporterBeacon;
import riskyken.utilities.common.inventory.ContainerHollower;
import riskyken.utilities.common.inventory.ContainerTeleporter;
import riskyken.utilities.common.inventory.ContainerGift;
import riskyken.utilities.common.inventory.ContainerHairStyleKit;
import riskyken.utilities.common.inventory.ContainerLeafCatcher;
import riskyken.utilities.common.inventory.ContainerMusicSequencer;
import riskyken.utilities.common.inventory.ContainerTransporterBeacon;
import riskyken.utilities.common.lib.LibGuiIds;
import riskyken.utilities.common.tileentities.TileEntityDeviceHollower;
import riskyken.utilities.common.tileentities.TileEntityDeviceTeleporter;
import riskyken.utilities.common.tileentities.TileEntityGift;
import riskyken.utilities.common.tileentities.TileEntityLeafCatcher;
import riskyken.utilities.common.tileentities.TileEntityMusicSequencer;
import riskyken.utilities.common.tileentities.TileEntityTransporterBeacon;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.network.NetworkRegistry;

public class GuiHandler implements IGuiHandler {

	public GuiHandler() {
		NetworkRegistry.INSTANCE.registerGuiHandler(RiskyKensUtilities.instance, this);
	}
	
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity te = world.getTileEntity(x, y, z);
		if (te == null) {
			switch (ID)
			{
				case LibGuiIds.HAIR_STYLE:
					return new ContainerHairStyleKit(player);
			}
			return null;
		}
		switch (ID)
		{
			case LibGuiIds.HOLLOWER:
				if (te instanceof TileEntityDeviceHollower){
					return new ContainerHollower(player.inventory,(TileEntityDeviceHollower)te);
				}
				break;
			case LibGuiIds.TELEPORTER:
				if (te instanceof TileEntityDeviceTeleporter){
					return new ContainerTeleporter(player.inventory,(TileEntityDeviceTeleporter)te);
				}
				break;
			case LibGuiIds.TRANSPORTER_BEACON:
				if (te instanceof TileEntityTransporterBeacon){
					return new ContainerTransporterBeacon(player.inventory,(TileEntityTransporterBeacon)te);
				}
				break;
			case LibGuiIds.GIFT:
				if (te instanceof TileEntityGift){
					return new ContainerGift(player.inventory,(TileEntityGift)te);
				}
				break;
			case LibGuiIds.MUSIC_SEQUENCER:
				if (te instanceof TileEntityMusicSequencer){
					return new ContainerMusicSequencer(player.inventory,(TileEntityMusicSequencer)te);
				}
				break;
			case LibGuiIds.LEAF_CATCHER:
				if (te instanceof TileEntityLeafCatcher){
					return new ContainerLeafCatcher(player.inventory,(TileEntityLeafCatcher)te);
				}
				break;
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity te = world.getTileEntity(x, y, z);
		if (te == null) {
			switch (ID)
			{
				case LibGuiIds.HAIR_STYLE:
					return new GuiHairStyleKit(player);
			}
			return null;
		}
		switch (ID)
		{
			case LibGuiIds.HOLLOWER:
				if (te instanceof TileEntityDeviceHollower){
					return new GuiHollower(player.inventory,(TileEntityDeviceHollower)te);
				}
				break;
			case LibGuiIds.TELEPORTER:
				if (te instanceof TileEntityDeviceTeleporter){
					return new GuiTeleporter(player.inventory,(TileEntityDeviceTeleporter)te);
				}
				break;
			case LibGuiIds.TRANSPORTER_BEACON:
				if (te instanceof TileEntityTransporterBeacon){
					return new GuiTransporterBeacon(player.inventory,(TileEntityTransporterBeacon)te);
				}
				break;
			case LibGuiIds.GIFT:
				if (te instanceof TileEntityGift){
					return new GuiGift(player.inventory,(TileEntityGift)te);
				}
				break;
			case LibGuiIds.MUSIC_SEQUENCER:
				if (te instanceof TileEntityMusicSequencer){
					return new GuiMusicSequencer(player.inventory,(TileEntityMusicSequencer)te);
				}
				break;
			case LibGuiIds.LEAF_CATCHER:
				if (te instanceof TileEntityLeafCatcher){
					return new GuiLeafCatcher(player.inventory,(TileEntityLeafCatcher)te);
				}
				break;
		}
		return null;
	}

}
