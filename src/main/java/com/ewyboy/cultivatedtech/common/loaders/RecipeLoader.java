package com.ewyboy.cultivatedtech.common.loaders;

import com.ewyboy.cultivatedtech.common.register.Register;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import static com.ewyboy.bibliotheca.common.loaders.RecipeLoader.*;
import static com.ewyboy.cultivatedtech.common.register.Register.Items.brick;
import static com.ewyboy.cultivatedtech.common.register.Register.Items.witheredBrick;
import static net.minecraft.init.Items.BRICK;
import static net.minecraftforge.fml.common.registry.GameRegistry.addSmelting;

public class RecipeLoader {

    public static void init() {
        registerRecipes();
        registerSmeltingRecipes();
    }

    public static void registerRecipes() {
        addShapelessOreRecipe(new ItemStack(Blocks.PLANKS, 4, 0), new ItemStack(Register.Blocks.strippedoak));
        addShapelessOreRecipe(new ItemStack(Blocks.PLANKS, 4, 1), new ItemStack(Register.Blocks.strippedspruce));
        addShapelessOreRecipe(new ItemStack(Blocks.PLANKS, 4, 2), new ItemStack(Register.Blocks.strippedbirch));
        addShapelessOreRecipe(new ItemStack(Blocks.PLANKS, 4, 3), new ItemStack(Register.Blocks.strippedjungle));
        addShapelessOreRecipe(new ItemStack(Blocks.PLANKS, 4, 4), new ItemStack(Register.Blocks.strippedacacia));
        addShapelessOreRecipe(new ItemStack(Blocks.PLANKS, 4, 5), new ItemStack(Register.Blocks.strippeddarkoak));
        addShapelessOreRecipe(new ItemStack(Items.SUGAR, 2), new ItemStack(Register.Items.sugarcane));
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
