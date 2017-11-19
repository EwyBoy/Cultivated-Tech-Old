package com.ewyboy.cultivatedtech.common.blocks;

import com.ewyboy.bibliotheca.common.interfaces.IBlockRenderer;
import com.ewyboy.cultivatedtech.common.loaders.CreativeTabLoader;
import net.minecraft.block.BlockHay;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.DefaultStateMapper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Created by EwyBoy
 */
public class BlockHemp extends BlockHay implements IBlockRenderer {

    public BlockHemp() {
        super();
        setCreativeTab(CreativeTabLoader.tabCultivatedTech);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockRenderer() {
        ModelLoader.setCustomStateMapper(this, new DefaultStateMapper() {
            @Override
            @SideOnly(Side.CLIENT)
            protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
                return new ModelResourceLocation(getRegistryName(), getPropertyString(state.getProperties()));
            }
        });
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockItemRenderer() {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), new ItemStack(this).getMetadata(), new ModelResourceLocation(getRegistryName(), "inventory"));
    }

    @Override
    public int[] modelMetas() {
        return new int[0];
    }
}
