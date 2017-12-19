package com.ewyboy.cultivatedtech.common.blocks;

import com.ewyboy.bibliotheca.common.interfaces.IBlockRenderer;
import com.ewyboy.cultivatedtech.common.register.Register;
import com.ewyboy.cultivatedtech.common.tiles.TileEntitySoil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFarmland;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

/**
 * Created by EwyBoy
 */
public class BlockSoil extends BlockFarmland implements IBlockRenderer {

    public BlockSoil() {
        super();
        setHardness(1.0f);
    }


    @Override
    public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, ITooltipFlag advanced) {
        tooltip.add("Adaptive soil - Can be moisturised by both §9Water§f and §cLava§f");
    }

    @Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
        int i = state.getValue(MOISTURE);
        Material moisturizer = findMoisturizer(world, pos);

        if (!this.hasMoisture(world, pos, moisturizer)) {
            if (i > 0) {
                world.setBlockState(pos, state.withProperty(MOISTURE, i - 1), 2);
            } else if (!this.hasCrops(world, pos)) {
                turnToIndustrialDirt(world, pos);
            }
        } else if (i < 7) {
            if (moisturizer == Material.WATER) {
                world.setBlockState(pos, Register.Blocks.industrialsoil1.getDefaultState().withProperty(MOISTURE, 7));
            } else if (moisturizer == Material.LAVA) {
                world.setBlockState(pos, Register.Blocks.industrialsoil2.getDefaultState().withProperty(MOISTURE, 7));
            }
        }
    }

    @Override
    public void onFallenUpon(World worldIn, BlockPos pos, Entity entityIn, float fallDistance) {
        if (!worldIn.isRemote && entityIn.canTrample(worldIn, this, pos, fallDistance)) {
            turnToIndustrialDirt(worldIn, pos);
        }
    }

    @Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
        super.neighborChanged(state, worldIn, pos, blockIn, fromPos);
        if (worldIn.getBlockState(pos.up()).getMaterial().isSolid()) {
            turnToIndustrialDirt(worldIn, pos);
        }
    }

    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
        super.onBlockAdded(worldIn, pos, state);
        if (worldIn.getBlockState(pos.up()).getMaterial().isSolid()) {
            turnToIndustrialDirt(worldIn, pos);
        }
    }

    private static void turnToIndustrialDirt(World world, BlockPos pos) {
        world.setBlockState(pos, Register.Blocks.industrialdirt.getDefaultState());
        AxisAlignedBB axisalignedbb = field_194405_c.offset(pos);

        for (Entity entity : world.getEntitiesWithinAABBExcludingEntity(null, axisalignedbb)) {
            double d0 = Math.min(axisalignedbb.maxY - axisalignedbb.minY, axisalignedbb.maxY - entity.getEntityBoundingBox().minY);
            entity.setPositionAndUpdate(entity.posX, entity.posY + d0 + 0.001D, entity.posZ);
        }
    }

    private boolean hasCrops(World worldIn, BlockPos pos) {
        Block block = worldIn.getBlockState(pos.up()).getBlock();
        return block instanceof IPlantable && canSustainPlant(worldIn.getBlockState(pos), worldIn, pos, EnumFacing.UP, (IPlantable)block);
    }

    @Override
    public boolean canSustainPlant(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing direction, IPlantable plantable) {
        return true;
    }

    @Override
    public boolean isFertile(World world, BlockPos pos) {
        return super.isFertile(world, pos);
    }

    private boolean hasMoisture(World world, BlockPos pos, Material moisturizer) {
        for (BlockPos.MutableBlockPos blockpos$mutableblockpos : BlockPos.getAllInBoxMutable(pos.add(-4, 0, -4), pos.add(4, 1, 4))) {
            if (world.getBlockState(blockpos$mutableblockpos).getMaterial() == moisturizer) {
                return true;
            }
        }
        return false;
    }

    private Material findMoisturizer(World world, BlockPos pos) {
        for (BlockPos.MutableBlockPos blockpos$mutableblockpos : BlockPos.getAllInBoxMutable(pos.add(-4, 0, -4), pos.add(4, 1, 4))) {
            if (world.getBlockState(blockpos$mutableblockpos).getMaterial() == Material.WATER) {
                return Material.WATER;
            } else if (world.getBlockState(blockpos$mutableblockpos).getMaterial() == Material.LAVA) {
                return Material.LAVA;
            }
        }
        return null;
    }


    @Override
    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
        return new ItemStack(this);
    }

    @Nullable
    @Override
    public Item getItemDropped(IBlockState state, Random random, int fortune) {
        return Item.getItemFromBlock(this);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerBlockItemRenderer() {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), new ItemStack(this).getMetadata(), new ModelResourceLocation(getRegistryName(), "inventory"));
    }

    @Override
    public int[] modelMetas() {
        return new int[0];
    }

    @Override
    public void registerBlockRenderer() {
        return;
    }

    private TileEntitySoil getTE(IBlockAccess world, BlockPos pos) {
        return (TileEntitySoil) world.getTileEntity(pos);
    }

}
