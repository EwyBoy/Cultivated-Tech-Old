package com.ewyboy.cultivatedtech.common.tiles;

import com.ewyboy.cultivatedtech.CultivatedTech;
import com.ewyboy.cultivatedtech.common.loaders.PacketLoader;
import com.ewyboy.cultivatedtech.common.network.PacketRequestUpdateDebarker;
import com.ewyboy.cultivatedtech.common.network.PacketUpdateDebarker;
import com.ewyboy.cultivatedtech.common.utility.Logger;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;
import java.util.Random;

import static com.ewyboy.cultivatedtech.common.blocks.BlockDebarker.*;
import static com.ewyboy.cultivatedtech.common.register.Register.Blocks.*;
import static net.minecraft.init.Blocks.*;

/**
 * Created by EwyBoy
 */
public class TileEntityDebarker extends TileEntity implements ITickable {

    public long lastChangeTime;

    public  ItemStackHandler inventory = new ItemStackHandler(1) {
        @Override
        protected void onContentsChanged(int slot) {
            if (!world.isRemote) {
                lastChangeTime = world.getTotalWorldTime();
                PacketLoader.wrapper.sendToAllAround(new PacketUpdateDebarker(TileEntityDebarker.this), new NetworkRegistry.TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 64));
                Logger.info(isLog(getStackInSlot(0)));
                if (isLog(getStackInSlot(0))) {
                    world.setBlockState(pos, world.getBlockState(pos).withProperty(ENABLED, true));
                }
            }
        }
        @Override
        public int getSlotLimit(int slot) {
            return 1;
        }
    };

    public TileEntityDebarker() {}

    public ItemStack getStack() {
        return inventory.getStackInSlot(0);
    }

    @Override
    public void onLoad() {
        if (world.isRemote) {
            PacketLoader.wrapper.sendToServer(new PacketRequestUpdateDebarker(this));
        }
    }

    public void changed() {
        if (this.getStack() != null) {
            final IBlockState state = this.getWorld().getBlockState(this.getPos());
            this.getWorld().notifyBlockUpdate(this.getPos(), state, state, 8);
            this.markDirty();
        }
    }

    @Override
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newSate) {
        return oldState.getBlock() != newSate.getBlock();
    }

    private boolean isLog(ItemStack stack) {
        return stack.getItem() == Item.getItemFromBlock(LOG) || stack.getItem() == Item.getItemFromBlock(LOG2);
    }

    private int ticks;
    private int count;

    private void spawnParticleEffects(ItemStack stack) {
        Random random = new Random();
        CultivatedTech.proxy.spawnBlockParticle(world, stack, pos.getX() + random.nextDouble() + 0.2, pos.getY() + 0.8, pos.getZ() + random.nextDouble() + 0.2, 0.3f, 0.7f,
                new Vec3d(0,0,0)
        );
        CultivatedTech.proxy.spawnBlockParticle(world, stack, pos.getX() + random.nextDouble() - 0.2, pos.getY() + 0.8, pos.getZ() + random.nextDouble() - 0.2, 0.3f, 0.7f,
                new Vec3d(0,0,0)
        );
    }

    @Override
    public void update() {
        if (world.getBlockState(pos).getValue(ENABLED)) {
            if (!world.isRemote) {
                if (getStack() != null && getStack().getItem() != Items.AIR && isLog(getStack())) {
                    ticks++;
                } else {
                    world.setBlockState(pos, world.getBlockState(pos).withProperty(ENABLED, false).withProperty(STATE, 0));
                    ticks = 0; count = 0;
                } if (count < 3) {
                    if (ticks <= 18)  {
                        //Logger.info("Ticks: " + ticks + "   |   " + ((-1) * Math.abs(ticks-9) + 9));
                        world.setBlockState(pos, world.getBlockState(pos).withProperty(STATE, (-1) * Math.abs(ticks-9) + 9));
                        if (ticks == 18) {
                            ticks = 0;
                            count++;
                        }
                    }
                } else {
                    world.setBlockState(pos, world.getBlockState(pos).withProperty(ENABLED, false));
                    Block strippedWoodType = null;
                    if (getStack().getItem() == Item.getItemFromBlock(LOG)) {
                        switch (getStack().getItemDamage()) {
                            case 0: strippedWoodType = strippedoak; break;
                            case 1: strippedWoodType = strippedspruce; break;
                            case 2: strippedWoodType = strippedbirch; break;
                            case 3: strippedWoodType = strippedjungle; break;
                            default: strippedWoodType = null;
                        }
                    } else if(getStack().getItem() == Item.getItemFromBlock(LOG2)) {
                        switch (getStack().getItemDamage()) {
                            case 0: strippedWoodType = strippedacacia; break;
                            case 1: strippedWoodType = strippeddarkoak; break;
                            default: break;
                        }
                    } if (strippedWoodType != null) {
                        inventory.setStackInSlot(0, new ItemStack(strippedWoodType));
                    }
                    count = 0;
                }
            }
            if (isLog(getStack())) spawnParticleEffects(getStack());
        }
    }

    @Override
    public NBTTagCompound getUpdateTag() {
        return writeToNBT(new NBTTagCompound());
    }

    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        NBTTagCompound nbtTag = new NBTTagCompound();
        this.writeToNBT(nbtTag);
        return new SPacketUpdateTileEntity(getPos(), 1, nbtTag);
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity packet) {
        this.readFromNBT(packet.getNbtCompound());
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        if (facing == EnumFacing.DOWN) {
            return !isLog(getStack()) && (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || super.hasCapability(capability, facing));
        }
        return (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || super.hasCapability(capability, facing));
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY ? (T)inventory : super.getCapability(capability, facing);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setTag("inventory", inventory.serializeNBT());
        compound.setLong("lastChangeTime", lastChangeTime);
        return super.writeToNBT(compound);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        inventory.deserializeNBT(compound.getCompoundTag("inventory"));
        lastChangeTime = compound.getLong("lastChangeTime");
        super.readFromNBT(compound);
    }

}
