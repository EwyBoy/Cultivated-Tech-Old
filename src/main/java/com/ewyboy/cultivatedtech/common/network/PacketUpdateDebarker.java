package com.ewyboy.cultivatedtech.common.network;

import com.ewyboy.cultivatedtech.common.tiles.TileEntityDebarker;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/**
 * Created by EwyBoy
 */
public class PacketUpdateDebarker implements IMessage {

    private BlockPos pos;
    private ItemStack stack;
    private long lastChangeTime;

    public PacketUpdateDebarker(BlockPos pos, ItemStack stack, long lastChangeTime) {
        this.pos = pos;
        this.stack = stack;
        this.lastChangeTime = lastChangeTime;
    }

    public PacketUpdateDebarker(TileEntityDebarker te) {
        this(te.getPos(), te.getStack(), te.lastChangeTime);
    }

    public PacketUpdateDebarker() {}

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeLong(pos.toLong());
        ByteBufUtils.writeItemStack(buf, stack);
        buf.writeLong(lastChangeTime);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        pos = BlockPos.fromLong(buf.readLong());
        stack = ByteBufUtils.readItemStack(buf);
        lastChangeTime = buf.readLong();
    }

    public static class Handler implements IMessageHandler<PacketUpdateDebarker, IMessage> {
        @Override
        public IMessage onMessage(PacketUpdateDebarker message, MessageContext ctx) {
            Minecraft.getMinecraft().addScheduledTask(() -> {
                TileEntityDebarker te = (TileEntityDebarker) Minecraft.getMinecraft().world.getTileEntity(message.pos);
                if (te != null) {
                    te.inventory.setStackInSlot(0, message.stack);
                    te.lastChangeTime = message.lastChangeTime;
                }
            });
            return null;
        }

    }

}
