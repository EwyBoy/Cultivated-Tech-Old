package com.ewyboy.cultivatedtech.common.blocks;

import com.ewyboy.bibliotheca.common.block.BlockBaseModeled;
import com.ewyboy.bibliotheca.common.helpers.SoundHelper;
import com.ewyboy.cultivatedtech.common.loaders.CreativeTabLoader;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Created by EwyBoy
 */
public class BlockSwapper extends BlockBaseModeled {

    public BlockSwapper() {
        super(Material.ANVIL);
        setHardness(3.0f);
        setLightLevel(0.2f);
        setCreativeTab(CreativeTabLoader.tabCultivatedTech);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getPackedLightmapCoords(IBlockState state, IBlockAccess source, BlockPos pos) {
        return MinecraftForgeClient.getRenderLayer().equals(BlockRenderLayer.TRANSLUCENT) ? 0xffffff : super.getPackedLightmapCoords(state, source, pos);
    }

    @Override
    public boolean canRenderInLayer(IBlockState state, BlockRenderLayer layer) {
         return layer.equals(BlockRenderLayer.SOLID) || layer.equals(BlockRenderLayer.TRANSLUCENT);
    }

    private static BlockPos setTargetPos(BlockPos targetPos, EnumFacing facing, int range) {
        switch (facing) {
            case NORTH: targetPos = new BlockPos(targetPos.add(0,0,range)); break;
            case SOUTH: targetPos = new BlockPos(targetPos.add(0,0,-range)); break;
            case EAST: targetPos = new BlockPos(targetPos.add(-range,0,0)); break;
            case WEST: targetPos = new BlockPos(targetPos.add(range,0,0)); break;
            case UP: targetPos = new BlockPos(targetPos.add(0, -range, 0)); break;
            case DOWN: targetPos = new BlockPos(targetPos.add(0, range, 0)); break;

            default: targetPos = new BlockPos(targetPos.add(0,0,0)); break;
        }
        return targetPos;
    }

    //TODO Clean up this shady code
    private void triggerBlockMove(World world, BlockPos pos, EntityPlayer player, EnumFacing facing) {
        for(EnumFacing face : EnumFacing.VALUES) {
            IBlockState selectedBlock = world.getBlockState(pos.offset(face));
            BlockPos selectedBlockPos = pos.offset(face);
            for (int i = 1; i < 16; i++) {
                if (player.isSneaking()) {
                    if (world.getBlockState(selectedBlockPos.offset(facing.getOpposite(), -i).offset(face.getOpposite())).getBlock() == this) {
                        selectedBlockPos = setTargetPos(selectedBlockPos, facing.getOpposite(), i);
                        break;
                    }
                } else if (world.getBlockState(selectedBlockPos.offset(facing.getOpposite(), i).offset(face.getOpposite())).getBlock() == this) {
                    selectedBlockPos = setTargetPos(selectedBlockPos, facing, i);
                    break;
                }
            }

            IBlockState targetBlock = world.getBlockState(selectedBlockPos);

            if (world.getBlockState(selectedBlockPos.offset(face.getOpposite())).getBlock().equals(this)) {
                if (world.getBlockState(pos.offset(face)).getBlock() != Blocks.AIR && !world.getBlockState(pos.offset(face)).getBlock().hasTileEntity()) {
                    world.setBlockState(pos.offset(face), targetBlock);
                    world.setBlockState(selectedBlockPos, selectedBlock);
                    SoundHelper.broadcastServerSidedSoundToAllPlayerNearby(world, selectedBlockPos, SoundEvents.ENTITY_ENDERMEN_TELEPORT, SoundCategory.PLAYERS, 10);
                }
            }
        }
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (player.getHeldItem(hand).isEmpty()) {
            triggerBlockMove(world, pos, player, facing);
            return true;
        } else {
            return false;
        }
    }

   /* @Override
    @SideOnly(Side.CLIENT)
    public void onEntityWalk(World world, BlockPos pos, Entity entity) {
        if (entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) entity;
            entity.setSneaking(true);

            RayTraceResult stuff = entity.rayTrace(10, 1.0f);
            if (stuff != null) {
                if (world.getBlockState(stuff.getBlockPos()).getBlock().equals(this)) {
                    ParticleHelper.spawnParticle(world, stuff.getBlockPos().getX(), stuff.getBlockPos().getY() + 1.0, stuff.getBlockPos().getZ(), EnumParticleTypes.CRIT_MAGIC, 0f, 0.35f, 0f);
                    if (entity.isSneaking()) {
                        if (world.getBlockState(stuff.getBlockPos()).getBlock().equals(this)) {
                            player.setPositionAndUpdate(stuff.getBlockPos().getX() + 0.5, stuff.getBlockPos().getY() + 1.25, stuff.getBlockPos().getZ() + 0.5);
                        }
                    }
                }
            }
        }
    }*/
}
