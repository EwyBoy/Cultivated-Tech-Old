package com.ewyboy.cultivatedtech.common.blocks;

import com.ewyboy.bibliotheca.common.block.BlockBaseModeledFacing;
import com.ewyboy.bibliotheca.common.interfaces.IBlockRenderer;
import com.ewyboy.cultivatedtech.client.DebarkerTESR;
import com.ewyboy.cultivatedtech.common.loaders.CreativeTabLoader;
import com.ewyboy.cultivatedtech.common.tiles.TileEntityDebarker;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nullable;
import java.util.Random;

/**
 * Created by EwyBoy
 */
public class BlockDebarker extends BlockBaseModeledFacing implements ITileEntityProvider, IBlockRenderer {

    public static final PropertyBool ENABLED = PropertyBool.create("enabled");
    public static final PropertyInteger STATE = PropertyInteger.create("state", 0, 9);

    public BlockDebarker() {
        super(Material.ROCK);
        setCreativeTab(CreativeTabLoader.tabCultivatedTech);
        setDefaultState(blockState.getBaseState().withProperty(ENABLED, false));
    }

    private TileEntityDebarker getTE(World world, BlockPos pos) {
        return (TileEntityDebarker) world.getTileEntity(pos);
    }

    @Override
    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {}

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
        if (!world.isRemote) {
            TileEntityDebarker tile = getTE(world, pos);
            IItemHandler itemHandler = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, side);
            ItemStack heldItem = player.getHeldItem(hand);
            if (!player.isSneaking()) {
                if (heldItem.isEmpty()) {
                    player.setHeldItem(hand, itemHandler.extractItem(0, 64, false));
                } else {
                    if (heldItem.getItem() == Item.getItemFromBlock(Blocks.LOG) || heldItem.getItem() == Item.getItemFromBlock(Blocks.LOG2)) {
                        player.setHeldItem(hand, itemHandler.insertItem(0, heldItem, false));
                        world.setBlockState(pos, world.getBlockState(pos).withProperty(ENABLED, true));
                    }
                }
                tile.markDirty();
            }
        }
        return true;
    }


    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state) {
        TileEntityDebarker tile = getTE(world, pos);
        IItemHandler itemHandler = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.NORTH);
        ItemStack stack = itemHandler.getStackInSlot(0);
        if (!stack.isEmpty()) {
            EntityItem item = new EntityItem(world, pos.getX(), pos.getY(), pos.getZ(), stack);
            world.spawnEntity(item);
        }
        super.breakBlock(world, pos, state);
    }


    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(STATE, (meta));
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return (state.getValue(STATE));
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, STATE, ENABLED, FACING);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockRenderer() {
        super.registerBlockRenderer();
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityDebarker.class, new DebarkerTESR());
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityDebarker();
    }
}
