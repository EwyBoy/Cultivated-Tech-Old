package com.ewyboy.cultivatedtech.common.items;

import com.ewyboy.bibliotheca.common.item.ItemBaseMeta;
import com.ewyboy.cultivatedtech.common.loaders.CreativeTabLoader;
import com.ewyboy.cultivatedtech.common.register.Register;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemBrick extends ItemBaseMeta {

    private int maxDmg;
    private int dmg;

    public ItemBrick(String name, int maxDmg) {
        super(name, maxDmg);
        this.maxDmg = maxDmg;
    }

    @Override
    public boolean showDurabilityBar(ItemStack stack) {
        return false;
    }

    /**
     * DEBUG CODE TODO REMOVE BEFORE RELEASE
     **/
   /* @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        ItemStack stack = playerIn.getHeldItem(handIn);
        if (!worldIn.isRemote) {
            if (stack.getMetadata() < stack.getMaxDamage()) {
                playerIn.setHeldItem(handIn, new ItemStack(playerIn.getHeldItem(handIn).getItem(), 1, playerIn.getHeldItem(handIn).getMetadata() + 1));
            } else if (stack.getMetadata() == 6) {
                playerIn.setHeldItem(handIn, new ItemStack(Register.Items.witheredBrick));
            }
        }
        return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
    }*/

    @Override
    @SideOnly(Side.CLIENT)
    public void registerItemRenderer() {
        ModelResourceLocation model0 = new ModelResourceLocation(getRegistryName() + "_0", "inventory");
        ModelResourceLocation model1 = new ModelResourceLocation(getRegistryName() + "_1", "inventory");
        ModelResourceLocation model2 = new ModelResourceLocation(getRegistryName() + "_2", "inventory");
        ModelResourceLocation model3 = new ModelResourceLocation(getRegistryName() + "_3", "inventory");
        ModelResourceLocation model4 = new ModelResourceLocation(getRegistryName() + "_4", "inventory");
        ModelResourceLocation model5 = new ModelResourceLocation(getRegistryName() + "_5", "inventory");
        ModelResourceLocation model6 = new ModelResourceLocation(getRegistryName() + "_6", "inventory");

        ModelBakery.registerItemVariants(this, model0, model1, model2, model3, model4, model5, model6);
        ModelLoader.setCustomMeshDefinition(this, stack -> {
            switch (stack.getMetadata()) {
                case 0: return model0;
                case 1: return model1;
                case 2: return model2;
                case 3: return model3;
                case 4: return model4;
                case 5: return model5;
                case 6: return model6;

                default: return model6;
            }
        });
    }
}
