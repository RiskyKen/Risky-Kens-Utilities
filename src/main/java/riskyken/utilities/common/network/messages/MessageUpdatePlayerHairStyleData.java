package riskyken.utilities.common.network.messages;

import riskyken.utilities.common.hair.PlayerHairStyleData;
import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class MessageUpdatePlayerHairStyleData implements IMessage, IMessageHandler<MessageUpdatePlayerHairStyleData, IMessage> {

	int hairStyleId;
	int hairColour;
	
	public MessageUpdatePlayerHairStyleData() {}
	
	public MessageUpdatePlayerHairStyleData(int hairStyleId, int hairColour) {
		this.hairStyleId = hairStyleId;
		this.hairColour = hairColour;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.hairStyleId = buf.readInt();
		this.hairColour = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(this.hairStyleId);
		buf.writeInt(this.hairColour);
	}
	
	@Override
	public IMessage onMessage(MessageUpdatePlayerHairStyleData message, MessageContext ctx) {
		PlayerHairStyleData props = PlayerHairStyleData.get(ctx.getServerHandler().playerEntity);
		//props.setHairType((byte) hairStyleId);
		//System.out.println("");
		props.setHairColour(message.hairColour);
		return null;
	}

}
