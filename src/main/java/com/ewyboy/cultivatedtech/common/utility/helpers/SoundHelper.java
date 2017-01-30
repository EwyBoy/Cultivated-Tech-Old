package com.ewyboy.cultivatedtech.common.utility.helpers;

import net.minecraft.init.SoundEvents;
import net.minecraft.network.play.server.SPacketSoundEffect;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;

import java.util.Random;

/**
 * Created by EwyBoy
 */
public class SoundHelper {

    private static Random random = new Random();

    public static void brodcastServerSidedSoundToAllPlayerNerby(World world, BlockPos pos, SoundEvent sound, SoundCategory soundCategory, int radius) {
        FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().sendToAllNearExcept(null,
                (double)pos.getX(), (double)pos.getY(), (double)pos.getZ(),
                radius, world.provider.getDimension(),
                new SPacketSoundEffect(SoundEvents.BLOCK_GRASS_BREAK, SoundCategory.BLOCKS,
                        (double) pos.getX(), (double)pos.getY(), (double)pos.getZ(),
                        1.0f, (float) MathHelper.getRandomDoubleInRange(random, -1.0d, 1.0d))
        );
    }
}
