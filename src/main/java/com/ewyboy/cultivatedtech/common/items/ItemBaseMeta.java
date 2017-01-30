package com.ewyboy.cultivatedtech.common.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import java.util.List;

/**
 * Created by EwyBoy
 */
public class ItemBaseMeta extends ItemBase {

    public ItemBaseMeta(String name, int maxDmg) {
        super(name);
        setMaxDamage(maxDmg);
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
        tooltip.add("Meta: " + this.getMetadata(stack));
    }
}
