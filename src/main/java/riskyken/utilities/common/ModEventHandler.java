package riskyken.utilities.common;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingJumpEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerFlyableFallEvent;
import riskyken.utilities.common.hair.PlayerHairStyleData;
import riskyken.utilities.common.items.ModItems;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.common.gameevent.TickEvent.Type;
import cpw.mods.fml.relauncher.Side;

public class ModEventHandler {

	@SubscribeEvent
	public void onEntityConstructing(EntityConstructing event) {
		if (event.entity instanceof EntityPlayer && PlayerHairStyleData.get((EntityPlayer) event.entity) == null) {
			PlayerHairStyleData.register((EntityPlayer) event.entity);
		}
	}
	
	@SubscribeEvent
	public void onEntityJoinWorld(EntityJoinWorldEvent event) {
		if (!event.entity.worldObj.isRemote && event.entity instanceof EntityPlayer) {
			PlayerHairStyleData.get((EntityPlayer) event.entity).sync();
		}
	}
	
	/*
	@SubscribeEvent
	public void onLivingHurtEvent(LivingHurtEvent event) {
		if (event.entity instanceof EntityPlayer & event.source == DamageSource.fall) {
			EntityPlayer player = (EntityPlayer) event.entity;
			System.out.println(event.ammount);
			if (player.inventory.hasItem(ModItems.wings)) {
				event.setCanceled(true);
			}
		}
	}
	
	@SubscribeEvent
	public void onLivingUpdateEvent(LivingUpdateEvent event) {
		if (event.entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.entity;
			if (!player.onGround & player.inventory.hasItem(ModItems.wings)) {
				//player.motionY += 0.07D;
			}
		}
	}
	
	@SubscribeEvent
	public void onLivingJumpEvent(LivingJumpEvent event) {
		if (event.entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.entity;
			System.out.println("jump!");
			player.motionY += 0.5D;
			if (!player.onGround & player.inventory.hasItem(ModItems.wings)) {
				player.motionY += 200.07D;
			}
		}
	}
	
	@SubscribeEvent
	public void onLivingFallEventt(LivingFallEvent event) {
		if (event.entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.entity;
			event.distance = 0;

			if (!player.onGround & player.inventory.hasItem(ModItems.wings)) {
				player.motionY += 200.07D;
			}
		}
	}
	
	@SubscribeEvent
	public void onPlayerFlyableFallEvent(PlayerFlyableFallEvent event) {
		if (event.entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.entity;
			
			//System.out.println("jump!");
			//player.motionY += 0.5D;
			if (!player.onGround & player.inventory.hasItem(ModItems.wings)) {
				//player.motionY += 200.07D;
			}
		}
	}
	*/
}
