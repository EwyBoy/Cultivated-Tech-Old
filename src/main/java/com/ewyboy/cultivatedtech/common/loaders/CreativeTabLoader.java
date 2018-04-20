package com.ewyboy.cultivatedtech.common.loaders;

import com.ewyboy.cultivatedtech.common.register.Register;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import static com.ewyboy.cultivatedtech.common.utility.Reference.Info.MOD_ID;

/**
 * Created by EwyBoy
 **/
public class CreativeTabLoader {

    public static CreativeTabs tabCultivatedTech = new CreativeTabs (MOD_ID) {
        public ItemStack getIconItemStack() {
            return new ItemStack(Register.Blocks.barrel);
        }
        @Override
        public ItemStack getTabIconItem() {return null;}
    };

    public static void initVanillaStuffToTab() {
        Blocks.BRICK_BLOCK.setCreativeTab(tabCultivatedTech);
        Items.BRICK.setCreativeTab(tabCultivatedTech);
        Blocks.PORTAL.setCreativeTab(tabCultivatedTech);
        Blocks.END_PORTAL.setCreativeTab(tabCultivatedTech);
    }
}
