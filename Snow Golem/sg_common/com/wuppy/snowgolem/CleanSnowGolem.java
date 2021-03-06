package com.wuppy.snowgolem;

import net.minecraft.entity.monster.EntitySnowman;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import cpw.mods.fml.common.registry.EntityRegistry;

@Mod(modid = CleanSnowGolem.modid, name = "Clean Snow Golem", version = "1.6.1")
public class CleanSnowGolem
{
	public static final String modid = "wuppy29_snowgolem";

	public static final int VERSION = 2;
	public static String updates = "";
	public static boolean outdated = false;

	@SidedProxy(clientSide = "com.wuppy.snowgolem.ClientProxySG", serverSide = "com.wuppy.snowgolem.CommonProxySG")
	public static CommonProxySG proxy;
	
	public static boolean checkForUpdates = true;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		FMLCommonHandler.instance().bus().register(this);
		MinecraftForge.EVENT_BUS.register(this);

		Configuration config = new Configuration(event.getSuggestedConfigurationFile());

		config.load();

		checkForUpdates = config.get(Configuration.CATEGORY_GENERAL, "checkForUpdates", true).getBoolean(true);

		config.save();

		if (checkForUpdates)
			UpdateChecker.checkForUpdates();
	}

	@EventHandler
	public void load(FMLInitializationEvent event)
	{
		proxy.registerRenderThings();

		EntityRegistry.registerModEntity(EntityCleanSnowGolem.class, "snow golem", 1, this, 80, 3, true);
	}

	@SubscribeEvent
	public void checkUpdate(PlayerEvent.PlayerLoggedInEvent event)
	{
		if (outdated)
		{
			event.player.addChatComponentMessage(new ChatComponentText("Jelly Cubes is outdated."));
			event.player.addChatComponentMessage(new ChatComponentText("Changelog: "));
			event.player.addChatComponentMessage(new ChatComponentText(updates));
		}
	}

	@SubscribeEvent
	public void checkSnowGolems(EntityJoinWorldEvent event)
	{
		if (event.entity instanceof EntitySnowman && !(event.entity instanceof EntityCleanSnowGolem))
		{
			if (!event.world.isRemote)
			{
				EntityCleanSnowGolem golem = new EntityCleanSnowGolem(event.world);
				golem.setLocationAndAngles(event.entity.posX, event.entity.posY, event.entity.posZ, 0, 0);

				event.world.spawnEntityInWorld(golem);
				event.entity.setDead();
			}
		}
	}
}