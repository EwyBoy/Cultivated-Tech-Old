package com.ewyboy.cultivatedtech.common.loaders;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;

/**
 * Created by EwyBoy
 **/
public class RecipeLoader {

    public static void registerRecipes() {
        addSmelting(new ItemStack(Items.BRICK), new ItemStack(ItemLoader.brick, 1, 1), 0f);
        addSmelting(new ItemStack(ItemLoader.brick, 1, 1), new ItemStack(ItemLoader.brick, 1, 2), 0f);
        addSmelting(new ItemStack(ItemLoader.brick, 1, 2), new ItemStack(ItemLoader.brick, 1, 3), 0f);
        addSmelting(new ItemStack(ItemLoader.brick, 1, 3), new ItemStack(ItemLoader.brick, 1, 4), 0f);
        addSmelting(new ItemStack(ItemLoader.brick, 1, 4), new ItemStack(ItemLoader.brick, 1, 5), 0f);
        addSmelting(new ItemStack(ItemLoader.brick, 1, 5), new ItemStack(ItemLoader.brick, 1, 6), 0f);
        addSmelting(new ItemStack(ItemLoader.brick, 1, 6), new ItemStack(ItemLoader.witheredBrick, 1), 0f);
    }

    private static void addSmelting(ItemStack stackIn, ItemStack stackOut, float xp) {
        GameRegistry.addSmelting(stackIn, stackOut, xp);
    }

    private static void addRecipe(ItemStack stack, Object ... recipe) {
        GameRegistry.addRecipe(new ShapedOreRecipe(stack, recipe));
    }
}
