package com.ewyboy.cultivatedtech.common.loaders;

import net.minecraftforge.common.config.Configuration;

import java.io.File;

/**
 * Created by EwyBoy
 **/
public class ConfigLoader {

    public static void registerConfig(File file) {
        Configuration config = new Configuration(file);

        config.load();
            //Config here
        config.save();
    }
}
