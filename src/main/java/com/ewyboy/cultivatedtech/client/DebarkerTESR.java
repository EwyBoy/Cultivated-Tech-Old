package com.ewyboy.cultivatedtech.client;

import com.ewyboy.cultivatedtech.common.tiles.TileEntityDebarker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;

/**
 * Created by EwyBoy
 */
public class DebarkerTESR extends TileEntitySpecialRenderer<TileEntityDebarker> {

    private void renderItem(TileEntityDebarker te) {
        if (!te.getStack().isEmpty()) {
            RenderHelper.enableStandardItemLighting();
            GlStateManager.enableLighting();
            GlStateManager.pushMatrix();

            GlStateManager.translate(.5, .5, .5);
            GlStateManager.scale(0.75f, 0.8f, 0.75f);

            Minecraft.getMinecraft().getRenderItem().renderItem(te.getStack(), ItemCameraTransforms.TransformType.NONE);

            GlStateManager.popMatrix();
        }
    }


    @Override
    public void render(TileEntityDebarker te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        GlStateManager.pushAttrib();
        GlStateManager.pushMatrix();

        // Translate to the location of our tile entity
        GlStateManager.translate(x, y, z);
        GlStateManager.disableRescaleNormal();

        // Render our item
        renderItem(te);

        GlStateManager.popMatrix();
        GlStateManager.popAttrib();
    }
}
