package com.ewyboy.cultivatedtech.proxy;

import com.ewyboy.bibliotheca.common.helpers.ParticleHelper;
import com.ewyboy.bibliotheca.common.loaders.BlockLoader;
import com.ewyboy.cultivatedtech.client.ParticleEffectSpray;
import com.ewyboy.cultivatedtech.common.fluids.BaseFluidBlock;
import com.ewyboy.cultivatedtech.common.utility.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Created by EwyBoy
 **/
public class ClientProxy extends CommonProxy {

    @Override
    public Side getSide() {
        return Side.CLIENT;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);
        initFluidModels();
    }

    @Override
    public void init(FMLInitializationEvent event) {
        super.init(event);
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {
        super.postInit(event);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void spawnLiquidSpray(World world, FluidStack fluid, double x, double y, double z, float scale, float gravity, Vec3d velocity) {
        ParticleHelper.spawnParticle(new ParticleEffectSpray(world, fluid, x, y, z, scale, gravity, velocity));
    }

    private void initFluidModels() {
        BlockLoader.BLOCKS.values().stream().filter(block -> block instanceof BaseFluidBlock).forEachOrdered(block -> registerFluidBlockRendering(block, block.getRegistryName().toString().replace("cultivatedtech:block", "")));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerFluidBlockRendering(Block block, String name) {
        final ModelResourceLocation fluidLocation = new ModelResourceLocation(Reference.Info.MOD_ID + ":" + "fluids", name);
        ModelLoader.setCustomStateMapper(block, new StateMapperBase() {
            @Override
            protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
                return fluidLocation;
            }
        });
    }
}
