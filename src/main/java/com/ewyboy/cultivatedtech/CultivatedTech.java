package com.ewyboy.cultivatedtech;

import com.ewyboy.cultivatedtech.proxy.CommonProxy;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import static com.ewyboy.cultivatedtech.common.utility.Reference.Info.*;
import static com.ewyboy.cultivatedtech.common.utility.Reference.Paths.*;

@Mod(modid = MOD_ID, name = NAME, version = VERSION, dependencies = DEPENDENCIES)
public class CultivatedTech {

    public CultivatedTech() {
        FluidRegistry.enableUniversalBucket();
    }

    @Mod.Instance(MOD_ID)
    public static CultivatedTech INSTANCE;

    @SidedProxy(clientSide = CLIENT_PROXY, serverSide = COMMON_PROXY)
    public static CommonProxy proxy;

    static {FluidRegistry.enableUniversalBucket();}

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        proxy.preInit(event);
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
    }
}
