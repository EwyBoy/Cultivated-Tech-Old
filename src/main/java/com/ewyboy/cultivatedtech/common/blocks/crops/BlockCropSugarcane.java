package com.ewyboy.cultivatedtech.common.blocks.crops;

import com.ewyboy.cultivatedtech.common.register.Register;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

import java.util.Random;

/**
 * Created by EwyBoy
 */
public class BlockCropSugarcane extends BlockCropHemp {

    public BlockCropSugarcane() {
        super();
        setDefaultState(this.blockState.getBaseState().withProperty(AGE, 0));
        setTickRandomly(true);
        setHardness(0.3F);
        this.disableStats();
        this.setCreativeTab(null);
    }

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state) {
        super.breakBlock(world, pos, state);
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        if (state.getValue(AGE) != 7) {
            return Register.Items.seedsugarcane;
        } else {
            return Register.Items.sugarcane;
        }
    }

    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
        return new ItemStack(Register.Items.seedsugarcane);
    }

    @Override
    public void dropHarvest(World world, BlockPos pos, IBlockState state) {
        ItemStack out = null;
        int currentState = state.getValue(AGE);

        if(currentState == 7) {
            float chance = world.rand.nextFloat();
            if (chance >= 0.40) {
                out = new ItemStack(Register.Items.sugarcane,2);
            } else if (chance >=0.1) {
                out = new ItemStack(Register.Items.sugarcane);
            }
        }
        if (out != null) spawnAsEntity(world, pos, out);
    }

    @Override
    public ItemStack getWailaStack(IWailaDataAccessor accessor, IWailaConfigHandler config) {
        return new ItemStack(Register.Items.sugarcane);
    }
}