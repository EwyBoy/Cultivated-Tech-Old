package com.ewyboy.cultivatedtech.common.world.nether;

import com.ewyboy.bibliotheca.common.world.WorldGenUtilities;
import com.ewyboy.cultivatedtech.common.register.Register;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class MagmalilyGenerator extends WorldGenerator {

    @Override
    public boolean generate(World world, Random random, BlockPos pos) {
        int y = WorldGenUtilities.getBlockFromAbove(world, pos.getX(), pos.getZ(), Blocks.LAVA, 35);
        if (y >= pos.getY()) {
            BlockPos targetPos = new BlockPos(pos.getX(),  y + 1, pos.getZ());
            Block toReplace = world.getBlockState(targetPos).getBlock();
            if(toReplace.equals(Blocks.AIR)) {
                world.setBlockState(targetPos, Register.Blocks.magmalily.getDefaultState(), 2);
            }
        }
        return true;
    }
}
