package com.ewyboy.cultivatedtech.common.blocks;

import com.ewyboy.bibliotheca.common.helpers.ParticleHelper;
import com.ewyboy.bibliotheca.common.interfaces.IBlockRenderer;
import com.ewyboy.bibliotheca.common.loaders.BlockLoader;
import com.ewyboy.cultivatedtech.common.items.ItemEnderLily;
import com.ewyboy.cultivatedtech.common.loaders.CreativeTabLoader;
import com.ewyboy.cultivatedtech.common.utility.Logger;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLilyPad;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public class BlockEnderLily extends BlockLilyPad implements IBlockRenderer, BlockLoader.IHasCustomItem {

    public BlockEnderLily() {
        super();
        setCreativeTab(CreativeTabLoader.tabCultivatedTech);
        setTickRandomly(true);
    }

    @Override
    public Block setTickRandomly(boolean shouldTick) {
        return super.setTickRandomly(shouldTick);
    }

    @Override
    protected boolean canSustainBush(IBlockState state) {
        return state.getBlock() == Blocks.AIR || state.getMaterial() == Material.AIR;
    }

    @Override
    public boolean canBlockStay(World world, BlockPos pos, IBlockState state) {
        if (pos.getY() >= 0 && pos.getY() < 256) {
            IBlockState iState = world.getBlockState(pos.down());
            Material material = iState.getMaterial();
            return material == Material.AIR;
        } else {
            return false;
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        ParticleHelper.spawnParticle(worldIn, pos.getX(), pos.getY(), pos.getZ(), EnumParticleTypes.PORTAL, 0,0,0);
    }

    @Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random random) {
        if (world.provider.getDimension() == 1) {
            BlockPos targetPos = new BlockPos(pos.getX() + random.nextInt(3) - 1, pos.getY() + random.nextInt(3) - 1, pos.getZ() + random.nextInt(3) - 1);
            if (canBlockStay(world, targetPos, state)) {
                world.destroyBlock(pos, false);
                world.setBlockState(targetPos, this.getDefaultState(), 11);
            }
        } else {
            this.setTickRandomly(false);
        }
    }

    @Override
    public int[] modelMetas() {
        return new int[0];
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockRenderer() {

    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockItemRenderer() {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), new ItemStack(this).getMetadata(), new ModelResourceLocation(getRegistryName(), "inventory"));
    }

    @Override
    public ItemBlock getItemBlock() {
        return new ItemEnderLily(this);
    }
}
