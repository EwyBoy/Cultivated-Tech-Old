package com.ewyboy.cultivatedtech.common.tiles;

import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyProvider;
import net.minecraft.util.EnumFacing;

/**
 * Created by EwyBoy
 */
public class TileEntityEnergyProducerBase extends TileEntityBase implements IEnergyProvider {

    EnergyStorage storage = new EnergyStorage(0);

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
