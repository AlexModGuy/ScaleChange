package com.github.alexthe666.scalechange;

import net.ilexiconn.llibrary.common.entity.EntityHelper;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;

import com.github.alexthe666.scalechange.event.EventCommon;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.relauncher.ReflectionHelper;

@Mod(modid = ScaleChange.MODID, version = ScaleChange.VERSION)
public class ScaleChange
{
    public static final String MODID = "scalechange";
    public static final String VERSION = "1.0";
	@SidedProxy(clientSide = "com.github.alexthe666.scalechange.ClientProxy", serverSide = "com.github.alexthe666.scalechange.CommonProxy")
	public static CommonProxy proxy;
	public static ScaleChange instance;
	public static CreativeTabs tab;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
    	
    }
    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
    	MinecraftForge.EVENT_BUS.register(new EventCommon());
    	proxy.render();
    }
    
    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
    
    }
    
    public static void setupScale(EntityLivingBase entity, float scale){
    	EntityHelper.setScale(entity, scale);
    	entity.stepHeight = 0.5F * EntityHelper.getScale(entity);
    	entity.entityCollisionReduction = 1 - EntityHelper.getScale(entity);
    	entity.setAIMoveSpeed(0.1F * EntityHelper.getScale(entity));
    	if(entity instanceof EntityPlayer){
    		EntityPlayer player = (EntityPlayer)entity;
    	}
    	
    }
}
