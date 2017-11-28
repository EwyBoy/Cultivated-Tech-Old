package com.ewyboy.cultivatedtech.common.blocks.crops;

import com.ewyboy.bibliotheca.common.compatibilities.waila.IWailaCamouflageUser;
import com.ewyboy.cultivatedtech.common.register.Register;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockFarmland;
import net.minecraft.block.IGrowable;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;

import java.util.Random;

import static com.ewyboy.cultivatedtech.common.register.Register.Items.hemp;
import static com.ewyboy.cultivatedtech.common.register.Register.Items.seedHemp;

/**
 * Created by EwyBoy
 **/
public class BlockCropHemp extends BlockBush implements IGrowable, IPlantable, IWailaCamouflageUser {

    public static final PropertyInteger AGE = PropertyInteger.create("age", 0, 7);

    public BlockCropHemp() {
        setDefaultState(this.blockState.getBaseState().withProperty(AGE, 0));
        setTickRandomly(true);
        setHardness(0.3F);
        this.disableStats();
        this.setCreativeTab(null);
    }

    @Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
        int currentState = state.getValue(AGE);
        if (world.getBlockState(pos.down()).getBlock() == this || canBlockStay(world, pos, state)) {
            if (rand.nextInt(8) == 0) {
                if (world.getLightFromNeighbors(pos.up()) >= 7) {
                    if (world.isAirBlock(pos.up()) && currentState == 7 && canGrowUp(world, pos)) {
                        world.setBlockState(pos.up(), getStateFromMeta(0));
                    }
                }
                if (currentState < 7) {
                    world.setBlockState(pos, this.getStateFromMeta(currentState + 1));
                }
            }
        }
    }

    private boolean canGrowUp(World world, BlockPos pos) {
        for(int i = 0; i < 4; i++) { pos = pos.down();
            if(world.getBlockState(pos).getBlock() != this) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (state.getValue(AGE) == 7) {
            breakBlock(world, pos, state);
            int minY = pos.getY() - 4;
            for(; pos.getY() >= minY && !(world.getBlockState(pos).getBlock() instanceof BlockFarmland); pos = pos.down());
            if (world.getBlockState(pos).getBlock() instanceof BlockFarmland) world.setBlockState(pos.up(), getStateFromMeta(0));
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean canBlockStay(World world, BlockPos pos,IBlockState state) {
        return (world.getBlockState(pos.down()).getBlock() == this || world.getBlockState(pos.down()).getBlock() instanceof BlockFarmland) || this.canPlaceBlockAt(world, pos);
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        if (state.getValue(AGE) == 7) {
            return Register.Items.hemp;
        } else {
            return Register.Items.seedHemp;
        }
    }

    @Override
    public boolean canGrow(World world, BlockPos pos, IBlockState state, boolean isClient) {
        return state.getValue(AGE) < 7;
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
    public boolean canPlaceBlockAt(World world, BlockPos pos) {
        Block block = world.getBlockState(pos.down()).getBlock();
        return block.canSustainPlant(world.getBlockState(pos), world, pos, EnumFacing.UP, this) || block == this;
    }

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state) {
        if(world.getBlockState(pos.up()).getBlock() == this) {
            dropHemp(world,pos,state);
            world.setBlockToAir(pos.up());
        }
        if(world.getBlockState(pos.up(2)).getBlock() == this) {
            dropHemp(world,pos,state);
            world.setBlockToAir(pos.up(2));
        }
        if(world.getBlockState(pos).getBlock() == this) {
            dropHemp(world,pos,state);
            world.setBlockToAir(pos);
        }
        if(world.getBlockState(pos.down()).getBlock() == this) {
            dropHemp(world,pos,state);
            world.setBlockToAir(pos.down());
        }
        if(world.getBlockState(pos.down(2)).getBlock() == this) {
            dropHemp(world,pos,state);
            world.setBlockToAir(pos.down(2));
        }
    }

    private void dropHemp(World world, BlockPos pos, IBlockState state) {
        ItemStack out = null;
        int currentState = state.getValue(AGE);

        if(currentState == 7) {
            float chance = world.rand.nextFloat();
            if (chance >= 0.40) {
                out = new ItemStack(hemp,2);
            } else if (chance >=0.1) {
                out = new ItemStack(hemp);
            }
        }

        if (out != null) spawnAsEntity(world, pos, out);
    }

    @Override
    public boolean canUseBonemeal(World world, Random rand, BlockPos pos,IBlockState state) {
        return true;
    }

    @Override
    public void grow(World world, Random rand, BlockPos pos, IBlockState state) {
        this.updateTick(world, pos, state, rand);
    }

    @Override
    public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
        entityIn.motionX *=(1.0-(state.getValue(AGE) / 14.0));
        entityIn.motionZ *=(1.0-(state.getValue(AGE) / 14.0));
    }

    @Override
    public ItemStack getWailaStack(IWailaDataAccessor accessor, IWailaConfigHandler config) {
        return new ItemStack(Register.Items.hemp);
    }
}
