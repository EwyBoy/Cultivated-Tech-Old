package com.ewyboy.cultivatedtech.common.material;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialLiquid;

/**
 * Created by EwyBoy
 **/
public class UnknownMaterials extends Material {

    public static final Material FUEL = (new MaterialLiquid(MapColor.YELLOW));

    public UnknownMaterials(MapColor color) {
        super(color);
    }
}
