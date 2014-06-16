package riskyken.utilities.common.network;

import riskyken.utilities.common.lib.LibModInfo;
import riskyken.utilities.common.network.messages.MessageBlockPowerUpdate;
import riskyken.utilities.common.network.messages.MessageButton;
import riskyken.utilities.common.network.messages.MessageDimensionSound;
import riskyken.utilities.common.network.messages.MessagePlayerHairStyleData;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;

public class PacketHandler {
	
	public static final SimpleNetworkWrapper networkWrapper = NetworkRegistry.INSTANCE.newSimpleChannel(LibModInfo.CHANNEL);
	
	public static void init() {
		networkWrapper.registerMessage(MessageButton.class, MessageButton.class, 0, Side.SERVER);
		networkWrapper.registerMessage(MessageDimensionSound.class, MessageDimensionSound.class, 1, Side.CLIENT);
		networkWrapper.registerMessage(MessageBlockPowerUpdate.class, MessageBlockPowerUpdate.class, 2, Side.CLIENT);
		networkWrapper.registerMessage(MessagePlayerHairStyleData.class, MessagePlayerHairStyleData.class, 3, Side.CLIENT);
	}
}
