package com.github.alexthe666.scalechange.access;

import net.ilexiconn.llibrary.LLibrary;
import net.ilexiconn.llibrary.common.entity.EntityHelper;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraftforge.client.ForgeHooksClient;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.Project;

import cpw.mods.fml.relauncher.ReflectionHelper;

public class ScaleChangeClientHooks {

	public static void preRenderCallBack(EntityLivingBase entity){
		if(!(entity instanceof EntityPlayer)){
			float scale = EntityHelper.getScale(entity);
			GL11.glScalef(scale, scale, scale);
		}
	}

	public static void gluPerspectiveRender(){
		Project.gluPerspective(getFOVModifier(LLibrary.proxy.getPartialTicks(), true),
				(float)Minecraft.getMinecraft().displayWidth / (float)Minecraft.getMinecraft().displayHeight,
				EntityHelper.getScale(Minecraft.getMinecraft().thePlayer) * 0.05F,
				Minecraft.getMinecraft().gameSettings.renderDistanceChunks * 32);
	}

	public static void gluPerspectiveHand(){
		Project.gluPerspective(getFOVModifier(LLibrary.proxy.getPartialTicks(), false),
				(float)Minecraft.getMinecraft().displayWidth / (float)Minecraft.getMinecraft().displayHeight,
				EntityHelper.getScale(Minecraft.getMinecraft().thePlayer) * 0.05F,
				Minecraft.getMinecraft().gameSettings.renderDistanceChunks * 32);
	}

	private static float getFOVModifier(float partialTicks, boolean debugedViewDirection)
	{
		if (Minecraft.getMinecraft().entityRenderer.debugViewDirection > 0)
		{
			return 90.0F;
		}
		else
		{
			EntityLivingBase entityplayer = (EntityLivingBase)Minecraft.getMinecraft().renderViewEntity;
			float f1 = 70.0F;

			if (debugedViewDirection)
			{
				f1 = Minecraft.getMinecraft().gameSettings.fovSetting;
				f1 *= getFOVField(new String[]{"fovModifierHandPrev", "field_78506_S"}) + (getFOVField(new String[]{"fovModifierHand", "field_78507_R"}) - getFOVField(new String[]{"fovModifierHandPrev", "field_78506_S"})) * partialTicks;
			}

			if (entityplayer.getHealth() <= 0.0F)
			{
				float f2 = (float)entityplayer.deathTime + partialTicks;
				f1 /= (1.0F - 500.0F / (f2 + 500.0F)) * 2.0F + 1.0F;
			}

			Block block = ActiveRenderInfo.getBlockAtEntityViewpoint(Minecraft.getMinecraft().theWorld, entityplayer, partialTicks);

			if (block.getMaterial() == Material.water)
			{
				f1 = f1 * 60.0F / 70.0F;
			}

			return f1 + getFOVField(new String[]{"prevDebugCamFOV", "field_78494_N"}) + (getFOVField(new String[]{"debugCamFOV", "field_78493_M"}) - getFOVField(new String[]{"prevDebugCamFOV", "field_78494_N"})) * partialTicks;
		}
	}
	

    public static void orientCamera(){
        EntityLivingBase entitylivingbase = Minecraft.getMinecraft().renderViewEntity;
        float scale = EntityHelper.getScale(entitylivingbase);
        float partialTicks = LLibrary.proxy.getPartialTicks();
        float f1 = entitylivingbase.yOffset - 1.62F;
        double d0 = (entitylivingbase.prevPosX + (entitylivingbase.posX - entitylivingbase.prevPosX) * (double)partialTicks);
        double d1 = entitylivingbase.prevPosY + (entitylivingbase.posY - entitylivingbase.prevPosY) * (double)partialTicks - (double)f1;
        double d2 = (entitylivingbase.prevPosZ + (entitylivingbase.posZ - entitylivingbase.prevPosZ) * (double)partialTicks);
        GL11.glRotatef(getFOVField(new String[]{"prevCamRoll", "field_78505_P"}) + (getFOVField(new String[]{"camRoll", "field_78495_O"}) + getFOVField(new String[]{"prevCamRoll", "field_78505_P"})) * partialTicks, 0.0F, 0.0F, 1.0F);

        if (entitylivingbase.isPlayerSleeping())
        {
            f1 = (float)((double)f1 + 1.0D);
            GL11.glTranslatef(0.0F, 0.3F, 0.0F);

            if (!Minecraft.getMinecraft().gameSettings.debugCamEnable)
            {
                ForgeHooksClient.orientBedCamera(Minecraft.getMinecraft(), entitylivingbase);
                GL11.glRotatef(entitylivingbase.prevRotationYaw + (entitylivingbase.rotationYaw - entitylivingbase.prevRotationYaw) * partialTicks + 180.0F, 0.0F, -1.0F, 0.0F);
                GL11.glRotatef(entitylivingbase.prevRotationPitch + (entitylivingbase.rotationPitch - entitylivingbase.prevRotationPitch) * partialTicks, -1.0F, 0.0F, 0.0F);
            }
        }
        else if (Minecraft.getMinecraft().gameSettings.thirdPersonView > 0)
        {
            double d7 = (double)(getFOVField(new String[]{"thirdPersonDistanceTemp", "field_78491_C"}) + (getFOVField(new String[]{"thirdPersonDistance", "field_78490_B"}) - getFOVField(new String[]{"thirdPersonDistanceTemp", "field_78491_C"})) * partialTicks);
            float f2;
            float f6;

            if (Minecraft.getMinecraft().gameSettings.debugCamEnable)
            {
                f6 = getFOVField(new String[]{"prevDebugCamYaw", "field_78486_E"}) + (getFOVField(new String[]{"debugCamYaw", "field_78485_D"}) - getFOVField(new String[]{"prevDebugCamYaw", "field_78486_E"})) * partialTicks;
                f2 = getFOVField(new String[]{"prevDebugCamPitch", "field_78488_G"}) + (getFOVField(new String[]{"debugCamPitch", "field_78487_F"}) - getFOVField(new String[]{"prevDebugCamPitch", "field_78488_G"})) * partialTicks;
                GL11.glTranslatef(0.0F, 0.0F, (float)(-d7));
                GL11.glRotatef(f2, 1.0F, 0.0F, 0.0F);
                GL11.glRotatef(f6, 0.0F, 1.0F, 0.0F);
            }
            else
            {

                f6 = entitylivingbase.rotationYaw;
                f2 = entitylivingbase.rotationPitch;

                if (Minecraft.getMinecraft().gameSettings.thirdPersonView == 2)
                {
                    f2 += 180.0F;
                }

                double d3 = (double)(-MathHelper.sin(f6 / 180.0F * (float)Math.PI) * MathHelper.cos(f2 / 180.0F * (float)Math.PI)) * d7;
                double d4 = (double)(MathHelper.cos(f6 / 180.0F * (float)Math.PI) * MathHelper.cos(f2 / 180.0F * (float)Math.PI)) * d7;
                double d5 = (double)(-MathHelper.sin(f2 / 180.0F * (float)Math.PI)) * d7;

                for (int k = 0; k < 8; ++k)
                {
                    float f3 = (float)((k & 1) * 2 - 1);
                    float f4 = (float)((k >> 1 & 1) * 2 - 1);
                    float f5 = (float)((k >> 2 & 1) * 2 - 1);
                    f3 *= (0.01F * scale);
                    f4 *= (0.01F * scale);
                    f5 *= (0.01F * scale);
                    MovingObjectPosition movingobjectposition = Minecraft.getMinecraft().theWorld.rayTraceBlocks(Vec3.createVectorHelper(d0 + (double)f3, d1 + (double)f4, d2 + (double)f5), Vec3.createVectorHelper(d0 - d3 + (double)f3 + (double)f5, d1 - d5 + (double)f4, d2 - d4 + (double)f5));

                    if (movingobjectposition != null)
                    {
                        double d6 = movingobjectposition.hitVec.distanceTo(Vec3.createVectorHelper(d0, d1, d2));

                        if (d6 < d7)
                        {
                            d7 = d6;
                        }
                    }
                }

                if (Minecraft.getMinecraft().gameSettings.thirdPersonView == 2)
                {
                    GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
                }

                GL11.glRotatef(entitylivingbase.rotationPitch - f2, 1.0F, 0.0F, 0.0F);
                GL11.glRotatef(entitylivingbase.rotationYaw - f6, 0.0F, 1.0F, 0.0F);
                GL11.glTranslatef(0.0F, 0.0F, (float)(-d7));
                GL11.glRotatef(f6 - entitylivingbase.rotationYaw, 0.0F, 1.0F, 0.0F);
                GL11.glRotatef(f2 - entitylivingbase.rotationPitch, 1.0F, 0.0F, 0.0F);
            }
        }
        else
        {
            GL11.glTranslatef(0.0F, 0.0F, -0.1F);
        }

        if (!Minecraft.getMinecraft().gameSettings.debugCamEnable)
        {
            GL11.glRotatef(entitylivingbase.prevRotationPitch + (entitylivingbase.rotationPitch - entitylivingbase.prevRotationPitch) * partialTicks, 1.0F, 0.0F, 0.0F);
            GL11.glRotatef(entitylivingbase.prevRotationYaw + (entitylivingbase.rotationYaw - entitylivingbase.prevRotationYaw) * partialTicks + 180.0F, 0.0F, 1.0F, 0.0F);
        }

        GL11.glTranslatef(0.0F, f1, 0.0F);
        d0 = entitylivingbase.prevPosX + (entitylivingbase.posX - entitylivingbase.prevPosX) * (double)partialTicks;
        d1 = entitylivingbase.prevPosY + (entitylivingbase.posY - entitylivingbase.prevPosY) * (double)partialTicks - (double)f1;
        d2 = entitylivingbase.prevPosZ + (entitylivingbase.posZ - entitylivingbase.prevPosZ) * (double)partialTicks;
        try {
			ReflectionHelper.findField(EntityRenderer.class, new String[]{"cloudFog", "field_78500_U"}).setBoolean(Minecraft.getMinecraft().entityRenderer, Minecraft.getMinecraft().renderGlobal.hasCloudFog(d0, d1, d2, partialTicks));
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
    }
    
	public static float getFOVField(String[] names){
		float f = 0;
		try {
			f = ReflectionHelper.findField(EntityRenderer.class, names).getFloat(Minecraft.getMinecraft().entityRenderer);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return f;
	}
}
