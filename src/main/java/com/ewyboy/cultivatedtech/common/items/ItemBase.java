package com.ewyboy.cultivatedtech.common.items;

import com.ewyboy.cultivatedtech.common.loaders.CreativeTabLoader;
import com.ewyboy.cultivatedtech.common.utility.interfaces.IItemRenderer;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Created by EwyBoy
 **/
public class ItemBase extends Item implements IItemRenderer {

    public ItemBase(String name) {
        setCreativeTab(CreativeTabLoader.tabUnknown);
    }

    public String itemName(int meta){
        return null;
    }

    public int[] modelMetas() {
        return new int[0];
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerItemRenderer() {
        ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }
}
