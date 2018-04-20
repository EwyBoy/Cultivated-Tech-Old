package com.ewyboy.cultivatedtech.common.items;

import com.ewyboy.bibliotheca.common.item.ItemBase;
import com.ewyboy.cultivatedtech.common.loaders.CreativeTabLoader;
import net.minecraft.item.ItemStack;

/**
 * Created by EwyBoy
 */
public class ItemFuel extends ItemBase {

    private int burntime;

    public ItemFuel(String name, int burntime) {
        super(name);
        this.burntime = burntime;
        setCreativeTab(CreativeTabLoader.tabCultivatedTech);
    }

    @Override
    public int getItemBurnTime(ItemStack itemStack) {
        return burntime;
    }
}