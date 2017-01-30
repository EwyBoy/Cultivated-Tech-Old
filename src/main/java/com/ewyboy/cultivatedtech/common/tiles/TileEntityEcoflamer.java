package com.ewyboy.cultivatedtech.common.tiles;

import cofh.api.energy.IEnergyProvider;
import com.ewyboy.cultivatedtech.common.loaders.BlockLoader;
import com.ewyboy.cultivatedtech.common.utility.helpers.SoundHelper;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.BlockGrass;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

import javax.annotation.Nullable;
import java.util.Random;

/**
 * Created by EwyBoy
 */
public class TileEntityEcoflamer extends TileEntityBase implements ITickable, IEnergyProvider {

    public TileEntityEcoflamer() {}

    private Random random = new Random();

    private int RF;
    private int maxRF = 5000;
    private IBlockState state = BlockLoader.ecoflamer.getDefaultState();

    public int getRF() {
        return RF;
    }

    public void setRF(int RF) {
        this.RF = RF;
        this.markDirty();
    }

    public int getMaxRF() {
        return maxRF;
    }

    @Override
    public void update() {
        int probabilityPercentage = 5;
        double prob = probabilityPercentage * 0.01;

        boolean grassBurned = false;

        if(Math.random() < prob) {
            BlockPos targetPos = new BlockPos(
                    pos.getX() + Math.round(MathHelper.getRandomDoubleInRange(random, -3.0D, 3.0D)),
                    pos.getY() - 1,
                    pos.getZ() + Math.round(MathHelper.getRandomDoubleInRange(random, -3.0D, 3.0D))
            );
        if (!worldObj.isRemote) {
                if (targetPos.equals(pos.down()) || worldObj.getBlockState(targetPos) instanceof BlockDirt) {
                    update();
                } else {
                    if (worldObj.getBlockState(targetPos).getBlock() instanceof BlockGrass) {
                        worldObj.playSound((double) targetPos.getX(), (double)targetPos.getY(), (double)targetPos.getZ(), SoundEvents.BLOCK_GRASS_BREAK, SoundCategory.BLOCKS, 1.0f, (float) MathHelper.getRandomDoubleInRange(random, -1.0d, 1.0d), false);
                        worldObj.setBlockState(targetPos, Blocks.DIRT.getDefaultState(), 3);
                        grassBurned = true;
                    }
                }
            }
            if (grassBurned) {
                setRF(getRF() + 50);
                worldObj.notifyBlockUpdate(pos, state, state,3);
                SoundHelper.brodcastServerSidedSoundToAllPlayerNerby(worldObj, targetPos, SoundEvents.BLOCK_GRASS_BREAK, SoundCategory.BLOCKS, 9);
            }
        }
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
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
        readFromNBT(pkt.getNbtCompound());
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);
        tag.setInteger("rf", getRF());
        return tag;
    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);
        RF = tag.getInteger("rf");
    }

    @Override
    public boolean canConnectEnergy(EnumFacing facing) {
        return true;
    }

    @Override
    public int getEnergyStored(EnumFacing from) {
        return RF;
    }

    @Override
    public int getMaxEnergyStored(EnumFacing from) {
        return getMaxRF();
    }

    @Override
    public int extractEnergy(EnumFacing from, int maxExtract, boolean simulate) {
        return 0;
    }
}
