package com.ewyboy.cultivatedtech.common.blocks.fluid;

import com.ewyboy.cultivatedtech.common.utility.Reference;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

/**
 * Created by EwyBoy
 **/
public class UnknownFluid extends Fluid {

    public UnknownFluid(String fluidName, int viscosity, int density, int luminosity) {
        super(
                fluidName,
                new ResourceLocation(Reference.Info.MODID + ":" + "blocks" + "/" + fluidName + "_still"),
                new ResourceLocation(Reference.Info.MODID + ":" + "blocks" + "/" + fluidName + "_flow")
        );
        this.setViscosity(viscosity);
        this.setDensity(density);
        this.setLuminosity(luminosity);
        FluidRegistry.registerFluid(this);
    }
}