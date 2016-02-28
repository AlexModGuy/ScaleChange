package com.github.alexthe666.scalechange;

import net.minecraftforge.common.MinecraftForge;

import com.github.alexthe666.scalechange.event.EventClient;

public class ClientProxy extends CommonProxy{

	public void render(){
		MinecraftForge.EVENT_BUS.register(new EventClient());
		
	}
}
