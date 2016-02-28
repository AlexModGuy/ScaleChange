package com.github.alexthe666.scalechange.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

public class EntityScaleChangePlayer implements IExtendedEntityProperties
{

	public final static String EXT_PROP_NAME = "JARMPlayer";
	private final EntityPlayer player;
	private float size;


	public EntityScaleChangePlayer(EntityPlayer player)
	{
		this.player = player;
		size = 1.0F;
	}

	public static final void register(EntityPlayer player)
	{
		player.registerExtendedProperties(EntityScaleChangePlayer.EXT_PROP_NAME, new EntityScaleChangePlayer(player));
	}

	public static final EntityScaleChangePlayer get(EntityPlayer player)
	{
		return (EntityScaleChangePlayer) player.getExtendedProperties(EXT_PROP_NAME);
	}

	@Override
	public void saveNBTData(NBTTagCompound compound)
	{
		NBTTagCompound properties = new NBTTagCompound();
		properties.setFloat("Size", this.size);
		compound.setTag(EXT_PROP_NAME, properties);
	}

	@Override
	public void loadNBTData(NBTTagCompound compound)
	{
		NBTTagCompound properties = (NBTTagCompound) compound.getTag(EXT_PROP_NAME);
		this.size = properties.getFloat("Size");
		compound.setTag(EXT_PROP_NAME, properties);
	}


	@Override
	public void init(Entity entity, World world){}

	public void setSizeModifier(float i){
		this.size = i;
	}
	
	public float getSizeModifier() {
		return this.size;
	}
	
	
}