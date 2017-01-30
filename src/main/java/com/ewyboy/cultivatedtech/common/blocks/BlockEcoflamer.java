package com.ewyboy.cultivatedtech.common.blocks;

import com.ewyboy.cultivatedtech.common.blocks.blockbases.BlockBaseModeled;
import com.ewyboy.cultivatedtech.common.compatibilities.waila.IWailaCamouflageUser;
import com.ewyboy.cultivatedtech.common.compatibilities.waila.IWailaInformationUser;
import com.ewyboy.cultivatedtech.common.tiles.TileEntityEcoflamer;
import com.ewyboy.cultivatedtech.common.tiles.TileEntitySoil;
import com.ewyboy.cultivatedtech.common.utility.Logger;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import net.minecraft.block.BlockGrass;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;
import java.util.Random;

/**
 * Created by EwyBoy
 */
public class BlockEcoflamer extends BlockBaseModeled implements ITileEntityProvider, IWailaCamouflageUser, IWailaInformationUser {

    public BlockEcoflamer() {
        super(Material.ROCK);
    }

    @Override
    public ItemStack getWailaStack(IWailaDataAccessor accessor, IWailaConfigHandler config) {
        return new ItemStack(this);
    }

    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random rand) {
        double x = (double)pos.getX() + 0.5D;
        double z = (double)pos.getZ() + 0.5D;
        double flameY = (double)pos.getY() + 0.45D;
        double smokeY = (double)pos.getY() + 0.8D;

        for (int i = 0; i < 30; i++) {
            world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, x, smokeY, z, 0.0D, 0.0D + (i / 100), 0.0D);
            world.spawnParticle(EnumParticleTypes.FLAME, MathHelper.getRandomDoubleInRange(rand, -0.25d, 0.25d) + x, flameY, MathHelper.getRandomDoubleInRange(rand, -0.25d, 0.25d) + z, 0.0D, 0.0D, 0.0D, new int[0]);
        }
    }

    private TileEntityEcoflamer getTE(IBlockAccess world, BlockPos pos) {
        return (TileEntityEcoflamer) world.getTileEntity(pos);
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityEcoflamer();
    }

    @Override
    public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
        currenttip.add("RF: " + accessor.getNBTInteger(accessor.getNBTData(), "rf") + " / " + getTE(accessor.getWorld(), accessor.getPosition()).getMaxRF());
        return currenttip;
    }
}
