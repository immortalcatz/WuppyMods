package com.wuppy.jellycubes;

import java.io.IOException;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(modid = JellyCube.modid, name = "Jelly Cubes", version = "1.12.5")
public class JellyCube
{
	public static final String modid = "wuppy29_jellycubes";
	
	public static final int VERSION = 2;
	public static String updates = "";
	public static boolean outdated = false;
	
	public static int JellyCubeID;
	
	public static Item jellycube;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		FMLCommonHandler.instance().bus().register(this);
		
		try
		{
			UpdateChecker.checkForUpdates();
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		//item registry
		jellycube = new ItemJellyFood(4, 6, false).setUnlocalizedName("jelly cube").setTextureName(modid + ":" + "jelly cube");
		GameRegistry.registerItem(jellycube, jellycube.getUnlocalizedName());
	}
	
	@EventHandler
	public void load(FMLInitializationEvent event)
	{
		GameRegistry.addShapelessRecipe(new ItemStack(jellycube, 1), new Object[] 
		{ 
			new ItemStack(Items.slime_ball), new ItemStack(Items.slime_ball) 
		});
	}
	
	@SubscribeEvent
	public void checkUpdate(PlayerEvent.PlayerLoggedInEvent event)
	{
		if(outdated)
		{
			event.player.addChatComponentMessage(new ChatComponentText("Jelly Cubes is outdated."));
			event.player.addChatComponentMessage(new ChatComponentText("Changelog: "));
			event.player.addChatComponentMessage(new ChatComponentText(updates));
		}
	}
}
