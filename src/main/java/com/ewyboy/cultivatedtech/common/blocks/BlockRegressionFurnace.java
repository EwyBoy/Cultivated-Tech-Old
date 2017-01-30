package com.ewyboy.cultivatedtech.common.blocks;

import com.ewyboy.cultivatedtech.common.blocks.blockbases.BlockBaseModeledFacing;
import com.ewyboy.cultivatedtech.common.compatibilities.waila.IWailaCamouflageUser;
import com.ewyboy.cultivatedtech.common.tiles.TileEntityRegressionFurnace;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

/**
 * Created by EwyBoy
 */
public class BlockRegressionFurnace extends BlockBaseModeledFacing implements ITileEntityProvider, IWailaCamouflageUser {

    public BlockRegressionFurnace() {
        super(Material.ROCK);
    }

    @Override
    public ItemStack getWailaStack(IWailaDataAccessor accessor, IWailaConfigHandler config) {
        return new ItemStack(this);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random rand) {
        double x = (double)pos.getX() + 0.5D;
        double z = (double)pos.getZ() + 0.5D;
        double flameY = (double)pos.getY() + 0.2D;

        for (int i = 0; i < 30; i++) {
            world.spawnParticle(EnumParticleTypes.FLAME, MathHelper.getRandomDoubleInRange(rand, -0.3d, 0.3d) + x, flameY, MathHelper.getRandomDoubleInRange(rand, -0.3d, 0.3d) + z, 0.0D, 0.0D, 0.0D, new int[0]);
        }
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileEntityRegressionFurnace();
    }
}
