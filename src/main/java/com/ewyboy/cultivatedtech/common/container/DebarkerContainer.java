package com.ewyboy.cultivatedtech.common.container;

import com.ewyboy.cultivatedtech.common.tiles.TileEntityDebarker;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;

/**
 * Created by EwyBoy
 */
public class DebarkerContainer extends Container {

    private TileEntityDebarker te;

    public DebarkerContainer(IInventory playerInv, TileEntityDebarker te) {
        this.te = te;
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return true;
    }
}
