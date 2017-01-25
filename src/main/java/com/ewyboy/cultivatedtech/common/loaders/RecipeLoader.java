package com.ewyboy.cultivatedtech.common.loaders;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;

/**
 * Created by EwyBoy
 **/
public class RecipeLoader {

    public static void registerRecipes() {

    }

    private static void addRecipe(ItemStack stack, Object ... recipe) {
        GameRegistry.addRecipe(new ShapedOreRecipe(stack, recipe));
    }
}
