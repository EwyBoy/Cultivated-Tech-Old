package com.ewyboy.cultivatedtech;

import com.ewyboy.cultivatedtech.common.compatibilities.CompatibilityHandler;
import com.ewyboy.cultivatedtech.common.loaders.*;
import com.ewyboy.cultivatedtech.proxy.CommonProxy;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import static com.ewyboy.cultivatedtech.common.utility.Reference.Info.*;
import static com.ewyboy.cultivatedtech.common.utility.Reference.Paths.CLIENT_PROXY;
import static com.ewyboy.cultivatedtech.common.utility.Reference.Paths.COMMON_PROXY;

@Mod(modid = MODID, name = NAME, version = BUILD_VERSION, acceptedMinecraftVersions = MINECRAFT_VERSION)
public class CultivatedTech {

    public CultivatedTech() {
        FluidRegistry.enableUniversalBucket();
    }

    @Mod.Instance(MODID)
    public static CultivatedTech INSTANCE;

    @SidedProxy(clientSide = CLIENT_PROXY, serverSide = COMMON_PROXY)
    public static CommonProxy proxy;

    static {FluidRegistry.enableUniversalBucket();}

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        CompatibilityHandler.registerWaila();
        ConfigLoader.registerConfig(event.getSuggestedConfigurationFile());
        FluidLoader.init();
        BlockLoader.init();
        ItemLoader.init();
        RecipeLoader.registerRecipes();
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
