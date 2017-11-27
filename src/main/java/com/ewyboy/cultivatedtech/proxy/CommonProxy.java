package com.ewyboy.cultivatedtech.proxy;

import com.ewyboy.bibliotheca.common.compatibilities.CompatibilityHandler;
import com.ewyboy.bibliotheca.common.loaders.BlockLoader;
import com.ewyboy.bibliotheca.common.loaders.ItemLoader;
import com.ewyboy.bibliotheca.common.loaders.TileEntityLoader;
import com.ewyboy.cultivatedtech.common.loaders.ConfigLoader;
import com.ewyboy.cultivatedtech.common.loaders.CreativeTabLoader;
import com.ewyboy.cultivatedtech.common.loaders.RecipeLoader;
import com.ewyboy.cultivatedtech.common.loaders.SeedLoader;
import com.ewyboy.cultivatedtech.common.register.Register;
import com.ewyboy.cultivatedtech.common.world.TheWorldGenerator;
import net.minecraft.block.Block;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.FMLEventChannel;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;

import static com.ewyboy.cultivatedtech.common.utility.Reference.Info.MOD_ID;

/**
 * Created by EwyBoy
 **/
public class CommonProxy {

    public static FMLEventChannel packetHandler;

    public Side getSide(){return Side.SERVER;}

    public void preInit(FMLPreInitializationEvent event) {
        CompatibilityHandler.registerWaila();
        ConfigLoader.registerConfig(event.getSuggestedConfigurationFile());
        CreativeTabLoader.initVanillaStuffToTab();
        BlockLoader.init(MOD_ID, Register.Blocks.class);
        ItemLoader.init(MOD_ID, Register.Items.class);
        TileEntityLoader.init(Register.Tiles.class);
        RecipeLoader.init();
        SeedLoader.registerSeeds();
    }

    public void init(FMLInitializationEvent event) {
        GameRegistry.registerWorldGenerator(new TheWorldGenerator(),10);
    }

    public void postInit(FMLPostInitializationEvent event) {}

    public void spawnLiquidSpray(World worldObj, FluidStack fluid, double x, double y, double z, float scale, float gravity, Vec3d velocity) {}

    public void registerFluidBlockRendering(Block block, String name) {}
}
