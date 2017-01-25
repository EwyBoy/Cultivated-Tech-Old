package com.ewyboy.cultivatedtech.common.blocks.crops;

import com.ewyboy.cultivatedtech.common.utility.interfaces.IBlockRenderer;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.IGrowable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;
import java.util.Random;

/**
 * Created by EwyBoy
 **/
public class BlockBaseCrop extends BlockCrops implements IBlockRenderer, IGrowable {

    public BlockBaseCrop() {}

    @Override
    protected Item getSeed() {
        return super.getSeed();
    }

    @Override
    protected Item getCrop() {
        return super.getCrop();
    }

    @Override
    public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state) {
        return super.canUseBonemeal(worldIn, rand, pos, state);
    }

    @Override
    public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        return super.getDrops(world, pos, state, fortune);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerBlockRenderer() {}

    @SideOnly(Side.CLIENT)
    @Override
    public void registerBlockItemRenderer() {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), new ItemStack(this).getMetadata(), new ModelResourceLocation(getRegistryName(), "inventory"));
    }

    @Override
    public int[] modelMetas() {
        return new int[0];
    }
}
