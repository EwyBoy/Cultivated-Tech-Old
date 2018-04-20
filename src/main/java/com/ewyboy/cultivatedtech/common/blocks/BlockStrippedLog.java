package com.ewyboy.cultivatedtech.common.blocks;

import com.ewyboy.bibliotheca.common.interfaces.IBlockRenderer;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;

/**
 * Created by EwyBoy
 */
public class BlockStrippedLog extends BlockHemp implements IBlockRenderer {

    public BlockStrippedLog() {
        super();
        this.setHardness(2.0F);
        this.setSoundType(SoundType.WOOD);
    }

    @Override
    public Material getMaterial(IBlockState state) {
        return Material.WOOD;
    }
}
