package com.ewyboy.cultivatedtech.common.register;

import com.ewyboy.bibliotheca.common.block.BlockBase;
import com.ewyboy.bibliotheca.common.item.ItemBase;
import com.ewyboy.bibliotheca.common.item.ItemBaseMeta;
import com.ewyboy.cultivatedtech.common.blocks.*;
import com.ewyboy.cultivatedtech.common.blocks.crops.BlockCropHemp;
import com.ewyboy.cultivatedtech.common.blocks.crops.BlockCropScorch;
import com.ewyboy.cultivatedtech.common.items.ItemBaseSeeds;
import com.ewyboy.cultivatedtech.common.items.ItemBrick;
import com.ewyboy.cultivatedtech.common.items.ItemScorch;
import com.ewyboy.cultivatedtech.common.loaders.CreativeTabLoader;
import com.ewyboy.cultivatedtech.common.tiles.TileEntityEcoflamer;
import com.ewyboy.cultivatedtech.common.tiles.TileEntitySoil;
import com.ewyboy.cultivatedtech.common.tiles.TileEntitySprinkler;
import net.minecraft.block.material.Material;

import static com.ewyboy.cultivatedtech.common.loaders.CreativeTabLoader.tabCultivatedTech;

public class Register {

    public static class Blocks {
        public static final BlockHemp hemp = new BlockHemp();
        public static final BlockSoil soil = new BlockSoil();
        public static final BlockBarrel barrel = new BlockBarrel();
        public static final BlockFireplace fireplace = new BlockFireplace();
        public static final BlockEcoflamer ecoflamer1 = new BlockEcoflamer(1);
        public static final BlockEcoflamer ecoflamer2 = new BlockEcoflamer(2);
        public static final BlockEcoflamer ecoflamer3 = new BlockEcoflamer(3);
        public static final BlockBase witheredbrick = new BlockBase(Material.ROCK, CreativeTabLoader.tabCultivatedTech);
        public static final BlockBase enderizedbrick = new BlockBase(Material.ROCK, CreativeTabLoader.tabCultivatedTech);
        public static final BlockCropHemp crophemp = new BlockCropHemp();
        public static final BlockCropScorch cropscorch = new BlockCropScorch();
        public static final BlockSprinkler sprinkler1 = new BlockSprinkler(1);
        public static final BlockSprinkler sprinkler2 = new BlockSprinkler(2);
        public static final BlockSprinkler sprinkler3 = new BlockSprinkler(3);
        public static final BlockNetherGrass nethergrass = new BlockNetherGrass();
        public static final BlockMagmaLily magmalily = new BlockMagmaLily();
        public static final BlockEnderLily enderlily = new BlockEnderLily();
        public static final BlockIndustrialSoil industrialsoil1 = new BlockIndustrialSoil(1);
        public static final BlockIndustrialSoil industrialsoil2 = new BlockIndustrialSoil(2);
        public static final BlockBase industrialdirt = new BlockIndustrialDirt();
        public static final BlockSwapper swapper = new BlockSwapper();
    }

    public static class Items {
        public static ItemBase hemp = new ItemBase("hemp", tabCultivatedTech);
        public static ItemScorch scorch = new ItemScorch();
        public static ItemBase barrelTap = new ItemBase("barreltap", tabCultivatedTech);
        public static ItemBaseMeta brick = new ItemBrick("brick", 6);
        public static ItemBase witheredBrick = new ItemBase("witheredbrick", tabCultivatedTech);
        public static ItemBase enderizedBrick = new ItemBase("enderizedbrick", tabCultivatedTech);
        public static ItemBaseSeeds seedHemp = new ItemBaseSeeds("seedhemp", Blocks.crophemp, Blocks.industrialsoil1);
    }

    public static class Tiles {
        public static TileEntitySoil tileEntitySoil = new TileEntitySoil();
        public static TileEntityEcoflamer tileEcoflamer = new TileEntityEcoflamer();
        public static TileEntitySprinkler tileEntitySprinkler = new TileEntitySprinkler();
    }
}
