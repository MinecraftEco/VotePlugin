package org.craftarix.monitoring.util;

import lombok.experimental.UtilityClass;
import lombok.var;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;

@UtilityClass
public class NbtUtil {
    private static final String VERSION;

    private static final ConcurrentHashMap<String, Method> methodCache = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<String, Class<?>> classCache = new ConcurrentHashMap<>();
    static {
        VERSION = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];

        try{
            classCache.put("NBTTagCompound", Class.forName("net.minecraft.server." + VERSION + "." + "NBTTagCompound"));
            classCache.put("ItemStack", Class.forName("net.minecraft.server." + VERSION + "." + "ItemStack"));
        }
        catch (ClassNotFoundException ignored){
            try{
                classCache.put("ItemStack", Class.forName("net.minecraft.world.item.ItemStack"));
                classCache.put("NBTTagCompound", Class.forName("net.minecraft.nbt.NBTTagCompound"));
            }
            catch (ClassNotFoundException ex){
                ex.printStackTrace();
            }
        }
        try{
            classCache.put("CraftItemStack", Class.forName("org.bukkit.craftbukkit." + VERSION + ".inventory." + "CraftItemStack"));
            methodCache.put("asNMSCopy", classCache.get("CraftItemStack").getMethod("asNMSCopy", ItemStack.class));
            methodCache.put("asBukkitCopy", classCache.get("CraftItemStack").getMethod("asBukkitCopy", classCache.get("ItemStack")));
            methodCache.put("getTag", classCache.get("ItemStack").getMethod("getTag"));
            methodCache.put("setTag", classCache.get("ItemStack").getMethod("setTag", classCache.get("NBTTagCompound")));
            methodCache.put("hasTag", classCache.get("ItemStack").getMethod("hasTag"));
            methodCache.put("setString", classCache.get("NBTTagCompound").getMethod("setString", String.class, String.class));
            methodCache.put("getString", classCache.get("NBTTagCompound").getMethod("getString", String.class));
            methodCache.put("hasKey", classCache.get("NBTTagCompound").getMethod("hasKey", String.class));
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }
    public final String AntiDupeTAG = "AntiDupeMenu";
    public ItemStack setTag(ItemStack itemStack, String key, String value){
        ItemStack finalItemStack = null;
        try{
            var itemCompound = getNbtTagCompound(itemStack);
            var nmsItem = methodCache.get("asNMSCopy").invoke(null, itemStack);
            methodCache.get("setString").invoke(itemCompound, key, value);
            methodCache.get("setTag").invoke(nmsItem, itemCompound);
            finalItemStack = (ItemStack) methodCache.get("asBukkitCopy").invoke(null, nmsItem);
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        return finalItemStack;
    }
    public boolean hasTag(ItemStack itemStack, String key){
        boolean hasTag = false;
        try{
            var itemCompound = getNbtTagCompound(itemStack);
            hasTag = (boolean)methodCache.get("hasKey").invoke(itemCompound, key);
        }
        catch (Exception ex){
            ex.printStackTrace();
        }

        return hasTag;
    }
    public String getTag(ItemStack itemStack, String key){
        var tag = "";
        try {
            var itemCompound = getNbtTagCompound(itemStack);
            tag = (String) methodCache.get("getString").invoke(itemCompound, key);
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        return tag;
    }

    private Object getNbtTagCompound(ItemStack itemStack) throws Exception{
        var nmsItem = methodCache.get("asNMSCopy").invoke(null, itemStack);
        var itemCompound = classCache.get("NBTTagCompound").newInstance();
        if ((boolean) methodCache.get("hasTag").invoke(nmsItem)) {
            itemCompound = methodCache.get("getTag").invoke(nmsItem);
        }
        return itemCompound;
    }
}
