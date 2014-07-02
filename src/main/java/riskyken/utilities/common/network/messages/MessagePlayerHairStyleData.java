package riskyken.utilities.common.network.messages;

import org.apache.logging.log4j.Level;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import riskyken.utilities.RiskyKensUtilities;
import riskyken.utilities.common.hair.PlayerHairStyleData;
import riskyken.utilities.utils.ModLogger;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;

public class MessagePlayerHairStyleData implements IMessage, IMessageHandler<MessagePlayerHairStyleData, IMessage> {

	private int hairStyleUnlockFlags;
	private int hairAccessoriesUnlockFlags;
	private int hairAccessoryColourUnlockFlags;

	public MessagePlayerHairStyleData() {}
	
	public MessagePlayerHairStyleData(EntityPlayer player) {
		PlayerHairStyleData props = PlayerHairStyleData.get(player);
		this.hairStyleUnlockFlags = props.getHairStyleUnlockFlags();
		this.hairAccessoriesUnlockFlags = props.getHairAccessoriesUnlockFlags();
		this.hairAccessoryColourUnlockFlags = props.getHairAccessoryColourUnlockFlags();
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		this.hairStyleUnlockFlags = buf.readInt();
		this.hairAccessoriesUnlockFlags = buf.readInt();
		this.hairAccessoryColourUnlockFlags = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(this.hairStyleUnlockFlags);
		buf.writeInt(this.hairAccessoriesUnlockFlags);
		buf.writeInt(this.hairAccessoryColourUnlockFlags);
	}
	
	@Override
	public IMessage onMessage(MessagePlayerHairStyleData message, MessageContext ctx) {
		if (ctx.side == Side.SERVER) {
			ModLogger.log(Level.WARN, "Error got MessagePlayerHairStyleData packet on the wrong side.");
			return null;
		}
		RiskyKensUtilities.proxy.setHairStyleData(message.hairStyleUnlockFlags, message.hairAccessoriesUnlockFlags, message.hairAccessoryColourUnlockFlags);
		return null;
	}
}
