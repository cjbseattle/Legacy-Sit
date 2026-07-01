package com.cjbseattle.LegacySit.init;

import com.cjbseattle.LegacySit.LegacySit;
import com.cjbseattle.LegacySit.entity.SeatEntity;
import com.cjbseattle.LegacySit.util.Reference;

import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class EntityInit
{
	public static void registerEntities()
	{
		registerEntity("Seat", SeatEntity.class, Reference.SEAT_ENTITY_ID, 25, 16711935, 0);
	}
	
	private static void registerEntity(String name, Class<? extends Entity> entity, int id, int range, int color1, int color2)
	{
		EntityRegistry.registerModEntity(new ResourceLocation(Reference.MODID + ":" + name), entity, name, id, LegacySit.instace, range, 1, true, color1, color2);
	}
	
	private static void registerNonMobEntity()
	{
		
	}
}
