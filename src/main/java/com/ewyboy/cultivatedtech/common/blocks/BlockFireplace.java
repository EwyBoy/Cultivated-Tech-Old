package com.ewyboy.cultivatedtech.common.blocks;

import com.ewyboy.bibliotheca.common.block.BlockBaseModeledFacing;
import com.ewyboy.bibliotheca.common.compatibilities.waila.IWailaCamouflageUser;
import com.ewyboy.cultivatedtech.common.loaders.CreativeTabLoader;
import com.ewyboy.cultivatedtech.common.tiles.TileEntityFireplace;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

/**
 * Created by EwyBoy
 */
public class BlockFireplace extends BlockBaseModeledFacing implements IWailaCamouflageUser, ITileEntityProvider {

    public BlockFireplace() {
        super(Material.WOOD);
        setCreativeTab(CreativeTabLoader.tabCultivatedTech);
        setLightLevel(1.0f);
        setHardness(1.5f);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, ITooltipFlag advanced) {
        tooltip.add("Provides Light And A Easy Way To Cook Food");
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (worldIn.isRemote) {
            return true;
        } else {
            TileEntity tileentity = worldIn.getTileEntity(pos);
            if (tileentity instanceof TileEntityFurnace) {
                playerIn.displayGUIChest((TileEntityFurnace)tileentity);
                playerIn.addStat(StatList.FURNACE_INTERACTION);
            }
            return true;
        }
    }

    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random rand) {
        double x = (double)pos.getX() + 0.5D;
        double z = (double)pos.getZ() + 0.5D;
        double flameY = (double)pos.getY() + 0.2D;
        double smokeY = (double)pos.getY() + 0.2D;

        for (int i = 0; i < 10; i++) {
            world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, x, smokeY, z, 0.0D, 0.0D + (i / 100), 0.0D);
            world.spawnParticle(EnumParticleTypes.FLAME, MathHelper.nextFloat(rand, -0.15f, 0.15f) + x, flameY, MathHelper.nextFloat(rand, -0.15f, 0.15f) + z, 0.0D, 0.0D, 0.0D, new int[0]);
        }
    }

    @Override
    public ItemStack getWailaStack(IWailaDataAccessor accessor, IWailaConfigHandler config) {
        return new ItemStack(this);
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityFireplace();
    }
}
