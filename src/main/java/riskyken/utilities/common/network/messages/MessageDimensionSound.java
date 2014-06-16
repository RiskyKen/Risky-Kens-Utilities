package riskyken.utilities.common.network.messages;

import riskyken.utilities.RiskyKensUtilities;
import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class MessageDimensionSound implements IMessage, IMessageHandler<MessageDimensionSound, IMessage>{
	
	byte soundId;
	
	public MessageDimensionSound() {}
	
	public MessageDimensionSound(byte soundId) {
		this.soundId = soundId;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		this.soundId = buf.readByte();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeByte(this.soundId);
	}
	
	@Override
	public IMessage onMessage(MessageDimensionSound message, MessageContext ctx) {
		RiskyKensUtilities.proxy.playDimensionSound(message.soundId);
		return null;
	}
}
