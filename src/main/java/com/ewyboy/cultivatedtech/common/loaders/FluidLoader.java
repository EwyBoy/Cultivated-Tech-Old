package com.ewyboy.cultivatedtech.common.loaders;

import com.ewyboy.cultivatedtech.common.blocks.fluid.UnknownFluid;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

/**
 * Created by EwyBoy
 **/
public class FluidLoader {

    public static Fluid ETHANOL;

    public static void init() {
        ETHANOL = new UnknownFluid("ethanol", 0, 200, 15);
        ETHANOL = FluidRegistry.getFluid("ethanol");
        if(!FluidRegistry.getBucketFluids().contains(ETHANOL)) FluidRegistry.addBucketForFluid(ETHANOL);
    }
}