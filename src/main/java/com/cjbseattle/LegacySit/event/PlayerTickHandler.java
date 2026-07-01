package com.cjbseattle.LegacySit.event;

import com.cjbseattle.LegacySit.entity.SeatEntity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

@Mod.EventBusSubscriber
public class PlayerTickHandler
{
	@SubscribeEvent
	public static void onPlayerTick(TickEvent.PlayerTickEvent event)
	{
		EntityPlayer player = event.player;
		
		// dismount sneaking players
		for (Entity passenger : player.getPassengers())
		{
			if (player.world.isRemote)
			{
				System.out.println("passenger riding: " + passenger.getRidingEntity());
				System.out.println("driver riding: " + player.getRidingEntity());
			}

			if (!(passenger instanceof EntityPlayer) || passenger.world.isRemote)
				continue;

			if (passenger.isSneaking())
				passenger.dismountRidingEntity();

			passenger.setPosition(player.posX, player.posY + 1.15d, player.posZ);
		}
	}
}