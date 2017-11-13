package com.ewyboy.cultivatedtech.proxy;

import com.ewyboy.bibliotheca.common.helpers.ParticleHelper;
import com.ewyboy.cultivatedtech.client.ParticleEffectSpray;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Created by EwyBoy
 **/
public class ClientProxy extends CommonProxy {

    @Override
    public Side getSide() {
        return Side.CLIENT;
    }

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);
    }

    @Override
    public void init(FMLInitializationEvent event) {
        super.init(event);
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {
        super.postInit(event);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void spawnLiquidSpray(World world, FluidStack fluid, double x, double y, double z, float scale, float gravity, Vec3d velocity) {
        ParticleHelper.spawnParticle(new ParticleEffectSpray(world, fluid, x, y, z, scale, gravity, velocity));
    }
}
