package com.ewyboy.cultivatedtech.common.world;

import com.ewyboy.cultivatedtech.common.register.Register;
import com.ewyboy.cultivatedtech.common.world.end.EnderlilyGenerator;
import com.ewyboy.cultivatedtech.common.world.nether.MagmalilyGenerator;
import com.ewyboy.cultivatedtech.common.world.nether.NetherGrassGenerator;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.Random;

public class TheWorldGenerator implements IWorldGenerator {

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
        int blockX = chunkX * 16;
        int blockZ = chunkZ * 16;

        switch(world.provider.getDimension()) {
            case -1: generateNether(world, random, blockX, blockZ);
                break;
            case 0: generateWorld(world, random, blockX, blockZ);
                break;
            case 1: generateEnd(world, random, blockX, blockZ);
                break;
        }
    }

    private void generateWorld(World world, Random random, int blockX, int blockZ) {
        generateThisShitUnderGround(Register.Blocks.industrialdirt, world, random, blockX, blockZ, 6, 28, 3,  7, 8, 24);
    }

    private void generateNether(World world, Random random, int blockX, int blockZ) {
        WorldGenerator nethergrassGen = new NetherGrassGenerator();
        WorldGenerator magmalilyGen = new MagmalilyGenerator();
        generateThisShit(nethergrassGen, world, random, blockX, blockZ, 25, 4, 12);
        generateThisShit(magmalilyGen, world, random, blockX, blockZ, 0, 4, 12);
    }

    private void generateEnd(World world, Random random, int blockX, int blockZ) {
        WorldGenerator enderlilyGen = new EnderlilyGenerator();
        generateThisShit(enderlilyGen, world, random, blockX, blockZ, 60, 2, 6);
    }

    private void generateThisShit(WorldGenerator worldGenerator, World world, Random random, int blockX, int blockZ, int minimumSpawnHeight, int randomMin, int randomMax) {
        int numberOfStuff = randomMin + random.nextInt(randomMax - randomMin);
        for (int i = 0; i < numberOfStuff; i++) {
            int randX = blockX + random.nextInt(16);
            int randZ = blockZ + random.nextInt(16);
            worldGenerator.generate(world, random, new BlockPos(randX, minimumSpawnHeight ,randZ));
        }
    }

    private void generateThisShitUnderGround(
            Block block, World world, Random random,
            int blockX, int blockZ,
            int minSpawnHeight, int maxSpawnHeight,
            int minSpawnRate, int maxSpawnRate,
            int minVeinSize, int maxVeinSize
    ) {
        int spawnRate = minSpawnRate + random.nextInt(maxSpawnRate - minSpawnRate);
        int veinSize = minVeinSize + random.nextInt(maxVeinSize - minVeinSize);
        for (int i = 0; i < spawnRate; i++) {
            int randX = blockX + random.nextInt(16);
            int randZ = blockZ + random.nextInt(16);
            int randY = minSpawnHeight + random.nextInt(maxSpawnHeight - minSpawnHeight);

            new WorldGenMinable(block.getDefaultState(), veinSize).generate(world, random, new BlockPos(randX, randY, randZ));
        }
    }
}
