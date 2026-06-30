package com.cjbseattle.LegacySit.events;

import java.util.List;

import com.cjbseattle.LegacySit.util.Reference;

import net.minecraft.block.BlockSlab;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
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
		
		if ((vec.x - player.posX) * (vec.x - player.posX) + (vec.y - player.posY) * (vec.y - player.posY)
				+ (vec.z - player.posZ) * (vec.z - player.posZ) > Reference.MAX_SIT_DISTANCE * Reference.MAX_SIT_DISTANCE)
			return;

		IBlockState state = worldIn.getBlockState(pos);
		ItemStack mainStack = player.getHeldItemMainhand();
		ItemStack offStack = player.getHeldItemOffhand();

		if (!mainStack.isEmpty() || !offStack.isEmpty())
			return;

		if (state.getBlock() instanceof BlockStairs || state.getBlock() instanceof BlockSlab)
		{
			//wtf does this do List<SeatStair> seats = worldIn.getEntitiesWithinAABB(SeatStair.class, new AxisAlignedBB(pos, pos.add(1,1,1)));

			SeatEntity seat = new SeatEntity(worldIn, pos);

			worldIn.spawnEntity(seat);
			player.startRiding(seat);
		}
	}

	public static class SeatEntity extends Entity
	{
		public SeatEntity(World worldIn, BlockPos pos)
		{
			this(worldIn);
			setPosition(pos.getX() + 0.5d, pos.getY() + 0.23d, pos.getZ() + 0.5d);
		}

		public SeatEntity(World worldIn)
		{
			super(worldIn);
			setSize(0.0f, 0.0f);
		}

		@Override
		public void onUpdate()
		{
			super.onUpdate();

			BlockPos pos = getPosition();
			if (!(getEntityWorld().getBlockState(pos).getBlock() instanceof BlockStairs) && !(getEntityWorld().getBlockState(pos).getBlock() instanceof BlockSlab))
			{
				setDead();
				return;
			}

			List<Entity> passengers = getPassengers();
			if (passengers.isEmpty())
			{
				setDead();
			}

			for (Entity entity : passengers)
			{
				if (entity.isSneaking())
				{
					setDead();
				}
			}
		}

		@Override
		protected void entityInit()
		{
		}

		@Override
		protected void readEntityFromNBT(NBTTagCompound compound)
		{
		}

		@Override
		protected void writeEntityToNBT(NBTTagCompound compound)
		{
		}
	}
}
