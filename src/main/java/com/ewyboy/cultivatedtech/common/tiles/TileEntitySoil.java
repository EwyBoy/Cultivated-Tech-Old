package com.ewyboy.cultivatedtech.common.tiles;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

import javax.annotation.Nullable;

/**
 * Created by EwyBoy
 */
public class TileEntitySoil extends TileEntity {

    public TileEntitySoil() {}

    private int statFertile;
    private int statGrowth;

    public int getStatFertile() {
        return statFertile;
    }

    public void setStatFertile(int statFertile) {
        this.statFertile = statFertile;
        this.markDirty();
    }

    public int getStatGrowth() {
        return statGrowth;
    }

    public void setStatGrowth(int statGrowth) {
        this.statGrowth = statGrowth;
        this.markDirty();
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
        tag.setInteger("fertile", statFertile);
        tag.setInteger("growth", statGrowth);
        return tag;
    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);
        statFertile =  tag.getInteger("fertile");
        statGrowth = tag.getInteger("growth");
    }
}
