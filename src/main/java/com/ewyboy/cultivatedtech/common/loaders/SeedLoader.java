package com.ewyboy.cultivatedtech.common.loaders;

import com.ewyboy.cultivatedtech.common.register.Register;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;

public class SeedLoader {

    public static void registerSeeds() {
        MinecraftForge.addGrassSeed(new ItemStack(Register.Items.seedHemp), 2);
    }
}
