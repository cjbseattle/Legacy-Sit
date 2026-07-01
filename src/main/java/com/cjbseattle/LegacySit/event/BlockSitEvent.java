package com.cjbseattle.LegacySit.event;

import com.cjbseattle.LegacySit.entity.SeatEntity;
import com.cjbseattle.LegacySit.util.Reference;

import net.minecraft.block.BlockSlab;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.SPacketSetPassengers;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = Reference.MODID)
public class BlockSitEvent
{
	@SubscribeEvent
	public static void onInteractWithBlock(PlayerInteractEvent.RightClickBlock event)
	{
		EntityPlayer player = event.getEntityPlayer();

		if (player.getRidingEntity() != null)
			return;

		World worldIn = event.getWorld();
		BlockPos pos = event.getPos();
		Vec3d vec = new Vec3d(pos.getX() + 0.5d, pos.getY(), pos.getZ() + 0.5d);

		if (player.getDistanceSq(vec.x, vec.y, vec.z) > Reference.MAX_SIT_DISTANCE * Reference.MAX_SIT_DISTANCE)
			return;

		IBlockState state = worldIn.getBlockState(pos);
		ItemStack mainStack = player.getHeldItemMainhand();
		ItemStack offStack = player.getHeldItemOffhand();

		if (!mainStack.isEmpty() || !offStack.isEmpty())
			return;

		if (state.getBlock() instanceof BlockStairs || state.getBlock() instanceof BlockSlab)
		{
			// wtf does this do List<SeatStair> seats =
			// worldIn.getEntitiesWithinAABB(SeatStair.class, new AxisAlignedBB(pos,
			// pos.add(1,1,1)));
			SeatEntity seat = new SeatEntity(worldIn, pos);

			if (!worldIn.isRemote)
			{
				worldIn.spawnEntity(seat);
				boolean mounted = player.startRiding(seat, true);
				
				System.out.println(seat.isAddedToWorld());
				System.out.println(seat.getEntityId());
				
				System.out.println(worldIn.getEntityByID(seat.getEntityId()) == seat);
			}
		}
	}
}
