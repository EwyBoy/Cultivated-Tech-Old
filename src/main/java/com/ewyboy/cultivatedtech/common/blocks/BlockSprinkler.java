package com.ewyboy.cultivatedtech.common.blocks;

import com.ewyboy.bibliotheca.common.block.BlockBaseModeledFacing;
import com.ewyboy.cultivatedtech.common.loaders.CreativeTabLoader;
import com.ewyboy.cultivatedtech.common.tiles.TileEntitySprinkler;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockSprinkler extends BlockBaseModeledFacing implements ITileEntityProvider {

    private int tier;
    public static final PropertyBool ENABLED = PropertyBool.create("enabled");
    private static final AxisAlignedBB SPRINKLER_X_AXIS = new AxisAlignedBB(0.275D, 0.0D, 0.05, 0.725D, 0.3D, 0.95);
    private static final AxisAlignedBB SPRINKLER_Z_AXIS = new AxisAlignedBB(0.05, 0.0D, 0.275D, 0.95, 0.3D, 0.725D);

    public BlockSprinkler(int tier) {
        super(Material.IRON);
        setHardness(1.0f);
        setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
        setCreativeTab(CreativeTabLoader.tabCultivatedTech);
        this.tier = tier;
    }

    @Deprecated
    @Override
    public boolean isFullBlock(IBlockState state) {
        return false;
    }

    @Deprecated
    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Deprecated
    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return state.getValue(FACING).equals(EnumFacing.NORTH) || state.getValue(FACING).equals(EnumFacing.SOUTH) ? SPRINKLER_Z_AXIS : SPRINKLER_X_AXIS;
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (playerIn.getHeldItem(hand).isEmpty()) {
            worldIn.setBlockState(pos, worldIn.getBlockState(pos).getValue(ENABLED)
                    ? getDefaultState().withProperty(ENABLED, false).withProperty(FACING, worldIn.getBlockState(pos).getValue(FACING))
                    : getDefaultState().withProperty(ENABLED, true).withProperty(FACING, worldIn.getBlockState(pos).getValue(FACING))
            );
        }
        return true;
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(FACING, EnumFacing.getFront(meta & 7)).withProperty(ENABLED, (meta & 8) != 0);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(FACING).getIndex() + (state.getValue(ENABLED) ? 8 : 0);
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING, ENABLED);
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileEntitySprinkler(tier);
    }
}