package com.ewyboy.cultivatedtech.common.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

/**
 * Created by EwyBoy
 */
public class BlockEcoflamer extends BlockBaseModeled {

    public BlockEcoflamer() {
        super(Material.ROCK);
        setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
    }

    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        double x = (double)pos.getX() + 0.5D;
        double z = (double)pos.getZ() + 0.5D;
        double flameY = (double)pos.getY() + 0.45D;
        double smokeY = (double)pos.getY() + 0.8D;

        for (int i = 0; i < 30; i++) {
            worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, x, smokeY, z, 0.0D, 0.0D + (i / 100), 0.0D);
            worldIn.spawnParticle(EnumParticleTypes.FLAME, MathHelper.getRandomDoubleInRange(rand, -0.25d, 0.25d) + x, flameY, MathHelper.getRandomDoubleInRange(rand, -0.25d, 0.25d) + z, 0.0D, 0.0D, 0.0D, new int[0]);
        }
    }
}
