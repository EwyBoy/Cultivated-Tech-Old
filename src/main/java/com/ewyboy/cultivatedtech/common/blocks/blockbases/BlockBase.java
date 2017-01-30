package com.ewyboy.cultivatedtech.common.blocks.blockbases;

import com.ewyboy.cultivatedtech.common.loaders.CreativeTabLoader;
import com.ewyboy.cultivatedtech.common.utility.interfaces.IBlockRenderer;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
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
 **/
public class BlockBase extends Block implements IBlockRenderer {

    public BlockBase(Material material) {
        super(material);
        setCreativeTab(CreativeTabLoader.tabUnknown);
    }

    public int[] modelMetas() {
        return new int[0];
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockRenderer() {
        ModelLoader.setCustomStateMapper(this, new DefaultStateMapper() {
            @Override
            protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
                return new ModelResourceLocation(getRegistryName(), getPropertyString(state.getProperties()));
            }
        });
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerBlockItemRenderer() {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), new ItemStack(this).getMetadata(), new ModelResourceLocation(getRegistryName(), "inventory"));
    }
}
