package com.ewyboy.cultivatedtech.common.loaders;

import com.ewyboy.cultivatedtech.common.fluids.BaseFluid;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

/**
 * Created by EwyBoy
 */
public class FluidLoader {

    public static Fluid LIQUID_ENDER;

    public static void init() {
        LIQUID_ENDER = new BaseFluid("liquid_ender", 0, 200, 15);
        LIQUID_ENDER = FluidRegistry.getFluid("liquid_ender");
        if (!FluidRegistry.getBucketFluids().contains(LIQUID_ENDER)) FluidRegistry.addBucketForFluid(LIQUID_ENDER);
    }
}
