package com.cjbseattle.LegacySit.entity;

import java.util.List;

import net.minecraft.block.BlockSlab;
import net.minecraft.block.BlockStairs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SeatEntity extends Entity
{
	public SeatEntity(World worldIn, BlockPos pos)
	{
		this(worldIn);
		setPosition(pos.getX() + 0.5d, pos.getY() + 0.23d, pos.getZ() + 0.5d);
	}

	public SeatEntity(World worldIn)
	{
		super(worldIn);
		setSize(0.01f, 0.01f);
	}

	@Override
	public void onUpdate()
	{
		super.onUpdate();

		BlockPos pos = getPosition();
		if (!(getEntityWorld().getBlockState(pos).getBlock() instanceof BlockStairs)
				&& !(getEntityWorld().getBlockState(pos).getBlock() instanceof BlockSlab))
		{
			setDead();
			return;
		}

		List<Entity> passengers = getPassengers();

		if (getPassengers().isEmpty())
		{
			setDead();
		}

		for (Entity passenger : passengers)
		{
			if (passenger.isSneaking())
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

	@Override
	public boolean canBeCollidedWith()
	{
		return false;
	}

	@Override
	public boolean canBePushed()
	{
		return false;
	}
}