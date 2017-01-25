package com.ewyboy.cultivatedtech.common.utility;

import net.minecraft.util.ResourceLocation;

/**
 * Created by EwyBoy
 **/
public class ResourceLocationHelper {

    public static ResourceLocation createResourceLocation(String path) {
        return new ResourceLocation(Reference.Info.MODID, path);
    }

    public static ResourceLocation createGuiResourceLocation(String path) {
        return ResourceLocationHelper.createResourceLocation("textures" + "/" + "gui" + "/" + path);
    }

    public static ResourceLocation createBlockResourceLocation(String path) {
        return ResourceLocationHelper.createResourceLocation("blocks" + "/" + path);
    }

}
