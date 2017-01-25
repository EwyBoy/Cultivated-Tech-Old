package com.ewyboy.cultivatedtech.common.tiles;

import com.ewyboy.cultivatedtech.common.utility.helpers.CapHelper;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

/**
 * Created by EwyBoy
 **/
public class TileEntityBase extends TileEntity {

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);
        return tag;
    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);
    }

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        return CapHelper.hasCapability(capability, facing, getClass(), this) || super.hasCapability(capability, facing);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        return (T) CapHelper.getCapability(capability, facing, getClass(), this, (capability1, enumFacing) -> (T)super.getCapability(capability1, enumFacing));
    }
}
