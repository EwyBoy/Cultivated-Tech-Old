package com.ewyboy.cultivatedtech.common.network;

import com.ewyboy.cultivatedtech.common.tiles.TileEntityDebarker;
import io.netty.buffer.ByteBuf;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/**
 * Created by EwyBoy
 */
public class PacketRequestUpdateDebarker implements IMessage {

    private BlockPos pos;
    private int dimension;

    public PacketRequestUpdateDebarker(BlockPos pos, int dimension) {
        this.pos = pos;
        this.dimension = dimension;
    }

    public PacketRequestUpdateDebarker(TileEntityDebarker te) {
        this(te.getPos(), te.getWorld().provider.getDimension());
    }

    public PacketRequestUpdateDebarker() {}

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeLong(pos.toLong());
        buf.writeInt(dimension);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        pos = BlockPos.fromLong(buf.readLong());
        dimension = buf.readInt();
    }

    public static class Handler implements IMessageHandler<PacketRequestUpdateDebarker, PacketUpdateDebarker> {
        @Override
        public PacketUpdateDebarker onMessage(PacketRequestUpdateDebarker message, MessageContext ctx) {
            World world = FMLCommonHandler.instance().getMinecraftServerInstance().getWorld(message.dimension);
            TileEntityDebarker te = (TileEntityDebarker) world.getTileEntity(message.pos);
            return te != null ? new PacketUpdateDebarker(te) : null;
        }
    }
}
