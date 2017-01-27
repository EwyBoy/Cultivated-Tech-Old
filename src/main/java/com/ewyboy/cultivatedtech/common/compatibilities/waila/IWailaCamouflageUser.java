package com.ewyboy.cultivatedtech.common.compatibilities.waila;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import net.minecraft.item.ItemStack;

import java.util.List;

/**
 * Created by EwyBoy
 */
public interface IWailaCamouflageUser {
    ItemStack getWailaStack(IWailaDataAccessor accessor, IWailaConfigHandler config);
}
