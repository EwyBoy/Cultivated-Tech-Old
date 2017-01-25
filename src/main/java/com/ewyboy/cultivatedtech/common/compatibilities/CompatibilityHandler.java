package com.ewyboy.cultivatedtech.common.compatibilities;

import com.ewyboy.cultivatedtech.common.compatibilities.waila.WailaCompatibility;
import net.minecraftforge.fml.common.Loader;

/** Created by EwyBoy **/
public class CompatibilityHandler {
    public static void registerWaila() {
        if (Loader.isModLoaded("Waila")) WailaCompatibility.register();
    }
}