package com.ewyboy.cultivatedtech.common.world;

import com.ewyboy.bibliotheca.common.world.WorldGenUtilities;
import com.ewyboy.cultivatedtech.common.register.Register;
import com.ewyboy.cultivatedtech.common.world.end.EnderlilyGenerator;
import com.ewyboy.cultivatedtech.common.world.nether.MagmalilyGenerator;
import com.ewyboy.cultivatedtech.common.world.nether.NetherGrassGenerator;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.Random;

public class TheWorldGenerator implements IWorldGenerator {

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
        int blockX = chunkX * 16;
        int blockZ = chunkZ * 16;

        switch(world.provider.getDimension()) {
            case -1: generateNether(world, random, blockX, blockZ); break;
            case 0: generateWorld(world, random, blockX, blockZ); break;
            case 1: generateEnd(world, random, blockX, blockZ); break;
        }
    }

    private void generateWorld(World world, Random random, int blockX, int blockZ) {
        WorldGenUtilities.generateUnderGround(Register.Blocks.industrialdirt, world, random, blockX, blockZ, 6, 28, 0,  3, 4, 12);
    }

    private void generateNether(World world, Random random, int blockX, int blockZ) {
        WorldGenerator nethergrassGen = new NetherGrassGenerator();
        WorldGenerator magmalilyGen = new MagmalilyGenerator();
        WorldGenUtilities.generate(nethergrassGen, world, random, blockX, blockZ, 25, 4, 12);
        WorldGenUtilities.generate(magmalilyGen, world, random, blockX, blockZ, 0, 4, 12);
    }

    private void generateEnd(World world, Random random, int blockX, int blockZ) {
        WorldGenerator enderlilyGen = new EnderlilyGenerator();
        WorldGenUtilities.generate(enderlilyGen, world, random, blockX, blockZ, 60, 2, 6);
    }
}
