package riskyken.utilities.common.network.messages;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Container;
import riskyken.utilities.common.hair.PlayerHairStyleData;
import riskyken.utilities.common.inventory.ContainerHollower;
import riskyken.utilities.common.inventory.ContainerHairStyleKit;
import riskyken.utilities.common.inventory.ContainerMusicSequencer;
import riskyken.utilities.common.tileentities.TileEntityDeviceHollower;
import riskyken.utilities.common.tileentities.TileEntityMusicSequencer;
import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class MessageButton implements IMessage, IMessageHandler<MessageButton, IMessage>{

	short buttonId;
	
	public MessageButton() {}
	
	public MessageButton(short buttonId) {
		this.buttonId = buttonId;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		this.buttonId = buf.readShort();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeShort(this.buttonId);
	}

	@Override
	public IMessage onMessage(MessageButton message, MessageContext ctx) {
		System.out.println("got button data server");
		EntityPlayerMP player = ctx.getServerHandler().playerEntity;
		
		if (player == null) {
			return null;
		}
		
		Container container = player.openContainer;
		if (container != null && container instanceof ContainerHollower)
		{
			TileEntityDeviceHollower deviceHollower = ((ContainerHollower)container).getTileEntity();
			deviceHollower.receiveButtonEvent(message.buttonId);
		}
		else if(container != null && container instanceof ContainerMusicSequencer)
		{
			TileEntityMusicSequencer musicSequencer = ((ContainerMusicSequencer)container).getTileEntity();
			musicSequencer.receiveButtonEvent(message.buttonId, player);
		}
		else if(container != null && container instanceof ContainerHairStyleKit)
		{
			PlayerHairStyleData props = PlayerHairStyleData.get(player);
			switch (message.buttonId) {
			case 0:
				props.toggleHair();
				break;
			}
			
		}
		return null;
	}

}
