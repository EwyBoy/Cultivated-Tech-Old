package com.ewyboy.cultivatedtech.common.loaders;

import com.ewyboy.cultivatedtech.common.items.ItemBase;
import com.ewyboy.cultivatedtech.common.items.ItemBaseMeta;
import com.ewyboy.cultivatedtech.common.items.ItemBaseSeeds;
import com.ewyboy.cultivatedtech.common.items.ItemTestSword;
import com.ewyboy.cultivatedtech.common.utility.Reference;
import com.ewyboy.cultivatedtech.common.utility.interfaces.IItemRenderer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Locale;

/**
 * Created by EwyBoy
 **/
public class ItemLoader {

    public static final HashMap<String, Item> ITEMS = new HashMap<>();

    public static ItemBase hemp = new ItemBase("hemp");
    public static ItemBase barrelTap = new ItemBase("barreltap");
    public static ItemBaseMeta brick = new ItemBaseMeta("brick", 6);
    public static ItemBase witheredBrick = new ItemBase("witheredbrick");
    public static ItemBase enderizedBrick = new ItemBase("enderizedbrick");
    public static ItemBaseSeeds seedHemp = new ItemBaseSeeds("seedhemp", BlockLoader.cropHemp, BlockLoader.soil);
    public static ItemTestSword testSword = new ItemTestSword(Item.ToolMaterial.WOOD);

    public static void init() {
        registerItems();
        initModels();
    }

    @SideOnly(Side.CLIENT)
    public static void initModels() {
        ItemLoader.ITEMS.values().stream().filter(item -> item instanceof IItemRenderer).forEachOrdered(item -> ((IItemRenderer) item).registerItemRenderer());
    }

    private static void registerItems() {
        try {
            for (Field field : ItemLoader.class.getDeclaredFields()) {
                Object obj = field.get(null);
                if (obj instanceof Item) {
                    Item item = (Item) obj;
                    String name = "item" + field.getName().toLowerCase(Locale.ENGLISH);
                    registerItem(item, name);
                }
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private static void registerItem(Item item, String name) {
        String itemName = name.toLowerCase(Locale.ENGLISH);
        GameRegistry.register(item.setRegistryName(Reference.Info.MODID, itemName).setUnlocalizedName(Reference.Info.MODID + "." + itemName));
        ITEMS.put(item.getRegistryName().toString(), item);
    }
}
