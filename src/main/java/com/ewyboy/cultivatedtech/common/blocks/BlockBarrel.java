package com.ewyboy.cultivatedtech.common.blocks;

import com.ewyboy.bibliotheca.common.block.BlockBaseModeledFacing;
import com.ewyboy.bibliotheca.common.compatibilities.waila.IWailaCamouflageUser;
import com.ewyboy.bibliotheca.common.compatibilities.waila.IWailaInformationUser;
import com.ewyboy.cultivatedtech.common.loaders.CreativeTabLoader;
import com.ewyboy.cultivatedtech.common.register.Register;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.List;

/**
 * Created by EwyBoy
 **/
public class BlockBarrel extends BlockBaseModeledFacing implements IWailaInformationUser, IWailaCamouflageUser {

    public static final PropertyBool ENABLED = PropertyBool.create("enabled");

    public BlockBarrel() {
        super(Material.WOOD);
        setHardness(1.0f);
        setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
        setCreativeTab(CreativeTabLoader.tabCultivatedTech);
    }

    @Override
    public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        List<ItemStack> list = super.getDrops(world, pos, state, fortune);
        if(state.getValue(ENABLED)) list.add(new ItemStack(Register.Items.barrelTap));

        return list;
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack item = new ItemStack(Register.Items.barrelTap);
        ItemStack heldItem = player.getHeldItem(hand);

        if (!heldItem.isEmpty() && !world.getBlockState(pos).getValue(ENABLED) && world.getBlockState(pos).getValue(FACING).equals(facing)) {
            if (heldItem.getItem().equals(item.getItem())) {
                if (!player.isCreative()) heldItem.shrink(1);
                world.setBlockState(pos, getDefaultState().withProperty(ENABLED, true).withProperty(FACING, world.getBlockState(pos).getValue(FACING)));
            }
        }
        if (player.isSneaking() && world.getBlockState(pos).getValue(ENABLED) && world.getBlockState(pos).getValue(FACING).equals(facing)) {
            world.setBlockState(pos, getDefaultState().withProperty(ENABLED, false).withProperty(FACING, world.getBlockState(pos).getValue(FACING)));
            if (!player.isCreative()) {
                if (!player.inventory.addItemStackToInventory(item)) {
                    EntityItem entityItem = new EntityItem(world, pos.getX() + 0.5, pos.getY() + 1.25, pos.getZ() + 0.5, item);
                    world.spawnEntity(entityItem);
                }
            } else if (!player.isCreative()) player.openContainer.detectAndSendChanges();
        }
        return true;
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(FACING, EnumFacing.getFront(meta & 7)).withProperty(ENABLED, (meta & 8) != 0);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(FACING).getIndex() + (state.getValue(ENABLED) ? 8 : 0);
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING, ENABLED);
    }

    @Override
    public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
        if (accessor.getBlockState().getValue(ENABLED)) currenttip.add("Enabled");
        return currenttip;
    }

    @Override
    public ItemStack getWailaStack(IWailaDataAccessor accessor, IWailaConfigHandler config) {
        return new ItemStack(this);
    }
}
