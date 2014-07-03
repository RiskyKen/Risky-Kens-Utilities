package riskyken.utilities.common;

import java.util.EnumSet;

import riskyken.utilities.RiskyKensUtilities;
import riskyken.utilities.common.config.ConfigHandler;
import riskyken.utilities.common.lib.LibModInfo;
import riskyken.utilities.utils.ModLogger;
import cpw.mods.fml.client.event.ConfigChangedEvent;
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

public class ModFMLEventHandler {

	private boolean shownUpdateInfo = false;
	
	@SubscribeEvent
	public void onPlayerTick(TickEvent.PlayerTickEvent event) {
		if (event.side == Side.CLIENT) {
			if (event.type == Type.PLAYER) {
				if (event.phase == Phase.END) {
					RiskyKensUtilities.proxy.onPlayerTick(event.player);
					onPlayerTickEndEvent();
				}
				//System.out.println("showing update message");
			}
		}
	}
	  
	public void onPlayerTickEndEvent() {
		EntityClientPlayerMP player = Minecraft.getMinecraft().thePlayer;
		//RiskyKensUtilities.proxy.onPlayerTick(player);
		
		if (shownUpdateInfo) { return; }
		if (UpdateCheck.showUpdateInfo) {
			shownUpdateInfo = true;
			player.addChatMessage(new ChatComponentText(LibModInfo.NAME + " - New version is " + UpdateCheck.remoteVersion));
			player.addChatMessage(new ChatComponentText(LibModInfo.NAME + " - " + UpdateCheck.remoteVersionInfo));
		}
	}
	
	@SubscribeEvent
	public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent eventArgs) {
		if(eventArgs.modID.equals(LibModInfo.ID)) {
			ConfigHandler.loadConfigFile();
		}
	}
}
