package com.ewyboy.cultivatedtech.common.loaders;

import com.ewyboy.cultivatedtech.common.network.PacketRequestUpdateDebarker;
import com.ewyboy.cultivatedtech.common.network.PacketUpdateDebarker;
import com.ewyboy.cultivatedtech.common.utility.Reference;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

/**
 * Created by EwyBoy
 */
public class PacketLoader {

    public static SimpleNetworkWrapper wrapper;

    public static void loadPackets() {
        wrapper = NetworkRegistry.INSTANCE.newSimpleChannel(Reference.Info.MOD_ID);
        wrapper.registerMessage(new PacketUpdateDebarker.Handler(), PacketUpdateDebarker.class, 0, Side.CLIENT);
        wrapper.registerMessage(new PacketRequestUpdateDebarker.Handler(), PacketRequestUpdateDebarker.class, 1, Side.SERVER);
    }

}
