package com.ewyboy.cultivatedtech.common.blocks;

import com.ewyboy.bibliotheca.common.block.BlockBase;
import com.ewyboy.cultivatedtech.common.loaders.CreativeTabLoader;
import com.ewyboy.cultivatedtech.common.register.Register;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockIndustrialDirt extends BlockBase {

    public BlockIndustrialDirt() {
        super(Material.GOURD);
        setCreativeTab(CreativeTabLoader.tabCultivatedTech);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack heldItem = playerIn.getHeldItem(hand);
        if (heldItem.getItem() instanceof ItemHoe) {
            playerIn.swingArm(hand);
            worldIn.setBlockState(pos, Register.Blocks.soil.getDefaultState(), 11);
            worldIn.playSound(playerIn, pos, SoundEvents.ITEM_HOE_TILL, SoundCategory.BLOCKS, 1.0f, 1.0f);
            if (playerIn.isCreative()) {
                heldItem.damageItem(1, playerIn);
            }
        }

        return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
    }
}
