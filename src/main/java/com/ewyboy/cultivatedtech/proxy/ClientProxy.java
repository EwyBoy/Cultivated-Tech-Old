package com.ewyboy.cultivatedtech.proxy;

import com.ewyboy.cultivatedtech.common.blocks.fluid.CTFluidBlock;
import com.ewyboy.cultivatedtech.common.items.ItemBase;
import com.ewyboy.cultivatedtech.common.loaders.BlockLoader;
import com.ewyboy.cultivatedtech.common.loaders.ItemLoader;
import com.ewyboy.cultivatedtech.common.utility.Reference;
import com.ewyboy.cultivatedtech.common.utility.interfaces.IBlockRenderer;
import com.ewyboy.cultivatedtech.common.utility.interfaces.IItemRenderer;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;

/**
 * Created by EwyBoy
 **/
public class ClientProxy extends CommonProxy {

    @Override
    public Side getSide() {
        return Side.CLIENT;
    }

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);
        initFluidModels();
        initBlockModels();
        initItemModels();
    }

    @Override
    public void init(FMLInitializationEvent event) {
        super.init(event);
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {
        super.postInit(event);
    }

    private void initItemModels() {
        ItemLoader.ITEMS.values().stream().filter(item -> item instanceof IItemRenderer).forEachOrdered(item -> {
            for (int i : ((IItemRenderer) item).modelMetas()) {
                ModelBakery.registerItemVariants(item, new ResourceLocation(Reference.Info.MODID + ":" + ((ItemBase) item).itemName(i)));
                ModelLoader.setCustomModelResourceLocation(item, i, new ModelResourceLocation(Reference.Info.MODID + ":" + ((ItemBase) item).itemName(i), "inventory"));
            }
        });
    }

    private void initBlockModels() {
        BlockLoader.BLOCKS.values().stream().filter(block -> block instanceof IBlockRenderer).forEachOrdered(block -> {
            for (int i : ((IBlockRenderer) block).modelMetas()) {
                ModelBakery.registerItemVariants(Item.getItemFromBlock(block), block.getRegistryName());
                ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), i, new ModelResourceLocation(block.getRegistryName(), "inventory"));
            }
        });
    }

    private void initFluidModels() {
        BlockLoader.BLOCKS.values().stream().filter(block -> block instanceof CTFluidBlock).forEachOrdered(block -> registerFluidBlockRendering(block, block.getRegistryName().toString().replace("cultivatedtech:", "")));
    }

    @Override
    public void registerFluidBlockRendering(Block block, String name) {
        final ModelResourceLocation fluidLocation = new ModelResourceLocation(Reference.Info.MODID + ":" + "fluids", name);
        ModelLoader.setCustomStateMapper(block, new StateMapperBase() {
            @Override
            protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
                return fluidLocation;
            }
        });
    }

}
