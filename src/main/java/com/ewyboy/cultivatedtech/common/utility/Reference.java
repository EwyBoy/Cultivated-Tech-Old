package com.ewyboy.cultivatedtech.common.utility;

/**
 * Created by EwyBoy
 **/
public class Reference {

    public static final class Info {
        public static final String MODID = "cultivatedtech";
        public static final String NAME = "Cultivated Tech";
        static final String VERSION_MAJOR = "1";
        static final String VERSION_MINOR = "0";
        static final String VERSION_PATCH = "0";
        public static final String MINECRAFT_VERSION = "1.10.2";
        public static final String BUILD_VERSION = VERSION_MAJOR + "." + VERSION_MINOR + "." + VERSION_PATCH + "-" + MINECRAFT_VERSION;
    }

    public static final class Paths {
        public static final String CLIENT_PROXY = "com.ewyboy.cultivatedtech.proxy.ClientProxy";
        public static final String COMMON_PROXY = "com.ewyboy.cultivatedtech.proxy.CommonProxy";
        public static final String wailaPath = "com.ewyboy.cultivatedtech.common.compatibilities.waila.WailaCompatibility.load";
    }
}
