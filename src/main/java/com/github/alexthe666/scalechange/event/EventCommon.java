package com.github.alexthe666.scalechange.event;

import net.ilexiconn.llibrary.common.entity.EntityHelper;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.living.LivingEvent.LivingJumpEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;

import com.github.alexthe666.scalechange.entity.EntityScaleChangePlayer;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

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
		EntityHelper.setScale(event.entity, 0.125F);
		event.entityLiving.stepHeight = 0.5F * 0.125F;
		event.entityLiving.entityCollisionReduction = 1 - 0.125F;
		//event.entityLiving.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.movementSpeed).setBaseValue(0.10000000149011612D * 0.125D);
		if(event.entityLiving instanceof EntityPlayer){
			//		EntityHelper.setScale(event.entity, 1F);

		}
		else{

		}
		/*if(event.entityLiving.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.movementSpeed).getAttributeValue() != EntityHelper.getScale(event.entityLiving)){
			event.entityLiving.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.movementSpeed).setBaseValue((double)EntityHelper.getScale(event.entityLiving));
		}*/
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
