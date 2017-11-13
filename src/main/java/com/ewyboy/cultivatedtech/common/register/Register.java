package com.ewyboy.cultivatedtech.common.register;

import com.ewyboy.bibliotheca.common.block.BlockBase;
import com.ewyboy.bibliotheca.common.item.ItemBase;
import com.ewyboy.bibliotheca.common.item.ItemBaseMeta;
import com.ewyboy.cultivatedtech.common.blocks.*;
import com.ewyboy.cultivatedtech.common.blocks.crops.BlockCropHemp;
import com.ewyboy.cultivatedtech.common.blocks.fluid.CTFluidBlock;
import com.ewyboy.cultivatedtech.common.items.ItemBaseSeeds;
import com.ewyboy.cultivatedtech.common.items.ItemBrick;
import com.ewyboy.cultivatedtech.common.loaders.CreativeTabLoader;
import com.ewyboy.cultivatedtech.common.loaders.FluidLoader;
import com.ewyboy.cultivatedtech.common.material.UnknownMaterials;
import com.ewyboy.cultivatedtech.common.tiles.TileEntityEcoflamer;
import com.ewyboy.cultivatedtech.common.tiles.TileEntitySoil;
import com.ewyboy.cultivatedtech.common.tiles.TileEntitySprinkler;
import net.minecraft.block.material.Material;
import net.minecraftforge.fluids.BlockFluidClassic;

import static com.ewyboy.cultivatedtech.common.loaders.CreativeTabLoader.tabCultivatedTech;

public class Register {

    public static class Blocks {
        public static final BlockFluidClassic ethanol = new CTFluidBlock(FluidLoader.ETHANOL, UnknownMaterials.FUEL);
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
        public static final BlockSprinkler sprinkler1 = new BlockSprinkler(1);
        public static final BlockSprinkler sprinkler2 = new BlockSprinkler(2);
        public static final BlockSprinkler sprinkler3 = new BlockSprinkler(3);
    }

    public static class Items {
        public static ItemBase hemp = new ItemBase("hemp", tabCultivatedTech);
        public static ItemBase barrelTap = new ItemBase("barreltap", tabCultivatedTech);
        public static ItemBaseMeta brick = new ItemBrick("brick", 6);
        public static ItemBase witheredBrick = new ItemBase("witheredbrick", tabCultivatedTech);
        public static ItemBase enderizedBrick = new ItemBase("enderizedbrick", tabCultivatedTech);
        public static ItemBaseSeeds seedHemp = new ItemBaseSeeds("seedhemp", Blocks.crophemp, Register.Blocks.soil);
    }

    public static class Tiles {
        public static TileEntitySoil tileEntitySoil = new TileEntitySoil();
        public static TileEntityEcoflamer tileEcoflamer = new TileEntityEcoflamer();
        public static TileEntitySprinkler tileEntitySprinkler = new TileEntitySprinkler();
    }
}
