package com.ewyboy.cultivatedtech.common.tiles;

import cofh.redstoneflux.api.IEnergyProvider;
import cofh.redstoneflux.api.IEnergyReceiver;
import cofh.redstoneflux.impl.EnergyStorage;
import com.ewyboy.bibliotheca.common.helpers.SoundHelper;
import com.ewyboy.cultivatedtech.common.blocks.BlockEcoflamer;
import com.ewyboy.cultivatedtech.common.register.Register;
import com.google.common.collect.Lists;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

/**
 * Created by EwyBoy
 */
public class TileEntityEcoflamer extends TileEntity implements ITickable, IEnergyProvider {

    private EnergyStorage storage;
    private IBlockState state;
    private int generatedAmount, range, probability;

    public TileEntityEcoflamer() {}

    public TileEntityEcoflamer(int tier) {
        switch (tier) {
            case 1:
                 storage = new EnergyStorage(6400, 640);
                 generatedAmount = 1000;
                 range = 3;
                 probability = 100;
                 state = Register.Blocks.ecoflamer1.getDefaultState();
            break;

            case 2:
                storage = new EnergyStorage(64000, 6400);
                generatedAmount = 10000;
                range = 5;
                probability = 75;
                state = Register.Blocks.ecoflamer2.getDefaultState();
            break;

            case 3:
                 storage = new EnergyStorage(640000, 64000);
                 generatedAmount = 100000;
                 range = 7;
                 probability = 50;
                 state = Register.Blocks.ecoflamer3.getDefaultState();
            break;

            default:
                storage = new EnergyStorage(0, 0);
                generatedAmount = 0;
                range = 0;
                probability = -1;
                state = null;
            break;
        }
    }

    private void extractEnergyToSurroundingReceivers() {
        for(EnumFacing facing : EnumFacing.VALUES) {
            TileEntity tileEntity = world.getTileEntity(pos.offset(facing));
            if (tileEntity instanceof IEnergyReceiver && ((IEnergyReceiver) tileEntity).canConnectEnergy(facing)) {
                try {
                    int received = ((IEnergyReceiver) tileEntity).receiveEnergy(facing.getOpposite(), extractEnergy(facing, storage.getMaxExtract(), true), false);
                    extractEnergy(facing, received, false);
                } catch (NullPointerException ignored) {}
            }
        }
    }

    private void generateEnergy() {
         if (storage.getEnergyStored() < storage.getMaxEnergyStored()) {
             storage.modifyEnergyStored(generatedAmount);
         }
    }

    @Override
    public void update() {
        if (!world.getBlockState(pos).getValue(BlockEcoflamer.ENABLED)) {
            if (!world.isRemote && world.getTotalWorldTime() % probability == 0) {
                List<BlockPos> list = Lists.newArrayList(BlockPos.getAllInBox(pos.add(range, 1, range), pos.add(-range, -1, -range)));
                Collections.shuffle(list);

                //maximal tries, you could change it to list.size() for the entire list
                final int maxTries = 3;
                int tries = 0;

                //iterate over all blocks
                for (BlockPos targetPos : list) {
                    if (tries >= maxTries)
                        break;
                    tries++;
                    if (world.getBlockState(targetPos).getBlock() == Blocks.GRASS) {
                        world.setBlockState(targetPos, Blocks.DIRT.getDefaultState(), 3);
                        generateEnergy();
                        world.notifyBlockUpdate(pos, state, state, 3);
                        SoundHelper.broadcastServerSidedSoundToAllPlayerNearby(world, targetPos, SoundEvents.BLOCK_GRASS_BREAK, SoundCategory.BLOCKS, 9);
                        break;
                    } else if (world.getBlockState(targetPos).getBlock() instanceof IPlantable) {
                        world.destroyBlock(targetPos, true);
                        generateEnergy();
                        world.notifyBlockUpdate(pos, state, state, 3);
                        break;
                    }
                }
            }
        }
        extractEnergyToSurroundingReceivers();
    }

    @Nullable
    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        return new SPacketUpdateTileEntity(this.pos, 1, this.getUpdateTag());
    }

    @Override
    public NBTTagCompound getUpdateTag() {
        return this.writeToNBT(new NBTTagCompound());
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
        readFromNBT(pkt.getNbtCompound());
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);
        tag.setInteger("energy", this.storage.getEnergyStored());
        return tag;
    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);
        this.storage.setEnergyStored(tag.getInteger("energy"));
    }

    @Override
    public boolean canConnectEnergy(EnumFacing from) {
        return true;
    }

    @Override
    public int getEnergyStored(EnumFacing from) {
        return storage.getEnergyStored();
    }

    @Override
    public int getMaxEnergyStored(EnumFacing from) {
        return storage.getMaxEnergyStored();
    }

    @Override
    public int extractEnergy(EnumFacing from, int maxExtract, boolean simulate) {
        return storage.extractEnergy(maxExtract, simulate);
    }
}
