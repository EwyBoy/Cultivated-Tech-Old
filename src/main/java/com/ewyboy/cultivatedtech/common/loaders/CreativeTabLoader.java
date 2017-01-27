package com.ewyboy.cultivatedtech.common.loaders;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import static com.ewyboy.cultivatedtech.common.utility.Reference.Info.MODID;

/**
 * Created by EwyBoy
 **/
public class CreativeTabLoader {

    public static CreativeTabs tabUnknown = new CreativeTabs (MODID) {
        public ItemStack getIconItemStack() {
            return new ItemStack(BlockLoader.barrel);
        }
        @Override
        public Item getTabIconItem() {return null;}
    };
}
