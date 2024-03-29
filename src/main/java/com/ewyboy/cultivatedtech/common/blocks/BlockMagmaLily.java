package com.ewyboy.cultivatedtech.common.blocks;

import com.ewyboy.bibliotheca.common.interfaces.IBlockRenderer;
import com.ewyboy.bibliotheca.common.loaders.BlockLoader;
import com.ewyboy.cultivatedtech.common.items.ItemMagmaLily;
import com.ewyboy.cultivatedtech.common.loaders.CreativeTabLoader;
import com.ewyboy.cultivatedtech.common.register.Register;
import net.minecraft.block.BlockLilyPad;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockMagmaLily extends BlockLilyPad implements IBlockRenderer, BlockLoader.IHasCustomItem {

    public BlockMagmaLily() {
        setHardness(50.0f);
        setResistance(2000.0F);
        setCreativeTab(CreativeTabLoader.tabCultivatedTech);
    }

    @Override
    protected boolean canSustainBush(IBlockState state) {
        return state.getBlock() == Blocks.LAVA || state.getMaterial() == Material.LAVA;
    }

    @Override
    public boolean canBlockStay(World world, BlockPos pos, IBlockState state) {
        if (pos.getY() >= 0 && pos.getY() < 256) {
            IBlockState iblockstate = world.getBlockState(pos.down());
            Material material = iblockstate.getMaterial();
            return material == Material.LAVA && iblockstate.getValue(BlockLiquid.LEVEL) == 0;
        } else {
            return false;
        }
    }

    @Override
    public int[] modelMetas() {
        return new int[0];
    }

    @Override
    public void registerBlockRenderer() {}

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockItemRenderer() {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), new ItemStack(this).getMetadata(), new ModelResourceLocation(getRegistryName(), "inventory"));
    }

    @Override
    public ItemBlock getItemBlock() {
        return new ItemMagmaLily(this);
    }
}
