package com.ewyboy.cultivatedtech.common.blocks;

import com.ewyboy.cultivatedtech.common.blocks.blockbases.BlockBaseModeledFacing;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.IBlockState;
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
public class BlockFireplace extends BlockBaseModeledFacing {

    public static final PropertyInteger STATE = PropertyInteger.create("state", 0, 8);
    public static final PropertyBool FIRE = PropertyBool.create("fire");

    public BlockFireplace() {
        super(Material.WOOD);
    }

    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random rand) {
        double x = (double)pos.getX() + 0.5D;
        double z = (double)pos.getZ() + 0.5D;
        double flameY = (double)pos.getY() + 0.2D;
        double smokeY = (double)pos.getY() + 0.2D;

        for (int i = 0; i < 10; i++) {
            world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, x, smokeY, z, 0.0D, 0.0D + (i / 100), 0.0D);
            world.spawnParticle(EnumParticleTypes.FLAME, MathHelper.getRandomDoubleInRange(rand, -0.15d, 0.15d) + x, flameY, MathHelper.getRandomDoubleInRange(rand, -0.15d, 0.15d) + z, 0.0D, 0.0D, 0.0D, new int[0]);
        }
    }
}
