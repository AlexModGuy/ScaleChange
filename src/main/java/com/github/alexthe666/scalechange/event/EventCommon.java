package com.github.alexthe666.scalechange.event;

import net.ilexiconn.llibrary.common.entity.EntityHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.living.LivingEvent.LivingJumpEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;

import com.github.alexthe666.scalechange.ScaleChange;
import com.github.alexthe666.scalechange.entity.EntityScaleChangePlayer;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.ReflectionHelper;

public class EventCommon {

	@SubscribeEvent
	public void onClonePlayer(PlayerEvent.Clone event)
	{
		NBTTagCompound compound = new NBTTagCompound();
		EntityScaleChangePlayer.get(event.original).saveNBTData(compound);
		EntityScaleChangePlayer.get(event.entityPlayer).loadNBTData(compound);

	}

	@SubscribeEvent
	public void onEntityUpdate(LivingUpdateEvent event)
	{
		ScaleChange.setupScale(event.entityLiving, 0.125F);
	}

	@SubscribeEvent
	public void onEntityJump(LivingJumpEvent event)
	{
		boolean b = false;
		if(event.entityLiving.isSprinting()){
			b = true;			
		}
		if(event.entityLiving.isSneaking()){
			b = true;
		}
		if(!b){
			event.entityLiving.motionY -= 1 * 0.125D;
			b = false;
		}
		//|| !event.entityLiving.isSneaking())
		//	
	}

	@SubscribeEvent
	public void onEntityConstructing(EntityConstructing event)
	{

		if (event.entity instanceof EntityPlayer && EntityScaleChangePlayer.get((EntityPlayer) event.entity) == null){
			EntityScaleChangePlayer.register((EntityPlayer) event.entity);
		}
		if (event.entity instanceof EntityPlayer && event.entity.getExtendedProperties(EntityScaleChangePlayer.EXT_PROP_NAME) == null){
			event.entity.registerExtendedProperties(EntityScaleChangePlayer.EXT_PROP_NAME, new EntityScaleChangePlayer((EntityPlayer) event.entity));
		}
	}

}
