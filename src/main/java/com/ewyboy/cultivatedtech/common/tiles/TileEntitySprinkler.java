package com.ewyboy.cultivatedtech.common.tiles;

import com.ewyboy.bibliotheca.Bibliotheca;
import com.ewyboy.cultivatedtech.CultivatedTech;
import com.ewyboy.cultivatedtech.common.blocks.BlockSprinkler;
import com.google.common.collect.Lists;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFarmland;
import net.minecraft.block.IGrowable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

import java.util.List;
import java.util.Random;

public class TileEntitySprinkler extends TileEntityBase implements ITickable {

    private int tier;
    private int range;
    private float chance;

    public TileEntitySprinkler() {}

    public TileEntitySprinkler(int tier) {
        switch (tier) {
            case 1:
                range = 1;
                chance = 0.05f;
            break;

            case 2:
                range = 3;
                chance = 0.5f;
            break;

            case 3:
                range = 5;
                chance = 1.0f;
            break;

            default:
                range = 0;
                chance = 0.0f;
            break;
        }
        this.tier = tier;
    }

    @Override
    public void update() {
        if (world.getBlockState(pos).getValue(BlockSprinkler.ENABLED)) {
            if (world.isRemote) {
                sprayParticles();
            } else {
                sprinkleWater();
            }
        }
    }

    private void sprinkleWater() {
        List<BlockPos> area  = Lists.newArrayList(BlockPos.getAllInBox(pos.add(-range, -1, -range), pos.add(range, 1, range)));
        for (BlockPos scan : area) {
            IBlockState state = world.getBlockState(scan);
            if (state.getBlock() instanceof BlockFarmland) {
                int moisture = state.getValue(BlockFarmland.MOISTURE);
                if (moisture < 7) {
                    world.setBlockState(scan, state.withProperty(BlockFarmland.MOISTURE, 7), 2);
                }
            }
        }
        if (world.rand.nextInt(100) < chance) {
            for (BlockPos scan : area) {
                Block plant = world.getBlockState(scan).getBlock();
                if (plant instanceof IGrowable || plant instanceof IPlantable || plant == Blocks.MYCELIUM || plant == Blocks.CHORUS_FLOWER) {
                    world.scheduleBlockUpdate(scan, plant, 0, 1);
                }
            }
        }
    }

    private int ticks;
    private static final double SPRAY_SIDE_SCATTER = Math.toRadians(25);
    private static final double[] SPRINKER_DELTA = new double[] { 0.2, 0.25, 0.5 };
    private static final Random RANDOM = new Random();

    private float getSprayDirection() {
        return MathHelper.sin(ticks * 0.02f);
    }

    private void sprayParticles() {
        ticks++;
        final EnumFacing blockYawRotation = world.getBlockState(pos).getValue(BlockSprinkler.FACING).rotateYCCW();
        final double nozzleAngle = getSprayDirection();
        final double sprayForwardVelocity = Math.sin(Math.toRadians(nozzleAngle * 25));

        final int offsetZ = blockYawRotation.getFrontOffsetZ();
        final int offsetX = blockYawRotation.getFrontOffsetX();

        final double forwardVelocityX = sprayForwardVelocity * offsetZ / -2;
        final double forwardVelocityZ = sprayForwardVelocity * offsetX / 2;

        final double sprinklerDelta = SPRINKER_DELTA[Bibliotheca.proxy.getParticleSettings()];
        double outletPosition = -0.5;

        while (outletPosition <= 0.5) {
            final Vec3d vec;
            final double spraySideVelocity = Math.sin(SPRAY_SIDE_SCATTER * (RANDOM.nextDouble() - 0.5));
            final double sideVelocityX = spraySideVelocity * offsetX;
            final double sideVelocityZ = spraySideVelocity * offsetZ;

            switch (tier) {
                case 1: vec = new Vec3d(
                        (forwardVelocityX + sideVelocityX) / 3,
                        0.35,
                        (forwardVelocityZ + sideVelocityZ) / 3
                ); break;
                case 2: vec = new Vec3d(
                        (forwardVelocityX + sideVelocityX) / 1.5,
                        0.45,
                        (forwardVelocityZ + sideVelocityZ) / 1.5
                ); break;
                case 3: vec = new Vec3d(
                        (forwardVelocityX + sideVelocityX),
                        0.55,
                        (forwardVelocityZ + sideVelocityZ)
                ); break;
                default: vec = new Vec3d(0.0f,0.0f,0.0f); break;
            }

            CultivatedTech.proxy.spawnLiquidSpray(world, new FluidStack(FluidRegistry.WATER, 0),
                    pos.getX() + 0.5 + (outletPosition * 0.6 * offsetX),
                    pos.getY() + 0.2,
                    pos.getZ() + 0.5 + (outletPosition * 0.6 * offsetZ),
                    0.3f, 0.7f, vec);
            outletPosition += sprinklerDelta;
        }
    }
}