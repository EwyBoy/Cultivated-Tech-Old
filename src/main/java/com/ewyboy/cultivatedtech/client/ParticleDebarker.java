package com.ewyboy.cultivatedtech.client;

import com.ewyboy.cultivatedtech.common.utility.Logger;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import static net.minecraft.init.Blocks.LOG;
import static net.minecraft.init.Blocks.LOG2;

/**
 * Created by EwyBoy
 */
public class ParticleDebarker extends Particle {

    public ParticleDebarker(World world, ItemStack stack, double x, double y, double z, float scale, float gravity, Vec3d velocity) {
        super(world, x, y, z, velocity.x, velocity.y, velocity.z);
        particleGravity = gravity;
        this.particleMaxAge = 50;
        setSize(0.2f, 0.2f);
        this.particleScale = scale;
        this.canCollide = true;
        motionX = velocity.x;
        motionY = velocity.y;
        motionZ = velocity.z;
        String icon = null;
        if (stack.getItem() == Item.getItemFromBlock(LOG)) {
            switch (stack.getItemDamage()) {
                case 0: icon = "oak"; break;
                case 1: icon = "spruce"; break;
                case 2: icon = "birch"; break;
                case 3: icon = "jungle"; break;
                default: icon = null;
            }
        } else if(stack.getItem() == Item.getItemFromBlock(LOG2)) {
            switch (stack.getItemDamage()) {
                case 0: icon = "acacia"; break;
                case 1: icon = "dark_oak"; break;
                default: break;
            }
        }
        Logger.info(icon);
        if (icon != null) setParticleTexture(Minecraft.getMinecraft().getTextureMapBlocks().getTextureExtry("minecraft:blocks/log_" + icon));
    }

    @Override
    public int getFXLayer() {
        return 1;
    }
}
