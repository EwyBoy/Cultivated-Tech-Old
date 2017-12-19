package com.ewyboy.cultivatedtech.common.blocks;

import com.ewyboy.bibliotheca.common.block.BlockBaseModeled;
import com.ewyboy.bibliotheca.common.compatibilities.waila.IWailaCamouflageUser;
import com.ewyboy.bibliotheca.common.compatibilities.waila.IWailaInformationUser;
import com.ewyboy.cultivatedtech.common.loaders.CreativeTabLoader;
import com.ewyboy.cultivatedtech.common.tiles.TileEntityEcoflamer;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

import static net.minecraft.util.EnumParticleTypes.*;

/**
 * Created by EwyBoy
 */
public class BlockEcoflamer extends BlockBaseModeled implements ITileEntityProvider, IWailaCamouflageUser, IWailaInformationUser {

    public static final PropertyBool ENABLED = PropertyBool.create("enabled");
    private int tier;

    public BlockEcoflamer(int tier) {
        super(Material.ROCK);
        setCreativeTab(CreativeTabLoader.tabCultivatedTech);
        this.tier = tier;
        setHardness(2.0f);
    }

    @Override
    public ItemStack getWailaStack(IWailaDataAccessor accessor, IWailaConfigHandler config) {
        return new ItemStack(this);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, ITooltipFlag advanced) {
        tooltip.add("Burns grass and plants from surroundings");
        tooltip.add("Produces RF as a byproduct");
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(ENABLED, (meta & 8) != 0);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(ENABLED) ? 8 : 0;
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, ENABLED);
    }

    @Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
        int powered = worldIn.isBlockIndirectlyGettingPowered(pos);
        worldIn.setBlockState(pos, state.withProperty(ENABLED, powered > 0), 3);
    }

    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random rand) {
        double x = (double)pos.getX() + 0.5D;
        double z = (double)pos.getZ() + 0.5D;

        if (!world.getBlockState(pos).getValue(ENABLED)) {
            switch (tier) {
                case 1:
                    for (int i = 0; i < 30; i++) {
                        world.spawnParticle(SMOKE_NORMAL, x, (double)pos.getY() + 0.8D, z, 0.0D, 0.0D + (i / 100), 0.0D);
                        world.spawnParticle(FLAME, MathHelper.nextFloat(rand, -0.25f, 0.25f) + x, pos.getY() + 0.45D, MathHelper.nextFloat(rand, -0.25f, 0.25f) + z, 0.0D, 0.0D, 0.0D);
                    }
                break;

                case 2:
                    for (int i = 0; i < 30; i++) {
                        world.spawnParticle(PORTAL, MathHelper.nextFloat(rand, -0.15f, 0.15f) + x, pos.getY() + 2.25D, MathHelper.nextFloat(rand, -0.15f, 0.15f) + z, 0.0D, -2.0D, 0.0D);
                        world.spawnParticle(PORTAL, MathHelper.nextFloat(rand, -0.25f, 0.25f) + x, pos.getY() + 0.8D, MathHelper.nextFloat(rand, -0.25f, 0.25f) + z, 0.0D, -1.0D + ((i / 100) * -1), 0.0D);
                    }
                break;

                case 3:
                    for (int i = 0; i < 30; i++) {
                        world.spawnParticle(CRIT_MAGIC, MathHelper.nextFloat(rand, -0.15f, 0.15f) + x, pos.getY() + 0.35D, MathHelper.nextFloat(rand, -0.15f, 0.15f) + z, 0.0D, 0.2D, 0.0D);
                        world.spawnParticle(CRIT_MAGIC, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, Math.sin(2 * Math.PI * 30 / i), 2.5D,  Math.cos(2 * Math.PI * 30 / i));
                    }
                break;
            }
        }
    }

    private TileEntityEcoflamer getTE(IBlockAccess world, BlockPos pos) {
        return (TileEntityEcoflamer) world.getTileEntity(pos);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileEntityEcoflamer(tier);
    }

    @Override
    public List<String> getWailaBody(ItemStack itemStack, List<String> list, IWailaDataAccessor accessor, IWailaConfigHandler iWailaConfigHandler) {
        list.add(getTE(accessor.getWorld(), accessor.getPosition()).getEnergyStored(null) + " / " + getTE(accessor.getWorld(), accessor.getPosition()).getMaxEnergyStored(null));
        return list;
    }
}