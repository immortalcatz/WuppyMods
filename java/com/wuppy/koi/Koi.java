package com.wuppy.koi;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;

@Mod(modid = Koi.modid, name = "Koi", version = "1.11.3")
public class Koi
{
	public static final String modid = "wuppy29_koi";
	
	public static final int VERSION = 4;
	public static String updates = "";
	public static boolean outdated = false;
	
	@SidedProxy(clientSide = "com.wuppy.koi.ClientProxyKoi", serverSide = "com.wuppy.koi.CommonProxyKoi")
	public static CommonProxyKoi proxy;
	
	public static boolean checkForUpdates = true;
	
	static int startEntityId = 300;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		FMLCommonHandler.instance().bus().register(this);
		
		Configuration config = new Configuration(event.getSuggestedConfigurationFile());

		config.load();

		checkForUpdates = config.get(Configuration.CATEGORY_GENERAL, "checkForUpdates", true).getBoolean(true);

		config.save();

		if (checkForUpdates)
			UpdateChecker.checkForUpdates();
		
		EntityRegistry.registerModEntity(EntityKoi.class, "Koi", 44, this, 40, 1, true);
		
		for(int i = 0; i < BiomeGenBase.getBiomeGenArray().length; i++)
		{
			if(BiomeGenBase.getBiomeGenArray()[i] != null)
				EntityRegistry.addSpawn(EntityKoi.class, 10, 2, 4, EnumCreatureType.WATER_CREATURE, BiomeGenBase.getBiomeGenArray()[i]);
		}
		
		registerEntityEgg(EntityKoi.class, 0x4A8FFF, 0xFFDB4A);
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		proxy.registerRenderThings();
	}
	
	public static int getUniqueEntityId()
	{
		do
		{
			startEntityId++;
		} while (EntityList.getStringFromID(startEntityId) != null);
		
		return startEntityId;
	}

	public static void registerEntityEgg(Class<? extends Entity> entity, int primaryColor, int secondaryColor)
	{
		int id = getUniqueEntityId();
		EntityList.addMapping(EntityKoi.class, "Koi", id, primaryColor, secondaryColor);
	}
	
	@SubscribeEvent
	public void checkUpdate(PlayerEvent.PlayerLoggedInEvent event)
	{
		if(outdated)
		{
			event.player.addChatComponentMessage(new ChatComponentText("Koi is outdated."));
			event.player.addChatComponentMessage(new ChatComponentText("Changelog: "));
			event.player.addChatComponentMessage(new ChatComponentText(updates));
		}
	}
}