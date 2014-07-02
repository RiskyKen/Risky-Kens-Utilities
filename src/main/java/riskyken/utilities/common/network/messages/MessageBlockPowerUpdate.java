package riskyken.utilities.common.network.messages;

import org.apache.logging.log4j.Level;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import riskyken.utilities.common.tileentities.TileEntityUtilitiesBasePowered;
import riskyken.utilities.utils.ModLogger;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;

public class MessageBlockPowerUpdate implements IMessage, IMessageHandler<MessageBlockPowerUpdate, IMessage>{

	int x;
	int y;
	int z;
	double powerLevel;
	
	public MessageBlockPowerUpdate() {}
	
	public MessageBlockPowerUpdate(TileEntityUtilitiesBasePowered te) {
		this.x = te.xCoord;
		this.y = te.yCoord;
		this.z = te.zCoord;
		this.powerLevel = te.powerLevel;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		this.x = buf.readInt();
		this.y = buf.readInt();
		this.z = buf.readInt();
		this.powerLevel = buf.readDouble();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(this.x);
		buf.writeInt(this.y);
		buf.writeInt(this.z);
		buf.writeDouble(this.powerLevel);
	}
	
	@Override
	public IMessage onMessage(MessageBlockPowerUpdate message, MessageContext ctx) {
		if (ctx.side == Side.SERVER) {
			ModLogger.log(Level.WARN, "Error got MessageBlockPowerUpdate packet on the wrong side.");
			return null;
		}
		
		EntityClientPlayerMP player = Minecraft.getMinecraft().thePlayer;
		World world = player.worldObj;
		
		TileEntity te = world.getTileEntity(message.x, message.y, message.z);
		if (te != null & te instanceof TileEntityUtilitiesBasePowered) {
			((TileEntityUtilitiesBasePowered)te).updatePowerLevel(message.powerLevel);
		}
		
		return null;
	}
}
