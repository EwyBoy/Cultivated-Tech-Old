package com.ewyboy.cultivatedtech.common.blocks.crops;

import com.ewyboy.bibliotheca.common.compatibilities.waila.IWailaCamouflageUser;
import com.ewyboy.cultivatedtech.common.register.Register;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockFarmland;
import net.minecraft.block.IGrowable;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;

import java.util.Random;

public class BlockCropScorch extends BlockBush implements IGrowable, IPlantable, IWailaCamouflageUser {

    public static final PropertyInteger AGE = PropertyInteger.create("age", 0, 2);

    public BlockCropScorch() {
        setDefaultState(this.blockState.getBaseState().withProperty(AGE, 0));
        setTickRandomly(true);
        setHardness(0.3f);
        this.disableStats();
        this.setCreativeTab(null);
    }

    @Override
    public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
        entityIn.motionX *=(1.0-(state.getValue(AGE) / 14.0));
        entityIn.motionZ *=(1.0-(state.getValue(AGE) / 14.0));
        if (entityIn instanceof EntityPlayer) {
            EntityPlayer entityplayer = (EntityPlayer) entityIn;
            entityplayer.setFire(1);
        }
    }

    private boolean shouldDrop() {
        Random random = new Random();
        return random.nextInt(2) == 0;
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (state.getValue(AGE) == 2) {
            if (world.getBlockState(pos.down()).getBlock() instanceof BlockFarmland) {
                if (!world.isRemote) {
                    world.destroyBlock(pos, shouldDrop());
                    world.setBlockState(pos, getStateFromMeta(0));
                    world.playSound(playerIn, pos, SoundEvents.BLOCK_GRASS_BREAK, SoundCategory.BLOCKS, 0.5f, 0.0f);
                }
                return true;
            }
        }
        return true;
    }

    @Override
    public boolean canGrow(World world, BlockPos pos, IBlockState state, boolean isClient) {
        return state.getValue(AGE) < 2;
    }

    @Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
        int currentState = state.getValue(AGE);
        if (currentState < 2) {
            if (rand.nextInt(12) == 0) {
                world.setBlockState(pos, this.getStateFromMeta(currentState + 1));
            }
        }
    }

    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
        return new ItemStack(Register.Items.scorch);
    }

    @Override
    public boolean canUseBonemeal(World world, Random random, BlockPos pos, IBlockState state) {
        return false;
    }

    @Override
    public void grow(World world, Random random, BlockPos pos, IBlockState state) {
        this.updateTick(world, pos, state, random);
    }

    @Override
    public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
        return worldIn.getBlockState(pos.down()).getBlock().canSustainPlant(worldIn.getBlockState(pos), worldIn, pos, EnumFacing.UP, this);
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return state.getValue(AGE) == 2 ? new ItemStack(Register.Items.scorch, rand.nextInt(2) + 1).getItem() : Register.Items.scorch;
    }

    @Override
    public EnumPlantType getPlantType(IBlockAccess world, BlockPos pos) {
        return EnumPlantType.Crop;
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(AGE, meta);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(AGE);
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, AGE);
    }

    @Override
    public ItemStack getWailaStack(IWailaDataAccessor iWailaDataAccessor, IWailaConfigHandler iWailaConfigHandler) {
        return new ItemStack(Register.Items.scorch);
    }
}