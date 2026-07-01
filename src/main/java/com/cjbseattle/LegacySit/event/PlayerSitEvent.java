package com.cjbseattle.LegacySit.event;

import com.cjbseattle.LegacySit.util.Reference;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = Reference.MODID)
public class PlayerSitEvent
{
	@SubscribeEvent
	public static void onInteractWithEntity(PlayerInteractEvent.EntityInteract event)
	{
		EntityPlayer player = event.getEntityPlayer();

		if (player.getRidingEntity() != null)
			return;

		Entity target = event.getTarget();

		if (!(target instanceof EntityPlayer) || target == player || player.isRidingOrBeingRiddenBy(target))
			return;

		World worldIn = event.getWorld();
		BlockPos pos = event.getPos();
		Vec3d vec = target.getPositionVector();

		if (player.getDistanceSq(target) > Reference.MAX_SIT_DISTANCE * Reference.MAX_SIT_DISTANCE)
			return;

		IBlockState state = worldIn.getBlockState(pos);
		ItemStack mainStack = player.getHeldItemMainhand();
		ItemStack offStack = player.getHeldItemOffhand();

		if (!mainStack.isEmpty() || !offStack.isEmpty())
			return;

		if (!player.world.isRemote)
			player.startRiding(target, true);
	}
}
