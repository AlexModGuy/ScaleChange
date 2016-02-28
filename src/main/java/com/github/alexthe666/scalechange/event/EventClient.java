package com.github.alexthe666.scalechange.event;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import net.ilexiconn.llibrary.common.entity.EntityHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityDiggingFX;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import cpw.mods.fml.common.ObfuscationReflectionHelper;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.ReflectionHelper;

public class EventClient {

	public static final String[] thirdPersonDistanceNames = new String[]{"thirdPersonDistance", "field_78490_B"};
	public static final String[] cameraZoomNames = new String[]{"cameraZoom", "field_78503_V"};

	public static float NAME_TAG_RANGE = 64.0f;
	public static float NAME_TAG_RANGE_SNEAK = 32.0f;

	@SubscribeEvent
	public void onPlayerRenderPre(RenderPlayerEvent.Pre event)
	{
		float thirdPersonDistance = EntityHelper.getScale(event.entityPlayer) < 4 ? 4 * EntityHelper.getScale(event.entityPlayer) : 16;
		float cameraZoom = 1F;
		//EntityHelper.setScale(Minecraft.getMinecraft().renderViewEntity, 0.125F);
		if(event.entityPlayer == Minecraft.getMinecraft().thePlayer){
			EntityRenderer renderer = Minecraft.getMinecraft().entityRenderer;
			Field thirdPersonDistanceField = ReflectionHelper.findField(EntityRenderer.class, ObfuscationReflectionHelper.remapFieldNames(EntityRenderer.class.getName(), thirdPersonDistanceNames));
			try
			{
				Field modifier = Field.class.getDeclaredField("modifiers");
				modifier.setAccessible(true);
				modifier.setInt(thirdPersonDistanceField, thirdPersonDistanceField.getModifiers() & ~Modifier.FINAL);
				thirdPersonDistanceField.set(renderer, thirdPersonDistance);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			Field cameraZoomField = ReflectionHelper.findField(EntityRenderer.class, ObfuscationReflectionHelper.remapFieldNames(EntityRenderer.class.getName(), cameraZoomNames));
			try
			{
				Field modifier = Field.class.getDeclaredField("modifiers");
				modifier.setAccessible(true);
				modifier.setInt(cameraZoomField, cameraZoomField.getModifiers() & ~Modifier.FINAL);
				cameraZoomField.set(renderer, cameraZoom);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}

		GL11.glPushMatrix();
		int bright = getBrightnessForRender(event.entityPlayer);
		int brightX = bright % 65536;
		int brightY = bright / 65536;
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, brightX, brightY);
		if(event.entityPlayer.isSneaking()){
			GL11.glTranslatef(0, 0.125F, 0);
			GL11.glTranslatef(0, -0.220F * EntityHelper.getScale(event.entityPlayer), 0);

		}
	}

	@SubscribeEvent
	public void onPlayerRenderPost(RenderPlayerEvent.Post event)
	{
		GL11.glPopMatrix();
	}

	public int getBrightnessForRender(Entity entity)
	{
		int i = MathHelper.floor_double(entity.posX);
		int j = MathHelper.floor_double(entity.posZ);

		if (entity.worldObj.blockExists(i, 0, j))
		{
			double d0 = (entity.boundingBox.maxY - entity.boundingBox.minY) * 0.66D;
			int k = MathHelper.floor_double(entity.posY - 1.62F + d0);
			return entity.worldObj.getLightBrightnessForSkyBlocks(i, k, j, 0);
		}
		else
		{
			return 0;
		}
	}

}
