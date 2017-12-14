package com.ewyboy.cultivatedtech.client;

import com.ewyboy.bibliotheca.common.loaders.BlockLoader;
import com.ewyboy.cultivatedtech.common.fluids.BaseFluidBlock;
import com.ewyboy.cultivatedtech.common.utility.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Created by EwyBoy
 */
public class FluidModelLoader {

    private void initFluidModels() {
        BlockLoader.BLOCKS.values().stream().filter(block -> block instanceof BaseFluidBlock).forEachOrdered(block -> registerFluidBlockRendering(block, block.getRegistryName().toString().replace("cultivatedtech:", "")));
    }

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
