package com.ewyboy.cultivatedtech.common.network;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;


/**
 * Created by EwyBoy
 */
public class PacketHandler {

    private static int packetID = 0;
    public static SimpleNetworkWrapper INSTANCE = null;

    public PacketHandler() {}

    private static int nextID () {
        return packetID++;
    }

    public static void registerMessages(String channelName) {
        INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(channelName);
        registerMessages();
    }

    //Register shit here
    private static void registerMessages() {
        //INSTANCE.registerMessage(Handler.class, class, nextID(), Side.SERVER);
    }
}
