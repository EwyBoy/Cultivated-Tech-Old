package com.ewyboy.cultivatedtech.common.items;

import com.ewyboy.bibliotheca.common.interfaces.IItemRenderer;
import com.ewyboy.cultivatedtech.common.loaders.CreativeTabLoader;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.ItemSeeds;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Created by EwyBoy
 **/
public class ItemBaseSeeds extends ItemSeeds implements IItemRenderer {

    public ItemBaseSeeds(String name, Block crops, Block soil) {
        super(crops, soil);
        setCreativeTab(CreativeTabLoader.tabCultivatedTech);
    }

    public String itemName(int meta){
        return null;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerItemRenderer() {
        ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }

    @Override
    public int[] modelMetas() {
        return new int[0];
    }
}
