package com.wuppy.goblinsgiants.dimension.savannah;

import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.WorldChunkManagerHell;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;

import com.wuppy.goblinsgiants.worldgen.ModGeneration;

public class WorldProviderGoblinsGiantsSavannah extends WorldProvider
{
	public void registerWorldChunkManager()
	{
		this.worldChunkMgr = new WorldChunkManagerHell(ModGeneration.savannah, 0.1F);
		this.dimensionId = ModGeneration.dimension;
	}

	public String getSaveFolder()
	{
		return "DIM-G&G";
	}

	public String getWelcomeMessage()
	{
		return "Entering The Savannah";
	}

	public String getDepartMessage()
	{
		return "Leaving The Savannah";
	}

	public boolean canRespawnHere()
	{
		return true;
	}

	public IChunkProvider createChunkGenerator()
	{
		return new ChunkProviderSavannah(worldObj, worldObj.getSeed(), true);
	}

	public String getDimensionName()
	{
		return "Savannah";
	}

	/**
	* Gets the hard-coded portal location to use when entering this dimension.
	*/
	public ChunkCoordinates getEntrancePortalLocation()
	{
		return null;
	}

	public boolean canDoLightning(Chunk chunk)
	{
		return true;
	}

	public boolean canDoRainSnowIce(Chunk chunk)
	{
		return false;
	}
}
