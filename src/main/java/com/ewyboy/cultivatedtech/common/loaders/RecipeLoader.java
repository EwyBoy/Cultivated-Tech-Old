package com.ewyboy.cultivatedtech.common.loaders;

import com.ewyboy.cultivatedtech.common.utility.RecipeHelper;
import net.minecraft.item.ItemStack;

import static com.ewyboy.cultivatedtech.common.register.Register.Items.brick;
import static com.ewyboy.cultivatedtech.common.register.Register.Items.witheredBrick;
import static net.minecraft.init.Items.BRICK;
import static net.minecraftforge.fml.common.registry.GameRegistry.addSmelting;

public class RecipeLoader {

    public static void init() {
        //registerRecipes();
        registerSmeltingRecipes();
    }

    public static void registerRecipes() {
        RecipeHelper.setupDir();
        RecipeHelper.generateConstants();
    }

    public static void registerSmeltingRecipes() {
        addSmelting(new ItemStack(BRICK), new ItemStack(brick, 1,0), 0);
        addSmelting(new ItemStack(brick, 1, 0), new ItemStack(brick, 1,1), 0);
        addSmelting(new ItemStack(brick, 1, 1), new ItemStack(brick, 1,2), 0);
        addSmelting(new ItemStack(brick, 1, 2), new ItemStack(brick, 1,3), 0);
        addSmelting(new ItemStack(brick, 1, 3), new ItemStack(brick, 1,4), 0);
        addSmelting(new ItemStack(brick, 1, 4), new ItemStack(brick, 1,5), 0);
        addSmelting(new ItemStack(brick, 1, 5), new ItemStack(brick, 1,6), 0);
        addSmelting(new ItemStack(brick, 1, 6), new ItemStack(witheredBrick), 0);
    }

}
