package com.ewyboy.cultivatedtech.common.blocks;

import com.ewyboy.bibliotheca.common.compatibilities.waila.IWailaInformationUser;
import com.ewyboy.bibliotheca.common.helpers.SoundHelper;
import com.ewyboy.cultivatedtech.common.material.MaterialLoader;
import com.ewyboy.cultivatedtech.common.register.Register;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;

import java.util.List;
import java.util.Random;

public class BlockIndustrialSoil extends BlockSoil implements IWailaInformationUser {

    private int tier;

    public BlockIndustrialSoil(int tier) {
        this.tier = tier;
        setHardness(1.0f);
    }

    @Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
        int i = state.getValue(MOISTURE);
        Material moisturizer;

        switch (tier) {
            case 1: moisturizer = Material.WATER; break;
            case 2: moisturizer = Material.LAVA; break;
            case 3: moisturizer = MaterialLoader.ENDER; break;

            default: moisturizer = MaterialLoader.ENDER;
        }

        if (!this.hasMoisture(world, pos, moisturizer)) {
            if (i > 0) {
                switch (tier) {
                    case 1: world.setBlockState(pos, Register.Blocks.industrialsoil1.getDefaultState().withProperty(MOISTURE, i - 1), 2); break;
                    case 2: world.setBlockState(pos, Register.Blocks.industrialsoil2.getDefaultState().withProperty(MOISTURE, i - 1), 2); break;
                    case 3: world.setBlockState(pos, Register.Blocks.industrialsoil3.getDefaultState().withProperty(MOISTURE, i - 1), 2); break;
                }
            } else if (!this.hasCrops(world, pos)) {
                turnToSoil(world, pos, Register.Blocks.soil);
            }
        } else if (i < 7) {
            switch (tier) {
                case 1: world.setBlockState(pos, Register.Blocks.industrialsoil1.getDefaultState().withProperty(MOISTURE, 7), 2); break;
                case 2: world.setBlockState(pos, Register.Blocks.industrialsoil2.getDefaultState().withProperty(MOISTURE, 7), 2); break;
                case 3: world.setBlockState(pos, Register.Blocks.industrialsoil3.getDefaultState().withProperty(MOISTURE, 7), 2); break;
            }
        }
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (playerIn.getHeldItem(hand).getItem() == Items.NETHER_WART && tier == 2) {
            worldIn.setBlockState(pos.up(), Blocks.NETHER_WART.getDefaultState());
            SoundHelper.broadcastServerSidedSoundToAllPlayerNearby(worldIn, pos.up(), SoundEvents.BLOCK_WATERLILY_PLACE, SoundCategory.PLAYERS, 5);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onEntityWalk(World worldIn, BlockPos pos, Entity entityIn) {
        Random random = new Random();
        if (tier == 2) {
            if (!entityIn.isImmuneToFire() && entityIn instanceof EntityLivingBase && !EnchantmentHelper.hasFrostWalkerEnchantment((EntityLivingBase) entityIn)) {
                entityIn.attackEntityFrom(DamageSource.HOT_FLOOR, 1.0F);
            }
        }
        if (tier == 3) {
            if (entityIn instanceof EntityLivingBase) {
                int x = random.nextInt(16) - 8;
                int y = random.nextInt(4) - 2;
                int z = random.nextInt(16) - 8;
                ((EntityLivingBase) entityIn).attemptTeleport(pos.getX() + x, pos.getY() + y, pos.getZ() + z);
            }
        }
        super.onEntityWalk(worldIn, pos, entityIn);
    }

    @Override
    public void onFallenUpon(World worldIn, BlockPos pos, Entity entityIn, float fallDistance) {
        if (!worldIn.isRemote && entityIn.canTrample(worldIn, this, pos, fallDistance)) {
            turnToSoil(worldIn, pos, Register.Blocks.industrialdirt);
        }
        super.onFallenUpon(worldIn, pos, entityIn, fallDistance);
    }

    @Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
        super.neighborChanged(state, worldIn, pos, blockIn, fromPos);
        if (worldIn.getBlockState(pos.up()).getMaterial().isSolid()) {
            turnToSoil(worldIn, pos, Register.Blocks.industrialdirt);
        }
    }

    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
        super.onBlockAdded(worldIn, pos, state);
        if (worldIn.getBlockState(pos.up()).getMaterial().isSolid()) {
            turnToSoil(worldIn, pos, Register.Blocks.industrialdirt);
        }
    }

    private static void turnToSoil(World world, BlockPos pos, Block block) {
        world.setBlockState(pos, block.getDefaultState());
        AxisAlignedBB axisalignedbb = field_194405_c.offset(pos);

        for (Entity entity : world.getEntitiesWithinAABBExcludingEntity(null, axisalignedbb)) {
            double d0 = Math.min(axisalignedbb.maxY - axisalignedbb.minY, axisalignedbb.maxY - entity.getEntityBoundingBox().minY);
            entity.setPositionAndUpdate(entity.posX, entity.posY + d0 + 0.001D, entity.posZ);
        }
    }

    private boolean hasCrops(World worldIn, BlockPos pos) {
        Block block = worldIn.getBlockState(pos.up()).getBlock();
        return block instanceof IPlantable && canSustainPlant(worldIn.getBlockState(pos), worldIn, pos, EnumFacing.UP, (IPlantable)block);
    }

    private boolean hasMoisture(World world, BlockPos pos, Material moisturizer) {
        for (BlockPos.MutableBlockPos blockpos$mutableblockpos : BlockPos.getAllInBoxMutable(pos.add(-4, 0, -4), pos.add(4, 1, 4))) {
            if (world.getBlockState(blockpos$mutableblockpos).getMaterial() == moisturizer) {
                return true;
            }
        }
        return false;
    }

    @Override
    public List<String> getWailaBody(ItemStack itemStack, List<String> list, IWailaDataAccessor iWailaDataAccessor, IWailaConfigHandler iWailaConfigHandler) {
        String moisturizer = null;
        int i = iWailaDataAccessor.getBlockState().getValue(MOISTURE);

        if (iWailaDataAccessor.getPlayer().isSneaking()) {
            list.add("Moisture: " + i);
            if (i == 7) {
                switch (tier) {
                    case 1: moisturizer = "§3Water§f"; break;
                    case 2: moisturizer = "§4Lava§f"; break;
                    case 3: moisturizer = "§2Ender§f"; break;
                }
                list.add("Moisturizer: " + moisturizer);
            }
        }
        return list;
    }
}