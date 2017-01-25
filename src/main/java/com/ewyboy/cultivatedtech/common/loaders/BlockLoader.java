package com.ewyboy.cultivatedtech.common.loaders;

import com.ewyboy.cultivatedtech.common.blocks.BlockBarrel;
import com.ewyboy.cultivatedtech.common.blocks.BlockHemp;
import com.ewyboy.cultivatedtech.common.blocks.BlockSoil;
import com.ewyboy.cultivatedtech.common.blocks.crops.BlockCropHemp;
import com.ewyboy.cultivatedtech.common.blocks.fluid.UnknownFluidBlock;
import com.ewyboy.cultivatedtech.common.material.UnknownMaterials;
import com.ewyboy.cultivatedtech.common.utility.Reference;
import com.ewyboy.cultivatedtech.common.utility.interfaces.IBlockRenderer;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Locale;

/**
 * Created by EwyBoy
 **/
public class BlockLoader {

    public static final HashMap<String, Block> BLOCKS = new HashMap<>();

    //Fluids
    public static BlockFluidClassic ethanol = new UnknownFluidBlock(FluidLoader.ETHANOL, UnknownMaterials.FUEL);

    //Blocks
    public static BlockBarrel barrel = new BlockBarrel();
    public static BlockSoil soil = new BlockSoil();
    public static BlockHemp blockHemp = new BlockHemp();

    //Crops
    public static BlockCropHemp cropHemp = new BlockCropHemp();

    public static void init() {
        registerBlocks();
        initModels();
        initItemModels();
    }

    @SideOnly(Side.CLIENT)
    public static void initModels() {
        BLOCKS.values().stream().filter(block -> block instanceof IBlockRenderer).forEachOrdered(block -> ((IBlockRenderer) block).registerBlockRenderer());
    }

    @SideOnly(Side.CLIENT)
    public static void initItemModels() {
        BLOCKS.values().stream().filter(block -> block instanceof IBlockRenderer).forEachOrdered(block -> ((IBlockRenderer) block).registerBlockItemRenderer());
    }

    private static void registerBlocks() {
        try {
            for (Field field : BlockLoader.class.getDeclaredFields()) {
                Object obj = field.get(null);
                if (obj instanceof Block) {
                    Block block = (Block) obj;
                    String name = field.getName().toLowerCase(Locale.ENGLISH);
                    registerBlock(block, name);
                }
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private static void registerBlock(Block block, String name) {
        GameRegistry.register(block.setRegistryName(Reference.Info.MODID, name).setUnlocalizedName(Reference.Info.MODID + "." + name));
        BLOCKS.put(block.getRegistryName().toString(), block);
        ItemBlock item;
        item = block instanceof IHasCustomItem ? ((IHasCustomItem) block).getItemBlock() : new ItemBlock(block);
        GameRegistry.register((ItemBlock) item.setRegistryName(Reference.Info.MODID, name).setUnlocalizedName(Reference.Info.MODID + "." + name));
    }


    public interface IHasCustomItem {
        ItemBlock getItemBlock();
    }
}