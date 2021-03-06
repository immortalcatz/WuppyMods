package com.wuppy.slimedungeons.generation;

import java.util.Random;

import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import cpw.mods.fml.common.IWorldGenerator;

public class SlimeGenerator implements IWorldGenerator 
{
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider)
	{
		switch (world.provider.dimensionId)
		{
			case 0: generateSurface(world, random, chunkX*16, chunkZ*16); //To make these values the same as ModLoader they need to be multiplied by 16
		}
	}

	public void generateSurface(World world, Random random, int blockX, int blockZ)
	{
		int Xcoord = blockX + random.nextInt(16);
		int Ycoord = random.nextInt(60);
		int Zcoord = blockZ + random.nextInt(16);
		(new WorldGenSlimeDungeon()).generate(world, random, Xcoord, Ycoord, Zcoord);
	}
}
