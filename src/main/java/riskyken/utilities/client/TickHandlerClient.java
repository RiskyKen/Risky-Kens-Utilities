package riskyken.utilities.client;

import java.util.EnumSet;

import riskyken.utilities.common.UpdateCheck;
import riskyken.utilities.common.lib.LibModInfo;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.common.gameevent.TickEvent.Type;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatComponentText;

@SideOnly(Side.CLIENT)
public class TickHandlerClient {

	private boolean shownUpdateInfo = false;
	
	@SubscribeEvent
	public void onTick(TickEvent.PlayerTickEvent event) {
		if (event.side == Side.CLIENT) {
			if (event.type == Type.PLAYER) {
				if (event.phase == Phase.END) {
					onPlayerTickEndEvent();
				}
				//System.out.println("showing update message");
			}
		}
	}
	  
	public void onPlayerTickEndEvent() {
		if (shownUpdateInfo) { return; }
		
		EntityClientPlayerMP player = Minecraft.getMinecraft().thePlayer;
		
		if (UpdateCheck.showUpdateInfo) {
			shownUpdateInfo = true;
			player.addChatMessage(new ChatComponentText(LibModInfo.NAME + " - New version is " + UpdateCheck.remoteVersion));
			player.addChatMessage(new ChatComponentText(LibModInfo.NAME + " - " + UpdateCheck.remoteVersionInfo));
		}
	}
}
