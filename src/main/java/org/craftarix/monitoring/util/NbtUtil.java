package org.craftarix.monitoring.util;

import lombok.experimental.UtilityClass;
import lombok.var;
import net.minecraft.server.v1_16_R3.NBTTagCompound;
import org.bukkit.craftbukkit.v1_16_R3.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

@UtilityClass
public class NbtUtil {
    public final String AntiDupeTAG = "AntiDupeMenu";
    public ItemStack setTag(ItemStack itemStack, String key, String value){
        var nmsItem = CraftItemStack.asNMSCopy(itemStack);
        var itemCompound = (nmsItem.hasTag()) ? nmsItem.getTag() : new NBTTagCompound();
        itemCompound.setString(key, value);
        nmsItem.setTag(itemCompound);
        return CraftItemStack.asBukkitCopy(nmsItem);
    }
    public boolean hasTag(ItemStack itemStack, String key){
        var nmsItem = CraftItemStack.asNMSCopy(itemStack);
        var itemCompound = (nmsItem.hasTag()) ? nmsItem.getTag() : new NBTTagCompound();
        return itemCompound.hasKey(key);
    }
    public String getTag(ItemStack itemStack, String key){
        var nmsItem = CraftItemStack.asNMSCopy(itemStack);
        var itemCompound = (nmsItem.hasTag()) ? nmsItem.getTag() : new NBTTagCompound();
        return itemCompound.getString(key);
    }
}
