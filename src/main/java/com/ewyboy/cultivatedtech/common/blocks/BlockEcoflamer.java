package com.ewyboy.cultivatedtech.common.blocks;

import com.ewyboy.cultivatedtech.common.blocks.blockbases.BlockBaseModeled;
import com.ewyboy.cultivatedtech.common.compatibilities.waila.IWailaCamouflageUser;
import com.ewyboy.cultivatedtech.common.utility.Logger;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import net.minecraft.block.BlockGrass;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

/**
 * Created by EwyBoy
 */
public class BlockEcoflamer extends BlockBaseModeled implements IWailaCamouflageUser {

    public BlockEcoflamer() {
        super(Material.ROCK);
    }

    @Override
    public ItemStack getWailaStack(IWailaDataAccessor accessor, IWailaConfigHandler config) {
        return new ItemStack(this);
    }

    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random rand) {
        double x = (double)pos.getX() + 0.5D;
        double z = (double)pos.getZ() + 0.5D;
        double flameY = (double)pos.getY() + 0.45D;
        double smokeY = (double)pos.getY() + 0.8D;

        boolean grassBurned = false;

        if (rand.nextInt(2) == 1) {
            BlockPos targetPos = new BlockPos(
                    pos.getX() + MathHelper.getRandomDoubleInRange(rand, -5.0D, 5.0D),
                    pos.getY() - 1,
                    pos.getZ() + MathHelper.getRandomDoubleInRange(rand, -5.0D, 5.0D)
            );

            if (targetPos.equals(pos.down())) {
                randomDisplayTick(state, world, pos, rand);
            } else {
                if (world.getBlockState(targetPos).getBlock() instanceof BlockGrass) {
                    world.playSound((double) targetPos.getX(), (double)targetPos.getY(), (double)targetPos.getZ(), SoundEvents.BLOCK_GRASS_BREAK, SoundCategory.BLOCKS, 1.0f, (float) MathHelper.getRandomDoubleInRange(rand, -1.0d, 1.0d), false);
                    world.setBlockState(targetPos, Blocks.DIRT.getDefaultState(), 3);
                    grassBurned = true;
                } if (grassBurned) {
                    Logger.info("NOM, NOM!");
                    //Produce Power
                }
            }
        }

        for (int i = 0; i < 30; i++) {
            world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, x, smokeY, z, 0.0D, 0.0D + (i / 100), 0.0D);
            world.spawnParticle(EnumParticleTypes.FLAME, MathHelper.getRandomDoubleInRange(rand, -0.25d, 0.25d) + x, flameY, MathHelper.getRandomDoubleInRange(rand, -0.25d, 0.25d) + z, 0.0D, 0.0D, 0.0D, new int[0]);
        }
    }
}
